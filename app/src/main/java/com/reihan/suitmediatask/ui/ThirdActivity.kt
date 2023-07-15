package com.reihan.suitmediatask.ui

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.reihan.suitmediatask.adapter.UserAdapter
import com.reihan.suitmediatask.api.ApiConfig
import com.reihan.suitmediatask.databinding.ActivityThirdBinding
import com.reihan.suitmediatask.model.User
import com.reihan.suitmediatask.model.UserData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ThirdActivity : AppCompatActivity(), SwipeRefreshLayout.OnRefreshListener {
    private lateinit var activityThirdBinding: ActivityThirdBinding
    private lateinit var userAdapter: UserAdapter
    private var isLoading = false
    private var page: Int = 1
    private var totalPage: Int = 1
    private lateinit var layoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.title = TAG

        super.onCreate(savedInstanceState)
        activityThirdBinding = ActivityThirdBinding.inflate(layoutInflater)
        setContentView(activityThirdBinding.root)

        layoutManager = LinearLayoutManager(this)
        setUpRecyclerView()
        userAdapter.setClickCallback(object : UserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: User) {
                Intent(this@ThirdActivity, SecondActivity::class.java).also {
                    it.putExtra(SecondActivity.EXTRA_NAME, data.first_name)
                    startActivity(it)
                }
            }
        })

        activityThirdBinding.swipeRefresh.setOnRefreshListener(this)
        getUsers(false)

        activityThirdBinding.recyclerview.addOnScrollListener(object :
            RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val visibleItemCount = layoutManager.childCount
                val pastVisibleItem = layoutManager.findFirstVisibleItemPosition()
                val total = userAdapter.itemCount
                if (!isLoading && page < totalPage) {
                    if (visibleItemCount + pastVisibleItem >= total) {
                        page++
                        getUsers(false)
                    }
                }
                super.onScrolled(recyclerView, dx, dy)
            }
        })
    }

    private fun setUpRecyclerView() {
        userAdapter = UserAdapter()
        activityThirdBinding.apply {
            recyclerview.layoutManager = layoutManager
            recyclerview.setHasFixedSize(true)
            recyclerview.adapter = userAdapter
        }
    }

    private fun getUsers(isOnRefresh: Boolean) {
        isLoading = true
        if (!isOnRefresh) {
            activityThirdBinding.progressBar.visibility = View.VISIBLE
        }

        @Suppress("DEPRECATION")
        Handler().postDelayed({
            val parameters = HashMap<String, String>()
            parameters["page"] = page.toString()
            ApiConfig.endpoint.mSearch(parameters).enqueue(object : Callback<UserData> {
                override fun onResponse(call: Call<UserData>, response: Response<UserData>) {
                    if (response.isSuccessful) {
                        totalPage = response.body()?.total_pages!!
                        val listUsers = response.body()?.data
                        if (listUsers!!.isNotEmpty()) {
                            userAdapter.setList(listUsers)
                        }
                        activityThirdBinding.progressBar.visibility = View.GONE
                        isLoading = false
                        activityThirdBinding.swipeRefresh.isRefreshing = false
                    }
                }

                override fun onFailure(call: Call<UserData>, t: Throwable) {
                    Toast.makeText(this@ThirdActivity, failConnection, Toast.LENGTH_SHORT).show()
                    activityThirdBinding.progressBar.visibility = View.GONE
                    isLoading = false
                    activityThirdBinding.swipeRefresh.isRefreshing = false
                }
            })
        }, 3000)
    }

    override fun onSupportNavigateUp(): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            finishAfterTransition()
        }
        return super.onSupportNavigateUp()
    }

    override fun onRefresh() {
        userAdapter.clearUsers()
        page = 1
        getUsers(true)
    }

    companion object {
        const val TAG = "Third Screen"
        var failConnection = "Koneksi Gagal"
    }
}


package com.reihan.suitmediatask.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.reihan.suitmediatask.R
import com.reihan.suitmediatask.databinding.ActivitySecondBinding

class SecondActivity : AppCompatActivity() {

    private lateinit var activitySecondBinding : ActivitySecondBinding

    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activitySecondBinding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(activitySecondBinding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.title = TAG

        val text = intent.getStringExtra(EXTRA_NAME)
        activitySecondBinding.namaUser.text = text

        var selectUser = activitySecondBinding.selectedUser.isVisible

        activitySecondBinding.namaUser.setOnClickListener {
            activitySecondBinding.selectedUser.visibility = View.VISIBLE
            activitySecondBinding.selectedUser.text = SELECTED_USER
            activitySecondBinding.namaUser.setTextColor(R.color.dark_blue)
            selectUser = false
        }

        activitySecondBinding.buttonChoose.setOnClickListener {
            intent = Intent(this@SecondActivity, ThirdActivity::class.java)
            if (!selectUser){
                startActivity(intent)
            }else {
                Toast.makeText(this@SecondActivity, clickName, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            finishAfterTransition()
        }
        return super.onSupportNavigateUp()
    }

    companion object {
        const val TAG = "Second Screen"
        const val EXTRA_NAME = "extra_name"
        const val SELECTED_USER = "Selected User Name"
        var clickName = "Must Click Name!"
    }

}
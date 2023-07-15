package com.reihan.suitmediatask.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.reihan.suitmediatask.R
import com.reihan.suitmediatask.databinding.ListUserBinding
import com.reihan.suitmediatask.model.User

@SuppressLint("NotifyDataSetChanged")
class UserAdapter: RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    private val list = ArrayList<User>()
    private var itemCallback: OnItemClickCallback? = null

    fun setClickCallback(ItemClickCallback: OnItemClickCallback){
        this.itemCallback = ItemClickCallback
    }

    fun clearUsers() {
        this.list.clear()
        notifyDataSetChanged()
    }

    fun setList(users:ArrayList<User>){
        list.clear()
        list.addAll(users)
        notifyDataSetChanged()
    }
    inner class UserViewHolder(private val binding: ListUserBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User) {
            binding.root.setOnClickListener{
                itemCallback?.onItemClicked(user)
            }
            binding.apply {
                Glide.with(itemView)
                    .load(user.avatar)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .placeholder(R.drawable.ic_photo)
                    .centerCrop()
                    .into(imgUser)
                firstName.text = user.first_name
                lastName.text = user.last_name
                email.text = user.email
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = ListUserBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return  UserViewHolder((view))
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size

    interface OnItemClickCallback{
        fun onItemClicked(data:User)
    }
}
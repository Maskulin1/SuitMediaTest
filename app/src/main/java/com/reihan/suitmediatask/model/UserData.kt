package com.reihan.suitmediatask.model

data class UserData (
    val page: Int,
    val total_pages: Int,
    val data : ArrayList<User>
)
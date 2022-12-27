package com.example.branchinternational.Model

data class Messages(
    val id:Int,
    val thread_id:Int,
    val user_id:Int,
    val body:String,
    val timestamp:String,
    val agent_id:Int
)

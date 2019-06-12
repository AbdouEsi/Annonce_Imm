package com.example.annonces_immobillieres


data class Announce(

    val type: String,
    val wilaya: String,
    val description: String,
    val date: String,
    val coordone_X: String,
    val coordone_Y: String,
    val username: String,
    val mobile: String,
    val email: String,
    val photo: Int

)

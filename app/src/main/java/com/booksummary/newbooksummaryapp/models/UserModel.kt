package com.booksummary.newbooksummaryapp.models

data class UserModel(
    val name            : String = "",
    val email           : String = "",
    val profileUrl      : String = "",
    val fcmToken        : String = "",
    val uid             : String = ""
)

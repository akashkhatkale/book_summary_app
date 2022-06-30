package com.booksummary.newbooksummaryapp.models

import com.google.firebase.auth.FirebaseUser

data class UserModel(
    val name            : String = "",
    val email           : String = "",
    val profileUrl      : String = "",
    val fcmToken        : String = "",
    val uid             : String = "",
    val coins           : Int = 0
) {
    fun fromUser(user: FirebaseUser, token: String) : UserModel {
        val name = user.displayName ?: ""
        val email = user.email ?: ""
        val profileUrl = user.photoUrl.toString()
        val uid = user.uid

        return UserModel(name=name, email=email, profileUrl=profileUrl, uid=uid, fcmToken=token)
    }
}

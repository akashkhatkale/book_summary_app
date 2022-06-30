package com.booksummary.newbooksummaryapp.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.booksummary.newbooksummaryapp.models.UserModel
import com.booksummary.newbooksummaryapp.repositories.AuthRepository
import com.google.firebase.auth.AuthCredential
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {

    private val authDb = AuthRepository()

    private var user : MutableLiveData<UserModel?> = MutableLiveData()


    // get user
    fun getUser() : MutableLiveData<UserModel?> = user

    // post user
    fun postUser(user : UserModel) = this.user.postValue(user)

    fun signInWithGoogle(credential: AuthCredential) : Boolean {
        viewModelScope.launch {
            var result = authDb.signInWithGoogle(credential)

            if (result.data != null) {
                // success
                user.postValue(result.data)
                true
            } else {
                user.postValue(null)
                false
            }
        }

        return false
    }

}
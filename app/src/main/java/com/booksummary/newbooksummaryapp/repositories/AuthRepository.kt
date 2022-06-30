package com.booksummary.newbooksummaryapp.repositories

import android.util.Log
import com.booksummary.newbooksummaryapp.models.DataOrException
import com.booksummary.newbooksummaryapp.models.UserModel
import com.booksummary.newbooksummaryapp.utils.constants.LOGINLOG
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.iid.FirebaseInstanceId
import kotlinx.coroutines.tasks.await

class AuthRepository {
    private var firestore = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    // SIGN IN WITH GOOGLE
    suspend fun signInWithGoogle(credential : AuthCredential) : DataOrException<UserModel, Exception> {
        try{
            Log.d(LOGINLOG,"Signin in....")
            val task = auth.signInWithCredential(credential).await()
            return if(task.user != null){
                var isAuthenticated = authenticateCurrentUser(task.user!!)
                isAuthenticated
            }else{
                DataOrException(null, null)
            }
        }catch (e : Exception){
            Log.d(LOGINLOG,"Error in signing with google: ${e.localizedMessage}")
            return DataOrException(null, e)
        }
    }

    suspend fun authenticateCurrentUser(user : FirebaseUser) : DataOrException<UserModel, Exception> {
        try{
            var ref= firestore.collection("users").document(user.uid)
            val data = ref.get().await()
            val token = FirebaseInstanceId.getInstance().instanceId.await().token

            if(!data.exists()){
                Log.d(LOGINLOG,"User doesnt exists")
                val u = UserModel().fromUser(user, token)
                ref.set(u).await()

                return DataOrException(u, null)
            }else{
                Log.d(LOGINLOG,"User exists")
                return DataOrException(data.toObject(UserModel::class.java), null)
            }
        }catch (e : Exception){
            Log.d(LOGINLOG,"Error : ${e.localizedMessage}")
            return DataOrException(null, e)
        }
    }

}
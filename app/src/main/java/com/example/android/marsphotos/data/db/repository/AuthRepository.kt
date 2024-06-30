package com.example.android.marsphotos.data.db.repository

import com.example.android.marsphotos.data.model.Login
import com.example.android.marsphotos.data.db.remote.FirebaseAuthSource
import com.example.android.marsphotos.data.db.remote.FirebaseAuthStateObserver
import com.example.android.marsphotos.data.Result
import com.google.firebase.auth.FirebaseUser
import android.util.Log

class AuthRepository{
    private val firebaseAuthService = FirebaseAuthSource()

    fun observeAuthState(stateObserver: FirebaseAuthStateObserver, b: ((Result<FirebaseUser>) -> Unit)){
        firebaseAuthService.attachAuthStateObserver(stateObserver,b)
    }

    fun loginUser(login: Login, b: (Result<FirebaseUser>) -> Unit) {
        b.invoke(Result.Loading)
        Log.v("Login", login.toString())
        firebaseAuthService.loginWithEmailAndPassword(login).addOnSuccessListener {
            Log.v("AuthResult", it.user.toString())
            b.invoke(Result.Success(it.user))
        }.addOnFailureListener {
            Log.v("Error", it.toString())
            b.invoke(Result.Error(msg = it.message))}

//        firebaseAuthService.loginWithEmailAndPassword(login).addOnCompleteListener() {it ->
//            Log.v("AuthResult", it.result.toString())
//            if(it.isSuccessful) {
//                Log.v("user", it.result.user.toString())
//                b.invoke(Result.Success(it.result.user))
//            }
//            else{
//                Log.v("Error", it.toString())
//              b.invoke(Result.Error(msg = it.exception?.message))
//            }
//        }
    }

    fun logoutUser() {
        firebaseAuthService.logout()
    }
}
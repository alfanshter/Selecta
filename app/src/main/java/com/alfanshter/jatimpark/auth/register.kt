package com.alfanshter.jatimpark.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.alfanshter.jatimpark.FirebaseHelper.FirebaseHelper
import com.alfanshter.jatimpark.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_register.*
import org.jetbrains.anko.toast

class register : AppCompatActivity() {
    private lateinit var mAuth : FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        mAuth = FirebaseAuth.getInstance()
        signup.setOnClickListener {
            daftar()
        }
    }

    private fun daftar()
    {
        val email = email.text.toString().trim()
        val username = user.text.toString().trim()
        val password = pass.text.toString().trim()
        val nomor = mob.text.toString().trim()

        if (email.isNotEmpty() && username.isNotEmpty() && password.isNotEmpty() && nomor.isNotEmpty()){
            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener {
                if (it.isSuccessful){
                    val me = mAuth.currentUser
                    if (me!=null){
                        FirebaseHelper.pushUserData(email,username,me.uid)
                    }
                    toast("Register Berhasil")
                    finish()
                }
                else{
                    toast("RegisterFailde")
                }
            }
        }
        else{
            toast("isi semua from")
        }
    }
}

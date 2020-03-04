package com.alfanshter.jatimpark.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.alfanshter.jatimpark.FirebaseHelper.FirebaseHelper
import com.alfanshter.jatimpark.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_register.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class register : AppCompatActivity() {
    private lateinit var auth : FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        auth = FirebaseAuth.getInstance()
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
            auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener {
                if (it.isSuccessful){
                    val me = auth.currentUser
                    if (me!=null){
                        FirebaseHelper.pushUserData(email,username,me.uid,nomor)
                    }
                    toast("Register Berhasil")
                    startActivity<Login>("email" to email,"password" to password,"nomor" to nomor)
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

package com.alfanshter.jatimpark.auth

import android.app.ProgressDialog
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.alfanshter.jatimpark.Menu
import com.alfanshter.jatimpark.R
import com.alfanshter.jatimpark.Rombongan.NamaProfil
import com.alfanshter.jatimpark.Session.SessionManager
import com.alfanshter.jatimpark.Tracking
import com.alfanshter.jatimpark.Tracking_Rombongan
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.karan.churi.PermissionManager.PermissionManager
import kotlinx.android.synthetic.main.login.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast


class Login : AppCompatActivity(), View.OnClickListener {
    lateinit var user: FirebaseUser
    lateinit var reference: DatabaseReference
    lateinit var progressdialog: ProgressDialog
    lateinit var userID: String
    lateinit var manager : PermissionManager

    private lateinit var sessionManager: SessionManager

    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {

        manager = object : PermissionManager() {}
        manager.checkAndRequestPermissions(this)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        sessionManager = SessionManager(this)

        login.setOnClickListener(this)
        progressdialog = ProgressDialog(this)
        reference = FirebaseDatabase.getInstance().reference.child("Selecta/Users")

        auth = FirebaseAuth.getInstance()
        login.setOnClickListener {
            login()
        }

        if (sessionManager.getLogin()!!) {
            startActivity<NamaProfil>()
        }

        signup.setOnClickListener {
            startActivity<register>()
        }
    }


    fun login() {
        var users = users.text.toString()
        var password = pass.text.toString()
        auth.signInWithEmailAndPassword(users, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    progressdialog.onStart()
                    user = auth.currentUser!!
                    userID = user.uid
                    reference.child(userID).child("email").setValue(users)
                    reference.child(userID).child("password").setValue(password)
                    reference.child(userID).child("iduser").setValue(userID)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                progressdialog.dismiss()
                                toast("user telah terregistrasi")
                                //menambah sesion
                                sessionManager.setLogin(true)
                                sessionManager.setIduser(users)
                            } else {
                                progressdialog.dismiss()
                                toast("user gagal di registrasi")
                            }
                        }
                    toast("Login Berhasil ")
                    startActivity<NamaProfil>()
                } else {
                    toast("gagal")
                }
            }
    }

    override fun onClick(v: View?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        manager.checkResult(requestCode,permissions,grantResults)
        val denied_permission : ArrayList<String> = manager.status.get(0).denied
        if (denied_permission.isEmpty())
        {
            toast("permission aktif")
        }
    }
}


package com.alfanshter.jatimpark.auth

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Context
import android.net.wifi.WifiManager
import android.os.Bundle
import android.view.View
import android.widget.NumberPicker
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
import org.jetbrains.anko.wifiManager
import java.util.*
import java.util.logging.Formatter
import kotlin.collections.ArrayList


class Login : AppCompatActivity(), View.OnClickListener {
    lateinit var user: FirebaseUser
    lateinit var reference: DatabaseReference
    lateinit var progressdialog: ProgressDialog
    lateinit var userID: String
    lateinit var manager : PermissionManager

    lateinit var ip : String
    private lateinit var sessionManager: SessionManager

    private lateinit var auth: FirebaseAuth
    @SuppressLint("HardwareIds")
    override fun onCreate(savedInstanceState: Bundle?) {

        manager = object : PermissionManager() {}
        manager.checkAndRequestPermissions(this)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)
        val manager : WifiManager = application.getSystemService(Context.WIFI_SERVICE) as WifiManager
        ip = android.text.format.Formatter.formatIpAddress(manager.connectionInfo.ipAddress)
        toast(ip)
        val info = wifiManager.connectionInfo
        val alfan = info.ssid
toast(alfan)
        if (info.equals("AndroidWifi"))
        {
            toast("uwaw")
        }

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
            finish()
        }

        signup.setOnClickListener {
            startActivity<register>()
        }
    }


    fun login() {
        val progressDialog = ProgressDialog(this)
        progressDialog.setTitle("uploading....")
        progressDialog.show()


        val bundle :Bundle ?=intent.extras



        var userss = users.text.toString()
        var password = pass.text.toString()
if (pass.text.toString().trim().isNotEmpty() && users.toString().trim().isNotEmpty())
{
    auth.signInWithEmailAndPassword(userss, password)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                progressdialog.onStart()
                user = auth.currentUser!!
                userID = user.uid
                reference.child(userID).child("email").setValue(userss)
                reference.child(userID).child("password").setValue(password)
                reference.child(userID).child("iduser").setValue(userID)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            progressdialog.dismiss()
                            toast("user telah terregistrasi")
                            //menambah sesion
                            sessionManager.setLogin(true)
                            sessionManager.setIduser(userss)
                        } else {
                            progressdialog.dismiss()
                            toast("user gagal di registrasi")
                        }
                    }
                progressDialog.dismiss()
                toast("Login Berhasil ")
                startActivity<NamaProfil>()
            } else {
                toast("gagal")
            }
        }

}
        else{
    toast("masukkan username dan password")

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


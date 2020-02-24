package com.alfanshter.jatimpark

import android.app.ProgressDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.widget.Constraints
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.alfanshter.jatimpark.Session.SessionManager
import com.alfanshter.jatimpark.auth.Login
import com.alfanshter.jatimpark.ui.Tracking.TrackingFragment
import com.alfanshter.jatimpark.ui.dashboard.DashboardFragment
import com.alfanshter.jatimpark.ui.generate.GenerateCode
import com.alfanshter.jatimpark.ui.generateKode.BlankFragment
import com.alfanshter.jatimpark.ui.generateKode.Generate
import com.alfanshter.jatimpark.ui.shareRombongan.ShareRombongan
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.karan.churi.PermissionManager.PermissionManager
import kotlinx.android.synthetic.main.drawer_header.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class Tracking_Rombongan : AppCompatActivity() {
    val PERMISSIONS_REQUEST = 1
    private lateinit var drawerConfiguration: AppBarConfiguration
    private lateinit var sessionManager: SessionManager

    private lateinit var auth: FirebaseAuth
    lateinit var user: FirebaseUser
    lateinit var referencebaru: DatabaseReference
    lateinit var progressdialog: ProgressDialog
    lateinit var userID: String
    lateinit var nilaikode: String
    var namaprofil = ""
    var emailprofil = ""

    private val onNavigationItemSelectedListener = NavigationView.OnNavigationItemSelectedListener { item->
        sessionManager = SessionManager(this)
        when(item.itemId)
        {
            R.id.navigation_generate ->
            {
                supportFragmentManager.beginTransaction().replace(R.id.nav_host_fragment,Generate()).commit()
                return@OnNavigationItemSelectedListener true
            }

            R.id.nav_logout ->{
/*
                supportFragmentManager.beginTransaction().replace(R.id.nav_host_fragment,ShareRombongan()).commit()
                return@OnNavigationItemSelectedListener true
*/
                FirebaseAuth.getInstance().signOut()
                toast("halo")
                sessionManager.setLogin(false)
                sessionManager.setNama(false)
                startActivity<Login>()
            }
        }
            false
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tracking__rombongan)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        auth = FirebaseAuth.getInstance()
        user = auth.currentUser!!
        userID = user.uid

        referencebaru = FirebaseDatabase.getInstance().reference.child("Selecta").child("Users")

        referencebaru.addValueEventListener(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onDataChange(p0: DataSnapshot) {
                namaprofil = p0.child(userID).child("nama").value.toString()
                emailprofil = p0.child(userID).child("email").value.toString()
                nama_drawer.text = namaprofil
                email_drawer.text = emailprofil


            }

        })
        checkPermission()

        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        val drawer: NavigationView = findViewById(R.id.drawer)

        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_sharingmaps,  R.id.navigation_dashboard, R.id.navigation_notifications, R.id.navigation_home
                )
        )

        drawerConfiguration =   AppBarConfiguration(
            setOf(
                R.id.navigation_generate, R.id.nav_gallery, R.id.nav_slideshow,
                R.id.nav_tools, R.id.nav_share
            )
        )


        drawer.setNavigationItemSelectedListener(onNavigationItemSelectedListener)
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

/*
        setupActionBarWithNavController(navController, drawerConfiguration)
        drawer.setupWithNavController(navController)
*/

    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(drawerConfiguration) || super.onSupportNavigateUp()
    }
    fun checkPermission(){

        val permission = ContextCompat.checkSelfPermission(this,
            android.Manifest.permission.ACCESS_FINE_LOCATION)

//If the location permission has been granted, then start the TrackerService//

        if (permission == PackageManager.PERMISSION_GRANTED) {
            startService(Intent(this, trackingservice::class.java))
        } else {

            //If the app doesn’t currently have access to the user’s location, then request access//

            ActivityCompat.requestPermissions(this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION,android.Manifest.permission.ACCESS_FINE_LOCATION),PERMISSIONS_REQUEST)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {

        //If the permission has been granted...//

        if (requestCode == PERMISSIONS_REQUEST && grantResults.size == 1
            && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            //...then start the GPS tracking service//

            startService(Intent(this, trackingservice::class.java))

        } else {

            //If the user denies the permission request, then display a toast with some more information//

            Toast.makeText(this, "Please enable location services to allow GPS tracking", Toast.LENGTH_SHORT).show()
        }
    }


}

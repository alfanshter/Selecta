package com.alfanshter.jatimpark

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Looper
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.LinearLayout.HORIZONTAL
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alfanshter.jatimpark.adapter.LokasiAdapter
import com.mapbox.android.core.location.LocationEngine
import com.mapbox.android.core.location.LocationEngineProvider
import com.mapbox.android.core.location.LocationEngineRequest
import com.mapbox.android.core.permissions.PermissionsListener
import com.mapbox.android.core.permissions.PermissionsManager
import com.mapbox.mapboxsdk.Mapbox
import com.mapbox.mapboxsdk.location.LocationComponentActivationOptions
import com.mapbox.mapboxsdk.location.LocationComponentOptions
import com.mapbox.mapboxsdk.maps.MapboxMap
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback
import com.mapbox.mapboxsdk.maps.Style
import com.mapbox.mapboxsdk.plugins.locationlayer.modes.CameraMode
import com.mapbox.mapboxsdk.plugins.locationlayer.modes.RenderMode
import kotlinx.android.synthetic.main.activity_tracking.*


@Suppress("DEPRECATION")
class Tracking : AppCompatActivity(),OnMapReadyCallback,PermissionsListener{
    private  var permissionsManager: PermissionsManager = PermissionsManager(this)
    private lateinit var mapboxMap: MapboxMap
    private lateinit var startnavigasi: Button

    private var locationEngine:LocationEngine? = null
    private val DEFAULT_INTERVAL_IN_MILLISECONDS = 1000L
    private val DEFAULT_MAX_WAIT_TIME = DEFAULT_INTERVAL_IN_MILLISECONDS * 5
     @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Mapbox.getInstance(this, getString(R.string.access_token))
        setContentView(R.layout.activity_tracking)
        mapview.onCreate(savedInstanceState)
        mapview.getMapAsync(this)
        val recyclerview = findViewById<View>(R.id.recyclerlokasi) as RecyclerView
        val tempat = intArrayOf(R.drawable.selectasatu,R.drawable.selectadua,R.drawable.selectatiga,R.drawable.selectaempat)

        val nama = arrayOf("kolamrenang","tempat ayun","outbond","gulat")
        val layoutManager = LinearLayoutManager(this,HORIZONTAL,false)
        recyclerview.layoutManager = layoutManager
        recyclerview.adapter = LokasiAdapter(tempat,nama,this)
    }

    override fun onMapReady(mapboxMap: MapboxMap) {
        this.mapboxMap = mapboxMap
        mapboxMap.setStyle(
            Style.Builder().fromUri(Style.LIGHT)){
            enableLocationComponent(it)
        }


    }

    //Mengatur pembaruan lokasi
    @SuppressLint("MissingPermission")
    private fun initLocationEngine(){
        locationEngine = LocationEngineProvider.getBestLocationEngine(this)
        val request = LocationEngineRequest.Builder(DEFAULT_INTERVAL_IN_MILLISECONDS)
            .setPriority(LocationEngineRequest.PRIORITY_HIGH_ACCURACY)
            .setMaxWaitTime(DEFAULT_MAX_WAIT_TIME).build()
    }

    @SuppressLint("MissingPermission", "WrongConstant")
    private fun enableLocationComponent(loadedMapStyle: Style){
        if (PermissionsManager.areLocationPermissionsGranted(this)){
            val customLocationComponentOptions = LocationComponentOptions.builder(this)
                .trackingGesturesManagement(true)
                .accuracyColor(ContextCompat.getColor(this,R.color.mapbox_blue))
                .build()

            val locationComponentActivationOptions = LocationComponentActivationOptions.builder(this,loadedMapStyle)
                .locationComponentOptions(customLocationComponentOptions)
                .build()

            mapboxMap.locationComponent.apply {
                activateLocationComponent(locationComponentActivationOptions)
                isLocationComponentEnabled = true
                cameraMode = CameraMode.TRACKING
                renderMode = RenderMode.COMPASS
            }
        } else{
            permissionsManager = PermissionsManager(this)
            permissionsManager.requestLocationPermissions(this)
        }
    }


    override fun onExplanationNeeded(permissionsToExplain: List<String>) {
        Toast.makeText(this, R.string.user_location_permission_explanation, Toast.LENGTH_LONG).show()
    }

    override fun onPermissionResult(granted: Boolean) {
        if (granted)
        {
         enableLocationComponent(mapboxMap.style!!)
        }
        else{
            Toast.makeText(this, R.string.user_location_permission_not_granted, Toast.LENGTH_LONG).show()
            finish()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>,
                                            grantResults: IntArray) {
        permissionsManager.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }




    override fun onStart() {
        super.onStart()
        mapview.onStart()
    }
    override fun onResume() {
        super.onResume()
        mapview.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapview.onPause()
    }

    override fun onStop() {
        super.onStop()
        mapview.onStop()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
            mapview.onSaveInstanceState(outState)
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapview.onLowMemory()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapview.onDestroy()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.menu_item_change_style -> {
                val items = arrayOf(
                    "Mapbox Street",
                    "Outdoor",
                    "Light",
                    "Dark",
                    "Satellite",
                    "Satellite Street",
                    "Traffic Day",
                    "Traffic Night"
                )
                val alertDialogChangeStyleMaps = AlertDialog.Builder(this)
                    .setItems(items) { dialog, item ->
                        when (item) {
                            0 -> {
                                mapboxMap.setStyle(Style.MAPBOX_STREETS)
                                dialog.dismiss()
                            }
                            1 -> {
                                mapboxMap.setStyle(Style.OUTDOORS)
                                dialog.dismiss()
                            }
                            2 -> {
                                mapboxMap.setStyle(Style.LIGHT)
                                dialog.dismiss()
                            }
                            3 -> {
                                mapboxMap.setStyle(Style.DARK)
                                dialog.dismiss()
                            }
                            4 -> {
                                mapboxMap.setStyle(Style.SATELLITE)
                                dialog.dismiss()
                            }
                            5 -> {
                                mapboxMap.setStyle(Style.SATELLITE_STREETS)
                                dialog.dismiss()
                            }
                            6 -> {
                                mapboxMap.setStyle(Style.TRAFFIC_DAY)
                                dialog.dismiss()
                            }
                            7 -> {
                                mapboxMap.setStyle(Style.TRAFFIC_NIGHT)
                                dialog.dismiss()
                            }
                        }
                    }
                    .setTitle(getString(R.string.change_style_maps))
                    .create()
                alertDialogChangeStyleMaps.show()
            }
        }
        return super.onOptionsItemSelected(item)
    }



}


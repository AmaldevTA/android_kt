package com.aml.tracklocation

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.tasks.Task

class MainActivity : AppCompatActivity() {

    companion object {
        const val REQUEST_CHECK_SETTINGS = 151
    }

    private val askMultiplePermissions =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { map ->
            for (entry in map.entries) {
                Toast.makeText(this, "${entry.key} = ${entry.value}", Toast.LENGTH_SHORT).show()
            }
        }

    private val askLocationPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            if (it) {
                Log.e("<<<TAG>>>", "Location permission granted")
            } else {
                Log.e("<<<TAG>>>", "Location permission denied")
                val showRationale =
                    shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)
                if (!showRationale) {
                    // deny and don't ask again chosen
                    showWarningAppSettings()
                }
            }
        }

    private val openSettings =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            checkLocationPermission()
        }

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult?) {
            locationResult ?: return
            for (location in locationResult.locations) {
                // Update UI with location data
                // ...
                Toast.makeText(
                    this@MainActivity,
                    "${location.latitude} - ${location.longitude}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.requestLocationPermission).setOnClickListener {
            checkLocationPermission()
        }

        findViewById<Button>(R.id.requestLocationUpdate).setOnClickListener {
            checkLocationSettings()
        }


        findViewById<Button>(R.id.requestBackgroundLocation).setOnClickListener {

        }

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                Log.e("<<<Location>>>", "${location?.latitude} , ${location?.longitude}")
            }


    }

    override fun onPause() {
        super.onPause()
        stopLocationUpdates()
    }

    private fun checkLocationPermission() {
        val permissionResult = isFineLocationAllowed(this)
        Log.e("<<<TAG>>>", "$permissionResult")
        if (permissionResult == PackageManager.PERMISSION_DENIED) {
            val showRationale =
                shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)

            if (showRationale) {
                // how an educational UI to the user. In this UI, describe why the feature,
                // which the user wants to enable, needs a particular permission.
                Log.e("<<<TAG>>>", "Location permission denied, show alert")
                showLocationWarning()
            } else {
                askLocationPermission.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            }
        }
    }

    private fun isFineLocationAllowed(context: Context): Int {
        return ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
    }

    private fun showLocationWarning() {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("App can't work without location permission.")
        builder.setPositiveButton(
            getString(android.R.string.ok)
        ) { _, _ ->
            askLocationPermission.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }

        val dialog = builder.create()
        dialog.setCancelable(false)
        dialog.show()
    }

    private fun showWarningAppSettings() {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("App can't work without location permission. open settings")
        builder.setPositiveButton(
            getString(android.R.string.ok)
        ) { _, _ ->
            openSettingsPage()
        }

        val dialog = builder.create()
        dialog.setCancelable(false)
        dialog.show()
    }

    private fun openSettingsPage() {
        val uri = Uri.fromParts("package", packageName, null)
        val intent = Intent()
        intent.apply {
            action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
            data = uri
        }

        openSettings.launch(intent)
    }

    private fun checkLocationSettings() {
        val locationRequest = LocationRequest.create()?.apply {
            interval = 10000
            fastestInterval = 5000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }

        if (locationRequest != null) {
            val builder = LocationSettingsRequest.Builder().addLocationRequest(locationRequest)
            val client: SettingsClient = LocationServices.getSettingsClient(this)
            val task: Task<LocationSettingsResponse> =
                client.checkLocationSettings(builder?.build())

            task.addOnSuccessListener { 
                Log.e("<<<TAG>>>", "All location settings are satisfied.")
                startLocationUpdates(locationRequest)
            }

            task.addOnFailureListener { exception ->
                if (exception is ResolvableApiException) {
                    Log.e("<<<TAG>>>", " Location settings are not satisfied.")
                    try {
                        exception.startResolutionForResult(
                            this@MainActivity,
                            REQUEST_CHECK_SETTINGS
                        )
                    } catch (sendEx: IntentSender.SendIntentException) {
                    }
                }
            }
        }

    }

    @SuppressLint("MissingPermission")
    private fun startLocationUpdates(locationRequest: LocationRequest) {
        fusedLocationClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.getMainLooper()
        )
    }

    private fun stopLocationUpdates() {
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }

}
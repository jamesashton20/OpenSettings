package com.james.openwifi

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {

    private val LOCATION_PERMISSION_REQUEST_CODE = 1
    private lateinit var grantPermissionsButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        // Initialize the Grant Permissions button
        grantPermissionsButton = findViewById(R.id.grantPermissionsButton)

        // Check if permissions are granted
        if (permissionsGranted()) {
            // Open Wi-Fi settings automatically if permissions are already granted
            openWifiSettings()
        } else {
            // Show the Grant Permissions button if permissions are not granted
            grantPermissionsButton.visibility = android.view.View.VISIBLE

            // Set click listener for the Grant Permissions button
            grantPermissionsButton.setOnClickListener {
                requestLocationPermission()
            }
        }
    }

    // Function to check if permissions are granted
    private fun permissionsGranted(): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    // Function to request location permission
    private fun requestLocationPermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE
            )
        }
    }

    // Open Wi-Fi settings
    private fun openWifiSettings() {
        val intent = Intent(Settings.ACTION_WIFI_SETTINGS)
        startActivity(intent)
        finish()
    }

    // Handle the result of the permission request
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Open Wi-Fi settings once permissions are granted
                openWifiSettings()
            } else {
                // Show a message or handle the case where permissions were not granted
                grantPermissionsButton.visibility = android.view.View.VISIBLE
            }
        }
    }
}
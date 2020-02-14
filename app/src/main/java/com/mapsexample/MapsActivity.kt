package com.mapsexample

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*

class MapsActivity : AppCompatActivity(), OnMapReadyCallback,GoogleMap.OnMarkerClickListener {

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var lastLocatioon: Location

    companion object{
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
    }

    private lateinit var map: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this@MapsActivity)

    }
    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap





        map.setOnMarkerClickListener(this)
        map.uiSettings.isZoomControlsEnabled = true

        setUpMap()

    }

    private fun placeMarker(location: LatLng,empresa: LatLng){

        val markerOption = MarkerOptions().position(location)
        val markerOption2 = MarkerOptions().position(empresa)
        markerOption.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET))
        map.addMarker(markerOption2.title("Giro"))
        map.addMarker(markerOption.title("Mi ubicación"))


    }

    private fun setUpMap(){

        if (ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_REQUEST_CODE)
            return

        }

        map.isMyLocationEnabled = true
        map.mapType = GoogleMap.MAP_TYPE_NORMAL

        fusedLocationClient.lastLocation.addOnSuccessListener(this) {location ->
            if (location != null){
                lastLocatioon = location
                val currentLatLong = LatLng(location.latitude,location.longitude)
                val empresa = LatLng(20.669731,-103.368986)
                val lat = location.latitude
                val lng = location.longitude
                val late = 20.670
                val lnge:Double = -103.369
                placeMarker(currentLatLong,empresa)
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(empresa,13f))

                map.addCircle(CircleOptions()
                    .center(empresa)
                    .radius(10.00)
                    .strokeWidth(1f)
                    .strokeColor(Color.BLUE)
                    .fillColor(Color.GRAY)
                )



                when(currentLatLong){
                    empresa->{

                    }

                    else->{
                        when(lat){

                            in lat..late->{
                               when(lng){

                                   in lnge..lng->{

                                       val intent = Intent(applicationContext, PruebaUbi::class.java)

                                       startActivity(intent)

                                   }
                               }
                            }

                        }

                    }
                }
            }

        }

    }

    override fun onMarkerClick(p0: Marker?) = false
}

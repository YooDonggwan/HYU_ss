package com.example.kakao


import android.content.pm.PackageManager

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.Polyline


class MainActivity : AppCompatActivity(),OnMapReadyCallback {

    private lateinit var mMap:GoogleMap
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       setContentView(R.layout.activity_main)
        if(!checkPermission()) {
            requestPermission()
        }
        val mapFragment=supportFragmentManager.findFragmentById(R.id.mapView) as SupportMapFragment
        mapFragment.getMapAsync(this)



//        var dir=Direction_Finder()
//       var url= dir.getDirectionUrl(LatLng(37.561005,127.038458),LatLng(37.547939,127.049843))
//        dir.GetDirection(url).execute()
    }
    private fun checkPermission():Boolean{
        val finePermission=
            ContextCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED
        val coarsePermission=
            ContextCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_COARSE_LOCATION)== PackageManager.PERMISSION_GRANTED
        return finePermission&&coarsePermission
    }
    private fun requestPermission(){
        if (ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            )
        )
            Toast.makeText(this, "앱을 실행시키려면 위치 권한이 필요합니다", Toast.LENGTH_LONG).show()
        ActivityCompat.requestPermissions(
            this, arrayOf(
                android.Manifest.permission.ACCESS_FINE_LOCATION,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ), 200
        )
    }
    private fun putMarker(latitude:Double,longitude:Double){
//        if(marker1==null) {
//            var marker = MapPOIItem()
//            marker.mapPoint = MapPoint.mapPointWithGeoCoord(latitude, longitude)
//            marker.selectedMarkerType = MapPOIItem.MarkerType.RedPin
//            marker.markerType = MapPOIItem.MarkerType.RedPin
//            marker.itemName = "Default Marker"
//            marker.tag = 0
//
//         //   map?.addPOIItem(marker)
//            marker1=marker
//        }
//        else if(marker2==null){
//            var marker = MapPOIItem()
//            marker.mapPoint = MapPoint.mapPointWithGeoCoord(latitude, longitude)
//            marker.selectedMarkerType = MapPOIItem.MarkerType.RedPin
//            marker.markerType = MapPOIItem.MarkerType.RedPin
//            marker.itemName = "Default Marker"
//            marker.tag = 0
//
//         //   map?.addPOIItem(marker)
//            marker2=marker
//        }
    }

//    override fun onMapViewDoubleTapped(p0: MapView?, p1: MapPoint?) {
//    }
//
//    override fun onMapViewInitialized(p0: MapView?) {
//    }
//
//    override fun onMapViewDragStarted(p0: MapView?, p1: MapPoint?) {
//    }
//
//    override fun onMapViewMoveFinished(p0: MapView?, p1: MapPoint?) {
//    }
//
//    override fun onMapViewCenterPointMoved(p0: MapView?, p1: MapPoint?) {
//    }
//
//    override fun onMapViewDragEnded(p0: MapView?, p1: MapPoint?) {
//    }
//
//    override fun onMapViewSingleTapped(p0: MapView?, p1: MapPoint?) {
//
//    }
//    override fun onMapViewZoomLevelChanged(p0: MapView?, p1: Int) {
//
//    }
//
//    override fun onMapViewLongPressed(p0: MapView?, p1: MapPoint?) {
//        var point=p1?.mapPointGeoCoord
//        var lat=point?.latitude
//        var lon=point?.longitude
//        putMarker(lat!!,lon!!)
//    }

    override fun onMapReady(p0: GoogleMap?) {
        mMap=p0!!
    }
}

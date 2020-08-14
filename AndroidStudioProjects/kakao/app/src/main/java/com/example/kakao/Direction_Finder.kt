package com.example.kakao

import android.graphics.Color
import android.os.AsyncTask
import android.widget.Toast
//import com.google.android.gms.maps.model.JointType
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Polyline
import com.google.android.gms.maps.model.PolylineOptions

import com.google.gson.Gson
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapPolyline
import okhttp3.OkHttpClient
import okhttp3.Request
import java.lang.Exception

class Direction_Finder {
    fun getDirectionUrl(origin: LatLng, dest: LatLng): String {
        return "https://maps.googleapis.com/maps/api/directions/json?origin=${origin.latitude},${origin.longitude}&destination=${dest.latitude},${dest.longitude}&mode=transit&departure_time=now&language=ko&key=AIzaSyD0982tla5btYfpYCWfH23NRXFU7pirdWc"
//        return "https://maps.googleapis.com/maps/api/directions/json?origin=${origin.latitude},${origin.longitude}&destination=${dest.latitude},${dest.longitude}&mode=walking&key=AIzaSyD0982tla5btYfpYCWfH23NRXFU7pirdWc"

    }
    //Poly line을 해독
    fun decodePolyline(encoded:String):List<LatLng>{
        val poly=ArrayList<LatLng>()
        var index=0
        var len=encoded.length
        var lat=0
        var lng=0
        while(index<len){
            var b:Int
            var shift=0
            var result=0
            do{
                b=encoded[index++].toInt() - 63
                result=result or (b and 0x1f shl shift)
                shift+=5
            }while(b>=0x20)
            val dlat=if(result and 1 !=0) (result shr 1).inv() else result shr 1
            lat+=dlat

            shift=0
            result=0
            do{
                b=encoded[index++].toInt() -63
                result=result or (b and 0x1f shl shift)
                shift+=5
            }while(b>=0x20)
            val dlng=if(result and 1 !=0) (result shr 1).inv() else result shr 1
            lng+=dlng
            val latLng=LatLng(lat.toDouble()/1E5,lng.toDouble()/1E5)
            poly.add(latLng)
        }
        return poly
    }

    inner class GetDirection(var url: String) : AsyncTask<Void, Void, List<List<LatLng>>>() {
        override fun doInBackground(vararg p0: Void?): List<List<LatLng>> {
            val client = OkHttpClient()
            val request = Request.Builder().url(url).build()
            val response = client.newCall(request).execute()
            val data = response.body?.string()
            val result = ArrayList<List<LatLng>>()
            try {

                val obj = Gson().fromJson(data,GoogleMapDTO::class.java)
                val path = ArrayList<LatLng>()
                var onlyFirst=0
                for (i in 0 until obj.routes[0].legs[0].steps.size) {
//                    val startlatLng = LatLng(
//                        obj.routes[0].legs[0].steps[i].start_location.lat.toDouble(),
//                        obj.routes[0].legs[0].steps[i].start_location.lng.toDouble()
//                    )
//                    path.add(startlatLng)
//                    val endlatLng = LatLng(
//                        obj.routes[0].legs[0].steps[i].end_location.lat.toDouble(),
//                        obj.routes[0].legs[0].steps[i].end_location.lng.toDouble()
//                    )
                    path.addAll(decodePolyline(obj.routes[0].legs[0].steps[i].polyline.points))
                }

                result.add(path)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return result
        }

        override fun onPostExecute(result: List<List<LatLng>>?) {

            if (result != null) {
                val lineoption=MapPolyline()
                for(i in result.indices){
                    var a=result[i]
                    for(j in a){
                        lineoption.addPoint(MapPoint.mapPointWithGeoCoord(j.latitude,j.longitude))

                    }
                }
                lineoption.lineColor=Color.argb(128,255,51,0)

             //   MainActivity.map?.addPolyline(lineoption)

            }
        }

    }
}
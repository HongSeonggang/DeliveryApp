package eu.tutorials.delapp

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.annotation.NonNull
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.*
import com.naver.maps.map.overlay.*
import com.naver.maps.map.util.FusedLocationSource
import com.naver.maps.map.util.MarkerIcons
import kotlinx.android.synthetic.main.delivery.*
import kotlinx.android.synthetic.main.order_packing.*
import kotlinx.android.synthetic.main.order_payment.*
import kotlin.math.*

class Delivery : AppCompatActivity(), OnMapReadyCallback{
    private lateinit var mapView: MapView
    private lateinit var naverMap: NaverMap

    private val LOCATION_PERMISSTION_REQUEST_CODE: Int = 1000
    private lateinit var locationSource: FusedLocationSource // 위치를 반환하는 구현체

    private val marker = Marker()
    private val marker1 = Marker()
    private val marker2 = Marker()
    private val marker3 = Marker()
    private val marker4 = Marker()
    private val marker5 = Marker()
    private val marker6 = Marker()
    private val marker7 = Marker()
    private val marker8 = Marker()
    private val marker9 = Marker()
    private val marker10 = Marker()
    private val marker_dest = Marker()
    private val marker_dest1 = Marker()


    //위도,경도 값으로 거리 구하는 함수
    object Distancemanager{
        private const val R = 6372.8 * 1000

        /**
         * 두 좌표의 거리를 계산한다.
         *
         * @param lat1 위도1
         * @param lon1 경도1
         * @param lat2 위도2
         * @param lon2 경도2
         * @return 두 좌표의 거리(m)
         */

        fun getDistance(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Int {
            val dLat = Math.toRadians(lat2 - lat1)
            val dLon = Math.toRadians(lon2 - lon1)
            val a = sin(dLat / 2).pow(2.0) + sin(dLon / 2).pow(2.0) * cos(Math.toRadians(lat1)) * cos(Math.toRadians(lat2))
            val c = 2 * asin(sqrt(a))
            return (R * c).toInt()
        }

    }

    //DB
    var fbAuth : FirebaseAuth? = null
    var fbFireStore : FirebaseFirestore? =null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.delivery)

        mapView = findViewById(R.id.delivery_view)
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)

        locationSource = FusedLocationSource(this, LOCATION_PERMISSTION_REQUEST_CODE)


        //DB
        fbAuth = FirebaseAuth.getInstance()
        fbFireStore = FirebaseFirestore.getInstance()

        delivery_btnmark1.setOnClickListener {

            Toast.makeText(this, " 배달 수행이 접수되었습니다", Toast.LENGTH_SHORT).show()

            val intent = Intent(this, MainScreen::class.java)
            startActivity(intent)
        }

    }

    override fun onMapReady(@NonNull naverMap: NaverMap) {
        this.naverMap = naverMap
        naverMap.locationSource = locationSource
        naverMap.locationTrackingMode = LocationTrackingMode.Follow

        //DB
        fbFireStore?.collection("distance")?.
        whereEqualTo("name","case1")?.
        get()?.
        addOnSuccessListener {result ->

            for (document in result) {  // 가져온 문서들은 result에 들어감
                // 위도,경도값 불러오기
                val latitude1= document["la1"] as Double//파리바게트(픽업)
                val longitude1 = document["lo1"] as Double

                val latitude2 = document["myla1"] as Double//내 목적지
                val longitude2 = document["mylo1"] as Double

                val latitude3 = document["ladst1"] as Double//파리바게트 배달목적지
                val longitude3 = document["lodst1"] as Double

                val latitude4 = document["lanow1"] as Double//현재위치
                val longitude4 = document["lonow1"] as Double

                val latitude5 = document["la8"] as Double//걸작떡볶이
                val longitude5 = document["lo8"] as Double

                val latitude7 = document["della2"] as Double//걸작떡볶이 배달목적지
                val longitude7 = document["dello2"] as Double

                val latitude6 = document["la6"] as Double//BHC치킨
                val longitude6 = document["lo6"] as Double

                val latitude8 = document["della1"] as Double//BHC치킨 배달목적지
                val longitude8 = document["dello2"] as Double


                // 현재 위치 마커
                marker.position = LatLng(latitude4, longitude4)
                marker.map = naverMap
                marker.icon = MarkerIcons.BLACK // 마커 검은색으로
                marker.iconTintColor = Color.BLUE // 현재위치 마커 파란색으로

                marker2.position = LatLng(latitude2, longitude2)
                marker2.map = naverMap
                marker2.captionText = "내 목적지"//목적지
                marker2.iconTintColor = Color.RED

                //파리바게트배달
                val newd1 = Distancemanager.getDistance(latitude4, longitude4, latitude1, longitude1)//내위치~픽업
                val newd2 = Distancemanager.getDistance(latitude2, longitude2, latitude1, longitude1)//픽업~내목적지
                val newd3 = Distancemanager.getDistance(latitude3, longitude3, latitude2, longitude2)//배달목적지~내목적지
                if(newd1<1000 && newd2 < 1000 && newd3<1000) {
                    // 장소 리스트 마커
                    marker1.position = LatLng(latitude1, longitude1)
                    marker1.map = naverMap
                    marker1.captionText = "파리 바게뜨"//픽업
                    marker1.iconTintColor = Color.BLACK

                    //정보창
                    val infoWindow1 = InfoWindow()
                    infoWindow1.adapter =
                        object : InfoWindow.DefaultTextAdapter(applicationContext) {
                            override fun getText(infoWindow: InfoWindow): CharSequence {
                                return "배달요청"
                            }
                        }
                    infoWindow1.open(marker1)//마커 위에 정보

                    val fix1 = Distancemanager.getDistance(
                        latitude1,
                        longitude1,
                        latitude3,
                        longitude3
                    )//픽업~배달
                    val distance1 = Distancemanager.getDistance(
                        latitude4,
                        longitude4,
                        latitude1,
                        longitude1
                    )//내위치~픽업
                    val distance2 = Distancemanager.getDistance(
                        latitude3,
                        longitude3,
                        latitude2,
                        longitude2
                    )//배달~내포장

                    val distance3 = Distancemanager.getDistance(
                        latitude4,
                        longitude4,
                        latitude2,
                        longitude2
                    )//내위치~내포장
                    val distance4 = Distancemanager.getDistance(
                        latitude2,
                        longitude2,
                        latitude1,
                        longitude1
                    )//내포장~픽업

                    val route1 = fix1 + distance1 + distance2
                    val route2 = fix1 + distance3 + distance4

                    val x1 = arrayOf(
                        latitude4,
                        longitude4,
                        latitude1,
                        longitude1,
                        latitude3,
                        longitude3,
                        latitude2,
                        longitude2
                    )//route1경로선
                    val x2 = arrayOf(
                        latitude4,
                        longitude4,
                        latitude2,
                        longitude2,
                        latitude1,
                        longitude1,
                        latitude3,
                        longitude3
                    )//route2경로선

                    infoWindow1.setOnClickListener {
                        //배달목적지마커
                        marker3.position = LatLng(latitude3, longitude3)
                        marker3.map = naverMap
                        marker3.captionText = "배달목적지"


                        if (route1 < route2) {
                            val arrowheadPath1 = ArrowheadPathOverlay()
                            arrowheadPath1.coords = listOf(
                                LatLng(x1[0], x1[1]),//현재위치
                                LatLng(x1[2], x1[3]),//픽업
                                LatLng(x1[4], x1[5]),//배달목적지
                                LatLng(x1[6], x1[7]),//교촌치킨
                            )
                            arrowheadPath1.color = Color.YELLOW//색상지
                            arrowheadPath1.map = naverMap
                        }

                        if (route2 < route1) {
                            val arrowheadPath1 = ArrowheadPathOverlay()
                            arrowheadPath1.coords = listOf(
                                LatLng(x2[0], x2[1]),//현재위치
                                LatLng(x2[2], x2[3]),//내포장
                                LatLng(x2[4], x2[5]),//픽업
                                LatLng(x2[6], x2[7]),//배달목적지
                            )
                            arrowheadPath1.color = Color.YELLOW//색상지
                            arrowheadPath1.map = naverMap
                        }


                        val route3 = min(route1, route2)

                        if (route3 < 1500) {//1500미터 안이면 결과값 출력
                            delivery_txt_distance1.text = ("총 배달 거리 : " + route3.toString() + "m")
                        }
                        true
                    }
                }

                    //걸작떡볶이 배달관련
                    val newd4 = Distancemanager.getDistance(latitude4, longitude4, latitude5, longitude5)//내위치~걸작
                    val newd5 = Distancemanager.getDistance(latitude2, longitude2, latitude5, longitude5)//걸작~내목적지
                    val newd6 = Distancemanager.getDistance(latitude7, longitude7, latitude2, longitude2)//배달목적지~내목적지
                    if(newd4<1000 && newd5 < 1000 && newd6<1000){
                        // 장소 리스트 마커
                        marker5.position = LatLng(latitude5, longitude5)
                        marker5.map = naverMap
                        marker5.captionText = "걸작떡볶이"//픽업
                        marker5.iconTintColor = Color.BLACK

                        //정보창
                        val infoWindow11 = InfoWindow()
                        infoWindow11.adapter = object : InfoWindow.DefaultTextAdapter(applicationContext) {
                            override fun getText(infoWindow: InfoWindow): CharSequence {
                                return "배달요청"
                            }
                        }
                        infoWindow11.open(marker5)//마커 위에 정보

                        val fix11 = Distancemanager.getDistance(latitude5, longitude5, latitude7, longitude7)//픽업~배달
                        val distance11 = Distancemanager.getDistance(latitude4, longitude4, latitude5, longitude5)//내위치~픽업
                        val distance22 = Distancemanager.getDistance(latitude7, longitude7, latitude2, longitude2)//배달~내포장

                        val distance33 = Distancemanager.getDistance(latitude4, longitude4, latitude2, longitude2)//내위치~내포장
                        val distance44 = Distancemanager.getDistance(latitude2, longitude2, latitude5, longitude5)//내포장~픽업

                        val route11 = fix11 + distance11 + distance22
                        val route22 = fix11 + distance33 + distance44

                        val x11 = arrayOf(latitude4,longitude4, latitude5, longitude5,latitude7,longitude7,latitude2,longitude2)//route1경로선
                        val x22 = arrayOf(latitude4,longitude4,latitude2,longitude2,latitude5, longitude5,latitude7,longitude7)//route2경로선

                        infoWindow11.setOnClickListener {
                            //배달목적지마커
                            marker7.position = LatLng(latitude7, longitude7)
                            marker7.map = naverMap
                            marker7.captionText = "배달목적지"
                            marker7.iconTintColor = Color.BLUE


                            if(route11< route22){
                                val arrowheadPath11 = ArrowheadPathOverlay()
                                arrowheadPath11.coords = listOf(
                                    LatLng(x11[0], x11[1]),//현재위치
                                    LatLng(x11[2], x11[3]),//픽업
                                    LatLng(x11[4], x11[5]),//배달목적지
                                    LatLng(x11[6], x11[7]),//교촌치킨
                                )
                                arrowheadPath11.color = Color.YELLOW//색상지
                                arrowheadPath11.map = naverMap
                            }

                            if(route22< route11){
                                val arrowheadPath11 = ArrowheadPathOverlay()
                                arrowheadPath11.coords = listOf(
                                    LatLng(x22[0], x22[1]),//현재위치
                                    LatLng(x22[2], x22[3]),//내포장
                                    LatLng(x22[4], x22[5]),//픽업
                                    LatLng(x22[6], x22[7]),//배달목적지
                                )
                                arrowheadPath11.color = Color.YELLOW//색상지
                                arrowheadPath11.map = naverMap
                            }


                            val route33 = min(route11, route22)

                            if(route33 < 1500) {//1500미터 안이면 결과값 출력
                                delivery_txt_distance1.text = ("총 배달 거리 : " + route33.toString() + "m")
                            }
                            true
                        }

                }

                //BHC 배달관련
                val newd7 = Distancemanager.getDistance(latitude4, longitude4, latitude6, longitude6)//내위치~BHC
                val newd8 = Distancemanager.getDistance(latitude2, longitude2, latitude6, longitude6)//BHC~내목적지
                val newd9 = Distancemanager.getDistance(latitude8, longitude8, latitude2, longitude2)//배달목적지~내목적지
                if(newd7<1000 && newd8 < 1000 && newd9<1000){
                    // 장소 리스트 마커
                    marker6.position = LatLng(latitude6, longitude6)
                    marker6.map = naverMap
                    marker6.captionText = "BHC치킨"//픽업
                    marker6.iconTintColor = Color.BLACK

                    //정보창
                    val infoWindow111 = InfoWindow()
                    infoWindow111.adapter = object : InfoWindow.DefaultTextAdapter(applicationContext) {
                        override fun getText(infoWindow: InfoWindow): CharSequence {
                            return "배달요청"
                        }
                    }
                    infoWindow111.open(marker6)//마커 위에 정보

                    val fix111 = Distancemanager.getDistance(latitude6, longitude6, latitude8, longitude8)//픽업~배달
                    val distance111 = Distancemanager.getDistance(latitude4, longitude4, latitude6, longitude6)//내위치~픽업
                    val distance222 = Distancemanager.getDistance(latitude8, longitude8, latitude2, longitude2)//배달~내포장

                    val distance333 = Distancemanager.getDistance(latitude4, longitude4, latitude2, longitude2)//내위치~내포장
                    val distance444 = Distancemanager.getDistance(latitude2, longitude2, latitude6, longitude6)//내포장~픽업

                    val route111 = fix111 + distance111 + distance222
                    val route222 = fix111 + distance333 + distance444

                    val x111 = arrayOf(latitude4,longitude4, latitude6, longitude6,latitude8,longitude8,latitude2,longitude2)//route1경로선
                    val x222 = arrayOf(latitude4,longitude4,latitude2,longitude2,latitude6, longitude6,latitude8,longitude8)//route2경로선

                    infoWindow111.setOnClickListener {
                        //배달목적지마커
                        marker8.position = LatLng(latitude8, longitude8)
                        marker8.map = naverMap
                        marker8.captionText = "배달목적지"
                        marker8.iconTintColor = Color.BLUE



                        if(route111< route222){
                            val arrowheadPath111 = ArrowheadPathOverlay()
                            arrowheadPath111.coords = listOf(
                                LatLng(x111[0], x111[1]),//현재위치
                                LatLng(x111[2], x111[3]),//픽업
                                LatLng(x111[4], x111[5]),//배달목적지
                                LatLng(x111[6], x111[7]),//교촌치킨
                            )
                            arrowheadPath111.color = Color.RED//색상지
                            arrowheadPath111.map = naverMap
                        }

                        if(route222< route111){
                            val arrowheadPath111 = ArrowheadPathOverlay()
                            arrowheadPath111.coords = listOf(
                                LatLng(x222[0], x222[1]),//현재위치
                                LatLng(x222[2], x222[3]),//내포장
                                LatLng(x222[4], x222[5]),//픽업
                                LatLng(x222[6], x222[7]),//배달목적지
                            )
                            arrowheadPath111.color = Color.RED//색상지
                            arrowheadPath111.map = naverMap
                        }


                        val route333 = min(route111, route222)

                        if(route333 < 1500) {//1500미터 안이면 결과값 출력
                            delivery_txt_distance1.text = ("총 배달 거리 : " + route333.toString() + "m")
                        }
                        true
                    }

                }







            }
        }




        val cameraPosition = CameraPosition( // 카메라 위치 변경
            LatLng(35.1758, 126.9085),  // 위치 지정
            13.3 // 줌 레벨
        )
        naverMap.cameraPosition = cameraPosition // 변경된 위치 반영

        // 마커2 버튼 클
        //marker2.setOnClickListener{
        //    val intent = Intent(this, Payment::class.java)
        //    startActivity(intent)
        //    true }
    }


    override fun onStart() {
        super.onStart()
        mapView.onStart()
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView.onSaveInstanceState(outState)
    }

    override fun onStop() {
        super.onStop()
        mapView.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }
}
package eu.tutorials.delapp

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.NonNull
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.*
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.Overlay
import com.naver.maps.map.util.FusedLocationSource
import com.naver.maps.map.util.MarkerIcons

class Order : AppCompatActivity(), OnMapReadyCallback{
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

    var fbAuth : FirebaseAuth? = null
    var fbFireStore : FirebaseFirestore? =null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.order_main)

        mapView = findViewById(R.id.map_view)
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)

        locationSource = FusedLocationSource(this, LOCATION_PERMISSTION_REQUEST_CODE)

        //DB
        fbAuth = FirebaseAuth.getInstance()
        fbFireStore = FirebaseFirestore.getInstance()
    }


    override fun onMapReady(@NonNull naverMap: NaverMap) {
        this.naverMap = naverMap
        naverMap.locationSource = locationSource
        naverMap.locationTrackingMode = LocationTrackingMode.Follow

        //DB
        fbFireStore?.collection("distance")?.whereEqualTo("name", "case1")?.get()
            ?.addOnSuccessListener { result ->

                for (document in result) {  // 가져온 문서들은 result에 들어감
                    // 위도,경도값 불러오기

                            val latitude0 = document["lanow1"] as Double//현재위치
                            val longitude0 = document["lonow1"] as Double

                            val latitude1 = document["la1"] as Double//파리바게트
                            val longitude1 = document["lo1"] as Double

                            val latitude2 = document["la2"] as Double//교촌
                            val longitude2 = document["lo2"] as Double

                            val latitude4 = document["la4"] as Double//청년피자
                            val longitude4 = document["lo4"] as Double

                            val latitude6 = document["la6"] as Double//BHC
                            val longitude6 = document["lo6"] as Double

                            val latitude7 = document["la7"] as Double//푸라닭
                            val longitude7 = document["lo7"] as Double

                            val latitude8 = document["la8"] as Double//걸작
                            val longitude8 = document["lo8"] as Double

                            val latitude9 = document["la9"] as Double//BBQ
                            val longitude9 = document["lo9"] as Double

                            val latitude10 = document["la10"] as Double//버거킹
                            val longitude10 = document["lo10"] as Double

                            marker.position = LatLng(latitude0, longitude0)//현재위치
                            marker.map = naverMap
                            marker.icon = MarkerIcons.BLACK // 마커 검은색으로
                            marker.iconTintColor = Color.BLUE // 현재위치 마커 파색으로

                            // 장소 리스트 마커
                            marker1.position = LatLng(latitude1, longitude1)
                            marker1.map = naverMap
                            marker1.captionText = "파리 바게뜨"

                            marker2.position = LatLng(latitude2, longitude2)
                            marker2.map = naverMap
                            marker2.captionText = "교촌 치킨"

                            marker4.position = LatLng(latitude4, longitude4)
                            marker4.map = naverMap
                            marker4.captionText = "청년피자"

                            marker6.position = LatLng(latitude6, longitude6)
                            marker6.map = naverMap
                            marker6.captionText = "BHC치킨"

                            marker7.position = LatLng(latitude7, longitude7)
                            marker7.map = naverMap
                            marker7.captionText = "푸라닭치킨"

                            marker8.position = LatLng(latitude8, longitude8)
                            marker8.map = naverMap
                            marker8.captionText = "걸작떡볶이"

                            marker9.position = LatLng(latitude9, longitude9)
                            marker9.map = naverMap
                            marker9.captionText = "BBQ치킨"

                            marker10.position = LatLng(latitude10, longitude10)
                            marker10.map = naverMap
                            marker10.captionText = "버거킹"
                        }
                }


                val cameraPosition = CameraPosition( // 카메라 위치 변경
                    LatLng(35.1758, 126.9085),  // 위치 지정
                    13.3 // 줌 레벨
                )
                naverMap.cameraPosition = cameraPosition // 변경된 위치 반영

                // 마커2 버튼 클
                marker2.setOnClickListener {
                    val intent = Intent(this, Menu::class.java)
                    startActivity(intent)
                    true
                }

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
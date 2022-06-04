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
import kotlinx.android.synthetic.main.order_packing.*
import kotlinx.android.synthetic.main.order_payment.*
import kotlin.math.*

class Packing : AppCompatActivity(), OnMapReadyCallback{
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
        setContentView(R.layout.order_packing)

        mapView = findViewById(R.id.packing_view)
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)

        locationSource = FusedLocationSource(this, LOCATION_PERMISSTION_REQUEST_CODE)

        //위,경도로 거리 구하기(현재위치에서 교촌치킨)
        //txt_distance.text = Distancemanager.getDistance(latitude1, 126.9085, 35.1761303, 126.91467979392138).toString()

        //DB
        fbAuth = FirebaseAuth.getInstance()
        fbFireStore = FirebaseFirestore.getInstance()

        btnmark1.setOnClickListener {

            Toast.makeText(this, " 배달 수행이 접수되었습니다", Toast.LENGTH_SHORT).show()

            val intent = Intent(this, MainScreen::class.java)
            startActivity(intent)
        }

    }

    override fun onMapReady(@NonNull naverMap: NaverMap) {
        this.naverMap = naverMap
        naverMap.locationSource = locationSource
        naverMap.locationTrackingMode = LocationTrackingMode.Follow

        // 현재 위치 마커
        marker.position = LatLng(35.1758, 126.9085)
        marker.map = naverMap
        marker.icon = MarkerIcons.BLACK // 마커 검은색으로
        marker.iconTintColor = Color.BLUE // 현재위치 마커 파란색으로


        // 장소 리스트 마커
        marker1.position = LatLng(35.176475, 126.912577)
        marker1.map = naverMap
        marker1.captionText = "파리 바게뜨"
        //marker1.iconTintColor = Color.RED


        marker2.position = LatLng(35.1761303, 126.91467979392138)
        marker2.map = naverMap
        marker2.captionText = "교촌 치킨"//목적지

        //marker3.position = LatLng(35.174857953111, 126.91581457308762)
        //marker3.map = naverMap
        //marker3.captionText = "깻잎두마리치킨"

        marker4.position = LatLng(35.18115896176068, 126.9043793864954)
        marker4.map = naverMap
        marker4.captionText = "청년피자"

        marker5.position = LatLng(35.17786292044936, 126.90239346132502)
        marker5.map = naverMap
        marker5.captionText = "만계치킨"

        marker6.position = LatLng(35.17722777729083, 126.90001473054771)
        marker6.map = naverMap
        marker6.captionText = "BHC치킨"

        marker7.position = LatLng(35.17135278936166, 126.90229421383835)
        marker7.map = naverMap
        marker7.captionText = "푸라닭치킨"

        marker8.position = LatLng(35.17773289770602, 126.90323344195374)
        marker8.map = naverMap
        marker8.captionText = "걸작떡볶이"

        marker9.position = LatLng(35.17179478220647, 126.90554845724402)
        marker9.map = naverMap
        marker9.captionText = "BBQ치킨"

        marker10.position = LatLng(35.17409200470322, 126.91291727125763)
        marker10.map = naverMap
        marker10.captionText = "버거킹"

        //파리바게트 정보 창
        val infoWindow1 = InfoWindow()
        infoWindow1.adapter = object : InfoWindow.DefaultTextAdapter(applicationContext) {
            override fun getText(infoWindow: InfoWindow): CharSequence {
                return "배달요청"
            }
        }
        infoWindow1.open(marker1)//마커 위에 정보

        //BBQ치킨 정보 창
        val infoWindow2 = InfoWindow()
        infoWindow2.adapter = object : InfoWindow.DefaultTextAdapter(applicationContext) {
            override fun getText(infoWindow: InfoWindow): CharSequence {
                return "배달요청"
            }
        }
        infoWindow2.open(marker9)//마커 위에 정보





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

        // 파리바게트 버튼 클릭
        infoWindow1.setOnClickListener {

            marker_dest.position = LatLng(35.1763855, 126.917682)//배달목적지
            marker_dest.map = naverMap
            //marker_dest.captionText = "배달목적지"
            marker_dest.iconTintColor = Color.RED

            val infoWindow3 = InfoWindow()
            infoWindow3.adapter = object : InfoWindow.DefaultTextAdapter(applicationContext) {
                override fun getText(infoWindow: InfoWindow): CharSequence {
                    return "배달지점"
                }
            }
            infoWindow3.open(marker_dest)//마커 위에 정보

            /**
            //경로선
            val arrowheadPath1 = ArrowheadPathOverlay()
            arrowheadPath1.coords = listOf(
                LatLng(35.1758, 126.9085),//현재위치
                LatLng(35.176475, 126.912577),//파리바게트
                LatLng(35.1763855, 126.917682),//배달목적지
                LatLng(35.1761303, 126.91467979392138),//교촌치킨
            )
            arrowheadPath1.color = Color.YELLOW//색상지
            arrowheadPath1.map = naverMap
            */


            //DB
            fbFireStore?.collection("distance")?.
            whereEqualTo("name","case1")?.
            get()?.
            addOnSuccessListener {result ->

                for (document in result) {  // 가져온 문서들은 result에 들어감
                    // 위도값 불러오기

                    val latitude1= document["la1"] as Double//파리바게트(픽업)
                    val longitude1 = document["lo1"] as Double

                    val latitude2 = document["la2"] as Double//교촌(내포장)
                    val longitude2 = document["lo2"] as Double

                    val latitude3 = document["ladst1"] as Double//배달목적지
                    val longitude3 = document["lodst1"] as Double

                    val latitude4 = document["lanow1"] as Double//현재위치
                    val longitude4 = document["lonow1"] as Double

                    val fix1 = Distancemanager.getDistance(latitude1, longitude1, latitude3, longitude3)//픽업~배달
                    val distance1 = Distancemanager.getDistance(latitude4, longitude4, latitude1, longitude1)//내위치~픽업
                    val distance2 = Distancemanager.getDistance(latitude3, longitude3, latitude2, longitude2)//배달~내포장


                    val distance3 = Distancemanager.getDistance(latitude4, longitude4, latitude2, longitude2)//내위치~내포장
                    val distance4 = Distancemanager.getDistance(latitude2, longitude2, latitude1, longitude1)//내포장~픽업

                    val route1 = fix1 + distance1 + distance2
                    val route2 = fix1 + distance3 + distance4

                    val x1 = arrayOf(latitude4,longitude4, latitude1, longitude1,latitude3,longitude3,latitude2,longitude2)//route1경로선
                    val x2 = arrayOf(latitude4,longitude4,latitude2,longitude2,latitude1, longitude1,latitude3,longitude3)//route2경로선

                    if(route1< route2){
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

                    if(route2< route1){
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

                    if(route3 < 1500) {//1500미터 안이면 결과값 출력
                        txt_distance.text = ("총 배달 거리 : " + route3.toString() + "m")
                    }
                }
            }
         true

        }

        // BBQ 정보창 버튼 클릭
        infoWindow2.setOnClickListener {

            marker_dest1.position = LatLng(35.16957997544401, 126.90590777131943)//배달목적지
            marker_dest1.map = naverMap
            //marker_dest.captionText = "배달목적지"
            marker_dest1.iconTintColor = Color.RED

            val infoWindow4 = InfoWindow()
            infoWindow4.adapter = object : InfoWindow.DefaultTextAdapter(applicationContext) {
                override fun getText(infoWindow: InfoWindow): CharSequence {
                    return "배달지점"
                }
            }
            infoWindow4.open(marker_dest1)//마커 위에 정보

            /**
            //경로선
            val arrowheadPath2 = ArrowheadPathOverlay()
            arrowheadPath2.coords = listOf(
                LatLng(35.1758, 126.9085),//현재위치
                LatLng(35.17179478220647, 126.90554845724402),//BBQ
                LatLng(35.16957997544401, 126.90590777131943),//배달목적지
                LatLng(35.1761303, 126.91467979392138),//교촌치킨
            )
            arrowheadPath2.color = Color.RED//색상지
            arrowheadPath2.map = naverMap
            */


            //DB
            fbFireStore?.collection("distance")?.
            whereEqualTo("name","case1")?.
            get()?.
            addOnSuccessListener {result ->

                for (document in result) {  // 가져온 문서들은 result에 들어감
                    // 위도값 불러오기

                    val latitude1= document["la9"] as Double//파리바게트(픽업)
                    val longitude1 = document["lo9"] as Double

                    val latitude2 = document["la2"] as Double//교촌(내포장)
                    val longitude2 = document["lo2"] as Double

                    val latitude3 = document["ladst2"] as Double//배달목적지
                    val longitude3 = document["lodst2"] as Double

                    val latitude4 = document["lanow1"] as Double//현재위치
                    val longitude4 = document["lonow1"] as Double

                    val fix1 = Distancemanager.getDistance(latitude1, longitude1, latitude3, longitude3)//픽업~배달
                    val distance1 = Distancemanager.getDistance(latitude4, longitude4, latitude1, longitude1)//내위치~픽업
                    val distance2 = Distancemanager.getDistance(latitude3, longitude3, latitude2, longitude2)//배달~내포장

                    val distance3 = Distancemanager.getDistance(latitude4, longitude4, latitude2, longitude2)//내위치~내포장
                    val distance4 = Distancemanager.getDistance(latitude2, longitude2, latitude1, longitude1)//내포장~픽업

                    val route1 = fix1 + distance1 + distance2
                    val route2 = fix1 + distance3 + distance4

                    val x1 = arrayOf(latitude4,longitude4, latitude1, longitude1,latitude3,longitude3,latitude2,longitude2)//route1경로선
                    val x2 = arrayOf(latitude4,longitude4,latitude2,longitude2,latitude1, longitude1,latitude3,longitude3)//route2경로선

                    if(route1< route2){
                        val arrowheadPath2 = ArrowheadPathOverlay()
                        arrowheadPath2.coords = listOf(
                            LatLng(x1[0], x1[1]),//현재위치
                            LatLng(x1[2], x1[3]),//픽업
                            LatLng(x1[4], x1[5]),//배달목적지
                            LatLng(x1[6], x1[7]),//내포장
                        )
                        arrowheadPath2.color = Color.RED//색상지
                        arrowheadPath2.map = naverMap
                    }

                    if(route2< route1){
                        val arrowheadPath2 = ArrowheadPathOverlay()
                        arrowheadPath2.coords = listOf(
                            LatLng(x2[0], x2[1]),//현재위치
                            LatLng(x2[2], x2[3]),//내포장
                            LatLng(x2[4], x2[5]),//픽업
                            LatLng(x2[6], x2[7]),//배달목적지
                        )
                        arrowheadPath2.color = Color.RED//색상지
                        arrowheadPath2.map = naverMap
                    }

                    val route3 = min(route1, route2)

                        txt_distance.text = (" 배달 거리 : " + route3.toString() + "m")

                    //else
                        //txt_distance.text=("거리가 너무 멉니다")
                }
            }
            true
        }




        /**
        val arrowheadPath2 = ArrowheadPathOverlay()
        arrowheadPath2.coords = listOf(
            LatLng(35.1758, 126.9085),//현재위치
            LatLng(35.1777306, 126.9032444),//걸작떡볶이
            LatLng(35.17723855, 126.905182),//배달목적지
            LatLng(35.1761303, 126.91467979392138)//교촌치킨
        )
        arrowheadPath2.color = Color.LTGRAY//색상지
        arrowheadPath2.map = naverMap
        */
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
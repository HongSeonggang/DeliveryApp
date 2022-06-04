package eu.tutorials.delapp

import android.content.ContentValues.TAG
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.*
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.util.FusedLocationSource
import com.naver.maps.map.util.MarkerIcons
import eu.tutorials.delapp.databinding.MainScreenBinding
import eu.tutorials.delapp.model.ListAdapter
import eu.tutorials.delapp.model.ListLayout
import eu.tutorials.delapp.model.ShopList
import eu.tutorials.delapp.model.User
import kotlinx.android.synthetic.main.main_screen.*

class MainScreen : AppCompatActivity(),OnMapReadyCallback {


    val db = FirebaseFirestore.getInstance()    // Firestore 인스턴스 선언
    val itemList = arrayListOf<ListLayout>()    // 리스트 아이템 배열
    val adapter = ListAdapter(itemList)         // 리사이클러 뷰 어댑터

    var fbAuth : FirebaseAuth? = null
    var fbFireStore : FirebaseFirestore? =null

    val binding by lazy { MainScreenBinding.inflate(layoutInflater) }

    //지도 관련
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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        //지도
        mapView = findViewById(R.id.map_view2)
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)
        locationSource = FusedLocationSource(this, LOCATION_PERMISSTION_REQUEST_CODE)


        // 정보창 버튼
        btn_status.setOnClickListener {
            val intent = Intent(this, Status::class.java)
            startActivity(intent)
        }
        // 게임하기 버튼
        btn_shoplist.setOnClickListener {
            val intent = Intent(this, Shoplist::class.java)
            startActivity(intent)
        }

        // 주문하기 버튼
        btn_order.setOnClickListener {
            val intent = Intent(this, Order::class.java)
            startActivity(intent)
        }

        // 배달하기 버튼
        btn_delivery.setOnClickListener {
            val intent = Intent(this, Delivery::class.java)
            startActivity(intent)
        }


        // 파이어 베이스로부터 정보를 받아온 변수
        fbAuth = FirebaseAuth.getInstance()
        fbFireStore = FirebaseFirestore.getInstance()


        // 유저 모델로부터 정보를 받아오는 변수
        var userInfo = User()
        var shopInfo = ShopList()

        // 파이어 스토어에서 정보를 받아온 변수
//        fbFireStore?.collection("users")?.whereEqualTo("uid", "${fbAuth?.uid}")?.get()
//            ?.addOnCompleteListener {
//
//                if(it.isSuccessful) {
//                    Log.d(TAG, "2222222${it.result.toString()}222222222222 ")
//
//                }else{
//                    userInfo.uid = fbAuth?.uid
//                    userInfo.id = fbAuth?.currentUser?.email
//                    userInfo.cash = "100000"
//
//                    fbFireStore?.collection("users")?.document(fbAuth?.uid.toString())
//                        ?.set(userInfo)
//                }
//
//            }


        // 아이디 값이 값이 존재하지 않는 유저에 대한 데이터 추가 및 갱신
        fbFireStore?.collection("users")?.whereEqualTo("id","${fbAuth?.currentUser?.email}")?.get()
            ?.addOnSuccessListener { result ->

                val result = result ?: return@addOnSuccessListener

                if (result.documents.isEmpty()) {
                    userInfo.uid = fbAuth?.uid
                    userInfo.id = fbAuth?.currentUser?.email
                    userInfo.cash = "100000"
                    userInfo.delopt = false

                    fbFireStore?.collection("users")?.document(fbAuth?.uid.toString())
                        ?.set(userInfo)

                    // 존재하지 않음
                } else {
                    // 존재함
                    for(dc in result.documents){
                        if(dc.id == fbAuth?.uid) {
                            Log.d(TAG, "이미 존재합니다 ")
                        }
                    }

                }

            }



                // 적립금 읽어오기



        binding.btnCashRead.setOnClickListener {
            fbFireStore?.collection("users")?.whereEqualTo("uid", "${fbAuth?.uid}")?.get()
                ?.addOnSuccessListener { result ->
                    for (document in result) {
                        cashAmount.text = document["cash"] as String

                    }
                    // 성공할 경우
                }
        }

        // 장바구니 생성
        // DB안에 아이디 값이 값이 존재하지 않는 유저에 대한 장바구니 생성
        fbFireStore?.collection("shoplist")?.whereEqualTo("uid","${fbAuth?.uid}")?.get()
            ?.addOnSuccessListener { result ->

                val result = result ?: return@addOnSuccessListener

                if (result.documents.isEmpty()) {
                    shopInfo.uid = fbAuth?.uid
                    shopInfo.listid = " "
                    shopInfo.pricesum = "0"
                    shopInfo.shoplist = " "

                    fbFireStore?.collection("shoplist")?.document(fbAuth?.uid.toString())
                        ?.set(shopInfo)

                    // 존재하지 않음
                } else {
                    // 존재함
                    for(dc in result.documents){
                        if(dc.id == fbAuth?.uid) {
                            Log.d(TAG, "이미 존재합니다 ")
                        }
                    }

                }

            }


            }
    //지도관련
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
            14.3 // 줌 레벨
        )
        naverMap.cameraPosition = cameraPosition // 변경된 위치 반영

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

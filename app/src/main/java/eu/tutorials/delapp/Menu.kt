package eu.tutorials.delapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import eu.tutorials.delapp.databinding.MainScreenBinding
import eu.tutorials.delapp.databinding.OrderMenuBinding
import eu.tutorials.delapp.model.ListLayout
import kotlinx.android.synthetic.main.order_menu.*

class Menu : AppCompatActivity() {

    var fbAuth : FirebaseAuth? = null
    var fbFireStore : FirebaseFirestore? =null

    val binding by lazy { OrderMenuBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        fbAuth = FirebaseAuth.getInstance()
        fbFireStore = FirebaseFirestore.getInstance()


        showMenu ()


        with(binding) {

            //결제하기
            binding. btnOrderGoPayment.setOnClickListener {goPayment() }

            //장바구니에 담기
            binding.btnGet1.setOnClickListener {getMenu1() }
            binding.btnGet2.setOnClickListener {getMenu2() }

        }

    }

    fun goPayment(){
        fbFireStore!!.collection("shoplist").whereEqualTo("uid","${fbAuth?.uid}")?.get().addOnSuccessListener {
                result ->
            for(document in result) {
                var listStatus = document["listid"] as String
                //주문하지 않은 경우에 대한 처리
                if(listStatus == " "){
                    Toast.makeText(this, " 메뉴를 골라주세요", Toast.LENGTH_SHORT).show()
                }else{
                    //비지 않았다면 다음화면으로
                    val intent = Intent(this, Payment::class.java)
                    startActivity(intent)
                }

            }
        }
    }

    //메뉴 보여주기
    fun showMenu (){

            fbFireStore!!.collection("menu").get().addOnSuccessListener { result ->
                // 성공할 경우
                for (document in result) {  // 가져온 문서들은 result에 들어감

                // 선택된 가게의 메뉴와 이름 보여주기
                    order_restaurant_info.text = document["shopId"] as String
                    menu1.text ="${document["name1"] } \n ${document["price1"] } 원"
                    menu2.text ="${document["name2"] } \n ${document["price2"] } 원"


                }

                }
        }

    // 장바구니에 메뉴 넣기 (후라이드 치킨)
    fun getMenu1 (){

        var addprice = 0
        var sumprice = 0
        var addlist = " "
        var sumlist = " "
        fbFireStore!!.collection("menu").get().addOnSuccessListener { result ->
            // 성공할 경우
            for (document in result) {


                // 선택된 메뉴 값



                // int로 캐스팅
                addprice = Integer.parseInt(document["price1"] as String)
                // 추가될 메뉴 이름
                addlist =  document["name1"] as String

                }

                fbFireStore!!.collection("shoplist").whereEqualTo("uid","${fbAuth?.uid}")?.get().addOnSuccessListener {
                        result2 ->
                    // 성공할 경우
                    for (i in result2) {

                        Toast.makeText(this, " 메뉴가 추가되었습니다", Toast.LENGTH_SHORT).show()
                        //담기1 버튼을 눌렀을때



                        // 기존 메뉴 값 (디폴트 "0" )
                        sumprice = Integer.parseInt(i["pricesum"] as String)

                        // 의 합을 나타내는 변수 sum
                        sumprice += addprice

                        // 기존 메뉴 이름 모음 (디폴트 " ")
                        sumlist = i["shoplist"] as String

                        // 메뉴 이름이 기존 메뉴 모음에 추가됩니다
                        sumlist += addlist

                        val outcome = sumprice.toString()

                        //결과값을 shoplist db에 업데이트
                        fbFireStore?.collection("shoplist")?.document( "${fbAuth?.uid}")?.update("pricesum",outcome)
                        fbFireStore?.collection("shoplist")?.document( "${fbAuth?.uid}")?.update("listid","교촌치킨")
                        fbFireStore?.collection("shoplist")?.document( "${fbAuth?.uid}")?.update("shoplist","${sumlist} 1 " )




                    }
                }


            }


    }

    // 장바구니에 메뉴 넣기 (양념 치킨)
    fun getMenu2 (){

        var addprice = 0
        var sumprice = 0
        var addlist = " "
        var sumlist = " "
        fbFireStore!!.collection("menu").get().addOnSuccessListener { result ->
            // 성공할 경우
            for (document in result) {


                // 선택된 메뉴 값



                // int로 캐스팅
                addprice = Integer.parseInt(document["price2"] as String)
                // 추가될 메뉴 이름
                addlist =  document["name2"] as String

            }

            fbFireStore!!.collection("shoplist").whereEqualTo("uid","${fbAuth?.uid}")?.get().addOnSuccessListener {
                    result2 ->
                // 성공할 경우
                for (i in result2) {

                    Toast.makeText(this, " 메뉴가 추가되었습니다", Toast.LENGTH_SHORT).show()
                    //담기1 버튼을 눌렀을때



                    // 기존 메뉴 값 (디폴트 "0" )
                    sumprice = Integer.parseInt(i["pricesum"] as String)

                    // 의 합을 나타내는 변수 sum
                    sumprice += addprice

                    // 기존 메뉴 이름 모음 (디폴트 " ")
                    sumlist = i["shoplist"] as String

                    // 메뉴 이름이 기존 메뉴 모음에 추가됩니다
                    sumlist += addlist

                    val outcome = sumprice.toString()

                    //결과값을 shoplist db에 업데이트
                    fbFireStore?.collection("shoplist")?.document( "${fbAuth?.uid}")?.update("pricesum",outcome)
                    fbFireStore?.collection("shoplist")?.document( "${fbAuth?.uid}")?.update("listid","교촌치킨")
                    fbFireStore?.collection("shoplist")?.document( "${fbAuth?.uid}")?.update("shoplist","${sumlist} 1 " )

                }
            }

        }

    }




    }

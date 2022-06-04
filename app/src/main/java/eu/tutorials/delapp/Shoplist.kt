package eu.tutorials.delapp

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import eu.tutorials.delapp.databinding.ShoplistScreenBinding
import eu.tutorials.delapp.model.ShopList
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.shoplist_screen.*

class Shoplist : AppCompatActivity() {

    val db = FirebaseFirestore.getInstance()

    var fbAuth : FirebaseAuth? = null
    var fbFireStore : FirebaseFirestore? =null
    val binding by lazy { ShoplistScreenBinding.inflate(layoutInflater) }

    var shopInfo = ShopList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        fbAuth = FirebaseAuth.getInstance()
        fbFireStore = FirebaseFirestore.getInstance()


        showShopList()

        with(binding) {
            binding.btnCancel.setOnClickListener { cancelOrder()
            }

        }


    }

    fun showShopList () {

        fbFireStore!!.collection("shoplist").whereEqualTo("uid","${fbAuth?.uid}")?.get().addOnSuccessListener {
                result ->
            for(document in result){
                var shopName = document["listid"] as String
                var totalPrice = document["pricesum"] as String
                var menuList = document["shoplist"] as String

                shoplist_info.text = "${shopName} \n주문 내역 : ${menuList} \n총 금액 : ${totalPrice}원"

            }
        }
    }
        fun cancelOrder (){

            var money = 10000
            fbFireStore?.collection("users")?.whereEqualTo("uid","${fbAuth?.uid}")?.get()
                ?.addOnSuccessListener { result ->
                    for (document in result) {
                        money = Integer.parseInt(document["cash"] as String)
                    }
                }


            fbFireStore?.collection("shoplist")?.whereEqualTo("uid","${fbAuth?.uid}")?.get()
                ?.addOnSuccessListener { result ->
                    for (document in result) {
                        var orderPrice = Integer.parseInt(document["pricesum"] as String)
                        var outcome = (orderPrice + money).toString()

                            fbFireStore?.collection("users")?.document("${fbAuth?.uid}")
                                ?.update("cash", outcome)

                        Toast.makeText(this, " 주문이 취소되었습니다", Toast.LENGTH_SHORT).show()
                        shopInfo.uid = fbAuth?.uid
                        shopInfo.listid = " "
                        shopInfo.pricesum = "0"
                        shopInfo.shoplist = " "

                        fbFireStore?.collection("shoplist")?.document(fbAuth?.uid.toString())
                            ?.set(shopInfo)

                    }
                }
            val intent = Intent(this, MainScreen::class.java)
            startActivity(intent)
        }


}
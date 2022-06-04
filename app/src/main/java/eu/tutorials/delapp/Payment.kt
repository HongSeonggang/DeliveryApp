package eu.tutorials.delapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import eu.tutorials.delapp.databinding.OrderPaymentBinding
import kotlinx.android.synthetic.main.order_payment.*

class Payment : AppCompatActivity() {


    var fbAuth : FirebaseAuth? = null
    var fbFireStore : FirebaseFirestore? =null

    val binding by lazy { OrderPaymentBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        fbAuth = FirebaseAuth.getInstance()
        fbFireStore = FirebaseFirestore.getInstance()



        // 결제하기 버튼
        with(binding) {
            binding. btnPayment.setOnClickListener { deliveryOptionCheck() }
        }
        //포장하기 버튼

    }

    fun deliveryOptionCheck() {

        var money = 0

        fbFireStore?.collection("users")?.whereEqualTo("uid","${fbAuth?.uid}")?.get()
            ?.addOnSuccessListener { result ->
                for (document in result)
                {
                    money = Integer.parseInt(document["cash"] as String)

                    if(checkbox_packing.isChecked){
                        if(document["delopt"] == true){
                            //배달 수행 체크하고 결제할때
                            Toast.makeText(this, " 배달 수행이 시작됩니다", Toast.LENGTH_SHORT).show()

                                val intent = Intent(this, Packing::class.java)
                                startActivity(intent)

                        }else{
                            Toast.makeText(this, " 배달원 신청을 하지 않았습니다", Toast.LENGTH_SHORT).show()
                        }
                    }else{
                        // 체크 안하고 그냥 주문만 할때
                        val intent = Intent(this, MainScreen::class.java)
                        startActivity(intent)
                        Toast.makeText(this, "배달 결제가 완료되었습니다", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        fbFireStore?.collection("shoplist")?.whereEqualTo("uid","${fbAuth?.uid}")?.get()
            ?.addOnSuccessListener { result ->
                for (document in result){

                    var payPrice = Integer.parseInt(document["pricesum"] as String)

                    var outPrice = money - payPrice
                    var outcome = outPrice.toString()
                    fbFireStore?.collection("users")?.document("${fbAuth?.uid}")?.update("cash", outcome)

                }
            }

    }



}
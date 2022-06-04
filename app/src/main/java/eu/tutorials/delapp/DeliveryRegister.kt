package eu.tutorials.delapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import eu.tutorials.delapp.databinding.DeliveryRegisterBinding
import eu.tutorials.delapp.databinding.MainScreenBinding
import eu.tutorials.delapp.model.User
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.delivery_register.*

class DeliveryRegister : AppCompatActivity() {

    val binding by lazy { DeliveryRegisterBinding.inflate(layoutInflater) }
    val database =
        Firebase.database("https://delapp-682de-default-rtdb.asia-southeast1.firebasedatabase.app")
    val userRefs = database.getReference("users")

    var fbAuth : FirebaseAuth? = null
    var fbFireStore : FirebaseFirestore? =null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        fbAuth = FirebaseAuth.getInstance()
        fbFireStore = FirebaseFirestore.getInstance()

        // 유저 모델로부터 정보를 받아오는 변수
        var userInfo = User()

        // 배달원 등록 버튼
        with(binding) {
            binding.btnConfirm.setOnClickListener { deliveryRegister() }
        }



    }

    fun deliveryRegister () {


                if (checkboxDeposit.isChecked) {

                    //현재 로그인 된 아이디에 대하여

                    fbFireStore?.collection("users")?.whereEqualTo("id","${fbAuth?.currentUser?.email}")?.get()
                        ?.addOnSuccessListener {result ->

                            for (document in result) {  // 가져온 문서들은 result에 들어감
                                //


                                // 캐쉬 값 불러오기
                                val cash = document["cash"] as String

                                val delopt = true

                                // int로 캐스팅
                                val intCash = Integer.parseInt(cash)
                                // 보증금 결과값 다시 String 캐스팅
                                val outCash = (intCash - 30000).toString()
                                fbFireStore?.collection("users")?.document("${fbAuth?.uid}")?.update("cash", outCash)
                                fbFireStore?.collection("users")?.document("${fbAuth?.uid}")?.update("delopt", delopt)

                                Toast.makeText(this, " 배달원 등록 완료", Toast.LENGTH_SHORT).show()


                                //메인화면 이동
                                val intent = Intent(this, MainScreen::class.java)
                                startActivity(intent)



                            }
                    }
                }


    }


}
package eu.tutorials.delapp

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import eu.tutorials.delapp.databinding.DeliveryRegisterBinding
import eu.tutorials.delapp.databinding.StatusScreenBinding
import kotlinx.android.synthetic.main.main_screen.*
import kotlinx.android.synthetic.main.status_screen.*

class Status : AppCompatActivity() {

    val binding by lazy { StatusScreenBinding.inflate(layoutInflater) }

    var fbAuth : FirebaseAuth? = null
    var fbFireStore : FirebaseFirestore? =null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        fbAuth = FirebaseAuth.getInstance()
        fbFireStore = FirebaseFirestore.getInstance()

        // 개인정보 수정 버튼

        with(binding) {
            binding.btnModifyInfo.setOnClickListener {
                goModifyInfo()
                 }
        }

        // 배달등록 버튼
        with(binding) {
            binding.btnDeliveryRegister.setOnClickListener { deloptCheck() }
        }

    }


    fun goModifyInfo()
    {
        val intent = Intent(this, ModifyInfo::class.java)
        startActivity(intent)
    }

    //배달원 속성 체크 함수
    private fun deloptCheck() {

        Log.d(TAG, "333333333333333")

        fbFireStore?.collection("users")?.whereEqualTo("id","${fbAuth?.currentUser?.email}")?.get()
            ?.addOnSuccessListener { result ->

                for (document in result) {



                    if(document["delopt"] as Boolean){

                        Toast.makeText(this, "이미 배달원입니다", Toast.LENGTH_SHORT).show()

                        Log.d(TAG, "22222222222222222222222222222")
                    }
                    else{
                        val intent = Intent(this, DeliveryRegister::class.java)
                        startActivity(intent)
                        Log.d(TAG, "1111111111111111111")
                    }
                }
            }
    }
}
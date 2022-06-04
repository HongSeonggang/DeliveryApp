package eu.tutorials.delapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.content.res.TypedArrayUtils.getText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import eu.tutorials.delapp.databinding.ActivityRegisterBinding
import eu.tutorials.delapp.databinding.LoginBinding
import eu.tutorials.delapp.model.User
import io.reactivex.internal.util.NotificationLite.getValue
import kotlinx.android.synthetic.main.login.*
import kotlinx.android.synthetic.main.main_screen.*

class Login : AppCompatActivity() {

    val binding by lazy { LoginBinding.inflate(layoutInflater) }
    val database =
        Firebase.database("https://delapp-682de-default-rtdb.asia-southeast1.firebasedatabase.app")
    val userRefs = database.getReference("users")


    private val RC_SIGN_IN = 9001
    private var firebaseAuth : FirebaseAuth? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // 회원가입 버튼
        btn_register.setOnClickListener {
            val intent = Intent(this, ActivityRegister::class.java)
            startActivity(intent)
        }

        // 로그인 버튼
        with(binding) {
            binding.btnLogin.setOnClickListener { login() }
        }

        firebaseAuth = FirebaseAuth.getInstance()

    }


    fun goMain() {

        val intent = Intent(this, MainScreen::class.java)
        startActivity(intent)

    }

    private fun login() {

        //입력된 값 가져오기
        val id = edit_id.text.toString()
        val password = edit_pw.text.toString()

        if (id.isNotEmpty() && password.isNotEmpty()) {
            //아이디로 User 데이터 가져오기
            firebaseAuth!!.signInWithEmailAndPassword(id,password)
                .addOnCompleteListener(this) {
                    if(it.isSuccessful){

                        Toast.makeText(this, " 로그인 성공", Toast.LENGTH_SHORT).show()
                        val user = firebaseAuth?.currentUser
                        goMain()
                    }else{
                        Toast.makeText(this, " 로그인 실패", Toast.LENGTH_SHORT).show()
                    }


                }
        }


    }
}

package eu.tutorials.delapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import eu.tutorials.delapp.databinding.MainScreenBinding
import eu.tutorials.delapp.databinding.ModifyInfoBinding
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.modify_info.*

class ModifyInfo : AppCompatActivity() {

    var fbAuth : FirebaseAuth? = null
    var fbFireStore : FirebaseFirestore? =null

    val binding by lazy { ModifyInfoBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        fbAuth = FirebaseAuth.getInstance()
        fbFireStore = FirebaseFirestore.getInstance()



        // 회원정보 수정버튼
        with(binding){
            binding.btnModifyConfirm.setOnClickListener{ findPassword() }
        }



    }


    fun findPassword(){
        FirebaseAuth.getInstance().sendPasswordResetEmail(modify_pw.text.toString()).addOnCompleteListener { task ->
            if(task.isSuccessful){
                Toast.makeText(this, "비밀번호 변경 메일을 전송했습니다", Toast.LENGTH_LONG).show()

                val intent = Intent(this, Login::class.java)
                startActivity(intent)
            }else{
                Toast.makeText(this, task.exception.toString(), Toast.LENGTH_LONG).show()
            }
        }
    }

}
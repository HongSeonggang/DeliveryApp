package eu.tutorials.delapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import eu.tutorials.delapp.databinding.ActivityRegisterBinding
import eu.tutorials.delapp.model.User
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.login.*
import kotlinx.android.synthetic.main.login.btn_register

class ActivityRegister : AppCompatActivity() {

    private var _binding: ActivityRegisterBinding? = null
    private val binding get() = _binding!!

    private lateinit var auth: FirebaseAuth

    var fbAuth : FirebaseAuth? = null
    var fbFireStore : FirebaseFirestore? =null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()






        with(binding){
            binding.btnSignIn.setOnClickListener {
                val id = binding.editEmail.text.toString()
                val password = binding.editPw.text.toString()

                // Validate...

                createUser(id, password)
            }
        }
    }



    fun createUser(id: String, password: String) {
        auth.createUserWithEmailAndPassword(id, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "회원가입 성공", Toast.LENGTH_SHORT).show()
                    val user = auth.currentUser


                    updateUI(user)
                } else {
                    Toast.makeText(this, "회원가입 실패1", Toast.LENGTH_SHORT).show()
                    updateUI(null)
                }
            }
            .addOnFailureListener {
                Toast.makeText(this, "회원가입 실패2", Toast.LENGTH_SHORT).show()
            }
    }

    fun updateUI(user: FirebaseUser?) {
        user?.let {
            binding.txtResult.text = "Email: ${user.email}\nUid: ${user.uid}"
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}
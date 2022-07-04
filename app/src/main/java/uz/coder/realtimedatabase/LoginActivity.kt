package uz.coder.realtimedatabase

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import uz.coder.realtimedatabase.data.Data
import uz.coder.realtimedatabase.databinding.ActivityLoginBinding
import uz.coder.realtimedatabase.models.User

class LoginActivity : AppCompatActivity() {

    lateinit var binding: ActivityLoginBinding
    private val database = FirebaseDatabase.getInstance()
    private val myRef = database.getReference("users")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var username = ""
        var intentt = false
        binding.btn.setOnClickListener {
            myRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val children = snapshot.children
                    for (value in children) {
                        val user = value.getValue(User::class.java)
                        if (user?.userName?.equals("@${binding.nameEdit.text.toString()}") == true) {
                            if (user.password?.equals(binding.passwordEdit.text.toString())!!) {
                                Data.username = user.userName!!

                                user.online = true
                                for (c in user.userName!!) {
                                    if (c != '@') {
                                        username += c
                                    }
                                }
                                val usernameLength = user.userName?.length!! - 1
                                if (usernameLength == username.length) {
                                    if (username != null) {
//                                        myRef.child(username).setValue(user)
                                    }
                                }

                            }
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {

                }
            })
            val intent = Intent(this@LoginActivity, MainActivity2::class.java)
            startActivity(intent)
        }


    }
}
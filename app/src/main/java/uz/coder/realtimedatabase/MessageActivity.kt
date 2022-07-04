package uz.coder.realtimedatabase

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import uz.coder.realtimedatabase.adapters.GroupChatAdapter
import uz.coder.realtimedatabase.data.Data
import uz.coder.realtimedatabase.databinding.ActivityMessageBinding
import uz.coder.realtimedatabase.models.Message
import uz.coder.realtimedatabase.models.User
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class MessageActivity : AppCompatActivity() {

    private val database = FirebaseDatabase.getInstance()


    lateinit var binding: ActivityMessageBinding
    val reference = database.getReference("users")


    lateinit var firebaseAuth: FirebaseAuth

    @SuppressLint("SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMessageBinding.inflate(layoutInflater)
        setContentView(binding.root)
    var username = ""
        val username1 = Data.username
        for (c in username1.toString()) {
            if (c != '@') {
                username += c
            }
        }


        val user = intent.getSerializableExtra("user") as User

        val arrayList = ArrayList<Message>()


        var friend = ""
        val s = user.userName
        for (c in s.toString()) {
            if (c != '@') {
                friend += c
            }
        }

        binding.name.text = friend

        binding.sendBtn.setOnClickListener {
            val message = binding.textMessage.text.toString()
            val simpleDateFormat = SimpleDateFormat("HH:mm")
            val date = simpleDateFormat.format(Date())
            val key = reference.push().key

//            reference.child("user").setValue(Message("s","s","s"))
            reference.child("${username}/message/${friend}").child(key!!)
                .setValue(
                    Message(
                        message,
                        date,
                        username
                    )
                )

            reference.child("${friend}/message/${username}").child(key)
                .setValue(
                    Message(
                        message,
                        date,
                        username
                    )
                )
            val iii = Intent(this,LockActivity::class.java)
//            startActivity(iii)
            binding.textMessage.setText("")
        }
 val adapter = GroupChatAdapter(this@MessageActivity, arrayList, username1)
        reference.child("$username/message/$friend")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    arrayList.clear()
                    val children = snapshot.children
                    for (child in children) {
                        val value = child.getValue(Message::class.java)
                        if (value != null) {
                            arrayList.add(value)
                        }
                    }
                    adapter.notifyItemInserted(arrayList.size)
                    binding.messageRv.scrollToPosition(arrayList.size - 1)
                }
                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(this@MessageActivity, "0", Toast.LENGTH_SHORT).show()
                }
            })
        binding.messageRv.adapter = adapter
    }
}
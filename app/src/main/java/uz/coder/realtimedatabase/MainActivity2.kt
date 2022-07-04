package uz.coder.realtimedatabase

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.database.*
import com.squareup.picasso.Picasso
import uz.coder.realtimedatabase.adapters.UserAdapter
import uz.coder.realtimedatabase.data.Data
import uz.coder.realtimedatabase.databinding.ActivityMain2Binding
import uz.coder.realtimedatabase.models.User


class MainActivity2 : AppCompatActivity() {

    var gso: GoogleSignInOptions? = null
    var gsc: GoogleSignInClient? = null
    var arrayList = ArrayList<User>()
    var user = User()
    val database = FirebaseDatabase.getInstance()
    val myRef = database.getReference("users")
    lateinit var binding: ActivityMain2Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)
        Picasso.get()
            .load("https://xoliqov.netlify.app/assets/img/profile-img.jpg")
            .into(binding.yourProfileImg)

        binding.statusBar.layoutParams.height = getStatusBarHeight()

        binding.searchEd.visibility = View.GONE

        binding.visSearch.setOnClickListener {
            if (binding.searchEd.visibility == View.GONE) {
                binding.searchEd.visibility = View.VISIBLE
                binding.icSearch.setImageResource(R.drawable.ic_add)
                binding.icSearch.rotation = 45F
            } else {
                binding.searchEd.visibility = View.GONE
                binding.icSearch.setImageResource(R.drawable.ic_serch)
                binding.icSearch.rotation = 0F
            }
        }


        binding.searchRemove.visibility = View.GONE
        binding.searchV.addTextChangedListener {
            if (binding.searchV.text.isNotEmpty()) {
                binding.searchRemove.visibility = View.VISIBLE
            } else {
                binding.searchRemove.visibility = View.GONE
            }
        }

        binding.searchRemove.setOnClickListener {
            binding.searchV.setText("")
        }
        var username1 = ""
        val ss = Data.username
        for (c in ss.toString()) {
            if (c != '@') {
                username1 += c
            }
        }
        val userAdapter = UserAdapter(arrayList, object : UserAdapter.OnClick {
            override fun onItemClickListener(user: User) {
                overridePendingTransition(R.anim.anim, R.anim.anim)
                val intent = Intent(this@MainActivity2, MessageActivity::class.java)
                intent.putExtra("user", user)
                startActivity(intent)

//                overridePendingTransition(R.anim.anim, R.anim.activity_stay)
            }
        })

        gso =
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build()
        gsc = GoogleSignIn.getClient(this, gso!!)
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("users")
        myRef.addValueEventListener(object : ValueEventListener {
            @SuppressLint("NotifyDataSetChanged")
            override fun onDataChange(snapshot: DataSnapshot) {
                arrayList.clear()
                val children = snapshot.children
                for (child in children) {
                    val value = child.getValue(User::class.java)
                    var username2 = ""
                    val ssss = value?.userName
                    for (c in ssss.toString()) {
                        if (c != '@') {
                            username2 += c
                        }
                    }
                    if (value != null && username2 != username1) {
                        arrayList.add(value)
                    }
                    binding.rv.adapter = userAdapter
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })



    }

    private fun getStatusBarHeight(): Int {
        var re = 0
        val reId = resources.getIdentifier("status_bar_height", "dimen", "android")
        if (reId > 0) {
            re = resources.getDimensionPixelSize(reId)
        }
        return re
    }

    fun signOut() {
        gsc!!.signOut().addOnCompleteListener {
            finish()
            startActivity(Intent(this, MainActivity::class.java))
        }
    }


//    override fun onDestroy() {
//        super.onDestroy()
//        var username = ""
//        user.online = false
//        for (c in intent.getStringExtra("username")!!) {
//            if (c != '@') {
//                username += c
//            }
//        }
//        val usernameLength = intent.getStringExtra("username")!!.length - 1
//        if (usernameLength == username.length) {
//            myRef.child(username).setValue(user.online)
//        }
//    }
//
//    override fun onStop() {
//        super.onStop()
//        var username = ""
//        user.online = false
//        for (c in intent.getStringExtra("username")!!) {
//            if (c != '@') {
//                username += c
//            }
//        }
//        val usernameLength = intent.getStringExtra("username")!!.length - 1
//        if (usernameLength == username.length) {
//            myRef.child(username).setValue(user)
//        }
//    }
}
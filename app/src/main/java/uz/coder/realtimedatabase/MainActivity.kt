package uz.coder.realtimedatabase


import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.database.*
import uz.coder.realtimedatabase.data.Data
import uz.coder.realtimedatabase.databinding.ActivityMainBinding
import uz.coder.realtimedatabase.models.User


class MainActivity : AppCompatActivity() {

    private lateinit var signInRequest: BeginSignInRequest
    private val TAG = "MainActivity"
    private lateinit var oneTapClient: SignInClient
    private var signUpRequest: BeginSignInRequest? = null
    lateinit var googleSignInOptions: GoogleSignInOptions
    private lateinit var binding: ActivityMainBinding
    lateinit var googleSignIn: GoogleSignInClient
    private val REQ_ONE_TAP = 2  // Can be any integer unique to the Activity
    private var showOneTapUI = true
    val database = FirebaseDatabase.getInstance()
    val myRef = database.getReference("users")

    override fun onCreate(savedInstanceState: Bundle?) {
        if (Build.VERSION.SDK_INT >= 19 && Build.VERSION.SDK_INT < 21) {
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        }
        if (Build.VERSION.SDK_INT >= 19) {
            window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }
        if (Build.VERSION.SDK_INT >= 21) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.statusBarColor = Color.TRANSPARENT
        }
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.statusBar.layoutParams.height = getStatusBarHeight()

        val options = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.your_web_client_id))
            .requestEmail()
            .build()
        googleSignIn = GoogleSignIn.getClient(this, options)

        binding.createdAccount.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
//            finish()
        }
        binding.btn.setOnClickListener {
            val id = 0
            myRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val children = snapshot.children
                    for (child in children) {
                        val value = child.getValue(User::class.java)
                        if (binding.usernameEdit.text.toString().contains(" ")) {
                            binding.usernameEdit.setTextColor(Color.parseColor("#EC484B"))
                        } else {
                            if (value?.userName.equals("@${binding.usernameEdit.text.toString()}")) {
                                binding.usernameEdit.setTextColor(Color.parseColor("#EC484B"))
//                                finish()
                            } else {
                                binding.usernameEdit.setTextColor(Color.WHITE)

                                if (value?.userName != "@${binding.usernameEdit.text.toString()}") {
                                    val user = User(
                                        binding.nameEdit.text.toString(),
                                        "@${binding.usernameEdit.text.toString()}",
                                        "https://images.unsplash.com/photo-1535713875002-d1d0cf377fde?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8M3x8dXNlcnxlbnwwfHwwfHw%3D&w=1000&q=80",
                                        id, binding.passwordEdit.text.toString(), true
                                    )
                                    myRef.child(binding.usernameEdit.text.toString()).setValue(user)
                                    val intent =
                                        Intent(this@MainActivity, MainActivity2::class.java)
                                    intent.putExtra(
                                        "username",
                                        "@${binding.usernameEdit.text.toString()}"
                                    )
                                    Data.username = value?.userName!!
                                    //startActivity(intent)
                                }
                            }
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {

                }
            })
            val user = User(
                binding.nameEdit.text.toString(),
                binding.usernameEdit.text.toString(),
                "https://images.unsplash.com/photo-1535713875002-d1d0cf377fde?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8M3x8dXNlcnxlbnwwfHwwfHw%3D&w=1000&q=80",
                id, binding.passwordEdit.text.toString(), true
            )
            myRef.child(binding.usernameEdit.text.toString()).setValue(user)
        }

        binding.usernameEdit.addTextChangedListener {
            if (binding.usernameEdit.text.toString().contains(" ")) {
                binding.usernameEdit.setTextColor(Color.parseColor("#EC484B"))
            }
            myRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val children = snapshot.children
                    for (child in children) {
                        val value = child.getValue(User::class.java)
                        if (value?.userName == binding.usernameEdit.text.toString()) {
                            binding.usernameEdit.setTextColor(Color.parseColor("#EC484B"))
                        } else {
                            binding.usernameEdit.setTextColor(Color.WHITE)
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {

                }
            })
        }
    }


    private fun getStatusBarHeight(): Int {
        var re = 0
        val reId = resources.getIdentifier("status_bar_height", "dimen", "android")
        if (reId > 0) {
            re = resources.getDimensionPixelSize(reId)
        }
        return re
    }
}
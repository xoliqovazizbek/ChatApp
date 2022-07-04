package uz.coder.realtimedatabase

import android.app.admin.DevicePolicyManager
import android.content.ComponentName
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import uz.coder.realtimedatabase.databinding.ActivityLockBinding

class LockActivity : AppCompatActivity() {

    val RESULT_ENABLE = 11
    private val devicePolicyManager: DevicePolicyManager? = null
    private val compName: ComponentName? = null

    lateinit var binding: ActivityLockBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLockBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }

//    override fun onResume() {
//        super.onResume()
//        val isActive = devicePolicyManager!!.isAdminActive(compName!!)
//        binding.disable.setVisibility(if (isActive) View.VISIBLE else View.GONE)
//        binding.enable.setVisibility(if (isActive) View.GONE else View.VISIBLE)
//    }


}
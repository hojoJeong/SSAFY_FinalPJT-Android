package com.ssafy.silencelake.activity.login

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import com.ssafy.silencelake.R
import com.ssafy.silencelake.activity.admin.AdminActivity
import com.ssafy.silencelake.activity.main.MainActivity
import com.ssafy.silencelake.fragment.login.JoinFragment
import com.ssafy.silencelake.fragment.login.LoginFragment
import com.ssafy.silencelake.util.ApplicationClass.Companion.sharedPreferencesUtil

class LoginActivity : AppCompatActivity() {
    @RequiresApi(api = Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        createNotificationChannel(channel_id, "ssafy")
        //로그인 상태 체크
        var user = sharedPreferencesUtil.getUser()

        //로그인 상태 확인. id가 있다면 로그인 된 상태.
        if (user.id != "") {
            if (user.id == "admin") {
                openFragment(4)
            } else {
                openFragment(3)
            }
        } else {
            openFragment(1)
        }
    }
    @RequiresApi(Build.VERSION_CODES.O)
    // Notification 수신을 위한 체널 추가
    private fun createNotificationChannel(id: String, name: String) {
        val importance = NotificationManager.IMPORTANCE_MAX
        val channel = NotificationChannel(id, name, importance)

        val notificationManager: NotificationManager
                = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
    fun openFragment(number: Int) {
        val transaction = supportFragmentManager.beginTransaction()
        when (number) {
            1 -> transaction.replace(R.id.fragment_container_login, LoginFragment())
            2 -> {
                transaction.setCustomAnimations(
                    R.anim.enter_from_right,
                    R.anim.exit_to_right,
                    R.anim.enter_from_right,
                    R.anim.exit_to_right
                )
                transaction.replace(R.id.fragment_container_login, JoinFragment())
                transaction.addToBackStack(null)
            }
            3 -> {
                val intent = Intent(this, MainActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent)
            }
            4 -> {
                val intent = Intent(this, AdminActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent)
            }
        }
        transaction.commit()
    }
    companion object{
        // Notification Channel ID
        const val channel_id = "ssafy_channel"
    }
}
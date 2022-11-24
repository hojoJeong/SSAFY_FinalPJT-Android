package com.ssafy.silencelake.activity.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ssafy.silencelake.R
import com.ssafy.silencelake.activity.MainActivity
import com.ssafy.silencelake.fragment.login.JoinFragment
import com.ssafy.silencelake.fragment.login.LoginFragment
import com.ssafy.silencelake.util.ApplicationClass.Companion.sharedPreferencesUtil

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //로그인 상태 체크
        var user = sharedPreferencesUtil.getUser()

        //로그인 상태 확인. id가 있다면 로그인 된 상태.
        if (user.id != "") {
            openFragment(3)
        } else {
            openFragment(1)
        }
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
        }
        transaction.commit()
    }

}
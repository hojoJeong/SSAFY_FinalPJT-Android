package com.ssafy.silencelake.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ssafy.silencelake.R
import com.ssafy.silencelake.fragment.JoinFragment
import com.ssafy.silencelake.fragment.LoginFragment

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container_login, LoginFragment()).commit()
    }
    fun openFragment(number: Int){
        when(number) {
            1 -> supportFragmentManager.beginTransaction().replace(R.id.fragment_container_login, LoginFragment()).commit()
            2 -> supportFragmentManager.beginTransaction().replace(R.id.fragment_container_login, JoinFragment()).commit()
            3 -> {
                val intent = Intent(this, MainActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent)
            }
        }
    }

}
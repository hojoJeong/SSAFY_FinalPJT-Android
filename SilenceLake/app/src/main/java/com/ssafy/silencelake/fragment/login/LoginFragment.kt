package com.ssafy.silencelake.fragment.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.ssafy.silencelake.activity.admin.AdminActivity
import com.ssafy.silencelake.activity.login.LoginActivity
import com.ssafy.silencelake.databinding.FragmentLoginBinding
import com.ssafy.silencelake.dto.UserDto
import com.ssafy.silencelake.repository.UserRepository
import com.ssafy.silencelake.util.ApplicationClass
import com.ssafy.silencelake.util.RetrofitCallback

private const val TAG = "LoginFragment_싸피"

class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private lateinit var loginActivity: LoginActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        loginActivity = context as LoginActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            buttonJoinLogin.setOnClickListener {
                loginActivity.openFragment(2)
            }
            buttonLoginLogin.setOnClickListener {
                login(textfieldIdLoginValue.text.toString(), textfieldPwLoginValue.text.toString())
            }
        }
    }

    private fun login(loginId: String, loginPW: String) {
        val user = UserDto(loginId, loginPW)
        UserRepository.login(user, LoginCallback())
    }

    inner class LoginCallback : RetrofitCallback<UserDto> {
        override fun onError(t: Throwable) {
            Log.d(TAG, t.message ?: "유저 정보 불러오는 중 통신오류")
        }

        override fun onSuccess(code: Int, user: UserDto) {
            if (user.id != null) {
                if(user.id == "admin" && user.pass == "admin"){
                    ApplicationClass.sharedPreferencesUtil.addUser(user)
                    val intent = Intent(requireActivity(), AdminActivity::class.java)
                    startActivity(intent)
                } else{
                    Log.d(TAG, "onSuccess: $user")
                    Toast.makeText(context, "로그인 되었습니다.", Toast.LENGTH_SHORT).show()
                    Log.d(TAG, "onSuccess: user : ${user}")
                    // 로그인 시 user정보 sp에 저장
                    ApplicationClass.sharedPreferencesUtil.addUser(user)
                    loginActivity.openFragment(3)
                }
            } else {
                Toast.makeText(context, "ID 또는 패스워드를 확인해 주세요.", Toast.LENGTH_SHORT).show()
            }
        }

        override fun onFailure(code: Int) {
            Log.d(TAG, "onResponse: Error Code $code")
        }

    }
}
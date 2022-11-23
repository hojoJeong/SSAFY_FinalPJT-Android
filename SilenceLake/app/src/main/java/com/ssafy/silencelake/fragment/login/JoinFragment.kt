package com.ssafy.silencelake.fragment.login

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import com.ssafy.silencelake.R
import com.ssafy.silencelake.activity.LoginActivity
import com.ssafy.silencelake.databinding.FragmentJoinBinding

import com.ssafy.silencelake.dto.UserDto
import com.ssafy.silencelake.repository.UserRepository
import com.ssafy.silencelake.util.RetrofitCallback

private const val TAG = "JoinFragment_싸피"

class JoinFragment : Fragment() {
    private lateinit var binding: FragmentJoinBinding
    private var checkedId = false
    private lateinit var loginActivity: LoginActivity
    override fun onAttach(context: Context) {
        super.onAttach(context)
        loginActivity = context as LoginActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentJoinBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            textfieldIdJoinValue.addTextChangedListener {
                checkedId = false
                binding.checkboxDuplicateJoin.backgroundTintList =
                    ColorStateList.valueOf(Color.LTGRAY)
            }
            checkboxDuplicateJoin.setOnClickListener {
                checkDuplicatedId(textfieldIdJoinValue.text.toString())
            }
            buttonJoinJoin.setOnClickListener {
                if (checkedId) {
                    if (textfieldIdJoinValue.text.toString() == "") {
                        textfieldIdJoin.error = "아이디는 필수 입력 입니다."
                    }
                    if (textfieldPwJoinValue.text.toString() == "") {
                        textfieldPwJoin.error = "비밀번호는 필수 입력 입니다."
                    }
                    if (textfieldNicknameJoinValue.text.toString() == "") {
                        textfieldNicknameJoin.error = "닉네임은는 필수 입력 입니다."
                    }
                    if (textfieldIdJoinValue.text.toString() != "" && textfieldPwJoinValue.text.toString() != "" && textfieldNicknameJoinValue.text.toString() != "") {
                        val user = UserDto(
                            textfieldIdJoinValue.text.toString(),
                            textfieldNicknameJoinValue.text.toString(),
                            textfieldPwJoinValue.text.toString(),

                            0,
                            null,
                        )
                        UserRepository.signUpUser(user, SignUpCallback())
                    }
                } else {
                    Toast.makeText(context, "중복 확인이 필요한 아이디", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    fun checkDuplicatedId(id: String) {
        UserRepository.checkDuplicatedId(id, CheckDupclicatedIdCallback())
    }

    inner class CheckDupclicatedIdCallback : RetrofitCallback<Boolean> {
        override fun onError(t: Throwable) {
            Log.d(TAG, "onError: ${t.message}")
        }

        override fun onSuccess(code: Int, responseData: Boolean) {
            if (responseData) {
                Toast.makeText(context, "이미 사용중인 아이디 입니다.", Toast.LENGTH_SHORT).show()
                checkedId = false
                binding.checkboxDuplicateJoin.backgroundTintList =
                    ColorStateList.valueOf(Color.LTGRAY)
            } else {
                Toast.makeText(context, "사용 가능한 아이디 입니다.", Toast.LENGTH_SHORT).show()
                checkedId = true
                binding.checkboxDuplicateJoin.backgroundTintList =
                    ColorStateList.valueOf(context!!.resources.getColor(R.color.primary, null))
            }
        }

        override fun onFailure(code: Int) {
            Log.d(TAG, "onFailure: $code")
        }
    }

    inner class SignUpCallback : RetrofitCallback<Boolean> {
        override fun onError(t: Throwable) {
            Log.d(TAG, "onError: ${t.message}")
        }

        override fun onSuccess(code: Int, responseData: Boolean) {
            Toast.makeText(context, "회원가입 성공!", Toast.LENGTH_SHORT).show()
            loginActivity.openFragment(1)
        }

        override fun onFailure(code: Int) {
            Log.d(TAG, "onFailure: $code")
        }

    }
}
package com.ssafy.silencelake.activity.main

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.nfc.NdefMessage
import android.nfc.NfcAdapter
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.ssafy.hw_android_network_05_hojojeong.util.CustomDialog
import com.ssafy.silencelake.R
import com.ssafy.silencelake.activity.login.LoginActivity
import com.ssafy.silencelake.api.FirebaseTokenApi
import com.ssafy.silencelake.databinding.ActivityMainBinding
import com.ssafy.silencelake.databinding.DialogCafeInfoBinding
import com.ssafy.silencelake.fragment.main.home.HomeFragment
import com.ssafy.silencelake.fragment.main.home.NFCStateViewModel
import com.ssafy.silencelake.fragment.main.menu.ProductMenuFragment
import com.ssafy.silencelake.fragment.main.menu.shoppinglist.ShoppingListFragment
import com.ssafy.silencelake.fragment.main.menu.detail.ProductDetailFragment
import com.ssafy.silencelake.fragment.main.mypage.MypageFragment
import com.ssafy.silencelake.fragment.main.mypage.UserResponseViewModel
import com.ssafy.silencelake.util.ApplicationClass
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val TAG = "MainActivity_싸피"

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val userResponseViewModel by viewModels<UserResponseViewModel>()
    lateinit var nAdapter: NfcAdapter
    private lateinit var filters: Array<IntentFilter>
    private lateinit var pIntent: PendingIntent
    private val nfcStateViewModel by viewModels<NFCStateViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w(TAG, "FCM 토큰 얻기에 실패하였습니다.", task.exception)
                return@OnCompleteListener
            }
            // token log 남기기
            Log.d(TAG, "token: ${task.result ?: "task.result is null"}")
            if (task.result != null) {
                uploadToken(task.result!!)
            }
        })

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()

    }


    companion object {
        // ratrofit  수업 후 network 에 업로드 할 수 있도록 구성
        fun uploadToken(token: String) {
            // 새로운 토큰 수신 시 서버로 전송
            ApplicationClass.myToken = token
            Log.d(TAG, "uploadToken: ${ApplicationClass.myToken}")
            val storeService = ApplicationClass.retrofit.create(FirebaseTokenApi::class.java)
            storeService.uploadToken(token).enqueue(object : Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    if (response.isSuccessful) {
                        val res = response.body()
                        Log.d(TAG, "onResponse: $res")
                    } else {
                        Log.d(TAG, "onResponse: Error Code ${response.code()}")
                    }
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    Log.d(TAG, t.message ?: "토큰 정보 등록 중 통신오류")
                }
            })
        }
    }

    private fun init() {
        nfcStateViewModel.customDialog = CustomDialog(this)
        setNdef()

        initView()
        initBottomNavigation()
    }

    private fun initView() {
        val initFragment = supportFragmentManager.findFragmentById(R.id.fragment_container_main)
        if (initFragment == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container_main, HomeFragment()).commit()
        }
    }

    private fun initBottomNavigation() {
        binding.bottomnvMain.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.item_bnv_main -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container_main, HomeFragment()).commit()
                }

                R.id.item_bnv_menu -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container_main, ProductMenuFragment()).commit()
                }

                R.id.item_bnv_mypage -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container_main, MypageFragment()).commit()
                }
            }
            true
        }
    }

    fun logout() {
        //preference 지우기
        ApplicationClass.sharedPreferencesUtil.deleteUser()

        //화면이동
        val intent = Intent(this, LoginActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        startActivity(intent)
    }

    fun openShoppingList() {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.setCustomAnimations(
            R.anim.enter_from_bottom,
            R.anim.exit_to_bottom,
            R.anim.enter_from_bottom,
            R.anim.exit_to_bottom
        )
        transaction.add(R.id.fragment_container_main, ShoppingListFragment())
        transaction.addToBackStack(null)
        transaction.commit()
    }

    fun openProductDetail() {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.setCustomAnimations(
            R.anim.enter_from_right,
            R.anim.exit_to_right,
            R.anim.enter_from_right,
            R.anim.exit_to_right
        )
        transaction.add(R.id.fragment_container_main, ProductDetailFragment())
        transaction.addToBackStack(null)
        transaction.commit()
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        Log.d(TAG, "onNewIntent: ")

        val action = intent.action
            if (action == NfcAdapter.ACTION_TAG_DISCOVERED) {
                val message =
                    intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES)!![0] as NdefMessage
                val record = message.records[0]
                val byteArray = record.payload
                val readMessage = String(byteArray, 3, byteArray.size - 3)
                nfcStateViewModel.customDialog.showCafeInfo(true)
                nfcStateViewModel.customDialog.confirmBtnEventListener()
                Log.d(ContentValues.TAG, "getNFCAction: $readMessage")
            }

    }


    private fun setNdef() {
        nAdapter = NfcAdapter.getDefaultAdapter(this)
        val i = Intent(this, MainActivity::class.java)
        i.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
        pIntent = PendingIntent.getActivity(this, 0, i, PendingIntent.FLAG_MUTABLE)
        val filter =
            IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED)
        filters = arrayOf(filter)
    }

    override fun onResume() {
        super.onResume()
        nAdapter.enableForegroundDispatch(this, pIntent, filters, null)
        Log.d(TAG, "onResume: $nAdapter")
    }

    override fun onPause() {
        super.onPause()
        nAdapter.disableForegroundDispatch(this)
    }

}
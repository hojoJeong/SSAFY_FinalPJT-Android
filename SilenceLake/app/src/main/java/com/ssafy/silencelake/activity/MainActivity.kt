package com.ssafy.silencelake.activity

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.ssafy.silencelake.R
import com.ssafy.silencelake.databinding.ActivityMainBinding
import com.ssafy.silencelake.fragment.main.home.HomeFragment
import com.ssafy.silencelake.fragment.main.menu.ProductMenuFragment
import com.ssafy.silencelake.fragment.main.menu.ShoppingListFragment
import com.ssafy.silencelake.fragment.main.menu.detail.ProductDetailFragment
import com.ssafy.silencelake.fragment.main.mypage.MypageFragment
import com.ssafy.silencelake.fragment.main.mypage.UserResponseViewModel
import com.ssafy.silencelake.util.ApplicationClass

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val userResponseViewModel by viewModels<UserResponseViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
    }

    private fun init() {
        initUserResponseData()
        initView()
        initBottomNavigation()
    }

    private fun initUserResponseData(){
        var user = ApplicationClass.sharedPreferencesUtil.getUser()
        userResponseViewModel.getUserResponseInfo(user.id)
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

}
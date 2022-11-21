package com.ssafy.silencelake.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ssafy.silencelake.ProductDetailFragment
import com.ssafy.silencelake.R
import com.ssafy.silencelake.databinding.ActivityMainBinding
import com.ssafy.silencelake.fragment.HomeFragment
import com.ssafy.silencelake.fragment.MypageFragment
import com.ssafy.silencelake.fragment.ProductMenuFragment
import com.ssafy.silencelake.fragment.ShoppingListFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
    }

    private fun init() {
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
        supportFragmentManager.beginTransaction()
            .add(R.id.fragment_container_main, ProductDetailFragment()).addToBackStack(null)
            .commit()
    }
}
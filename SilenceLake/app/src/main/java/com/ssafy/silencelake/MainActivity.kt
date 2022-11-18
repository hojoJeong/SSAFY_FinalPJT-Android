package com.ssafy.silencelake

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ssafy.silencelake.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
    }

    private fun init(){
        initView()
        initBottomNavigation()
    }

    private fun initView(){
        val initFragment = supportFragmentManager.findFragmentById(R.id.fragment_container_main)
        if(initFragment == null){
            supportFragmentManager.beginTransaction().replace(R.id.fragment_container_main, HomeFragment()).commit()
        }
    }

    private fun initBottomNavigation(){
        binding.bottomnvMain.setOnItemSelectedListener {
            when(it.itemId){
                R.id.item_bnv_main -> {
                    supportFragmentManager.beginTransaction().replace(R.id.fragment_container_main, HomeFragment()).commit()
                }

                R.id.item_bnv_menu -> {
                    supportFragmentManager.beginTransaction().replace(R.id.fragment_container_main, MenuFragment()).commit()
                }

                R.id.item_bnv_mypage -> {
                    supportFragmentManager.beginTransaction().replace(R.id.fragment_container_main, MypageFragment()).commit()
                }
            }
            true
        }
    }
}
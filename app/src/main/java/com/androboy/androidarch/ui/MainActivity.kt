package com.androboy.androidarch.ui

import android.util.Log
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.androboy.androidarch.BaseActivity
import com.androboy.androidarch.databinding.ActivityMainBinding
import com.androboy.androidarch.ui.viewmodel.MainViewModel
import com.androboy.androidarch.utils.NetworkResult
import dagger.hilt.android.AndroidEntryPoint

/**
 * This is main activity
 * */

@AndroidEntryPoint
class MainActivity : BaseActivity() {
    private lateinit var viewModel: MainViewModel
    lateinit var ui: ActivityMainBinding
    var count: Int = 0;
    override fun layoutRes(): ViewBinding {
        installSplashScreen()
        return ActivityMainBinding.inflate(layoutInflater)
    }

    override fun initView() {
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        ui = binding as ActivityMainBinding

        setObserver()
        setListeners()
    }


    private fun setListeners() {

        ui.tvAddStudent.setOnClickListener {
//            ++count
//            val user = User(count, "Kamal$count", "Singh", 25, "kamal$count@yopmail.com")
//            viewModel.insertUser(user)

            viewModel.getQuotes(1)
        }
    }

    private fun setObserver() {

        viewModel.quoteLiveData.observe(this, Observer {
            when (it) {
                is NetworkResult.Success -> {
                    Log.d("MMMMMM", "setObserver Success:  ${it.data.toString()}")

                }

                is NetworkResult.Error -> {
                    Log.d("MMMMMM", "setObserver Error: ")
                }

                is NetworkResult.Loading -> {
                    Log.d("MMMMMM", "setObserver Loading: ")
                }
            }

        })

    }
}
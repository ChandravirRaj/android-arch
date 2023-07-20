package com.androboy.androidarch.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.androboy.androidarch.BaseActivity
import com.androboy.androidarch.R
import com.androboy.androidarch.databinding.ActivityMainBinding
import com.androboy.androidarch.ui.viewmodel.MainViewModel
import com.androboy.androidarch.utils.MainViewModelFactory

/**
 * This is main activity
 * */
class MainActivity : BaseActivity() {
    lateinit var viewModel: MainViewModel

    override fun layoutRes(): ViewBinding {
        installSplashScreen()
        return ActivityMainBinding.inflate(layoutInflater)
    }

    override fun initView() {

        viewModel = ViewModelProvider(this,MainViewModelFactory(application))[MainViewModel::class.java]

        val array = viewModel.readFile(this)
        array.forEachIndexed { index, quote ->  Log.d("MMMM", "initView:  ${quote.quote}")  }
    }
}
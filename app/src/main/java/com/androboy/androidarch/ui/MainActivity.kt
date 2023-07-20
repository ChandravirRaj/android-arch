package com.androboy.androidarch.ui

import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.androboy.androidarch.BaseActivity
import com.androboy.androidarch.databinding.ActivityMainBinding
import com.androboy.androidarch.ui.viewmodel.MainViewModel
import com.androboy.androidarch.utils.MainViewModelFactory

/**
 * This is main activity
 * */
class MainActivity : BaseActivity() {
    lateinit var viewModel: MainViewModel
    lateinit var ui: ActivityMainBinding
    override fun layoutRes(): ViewBinding {
        installSplashScreen()
        return ActivityMainBinding.inflate(layoutInflater)
    }

    override fun initView() {
        viewModel = ViewModelProvider(this, MainViewModelFactory(application))[MainViewModel::class.java]
        ui = binding as ActivityMainBinding
        setListeners()
        setData()
    }

    private fun setData(){
        ui.tvQuote.text = viewModel.getQuote().quote
        ui.tvAuthor.text = viewModel.getQuote().author
    }

    private fun setListeners(){
        ui.tvNext.setOnClickListener {
            ui.tvQuote.text = viewModel.nextQuote().quote
            ui.tvAuthor.text = viewModel.nextQuote().author
        }

        ui.tvPrev.setOnClickListener {
            ui.tvQuote.text = viewModel.prevQuote().quote
            ui.tvAuthor.text = viewModel.prevQuote().author
        }
    }
}
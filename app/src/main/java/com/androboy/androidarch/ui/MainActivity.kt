package com.androboy.androidarch.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.androboy.androidarch.R

/**
 * This is main activity
 * */
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
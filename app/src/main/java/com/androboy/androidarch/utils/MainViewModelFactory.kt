package com.androboy.androidarch.utils

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.androboy.androidarch.repository.UserRepository
import com.androboy.androidarch.ui.viewmodel.MainViewModel

class MainViewModelFactory(private val context: Context,private val userRepository: UserRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(context,userRepository) as T
    }
}

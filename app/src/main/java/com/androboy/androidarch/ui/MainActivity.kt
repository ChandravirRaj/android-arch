package com.androboy.androidarch.ui

import android.widget.Toast
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.androboy.androidarch.BaseActivity
import com.androboy.androidarch.databinding.ActivityMainBinding
import com.androboy.androidarch.db.AppDatabase
import com.androboy.androidarch.db.entities.User
import com.androboy.androidarch.repository.UserRepository
import com.androboy.androidarch.ui.model.Student
import com.androboy.androidarch.ui.viewmodel.MainViewModel
import com.androboy.androidarch.utils.MainViewModelFactory

/**
 * This is main activity
 * */
class MainActivity : BaseActivity() {
    lateinit var viewModel: MainViewModel
    lateinit var ui: ActivityMainBinding
    var count: Int = 0;
    lateinit var userRepository: UserRepository
    override fun layoutRes(): ViewBinding {
        installSplashScreen()
        return ActivityMainBinding.inflate(layoutInflater)
    }

    override fun initView() {

        val dao = AppDatabase.getDbInstance(application).userDao()
        userRepository = UserRepository(dao)

        viewModel =
            ViewModelProvider(
                this,
                MainViewModelFactory(application, userRepository)
            )[MainViewModel::class.java]

        ui = binding as ActivityMainBinding

        setObserver()
        setListeners()
        setData()
    }

    private fun setData() {
        ui.tvQuote.text = viewModel.getQuote().quote
        ui.tvAuthor.text = viewModel.getQuote().author
    }

    private fun setListeners() {
        ui.tvNext.setOnClickListener {
            ui.tvQuote.text = viewModel.nextQuote().quote
            ui.tvAuthor.text = viewModel.nextQuote().author
        }

        ui.tvPrev.setOnClickListener {
            ui.tvQuote.text = viewModel.prevQuote().quote
            ui.tvAuthor.text = viewModel.prevQuote().author
        }

        ui.tvAddStudent.setOnClickListener {
            ++count
//            val student = Student("Kamal Singh $count",count*10)
//            viewModel.updateStudentList(student)
            val user = User("Kamal$count", "Singh", 25, "kamal$count@yopmail.com")
            viewModel.insertUser(user)
        }
    }

    private fun setObserver() {
        viewModel.studentLiveData.observe(this, Observer {
            Toast.makeText(this, "List Count is ${it.size}", Toast.LENGTH_SHORT).show()
        })

        viewModel.getUsers().observe(this, Observer {
            Toast.makeText(this, " User List Count is ${it.size}", Toast.LENGTH_SHORT).show()
        })
    }
}
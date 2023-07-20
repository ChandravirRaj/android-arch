package com.androboy.androidarch

import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.airbnb.lottie.LottieAnimationView
import com.androboy.androidarch.databinding.CustomSnackbarViewBinding
import com.androboy.androidarch.utils.AppUtil
import com.google.android.material.snackbar.Snackbar
import java.util.*


/**
 * BaseActivity : this one is base activity for all sub activities which are using in this project
 * it extends AppCompatActivity()
 * it has all commonly used methods and variables
 *
 * */
abstract class BaseActivity : AppCompatActivity(){

    // Root Progress bar
    private var progressDialog: ProgressDialog? = null

    // Lotti animation for all api
    private var lottieAnimationView: LottieAnimationView? = null

    // View Binding for root UI
    lateinit var binding: ViewBinding

    /**
     * Layout resource for View Binding for Activity
     */
    abstract fun layoutRes(): ViewBinding

    abstract fun initView()

    /**
     * Set All UI resource in Window
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = layoutRes()
        setContentView(binding.root)
        initView()
    }

    /**
     * Set status bar color
     */
    open fun setStatusBarColor(color: Int) {
        val window = window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.statusBarColor = color
    }


    open fun showProgressBar() {
        showProgressBar(true)
    }

    /**
     * Show progress bar
     */
    open fun showProgressBar(isCancel: Boolean) {
        hideProgressBar()
        if (!this@BaseActivity.isFinishing) {
            progressDialog = ProgressDialog.show(this@BaseActivity, "", "", true)

            if (progressDialog != null) {
                progressDialog?.setContentView(R.layout.activity_loader)
                lottieAnimationView = findViewById(R.id.ivLoader)
                lottieAnimationView?.setAnimation("egg_loader.json")
                lottieAnimationView?.playAnimation()
                Objects.requireNonNull(progressDialog?.window)
                    ?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            }
        }
    }

    /**
     * Hide progress bar
     */
    open fun hideProgressBar() {
        if (!this@BaseActivity.isFinishing) {
            if (progressDialog != null) {
                progressDialog?.dismiss()
                progressDialog = null
            }
        }
    }


    /**
     * Launch activity using class concept
     *
     * @param classType launching class
     */
    open fun launchActivity(classType: Class<out BaseActivity>) {
        startActivity(Intent(this, classType))
    }


    open fun launchActivity(
        classType: Class<out BaseActivity>,
        view: View
    ) {
        AppUtil.preventTwoClick(view)
        startActivity(Intent(this, classType))
    }


    open fun launchActivityForResult(
        classType: Class<out BaseActivity>,
        bundle: Bundle,
        requestCode: Int
    ) {
        val intent = Intent(this, classType)
        if (bundle != null) {
            intent.putExtras(bundle)
        }
        startActivityForResult(intent, requestCode)
    }

    open fun launchActivityForResult(
        classType: Class<out BaseActivity>,
        requestCode: Int,
        view: View?
    ) {
        AppUtil.preventTwoClick(view)
        val intent = Intent(this, classType)
        startActivityForResult(intent, requestCode)
    }

    open fun launchActivityForResult(
        classType: Class<out BaseActivity>,
        requestCode: Int,
        bundle: Bundle,
        view: View?
    ) {
        AppUtil.preventTwoClick(view)
        val intent = Intent(this, classType)
        if (bundle != null) {
            intent.putExtras(bundle)
        }
        startActivityForResult(intent, requestCode)
    }

    /**
     * Launch activity using class concept and pass data using bundle
     *
     * @param classType launching class
     */
    open fun launchActivity(
        classType: Class<out BaseActivity>,
        bundle: Bundle
    ) {
        val intent = Intent(this, classType)
        if (bundle != null) {
            intent.putExtras(bundle)
        }
        startActivity(intent)
    }

    open fun launchActivityForResult(
        classType: Class<out BaseActivity>,
        requestCode: Int
    ) {
        val intent = Intent(this, classType)
        startActivityForResult(intent, requestCode)
    }

    /**
     * Launch activity using class concept and pass data using bundle
     *
     * @param classType launching class
     */
    open fun launchActivity(
        bundle: Bundle,
        classType: Class<out BaseActivity>
    ) {
        val intent = Intent(this, classType)
        if (bundle != null) {
            intent.putExtras(bundle)
        }
        startActivity(intent)
    }

    /**
     * Launch activity using class concept and pass data using bundle
     *
     * @param classType launching class
     */
    open fun launchActivity(
        classType: Class<out BaseActivity>,
        bundle: Bundle,
        view: View
    ) {
        AppUtil.preventTwoClick(view)
        val intent = Intent(this, classType)
        if (bundle != null) {
            intent.putExtras(bundle)
        }

        startActivity(intent)
    }


    fun launchActivityWithClearTask(
        classType: Class<out BaseActivity?>?
    ) {
        val intent = Intent(this, classType)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
    }


    /**
     * Hide soft keyboard
     */
    open fun hideSoftKeyBoard() {
        val imm =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if (currentFocus != null) {
            imm.hideSoftInputFromWindow(
                currentFocus?.windowToken,
                InputMethodManager.RESULT_UNCHANGED_SHOWN
            )
        }
    }


    open fun checkSoftKeyBoardStatus(isOpen: Boolean) {}




    open fun showLoader() {
        lottieAnimationView?.setAnimation("egg_loader.json")
        lottieAnimationView?.playAnimation()
    }

    open fun hideLoader() {
        lottieAnimationView?.setAnimation("egg_loader.json")
        lottieAnimationView?.pauseAnimation()
    }



    /**
     * show Snack bar with msg
     */
    var snack: Snackbar? = null


    fun showSnackBar(str: String) {
        if (snack != null) {
            snack?.dismiss()
        }
        try {
            snack = Snackbar.make(findViewById(android.R.id.content), str, Snackbar.LENGTH_LONG)
            val view = snack?.view
            val params = view?.layoutParams as FrameLayout.LayoutParams
            params.gravity = Gravity.TOP
            view.layoutParams = params
            view.setBackgroundColor(AppUtil.getColor(R.color.black))
            snack?.show()
        } catch (e: Exception) {

        }
    }

    fun showCustomSnackBar(message: String,length:Int) {
        if (snack != null) {
            snack?.dismiss()
        }
        try {
            snack = Snackbar.make(findViewById(android.R.id.content), "",length )
            val binding = CustomSnackbarViewBinding.inflate(layoutInflater, null, false)
            val view = snack?.view
            view?.setBackgroundColor(Color.TRANSPARENT)
            val params = view?.layoutParams as FrameLayout.LayoutParams
            params.gravity = Gravity.TOP
            view.layoutParams = params

            val snackLayout = snack?.view as Snackbar.SnackbarLayout
            snackLayout.setPadding(0, 0, 0, 0)
            binding.tvMessage.text = message
            snackLayout.addView(binding.root, 0)
            snack?.show()
        } catch (e: Exception) {

        }
    }


    fun showCustomSnackBarLong(message: String) {
        if (snack != null) {
            snack?.dismiss()
        }
        try {
            snack = Snackbar.make(findViewById(android.R.id.content), "", 15000)

            val binding = CustomSnackbarViewBinding.inflate(layoutInflater, null, false)
            val view = snack?.view
            view?.setBackgroundColor(Color.TRANSPARENT)
            val params = view?.layoutParams as FrameLayout.LayoutParams
            params.gravity = Gravity.TOP
            view.layoutParams = params

            val snackLayout = snack?.view as Snackbar.SnackbarLayout
            snackLayout.setPadding(0, 0, 0, 0)
            binding.tvMessage.text = message


            binding.cardView.setOnClickListener {
                snack?.dismiss()
                snack = null
            }

            snackLayout.addView(binding.root, 0)
            snack?.show()
        } catch (e: Exception) {

        }
    }

    /**
     * activity result callback from intent
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_CANCELED) {
            return
        }
    }

}
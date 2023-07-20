package com.androboy.androidarch

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.androboy.androidarch.BaseActivity
import com.androboy.androidarch.utils.AppUtil

/**
 * BaseFragment : this one is base Fragment for all sub fragments which are using in this project
 * it extends Fragment()
 * it has all commonly used methods and variables
 *
 * */
abstract class BaseFragment : Fragment() {

    // Fragment View Binding for UI resource
    lateinit var binding: ViewBinding

    // all activity instance for fragment
    open lateinit var activity: BaseActivity

    // Fragment layout resource inflater
    protected abstract fun layoutRes(inflater: LayoutInflater, container: ViewGroup?): ViewBinding

    // Return fragment UI resource
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = layoutRes(inflater, container)
        return binding.root
    }


    /**
     * Set Context instance to current activity
     */
    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity = context as BaseActivity
    }

    override fun onDetach() {
        super.onDetach()
    }

    /**
     * Launch activity from fragment  with request code and bundle data
     */
    fun launchActivity(
        classType: Class<out BaseActivity?>?,
        bundle: Bundle?,
        requestCode: Int
    ) {
        val intent = Intent(activity, classType)
        if (bundle != null) {
            intent.putExtras(bundle)
        }
        startActivityForResult(intent, requestCode)
    }

    /**
     * Launch activity from fragment  with request code
     */
    fun launchActivity(
        classType: Class<out BaseActivity?>?,
        requestCode: Int
    ) {
        val intent = Intent(activity, classType)
        startActivityForResult(intent, requestCode)
    }

    /**
     * Launch activity from fragment  with view type
     */
    fun launchActivity(
        classType: Class<out BaseActivity?>?,
        view: View?
    ) {
        AppUtil.preventTwoClick(view)
        startActivity(Intent(activity, classType))
    }

    /**
     * Get callback when click on fragment tab
     */
    open fun onTabClick(pos: Int) {

    }


}
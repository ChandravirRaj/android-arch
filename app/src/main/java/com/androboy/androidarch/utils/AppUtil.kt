package com.androboy.androidarch.utils

import android.Manifest
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.text.TextUtils
import android.util.Patterns
import android.view.View
import android.webkit.MimeTypeMap
import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import com.androboy.androidarch.ArchApp
import com.androboy.androidarch.R
import com.androboy.androidarch.BaseActivity
import java.io.File
import java.util.Locale
import java.util.Objects
import java.util.TimeZone
import java.util.regex.Matcher
import java.util.regex.Pattern

// Set all utils method for high level calculation
// Network check
// Media Data
// Mime Type
// Validation

object AppUtil {
    fun isConnection(): Boolean {
        return isConnection(true)
    }

    fun isConnection(notShowMsg: Boolean): Boolean {
        val connectivityManager = ArchApp.INSTANCE
            .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo =
            Objects.requireNonNull(connectivityManager)
                .activeNetworkInfo
        val isNetwork =
            activeNetworkInfo != null && activeNetworkInfo.isConnected
        if (!isNetwork && !notShowMsg) {
            showToast(
                ArchApp.INSTANCE.resources
                    .getString(R.string.msg_network_connection)
            )
        }
        return isNetwork
    }


    fun getMimeType(url: String?): String {
        val path = Uri.fromFile(File(url))
        val type_map = MimeTypeMap.getSingleton()
        var extension = MimeTypeMap.getFileExtensionFromUrl(path.toString())
        extension = extension.toLowerCase(Locale.ROOT)
        if (extension.contains(".")) {
            extension = extension.substring(extension.lastIndexOf("."))
        }
        return type_map.getMimeTypeFromExtension(extension).toString()
    }

    fun getColor(color: Int): Int {
        return ContextCompat.getColor(ArchApp.INSTANCE, color)
    }


    fun showToast(msg: String?) {
        if (!TextUtils.isEmpty(msg)) {
            Toast.makeText(ArchApp.INSTANCE, msg, Toast.LENGTH_LONG)
                .show()
        }
    }

    val deviceId: String
        get() = Settings.Secure.getString(
            ArchApp.INSTANCE.contentResolver,
            Settings.Secure.ANDROID_ID
        )


    fun setBgView(layout: View, drawableId: Int) {
        layout.background = ContextCompat.getDrawable(
            ArchApp.INSTANCE,
            drawableId
        )
    }

    /**
     * Get user data from constant if not present then find from preference
     *
     * @return user object
     */


    fun isEmailValid(email: String?): Boolean {
        val regex = "^(\\w[.|-]?)*\\w+[@](\\w[.|-]?)*\\w+[.][a-z]{2,4}$"
        val regex1 = "[A-Z0-9a-z._%+-]+@[A-Za-z0-9-]+\\\\.[A-Za-z]{2,6}" //used in ios

        val pattern: Pattern = Pattern.compile(regex)
        val matcher: Matcher = pattern.matcher(email)
        return !TextUtils.isEmpty(email) && matcher.matches()
    }


    fun isValidEmailPattern(email: String?): Boolean {
        var isSingleDomain: Boolean = false
        if (email?.contains("@")!!) {
            val count = countOccurrences(email.substring(email.lastIndexOf("@"), email.length), '.')
            if (count == 1 || count == 2)
                isSingleDomain = true
        }

        val regex =
            "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+(?:\\.[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+)*@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$"

        val pattern: Pattern = Pattern.compile(regex)
        val matcher: Matcher = pattern.matcher(email)
        return !TextUtils.isEmpty(email) && matcher.matches() && isSingleDomain
    }

    private fun countOccurrences(s: String, ch: Char): Int {
        return s.filter { it == ch }.count()
    }




    fun preventTwoClick(view: View?) {
        if (view != null) {
            view.isEnabled = false
            view.postDelayed(Runnable { view.isEnabled = true }, 800)
        }
    }

    fun onOpenShareIntent(message: String?, activity: BaseActivity) {
        if (!TextUtils.isEmpty(message)) {
            val sendIntent = Intent()
            sendIntent.action = Intent.ACTION_SEND
            sendIntent.putExtra(Intent.EXTRA_TEXT, message)
            sendIntent.type = "text/plain"
            activity.startActivity(Intent.createChooser(sendIntent, "Tread Map"))
        } else {
            showToast("Share url can be empty")
        }
    }

    fun openWebSite(activity: BaseActivity, str: String?) {
        try {
            if (!TextUtils.isEmpty(str)) {
                val myIntent = Intent(Intent.ACTION_VIEW, Uri.parse(str))
                activity.startActivity(myIntent)
            }
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(
                activity, "No application can handle this request."
                        + " Please install a web browser", Toast.LENGTH_LONG
            ).show()
            e.printStackTrace()
        }
    }

    fun getFileType(url: String?): String {
        val path = Uri.fromFile(File(url))
        var extension =
            MimeTypeMap.getFileExtensionFromUrl(path.toString())
        extension = extension.toLowerCase()
        if (extension.contains(".")) {
            extension = extension.substring(extension.lastIndexOf("."))
        }
        return extension
    }


    fun getValue(month: Int): String {
        return if (month > 9) {
            month.toString()
        } else "0$month"
    }


    fun getFullName(firstName: String?, lastName: String?): String {
        var name1 = "NA"
        var name2: String? = ""
        if (!TextUtils.isEmpty(firstName)) {
            name1 = "$firstName "
        }
        if (!TextUtils.isEmpty(lastName)) {
            name2 = lastName
        }
        return name1 + "" + name2
    }


    /* fun getMediaMultiPart(
         profileImagePath: String,
         key: String
     ): MultipartBody.Part {
         val file = File(profileImagePath)
         val requestFileProfilePic =
             RequestBody.create(getMimeType(profileImagePath).toMediaTypeOrNull(), file)
         return MultipartBody.Part.createFormData(key, file.name, requestFileProfilePic)
     }*/


    fun getDeviceOS(): String {
        var codeName = "UNKNOWN"
        try {
            val fields = Build.VERSION_CODES::class.java.fields

            fields.filter { it.getInt(Build.VERSION_CODES::class) == Build.VERSION.SDK_INT }
                .forEach { codeName = it.name }
        } catch (e: Exception) {

        }
        return codeName
    }



    fun setBgTint(view: View?, color: Int) {
        view?.let {
            ViewCompat.setBackgroundTintList(
                it,
                ColorStateList.valueOf(color)
            )
        }
    }

    fun setTint(view: AppCompatImageView?, color: Int) {
        view?.setColorFilter(color)
    }

    fun getFileDir(): String? {
        return ArchApp.INSTANCE.getExternalFilesDir("image")?.absolutePath
    }

    fun getTimeZone(): String {
        return try {
            val tz = TimeZone.getDefault()
            if (tz != null && !TextUtils.isEmpty(tz.id)) {
                tz.id
            } else {
                ""
            }
        } catch (e: java.lang.Exception) {
            ""
        }
    }

    fun getProfile(id: String): String? {
        return "https://graph.facebook.com/$id/picture?type=large"
    }

    fun getDeviceId1(): String? {
        return Settings.Secure.getString(
            ArchApp.INSTANCE.contentResolver,
            Settings.Secure.ANDROID_ID
        )
    }


    fun isValidPhoneNumber(phoneNumber: CharSequence?): Boolean {
        return if (!TextUtils.isEmpty(phoneNumber)) {
            Patterns.PHONE.matcher(phoneNumber).matches()
        } else false
    }


    fun isForegroundLocationPermission(activity: BaseActivity?): Boolean {
        return ActivityCompat.checkSelfPermission(
            activity!!,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    fun isBackgroundLocationPermission(activity: BaseActivity?): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            ActivityCompat.checkSelfPermission(
                activity!!,
                Manifest.permission.ACCESS_BACKGROUND_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        } else true
    }
/*
    fun isLocationService(): Boolean {
        val c1 = SmartLocation.with(App.INSTANCE).location().state().locationServicesEnabled()
        val c2 = SmartLocation.with(App.INSTANCE).location().state().isAnyProviderAvailable
        return c1 && c2
    }

    fun isGpsEnable(): Boolean {
        return SmartLocation.with(App.INSTANCE).location().state().isGpsAvailable
    }*/

    fun formatSecondsToTime(inSecond: Float): String {
        val hours = getHour(inSecond)
        val minutes = getMinutes(inSecond)
        val seconds = getSecond(inSecond)
        return "${twoDigitString(hours.toLong())} : ${twoDigitString(minutes.toLong())} : ${
            twoDigitString(
                seconds.toLong()
            )
        }"
    }

    fun getSecond(inSecond: Float): Int {
        return inSecond.toInt() % 60
    }

    fun getMinutes(inSecond: Float): Int {
        return (inSecond / 60 % 60).toInt()
    }

    fun getHour(inSecond: Float): Int {
        return (inSecond / (60 * 60) % 24).toInt()
    }


    fun twoDigitString(number: Long): String? {
        if (number == 0L) {
            return "00"
        }
        return if (number / 10 == 0L) {
            "0$number"
        } else number.toString()
    }


    fun getFullNameWithoutSpace(firstName: String?, lastName: String?): String {
        var name1 = "NA"
        var name2: String?
        if (!TextUtils.isEmpty(firstName)) {
            name1 = "$firstName"
        }
        if (!TextUtils.isEmpty(lastName)) {
            name2 = lastName
        } else {
            name2 = " "
        }
        return (name1 + name2).toLowerCase(Locale.ROOT)
    }


    fun getDrawable(drawable: Int): Drawable? {
        return ContextCompat.getDrawable(ArchApp.INSTANCE, drawable)
    }






}
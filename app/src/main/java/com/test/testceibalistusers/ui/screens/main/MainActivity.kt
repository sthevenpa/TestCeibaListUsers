package com.test.testceibalistusers.ui.screens.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.test.testceibalistusers.R
import com.test.testceibalistusers.ui.screens.user.view.ListUserActivity
import com.test.testceibalistusers.utils.AppUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        validateInternet()
    }

    private fun validateInternet(){

        val internetConnection = AppUtils.internetAvailable(this)
        if (internetConnection){
            loadControls()
        }else{
            MaterialAlertDialogBuilder(this)
                .setTitle(resources.getString(R.string.title_dialog_internet_off))
                .setMessage(resources.getString(R.string.msg_internet_off))
                .setNegativeButton(resources.getString(R.string.button_text_cancel)) { _,_ ->
                    finishAffinity()
                }
                .setPositiveButton(resources.getString(R.string.button_text_accept)) { _,_ ->
                    validateInternet()
                }
                .show()
        }

    }
    private fun loadControls(){
        startActivity(
            Intent().setClass(
                this@MainActivity,
                ListUserActivity::class.java
            )
        )
    }
}
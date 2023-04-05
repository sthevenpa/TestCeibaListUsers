package com.test.testceibalistusers.utils

import android.annotation.SuppressLint
import android.app.Activity
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.test.testceibalistusers.R
import kotlin.system.exitProcess

object MessageManager {

    private var materialDialog: MaterialDialog? = null

    @SuppressLint("InflateParams")
    fun progressVisible(activity: Activity) {
        try {
            if (!activity.isFinishing) {
                materialDialog = MaterialDialog(activity)
                materialDialog?.show {
                    cornerRadius(res = R.dimen.card_corner_radius)
                    customView(R.layout.progress)
                }
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }

    fun progressGone(activity: Activity) {
        try {
            if (!activity.isFinishing) {
                if (materialDialog != null)
                    materialDialog?.dismiss()
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
        }

    }
}
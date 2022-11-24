package com.ssafy.silencelake.activity.admin

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi

private const val TAG = "AdminBroadCastReceiver_싸피"
@RequiresApi(api = Build.VERSION_CODES.O)
class AdminBroadCastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        Log.d(TAG, "onReceive: ")
        val it = Intent(context, AdminActivity::class.java)
        it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK + Intent.FLAG_ACTIVITY_SINGLE_TOP)
        context.startActivity(it)
    }
}
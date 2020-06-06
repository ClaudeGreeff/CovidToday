package com.app.covid.repository.db.preference

import android.content.Context
import android.content.SharedPreferences
import java.util.*

object CachePreference {
    private const val SHARED_PREFERENCE_NAME = "cache_preference"

    private fun getSharedPreferences(context: Context)
            : SharedPreferences {
        return context.getSharedPreferences(SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE)
    }
//
//    fun setChatMuteEnabled(context: Context, chatId: Long, isMuted: Boolean){
//        val pref = getSharedPreferences(context)
//        val editor = pref.edit()
//        editor.putBoolean("chat_mute_$chatId", isMuted)
//        editor.apply()
//    }
//
//    fun isChatMuted(context: Context, chatId: Long): Boolean {
//        return  getSharedPreferences(context).getBoolean("chat_mute_$chatId",false)
//    }

    fun setMyChannelsLastRefreshed(context: Context, channelId: Long, date: Date){
        val pref = getSharedPreferences(context)
        val editor = pref.edit()
        editor.putLong("my_channel_last_refreshed", date.time)
        editor.apply()
    }

    fun getMyChannelsLastRefreshed(context: Context): Date {
        val time =  getSharedPreferences(context).getLong("my_channel_last_refreshed",0L)
        return Date(time)
    }

    fun resetLastWorkspaceUserUpdated(context: Context){
        val pref = getSharedPreferences(context)
        val editor = pref.edit()
        pref.all.keys.forEach {
            if (it.contains("last_user_updated_at_")){
                editor.putLong(it, 0L)
            }
        }
        editor.apply()
    }

    fun setLastUserUpdateAt(context: Context, workspaceUuid: String?, date: Date?){
        val pref = getSharedPreferences(context)
        val editor = pref.edit()
        editor.putLong("last_user_updated_at_$workspaceUuid", date?.time?:0L)
        editor.apply()
    }

    fun getLastUserUpdateAt(context: Context, workspaceUuid: String?): Date? {
        val time =  getSharedPreferences(context).getLong("last_user_updated_at_$workspaceUuid",0L)
        return if (time == 0L){
            null
        } else {
            Date(time)
        }
    }

    fun setLastChannelDbVersion(context: Context, version: Int?){
        val pref = getSharedPreferences(context)
        val editor = pref.edit()
        editor.putInt("last_channel_db_version", version?:-1)
        editor.apply()
    }

    fun getLastChannelDbVersion(context: Context): Int? {
        return getSharedPreferences(context).getInt("last_channel_db_version",-1)
    }
}
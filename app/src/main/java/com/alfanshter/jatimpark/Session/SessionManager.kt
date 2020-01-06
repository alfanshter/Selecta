package com.alfanshter.jatimpark.Session

import android.content.Context
import android.content.SharedPreferences

class SessionManager(val context: Context) {
val privateMode = 0
val privateName ="login"
var Pref :SharedPreferences?=context.getSharedPreferences(privateName,privateMode)
    var editor : SharedPreferences.Editor?=Pref?.edit()

private val islogin = "login"
fun setLogin(check: Boolean){
    editor?.putBoolean(islogin,check)
    editor?.commit()
}

    fun getLogin():Boolean?
    {
        return Pref?.getBoolean(islogin,false)
    }

    private val iduser = "iduser"
    fun setIduser(check:String)
    {
        editor?.putString(iduser,check)
        editor?.commit()
    }

    fun getIduser():String?
    {
        return Pref?.getString(iduser,"")
    }

}
package com.lang.util

import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity

/**
 * Created by lang on 2018/4/8.
 */

fun AppCompatActivity.userName(): String {
    val userName by Preference("user_name", "请登录")
    return userName
}

fun AppCompatActivity.userPwd(): String {
    val userPwd by Preference("user_password", "null")
    return userPwd
}

fun AppCompatActivity.isLogin(): Boolean {
    val isLogin by Preference("is_login", false)
    return isLogin
}


fun AppCompatActivity.setUserName(name: String) {
    var userName by Preference("user_name", "请登录")
    userName = name
}

fun AppCompatActivity.setUserPwd(pwd: String) {
    var userPwd by Preference("user_password", "null")
    userPwd = pwd
}

fun AppCompatActivity.setIsLogin(login: Boolean) {
    var isLogin by Preference("is_login", false)
    isLogin = login
}

fun Fragment.userName(): String {
    val userName by Preference("user_name", "请登录")
    return userName
}

fun Fragment.userPwd(): String {
    val userPwd by Preference("user_password", "null")
    return userPwd
}

fun Fragment.isLogin(): Boolean {
    val isLogin by Preference("is_login", false)
    return isLogin
}


fun Fragment.setUserName(name: String) {
    var userName by Preference("user_name", "请登录")
    userName = name
}

fun Fragment.setUserPwd(pwd: String) {
    var userPwd by Preference("user_password", "null")
    userPwd = pwd
}

fun Fragment.setIsLogin(login: Boolean) {
    var isLogin by Preference("is_login", false)
    isLogin = login
}


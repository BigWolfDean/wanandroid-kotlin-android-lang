package com.lang.util

import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.widget.TextView

/**
 * Created by lang on 2018/3/14.
 */

fun Fragment.initToolbar(toolbar: Toolbar) {
    val activity = activity as AppCompatActivity
    activity.setSupportActionBar(toolbar)
    activity.supportActionBar!!.title = ""
}

fun Fragment.setToolbarTitle(textView: TextView, string: String) {
    textView.text = string
}

fun AppCompatActivity.initToolbar(toolbar: Toolbar) {

    setSupportActionBar(toolbar)
    supportActionBar!!.title = ""
}

fun AppCompatActivity.setToolbarTitle(textView: TextView, string: String) {

    textView.text = string
}
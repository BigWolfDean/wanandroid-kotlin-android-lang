package com.lang.ui.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.CardView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.lang.R
import com.lang.ui.activity.LoginActivity
import com.lang.ui.activity.personal.FavoriteActivity
import com.lang.util.*
import kotlinx.android.synthetic.main.include_toolbar.*
import org.jetbrains.anko.noButton
import org.jetbrains.anko.support.v4.alert
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.toast
import org.jetbrains.anko.yesButton

class PersonalFragment : Fragment(), View.OnClickListener {

    private lateinit var loginLayout: LinearLayout

    private lateinit var quitView: CardView

    private lateinit var userNameTV: TextView

    private lateinit var favoriteView: CardView


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_personal, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        initToolbar(toolbar)
        setToolbarTitle(toolbar_title, "个人")

        loginLayout = activity!!.findViewById(R.id.setting_login_layout)
        userNameTV = activity!!.findViewById(R.id.setting_login_tv)
        favoriteView = activity!!.findViewById(R.id.setting_favorite)
        quitView = activity!!.findViewById(R.id.setting_quit)


        loginLayout.setOnClickListener(this)
        favoriteView.setOnClickListener(this)
        quitView.setOnClickListener(this)

        userNameTV.text = userName()

    }


    override fun onClick(v: View?) {
        when (v!!.id) {

            R.id.setting_login_layout -> {
                if (!isLogin())
                    startActivity<LoginActivity>()
            }

            R.id.setting_favorite -> {
                startActivity<FavoriteActivity>()
            }

            R.id.setting_quit -> {
                if (!isLogin()) {
                    toast("未登录")
                    return
                }
                alert("确定要退出该账号吗？") {
                    title = "提示"
                    yesButton {
                        setIsLogin(false)
                        setUserName("请登录")
                        userNameTV.text = userName()
                    }

                    noButton {

                    }

                }.show()

            }

        }
    }


    override fun onResume() {
        userNameTV.text = userName()
        super.onResume()
    }


}
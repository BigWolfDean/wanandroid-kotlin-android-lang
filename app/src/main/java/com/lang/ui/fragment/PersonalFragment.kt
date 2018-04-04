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
import com.lang.util.Preference
import com.lang.util.initToolbar
import com.lang.util.setToolbarTitle
import kotlinx.android.synthetic.main.include_toolbar.*
import org.jetbrains.anko.support.v4.startActivity

class PersonalFragment : Fragment(), View.OnClickListener {

    private lateinit var loginLayout: LinearLayout

    private lateinit var userNameTV: TextView

    private lateinit var favoriteView: CardView

    private var userName by Preference("user_name", "请登录")

    private var isLogin by Preference("is_login", false)


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


        loginLayout.setOnClickListener(this)
        favoriteView.setOnClickListener(this)

        userNameTV.text = userName
    }


    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.setting_login_layout -> {
                startActivity<LoginActivity>()
            }
            R.id.setting_favorite -> {
                startActivity<FavoriteActivity>()
            }
        }
    }


    override fun onResume() {
        userNameTV.text = userName
        super.onResume()
    }


}
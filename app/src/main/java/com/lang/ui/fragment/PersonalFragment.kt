package com.lang.ui.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lang.R
import com.lang.util.initToolbar
import com.lang.util.setToolbarTitle
import kotlinx.android.synthetic.main.include_toolbar.*

class PersonalFragment : Fragment() {

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
    }

}
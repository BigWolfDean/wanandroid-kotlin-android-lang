package com.lang.ui.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.ashokvarma.bottomnavigation.BottomNavigationBar
import com.ashokvarma.bottomnavigation.BottomNavigationItem
import com.lang.R
import com.lang.ui.fragment.AllFragment
import com.lang.ui.fragment.IndexFragment
import com.lang.ui.fragment.PersonalFragment

class MainActivity : AppCompatActivity(), BottomNavigationBar.OnTabSelectedListener {


    private lateinit var bottomBar: BottomNavigationBar
    private lateinit var indexFragment: IndexFragment
    private lateinit var allFragment: AllFragment
    private lateinit var personalFragment: PersonalFragment

    private lateinit var onFirstTabClick: OnFirstTabClickListener


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
        initBar()
        initFragment()
        replaceFragments(0)
    }

    private fun initFragment() {
        indexFragment = IndexFragment()
        allFragment = AllFragment()
        personalFragment = PersonalFragment()

        supportFragmentManager.beginTransaction().apply {
            add(R.id.fm_main, indexFragment)
            add(R.id.fm_main, allFragment)
            add(R.id.fm_main, personalFragment)
        }.commitAllowingStateLoss()
    }

    private fun initBar() {
        bottomBar.addItem(BottomNavigationItem(R.drawable.ic_home, "首页").setActiveColorResource(R.color.colorPrimary))
                .addItem(BottomNavigationItem(R.drawable.ic_all, "全部").setActiveColorResource(R.color.colorPrimary))
                .addItem(BottomNavigationItem(R.drawable.ic_account, "个人").setActiveColorResource(R.color.colorPrimary))
                .initialise()
        bottomBar.setTabSelectedListener(this)
    }

    private fun initView() {
        bottomBar = findViewById(R.id.bottom_navigation_bar)
    }

    private fun replaceFragments(position: Int) {
        supportFragmentManager.beginTransaction().apply {
            when (position) {
                0 -> {
                    show(indexFragment)
                    hide(allFragment)
                    hide(personalFragment)
                }

                1 -> {
                    hide(indexFragment)
                    show(allFragment)
                    hide(personalFragment)
                }

                2 -> {
                    hide(indexFragment)
                    hide(allFragment)
                    show(personalFragment)
                }
            }
        }.commitNowAllowingStateLoss()
    }

    override fun onTabSelected(position: Int) {
        replaceFragments(position)
    }

    override fun onTabReselected(position: Int) {
        when (position) {
            0 -> {
                onFirstTabClick.onFirstTabClick()
            }
            1 -> {

            }
            2 -> {

            }
        }
    }

    override fun onTabUnselected(position: Int) {

    }

    fun setOnFirstTabClickListener(onFirstTabClick: OnFirstTabClickListener) {
        this.onFirstTabClick = onFirstTabClick
    }

    interface OnFirstTabClickListener {
        fun onFirstTabClick()
    }
}

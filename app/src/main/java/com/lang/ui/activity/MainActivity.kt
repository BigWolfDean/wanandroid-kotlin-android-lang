package com.lang.ui.activity

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import com.ashokvarma.bottomnavigation.BottomNavigationBar
import com.ashokvarma.bottomnavigation.BottomNavigationItem
import com.lang.R
import com.lang.ui.fragment.AllFragment
import com.lang.ui.fragment.IndexFragment
import com.lang.ui.fragment.PersonalFragment
import com.ohmerhe.kolley.request.Http

class MainActivity : AppCompatActivity(), BottomNavigationBar.OnTabSelectedListener {


    private lateinit var bottomBar: BottomNavigationBar
    private lateinit var indexFragment: IndexFragment
    private lateinit var allFragment: AllFragment
    private lateinit var personalFragment: PersonalFragment

    private lateinit var onFirstTabClick: OnFirstTabClickListener

    private var mCurrentFragment: Fragment? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Http.init(this)
        initView()
        initBar()
        initFragment()
        replaceFragments(0)
    }

    private fun initFragment() {
        indexFragment = IndexFragment()
        allFragment = AllFragment()
        personalFragment = PersonalFragment()

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
        when (position) {
            0 -> {
                showAndHide(R.id.fm_main, indexFragment.javaClass)
            }

            1 -> {
                showAndHide(R.id.fm_main, allFragment.javaClass)
            }

            2 -> {
                showAndHide(R.id.fm_main, personalFragment.javaClass)
            }
        }
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


    private fun showAndHide(contentId: Int, clazz: Class<out Fragment>) {
        //判断当前的fragment和需要替换的fragment是否是统一一个
        if (mCurrentFragment != null && mCurrentFragment!!.javaClass.simpleName == clazz.simpleName) {
            return
        }

        //判断fragment有没有添加过
        val transaction = supportFragmentManager.beginTransaction()
        //
        val fragment: Fragment
        try {

            val fragmentByTag = supportFragmentManager.findFragmentByTag(clazz.simpleName)

            if (fragmentByTag != null) {
                //显示需要的fragment
                transaction.show(fragmentByTag)
                //隐藏当前的fragment
                transaction.hide(mCurrentFragment)
                //让记录当前的fragment赋值为显示的fragment
                mCurrentFragment = fragmentByTag
            } else {
                //通过无参的 公开的构造函数来创建Fragment实例
                fragment = clazz.newInstance()
                val bundle = Bundle()
                fragment.arguments = bundle
                //当前的Fragment没有添加过 把Fragment添加到manager里面
                transaction.add(contentId, fragment, clazz.simpleName)
                if (mCurrentFragment != null) {
                    //隐藏当前的Fragment
                    transaction.hide(mCurrentFragment)
                }
                //记录当前的Fragment是那个
                mCurrentFragment = fragment
            }
            transaction.commit()
        } catch (e: InstantiationException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        }

    }


}

package com.lang.ui.fragment

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.gson.Gson
import com.lang.R
import com.lang.model.SystemDataModel
import com.lang.util.initToolbar
import com.lang.util.setToolbarTitle
import kotlinx.android.synthetic.main.include_toolbar.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.net.URL

class AllFragment : Fragment() {


    private lateinit var viewPager: ViewPager

    private lateinit var tabLayout: TabLayout

    private lateinit var systemDataModel: SystemDataModel

    private lateinit var tabTitleList: MutableList<String>

    private lateinit var fragmentList: MutableList<Fragment>

    private lateinit var myAdapter: MyAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_all, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initView()
        getSystemData()
        setAdapter()
    }

    private fun setAdapter() {
        myAdapter = MyAdapter(childFragmentManager)
        viewPager.adapter = myAdapter
        tabLayout.tabMode = TabLayout.MODE_SCROLLABLE
        tabLayout.setupWithViewPager(viewPager)
    }

    private fun getSystemData() {
        tabTitleList = ArrayList()
        fragmentList = ArrayList()

        doAsync {
            val result = URL("http://www.wanandroid.com/tree/json").readText()
            uiThread {
                val gson = Gson()
                systemDataModel = gson.fromJson<SystemDataModel>(result, SystemDataModel::class.java)
                for (data in systemDataModel.data) {
                    tabTitleList.add(data.name)
                    fragmentList.add(AllChildFragment())
                }
                myAdapter.notifyDataSetChanged()
            }
        }
    }

    private fun initView() {
        initToolbar(toolbar)
        setToolbarTitle(toolbar_title, "全部体系")

        viewPager = activity!!.findViewById(R.id.all_view_pager)
        tabLayout = activity!!.findViewById(R.id.all_tab_layout)
    }


    inner class MyAdapter constructor(manager: FragmentManager) : FragmentPagerAdapter(manager) {

        override fun getItem(position: Int): Fragment {
            return fragmentList[position]
        }

        override fun getCount(): Int {
            return fragmentList.size
        }


        override fun getPageTitle(position: Int): CharSequence? {
            return tabTitleList[position]
        }

    }
}
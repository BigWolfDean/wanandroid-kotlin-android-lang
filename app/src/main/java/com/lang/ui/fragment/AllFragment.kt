package com.lang.ui.fragment

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.*
import com.google.gson.Gson
import com.lang.R
import com.lang.adapter.base.BaseAdapter
import com.lang.adapter.SecondMenuListAdapter
import com.lang.model.SystemDataModel
import com.lang.ui.activity.SearchActivity
import com.lang.util.initToolbar
import com.lang.util.setToolbarTitle
import kotlinx.android.synthetic.main.include_toolbar.*
import net.lucode.hackware.magicindicator.FragmentContainerHelper
import net.lucode.hackware.magicindicator.MagicIndicator
import net.lucode.hackware.magicindicator.buildins.UIUtil
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ClipPagerTitleView
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.toast
import org.jetbrains.anko.uiThread
import java.net.URL


class AllFragment : Fragment(), BaseAdapter.OnItemClickListener {

    private lateinit var systemDataModel: SystemDataModel

    private lateinit var tabTitleList: MutableList<String>

    private lateinit var tabIndicator: MagicIndicator

    private lateinit var fragmentContainerHelper: FragmentContainerHelper

    private lateinit var myAdapter: MyAdapter

    private lateinit var linearLayoutManager: LinearLayoutManager

    private lateinit var secondMenuListAdapter: SecondMenuListAdapter

    private lateinit var menuListRecyclerView: RecyclerView

    private lateinit var secondMenuList: MutableList<SystemDataModel.DataBean.ChildrenBean>


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_all, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initView()
        initData()
        initIndicator()
        initMenuList()
        getSystemData()
    }

    private fun initMenuList() {

        linearLayoutManager = LinearLayoutManager(activity)
        linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL

        secondMenuListAdapter = SecondMenuListAdapter(activity!!.applicationContext, R.layout.item_second_menu_list)
        secondMenuListAdapter.setOnItemClickListener(this)
        menuListRecyclerView.layoutManager = linearLayoutManager

        menuListRecyclerView.adapter = secondMenuListAdapter


    }

    private fun initData() {
        tabTitleList = ArrayList()
        secondMenuList = ArrayList()
    }

    private fun initIndicator() {
        fragmentContainerHelper = FragmentContainerHelper(tabIndicator)
        fragmentContainerHelper.handlePageSelected(0)
        val commonNavigator = CommonNavigator(activity)
        tabIndicator.navigator = commonNavigator
        fragmentContainerHelper.attachMagicIndicator(tabIndicator)
        myAdapter = MyAdapter()
        commonNavigator.adapter = myAdapter
    }


    private fun getSystemData() {
        doAsync {
            val result = URL("http://www.wanandroid.com/tree/json").readText()
            uiThread {
                val gson = Gson()
                systemDataModel = gson.fromJson<SystemDataModel>(result, SystemDataModel::class.java)
                for (data in systemDataModel.data) {
                    tabTitleList.add(data.name)
                    myAdapter.notifyDataSetChanged()
                }
                switchPages(systemDataModel.data[0].children[0].id)
            }
        }
    }

    private fun initView() {
        initToolbar(toolbar)
        setToolbarTitle(toolbar_title, "全部体系")

        tabIndicator = activity!!.findViewById(R.id.all_tab_indicator)
        menuListRecyclerView = activity!!.findViewById(R.id.all_second_menu_recycler)

    }

    inner class MyAdapter : CommonNavigatorAdapter() {
        override fun getTitleView(p0: Context?, p1: Int): IPagerTitleView {
            val clipPagerTitleView = ClipPagerTitleView(context)
            clipPagerTitleView.text = tabTitleList[p1]
            clipPagerTitleView.textColor = activity!!.resources.getColor(R.color.text_color_black)

            clipPagerTitleView.setOnClickListener {
                fragmentContainerHelper.handlePageSelected(p1, false)
                secondMenuList.clear()
                secondMenuListAdapter.clearAllData()
                for (child in systemDataModel.data[p1].children) {
                    secondMenuList.add(child)
                }
                secondMenuListAdapter.addAllData(secondMenuList)
                menuListRecyclerView.visibility = View.VISIBLE
            }
            return clipPagerTitleView
        }

        override fun getCount(): Int {
            return tabTitleList.size
        }

        override fun getIndicator(p0: Context?): IPagerIndicator {
            val indicator = LinePagerIndicator(context)
            val navigatorHeight = 10
            val borderWidth = UIUtil.dip2px(context, 1.0).toFloat()
            val lineHeight = navigatorHeight - 2 * borderWidth
            indicator.lineHeight = lineHeight
            indicator.roundRadius = lineHeight / 2
            indicator.yOffset = borderWidth
            indicator.setColors(Color.parseColor("#bc2a2a"))
            return indicator
        }


    }

    override fun onItemClick(itemView: View, position: Int) {
        switchPages(secondMenuList[position].id)
        menuListRecyclerView.visibility = View.GONE
    }

    private fun switchPages(id: Int) {
        val fragmentManager = childFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        var fragment: Fragment
        fragment = AllChildFragment()
        val bundle = Bundle()
        bundle.putInt("id", id)
        fragment.arguments = bundle
        fragmentTransaction.replace(R.id.all_fm, fragment)
        fragmentTransaction.commitAllowingStateLoss()
    }


}
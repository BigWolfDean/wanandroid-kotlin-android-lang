package com.lang.ui.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.*
import com.google.gson.Gson
import com.lang.R
import com.lang.adapter.IndexListAdapter
import com.lang.adapter.base.BaseAdapter
import com.lang.banner.GlideImageLoader
import com.lang.model.BannerModel
import com.lang.model.index.DatasBean
import com.lang.model.index.IndexListModel
import com.lang.ui.activity.MainActivity
import com.lang.ui.activity.SearchActivity
import com.lang.ui.activity.WebViewActivity
import com.lang.ui.view.LoadMoreRecyclerView
import com.lang.util.initToolbar
import com.lang.util.setToolbarTitle
import com.youth.banner.Banner
import com.youth.banner.BannerConfig
import kotlinx.android.synthetic.main.fragment_index.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.uiThread
import java.net.URL

class IndexFragment : Fragment(), LoadMoreRecyclerView.LoadMoreListener, MainActivity.OnFirstTabClickListener, BaseAdapter.OnItemClickListener {

    private lateinit var bannerImgList: MutableList<String>

    private lateinit var bannerTitleList: MutableList<String>

    private lateinit var myAdapter: IndexListAdapter

    private lateinit var indexListModel: IndexListModel

    private lateinit var bannerModel: BannerModel

    private lateinit var indexBanner: Banner

    private lateinit var indexRecyclerView: LoadMoreRecyclerView

    private var totalPage: Int = 0

    private var currentPage: Int = 0

    private lateinit var datasList: MutableList<DatasBean>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_index, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        showToolbar()
        initView()
        showBanner()
        setAdapter()
        setTabClickListener()
        setBannerClickListener()
        setHasOptionsMenu(true)
    }

    private fun setBannerClickListener() {
        indexBanner.setOnBannerListener {
            startActivity<WebViewActivity>("url" to bannerModel.data[it].url, "title" to bannerModel.data[it].title)
        }
    }

    private fun initView() {
        indexBanner = activity!!.findViewById(R.id.index_banner)
        indexRecyclerView = activity!!.findViewById(R.id.load_more_recyclerview)
    }

    private fun setAdapter() {
        datasList = ArrayList()
        myAdapter = IndexListAdapter(activity!!.applicationContext, R.layout.item_index_list)
        myAdapter.setOnItemClickListener(this)
        indexRecyclerView.setLinerLayout()
        indexRecyclerView.setOnLoadMoreListener(this)
        indexRecyclerView.setIndexListAdapter(myAdapter)
    }


    private fun showToolbar() {
        initToolbar(index_toolbar)
        setToolbarTitle(index_toolbar_title, resources.getString(R.string.app_name))

    }


    private fun setTabClickListener() {
        (activity as MainActivity).setOnFirstTabClickListener(this)
    }

    private fun showBanner() {
        doAsync {
            val result = URL("http://www.wanandroid.com/banner/json").readText()
            uiThread {
                val gson = Gson()
                bannerModel = gson.fromJson<BannerModel>(result, BannerModel::class.java)
                bannerImgList = ArrayList()
                bannerTitleList = ArrayList()
                for (data in bannerModel.data) run {
                    bannerImgList.add(data.imagePath)
                    bannerTitleList.add(data.title)
                }
                indexBanner.setImageLoader(GlideImageLoader())
                indexBanner.setImages(bannerImgList)
                indexBanner.setBannerTitles(bannerTitleList)
                indexBanner.setDelayTime(10 * 1000)
                indexBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE)
                indexBanner.start()
                loadList(0)
            }
        }
    }


    private fun loadList(page: Int) {
        doAsync {
            val result = URL("http://www.wanandroid.com/article/list/$page/json").readText()
            uiThread {
                val gson = Gson()
                indexListModel = gson.fromJson<IndexListModel>(result, IndexListModel::class.java)
                datasList.addAll(indexListModel.data.datas)
                myAdapter.clearAllData()
                myAdapter.addAllData(datasList)
                totalPage = indexListModel.data.pageCount
                currentPage = indexListModel.data.curPage
                indexRecyclerView.refreshComplete()
                indexRecyclerView.loadMoreComplete()
            }
        }
    }

    override fun onRefresh() {
        datasList.clear()
        myAdapter.clearAllData()
        loadList(0)
    }

    override fun onLoadMore() {
        if (currentPage == totalPage) {
            indexRecyclerView.noMoreData()
            return
        }
        loadList(currentPage)
    }

    override fun onItemClick(itemView: View, position: Int) {
        startActivity<WebViewActivity>("url" to datasList[position].link, "title" to datasList[position].title)
    }

    override fun onFirstTabClick() {
        indexRecyclerView.getRecyclerView().smoothScrollToPosition(0)
        val mLayoutManager = indexRecyclerView.getRecyclerView().layoutManager as LinearLayoutManager
        mLayoutManager.scrollToPositionWithOffset(0, 0)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater!!.inflate(R.menu.menu_index, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            R.id.action_search -> startActivity<SearchActivity>()
        }
        return super.onOptionsItemSelected(item)
    }
}

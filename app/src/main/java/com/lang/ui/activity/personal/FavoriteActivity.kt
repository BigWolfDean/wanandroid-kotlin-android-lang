package com.lang.ui.activity.personal

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.view.View
import com.google.gson.Gson
import com.lang.R
import com.lang.adapter.FavoriteListAdapter
import com.lang.adapter.base.BaseAdapter
import com.lang.model.FavoriteListModel
import com.lang.ui.activity.WebViewActivity
import com.lang.ui.view.LoadMoreRecyclerView
import com.lang.util.initToolbar
import com.lang.util.setToolbarTitle
import com.ohmerhe.kolley.request.Http
import kotlinx.android.synthetic.main.include_toolbar.*
import org.jetbrains.anko.startActivity
import java.nio.charset.Charset

class FavoriteActivity : AppCompatActivity(), BaseAdapter.OnItemClickListener, LoadMoreRecyclerView.LoadMoreListener {

    private lateinit var loadMoreRecyclerView: LoadMoreRecyclerView

    private lateinit var favoriteListAdapter: FavoriteListAdapter

    private lateinit var favoriteDataList: MutableList<FavoriteListModel.DataBean.DatasBean>

    private var page: Int = 0

    private var pageCount: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)
        initView()
        initData()
        initAdapter()
        getFavoriteData()
    }

    private fun initView() {
        initToolbar(toolbar)
        setToolbarTitle(toolbar_title, "收藏")

        loadMoreRecyclerView = findViewById(R.id.favorite_load_recycler_view)
        loadMoreRecyclerView.setOnLoadMoreListener(this)
    }


    private fun initData() {
        favoriteDataList = ArrayList()
    }

    private fun initAdapter() {
        favoriteListAdapter = FavoriteListAdapter(this, R.layout.item_index_list)
        favoriteListAdapter.setOnItemClickListener(this)
        loadMoreRecyclerView.setLinerLayout()
        loadMoreRecyclerView.setFavoriteListAdapter(favoriteListAdapter)
    }


    private fun getFavoriteData() {
        Http.get {
            url = "http://www.wanandroid.com/lg/collect/list/$page/json"

            onSuccess {
                val result = it.toString(Charset.defaultCharset())
                val gson = Gson()
                val favoriteListModel = gson.fromJson<FavoriteListModel>(result, FavoriteListModel::class.java)
                favoriteDataList.clear()
                for (data in favoriteListModel.data.datas) {
                    favoriteDataList.add(data)
                }
                page = favoriteListModel.data.curPage
                pageCount = favoriteListModel.data.pageCount
                favoriteListAdapter.addAllData(favoriteDataList)
                loadMoreRecyclerView.refreshComplete()
                loadMoreRecyclerView.loadMoreComplete()

            }
        }
    }

    override fun onItemClick(itemView: View, position: Int) {
        startActivity<WebViewActivity>("url" to favoriteListAdapter.getAllData()[position].link, "title" to Html.fromHtml(favoriteListAdapter.getAllData()[position].title).toString())
    }

    override fun onRefresh() {
        page = 0
        favoriteListAdapter.clearAllData()
        getFavoriteData()
    }

    override fun onLoadMore() {
        if (page == pageCount) {
            loadMoreRecyclerView.noMoreData()
            return
        }
        getFavoriteData()
    }


}

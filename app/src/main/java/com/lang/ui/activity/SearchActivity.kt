package com.lang.ui.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.SearchView
import android.view.View
import com.google.gson.Gson
import com.lang.R
import com.lang.adapter.IndexListAdapter
import com.lang.adapter.SearchListAdapter
import com.lang.adapter.base.BaseAdapter
import com.lang.model.SearchListModel
import com.lang.ui.view.LoadMoreRecyclerView
import com.ohmerhe.kolley.request.Http
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.support.v4.startActivity
import java.nio.charset.Charset

class SearchActivity : AppCompatActivity(), SearchView.OnQueryTextListener, BaseAdapter.OnItemClickListener, LoadMoreRecyclerView.LoadMoreListener {

    private lateinit var searchLoadMoreRecyclerView: LoadMoreRecyclerView

    private lateinit var searchView: SearchView

    private lateinit var searchListAdapter: SearchListAdapter

    private lateinit var searchDataList: MutableList<SearchListModel.DataBean.DatasBean>

    private var page: Int = 0

    private var pageCount: Int = 0

    private var searchText: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seach)
        initView()
        initData()
        initAdapter()
    }

    private fun initData() {
        searchDataList = ArrayList()
    }

    private fun initAdapter() {
        searchListAdapter = SearchListAdapter(this, R.layout.item_index_list)
        searchListAdapter.setOnItemClickListener(this)
        searchLoadMoreRecyclerView.setLinerLayout()
        searchLoadMoreRecyclerView.setSearchListAdapter(searchListAdapter)
    }

    private fun initView() {
        searchLoadMoreRecyclerView = findViewById(R.id.search_load_recycler_view)
        searchView = findViewById(R.id.search_view)

        searchView.setOnQueryTextListener(this)
        searchLoadMoreRecyclerView.setOnLoadMoreListener(this)

        searchView.setIconifiedByDefault(true)
        searchView.isFocusable = true
        searchView.isIconified = false
        searchView.requestFocusFromTouch()
    }


    override fun onQueryTextSubmit(query: String?): Boolean {
        searchText = query!!
        getSearchData(query)
        return true
    }

    private fun getSearchData(query: String?) {
        Http.post {
            url = "http://www.wanandroid.com/article/query/$page/json"

            params {
                "k" - query!!
            }

            onSuccess {
                val result = it.toString(Charset.defaultCharset())
                val gson = Gson()
                val searchListModel = gson.fromJson<SearchListModel>(result, SearchListModel::class.java)
                searchDataList.clear()
                for (data in searchListModel.data.datas) {
                    searchDataList.add(data)
                }
                page = searchListModel.data.curPage
                pageCount = searchListModel.data.pageCount
                searchListAdapter.addAllData(searchDataList)
                searchLoadMoreRecyclerView.refreshComplete()
                searchLoadMoreRecyclerView.loadMoreComplete()

            }
        }
    }

    override fun onQueryTextChange(newText: String?): Boolean {

        return true
    }


    override fun onItemClick(itemView: View, position: Int) {
        startActivity<WebViewActivity>("url" to searchListAdapter.getAllData()[position].link, "title" to searchListAdapter.getAllData()[position].title)
    }

    override fun onRefresh() {
        searchListAdapter.clearAllData()
        getSearchData(searchText)
    }

    override fun onLoadMore() {
        if (page == pageCount) {
            searchLoadMoreRecyclerView.noMoreData()
            return
        }
        getSearchData(searchText)
    }


}

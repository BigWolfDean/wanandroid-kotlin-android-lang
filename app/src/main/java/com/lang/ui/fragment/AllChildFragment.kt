package com.lang.ui.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.gson.Gson
import com.lang.R
import com.lang.adapter.AllArticleListAdapter
import com.lang.adapter.base.BaseAdapter
import com.lang.model.AllArticleListModel
import com.lang.ui.activity.WebViewActivity
import com.lang.ui.view.LoadMoreRecyclerView
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.uiThread
import java.net.URL

class AllChildFragment : Fragment(), BaseAdapter.OnItemClickListener, LoadMoreRecyclerView.LoadMoreListener {


    private lateinit var allArticleList: MutableList<AllArticleListModel.DataBean.DatasBean>

    private lateinit var allArticleRecycler: LoadMoreRecyclerView

    private lateinit var allArticleListAdapter: AllArticleListAdapter

    private var articleId: Int = 0

    private var articlePage: Int = 0

    private var articleAllPage: Int = 0


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_all_child, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        articleId = arguments!!.getInt("id")
        allArticleList = ArrayList()
        initView()
        showArticleList(articlePage, articleId)
    }

    private fun showArticleList(page: Int, id: Int) {
        doAsync {
            val result = URL("http://www.wanandroid.com/article/list/$page/json?cid=$id").readText()
            uiThread {
                val gson = Gson()
                val allArticleListModel = gson.fromJson<AllArticleListModel>(result, AllArticleListModel::class.java)
                allArticleList.clear()
                for (data in allArticleListModel.data.datas) {
                    allArticleList.add(data)
                }
                articleAllPage = allArticleListModel.data.pageCount
                articlePage = allArticleListModel.data.curPage
                allArticleListAdapter.addAllData(allArticleList)
                allArticleRecycler.loadMoreComplete()
                allArticleRecycler.refreshComplete()
            }
        }
    }

    private fun initView() {
        allArticleRecycler = activity!!.findViewById(R.id.all_child_list_recycler_view)

        allArticleRecycler.setLinerLayout()

        allArticleListAdapter = AllArticleListAdapter(activity!!.applicationContext, R.layout.item_index_list)

        allArticleRecycler.setAllArticleListAdapter(allArticleListAdapter)

        allArticleListAdapter.setOnItemClickListener(this)

        allArticleRecycler.setOnLoadMoreListener(this)

    }


    override fun onItemClick(itemView: View, position: Int) {
        startActivity<WebViewActivity>("url" to allArticleListAdapter.getAllData()[position].link, "title" to allArticleListAdapter.getAllData()[position].title)
    }

    override fun onRefresh() {
        allArticleListAdapter.clearAllData()
        articlePage = 0
        showArticleList(articlePage, articleId)
    }

    override fun onLoadMore() {
        if (articlePage == articleAllPage) {
            allArticleRecycler.noMoreData()
            return
        }
        Log.e("ppppppppppppage", articlePage.toString())
        showArticleList(articlePage, articleId)
    }
}

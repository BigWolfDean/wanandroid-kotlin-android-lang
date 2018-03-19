package com.lang.ui.view

import android.content.Context
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import com.lang.R
import com.lang.adapter.IndexListAdapter


/**
 * Created by lang on 2018/3/15.
 */
class LoadMoreRecyclerView : LinearLayout, SwipeRefreshLayout.OnRefreshListener {

    private var recyclerView: RecyclerView

    private var swipeLayout: SwipeRefreshLayout

    private var loadMoreLayout: LinearLayout

    private var loadMoreTv: TextView

    private var loadMoreProgressBar: ProgressBar

    private lateinit var loadMoreListener: LoadMoreListener

    private var onScrollListener: OnMyScrollListener

    private var isLoading: Boolean = false

    private var view: View = LayoutInflater.from(context).inflate(R.layout.load_more_recyclerview, null)

    constructor(context: Context) : super(context)

    constructor(context: Context, attributes: AttributeSet) : super(context, attributes)

    init {
        recyclerView = view.findViewById(R.id.recycler_view)
        swipeLayout = view.findViewById(R.id.swipe_layout)
        loadMoreLayout = view.findViewById(R.id.load_more_layout)
        loadMoreTv = view.findViewById(R.id.load_more_tv)
        loadMoreProgressBar = view.findViewById(R.id.load_more_progress_bar)
        addView(view)

        onScrollListener = OnMyScrollListener()
        recyclerView.addOnScrollListener(onScrollListener)
        swipeLayout.setOnRefreshListener(this)
    }

    fun setLinerLayout() {
        recyclerView.layoutManager = LinearLayoutManager(context)
    }

    fun setAdapter(adapter: IndexListAdapter) {
        recyclerView.adapter = adapter
    }

    fun getRecyclerView(): RecyclerView {
        return recyclerView
    }

    fun getSwipeRefreshLayout(): SwipeRefreshLayout {
        return swipeLayout
    }

    fun refreshComplete() {
        swipeLayout.isRefreshing = false
    }

    fun loadMoreComplete() {
        loadMoreLayout.visibility = GONE
        isLoading = false
    }


    fun noMoreData() {
        loadMoreLayout.visibility = View.GONE
        loadMoreProgressBar.visibility = GONE
        loadMoreTv.text = "已加载完毕"
    }

    fun setOnLoadMoreListener(loadMoreListener: LoadMoreListener) {
        this.loadMoreListener = loadMoreListener
    }

    override fun onRefresh() {
        loadMoreListener.onRefresh()
    }

    open inner class OnMyScrollListener : RecyclerView.OnScrollListener() {

        override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            if (dy > 0) {
                val linearLayoutManager: LinearLayoutManager = recyclerView?.layoutManager as LinearLayoutManager
                val visibleItemCount: Int = linearLayoutManager.childCount
                val totalItemCount: Int = linearLayoutManager.itemCount
                val pastVisibleItems: Int = linearLayoutManager.findFirstVisibleItemPosition()

                if (!isLoading && (visibleItemCount + pastVisibleItems) >= totalItemCount) {
                    isLoading = true
                    loadMoreLayout.visibility = View.VISIBLE
                    loadMoreListener.onLoadMore()
                }
            }
        }

    }

    interface LoadMoreListener {

        fun onRefresh()

        fun onLoadMore()

    }
}


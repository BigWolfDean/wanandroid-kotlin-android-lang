package com.lang.adapter

import android.content.Context
import android.text.Html
import com.lang.R
import com.lang.adapter.base.BaseAdapter
import com.lang.model.SearchListModel
import com.lang.model.index.DatasBean

/**
 * Created by lang on 2018/3/22.
 */
class SearchListAdapter constructor(context: Context, layoutId: Int) : BaseAdapter<SearchListModel.DataBean.DatasBean>(context, layoutId) {


    override fun onBindData(holder: BaseViewHolder, position: Int) {
        holder.setText(R.id.item_tv_index_title, Html.fromHtml(list[position].title).toString())
        holder.setText(R.id.item_tv_index_date, list[position].niceDate)
    }

}
package com.lang.adapter

import android.content.Context
import com.lang.R
import com.lang.adapter.base.BaseAdapter
import com.lang.model.AllArticleListModel

/**
 * Created by lang on 2018/3/21.
 */
class AllArticleListAdapter constructor(context: Context, layoutId: Int) : BaseAdapter<AllArticleListModel.DataBean.DatasBean>(context, layoutId) {

    override fun onBindData(holder: BaseViewHolder, position: Int) {
        holder.setText(R.id.item_tv_index_title, list[position].title)
        holder.setText(R.id.item_tv_index_date, list[position].niceDate)
    }


}
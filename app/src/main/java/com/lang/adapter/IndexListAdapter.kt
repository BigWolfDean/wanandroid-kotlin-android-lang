package com.lang.adapter

import android.content.Context
import com.lang.R
import com.lang.model.DatasBean

/**
 * Created by lang on 2018/3/15.
 */
class IndexListAdapter constructor(context: Context, layoutId: Int) : BaseAdapter<DatasBean>(context, layoutId) {


    override fun onBindData(holder: BaseViewHolder, position: Int) {
        holder.setText(R.id.item_tv_index_title, list[position].title)
        holder.setText(R.id.item_tv_index_date, list[position].niceDate)
    }


}
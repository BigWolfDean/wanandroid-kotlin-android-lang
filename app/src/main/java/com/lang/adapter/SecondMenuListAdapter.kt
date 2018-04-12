package com.lang.adapter

import android.app.Activity
import android.content.Context
import com.lang.R
import com.lang.adapter.base.BaseAdapter
import com.lang.model.SystemDataModel

/**
 * Created by lang on 2018/3/21.
 */
class SecondMenuListAdapter constructor(context: Context, layoutId: Int) : BaseAdapter<SystemDataModel.DataBean.ChildrenBean>(context, layoutId) {

    override fun onBindData(holder: BaseViewHolder, position: Int) {
        holder.setText(R.id.second_menu_tv, list[position].name)
    }
}
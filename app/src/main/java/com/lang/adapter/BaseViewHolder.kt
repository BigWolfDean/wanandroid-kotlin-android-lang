package com.lang.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.util.SparseArray
import android.widget.TextView


@Suppress("UNCHECKED_CAST")
open
/**
 * Created by lang on 2018/3/15.
 */
class BaseViewHolder constructor(private val myItemView: View?) : RecyclerView.ViewHolder(myItemView) {

    private var views: SparseArray<View> = SparseArray()


    private fun <T : View> getView(viewId: Int): T {
        var view = views.get(viewId)
        if (view == null) {
            view = myItemView?.findViewById(viewId)
            views.put(viewId, view)
        }
        return view as T
    }

    //显示文字信息的方法
    fun setText(resId: Int, text: String) {
        val view = getView<TextView>(resId)
        view.text = text
    }
    
    fun setTextColorResource(resId: Int, color: Int) {
        val view = getView<TextView>(resId)
        view.setTextColor(color)
    }

}
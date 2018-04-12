package com.lang.adapter

import android.content.Context
import android.view.View
import android.widget.ImageView
import com.lang.R
import com.lang.adapter.base.BaseAdapter
import com.lang.model.index.DatasBean

/**
 * Created by lang on 2018/3/15.
 */
class IndexListAdapter constructor(context: Context, layoutId: Int) : BaseAdapter<DatasBean>(context, layoutId) {

    private lateinit var onImageClick: OnImageClickListener

    override fun onBindData(holder: BaseViewHolder, position: Int) {
        holder.setText(R.id.item_tv_index_title, list[position].title)
        holder.setText(R.id.item_tv_index_date, list[position].niceDate)
        holder.setImageView(R.id.item_tv_index_heart, if (list[position].isCollect) R.drawable.ic_favorite else R.drawable.ic_no_favorite)
        val collectView: ImageView = holder.getSingleView(R.id.item_tv_index_heart) as ImageView
        collectView.setOnClickListener {
            onImageClick.onImageClick(collectView, position)
        }
    }

    fun setOnCollectClickListener(onImageClick: OnImageClickListener) {
        this.onImageClick = onImageClick
    }

    interface OnImageClickListener {
        fun onImageClick(imgView: View, position: Int)
    }

}
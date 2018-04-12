package com.lang.adapter.base

import android.app.Activity
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lang.R
import com.lang.adapter.BaseViewHolder


/**
 * Created by lang on 2018/3/15.
 */
abstract class BaseAdapter<T> constructor(val context: Context, private val layoutId: Int) : RecyclerView.Adapter<BaseViewHolder>() {

    protected var list: MutableList<T> = ArrayList()
    protected var inflater: LayoutInflater? = null
    private lateinit var onItemClick: OnItemClickListener
    private lateinit var onItemLongClick: OnItemLongClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        inflater = LayoutInflater.from(context)
        return BaseViewHolder(inflater?.inflate(layoutId, parent, false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        onBindData(holder, position)
        holder.itemView.setOnClickListener {
            onItemClick.onItemClick(holder.itemView, position)
        }
        holder.itemView.setOnLongClickListener {
            onItemLongClick.onItemLongClick(holder.itemView, position)
            true
        }
    }

    fun addAllData(dataList: List<T>) {
        this.list.addAll(dataList)
        notifyDataSetChanged()
    }

    fun replaceSingleData(t: T, position: Int) {
        this.list[position] = t
    }

    fun removeSingleData(position: Int) {
        this.list.removeAt(position)
    }

    fun getAllData(): List<T> {
        return this.list
    }

    fun clearAllData() {
        this.list.clear()
    }

    fun setOnItemClickListener(onItemClick: OnItemClickListener) {
        this.onItemClick = onItemClick
    }

    fun setOnItemLongClickListener(onItemLongClickListener: OnItemLongClickListener) {
        this.onItemLongClick = onItemLongClickListener
    }

    abstract fun onBindData(holder: BaseViewHolder, position: Int)

    interface OnItemClickListener {
        fun onItemClick(itemView: View, position: Int)
    }

    interface OnItemLongClickListener {
        fun onItemLongClick(itemView: View, position: Int)
    }

}
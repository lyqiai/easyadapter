package com.river.easyadapter

import android.util.SparseArray
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * @Author River
 * @Date 2021/4/26 0026 上午 11:17
 * 通过实现EasyItem子类调用插入删除操作列表,该类提供通用列表、多样性、头部尾部
 * 无论列表多复杂，只需拆分列表实现不同EasyItem即可拼接成复杂列表
 */
open class EasyAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    //内容列表数据
    private val items: MutableList<EasyItem<out RecyclerView.ViewHolder>> = mutableListOf()

    //列表类型和position位置关系
    private val viewTypeMap = SparseArray<Int>()

    //默认列数
    private var spanCount = 1

    override fun getItemViewType(position: Int): Int {
        val item = items[position]
        val type = item.javaClass.hashCode()
        viewTypeMap.put(type, position)

        return type
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val position = viewTypeMap.get(viewType)
        val item = getItem(position)

        //加载布局，layoutId或者layoutView选择一个实现
        val view = if (item.layoutId() != null) {
            LayoutInflater.from(parent.context).inflate(item.layoutId()!!, parent, false)
        } else if (item.layoutView() != null) {
            item.layoutView()
        } else {
            throw RuntimeException("EasyItem必须实现方法layoutId、layoutView中的一个!")
        }

        return item.createViewHolder(view!!)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        getItem(position).convert(holder, position)
    }

    private fun getItem(position: Int): EasyItem<RecyclerView.ViewHolder> =
        items[position] as EasyItem<RecyclerView.ViewHolder>

    /**
     * 默认将LinearLayoutManager替换GridLayoutManager
     */
    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)

        var layoutManager = recyclerView.layoutManager

        if (layoutManager == null || layoutManager is LinearLayoutManager) {
            layoutManager = GridLayoutManager(recyclerView.context, getSpanCount())
        } else if (layoutManager is GridLayoutManager) {
            layoutManager.spanCount = getSpanCount()
        }

        if (layoutManager is GridLayoutManager) {
            layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    val current = items[position]
                    return current.getSpanCount() ?: spanCount
                }
            }
        }

        recyclerView.layoutManager = layoutManager
    }

    /**
     * 获取列表列数
     * @return Int
     */
    fun getSpanCount(): Int {
        return spanCount
    }

    /**
     * 设置列表列数
     * @param spanCount Int
     */
    fun setSpanCount(spanCount: Int) {
        this.spanCount = spanCount
    }

    /**
     * 根据position插入内容
     * @param position Int
     * @param item EasyItem<*, out RecyclerView.ViewHolder>
     * @param isNotify Boolean
     */
    fun insertItem(
        position: Int,
        item: EasyItem<out RecyclerView.ViewHolder>,
        isNotify: Boolean = true
    ) {
        val pos = if (position > items.size) items.size else position

        val realPosition = getContentPosition(pos)

        item.adapter = this
        items.add(pos, item)

        if (isNotify) {
            notifyItemInserted(realPosition)
        }
    }

    /**
     * 插入内容
     * @param item EasyItem<*, out RecyclerView.ViewHolder>
     * @param isNotify Boolean
     */
    fun addItem(item: EasyItem<out RecyclerView.ViewHolder>, isNotify: Boolean = true) {
        insertItem(items.size, item, isNotify)
    }

    /**
     * 批量插入内容
     * @param items List<EasyItem<*, out RecyclerView.ViewHolder>>
     */
    fun addItems(items: List<EasyItem<out RecyclerView.ViewHolder>>) {
        if (items.isEmpty()) {
            return
        }

        val start = this.items.size

        for (item in items) {
            addItem(item, false)
        }

        notifyItemRangeInserted(start, items.size)
    }

    /**
     * 删除内容
     * @param item EasyItem<*, out RecyclerView.ViewHolder>
     * @param isNotify Boolean
     */
    fun removeItem(item: EasyItem<out RecyclerView.ViewHolder>, isNotify: Boolean = true) {
        val position = items.indexOf(item)
        if (position == -1) {
            return
        }

        removePosition(position)
    }

    /**
     * 批量删除内容
     * @param items List<EasyItem<*, out RecyclerView.ViewHolder>>
     */
    fun removeItems(items: List<EasyItem<out RecyclerView.ViewHolder>>) {
        for (item in items) {
            removeItem(item)
        }
    }

    /**
     * 根据position删除内容
     * @param position Int
     * @param isNotify Boolean
     */
    fun removePosition(position: Int, isNotify: Boolean = true) {
        if (position >= items.size) return

        val realPosition = getContentPosition(position)

        items.removeAt(position)

        if (isNotify) {
            notifyItemRemoved(realPosition)
        }
    }

    /**
     * 根据position批量删除内容
     * @param positions List<Int>
     */
    fun removePositions(positions: List<Int>) {
        for (position in positions) {
            removePosition(position)
        }
    }

    /**
     * 获取内容数据
     * @return List<EasyItem<*, out RecyclerView.ViewHolder>>
     */
    fun getItems(): List<EasyItem<out RecyclerView.ViewHolder>> = items.toList()


    /**
     * 刷新Item
     * @param item EasyItem<*, out RecyclerView.ViewHolder>
     */
    fun refreshItem(item: EasyItem<out RecyclerView.ViewHolder>) {
        val posInAll = items.indexOf(item)

        if (posInAll == -1) {
            return
        }

        notifyItemChanged(posInAll)
    }

    /**
     * 清空列表
     */
    fun removeAll() {
        items.clear()
        notifyDataSetChanged()
    }


    /**
     * 获取内容列表的真实position
     */
    private fun getContentPosition(position: Int): Int {
        return position
    }
}
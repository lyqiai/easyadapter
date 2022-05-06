package com.river.easyadapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import java.lang.reflect.ParameterizedType

/**
 * @Author River
 * @Date 2021/4/26 0026 上午 11:17
 */
abstract class EasyItem<VH : RecyclerView.ViewHolder> {
    internal var adapter: EasyAdapter? = null
    private var itemClickListener: ItemClickListener? = null
    /**
     * 布局ID
     * @return Int
     */
    open fun layoutId(): Int = -1

    /**
     * 直接返回View
     * @return View?
     */
    open fun layoutView(): View? = null

    /**
     * 数据绑定
     * @param holder ViewHolder
     * @param data T?
     * @param position Int
     */
    open fun convert(holder: VH, position: Int) {
        holder.itemView.setOnClickListener {
            itemClickListener?.onClick(it, position)
        }
    }

    /**
     * 复杂布局时所占列数
     * @return Int
     */
    open fun getSpanCount(): Int? = null

    open fun createViewHolder(view: View): VH {
        val parameterizedType = javaClass.genericSuperclass as ParameterizedType
        var type = parameterizedType.actualTypeArguments[0] as Class<VH>
        val constructor = type.getDeclaredConstructor(View::class.java)

        return constructor.newInstance(view)
    }

    fun refresh() {
        adapter?.refreshItem(this)
    }

    fun remove() {
        adapter?.removeItem(this)
    }

    fun setOnItemClickListener(listener: ItemClickListener?) {
        itemClickListener = listener
    }
}
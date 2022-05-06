package com.river.easyadapter.viewholder

import android.util.SparseArray
import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * @Author River
 * @Date 2021/4/26 0026 上午 11:17
 */
open class EasyViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
    private val viewsCache = SparseArray<View?>()

    fun <T : View> findViewById(id: Int): T {
        val view = viewsCache.get(id)
        if (view == null) {
            val findView = this.view.findViewById<T>(id)
            viewsCache.put(id, findView)

            return findView
        }

        return view as T
    }
}

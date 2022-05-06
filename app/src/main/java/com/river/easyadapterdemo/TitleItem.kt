package com.river.easyadapterdemo

import android.widget.TextView
import com.river.easyadapter.EasyItem
import com.river.easyadapter.viewholder.EasyViewHolder

/**
 * @Author: River
 * @Emial: 1632958163@qq.com
 * @Create: 2021/11/9
 **/
class TitleItem(val title: String) : EasyItem<EasyViewHolder>() {
    override fun layoutId() = R.layout.layout_item_title

    override fun convert(holder: EasyViewHolder, position: Int) {
        holder.findViewById<TextView>(R.id.title).text = title
    }
}
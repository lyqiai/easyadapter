package com.river.easyadapterdemo

import com.river.easyadapter.EasyDataBindingItem
import com.river.easyadapter.viewholder.EasyViewBindingViewHolder
import com.river.easyadapterdemo.databinding.LayoutItemBinding

/**
 * @Author: River
 * @Emial: 1632958163@qq.com
 * @Create: 2021/11/9
 **/
class Item(val value: String): EasyDataBindingItem<LayoutItemBinding>() {
    override fun layoutId() = R.layout.layout_item

    override fun convert(holder: EasyViewBindingViewHolder<LayoutItemBinding>, position: Int) {
        holder.binding.value = value
    }
}
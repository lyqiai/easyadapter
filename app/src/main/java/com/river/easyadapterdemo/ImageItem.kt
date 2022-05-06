package com.river.easyadapterdemo

import com.river.easyadapter.EasyItem
import com.river.easyadapter.viewholder.EasyViewHolder

/**
 * @Author: River
 * @Emial: 1632958163@qq.com
 * @Create: 2021/11/9
 **/
class ImageItem : EasyItem<EasyViewHolder>() {
    override fun layoutId() = R.layout.layout_item_image

    override fun convert(holder: EasyViewHolder, position: Int) {
    }

    override fun getSpanCount() = 1
}
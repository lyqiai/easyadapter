package com.river.easyadapter.viewholder

import android.view.View
import androidx.viewbinding.ViewBinding

/**
 * @Author River
 * @Date 2021/7/22 0022 上午 9:12
 */
class EasyViewBindingViewHolder<VB : ViewBinding>(view: View) : EasyViewHolder(view) {
    lateinit var binding: VB
}


package com.river.easyadapter

import android.view.View

/**
 * @Author: River
 * @Emial: 1632958163@qq.com
 * @Create: 2021/11/9
 **/
fun interface ItemClickListener {
    fun onClick(view: View, position: Int)
}
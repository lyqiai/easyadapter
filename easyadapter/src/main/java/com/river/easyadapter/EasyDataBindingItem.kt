package com.river.easyadapter

import android.view.View
import androidx.databinding.ViewDataBinding
import com.river.easyadapter.viewholder.EasyViewBindingViewHolder
import java.lang.reflect.ParameterizedType

/**
 * @Author: River
 * @Emial: 1632958163@qq.com
 * @Create: 2021/11/9
 **/
abstract class EasyDataBindingItem<VB: ViewDataBinding>: EasyItem<EasyViewBindingViewHolder<VB>>() {
     override fun createViewHolder(view: View): EasyViewBindingViewHolder<VB> {
          val vh = EasyViewBindingViewHolder<VB>(view)

          val viewDataBindingClazz = (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0] as Class<VB>
          val bindMethod = viewDataBindingClazz.getDeclaredMethod("bind", View::class.java)
          val binding = bindMethod.invoke(null, view) as VB

          vh.binding = binding
          return vh
     }
}
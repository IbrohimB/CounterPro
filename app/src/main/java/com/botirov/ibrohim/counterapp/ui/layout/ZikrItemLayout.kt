package com.botirov.ibrohim.counterapp.ui.layout

import android.content.Context
import android.view.LayoutInflater
import android.widget.RelativeLayout
import com.botirov.ibrohim.model.Zikr
import com.botirov.ibrohim.tasbihapp.databinding.ZikrItemViewBinding

class ZikrItemLayout(
    context: Context?,
    listener: OnZikrClickListener
) : RelativeLayout(context) {

    // todo:[done] mirmuhsindan shuni function orqali qilishni so'ra, yoki ChatGPT'dan so'rab Mirmuhsinni impress qil, +$5

    private var binding: ZikrItemViewBinding = ZikrItemViewBinding.inflate(LayoutInflater.from(context), this, true)

    private lateinit var currentZikr: Zikr

    init {
        setupLayout(listener)
    }

    private fun setupLayout(listener: OnZikrClickListener) {
        binding = ZikrItemViewBinding.inflate(LayoutInflater.from(context), this, true)
        setOnClickListener {
            listener.onZikrClicked(currentZikr)
        }
    }

    fun fillContent(zikr: Zikr) {
        currentZikr = zikr
        binding.tvZikr.text = currentZikr.name
    }

    interface OnZikrClickListener {
        fun onZikrClicked(zikr: Zikr)
    }

}
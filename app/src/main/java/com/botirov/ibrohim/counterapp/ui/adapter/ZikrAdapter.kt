package com.botirov.ibrohim.counterapp.ui.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.botirov.ibrohim.counterapp.ui.layout.ZikrItemLayout
import com.botirov.ibrohim.model.Zikr

class ZikrAdapter (
    private val zikrList: List<Zikr>,
    private val listener: ZikrItemLayout.OnZikrClickListener
) : RecyclerView.Adapter<ZikrAdapter.ZikrViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ZikrViewHolder { // inflating view of item, and giving it to ViewHolder
        val view = ZikrItemLayout(parent.context, listener)
        return ZikrViewHolder(view)
    }

    override fun onBindViewHolder(holder: ZikrViewHolder, position: Int) { // Binds, view and data
        val zikr = zikrList[position]
        holder.layout.fillContent(zikr)
    }

    override fun getItemCount() = zikrList.size

    inner class ZikrViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val layout = itemView as ZikrItemLayout
    }
}
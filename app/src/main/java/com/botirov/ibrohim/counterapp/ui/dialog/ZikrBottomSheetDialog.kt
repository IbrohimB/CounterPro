package com.botirov.ibrohim.counterapp.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.botirov.ibrohim.counterapp.ui.adapter.ZikrAdapter
import com.botirov.ibrohim.counterapp.ui.layout.ZikrItemLayout
import com.botirov.ibrohim.model.Zikr
import com.botirov.ibrohim.tasbihapp.databinding.ZikrListDialogBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ZikrBottomSheetDialog(
    private val zikrList: List<Zikr>,
    private val listener: OnZikrSelectedListener
) : BottomSheetDialogFragment(), ZikrItemLayout.OnZikrClickListener {

    private lateinit var binding: ZikrListDialogBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ZikrListDialogBinding.inflate(inflater, container, false)
        setupRecyclerView() // passing the inflated view
        return binding.root
    }

    private fun setupRecyclerView() { // getting reference to RecyclerView
        // positions items in a vertical list
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        // setting its adapter to a new instance of ZikrAdapter, passing in your list of Zikr objects
        binding.recyclerView.adapter = ZikrAdapter(zikrList, this)
    }

    // called from adapter and invokes onZikrSelected(from MainActivity), passes selected object
    override fun onZikrClicked(zikr: Zikr) {
        listener.onZikrSelected(zikr)
        dismiss()
    }

    interface OnZikrSelectedListener {
        fun onZikrSelected(zikr: Zikr)
    }
}
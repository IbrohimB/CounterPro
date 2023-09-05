package com.botirov.ibrohim.counterapp.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.botirov.ibrohim.tasbihapp.databinding.BottomSheetMenuBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class MenuBottomSheetDialog: BottomSheetDialogFragment() {

    private lateinit var binding: BottomSheetMenuBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = BottomSheetMenuBinding.inflate(inflater,container,false)

        setupButtons()

        return binding.root
    }

    private fun setupButtons(){
        binding.notificationsButton.setOnClickListener{}
        binding.dynamicTouchButton.setOnClickListener {}
        binding.shareAppButton.setOnClickListener {}
        binding.rateUs.setOnClickListener {}
        binding.contactUs.setOnClickListener {}
        binding.termsOfUse.setOnClickListener {}
        binding.privacyPolicy.setOnClickListener {}
    }
}
package com.android.crazyskins.ui.fragments

import android.view.LayoutInflater
import android.view.ViewGroup
import com.android.crazyskins.base.BaseFragment
import com.android.crazyskins.databinding.FragmentEarnBinding

class EarnFragment : BaseFragment<FragmentEarnBinding>() {

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentEarnBinding.inflate(inflater, container, false)
}
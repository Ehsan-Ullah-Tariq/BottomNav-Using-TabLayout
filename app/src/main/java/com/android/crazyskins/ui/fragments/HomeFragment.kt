package com.android.crazyskins.ui.fragments

import android.view.LayoutInflater
import android.view.ViewGroup
import com.android.crazyskins.base.BaseFragment
import com.android.crazyskins.databinding.FragmentHomeBinding

class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentHomeBinding.inflate(inflater, container, false)
}
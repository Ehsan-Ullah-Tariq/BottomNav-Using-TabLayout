package com.android.crazyskins.ui.fragments

import android.view.LayoutInflater
import android.view.ViewGroup
import com.android.crazyskins.base.BaseFragment
import com.android.crazyskins.databinding.FragmentFeedBinding

class FeedsFragment : BaseFragment<FragmentFeedBinding>() {

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentFeedBinding.inflate(inflater, container, false)
}
package com.android.crazyskins.ui.fragments

import android.view.LayoutInflater
import android.view.ViewGroup
import com.android.crazyskins.base.BaseFragment
import com.android.crazyskins.databinding.FragmentProfileBinding

class ProfileFragment : BaseFragment<FragmentProfileBinding>() {

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentProfileBinding.inflate(inflater, container, false)
}
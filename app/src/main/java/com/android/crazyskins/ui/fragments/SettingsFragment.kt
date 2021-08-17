package com.android.crazyskins.ui.fragments

import android.view.LayoutInflater
import android.view.ViewGroup
import com.android.crazyskins.base.BaseFragment
import com.android.crazyskins.databinding.FragmentSettingsBinding

class SettingsFragment : BaseFragment<FragmentSettingsBinding>() {

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentSettingsBinding.inflate(inflater, container, false)
}
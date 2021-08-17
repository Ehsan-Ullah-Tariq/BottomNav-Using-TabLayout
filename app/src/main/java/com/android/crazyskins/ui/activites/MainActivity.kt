package com.android.crazyskins.ui.activites

import android.graphics.PorterDuff
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.viewpager2.widget.ViewPager2
import com.android.crazyskins.R
import com.android.crazyskins.adapters.MainPagerAdapter
import com.android.crazyskins.databinding.ActivityMainBinding
import com.android.crazyskins.ui.fragments.*
import com.google.android.material.tabs.TabLayout

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    lateinit var mainPagerAdapter: MainPagerAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        mainPagerAdapter = MainPagerAdapter(supportFragmentManager, lifecycle)

        setUpViewPagerWithTabLayout()
        setupTablayout()

        binding.mainViewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                binding.bottomTabLay.getTabAt(position)?.select()
            }
        })

    }


    private fun setupTablayout() {
         val titles: List<String> = listOf("Skins","Profile","Earn","Feed","Settings")
         val titleIcons = listOf(
             R.drawable.ic_home,
             R.drawable.ic_profile,
             R.drawable.ic_money,
             R.drawable.ic_activity_feed,
             R.drawable.ic_settings
         )
        for ((index, title) in titles?.withIndex()!!) {
            binding.bottomTabLay.newTab().also {
                binding.bottomTabLay.addTab(it)
                val tabView = LayoutInflater.from(applicationContext)
                    .inflate(R.layout.custom_tabs_layout, binding.bottomTabLay, false)
                tabView.findViewById<ImageView>(R.id.tabIcon)
                    .setImageResource(titleIcons[index])
                tabView.findViewById<TextView>(R.id.tabTitle).text = title


                if (index == 0) {
                    tabView.findViewById<ConstraintLayout>(R.id.tabsRoot)
                        .setBackgroundResource(R.drawable.bottom_tabs_bg)
                    tabView?.findViewById<ImageView>(R.id.tabIcon)
                        ?.setColorFilter(
                            ContextCompat.getColor(applicationContext, R.color.pink),
                            PorterDuff.Mode.SRC_IN
                        )
                }

                it.customView = tabView
            }
        }


        binding.bottomTabLay.getTabAt(0)?.customView?.findViewById<TextView>(R.id.tabTitle)
            ?.apply {
                visibility = View.VISIBLE
            }

        binding.bottomTabLay.addOnTabSelectedListener(object :
            TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                tab?.customView?.findViewById<TextView>(R.id.tabTitle)?.apply {
                    visibility = View.GONE
                }
                tab?.customView?.findViewById<ImageView>(R.id.tabIcon)
                        ?.setColorFilter(
                   ContextCompat.getColor(applicationContext, R.color.white),
                    PorterDuff.Mode.SRC_IN
                )
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.customView?.findViewById<TextView>(R.id.tabTitle)?.apply {
                    visibility = View.VISIBLE
                }


                tab?.customView?.findViewById<ConstraintLayout>(R.id.tabsRoot)
                    ?.setBackgroundResource(R.drawable.bottom_tabs_bg)
                tab?.customView?.findViewById<ImageView>(R.id.tabIcon)
                    ?.setColorFilter(
                ContextCompat.getColor(applicationContext, R.color.pink),
                PorterDuff.Mode.SRC_IN
                )

                binding.mainViewPager.currentItem = tab?.position!!
            }

        })
    }

    private fun setUpViewPagerWithTabLayout() {
        binding.mainViewPager.let {
            it.orientation = ViewPager2.ORIENTATION_HORIZONTAL
            it.isUserInputEnabled = true

            it.offscreenPageLimit = 4

            mainPagerAdapter.addFragment(HomeFragment())
            mainPagerAdapter.addFragment(ProfileFragment())
            mainPagerAdapter.addFragment(EarnFragment())
            mainPagerAdapter.addFragment(FeedsFragment())
            mainPagerAdapter.addFragment(SettingsFragment())
            it.adapter = mainPagerAdapter
        }

//        TabLayoutMediator(
//            binding.bottomTabLay,
//            binding.mainViewPager
//        ) { tab, position ->
//
//            val titles: List<String> = listOf("Skins","Profile","Earn","Feed","Settings")
//            val titleIcons = listOf(
//                R.drawable.ic_home,
//                R.drawable.ic_profile,
//                R.drawable.ic_money,
//                R.drawable.ic_activity_feed,
//                R.drawable.ic_settings
//            )

//            when (position) {
//                0 -> {
//                    tab.icon = titleIcons[position].
//                    tab.text = titles[position]
//                }
//                1 -> {
//                    tab.text = titles[position]
//                }
//                2 -> {
//                    tab.text = titles[position]
//                }
//                3 -> {
//                    tab.text = titles[position]
//                }
//                4 -> {
//                    tab.text = titles[position]
//                }
//            }
//        }.attach()
    }

}
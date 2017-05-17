package com.kanade.ushio.ui.main

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import butterknife.BindView
import butterknife.ButterKnife
import com.kanade.ushio.R
import com.kanade.ushio.adapter.MainVpAdapter
import com.kanade.ushio.ui.about.AboutFragment
import com.kanade.ushio.ui.calendar.CalendarFragment
import com.kanade.ushio.ui.subscribe.SubscribeListFragment
import me.yokeyword.fragmentation.SupportFragment

class MainFragment : SupportFragment(){
    @BindView(R.id.main_vp)
    lateinit var viewpager : ViewPager
    @BindView(R.id.main_bottombar)
    lateinit var bottombar : BottomNavigationView

    private var prevMenuItem: MenuItem? = null

    companion object{
        @JvmStatic
        fun newInstance(): MainFragment = MainFragment()
    }

    private val listener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.tab_sublist -> {
                viewpager.setCurrentItem(0, true)
                return@OnNavigationItemSelectedListener true
            }
            R.id.tab_calendar -> {
                viewpager.setCurrentItem(1, true)
                return@OnNavigationItemSelectedListener true
            }
            R.id.tab_me -> {
                viewpager.setCurrentItem(2, true)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_main, container, false)
        ButterKnife.bind(this, view)
        bottombar.setOnNavigationItemSelectedListener(listener)

        val fragmentList = listOf<SupportFragment>(
                SubscribeListFragment.newInstance(),
                CalendarFragment.newInstance(),
                AboutFragment.newInstance()
        )
        viewpager.adapter = MainVpAdapter(fragmentList, childFragmentManager)
        viewpager.setCurrentItem(0, true)
        viewpager.offscreenPageLimit = 3
        viewpager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) = Unit

            override fun onPageSelected(position: Int) {
                val curMenuItem = bottombar.menu.getItem(position)
                if (prevMenuItem != null) {
                    prevMenuItem?.isChecked = false
                } else {
                    bottombar.menu.getItem(0).isChecked = false
                }
                curMenuItem.isChecked = true
                prevMenuItem = curMenuItem

            }

            override fun onPageScrollStateChanged(state: Int) = Unit
        })
        return view
    }
}
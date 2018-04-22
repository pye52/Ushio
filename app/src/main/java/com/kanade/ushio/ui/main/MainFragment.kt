package com.kanade.ushio.ui.main

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.view.ViewPager
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import com.github.javiersantos.appupdater.AppUpdater
import com.github.javiersantos.appupdater.enums.UpdateFrom
import com.kanade.ushio.R
import com.kanade.ushio.R.id.bottomNv
import com.kanade.ushio.adapter.MainVpAdapter
import com.kanade.ushio.ui.user_collection.UserCollectionFragment
import com.kanade.ushio.utils.UPDATE_PATH
import kotlinx.android.synthetic.main.fragment_main.*
import me.yokeyword.fragmentation.SupportFragment

class MainFragment : SupportFragment(){
    private var prevMenuItem: MenuItem? = null

    companion object{
        @JvmStatic
        fun newInstance(): MainFragment = MainFragment()
    }

    private val listener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.tab_sublist -> {
                vp.setCurrentItem(0, true)
                return@OnNavigationItemSelectedListener true
            }
            R.id.tab_calendar -> {
                vp.setCurrentItem(1, true)
                return@OnNavigationItemSelectedListener true
            }
            R.id.tab_me -> {
                vp.setCurrentItem(2, true)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_main, container, false)
        AppUpdater(context)
                .setUpdateFrom(UpdateFrom.JSON)
                .setUpdateJSON(UPDATE_PATH)
                .setTitleOnUpdateAvailable(R.string.update)
                .setButtonUpdate(R.string.btn_update)
                .setButtonDismiss(R.string.cancel)
                .setButtonDoNotShowAgain("")
                .start()
        return view
    }

    override fun onEnterAnimationEnd(savedInstanceState: Bundle?) {
        val fragmentList = listOf<SupportFragment>(
                UserCollectionFragment.newInstance()
        )
        bottomNv.setOnNavigationItemSelectedListener(listener)
        vp.adapter = MainVpAdapter(fragmentList, childFragmentManager)
        vp.setCurrentItem(0, true)
        vp.offscreenPageLimit = 3
        vp.addOnPageChangeListener(pageChangeListener)
    }

    private val pageChangeListener = object : ViewPager.OnPageChangeListener {
        override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) = Unit

        override fun onPageSelected(position: Int) {
            val curMenuItem = bottomNv.menu.getItem(position)
            if (prevMenuItem != null) {
                prevMenuItem?.isChecked = false
            } else {
                bottomNv.menu.getItem(0).isChecked = false
            }
            curMenuItem.isChecked = true
            prevMenuItem = curMenuItem

        }

        override fun onPageScrollStateChanged(state: Int) = Unit
    }
}
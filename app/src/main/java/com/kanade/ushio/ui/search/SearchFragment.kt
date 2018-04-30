package com.kanade.ushio.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kanade.ushio.R
import me.yokeyword.fragmentation.SupportFragment

class SearchFragment : SupportFragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_search, container, false)
        return view
    }

    override fun onEnterAnimationEnd(savedInstanceState: Bundle?) {

    }
}
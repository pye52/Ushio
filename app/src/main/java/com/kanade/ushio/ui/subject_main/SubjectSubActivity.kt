package com.kanade.ushio.ui.subject_main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.kanade.ushio.R
import com.kanade.ushio.ui.subject_main.subject_crt.SubjectCrtFragment
import com.kanade.ushio.ui.subject_main.subject_progress.SubjectProgressFragment
import com.kanade.ushio.ui.subject_main.subject_staff.SubjectStaffFragment
import me.yokeyword.fragmentation.SupportActivity

class SubjectSubActivity : SupportActivity() {
    companion object {
        private const val FLAG_CRT = "flag_crt"
        private const val FLAG_PROGRESS = "flag_progress"
        private const val FLAG_STAFF = "flag_staff"

        private const val ARG_FLAG = "arg_fragment"
        private const val ARG_EXTRA = "arg_extra"

        @JvmStatic
        fun newStaffInstance(ctx: Context, subjectEpId: Int): Intent =
                Intent(ctx, SubjectSubActivity::class.java)
                        .putExtra(ARG_FLAG, FLAG_STAFF)
                        .putExtra(ARG_EXTRA, subjectEpId)

        @JvmStatic
        fun newCrtInstance(ctx: Context, subjectEpId: Int): Intent =
                Intent(ctx, SubjectSubActivity::class.java)
                        .putExtra(ARG_FLAG, FLAG_CRT)
                        .putExtra(ARG_EXTRA, subjectEpId)

        @JvmStatic
        fun newEpInstance(ctx: Context, subjectEpId: Int): Intent =
                Intent(ctx, SubjectSubActivity::class.java)
                        .putExtra(ARG_FLAG, FLAG_PROGRESS)
                        .putExtra(ARG_EXTRA, subjectEpId)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_subject_sub)
        val flag = intent.getStringExtra(ARG_FLAG)
        if (savedInstanceState == null) {
            val fragment = when (flag) {
                FLAG_STAFF -> SubjectStaffFragment.newInstance(intent.getIntExtra(ARG_EXTRA, -1))
                FLAG_CRT -> SubjectCrtFragment.newInstance(intent.getIntExtra(ARG_EXTRA, -1))
                FLAG_PROGRESS -> SubjectProgressFragment.newInstance(intent.getIntExtra(ARG_EXTRA, -1))
                else -> null
            }

            fragment?.let { loadRootFragment(R.id.fragment_subject__sub_fl, it) } ?: finish()
        }
    }
}
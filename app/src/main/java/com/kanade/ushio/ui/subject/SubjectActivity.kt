package com.kanade.ushio.ui.subject

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.kanade.ushio.R
import com.kanade.ushio.entity.Crt
import com.kanade.ushio.entity.Staff
import me.yokeyword.fragmentation.SupportActivity

class SubjectActivity : SupportActivity() {
    companion object {
        private const val FLAG_CRT = "flag_crt"
        private const val FLAG_PROGRESS = "flag_progress"
        private const val FLAG_STAFF = "flag_staff"

        private const val ARG_FLAG = "arg_fragment"
        private const val ARG_EXTRA = "arg_extra"

        @JvmStatic
        fun staffIntent(ctx: Context?, list: ArrayList<Staff>): Intent =
                Intent(ctx, SubjectActivity::class.java)
                        .putExtra(ARG_FLAG, FLAG_STAFF)
                        .putParcelableArrayListExtra(ARG_EXTRA, list)

        @JvmStatic
        fun crtIntent(ctx: Context?, list: ArrayList<Crt>): Intent =
                Intent(ctx, SubjectActivity::class.java)
                        .putExtra(ARG_FLAG, FLAG_CRT)
                        .putParcelableArrayListExtra(ARG_EXTRA, list)

        @JvmStatic
        fun progressIntent(ctx: Context?, subjectId: Long): Intent =
                Intent(ctx, SubjectActivity::class.java)
                        .putExtra(ARG_FLAG, FLAG_PROGRESS)
                        .putExtra(ARG_EXTRA, subjectId)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_subject)
        val flag = intent.getStringExtra(ARG_FLAG)
        if (savedInstanceState == null) {
            val fragment = when (flag) {
                FLAG_STAFF -> SubjectStaffFragment.newIntent(intent.getParcelableArrayListExtra(ARG_EXTRA))
                FLAG_CRT -> SubjectCrtFragment.newIntent(intent.getParcelableArrayListExtra(ARG_EXTRA))
                FLAG_PROGRESS -> SubjectProgressFragment.newIntent(intent.getLongExtra(ARG_EXTRA, -1L))
                else -> null
            }

            fragment?.let { loadRootFragment(R.id.fl, it) } ?: finish()
        }
    }
}
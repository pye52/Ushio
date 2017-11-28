package com.kanade.ushio.ui.subject_main

import android.content.Context
import android.support.v7.widget.AppCompatSpinner
import android.view.View
import android.widget.EditText
import com.afollestad.materialdialogs.MaterialDialog
import com.kanade.ushio.R
import com.kanade.ushio.api.responses.GradeResponse
import com.xw.repo.BubbleSeekBar
import android.widget.ArrayAdapter
import android.widget.AdapterView
import com.blankj.utilcode.util.LogUtils

class SubjectGradeDialog(ctx: Context, callback: MaterialDialog.SingleButtonCallback) {
    private var view: View? = null
    private var chooseStr: String = ""
    private var dialog: MaterialDialog = MaterialDialog.Builder(ctx)
            .title(R.string.title_grade)
            .customView(R.layout.dialog_subject_grade, true)
            .positiveText(R.string.certain)
            .negativeText(R.string.cancel)
            .cancelable(false)
            .onPositive(callback)
            .build()

    private lateinit var gradeSp: AppCompatSpinner
    private lateinit var ratingBar: BubbleSeekBar
    private lateinit var commentEt: EditText
    private lateinit var adapter: ArrayAdapter<String>

    init {
        view = dialog.customView as View
        view?.let { view ->
            gradeSp = view.findViewById(R.id.subject_grade_sp)
            ratingBar = view.findViewById(R.id.subject_grade_sb)
            commentEt = view.findViewById(R.id.subject_grade_et)

            adapter = ArrayAdapter<String>(ctx, android.R.layout.simple_spinner_item, ctx.resources.getStringArray(R.array.grade_spinner))
            adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice)
            gradeSp.adapter = adapter
            gradeSp.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(arg0: AdapterView<*>, arg1: View, position: Int, arg3: Long) {
                    LogUtils.d("GradeDialog", "选择位置: $position")
                    chooseStr = when (position) {
                        0 -> "wish"
                        1 -> "collect"
                        2 -> "do"
                        3 -> "on_hold"
                        4 -> "dropped"
                        else -> ""
                    }
                }

                override fun onNothingSelected(arg0: AdapterView<*>) {
                    chooseStr = ""
                }
            }
        }
    }

    fun show() {
        if (!dialog.isShowing) dialog.show()
    }

    fun setDatas(response: GradeResponse) {
        when (response.status.id) {
            1 -> gradeSp.setSelection(0)
            2 -> gradeSp.setSelection(1)
            3 -> gradeSp.setSelection(2)
            4 -> gradeSp.setSelection(3)
            5 -> gradeSp.setSelection(4)
            else -> {}
        }
        ratingBar.setProgress(response.rating.toFloat())
        commentEt.setText(response.comment)
    }

    fun getStatus(): String = chooseStr

    fun getRating(): Int =
            ratingBar.progress

    fun getComment(): String =
            commentEt.text.toString()
}
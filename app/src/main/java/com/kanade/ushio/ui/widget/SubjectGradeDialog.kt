package com.kanade.ushio.ui.widget

import android.app.AlertDialog
import android.app.Dialog
import android.app.DialogFragment
import android.content.DialogInterface
import android.os.Bundle
import android.support.v7.widget.AppCompatSpinner
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import com.xw.repo.BubbleSeekBar
import com.jakewharton.rxbinding2.widget.RxTextView
import com.kanade.ushio.R
import io.reactivex.disposables.CompositeDisposable

class SubjectGradeDialog : DialogFragment(), DialogInterface.OnClickListener {
    private var listener: GradeDialogOnClickListener? = null

    private lateinit var actionSp: AppCompatSpinner
    private lateinit var ratingBar: BubbleSeekBar
    private lateinit var commentEt: EditText
    private lateinit var tagsEt: EditText
    private lateinit var privacyCb: CheckBox

    private var action = ""
    private var comment = ""
    private var rating = 0
    private var tags = ""
    private var privacy = 0

    private lateinit var adapter: ArrayAdapter<String>

    private val disposable = CompositeDisposable()

    fun setListener(listener: GradeDialogOnClickListener) {
        this.listener = listener
    }

    fun setAction(action: String): SubjectGradeDialog {
        this.action = action
        return this
    }

    fun setRating(rating: Int): SubjectGradeDialog {
        this.rating = rating
        return this
    }

    fun setComment(comment: String): SubjectGradeDialog {
        this.comment = comment
        return this
    }

    fun setTags(tags: List<String>): SubjectGradeDialog {
        this.tags = tags.joinToString(" ")
        return this
    }

    fun setPrivacy(privacy: Int): SubjectGradeDialog {
        this.privacy = privacy
        return this
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // 加载自定义界面
        val view = LayoutInflater.from(activity).inflate(R.layout.dialog_subject_grade, null)

        actionSp = view.findViewById(R.id.sp)
        ratingBar = view.findViewById(R.id.bsb)
        commentEt = view.findViewById(R.id.comment_et)
        tagsEt = view.findViewById(R.id.tag_et)
        privacyCb = view.findViewById(R.id.privacy_cb)

        disposable.add(
                RxTextView.textChanges(commentEt)
                        .subscribe {
                            if (commentEt.error != null) {
                                commentEt.error = null
                            }
                        }
        )
        adapter = ArrayAdapter(activity, android.R.layout.simple_spinner_item, activity.resources.getStringArray(R.array.grade_spinner))
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice)

        actionSp.adapter = adapter
        actionSp.onItemSelectedListener = selectedListener

        val position = when (action) {
            "wish" -> 0
            "collect" -> 1
            "do" -> 2
            "on_hold" -> 3
            "dropped" -> 4
            else -> 0
        }
        actionSp.setSelection(position)
        ratingBar.setProgress(rating.toFloat())
        commentEt.setText(comment)
        tagsEt.setText(tags)
        privacyCb.isChecked = privacy == 0

        val builder = AlertDialog.Builder(activity)
                .setView(view)
                .setTitle(R.string.dialog_grade_title)
                .setCancelable(false)
                .setPositiveButton(R.string.confirm, this)
                .setNegativeButton(R.string.cancel, null)
        return builder.create()
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.dispose()
    }

    override fun onClick(dialog: DialogInterface, position: Int) {
        if (commentEt.text.length > 200) {
            commentEt.setError(activity.getString(R.string.dialog_grade_comment_error), null)
            return
        }

        val privacy = if (privacyCb.isChecked) 0 else 1
        listener?.onClick(action, commentEt.text.toString(), tagsEt.text.toString(), ratingBar.progress, privacy)
    }

    interface GradeDialogOnClickListener {
        fun onClick(status: String, comment: String, tags: String, rating: Int, privacy: Int)
    }

    private val selectedListener = object : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(adapter: AdapterView<*>, view: View, position: Int, arg3: Long) {
            action = when (position) {
                0 -> "wish"
                1 -> "collect"
                2 -> "do"
                3 -> "on_hold"
                4 -> "dropped"
                else -> ""
            }
        }

        override fun onNothingSelected(arg0: AdapterView<*>) {
            action = ""
        }
    }
}
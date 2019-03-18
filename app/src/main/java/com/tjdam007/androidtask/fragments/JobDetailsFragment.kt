package com.tjdam007.androidtask.fragments

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.RoundRectShape
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.tjdam007.androidtask.R
import com.tjdam007.androidtask.activities.JobAppliedActivity
import kotlinx.android.synthetic.main.bottomsheet_job_detalis.*

class JobDetailsFragment : BottomSheetDialogFragment() {
    companion object {
        const val TAG = "JobDetailsFragment"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.bottomsheet_job_detalis, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rootView.post {
            val drawable = ShapeDrawable()
            val r = resources.getDimension(R.dimen.dp5)
            drawable.shape = RoundRectShape(floatArrayOf(r, r, r, r, 0f, 0f, 0f, 0f), null, null)
            drawable.paint.color = ContextCompat.getColor(context!!, R.color.blue)
            rootView.background = drawable
        }
        callButton.post {
            val r = (callButton.measuredHeight / 2).toFloat()
            val shapeDrawable = ShapeDrawable()
            shapeDrawable.shape = RoundRectShape(floatArrayOf(r, r, r, r, r, r, r, r), null, null)
            shapeDrawable.paint.color = Color.WHITE
            callButton.background = shapeDrawable
        }

        imageClose.setOnClickListener {
            dismiss()
        }

        chipCancel.setOnClickListener {
            dismiss()
        }

        chipContinue.setOnClickListener {
            startActivity(Intent(context, JobAppliedActivity::class.java))
            dismiss()
        }
    }

    @SuppressLint("RestrictedApi")
    override fun setupDialog(dialog: Dialog, style: Int) {
        super.setupDialog(dialog, style)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }
}
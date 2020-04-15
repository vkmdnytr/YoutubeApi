package com.example.youtube.common

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatTextView
import com.example.youtube.R
import kotlinx.android.synthetic.main.error_layout.view.*

class YouTubeErrorView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet,
    defStyleRes: Int = 0
) : LinearLayout(context, attributeSet, defStyleRes) {

    private var errorRepeatTryTextView: AppCompatTextView
    private var errorImageView: ImageView
    init {
        View.inflate(context, R.layout.error_layout, this)
        errorRepeatTryTextView = errorTextView
        errorImageView=errorImage
    }

    fun setInfoText(text: String) {
        errorRepeatTryTextView.text = text
    }

    override fun setOnClickListener(l: OnClickListener?) {
        errorRepeatTryTextView.setOnClickListener(l)
        errorImageView.setOnClickListener(l)
    }

}
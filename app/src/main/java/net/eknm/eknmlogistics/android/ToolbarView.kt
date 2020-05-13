package net.eknm.eknmlogistics.android

import android.content.Context
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import kotlinx.android.synthetic.main.toolbar.view.*
import net.eknm.eknmlogistics.R

class ToolbarView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : ConstraintLayout(context, attrs, defStyle) {
    init {
        val inflater = LayoutInflater.from(context)
        inflater.inflate(R.layout.toolbar, this, true)
        attrs?.let { parseAttributes(it) }
    }

    private fun parseAttributes(attrs: AttributeSet) {
        context.theme.obtainStyledAttributes(attrs, R.styleable.ToolbarView, 0, 0).apply {
            try {
                leftActionIconDrawable = getDrawable(R.styleable.ToolbarView_leftActionIconDrawable)
                rightActionIconDrawable =
                    getDrawable(R.styleable.ToolbarView_rightActionIconDrawable)

                val leftActionTint =
                    getColor(R.styleable.ToolbarView_toolbarLeftActionIconTint, DEFAULT_ICON_TINT)
                if (leftActionTint != DEFAULT_ICON_TINT) {
                    setLeftActionIconTint(leftActionTint)
                }

                val rightActionTint =
                    getColor(R.styleable.ToolbarView_toolbarRightActionIconTint, DEFAULT_ICON_TINT)
                if (rightActionTint != DEFAULT_ICON_TINT) {
                    setRightActionIconTint(rightActionTint)
                }

                val titleColor =
                    getColor(R.styleable.ToolbarView_titleTextColor, DEFAULT_ICON_TINT)
                setTitleTextColor(titleColor)
                text = getString(R.styleable.ToolbarView_titleText) ?: ""
            } finally {
                recycle()
            }
        }
    }

    var leftActionIconDrawable: Drawable?
        get() = leftActionIcon.drawable
        set(value) {
            leftActionIcon.setImageDrawable(value)
            leftActionIcon.visibility = if (value == null) View.GONE else View.VISIBLE
        }

    var rightActionIconDrawable: Drawable?
        get() = rightActionIcon.drawable
        set(value) {
            rightActionIcon.setImageDrawable(value)
            rightActionIcon.visibility = if (value == null) View.GONE else View.VISIBLE
        }

    fun setTitleTextColor(color: Int) {
        titleText.setTextColor(color)
    }

    fun setLeftActionIconTint(tintColor: Int) {
        leftActionIcon.applyTint(tintColor)
    }

    fun setRightActionIconTint(tintColor: Int) {
        rightActionIcon.applyTint(tintColor)
    }

    fun setLeftActionIconClickListener(listener: ((View) -> Unit)) {
        leftActionIcon.setOnClickListener(listener)
    }

    var text: CharSequence
        get() = titleText.text ?: ""
        set(value) {
            titleText.text = value
        }

    private fun ImageView.applyTint(tintColor: Int) {
        setColorFilter(tintColor, PorterDuff.Mode.SRC_IN)
    }

    companion object {
        const val DEFAULT_ICON_TINT = 0
    }
}
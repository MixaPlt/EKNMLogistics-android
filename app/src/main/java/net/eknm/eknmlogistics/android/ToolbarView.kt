package net.eknm.eknmlogistics.android

import android.content.Context
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import net.eknm.eknmlogistics.R
import net.eknm.eknmlogistics.databinding.ToolbarBinding

class ToolbarView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : ConstraintLayout(context, attrs, defStyle) {

    private val binding: ToolbarBinding

    init {
        val inflater = LayoutInflater.from(context)
        binding = ToolbarBinding.inflate(inflater, this)
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
        get() = binding.leftActionIcon.drawable
        set(value) {
            binding.leftActionIcon.setImageDrawable(value)
            binding.leftActionIcon.visibility = if (value == null) View.GONE else View.VISIBLE
        }

    var rightActionIconDrawable: Drawable?
        get() = binding.rightActionIcon.drawable
        set(value) {
            binding.rightActionIcon.setImageDrawable(value)
            binding.rightActionIcon.visibility = if (value == null) View.GONE else View.VISIBLE
        }

    fun setTitleTextColor(color: Int) {
        binding.titleText.setTextColor(color)
    }

    fun setLeftActionIconTint(tintColor: Int) {
        binding.leftActionIcon.applyTint(tintColor)
    }

    fun setRightActionIconTint(tintColor: Int) {
        binding.rightActionIcon.applyTint(tintColor)
    }

    fun setLeftActionIconClickListener(listener: ((View) -> Unit)) {
        binding.leftActionIcon.setOnClickListener(listener)
    }

    var text: CharSequence
        get() = binding.titleText.text ?: ""
        set(value) {
            binding.titleText.text = value
        }

    private fun ImageView.applyTint(tintColor: Int) {
        setColorFilter(tintColor, PorterDuff.Mode.SRC_IN)
    }

    companion object {
        const val DEFAULT_ICON_TINT = 0
    }
}
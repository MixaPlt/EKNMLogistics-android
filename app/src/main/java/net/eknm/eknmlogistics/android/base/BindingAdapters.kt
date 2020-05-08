package net.eknm.eknmlogistics.android.base

import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop

@BindingAdapter("drawableId")
fun bindDrawableIdToImageView(imageView: ImageView, drawableId: Int) {
    if (drawableId == 0) {
        return
    }

    imageView.setImageDrawable(imageView.context.getDrawable(drawableId))
}

@BindingAdapter("goneIfFalse")
fun goneIfFalse(view: View, shouldBeVisible: Boolean) {
    view.visibility = if (shouldBeVisible) View.VISIBLE else View.GONE
}

@BindingAdapter("isEnabled")
fun bindImageButtonIsEnabled(imageButton: ImageButton, isEnabled: Boolean?) {
    imageButton.isEnabled = isEnabled ?: true
}

@BindingAdapter("avatarUrl")
fun bindAvatarUrlToImageView(imageView: ImageView, avatarUrl: String) {
    Glide.with(imageView.context)
        .load(avatarUrl)
        .transform(CircleCrop())
        .into(imageView)
}

@BindingAdapter("format", "argId")
fun setFormattedText(textView: TextView, format: String, argId: Int) {
    if (argId == 0) return
    textView.text = String.format(format, textView.resources.getString(argId))
}
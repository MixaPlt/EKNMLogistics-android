package net.eknm.eknmlogistics.android.base

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.annotation.StringRes

class HintSpinnerAdapter<T>(
    context: Context,
    private var itemList: List<T>,
    private val hintMessage: String,
    private val isOptional: Boolean = false,
    private val contentToStringConverter: (T) -> String = { it.toString() }
) :
    ArrayAdapter<T>(context, 0, itemList) {

    constructor(
        context: Context,
        itemList: List<T>,
        @StringRes
        hintMessageResource: Int,
        isOptional: Boolean = false,
        contentToStringConverter: (T) -> String = { it.toString() }
    ) : this(
        context,
        itemList,
        context.getString(hintMessageResource),
        isOptional,
        contentToStringConverter
    )

    private val dropDownViewResource get() = android.R.layout.simple_dropdown_item_1line

    override fun getCount(): Int {
        return itemList.size + 1
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val textView = (convertView ?: LayoutInflater.from(parent.context).inflate(
            dropDownViewResource,
            parent,
            false
        ))
            as TextView
        setupContentView(textView, position)
        return textView
    }

    private fun setupContentView(textView: TextView, position: Int) {
        textView.text = if (position == 0) "" else contentToStringConverter(itemList[position - 1])
        textView.hint = if (position == 0) hintMessage else ""
    }

    override fun isEnabled(position: Int): Boolean {
        return if (position == 0 && !isOptional) false else super.isEnabled(position)
    }

    override fun getItem(position: Int): T? {
        return if (position == 0) null else itemList[position - 1]
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val listItem = convertView ?: LayoutInflater.from(parent.context)
            .inflate(android.R.layout.simple_spinner_item, parent, false)
        setupContentView(listItem!!.findViewById(android.R.id.text1) as TextView, position)
        return listItem
    }

    fun resetContent(newItems: List<T>) {
        itemList = newItems
    }
}
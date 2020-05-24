package net.eknm.eknmlogistics.android.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import net.eknm.eknmlogistics.BR

abstract class BaseRecyclerAdapter<T> : RecyclerView.Adapter<BaseViewHolder<T>>() {

    protected val items = ArrayList<T>()

    protected open val detectMoves: Boolean = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<T> {
        val inflater = LayoutInflater.from(parent.context)

        val viewBinding: ViewDataBinding =
            DataBindingUtil.inflate(inflater, viewType, parent, false)

        return createViewHolder(viewBinding, viewType)
    }

    protected open fun createViewHolder(
        binding: ViewDataBinding,
        viewType: Int
    ): BaseViewHolder<T> =
        BaseViewHolder(binding)

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: BaseViewHolder<T>, position: Int) {
        val item = items[position]

        holder.bind(item)
    }

    override fun getItemViewType(position: Int): Int {
        return getLayoutIdForPosition(position)
    }

    fun updateData(updatedItems: List<T>) {
        val diffUtilCallback = createDiffUtilCallback(items, updatedItems)

        diffUtilCallback?.let {
            val diffResult = DiffUtil.calculateDiff(it, detectMoves)

            items.clear()
            items.addAll(updatedItems)

            diffResult.dispatchUpdatesTo(this)

            return
        }

        items.clear()
        items.addAll(updatedItems)
        notifyDataSetChanged()
    }

    open fun createDiffUtilCallback(
        items: List<T>,
        updatedItems: List<T>
    ): BaseDiffUtilCallback<T>? = null

    abstract fun getLayoutIdForPosition(position: Int): Int

    protected val RecyclerView.ViewHolder.item: T?
        get() = when (val adapterPosition = adapterPosition) {
            RecyclerView.NO_POSITION -> null
            else -> items[adapterPosition]
        }
}

open class BaseViewHolder<T>(binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {

    protected val itemBinding = binding

    open fun bind(item: T) {
        itemBinding.setVariable(BR.item, item)
        itemBinding.executePendingBindings()
    }
}

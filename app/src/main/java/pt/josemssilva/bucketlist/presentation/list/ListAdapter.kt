package pt.josemssilva.bucketlist.presentation.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.list_item.view.*
import pt.josemssilva.bucketlist.R
import pt.josemssilva.bucketlist.data.entities.Item

class ListAdapter(
    private val listener: Listener? = null
) : RecyclerView.Adapter<ListAdapter.Holder>() {

    interface Listener {
        fun itemClicked(item: Item)
    }

    var items: List<Item> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item, parent, false)

        return Holder(v)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(items[position])
    }

    inner class Holder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(item: Item) {
            itemView.itemId.text = item.id
            itemView.description.text = item.description
        }

        override fun onClick(v: View?) {
            listener?.itemClicked(items[adapterPosition])
        }
    }
}
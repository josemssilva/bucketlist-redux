package pt.josemssilva.bucketlist.modules.items

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.list_item.view.*
import pt.josemssilva.bucketlist.R
import pt.josemssilva.bucketlist.data.entities.Item

class ListAdapter(
    private val itemClick: (Item) -> Unit,
    private val longItemClick: (Item) -> Unit
) : RecyclerView.Adapter<ListAdapter.Holder>() {

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

    inner class Holder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener, View.OnLongClickListener {

        init {
            itemView.setOnClickListener(this)
            itemView.setOnLongClickListener(this)
        }

        fun bind(item: Item) {
            itemView.itemId.text = item.id
            itemView.description.text = item.description

            itemView.quantity.text = "%s %s".format(
                item.quantity.value.toString(),
                itemView.context.getString(item.quantity.unit.textResource())
            )
        }

        override fun onClick(v: View?) {
            itemClick(items[adapterPosition])
        }

        override fun onLongClick(v: View?): Boolean {
            longItemClick(items[adapterPosition])
            return true
        }
    }
}
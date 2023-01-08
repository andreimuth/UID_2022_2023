import android.annotation.SuppressLint
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.project.OnItemClick
import com.example.project.R
import com.example.project.databinding.ChatItemBinding
import com.example.project.models.Chat
import com.example.project.models.ChatStatus
import com.example.project.models.Post

class ChatsAdapter(private var dataSource: List<Chat>, private val usernameLoggedIn: String, val onItemClick: OnItemClick) :
    RecyclerView.Adapter<ChatsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.chat_item, parent, false)
        return ViewHolder(ChatItemBinding.bind(view))
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.setOnClickListener{
            onItemClick.onItemClick(position, it)
        }

        if(dataSource[position].usernameFrom == usernameLoggedIn) {
            holder.username.text = dataSource[position].usernameTo
            holder.text.text = "You: " + dataSource[position].text
        } else {
            holder.username.text = dataSource[position].usernameFrom
            holder.text.text = dataSource[position].text
            if(dataSource[position].status == ChatStatus.SEND) {
                holder.text.setTypeface(null, Typeface.BOLD_ITALIC);
            } else {
                holder.text.setTypeface(null, Typeface.NORMAL);
            }
        }

        holder.date.text = dataSource[position].dateSend
    }

    fun submitList(posts: List<Chat>) {
        dataSource = posts
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return dataSource.size
    }

    class ViewHolder(binding: ChatItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val userIcon: ImageView = binding.userIcon
        val username: TextView = binding.username
        val date: TextView = binding.date
        val text: TextView = binding.text
    }

}

interface OnItemClick {
    fun onItemClick(position:Int, v: View, v2: View? = null)
    fun onItemLongClick(position:Int, v: View)
}
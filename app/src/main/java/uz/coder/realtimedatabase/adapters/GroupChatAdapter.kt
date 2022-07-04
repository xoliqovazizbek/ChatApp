package uz.coder.realtimedatabase.adaptersimport android.content.Contextimport android.view.LayoutInflaterimport android.view.ViewGroupimport android.widget.Toastimport androidx.recyclerview.widget.RecyclerViewimport uz.coder.realtimedatabase.databinding.FriendMessageItemBindingimport uz.coder.realtimedatabase.databinding.MyMessageItemBindingimport uz.coder.realtimedatabase.models.Messageclass GroupChatAdapter(    var context: Context,    private var list: ArrayList<Message>,    private var currentUser: String) :    RecyclerView.Adapter<RecyclerView.ViewHolder>() {    inner class FromVh(var binding: MyMessageItemBinding) :        RecyclerView.ViewHolder(binding.root) {        fun onBind(chat: Message) {//            itemView.animation = AnimationUtils.loadAnimation(binding.root.context, R.anim.recy_anim)            binding.sendermessage.text = chat.message            binding.timeofmessage.text = chat.date        }    }    inner class ToVh(var binding: FriendMessageItemBinding) :        RecyclerView.ViewHolder(binding.root) {        fun onBind(chat: Message) {//            itemView.animation = AnimationUtils.loadAnimation(binding.root.context, R.anim.recy_anim)            binding.sendermessage.text = chat.message            binding.timeofmessage.text = chat.date        }    }    override fun getItemCount(): Int = list.size    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {        if (viewType == 1) {            return FromVh(                MyMessageItemBinding.inflate(                    LayoutInflater.from(parent.context),                    parent,                    false                )            )        } else {            return ToVh(                FriendMessageItemBinding.inflate(                    LayoutInflater.from(parent.context),                    parent,                    false                )            )        }    }    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {        if (getItemViewType(position) == 1) {            val fromVh = holder as FromVh            fromVh.onBind(list[position])        } else {            val toVh = holder as ToVh            toVh.onBind(list[position])        }    }    override fun getItemViewType(position: Int): Int {        var username = ""        for (c in currentUser) {            if (c != '@') {                username += c            }        }        if (list[position].username == username) {            return 1        } else {            return 2        }    }}
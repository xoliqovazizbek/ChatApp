package uz.coder.realtimedatabase.adaptersimport android.view.LayoutInflaterimport android.view.Viewimport android.view.ViewGroupimport androidx.recyclerview.widget.RecyclerViewimport com.squareup.picasso.Picassoimport uz.coder.realtimedatabase.databinding.ItemUserBindingimport uz.coder.realtimedatabase.models.Userclass UserAdapter(var list: List<User>, var onClick: OnClick) :    RecyclerView.Adapter<UserAdapter.ViewHolder>() {    inner class ViewHolder(private val itemBinding: ItemUserBinding) :        RecyclerView.ViewHolder(itemBinding.root) {        fun onBind(item: User) {            Picasso.get()                .load("https://www.nmcnagpur.gov.in/grievance/public//images/user/user-thumb.jpg")                .into(itemBinding.userImg)            itemBinding.name.text = item.displayName            itemBinding.email.text = item.userName            itemBinding.root.setOnClickListener {                onClick.onItemClickListener(user = item)            }            if (item.online == true) {                itemBinding.online.visibility = View.VISIBLE            } else {                itemBinding.online.visibility = View.GONE            }        }    }    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {        return ViewHolder(            ItemUserBinding.inflate(                LayoutInflater.from(parent.context),                parent,                false            )        )    }    override fun getItemCount(): Int = list.size    override fun onBindViewHolder(holder: ViewHolder, position: Int) {        holder.onBind(list[position])    }    interface OnClick {        fun onItemClickListener(user: User)    }}
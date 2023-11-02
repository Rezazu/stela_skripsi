package com.example.stela_android.Fragmentsimport android.content.Contextimport android.content.Intentimport android.os.Bundleimport android.util.Logimport android.view.LayoutInflaterimport android.view.Viewimport android.view.ViewGroupimport android.widget.ProgressBarimport android.widget.TextViewimport androidx.fragment.app.Fragmentimport androidx.recyclerview.widget.LinearLayoutManagerimport androidx.recyclerview.widget.RecyclerViewimport com.example.stela_android.Rimport com.example.stela_android.Retrofit.Retrofitimport com.example.stela_android.Retrofit.Ticket.*import com.example.stela_android.Ticket.Ticketimport kotlinx.android.synthetic.main.fragment_tiket.view.*import kotlinx.android.synthetic.main.fragment_tiket_selesai.*import retrofit2.Callimport retrofit2.Callbackimport retrofit2.Responseclass TiketSelesaiFragment: Fragment(), OnTicketClickListener {    private val list = ArrayList<Tiket>()    override fun onCreateView(        inflater: LayoutInflater,        container: ViewGroup?,        savedInstanceState: Bundle?    ): View? {        return inflater.inflate(R.layout.fragment_tiket_selesai, container, false)    }    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {        super.onViewCreated(view, savedInstanceState)        list.clear()        getTickets()    }    private fun getTickets() {        val prefs = activity?.getSharedPreferences("my_shared_preff", Context.MODE_PRIVATE)        val token = prefs?.getString("token", "")        val retro = Retrofit.getRetroData(token!!).create(TiketApi::class.java)        retro.getTickets().enqueue(object: Callback<TiketResponse> {            override fun onResponse(call: Call<TiketResponse>, response: Response<TiketResponse>) {                response.body()?.data?.let { list.addAll(it) }                val tvEmptyTiketSelesai = view?.findViewById<TextView>(R.id.tv_empty_tiket_selesai)                if(response.body()?.success == null) {                    tvEmptyTiketSelesai?.visibility = View.VISIBLE                    tvEmptyTiketSelesai?.text = "Anda tidak memiliki layanan selesai"                } else {                    tvEmptyTiketSelesai?.visibility = View.GONE                    val rvTicket = view?.findViewById<RecyclerView>(R.id.rvTicketSelesai)                    rvTicket?.apply {                        layoutManager = LinearLayoutManager(activity)                        adapter = TiketSelesaiAdapter(context, list, this@TiketSelesaiFragment)                        val ticketAdapter = adapter                        rvTicketSelesai.adapter = ticketAdapter                        ticketAdapter?.notifyDataSetChanged()                    }                }                val progressBar = view?.findViewById<ProgressBar>(R.id.progressBar4)                progressBar?.visibility = View.GONE            }            override fun onFailure(call: Call<TiketResponse>, t: Throwable) {                Log.d("Ticket", "onFailure: " + t.message)            }        })    }    override fun onTicketItemClicked(position: Int) {        val intent = Intent(activity, Ticket::class.java)        intent.putExtra("id", list[position]?.id)        intent.putExtra("no_tiket", list[position]?.no_tiket)        intent.putExtra("keterangan", list[position]?.keterangan)        startActivity(intent)    }}
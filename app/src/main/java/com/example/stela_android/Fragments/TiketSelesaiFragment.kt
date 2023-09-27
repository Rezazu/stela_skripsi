package com.example.stela_android.Fragmentsimport android.content.Contextimport android.content.Intentimport android.os.Bundleimport android.util.Logimport android.view.LayoutInflaterimport android.view.Viewimport android.view.ViewGroupimport androidx.fragment.app.Fragmentimport androidx.recyclerview.widget.LinearLayoutManagerimport androidx.recyclerview.widget.RecyclerViewimport com.example.stela_android.Rimport com.example.stela_android.Retrofit.Retrofitimport com.example.stela_android.Retrofit.Ticket.*import com.example.stela_android.Ticket.Ticketimport kotlinx.android.synthetic.main.fragment_tiket_selesai.*import retrofit2.Callimport retrofit2.Callbackimport retrofit2.Responseclass TiketSelesaiFragment: Fragment(), OnTicketClickListener {    private val list = ArrayList<Tiket>()    private val layoutManager: RecyclerView.LayoutManager? = null    private val adapter: RecyclerView.Adapter<TiketSelesaiAdapter.TicketViewHolder>? = null    override fun onCreateView(        inflater: LayoutInflater,        container: ViewGroup?,        savedInstanceState: Bundle?    ): View? {        return inflater.inflate(R.layout.fragment_tiket_selesai, container, false)    }    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {        super.onViewCreated(view, savedInstanceState)        getTickets()    }    private fun getTickets() {        val prefs = activity?.getSharedPreferences("my_shared_preff", Context.MODE_PRIVATE)        val token = prefs?.getString("token", "")        val retro = Retrofit.getRetroData(token!!).create(TiketApi::class.java)        retro.getTickets().enqueue(object: Callback<TiketResponse> {            override fun onResponse(call: Call<TiketResponse>, response: Response<TiketResponse>) {                response.body()?.data?.let { list.addAll(it) }                if(response.body()?.success == null) {                    tv_empty_tiket_selesai.visibility = View.VISIBLE                    tv_empty_tiket_selesai.text = "Anda tidak memiliki layanan selesai"                } else {                    tv_empty_tiket_selesai.visibility = View.GONE                    rvTicketSelesai.apply {                        // set a LinearLayoutManager to handle Android                        // RecyclerView behavior                        layoutManager = LinearLayoutManager(activity)                        // set the custom adapter to the RecyclerView                        adapter = TiketSelesaiAdapter(context, list, this@TiketSelesaiFragment)                    }                }            }            override fun onFailure(call: Call<TiketResponse>, t: Throwable) {                Log.d("Ticket", "onFailure: " + t.message)            }        })    }    override fun onTicketItemClicked(position: Int) {        val intent = Intent(activity, Ticket::class.java)        intent.putExtra("id", list[position]?.id)        intent.putExtra("judul", list[position]?.keterangan)        intent.putExtra("kode_tiket", list[position]?.no_tiket)        intent.putExtra("tanggal_permintaan", list[position]?.tanggal_input)        intent.putExtra("nama", list[position]?.nama_pelapor)        intent.putExtra("jabatan", list[position]?.bagian_pelapor)        intent.putExtra("unit_kerja", list[position]?.unit_kerja_pelapor)        intent.putExtra("gedung", list[position]?.gedung_pelapor)        intent.putExtra("lantai", list[position]?.lantai_pelapor)        intent.putExtra("ruangan", list[position]?.ruangan_pelapor)        intent.putExtra("status", list[position]?.status)        intent.putExtra("keterangan", list[position]?.keterangan)        intent.putExtra("permasalahan_akhir", list[position]?.permasalahan_akhir)        intent.putExtra("solusi", list[position]?.solusi)        intent.putExtra("statusTiket", list[position]?.id_status_tiket)        intent.putExtra("rating", list[position]?.rating)        if(list[position]?.dokumen_lampiran != null) {            val sizeOfDokumenLampiran: Int? = list[position]?.dokumen_lampiran?.size            val dokumenLampiranNames: ArrayList<String> = ArrayList<String>()            val dokumenLampiranPaths: ArrayList<String> = ArrayList<String>()            for(nums in 0 until sizeOfDokumenLampiran!!) {                list[position]?.dokumen_lampiran?.get(nums)?.original_name?.let {                    dokumenLampiranNames.add(nums,                        it                    )                }                list[position]?.dokumen_lampiran?.get(nums)?.path?.let {                    dokumenLampiranPaths.add(nums,                        it                    )                }            }            intent.putExtra("dokumenLampiranNames", dokumenLampiranNames)            intent.putExtra("dokumenLampiranPaths", dokumenLampiranPaths)        } else {            intent.putExtra("dokumenLampiranNames", ArrayList<String>())            intent.putExtra("dokumenLampiranPaths", ArrayList<String>())        }        startActivity(intent)    }}
package br.com.alura.leilao.ui.recyclerview.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.alura.leilao.R
import br.com.alura.leilao.model.Leilao

class ListaLeilaoAdapter(private val context: Context) :
    RecyclerView.Adapter<ListaLeilaoAdapter.ViewHolder>() {

    private val leiloes = mutableListOf<Leilao>()
    private var onItemClickListener: OnItemClickListener? = null

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        this.onItemClickListener = onItemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewCriada = createView(parent)
        return ViewHolder(viewCriada)
    }

    private fun createView(parent: ViewGroup): View {
        return LayoutInflater.from(context).inflate(R.layout.item_leilao, parent, false)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(leiloes[position])
    }

    override fun getItemCount(): Int {
        return leiloes.size
    }

    fun atualiza(leiloes: List<Leilao>?) {
        this.leiloes.clear()
        leiloes?.let { this.leiloes.addAll(it) }
        notifyDataSetChanged()
    }

    interface OnItemClickListener {
        fun onItemClick(leilao: Leilao)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val descricao: TextView = itemView.findViewById(R.id.item_leilao_descricao)
        private val maiorLance: TextView = itemView.findViewById(R.id.item_leilao_maior_lance)
        private lateinit var leilao: Leilao

        init {
            itemView.setOnClickListener {
                onItemClickListener?.onItemClick(leilao)
            }
        }

        fun bind(leilao: Leilao) {
            this.leilao = leilao
            setLeilaoDetails(leilao)
        }

        private fun setLeilaoDetails(leilao: Leilao) {
            descricao.text = leilao.descricao
            maiorLance.text = leilao.maiorLanceFormatado
        }
    }
}
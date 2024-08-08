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

    private val leiloes: MutableList<Leilao> = ArrayList()
    private var onItemClickListener: OnItemClickListener? = null

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        this.onItemClickListener = onItemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewCriada = LayoutInflater.from(context).inflate(R.layout.item_leilao, parent, false)
        return ViewHolder(viewCriada)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val leilao = pegaLeilaoPorPosicao(position)
        holder.vincula(leilao)
    }

    override fun getItemCount(): Int {
        return leiloes.size
    }

    fun atualiza(leiloes: List<Leilao>?) {
        this.leiloes.clear()
        if (leiloes != null) {
            this.leiloes.addAll(leiloes)
        }
        notificaMudancaNoConjuntoDeDados()
    }

    private fun notificaMudancaNoConjuntoDeDados() {
        notifyDataSetChanged()
    }

    private fun pegaLeilaoPorPosicao(posicao: Int): Leilao {
        return leiloes[posicao]
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

        fun vincula(leilao: Leilao) {
            this.leilao = leilao
            descricao.text = leilao.descricao
            maiorLance.text = leilao.getMaiorLanceFormatado()
        }
    }
}

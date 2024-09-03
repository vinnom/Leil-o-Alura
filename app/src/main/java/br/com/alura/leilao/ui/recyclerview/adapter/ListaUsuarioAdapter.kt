package br.com.alura.leilao.ui.recyclerview.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.alura.leilao.R
import br.com.alura.leilao.model.Usuario

class ListaUsuarioAdapter(private val context: Context) :
    RecyclerView.Adapter<ListaUsuarioAdapter.ViewHolder>() {

    private val usuarios = mutableListOf<Usuario>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewCriada = LayoutInflater.from(context).inflate(R.layout.item_usuario, parent, false)
        return ViewHolder(viewCriada)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.vincula(usuarios[position])
    }

    override fun getItemCount(): Int {
        return usuarios.size
    }

    fun adiciona(usuarios: List<Usuario>) {
        for (usuario in usuarios) {
            adiciona(usuario)
        }
    }

    fun adiciona(usuario: Usuario) {
        usuarios.add(usuario)
        notifyItemInserted(itemCount - 1)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val idComUsuario: TextView = itemView.findViewById(R.id.item_usuario_id_com_nome)

        fun vincula(usuario: Usuario) {
            idComUsuario.text = usuario.toString()
        }
    }
}

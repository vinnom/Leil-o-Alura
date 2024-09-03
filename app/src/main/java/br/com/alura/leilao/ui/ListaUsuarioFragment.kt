package br.com.alura.leilao.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import br.com.alura.leilao.databinding.FragmentListaUsuarioBinding
import br.com.alura.leilao.model.Usuario
import br.com.alura.leilao.ui.dialog.NovoUsuarioDialog
import br.com.alura.leilao.ui.recyclerview.adapter.ListaUsuarioAdapter

class ListaUsuarioFragment : Fragment() {

    private var _binding: FragmentListaUsuarioBinding? = null
    private val binding = _binding!!
    private val listaAdapter by lazy { ListaUsuarioAdapter(requireContext()) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = run {
        _binding = FragmentListaUsuarioBinding.inflate(inflater, container, false)
        binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (isAdded && !isDetached) {
            requireActivity().actionBar?.title = "Usuários"
            binding.listaUsuarioRecicla.adapter = listaAdapter
            binding.listaUsuarioFabAdd.setOnClickListener {
                NovoUsuarioDialog(
                    context = requireContext(),
                    listener = object : NovoUsuarioDialog.UsuarioCriadoListener {
                        override fun criado(usuario: Usuario) {

                        }
                    }
                ).mostra()
            }
        }
    }

}
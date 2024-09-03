package br.com.alura.leilao.ui.view

import androidx.fragment.app.Fragment

const val CHAVE_LEILAO = "leilao"

fun Fragment.replaceFragment(containerId: Int, fragment: Fragment, tag: String) {
    parentFragmentManager.beginTransaction()
        .replace(containerId, fragment, tag)
        .commit()
}
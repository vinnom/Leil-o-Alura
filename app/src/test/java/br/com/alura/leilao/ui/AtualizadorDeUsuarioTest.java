package br.com.alura.leilao.ui;

import androidx.recyclerview.widget.RecyclerView;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import br.com.alura.leilao.database.dao.UsuarioDAO;
import br.com.alura.leilao.model.Usuario;
import br.com.alura.leilao.ui.recyclerview.adapter.ListaUsuarioAdapter;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AtualizadorDeUsuarioTest{

   @Mock
   private UsuarioDAO usarioDAO;
   @Mock
   private ListaUsuarioAdapter adapter;
   @Mock
   private RecyclerView recyclerView;

   @Test
   public void deve_AtualizarListaDeUsuarios_QuandoSalvaUsuario(){
      when(usarioDAO.salva(new Usuario("A"))).thenReturn(new Usuario(1, "A"));
      when(adapter.getItemCount()).thenReturn(1);
      new AtualizadorDeUsuario(usarioDAO, adapter, recyclerView).salva(new Usuario("A"));

      verify(usarioDAO).salva(new Usuario("A"));
      verify(adapter).adiciona(new Usuario(1, "A"));
      verify(recyclerView).smoothScrollToPosition(0);

   }

}
package br.com.alura.leilao.model;

import org.junit.Test;

import java.util.List;

import br.com.alura.leilao.exception.QuantidadeMaximaDeLancesException;
import br.com.alura.leilao.exception.UsuarioDeuLancesSeguidosException;
import br.com.alura.leilao.exception.ValorMenorQueOAnteriorException;

import static org.junit.Assert.assertEquals;

public class LeilaoTest {

	public static final Usuario USUARIO = new Usuario("usuario");
	public static final double DELTA = 0.001;
	public final Leilao LEILAO = new Leilao("teste");

	@Test
	public void deve_ConterDescricao() {
		String descricaoReal = LEILAO.getDescricao();

		assertEquals("teste", descricaoReal);
	}

	@Test
	public void deve_ConterLanceProposto() {
		LEILAO.propoe(new Lance(USUARIO, 100.0));

		assertEquals(1, LEILAO.getQuantidadeLances());
		assertEquals(100.0, LEILAO.getLances().get(0).getValor(), DELTA);
	}

	@Test
	public void deve_DevolverMaiorLance_QuandoInseridoUmValor() {
		LEILAO.propoe(new Lance(USUARIO, 100.0));

		double maiorLanceReal = LEILAO.getMaiorLance();

		assertEquals(100.0, maiorLanceReal, DELTA);
	}

	@Test
	public void deve_DevolverMaiorLance_QuandoInseridoMaisQueUmValor() {
		LEILAO.propoe(new Lance(USUARIO, 100.0));
		LEILAO.propoe(new Lance(new Usuario("u"), 200.0));

		double maiorLanceReal = LEILAO.getMaiorLance();

		assertEquals(200.0, maiorLanceReal, DELTA);
	}

	@Test
	public void deve_DevolverMenorLance_QuandoInseridoUmValor() {
		LEILAO.propoe(new Lance(USUARIO, 100.0));

		double menorLanceReal = LEILAO.getMenorLance();

		assertEquals(100.0, menorLanceReal, DELTA);
	}

	@Test
	public void deve_DevolverMenorLance_QuandoInseridoMaisQueUmValor() {
		LEILAO.propoe(new Lance(USUARIO, 100.0));
		LEILAO.propoe(new Lance(new Usuario("u"), 200.0));

		double maiorLanceReal = LEILAO.getMenorLance();

		assertEquals(100.0, maiorLanceReal, DELTA);
	}

	@Test
	public void deve_DevolverTresMaioresLances_QuandoInseridoMenosQueTresLances() {
		LEILAO.propoe(new Lance(USUARIO, 100.0));
		LEILAO.propoe(new Lance(new Usuario("u"), 200.0));

		List<Lance> tresMaioresLances = LEILAO.getTresMaioresLances();

		assertEquals(2, tresMaioresLances.size());
		assertEquals(200.0, tresMaioresLances.get(0).getValor(), DELTA);
		assertEquals(100.0, tresMaioresLances.get(1).getValor(), DELTA);
	}

	@Test
	public void deve_DevolverTresMaioresLances_QuandoInseridoTresLances() {
		LEILAO.propoe(new Lance(USUARIO, 100.0));
		LEILAO.propoe(new Lance(new Usuario("u"), 200.0));
		LEILAO.propoe(new Lance(USUARIO, 300.0));

		List<Lance> tresMaioresLances = LEILAO.getTresMaioresLances();

		assertEquals(3, tresMaioresLances.size());
		assertEquals(300.0, tresMaioresLances.get(0).getValor(), DELTA);
		assertEquals(200.0, tresMaioresLances.get(1).getValor(), DELTA);
		assertEquals(100.0, tresMaioresLances.get(2).getValor(), DELTA);
	}

	@Test
	public void deve_DevolverTresMaioresLances_QuandoInseridoMaisQueTresLances() {
		final Usuario U = new Usuario("u");
		LEILAO.propoe(new Lance(USUARIO, 100.0));
		LEILAO.propoe(new Lance(U, 200.0));
		LEILAO.propoe(new Lance(USUARIO, 300.0));
		LEILAO.propoe(new Lance(U, 400.0));

		List<Lance> tresMaioresLances = LEILAO.getTresMaioresLances();

		assertEquals(3, tresMaioresLances.size());
		assertEquals(400.0, tresMaioresLances.get(0).getValor(), DELTA);
		assertEquals(300.0, tresMaioresLances.get(1).getValor(), DELTA);
		assertEquals(200.0, tresMaioresLances.get(2).getValor(), DELTA);
	}

	@Test
	public void deve_ConterLanceInicializadoComZero() {
		assertEquals(1, LEILAO.getLances().size());
		assertEquals(0.0, LEILAO.getLances().get(0).getValor(), DELTA);
	}

	@Test(expected = ValorMenorQueOAnteriorException.class)
	public void deve_AdicionarLancesApenasEmOrdemCrescente() {
		LEILAO.propoe(new Lance(USUARIO, 400.0));
		LEILAO.propoe(new Lance(new Usuario("u"), 300.0));
	}

	@Test(expected = UsuarioDeuLancesSeguidosException.class)
	public void deve_AdicionarLancesApenas_QuandoUsuarioNaoRepeteLanceEmSeguida() {
		LEILAO.propoe(new Lance(USUARIO, 300.0));
		LEILAO.propoe(new Lance(USUARIO, 400.0));
	}

	@Test(expected = QuantidadeMaximaDeLancesException.class)
	public void deve_AdicionarNoMaximoCincoLancesDeUmMesmoUsuario() {
		final Usuario U = new Usuario("u");
		LEILAO.propoe(new Lance(USUARIO, 100.0));
		LEILAO.propoe(new Lance(U, 200.0));
		LEILAO.propoe(new Lance(USUARIO, 300.0));
		LEILAO.propoe(new Lance(U, 400.0));
		LEILAO.propoe(new Lance(USUARIO, 500.0));
		LEILAO.propoe(new Lance(U, 600.0));
		LEILAO.propoe(new Lance(USUARIO, 700.0));
		LEILAO.propoe(new Lance(U, 800.0));
		LEILAO.propoe(new Lance(USUARIO, 900.0));
		LEILAO.propoe(new Lance(U, 1000.0));
		LEILAO.propoe(new Lance(USUARIO, 1500.0));
	}
}
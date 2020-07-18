package br.com.alura.leilao.model;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

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
	public void deve_NaoConterLanceProposto(){
		assertEquals(0, LEILAO.getQuantidadeLances());
	}

	@Test
	public void deve_ConterLanceProposto(){
		LEILAO.propoe(new Lance(USUARIO, 100.0));

		assertEquals(1, LEILAO.getQuantidadeLances());
		assertEquals(100.0, LEILAO.getLances().get(0).getValor(), DELTA);
	}

	@Test
	public void deve_DevolverMaiorLance_QuandoInseridoUmValor(){
		LEILAO.propoe(new Lance(USUARIO, 100.0));

		double maiorLanceReal = LEILAO.getMaiorLance();

		assertEquals(100.0, maiorLanceReal, DELTA);
	}

	@Test
	public void deve_DevolverMaiorLance_QuandoInseridoMaisQueUmValor_OrdemCrescente(){
		LEILAO.propoe(new Lance(USUARIO, 100.0));
		LEILAO.propoe(new Lance(USUARIO, 200.0));

		double maiorLanceReal = LEILAO.getMaiorLance();

		assertEquals(200.0, maiorLanceReal, DELTA);
	}

	@Test
	public void deve_DevolverMaiorLance_QuandoInseridoMaisQueUmValor_OrdemDecrescente(){
		LEILAO.propoe(new Lance(USUARIO, 200.0));
		LEILAO.propoe(new Lance(USUARIO, 100.0));

		double maiorLanceReal = LEILAO.getMaiorLance();

		assertEquals(200.0, maiorLanceReal, DELTA);
	}

	@Test
	public void deve_DevolverMenorLance_QuandoInseridoUmValor(){
		LEILAO.propoe(new Lance(USUARIO, 100.0));

		double menorLanceReal = LEILAO.getMenorLance();

		assertEquals(100.0, menorLanceReal, DELTA);
	}

	@Test
	public void deve_DevolverMenorLance_QuandoInseridoMaisQueUmValor_OrdemCrescente(){
		LEILAO.propoe(new Lance(USUARIO, 100.0));
		LEILAO.propoe(new Lance(USUARIO, 200.0));

		double maiorLanceReal = LEILAO.getMenorLance();

		assertEquals(100.0, maiorLanceReal, DELTA);
	}

	@Test
	public void deve_DevolverMenorLance_QuandoInseridoMaisQueUmValor_OrdemDecrescente(){
		LEILAO.propoe(new Lance(USUARIO, 200.0));
		LEILAO.propoe(new Lance(USUARIO, 100.0));

		double menorLanceReal = LEILAO.getMenorLance();

		assertEquals(100.0, menorLanceReal, DELTA);
	}

	@Test
	public void deve_DevolverTresMaioresLances_QuandoNaoInseridoNenhumValor(){
		List<Lance> tresMaioresLances = LEILAO.getTresMaioresLances();

		assertEquals(0, tresMaioresLances.size());
	}

	@Test
	public void deve_DevolverTresMaioresLances_QuandoInseridoMenosQueTresLances_OrdemCrescente(){
		LEILAO.propoe(new Lance(USUARIO, 100.0));
		LEILAO.propoe(new Lance(USUARIO, 200.0));

		List<Lance> tresMaioresLances = LEILAO.getTresMaioresLances();

		assertEquals(2, tresMaioresLances.size());
		assertEquals(200.0, tresMaioresLances.get(0).getValor(), DELTA);
		assertEquals(100.0, tresMaioresLances.get(1).getValor(), DELTA);
	}

	@Test
	public void deve_DevolverTresMaioresLances_QuandoInseridoTresLances_OrdemCrescente(){
		LEILAO.propoe(new Lance(USUARIO, 100.0));
		LEILAO.propoe(new Lance(USUARIO, 200.0));
		LEILAO.propoe(new Lance(USUARIO, 300.0));

		List<Lance> tresMaioresLances = LEILAO.getTresMaioresLances();

		assertEquals(3, tresMaioresLances.size());
		assertEquals(300.0, tresMaioresLances.get(0).getValor(), DELTA);
		assertEquals(200.0, tresMaioresLances.get(1).getValor(), DELTA);
		assertEquals(100.0, tresMaioresLances.get(2).getValor(), DELTA);
	}

	@Test
	public void deve_DevolverTresMaioresLances_QuandoInseridoMaisQueTresLances_OrdemCrescente(){
		LEILAO.propoe(new Lance(USUARIO, 100.0));
		LEILAO.propoe(new Lance(USUARIO, 200.0));
		LEILAO.propoe(new Lance(USUARIO, 300.0));
		LEILAO.propoe(new Lance(USUARIO, 400.0));

		List<Lance> tresMaioresLances = LEILAO.getTresMaioresLances();

		assertEquals(3, tresMaioresLances.size());
		assertEquals(400.0, tresMaioresLances.get(0).getValor(), DELTA);
		assertEquals(300.0, tresMaioresLances.get(1).getValor(), DELTA);
		assertEquals(200.0, tresMaioresLances.get(2).getValor(), DELTA);
	}

	@Test
	public void deve_DevolverTresMaioresLances_QuandoInseridoMenosQueTresLances_OrdemDecrescente(){
		LEILAO.propoe(new Lance(USUARIO, 200.0));
		LEILAO.propoe(new Lance(USUARIO, 100.0));

		List<Lance> tresMaioresLances = LEILAO.getTresMaioresLances();

		assertEquals(2, tresMaioresLances.size());
		assertEquals(200.0, tresMaioresLances.get(0).getValor(), DELTA);
		assertEquals(100.0, tresMaioresLances.get(1).getValor(), DELTA);
	}

	@Test
	public void deve_DevolverTresMaioresLances_QuandoInseridoTresLances_OrdemDecrescente(){
		LEILAO.propoe(new Lance(USUARIO, 300.0));
		LEILAO.propoe(new Lance(USUARIO, 200.0));
		LEILAO.propoe(new Lance(USUARIO, 100.0));

		List<Lance> tresMaioresLances = LEILAO.getTresMaioresLances();

		assertEquals(3, tresMaioresLances.size());
		assertEquals(300.0, tresMaioresLances.get(0).getValor(), DELTA);
		assertEquals(200.0, tresMaioresLances.get(1).getValor(), DELTA);
		assertEquals(100.0, tresMaioresLances.get(2).getValor(), DELTA);
	}

	@Test
	public void deve_DevolverTresMaioresLances_QuandoInseridoMaisQueTresLances_OrdemDecrescente(){
		LEILAO.propoe(new Lance(USUARIO, 400.0));
		LEILAO.propoe(new Lance(USUARIO, 300.0));
		LEILAO.propoe(new Lance(USUARIO, 200.0));
		LEILAO.propoe(new Lance(USUARIO, 100.0));

		List<Lance> tresMaioresLances = LEILAO.getTresMaioresLances();

		assertEquals(3, tresMaioresLances.size());
		assertEquals(400.0, tresMaioresLances.get(0).getValor(), DELTA);
		assertEquals(300.0, tresMaioresLances.get(1).getValor(), DELTA);
		assertEquals(200.0, tresMaioresLances.get(2).getValor(), DELTA);
	}
}
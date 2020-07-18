package br.com.alura.leilao.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Leilao implements Serializable {

	private final String descricao;
	private final List<Lance> lances;

	public Leilao(String descricao) {
		this.descricao = descricao;
		this.lances = new ArrayList<>();
	}

	public void propoe(Lance lance) {
		lances.add(lance);
		Collections.sort(lances);
	}

	public String getDescricao() {
		return descricao;
	}

	public int getQuantidadeLances() {
		return lances.size();
	}

	public List<Lance> getLances() {
		return lances;
	}

	public double getMaiorLance() {
		if(lances.isEmpty()){
			return 0;
		}
		return lances.get(0).getValor();
	}

	public double getMenorLance() {
		if(lances.isEmpty()){
			return 0;
		}
		return lances.get(lances.size() - 1).getValor();
	}

	public List<Lance> getTresMaioresLances() {
		if(lances.isEmpty()){
			return lances;
		}
		if(lances.size() < 3){
			return lances.subList(0, lances.size());
		}
		return lances.subList(0, 3);
	}
}

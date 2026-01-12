package br.com.campominado.modelo;

import java.util.ArrayList;
import java.util.List;

import br.com.campominado.excecao.Explosao;

public class Campo {
	private final int linha;
	private final int coluna;
	
	private boolean coodernadaMarcada = false;
	private boolean coodernadaCavada = false;
	private boolean minado = false;
	
	private List<Campo> vizinhos = new ArrayList<>();
	
	Campo(int linha, int coluna){
		this.linha = linha;
		this.coluna = coluna;
	}
	
	boolean adicionarVizinho(Campo vizinho) {
		boolean linhaDiferente = linha != vizinho.linha;
		boolean colunaDiferente = coluna != vizinho.coluna;
		boolean diagonal = linhaDiferente && colunaDiferente;
		
		int deltaLinha = Math.abs(linha - vizinho.linha);
		int deltaColuna = Math.abs(coluna - vizinho.coluna);
		int deltaGeral = deltaColuna + deltaLinha;
		
		if(deltaGeral == 1 && !diagonal) {
			vizinhos.add(vizinho);
			return true;
		}else if(deltaGeral == 2 && diagonal) {
			vizinhos.add(vizinho);
			return true;
		}else {
			return false;
		}		
	}
	public void alternarMarcador() {
		if(!coodernadaCavada) {
			coodernadaMarcada = !coodernadaMarcada;
		}		
	}
	
	boolean abrir() {
		if(!coodernadaCavada && !coodernadaMarcada) {
			coodernadaCavada = true;
			
			if(minado) {
				throw new Explosao();
			}
			if(vizinhancaSegura()) {
				vizinhos.forEach(v -> v.abrir());
			}
			return true;
		}else {
		return false;
		}
	}
	boolean vizinhancaSegura() {
		 return vizinhos.stream().noneMatch(v -> v.minado);
	}
	
	public boolean iscoodernadaMarcada() {
		return coodernadaMarcada;
	}
	public boolean iscoodernadaCavada() {
		return coodernadaCavada;
	}
	
	void minar() {
		minado = true;
	}
	
	public boolean isMinado() {
		return minado;
	}
	public boolean iscoodernadaFechada() {
		return !iscoodernadaCavada();
	}
	

	public int getLinha() {
		return linha;
	}

	public int getColuna() {
		return coluna;
	}
	
	boolean coodernadarTravada() {
		boolean desvendado = !minado && coodernadaCavada;
		boolean protegida = minado && coodernadaMarcada;
		return desvendado || protegida;
	}
	
	long minasNaVizinhanca() {
		return vizinhos.stream().filter(v -> v.minado).count();
	}
	
	void reiniciar() {
		
		coodernadaMarcada = false;
		coodernadaCavada = false;
		minado = false;
	}
	public String toString() {
		if (coodernadaMarcada) {
			return "x";
		}else if(coodernadaCavada && minado){
			return "#";
		}else if(coodernadaCavada && minasNaVizinhanca() > 0){
			return Long.toString(minasNaVizinhanca());
		}else if(coodernadaCavada){
			return " "; 
		}else {
			return "?";
		}
	}
}

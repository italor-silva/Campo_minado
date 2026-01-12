package br.com.campominado.modelo;


import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class Tabuleiro {
	private int linhas;
	private int colunas;
	private int minas;
	
	private final List<Campo> campos = new ArrayList<>();

	public Tabuleiro(int linhas, int colunas, int minas) {
		this.linhas = linhas;
		this.colunas = colunas;
		this.minas = minas;
		
		gerarCampo();
		associarOsVizinhos();
		sortearAsMinas();
	}

	public void abrir(int linha, int coluna) {
		campos.parallelStream().filter(c -> c.getLinha() == linha && c.getColuna() == coluna).findFirst().ifPresent(c -> c.abrir());
		
	}
	public void alternarMarcador(int linha, int coluna) {
		campos.parallelStream().filter(c -> c.getLinha() == linha && c.getColuna() == coluna).findFirst().ifPresent(c -> c.alternarMarcador());
		
	}
	private void gerarCampo() {		
		for (int i = 0; i < linhas; i++) {
			for(int j = 0; j < colunas; j++) {
				campos.add(new Campo(i,j));
			}
		}
	}
	
	private void associarOsVizinhos() {
		for(Campo c1: campos) {
			for(Campo c2: campos) {
				c1.adicionarVizinho(c2);
			}
		}
	}
	private void sortearAsMinas() {		
		int minasArmadas = 0;
		Predicate<Campo> minado = c -> c.isMinado();
		do {
			minasArmadas = (int) campos.stream().filter(minado).count();
			int aleatorio = (int) (Math.random() * campos.size());
			campos.get(aleatorio).minar();
		}while(minasArmadas < minas);
	}
	
	public boolean objetivoAlcancado() {
		return campos.stream().allMatch(c -> c.coodernadarTravada());
	}
	
	public void reiniciar() {
		campos.forEach(c -> c.reiniciar());
		sortearAsMinas();
	}
	public String toString() {	
		StringBuilder sb = new StringBuilder();
		int c = 0;
		
		for(int i = 0; i < linhas; i++) {
			for(int j = 0; j < colunas; j++) {
				sb.append(campos.get(c));
				sb.append(" ");
				c++;
			}
			sb.append("\n");
		}
		return sb.toString();
	}
}

package br.com.cod3r.cm.modelo;


import java.util.ArrayList;

import java.util.List;
import java.util.function.Predicate;

import br.com.cod3r.cm.excecao.ExplosaoException;

public class Tabuleiro {

	private int linhas;
	private int colunas;
	private int minas;
	
	private final List<Campo> campos = new ArrayList<>();

	public Tabuleiro(int linhas, int colunas, int minas) {
		this.linhas = linhas;
		this.colunas = colunas;
		this.minas = minas;
		
		gerarCampos();
		associarVizinhos();
		sortearMinas();
	}
	
	public void abrir(int linha, int coluna) {
	    try {
	        campos.parallelStream()
	            .filter(c -> c.getLinha() == linha && c.getColuna() == coluna) // Removi o ponto e vírgula aqui
	            .findFirst()
	            .ifPresent(c -> c.abrir());
	    } catch (ExplosaoException e) { // Corrigi a exceção para ExplosaoException
	        campos.forEach(c -> c.setAberto(true));
	        throw e;
	    }
	}

	public void alternarMarcacao(int linha, int coluna) {
	    campos.parallelStream()
	        .filter(c -> c.getLinha() == linha && c.getColuna() == coluna) // Removi o ponto e vírgula aqui
	        .findFirst()
	        .ifPresent(c -> c.alternarMarcacao());
	}
	
	
	
		
	private void gerarCampos() {
		for (int linha = 0; linha < linhas; linha++) {
			for (int coluna = 0; coluna < colunas; coluna++) {
				campos.add(new Campo(linha, coluna));
				
			}
		}
	}
	private void associarVizinhos() {
		for(Campo c1: campos) {
			for(Campo c2: campos) {
			c1.adicionarVizinho(c2);
		}
	  }
	}
	private void sortearMinas() {	
		long minasArmadas = 0;
		Predicate<Campo> minado = c -> c.isMinado();
		
		do {
			minasArmadas = campos.stream().filter(minado).count();
			int aleatorio = (int) (Math.random() * campos.size());
			campos.get(aleatorio).minar();
		} while(minasArmadas < minas);
	}
		
		public boolean abjetivoAlcancado() {
			return campos.stream().allMatch(c -> c.abojetivoAlcancado());
		 
		
	}
		
		public void reiniciar() {
			campos.stream().forEach(c -> c.reiniciar());
			sortearMinas();
		}
		
		public String toString() {
       StringBuilder sb = new StringBuilder();
       
       
       sb.append(" ");
       for (int c = 0; c < colunas; c++) {
    	   sb.append(" ");
		   sb.append(c);
		   sb.append(" ");
       }
       
       sb.append("\n");
       
       int i = 0; 
       for (int l = 0; l < linhas; l++) {
    	   sb.append(l);
    	   sb.append(" ");
		
       for(int c = 0; c < linhas; c++) {
    	   
    		   sb.append(" ");
    		   sb.append(campos.get(i));
    		   sb.append(" ");
    		   i++;
       }
    	   sb.append("\n");
       }
       
   

       
       return sb.toString();
	
	
}
}

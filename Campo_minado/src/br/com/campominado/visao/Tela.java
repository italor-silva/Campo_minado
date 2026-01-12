package br.com.campominado.visao;

import java.util.Scanner;

import br.com.campominado.modelo.Tabuleiro;

public class Tela {
    
    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        Tabuleiro tabuleiro = new Tabuleiro(6, 6, 6);
        
        
        System.out.println(tabuleiro);
        
 
        boolean jogoAtivo = true;
        
        while(jogoAtivo && !tabuleiro.objetivoAlcancado() ) {
            escolha(teclado, tabuleiro);
            System.out.println(tabuleiro);
            
           
            if(tabuleiro.objetivoAlcancado()) {
                System.out.println("🎉 PARABÉNS! Você venceu!");
                jogoAtivo = false;
            }
        }
        
        teclado.close();
        System.out.println("Fim do jogo!");
    }
    
 
    private static void escolha(Scanner teclado, Tabuleiro tabuleiro) {
        int opcao = 0;
        String campo = "";
        
        System.out.println("\n=== MENU ===");
        System.out.println("1 - Marcar/Desmarcar campo");
        System.out.println("2 - Abrir campo");
        System.out.print("Escolha uma opção: ");
        
        opcao = teclado.nextInt();
        teclado.nextLine(); 
        
        System.out.print("Digite as coordenadas (linha,coluna): ");
        campo = teclado.nextLine();
        
      
        String[] partes = campo.split(",");
        
        if(partes.length != 2) {
            System.out.println("Formato inválido! Use: linha,coluna");
            return;
        }
        
        try {
            int linha = (Integer.parseInt(partes[0].trim()) - 1);
            int coluna = (Integer.parseInt(partes[1].trim()) - 1);
            
            if(opcao == 1) {
                tabuleiro.alternarMarcador(linha, coluna);
                System.out.println("Campo (" + Integer.parseInt(partes[0].trim()) + "," + Integer.parseInt(partes[1].trim()) + ") marcado/desmarcado");
            } else if(opcao == 2) {
                tabuleiro.abrir(linha, coluna);
                System.out.println("Campo (" + Integer.parseInt(partes[0].trim()) + "," + Integer.parseInt(partes[1].trim()) + ") aberto");
            } else {
                System.out.println("Opção inválida!");
            }
            
        } catch(NumberFormatException e) {
            System.out.println("Coordenadas inválidas! Digite números.");
        } catch(IndexOutOfBoundsException e) {
            System.out.println("Coordenadas fora do tabuleiro! Tabuleiro é 6x6 (1 a 6)");
        }
    }
}
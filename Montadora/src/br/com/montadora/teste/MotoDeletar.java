package br.com.montadora.teste;

import java.sql.Connection;

import java.util.Scanner;

import br.com.montadora.conexao.Conexao;
import br.com.montadora.dao.MotoDAO;

public class MotoDeletar {

    public static void main(String[] args) {

        try (Scanner scanner = new Scanner(System.in)) {

            System.out.println("--- Exclusão de Motos ---");
            System.out.println("Escolha uma opção:");
            System.out.println("1. Apagar TODAS as motos automaticamente");
            System.out.println("2. Excluir uma moto manualmente pelo ID");
            System.out.print("Digite sua opção (1 ou 2): ");

            String opcao = scanner.nextLine();

            Connection con = null;
            try {
                con = Conexao.abrirConexao();

                if (con != null) {
                    MotoDAO motoDao = new MotoDAO(con);

                    switch (opcao) {
                        case "1":
                            System.out.println("\nOpção 1 selecionada: Apagando TODAS as motos...");
                            System.out.print("TEM CERTEZA que deseja apagar TODAS as motos? (S/N): ");
                            String confirmacao = scanner.nextLine().trim().toUpperCase();
                            if (confirmacao.equals("S")) {
                                String resultadoTodos = motoDao.deletarSemWhere(); // Não lança SQLException
                                System.out.println("\nResultado da exclusão total: " + resultadoTodos);
                            } else {
                                System.out.println("Exclusão total cancelada.");
                            }
                            break;

                        case "2":
                            System.out.println("\nOpção 2 selecionada: Excluindo moto por ID...");
                            int idParaExcluir = 0;
                            boolean idValido = false;
                            while (!idValido) {
                                System.out.print("Digite o ID da moto a ser excluída: ");
                                try {
                                    idParaExcluir = Integer.parseInt(scanner.nextLine());
                                    if (idParaExcluir > 0) {
                                         idValido = true;
                                    } else {
                                         System.out.println("ID deve ser um número positivo.");
                                    }
                                } catch (NumberFormatException e) {
                                    System.out.println("Erro: Por favor, digite um número de ID válido.");
                                }
                            }
                            String resultadoId = motoDao.deletarPorId(idParaExcluir); // Não lança SQLException
                            System.out.println("\nResultado da exclusão por ID (" + idParaExcluir + "): " + resultadoId);
                            break;

                        default:
                            System.out.println("Opção inválida!");
                            break;
                    }
                } else {
                    System.out.println("Erro: Não foi possível abrir a conexão com o banco de dados.");
                }

            } catch (Exception e) { 
                System.err.println("Ocorreu um erro inesperado durante a exclusão: " + e.getMessage());
                e.printStackTrace();
            } finally {
                if (con != null) {
                    Conexao.fecharConexao(con);
                    System.out.println("\nConexão com o banco de dados fechada.");
                }
            }

        }
    }
}

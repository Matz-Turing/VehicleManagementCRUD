package br.com.montadora.teste;

import java.sql.Connection;
import java.util.Scanner;

import br.com.montadora.conexao.Conexao;
import br.com.montadora.dao.CarroDAO;

public class CarroDeletar {

    public static void main(String[] args) {

        try (Scanner scanner = new Scanner(System.in)) {

            System.out.println("--- Exclusão de Carros ---");
            System.out.println("Escolha uma opção:");
            System.out.println("1. Apagar TODOS os carros automaticamente");
            System.out.println("2. Excluir um carro manualmente pelo ID");
            System.out.print("Digite sua opção (1 ou 2): ");

            String opcao = scanner.nextLine();

            Connection con = null;
            try {
                con = Conexao.abrirConexao();

                if (con != null) {
                    CarroDAO carroDao = new CarroDAO(con);

                    switch (opcao) {
                        case "1":
                            System.out.println("\nOpção 1 selecionada: Apagando TODOS os carros...");
                            System.out.print("TEM CERTEZA que deseja apagar TODOS os carros? (S/N): ");
                            String confirmacao = scanner.nextLine().trim().toUpperCase();
                            if (confirmacao.equals("S")) {
                                String resultadoTodos = carroDao.deletarSemWhere();
                                System.out.println("\nResultado da exclusão total: " + resultadoTodos);
                            } else {
                                System.out.println("Exclusão total cancelada.");
                            }
                            break;

                        case "2":
                            System.out.println("\nOpção 2 selecionada: Excluindo carro por ID...");
                            int idParaExcluir = 0;
                            boolean idValido = false;
                            while (!idValido) {
                                System.out.print("Digite o ID do carro a ser excluído: ");
                                try {
                                    idParaExcluir = Integer.parseInt(scanner.nextLine());
                                    idValido = true;
                                } catch (NumberFormatException e) {
                                    System.out.println("Erro: Por favor, digite um número de ID válido.");
                                }
                            }
                            String resultadoId = carroDao.deletarPorId(idParaExcluir);
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

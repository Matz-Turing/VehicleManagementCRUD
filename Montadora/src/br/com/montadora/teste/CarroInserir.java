package br.com.montadora.teste;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

import br.com.montadora.conexao.Conexao;
import br.com.montadora.dao.CarroDAO;
import br.com.montadora.model.Carro;

public class CarroInserir {

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("--- Inserção de Carros ---");
            System.out.println("Escolha uma opção:");
            System.out.println("1. Inserir lista de carros predefinidos automaticamente");
            System.out.println("2. Inserir um carro manualmente");
            System.out.print("Digite sua opção (1 ou 2): ");

            String opcao = scanner.nextLine();

            Connection con = null;
            try {
                con = Conexao.abrirConexao();

                if (con != null) {
                    CarroDAO carroDao = new CarroDAO(con);

                    switch (opcao) {
                        case "1":
                            System.out.println("\nOpção 1 selecionada: Inserindo carros automaticamente...");
                            inserirCarrosAutomaticamente(carroDao);
                            break;
                        case "2":
                            System.out.println("\nOpção 2 selecionada: Inserindo carro manualmente...");
                            inserirCarroManualmente(carroDao, scanner);
                            break;
                        default:
                            System.out.println("Opção inválida!");
                            break;
                    }
                } else {
                    System.out.println("Erro: Não foi possível abrir a conexão com o banco de dados.");
                }

            } catch (SQLException e) {
                System.err.println("Erro de Banco de Dados durante a operação: " + e.getMessage());
                e.printStackTrace();
            } catch (Exception e) {
                System.err.println("Ocorreu um erro inesperado: " + e.getMessage());
                e.printStackTrace();
            } finally {
                if (con != null) {
                    Conexao.fecharConexao(con);
                    System.out.println("\nConexão com o banco de dados fechada.");
                }
            }

        }
    }

    private static void inserirCarrosAutomaticamente(CarroDAO carroDao) throws SQLException {
        Carro carro = new Carro();

        System.out.println("\nInserindo Carro 1 (Fiat Palio)...");
        carro.setMontadora("Fiat");
        carro.setNomeCarro("Palio");
        carro.setQuantidadePortas(4);
        System.out.println("Resultado: " + carroDao.inserir(carro));

        System.out.println("\nInserindo Carro 2 (Volkswagen Gol)...");
        carro.setMontadora("Volkswagen");
        carro.setNomeCarro("Gol");
        carro.setQuantidadePortas(2);
        System.out.println("Resultado: " + carroDao.inserir(carro));

        System.out.println("\nInserindo Carro 3 (Chevrolet Onix)...");
        carro.setMontadora("Chevrolet");
        carro.setNomeCarro("Onix");
        carro.setQuantidadePortas(4);
        System.out.println("Resultado: " + carroDao.inserir(carro));

        System.out.println("\nInserindo Carro 4 (Toyota Corolla)...");
        carro.setMontadora("Toyota");
        carro.setNomeCarro("Corolla");
        carro.setQuantidadePortas(4);
        System.out.println("Resultado: " + carroDao.inserir(carro));

        System.out.println("\nInserindo Carro 5 (Honda Civic)...");
        carro.setMontadora("Honda");
        carro.setNomeCarro("Civic");
        carro.setQuantidadePortas(4);
        System.out.println("Resultado: " + carroDao.inserir(carro));

        System.out.println("\nInserção automática concluída.");
    }

    private static void inserirCarroManualmente(CarroDAO carroDao, Scanner scanner) throws SQLException {
        Carro carroManual = new Carro();

        System.out.print("Digite a montadora: ");
        String montadora = scanner.nextLine();
        carroManual.setMontadora(montadora);

        System.out.print("Digite o nome do carro: ");
        String nomeCarro = scanner.nextLine();
        carroManual.setNomeCarro(nomeCarro);

        int quantidadePortas = 0;
        boolean entradaValida = false;
        while (!entradaValida) {
            System.out.print("Digite a quantidade de portas: ");
            try {
                quantidadePortas = Integer.parseInt(scanner.nextLine());
                if (quantidadePortas > 0) {
                    carroManual.setQuantidadePortas(quantidadePortas);
                    entradaValida = true;
                } else {
                    System.out.println("Quantidade de portas deve ser um número positivo.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Erro: Por favor, digite um número válido para a quantidade de portas.");
            }
        }

        System.out.println("\nInserindo carro com os dados fornecidos...");
        System.out.println("Resultado: " + carroDao.inserir(carroManual));
    }
}

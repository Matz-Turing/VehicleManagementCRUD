package br.com.montadora.teste;

import java.sql.Connection;
import java.util.Scanner;

import br.com.montadora.conexao.Conexao;
import br.com.montadora.dao.CarroDAO;
import br.com.montadora.model.Carro;

public class CarroAlterar {

    public static void main(String[] args) {

        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("--- Alteração de Carros ---");
            System.out.println("Escolha uma opção:");
            System.out.println("1. Alterar dados de um carro específico pelo ID");
            System.out.println("2. Alterar nome da montadora para todos os carros de uma montadora específica");
            System.out.print("Digite sua opção (1 ou 2): ");

            String opcao = scanner.nextLine();

            Connection con = null;
            try {
                con = Conexao.abrirConexao();

                if (con != null) {
                    CarroDAO carroDao = new CarroDAO(con);

                    switch (opcao) {
                        case "1":
                            alterarCarroPorId(carroDao, scanner);
                            break;
                        case "2":
                            alterarMontadoraGlobal(carroDao, scanner);
                            break;
                        default:
                            System.out.println("Opção inválida!");
                            break;
                    }
                } else {
                    System.out.println("Erro: Não foi possível abrir a conexão com o banco de dados.");
                }

            } catch (Exception e) {
                System.err.println("Ocorreu um erro inesperado durante a alteração: " + e.getMessage());
                e.printStackTrace();
            } finally {
                if (con != null) {
                    Conexao.fecharConexao(con);
                    System.out.println("\nConexão com o banco de dados fechada.");
                }
            }
        }
    }

    private static void alterarCarroPorId(CarroDAO carroDao, Scanner scanner) {
        int idParaAlterar = 0;
        boolean idValido = false;
        while (!idValido) {
            System.out.print("Digite o ID do carro a ser alterado: ");
            try {
                idParaAlterar = Integer.parseInt(scanner.nextLine());
                if (idParaAlterar > 0) {
                    idValido = true;
                } else {
                    System.out.println("ID deve ser um número positivo.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Erro: Por favor, digite um número de ID válido.");
            }
        }

        System.out.print("Digite a NOVA montadora: ");
        String novaMontadora = scanner.nextLine();

        System.out.print("Digite o NOVO nome do carro: ");
        String novoNomeCarro = scanner.nextLine();

        int novaQtdPortas = 0;
        boolean portasValidas = false;
        while (!portasValidas) {
            System.out.print("Digite a NOVA quantidade de portas: ");
            try {
                novaQtdPortas = Integer.parseInt(scanner.nextLine());
                if (novaQtdPortas >= 0) {
                    portasValidas = true;
                } else {
                    System.out.println("Quantidade de portas não pode ser negativa.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Erro: Por favor, digite um número válido para portas.");
            }
        }

        Carro carroParaAlterar = new Carro();
        carroParaAlterar.setId(idParaAlterar);
        carroParaAlterar.setMontadora(novaMontadora);
        carroParaAlterar.setNomeCarro(novoNomeCarro);
        carroParaAlterar.setQuantidadePortas(novaQtdPortas);

        String resultado = carroDao.alterarPorId(carroParaAlterar);
        System.out.println("\nResultado da alteração por ID (" + idParaAlterar + "): " + resultado);
    }

    private static void alterarMontadoraGlobal(CarroDAO carroDao, Scanner scanner) {
        System.out.print("Digite o nome ATUAL da montadora que deseja alterar: ");
        String montadoraAntiga = scanner.nextLine();

        System.out.print("Digite o NOVO nome para esta montadora: ");
        String montadoraNova = scanner.nextLine();

        String resultado = carroDao.alterarMontadora(montadoraAntiga, montadoraNova);
        System.out.println("\nResultado da alteração global da montadora: " + resultado);
    }
}

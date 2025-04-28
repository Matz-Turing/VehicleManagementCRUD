package br.com.montadora.teste;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import br.com.montadora.conexao.Conexao;
import br.com.montadora.dao.CarroDAO;
import br.com.montadora.model.Carro;

public class CarroSelecionar {

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("--- Seleção de Carros ---");
            System.out.println("Escolha uma opção:");
            System.out.println("1. Selecionar UM carro pelo ID");
            System.out.println("2. Selecionar TODOS os carros de uma MONTADORA específica");
            System.out.print("Digite sua opção (1 ou 2): ");

            String opcao = scanner.nextLine();

            Connection con = null;
            try {
                con = Conexao.abrirConexao();

                if (con != null) {
                    System.out.println("\nConexão aberta para teste de seleção de carros...");
                    CarroDAO carroDao = new CarroDAO(con);

                    switch (opcao) {
                        case "1":
                            System.out.println("\nOpção 1: Selecionar por ID");
                            selecionarCarroPorId(carroDao, scanner);
                            break;
                        case "2":
                            System.out.println("\nOpção 2: Selecionar por Montadora");
                            selecionarCarrosPorMontadora(carroDao, scanner);
                            break;
                        default:
                            System.out.println("Opção inválida!");
                            break;
                    }
                } else {
                    System.out.println("Erro ao abrir a conexão.");
                }
            } catch (SQLException e) {
                System.err.println("Ocorreu um erro de Banco de Dados durante a seleção: " + e.getMessage());
                e.printStackTrace();
            } catch (Exception e) {
                System.err.println("Ocorreu um erro inesperado no teste de seleção de carro: " + e.getMessage());
                e.printStackTrace();
            } finally {
                if (con != null) {
                    Conexao.fecharConexao(con);
                    System.out.println("\nConexão fechada após teste de seleção.");
                }
            }
        }
    }

    private static void selecionarCarroPorId(CarroDAO carroDao, Scanner scanner) throws SQLException {
        int idParaBuscar = 0;
        boolean idValido = false;
        while(!idValido) {
            System.out.print("Digite o ID do carro a ser buscado: ");
            try {
                idParaBuscar = Integer.parseInt(scanner.nextLine());
                if (idParaBuscar > 0) {
                    idValido = true;
                } else {
                    System.out.println("ID deve ser um número positivo.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Erro: Por favor, digite um número de ID válido.");
            }
        }

        Carro carroEncontrado = carroDao.selecionarPorId(idParaBuscar);

        if (carroEncontrado != null) {
            System.out.println("\nCarro encontrado:");
            System.out.println("  ID: " + carroEncontrado.getId());
            System.out.println("  Montadora: " + carroEncontrado.getMontadora());
            System.out.println("  Nome: " + carroEncontrado.getNomeCarro());
            System.out.println("  Portas: " + carroEncontrado.getQuantidadePortas());
        } else {
            System.out.println("\nNenhum carro encontrado com o ID " + idParaBuscar + ".");
        }
    }

    private static void selecionarCarrosPorMontadora(CarroDAO carroDao, Scanner scanner) throws SQLException {
        System.out.print("Digite o nome da MONTADORA a ser buscada: ");
        String montadoraParaBuscar = scanner.nextLine();

        List<Carro> carrosEncontrados = carroDao.selecionarPorMontadora(montadoraParaBuscar);

        if (carrosEncontrados.isEmpty()) {
            System.out.println("\nNenhum carro encontrado para a montadora '" + montadoraParaBuscar + "'.");
        } else {
            System.out.println("\nCarros encontrados para '" + montadoraParaBuscar + "' (" + carrosEncontrados.size() + "):");
            for (Carro c : carrosEncontrados) {
                System.out.println("  ID: " + c.getId() +
                                   ", Montadora: " + c.getMontadora() +
                                   ", Nome: " + c.getNomeCarro() +
                                   ", Portas: " + c.getQuantidadePortas());
            }
        }
    }
}

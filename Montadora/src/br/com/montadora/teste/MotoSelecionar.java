package br.com.montadora.teste;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import br.com.montadora.conexao.Conexao;
import br.com.montadora.dao.MotoDAO;
import br.com.montadora.model.Moto;

public class MotoSelecionar {

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("--- Seleção de Motos ---");
            System.out.println("Escolha uma opção:");
            System.out.println("1. Selecionar UMA moto pelo ID");
            System.out.println("2. Selecionar TODAS as motos de uma MONTADORA específica");
            System.out.print("Digite sua opção (1 ou 2): ");

            String opcao = scanner.nextLine();

            Connection con = null;
            try {
                con = Conexao.abrirConexao();

                if (con != null) {
                    System.out.println("\nConexão aberta para teste de seleção de motos...");
                    MotoDAO motoDao = new MotoDAO(con);

                    switch (opcao) {
                        case "1":
                            System.out.println("\nOpção 1: Selecionar por ID");
                            selecionarMotoPorId(motoDao, scanner);
                            break;
                        case "2":
                            System.out.println("\nOpção 2: Selecionar por Montadora");
                            selecionarMotosPorMontadora(motoDao, scanner);
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
                System.err.println("Ocorreu um erro inesperado no teste de seleção de moto: " + e.getMessage());
                e.printStackTrace();
            } finally {
                if (con != null) {
                    Conexao.fecharConexao(con);
                    System.out.println("\nConexão fechada após teste de seleção.");
                }
            }
        }
    }

    private static void selecionarMotoPorId(MotoDAO motoDao, Scanner scanner) throws SQLException {
        int idParaBuscar = 0;
        boolean idValido = false;
        while (!idValido) {
            System.out.print("Digite o ID da moto a ser buscada: ");
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

        Moto motoEncontrada = motoDao.selecionarPorId(idParaBuscar);

        if (motoEncontrada != null) {
            System.out.println("\nMoto encontrada:");
            System.out.println("  ID: " + motoEncontrada.getId());
            System.out.println("  Montadora: " + motoEncontrada.getMontadora());
            System.out.println("  Nome/Modelo: " + motoEncontrada.getNomeCarro());
            System.out.println("  Adesivos: " + motoEncontrada.getQuantidadeAdesivos());
        } else {
            System.out.println("\nNenhuma moto encontrada com o ID " + idParaBuscar + ".");
        }
    }

    private static void selecionarMotosPorMontadora(MotoDAO motoDao, Scanner scanner) throws SQLException {
        System.out.print("Digite o nome da MONTADORA a ser buscada: ");
        String montadoraParaBuscar = scanner.nextLine().trim();
        while (montadoraParaBuscar.isEmpty()) {
            System.out.println("Erro: Nome da montadora não pode ser vazio.");
            System.out.print("Digite o nome da MONTADORA a ser buscada: ");
            montadoraParaBuscar = scanner.nextLine().trim();
        }

        List<Moto> motosEncontradas = motoDao.selecionarPorMontadora(montadoraParaBuscar);

        if (motosEncontradas.isEmpty()) {
            System.out.println("\nNenhuma moto encontrada para a montadora '" + montadoraParaBuscar + "'.");
        } else {
            System.out.println("\nMotos encontradas para '" + montadoraParaBuscar + "' (" + motosEncontradas.size() + "):");
            for (Moto m : motosEncontradas) {
                System.out.println("  ID: " + m.getId() +
                                   ", Montadora: " + m.getMontadora() +
                                   ", Nome/Modelo: " + m.getNomeCarro() +
                                   ", Adesivos: " + m.getQuantidadeAdesivos());
            }
        }
    }
}

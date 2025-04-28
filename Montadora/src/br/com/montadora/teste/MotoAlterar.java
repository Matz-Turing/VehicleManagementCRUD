package br.com.montadora.teste;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

import br.com.montadora.conexao.Conexao;
import br.com.montadora.dao.MotoDAO;
import br.com.montadora.model.Moto;

public class MotoAlterar {

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("--- Alteração de Motos ---");
            System.out.println("Escolha uma opção:");
            System.out.println("1. Alterar dados de uma moto específica pelo ID");
            System.out.println("2. Alterar nome da montadora para todas as motos de uma montadora específica");
            System.out.print("Digite sua opção (1 ou 2): ");

            String opcao = scanner.nextLine();

            Connection con = null;
            try {
                con = Conexao.abrirConexao();

                if (con != null) {
                    MotoDAO motoDao = new MotoDAO(con);

                    switch (opcao) {
                        case "1":
                            alterarMotoPorId(motoDao, scanner);
                            break;
                        case "2":
                            alterarMontadoraGlobal(motoDao, scanner);
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

    private static void alterarMotoPorId(MotoDAO motoDao, Scanner scanner) throws SQLException {
        int idParaAlterar = 0;
        boolean idValido = false;
        while (!idValido) {
            System.out.print("Digite o ID da moto a ser alterada: ");
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
        String novaMontadora = scanner.nextLine().trim();
        while (novaMontadora.isEmpty()) {
            System.out.println("Erro: Montadora não pode ser vazia.");
            System.out.print("Digite a NOVA montadora: ");
            novaMontadora = scanner.nextLine().trim();
        }

        System.out.print("Digite o NOVO nome/modelo da moto: ");
        String novoNomeMoto = scanner.nextLine().trim();
        while (novoNomeMoto.isEmpty()) {
            System.out.println("Erro: Nome/Modelo não pode ser vazio.");
            System.out.print("Digite o NOVO nome/modelo da moto: ");
            novoNomeMoto = scanner.nextLine().trim();
        }

        int novaQtdAdesivos = -1;
        boolean adesivosValidos = false;
        while (!adesivosValidos) {
            System.out.print("Digite a NOVA quantidade de adesivos: ");
            try {
                novaQtdAdesivos = Integer.parseInt(scanner.nextLine());
                if (novaQtdAdesivos >= 0) {
                    adesivosValidos = true;
                } else {
                    System.out.println("Quantidade de adesivos não pode ser negativa.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Erro: Por favor, digite um número válido para adesivos.");
            }
        }

        Moto motoParaAlterar = new Moto();
        motoParaAlterar.setId(idParaAlterar);
        motoParaAlterar.setMontadora(novaMontadora);
        motoParaAlterar.setNomeCarro(novoNomeMoto);
        motoParaAlterar.setQuantidadeAdesivos(novaQtdAdesivos);

        String resultado = motoDao.alterarPorId(motoParaAlterar);
        System.out.println("\nResultado da alteração por ID (" + idParaAlterar + "): " + resultado);
    }

    private static void alterarMontadoraGlobal(MotoDAO motoDao, Scanner scanner) throws SQLException {
        System.out.print("Digite o nome ATUAL da montadora que deseja alterar: ");
        String montadoraAntiga = scanner.nextLine().trim();
        while (montadoraAntiga.isEmpty()) {
            System.out.println("Erro: Nome da montadora antiga não pode ser vazio.");
            System.out.print("Digite o nome ATUAL da montadora que deseja alterar: ");
            montadoraAntiga = scanner.nextLine().trim();
        }

        System.out.print("Digite o NOVO nome para esta montadora: ");
        String montadoraNova = scanner.nextLine().trim();
        while (montadoraNova.isEmpty()) {
            System.out.println("Erro: Nome da montadora nova não pode ser vazio.");
            System.out.print("Digite o NOVO nome para esta montadora: ");
            montadoraNova = scanner.nextLine().trim();
        }

        String resultado = motoDao.alterarMontadora(montadoraAntiga, montadoraNova);
        System.out.println("\nResultado da alteração global da montadora: " + resultado);
    }
}

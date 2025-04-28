package br.com.montadora.teste;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner; 

import br.com.montadora.conexao.Conexao;
import br.com.montadora.dao.MotoDAO;
import br.com.montadora.model.Moto;

public class MotoInserir {

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("--- Inserção de Motos ---");
            System.out.println("Escolha uma opção:");
            System.out.println("1. Inserir lista de motos predefinidas automaticamente");
            System.out.println("2. Inserir uma moto manualmente");
            System.out.print("Digite sua opção (1 ou 2): ");

            String opcao = scanner.nextLine();

            Connection con = null;
            try {
                con = Conexao.abrirConexao();

                if (con != null) {
                   
                    MotoDAO motoDao = new MotoDAO(con);

                    switch (opcao) {
                        case "1":
                            System.out.println("\nOpção 1 selecionada: Inserindo motos automaticamente...");
                            inserirMotosAutomaticamente(motoDao);
                            break;
                        case "2":
                            System.out.println("\nOpção 2 selecionada: Inserindo moto manualmente...");
                            inserirMotoManualmente(motoDao, scanner);
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

    private static void inserirMotosAutomaticamente(MotoDAO motoDao) throws SQLException {
        Moto moto = new Moto();

        System.out.println("\nInserindo Moto 1 (Honda CG 160)...");
        moto.setMontadora("Honda");
        moto.setNomeCarro("CG 160"); 
        moto.setQuantidadeAdesivos(2);
        System.out.println("Resultado: " + motoDao.inserir(moto)); 

        System.out.println("\nInserindo Moto 2 (Yamaha Fazer 250)...");
        moto.setMontadora("Yamaha");
        moto.setNomeCarro("Fazer 250"); 
        moto.setQuantidadeAdesivos(4);
        System.out.println("Resultado: " + motoDao.inserir(moto));

        System.out.println("\nInserindo Moto 3 (Suzuki GSX-S750)...");
        moto.setMontadora("Suzuki");
        moto.setNomeCarro("GSX-S750");
        moto.setQuantidadeAdesivos(5);
        System.out.println("Resultado: " + motoDao.inserir(moto));

        System.out.println("\nInserindo Moto 4 (Kawasaki Ninja 400)...");
        moto.setMontadora("Kawasaki");
        moto.setNomeCarro("Ninja 400");
        moto.setQuantidadeAdesivos(6);
        System.out.println("Resultado: " + motoDao.inserir(moto));

        System.out.println("\nInserindo Moto 5 (BMW R 1250 GS)...");
        moto.setMontadora("BMW");
        moto.setNomeCarro("R 1250 GS");
        moto.setQuantidadeAdesivos(3);
        System.out.println("Resultado: " + motoDao.inserir(moto));

        System.out.println("\nInserção automática concluída.");
    }

    private static void inserirMotoManualmente(MotoDAO motoDao, Scanner scanner) throws SQLException {
        Moto motoManual = new Moto();

        System.out.print("Digite a montadora da moto: ");
        String montadora = scanner.nextLine();
        while (montadora == null || montadora.trim().isEmpty()) {
            System.out.println("Erro: Montadora não pode ser vazia.");
            System.out.print("Digite a montadora da moto: ");
            montadora = scanner.nextLine();
        }
        motoManual.setMontadora(montadora.trim());

        System.out.print("Digite o nome/modelo da moto: ");
        String nomeMoto = scanner.nextLine();
        while (nomeMoto == null || nomeMoto.trim().isEmpty()) {
            System.out.println("Erro: Nome/modelo não pode ser vazio.");
            System.out.print("Digite o nome/modelo da moto: ");
            nomeMoto = scanner.nextLine();
        }
        
        motoManual.setNomeCarro(nomeMoto.trim());

        int quantidadeAdesivos = -1;
        boolean entradaValida = false;
        while (!entradaValida) {
            System.out.print("Digite a quantidade de adesivos: ");
            try {
                quantidadeAdesivos = Integer.parseInt(scanner.nextLine());
                if (quantidadeAdesivos >= 0) {
                    motoManual.setQuantidadeAdesivos(quantidadeAdesivos);
                    entradaValida = true;
                } else {
                    System.out.println("Erro: A quantidade de adesivos não pode ser negativa.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Erro: Por favor, digite um número inteiro válido para a quantidade de adesivos.");
            }
        }

        System.out.println("\nInserindo moto com os dados fornecidos...");
        
        System.out.println("Resultado: " + motoDao.inserir(motoManual));
    }
}

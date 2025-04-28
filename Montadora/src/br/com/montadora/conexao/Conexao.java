package br.com.montadora.conexao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {

	public static Connection abrirConexao() {
		Connection con = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			String url = "jdbc:mysql://localhost:3306/montadora";
			
			final String USER = "root";
			final String PASS = "senha1234";
			
			con = DriverManager.getConnection(url, USER, PASS);
			System.out.println("Conexao aberta");
		} catch (ClassNotFoundException e) {
			System.out.println(e.getMessage() + "Erro no envio dos dados");
		} catch (SQLException e) {
			System.out.println(e.getMessage() + "Erro no envio dos dados");
		} catch (Exception e) {
			System.out.println(e.getMessage() + "Erro no envio dos dados");
		}
		return con;
	}

	public static void fecharConexao(Connection con) {
		try {
			con.close();
			System.out.println("Conexão fechada");
		} catch (SQLException e) {
			System.out.println(e.getMessage() + " Erro para fechar");
		} catch (Exception e) {
			System.out.println(e.getMessage() + " Erro para fechar");
		}
	}

}

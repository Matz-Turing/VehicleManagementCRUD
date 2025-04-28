package br.com.montadora.conexao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoFactory {

	public static void main(String[] args) throws SQLException 	{
		
		Connection conexao = null;
		//Na linha abaixo, estou armazenando o caminho do BD na variavel url
		String url = "jdbc:mysql://localhost:3307/teste";
		conexao = DriverManager.getConnection(url, "root", "senha1234");
		System.out.println("Abriu a conexão.");
		conexao.close();
	}
}

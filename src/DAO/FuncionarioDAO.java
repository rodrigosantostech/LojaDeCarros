package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import oracle.jdbc.pool.OracleDataSource;
import Entidade.Funcionario;

public class FuncionarioDAO {
	
	
	public List<Funcionario> consultarFuncionarios(String funcionarioSelecionado) {
		List<Funcionario> lista = new ArrayList<Funcionario>();
		StringBuilder sb = new StringBuilder();
		Funcionario funcionario;

		try {
			Connection con;
			Statement stmt = null;
			PreparedStatement ps = null;
			ResultSet rs = null;
			String urloracle = "jdbc:oracle:thin:@172.30.0.25:1521:desv";
			String useroracle = "rodrigoscs";
			String passwordoracle = "regueiro";

			OracleDataSource ds;
			ds = new OracleDataSource();
			ds.setURL(urloracle);
			con = ds.getConnection(useroracle, passwordoracle);
			stmt = con.createStatement();

			if (funcionarioSelecionado != null && funcionarioSelecionado != "") {
				sb.append(" SELECT * FROM FUNCIONARIOS WHERE FUNCIONARIOS_NOME LIKE ? ORDER BY FUNCIONARIO_ID ASC ");
			} else {
				sb.append(" SELECT * FROM FUNCIONARIOS ORDER BY FUNCIONARIO_ID ASC ");
			}

			ps = con.prepareStatement(sb.toString());
			if (funcionarioSelecionado != null && funcionarioSelecionado != "") {
				ps.setString(1, "%" + funcionarioSelecionado + "%");
			}
			rs = ps.executeQuery();

			// percorre o resultado da consulta
			while (rs.next()) {

				funcionario = new Funcionario();

				funcionario.setNome(rs.getString("FUNCIONARIOS_NOME"));
				funcionario.setData(rs.getString("FUNCIONARIOS_DATA"));
				funcionario.setCpf(rs.getString("FUNCIONARIOS_CPF"));
				funcionario.setFuncionarioID(rs.getInt("FUNCIONARIO_ID"));

				lista.add(funcionario);

			}

			// fechando a connection
			if (con != null) {
				con.close();
				con = null;
			}
			if (stmt != null) {
				stmt.close();
				stmt = null;
			}

			if (rs != null) {
				rs.close();
				rs = null;
			}

		} catch (SQLException ex) {
			System.out.println("Erro: " + ex.getMessage());
		}
		return lista;
	}

	public void cadastrarFuncionario(Funcionario funcionario) {

		int quantidade = consultarQuantidadeFuncionariosID();
		quantidade++;
		try {
			Connection con;
			Statement stmt = null;
			PreparedStatement ps = null;
			String urloracle = "jdbc:oracle:thin:@172.30.0.25:1521:desv";
			String useroracle = "rodrigoscs";
			String passwordoracle = "regueiro";

			OracleDataSource ds;
			ds = new OracleDataSource();
			ds.setURL(urloracle);
			con = ds.getConnection(useroracle, passwordoracle);
			stmt = con.createStatement();

			String sql = "INSERT INTO FUNCIONARIOS (FUNCIONARIOS_NOME, FUNCIONARIOS_CPF, FUNCIONARIOS_DATA, FUNCIONARIO_ID)"
					+ " VALUES (?, ?, ?, ?)";

			ps = con.prepareStatement(sql);
			ps.setString(1, funcionario.getNome());
			ps.setString(2, funcionario.getCpf());
			ps.setString(3, funcionario.getData());
			ps.setInt(4, quantidade);

			ps.executeUpdate();

			// fechando a connection
			if (con != null) {
				con.close();
				con = null;
			}

			if (stmt != null) {
				stmt.close();
				stmt = null;
			}

		} catch (SQLException ex) {
			System.out.println("Erro: " + ex.getMessage());
		}
	}

	public int consultarQuantidadeFuncionariosID() {

		int quantidade = 0;

		try {

			Connection con;
			Statement stmt = null;
			ResultSet rs = null;
			String urloracle = "jdbc:oracle:thin:@172.30.0.25:1521:desv";
			String useroracle = "rodrigoscs";
			String passwordoracle = "regueiro";

			OracleDataSource ds;
			ds = new OracleDataSource();
			ds.setURL(urloracle);
			con = ds.getConnection(useroracle, passwordoracle);
			stmt = con.createStatement();

			String query = "SELECT COUNT(FUNCIONARIO_ID) id FROM FUNCIONARIOS";
			rs = stmt.executeQuery(query);

			// percorre o resultado da consulta
			while (rs.next()) {

				Funcionario funcionario = new Funcionario();

				funcionario.setId(rs.getInt("id"));
				quantidade = funcionario.getId();
			}

			// fechando a connection
			if (con != null) {
				con.close();
				con = null;
			}

			if (stmt != null) {
				stmt.close();
				stmt = null;
			}

			if (rs != null) {
				rs.close();
				rs = null;
			}

		} catch (SQLException ex) {
			System.out.println("Erro: " + ex.getMessage());
		}

		return quantidade;
	}
	
	public void excluirFuncionario(Funcionario funcionario) {

		try {

			Connection con;
			Statement stmt = null;
			PreparedStatement ps = null;
			String urloracle = "jdbc:oracle:thin:@172.30.0.25:1521:desv";
			String useroracle = "rodrigoscs";
			String passwordoracle = "regueiro";

			OracleDataSource ds;
			ds = new OracleDataSource();
			ds.setURL(urloracle);
			con = ds.getConnection(useroracle, passwordoracle);
			stmt = con.createStatement();

			String sql = " DELETE FROM FUNCIONARIOS WHERE FUNCIONARIO_ID = ? ";

			ps = con.prepareStatement(sql);
			ps.setInt(1, funcionario.getFuncionarioID());
			ps.executeUpdate();

			// fechando a connection
			if (con != null) {
				con.close();
				con = null;
			}

			if (stmt != null) {
				stmt.close();
				stmt = null;
			}

		} catch (SQLException ex) {
			System.out.println("Erro: " + ex.getMessage());
		}
	}
	
	public void editarCarros(Funcionario telaFuncionario) {
	
		// = consultarID();

		try {
			Connection con;
			Statement stmt = null;
			PreparedStatement ps = null;
			String urloracle = "jdbc:oracle:thin:@172.30.0.25:1521:desv";
			String useroracle = "rodrigoscs";
			String passwordoracle = "regueiro";

			OracleDataSource ds;
			ds = new OracleDataSource();
			ds.setURL(urloracle);
			con = ds.getConnection(useroracle, passwordoracle);
			stmt = con.createStatement();

			String sql = "UPDATE FUNCIONARIOS SET FUNCIONARIOS_NOME = ?, FUNCIONARIOS_CPF = ? , FUNCIONARIOS_DATA = ? WHERE FUNCIONARIO_ID = ?";

			ps = con.prepareStatement(sql);
			ps.setString(1, telaFuncionario.getNome());
			ps.setString(2, telaFuncionario.getCpf());
			ps.setString(3, telaFuncionario.getData());
			ps.setInt(4, telaFuncionario.getFuncionarioID());
			ps.executeUpdate();

		
			// fechando a connection
			if (con != null) {
				con.close();
				con = null;
			}
			if (stmt != null) {
				stmt.close();
				stmt = null;
			}

		} catch (SQLException ex) {
			System.out.println("Erro: " + ex.getMessage());
		}
	}

}

package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import oracle.jdbc.pool.OracleDataSource;
import Entidade.Carro;
import Entidade.Marca;

public class CarrosDAO {

	public List<Carro> consultarCarros(String carroSelecionado) {
		List<Carro> lista = new ArrayList<Carro>();
		StringBuilder sb = new StringBuilder();
		Carro carro;

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

			if (carroSelecionado != null && carroSelecionado != "") {
				sb.append(" SELECT * FROM CARROS WHERE CARROS_MODELO LIKE ? ORDER BY CARROS_ID ASC ");
			} else {
				sb.append(" SELECT * FROM CARROS ORDER BY CARROS_ID ASC ");
			}

			ps = con.prepareStatement(sb.toString());
			if (carroSelecionado != null && carroSelecionado != "") {
				ps.setString(1, "%" + carroSelecionado + "%");
			}
			rs = ps.executeQuery();

			// percorre o resultado da consulta
			while (rs.next()) {

				carro = new Carro();

				carro.setModelo(rs.getString("CARROS_MODELO"));
				carro.setCor(rs.getString("CARROS_COR"));
				carro.setAno(rs.getInt("CARROS_ANO"));
				carro.setPrecoVenda(rs.getInt("CARROS_PVENDA"));
				carro.setPrecoCompra(rs.getInt("CARROS_PCOMPRA"));
				carro.setCarroID(rs.getInt("CARROS_ID"));

				lista.add(carro);

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
	
	
	
	public List<Marca> consultarMarca() {
		List<Marca> listaMarca = new ArrayList<Marca>();
		StringBuilder sb = new StringBuilder();
		Marca marca;
		
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

			
				sb.append("SELECT NOME, ID FROM MARCA ");
			

			ps = con.prepareStatement(sb.toString());
			rs = ps.executeQuery();

			// percorre o resultado da consulta
			while (rs.next()) {

				marca = new Marca();
				marca.setNome(rs.getString("NOME"));
				marca.setMarcaID(rs.getInt("ID"));
				listaMarca.add(marca);
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
		return listaMarca;
	}

	public List<Carro> consultarModelos(Marca marcaSelecionada) {
		
		List<Carro> listaModelo = new ArrayList<Carro>();
		StringBuilder sb = new StringBuilder();
		Carro carro;
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

			sb.append("SELECT C.CARROS_ID, C.CARROS_MODELO FROM CARROS C, MARCA M WHERE ID = CARROS_ID AND M.ID= ? ");

			ps = con.prepareStatement(sb.toString());
			ps.setInt(1, marcaSelecionada.getMarcaID());
			rs = ps.executeQuery();

			// percorre o resultado da consulta
			while (rs.next()) {

				carro = new Carro();
				carro.setId(rs.getInt("CARROS_ID"));
				carro.setModelo(rs.getString("CARROS_MODELO"));
				listaModelo.add(carro);

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
		return listaModelo;
	}

	public void cadastrarCarros(Carro carro) {

		int quantidade = consultarQuantidadeCarrosID();

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

			String sql = "INSERT INTO CARROS (CARROS_MODELO, CARROS_COR, CARROS_ANO, CARROS_PVENDA, CARROS_PCOMPRA, CARROS_ID)"
					+ " VALUES (?, ?, ?, ? , ? , ?)";

			ps = con.prepareStatement(sql);
			ps.setString(1, carro.getModelo());
			ps.setString(3, carro.getCor());
			ps.setInt(4, carro.getAno());
			ps.setInt(5, carro.getPrecoVenda());
			ps.setInt(6, carro.getPrecoCompra());
			ps.setInt(7, quantidade);

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

	public void excluirCarro(Carro carro) {

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

			String sql = " DELETE FROM CARROS WHERE CARROS_ID = ? ";

			ps = con.prepareStatement(sql);
			ps.setInt(1, carro.getCarroID());
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

	public int consultarQuantidadeCarrosID() {

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

			String query = "SELECT COUNT(CARROS_ID) id FROM CARROS";
			rs = stmt.executeQuery(query);

			// percorre o resultado da consulta
			while (rs.next()) {

				Carro carro = new Carro();

				carro.setId(rs.getInt("id"));
				quantidade = carro.getId();
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

	public void editarCarros(Carro telaCarro) {

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

			String sql = "UPDATE CARROS SET CARROS_MODELO = ?, CARROS_COR = ?, CARROS_ANO = ?, CARROS_PVENDA = ?, CARROS_PCOMPRA = ? WHERE CARROS_ID = ?";

			ps = con.prepareStatement(sql);
			ps.setString(1, telaCarro.getModelo());
			ps.setString(3, telaCarro.getCor());
			ps.setInt(4, telaCarro.getAno());
			ps.setInt(5, telaCarro.getPrecoVenda());
			ps.setInt(6, telaCarro.getPrecoCompra());
			ps.setInt(7, telaCarro.getCarroID());
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

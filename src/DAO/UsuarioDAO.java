package DAO;
  
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import Entidade.Carro;
import Entidade.Usuario;
import oracle.jdbc.pool.OracleDataSource;


public class UsuarioDAO {
		
	public boolean validate(String login, String senha) {
		
		Usuario usuario = new Usuario();
		boolean verificou = false;
		
		
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
		
		
			String sql =("SELECT * FROM USUARIO WHERE USUARIO_LOGIN = ? AND USUARIO_PASSWORD = ?");
			ps = con.prepareStatement(sql);
			
			
			ps.setString(1,  login);
			ps.setString(2,  senha);
			
			rs = ps.executeQuery();
			
			if(rs.next()){
				
				usuario = new Usuario();
				
				usuario.setLogin(rs.getString("USUARIO_LOGIN"));
				usuario.setSenha(rs.getString("USUARIO_PASSWORD"));
				
				 
				
			}
			 
			if(usuario.getLogin() != null){
				verificou = true;
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
			verificou = false;
		}
		return verificou;
}
}

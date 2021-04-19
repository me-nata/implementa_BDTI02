package DAO;

import java.sql.*;

import model.Carro;

public class CarroDAO {
	private Connection conexao;
	private int         id = 0;
	
	public CarroDAO() {
		conexao = null;
	}
	
	private int getAtualId() {
		id++;
		return id;
	}
	
	public int getId() {
		return getAtualId();
	}
	
	public boolean conectar() {
		String driverName = "org.postgresql.Driver";                    
		String serverName = "localhost";
		String mydatabase = "teste";
		int porta = 5432;
		String url = "jdbc:postgresql://" + serverName + ":" + porta +"/" + mydatabase;
		String username = "ti2cc";
		String password = "ti@cc";
		boolean status = false;

		try {
			Class.forName(driverName);
			conexao = DriverManager.getConnection(url, username, password);
			status = (conexao == null);
			System.out.println("Conexão efetuada com o postgres!");
		} catch (ClassNotFoundException e) { 
			System.err.println("Conexão NÃO efetuada com o postgres -- Driver não encontrado -- " + e.getMessage());
		} catch (SQLException e) {
			System.err.println("Conexão NÃO efetuada com o postgres -- " + e.getMessage());
		}

		return status;
	}
	
	public boolean close() {
		boolean status = false;
		
		try {
			conexao.close();
			status = true;
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return status;
	}
	
	public boolean inserirCarro(Carro c) {
		boolean status = false;
		try {
			//INSERT INTO "public"."carro" ("codigo","nome","marca","ano")
			//VALUES ('','Uno','FIAT','2012')
			Statement st = conexao.createStatement();
			st.executeUpdate("INSERT INTO carro (\"codigo\", \"nome\", \"marca\", \"ano\") "
					       + "VALUES ('"+c.getCodigo()+ "', '" + c.getNome() + "', '"  
					       + c.getMarca() + "', '" + c.getAno() + "');");
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		
		System.out.println(""+status);
	
		return status;
	}
	
	public boolean atualizarCarro(Carro c) {
		boolean status = false;
		try {  
			Statement st = conexao.createStatement();
			String sql = "UPDATE carro SET nome = '" + c.getNome() + "', marca = '"  
				       + c.getMarca() + "', ano = '" + c.getAno() + "'"
					   + " WHERE codigo = " + c.getCodigo();
			st.executeUpdate(sql);
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}
	
	public boolean excluirCarro(int codigo) {
		boolean status = false;
		try {  
			Statement st = conexao.createStatement();
			st.executeUpdate("DELETE FROM carro WHERE codigo = " + codigo);
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}
	
	public Carro getCarro( int id ) {
		Carro carros = null;
		
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = st.executeQuery("SELECT codigo FROM carro WHERE codigo = " + id );		
	         if(rs.next()){
	             rs.last();
	             carros = new Carro();
	             rs.beforeFirst();

                carros = new Carro(rs.getInt("codigo"), rs.getString("nome"), 
                		              rs.getString("marca"), rs.getInt("ano"));
	             
	          }
	          st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		
		return carros;
	}
	
	public Carro[] getCarros() {
		Carro[] carros = null;
		
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = st.executeQuery("SELECT * FROM carro");		
	         if(rs.next()){
	             rs.last();
	             carros = new Carro[rs.getRow()];
	             rs.beforeFirst();

	             for(int i = 0; rs.next(); i++) {
	                carros[i] = new Carro(rs.getInt("codigo"), rs.getString("nome"), 
	                		              rs.getString("marca"), rs.getInt("ano"));
	             }
	          }
	          st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return carros;
	}
}	
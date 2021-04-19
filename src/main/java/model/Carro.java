package model;

import java.util.Random;

public class Carro {
	private int   codigo;
	private String  nome;
	private String marca;
	private int      ano;
	
	Random random = new Random();
	
	public Carro() {
		codigo =  random.nextInt(888888) + 100000;
		nome   = "";
		marca  = "";
		ano    =  0;
	}
	public Carro(String nome, String marca, int ano) {
		this.codigo =  random.nextInt(888888) + 100000;
		this.nome   = nome;
		this.marca  = marca;
		this.ano    =  ano;
	}
	public Carro(int codigo, String nome, String marca, int ano) {
		this.codigo =  codigo;
		this.nome   = nome;
		this.marca  = marca;
		this.ano    =  ano;
	}

	public String getNome(){
		return this.nome;
	}
	public String getMarca(){
		return this.marca;
	}
	public int getAno(){
		return this.ano;
	}
	public int getCodigo(){
		return this.codigo;
	}
	
	public void setNome(String nome) {
		if(nome.compareTo("") != 0) {
			this.nome = nome;
		}
	}
	public void setMarca(String marca) {
		if(marca.compareTo("") != 0) {
			this.marca = marca;
		}
	}
	public void setAno(int ano) {
		if(ano >= 0) {
			this.ano = ano;
		}
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

}

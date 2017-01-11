package Entidade;



public class Funcionario {
   
	
	private String nome;
	private String data;
	private String cpf;
	private int id;
	private int FuncionarioID;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getFuncionarioID() {
		return FuncionarioID;
	}

	public void setFuncionarioID(int funcionarioID) {
		FuncionarioID = funcionarioID;
	}
}

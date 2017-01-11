package Entidade;

import java.io.Serializable;

public class Marca implements BaseEntityObject, Serializable {
 
	/**
	 * 
	 */
	private static final long serialVersionUID = 7520656900459316080L;
	/**
	 * 
	 */
	private String nome;
	private int marcaID;
	private Integer id;
	
	public Marca(){
	 
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getMarcaID() {
		return marcaID;
	}

	public void setMarcaID(int marcaID) {
		this.marcaID = marcaID;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Carro other = (Carro) obj;
		if (id == null) {
			if (other.getId() != null)
				return false;
		} else if (!id.equals(other.getId()))
			return false;
		return true;
	}
}

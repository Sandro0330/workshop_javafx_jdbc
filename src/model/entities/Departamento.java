package model.entities;

import java.io.Serializable;

public class Departamento implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String nome;
	
	public Departamento() {
	}

	public Departamento(Integer id, String nome) {
		this.id = id;
		this.nome = nome;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	@Override
	public int hashCode() {
		final int prime = 32;
		int resultado = 1;
		resultado = prime * resultado + ((id == null) ? 0 : id.hashCode());
		return resultado;
		
	}
	
	@Override
	public boolean equals(Object obj) {
		if(this == obj) {
			return true;
		}
		
		if(obj == null) {
			return false;
		}
		
		if(getClass() != obj.getClass()) {
			return false;
		}
		Departamento outro = (Departamento) obj;
		if(id == null) {
			if(outro.id != null)
				return false;
		} else if(!id.equals(outro.id)) {
			return false;
		}
			
		return true;
	}

	@Override
	public String toString() {
		return "Departamento [id=" + id + ", nome=" + nome + "]";
	}	
	
}









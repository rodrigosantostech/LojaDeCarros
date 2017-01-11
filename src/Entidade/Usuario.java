package Entidade;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class Usuario {
	private String login;
	private String senha;
	public boolean verificou;

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public boolean isVerificou() {
		return verificou;
	}

	public void setVerificou(boolean verificou) {
		this.verificou = verificou;
	}
}

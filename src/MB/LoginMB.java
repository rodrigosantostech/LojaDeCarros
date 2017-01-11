package MB;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;

import DAO.UsuarioDAO;
import Entidade.BaseEntity;
import Entidade.Usuario;




 
@ManagedBean
@SessionScoped
/**
 *
 * @author rodrigoscs
 */
public class LoginMB implements BaseEntity, Serializable {
		private Usuario usuario = new Usuario();
		private UsuarioDAO usuarioDAO = new UsuarioDAO();
		private List<Usuario> listaUsuario = new ArrayList<Usuario>();
		private String login;
		private String senha;
		private String msg;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	public void verificarLogin() {
		
		
		if(usuarioDAO.validate(login , senha) == true){	
			redirecionar("/portal/menu.jsf");
		 }
		else{
			msg = "Usuario ou Senha Invalidos";
			String menssagem = "";
			saveMessage(menssagem, msg);
		}
	}
	
	public void addMessage(String summary, String detail) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
				summary, detail);
		FacesContext.getCurrentInstance().addMessage(null, message);
	}

	public void saveMessage(String menssagem, String msg) {
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, new FacesMessage("Falha.", "" + menssagem
				+ msg));
	}

	public void showMessage() {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
				"What we do in life", "Echoes in eternity.");
		RequestContext.getCurrentInstance().showMessageInDialog(message);
	}
	public void redirecionar(String endereco) {
		FacesContext context = FacesContext.getCurrentInstance();
		try {
			context.getExternalContext().redirect(endereco);
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public UsuarioDAO getUsuarioDAO() {
		return usuarioDAO;
	}

	public void setUsuarioDAO(UsuarioDAO usuarioDAO) {
		this.usuarioDAO = usuarioDAO;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Usuario> getListaUsuario() {
		return listaUsuario;
	}

	public void setListaUsuario(List<Usuario> listaUsuario) {
		this.listaUsuario = listaUsuario;
	}

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


	}
 

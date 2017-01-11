package MB;

import java.io.IOException;
import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
//import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

//import org.primefaces.context.RequestContext;

import javax.swing.text.MaskFormatter;

import org.primefaces.context.RequestContext;

import DAO.FuncionarioDAO;
import Entidade.BaseEntity;
//import Entidade.Carro;
import Entidade.Funcionario;

@SessionScoped
@ManagedBean
public class controleMB implements BaseEntity, Serializable {
	private Funcionario funcionario = new Funcionario();
	private List<Funcionario> listaFuncionarios = new ArrayList<Funcionario>();;
	private String funcionarioSelecionado;
	private String msg;
	private static final long serialVersionUID = 1L;
	private FuncionarioDAO funcionarioDAO = new FuncionarioDAO();

	private boolean flagAdd = true;
	private boolean FlagEdit = false;

	@PostConstruct
	public void init() {
		funcionario = new Funcionario();
		System.out.println("--> init*()");
	}

	public void cadastrar() {
		funcionarioDAO.cadastrarFuncionario(getFuncionario());
		listaFuncionarios.add(funcionario);
		getFuncionario();
		getListaFuncionarios();
		funcionario = new Funcionario();
	}

	public void deletar() {
		funcionarioDAO.excluirFuncionario(funcionario);
		listaFuncionarios.remove(funcionario);
	}

	public void buscar() {

		listaFuncionarios.clear();
		List<Funcionario> listaAux = new ArrayList<Funcionario>();
		listaAux = funcionarioDAO.consultarFuncionarios(funcionarioSelecionado);
		Funcionario atual = new Funcionario();
		Funcionario listar = new Funcionario();

		for (int x = 0; x < listaAux.size(); x++) {
			listar = new Funcionario();

			atual = listaAux.get(x);
			listar.setNome(atual.getNome());
			listar.setData(atual.getData());
			listar.setCpf(atual.getCpf());
			listar.setFuncionarioID(atual.getFuncionarioID());

			listaFuncionarios.add(listar);
		}
	}

	public void editar() {
		if (verificar()) {

			for (Funcionario lista : listaFuncionarios) {
				if (funcionario.equals(lista)) {
					funcionarioDAO.editarCarros(funcionario);
				}
			}
			redirecionar("/portal/controleFuncionarios.jsf");
		}
		funcionario = new Funcionario();
	}
	public boolean verificar() {
		boolean verificar = true;

		if (getFuncionario().getNome().trim().equals("")
				|| getFuncionario().getNome() == null) {
			verificar = false;
			msg = "Nome em branco";
			String menssagem = "";
			saveMessage(menssagem, msg);
		}

		if (getFuncionario().getCpf().trim().equals("")) {
			verificar = false;
			msg = "CPF em Branco";
			String menssagem = "";
			saveMessage(menssagem, msg);
		}

		if (getFuncionario().getData().trim().equals("")) {
			verificar = false;
			msg = "Data em Branco";
			String menssagem = "";
			saveMessage(menssagem, msg);
		}
		return verificar;
	}

	public void carregar() {
		if (getFuncionario() != null) {

			setFuncionario(getFuncionario());

			setFlagAdd(false);
			setFlagEdit(true);
		}
	}

	public void redirecionar(String endereco) {
		FacesContext context = FacesContext.getCurrentInstance();
		try {
			context.getExternalContext().redirect(endereco);
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	public void redirecionarControleFuncionarios() {
		redirecionar("/portal/controleFuncionarios.jsf");
	}

	public void iniciar() {
		funcionario = new Funcionario();

		setFlagAdd(true);
		setFlagEdit(false);
	}

	public String format(String pattern, Object value, boolean suppressZero) {
		if (!suppressZero || Double.parseDouble(value.toString()) != 0) {
			MaskFormatter mask;
			try {
				mask = new MaskFormatter(pattern);
				mask.setValueContainsLiteralCharacters(false);
				return mask.valueToString(value);
			} catch (ParseException e) {
				throw new RuntimeException(e);
			}
		} else {
			return "";
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

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Funcionario> getListaFuncionarios() {
		return listaFuncionarios;
	}

	public void setListaFuncionarios(List<Funcionario> listaFuncionarios) {
		this.listaFuncionarios = listaFuncionarios;
	}

	public String getFuncionarioSelecionado() {
		return funcionarioSelecionado;
	}

	public void setFuncionarioSelecionado(String funcionarioSelecionado) {
		this.funcionarioSelecionado = funcionarioSelecionado;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public boolean isFlagAdd() {
		return flagAdd;
	}

	public void setFlagAdd(boolean flagAdd) {
		this.flagAdd = flagAdd;
	}

	public boolean isFlagEdit() {
		return FlagEdit;
	}

	public void setFlagEdit(boolean flagEdit) {
		FlagEdit = flagEdit;
	}

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}

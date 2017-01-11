package MB;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;

import DAO.CarrosDAO;
import Entidade.BaseEntity;
import Entidade.Carro;
import Entidade.Marca;

@ViewScoped
@ManagedBean
public class VendasMB implements BaseEntity, Serializable {

	private static final long serialVersionUID = 1L;
	private Carro carro = new Carro();
	private List<Carro> listaCarros = new ArrayList<Carro>();;
	private List<Marca> listaMarca = new ArrayList<Marca>();
	private List<Carro> listaModelo = new ArrayList<Carro>();
	private String carroSelecionado;
	private Marca marcaSelecionada;
	private Carro modeloSelecionado;
	private String msg;
	private boolean flagAdd = true;
	private boolean FlagEdit = false;
	private CarrosDAO carrosDAO = new CarrosDAO();


	@PostConstruct
	public void init() {
		marcaSelecionada = new Marca();
		listaMarca = carrosDAO.consultarMarca();
		getListaMarca();
		
		System.out.println("--> init*()");
	}

	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return null;
	}

	public void carregar() {

		if (getCarro() != null) {

			setCarro(getCarro());

			setFlagAdd(false);
			setFlagEdit(true);
		}
	}

	public void cadastrar() {

		carrosDAO.cadastrarCarros(getCarro());
		listaCarros.add(carro);
		getCarro();
		getListaCarros();
		carro = new Carro();
	}

	public void deletar() {
		carrosDAO.excluirCarro(carro);
		listaCarros.remove(carro);
	}

	public void carregarModelos() {
		System.out.println(marcaSelecionada);
		listaModelo = carrosDAO.consultarModelos(marcaSelecionada);
	}

	public void consultar() {
		listaCarros.clear();
		List<Carro> listaAux = new ArrayList<Carro>();
		listaAux = carrosDAO.consultarCarros(carroSelecionado);
		Carro atual = new Carro();
		Carro listar = new Carro();

		for (int x = 0; x < listaAux.size(); x++) {
			listar = new Carro();

			atual = listaAux.get(x);
			listar.setModelo(atual.getModelo());
			listar.setCor(atual.getCor());
			listar.setAno(atual.getAno());
			listar.setPrecoCompra(atual.getPrecoCompra());
			listar.setPrecoVenda(atual.getPrecoVenda());
			listar.setCarroID(atual.getCarroID());

			listaCarros.add(listar);
		}
	}

	public void editar() {

		if (verificar()) {

			for (Carro lista : listaCarros) {
				if (carro.equals(lista)) {
					carrosDAO.editarCarros(carro);
				}
			}
			redirecionar("/portal/estoqueCarros.jsf");
		}
		carro = new Carro();
	}

	public boolean verificar() {
		boolean verificar = true;

		if (getCarro().getModelo().trim().equals("")
				|| getCarro().getModelo() == null) {
			verificar = false;
			msg = "Modelo em Branco";
			String menssagem = "";
			saveMessage(menssagem, msg);
		}


		if (getCarro().getCor().trim().equals("")) {
			verificar = false;
			msg = "Cor em Branco";
			String menssagem = "";
			saveMessage(menssagem, msg);
		}

		if (getCarro().getAno() == 0) {
			verificar = false;
			msg = "Ano em Branco";
			String menssagem = "";
			saveMessage(menssagem, msg);
		}
		if (getCarro().getPrecoCompra() == 0) {
			verificar = false;
			msg = "Preço de Compra em Branco";
			String menssagem = "";
			saveMessage(menssagem, msg);
		}
		if (getCarro().getPrecoVenda() == 0) {
			verificar = false;
			msg = "Preço de Venda em Branco";
			String menssagem = "";
			saveMessage(menssagem, msg);
		}
		return verificar;
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

	/**
	 * public void onCountryChange() { if(carro !=null && !carro.equals(""))
	 * listaMarca = carro.get(carroSelecionado); else listaCarros = new
	 * ArrayList<Carro>(); }
	 **/
	


	public void limpar() {
		getCarro().setModelo(null);
		getCarro().setCor(null);
		getCarro().setAno(0);
		getCarro().setPrecoCompra(0);
		getCarro().setPrecoVenda(0);
	}

	public void redirecionar(String endereco) {
		FacesContext context = FacesContext.getCurrentInstance();
		try {
			context.getExternalContext().redirect(endereco);
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	public void iniciar() {
		carro = new Carro();

		setFlagAdd(true);
		setFlagEdit(false);
	}

	public void redirecionarEstoqueCarros() {
		redirecionar("/portal/estoqueCarros.jsf");
	}

	public void redirecionarVendas() {
		redirecionar("/portal/telaDeVendas.jsf");
	}

	public Carro getCarro() {
		return carro;
	}

	public void setCarro(Carro carro) {
		this.carro = carro;
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

	public List<Carro> getListaCarros() {
		return listaCarros;
	}

	public void setListaCarros(List<Carro> listaCarros) {
		this.listaCarros = listaCarros;
	}

	public String getCarroSelecionado() {
		return carroSelecionado;
	}

	public void setCarroSelecionado(String carroSelecionado) {
		this.carroSelecionado = carroSelecionado;
	}

	public List<Marca> getLista() {
		return listaMarca;
	}

	public void setLista(List<Marca> lista) {
		this.listaMarca = lista;
	}

	public List<Marca> getListaMarca() {
		return listaMarca;
	}

	public void setListaMarca(List<Marca> listaMarca) {
		this.listaMarca = listaMarca;
	}



	public List<Carro> getListaModelo() {
		return listaModelo;
	}

	public void setListaModelo(List<Carro> listaModelo) {
		this.listaModelo = listaModelo;
	}

	public Carro getModeloSelecionado() {
		return modeloSelecionado;
	}

	public void setModeloSelecionado(Carro modeloSelecionado) {
		this.modeloSelecionado = modeloSelecionado;
	}

	public Marca getMarcaSelecionada() {
		return marcaSelecionada;
	}

	public void setMarcaSelecionada(Marca marcaSelecionada) {
		this.marcaSelecionada = marcaSelecionada;
	}


}

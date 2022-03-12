package compass.microservice.usuario.controller.form;

import java.util.List;

import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;

public class BuscarNomeLivrosForm {

	@JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
	@Size(min = 1, message = "Digite 1 livro")
	private List<String> nomeLivros;

	private boolean mostrarIndisponiveis;


	public BuscarNomeLivrosForm() {

	}


	public boolean isMostrarIndisponiveis() {
		return mostrarIndisponiveis;
	}

	public void setMostrarIndisponiveis(boolean mostrarIndisponiveis) {
		this.mostrarIndisponiveis = mostrarIndisponiveis;
	}

	public List<String> getNomeLivros() {
		return nomeLivros;
	}

	public void setNomeLivros(List<String> nomeLivros) {
		this.nomeLivros = nomeLivros;
	}

}

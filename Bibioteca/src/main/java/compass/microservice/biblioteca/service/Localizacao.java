package compass.microservice.biblioteca.service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import compass.microservice.biblioteca.controller.form.ReceberEnderecoUsuario;
import compass.microservice.biblioteca.modelos.Biblioteca;
import compass.microservice.biblioteca.modelos.Endereco;
import compass.microservice.biblioteca.modelos.Livro;
import compass.microservice.biblioteca.modelos.StatusLivro;

public class Localizacao {



	public Biblioteca procurarBibliotecaMaisProxima(ReceberEnderecoUsuario form, List<Biblioteca> bibliotecas)  throws Exception{

		Map<Integer,Biblioteca> relacao = new HashMap< Integer ,Biblioteca>();	

		String apiKey = "AIzaSyBS_-kGtjTHQ2L8Zc_1yERB9XxGDyCklHY";

		String origem = montarOrigem(form);

		String url = "https://maps.googleapis.com/maps/api/distancematrix/json?origins="+origem
				+ "&destinations=";

		int keyBiblioteca = 0;
		for (Biblioteca biblioteca : bibliotecas) {
			String destino = montarDestino(biblioteca);
			url += "%7C"+destino;
			relacao.put(keyBiblioteca, biblioteca);
			keyBiblioteca++;
		}

		url+= "&key="+apiKey;

		HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create(url)).build();
		HttpClient client = HttpClient.newBuilder().build();
		String response = client.send(request, HttpResponse.BodyHandlers.ofString()).body();



		JSONParser jp = new JSONParser();
		JSONObject jo = (JSONObject) jp.parse(response);
		JSONArray ja = (JSONArray) jo.get("rows");
		jo = (JSONObject) ja.get(0);
		ja =(JSONArray) jo.get("elements");

		Long menor = null;
		int keyMaisProxima = 0;


		for(int i = 0; i <relacao.size(); i++) {
			jo= (JSONObject) ja.get(i);
			JSONObject jd = (JSONObject) jo.get("distance");
			String status = (String) jo.get("status");
			if(status.equals("OK")){
				Long distance = (Long) jd.get("value");
				if (menor == null || distance <= menor) {
					menor = distance;
					keyMaisProxima = i;

				}	
			}
		}


		return relacao.get(keyMaisProxima);


	}


	public Livro livroMaisProximo(ReceberEnderecoUsuario usuarioEnd, List<Livro> livros, Boolean mostarIndisponiveis) throws Exception {

		List<Biblioteca> bibliotecas = new ArrayList<Biblioteca>();
		HashMap<Long, Livro> relacao = new HashMap<>();

		if(!mostarIndisponiveis) {

			for(Livro l: livros) {
				if(l.getStatusLivro().equals(StatusLivro.DISPONIVEL)) {
					bibliotecas.add(l.getBiblioteca());
					relacao.put(l.getBiblioteca().getId(), l);					
				}
			}
		}else {
			for (Livro l : livros) {
				bibliotecas.add(l.getBiblioteca());
				//garantindo, caso existam dois livros iguais em uma mesma biblioteca, que o disponivel tenha prioridade
				if (relacao.containsKey(l.getBiblioteca().getId())) {
					Livro livroPresente = relacao.get(l.getBiblioteca().getId());
					if(livroPresente.getStatusLivro().equals(StatusLivro.INDISPONIVEL));{
						relacao.put(l.getBiblioteca().getId(), l);
					}
				}else {
					relacao.put(l.getBiblioteca().getId(), l);
				}
			}				
		}

		Livro livroMaisProximo = null;

		if (!bibliotecas.isEmpty()) {
			Biblioteca bbMaisProxima = procurarBibliotecaMaisProxima(usuarioEnd , bibliotecas);
			for (Map.Entry<Long, Livro> entry: relacao.entrySet()) {
				if(entry.getKey().equals(bbMaisProxima.getId())) {
					livroMaisProximo = entry.getValue();
				}
			}
		}
		return livroMaisProximo;

	}



	public String montarOrigem (ReceberEnderecoUsuario form) {
		String origem = 
				form.getEstado().replaceAll(" ","%20")+"%20"
						+form.getCidade().replaceAll(" ","%20")+"%20"
						+form.getBairro().replaceAll(" ","%20")+"%20"
						+form.getRua().replaceAll(" ","%20")+"%20"
						+form.getNumero();

		return origem;
	}

	public String montarDestino (Biblioteca biblioteca) {
		Endereco end = biblioteca.getEndereco(); 
		String destino = 
				end.getEstado()+"%20"
						+end.getCidade().replaceAll(" ","%20")+"%20"
						+end.getBairro().replaceAll(" ","%20")+"%20"
						+end.getRua().replaceAll(" ","%20")+"%20"
						+end.getNumero();

		return destino;
	}





}

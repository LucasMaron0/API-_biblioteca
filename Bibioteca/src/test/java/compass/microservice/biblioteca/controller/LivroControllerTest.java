package compass.microservice.biblioteca.controller;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import compass.microservice.biblioteca.modelos.Biblioteca;
import compass.microservice.biblioteca.modelos.Categoria;
import compass.microservice.biblioteca.modelos.Livro;
import compass.microservice.biblioteca.repository.BibliotecaRepository;
import compass.microservice.biblioteca.repository.LivrosRepository;

@WebMvcTest(LivroController.class)
class LivroControllerTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private LivrosRepository lRepo;

	@MockBean
	private BibliotecaRepository bRepo;

	@Test
	void testeListar() throws Exception {

		Biblioteca biblioteca1 = new Biblioteca("Biblioteca Central");
		Biblioteca biblioteca2 = new Biblioteca("Biblioteca Universitária");

		List<Livro> listaLivros = new ArrayList<>();

		Livro livro1 = new Livro("O Senhor dos Aneis", "J R R Tolkien", Categoria.AVENTURA, "Allen & Unwin",
				LocalDate.of(1954, 7, 29), biblioteca1);
		livro1.setId(Long.valueOf(1));
		Livro livro2 = new Livro("O Hobbit", "J R R Tolkien", Categoria.AVENTURA, "Allen & Unwin",
				LocalDate.of(1937, 9, 21), biblioteca2);
		livro2.setId(Long.valueOf(2));

		listaLivros.add(livro1);
		listaLivros.add(livro2);

		Pageable paginacao = PageRequest.of(0, 10);
		Page<Livro> livros = new PageImpl<>(listaLivros, paginacao, listaLivros.size());

		given(lRepo.findAll(Mockito.any(Pageable.class))).willReturn(livros);

		mvc.perform(get("/livros").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andDo(print());

	}

	@Test
	void testeDetalhar() throws Exception {
		Biblioteca biblioteca1 = new Biblioteca("Biblioteca Central");
		Biblioteca biblioteca2 = new Biblioteca("Biblioteca Universitária");

		Categoria aventura = Categoria.AVENTURA;

		List<Livro> listaLivros = new ArrayList<>();

		Livro livro1 = new Livro("O Senhor dos Aneis", "J R R Tolkien", aventura, "Allen & Unwin",
				LocalDate.of(1954, 7, 29), biblioteca1);
		livro1.setId(Long.valueOf(1));
		Livro livro2 = new Livro("O Hobbit", "J R R Tolkien", aventura, "Allen & Unwin", LocalDate.of(1937, 9, 21),
				biblioteca2);
		livro2.setId(Long.valueOf(2));

		listaLivros.add(livro1);
		listaLivros.add(livro2);

		when(lRepo.findById(Long.valueOf(2))).thenReturn(Optional.of(livro2));

		mvc.perform(get("/livros/2").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andDo(print());
	}

	@Test
	void testeDetalharPorBiblioteca() throws Exception {
		Biblioteca biblioteca1 = new Biblioteca("Biblioteca Central");
		biblioteca1.setId(Long.valueOf(1));
		Biblioteca biblioteca2 = new Biblioteca("Biblioteca Universitária");
		biblioteca2.setId(Long.valueOf(2));

		List<Livro> listaLivros = new ArrayList<>();

		Livro livro1 = new Livro("O Senhor dos Aneis", "J R R Tolkien", Categoria.AVENTURA, "Allen & Unwin",
				LocalDate.of(1954, 7, 29), biblioteca1);
		livro1.setId(Long.valueOf(1));
		Livro livro2 = new Livro("O Hobbit", "J R R Tolkien", Categoria.AVENTURA, "Allen & Unwin",
				LocalDate.of(1937, 9, 21), biblioteca1);
		livro2.setId(Long.valueOf(2));

		listaLivros.add(livro1);
		listaLivros.add(livro2);

		Pageable paginacao = PageRequest.of(0, 10);
		Page<Livro> livros = new PageImpl<>(listaLivros, paginacao, listaLivros.size());

		given(lRepo.findByBiblioteca_id(Mockito.eq(Long.valueOf(1)), Mockito.any(Pageable.class))).willReturn(livros);

		mvc.perform(get("/livros/biblioteca/1").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andDo(print()).andReturn();

	}

	@Test
	void testeLivroNaoEncontrado() throws Exception {

		mvc.perform(get("/livros/20")).andExpect(status().isNotFound()).andDo(print()).andReturn();
	}

}

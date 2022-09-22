package br.edu.ifms.ordem.repositories;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.EmptyResultDataAccessException;

import br.edu.ifms.ordem.entities.Tecnico;
import br.edu.ifms.ordem.tests.Factory;

@DataJpaTest
public class TecnicoRepositoryTests {

	/*
	 * Acesso a Dados
	 * CRUD - Create, Read, Update and Delete
	 */

	@Autowired
	private TecnicoRepository repository;

	/*
	 * SAVE
	 * -- DEVERIA SALVAR REGISTRO [QUANDO ID FOR NULO]
	 * saveDeveriaSalvarRegistroQuandoIdForNulo
	 */

	@Test
	public void saveDeveriaSalvarRegistroQuandoIdForNulo() {
		Tecnico tecnico = Factory.novotecnico();

		tecnico.setId(null);
		tecnico = repository.save(tecnico);

		Assertions.assertNotNull(tecnico.getId());
	}

	/**
	 * DELETE
	 * deveria EXCLUIR REGISTRO [quando O ID EXISTIR]
	 */
	@Test
	public void deleteDeveriaExcluirRegistroQuandoIdExistir() {
		// Arrange
		Long idConsultado = 1L;

		// Act
		repository.deleteById(idConsultado);

		// Assert
		Optional<Tecnico> resultado;
		resultado = repository.findById(idConsultado);
		Assertions.assertFalse(resultado.isPresent());
	}

	@Test
	public void deleteDeveriaLancarEmptyResultDataAccessExceptionQuandoIdNaoExistir() {
		long idConsultado = 10L;

		Assertions.assertThrows(EmptyResultDataAccessException.class, () -> {
			repository.deleteById(idConsultado);
		});
	}

}

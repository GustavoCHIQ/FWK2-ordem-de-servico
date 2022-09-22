package br.edu.ifms.ordem.tests;

import br.edu.ifms.ordem.dto.TecnicoDTO;
import br.edu.ifms.ordem.entities.Tecnico;

public class Factory {
	public static Tecnico novotecnico() {
		Tecnico tecnico = new Tecnico(5L, "Gustavo", "67 999999999", "aaa@gmail.com", "senha");

		return tecnico;
	}

	public static TecnicoDTO novoTecnicoDTO() {
		Tecnico tecnico = novotecnico();

		return new TecnicoDTO(tecnico);
	}
}

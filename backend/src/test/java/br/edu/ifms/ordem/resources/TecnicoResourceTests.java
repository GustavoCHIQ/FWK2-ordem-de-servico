package br.edu.ifms.ordem.resources;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import br.edu.ifms.ordem.dto.TecnicoDTO;
import br.edu.ifms.ordem.services.TecnicoService;
import br.edu.ifms.ordem.services.exceptions.ResourceNotFoundException;
import br.edu.ifms.ordem.tests.Factory;

@WebMvcTest(TecnicoResource.class) // Qual recurso da camada web será testado
public class TecnicoResourceTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TecnicoService service; // Simula o serviço

    private PageImpl<TecnicoDTO> page; // Simula a página
    private TecnicoDTO tecnicoDTO; // DTO é o objeto que será enviado para o cliente
    private Long idExistente;
    private Long idNaoExistente;

    @BeforeEach
    void setUp() throws Exception {

        idExistente = 1L;
        idNaoExistente = 100L;

        tecnicoDTO = Factory.novoTecnicoDTO(); // Instancia um novo técnico
        page = new PageImpl<>(List.of(tecnicoDTO)); // Instancia uma nova página
        when(service.findAllPaged(any())).thenReturn(page); // Quando o método findAllPaged for chamado, retorne a
                                                            // página

        when(service.findById(idExistente)).thenReturn(tecnicoDTO); // Quando o método findById for chamado com o id
                                                                    // existente, retorne o técnicoDTO

        when(service.findById(idNaoExistente)).thenThrow(ResourceNotFoundException.class); // Quando o método findById
                                                                                           // for chamado com o id não
                                                                                           // existente, retorne uma
                                                                                           // exceção
    }

    @Test
    public void findAllDeveriaRetornarPaginaTecnicoDTO() throws Exception {
        ResultActions result = mockMvc.perform(get("/tecnicos")
                .accept(MediaType.APPLICATION_JSON)); // Faz uma requisição GET para o recurso /tecnicos e espera que o
                                                      // tipo de resposta seja JSON

        result.andExpect(status().isOk()); // Espera que o status da resposta seja 200 (OK)
    }

    @Test
    public void findByIdDeveriaRetornarTecnicoDTO() throws Exception {
        ResultActions result = mockMvc.perform(get("/tecnicos/{id}", idExistente)
                .accept(MediaType.APPLICATION_JSON)); // Faz uma requisição GET para o recurso /tecnicos/{id} e espera
                                                      // que
                                                      // o tipo de resposta seja JSON

        result.andExpect(status().isOk()); // Espera que o status da resposta seja 200 (OK)
    }
}
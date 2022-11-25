package br.edu.ifms.ordem.resources;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
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

import com.fasterxml.jackson.databind.ObjectMapper;

import br.edu.ifms.ordem.dto.TecnicoDTO;
import br.edu.ifms.ordem.services.TecnicoService;
import br.edu.ifms.ordem.services.exceptions.ResourceNotFoundException;
import br.edu.ifms.ordem.tests.Factory;

@WebMvcTest(TecnicoResource.class) // Qual recurso da camada web será testado
public class TecnicoResourceTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

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
        when(service.findAllPaged(any()))
                .thenReturn(page); // Quando o método findAllPaged for chamado, retorne a
                                   // página

        // FindByID
        when(service.findById(idExistente))
                .thenReturn(tecnicoDTO); // Quando o método findById for chamado com o id
                                         // existente, retorne o técnicoDTO

        when(service.findById(idNaoExistente))
                .thenThrow(ResourceNotFoundException.class); // Quando o método findById
                                                             // for chamado com o id não
                                                             // existente, retorne uma
                                                             // exceção
        // UPDATE
        when(service.update(eq(idExistente), any()))
                .thenReturn(tecnicoDTO); // Quando o método update for chamado com o id
                                         // existente, retorne o técnicoDTO

        when(service.update(eq(idNaoExistente), any()))
                .thenThrow(ResourceNotFoundException.class); // Quando o método
                                                             // update for chamado
                                                             // com o
                                                             // id não existente,
                                                             // retorne
                                                             // uma exceção

        // INSERT
        when(service.insert(any()))
                .thenReturn(tecnicoDTO); // Quando o método insert for chamado, retorne o
                                         // técnicoDTO
    }

    @Test
    public void updateDeveriaRetornarTecnicoQuandoIdExistente() throws Exception {

        String jsonBody = objectMapper.writeValueAsString(tecnicoDTO); // Converte o objeto para JSON

        ResultActions result = mockMvc.perform(put("/tecnicos/{id}", idExistente) // Faz a requisição
                .content(jsonBody) // Envia o corpo da requisição
                .contentType(MediaType.APPLICATION_JSON) // Define o tipo de conteúdo
                .accept(MediaType.APPLICATION_JSON)); // Define o tipo de resposta

        result.andExpect(status().isOk()); // Espera que o status seja 200
        result.andExpect(jsonPath("$.id").exists()); // Espera que o id exista
    }

    @Test
    public void updateDeveriaRetornarNotFoundQuandoIdNaoExistente() throws Exception {

        String jsonBody = objectMapper.writeValueAsString(tecnicoDTO); // Converte o objeto para JSON

        ResultActions result = mockMvc.perform(put("/tecnicos/{id}", idNaoExistente) // Faz a requisição
                .content(jsonBody) // Envia o corpo da requisição
                .contentType(MediaType.APPLICATION_JSON) // Define o tipo de conteúdo
                .accept(MediaType.APPLICATION_JSON)); // Define o tipo de resposta

        result.andExpect(status().isNotFound()); // Espera que o status seja 404
    }

    @Test
    public void findAllDeveriaRetornarPaginaTecnicoDTO() throws Exception {
        ResultActions resultado = mockMvc.perform(get("/tecnicos")
                .accept(MediaType.APPLICATION_JSON)); // Faz uma requisição GET para o recurso /tecnicos e espera que o
                                                      // tipo de resposta seja JSON

        resultado.andExpect(status().isOk()); // Espera que o status da resposta seja 200 (OK)
    }

    @Test
    public void findByIdDeveriaRetornarTecnicoDTO() throws Exception {
        ResultActions resultado = mockMvc.perform(get("/tecnicos/{id}", idExistente)
                .accept(MediaType.APPLICATION_JSON)); // Faz uma requisição GET para o recurso /tecnicos/{id} e espera
                                                      // que
                                                      // o tipo de resposta seja JSON

        resultado.andExpect(status().isOk()); // Espera que o status da resposta seja 200 (OK)
    }

    @Test
    public void findByIdDeveriaRetornarTecnicoQuandoIDExistente() throws Exception {
        ResultActions resultado = mockMvc.perform(get("/tecnicos/{id}", idExistente)
                .accept(MediaType.APPLICATION_JSON)); // Faz uma requisição GET para o recurso /tecnicos/{id} e espera
                                                      // que
                                                      // o tipo de resposta seja JSON

        resultado.andExpect(status().isOk()); // Espera que o status da resposta seja 200 (OK)
        resultado.andExpect(jsonPath("$.id").exists()); // Espera que o JSON retornado tenha o campo id
        resultado.andExpect(jsonPath("$.nome").exists()); // Espera que o JSON retornado tenha o campo nome
    }

    @Test
    public void findByIdDeveriaRetornarExceptionQuandoIDNaoExistente() throws Exception {
        ResultActions resultado = mockMvc.perform(get("/tecnicos/{id}", idNaoExistente)
                .accept(MediaType.APPLICATION_JSON)); // Faz uma requisição GET para o recurso /tecnicos/{id} e espera
                                                      // que
                                                      // o tipo de resposta seja JSON

        resultado.andExpect(status().isNotFound()); // Espera que o status da resposta seja 404 (Not Found)
    }

    @Test
    public void insertDeveriaRetornarCreatedETecnicoDTO() throws Exception {
        String jsonBody = objectMapper.writeValueAsString(tecnicoDTO); // Converte o objeto para JSON

        ResultActions resultado = mockMvc.perform(post("/tecnicos") // Faz uma requisição POST para o recurso /tecnicos
                .content(jsonBody) // Envia o corpo da requisição
                .contentType(MediaType.APPLICATION_JSON) // Define o tipo de conteúdo
                .accept(MediaType.APPLICATION_JSON)); // Define o tipo de resposta

        resultado.andExpect(status().isCreated()); // Espera que o status da resposta seja 201 (Created)
        resultado.andExpect(jsonPath("$.id").exists()); // Espera que o JSON retornado tenha o campo id
        resultado.andExpect(jsonPath("$.nome").exists()); // Espera que o JSON retornado tenha o campo nome
    }

    @Test
    public void deleteDeveriaRetornarNoContentQuandoIDExistir() throws Exception {
        ResultActions resultado = mockMvc.perform(delete("/tecnicos/{id}", idExistente)
                .accept(MediaType.APPLICATION_JSON)); // Faz uma requisição DELETE para o recurso /tecnicos/{id} e
                                                      // espera
                                                      // que
                                                      // o tipo de resposta seja JSON

        resultado.andExpect(status().isNoContent()); // Espera que o status da resposta seja 204 (No Content)
    }

    @Test
    public void deleteDeveriaRetornarNotFoundQuandoIDNaoExistir() throws Exception {
        ResultActions resultado = mockMvc.perform(delete("/tecnicos/{id}", idNaoExistente)
                .accept(MediaType.APPLICATION_JSON)); // Faz uma requisição DELETE para o recurso /tecnicos/{id} e
                                                      // espera
                                                      // que
                                                      // o tipo de resposta seja JSON

        resultado.andExpect(status().isNotFound()); // Espera que o status da resposta seja 404 (Not Found)
    }

}
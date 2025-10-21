package br.edu.iftm.PPWIIJAVA.aviao;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import br.edu.iftm.PPWIIJAVA.config.TestConfig;
import br.edu.iftm.PPWIIJAVA.controller.AviaoController;
import br.edu.iftm.PPWIIJAVA.modelo.Aviao;
import br.edu.iftm.PPWIIJAVA.service.AviaoService;



@WebMvcTest(AviaoController.class)
@Import(TestConfig.class)
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AviaoService aviaoService;

    @AfterEach
    void resetMocks() {
        reset(aviaoService);
    }

    private List<Aviao> testCreateAviaotList(){
        Aviao aviaoB = new Aviao();
        aviaoB.setId(1);
        aviaoB.setNome("Su-57");
        aviaoB.setAlcanceKm(5000);
        aviaoB.setFabricante("Sukhoy");
        aviaoB.setTipoMissao("Multimissão");
        aviaoB.setTripulacao(2);
        aviaoB.setVelocidadeMax(2100);
        

        return List.of(aviaoB);
    }

    @Test
    @DisplayName("GET /aviao - Listar avioes na tela index sem usuário autenticado")
    void testIndexNotAuthenticatedUser() throws Exception {
         mockMvc.perform(get("/aviao"))
            .andExpect(status().isUnauthorized()); 
    }

    @Test
    @WithMockUser
    @DisplayName("GET /aviao - Listar aviao na tela index com usuário logado")
    void testIndexAuthenticatedUser() throws Exception {
        when(aviaoService.getAllAvioes()).thenReturn(testCreateAviaotList());

        mockMvc.perform(get("/aviao"))
               .andExpect(status().isOk())
               .andExpect(view().name("aviao/index"))
               .andExpect(model().attributeExists("aviaoList"))
               .andExpect(content().string(containsString("Listagem de Aviao")))
               .andExpect(content().string(containsString("Su-57")));
    }

    @Test
    @WithMockUser(username = "aluno@iftm.edu.br", authorities = { "Admin" })
    @DisplayName("GET /aviao/create - Exibe formulário de criação")
    void testCreateFormAuthorizedUser() throws Exception {
        mockMvc.perform(get("/aviao/create"))
                .andExpect(status().isOk())
                .andExpect(view().name("aviao/create"))
                .andExpect(model().attributeExists("aviao"))
                .andExpect(content().string(containsString("Cadastrar Aviao")));
    }

    @Test
    @WithMockUser(username = "aluno2@iftm.edu.br", authorities = { "Manager" })
    @DisplayName("GET /aviao - Verificar o link de cadastrar para um usuario não admin logado")
    void testCreateFormNotAuthorizedUser() throws Exception {
        when(aviaoService.getAllAvioes()).thenReturn(testCreateAviaotList());
       // Obter o HTML da página renderizada pelo controlador
       mockMvc.perform(get("/aviao/create"))
            .andExpect(status().isOk())
            .andExpect(view().name("aviao/create"))
            .andExpect(model().attributeExists("aviao"))
            .andExpect(content().string(not(containsString("<a class=\"dropdown-item\" href=\"/aviao/create\">Cadastrar</a>"))));
    }

    @Test
    @WithMockUser
    @DisplayName("POST /aviao/save - Falha na validação e retorna para o formulário")
    void testSaveAviaoValidationError() throws Exception {
        Aviao aviao = new Aviao(); // Produto sem nome, o que causará erro de validação

        mockMvc.perform(post("/aviao/save")
                        .with(csrf())
                        .flashAttr("aviao", aviao))
                .andExpect(status().isOk())
                .andExpect(view().name("aviao/create"))
                .andExpect(model().attributeHasErrors("aviao"));

        verify(aviaoService, never()).saveAviao(any(Aviao.class));
    }

    @Test
    @WithMockUser(username = "aluno@iftm.edu.br", authorities = { "Admin" })
    @DisplayName("POST /aviao/save - Aviao válido é salvo com sucesso")
    void testSaveValidAviao() throws Exception {
        Aviao aviao = new Aviao();
        aviao.setId(1);
        aviao.setNome("Su-57");
        aviao.setAlcanceKm(5000);
        aviao.setFabricante("Sukhoy");
        aviao.setTipoMissao("Multimissão");
        aviao.setTripulacao(2);
        aviao.setVelocidadeMax(2100);
        

        mockMvc.perform(post("/aviao/save")
                        .with(csrf())
                        .flashAttr("aviao", aviao))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/aviao"));
        
        verify(aviaoService).saveAviao(any(Aviao.class));
    }

}
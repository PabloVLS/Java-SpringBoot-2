package br.edu.iftm.PPWIIJAVA.aviao;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import br.edu.iftm.PPWIIJAVA.modelo.Aviao;
import br.edu.iftm.PPWIIJAVA.repository.AviaoForcaAereaRepository;
import jakarta.transaction.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test") // Usa application-test.properties
@Transactional // Limpa o banco após cada teste
public class AviaoIntegrationTest {
    

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AviaoForcaAereaRepository aviaoRepository;

    @Test
    @WithMockUser(authorities = { "Admin" })
    void testSaveProductIntegration() throws Exception {

        Aviao aviaoA = new Aviao();
        aviaoA.setModelo("Sukhoy");
        aviaoA.setNome("Su-57");
        aviaoA.setAlcanceKm(5000);
        aviaoA.setFabricante("Sukhoy");
        aviaoA.setTipoMissao("Multimissão");
        aviaoA.setTripulacao(2);
        aviaoA.setVelocidadeMax(2100);


        mockMvc.perform(post("/aviao/save")
                .with(csrf())
                .flashAttr("aviao", aviaoA))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/aviao"));

        // Verifica no banco se foi salvo
        assertTrue(aviaoRepository.findAll()
                .stream()
                .anyMatch(p -> "Su-57".equals(p.getNome())));
        
    }
}
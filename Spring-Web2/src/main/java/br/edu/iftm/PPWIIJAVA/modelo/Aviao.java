package br.edu.iftm.PPWIIJAVA.modelo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Getter
@Table(name = "aviao")
@Setter
public class Aviao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Size(min = 3, max = 50, message= "Nome deve conter pelo menos 3 caracteres")
    @NotBlank(message= "Nome é um campo obrigatório")
    @Column(name = "nome", nullable = false)
    private String nome; 


    @Size(min = 3, max = 50, message= "Modelo deve conter pelo menos 3 caracteres")
    @NotBlank(message= "Modelo é um campo obrigatório")
    @Column(name = "modelo", nullable = false)
    private String modelo; 


    @Size(min = 3, max = 50, message= "Fabricante deve conter pelo menos 3 caracteres")
    @NotBlank(message= "Fabricante é um campo obrigatório")
    @Column(name = "fabricante", nullable = false)
    private String fabricante; 

    @Size(min = 3, max = 50, message= "tipoMissao deve conter pelo menos 3 caracteres")
    @NotBlank(message= "Tipo da Missao é um campo obrigatório")
    @Column(name = "tipoMissao", nullable = false)
    private String tipoMissao; 

    @NotNull(message = "Informe um alcance válido")
    @Min(value = 1, message = "O alcance deve ser maior que 0")
    @Column(name = "alcanceKm", nullable = false)
    private double alcanceKm;

    @NotNull(message = "Informe uma velocidade válida")
    @Min(value = 1, message = "A velocidade máxima deve ser maior que 0")
    @Column(name = "velocidadeMax", nullable = false)
    private double velocidadeMax;

    @NotNull(message = "Informe uma quantidade válida")
    @Min(value = 1, message = "A tripulação deve ser maior que 0")
    @Column(name = "tripulacao", nullable = false)
    private int tripulacao;
}

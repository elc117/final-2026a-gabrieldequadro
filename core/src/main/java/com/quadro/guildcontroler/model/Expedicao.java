package com.quadro.guildcontroler.model;

public class Expedicao {
    private MembroGuilda personagem1;
    private MembroGuilda personagem2;
    private MembroGuilda personagemAtivo;

    public Expedicao(MembroGuilda p1, MembroGuilda p2) {
        this.personagem1 = p1;
        this.personagem2 = p2;
        this.personagemAtivo = p1;
    }

    // Método para apertar o botão de "Trocar de Personagem"
    public void alternarPersonagem() {
        if (personagemAtivo == personagem1) {
            personagemAtivo = personagem2;
        } else {
            personagemAtivo = personagem1;
        }
        System.out.println("Trocou de personagem! Agora controlando: " + personagemAtivo.getNome());
    }


    public void enfrentarObstaculo(Obstaculo obstaculo) {
        System.out.println("\n[AÇÃO] O grupo encontrou: " + obstaculo.getNome());

        boolean sucesso = obstaculo.tentarSuperar(personagemAtivo);

        if (!sucesso) {
            System.out.println("Dica: Talvez o outro membro do grupo consiga resolver isso...");
        }
    }

    public MembroGuilda getPersonagemAtivo() {
        return personagemAtivo;
    }
}
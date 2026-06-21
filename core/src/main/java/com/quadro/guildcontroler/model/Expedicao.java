package com.quadro.guildcontroler.model;

import java.util.ArrayList;
import java.util.List;

public class Expedicao {
    private List<MembroGuilda> equipe;
    private int indiceAtivo;

    public Expedicao(List<MembroGuilda> membrosDaGuilda) {
        this.equipe = new ArrayList<>(membrosDaGuilda);
        this.indiceAtivo = 0;
    }

    public void alternarPersonagem() {
        if (equipe.size() > 1) {
            indiceAtivo++;
            if (indiceAtivo >= equipe.size()) {
                indiceAtivo = 0;
            }
            System.out.println("[SISTEMA] Trocou o personagem ativo para: " + getPersonagemAtivo().getNome());
        }
    }

    public MembroGuilda getPersonagemAtivo() {
        return equipe.get(indiceAtivo);
    }
}
package com.quadro.guildcontroler.model;

public abstract class MembroGuilda {
    private String nome;
    private int nivelBase;
    private double custoRecrutamento;
    private Item itemEquipado;

    public MembroGuilda(String nome, int nivel, double custoRecrutamento) {
        this.nome = nome;
        this.nivelBase = nivel;
        this.custoRecrutamento = custoRecrutamento;
        this.itemEquipado = null;
    }

    public String getNome() { return nome; }
    public double getCustoRecrutamento() { return custoRecrutamento; }
    public void equiparItem(Item novoItem) {
        this.itemEquipado = novoItem;
        System.out.println("[" + nome + "] equipou " + novoItem.getNome() + "!");
    }

    public int getNivel() {
        return getNivelTotal();
    }

    public int getNivelTotal() {
        int nivelFinal = this.nivelBase;
        if (this.itemEquipado != null) {
            nivelFinal += this.itemEquipado.getBonusNivel();
        }
        return nivelFinal;
    }

    public abstract void exibirHabilidade();

    @Override
    public String toString() {
        String infoItem = (itemEquipado != null) ? " | Equipado: " + itemEquipado.getNome() : " | Desarmado";
        return nome + " (Nível Real: " + getNivelTotal() + infoItem + ")";
    }
}
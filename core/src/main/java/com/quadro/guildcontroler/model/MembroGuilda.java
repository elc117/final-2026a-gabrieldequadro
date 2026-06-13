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


    public int getNivel() {
        if (itemEquipado != null) {
            return nivelBase + itemEquipado.getBonusNivel();
        }
        return nivelBase;
    }

    public void equipar(Item novoItem) {
        this.itemEquipado = novoItem;
        System.out.println("[" + nome + "] equipou " + novoItem.getNome() + "!");
    }

    public abstract void exibirHabilidade();

    @Override
    public String toString() {
        String infoItem = (itemEquipado != null) ? " | Equipado: " + itemEquipado.getNome() : " | Desarmado";
        return nome + " (Nível Real: " + getNivel() + infoItem + ")";
    }
}
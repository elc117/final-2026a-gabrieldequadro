package com.quadro.guildcontroler.model;
public class Guerreiro extends MembroGuilda {
    private int forcaFisica;

    public Guerreiro(String nome, int nivel, double custo, int forcaFisica) {
        super(nome, nivel, custo); // Chama o construtor da classe pai
        this.forcaFisica = forcaFisica;
    }

    @Override
    public void exibirHabilidade() {
        System.out.println(getNome() + " balança a espada com força " + forcaFisica + "!");
    }

    @Override
    public String toString() {
        return super.toString() + " - Classe: Guerreiro";
    }
}
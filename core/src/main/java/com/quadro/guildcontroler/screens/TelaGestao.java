package com.quadro.guildcontroler.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.quadro.guildcontroler.MainGame;
import com.quadro.guildcontroler.model.Guerreiro;
import com.quadro.guildcontroler.model.Mago;
import com.quadro.guildcontroler.model.Ladino;
import com.quadro.guildcontroler.model.MembroGuilda;

public class TelaGestao implements Screen {
    private final MainGame game;
    private BitmapFont fonte;

    // Contadores simples para dar nomes diferentes aos recrutados (Ex: Guerreiro 1, Guerreiro 2)
    private int qtdGuerreiros = 1;
    private int qtdMagos = 1;
    private int qtdLadinos = 1;

    public TelaGestao(MainGame game) {
        this.game = game;
    }

    @Override
    public void show() {
        System.out.println("Ecrã de Gestão da Guilda Iniciado!");
        fonte = new BitmapFont();
        fonte.getData().setScale(1.2f); // Tamanho ligeiramente menor para caber tudo
    }

    @Override
    public void render(float delta) {
        // 1. ESCUTAR O TECLADO (Interação/Controle)
        checarTecladoRecrutamento();

        // 2. DESENHAR NA TELA (Visual/View)
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();

        // Dados da Guilda
        String nomeGuilda = game.minhaGuilda.getNome();
        double ouroAtual = game.minhaGuilda.getOuro();

        fonte.draw(game.batch, "=== PAINEL DE GESTÃO (TAVERNA) ===", 50, 450);
        fonte.draw(game.batch, "Guilda: " + nomeGuilda, 50, 410);
        fonte.draw(game.batch, "Ouro: " + ouroAtual + " PO", 50, 380);

        // Opções de Recrutamento
        fonte.draw(game.batch, "--- OPÇÕES DE RECRUTAMENTO ---", 50, 320);
        fonte.draw(game.batch, "[Pressione G] Recrutar Guerreiro (Custo: 150 PO)", 50, 290);
        fonte.draw(game.batch, "[Pressione M] Recrutar Mago      (Custo: 250 PO)", 50, 260);
        fonte.draw(game.batch, "[Pressione L] Recrutar Ladino    (Custo: 120 PO)", 50, 230);

        // Listar Membros Atuais da Guilda do lado direito da tela
        fonte.draw(game.batch, "--- MEMBROS DA GUILDA ---", 450, 320);

        int posY = 290; // Posição vertical inicial para listar os heróis
        if (game.minhaGuilda.getMembros().isEmpty()) {
            fonte.draw(game.batch, "(Nenhum herói recrutado)", 450, posY);
        } else {
            for (MembroGuilda heroi : game.minhaGuilda.getMembros()) {
                fonte.draw(game.batch, "- " + heroi.toString(), 450, posY);
                posY -= 25; // Desce a linha para o próximo herói não ficar por cima
            }
        }

        fonte.draw(game.batch, "[Pressione ESPAÇO para ir para a Expedição]", 50, 50);

        game.batch.end();
    }

    // Método auxiliar isolado para cuidar da lógica de entrada de dados (POO - Separação de conceitos)
    private void checarTecladoRecrutamento() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.G)) {
            Guerreiro g = new Guerreiro("Guerreiro " + qtdGuerreiros, 1, 150.0, 15);
            if (game.minhaGuilda.recrutar(g)) {
                qtdGuerreiros++;
            }
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.M)) {
            Mago m = new Mago("Mago " + qtdMagos, 1, 250.0, 18);
            if (game.minhaGuilda.recrutar(m)) {
                qtdMagos++;
            }
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.L)) {
            Ladino l = new Ladino("Ladino " + qtdLadinos, 1, 120.0, 14);
            if (game.minhaGuilda.recrutar(l)) {
                qtdLadinos++;
            }
        }
    }

    @Override
    public void dispose() {
        fonte.dispose();
    }

    @Override public void resize(int width, int height) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() { dispose(); }
}
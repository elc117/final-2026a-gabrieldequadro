package com.quadro.guildcontroler.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.quadro.guildcontroler.MainGame;
import com.quadro.guildcontroler.model.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TelaPlataforma implements Screen {
    private final MainGame game;
    private final Expedicao expedicao;
    private ShapeRenderer shapeRenderer;

    private final float ALTURA_CHAO = 100f;


    private Rectangle jogadorRect;
    private float jogadorVelocidadeX = 300f;
    private float velocidadeY = 0f;
    private final float GRAVIDADE = -1200f;
    private final float FORCA_PULO = 550f;
    private boolean estaNoChao = true;


    private boolean isFurtivo = false;
    private float tempoFurtividade = 0f;


    private List<Rectangle> moedas;
    private Rectangle obstaculoVisual;
    private Obstaculo obstaculoLogico;
    private boolean portaDestruida = false;


    private Rectangle guardaFisico;
    private Rectangle guardaVisao;

    public TelaPlataforma(MainGame game, Expedicao expedicao) {
        this.game = game;
        this.expedicao = expedicao;
    }

    @Override
    public void show() {
        shapeRenderer = new ShapeRenderer();
        jogadorRect = new Rectangle(50, ALTURA_CHAO, 40, 40);

        moedas = new ArrayList<>();
        moedas.add(new Rectangle(300, ALTURA_CHAO + 20, 20, 20));
        moedas.add(new Rectangle(450, ALTURA_CHAO + 150, 20, 20));

        obstaculoVisual = new Rectangle(550, ALTURA_CHAO, 50, 140);
        obstaculoLogico = new Obstaculo("Portão de Ferro", "Força", 1, 200.0);
        guardaFisico = new Rectangle(850, ALTURA_CHAO, 40, 60); // O corpo do guarda
        guardaVisao = new Rectangle(700, ALTURA_CHAO, 150, 60);
    }

    @Override
    public void render(float delta) {
        //INPUTS ESPECIAIS (TAB, E, Q)

        MembroGuilda heroiAtivo = expedicao.getPersonagemAtivo();
        if (Gdx.input.isKeyJustPressed(Input.Keys.TAB)) {
            expedicao.alternarPersonagem();
            isFurtivo = false;
        }
        // Interagir com Objetos (E)
        if (Gdx.input.isKeyJustPressed(Input.Keys.E) && !portaDestruida) {
            float centroJogador = jogadorRect.x + (jogadorRect.width / 2);
            float centroPorta = obstaculoVisual.x + (obstaculoVisual.width / 2);
            if (Math.abs(centroJogador - centroPorta) <= 70f) {
                if (obstaculoLogico.tentarSuperar(heroiAtivo)) {
                    portaDestruida = true;
                    game.minhaGuilda.adicionarOuro(obstaculoLogico.getRecompensaOculta());
                }
            }
        }

        // HABILIDADE DE CLASSE
        if (Gdx.input.isKeyJustPressed(Input.Keys.Q) && !isFurtivo) {
            if (heroiAtivo instanceof Ladino) {
                isFurtivo = true;
                tempoFurtividade = 3.0f; // 3 segundos de invisibilidade
                System.out.println("Ladino usou Passo das Sombras!");
            } else {
                System.out.println(heroiAtivo.getNome() + " não possui habilidades furtivas.");
            }
        }


        if (isFurtivo) {
            tempoFurtividade -= delta;
            if (tempoFurtividade <= 0) {
                isFurtivo = false;
                System.out.println("A furtividade acabou! O Ladino está visível.");
            }
        }

        // FÍSICA E MOVIMENTAÇÃO
        float posicaoAntigaX = jogadorRect.x;
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)) jogadorRect.x -= jogadorVelocidadeX * delta;
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)) jogadorRect.x += jogadorVelocidadeX * delta;
        if (jogadorRect.x < 0) jogadorRect.x = 0;
        if (!portaDestruida && jogadorRect.overlaps(obstaculoVisual)) {
            jogadorRect.x = posicaoAntigaX;
        }
        if (jogadorRect.overlaps(guardaFisico)) {
            jogadorRect.x = posicaoAntigaX;
        }

        if (jogadorRect.overlaps(guardaVisao)) {
            if (!isFurtivo) {
                System.out.println("🚨 ALARME! O Guarda te viu!");
                game.setScreen(new TelaGestao(game));
                return;
            }
        }

        if (!estaNoChao) velocidadeY += GRAVIDADE * delta;
        if ((Gdx.input.isKeyJustPressed(Input.Keys.UP) || Gdx.input.isKeyJustPressed(Input.Keys.W)) && estaNoChao) {
            velocidadeY = FORCA_PULO; estaNoChao = false;
        }
        jogadorRect.y += velocidadeY * delta;
        if (jogadorRect.y <= ALTURA_CHAO) {
            jogadorRect.y = ALTURA_CHAO; velocidadeY = 0f; estaNoChao = true;
        }

        Iterator<Rectangle> iter = moedas.iterator();
        while (iter.hasNext()) {
            Rectangle moeda = iter.next();
            if (jogadorRect.overlaps(moeda)) {
                game.minhaGuilda.adicionarOuro(50);
                iter.remove();
            }
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) game.setScreen(new TelaGestao(game));

        // RENDERIZAÇÃO GRÁFICA
        Gdx.gl.glClearColor(0.5f, 0.8f, 1f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        shapeRenderer.begin(ShapeType.Filled);
        shapeRenderer.setColor(0.2f, 0.8f, 0.2f, 1f);
        shapeRenderer.rect(0, 0, 1000, ALTURA_CHAO);
        shapeRenderer.setColor(1f, 0.8f, 0.1f, 1f);
        for (Rectangle moeda : moedas) shapeRenderer.rect(moeda.x, moeda.y, moeda.width, moeda.height);

        if (!portaDestruida) {
            shapeRenderer.setColor(0.1f, 0.2f, 0.5f, 1f);
            shapeRenderer.rect(obstaculoVisual.x, obstaculoVisual.y, obstaculoVisual.width, obstaculoVisual.height);
        }

        shapeRenderer.setColor(1f, 0.5f, 0f, 1f);
        shapeRenderer.rect(guardaFisico.x, guardaFisico.y, guardaFisico.width, guardaFisico.height);
        shapeRenderer.setColor(1f, 0.8f, 0f, 0.3f);
        shapeRenderer.rect(guardaVisao.x, guardaVisao.y, guardaVisao.width, guardaVisao.height);

        // COR DINÂMICA DO JOGADOR
        if (isFurtivo) {
            shapeRenderer.setColor(0.3f, 0.3f, 0.3f, 1f);
        } else {
            if (heroiAtivo instanceof Guerreiro) shapeRenderer.setColor(0.8f, 0.1f, 0.1f, 1f);
            else if (heroiAtivo instanceof Mago) shapeRenderer.setColor(0.6f, 0.1f, 0.8f, 1f);
            else if (heroiAtivo instanceof Ladino) shapeRenderer.setColor(0.1f, 0.6f, 0.1f, 1f);
        }

        shapeRenderer.rect(jogadorRect.x, jogadorRect.y, jogadorRect.width, jogadorRect.height);
        shapeRenderer.end();
    }

    @Override public void dispose() { shapeRenderer.dispose(); }
    @Override public void resize(int width, int height) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() { dispose(); }
}
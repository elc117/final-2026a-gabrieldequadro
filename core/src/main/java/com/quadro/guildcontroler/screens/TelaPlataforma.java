package com.quadro.guildcontroler.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
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
    private TiledMap mapa;
    private OrthogonalTiledMapRenderer mapRenderer;
    private List<Rectangle> colisoesMapa;
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
        jogadorRect = new Rectangle(50, 200, 32, 32);

        mapa = new TmxMapLoader().load("mapa.tmx");
        mapRenderer = new OrthogonalTiledMapRenderer(mapa, 2f);

        colisoesMapa = new ArrayList<>();
        moedas = new ArrayList<>();
        obstaculoLogico = new Obstaculo("Portão de Ferro", "Força", 1, 200.0);


        MapObjects objetosDoMapa = mapa.getLayers().get("Colisoes").getObjects();

        for (MapObject objeto : objetosDoMapa) {
            if (objeto instanceof RectangleMapObject) {
                Rectangle rectOriginal = ((RectangleMapObject) objeto).getRectangle();


                Rectangle rect = new Rectangle(
                        rectOriginal.x * 2f, rectOriginal.y * 2f,
                        rectOriginal.width * 2f, rectOriginal.height * 2f
                );

                String nomeDoObjeto = objeto.getName();

                if (nomeDoObjeto == null) {
                    colisoesMapa.add(rect);
                } else if (nomeDoObjeto.equalsIgnoreCase("moeda")) {
                    moedas.add(rect);
                } else if (nomeDoObjeto.equalsIgnoreCase("porta")) {
                    obstaculoVisual = rect;
                } else if (nomeDoObjeto.equalsIgnoreCase("guarda")) {
                    guardaFisico = rect;
                    // Visão do guarda 150 pixels para a esquerda
                    guardaVisao = new Rectangle(guardaFisico.x - 150, guardaFisico.y, 150, guardaFisico.height);
                }
            }
        }
    }

    @Override
    public void render(float delta) {
        MembroGuilda heroiAtivo = expedicao.getPersonagemAtivo();


        if (Gdx.input.isKeyJustPressed(Input.Keys.TAB)) {
            expedicao.alternarPersonagem();
            isFurtivo = false;
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.E) && !portaDestruida && obstaculoVisual != null) {
            float centroJogador = jogadorRect.x + (jogadorRect.width / 2);
            float centroPorta = obstaculoVisual.x + (obstaculoVisual.width / 2);
            if (Math.abs(centroJogador - centroPorta) <= 70f) {
                if (obstaculoLogico.tentarSuperar(heroiAtivo)) {
                    portaDestruida = true;
                    game.minhaGuilda.adicionarOuro(obstaculoLogico.getRecompensaOculta());
                }
            }
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.Q) && !isFurtivo && heroiAtivo instanceof Ladino) {
            isFurtivo = true; tempoFurtividade = 3.0f;
        }

        if (isFurtivo) {
            tempoFurtividade -= delta;
            if (tempoFurtividade <= 0) isFurtivo = false;
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) game.setScreen(new TelaGestao(game));

        // --- 2. FÍSICA VERTICAL (Gravidade e Pulo) ---
        if (!estaNoChao) velocidadeY += GRAVIDADE * delta;

        if ((Gdx.input.isKeyJustPressed(Input.Keys.UP) || Gdx.input.isKeyJustPressed(Input.Keys.W)) && estaNoChao) {
            velocidadeY = FORCA_PULO;
        }

        jogadorRect.y += velocidadeY * delta;
        estaNoChao = false;


        for (Rectangle parede : colisoesMapa) {
            if (jogadorRect.overlaps(parede)) {
                if (velocidadeY < 0) {
                    jogadorRect.y = parede.y + parede.height;
                    estaNoChao = true;
                } else if (velocidadeY > 0) {
                    jogadorRect.y = parede.y - jogadorRect.height;
                }
                velocidadeY = 0f;
            }
        }

        // --- 3. FÍSICA HORIZONTAL ---
        float posicaoAntigaX = jogadorRect.x;
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)) jogadorRect.x -= jogadorVelocidadeX * delta;
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)) jogadorRect.x += jogadorVelocidadeX * delta;
        if (jogadorRect.x < 0) jogadorRect.x = 0;

        for (Rectangle parede : colisoesMapa) {
            if (jogadorRect.overlaps(parede)) {
                jogadorRect.x = posicaoAntigaX;
            }
        }

        if (!portaDestruida && obstaculoVisual != null && jogadorRect.overlaps(obstaculoVisual)) {
            jogadorRect.x = posicaoAntigaX;
        }
        if (guardaFisico != null && jogadorRect.overlaps(guardaFisico)) {
            jogadorRect.x = posicaoAntigaX;
        }

        if (guardaVisao != null && jogadorRect.overlaps(guardaVisao) && !isFurtivo) {
            System.out.println("🚨 ALARME! O Guarda te viu!");
            game.setScreen(new TelaGestao(game));
            return;
        }

        Iterator<Rectangle> iter = moedas.iterator();
        while (iter.hasNext()) {
            Rectangle moeda = iter.next();
            if (jogadorRect.overlaps(moeda)) {
                game.minhaGuilda.adicionarOuro(50);
                iter.remove();
            }
        }

        // --- 4. RENDERIZAÇÃO GRÁFICA ---
        game.camera.update();

        Gdx.gl.glClearColor(0.2f, 0.2f, 0.3f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        mapRenderer.setView(game.camera);
        mapRenderer.render();
        shapeRenderer.setProjectionMatrix(game.camera.combined);
        shapeRenderer.begin(ShapeType.Filled);
        shapeRenderer.setColor(1f, 0.8f, 0.1f, 1f);
        for (Rectangle moeda : moedas) shapeRenderer.rect(moeda.x, moeda.y, moeda.width, moeda.height);
        if (!portaDestruida && obstaculoVisual != null) {
            shapeRenderer.setColor(0.1f, 0.2f, 0.5f, 1f);
            shapeRenderer.rect(obstaculoVisual.x, obstaculoVisual.y, obstaculoVisual.width, obstaculoVisual.height);
        }

        if (guardaFisico != null) {
            shapeRenderer.setColor(1f, 0.5f, 0f, 1f);
            shapeRenderer.rect(guardaFisico.x, guardaFisico.y, guardaFisico.width, guardaFisico.height);
            shapeRenderer.setColor(1f, 0.8f, 0f, 0.3f);
            shapeRenderer.rect(guardaVisao.x, guardaVisao.y, guardaVisao.width, guardaVisao.height);
        }

        if (isFurtivo) shapeRenderer.setColor(0.3f, 0.3f, 0.3f, 1f);
        else if (heroiAtivo instanceof Guerreiro) shapeRenderer.setColor(0.8f, 0.1f, 0.1f, 1f);
        else if (heroiAtivo instanceof Mago) shapeRenderer.setColor(0.6f, 0.1f, 0.8f, 1f);
        else if (heroiAtivo instanceof Ladino) shapeRenderer.setColor(0.1f, 0.6f, 0.1f, 1f);

        shapeRenderer.rect(jogadorRect.x, jogadorRect.y, jogadorRect.width, jogadorRect.height);
        shapeRenderer.end();
    }

    @Override public void resize(int width, int height) { game.viewport.update(width, height); }
    @Override public void dispose() {
        shapeRenderer.dispose();
        if(mapa != null) mapa.dispose();
        if(mapRenderer != null) mapRenderer.dispose();
    }
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() { dispose(); }
}
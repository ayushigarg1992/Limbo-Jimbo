package com.nui.limbojimbo;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import java.util.ArrayList;
import java.util.Collection;

public class startScreen implements Screen {
    private SpriteBatch batch;
    private Stage stage;
    private Texture myTexture;
    private TextureRegion myTextureRegion;
    private TextureRegionDrawable myTexRegionDrawable;
    private ImageButton button;
    private Texture background;
    private SpriteBatch spriteB;
    private TextureAtlas wizardatlas;
    private Animation animation;
    private float timePassed=0;
    private Game game;
    int x = 0;
    boolean ispressed = false;
    SwiperImproved main;
    int y = 80;
    Music click;



    public  startScreen(final Game game) {
        batch = new SpriteBatch();
        this.game = game;
        wizardatlas = new TextureAtlas(Gdx.files.internal("broom.pack"));

        animation =new Animation(1/3f, wizardatlas.getRegions());

        myTexture = new Texture(Gdx.files.internal("button.png"));

        myTextureRegion = new TextureRegion(myTexture);
        myTexRegionDrawable = new TextureRegionDrawable(myTextureRegion);
        button = new ImageButton(myTexRegionDrawable,null,null); //Set the button up
//        button.setHeight(600);
//        button.setWidth(300);
        button.setPosition(Gdx.graphics.getWidth()/2-button.getWidth()/2, Gdx.graphics.getHeight()/4-button.getHeight()/4);

        background = new Texture(Gdx.files.internal("Start.jpg"));
        click = Gdx.audio.newMusic(Gdx.files.internal("data/kill.wav"));
        stage = new Stage(new ScreenViewport()); //Set up a stage for the ui
        stage.addActor(button); //Add the button to the stage to perform rendering and take input.
        Gdx.input.setInputProcessor(stage); //Start taking input from the ui
        button.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ispressed = true;
                button.setChecked(true);
                button.remove();
                nextscreen();

            }
        });


    }


    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(background,0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());

        //timePassed += Gdx.graphics.getDeltaTime();
        if (ispressed) {
            batch.draw((TextureRegion) animation.getKeyFrame(timePassed+=delta, true), x, y, 300, 500);
            x += 10;
            if (x == Gdx.graphics.getWidth()-30){
                game.setScreen(main);
            }
        }
        batch.end();
        stage.act(delta);
        stage.draw();
    }


    public void  nextscreen(){
        click.play();
        Gdx.app.postRunnable(new Runnable() {
            @Override
            public void run() {
                // Do something on the main thread
                main=new SwiperImproved(game);

            }
        });


    }

  /*  public void  nextscreen(){
        click.play();
        Gdx.app.postRunnable(new Runnable() {
            @Override
            public void run() {
                // Do something on the main thread
                main=new SwiperImproved(game);

            }
        });


    }*/


    @Override
    public void  hide(){
       // game.setScreen(new SwiperImproved());
    }

    @Override
    public void  pause(){

    }

    @Override
    public void  resume(){

    }

    @Override
    public void  resize(int x, int y){

    }
    @Override
    public void  show(){

    }
    @Override
    public void dispose() {
      //  stage.dispose();
     /*   batch.dispose();
        myTexture.dispose();
        background.dispose();
      //  spriteB.dispose();
        wizardatlas.dispose();
        game.dispose();
        main.dispose();
        click.dispose();*/
    }

}
package com.nui.limbojimbo;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by brentaureli on 10/8/15.
 */
public class GameOverScreen implements Screen {
    private Viewport viewport;
    private SpriteBatch batch;
    private Stage stage;
    private Stage stage1;
    private Texture myTexture;
    private Texture myTexture1;
    private TextureRegion myTextureRegion;
    private TextureRegion myTextureRegion1;
    private TextureRegionDrawable myTexRegionDrawable;
    private TextureRegionDrawable myTexRegionDrawable1;
    private ImageButton button;
    private ImageButton button1;
    private ImageButton gameover;
    private Texture background;
    private SpriteBatch spriteB;
    private TextureAtlas wizardatlas;
    private Animation animation;
    private float timePassed=0;
    Music click;

    private Game game;

    public GameOverScreen(Game game){
        batch = new SpriteBatch();
        this.game = game;
        wizardatlas = new TextureAtlas(Gdx.files.internal("wizard.atlas"));

        animation =new Animation(1/3f, wizardatlas.getRegions());

       /* Texture gameTexture = new Texture(Gdx.files.internal("gameOver.png"));
        TextureRegion gameRegion = new TextureRegion(gameTexture);
        TextureRegionDrawable gameRegionDrawable = new TextureRegionDrawable(gameRegion);
        gameover = new ImageButton(gameRegionDrawable); //Set the button up
        gameover.setPosition(Gdx.graphics.getWidth()/2-gameover.getWidth()/2, Gdx.graphics.getHeight()/4-gameover.getHeight()/4);*/

        myTexture = new Texture(Gdx.files.internal("playAgain.png"));
        myTextureRegion = new TextureRegion(myTexture);
        myTexRegionDrawable = new TextureRegionDrawable(myTextureRegion);
        button = new ImageButton(myTexRegionDrawable); //Set the button up

        myTexture1 = new Texture(Gdx.files.internal("playDemo.png"));
        myTextureRegion1 = new TextureRegion(myTexture1);
        myTexRegionDrawable1 = new TextureRegionDrawable(myTextureRegion1);
        button1 = new ImageButton(myTexRegionDrawable1); //Set the button up


        button.setPosition(Gdx.graphics.getWidth()/2-button.getWidth()/2, Gdx.graphics.getHeight()/4-button.getHeight()/4);
        button1.setPosition(Gdx.graphics.getWidth()/2-button1.getWidth()/2, Gdx.graphics.getHeight()/8-button1.getHeight()/8);

        background = new Texture(Gdx.files.internal("nextLevel.jpg"));
        click = Gdx.audio.newMusic(Gdx.files.internal("data/kill.wav"));
        stage = new Stage(new ScreenViewport()); //Set up a stage for the ui
        stage1 = new Stage(new ScreenViewport());
        stage.addActor(button); //Add the button to the stage to perform rendering and take input.
        //stage.addActor(gameover);
        stage1.addActor(button1);
        Gdx.input.setInputProcessor(stage); //Start taking input from the ui
        Gdx.input.setInputProcessor(stage1);
        button.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                //System.out.println("clicked");
                // game.setScreen(new SwiperImproved());
                nextscreen();
                return true;
            }
        });

        button1.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                //System.out.println("clicked");
                // game.setScreen(new SwiperImproved());
                demoscreen();
                return true;
            }
        });

    }


    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(background,0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());

        //timePassed += Gdx.graphics.getDeltaTime();
        // batch.draw((TextureRegion) animation.getKeyFrame(timePassed, true), 300, 500);
        batch.end();
        stage.act(delta);
        stage.draw();
        stage1.act(delta);
        stage1.draw();
    }


    public void  nextscreen(){
        click.play();
        game.setScreen(new SwiperImproved(game));
    }

    public void  demoscreen(){
        click.play();
        game.setScreen(new SwiperImprovedDemo(game));
    }


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
        stage.dispose();
    }

}
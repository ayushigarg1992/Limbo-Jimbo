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
    private Texture myTexture;
    private TextureRegion myTextureRegion;
    private TextureRegionDrawable myTexRegionDrawable;
    private ImageButton button;
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
//        button.setHeight(600);
//        button.setWidth(300);
        button.setPosition(Gdx.graphics.getWidth()/2-button.getWidth()/2, Gdx.graphics.getHeight()/4-button.getHeight()/4);
        background = new Texture(Gdx.files.internal("gameover.jpg"));
        click = Gdx.audio.newMusic(Gdx.files.internal("data/kill.wav"));
        stage = new Stage(new ScreenViewport()); //Set up a stage for the ui
        stage.addActor(button); //Add the button to the stage to perform rendering and take input.
        //stage.addActor(gameover);
        Gdx.input.setInputProcessor(stage); //Start taking input from the ui
        button.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                //System.out.println("clicked");
                // game.setScreen(new SwiperImproved());
                nextscreen();
                return true;
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
        // batch.draw((TextureRegion) animation.getKeyFrame(timePassed, true), 300, 500);
        batch.end();
        stage.act(delta);
        stage.draw();
    }


    public void  nextscreen(){
        click.play();
        game.setScreen(new SwiperImproved(game));
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
package com.nui.limbojimbo;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g3d.particles.ResourceData;
import com.badlogic.gdx.utils.Timer;

/**
 * Created by Maddy on 15/02/17.
 */

public class GdxSplashScreenGame extends Game {

    private static long SPLASH_MINIMUM_MILLIS = 2000L;
    private AssetManager manager;
    public GdxSplashScreenGame() {
        super();
         manager = new AssetManager();

    }
    public AssetManager getmanager(){
        return this.manager;
    }

    public void load (){
        manager.load("bg1.jpg", Texture.class);
        manager.load("bg2.jpg", Texture.class);
        manager.load("bg3.jpg", Texture.class);
        manager.load("bg4.jpg", Texture.class);
        manager.load("alpha.png",Texture.class)/*new Texture(Gdx.files.internal("alpha.png"))*/;
        manager.load("ss.png",Texture.class)/*new Texture(Gdx.files.internal("ss.png"))*/;
        manager.load("HLine.png",Texture.class)/*new Texture(Gdx.files.internal("HLine.png"))*/;
        manager.load("Circle.png",Texture.class)/*new Texture(Gdx.files.internal("Circle.png"))*/;
        manager.load("VLine.png",Texture.class)/*new Texture(Gdx.files.internal("VLine.png"))*/;
        manager.load("heart.png",Texture.class);
        manager.load("heartempty.png",Texture.class);
        manager.load("arrow.png",Texture.class);
        manager.load("witch.png",Texture.class);
        manager.load("pause_filled.png",Texture.class);
        manager.load("play_filled.png",Texture.class);
        manager.load("ghost1_left.atlas",TextureAtlas.class);

        manager.load("ghost2.atlas",TextureAtlas.class);
        manager.load("ghost3.atlas",TextureAtlas.class);

        manager.load("ghostLeftKill.atlas",TextureAtlas.class);
        manager.load("ghost2_kill.atlas",TextureAtlas.class);
        manager.load("ghost3_kill.atlas",TextureAtlas.class);
        manager.load("ghost1_right.atlas",TextureAtlas.class);

        manager.load("ghost2.atlas",TextureAtlas.class);
        manager.load("ghost3.atlas",TextureAtlas.class);
        manager.load("ghost_rightkill1.atlas",TextureAtlas.class);
        manager.load("ghost2_kill.atlas",TextureAtlas.class);
        manager.load("ghost3_kill.atlas",TextureAtlas.class);

        manager.load("mainghost1.atlas",TextureAtlas.class);
        manager.load("mainghost1_kill.atlas",TextureAtlas.class);
        manager.load("ghostmain2.atlas",TextureAtlas.class);
        manager.load("ghostMain.atlas",TextureAtlas.class);
        manager.load("ghostmain2.atlas",TextureAtlas.class);
        manager.load("ghostmain2_kill.atlas",TextureAtlas.class);
        manager.load("ghostKilledMain.atlas",TextureAtlas.class);
        manager.load("ghostmain2_kill.atlas",TextureAtlas.class);
        manager.finishLoading();
    }
    @Override
    public void create () {
        setScreen(new SplashScreen(GdxSplashScreenGame.this));

        final long splash_start_time = System.currentTimeMillis();
        new Thread(new Runnable() {
            @Override
            public void run() {

                Gdx.app.postRunnable(new Runnable() {
                    @Override
                    public void run() {
                        // ... carga de datos
                        // ... carga de fuentes tipograficas
                        // ... carga de sonidos
                        // ... carga de imagenes
                        // ... carga de recursos de internacionalizacion
                        // ... otros

                        GestureLibrary.getInstance().LoadLibrary();
                        load ();
                      //  Assets.getInstance().load();
                       // Assets.getInstance().manager.finishLoading();


                        // Se muestra el menu principal tras la SpashScreen
                        long splash_elapsed_time = System.currentTimeMillis() - splash_start_time;
                        if (splash_elapsed_time < GdxSplashScreenGame.SPLASH_MINIMUM_MILLIS) {
                            Timer.schedule(
                                    new Timer.Task() {
                                        @Override
                                        public void run() {
                                            GdxSplashScreenGame.this.setScreen(new SplashScreen((Game)GdxSplashScreenGame.this));
                                        }
                                    }, (float)(GdxSplashScreenGame.SPLASH_MINIMUM_MILLIS - splash_elapsed_time) / 1000f);
                        } else {
                            GdxSplashScreenGame.this.setScreen(new SplashScreen((Game)GdxSplashScreenGame.this));
                        }
                    }
                });
            }
        }).start();
    }




    @Override
    public void dispose() {
        // DISPOSE ALL RESOURCES
        getScreen().dispose();
        //Assets.getInstance().dispose();
        Gdx.app.exit();
    }
}
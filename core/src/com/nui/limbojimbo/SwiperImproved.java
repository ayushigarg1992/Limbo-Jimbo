package com.nui.limbojimbo;

/**
 * Created by Maddy on 17/02/17.
 */



import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
//import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

public class SwiperImproved  implements Screen {

   /* public static void main(String[] args) {
        new LwjglApplication(new SwiperImproved(), "Game", 256, 256, true);
    }*/

    OrthographicCamera cam;
    private PointCloudLibrary _library;
    Stage stage;
    SpriteBatch batch;
    int timeAux = 0;
    private float time = 0;
    private float spawntime = 3;

    SwipeHandler swipe;
    private boolean isright = true;
    private Texture backGround;

    Texture tex;
    ShapeRenderer shapes;
    private String gesture = "";

    SwipeTriStrip tris;
    private List<Ghosts> ghosts = new ArrayList<Ghosts>();
    private Wizard wiz;
    TextureAtlas atlasLeft;
    TextureAtlas atlasRight;
    Music music;
    Music hunt;
    Dialog  endDialog;

    Skin skin;
    private Game game;
    Multimap<String, Ghosts> GhostMap = ArrayListMultimap.create();

  //  @Override
    public  SwiperImproved(Game game) {
        this.game = game;
        //the triangle strip renderer
       // GestureLibrary.getInstance().LoadLibrary(); //remove later
        _library = GestureLibrary.getInstance().getLibrary();
        tris = new SwipeTriStrip();

        //a swipe handler with max # of input points to be kept alive
        swipe = new SwipeHandler(15,this);

        //minimum distance between two points
        swipe.minDistance = 10;

        //minimum distance between first and second point
        swipe.initialDistance = 10;

        //we will use a texture for the smooth edge, and also for stroke effects
        tex = new Texture("data/gradient.png");
        tex.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        backGround = new Texture(Gdx.files.internal("libsmall.jpg"));
        music = Gdx.audio.newMusic(Gdx.files.internal("data/bgsound.m4a"));
        hunt = Gdx.audio.newMusic(Gdx.files.internal("data/kill.wav"));
        //skin = new Skin(Gdx.files.internal("uiskin.json"));
       // music.setVolume(3f);                 // sets the volume to half the maximum volume
        music.setLooping(true);                // will repeat playback until music.stop() is called
        //music.stop();                          // stops the playback
       // music.pause();                         // pauses the playback



        shapes = new ShapeRenderer();
        batch = new SpriteBatch();

        cam = new OrthographicCamera();
        cam.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        ScreenViewport viewport = new ScreenViewport();
        stage = new Stage(viewport);

        wiz = new Wizard(new Texture(Gdx.files.internal("wizard5.png")));
        atlasLeft =new TextureAtlas(Gdx.files.internal("ghoulsLeft.atlas"));
        atlasRight =new TextureAtlas(Gdx.files.internal("ghoulsRight.atlas"));
       // ghosts.add(new Ghosts(new Texture(Gdx.files.internal("ghoulsRight.png")),atlasLeft,-Gdx.graphics.getWidth()/2,0));
       // ghosts.add(new Ghosts(new Texture(Gdx.files.internal("ghoulsRight.png")),atlasRight,Gdx.graphics.getWidth(),0));
       // GhostMap.put("|", ghosts.get(0));
       // GhostMap.put("_", ghosts.get(1));
        SpwanEnemy();
        SpwanEnemy();
        stage.addActor(wiz);
       // stage.addActor(ghosts.get(0));
        //stage.addActor(ghosts.get(1));

        //handle swipe input
        Gdx.input.setInputProcessor(swipe);
    }


    public void SpwanEnemy(){


        Random rn = new Random();
        int isleft = rn.nextInt(2);
        if (isleft ==0)
            ghosts.add(isleft,new Ghosts(new Texture(Gdx.files.internal("ghoulsRight.png")),atlasLeft,-Gdx.graphics.getWidth()/2,0));
        else
            ghosts.add(isleft,new Ghosts(new Texture(Gdx.files.internal("ghoulsRight.png")),atlasRight,Gdx.graphics.getWidth(),0));
        int i  = rn.nextInt(5);switch( i ){
            case 0 :
                GhostMap.put("_", ghosts.get(isleft));
                break;
            case 1 :
                GhostMap.put("|", ghosts.get(isleft));
                break;
            case 2:
                GhostMap.put("|", ghosts.get(isleft));
                break;
            case 3 :
                GhostMap.put("O", ghosts.get(isleft));
                break;
            case 4 :
                GhostMap.put("_", ghosts.get(isleft));
                break;
            default:
                GhostMap.put("_", ghosts.get(isleft));

        }

        stage.addActor(ghosts.get(isleft));
    }

    public void update()
    {
        time += Gdx.graphics.getDeltaTime();
        if(time > spawntime)
        {
           // x_pattern = MathUtils.random(Stratofall.WIDTH - (cols * balloonWidth));
           // y_pattern = 0; //this will change depending on how many rows of the pattern contain a coin

            SpwanEnemy();
            time = 0;
        }
    }

    public void recognise(final ArrayList<PointCloudPoint> _curGesture){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    PointCloud c = new PointCloud("input gesture", _curGesture);
                    ArrayList<PointCloudPoint> pts = c.getPoints();

                    PointCloudMatchResult r = _library.originalRecognize(c);
                    if (r.getScore() > 0.5) {

                        //System.out.println("matched"+r.getName());
                        //obj.setgesture(r.getName());

                        if (GhostMap.containsKey(r.getName())){
                                hunt.play();
                                Collection<Ghosts> ghostsCollection = GhostMap.get(r.getName());
                                for(Ghosts value : ghostsCollection){
                                    value.remove();
                                }
                                 GhostMap.removeAll(r.getName());


                                //ghosts.remove(i);

                               /* Ghosts G1  = new Ghosts(new Texture(Gdx.files.internal("ghoulsRight.png")),atlasLeft,-Gdx.graphics.getWidth()/2,0,"|");
                                ghosts.add(G1);
                                stage.addActor(G1);
                                Ghosts G2 = new Ghosts(new Texture(Gdx.files.internal("ghoulsRight.png")),atlasRight,Gdx.graphics.getWidth(),0, "_");
                                ghosts.add(G2);
                                stage.addActor(G2);*/

                        }
                    }
            }catch(IllegalArgumentException e){
                    //do nothing
            }

            }
        }).start();
        wiz.setTouch(false);
    }

    @Override
    public void resize(int width, int height) {
        //cam.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    public void setgesture(String s){
        this.gesture = s;
    }



    @Override
    public void render(float delta ) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glClearColor(1,1,1, 1);
        cam.update();
        batch.setProjectionMatrix(cam.combined);
        batch.begin();
        batch.draw(backGround,0,0,Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();
        stage.act(Gdx.graphics.getDeltaTime());

        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        tex.bind();


        //the endcap scale
//		tris.endcap = 5f;



        //the thickness of the line
        tris.thickness = 10f;

        //generate the triangle strip from our path
        tris.update(swipe.path());

        //the vertex color for tinting, i.e. for opacity
        tris.color = Color.GREEN;

        //render the triangles to the screen
        tris.draw(cam);
        update();
        act();
        stage.draw();

        //uncomment to see debug lines
        //drawDebug();
    }

    //optional debug drawing..
    void drawDebug() {
        Array<Vector2> input = swipe.input();

        //draw the raw input
        shapes.begin(ShapeType.Line);
        shapes.setColor(Color.GRAY);
        for (int i=0; i<input.size-1; i++) {
            Vector2 p = input.get(i);
            Vector2 p2 = input.get(i+1);
            shapes.line(p.x, p.y, p2.x, p2.y);
        }
        shapes.end();

        //draw the smoothed and simplified path
        shapes.begin(ShapeType.Line);
        shapes.setColor(Color.RED);
        Array<Vector2> out = swipe.path();
        for (int i=0; i<out.size-1; i++) {
            Vector2 p = out.get(i);
            Vector2 p2 = out.get(i+1);
            shapes.line(p.x, p.y, p2.x, p2.y);
        }
        shapes.end();


        //render our perpendiculars
        shapes.begin(ShapeType.Line);
        Vector2 perp = new Vector2();

        for (int i=1; i<input.size-1; i++) {
            Vector2 p = input.get(i);
            Vector2 p2 = input.get(i+1);

            shapes.setColor(Color.LIGHT_GRAY);
            perp.set(p).sub(p2).nor();
            perp.set(perp.y, -perp.x);
            perp.scl(10f);
            shapes.line(p.x, p.y, p.x+perp.x, p.y+perp.y);
            perp.scl(-1f);
            shapes.setColor(Color.BLUE);
            shapes.line(p.x, p.y, p.x+perp.x, p.y+perp.y);
        }
        shapes.end();
    }


    private void act(){
        stage.act(Gdx.graphics.getDeltaTime());
        for(int i=0;i<ghosts.size();i++)
        {
            wiz.setBounds(wiz.getX(),wiz.getY(),wiz.getWidth()-200,wiz.getHeight()-200);
            ghosts.get(i).setBounds(ghosts.get(i).getX(),ghosts.get(i).getY(),ghosts.get(i).getWidth(),ghosts.get(i).getHeight());
            if(wiz.getBounds().overlaps(ghosts.get(i).bounds)){
                game.setScreen(new GameOverScreen(game));
                dispose();
                //game.
              //  game.setScreen(new startScreen(game));
               // Gdx.app.exit();
               /* endDialog = new Dialog("Game Over", skin)
                {
                    protected void result(Object object)
                    {
                        System.out.println("Option: " + object);
                        Timer.schedule(new Timer.Task()
                        {

                            @Override
                            public void run()
                            {
                                endDialog.show(stage);
                            }
                        }, 1);
                    };
                };*/
               // System.out.println("Collision Bitches");
                break;
                //ghosts.get(i).setVisible(false);
            }


        }


        //
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }


    public void touchdown(){
        wiz.setTouch(true);
    }


    @Override
    public void  hide(){

    }


    @Override
    public void  show(){
        music.play();

    }
    @Override
    public void dispose() {
        batch.dispose();
        shapes.dispose();
        tex.dispose();
        stage.dispose();
        backGround.dispose();
        music.dispose();
    }

}

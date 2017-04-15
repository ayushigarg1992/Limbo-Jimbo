package com.nui.limbojimbo;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.sun.org.apache.xpath.internal.functions.Function;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import static com.badlogic.gdx.Gdx.graphics;

/**
 * Created by Utsa on 3/23/2017.
 */



public class SwiperImprovedDemo implements Screen{
    private Texture myTexture2;
    private TextureRegion myTextureRegion2;
    private TextureRegionDrawable myTexRegionDrawable2;
    private ImageButton button2;
    private Texture myTexture3;
    private TextureRegion myTextureRegion3;
    private TextureRegionDrawable myTexRegionDrawable3;
    private ImageButton button3;
    private Stage stage2;
    private Stage stage3;
    private Stage stage4;
    private Stage stage5;
    private Stage stage6;
    private Stage stage7;
    private Texture myTexture4;
    private TextureRegion myTextureRegion4;
    private TextureRegionDrawable myTexRegionDrawable4;
    private ImageButton button4;
    private Texture myTexture5;
    private TextureRegion myTextureRegion5;
    private TextureRegionDrawable myTexRegionDrawable5;
    private ImageButton button5;
    private Texture myTexture6;
    private TextureRegion myTextureRegion6;
    private TextureRegionDrawable myTexRegionDrawable6;
    private ImageButton button6;
    private Texture myTexture7;
    private TextureRegion myTextureRegion7;
    private TextureRegionDrawable myTexRegionDrawable7;
    private ImageButton button7;

    OrthographicCamera cam;
    private boolean isPause;
    private PointCloudLibrary _library;
    Stage stage;
    SpriteBatch batch;
    SpriteBatch batch1;
    int timeAux = 0;
    private float time = 0;
    private float spawntime = 6;
    private ImageButton button;
    SwipeHandlerDemo swipe;
    private boolean isright = true;
    private Texture backGround;
    private InputMultiplexer multiplexer;
    static int lifeline=4;
    private Texture limage;
    private BitmapFont yourBitmapFontName;
    String ll;
    Texture tex;
    ShapeRenderer shapes;
    private String gesture = "";

    SwipeTriStrip tris;
    private List<GhostsDemo> ghosts = new ArrayList<GhostsDemo>();
    private Wizard wiz;
    TextureAtlas atlasLeft;
    TextureAtlas atlasRight;
    Music music;
    Music hunt;
    Dialog endDialog;
    BitmapFont font;
    Skin skin;
    private Game game;
    Multimap<String, GhostsDemo> GhostMap = ArrayListMultimap.create();
    private Queue<GhostsDemo> GhostsQ = new LinkedList<GhostsDemo>();
    String l;
    //  @Override
    public  SwiperImprovedDemo(Game game) {
        this.game = game;
        this.lifeline=3;
        this.l=String.valueOf(lifeline);
        font=new BitmapFont();
        //the triangle strip renderer
        // GestureLibrary.getInstance().LoadLibrary(); //remove later
        _library = GestureLibrary.getInstance().getLibrary();
        tris = new SwipeTriStrip();
        //a swipe handler with max # of input points to be kept alive
        swipe = new SwipeHandlerDemo(15,this);

        //minimum distance between two points
        swipe.minDistance = 10;

        //minimum distance between first and second point
        swipe.initialDistance = 10;

        //we will use a texture for the smooth edge, and also for stroke effects
        tex = new Texture("data/gradient.png");
        tex.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
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
        cam.setToOrtho(false, graphics.getWidth(), graphics.getHeight());

        ScreenViewport viewport = new ScreenViewport();
        stage = new Stage(viewport);
        wiz = new Wizard(new Texture(Gdx.files.internal("wizard5.png")));
        atlasLeft =new TextureAtlas(Gdx.files.internal("ghoulsLeft.atlas"));
        atlasRight =new TextureAtlas(Gdx.files.internal("ghoulsRight.atlas"));
        isPause=false;

        SpwanEnemy();
        stage.addActor(wiz);
        myTexture2 = new Texture(Gdx.files.internal("pause_filled.png"));
        myTextureRegion2 = new TextureRegion(myTexture2);
        myTexRegionDrawable2 = new TextureRegionDrawable(myTextureRegion2);
        button2 = new ImageButton(myTexRegionDrawable2); //Set the button up
        stage2=new Stage(new ScreenViewport());
        multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(stage);
        multiplexer.addProcessor(stage2);
        multiplexer.addProcessor(swipe);
        stage2.addActor(button2);
        button2.setPosition(graphics.getWidth()-100, graphics.getHeight()-100);
        Gdx.input.setInputProcessor(stage2);
        button2.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(!isPause) {
                    Gdx.app.getApplicationListener().pause();}
                else{
                    Gdx.app.getApplicationListener().resume();
                }

            }
        });

        myTexture3 = new Texture(Gdx.files.internal("heart.png"));
        myTextureRegion3 = new TextureRegion(myTexture3);
        myTexRegionDrawable3 = new TextureRegionDrawable(myTextureRegion3);
        button3 = new ImageButton(myTexRegionDrawable3); //Set the button up
        button3.setPosition(0,1350);
        stage3=new Stage(new ScreenViewport());
        stage3.addActor(button3);



        myTexture4 = new Texture(Gdx.files.internal("heart.png"));
        myTextureRegion4 = new TextureRegion(myTexture4);
        myTexRegionDrawable4 = new TextureRegionDrawable(myTextureRegion4);
        button4 = new ImageButton(myTexRegionDrawable4); //Set the button up
        button4.setPosition(button3.getWidth()+2,1350);
        stage4=new Stage(new ScreenViewport());
        stage4.addActor(button4);

        myTexture7 = new Texture(Gdx.files.internal("heart.png"));
        myTextureRegion7 = new TextureRegion(myTexture7);
        myTexRegionDrawable7 = new TextureRegionDrawable(myTextureRegion7);
        button7 = new ImageButton(myTexRegionDrawable7); //Set the button up
        button7.setPosition(button3.getWidth()+button4.getWidth()+4,1350);
        stage7=new Stage(new ScreenViewport());
        stage7.addActor(button7);

        myTexture5 = new Texture(Gdx.files.internal("heartempty.png"));
        myTextureRegion5 = new TextureRegion(myTexture5);
        myTexRegionDrawable5 = new TextureRegionDrawable(myTextureRegion5);
        button5 = new ImageButton(myTexRegionDrawable5); //Set the button up
        button5.setPosition(button3.getWidth()+button4.getWidth()+4,1350);
        stage5=new Stage(new ScreenViewport());
        stage5.addActor(button5);

        myTexture6 = new Texture(Gdx.files.internal("heartempty.png"));
        myTextureRegion6 = new TextureRegion(myTexture6);
        myTexRegionDrawable6 = new TextureRegionDrawable(myTextureRegion6);
        button6 = new ImageButton(myTexRegionDrawable6);
        button6.setPosition(button3.getWidth()+2,1350);
        stage6=new Stage(new ScreenViewport());
        stage6.addActor(button6);


        //handle swipe input
       Gdx.input.setInputProcessor(swipe);
    }

    public int atlastenemy(String img){
        Random rn = new Random();
        int isleft = 0;//rn.nextInt(2);
        if (ghosts.size()> 1) {
            isleft = rn.nextInt(2);
            if (isleft == 0)
                ghosts.add(isleft, new GhostsDemo(isleft,new Texture(Gdx.files.internal(img)), atlasLeft, -200, 0));
            else
                ghosts.add(isleft, new GhostsDemo(isleft,new Texture(Gdx.files.internal(img)), atlasRight, graphics.getWidth()+20, 0));
        } else {
            isleft = ghosts.size();
            if (isleft == 0)
                ghosts.add(isleft, new GhostsDemo(isleft,new Texture(Gdx.files.internal(img)), atlasLeft, -200, 0));
            else
                ghosts.add(isleft, new GhostsDemo(isleft,new Texture(Gdx.files.internal(img)), atlasRight, graphics.getWidth()+20, 0));
        }
        return isleft;
    }

    public void SpwanEnemy(){


        Random rn = new Random();
        int isleft = 0;//rn.nextInt(2);
        int i  = rn.nextInt(5);

        switch( i ){

            case 0 :
                isleft = atlastenemy("HLine.png");
                //ghosts.get(isleft).setGesture(new Texture(Gdx.files.internal("HLine.png")));
               GhostMap.put("_", ghosts.get(isleft));
                GhostsQ.add(ghosts.get(isleft));
                System.out.println(" ghost is _ ");

                break;
            case 1 :
                isleft = atlastenemy("VLine.png");
                //ghosts.get(isleft).setGesture(new Texture(Gdx.files.internal("VLine.png")));
              GhostMap.put("|", ghosts.get(isleft));
                GhostsQ.add(ghosts.get(isleft));
                System.out.println(" ghost is | ");

                break;
            case 2:
                isleft = atlastenemy("HLine.png");
                //ghosts.get(isleft).setGesture(new Texture(Gdx.files.internal("VLine.png")));
              GhostMap.put("_", ghosts.get(isleft));
                GhostsQ.add(ghosts.get(isleft));
                System.out.println(" ghost is _` ");

                break;
            case 3 :
                isleft = atlastenemy("Circle.png");
                //ghosts.get(isleft).setGesture(new Texture(Gdx.files.internal("Circle.png")));
               GhostMap.put("O", ghosts.get(isleft));
                GhostsQ.add(ghosts.get(isleft));
                System.out.println(" ghost is O ");
                break;
            case 4 :
                isleft = atlastenemy("VLine.png");
                // ghosts.get(isleft).setGesture(new Texture(Gdx.files.internal("HLine.png")));
              GhostMap.put("|", ghosts.get(isleft));
               GhostsQ.add(ghosts.get(isleft));
                System.out.println(" ghost is | ");

                break;
            default:
                isleft = atlastenemy("HLine.png");
                // ghosts.get(isleft).setGesture(new Texture(Gdx.files.internal("HLine.png")));
              GhostMap.put("_", ghosts.get(isleft));
               GhostsQ.add(ghosts.get(isleft));
                System.out.println(" ghost is _ ");
        }

        stage.addActor(ghosts.get(isleft));
    }

    public void update()
    {
        time += graphics.getDeltaTime();
        if(time > spawntime)
        {

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

//                    PointCloudMatchResult r = _library.originalRecognize(c);
//                    System.out.println("matched "+r.getName() + " score "+r.getScore());
//                    if (r.getScore() > 0.5) {
//
//                        if (GhostMap.containsKey(r.getName())){
//
//                            hunt.play();
//                           Collection<GhostsDemo> ghostsCollection = GhostMap.get(r.getName());
//                            for(GhostsDemo value : ghostsCollection){
//                                value.remove();
//                            }
//
//                            GhostMap.removeAll(r.getName());
                    GhostsQ.peek().remove();
                    GhostsQ.poll();
//
//                        }
//                    }
                }catch(IllegalArgumentException e){
                    //do nothing
                }

            }
        }).start();
        wiz.setTouch(false);
    }

    @Override
    public void resize(int width, int height) {
    }

    public void setgesture(String s){
        this.gesture = s;
    }


    @Override
    public void render(float delta ) {

        Gdx.gl.glClearColor(1,1,1,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        cam.update();
        batch.setProjectionMatrix(cam.combined);
        batch.begin();

        batch.draw(backGround,0,0, graphics.getWidth(), graphics.getHeight());
//        font.setColor(Color.GREEN);
//        font.getData().setScale(6);
//        font.draw(batch,l,500,500);
       // lifel(l);
      //  batch.draw(limage,100,100,graphics.getWidth(),graphics.getHeight());
        batch.enableBlending();
        batch.end();
        if(!isPause) {

            stage.act(graphics.getDeltaTime());
            stage2.act(graphics.getDeltaTime());

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
            //  if(isPause==false) {
            update();
            //}
            act();
        }
            if(l.equals("3")){
                stage3.draw();
                stage4.draw();
                stage7.draw();}
            else if(l.equals("2")){
                stage3.draw();
                stage4.draw();
                stage5.draw();}
            else if(l.equals("1")){
                stage3.draw();
                stage6.draw();
                stage5.draw();}
            stage.draw();
            stage2.draw();

        //uncomment to see debug lines
        //drawDebug();
    }




    //optional debug drawing..
    void drawDebug() {
        Array<Vector2> input = swipe.input();

        //draw the raw input
        shapes.begin(ShapeRenderer.ShapeType.Line);
        shapes.setColor(Color.GRAY);
        for (int i=0; i<input.size-1; i++) {
            Vector2 p = input.get(i);
            Vector2 p2 = input.get(i+1);
            shapes.line(p.x, p.y, p2.x, p2.y);
        }
        shapes.end();

        //draw the smoothed and simplified path
        shapes.begin(ShapeRenderer.ShapeType.Line);
        shapes.setColor(Color.RED);
        Array<Vector2> out = swipe.path();
        for (int i=0; i<out.size-1; i++) {
            Vector2 p = out.get(i);
            Vector2 p2 = out.get(i+1);
            shapes.line(p.x, p.y, p2.x, p2.y);
        }
        shapes.end();


        //render our perpendiculars
        shapes.begin(ShapeRenderer.ShapeType.Line);
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
        stage.act(graphics.getDeltaTime());
        if (GhostsQ.size()<=0)
            return;
       // for(int i=0;i<ghosts.size();i++)
        {
            wiz.setBounds(wiz.getX(),wiz.getY(),wiz.getWidth()-200,wiz.getHeight()-200);
            GhostsQ.peek().setBounds(GhostsQ.peek().getX(),GhostsQ.peek().getY(),GhostsQ.peek().getWidth(),GhostsQ.peek().getHeight());
            if(wiz.getBounds().overlaps(GhostsQ.peek().bounds)){
                if(GhostsQ.size()>0 && lifeline>1){
                    lifeline=lifeline-1;
                    l=String.valueOf(lifeline);
                                if(GhostsQ.size()>0) {
                                    GhostsQ.peek().remove();
                                    GhostsQ.poll();
                                }
                }
                else if(lifeline==1){
                    game.setScreen(new GameOverScreen(game));
                    dispose();
                   // break;
                }
                //ghosts.get(i).setVisible(false);
            }


        }



    }

    @Override
    public void pause() {
       isPause=true;
    }

    @Override
    public void resume() {
        isPause=false;
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
        Gdx.input.setInputProcessor(multiplexer);
    }
    @Override
    public void dispose() {
        batch.dispose();
        shapes.dispose();
        tex.dispose();
        stage.dispose();
        backGround.dispose();
        music.dispose();
        font.dispose();
    }

}

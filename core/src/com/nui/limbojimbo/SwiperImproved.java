package com.nui.limbojimbo;

/**
 * Created by Maddy on 17/02/17.
 */



import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
//import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.InputMultiplexer;
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
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
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
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import static com.badlogic.gdx.Gdx.graphics;

public class SwiperImproved  implements Screen {

   /* public static void main(String[] args) {
        new LwjglApplication(new SwiperImproved(), "Game", 256, 256, true);
    }*/

    OrthographicCamera cam;
    private boolean isPause;
    private PointCloudLibrary _library;
    Stage stage;
    SpriteBatch batch;
    int timeAux = 0;
    private float curenttime = 0 ;
    private float time = 0;
    private float spawntime = 6;
    private  ImageButton button;
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
    private List<TextureAtlas> leftghosts = new ArrayList<TextureAtlas>();
    private List<TextureAtlas> rightghosts = new ArrayList<TextureAtlas>();
    private List<TextureAtlas> leftghostskill = new ArrayList<TextureAtlas>();
    private List<TextureAtlas> rightghostskill = new ArrayList<TextureAtlas>();
    private List<Texture> gestureText =  new ArrayList<Texture>();
    TextureAtlas atlasRight;
    Music music;
    Music hunt;
    boolean isinit = false;
    private boolean isdemo = true;
    Dialog  endDialog;
    BitmapFont font;
    Skin skin;
    private Game game;
    Multimap<String, Ghosts> GhostMap = ArrayListMultimap.create();
    private Texture myTexture2;
    private TextureRegion myTextureRegion2;
    private TextureRegionDrawable myTexRegionDrawable2;
    private Texture myTexture3;
    private TextureRegion myTextureRegion3;
    private TextureRegionDrawable myTexRegionDrawable3;
    private ImageButton button2;
    private ImageButton button3;
    private InputMultiplexer multiplexer;
    private Stage stage2;
    private Stage stage3;
    static int lifeline=3;
    private int score;
    String s;
    String l;
  //  @Override
    public  SwiperImproved(Game game) {
        this.game = game;
        this.lifeline=3;
        this.score=0;
        this.s=String.valueOf(score);
        this.l=String.valueOf(lifeline);
        //the triangle strip renderer
       // GestureLibrary.getInstance().LoadLibrary(); //remove later

        tris = new SwipeTriStrip();

        //a swipe handler with max # of input points to be kept alive
        swipe = new SwipeHandler(15,this);

        //minimum distance between two points
        swipe.minDistance = 10;
        tris.thickness = 10f;

        //minimum distance between first and second point
        swipe.initialDistance = 10;

        //we will use a texture for the smooth edge, and also for stroke effects
        tex = new Texture("data/gradient.png");
        tex.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        backGround = new Texture(Gdx.files.internal("bg1.jpg"));
        music = Gdx.audio.newMusic(Gdx.files.internal("data/bgsound.m4a"));

        //skin = new Skin(Gdx.files.internal("uiskin.json"));
       // music.setVolume(3f);                 // sets the volume to half the maximum volume
        music.setLooping(true);              // will repeat playback until music.stop() is called
        //music.stop();                          // stops the playback
       // music.pause();                         // pauses the playback


        font=new BitmapFont();
        shapes = new ShapeRenderer();
        batch = new SpriteBatch();

        cam = new OrthographicCamera();
        cam.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        ScreenViewport viewport = new ScreenViewport();
        stage = new Stage(viewport);

        wiz = new Wizard(new Texture(Gdx.files.internal("wizard5.png")));
        atlasLeft =new TextureAtlas(Gdx.files.internal("ghoulsLeft.atlas"));
        atlasRight =new TextureAtlas(Gdx.files.internal("ghoulsRight.atlas"));
        isPause=false;
       // ghosts.add(new Ghosts(new Texture(Gdx.files.internal("ghoulsRight.png")),atlasLeft,-Gdx.graphics.getWidth()/2,0));
       // ghosts.add(new Ghosts(new Texture(Gdx.files.internal("ghoulsRight.png")),atlasRight,Gdx.graphics.getWidth(),0));
       // GhostMap.put("|", ghosts.get(0));
       // GhostMap.put("_", ghosts.get(1));
       // SpwanEnemy();
        stage.addActor(wiz);
        myTexture2 = new Texture(Gdx.files.internal("pause_filled.png"));
        myTextureRegion2 = new TextureRegion(myTexture2);
        myTexRegionDrawable2 = new TextureRegionDrawable(myTextureRegion2);


        myTexture3 = new Texture(Gdx.files.internal("play_filled.png"));
        myTextureRegion3 = new TextureRegion(myTexture3);
        myTexRegionDrawable3 = new TextureRegionDrawable(myTextureRegion3);
        button2 = new ImageButton(myTexRegionDrawable2,myTexRegionDrawable3); //Set the button up
        stage2=new Stage(new ScreenViewport());
//        button3=new ImageButton(myTexRegionDrawable3);
//        stage3=new Stage(new ScreenViewport());
        multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(stage);
        multiplexer.addProcessor(stage2);
        multiplexer.addProcessor(swipe);
     //   multiplexer.addProcessor(stage3);
        stage2.addActor(button2);
     //   stage3.addActor(button3);
        button2.setPosition(graphics.getWidth()-100, graphics.getHeight()-100);
      //  button3.setPosition(graphics.getWidth()-100, graphics.getHeight()-100);
        Gdx.input.setInputProcessor(stage2);
        createactor();
        if (isdemo){
            //  ghosts.add(0, new Ghosts(0, new Texture(Gdx.files.internal("HLine.png")), leftghosts.get(0),leftghostskill.get(0), -200, 400, 60f));
            ghosts.add(0,new Ghosts( new Texture(Gdx.files.internal("HLine.png")), rightghosts.get(0),rightghostskill.get(0),Gdx.graphics.getWidth()/2 +100,400, 60f , true));
            GhostMap.put("_", ghosts.get(0));
            stage.addActor(ghosts.get(0));
        }
       // Gdx.input.setInputProcessor(stage3);
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
       // stage.addActor(ghosts.get(0));
        //stage.addActor(ghosts.get(1));

        //handle swipe input
        Gdx.input.setInputProcessor(swipe);
    }


    public void createactor(){
       // new Thread(new Runnable() {
           // @Override
            //public void run() {
        //        try {

        {
                    gestureText.add(new Texture(Gdx.files.internal("alpha.png")));
                    gestureText.add(new Texture(Gdx.files.internal("ss.png")));
                    gestureText.add(new Texture(Gdx.files.internal("HLine.png")));
                    gestureText.add(new Texture(Gdx.files.internal("Circle.png")));
                    gestureText.add(new Texture(Gdx.files.internal("VLine.png")));

        }


                    atlasLeft =new TextureAtlas(Gdx.files.internal("ghost1_left.atlas"));
                    leftghosts.add(atlasLeft);
                    leftghosts.add( new TextureAtlas(Gdx.files.internal("ghost2.atlas")));
                    leftghosts.add( new TextureAtlas(Gdx.files.internal("ghost3.atlas")));

                    leftghostskill.add( new TextureAtlas(Gdx.files.internal("ghostLeftKill.atlas")));
                    leftghostskill.add( new TextureAtlas(Gdx.files.internal("ghost2_kill.atlas")));
                    leftghostskill.add( new TextureAtlas(Gdx.files.internal("ghost3_kill.atlas")));
                    atlasRight =new TextureAtlas(Gdx.files.internal("ghost1_right.atlas"));
                    rightghosts.add(atlasRight);
                    rightghosts.add( new TextureAtlas(Gdx.files.internal("ghost2.atlas")));
                    rightghosts.add( new TextureAtlas(Gdx.files.internal("ghost3.atlas")));
                    rightghostskill.add( new TextureAtlas(Gdx.files.internal("ghost_rightkill1.atlas")));
                    rightghostskill.add( new TextureAtlas(Gdx.files.internal("ghost2_kill.atlas")));
                    rightghostskill.add( new TextureAtlas(Gdx.files.internal("ghost3_kill.atlas")));
                    _library = GestureLibrary.getInstance().getLibrary();
                    hunt = Gdx.audio.newMusic(Gdx.files.internal("data/kill.wav"));
                    isinit = true;



            //    }catch(IllegalArgumentException e){
                    //do nothing
           //     }

      //      }
       // }).start();
        //wiz.setTouch(false);
    }





    public void pauseToggle(){
        Texture myTexture = new Texture(Gdx.files.internal("pause_filled.png"));
        TextureRegion myTextureRegion = new TextureRegion(myTexture);
        TextureRegionDrawable myTexRegionDrawable = new TextureRegionDrawable(myTextureRegion);
         button = new ImageButton(myTexRegionDrawable); //Set the button up
//        button.setHeight(600);
//        button.setWidth(300);
        button.setPosition(Gdx.graphics.getWidth()-100, Gdx.graphics.getHeight()-100);
        stage.addActor(button);
        button.setZIndex(2000);
        button.addListener(new InputListener(){
            @Override
          public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                pause();
            return true;
        }
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                resume();

            }

        });

        //handle swipe input
        Gdx.input.setInputProcessor(swipe);
    }


    public int atlastenemy(int idx,float speed){
        idx = idx % gestureText.size();
        Random rn = new Random();
        int pos = rn.nextInt(500);
        int isleft = 0;//rn.nextInt(2);
        int i = rn.nextInt(3);
        if (ghosts.size()> 1) {
            isleft = rn.nextInt(2);
            if (isleft == 0) {
                ghosts.add(isleft, new Ghosts(isleft,gestureText.get(idx), leftghosts.get(i),leftghostskill.get(i), -200, pos, speed));

            } else {
                ghosts.add(isleft, new Ghosts(isleft, gestureText.get(idx), rightghosts.get(i),rightghostskill.get(i), Gdx.graphics.getWidth() + 20, pos, speed));
            }
        } else {
            isleft = ghosts.size();
            if (isleft == 0)
                ghosts.add(isleft, new Ghosts(isleft,gestureText.get(idx), leftghosts.get(i),leftghostskill.get(i), -200, pos,speed));
            else
                ghosts.add(isleft, new Ghosts(isleft,gestureText.get(idx), rightghosts.get(i),rightghostskill.get(i), Gdx.graphics.getWidth()+20, pos,speed));
            //isleft= indx;
        }
        return isleft;
    }

    public void SpwanEnemy(float speed){


        Random rn = new Random();
        int isleft = 0;//rn.nextInt(2);
        int i  = rn.nextInt(5);

        switch( i ){

            case 0 :
                isleft = atlastenemy(i,speed);
                //ghosts.get(isleft).setGesture(new Texture(Gdx.files.internal("HLine.png")));

                GhostMap.put("a", ghosts.get(isleft));
                System.out.println(" ghost is a ");

                break;
            case 1 :
                isleft = atlastenemy(i,speed);
                //ghosts.get(isleft).setGesture(new Texture(Gdx.files.internal("VLine.png")));
                GhostMap.put("/", ghosts.get(isleft));
                System.out.println(" ghost is / ");


                break;
            case 2:
                isleft = atlastenemy(i,speed);
                //ghosts.get(isleft).setGesture(new Texture(Gdx.files.internal("VLine.png")));
                ghosts.get(isleft).setGest("_");
                GhostMap.put("_", ghosts.get(isleft));
                System.out.println(" ghost is _ ");

                break;
            case 3 :
                isleft = atlastenemy(i,speed);
                //ghosts.get(isleft).setGesture(new Texture(Gdx.files.internal("Circle.png")));
                ghosts.get(isleft).setGest("O");
                GhostMap.put("O", ghosts.get(isleft));
                System.out.println(" ghost is O ");
                break;
            case 4 :
                isleft = atlastenemy(i,speed);
            // ghosts.get(isleft).setGesture(new Texture(Gdx.files.internal("HLine.png")));
                ghosts.get(isleft).setGest("|");
                GhostMap.put("|", ghosts.get(isleft));
                System.out.println(" ghost is | ");

                break;
            default:
                isleft = atlastenemy(2,speed);
                // ghosts.get(isleft).setGesture(new Texture(Gdx.files.internal("HLine.png")));
                ghosts.get(isleft).setGest("_");
                GhostMap.put("_", ghosts.get(isleft));
                System.out.println(" ghost is _ ");
        }

        stage.addActor(ghosts.get(isleft));
    }

    public void update()
    {
        curenttime +=Gdx.graphics.getDeltaTime();
        time += Gdx.graphics.getDeltaTime();
        if(time > spawntime)
        {
           // x_pattern = MathUtils.random(Stratofall.WIDTH - (cols * balloonWidth));
           // y_pattern = 0; //this will change depending on how many rows of the pattern contain a coin
            float speed = 0;
            if (curenttime < 30){
                speed = 60f;
                System.out.println(" curenttime is <30 "+curenttime);
                SpwanEnemy(speed);
                SpwanEnemy(speed);
            } else if (curenttime > 30 && curenttime < 60 ){
                speed = 40f;
                spawntime = 6;
                SpwanEnemy(speed);
                SpwanEnemy(speed);
                System.out.println(" curenttime is <60 "+curenttime);
            } else {
                speed = 20f;
                spawntime = 3;
                SpwanEnemy(speed);
                SpwanEnemy(speed);
                System.out.println(" curenttime is last "+curenttime);
            }

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
                    System.out.println("matched "+r.getName() + " score "+r.getScore());
                    if (r.getScore() > 0.1) {

                        if (GhostMap.containsKey(r.getName())){

                            hunt.play();
                            Collection<Ghosts> ghostsCollection = GhostMap.get(r.getName());
                            for(Ghosts value : ghostsCollection){
                                value.setDead();
                            }
                            GhostMap.removeAll(r.getName());
                            score=score+10;
                            s=String.valueOf(score);
                        }

                        if (isdemo){
                            isdemo = false;
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
        font.setColor(Color.GREEN);
        font.getData().setScale(6);
        font.draw(batch,s,0,1350);
        batch.end();

        if(!isPause) {
            music.play();
            stage.act(Gdx.graphics.getDeltaTime());
            stage2.act(Gdx.graphics.getDeltaTime());
            Gdx.gl.glEnable(GL20.GL_BLEND);
            Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
            tex.bind();
            //the thickness of the line


            //generate the triangle strip from our path
            if (swipe.isDrawing) {
                tris.update(swipe.path());

                //the vertex color for tinting, i.e. for opacity
                tris.color = Color.GREEN;
                //render the triangles to the screen
                tris.draw(cam);
            }


            if (!isdemo)
                update();
            act();
        } else {
            music.pause();
        }
        stage.draw();
        stage2.draw();

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
        if(ghosts.size()<=0) {return;}
        //int i=0;
        for(int i=0;i<ghosts.size();i++)

        {
            //Ghosts ghoul = ghosts.get(i);
            wiz.setBounds(wiz.getX(),wiz.getY(),wiz.getWidth()-200,wiz.getHeight()-200);
            ghosts.get(i).setBounds(ghosts.get(i).getX(),ghosts.get(i).getY(),ghosts.get(i).getWidth(),ghosts.get(i).getHeight());
            if(wiz.getBounds().overlaps(ghosts.get(i).bounds)){
                String g = ghosts.get(i).getGest();
                System.out.println("lifeline =  "+lifeline+ "g="+g );
                if (lifeline > 1) {


                    ghosts.get(i).remove();
                    GhostMap.remove(g, ghosts.get(i));
                    lifeline = lifeline - 1;
                    l = String.valueOf(lifeline);

                    // System.out.println("The gesture is "+ g);
                    //Ghosts ghoul = ghosts.get(i);

                }

                else if (lifeline == 1) {
                   // lifeline = 3;
                    game.setScreen(new GameOverScreen(game));
                    dispose();
                    // break;
                }

            }
            //ghosts.get(i).setVisible(false);
        }

        }


        //


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
       // music.play();
        Gdx.input.setInputProcessor(multiplexer);
    }
    @Override
    public void dispose() {
       for (Texture t :  gestureText){
           t.dispose();
       }
        for (TextureAtlas t :  leftghosts){
            t.dispose();
        }
        for (TextureAtlas t :  leftghostskill){
            t.dispose();
        }
        for (TextureAtlas t :  rightghosts){
            t.dispose();
        }
        for (TextureAtlas t :  rightghostskill){
            t.dispose();
        }
        atlasLeft.dispose();
        atlasRight.dispose();
        wiz.remove();

//        batch.dispose();
//        shapes.dispose();
        tex.dispose();
        stage.dispose();
        backGround.dispose();
        music.dispose();
        myTexture2.dispose();
        stage2.dispose();

    }

}

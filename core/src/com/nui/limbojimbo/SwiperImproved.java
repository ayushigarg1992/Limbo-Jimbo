package com.nui.limbojimbo;

/**
 * Created by Maddy on 17/02/17.
 */


import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
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
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import static com.badlogic.gdx.Gdx.graphics;

public class SwiperImproved implements Screen {

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
    private float mainghostspawntime=30;
    private  ImageButton button;
    SwipeHandler swipe;
    private boolean isright = true;
    private Texture backGround;

    private final Lock _mutex = new ReentrantLock(true);
    private int gestureSize;

    Texture tex;
    int [] position;
    ShapeRenderer shapes;
    private String gesture = "";

    SwipeTriStrip tris;
    private List<Ghosts> ghosts = new ArrayList<Ghosts>();
    private MainGhosts mainGhost;
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
    BitmapFont font1;
    Skin skin;
    private Game game;
    Multimap<String, Ghosts> GhostMap = Multimaps.synchronizedMultimap(HashMultimap.<String, Ghosts> create());
    private Texture myTexture2;
    private TextureRegion myTextureRegion2;
    private TextureRegionDrawable myTexRegionDrawable2;
    private Texture myTexture3;
    private TextureRegion myTextureRegion3;
    private TextureRegionDrawable myTexRegionDrawable3;
    private ImageButton button2;
    private Texture myTexture4;
    private TextureRegion myTextureRegion4;
    private TextureRegionDrawable myTexRegionDrawable4;
    private ImageButton button4;
    private Stage stage4;
    private Texture myTexture5;
    private TextureRegion myTextureRegion5;
    private TextureRegionDrawable myTexRegionDrawable5;
    private ImageButton button5;
    private Stage stage5;
    private Texture myTexture6;
    private TextureRegion myTextureRegion6;
    private TextureRegionDrawable myTexRegionDrawable6;
    private ImageButton button6;
    private Stage stage6;
    private Texture myTexture7;
    private TextureRegion myTextureRegion7;
    private TextureRegionDrawable myTexRegionDrawable7;
    private ImageButton button7;
    private Stage stage7;
    private Texture myTexture8;
    private TextureRegion myTextureRegion8;
    private TextureRegionDrawable myTexRegionDrawable8;
    private ImageButton button8;
    private Stage stage8;
    private Texture myTexture9;
    private TextureRegion myTextureRegion9;
    private TextureRegionDrawable myTexRegionDrawable9;
    private ImageButton button9;
    private Stage stage9;
    private ImageButton button3;
    private InputMultiplexer multiplexer;
    private boolean multgesture = true;
    private Stage stage2;
    private Stage stage3;
    private Ghosts lastremoved  = null ;
    static int lifeline=3;
    private int score;
    private int level=1;
    String s;
    String l;
    int getsize = 1;
    int posidx = 0 ;
    ReadWriteLock lock = new ReentrantReadWriteLock();
    Lock writeLock = lock.writeLock();

    private Queue<Ghosts> GhostsQ = new LinkedList<Ghosts>();

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
        tris.thickness = 20f;

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

      //  wiz = new Wizard(new Texture(Gdx.files.internal("wizard5.png")));
        atlasLeft =new TextureAtlas(Gdx.files.internal("ghoulsLeft.atlas"));
        atlasRight =new TextureAtlas(Gdx.files.internal("ghoulsRight.atlas"));
        isPause=false;

        myTexture2 = new Texture(Gdx.files.internal("pause_filled.png"));
        myTextureRegion2 = new TextureRegion(myTexture2);
        myTexRegionDrawable2 = new TextureRegionDrawable(myTextureRegion2);


        myTexture3 = new Texture(Gdx.files.internal("play_filled.png"));
        myTextureRegion3 = new TextureRegion(myTexture3);
        myTexRegionDrawable3 = new TextureRegionDrawable(myTextureRegion3);
        button2 = new ImageButton(myTexRegionDrawable2,myTexRegionDrawable2,myTexRegionDrawable3); //Set the button up
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
            font1=new BitmapFont();
            //  ghosts.add(0, new Ghosts(0, new Texture(Gdx.files.internal("HLine.png")), leftghosts.get(0),leftghostskill.get(0), -200, 400, 60f));
            ghosts.add(0,new Ghosts( new Texture(Gdx.files.internal("HLine.png")), rightghosts.get(0),rightghostskill.get(0),Gdx.graphics.getWidth()/2 +100,Gdx.graphics.getHeight()/4, 60f , true));
            //ghosts.add(0,g);
            GhostMap.put("_", ghosts.get(0));
            stage.addActor(ghosts.get(0));
        }

        wiz = new Wizard(new Texture(Gdx.files.internal("witch.png")));

        stage.addActor(wiz);
      //  pauseToggle();
       // Gdx.input.setInputProcessor(stage3);
        button2.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(!isPause) {
                    button2.setChecked(true);
                    Gdx.app.getApplicationListener().pause();}
                else{
                    button2.setChecked(false);
                    Gdx.app.getApplicationListener().resume();
                }

            }
        });

        myTexture8 = new Texture(Gdx.files.internal("arrow.png"));
        myTextureRegion8 = new TextureRegion(myTexture8);
        myTexRegionDrawable8 = new TextureRegionDrawable(myTextureRegion8);
        button8 = new ImageButton(myTexRegionDrawable8);
        //stage.addActor(ghosts.get(1));
        button8.setPosition(Gdx.graphics.getWidth()/2-100,Gdx.graphics.getHeight()/2+60);
        button8.setWidth(300);
        stage8 = new Stage(new ScreenViewport());
        stage8.addActor(button8);
        //handle swipe input
        Gdx.input.setInputProcessor(swipe);

        myTexture9 = new Texture(Gdx.files.internal("heart.png"));
        myTextureRegion9 = new TextureRegion(myTexture9);
        myTexRegionDrawable9 = new TextureRegionDrawable(myTextureRegion9);
        button9 = new ImageButton(myTexRegionDrawable9); //Set the button up
        button9.setPosition(0,graphics.getHeight()-100);
        stage9=new Stage(new ScreenViewport());
        stage9.addActor(button9);



        myTexture4 = new Texture(Gdx.files.internal("heart.png"));
        myTextureRegion4 = new TextureRegion(myTexture4);
        myTexRegionDrawable4 = new TextureRegionDrawable(myTextureRegion4);
        button4 = new ImageButton(myTexRegionDrawable4); //Set the button up
        button4.setPosition(button4.getWidth()+2,graphics.getHeight()-100);
        stage4=new Stage(new ScreenViewport());
        stage4.addActor(button4);

        myTexture7 = new Texture(Gdx.files.internal("heart.png"));
        myTextureRegion7 = new TextureRegion(myTexture7);
        myTexRegionDrawable7 = new TextureRegionDrawable(myTextureRegion7);
        button7 = new ImageButton(myTexRegionDrawable7); //Set the button up
        button7.setPosition(button7.getWidth()+button4.getWidth()+4,graphics.getHeight()-100);
        stage7=new Stage(new ScreenViewport());
        stage7.addActor(button7);

        myTexture5 = new Texture(Gdx.files.internal("heartempty.png"));
        myTextureRegion5 = new TextureRegion(myTexture5);
        myTexRegionDrawable5 = new TextureRegionDrawable(myTextureRegion5);
        button5 = new ImageButton(myTexRegionDrawable5); //Set the button up
        button5.setPosition(button5.getWidth()+button4.getWidth()+4,graphics.getHeight()-100);
        stage5=new Stage(new ScreenViewport());
        stage5.addActor(button5);

        myTexture6 = new Texture(Gdx.files.internal("heartempty.png"));
        myTextureRegion6 = new TextureRegion(myTexture6);
        myTexRegionDrawable6 = new TextureRegionDrawable(myTextureRegion6);
        button6 = new ImageButton(myTexRegionDrawable6);
        button6.setPosition(button6.getWidth()+2,graphics.getHeight()-100);
        stage6=new Stage(new ScreenViewport());
        stage6.addActor(button6);
    }


    public void createactor(){
        // new Thread(new Runnable() {
        // @Override
        //public void run() {
        //        try {

        position = new int[graphics.getHeight()/100];
        int pos = 0;
        for (int i = 0 ; i < graphics.getHeight()/100 ; i ++){
            position[i] = pos;
            pos += 100;
        }


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

    public String getGestureString(int index){
        switch(index){
            case 0 :
                return "a";


            case 1 :
                return "/";


            case 2:
                return "_";


            case 3 :
                return "O";


            case 4 :
                return "|";



            default:
                return "_";
        }
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
                // List<Texture> list = new ArrayList<Texture>();
                //  list.add(gestureText.get(0));
                //list.add(gestureText.get(1));
                ghosts.add(isleft, new Ghosts(isleft,gestureText.get(idx), leftghosts.get(i),leftghostskill.get(i), -200, pos, speed));
                //ghosts.add(isleft, new Ghosts(isleft,list, leftghosts.get(i),leftghostskill.get(i), -200, pos, speed));

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
    public int atlastenemy(List<GestureTexture> gestureList,float speed){

        Random rn = new Random();
        posidx = posidx % position.length;
        int pos =   position[posidx++];
        posidx = position.length - posidx-1;
        int isleft = 0;//rn.nextInt(2);
        int i = rn.nextInt(3);

        if (ghosts.size()> 1) {
            isleft = rn.nextInt(2);
            if (isleft == 0) {
                // List<Texture> list = new ArrayList<Texture>();
                //  list.add(gestureText.get(0));
                //list.add(gestureText.get(1));
                ghosts.add(isleft, new Ghosts(isleft,gestureList, leftghosts.get(i),leftghostskill.get(i), -200, pos, speed));
                //ghosts.add(isleft, new Ghosts(isleft,list, leftghosts.get(i),leftghostskill.get(i), -200, pos, speed));

            } else {
                ghosts.add(isleft, new Ghosts(isleft, gestureList, rightghosts.get(i),rightghostskill.get(i), Gdx.graphics.getWidth() + 20, pos, speed));
            }
        } else {
            isleft = ghosts.size();
            if (isleft == 0)
                ghosts.add(isleft, new Ghosts(isleft,gestureList, leftghosts.get(i),leftghostskill.get(i), -200, pos,speed));
            else
                ghosts.add(isleft, new Ghosts(isleft,gestureList, rightghosts.get(i),rightghostskill.get(i), Gdx.graphics.getWidth()+20, pos,speed));
            //isleft= indx;
        }
        return isleft;
    }

    public void SpwanEnemy(float speed){


        Random rn = new Random();
        int isleft = 0;//rn.nextInt(2);
        int i  = rn.nextInt(5);
        int size = getsize;//set this later by level
        int x=0;
        if (multgesture) {
            List<GestureTexture> gestures = getGestureTextures(rn, size);
            isleft = atlastenemy(gestures, speed);
            GhostMap.put(gestures.get(0).getString(), ghosts.get(isleft));
        } else {
            switch (i) {

                case 0:
                    isleft = atlastenemy(i, speed);
                    ghosts.get(isleft).setGest("a");
                    GhostMap.put("a", ghosts.get(isleft));
                    System.out.println(" ghost is a ");
                    break;
                case 1:
                    isleft = atlastenemy(i, speed);
                    ghosts.get(isleft).setGest("/");
                    GhostMap.put("/", ghosts.get(isleft));
                    System.out.println(" ghost is / ");
                    break;
                case 2:
                    isleft = atlastenemy(i, speed);
                    ghosts.get(isleft).setGest("_");
                    GhostMap.put("_", ghosts.get(isleft));
                    System.out.println(" ghost is _ ");
                    break;
                case 3:
                    isleft = atlastenemy(i, speed);
                    ghosts.get(isleft).setGest("O");
                    GhostMap.put("O", ghosts.get(isleft));
                    System.out.println(" ghost is O ");
                    break;
                case 4:
                    isleft = atlastenemy(i, speed);
                    ghosts.get(isleft).setGest("|");
                    GhostMap.put("|", ghosts.get(isleft));
                    System.out.println(" ghost is | ");
                    break;
                default:
                    isleft = atlastenemy(2, speed);
                    ghosts.get(isleft).setGest("_");
                    GhostMap.put("_", ghosts.get(isleft));
                    System.out.println(" ghost is _ ");
            }
        }

        stage.addActor(ghosts.get(isleft));
    }
    public void SpwanMainEnemy(float speed){
        Random rn = new Random();
        int size = 5;
        List<GestureTexture> gestures = getGestureTextures(rn, size);
        atlastmainenemy(gestures,speed);
        GhostMap.put(gestures.get(0).getString(), mainGhost);
        stage.addActor(mainGhost);
    }
    public void atlastmainenemy(List<GestureTexture> gestureList,float speed){
        int isleft=1;
        mainGhost=new MainGhosts(isleft, gestureList,  new TextureAtlas(Gdx.files.internal("mainghost1.atlas")), new TextureAtlas(Gdx.files.internal("mainghost1_kill.atlas")), Gdx.graphics.getWidth() + 20, Gdx.graphics.getHeight()/2, speed);
    }




    private List<GestureTexture> getGestureTextures(Random rn, int size) {
        List<GestureTexture> gestures = new ArrayList<GestureTexture>();
        int x=0;
        while(size> 0){
            int m = rn.nextInt(5);
            Texture tx = gestureText.get(m);
            String s = getGestureString(m);
            gestures.add(new GestureTexture(tx,s));
            size--;
        }
        return gestures;
    }

    public void update()
    {
        curenttime +=Gdx.graphics.getDeltaTime();
        time += Gdx.graphics.getDeltaTime();

        if(curenttime>mainghostspawntime)
        {
            SpwanMainEnemy(60f);

            mainghostspawntime=Integer.MAX_VALUE;
            spawntime = Integer.MAX_VALUE;

        }
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
                speed = 50f;
                spawntime = 6;
                getsize = 2;
                SpwanEnemy(speed);
                SpwanEnemy(speed);
                System.out.println(" curenttime is <60 "+curenttime);
            } else {
                speed = 40f;

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


                            List<Ghosts> enhancedList = new ArrayList<Ghosts>();
                            Iterator<Ghosts> ghostsCollection = GhostMap.get(r.getName()).iterator();
                           /* try {
                                writeLock.lock();*/
                                while (ghostsCollection.hasNext()) {
                                    Ghosts value = ghostsCollection.next();
                                    if (multgesture) {
                                        if(value instanceof MainGhosts)
                                        {
                                            if (value.gestureSet != null && value.gestureSet.size() > 1) {
                                                value.gestureSet.remove(0);
                                                enhancedList.add(value);
                                                ghostsCollection.remove();
                                            } else {

                                                    Random rn = new Random();
                                                    int size = 5;
                                                    List<GestureTexture> gestures = getGestureTextures(rn, size);
                                                    value.setGestureSet(gestures);
                                                    enhancedList.add(value);
                                                if(((MainGhosts) value).getOcc()>0) {
                                                    ((MainGhosts) value).setSequence();
                                                }


                                            }


                                        }
                                        else {

                                            if (value.gestureSet != null && value.gestureSet.size() > 1) {
                                                value.gestureSet.remove(0);
                                                enhancedList.add(value);
                                                ghostsCollection.remove();

                                            } else {
                                                value.setDead();
                                                ghostsCollection.remove();
                                            }
                                        }
                                    } else
                                        value.setDead();
                                }

                            for (Ghosts value : enhancedList)
                                GhostMap.put(value.gestureSet.get(0).getString(), value);
                            if (!multgesture)
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
        //batch.draw(button2);
        font.setColor(Color.RED);
        font.getData().setScale(6);
        font.draw(batch,"Score: "+ s,Gdx.graphics.getWidth()/2-70,graphics.getHeight()-10);
        if(isdemo){
            font.setColor(Color.YELLOW);
            font.getData().setScale(8);
            font.draw(batch,"Draw the symbol to kill the ghost",30,Gdx.graphics.getHeight()-200);
        }
        batch.end();
        if(isdemo){
            stage8.draw();
        }
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
        if(l.equals("3")){
            stage9.draw();
            stage4.draw();
            stage7.draw();}
        else if(l.equals("2")){
            stage9.draw();
            stage4.draw();
            stage5.draw();}
        else if(l.equals("1")){
            stage9.draw();
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


   /* private void act() {
        stage.act(Gdx.graphics.getDeltaTime());
        if (GhostMap.size() <= 0) {
            return;
        }

        for(Ghosts gh: GhostMap.values()) {
            wiz.setBounds(wiz.getX(), wiz.getY(), wiz.getWidth() - 200, wiz.getHeight() - 200);
            gh.setBounds(gh.getX(), gh.getY(), gh.getWidth(), gh.getHeight());
            if (wiz.getBounds().overlaps(gh.bounds)) {
                if (lifeline > 1) {
                    _mutex.lock();
                    String g = gh.getGest();
                    gh.remove();
                    GhostMap.remove(g, gh);

                    GhostMap.remove(g, lastremoved);
                    lifeline = lifeline - 1;
                    l = String.valueOf(lifeline);
                    _mutex.unlock();

                }

                else if (lifeline == 1) {
                    // lifeline = 3;
                    game.setScreen(new GameOverScreen(game));

                    // break;
                }

            }
        }
        //ghosts.get(i).setVisible(false);

    }*/

    private void act(){
        stage.act(Gdx.graphics.getDeltaTime());
        if(ghosts.size()<=0) {return;}
        //int i=0;
        for(int i=0;i<ghosts.size();i++)

        {
            if (lastremoved != null && ghosts.get(i)==lastremoved)
                continue;
            //Ghosts ghoul = ghosts.get(i);
            wiz.setBounds(wiz.getX(),wiz.getY(),wiz.getWidth()-200,wiz.getHeight()-200);
            ghosts.get(i).setBounds(ghosts.get(i).getX(),ghosts.get(i).getY(),ghosts.get(i).getWidth(),ghosts.get(i).getHeight());
            if(wiz.getBounds().overlaps(ghosts.get(i).bounds)){
                lastremoved = ghosts.get(i);
                String g = ghosts.get(i).getGest();
                System.out.println("lifeline =  "+lifeline+ "g="+g );
                if (lifeline > 1) {


                    ghosts.get(i).remove();
                    GhostMap.remove(g, lastremoved);
                    lifeline = lifeline - 1;
                    l = String.valueOf(lifeline);

                    // System.out.println("The gesture is "+ g);
                    //Ghosts ghoul = ghosts.get(i);

                }

                else if (lifeline == 1) {
                    // lifeline = 3;
                    game.setScreen(new GameOverScreen(game));

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
        dispose();
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
        myTexture3.dispose();
        myTexture4.dispose();
        myTexture5.dispose();
        myTexture6.dispose();
        myTexture7.dispose();
        myTexture8.dispose();
        myTexture9.dispose();
       // stage3.dispose();
        stage4.dispose();
        atlasLeft.dispose();
        atlasRight.dispose();
        wiz.remove();

        batch.dispose();
        shapes.dispose();
        //gestureText.dispose();
        tex.dispose();

        music.dispose();
        myTexture2.dispose();
        stage.dispose();
        backGround.dispose();
        stage2.dispose();
        stage5.dispose();
        stage6.dispose();
        stage7.dispose();
        stage8.dispose();
        stage9.dispose();

    }

}

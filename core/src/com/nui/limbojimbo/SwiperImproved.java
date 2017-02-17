package com.nui.limbojimbo;

/**
 * Created by Maddy on 17/02/17.
 */



import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
//import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
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
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.util.ArrayList;
import java.util.List;

public class SwiperImproved extends Game implements ApplicationListener {

   /* public static void main(String[] args) {
        new LwjglApplication(new SwiperImproved(), "Game", 256, 256, true);
    }*/

    OrthographicCamera cam;
    Stage stage;
    SpriteBatch batch;

    SwipeHandler swipe;
    private Texture backGround;

    Texture tex;
    ShapeRenderer shapes;

    SwipeTriStrip tris;
    private List<Ghosts> ghosts = new ArrayList<Ghosts>();
    private Wizard wiz;

    @Override
    public void create() {
        //the triangle strip renderer
        tris = new SwipeTriStrip();

        //a swipe handler with max # of input points to be kept alive
        swipe = new SwipeHandler(20);

        //minimum distance between two points
        swipe.minDistance = 10;

        //minimum distance between first and second point
        swipe.initialDistance = 10;

        //we will use a texture for the smooth edge, and also for stroke effects
        tex = new Texture("data/gradient.png");
        tex.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        backGround = new Texture(Gdx.files.internal("libsmall.jpg"));


        shapes = new ShapeRenderer();
        batch = new SpriteBatch();

        cam = new OrthographicCamera();
        cam.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        ScreenViewport viewport = new ScreenViewport();
        stage = new Stage(viewport);

        wiz = new Wizard(new Texture(Gdx.files.internal("wizard5.png")));
        TextureAtlas atlasLeft =new TextureAtlas(Gdx.files.internal("ghoulsLeft.atlas"));
        TextureAtlas atlasRight =new TextureAtlas(Gdx.files.internal("ghoulsRight.atlas"));
        ghosts.add(new Ghosts(new Texture(Gdx.files.internal("ghoulsRight.png")),atlasLeft,-Gdx.graphics.getWidth()/2,0));
        ghosts.add(new Ghosts(new Texture(Gdx.files.internal("ghoulsRight.png")),atlasRight,Gdx.graphics.getWidth(),0));
        stage.addActor(wiz);
        stage.addActor(ghosts.get(0));
        stage.addActor(ghosts.get(1));

        //handle swipe input
        Gdx.input.setInputProcessor(swipe);
    }

    @Override
    public void resize(int width, int height) {
        //cam.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }



    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
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
        tris.thickness = 20f;

        //generate the triangle strip from our path
        tris.update(swipe.path());

        //the vertex color for tinting, i.e. for opacity
        tris.color = Color.BLUE;

        //render the triangles to the screen
        tris.draw(cam);
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
            wiz.setBounds(wiz.getX(),wiz.getY(),wiz.getWidth(),wiz.getHeight());
            ghosts.get(i).setBounds(ghosts.get(i).getX(),ghosts.get(i).getY(),ghosts.get(i).getWidth(),ghosts.get(i).getHeight());
            if(wiz.getBounds().overlaps(ghosts.get(i).bounds)){

                break;
                //ghosts.get(i).setVisible(false);
            }

        }
        System.out.println("Collision Bitches");
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        batch.dispose();
        shapes.dispose();
        tex.dispose();
        stage.dispose();
        backGround.dispose();
    }

}

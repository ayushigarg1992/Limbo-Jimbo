package com.nui.limbojimbo;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import com.badlogic.gdx.math.Rectangle;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.util.ArrayList;
import java.util.List;

public class LimboScreen extends Game implements ApplicationListener {
	Stage stage;
	SpriteBatch batch;
	Texture texture;
	Sprite sprite ;
    private List<Ghosts> ghosts = new ArrayList<Ghosts>();
	private Wizard wiz;
	private Texture backGround;


	@Override
	public void create(){
		//sprite = new Sprite(new Texture(Gdx.files.internal("wizard.png")));

		batch = new SpriteBatch();
		ScreenViewport viewport = new ScreenViewport();
		stage = new Stage(viewport);
		Gdx.input.setInputProcessor(stage);
		 backGround = new Texture(Gdx.files.internal("bwlibsmall.jpg"));

		wiz = new Wizard(new Texture(Gdx.files.internal("wizard5.png")));
		TextureAtlas atlasLeft =new TextureAtlas(Gdx.files.internal("ghoulsLeft.atlas"));
		TextureAtlas atlasRight =new TextureAtlas(Gdx.files.internal("ghoulsRight.atlas"));
		ghosts.add(new Ghosts(1,new Texture(Gdx.files.internal("HLine.png")),atlasLeft,-Gdx.graphics.getWidth()/2+wiz.getWidth()/2,0,"_"));
		ghosts.add(new Ghosts(0,new Texture(Gdx.files.internal("VLine.png")),atlasRight,Gdx.graphics.getWidth()-wiz.getWidth()/2,0,"|"));
		stage.addActor(wiz);
		stage.addActor(ghosts.get(0));
		stage.addActor(ghosts.get(1));

	}




	public void resize (int width, int height) {
		// See below for what true means.
		stage.getViewport().update(width, height, true);
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
	public void render(){
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		Gdx.gl.glClearColor(1,1,1, 1);



		batch.begin();
		batch.draw(backGround,0,0,Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		batch.end();
		act();
		stage.draw();


	}

	@Override
	public void dispose(){
		stage.dispose();
		batch.dispose();
		texture.dispose();
	}


}

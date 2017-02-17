package com.nui.limbojimbo;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.util.ArrayList;
import java.util.List;

public class LimboScreen extends Game{
	Stage stage;
	SpriteBatch batch;
	Texture texture;
	Sprite sprite ;
    private List<Ghosts> ghosts = new ArrayList<Ghosts>();
	private Wizard wiz;

	@Override
	public void create(){
		//sprite = new Sprite(new Texture(Gdx.files.internal("wizard.png")));
		ScreenViewport viewport = new ScreenViewport();
		stage = new Stage(viewport);
		Gdx.input.setInputProcessor(stage);
		wiz = new Wizard(new Texture(Gdx.files.internal("wizard5.png")));
		TextureAtlas atlasLeft =new TextureAtlas(Gdx.files.internal("ghoulsLeft.atlas"));
		TextureAtlas atlasRight =new TextureAtlas(Gdx.files.internal("ghoulsRight.atlas"));
		/*Animation animationLeft = new Animation(1/3f,atlasLeft.getRegions());
		Animation animationRight = new Animation(1/3f,atlasRight.getRegions());
		*/
		ghosts.add(new Ghosts(new Texture(Gdx.files.internal("ghoulsRight.png")),atlasLeft,-Gdx.graphics.getWidth()/2,Gdx.graphics.getHeight()/2));
		ghosts.add(new Ghosts(new Texture(Gdx.files.internal("ghoulsRight.png")),atlasRight,Gdx.graphics.getWidth(),Gdx.graphics.getHeight()/2));
		stage.addActor(wiz);
		stage.addActor(ghosts.get(0));
		stage.addActor(ghosts.get(1));

			}
	public void resize (int width, int height) {
		// See below for what true means.
		stage.getViewport().update(width, height, true);
	}
	@Override
	public void render(){
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		Gdx.gl.glClearColor(1,1,1, 1);

		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();
		if(wiz.getBounds().overlaps(ghosts.get(0).bounds)){
			System.out.print("Collision Bitches");
		}
	}

//	@Override
//	public void update(float delta) {
//		if(wiz.getBounds().overlaps(ghosts.get(0).bounds)){
//			System.out.print("Collision Bitches");
//		}
//	}
	@Override
	public void dispose(){
		stage.dispose();
		batch.dispose();
		texture.dispose();
	}
}

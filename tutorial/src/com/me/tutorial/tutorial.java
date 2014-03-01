package com.me.tutorial;

import java.io.IOException;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class tutorial implements ApplicationListener {
	SpriteBatch batch;
	Player player;
	InputProcessor inputProcessor;
	Texture mario;
	Vector2 position;

	
	@Override
	public void create() {		
		batch = new SpriteBatch();
		mario = new Texture(Gdx.files.internal("mario.png"));
		
		if(Gdx.files.local("player.dat").exists()){
			try {
				player = new Player(new Vector2(Gdx.graphics.getWidth() / 2,Gdx.graphics.getHeight() / 2),"mario.png");
				player.setPosition(Player.readPlayer());
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.out.println("Player Exists,Reading File");
		}else{
			player = new Player(new Vector2(Gdx.graphics.getWidth() / 2,Gdx.graphics.getHeight() / 2),"mario.png");
			try {
				Player.savePlayer(player);
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.out.println("Player Does Not Exists,Creating & Saving Player");
		}
		
	}

	@Override
	public void dispose() {
		try {
			player.savePlayer(player);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void render() {		
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		player.update();
		batch.begin();
		batch.draw(player.getCurrentFrame(), player.getPosition().x,player.getPosition().y);
		batch.end();
		
		
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}
}

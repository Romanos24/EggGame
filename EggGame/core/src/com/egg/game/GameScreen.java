package com.egg.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.Iterator;

public class GameScreen implements Screen {

	final drop game;
	OrthographicCamera camera;
	SpriteBatch batch;
	Texture bucketImage;
	Texture eggImage;
	Texture leaveImage;
	Texture background;
	Sound dropSound;
	Music fonMusic;
	Rectangle bucket;
	Vector3 touchPos;
	Array<Rectangle> eggdrops;
	long lastDropTime;
	int eggsGatchered;
	int speed;
	int index;
	String[] colors = {
			"egg.png",
			"egg1.png",
			"egg2.png",
			"egg3.png",
			"egg4.png",
			"egg5.png",
			"egg6.png",
			"egg7.png",
			"egg8.png"
	};
	
	public GameScreen (final drop gam) {
		this.game = gam;

		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);

		batch = new SpriteBatch();

		touchPos = new Vector3();

		speed = 200;

		//eggImage = new Texture("egg.png");

		//eggImage = new Texture(colors[index]);
		eggImage = new Texture(colors[index]);
		leaveImage = new Texture("leave1.png");
		bucketImage = new Texture("bucket.png");
		background = new Texture("background1.png");

		dropSound = Gdx.audio.newSound(Gdx.files.internal("eggdrop.wav"));
		fonMusic = Gdx.audio.newMusic(Gdx.files.internal("fon.mp3"));

		fonMusic.setLooping(true);
		fonMusic.play();

		bucket = new Rectangle();
		bucket.x = 800 / 2 - 64 / 2;
		bucket.y = 20;
		bucket.width = 64;
		bucket.height = 64;

		eggdrops = new Array<Rectangle>();
		spawnEggdrop();
	}

	private void spawnEggdrop(){
		Rectangle eggdrop = new Rectangle();
		eggdrop.x = MathUtils.random(0, 800-64);
		eggdrop.y = 480;
		eggdrop.width = 64;
		eggdrop.height = 64;
		eggdrops.add(eggdrop);

		lastDropTime = TimeUtils.nanoTime();
	}

	@Override
	public void render (float delta) {
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		camera.update();
		game.batch.setProjectionMatrix(camera.combined);

		game.batch.begin();
		game.batch.draw(background, 0, 0);
		game.font.draw(game.batch, "Eggs collected: " + eggsGatchered, 200, 480);
		game.batch.draw(bucketImage, bucket.x, bucket.y);
		for (Rectangle eggdrop: eggdrops){
			game.batch.draw(eggImage, eggdrop.x, eggdrop.y);

		}
		game.batch.end();

		if(Gdx.input.isTouched()){
			touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			camera.unproject(touchPos);
			bucket.x = (int) (touchPos.x - 64/2);
		}

		if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) bucket.x -= 500 * Gdx.graphics.getDeltaTime();
		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) bucket.x += 500 * Gdx.graphics.getDeltaTime();

		if(bucket.x < 0) bucket.x = 0;
		if(bucket.x > 800-64) bucket.x = 800-64;

		if(TimeUtils.nanoTime() - lastDropTime > 1000000000) spawnEggdrop();

		Iterator<Rectangle> iter = eggdrops.iterator();
		while (iter.hasNext()){

			Rectangle eggdrop = iter.next();
			eggdrop.y -= speed * Gdx.graphics.getDeltaTime();
			if(eggdrop.y + 64 < 0) iter.remove();
			if(eggdrop.overlaps(bucket)){
				eggsGatchered++;
				if(eggsGatchered%3 == 0) index++;
				if(index >= 9) index = 0;

				if (eggsGatchered%25 == 0) speed = speed + 50;
				dropSound.play();
				iter.remove();
			};
		}
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

	@Override
	public void hide() {

	}

	@Override
	public void dispose () {
		eggImage.dispose();
		bucketImage.dispose();
		dropSound.dispose();
		fonMusic.dispose();
	}

	@Override
	public void show() {
		fonMusic.play();
	}
}

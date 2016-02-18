package com.theironyard.minicraft;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Minicraft extends ApplicationAdapter {
    final int WIDTH = 100;
    final int HEIGHT = 100;
    final int CHANGE_WIDTH = WIDTH / 2;
    final int CHANGE_HEIGHT = HEIGHT / 2;
    final int MAX_VELOCITY = 2000;


    SpriteBatch batch;
    TextureRegion down, up, right, left;

    float xv, yv;
    float x = 0;
    float y = 0;
    boolean faceRight, faceDown;

    @Override
    public void create () {
        batch = new SpriteBatch();

        Texture tiles = new Texture("tiles.png");
        TextureRegion[][] grid = TextureRegion.split(tiles, 16, 16);
        down = grid[6][0];
        up = grid[6][1];
        right = grid[6][3];
        left = new TextureRegion(right);
        left.flip(true, false);
    }

    @Override
    public void render () {
        move();
        border();

        Gdx.gl.glClearColor(0, 0.5f, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        TextureRegion img;
        if (Math.abs(yv) > Math.abs(xv)) {
            if (yv >= 0) {
                img = up;
            } else {
                img = down;
            }
        } else {
            if (xv >= 0) {
                img = right;
            } else {
                img = left;
            }
        }
        if (xv == 0 && yv == 0){
            img = down;
        }

        batch.begin();
        batch.draw(img, x, y, CHANGE_WIDTH, CHANGE_HEIGHT);
        batch.end();
    }

    float decelerate(float velocity, float deceleration) {
        velocity *= deceleration;
        if (Math.abs(velocity) < 1) {
            velocity = 0;
        }
        return velocity;
    }

    void move() {
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) { // This takes input
            yv = MAX_VELOCITY;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            yv = MAX_VELOCITY * -1;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            xv = MAX_VELOCITY * -1;
            faceRight = false;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            xv = MAX_VELOCITY;
            faceRight = true;
        }
       // yv = decelerate(yv, 0.4f);
       // xv = decelerate(xv, 0.4f);
        yv *= 0.3f;
        xv *= 0.3f;

    }

    void border() {
        float oldX = x; // creating variables to hold x and y's last position
        float oldY = y;

        y += yv * Gdx.graphics.getDeltaTime(); // getting a sliver of Max Velocity   magic
        x += xv * Gdx.graphics.getDeltaTime(); // getting a sliver of Max Velocity

        if (x < 0 || x > (Gdx.graphics.getWidth() - CHANGE_WIDTH)) {
            x = oldX;
        } if (y < 0 || y > (Gdx.graphics.getHeight() - CHANGE_HEIGHT)) {
            y = oldY;
        }
    }
}
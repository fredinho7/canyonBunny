package com.mygdx.game;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.util.CameraHelper;

/**
 * Created by freddeWindows on 2016-11-05.
 */
public class WorldController extends InputAdapter {

    private static final String TAG = WorldController.class.getName();

    public Sprite[] testSprites;
    public int selectedSprite;

    public CameraHelper hepler;


    public WorldController() {
        this.init();
    }

    private void init() {
        Gdx.input.setInputProcessor(this);

        hepler = new CameraHelper();

        initTestObjects();
    }

    private void initTestObjects() {
        testSprites = new Sprite[5];

        int w = 32;
        int h = 32;

        Pixmap pixmap = createProcediralPixmap(w, h);
        Texture texture = new Texture(pixmap);

        for(int i = 0; i < testSprites.length; i++) {
            Sprite spr = new Sprite(texture);
            spr.setSize(1, 1);
            spr.setOrigin(spr.getWidth() / 2.0f, spr.getHeight() / 2.0f);
            float randomX = MathUtils.random(-2.0f, 2.0f);
            float randomY = MathUtils.random(-2.0f, 2.0f);

            spr.setPosition(randomX, randomY);

            testSprites[i] = spr;
        }
        selectedSprite = 0;
    }

    private Pixmap createProcediralPixmap(int w, int h) {
        Pixmap p = new Pixmap(w, h, Pixmap.Format.RGB888);

        p.setColor(1, 0, 0, 0.5f);
        p.fill();

        p.setColor(1, 1, 0, 1);
        p.drawLine(0, 0, w, h);
        p.drawLine(w, 0 , 0, h);

        p.setColor(0, 1, 1, 1);
        p.drawRectangle(0, 0, w, h);

        return p;
    }

    public void update(float deltaTime) {

        debugInput(deltaTime);
        updateTestObjects(deltaTime);
        hepler.update(deltaTime);
    }

    private void debugInput(float deltaTime) {

        if(Gdx.app.getType() != Application.ApplicationType.Desktop) {
            return;
        }

        float sprMoveSpeed = 5 * deltaTime;
        if(Gdx.input.isKeyPressed(Input.Keys.A)) {
            moveSelectedSprite(-sprMoveSpeed, 0);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.D)) {
            moveSelectedSprite(sprMoveSpeed, 0);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.W)) {
            moveSelectedSprite(0, sprMoveSpeed);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.S)) {
            moveSelectedSprite(0, -sprMoveSpeed);
        }


        // Helper camera controlls.
        float camMoveSpeed = 5 * deltaTime;
        float camMoveAccFactor = 5;
        if(Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) {
            camMoveSpeed *= camMoveAccFactor;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            moveCamera(-camMoveSpeed, 0);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            moveCamera(camMoveSpeed, 0);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.UP)) {
            moveCamera(0, camMoveSpeed);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            moveCamera(0, -camMoveSpeed);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.BACKSPACE)) {
            moveCamera(0, 0);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.V)) {
            rotateCamera(15);

        }



        // Camera controll zoom.
        float camZoomSpeed = 5 * deltaTime;
        float camZoomAccFactor = 5;
        if(Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) {
            camZoomSpeed *= camZoomAccFactor;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.COMMA)) {
            hepler.addZoom(camZoomSpeed);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.PERIOD)) {
            hepler.addZoom(-camZoomSpeed);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.SLASH)) {
            hepler.setZoom(1);
        }

    }

    private void rotateCamera(float i) {
        hepler.getPosition().rotate(i);


    }

    private void moveCamera(float x, float y) {
        x += hepler.getPosition().x;
        y += hepler.getPosition().y;
        hepler.setPosition(x, y);
    }

    private void moveSelectedSprite(float x, float y) {
        testSprites[selectedSprite].translate(x, y);

    }

    private void updateTestObjects(float deltaTime) {
        float rotation = testSprites[selectedSprite].getRotation();
        rotation += 90 * deltaTime;
        rotation %= 360;
        testSprites[selectedSprite].setRotation(rotation);

    }


    public boolean keyUp( int keyCode) {
        if(keyCode == Input.Keys.R) {
            init();
            Gdx.app.debug(TAG, "Gameworld Resetted");
        } else if(keyCode == Input.Keys.SPACE) {
            selectedSprite = (selectedSprite + 1) % testSprites.length;
            if(hepler.hasTarget()) {
                hepler.setTargetSprite(testSprites[selectedSprite]);
            }
            Gdx.app.debug(TAG, "Sprite #" + selectedSprite + " selected...");
        } else if(keyCode == Input.Keys.ENTER) {
            hepler.setTargetSprite(hepler.hasTarget() ? null : testSprites[selectedSprite]);
            Gdx.app.debug(TAG, "Camera follow enabled: " + hepler.hasTarget());
        }
        return false;
    }


}

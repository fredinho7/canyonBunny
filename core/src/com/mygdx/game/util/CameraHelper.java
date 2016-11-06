package com.mygdx.game.util;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by freddeWindows on 2016-11-05.
 */

public class CameraHelper {

    private static final String TAG = CameraHelper.class.getName();

    private final float MAX_ZOOM_IN = 0.25f;
    private final float MAX_ZOOM_OUT = 10.0f;

    private Vector2 position;
    private float zoom;
    private Sprite targetSprite;


    public CameraHelper() {
        position = new Vector2();
        zoom = 1.0f;
    }


    public void update(float deltaTime) {
        if(!hasTarget()) {
            return;
        }

        position.x = targetSprite.getX() + targetSprite.getOriginX();
        position.y = targetSprite.getY() + targetSprite.getOriginY();
    }

    public void setPosition(float x, float y) {
        this.position.set(x, y);
    }

    public Vector2 getPosition() {
        return this.position;
    }

    public void addZoom(float amount) {
        setZoom(zoom + amount);
    }

    public void setZoom(float zoom) {
        this.zoom = MathUtils.clamp(zoom, MAX_ZOOM_IN, MAX_ZOOM_OUT);
    }

    public float getZoom() {
        return zoom;
    }

    public void setTargetSprite(Sprite target) {
        this.targetSprite = target;
    }

    public Sprite getTargetSprite() {
        return targetSprite;
    }

    public boolean hasTarget() {
        return targetSprite != null;
    }

    public boolean hasTarget(Sprite target) {
        return hasTarget() && this.targetSprite.equals(target);
    }

    public void applyTo(OrthographicCamera camera) {
        camera.position.x = position.x;
        camera.position.y = position.y;
        camera.zoom = zoom;
        camera.update();
    }
}

package com.mygdx.game;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;
import com.mygdx.game.util.Constatns;

/**
 * Created by freddeWindows on 2016-11-05.
 */
public class WorldRenderer implements Disposable{

    private OrthographicCamera camera;
    private SpriteBatch batch;
    private WorldController worldController;


    public WorldRenderer(WorldController worldController) {
        this.worldController = worldController;
        init();
    }


    private void init() {
        batch = new SpriteBatch();
        camera = new OrthographicCamera(Constatns.VIEWPORT_WIDTH, Constatns.VIEWPORT_HEIGHT);
        camera.position.set(0, 0, 0);
        camera.update();
    }

    public void render(){
        rendererTestObjects();
    }

    private void rendererTestObjects() {
        worldController.hepler.applyTo(this.camera);
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        for(Sprite s : worldController.testSprites) {
            s.draw(batch);
        }
        batch.end();
    }

    public void resize(int width, int height){
        camera.viewportWidth = (Constatns.VIEWPORT_HEIGHT / height) * width;
        camera.update();
    }


    @Override
    public void dispose() {
        batch.dispose();
    }
}

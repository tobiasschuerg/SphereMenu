package com.tobiasschuerg.spheremenu;

import com.jme3.app.SimpleApplication;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Quad;

/**
 * Example of the Sphere Menu
 *
 * @author normenhansen
 */
public class SphereMenuExample extends SimpleApplication {

    public static void main(String[] args) {
        SphereMenuExample app = new SphereMenuExample();
        app.start();
    }

    @Override
    public void simpleInitApp() {
        createFloor();
        createSphereMenu();

        DirectionalLight sun = new DirectionalLight();
        sun.setColor(ColorRGBA.Red);
        sun.setDirection(new Vector3f(-.5f, -.5f, -.5f).normalizeLocal());
        rootNode.addLight(sun);
    }

    @Override
    public void simpleUpdate(float tpf) {
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }
    public static final Quaternion PITCH270 = new Quaternion().fromAngleAxis(FastMath.PI * 3 / 2, new Vector3f(1, 0, 0));

    private void createFloor() {
        Quad q = new Quad(20f, 20f);
        Geometry floor = new Geometry("floor", q);
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.Brown);
        floor.setMaterial(mat);
        floor.rotate(PITCH270);
        floor.move(getCamera().getLocation().add(new Vector3f(-15f, -1f, 15f)));
        rootNode.attachChild(floor);
    }

    private void createSphereMenu() {
        // create the menu
        SphereMenu menu = new SphereMenu(this);

        // set up the menu
        menu.setSize(45, 75);
        menu.setRadius(2f);
        menu.setColumns(2);

        // create / update the menu
        menu.create(4);

        // attach it and move it around user
        rootNode.attachChild(menu);
        menu.move(getCamera().getLocation());
        menu.rotate(getCamera().getRotation());
    }
}

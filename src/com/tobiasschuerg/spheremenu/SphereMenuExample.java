package com.tobiasschuerg.spheremenu;

import com.jme3.app.SimpleApplication;
import com.jme3.app.StatsAppState;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Geometry;
import com.simsilica.lemur.GuiGlobals;
import com.jme3.scene.shape.Quad;
import tobiasschuerg.lemur.piemenu.AbstractPieMenuCallback;
import tobiasschuerg.lemur.piemenu.PieMenu;
import tobiasschuerg.lemur.piemenu.example.CameraMovementFunctions;
import tobiasschuerg.lemur.piemenu.example.CameraMovementState;
import tobiasschuerg.lemur.piemenu.example.CameraToggleState;

/**
 * Example of the Sphere Menu.
 *
 * @author Tobias Schürg
 */
public class SphereMenuExample extends SimpleApplication {

    public static final Quaternion PITCH270 = new Quaternion().fromAngleAxis(FastMath.PI * 3 / 2, new Vector3f(1, 0, 0));

    public static void main(String[] args) {
        SphereMenuExample app = new SphereMenuExample();
        app.start();
    }
    private SphereMenu menu;

    public SphereMenuExample() {
        super(new StatsAppState(), new CameraMovementState(), new CameraToggleState());
    }

    @Override
    public void simpleInitApp() {
        GuiGlobals.initialize(this);
        CameraMovementFunctions.initializeDefaultMappings(GuiGlobals.getInstance().getInputMapper());

        createFloor();
        createSphereMenu();
        createLight();
    }

    @Override
    public void simpleUpdate(float tpf) {
        if (menu != null) {
            // make the menu follow the user.
            menu.setLocalTranslation(getCamera().getLocation());
            // menu.lookAt(getCamera().getLocation().negate(), getCamera().getUp());
        }
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //nothing to do
    }

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
        menu = new SphereMenu(this);

        // set up the menu
        menu.setSize(30, 40);
        menu.setRadius(4f);
        menu.setColumns(3);

        // create options
        menu.add("bar", "Interface/favorite21.png");
        menu.add("bar", "Interface/home152.png");
        menu.add("bar", "Interface/settings49.png");
        menu.add("bar", "Interface/power107.png");
        menu.add("bar", "Interface/calculator69.png");
        addButtonWithRadialMenu();
        menu.add("bar", "Interface/facebook4.png");

        // create the menu
        menu.create();

        // attach it and move it around user
        rootNode.attachChild(menu);
        menu.move(getCamera().getLocation());
        menu.rotate(getCamera().getRotation());
    }

    private void createLight() {
        DirectionalLight sun = new DirectionalLight();
        sun.setColor(ColorRGBA.Red);
        sun.setDirection(new Vector3f(-.5f, -.5f, -.5f).normalizeLocal());
        rootNode.addLight(sun);
    }

    private void addButtonWithRadialMenu() {
        // add piemenu
        Geometry radial = menu.add("one", "Interface/more9.png");
        radial.rotate(3f, 0f, 0f);
        PieMenu pieMenu = new PieMenu(this, radial); // create a new pieMenu
        pieMenu.setButtonSize(0.2f);
        pieMenu.setRadius(0.8f);

        pieMenu.setCallback(new AbstractPieMenuCallback() {
            @Override
            public void onOptionSelected(String name) {
                System.out.println("Selected option: " + name);
            }
        });
        pieMenu.addOption("RESIZE", "Interface/Logo/Monkey.png");
        pieMenu.addOption("TRANSLATE", "Interface/Logo/Monkey.png");
        pieMenu.addOption("ROTATE", "Interface/Logo/Monkey.png");
    }
}

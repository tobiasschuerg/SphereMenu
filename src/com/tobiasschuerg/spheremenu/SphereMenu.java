/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tobiasschuerg.spheremenu;

import com.jme3.app.Application;
import com.jme3.material.Material;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;
import com.jme3.texture.Texture;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Tobias
 */
public class SphereMenu extends Node {

    private List<Node> options = new ArrayList<Node>();
    private final Application app;

    public SphereMenu(Application app) {
        super("SphereMenu");
        this.app = app;
        create();
    }

    public void create() {
        for (int i = 0; i < 10; i++) {
            Node option = new Node();
            attachChild(option);
            options.add(option);

            Geometry buttonGeo = createButton();
            option.attachChild(buttonGeo);

            buttonGeo.setLocalTranslation(new Vector3f(0f, 0f, 6f));

            // move option to the correct position
            // get random between 0 and 30;
            int xdeg = - 40; // -rand.nextInt(45);
            float rot = xdeg * FastMath.DEG_TO_RAD;
            Quaternion xrot = new Quaternion().fromAngleAxis(rot, new Vector3f(1, 0, 0));

            int deg = 60 / 10 * i;
            rot = deg * FastMath.DEG_TO_RAD;
            Quaternion yrot = new Quaternion().fromAngleAxis(rot, new Vector3f(0, 1, 0));

            deg = 0; //-rand.nextInt(30);
            rot = deg * FastMath.DEG_TO_RAD;
            Quaternion zrot = new Quaternion().fromAngleAxis(rot, new Vector3f(0, 0, 1));

            option.rotate(xrot.mult(yrot));
        }
    }

    private Geometry createButton() {
        float length = 0.5f;
        Box b = new Box(length, length, length / 10);
        Geometry geom = new Geometry("button", b);

        Material cube1Mat = new Material(app.getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
        Texture cube1Tex = app.getAssetManager().loadTexture("Interface/Logo/Monkey.png");
        cube1Mat.setTexture("ColorMap", cube1Tex);
        geom.setMaterial(cube1Mat);
        return geom;
    }
}

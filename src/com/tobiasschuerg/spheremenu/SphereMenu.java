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
 * @author Tobias Schürg
 */
public class SphereMenu extends Node {

    private int columns = 4;
    private List<Node> options = new ArrayList<Node>();
    private final Application app;
    private float degMenuWidth = 60;
    private float degMenuHeight = 50;
    private float radius = 9f;
    private float buttonSize = 0.25f;
    private float offsetVer = 5f;

    public SphereMenu(Application app) {
        super("SphereMenu");
        this.app = app;
    }

    public void setSize(float horDeg, float verDeg) {
        this.degMenuWidth = horDeg;
        this.degMenuHeight = verDeg;
    }

    public void setColumns(int colums) {
        columns = colums;
    }

    public void create() {
        int itemCount = options.size();
        System.out.println("items " + itemCount);
        int rows;
        int itemsLastRow = itemCount % columns;
        if (itemsLastRow > 0) {
            rows = itemCount / columns;
        } else {
            rows = itemCount / columns + 1;
        }
        int currentRow = 0;
        int currentCol = 0;

        for (int i = 0; i < itemCount; i++) {
            Node option = options.get(i);
            /*
             * move option to the correct position
             */
            float degVer = -degMenuHeight / (rows) * currentRow - offsetVer;
            float rot = degVer * FastMath.DEG_TO_RAD;
            Quaternion xrot = new Quaternion().fromAngleAxis(rot, new Vector3f(1, 0, 0));

            float degHor;
            if (currentRow == rows && itemsLastRow > 0) {
                degHor = degMenuWidth / (itemsLastRow) * (currentCol + 0.5f) - (degMenuWidth / 2);
            } else {
                degHor = degMenuWidth / (columns - 1) * currentCol - (degMenuWidth / 2);
            }
            rot = degHor * FastMath.DEG_TO_RAD;
            Quaternion yrot = new Quaternion().fromAngleAxis(rot, new Vector3f(0, 1, 0));

            option.rotate(yrot.mult(xrot));

            currentCol++;
            if (currentCol >= columns) {
                currentCol = 0;
                currentRow++;
            }
        }
    }

    private Geometry createButton(String imageResource) {
        Box b = new Box(buttonSize, buttonSize, buttonSize / 10);
        Geometry geom = new Geometry("button", b);

        Material cube1Mat = new Material(app.getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
        Texture cube1Tex = app.getAssetManager().loadTexture(imageResource);
        cube1Mat.setTexture("ColorMap", cube1Tex);
        geom.setMaterial(cube1Mat);
        return geom;
    }

    void setRadius(float f) {
        this.radius = f;
    }

    public Geometry add(String foo, String image) {
        Node option = new Node(foo);

        Geometry buttonGeo = createButton(image);
        buttonGeo.setLocalTranslation(new Vector3f(0f, 0f, radius));

        option.attachChild(buttonGeo);
        attachChild(option);
        options.add(option);
        return buttonGeo;
    }
}

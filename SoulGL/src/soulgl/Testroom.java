/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package soulgl;

import com.jme3.asset.AssetManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.bullet.util.CollisionShapeFactory;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Vector2f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;
import com.jme3.texture.Texture;
import com.jme3.util.TangentBinormalGenerator;

/**
 *
 * @author catthew
 */
public class Testroom extends Node{
    private RigidBodyControl ctrl;
    
    public Testroom(AssetManager assetManager, BulletAppState bulletAppState){
        //Make floor
        Box f = new Box(100, 1, 100);
        Box w = new Box(1,10,100);
        TangentBinormalGenerator.generate(f); 
        TangentBinormalGenerator.generate(w);
        f.scaleTextureCoordinates(new Vector2f(16,16));
        w.scaleTextureCoordinates(new Vector2f(16,2));
        Geometry floor = new Geometry("Box", f);
        Geometry wall1 = new Geometry("Wall1", w);
        //Make the material
        Material mat = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");
        //Diffuse texture
        Texture tex = assetManager.loadTexture("Textures/pond.jpg");
        tex.setWrap(Texture.WrapMode.Repeat);
        mat.setTexture("DiffuseMap", tex);
        //Normal texture
        tex = assetManager.loadTexture("Textures/pond_normal.png");
        tex.setWrap(Texture.WrapMode.Repeat);
        mat.setTexture("NormalMap", tex);
        mat.setBoolean("UseMaterialColors", true);
        mat.setColor("Ambient", ColorRGBA.DarkGray);
        mat.setColor("Diffuse",ColorRGBA.White);
        mat.setColor("Specular", ColorRGBA.Pink);
        mat.setFloat("Shininess", 8f);
        floor.setMaterial(mat);
        wall1.setMaterial(mat);
        
        
        Geometry wall2 = wall1.clone();
        Geometry wall3 = wall1.clone();
        wall3.rotate(0,90.f*FastMath.DEG_TO_RAD,0);
        Geometry wall4 = wall3.clone();
        //Position the pieces
        floor.setLocalTranslation(0,0,0);
        wall1.setLocalTranslation(-100,10,0);
        wall2.setLocalTranslation(100,10,0);
        wall3.setLocalTranslation(0,10,-100);
        wall4.setLocalTranslation(0,10,100);
        
        this.attachChild(floor);
        this.attachChild(wall1);
        this.attachChild(wall2);
        this.attachChild(wall3);
        this.attachChild(wall4);
        // Set up collision detection for the floor
        CollisionShape shape = CollisionShapeFactory.createMeshShape(this);
        ctrl = new RigidBodyControl(shape,0);
        floor.addControl(ctrl);
        bulletAppState.getPhysicsSpace().add(ctrl);
    }
    
}

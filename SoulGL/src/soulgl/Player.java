package soulgl;

import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.shapes.CapsuleCollisionShape;
import com.jme3.bullet.control.CharacterControl;
import com.jme3.input.InputManager;
import com.jme3.input.controls.ActionListener;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.renderer.Camera;

public class Player extends Node implements ActionListener {
    
    private CharacterControl ctrl;
    private Vector3f walkDirection = new Vector3f();
    private boolean left = false, right = false, up = false, down = false;
    //Temporary working variables
    private Vector3f camDir = new Vector3f();
    private Vector3f camLeft = new Vector3f();
    private Camera cam;
    private BulletAppState bullet;
    private InputManager inputManager;
    
    public Player(InputManager inputManager, Camera cam, BulletAppState bullet){
        this.cam = cam;
        this.bullet = bullet;
        this.inputManager = inputManager;
        
        inputManager.addListener(this, "Left","Right","Up","Down","Jump");
        
        // Set up collision detection for the player
        CapsuleCollisionShape capsuleShape = new CapsuleCollisionShape(1.5f, 6f, 1);
        ctrl = new CharacterControl(capsuleShape, 0.05f);
        ctrl.setJumpSpeed(20);
        ctrl.setFallSpeed(30);
        ctrl.setGravity(30);
        ctrl.setPhysicsLocation(new Vector3f(0, 10, 0));
        
        this.bullet.getPhysicsSpace().add(ctrl);
    }
    
    public void onAction(String binding, boolean isPressed, float tpf) {
        if (binding.equals("Left")) {
            left = isPressed;
        } else if (binding.equals("Right")) {
            right= isPressed;
        } else if (binding.equals("Up")) {
            up = isPressed;
        } else if (binding.equals("Down")) {
            down = isPressed;
        } else if (binding.equals("Jump")) {
            if (isPressed) { ctrl.jump(); }
        }
    }
    
    public void update(float tpf){
        camDir.set(cam.getDirection()).multLocal(0.6f);
        camLeft.set(cam.getLeft()).multLocal(0.4f);
        walkDirection.set(0,0,0);
        if(left){
            walkDirection.addLocal(camLeft);
        }
        if(right){
            walkDirection.addLocal(camLeft.negate());
        }
        if(up){
            walkDirection.addLocal(camDir);
        }
        if(down){
            walkDirection.addLocal(camDir.negate());
        }
        ctrl.setWalkDirection(walkDirection);
        cam.setLocation(ctrl.getPhysicsLocation());
    }
    
    
}

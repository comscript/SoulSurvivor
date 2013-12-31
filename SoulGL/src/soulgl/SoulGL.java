package soulgl;

import com.jme3.app.SimpleApplication;
import com.jme3.bullet.BulletAppState;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;

/**
 * test
 * @author normenhansen
 */
public class SoulGL extends SimpleApplication {

    private BulletAppState bulletAppState;
    private Player player;
    private Testroom testroom;
    
    public static void main(String[] args) {
        SoulGL app = new SoulGL();
        app.start();
    }

    @Override
    public void simpleInitApp() {
        /* Set up Physics */
        bulletAppState = new BulletAppState();
        stateManager.attach(bulletAppState);
        //bulletAppState.getPhysicsSpace().enableDebug(assetManager);
       
        // We re-use the flyby camera for rotation, while positioning is handled by physics.
        viewPort.setBackgroundColor(new ColorRGBA(0.7f,0.8f,1f,1f));
        flyCam.setMoveSpeed(100.f);
        setUpKeys();
        setUpLight();
        
        //Make player
        player = new Player(inputManager,cam,bulletAppState);
        
        //Make room
        testroom = new Testroom(assetManager,bulletAppState);
        
        rootNode.attachChild(testroom);
        
    }
    
    private void setUpLight(){
        AmbientLight al = new AmbientLight();
        al.setColor(ColorRGBA.White.mult(1.3f));
        rootNode.addLight(al);
        
        DirectionalLight dl = new DirectionalLight();
        dl.setColor(ColorRGBA.White);
        dl.setDirection(new Vector3f(2.8f,-2.8f,-2.8f).normalizeLocal());
        rootNode.addLight(dl);
    }
    
    private void setUpKeys(){
        inputManager.addMapping("Left", new KeyTrigger(KeyInput.KEY_A));
        inputManager.addMapping("Right", new KeyTrigger(KeyInput.KEY_D));
        inputManager.addMapping("Up", new KeyTrigger(KeyInput.KEY_W));
        inputManager.addMapping("Down", new KeyTrigger(KeyInput.KEY_S));
        inputManager.addMapping("Jump", new KeyTrigger(KeyInput.KEY_SPACE));
    }
   

    @Override
    public void simpleUpdate(float tpf) {
        player.update(tpf);
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }
}

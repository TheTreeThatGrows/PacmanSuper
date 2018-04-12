package pacmansuper.game.CollectableObjects;

import com.almasb.fxgl.app.FXGL;
import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.entity.Control;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import com.almasb.fxgl.time.LocalTimer;
import javafx.util.Duration;

public class HealthBoost extends Control {

    // ------------------------------------- <<< Data >>> -------------------------------------
    private PhysicsComponent physics;


    // ------------------------------------- <<< Energy Boost Animation >>> -------------------------------------


    //Animation
    private AnimatedTexture texture;
    private AnimationChannel EnergyBoostAnim;

    public HealthBoost() {

        //Coin Animation
        EnergyBoostAnim = new AnimationChannel("healthBoost.png", 4, 52, 52, Duration.seconds(0.3), 0, 3);

        texture = new AnimatedTexture(EnergyBoostAnim); //Default animation
    }

    //Set entity texture
    @Override
    public void onAdded(Entity entity) {
        entity.setView(texture);
    }

    @Override
    public void onUpdate(Entity entity, double v) {

    }



}
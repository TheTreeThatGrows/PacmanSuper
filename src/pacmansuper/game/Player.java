package pacmansuper.game;

import com.almasb.fxgl.app.FXGL;
import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.entity.Control;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import com.almasb.fxgl.time.LocalTimer;
import javafx.util.Duration;

import static com.almasb.fxgl.app.DSLKt.play;
import static com.almasb.fxgl.app.DSLKt.spawn;

public class Player extends Control {

    // ------------------------------------- <<< Data >>> -------------------------------------
    private PhysicsComponent physics;
    private Entity player;


    // ------------------------------------- <<< Player Animation >>> -------------------------------------
    //Animation
    private AnimatedTexture texture;
    private AnimationChannel idle, walk;

    public Player() {

        //Player Animation
        idle = new AnimationChannel("pacman3.png", 4, 65, 52, Duration.seconds(1), 3, 3);
        walk = new AnimationChannel("pacman3.png", 4, 65, 52, Duration.seconds(0.3), 0, 3);



        texture = new AnimatedTexture(idle); //Default animation
    }

    //Set entity texture
    @Override
    public void onAdded(Entity entity) {
        entity.setView(texture);
    }


    @Override
    public void onUpdate(Entity entity, double tpf) {

        //Move animation
        if(FXGLMath.abs(physics.getVelocityX()) > 0) {
            texture.setAnimationChannel(walk);

            //Slowing down
            if (FXGLMath.abs(physics.getVelocityX()) < 320) {
                physics.setVelocityX(0);
            }
        }
        else  if (FXGLMath.abs(physics.getVelocityY()) == 0) {
            texture.setAnimationChannel(idle);
        } else {
            texture.setAnimationChannel(walk);
        }


    }


    // ------------------------------------- <<< Movement >>> -------------------------------------
    public void right() {

        getEntity().setScaleX(1); //Player faces right
        physics.setVelocityX(320); //Move right Velocity
    }

    public void left() {

        getEntity().setScaleX(-1); //Player faces left
        physics.setVelocityX(-320); //Move left Velocity
    }

    public void fly() {
        physics.setVelocityY(-350);
    }

    public void descend() {
        physics.setVelocityY(350);
    }

    public void cantfly() {
        physics.getVelocityY();
    }

}



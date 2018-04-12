package pacmansuper.game.Bosses;

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

import java.util.Random;

import static com.almasb.fxgl.app.DSLKt.play;
import static com.almasb.fxgl.app.DSLKt.spawn;

public class Rhatbu extends Control {

    // ------------------------------------- <<< Data >>> -------------------------------------
    private PhysicsComponent physics;
    private Entity player;


    // ------------------------------------- <<< Player Animation >>> -------------------------------------
    //Animation
    private AnimatedTexture texture;
    private LocalTimer aiTimer;
    private AnimationChannel idle;

    public Rhatbu() {

        //Player Animation
        idle = new AnimationChannel("Rhatbu.png", 4, 226, 180, Duration.seconds(0.3), 0, 3);


        texture = new AnimatedTexture(idle); //Default animation
    }

    //Set entity texture
    @Override
    public void onAdded(Entity entity) {
        entity.setView(texture);
        aiTimer = FXGL.newLocalTimer();
        aiTimer.capture();
    }
    // ------------------------------------- <<< Rhatbu AI>>> -------------------------------------
    @Override
    public void onUpdate(Entity entity, double tpf) {

        //Rhatbu AI
        if(aiTimer.elapsed(Duration.seconds(0.8))) {
            Random rand = new Random();
            int  rnd = rand.nextInt(10) + 0;
            aiTimer.capture();

            if(rnd <= 3) {
                enemyRight();
            } else if (rnd <= 6 && rnd > 3){
                enemyLeft();
            } else {
                enemyJump();
            }
        }
    }


    // ------------------------------------- <<< Movement >>> -------------------------------------
    public void enemyRight() {

        getEntity().setScaleX(1); //Enemy faces right
        physics.setVelocityX(700); //Move right Velocity
    }

    public void enemyLeft() {

        getEntity().setScaleX(-1); //Enemy faces left
        physics.setVelocityX(-700); //Move left Velocity
    }

    public void enemyJump() {

        physics.setVelocityY(-950);
    }

}



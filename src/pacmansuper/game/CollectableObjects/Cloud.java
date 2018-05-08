package pacmansuper.game.CollectableObjects;

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

/**
 * Cloud Class Animation
 *
 * @author Earl John Laguardia
 */

public class Cloud extends Control {

    // ------------------------------------- <<< Data >>> -------------------------------------
    private PhysicsComponent physics;
    private Entity player;


    // ------------------------------------- <<< Player Animation >>> -------------------------------------
    //Animation
    private AnimatedTexture texture;
    private LocalTimer aiTimer;
    private AnimationChannel idle;

    public Cloud() {

    }

    //Set entity texture
    @Override
    public void onAdded(Entity entity) {
        aiTimer = FXGL.newLocalTimer();
        aiTimer.capture();
    }
    // ------------------------------------- <<< Dark Flame Master AI>>> -------------------------------------
    @Override
    public void onUpdate(Entity entity, double tpf) {

        //Dark Flame Master AI
        if(aiTimer.elapsed(Duration.seconds(0))) {
            Random rand = new Random();
            int  rnd = rand.nextInt(10) + 0;
            aiTimer.capture();

            if(rnd <= 5) {
                cloudRight();
            } else {
                cloudLeft();
            }
        }
    }


    // ------------------------------------- <<< Movement >>> -------------------------------------

    /**
     * Method for Cloud Animation Right
     *
     * @author Earl John Laguardia
     */

    public void cloudRight() {

        getEntity().translateX(0.4);
    }

    /**
     * Method for Cloud Animation Left
     *
     * @author Earl John Laguardia
     */

    public void cloudLeft() {

        getEntity().translateX(-0.4);
    }
}



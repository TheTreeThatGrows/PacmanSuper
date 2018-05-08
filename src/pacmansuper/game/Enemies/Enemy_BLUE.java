package pacmansuper.game.Enemies;

import com.almasb.fxgl.app.FXGL;
import com.almasb.fxgl.entity.Control;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.time.LocalTimer;
import javafx.util.Duration;

import java.util.Random;

import static com.almasb.fxgl.app.DSLKt.spawn;

/**
 * Enemy Blue Class
 *
 * @author Earl John Laguardia
 */

public class Enemy_BLUE extends Control {

    // ------------------------------------- <<< Data >>> -------------------------------------
    private PhysicsComponent physics;
    private LocalTimer LeftTimer;
    private LocalTimer RightTimer;
    private LocalTimer JumpTimer;

    @Override
    public void onAdded(Entity entity) {
        JumpTimer = FXGL.newLocalTimer();
        JumpTimer.capture();
    }

    // ------------------------------------- <<< Enemy AI >>> -------------------------------------
    @Override
    public void onUpdate(Entity entity, double tpf) {


        //Enemy AI
        if(JumpTimer.elapsed(Duration.seconds(3))) {
            enemyJump();
            JumpTimer.capture();
        }
    }


    // ------------------------------------- <<< Movement >>> -------------------------------------

    /**
     * Method for Moving Right
     *
     * @author Earl John Laguardia
     */

    public void enemyRight() {

        getEntity().setScaleX(-1); //Enemy faces right
        physics.setVelocityX(250); //Move right Velocity
    }

    /**
     * Method for Moving Left
     *
     * @author Earl John Laguardia
     */

    public void enemyLeft() {

        getEntity().setScaleX(1); //Enemy faces left
        physics.setVelocityX(-250); //Move left Velocity
    }

    /**
     * Method for Jumping
     *
     * @author Earl John Laguardia
     */

    public void enemyJump() {

        physics.setVelocityY(-650);
    }

}
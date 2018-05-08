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
 * Enemy Green Class
 *
 * @author Earl John Laguardia
 */

public class Enemy_GREEN extends Control {

    // ------------------------------------- <<< Data >>> -------------------------------------
    private PhysicsComponent physics;
    private LocalTimer aiTimer;

    @Override
    public void onAdded(Entity entity) {
        aiTimer = FXGL.newLocalTimer();
        aiTimer.capture();
    }

    // ------------------------------------- <<< Enemy AI>>> -------------------------------------
    @Override
    public void onUpdate(Entity entity, double tpf) {

        //Enemy AI
        if(aiTimer.elapsed(Duration.seconds(0.7))) {
            Random rand = new Random();
            int  rnd = rand.nextInt(10) + 1;
            aiTimer.capture();

            if(rnd <= 5) {
                enemyRight();
            } else {
                enemyLeft();
            }
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
        physics.setVelocityX(400); //Move right Velocity
    }

    /**
     * Method for Moving Left
     *
     * @author Earl John Laguardia
     */

    public void enemyLeft() {

        getEntity().setScaleX(1); //Enemy faces left
        physics.setVelocityX(-400); //Move left Velocity
    }


}
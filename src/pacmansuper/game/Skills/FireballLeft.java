package pacmansuper.game.Skills;

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
 * Skill Class For: FireballLeft
 *
 * @author Earl John Laguardia
 */

public class FireballLeft extends Control {

    // ------------------------------------- <<< Data >>> -------------------------------------
    private PhysicsComponent physics;
    private LocalTimer shoot;
    private Entity player;


    @Override
    public void onAdded(Entity entity) {
        shoot = FXGL.newLocalTimer();
        shoot.capture();
    }


    @Override
    public void onUpdate(Entity entity, double tpf) {

        if(shoot.elapsed(Duration.seconds(0))) {
            shootLeft();
            shoot.capture();

        }
    }


    // ------------------------------------- <<< Movement >>> -------------------------------------

    /**
     * Method for Shooting Left
     *
     * @author Earl John Laguardia
     */

    public void shootLeft() {

        getEntity().setScaleX(-1);
        getEntity().translateX(-25);
    }

}
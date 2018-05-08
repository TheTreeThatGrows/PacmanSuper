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
 * Skill Class For: Supernova 1
 *
 * @author Earl John Laguardia
 */

public class Supernova1 extends Control {

    // ------------------------------------- <<< Data >>> -------------------------------------
    private PhysicsComponent physics;
    private LocalTimer supernova;
    private Entity player;

    @Override
    public void onAdded(Entity entity) {
        supernova = FXGL.newLocalTimer();
        supernova.capture();
    }

    @Override
    public void onUpdate(Entity entity, double tpf) {

        if(supernova.elapsed(Duration.seconds(0))) {
            supernova1();
            supernova.capture();
        }
    }

    // ------------------------------------- <<< Movement >>> -------------------------------------

    /**
     * Method for Shooting Supernova 1
     *
     * @author Earl John Laguardia
     */

    public void supernova1() {

        getEntity().setScaleX(1);

        getEntity().translateY(-7);
        getEntity().translateX(7);
    }
}
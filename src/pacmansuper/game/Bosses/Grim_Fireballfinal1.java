package pacmansuper.game.Bosses;

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
 * Grim Attack Pattern: Fireball Final 1
 *
 * @author Earl John Laguardia
 */

public class Grim_Fireballfinal1 extends Control {

    // ------------------------------------- <<< Data >>> -------------------------------------
    private PhysicsComponent physics;
    private LocalTimer fireballfinal1;
    private Entity player;

    @Override
    public void onAdded(Entity entity) {
        fireballfinal1 = FXGL.newLocalTimer();
        fireballfinal1.capture();
    }

    @Override
    public void onUpdate(Entity entity, double tpf) {

        if(fireballfinal1.elapsed(Duration.seconds(0))) {
            fireballfinal1();
            fireballfinal1.capture();
        }
    }

    // ------------------------------------- <<< Movement >>> -------------------------------------

    /**
     * Method for Shooting Fireball Final 1
     *
     * @author Earl John Laguardia
     */

    public void fireballfinal1() {

        getEntity().setScaleX(1);
        getEntity().translateY(7);
    }
}
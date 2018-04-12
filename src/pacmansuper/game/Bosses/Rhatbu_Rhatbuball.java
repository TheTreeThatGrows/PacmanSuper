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

public class Rhatbu_Rhatbuball extends Control {

    // ------------------------------------- <<< Data >>> -------------------------------------
    private PhysicsComponent physics;
    private LocalTimer rhatbuball;
    private Entity player;

    @Override
    public void onAdded(Entity entity) {
        rhatbuball = FXGL.newLocalTimer();
        rhatbuball.capture();
    }

    @Override
    public void onUpdate(Entity entity, double tpf) {

        if(rhatbuball.elapsed(Duration.seconds(0))) {
            rhatbuball();
            rhatbuball.capture();
        }
    }

    // ------------------------------------- <<< Movement >>> -------------------------------------
    public void rhatbuball() {

        getEntity().setScaleX(1);
        getEntity().translateY(7);
    }
}
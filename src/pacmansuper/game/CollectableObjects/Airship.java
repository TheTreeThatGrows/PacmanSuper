package pacmansuper.game.CollectableObjects;

import com.almasb.fxgl.app.FXGL;
import com.almasb.fxgl.entity.Control;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.time.LocalTimer;
import javafx.util.Duration;

import java.util.Random;

import static com.almasb.fxgl.app.DSLKt.spawn;

public class Airship extends Control {

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
            FlyRight();
            shoot.capture();

        }
    }


    // ------------------------------------- <<< Movement >>> -------------------------------------
    public void FlyRight() {

        getEntity().setScaleX(1);
        getEntity().translateX(5);
    }

}
package pacmansuper.game;

import com.almasb.fxgl.entity.*;
import com.almasb.fxgl.entity.component.CollidableComponent;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.box2d.dynamics.BodyType;
import pacmansuper.game.Bosses.*;
import pacmansuper.game.CollectableObjects.*;
import pacmansuper.game.Enemies.Enemy_BLUE;
import pacmansuper.game.Enemies.Enemy_RED;
import pacmansuper.game.Skills.*;
import pacmansuper.game.Skills.FireballLeft;
import pacmansuper.game.Skills.FireballRight;

import static com.almasb.fxgl.app.DSLKt.texture;

@SetEntityFactory
public class PacmanFactory implements EntityFactory {

    // ------------------------------------- <<< Data >>> -------------------------------------
    private Entity player;

    // ------------------------------------- <<< Stage main objects >>> -------------------------------------
    @Spawns("platform")
    public Entity newPlatform(SpawnData data) {
        return Entities.builder()
                .type(GameType.PLATFORM)
                .from(data)
                .bbox(new HitBox(BoundingShape.box(data.<Integer>get("width"), data.<Integer>get("height"))))
                .with(new PhysicsComponent()) //Platform has physics
                .with(new CollidableComponent(true))
                .build();
    }
    @Spawns("coin")
    public Entity newCoin(SpawnData data) {
        return Entities.builder()
                .type(GameType.COIN)
                .from(data)
                .with(new Coin())
                .bbox(new HitBox(BoundingShape.box(52, 52)))
                .with(new CollidableComponent(true))
                .build();
    }
    @Spawns("healthboost")
    public Entity HealthBoost(SpawnData data) {
        return Entities.builder()
                .type(GameType.HEALTHBOOST)
                .from(data)
                .with(new HealthBoost())
                .bbox(new HitBox(BoundingShape.box(52, 52)))
                .with(new CollidableComponent(true))
                .build();
    }
    @Spawns("cloud")
    public Entity newCloudRight(SpawnData data) {
        return Entities.builder()
                .type(GameType.CLOUD)
                .from(data)
                .with(new Cloud())
                .viewFromTexture("Cloud.png")
                .bbox(new HitBox(BoundingShape.box(380, 240)))
                .build();
    }

    @Spawns("removeFlight")
    public Entity newRemoveFlight(SpawnData data) {
        return Entities.builder()
                .type(GameType.REMOVEFLIGHT)
                .from(data)
                .bbox(new HitBox(BoundingShape.box(data.<Integer>get("width"), data.<Integer>get("height"))))
                .with(new CollidableComponent(true))
                .build();
    }


    // ------------------------------------- <<< Sprites >>> -------------------------------------

    //Player
    @Spawns("player")
    public Entity newPlayer(SpawnData data) {
        PhysicsComponent physics = new PhysicsComponent();
        physics.setBodyType(BodyType.DYNAMIC);

        return Entities.builder()
                .type(GameType.PLAYER)
                .from(data)
                .bbox(new HitBox(BoundingShape.box(65, 52)))
                .with(physics)
                .with(new Player())
                .with(new CollidableComponent(true))
                .build();
    }

    //Bosses
    @Spawns("boss_darkflamemaster")
    public Entity newBoss_DarkFlameMaster(SpawnData data) {
        PhysicsComponent physics = new PhysicsComponent();
        physics.setBodyType(BodyType.DYNAMIC);

        return Entities.builder()
                .type(GameType.BOSS_DARKFLAMEMASTER)
                .from(data)
                .viewFromTexture("DarkFlameMaster.gif")
                .bbox(new HitBox(BoundingShape.box(192, 249)))
                .with(physics)
                .with(new DarkFlameMaster())
                .with(new CollidableComponent(true))
                .build();
    }
    @Spawns("boss_rhatbu")
    public Entity newBoss_Rhatbu(SpawnData data) {
        PhysicsComponent physics = new PhysicsComponent();
        physics.setBodyType(BodyType.DYNAMIC);

        return Entities.builder()
                .type(GameType.BOSS_RHATBU)
                .from(data)
                .bbox(new HitBox(BoundingShape.box(226, 180)))
                .with(physics)
                .with(new Rhatbu())
                .with(new CollidableComponent(true))
                .build();
    }

    @Spawns("boss_bedj")
    public Entity newBoss_Bedj(SpawnData data) {
        PhysicsComponent physics = new PhysicsComponent();
        physics.setBodyType(BodyType.DYNAMIC);

        return Entities.builder()
                .type(GameType.BOSS_BEDJ)
                .from(data)
                .viewFromTexture("Bedj.gif")
                .bbox(new HitBox(BoundingShape.box(672, 378)))
                .with(physics)
                .with(new Bedj())
                .with(new CollidableComponent(true))
                .build();
    }

    @Spawns("boss_grim")
    public Entity newBoss_Grim(SpawnData data) {
        PhysicsComponent physics = new PhysicsComponent();
        physics.setBodyType(BodyType.DYNAMIC);

        return Entities.builder()
                .type(GameType.BOSS_GRIM)
                .from(data)
                .viewFromTexture("Grim.gif")
                .bbox(new HitBox(BoundingShape.box(347, 297)))
                .with(physics)
                .with(new Grim())
                .with(new CollidableComponent(true))
                .build();
    }

    //Enemies
    @Spawns("enemy_red")
    public Entity newEnemy_RED(SpawnData data) {
        PhysicsComponent physics = new PhysicsComponent();
        physics.setBodyType(BodyType.DYNAMIC);

        return Entities.builder()
                .type(GameType.ENEMY)
                .from(data)
                .viewFromTexture("enemy_red.png")
                .bbox(new HitBox(BoundingShape.box(52, 52)))
                .with(physics)
                .with(new Enemy_RED())
                .with(new CollidableComponent(true))
                .build();
    }

    @Spawns("enemy_blue")
    public Entity newEnemy_BLUE(SpawnData data) {
        PhysicsComponent physics = new PhysicsComponent();
        physics.setBodyType(BodyType.DYNAMIC);

        return Entities.builder()
                .type(GameType.ENEMY)
                .from(data)
                .viewFromTexture("enemy_blue.png")
                .bbox(new HitBox(BoundingShape.box(52, 52)))
                .with(physics)
                .with(new Enemy_BLUE())
                .with(new CollidableComponent(true))
                .build();
    }

    // ------------------------------------- <<< Skills >>> -------------------------------------

    //Fireballs
    @Spawns("fireballRight")
    public Entity newfireballRight(SpawnData data) {
        return Entities.builder()
                .type(GameType.FIREBALL)
                .from(data)
                .viewFromTexture("fireball.png")
                .bbox(new HitBox(BoundingShape.box(52, 17)))
                .with(new CollidableComponent(true))
                .with(new FireballRight())
                .build();
    }
    @Spawns("fireballLeft")
    public Entity newfireballLeft(SpawnData data) {
        return Entities.builder()
                .type(GameType.FIREBALL)
                .from(data)
                .viewFromTexture("fireball.png")
                .bbox(new HitBox(BoundingShape.box(52, 17)))
                .with(new CollidableComponent(true))
                .with(new FireballLeft())
                .build();
    }

    //Fireblast
    @Spawns("fireblastRight")
    public Entity newFireblastRight(SpawnData data) {
        return Entities.builder()
                .type(GameType.FIREBLAST)
                .from(data)
                .viewFromTexture("Fireblast.gif")
                .bbox(new HitBox(BoundingShape.box(140, 94)))
                .with(new CollidableComponent(true))
                .with(new FireblastRight())
                .build();
    }
    @Spawns("fireblastLeft")
    public Entity newFireblastLeft(SpawnData data) {
        return Entities.builder()
                .type(GameType.FIREBLAST)
                .from(data)
                .viewFromTexture("Fireblast.gif")
                .bbox(new HitBox(BoundingShape.box(140, 94)))
                .with(new CollidableComponent(true))
                .with(new FireblastLeft())
                .build();
    }

    //Flamestrike
    @Spawns("flamestrikeRight")
    public Entity newflamestrikeRight(SpawnData data) {
        return Entities.builder()
                .type(GameType.FLAMESTRIKE)
                .from(data)
                .viewFromTexture("Flamestrike.gif")
                .bbox(new HitBox(BoundingShape.box(193, 130)))
                .with(new CollidableComponent(true))
                .with(new FlamestrikeRight())
                .build();
    }
    @Spawns("flamestrikeLeft")
    public Entity newflamestrikeLeft(SpawnData data) {
        return Entities.builder()
                .type(GameType.FLAMESTRIKE)
                .from(data)
                .viewFromTexture("Flamestrike.gif")
                .bbox(new HitBox(BoundingShape.box(193, 130)))
                .with(new CollidableComponent(true))
                .with(new FlamestrikeLeft())
                .build();
    }

    //Ultimate Supernova
    @Spawns("supernovaRight")
    public Entity newsupernovaRight(SpawnData data) {
        return Entities.builder()
                .type(GameType.SUPERNOVA)
                .from(data)
                .viewFromTexture("supernovaRight.gif")
                .bbox(new HitBox(BoundingShape.box(140, 94)))
                .with(new CollidableComponent(true))
                .with(new SupernovaRight())
                .build();
    }
    @Spawns("supernovaLeft")
    public Entity newsupernovaLeft(SpawnData data) {
        return Entities.builder()
                .type(GameType.SUPERNOVA)
                .from(data)
                .viewFromTexture("supernovaRight.gif")
                .bbox(new HitBox(BoundingShape.box(140, 94)))
                .with(new CollidableComponent(true))
                .with(new SupernovaLeft())
                .build();
    }
    @Spawns("supernovaUp")
    public Entity newsupernovaUp(SpawnData data) {
        return Entities.builder()
                .type(GameType.SUPERNOVA)
                .from(data)
                .viewFromTexture("supernovaUp.gif")
                .bbox(new HitBox(BoundingShape.box(91, 140)))
                .with(new CollidableComponent(true))
                .with(new SupernovaUp())
                .build();
    }
    @Spawns("supernovaDown")
    public Entity newsupernovaDown(SpawnData data) {
        return Entities.builder()
                .type(GameType.SUPERNOVA)
                .from(data)
                .viewFromTexture("supernovaDown.gif")
                .bbox(new HitBox(BoundingShape.box(91, 140)))
                .with(new CollidableComponent(true))
                .with(new SupernovaDown())
                .build();
    }
    @Spawns("supernova1")
    public Entity newsupernova1(SpawnData data) {
        return Entities.builder()
                .type(GameType.SUPERNOVA)
                .from(data)
                .viewFromTexture("supernova1.gif")
                .bbox(new HitBox(BoundingShape.box(126, 126)))
                .with(new CollidableComponent(true))
                .with(new Supernova1())
                .build();
    }
    @Spawns("supernova2")
    public Entity newsupernova2(SpawnData data) {
        return Entities.builder()
                .type(GameType.SUPERNOVA)
                .from(data)
                .viewFromTexture("supernova2.gif")
                .bbox(new HitBox(BoundingShape.box(126, 126)))
                .with(new CollidableComponent(true))
                .with(new Supernova2())
                .build();
    }
    @Spawns("supernova3")
    public Entity newsupernova3(SpawnData data) {
        return Entities.builder()
                .type(GameType.SUPERNOVA)
                .from(data)
                .viewFromTexture("supernova2.gif")
                .bbox(new HitBox(BoundingShape.box(126, 126)))
                .with(new CollidableComponent(true))
                .with(new Supernova3())
                .build();
    }
    @Spawns("supernova4")
    public Entity newsupernova4(SpawnData data) {
        return Entities.builder()
                .type(GameType.SUPERNOVA)
                .from(data)
                .viewFromTexture("supernova1.gif")
                .bbox(new HitBox(BoundingShape.box(126, 126)))
                .with(new CollidableComponent(true))
                .with(new Supernova4())
                .build();
    }


    // ------------------------------------- <<< [[[ Stages ]]] >>> -------------------------------------

    // ------------------------------------- <<< Base >>> -------------------------------------

    @Spawns("fall_base")
    public Entity newWater_base(SpawnData data) {
        return Entities.builder()
                .type(GameType.FALL_BASE)
                .from(data)
                .bbox(new HitBox(BoundingShape.box(data.<Integer>get("width"), data.<Integer>get("height"))))
                .with(new CollidableComponent(true))
                .build();
    }
    @Spawns("portal")
    public Entity newWPortal(SpawnData data) {
        return Entities.builder()
                .type(GameType.PORTAL)
                .from(data)
                .viewFromTexture("portal.gif")
                .bbox(new HitBox(BoundingShape.box(data.<Integer>get("width"), data.<Integer>get("height"))))
                .build();
    }
    @Spawns("Airship")
    public Entity newAirship(SpawnData data) {
        return Entities.builder()
                .type(GameType.AIRSHIP)
                .from(data)
                .viewFromTexture("airship.png")
                .bbox(new HitBox(BoundingShape.box(918, 552)))
                .with(new CollidableComponent(true))
                .with(new Airship())
                .build();
    }
    @Spawns("LevelComplete")
    public Entity newLevelComplete(SpawnData data) {
        return Entities.builder()
                .type(GameType.LEVEL_COMPLETE)
                .from(data)
                .viewFromTexture("LevelComplete.png")
                .bbox(new HitBox(BoundingShape.box(162, 98)))
                .build();
    }
    @Spawns("save_reminder")
    public Entity newSaveReminder(SpawnData data) {
        return Entities.builder()
                .type(GameType.SAVE_REMINDER)
                .from(data)
                .bbox(new HitBox(BoundingShape.box(data.<Integer>get("width"), data.<Integer>get("height"))))
                .with(new CollidableComponent(true))
                .build();
    }
    @Spawns("complete_tutorial")
    public Entity newCompleteTutorial(SpawnData data) {
        PhysicsComponent physics = new PhysicsComponent();
        physics.setBodyType(BodyType.STATIC);
        return Entities.builder()
                .type(GameType.COMPLETE_TUTORIAL)
                .from(data)
                .bbox(new HitBox(BoundingShape.box(data.<Integer>get("width"), data.<Integer>get("height"))))
                .with(new CollidableComponent(true))
                .with(physics)
                .build();
    }

    //Upgrade Skill Shop
    @Spawns("first_upgrade")
    public Entity newFirstUpgrade(SpawnData data) {
        return Entities.builder()
                .type(GameType.FIRST_UPGRADE)
                .from(data)
                .bbox(new HitBox(BoundingShape.box(data.<Integer>get("width"), data.<Integer>get("height"))))
                .with(new CollidableComponent(true))
                .build();
    }

    @Spawns("supernova_upgrade")
    public Entity newSupernovaUpgrade(SpawnData data) {
        return Entities.builder()
                .type(GameType.SUPERNOVA_UPGRADE)
                .from(data)
                .bbox(new HitBox(BoundingShape.box(data.<Integer>get("width"), data.<Integer>get("height"))))
                .with(new PhysicsComponent())
                .with(new CollidableComponent(true))
                .build();
    }
    @Spawns("flamestrike_upgrade")
    public Entity newFlamestrikeUpgrade(SpawnData data) {
        return Entities.builder()
                .type(GameType.FLAMESTRIKE_UPGRADE)
                .from(data)
                .bbox(new HitBox(BoundingShape.box(data.<Integer>get("width"), data.<Integer>get("height"))))
                .with(new PhysicsComponent())
                .with(new CollidableComponent(true))
                .build();
    }
    @Spawns("fireblast_upgrade")
    public Entity newFireblastUpgrade(SpawnData data) {
        return Entities.builder()
                .type(GameType.FIREBLAST_UPGRADE)
                .from(data)
                .bbox(new HitBox(BoundingShape.box(data.<Integer>get("width"), data.<Integer>get("height"))))
                .with(new PhysicsComponent())
                .with(new CollidableComponent(true))
                .build();
    }

    //Map Level Doors
    @Spawns("door_dive_tutorial")
    public Entity newDoor_DiveTutorial(SpawnData data) {
        return Entities.builder()
                .type(GameType.DOOR_DIVE_TUTORIAL)
                .from(data)
                .bbox(new HitBox(BoundingShape.box(data.<Integer>get("width"), data.<Integer>get("height"))))
                .with(new CollidableComponent(true)) //Door can collide
                .build();
    }
    @Spawns("door_dive_level_1x3")
    public Entity newDoor_DiveLevel_1x3(SpawnData data) {
        return Entities.builder()
                .type(GameType.DOOR_DIVE_LEVEL_1x3)
                .from(data)
                .bbox(new HitBox(BoundingShape.box(data.<Integer>get("width"), data.<Integer>get("height"))))
                .with(new CollidableComponent(true))
                .build();
    }
    @Spawns("door_dive_level_4x6")
    public Entity newDoor_DiveLevel_4x6(SpawnData data) {
        return Entities.builder()
                .type(GameType.DOOR_DIVE_LEVEL_4x6)
                .from(data)
                .bbox(new HitBox(BoundingShape.box(data.<Integer>get("width"), data.<Integer>get("height"))))
                .with(new CollidableComponent(true))
                .build();
    }
    @Spawns("door_dive_level_7x9")
    public Entity newDoor_DiveLevel_7x9(SpawnData data) {
        return Entities.builder()
                .type(GameType.DOOR_DIVE_LEVEL_7x9)
                .from(data)
                .bbox(new HitBox(BoundingShape.box(data.<Integer>get("width"), data.<Integer>get("height"))))
                .with(new CollidableComponent(true))
                .build();
    }
    @Spawns("door_dive_level_11x13")
    public Entity newDoor_DiveLevel_11x13(SpawnData data) {
        return Entities.builder()
                .type(GameType.DOOR_DIVE_LEVEL_11x13)
                .from(data)
                .bbox(new HitBox(BoundingShape.box(data.<Integer>get("width"), data.<Integer>get("height"))))
                .with(new CollidableComponent(true))
                .build();
    }
    @Spawns("door_dive_level_14x16")
    public Entity newDoor_DiveLevel_14x16(SpawnData data) {
        return Entities.builder()
                .type(GameType.DOOR_DIVE_LEVEL_14x16)
                .from(data)
                .bbox(new HitBox(BoundingShape.box(data.<Integer>get("width"), data.<Integer>get("height"))))
                .with(new CollidableComponent(true))
                .build();
    }
    @Spawns("door_dive_level_17x19")
    public Entity newDoor_DiveLevel_17x19(SpawnData data) {
        return Entities.builder()
                .type(GameType.DOOR_DIVE_LEVEL_17x19)
                .from(data)
                .bbox(new HitBox(BoundingShape.box(data.<Integer>get("width"), data.<Integer>get("height"))))
                .with(new CollidableComponent(true))
                .build();
    }

    @Spawns("door_dive_boss1")
    public Entity newDoor_DiveBoss1(SpawnData data) {
        return Entities.builder()
                .type(GameType.DOOR_DIVE_BOSS1)
                .from(data)
                .bbox(new HitBox(BoundingShape.box(data.<Integer>get("width"), data.<Integer>get("height"))))
                .with(new CollidableComponent(true)) //Door can collide
                .build();
    }
    @Spawns("door_boss1")
    public Entity newDoor_Boss1(SpawnData data) {
        return Entities.builder()
                .type(GameType.DOOR_BOSS1)
                .from(data)
                .bbox(new HitBox(BoundingShape.box(data.<Integer>get("width"), data.<Integer>get("height"))))
                .with(new CollidableComponent(true)) //Door can collide
                .build();
    }
    @Spawns("door_dive_boss2")
    public Entity newDoor_DiveBoss2(SpawnData data) {
        return Entities.builder()
                .type(GameType.DOOR_DIVE_BOSS2)
                .from(data)
                .bbox(new HitBox(BoundingShape.box(data.<Integer>get("width"), data.<Integer>get("height"))))
                .with(new CollidableComponent(true)) //Door can collide
                .build();
    }
    @Spawns("door_boss2")
    public Entity newDoor_Boss3(SpawnData data) {
        return Entities.builder()
                .type(GameType.DOOR_BOSS2)
                .from(data)
                .bbox(new HitBox(BoundingShape.box(data.<Integer>get("width"), data.<Integer>get("height"))))
                .with(new CollidableComponent(true)) //Door can collide
                .build();
    }
    @Spawns("door_dive_bossfinal")
    public Entity newDoor_DiveBossfinal(SpawnData data) {
        return Entities.builder()
                .type(GameType.DOOR_DIVE_BOSSFINAL)
                .from(data)
                .bbox(new HitBox(BoundingShape.box(data.<Integer>get("width"), data.<Integer>get("height"))))
                .with(new CollidableComponent(true)) //Door can collide
                .build();
    }
    @Spawns("door_bossfinal")
    public Entity newDoor_Bossfinal(SpawnData data) {
        return Entities.builder()
                .type(GameType.DOOR_BOSSFINAL)
                .from(data)
                .bbox(new HitBox(BoundingShape.box(data.<Integer>get("width"), data.<Integer>get("height"))))
                .with(new CollidableComponent(true)) //Door can collide
                .build();
    }

    // ------------------------------------- <<< Level Clear >>> -------------------------------------

    @Spawns("door_base")
    public Entity newDoor_Base(SpawnData data) {
        return Entities.builder()
                .type(GameType.DOOR_BASE)
                .from(data)
                .bbox(new HitBox(BoundingShape.box(data.<Integer>get("width"), data.<Integer>get("height"))))
                .with(new CollidableComponent(true))
                .build();
    }
    @Spawns("level_clear")
    public Entity newLevelClear(SpawnData data) {
        return Entities.builder()
                .type(GameType.LEVEL_CLEAR)
                .from(data)
                .bbox(new HitBox(BoundingShape.box(data.<Integer>get("width"), data.<Integer>get("height"))))
                .with(new CollidableComponent(true))
                .build();
    }

    // ------------------------------------- <<< Dive Tutorial >>> -------------------------------------

    @Spawns("door_tutorial")
    public Entity newDoor_Tutorial(SpawnData data) {
        return Entities.builder()
                .type(GameType.DOOR_TUTORIAL)
                .from(data)
                .bbox(new HitBox(BoundingShape.box(data.<Integer>get("width"), data.<Integer>get("height"))))
                .with(new CollidableComponent(true)) //Door can collide
                .build();
    }

    // ------------------------------------- <<< Tutorial >>> -------------------------------------
    @Spawns("door_tutorial2")
    public Entity newDoor_Tutorial2(SpawnData data) {
        return Entities.builder()
                .type(GameType.DOOR_TUTORIAL2)
                .from(data)
                .bbox(new HitBox(BoundingShape.box(data.<Integer>get("width"), data.<Integer>get("height"))))
                .with(new CollidableComponent(true)) //Door can collide
                .build();
    }

    @Spawns("fall")
    public Entity newFall_tutorial(SpawnData data) {
        return Entities.builder()
                .type(GameType.FALL)
                .from(data)
                .bbox(new HitBox(BoundingShape.box(data.<Integer>get("width"), data.<Integer>get("height"))))
                .with(new CollidableComponent(true)) //Trap can collide
                .build();
    }

    @Spawns("guide1")
    public Entity newGuide1(SpawnData data) {
        return Entities.builder()
                .type(GameType.GUIDE1)
                .from(data)
                .bbox(new HitBox(BoundingShape.box(data.<Integer>get("width"), data.<Integer>get("height"))))
                .with(new CollidableComponent(true))
                .build();
    }

    @Spawns("guide2")
    public Entity newGuide2(SpawnData data) {
        return Entities.builder()
                .type(GameType.GUIDE2)
                .from(data)
                .bbox(new HitBox(BoundingShape.box(data.<Integer>get("width"), data.<Integer>get("height"))))
                .with(new CollidableComponent(true))
                .build();
    }
    @Spawns("guide3")
    public Entity newGuide3(SpawnData data) {
        return Entities.builder()
                .type(GameType.GUIDE3)
                .from(data)
                .bbox(new HitBox(BoundingShape.box(data.<Integer>get("width"), data.<Integer>get("height"))))
                .with(new CollidableComponent(true))
                .build();
    }
    // ------------------------------------- <<< Tutorial 2 >>> -------------------------------------
    @Spawns("door_tutorial3")
    public Entity newDoor_Tutorial3(SpawnData data) {
        return Entities.builder()
                .type(GameType.DOOR_TUTORIAL3)
                .from(data)
                .bbox(new HitBox(BoundingShape.box(data.<Integer>get("width"), data.<Integer>get("height"))))
                .with(new CollidableComponent(true)) //Door can collide
                .build();
    }

    // ------------------------------------- <<< Dive Tutorial 3 >>> -------------------------------------
    @Spawns("door_dive_tutorial3")
    public Entity newDoor_DiveTutorial3(SpawnData data) {
        return Entities.builder()
                .type(GameType.DOOR_DIVE_TUTORIAL3)
                .from(data)
                .bbox(new HitBox(BoundingShape.box(data.<Integer>get("width"), data.<Integer>get("height"))))
                .with(new CollidableComponent(true)) //Door can collide
                .build();
    }
    // ------------------------------------- <<< Tutorial 3 >>> -------------------------------------
    @Spawns("BossPattern_DarkFlameMaster")
    public Entity newBossPattern_DarkFlameMaster(SpawnData data) {
        return Entities.builder()
                .type(GameType.BOSSPATTERN_DARKFLAMEMASTER)
                .from(data)
                .bbox(new HitBox(BoundingShape.box(data.<Integer>get("width"), data.<Integer>get("height"))))
                .with(new CollidableComponent(true))
                .build();
    }

    @Spawns("deathball")
    public Entity newDeathball(SpawnData data) {
        return Entities.builder()
                .type(GameType.DARKFLAMEMASTER_DEATHBALL)
                .from(data)
                .viewFromTexture("deathball.gif")
                .bbox(new HitBox(BoundingShape.box(140, 94)))
                .with(new CollidableComponent(true))
                .with(new DarkFlameMaster_Deathball())
                .build();
    }
    // ------------------------------------- <<< DIVE LEVEL: 1 - 3 >>> -------------------------------------

    @Spawns("door_levelclear_jungle")
    public Entity newDoor_LevelClear_Jungle(SpawnData data) {
        return Entities.builder()
                .type(GameType.DOOR_LEVELCLEAR_JUNGLE)
                .from(data)
                .bbox(new HitBox(BoundingShape.box(data.<Integer>get("width"), data.<Integer>get("height"))))
                .with(new CollidableComponent(true))
                .build();
    }

    // ------------------------------------- <<< DIVE LEVEL: 4 - 6 >>> -------------------------------------

    // ------------------------------------- <<< DIVE LEVEL: 7 - 9 >>> -------------------------------------

    // ------------------------------------- <<< DIVE LEVEL: 11 - 13 >>> -------------------------------------

    @Spawns("door_levelclear_cave")
    public Entity newDoor_LevelClear_Cave(SpawnData data) {
        return Entities.builder()
                .type(GameType.DOOR_LEVELCLEAR_CAVE)
                .from(data)
                .bbox(new HitBox(BoundingShape.box(data.<Integer>get("width"), data.<Integer>get("height"))))
                .with(new CollidableComponent(true))
                .build();
    }
    // ------------------------------------- <<< DIVE LEVEL: 14 - 16 >>> -------------------------------------

    // ------------------------------------- <<< DIVE LEVEL: 17 - 19 >>> -------------------------------------

    // ------------------------------------- <<< BOSS 1 >>> -------------------------------------
    @Spawns("rhatbuball")
    public Entity newRhatbu(SpawnData data) {
        return Entities.builder()
                .type(GameType.RHATBU_RHATBUBALL)
                .from(data)
                .viewFromTexture("rhatbuball.gif")
                .bbox(new HitBox(BoundingShape.box(80, 120)))
                .with(new CollidableComponent(true))
                .with(new Rhatbu_Rhatbuball())
                .build();
    }

    @Spawns("BossPattern_Rhatbu1")
    public Entity newBossPattern_Rhatbu1(SpawnData data) {
        return Entities.builder()
                .type(GameType.BOSSPATTERN_RHATBU1)
                .from(data)
                .bbox(new HitBox(BoundingShape.box(data.<Integer>get("width"), data.<Integer>get("height"))))
                .with(new CollidableComponent(true))
                .build();
    }
    @Spawns("BossPattern_Rhatbu2")
    public Entity newBossPattern_Rhatbu2(SpawnData data) {
        return Entities.builder()
                .type(GameType.BOSSPATTERN_RHATBU2)
                .from(data)
                .bbox(new HitBox(BoundingShape.box(data.<Integer>get("width"), data.<Integer>get("height"))))
                .with(new CollidableComponent(true))
                .build();
    }
    @Spawns("BossPattern_Rhatbu3")
    public Entity newBossPattern_Rhatbu3(SpawnData data) {
        return Entities.builder()
                .type(GameType.BOSSPATTERN_RHATBU3)
                .from(data)
                .bbox(new HitBox(BoundingShape.box(data.<Integer>get("width"), data.<Integer>get("height"))))
                .with(new CollidableComponent(true))
                .build();
    }
    @Spawns("BossPattern_Rhatbu4")
    public Entity newBossPattern_Rhatbu4(SpawnData data) {
        return Entities.builder()
                .type(GameType.BOSSPATTERN_RHATBU4)
                .from(data)
                .bbox(new HitBox(BoundingShape.box(data.<Integer>get("width"), data.<Integer>get("height"))))
                .with(new CollidableComponent(true))
                .build();
    }
    // ------------------------------------- <<< BOSS 2 >>> -------------------------------------

    @Spawns("fall")
    public Entity newwater_boss2(SpawnData data) {
        return Entities.builder()
                .type(GameType.FALL)
                .from(data)
                .bbox(new HitBox(BoundingShape.box(data.<Integer>get("width"), data.<Integer>get("height"))))
                .with(new CollidableComponent(true)) //Trap can collide
                .build();
    }
    @Spawns("waterball_bedj")
    public Entity newwaterball(SpawnData data) {
        PhysicsComponent physics = new PhysicsComponent();
        physics.setBodyType(BodyType.DYNAMIC);
        return Entities.builder()
                .type(GameType.BEDJ_WATERBALL)
                .from(data)
                .viewFromTexture("waterball.gif")
                .bbox(new HitBox(BoundingShape.box(105, 94)))
                .with(new CollidableComponent(true))
                .with(physics)
                .with(new Bedj_Waterball())
                .build();
    }
    @Spawns("BossPattern_Bedj1")
    public Entity newBossPattern_Bedj1(SpawnData data) {
        return Entities.builder()
                .type(GameType.BOSSPATTERN_BEDJ1)
                .from(data)
                .bbox(new HitBox(BoundingShape.box(data.<Integer>get("width"), data.<Integer>get("height"))))
                .with(new CollidableComponent(true))
                .build();
    }
    @Spawns("BossPattern_Bedj2")
    public Entity newBossPattern_Bedj2(SpawnData data) {
        return Entities.builder()
                .type(GameType.BOSSPATTERN_BEDJ2)
                .from(data)
                .bbox(new HitBox(BoundingShape.box(data.<Integer>get("width"), data.<Integer>get("height"))))
                .with(new CollidableComponent(true))
                .build();
    }
    @Spawns("BossPattern_Bedj3")
    public Entity newBossPattern_Bedj3(SpawnData data) {
        return Entities.builder()
                .type(GameType.BOSSPATTERN_BEDJ3)
                .from(data)
                .bbox(new HitBox(BoundingShape.box(data.<Integer>get("width"), data.<Integer>get("height"))))
                .with(new CollidableComponent(true))
                .build();
    }
    @Spawns("waterwall")
    public Entity newWaterwall(SpawnData data) {
        return Entities.builder()
                .type(GameType.BEDJ_WATERWALL)
                .from(data)
                .viewFromTexture("waterwall.gif")
                .bbox(new HitBox(BoundingShape.box(101, 150)))
                .with(new CollidableComponent(true))
                .with(new Bedj_Waterwall())
                .build();
    }
    // ------------------------------------- <<< Final Boss >>> -------------------------------------
    @Spawns("BossPattern_Grim1")
    public Entity newBossPattern_Grim1(SpawnData data) {
        return Entities.builder()
                .type(GameType.BOSSPATTERN_GRIM1)
                .from(data)
                .bbox(new HitBox(BoundingShape.box(data.<Integer>get("width"), data.<Integer>get("height"))))
                .with(new CollidableComponent(true))
                .build();
    }

    @Spawns("BossPattern_Grim2")
    public Entity newBossPattern_Grim2(SpawnData data) {
        return Entities.builder()
                .type(GameType.BOSSPATTERN_GRIM2)
                .from(data)
                .bbox(new HitBox(BoundingShape.box(data.<Integer>get("width"), data.<Integer>get("height"))))
                .with(new CollidableComponent(true))
                .build();
    }

    @Spawns("BossPattern_Grim3")
    public Entity newBossPattern_Grim3(SpawnData data) {
        return Entities.builder()
                .type(GameType.BOSSPATTERN_GRIM3)
                .from(data)
                .bbox(new HitBox(BoundingShape.box(data.<Integer>get("width"), data.<Integer>get("height"))))
                .with(new CollidableComponent(true))
                .build();
    }

    @Spawns("BossPattern_Grim4")
    public Entity newBossPattern_Grim4(SpawnData data) {
        return Entities.builder()
                .type(GameType.BOSSPATTERN_GRIM4)
                .from(data)
                .bbox(new HitBox(BoundingShape.box(data.<Integer>get("width"), data.<Integer>get("height"))))
                .with(new CollidableComponent(true))
                .build();
    }

    @Spawns("BossPattern_Grim5")
    public Entity newBossPattern_Grim5(SpawnData data) {
        return Entities.builder()
                .type(GameType.BOSSPATTERN_GRIM5)
                .from(data)
                .bbox(new HitBox(BoundingShape.box(data.<Integer>get("width"), data.<Integer>get("height"))))
                .with(new CollidableComponent(true))
                .build();
    }

    @Spawns("BossPattern_Grim6")
    public Entity newBossPattern_Grim6(SpawnData data) {
        return Entities.builder()
                .type(GameType.BOSSPATTERN_GRIM6)
                .from(data)
                .bbox(new HitBox(BoundingShape.box(data.<Integer>get("width"), data.<Integer>get("height"))))
                .with(new CollidableComponent(true))
                .build();
    }

    @Spawns("BossPattern_Grim7")
    public Entity newBossPattern_Grim7(SpawnData data) {
        return Entities.builder()
                .type(GameType.BOSSPATTERN_GRIM7)
                .from(data)
                .bbox(new HitBox(BoundingShape.box(data.<Integer>get("width"), data.<Integer>get("height"))))
                .with(new CollidableComponent(true))
                .build();
    }


    @Spawns("fireballfinal1")
    public Entity newFireballFinal1(SpawnData data) {
        return Entities.builder()
                .type(GameType.GRIM_FIREBALLFINAL1)
                .from(data)
                .viewFromTexture("fireballfinal1.gif")
                .bbox(new HitBox(BoundingShape.box(126, 192)))
                .with(new CollidableComponent(true))
                .with(new Grim_Fireballfinal1())
                .build();
    }

    @Spawns("fireballfinal2")
    public Entity newFireballFinal2(SpawnData data) {
        return Entities.builder()
                .type(GameType.GRIM_FIREBALLFINAL2)
                .from(data)
                .viewFromTexture("fireballfinal2.gif")
                .bbox(new HitBox(BoundingShape.box(80, 150)))
                .with(new CollidableComponent(true))
                .with(new Grim_Fireballfinal2())
                .build();
    }
    @Spawns("fireballfinal3")
    public Entity newFireballFinal3(SpawnData data) {
        return Entities.builder()
                .type(GameType.GRIM_FIREBALLFINAL2)
                .from(data)
                .viewFromTexture("fireballfinal2.gif")
                .bbox(new HitBox(BoundingShape.box(80, 150)))
                .with(new CollidableComponent(true))
                .with(new Grim_Fireballfinal3())
                .build();
    }

    @Spawns("fireballFinal")
    public Entity newfireballFinal(SpawnData data) {
        PhysicsComponent physics = new PhysicsComponent();
        physics.setBodyType(BodyType.DYNAMIC);
        return Entities.builder()
                .type(GameType.BOSS_GRIMFIREBALL)
                .from(data)
                .viewFromTexture("fireballfinal.gif")
                .bbox(new HitBox(BoundingShape.box(192, 126)))
                .with(new CollidableComponent(true))
                .with(physics)
                .with(new Grim_Fireball())
                .build();
    }

    @Spawns("lava_final")
    public Entity newlava_final(SpawnData data) {
        return Entities.builder()
                .type(GameType.LAVA_FINAL)
                .from(data)
                .bbox(new HitBox(BoundingShape.box(data.<Integer>get("width"), data.<Integer>get("height"))))
                .with(new CollidableComponent(true)) //Trap can collide
                .build();
    }
}


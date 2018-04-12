package pacmansuper.game;

import com.almasb.fxgl.app.FXGL;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.physics.*;
import com.almasb.fxgl.scene.FXGLMenu;
import com.almasb.fxgl.scene.SceneFactory;
import com.almasb.fxgl.scene.menu.MenuType;
import com.almasb.fxgl.settings.MenuItem;
import com.almasb.fxgl.time.LocalTimer;
import com.almasb.fxgl.settings.GameSettings;
import com.almasb.fxgl.texture.Texture;
import com.almasb.fxgl.io.serialization.Bundle;
import com.almasb.fxgl.saving.DataFile;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;
import org.jetbrains.annotations.NotNull;
import pacmansuper.game.CollectableObjects.HealthBoost;

import java.io.Serializable;
import java.util.EnumSet;
import java.util.Map;

import static com.almasb.fxgl.app.DSLKt.*;

public class GameMain extends GameApplication {

    // ------------------------------------- <<< Data >>> -------------------------------------

    //Player
    private Entity player;
    private int Health;
    private int Gold;

    private int Fireball;
    private int Fireblast;
    private int Flamestrike;
    private int Supernova;

    //Enemy
    private int EnemyHealth;

    //Boss
    private int DarkFlameMasterHealth;
    private int RhatbuHealth;
    private int BedjHealth;
    private int GrimHealth;

    //Skills
    private boolean canMove = false;
    private boolean canFly = true;
    private boolean directionRight = true;
    private boolean directionLeft = false;
    private boolean canShootValue = true;
    private boolean canFireblastValue = true;
    private boolean canFlamestrikeValue = true;
    private boolean canSupernovaValue = true;


    //Other
    private LocalTimer returnTimer;
    private LocalTimer finalBossTimer;

    // ------------------------------------- <<< Game Settings >>> -------------------------------------
    @Override
    protected void initSettings(GameSettings settings) {

        settings.setTitle("Pacman Super");
        settings.setVersion("0.0.3");
        settings.setWidth(1280);
        settings.setHeight(770); //770
        settings.setProfilingEnabled(false); //Profile
        settings.setIntroEnabled(false);
        settings.setCloseConfirmation(false);
        settings.setMenuEnabled(true); //Menu

        settings.setEnabledMenuItems(EnumSet.allOf(MenuItem.class));
        settings.setSceneFactory(new GameMenu());
    }

    // ------------------------------------- <<< Save & Load >>> -------------------------------------
    @Override
    public DataFile saveState() {

        //Player Stats
        Serializable gold = getGameState().getInt("Gold") ;

        return new DataFile(gold);
    }

    @Override
    public void loadState(DataFile dataFile) {

        //Load Data
        Serializable gold = dataFile.getData();

        //Player Stats
        int gp = (int) gold;

        //Implement Load Data
        getGameState().setValue("Gold", + gp);

        //Init Game
        initGame();
        
    }
    // ------------------------------------- <<< Game Menu >>> -------------------------------------
    public static class GameMenu extends SceneFactory {

        //Main Menu
        @NotNull
        @Override
        public FXGLMenu newMainMenu(@NotNull GameApplication app){
            return new Menu(app, MenuType.MAIN_MENU) {
            };
        }
        // Pause Menu
        @NotNull
        @Override
        public FXGLMenu newGameMenu(@NotNull GameApplication app) {
            return new Menu(app, MenuType.GAME_MENU) {
            };
        }
    }

    // ------------------------------------- <<< Game Initialize >>> -------------------------------------
    @Override
    protected void initGame() {

        //Initialize Map
        getMasterTimer().clear();
        getMasterTimer().runOnceAfter(() -> {
            getAudioPlayer().stopAllMusic();
        }, Duration.seconds(0));
        getMasterTimer().runOnceAfter(() -> {
            getAudioPlayer().loopBGM("BackgroundMusic.mp3");
        }, Duration.seconds(0.1));

        getGameWorld().setLevelFromMap("base.json");

        if (getGameState().getInt("Gold") > 0) {
            //Message
            String message = "Welcome Back!";
            FXGL.getNotificationService().pushNotification(message);
        } else {
            getMasterTimer().runOnceAfter(() -> {
                getDisplay().showMessageBox("Use the 'Arrow Keys' To Move Around");
                getDisplay().showMessageBox("Head to the Tutorial to begin your Adventure...");
                getDisplay().showMessageBox("Welcome to Pacman Super!");
            }, Duration.seconds(1));
        }

        //Initialize Player
        if (getGameState().getInt("Gold") > 0) {
            player = getGameWorld().spawn("player", 850, 410);
        } else {
            player = getGameWorld().spawn("player", 2880, 420);
        }

        //Skill Charge
        canMove = true;
        canFly = true;
        HealthCharge();
        FireballCharge();
        FireblastCharge();
        FlamestrikeCharge();
        SupernovaCharge();

        //Clouds
        getGameWorld().spawn("cloud", 2900, 500);
        getGameWorld().spawn("cloud", 500, 100);
        getGameWorld().spawn("cloud", 2630, 100);
        getGameWorld().spawn("cloud", 5600, 100);
        getGameWorld().spawn("cloud", 4150, 100);

        //Camera Settings
        getGameScene().getViewport().setBounds(0, 0, 11900, 770);
        getGameScene().getViewport().bindToEntity(player, getWidth() / 2, getHeight() / 2);
    }

    protected void initDiveTutorial() {

        //Initialize Map
        getMasterTimer().clear();
        getGameWorld().setLevelFromMap("dive_tutorial.json");

        //Initialize Player
        player = getGameWorld().spawn("player", 700, 300);

        //Skill Charge
        canMove = true;
        canFly = true;
        HealthCharge();
        FireballCharge();
        FireblastCharge();
        FlamestrikeCharge();
        SupernovaCharge();

        //Camera Settings
        getGameScene().getViewport().setBounds(0, 0, 2800, 4900);
        getGameScene().getViewport().bindToEntity(player, getWidth() / 2, getHeight() / 2);
    }

    protected void initTutorial() {

        //Initialize Map
        getMasterTimer().clear();
        getGameWorld().setLevelFromMap("tutorial.json");
        getGameState().increment("Health", +500);
        getGameState().increment("Energy", +500);

        //Initialize Player
        player = getGameWorld().spawn("player", 200, 0);

        //Skill Charge
        canMove = true;
        canFly = true;
        HealthCharge();
        FireballCharge();
        FireblastCharge();
        FlamestrikeCharge();
        SupernovaCharge();

        //Enemies
        getGameWorld().spawn("enemy_red", 4020, 535);
        getGameWorld().spawn("enemy_blue", 2440, 180);

        //Clouds
        getGameWorld().spawn("cloud", 2000, 30);
        getGameWorld().spawn("cloud", 100, 100);
        getGameWorld().spawn("cloud", 3600, 200);

        //Camera Settings
        getGameScene().getViewport().setBounds(0, 0, 5040, 770);
        getGameScene().getViewport().bindToEntity(player, getWidth() / 2, getHeight() / 2);
    }

    protected void initTutoria2() {

        //Initialize Map
        getMasterTimer().clear();
        getGameWorld().setLevelFromMap("tutorial2.json");

        //Initialize Player
        player = getGameWorld().spawn("player", 630, 180);

        //Skill Charge
        canMove = true;
        canFly = true;
        HealthCharge();
        FireballCharge();
        FireblastCharge();
        FlamestrikeCharge();
        SupernovaCharge();

        //Enemies
        getGameWorld().spawn("enemy_red", 550, 500);
        getGameWorld().spawn("enemy_red", 2400, 140);
        getGameWorld().spawn("enemy_red", 4380, 270);
        getGameWorld().spawn("enemy_blue", 1570, 640);

        //Clouds
        getGameWorld().spawn("cloud", 772, 250);
        getGameWorld().spawn("cloud", 1700, 180);
        getGameWorld().spawn("cloud", 2300, 550);
        getGameWorld().spawn("cloud", 4400, 60);

        //Camera Settings
        getGameScene().getViewport().setBounds(0, 0, 5600, 770);
        getGameScene().getViewport().bindToEntity(player, getWidth() / 2, getHeight() / 2);
    }

    protected void initDiveTutorial3() {

        //Initialize Map
        getMasterTimer().clear();
        getGameWorld().setLevelFromMap("dive_tutorial3.json");

        //Initialize Player
        player = getGameWorld().spawn("player", 280, 100);

        //Skill Charge
        canMove = true;
        canFly = true;
        HealthCharge();
        FireballCharge();
        FireblastCharge();
        FlamestrikeCharge();
        SupernovaCharge();

        //Camera Settings
        getGameScene().getViewport().setBounds(0, 0, 1400, 4900);
        getGameScene().getViewport().bindToEntity(player, getWidth() / 2, getHeight() / 2);
    }

    protected void initTutoria3() {

        //Initialize Map
        getMasterTimer().clear();
        getMasterTimer().runOnceAfter(() -> {
            getAudioPlayer().stopAllMusic();
        }, Duration.seconds(0));
        getMasterTimer().runOnceAfter(() -> {
            getAudioPlayer().loopBGM("Bossfight.mp3");
        }, Duration.seconds(0.1));

        getGameWorld().setLevelFromMap("tutorial3.json");

        getMasterTimer().runOnceAfter(() -> {
            String message4 = "Defeat The Dark Flame Master!";
            FXGL.getNotificationService().pushNotification(message4);
        }, Duration.seconds(1));

        //Initialize Player
        player = getGameWorld().spawn("player", 1890, 0);

        //Skill Charge
        canMove = true;
        canFly = true;
        HealthCharge();
        FireballCharge();
        FireblastCharge();
        FlamestrikeCharge();
        SupernovaCharge();

        //Enemies
        getGameWorld().spawn("boss_darkflamemaster", 770, 440);

        //Camera Settings
        getGameScene().getViewport().setBounds(0, 0, 2100, getHeight());
        getGameScene().getViewport().bindToEntity(player, getWidth() / 2, getHeight() / 2);
    }
    protected void initDiveBoss1() {

        //Initialize Map
        getMasterTimer().clear();
        getGameWorld().setLevelFromMap("dive_boss1.json");

        //Initialize Player
        player = getGameWorld().spawn("player", 700, 300);

        //Skill Charge
        canMove = true;
        canFly = true;
        HealthCharge();
        FireballCharge();
        FireblastCharge();
        FlamestrikeCharge();
        SupernovaCharge();

        //Camera Settings
        getGameScene().getViewport().setBounds(0, 0, 2800, 4900);
        getGameScene().getViewport().bindToEntity(player, getWidth() / 2, getHeight() / 2);
    }

    protected void initBoss1() {

        //Initialize Map
        getMasterTimer().clear();
        getMasterTimer().runOnceAfter(() -> {
            getAudioPlayer().stopAllMusic();
        }, Duration.seconds(0));
        getMasterTimer().runOnceAfter(() -> {
            getAudioPlayer().loopBGM("Bossfight.mp3");
        }, Duration.seconds(0.1));

        getGameWorld().setLevelFromMap("boss_1.json");

        //Message
        String message = "Defeat Rhatbu!";
        FXGL.getNotificationService().pushNotification(message);

        //Initialize Player
        player = getGameWorld().spawn("player", 200, 0);

        //Skill Charge
        canMove = true;
        canFly = true;
        HealthCharge();
        FireballCharge();
        FireblastCharge();
        FlamestrikeCharge();
        SupernovaCharge();

        //Enemies
        getGameWorld().spawn("boss_rhatbu", 3500, 300);
        getGameWorld().spawn("enemy_blue", 1180, 530);
        getGameWorld().spawn("enemy_blue", 1470, 530);

        //Camera Settings
        getGameScene().getViewport().setBounds(0, 0, 4200, 770);
        getGameScene().getViewport().bindToEntity(player, getWidth() / 2, getHeight() / 2);
    }

    protected void initDiveBoss2() {

        //Initialize Map
        getMasterTimer().clear();
        getGameWorld().setLevelFromMap("dive_boss2.json");

        //Initialize Player
        player = getGameWorld().spawn("player", 700, 300);

        //Skill Charge
        canMove = true;
        canFly = true;
        HealthCharge();
        FireballCharge();
        FireblastCharge();
        FlamestrikeCharge();
        SupernovaCharge();

        //Camera Settings
        getGameScene().getViewport().setBounds(0, 0, 2800, 4900);
        getGameScene().getViewport().bindToEntity(player, getWidth() / 2, getHeight() / 2);
    }

    protected void initBoss2() {

        //Initialize Map
        getMasterTimer().clear();
        getMasterTimer().runOnceAfter(() -> {
            getAudioPlayer().stopAllMusic();
        }, Duration.seconds(0));
        getMasterTimer().runOnceAfter(() -> {
            getAudioPlayer().loopBGM("Bossfight.mp3");
        }, Duration.seconds(0.1));

        getGameWorld().setLevelFromMap("boss_2.json");

        //Message
        String message = "Defeat Bedj!";
        FXGL.getNotificationService().pushNotification(message);

        //Initialize Player
        player = getGameWorld().spawn("player", 190, 0);

        //Skill Charge
        canMove = true;
        canFly = true;
        HealthCharge();
        FireballCharge();
        FireblastCharge();
        FlamestrikeCharge();
        SupernovaCharge();

        //Enemies
        getGameWorld().spawn("boss_bedj", 1880, 370);

        //Camera Settings
        getGameScene().getViewport().setBounds(0, 0, 2800, 770);
        getGameScene().getViewport().bindToEntity(player, getWidth() / 2, getHeight() / 2);
    }

    protected void initDiveBossFinal() {

        //Initialize Map
        getMasterTimer().clear();
        getGameWorld().setLevelFromMap("dive_boss3.json");

        //Initialize Player
        player = getGameWorld().spawn("player", 700, 300);

        //Skill Charge
        canMove = true;
        canFly = true;
        HealthCharge();
        FireballCharge();
        FireblastCharge();
        FlamestrikeCharge();
        SupernovaCharge();

        //Camera Settings
        getGameScene().getViewport().setBounds(0, 0, 2800, 4900);
        getGameScene().getViewport().bindToEntity(player, getWidth() / 2, getHeight() / 2);
    }


    protected void initBossFinal() {

        //Initialize Map
        getMasterTimer().clear();
        getMasterTimer().runOnceAfter(() -> {
            getAudioPlayer().stopAllMusic();
        }, Duration.seconds(0));
        getMasterTimer().runOnceAfter(() -> {
            getAudioPlayer().loopBGM("Bossfight.mp3");
        }, Duration.seconds(0.1));

        getGameWorld().setLevelFromMap("boss_final.json");

        //Message
        String message = "Defeat Grim!";
        FXGL.getNotificationService().pushNotification(message);

        //Initialize Player
        player = getGameWorld().spawn("player", 540, 0);

        //Skill Charge
        canMove = true;
        canFly = true;
        HealthCharge();
        FireballCharge();
        FireblastCharge();
        FlamestrikeCharge();
        SupernovaCharge();

        //Enemies
        getGameWorld().spawn("boss_grim", 2660, 280);
        //getGameWorld().spawn("fireballFinal", 1800, 500);
        //getGameWorld().spawn("fireballFinal", 700, 500);

        //Camera Settings
        getGameScene().getViewport().setBounds(0, 0, 3850, 770);
        getGameScene().getViewport().bindToEntity(player, getWidth() / 2, getHeight() / 2);
    }

    // ------------------------------------- <<< Inputs >>> -------------------------------------
    @Override
    protected void initInput() {

        //Player Movement Input
        getInput().addAction(new UserAction("Right") {
            @Override
            protected void onAction() {
                if (canMove) {
                    player.getControl(Player.class).right();
                    directionRight = true;
                    directionLeft = false;
                } else {
                    player.getControl(Player.class).cantfly();
                }
            }
        }, KeyCode.RIGHT);

        getInput().addAction(new UserAction("Left") {
            @Override
            protected void onAction() {
                if (canMove) {
                    player.getControl(Player.class).left();
                    directionRight = false;
                    directionLeft = true;
                } else {
                    player.getControl(Player.class).cantfly();
                }
            }
        }, KeyCode.LEFT);

        getInput().addAction(new UserAction("Fly") {
            @Override
            protected void onAction() {
                if (canFly) {
                    player.getControl(Player.class).fly();
                } else {
                    player.getControl(Player.class).cantfly();
                }
            }
        }, KeyCode.UP);

        getInput().addAction(new UserAction("Descend") {
            @Override
            protected void onAction() {
                if (canFly) {
                    player.getControl(Player.class).descend();
                } else {
                    player.getControl(Player.class).cantfly();
                }
            }
        }, KeyCode.DOWN);

        getInput().addAction(new UserAction("Cheat") {
            @Override
            protected void onAction() {
                getGameState().increment("Health", 15000);
                getGameState().increment("Gold", 9999);
                getDisplay().showMessageBox("Cheats Activated!");
            }
        }, KeyCode.BACK_SPACE);

        // ---- Skill Input ----
        getInput().addAction(new UserAction("Fireball") {
            @Override
            protected void onAction() {
                canShoot();
                if(getGameState().getInt("Fireball") == 100 && directionRight) {
                    fireballRight();
                    getGameState().setValue("Fireball", 0);
                } else if (getGameState().getInt("Fireball") == 100 && directionLeft) {
                    fireballLeft();
                    getGameState().setValue("Fireball", 0);
                }
            }
        }, KeyCode.SPACE);

        getInput().addAction(new UserAction("Fireblast") {
            @Override
            protected void onAction() {
                canFireblast();
                if(getGameState().getInt("Fireblast") == 100 && directionRight) {
                    FireblasRight();
                    getGameState().setValue("Fireblast", 0);
                    getGameScene().getViewport().shakeRotational(0.8);
                } else if (getGameState().getInt("Fireblast") == 100 && directionLeft) {
                    FireblasLeft();
                    getGameState().setValue("Fireblast", 0);
                    getGameScene().getViewport().shakeRotational(0.6);
                }
            }
        }, KeyCode.D);

        getInput().addAction(new UserAction("Flamestrike") {
            @Override
            protected void onAction() {
                canFlamestrike();
                Flamestrike();
                if(getGameState().getInt("Flamestrike") == 100) {
                    getGameState().setValue("Flamestrike", 0);
                    getGameScene().getViewport().shakeRotational(0.6);
                }
            }
        }, KeyCode.S);

        getInput().addAction(new UserAction("Supernova") {
            @Override
            protected void onAction() {
                canSupernova();
                Supernova();
                if(getGameState().getInt("Supernova") == 100) {
                    getGameState().setValue("Supernova", 0);
                    getGameScene().getViewport().shakeRotational(0.6);
                }
            }
        }, KeyCode.A);
    }

    // ------------------------------------- <<< Skills >>> -------------------------------------

    //Player Health
    private void HealthCharge() {
        getMasterTimer().runAtInterval(() -> {
            getGameState().increment("Health", +1);
            if(getGameState().getInt("Health") > 100) {
                getGameState().setValue("Health", 100);
            }
        }, Duration.seconds(0.2));
    }

    //Fireball
    private void canShoot() {
        if(getGameState().getInt("Fireball") != 100) {
            canShootValue = false;
            getAudioPlayer().playSound("NoSound.wav");
        } else {
            canShootValue = true;
        }
    }
    public void FireballCharge() {
        getMasterTimer().runAtInterval(() -> {
            getGameState().increment("Fireball", +50);
            if(getGameState().getInt("Fireball") > 100) {
                getGameState().setValue("Fireball", 100);
            }
        }, Duration.seconds(0.1));
    }
    public void fireballRight() {
        if (canShootValue) {
            spawn("fireballRight", player.getPosition());
            getAudioPlayer().playSound("fireball.wav");
        }
    }
    public void fireballLeft() {
        if (canShootValue) {
            spawn("fireballLeft", player.getPosition());
            getAudioPlayer().playSound("fireball.wav");
        }
    }

    //Fireblast
    private void canFireblast() {
        if(getGameState().getInt("Fireblast") != 100) {
            canFireblastValue = false;
            getAudioPlayer().playSound("NoSound.wav");
        } else {
            canFireblastValue = true;
        }
    }
    public void FireblastCharge() {
        getMasterTimer().runAtInterval(() -> {
            getGameState().increment("Fireblast", +6);
            if(getGameState().getInt("Fireblast") > 100) {
                getGameState().setValue("Fireblast", 100);
            }
        }, Duration.seconds(0.2));
    }
    public void FireblasRight() {
        if (canFireblastValue) {
            spawn("fireblastRight", player.getPosition());
            getAudioPlayer().playSound("Supernova1.wav");
        }
    }
    public void FireblasLeft() {
        if (canFireblastValue) {
            spawn("fireblastLeft", player.getPosition());
            getAudioPlayer().playSound("Supernova1.wav");
        }
    }

    //Flamestrike
    private void canFlamestrike() {
        if(getGameState().getInt("Flamestrike") != 100) {
            canFlamestrikeValue = false;
            getAudioPlayer().playSound("NoSound.wav");
        } else {
            canFlamestrikeValue = true;
        }
    }
    public void FlamestrikeCharge() {
        getMasterTimer().runAtInterval(() -> {
            getGameState().increment("Flamestrike", +4);
            if(getGameState().getInt("Flamestrike") > 100) {
                getGameState().setValue("Flamestrike", 100);
            }
        }, Duration.seconds(0.2));
    }

    public void Flamestrike() {
        if (canFlamestrikeValue) {
            spawn("flamestrikeRight", player.getPosition());
            spawn("flamestrikeLeft", player.getPosition());
            getAudioPlayer().playSound("Supernova1.wav");
        }
    }

    //Supernova
    private void canSupernova() {
        if(getGameState().getInt("Supernova") != 100) {
            canSupernovaValue = false;
            getAudioPlayer().playSound("NoSound.wav");
        } else {
            canSupernovaValue = true;
        }
    }
    public void SupernovaCharge() {
        getMasterTimer().runAtInterval(() -> {
            getGameState().increment("Supernova", +2);
            if (getGameState().getInt("Supernova") > 100) {
                getGameState().setValue("Supernova", 100);
            }
        }, Duration.seconds(0.2));
    }

    public void Supernova() {
        if (canSupernovaValue) {
            getAudioPlayer().playSound("Supernova1.wav");
            spawn("supernovaRight", player.getPosition());
            spawn("supernovaLeft", player.getPosition());
            spawn("supernovaUp", player.getPosition());
            spawn("supernovaDown", player.getPosition());
            spawn("supernova1", player.getPosition());
            spawn("supernova2", player.getPosition());
            spawn("supernova3", player.getPosition());
            spawn("supernova4", player.getPosition());
        }
    }


    // ------------------------------------- <<< Physics >>> -------------------------------------
    @Override
    protected void initPhysics() {

        // ----- PlAYER COLLISIONS COMBAT -----

        //PLAYER & ENEMY
        getPhysicsWorld().addCollisionHandler(new CollisionHandler(GameType.PLAYER, GameType.ENEMY) {

            @Override
            protected void onCollisionBegin(Entity player, Entity enemy) {


                //Reduce Player Health
                getGameState().increment("Health", -50);
                getAudioPlayer().playSound("PlayerHealth.wav");
                getGameScene().getViewport().shakeRotational(0.6);

                //Check Player Health
                if (getGameState().getInt("Health") <= 0) {
                    getDisplay().showMessageBox("Game Over", () -> {
                        exit();
                    });
                }
            }
        });

        //  FIREBALL & ENEMY
        getPhysicsWorld().addCollisionHandler(new CollisionHandler(GameType.FIREBALL, GameType.ENEMY) {

            @Override
            protected void onCollisionBegin(Entity fireball, Entity enemy) {

                //Kill Enemy
                getAudioPlayer().playSound("Hit_Collide.wav");
                getGameState().increment("EnemyHealth", -100);
                fireball.removeFromWorld();

                if (getGameState().getInt("EnemyHealth") <= 0) {
                    getAudioPlayer().playSound("enemyDeath.wav");
                    getGameState().increment("Gold", +20);
                    getGameState().setValue("EnemyHealth", 300);
                    fireball.removeFromWorld();
                    enemy.removeFromWorld();
                }
            }
        });
        //FIREBALL & PLATFORM
        getPhysicsWorld().addCollisionHandler(new CollisionHandler(GameType.FIREBALL, GameType.PLATFORM) {

            @Override
            protected void onCollisionBegin(Entity fireball, Entity platform) {

                //Remove Bullet
                fireball.removeFromWorld();
            }
        });
        //FIREBLAST REMOVE
        getPhysicsWorld().addCollisionHandler(new CollisionHandler(GameType.FIREBLAST, GameType.PLAYER) {

            @Override
            protected void onCollisionBegin(Entity fireblast, Entity platform) {
                getMasterTimer().runOnceAfter(() -> {
                    fireblast.removeFromWorld();
                }, Duration.seconds(1));
            }
        });

        //FLAMESTRIKE REMOVE
        getPhysicsWorld().addCollisionHandler(new CollisionHandler(GameType.FLAMESTRIKE, GameType.PLAYER) {

            @Override
            protected void onCollisionBegin(Entity flamestrike, Entity platform) {

                getMasterTimer().runOnceAfter(() -> {
                    flamestrike.removeFromWorld();
                }, Duration.seconds(1));
            }
        });

        //SUPERNOVA REMOVE
        getPhysicsWorld().addCollisionHandler(new CollisionHandler(GameType.SUPERNOVA, GameType.PLAYER) {

            @Override
            protected void onCollisionBegin(Entity supernova, Entity platform) {

                getMasterTimer().runOnceAfter(() -> {
                    supernova.removeFromWorld();
                }, Duration.seconds(1));
            }
        });
        //  FIREBLAST & ENEMY
        getPhysicsWorld().addCollisionHandler(new CollisionHandler(GameType.FIREBLAST, GameType.ENEMY) {

            @Override
            protected void onCollisionBegin(Entity fireblast, Entity enemy) {

                //Kill Enemy
                getAudioPlayer().playSound("Hit_Collide.wav");
                getGameState().increment("EnemyHealth", -250);

                if (getGameState().getInt("EnemyHealth") <= 0) {
                    getAudioPlayer().playSound("enemyDeath.wav");
                    getGameState().increment("Gold", +25);
                    getGameState().setValue("EnemyHealth", 300);
                    enemy.removeFromWorld();
                }
            }
        });

        //  FLAMESTRIKE & ENEMY
        getPhysicsWorld().addCollisionHandler(new CollisionHandler(GameType.FLAMESTRIKE, GameType.ENEMY) {

            @Override
            protected void onCollisionBegin(Entity flamestrike, Entity enemy) {

                //Kill Enemy
                getAudioPlayer().playSound("Hit_Collide.wav");
                getGameState().increment("EnemyHealth", -250);

                if (getGameState().getInt("EnemyHealth") <= 0) {
                    getAudioPlayer().playSound("enemyDeath.wav");
                    getGameState().increment("Gold", +25);
                    getGameState().setValue("EnemyHealth", 300);
                    enemy.removeFromWorld();
                }
            }
        });

        //  SUPERNOVA & ENEMY
        getPhysicsWorld().addCollisionHandler(new CollisionHandler(GameType.SUPERNOVA, GameType.ENEMY) {

            @Override
            protected void onCollisionBegin(Entity supernova, Entity enemy) {

                //Kill Enemy
                getAudioPlayer().playSound("Hit_Collide.wav");
                getGameState().increment("EnemyHealth", -150);

                if (getGameState().getInt("EnemyHealth") <= 0) {
                    getAudioPlayer().playSound("enemyDeath.wav");
                    getGameState().increment("Gold", +25);
                    getGameState().setValue("EnemyHealth", 300);
                    enemy.removeFromWorld();
                }
            }
        });

        // ----- PlAYER & BOSS -----

        //Dark Flame Master Battle Pattern
        getPhysicsWorld().addCollisionHandler(new CollisionHandler(GameType.PLAYER, GameType.BOSSPATTERN_DARKFLAMEMASTER) {

            @Override
            protected void onCollisionBegin(Entity fireblast, Entity BossPattern_DarkFlameMaster) {

                //Boss Attack Pattern
                if (getGameState().getInt("DarkFlameMasterHealth") <= 4000) {
                    getMasterTimer().runOnceAfter(() -> {
                        getGameWorld().spawn("deathball", 2100, 500);
                    }, Duration.seconds(0));
                }
            }
        });

        //Dark Flame Master & Fireball
        getPhysicsWorld().addCollisionHandler(new CollisionHandler(GameType.FIREBALL, GameType.BOSS_DARKFLAMEMASTER) {

            @Override
            protected void onCollisionBegin(Entity fireball, Entity boss_darkflamemaster) {

                //Kill Dark Flame Master
                getAudioPlayer().playSound("Hit_Collide.wav");
                getGameState().increment("DarkFlameMasterHealth", -100);
                fireball.removeFromWorld();

                if (getGameState().getInt("DarkFlameMasterHealth") <= 0) {

                    getAudioPlayer().playSound("enemyDeath.wav");
                    fireball.removeFromWorld();
                    boss_darkflamemaster.removeFromWorld();
                    getGameState().setValue("DarkFlameMasterHealth", 9000);

                    //Message
                    String messageDarkFlameMasterDefeated1 = "Dark Flame Master Defeated!";
                    String messageDarkFlameMasterDefeated2 = "Returning back to base in 5 seconds...";
                    FXGL.getNotificationService().pushNotification(messageDarkFlameMasterDefeated1);
                    FXGL.getNotificationService().pushNotification(messageDarkFlameMasterDefeated2);

                    getMasterTimer().runOnceAfter(() -> {
                        getAudioPlayer().stopAllMusic();
                    }, Duration.seconds(8.9));

                    getMasterTimer().runOnceAfter(() -> {

                        getDisplay().showMessageBox("Also, don't forget to visit the Shop once in a while!");
                        getDisplay().showMessageBox("Press 'Esc' to Pause the Game and Save your progress");
                        getDisplay().showMessageBox("Tutorial Complete!");
                        initGame();
                    }, Duration.seconds(9));

                }
            }
        });

        //Dark Flame Master & Fireblast
        getPhysicsWorld().addCollisionHandler(new CollisionHandler(GameType.FIREBLAST, GameType.BOSS_DARKFLAMEMASTER) {

            @Override
            protected void onCollisionBegin(Entity fireblast, Entity boss_darkflamemaster) {

                //Kill Dark Flame Master
                getAudioPlayer().playSound("Hit_Collide.wav");
                getGameState().increment("DarkFlameMasterHealth", -250);
            }
        });

        //Dark Flame Master & Flamestrike
        getPhysicsWorld().addCollisionHandler(new CollisionHandler(GameType.FLAMESTRIKE, GameType.BOSS_DARKFLAMEMASTER) {

            @Override
            protected void onCollisionBegin(Entity flamestrike, Entity boss_darkflamemaster) {

                //Kill Dark Flame Master
                getAudioPlayer().playSound("Hit_Collide.wav");
                getGameState().increment("DarkFlameMasterHealth", -250);
            }
        });

        //Dark Flame Master & Supernova
        getPhysicsWorld().addCollisionHandler(new CollisionHandler(GameType.SUPERNOVA, GameType.BOSS_DARKFLAMEMASTER) {

            @Override
            protected void onCollisionBegin(Entity supernova, Entity boss_darkflamemaster) {

                //Kill Dark Flame Master
                getAudioPlayer().playSound("Hit_Collide.wav");
                getGameState().increment("DarkFlameMasterHealth", -150);
            }
        });


        //Dark Flame Master & Player
        getPhysicsWorld().addCollisionHandler(new CollisionHandler(GameType.PLAYER, GameType.BOSS_DARKFLAMEMASTER) {

            @Override
            protected void onCollision(Entity player, Entity boss_darkflamemaster) {

                //Reduce Player Health
                getGameState().increment("Health", -1);
                getAudioPlayer().playSound("PlayerHealth.wav");

                //Check Player Health
                if (getGameState().getInt("Health") <= 0) {
                    getDisplay().showMessageBox("Game Over", () -> {
                        exit();
                    });
                }
            }
        });

        //Rhatbu Battle Pattern
        getPhysicsWorld().addCollisionHandler(new CollisionHandler(GameType.PLAYER, GameType.BOSSPATTERN_RHATBU1) {

            @Override
            protected void onCollisionBegin(Entity fireblast, Entity BossPattern_Rhatbu1) {

                //Boss Attack Pattern
                if (getGameState().getInt("RhatbuHealth") <= 7000) {
                    getMasterTimer().runOnceAfter(() -> {
                        getGameWorld().spawn("rhatbuball", 2780, 0);
                    }, Duration.seconds(0));
                }
            }
        });
        getPhysicsWorld().addCollisionHandler(new CollisionHandler(GameType.PLAYER, GameType.BOSSPATTERN_RHATBU2) {

            @Override
            protected void onCollisionBegin(Entity fireblast, Entity BossPattern_Rhatbu2) {

                //Boss Attack Pattern
                if (getGameState().getInt("RhatbuHealth") <= 7000) {
                    getMasterTimer().runOnceAfter(() -> {
                        getGameWorld().spawn("rhatbuball", 3180, 0);
                    }, Duration.seconds(0));
                }
            }
        });
        getPhysicsWorld().addCollisionHandler(new CollisionHandler(GameType.PLAYER, GameType.BOSSPATTERN_RHATBU3) {

            @Override
            protected void onCollisionBegin(Entity fireblast, Entity BossPattern_Rhatbu3) {

                //Boss Attack Pattern
                if (getGameState().getInt("RhatbuHealth") <= 7000) {
                    getMasterTimer().runOnceAfter(() -> {
                        getGameWorld().spawn("rhatbuball", 3580, 0);
                    }, Duration.seconds(0));
                }
            }
        });
        getPhysicsWorld().addCollisionHandler(new CollisionHandler(GameType.PLAYER, GameType.BOSSPATTERN_RHATBU4) {

            @Override
            protected void onCollisionBegin(Entity fireblast, Entity BossPattern_Rhatbu4) {

                //Boss Attack Pattern
                if (getGameState().getInt("RhatbuHealth") <= 7000) {
                    getMasterTimer().runOnceAfter(() -> {
                        getGameWorld().spawn("rhatbuball", 3970, 0);
                    }, Duration.seconds(0));
                }
            }
        });

        //Rhatbu & Fireball
        getPhysicsWorld().addCollisionHandler(new CollisionHandler(GameType.FIREBALL, GameType.BOSS_RHATBU) {

            @Override
            protected void onCollisionBegin(Entity fireball, Entity boss_rhatbu) {

                //Kill Rhatbu
                getAudioPlayer().playSound("Hit_Collide.wav");
                getGameState().increment("RhatbuHealth", -100);
                fireball.removeFromWorld();

                if (getGameState().getInt("RhatbuHealth") <= 0) {

                    getAudioPlayer().playSound("enemyDeath.wav");
                    fireball.removeFromWorld();
                    boss_rhatbu.removeFromWorld();
                    getGameState().increment("Gold", + 500);
                    getGameState().setValue("RhatbuHealth", 15000);

                    //Message
                    String messageRhatbuDefeated1 = "Rhatbu Defeated!";
                    String messageRhatbuDefeated2 = "Returning back to base in 5 seconds...";
                    FXGL.getNotificationService().pushNotification(messageRhatbuDefeated1);
                    FXGL.getNotificationService().pushNotification(messageRhatbuDefeated2);

                    getMasterTimer().runOnceAfter(() -> {
                        getAudioPlayer().stopAllMusic();
                    }, Duration.seconds(8.9));

                    getMasterTimer().runOnceAfter(() -> {
                        initGame();
                    }, Duration.seconds(9));

                }
            }
        });

        //Rhatbu & Fireblast
        getPhysicsWorld().addCollisionHandler(new CollisionHandler(GameType.FIREBLAST, GameType.BOSS_RHATBU) {

            @Override
            protected void onCollisionBegin(Entity fireblast, Entity boss_rhatbu) {

                //Kill Rhatbu
                getAudioPlayer().playSound("Hit_Collide.wav");
                getGameState().increment("RhatbuHealth", -250);
            }
        });

        //Rhatbu & Flamestrike
        getPhysicsWorld().addCollisionHandler(new CollisionHandler(GameType.FLAMESTRIKE, GameType.BOSS_RHATBU) {

            @Override
            protected void onCollisionBegin(Entity flamestrike, Entity boss_rhatbu) {

                //Kill Rhatbu
                getAudioPlayer().playSound("Hit_Collide.wav");
                getGameState().increment("RhatbuHealth", -250);
            }
        });

        //Rhatbu & Supernova
        getPhysicsWorld().addCollisionHandler(new CollisionHandler(GameType.SUPERNOVA, GameType.BOSS_RHATBU) {

            @Override
            protected void onCollisionBegin(Entity supernova, Entity boss_rhatbu) {

                //Kill Rhatbu
                getAudioPlayer().playSound("Hit_Collide.wav");
                getGameState().increment("RhatbuHealth", -150);
            }
        });

        //Rhatbu & Player
        getPhysicsWorld().addCollisionHandler(new CollisionHandler(GameType.PLAYER, GameType.BOSS_RHATBU) {

            @Override
            protected void onCollision(Entity player, Entity boss_rhatbu) {

                //Reduce Player Health
                getGameState().increment("Health", -1);
                getAudioPlayer().playSound("PlayerHealth.wav");

                //Check Player Health
                if (getGameState().getInt("Health") <= 0) {
                    getDisplay().showMessageBox("Game Over", () -> {
                        exit();
                    });
                }
            }
        });

        //Bedj Battle Pattern
        getPhysicsWorld().addCollisionHandler(new CollisionHandler(GameType.PLAYER, GameType.BOSSPATTERN_BEDJ1) {

            @Override
            protected void onCollisionBegin(Entity fireblast, Entity BossPattern_Bedj1) {

                //Boss Attack Pattern
                if (getGameState().getInt("BedjHealth") <= 15000) {

                    BossPattern_Bedj1.removeFromWorld();

                    getMasterTimer().runOnceAfter(() -> {
                        getGameWorld().spawn("waterball_bedj", 1600, 300);
                        getGameWorld().spawn("waterball_bedj", 2400, 300);
                    }, Duration.seconds(0));
                }
            }
        });

        getPhysicsWorld().addCollisionHandler(new CollisionHandler(GameType.PLAYER, GameType.BOSSPATTERN_BEDJ2) {

            @Override
            protected void onCollisionBegin(Entity fireblast, Entity BossPattern_Bedj2) {

                //Boss Attack Pattern
                if (getGameState().getInt("BedjHealth") <= 10000) {

                    getMasterTimer().runOnceAfter(() -> {
                        getGameWorld().spawn("waterwall", 2740, 140);
                    }, Duration.seconds(0));
                }
            }
        });
        getPhysicsWorld().addCollisionHandler(new CollisionHandler(GameType.PLAYER, GameType.BOSSPATTERN_BEDJ3) {

            @Override
            protected void onCollisionBegin(Entity fireblast, Entity BossPattern_Bedj3) {

                //Boss Attack Pattern
                if (getGameState().getInt("BedjHealth") <= 9000) {

                    getMasterTimer().runOnceAfter(() -> {
                        getGameWorld().spawn("waterwall", 2740, 415);
                    }, Duration.seconds(0));
                }
            }
        });

        //Bedj & Fireball
        getPhysicsWorld().addCollisionHandler(new CollisionHandler(GameType.FIREBALL, GameType.BOSS_BEDJ) {

            @Override
            protected void onCollisionBegin(Entity fireball, Entity boss_bedj) {

                //Kill Bedj
                getAudioPlayer().playSound("Hit_Collide.wav");
                getGameState().increment("BedjHealth", -100);
                fireball.removeFromWorld();

                if (getGameState().getInt("BedjHealth") <= 0) {

                    getAudioPlayer().playSound("enemyDeath.wav");
                    fireball.removeFromWorld();
                    boss_bedj.removeFromWorld();
                    getGameState().increment("Gold", + 1500);
                    getGameState().setValue("BedjHealth", 20000);

                    //Message
                    String messageBedjDefeated1 = "Bedj Defeated!";
                    String messageBedjDefeated2 = "Returning back to base in 5 seconds...";
                    FXGL.getNotificationService().pushNotification(messageBedjDefeated1);
                    FXGL.getNotificationService().pushNotification(messageBedjDefeated2);

                    getMasterTimer().runOnceAfter(() -> {
                        getAudioPlayer().stopAllMusic();
                    }, Duration.seconds(8.9));

                    getMasterTimer().runOnceAfter(() -> {
                        initGame();
                    }, Duration.seconds(9));

                }
            }
        });

        //Bedj & Fireblast
        getPhysicsWorld().addCollisionHandler(new CollisionHandler(GameType.FIREBLAST, GameType.BOSS_BEDJ) {

            @Override
            protected void onCollisionBegin(Entity fireblast, Entity boss_bedj) {

                //Kill Bedj
                getAudioPlayer().playSound("Hit_Collide.wav");
                getGameState().increment("BedjHealth", -250);
            }
        });

        //Bedj & Flamestrike
        getPhysicsWorld().addCollisionHandler(new CollisionHandler(GameType.FLAMESTRIKE, GameType.BOSS_BEDJ) {

            @Override
            protected void onCollisionBegin(Entity flamestrike, Entity boss_bedj) {

                //Kill Bedj
                getAudioPlayer().playSound("Hit_Collide.wav");
                getGameState().increment("BedjHealth", -250);
            }
        });

        //Bedj & Supernova
        getPhysicsWorld().addCollisionHandler(new CollisionHandler(GameType.SUPERNOVA, GameType.BOSS_BEDJ) {

            @Override
            protected void onCollisionBegin(Entity supernova, Entity boss_bedj) {

                //Kill Bedj
                getAudioPlayer().playSound("Hit_Collide.wav");
                getGameState().increment("BedjHealth", -150);
            }
        });

        //Bedj & Player
        getPhysicsWorld().addCollisionHandler(new CollisionHandler(GameType.PLAYER, GameType.BOSS_BEDJ) {

            @Override
            protected void onCollision(Entity player, Entity boss_bedj) {

                //Reduce Player Health
                getGameState().increment("Health", -1);
                getAudioPlayer().playSound("PlayerHealth.wav");

                //Check Player Health
                if (getGameState().getInt("Health") <= 0) {
                    getDisplay().showMessageBox("Game Over", () -> {
                        exit();
                    });
                }
            }
        });

        //Grim Battle Pattern
        getPhysicsWorld().addCollisionHandler(new CollisionHandler(GameType.PLAYER, GameType.BOSSPATTERN_GRIM1) {

            @Override
            protected void onCollisionBegin(Entity fireblast, Entity BossPattern_Grim1) {

                //Boss Attack Pattern
                if (getGameState().getInt("GrimHealth") <= 18000) {
                    getMasterTimer().runOnceAfter(() -> {
                        getGameWorld().spawn("fireballfinal1", 2100, -100);
                    }, Duration.seconds(0));
                }
            }
        });
        getPhysicsWorld().addCollisionHandler(new CollisionHandler(GameType.PLAYER, GameType.BOSSPATTERN_GRIM2) {

            @Override
            protected void onCollisionBegin(Entity fireblast, Entity BossPattern_Grim2) {

                //Boss Attack Pattern
                if (getGameState().getInt("GrimHealth") <= 18000) {
                    getMasterTimer().runOnceAfter(() -> {
                        getGameWorld().spawn("fireballfinal1", 2520, -100);
                    }, Duration.seconds(0));
                }
            }
        });
        getPhysicsWorld().addCollisionHandler(new CollisionHandler(GameType.PLAYER, GameType.BOSSPATTERN_GRIM3) {

            @Override
            protected void onCollisionBegin(Entity fireblast, Entity BossPattern_Grim3) {

                //Boss Attack Pattern
                if (getGameState().getInt("GrimHealth") <= 18000) {
                    getMasterTimer().runOnceAfter(() -> {
                        getGameWorld().spawn("fireballfinal1", 2940, -100);
                    }, Duration.seconds(0));
                }
            }
        });
        getPhysicsWorld().addCollisionHandler(new CollisionHandler(GameType.PLAYER, GameType.BOSSPATTERN_GRIM4) {

            @Override
            protected void onCollisionBegin(Entity fireblast, Entity BossPattern_Grim4) {

                //Boss Attack Pattern
                if (getGameState().getInt("GrimHealth") <= 18000) {
                    getMasterTimer().runOnceAfter(() -> {
                        getGameWorld().spawn("fireballfinal1", 3370, -100);
                    }, Duration.seconds(0));
                }
            }
        });
        getPhysicsWorld().addCollisionHandler(new CollisionHandler(GameType.PLAYER, GameType.BOSSPATTERN_GRIM5) {

            @Override
            protected void onCollisionBegin(Entity fireblast, Entity BossPattern_Grim5) {

                //Boss Attack Pattern
                if (getGameState().getInt("GrimHealth") <= 8000) {
                    getMasterTimer().runOnceAfter(() -> {
                        getGameWorld().spawn("fireballfinal2", 3850, 40);
                    }, Duration.seconds(0));
                }
            }
        });
        getPhysicsWorld().addCollisionHandler(new CollisionHandler(GameType.PLAYER, GameType.BOSSPATTERN_GRIM6) {

            @Override
            protected void onCollisionBegin(Entity fireblast, Entity BossPattern_Grim6) {

                //Boss Attack Pattern
                if (getGameState().getInt("GrimHealth") <= 8000) {
                    getMasterTimer().runOnceAfter(() -> {
                        getGameWorld().spawn("fireballfinal2", 3850, 250);
                    }, Duration.seconds(0));
                }
            }
        });
        getPhysicsWorld().addCollisionHandler(new CollisionHandler(GameType.PLAYER, GameType.BOSSPATTERN_GRIM7) {

            @Override
            protected void onCollisionBegin(Entity fireblast, Entity BossPattern_Grim7) {

                //Boss Attack Pattern
                if (getGameState().getInt("GrimHealth") <= 8000) {
                    getMasterTimer().runOnceAfter(() -> {
                        getGameWorld().spawn("fireballfinal3", 1850, 430);
                    }, Duration.seconds(0));
                }
            }
        });

        //Grim & Fireball
        getPhysicsWorld().addCollisionHandler(new CollisionHandler(GameType.FIREBALL, GameType.BOSS_GRIM) {

            @Override
            protected void onCollisionBegin(Entity fireball, Entity boss_grim) {

                //Kill Grim
                getAudioPlayer().playSound("Hit_Collide.wav");
                getGameState().increment("GrimHealth", -100);
                fireball.removeFromWorld();

                if (getGameState().getInt("GrimHealth") <= 0) {

                    getAudioPlayer().playSound("enemyDeath.wav");
                    fireball.removeFromWorld();
                    boss_grim.removeFromWorld();
                    getGameState().increment("Gold", + 2500);
                    getGameState().setValue("GrimHealth", 25000);

                    //Message
                    String messageGrimDefeated1 = "Grim Defeated!";
                    String messageGrimDefeated2 = "Returning back to base in 5 seconds...";
                    FXGL.getNotificationService().pushNotification(messageGrimDefeated1);
                    FXGL.getNotificationService().pushNotification(messageGrimDefeated2);

                    getMasterTimer().runOnceAfter(() -> {
                        getAudioPlayer().stopAllMusic();
                    }, Duration.seconds(8.9));

                    getMasterTimer().runOnceAfter(() -> {
                        initGame();
                    }, Duration.seconds(9));

                }
            }
        });

        //Grim & Fireblast
        getPhysicsWorld().addCollisionHandler(new CollisionHandler(GameType.FIREBLAST, GameType.BOSS_GRIM) {

            @Override
            protected void onCollisionBegin(Entity fireblast, Entity boss_grim) {

                //Kill Grim
                getAudioPlayer().playSound("Hit_Collide.wav");
                getGameState().increment("GrimHealth", -250);

            }
        });

        //Grim & Flamestrike
        getPhysicsWorld().addCollisionHandler(new CollisionHandler(GameType.FLAMESTRIKE, GameType.BOSS_GRIM) {

            @Override
            protected void onCollisionBegin(Entity supernova, Entity boss_grim) {

                //Kill Grim
                getAudioPlayer().playSound("Hit_Collide.wav");
                getGameState().increment("GrimHealth", -250);

            }
        });

        //Grim & Supernova
        getPhysicsWorld().addCollisionHandler(new CollisionHandler(GameType.SUPERNOVA, GameType.BOSS_GRIM) {

            @Override
            protected void onCollisionBegin(Entity supernova, Entity boss_grim) {

                //Kill Grim
                getAudioPlayer().playSound("Hit_Collide.wav");
                getGameState().increment("GrimHealth", -150);

            }
        });

        //Grim & Player
        getPhysicsWorld().addCollisionHandler(new CollisionHandler(GameType.PLAYER, GameType.BOSS_GRIM) {

            @Override
            protected void onCollision(Entity player, Entity boss_grim) {

                //Reduce Player Health
                getGameState().increment("Health", -1);
                getAudioPlayer().playSound("PlayerHealth.wav");

                //Check Player Health
                if (getGameState().getInt("Health") <= 0) {
                    getDisplay().showMessageBox("Game Over", () -> {
                        exit();
                    });
                }
            }
        });

        // ----- PlAYER COLLISIONS MAIN -----

        //PLAYER & COIN
        getPhysicsWorld().addCollisionHandler(new CollisionHandler(GameType.PLAYER, GameType.COIN) {

            @Override
            protected void onCollisionBegin(Entity player, Entity coin) {

                getGameState().increment("Gold", +1);
                getAudioPlayer().playSound("CoinGet.wav");
                coin.removeFromWorld();
            }
        });



        //PLAYER & HEALTHYBOOST
        getPhysicsWorld().addCollisionHandler(new CollisionHandler(GameType.PLAYER, GameType.HEALTHBOOST) {
            @Override
            protected void onCollisionBegin(Entity player, Entity HealthBoost) {

                getGameState().setValue("Health", +100);
                getAudioPlayer().playSound("EnergyBoostGet.wav");
                HealthBoost.removeFromWorld();
            }
        });

        //PLAYER & REMOVEFLIGHT
        getPhysicsWorld().addCollisionHandler(new CollisionHandler(GameType.PLAYER, GameType.REMOVEFLIGHT) {

            @Override
            protected void onCollisionBegin(Entity player, Entity coin) {
                canFly = false;
            }
        });


        // ----- MAP COLLISIONS [BASE] -----

        //PLAYER & DOOR_DIVE_TUTORIAL
        getPhysicsWorld().addCollisionHandler(new CollisionHandler(GameType.PLAYER, GameType.DOOR_DIVE_TUTORIAL) {
            @Override
            protected void onCollisionBegin(Entity player, Entity door_dive_tutorial) {

                canMove = false;
                canFly = false;

                //Airship Arrival
                getGameWorld().spawn("Airship", 1810, 250);

                //Message
                String dive_tutorial = "Preparing for Adventure...";
                FXGL.getNotificationService().pushNotification(dive_tutorial);
                getMasterTimer().runOnceAfter(() -> {
                    initDiveTutorial();
                }, Duration.seconds(5));
            }
        });

        //PLAYER & DOOR_DIVE_BOSS1
        getPhysicsWorld().addCollisionHandler(new CollisionHandler(GameType.PLAYER, GameType.DOOR_DIVE_BOSS1) {
            @Override
            protected void onCollisionBegin(Entity player, Entity door_dive_boss1) {

                if (getGameState().getInt("Gold") >= 1000) {

                    getGameState().increment("Gold", -1000);
                    canMove = false;
                    canFly = false;

                    //Airship Arrival
                    getGameWorld().spawn("Airship", 4850, 250);

                    //Message
                    String dive_boss1 = "Preparing for Adventure...";
                    FXGL.getNotificationService().pushNotification(dive_boss1);
                    getMasterTimer().runOnceAfter(() -> {
                        initDiveBoss1();
                    }, Duration.seconds(5));
                } else {
                    getDisplay().showMessageBox("You need atleast 1000 Gold to Travel to This Boss");
                }

            }
        });

        //PLAYER & DOOR_BOSS1
        getPhysicsWorld().addCollisionHandler(new CollisionHandler(GameType.PLAYER, GameType.DOOR_BOSS1) {
            @Override
            protected void onCollisionBegin(Entity player, Entity door_boss1) {

                initBoss1();
            }
        });

        //PLAYER & DOOR_DIVE_BOSS2
        getPhysicsWorld().addCollisionHandler(new CollisionHandler(GameType.PLAYER, GameType.DOOR_DIVE_BOSS2) {
            @Override
            protected void onCollisionBegin(Entity player, Entity door_dive_boss2) {

                if (getGameState().getInt("Gold") >= 2000) {

                    getGameState().increment("Gold", -2000);
                    canMove = false;
                    canFly = false;

                    //Airship Arrival
                    getGameWorld().spawn("Airship", 7940, 180);

                    //Message
                    String dive_boss1 = "Preparing for Adventure...";
                    FXGL.getNotificationService().pushNotification(dive_boss1);
                    getMasterTimer().runOnceAfter(() -> {
                        initDiveBoss2();
                    }, Duration.seconds(5));
                } else {
                    getDisplay().showMessageBox("You need atleast 2000 Gold to Travel to This Boss");
                }
            }
        });

        //PLAYER & DOOR_BOSS2
        getPhysicsWorld().addCollisionHandler(new CollisionHandler(GameType.PLAYER, GameType.DOOR_BOSS2) {
            @Override
            protected void onCollisionBegin(Entity player, Entity door_boss2) {

                initBoss2();
            }
        });

        //PLAYER & DOOR_DIVE_BOSSFINAL
        getPhysicsWorld().addCollisionHandler(new CollisionHandler(GameType.PLAYER, GameType.DOOR_DIVE_BOSSFINAL) {
            @Override
            protected void onCollisionBegin(Entity player, Entity door_dive_bossfinal) {

                if (getGameState().getInt("Gold") >= 3000) {

                    getGameState().increment("Gold", -3000);
                    canMove = false;
                    canFly = false;

                    //Airship Arrival
                    getGameWorld().spawn("Airship", 9680, 90);

                    //Message
                    String dive_boss1 = "Preparing for Adventure...";
                    FXGL.getNotificationService().pushNotification(dive_boss1);
                    getMasterTimer().runOnceAfter(() -> {
                        initDiveBossFinal();
                    }, Duration.seconds(5));
                } else {
                    getDisplay().showMessageBox("You need atleast 3000 Gold to Travel to This Boss");
                }
            }
        });

        //PLAYER & DOOR_BOSSFINAL
        getPhysicsWorld().addCollisionHandler(new CollisionHandler(GameType.PLAYER, GameType.DOOR_BOSSFINAL) {
            @Override
            protected void onCollisionBegin(Entity player, Entity door_bossfinal) {

                initBossFinal();
            }
        });

        //PLAYER & BUYHEALTH
        getPhysicsWorld().addCollisionHandler(new CollisionHandler(GameType.PLAYER, GameType.BUYHEALTH) {
            @Override
            protected void onCollisionBegin(Entity player, Entity buyhealth) {
                getGameState().increment("Gold", -20);

                if (getGameState().getInt("Gold") <= 0) {
                    getGameState().setValue("Gold", 0);
                    getGameState().increment("Health", +0);

                    //Message
                    String message = "Not Enough Gold!";
                    FXGL.getNotificationService().pushNotification(message);
                    getAudioPlayer().playSound("notenough.wav");
                } else {
                    getGameState().increment("Health", +200);
                    getAudioPlayer().playSound("EnergyBoostGet.wav");
                }
            }
        });

        //PLAYER & FALL_BASE
        getPhysicsWorld().addCollisionHandler(new CollisionHandler(GameType.PLAYER, GameType.FALL_BASE) {
            @Override
            protected void onCollisionBegin(Entity player, Entity fall_base) {

                getDisplay().showMessageBox("You Can't Fly There...");

                getAudioPlayer().playSound("drown.wav");
                getGameScene().getViewport().shakeRotational(0.6);
            }
        });

        //PLAYER & FALL
        getPhysicsWorld().addCollisionHandler(new CollisionHandler(GameType.PLAYER, GameType.FALL) {
            @Override
            protected void onCollisionBegin(Entity player, Entity fall_tutorial) {

                //Reduce Player Health
                getGameState().increment("Health", -30);
                getGameScene().getViewport().shakeRotational(0.6);

                //Check Player Health
                if (getGameState().getInt("Health") <= 0) {
                    getDisplay().showMessageBox("Game Over", () -> {
                    });
                } else {
                    getAudioPlayer().playSound("drown.wav");
                }
            }
        });
        // ----- MAP COLLISIONS [DIVE TUTORIAL] -----
        //PLAYER & DOOR_TUTORIAL
        getPhysicsWorld().addCollisionHandler(new CollisionHandler(GameType.PLAYER, GameType.DOOR_TUTORIAL) {
            @Override
            protected void onCollisionBegin(Entity player, Entity door_tutorial) {
                initTutorial();
            }
        });

        // ----- MAP COLLISIONS [TUTORIAL] -----

        //PLAYER & DOOR_TUTORIAL2
        getPhysicsWorld().addCollisionHandler(new CollisionHandler(GameType.PLAYER, GameType.DOOR_TUTORIAL2) {
            @Override
            protected void onCollisionBegin(Entity player, Entity door_tutorial2) {
                initTutoria2();
            }
        });

        //PLAYER & GUIDE1
        getPhysicsWorld().addCollisionHandler(new CollisionHandler(GameType.PLAYER, GameType.GUIDE1) {
            @Override
            protected void onCollisionBegin(Entity player, Entity guide) {

                guide.removeFromWorld();

                //Message
                getDisplay().showMessageBox("Hold down 'UP' to Fly, or 'DOWN' to Descend.");
            }
        });

        //PLAYER & GUIDE2
        getPhysicsWorld().addCollisionHandler(new CollisionHandler(GameType.PLAYER, GameType.GUIDE2) {
            @Override
            protected void onCollisionBegin(Entity player, Entity guide2) {

                guide2.removeFromWorld();

                //Message
                getDisplay().showMessageBox("Press 'SPACE' to Shoot Fireballs.");
            }
        });

        //PLAYER & GUIDE3
        getPhysicsWorld().addCollisionHandler(new CollisionHandler(GameType.PLAYER, GameType.GUIDE3) {
            @Override
            protected void onCollisionBegin(Entity player, Entity guide3) {

                guide3.removeFromWorld();

                //Message
                getDisplay().showMessageBox("Press 'A', 'S', or 'D' to Use Your Skills.");
            }
        });

        // ----- MAP COLLISIONS [TUTORIAL 2] -----

        //PLAYER & DOOR_DIVE_TUTORIAL3
        getPhysicsWorld().addCollisionHandler(new CollisionHandler(GameType.PLAYER, GameType.DOOR_DIVE_TUTORIAL3) {
            @Override
            protected void onCollisionBegin(Entity player, Entity door_dive_tutorial3) {
                initDiveTutorial3();
            }
        });

        // ----- MAP COLLISIONS [DIVE TUTORIAL 3] -----

        //PLAYER & DOOR_TUTORIAL3
        getPhysicsWorld().addCollisionHandler(new CollisionHandler(GameType.PLAYER, GameType.DOOR_TUTORIAL3) {
            @Override
            protected void onCollisionBegin(Entity player, Entity door_tutorial2) {
                initTutoria3();
            }
        });

        // ----- MAP COLLISIONS [TUTORIAL 3] -----

        //Player & Darkflamemaster_Deathball
        getPhysicsWorld().addCollisionHandler(new CollisionHandler(GameType.PLAYER, GameType.DARKFLAMEMASTER_DEATHBALL) {

            @Override
            protected void onCollisionBegin(Entity player, Entity darkflamemaster_deathball) {

                //Reduce Player Health
                getGameState().increment("Health", -10);
                getAudioPlayer().playSound("PlayerHealth.wav");
                getGameScene().getViewport().shakeRotational(0.6);

                //Check Player Health
                if (getGameState().getInt("Health") <= 0) {
                    getDisplay().showMessageBox("Game Over", () -> {
                        exit();
                    });
                }
            }
        });
        //Deathball Remove from World
        getPhysicsWorld().addCollisionHandler(new CollisionHandler(GameType.PLATFORM, GameType.DARKFLAMEMASTER_DEATHBALL) {

            @Override
            protected void onCollisionBegin(Entity platform, Entity darkflamemaster_deathball) {
                getMasterTimer().runOnceAfter(() -> {
                    darkflamemaster_deathball.removeFromWorld();
                }, Duration.seconds(5));

            }
        });

        // ----- MAP COLLISIONS [BOSS 1] -----

        //Player & Rhatbu_Rhatbuball
        getPhysicsWorld().addCollisionHandler(new CollisionHandler(GameType.PLAYER, GameType.RHATBU_RHATBUBALL) {

            @Override
            protected void onCollisionBegin(Entity player, Entity rhatbu_rhatbuball) {

                //Reduce Player Health
                getGameState().increment("Health", -20);
                getAudioPlayer().playSound("PlayerHealth.wav");
                getGameScene().getViewport().shakeRotational(0.6);

                //Check Player Health
                if (getGameState().getInt("Health") <= 0) {
                    getDisplay().showMessageBox("Game Over", () -> {
                        exit();
                    });
                }
            }
        });

        //Rhatbuball Remove from World
        getPhysicsWorld().addCollisionHandler(new CollisionHandler(GameType.PLATFORM, GameType.RHATBU_RHATBUBALL) {

            @Override
            protected void onCollisionBegin(Entity platform, Entity rhatbu_rhatbuball) {
                getMasterTimer().runOnceAfter(() -> {
                    rhatbu_rhatbuball.removeFromWorld();
                }, Duration.seconds(5));

            }
        });

        // ----- MAP COLLISIONS [BOSS 2] -----

        //Player & Bedj_Waterwall
        getPhysicsWorld().addCollisionHandler(new CollisionHandler(GameType.PLAYER, GameType.BEDJ_WATERWALL) {

            @Override
            protected void onCollisionBegin(Entity player, Entity bedj_waterwall) {

                //Reduce Player Health
                getGameState().increment("Health", -10);
                getAudioPlayer().playSound("PlayerHealth.wav");
                getGameScene().getViewport().shakeRotational(0.6);

                //Check Player Health
                if (getGameState().getInt("Health") <= 0) {
                    getDisplay().showMessageBox("Game Over", () -> {
                        exit();
                    });
                }
            }
        });

        //Waterwall Remove from World
        getPhysicsWorld().addCollisionHandler(new CollisionHandler(GameType.PLATFORM, GameType.BEDJ_WATERWALL) {

            @Override
            protected void onCollisionBegin(Entity platform, Entity bedj_waterwall) {
                getMasterTimer().runOnceAfter(() -> {
                    bedj_waterwall.removeFromWorld();
                }, Duration.seconds(5));

            }
        });

        //Player & Bedj waterball
        getPhysicsWorld().addCollisionHandler(new CollisionHandler(GameType.PLAYER, GameType.BEDJ_WATERBALL) {

            @Override
            protected void onCollisionBegin(Entity player, Entity bedj_waterball) {

                //Reduce Player Health
                getGameState().increment("Health", -10);
                getAudioPlayer().playSound("PlayerHealth.wav");

                //Check Player Health
                if (getGameState().getInt("Health") <= 0) {
                    getDisplay().showMessageBox("Game Over", () -> {
                        exit();
                    });
                }
            }
        });

        // ----- MAP COLLISIONS [FINAL BOSS] -----

        //Player & Grim_Fireballfinal1
        getPhysicsWorld().addCollisionHandler(new CollisionHandler(GameType.PLAYER, GameType.GRIM_FIREBALLFINAL1) {

            @Override
            protected void onCollisionBegin(Entity player, Entity Grim_FireballFinal1) {

                //Reduce Player Health
                getGameState().increment("Health", -10);
                getAudioPlayer().playSound("PlayerHealth.wav");
                getGameScene().getViewport().shakeRotational(0.6);

                //Check Player Health
                if (getGameState().getInt("Health") <= 0) {
                    getDisplay().showMessageBox("Game Over", () -> {
                        exit();
                    });
                }
            }
        });

        //Fireballfinal1 Remove from World
        getPhysicsWorld().addCollisionHandler(new CollisionHandler(GameType.PLATFORM, GameType.GRIM_FIREBALLFINAL1) {

            @Override
            protected void onCollisionBegin(Entity platform, Entity Grim_FireballFinal1) {
                getMasterTimer().runOnceAfter(() -> {
                    Grim_FireballFinal1.removeFromWorld();
                }, Duration.seconds(5));

            }
        });

        //Player & Grim_Fireballfinal2
        getPhysicsWorld().addCollisionHandler(new CollisionHandler(GameType.PLAYER, GameType.GRIM_FIREBALLFINAL2) {

            @Override
            protected void onCollisionBegin(Entity player, Entity Grim_FireballFinal2) {

                //Reduce Player Health
                getGameState().increment("Health", -10);
                getAudioPlayer().playSound("PlayerHealth.wav");
                getGameScene().getViewport().shakeRotational(0.6);

                //Check Player Health
                if (getGameState().getInt("Health") <= 0) {
                    getDisplay().showMessageBox("Game Over", () -> {
                        exit();
                    });
                }
            }
        });

        //Fireballfinal2 Remove from World
        getPhysicsWorld().addCollisionHandler(new CollisionHandler(GameType.PLATFORM, GameType.GRIM_FIREBALLFINAL2) {

            @Override
            protected void onCollisionBegin(Entity platform, Entity Grim_FireballFinal2) {
                getMasterTimer().runOnceAfter(() -> {
                    Grim_FireballFinal2.removeFromWorld();
                }, Duration.seconds(5));

            }
        });

        //PLAYER & LAVA_FINAL
        getPhysicsWorld().addCollisionHandler(new CollisionHandler(GameType.PLAYER, GameType.LAVA_FINAL) {
            @Override
            protected void onCollisionBegin(Entity player, Entity water_tutorial) {

                //Reduce Player Health
                getGameState().increment("Health", -30);
                getGameScene().getViewport().shakeRotational(0.6);

                //Check Player Health
                if (getGameState().getInt("Health") <= 0) {
                    getDisplay().showMessageBox("Game Over", () -> {
                        exit();
                    });
                } else {
                    getAudioPlayer().playSound("drown.wav");
                }
            }
        });

        //Player & Grim Fireball
        getPhysicsWorld().addCollisionHandler(new CollisionHandler(GameType.PLAYER, GameType.BOSS_GRIMFIREBALL) {

            @Override
            protected void onCollisionBegin(Entity player, Entity boss_grimfireball) {

                //Reduce Player Health
                getGameState().increment("Health", -10);
                getAudioPlayer().playSound("PlayerHealth.wav");

                //Check Player Health
                if (getGameState().getInt("Health") <= 0) {
                    getDisplay().showMessageBox("Game Over", () -> {
                        exit();
                    });
                }
            }
        });
    }

    // ------------------------------------- <<< User Interface >>> -------------------------------------
    @Override
    protected void initUI() {

        //Stats UI
        Texture HealthIcon = getAssetLoader().loadTexture("HeartIcon.gif");
        HealthIcon.setTranslateX(10);
        HealthIcon.setTranslateY(15);

        Texture GoldIcon = getAssetLoader().loadTexture("GoldIcon.gif");
        GoldIcon.setTranslateX(1090);
        GoldIcon.setTranslateY(15);

        //Control UI
        Texture controlUI = getAssetLoader().loadTexture("ControlUI.png");
        controlUI.setTranslateX(1110);
        controlUI.setTranslateY(620);

        //Health Number
        Text Health = new Text();
        Health.setFont(Font.font ("Berlin Sans FB Demi", 36));
        Health.setFill(Color.WHITE);
        Health.setTranslateX(80);
        Health.setTranslateY(50);

        Text HealthPercent = new Text();
        HealthPercent.setText("%");
        HealthPercent.setFont(Font.font ("Berlin Sans FB Demi", 36));
        HealthPercent.setFill(Color.WHITE);
        HealthPercent.setTranslateX(140);
        HealthPercent.setTranslateY(50);

        Rectangle HealthUI = new Rectangle(110, 35);
        HealthUI.setFill(Color.rgb(0, 0, 0, 0.7));
        HealthUI.setArcHeight(15);
        HealthUI.setArcWidth(15);
        HealthUI.setTranslateX(70);
        HealthUI.setTranslateY(23);

        //Gold Number
        Text Gold = new Text();
        Gold.setFont(Font.font ("Berlin Sans FB Demi", 36));
        Gold.setFill(Color.WHITE);
        Gold.setTranslateX(1150);
        Gold.setTranslateY(50);

        Rectangle GoldUI = new Rectangle(110, 35);
        GoldUI.setFill(Color.rgb(0, 0, 0, 0.7));
        GoldUI.setArcHeight(15);
        GoldUI.setArcWidth(15);
        GoldUI.setTranslateX(1140);
        GoldUI.setTranslateY(23);

        //Skills UI
        Texture skillsUI = getAssetLoader().loadTexture("skillslUI.png");
        skillsUI.setTranslateX(0);
        skillsUI.setTranslateY(660);

        Texture supernovaIcon = getAssetLoader().loadTexture("supernovaIcon.gif");
        supernovaIcon.setTranslateX(35);
        supernovaIcon.setTranslateY(653);

        Texture flamestrikeIcon = getAssetLoader().loadTexture("flamestrikeIcon.gif");
        flamestrikeIcon.setTranslateX(140);
        flamestrikeIcon.setTranslateY(653);

        Texture fireblastIcon = getAssetLoader().loadTexture("fireblastIcon.gif");
        fireblastIcon.setTranslateX(240);
        fireblastIcon.setTranslateY(653);

        //Fireball UI
        Texture fireballUI = getAssetLoader().loadTexture("fireballUI.png");
        fireballUI.setTranslateX(480);
        fireballUI.setTranslateY(625);

        //Firepower Number
        Text Fireball = new Text();
        Fireball.setFont(Font.font ("Berlin Sans FB Demi", 30));
        Fireball.setFill(Color.WHITE);
        Fireball.setTranslateX(640);
        Fireball.setTranslateY(715);

        //Supernova Number
        Text Supernova = new Text();
        Supernova.setFont(Font.font ("Berlin Sans FB Demi", 24));
        Supernova.setFill(Color.WHITE);
        Supernova.setTranslateX(30);
        Supernova.setTranslateY(715);

        Text SupernovaCharge = new Text();
        SupernovaCharge.setText("%");
        SupernovaCharge.setFont(Font.font ("Berlin Sans FB Demi", 24));
        SupernovaCharge.setFill(Color.WHITE);
        SupernovaCharge.setTranslateX(70);
        SupernovaCharge.setTranslateY(715);

        Rectangle SupernovaNumberUI = new Rectangle(70, 25);
        SupernovaNumberUI.setFill(Color.rgb(0, 0, 0, 0.6));
        SupernovaNumberUI.setArcHeight(15);
        SupernovaNumberUI.setArcWidth(15);
        SupernovaNumberUI.setTranslateX(25);
        SupernovaNumberUI.setTranslateY(695);

        //Flamestrike Number
        Text Flamestrike = new Text();
        Flamestrike.setFont(Font.font ("Berlin Sans FB Demi", 24));
        Flamestrike.setFill(Color.WHITE);
        Flamestrike.setTranslateX(135);
        Flamestrike.setTranslateY(715);

        Text FlamestrikeCharge = new Text();
        FlamestrikeCharge.setText("%");
        FlamestrikeCharge.setFont(Font.font ("Berlin Sans FB Demi", 24));
        FlamestrikeCharge.setFill(Color.WHITE);
        FlamestrikeCharge.setTranslateX(175);
        FlamestrikeCharge.setTranslateY(715);

        Rectangle FlamestrikeNumberUI = new Rectangle(70, 25);
        FlamestrikeNumberUI.setFill(Color.rgb(0, 0, 0, 0.6));
        FlamestrikeNumberUI.setArcHeight(15);
        FlamestrikeNumberUI.setArcWidth(15);
        FlamestrikeNumberUI.setTranslateX(130);
        FlamestrikeNumberUI.setTranslateY(695);

        //Fireblast Number
        Text Fireblast = new Text();
        Fireblast.setFont(Font.font ("Berlin Sans FB Demi", 24));
        Fireblast.setFill(Color.WHITE);
        Fireblast.setTranslateX(240);
        Fireblast.setTranslateY(715);

        Text FireblastCharge = new Text();
        FireblastCharge.setText("%");
        FireblastCharge.setFont(Font.font ("Berlin Sans FB Demi", 24));
        FireblastCharge.setFill(Color.WHITE);
        FireblastCharge.setTranslateX(280);
        FireblastCharge.setTranslateY(715);

        Rectangle FireblastNumberUI = new Rectangle(70, 25);
        FireblastNumberUI.setFill(Color.rgb(0, 0, 0, 0.6));
        FireblastNumberUI.setArcHeight(15);
        FireblastNumberUI.setArcWidth(15);
        FireblastNumberUI.setTranslateX(235);
        FireblastNumberUI.setTranslateY(695);

        //Enemy Health Number
        Text EnemyHealth = new Text();
        EnemyHealth.setFont(Font.font ("Berlin Sans FB Demi", 20));
        EnemyHealth.setFill(Color.WHITE);
        EnemyHealth.setTranslateX(600);
        EnemyHealth.setTranslateY(40);

        //Dark Flame Master Health Text
        Text DarkFlameMasterHealthUI = new Text();
        DarkFlameMasterHealthUI .setText("Dark Flame Master:");
        DarkFlameMasterHealthUI .setFont(Font.font ("Berlin Sans FB Demi", 40));
        DarkFlameMasterHealthUI .setFill(Color.RED);
        DarkFlameMasterHealthUI .setTranslateX(300);
        DarkFlameMasterHealthUI .setTranslateY(40);

        //Dark Flame Master Health Number
        Text DarkFlameMasterHealth = new Text();
        DarkFlameMasterHealth.setFont(Font.font ("Berlin Sans FB Demi", 40));
        DarkFlameMasterHealth.setFill(Color.WHITE);
        DarkFlameMasterHealth.setTranslateX(700);
        DarkFlameMasterHealth.setTranslateY(40);

        //Rhatbu Health Text
        Text RhatbuHealthUI = new Text();
        RhatbuHealthUI .setText("Rhatbu:");
        RhatbuHealthUI .setFont(Font.font ("Berlin Sans FB Demi", 40));
        RhatbuHealthUI .setFill(Color.RED);
        RhatbuHealthUI .setTranslateX(400);
        RhatbuHealthUI .setTranslateY(40);

        //Rhatbu Health Number
        Text RhatbuHealth = new Text();
        RhatbuHealth.setFont(Font.font ("Berlin Sans FB Demi", 40));
        RhatbuHealth.setFill(Color.WHITE);
        RhatbuHealth.setTranslateX(500);
        RhatbuHealth.setTranslateY(40);

        //Bedj Health Text
        Text BedjHealthUI = new Text();
        BedjHealthUI.setText("Bedj:");
        BedjHealthUI.setFont(Font.font ("Berlin Sans FB Demi", 40));
        BedjHealthUI.setFill(Color.RED);
        BedjHealthUI.setTranslateX(300);
        BedjHealthUI.setTranslateY(40);

        //Bedj Health Number
        Text GrimHealth = new Text();
        GrimHealth.setFont(Font.font ("Berlin Sans FB Demi", 40));
        GrimHealth.setFill(Color.WHITE);
        GrimHealth.setTranslateX(600);
        GrimHealth.setTranslateY(40);

        //Grim Health Text
        Text GrimHealthUI = new Text();
        GrimHealthUI.setText("Grim:");
        GrimHealthUI.setFont(Font.font ("Berlin Sans FB Demi", 40));
        GrimHealthUI.setFill(Color.RED);
        GrimHealthUI.setTranslateX(300);
        GrimHealthUI.setTranslateY(40);

        //Grim Health Number
        Text BedjHealth = new Text();
        BedjHealth.setFont(Font.font ("Berlin Sans FB Demi", 40));
        BedjHealth.setFill(Color.WHITE);
        BedjHealth.setTranslateX(600);
        BedjHealth.setTranslateY(40);

        //Add UI to scene
        getGameScene().addUINode(HealthUI);
        getGameScene().addUINode(Health);
        getGameScene().addUINode(HealthPercent);

        //Skills
        getGameScene().addUINode(skillsUI);
        getGameScene().addUINode(supernovaIcon);
        getGameScene().addUINode(flamestrikeIcon);
        getGameScene().addUINode(fireblastIcon);

        getGameScene().addUINode(fireballUI);
        //getGameScene().addUINode(Fireball);

        getGameScene().addUINode(FlamestrikeNumberUI);
        getGameScene().addUINode(Flamestrike);
        getGameScene().addUINode(FlamestrikeCharge);

        getGameScene().addUINode(SupernovaNumberUI);
        getGameScene().addUINode(Supernova);
        getGameScene().addUINode(SupernovaCharge);

        getGameScene().addUINode(FireblastNumberUI);
        getGameScene().addUINode(Fireblast);
        getGameScene().addUINode(FireblastCharge);

        getGameScene().addUINode(controlUI);
        getGameScene().addUINode(HealthIcon);
        getGameScene().addUINode(GoldIcon);
        getGameScene().addUINode(GoldUI);
        getGameScene().addUINode(Gold);

        //getGameScene().addUINode(EnemyHealth);

        //getGameScene().addUINode(DarkFlameMasterHealth);
        //getGameScene().addUINode(DarkFlameMasterHealthUI);

        //getGameScene().addUINode(RhatbuHealth);
        //getGameScene().addUINode(RhatbuHealthUI);

        //getGameScene().addUINode(BedjHealth);
        //getGameScene().addUINode(BedjHealthUI);

        //getGameScene().addUINode(GrimHealth);
        //getGameScene().addUINode(GrimHealthUI);

        //Player
        Health.textProperty().bind(getGameState().intProperty("Health").asString());
        Gold.textProperty().bind(getGameState().intProperty("Gold").asString());

        //Skills
        Fireball.textProperty().bind(getGameState().intProperty("Fireball").asString());
        Fireblast.textProperty().bind(getGameState().intProperty("Fireblast").asString());
        Flamestrike.textProperty().bind(getGameState().intProperty("Flamestrike").asString());
        Supernova.textProperty().bind(getGameState().intProperty("Supernova").asString());

        //Enemy
        EnemyHealth.textProperty().bind(getGameState().intProperty("EnemyHealth").asString());

        //Boss
        RhatbuHealth.textProperty().bind(getGameState().intProperty("RhatbuHealth").asString());
        BedjHealth.textProperty().bind(getGameState().intProperty("BedjHealth").asString());
        GrimHealth.textProperty().bind(getGameState().intProperty("GrimHealth").asString());
        DarkFlameMasterHealth.textProperty().bind(getGameState().intProperty("DarkFlameMasterHealth").asString());

    }

    @Override
    protected void initGameVars(Map<String, Object> vars) {

        //View in-game
        vars.put("Health", 100);
        vars.put("Gold", 0);
        vars.put("Energy", 100);

        //Skill
        vars.put("Fireball", 100);
        vars.put("Fireblast", 100);
        vars.put("Flamestrike", 100);
        vars.put("Supernova", 100);

        //Enemy
        vars.put("EnemyHealth", 300);

        //Bosses
        vars.put("DarkFlameMasterHealth", 9000);
        vars.put("RhatbuHealth", 15000);
        vars.put("BedjHealth", 20000);
        vars.put("GrimHealth", 25000);

    }


    // ------------------------------------- <<< Main Launch >>> -------------------------------------
    public static void main(String[] args) {
        launch(args);
    }
}

//hey
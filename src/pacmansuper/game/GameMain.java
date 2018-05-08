package pacmansuper.game;

import com.almasb.fxgl.animation.Animation;
import com.almasb.fxgl.app.FXGL;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.physics.*;
import com.almasb.fxgl.scene.FXGLMenu;
import com.almasb.fxgl.scene.SceneFactory;
import com.almasb.fxgl.scene.menu.MenuEventListener;
import com.almasb.fxgl.scene.menu.MenuType;
import com.almasb.fxgl.settings.MenuItem;
import com.almasb.fxgl.time.LocalTimer;
import com.almasb.fxgl.settings.GameSettings;
import com.almasb.fxgl.texture.Texture;
import com.almasb.fxgl.io.serialization.Bundle;
import com.almasb.fxgl.saving.DataFile;
import javafx.geometry.Point2D;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.EnumSet;
import java.util.Map;
import java.util.logging.Level;

import static com.almasb.fxgl.app.DSLKt.*;

/**
 * Main Class that extends FXGL Library
 *
 * @author Earl John Laguardia
 * @author Celina Kai
 * @author Mariha Bati
 */

public class GameMain extends GameApplication {

    // ------------------------------------- <<< Data >>> -------------------------------------

    //Player
    private Entity player;
    private int Health;
    private int Gold;
    private int Potion;
    private int PotionCharge;
    private int Rank;
    private int Points;

    private int Fireball;
    private int Fireblast;
    private int FireblastLevel;
    private int Flamestrike;
    private int FlamestrikeLevel;
    private int Supernova;
    private int SupernovaLevel;

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
    private boolean canPotion = true;
    private boolean canFireblastValue = true;
    private boolean canFlamestrikeValue = true;
    private boolean canSupernovaValue = true;
    private boolean godmode = false;

    //-- Level Map Completion --
    //Base
    private int first_upgrade;
    private int LvlComplete_Tutorial;

    private int LvlComplete_Level_1x3;
    private int LvlComplete_Level_4x6;
    private int LvlCOmplete_Level_7x9;
    private int LvlComplete_Level_11x13;
    private int LvlComplete_Level_14x16;
    private int LvlComplete_Level_17x19;

    private int LvlComplete_Rhatbu;
    private int LvlComplete_Bedj;
    private int LvlComplete_Grim;

    //Other
    private LocalTimer returnTimer;
    private LocalTimer finalBossTimer;

    // ------------------------------------- <<< Game Settings >>> -------------------------------------

    /**
     * Game Launcher Settings
     *
     * @author Earl John Laguardia
     */
    @Override
    protected void initSettings(GameSettings settings) {

        settings.setTitle("Pacman Super");
        settings.setVersion("1.0.0");
        settings.setWidth(1280);
        settings.setHeight(770); //770
        settings.setProfilingEnabled(false); //Profile
        settings.setIntroEnabled(true);
        settings.setCloseConfirmation(false);
        settings.setMenuEnabled(true); //Menu

        settings.setEnabledMenuItems(EnumSet.allOf(MenuItem.class));
        settings.setSceneFactory(new GameMenu());
    }

    // ------------------------------------- <<< Save & Load >>> -------------------------------------

    /**
     * Save Method
     *
     * @author Mariha Bati
     */
    @Override
    public DataFile saveState() {

        //-- Save to Serializable Data --

        //Player Stats
        Serializable rank = getGameState().getInt("Rank");
        Serializable points = getGameState().getInt("Points");
        Serializable gold = getGameState().getInt("Gold");
        Serializable Potion = getGameState().getInt("Potion");

        Serializable fireblastLevel = getGameState().getInt("FireblastLevel");
        Serializable flamestrikeLevel = getGameState().getInt("FlamestrikeLevel");
        Serializable supernovaLevel = getGameState().getInt("SupernovaLevel");

        //Map Level Completion
        Serializable first_upgrade = getGameState().getInt("first_upgrade") ;
        Serializable LvlComplete_Tutorial = getGameState().getInt("LvlComplete_Tutorial");

        Serializable LvlComplete_Level_1x3 = getGameState().getInt("LvlComplete_Level_1x3");
        Serializable LvlComplete_Level_4x6 = getGameState().getInt("LvlComplete_Level_4x6");
        Serializable LvlComplete_Level_7x9 = getGameState().getInt("LvlComplete_Level_7x9");
        Serializable LvlComplete_Level_11x13 = getGameState().getInt("LvlComplete_Level_11x13");
        Serializable LvlComplete_Level_14x16 = getGameState().getInt("LvlComplete_Level_14x16");
        Serializable LvlComplete_Level_17x19 = getGameState().getInt("LvlComplete_Level_17x19");


        Serializable LvlComplete_Rhatbu = getGameState().getInt("LvlComplete_Rhatbu");
        Serializable LvlComplete_Bedj = getGameState().getInt("LvlComplete_Bedj");
        Serializable LvlComplete_Grim = getGameState().getInt("LvlComplete_Grim");

        //-- Pack to Bundle --

        //Player stats
        Bundle bundleRoot = new Bundle("Root");
        bundleRoot.put("Rank", rank);
        bundleRoot.put("Points", points);
        bundleRoot.put("gold", gold);
        bundleRoot.put("Potion", Potion);

        bundleRoot.put("FireblastLevel", fireblastLevel);
        bundleRoot.put("FlamestrikeLevel", flamestrikeLevel);
        bundleRoot.put("SupernovaLevel", supernovaLevel);

        //Map Level Completion
        bundleRoot.put("first_upgrade", first_upgrade);
        bundleRoot.put("LvlComplete_Tutorial", LvlComplete_Tutorial);

        bundleRoot.put("LvlComplete_Level_1x3", LvlComplete_Level_1x3);
        bundleRoot.put("LvlComplete_Level_4x6", LvlComplete_Level_4x6);
        bundleRoot.put("LvlComplete_Level_7x9", LvlComplete_Level_7x9);
        bundleRoot.put("LvlComplete_Level_11x13", LvlComplete_Level_11x13);
        bundleRoot.put("LvlComplete_Level_14x16", LvlComplete_Level_14x16);
        bundleRoot.put("LvlComplete_Level_17x19", LvlComplete_Level_17x19);

        bundleRoot.put("LvlComplete_Rhatbu", LvlComplete_Rhatbu);
        bundleRoot.put("LvlComplete_Bedj", LvlComplete_Bedj);
        bundleRoot.put("LvlComplete_Grim", LvlComplete_Grim);


        //return the Data
        return new DataFile(bundleRoot);
    }

    /**
     * Load Method
     *
     * @author Mariha Bati
     */
    @Override
    public void loadState(DataFile dataFile) {

        //-- Load Data --
        Bundle bundleRoot = (Bundle) dataFile.getData();

        //Player Stats
        int rank = bundleRoot.get("Rank");
        int pts = bundleRoot.get("Points");
        int gp = bundleRoot.get("gold");
        int pot = bundleRoot.get("Potion");

        int fireblastLvl = bundleRoot.get("FireblastLevel");
        int flamestrikeLvl = bundleRoot.get("FlamestrikeLevel");
        int supernovaLvl = bundleRoot.get("SupernovaLevel");

        //Map Level Completion
        int first_upgrade = bundleRoot.get("first_upgrade");
        int LvlComplete_Tutorial = bundleRoot.get("LvlComplete_Tutorial");

        int LvlComplete_Level_1x3 = bundleRoot.get("LvlComplete_Level_1x3");
        int LvlComplete_Level_4x6 = bundleRoot.get("LvlComplete_Level_4x6");
        int LvlComplete_Level_7x9 = bundleRoot.get("LvlComplete_Level_7x9");
        int LvlComplete_Level_11x13 = bundleRoot.get("LvlComplete_Level_11x13");
        int LvlComplete_Level_14x16 = bundleRoot.get("LvlComplete_Level_14x16");
        int LvlComplete_Level_17x19 = bundleRoot.get("LvlComplete_Level_17x19");


        int LvlComplete_Rhatbu = bundleRoot.get("LvlComplete_Rhatbu");
        int LvlComplete_Bedj = bundleRoot.get("LvlComplete_Bedj");
        int LvlComplete_Grim = bundleRoot.get("LvlComplete_Grim");

        //-- Implement Load Data --

        //Player stats
        getGameState().setValue("Rank", rank);
        getGameState().setValue("Points", pts);
        getGameState().setValue("Gold", gp);
        getGameState().setValue("Potion", pot);

        getGameState().setValue("FireblastLevel", fireblastLvl);
        getGameState().setValue("FlamestrikeLevel", flamestrikeLvl);
        getGameState().setValue("SupernovaLevel", supernovaLvl);

        //Map Level Completion
        getGameState().setValue("first_upgrade", first_upgrade);
        getGameState().setValue("LvlComplete_Tutorial", LvlComplete_Tutorial);

        getGameState().setValue("LvlComplete_Level_1x3", LvlComplete_Level_1x3);
        getGameState().setValue("LvlComplete_Level_4x6", LvlComplete_Level_4x6);
        getGameState().setValue("LvlComplete_Level_7x9", LvlComplete_Level_7x9);
        getGameState().setValue("LvlComplete_Level_11x13", LvlComplete_Level_11x13);
        getGameState().setValue("LvlComplete_Level_14x16", LvlComplete_Level_14x16);
        getGameState().setValue("LvlComplete_Level_17x19", LvlComplete_Level_17x19);

        getGameState().setValue("LvlComplete_Rhatbu", LvlComplete_Rhatbu);
        getGameState().setValue("LvlComplete_Bedj", LvlComplete_Bedj);
        getGameState().setValue("LvlComplete_Grim", LvlComplete_Grim);

        //Init Game
        initGame();
        
    }
    // ------------------------------------- <<< Game Menu >>> -------------------------------------

    /**
     * Menu Class that extends FXGL Default Menu
     *
     * @author Earl John Laguardia
     */
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

    /**
     * Main Game Initializer
     *
     * @author Earl John Laguardia
     */
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

        if (getGameState().getInt("LvlComplete_Tutorial") > 0) {
            //Message
            initNotification_WelcomeBack();
        } else {
            getMasterTimer().runOnceAfter(() -> {
                getDisplay().showMessageBox("Use the 'ARROW KEYS' To Move Around");
                getDisplay().showMessageBox("Head to the TUTORIAL to begin your Adventure...");
                getDisplay().showMessageBox("Welcome to Pacman Super!");
            }, Duration.seconds(0.5));
        }

        //Initialize Player
        getGameState().setValue("Health", 100);
        if (getGameState().getInt("LvlComplete_Tutorial") > 0) {
            player = getGameWorld().spawn("player", 650, 410);
        } else {
            player = getGameWorld().spawn("player", 2880, 420);
        }

        //Skill Charge
        canMove = true;
        canFly = true;
        HealthCharge();
        PotionDrinkCharge();
        RankPointsCap();
        SkillUpgradeCap();
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

        //Reset Boss HP



        //-- Level Complete --

        //Level: Tutorial
        if (getGameState().getInt("LvlComplete_Tutorial") == 1) {
            getGameWorld().spawn("LevelComplete", 3600, 450);
        }

        //Level: 1x3
        if (getGameState().getInt("LvlComplete_Level_1x3") == 1) {
            getGameWorld().spawn("LevelComplete", 4645, 250);
        }

        //Level: 4x6
        if (getGameState().getInt("LvlComplete_Level_4x6") == 1) {
            getGameWorld().spawn("LevelComplete", 5360, 320);
        }

        //Level: 7x9
        if (getGameState().getInt("LvlComplete_Level_7x9") == 1) {
            getGameWorld().spawn("LevelComplete", 5920, 40);
        }

        //Level: 11x13
        if (getGameState().getInt("LvlComplete_Level_11x13") == 1) {
            getGameWorld().spawn("LevelComplete", 7520, 170);
        }

        //Level: 14x16
        if (getGameState().getInt("LvlComplete_Level_14x16") == 1) {
            getGameWorld().spawn("LevelComplete", 8230, 170);
        }

        //Level: 17x19
        if (getGameState().getInt("LvlComplete_Level_17x19") == 1) {
            getGameWorld().spawn("LevelComplete", 8930, 300);
        }

        //Level: Rhatbu
        if (getGameState().getInt("LvlComplete_Rhatbu") == 1) {
            getGameWorld().spawn("LevelComplete", 6640, 405);
        }

        //Level: Bedj
        if (getGameState().getInt("LvlComplete_Bedj") == 1) {
            getGameWorld().spawn("LevelComplete", 9720, 335);
        }

        //Level: Grim
        if (getGameState().getInt("LvlComplete_Grim") == 1) {
            getGameWorld().spawn("LevelComplete", 11475, 280);
        }

        //Camera Settings
        getGameScene().getViewport().setBounds(0, 0, 11900, 770);
        getGameScene().getViewport().bindToEntity(player, getWidth() / 2, getHeight() / 2);

    }

    /**
     * Initialize Level: Jungle Clear
     *
     * @author Earl John Laguardia
     */

    protected void initLevelClear_Jungle() {

        //Initialize Map
        getMasterTimer().clear();
        getGameWorld().setLevelFromMap("LevelClear_Jungle.json");

        //Initialize Player
        player = getGameWorld().spawn("player", 90, 550);

        //Skill Charge
        canMove = true;
        canFly = true;
        HealthCharge();
        RankPointsCap();
        SkillUpgradeCap();
        FireballCharge();
        FireblastCharge();
        FlamestrikeCharge();
        SupernovaCharge();

        //Camera Settings
        getGameScene().getViewport().setBounds(0, 0, 2800, 770);
        getGameScene().getViewport().bindToEntity(player, getWidth() / 2, getHeight() / 2);
    }

    /**
     * Initialize Level: Jungle Clear
     *
     * @author Earl John Laguardia
     */
    protected void initLevelClear_Cave() {

        //Initialize Map
        getMasterTimer().clear();
        getGameWorld().setLevelFromMap("LevelClear_Cave.json");

        //Initialize Player
        player = getGameWorld().spawn("player", 90, 550);

        //Skill Charge
        canMove = true;
        canFly = true;
        HealthCharge();
        PotionDrinkCharge();
        RankPointsCap();
        SkillUpgradeCap();
        FireballCharge();
        FireblastCharge();
        FlamestrikeCharge();
        SupernovaCharge();

        //Camera Settings
        getGameScene().getViewport().setBounds(0, 0, 2800, 770);
        getGameScene().getViewport().bindToEntity(player, getWidth() / 2, getHeight() / 2);
    }

    /**
     * Initialize Level: Dive Tutorial
     *
     * @author Earl John Laguardia
     */
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
        PotionDrinkCharge();
        RankPointsCap();
        SkillUpgradeCap();
        FireballCharge();
        FireblastCharge();
        FlamestrikeCharge();
        SupernovaCharge();

        //Camera Settings
        getGameScene().getViewport().setBounds(0, 0, 2800, 4900);
        getGameScene().getViewport().bindToEntity(player, getWidth() / 2, getHeight() / 2);
    }

    /**
     * Initialize Level: Tutorial 1
     *
     * @author Earl John Laguardia
     */
    protected void initTutorial() {

        //Initialize Map
        getMasterTimer().clear();
        getGameWorld().setLevelFromMap("tutorial.json");

        initLevelIndicator_Tutorial();

        //Initialize Player
        player = getGameWorld().spawn("player", 200, 0);

        //Skill Charge
        canMove = true;
        canFly = true;
        HealthCharge();
        PotionDrinkCharge();
        RankPointsCap();
        SkillUpgradeCap();
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

    /**
     * Initialize Level: Tutorial 2
     *
     * @author Earl John Laguardia
     */
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
        PotionDrinkCharge();
        RankPointsCap();
        SkillUpgradeCap();
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

    /**
     * Initialize Level: Dive Tutorial 3
     *
     * @author Earl John Laguardia
     */

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
        PotionDrinkCharge();
        RankPointsCap();
        SkillUpgradeCap();
        FireballCharge();
        FireblastCharge();
        FlamestrikeCharge();
        SupernovaCharge();

        //Camera Settings
        getGameScene().getViewport().setBounds(0, 0, 1400, 4900);
        getGameScene().getViewport().bindToEntity(player, getWidth() / 2, getHeight() / 2);
    }

    /**
     * Initialize Level: Tutorial 3
     *
     * @author Earl John Laguardia
     */

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
        initLevelIndicator_BOSSFIGHT();

        //Initialize Player
        player = getGameWorld().spawn("player", 1890, 0);

        //Skill Charge
        canMove = true;
        canFly = true;
        HealthCharge();
        PotionDrinkCharge();
        RankPointsCap();
        SkillUpgradeCap();
        FireballCharge();
        FireblastCharge();
        FlamestrikeCharge();
        SupernovaCharge();

        //Enemies
        initDarkFlameMasterHP();
        getGameWorld().spawn("boss_darkflamemaster", 770, 440);

        //Camera Settings
        getGameScene().getViewport().setBounds(0, 0, 2100, getHeight());
        getGameScene().getViewport().bindToEntity(player, getWidth() / 2, getHeight() / 2);
    }

    /**
     * Initialize Level: Dive 1-3
     *
     * @author Earl John Laguardia
     */

    protected void initDiveLevel_1x3() {

        //Initialize Map
        getMasterTimer().clear();
        getGameWorld().setLevelFromMap("dive_level_1x3.json");

        //Initialize Player
        player = getGameWorld().spawn("player", 700, 300);

        //Skill Charge
        canMove = true;
        canFly = true;
        HealthCharge();
        PotionDrinkCharge();
        RankPointsCap();
        SkillUpgradeCap();
        FireballCharge();
        FireblastCharge();
        FlamestrikeCharge();
        SupernovaCharge();

        //Camera Settings
        getGameScene().getViewport().setBounds(0, 0, 2800, 4900);
        getGameScene().getViewport().bindToEntity(player, getWidth() / 2, getHeight() / 2);
    }

    /**
     * Initialize Level: Dive 4-6
     *
     * @author Earl John Laguardia
     */

    protected void initDiveLevel_4x6() {

        //Initialize Map
        getMasterTimer().clear();
        getGameWorld().setLevelFromMap("dive_level_4x6.json");

        //Initialize Player
        player = getGameWorld().spawn("player", 700, 300);

        //Skill Charge
        canMove = true;
        canFly = true;
        HealthCharge();
        PotionDrinkCharge();
        RankPointsCap();
        SkillUpgradeCap();
        FireballCharge();
        FireblastCharge();
        FlamestrikeCharge();
        SupernovaCharge();

        //Camera Settings
        getGameScene().getViewport().setBounds(0, 0, 2800, 4900);
        getGameScene().getViewport().bindToEntity(player, getWidth() / 2, getHeight() / 2);
    }

    /**
     * Initialize Level: Dive 7-9
     *
     * @author Earl John Laguardia
     */

    protected void initDiveLevel_7x9() {

        //Initialize Map
        getMasterTimer().clear();
        getGameWorld().setLevelFromMap("dive_level_7x9.json");

        //Initialize Player
        player = getGameWorld().spawn("player", 700, 300);

        //Skill Charge
        canMove = true;
        canFly = true;
        HealthCharge();
        PotionDrinkCharge();
        RankPointsCap();
        SkillUpgradeCap();
        FireballCharge();
        FireblastCharge();
        FlamestrikeCharge();
        SupernovaCharge();

        //Camera Settings
        getGameScene().getViewport().setBounds(0, 0, 2800, 4900);
        getGameScene().getViewport().bindToEntity(player, getWidth() / 2, getHeight() / 2);
    }

    /**
     * Initialize Level: Dive 11-13
     *
     * @author Earl John Laguardia
     */

    protected void initDiveLevel_11x13() {

        //Initialize Map
        getMasterTimer().clear();
        getGameWorld().setLevelFromMap("dive_level_11x13.json");

        //Initialize Player
        player = getGameWorld().spawn("player", 700, 300);

        //Skill Charge
        canMove = true;
        canFly = true;
        HealthCharge();
        PotionDrinkCharge();
        RankPointsCap();
        SkillUpgradeCap();
        FireballCharge();
        FireblastCharge();
        FlamestrikeCharge();
        SupernovaCharge();

        //Camera Settings
        getGameScene().getViewport().setBounds(0, 0, 2800, 4900);
        getGameScene().getViewport().bindToEntity(player, getWidth() / 2, getHeight() / 2);
    }

    /**
     * Initialize Level: Dive 14-16
     *
     * @author Earl John Laguardia
     */

    protected void initDiveLevel_14x16() {

        //Initialize Map
        getMasterTimer().clear();
        getGameWorld().setLevelFromMap("dive_level_14x16.json");

        //Initialize Player
        player = getGameWorld().spawn("player", 700, 300);

        //Skill Charge
        canMove = true;
        canFly = true;
        HealthCharge();
        PotionDrinkCharge();
        RankPointsCap();
        SkillUpgradeCap();
        FireballCharge();
        FireblastCharge();
        FlamestrikeCharge();
        SupernovaCharge();

        //Camera Settings
        getGameScene().getViewport().setBounds(0, 0, 2800, 4900);
        getGameScene().getViewport().bindToEntity(player, getWidth() / 2, getHeight() / 2);
    }

    /**
     * Initialize Level: Dive 17-19
     *
     * @author Earl John Laguardia
     */

    protected void initDiveLevel_17x19() {

        //Initialize Map
        getMasterTimer().clear();
        getGameWorld().setLevelFromMap("dive_level_17x19.json");

        //Initialize Player
        player = getGameWorld().spawn("player", 700, 300);

        //Skill Charge
        canMove = true;
        canFly = true;
        HealthCharge();
        PotionDrinkCharge();
        RankPointsCap();
        SkillUpgradeCap();
        FireballCharge();
        FireblastCharge();
        FlamestrikeCharge();
        SupernovaCharge();

        //Camera Settings
        getGameScene().getViewport().setBounds(0, 0, 2800, 4900);
        getGameScene().getViewport().bindToEntity(player, getWidth() / 2, getHeight() / 2);
    }

    /**
     * Initialize Level: 1
     *
     * @author Earl John Laguardia
     */

    protected void initLevel_1() {

        //Initialize Map
        getMasterTimer().clear();
        getGameWorld().setLevelFromMap("level_1.json");

        initLevelIndicator_1();

        //Initialize Player
        player = getGameWorld().spawn("player", 200, 0);

        //Skill Charge
        canMove = true;
        canFly = true;
        HealthCharge();
        PotionDrinkCharge();
        RankPointsCap();
        SkillUpgradeCap();
        FireballCharge();
        FireblastCharge();
        FlamestrikeCharge();
        SupernovaCharge();

        //Enemies
        getGameWorld().spawn("enemy_red", 900, 460);
        getGameWorld().spawn("enemy_red", 1900, 300);

        getGameWorld().spawn("enemy_blue", 3545, 545);
        getGameWorld().spawn("enemy_blue", 3960, 545);
        getGameWorld().spawn("enemy_blue", 4375, 545);


        //Camera Settings
        getGameScene().getViewport().setBounds(0, 0, 5600, 770);
        getGameScene().getViewport().bindToEntity(player, getWidth() / 2, getHeight() / 2);
    }

    /**
     * Initialize Level: 2
     *
     * @author Earl John Laguardia
     */

    protected void initLevel_2() {

        //Initialize Map
        getMasterTimer().clear();
        getGameWorld().setLevelFromMap("level_2.json");

        initLevelIndicator_2();

        //Initialize Player
        player = getGameWorld().spawn("player", 180, 120);

        //Skill Charge
        canMove = true;
        canFly = true;
        HealthCharge();
        PotionDrinkCharge();
        RankPointsCap();
        SkillUpgradeCap();
        FireballCharge();
        FireblastCharge();
        FlamestrikeCharge();
        SupernovaCharge();

        //Enemies
        getGameWorld().spawn("enemy_red", 140, 500);
        getGameWorld().spawn("enemy_red", 1130, 500);
        getGameWorld().spawn("enemy_red", 2757, 130);

        getGameWorld().spawn("enemy_blue", 2660, 545);
        getGameWorld().spawn("enemy_blue", 3080, 545);
        getGameWorld().spawn("enemy_blue", 4445, 545);


        //Camera Settings
        getGameScene().getViewport().setBounds(0, 0, 4900, 770);
        getGameScene().getViewport().bindToEntity(player, getWidth() / 2, getHeight() / 2);
    }

    /**
     * Initialize Level: 3
     *
     * @author Earl John Laguardia
     */

    protected void initLevel_3() {

        //Initialize Map
        getMasterTimer().clear();
        getGameWorld().setLevelFromMap("level_3.json");

        initLevelIndicator_3();

        //Initialize Player
        player = getGameWorld().spawn("player", 140, 480);

        //Skill Charge
        canMove = true;
        canFly = true;
        HealthCharge();
        PotionDrinkCharge();
        RankPointsCap();
        SkillUpgradeCap();
        FireballCharge();
        FireblastCharge();
        FlamestrikeCharge();
        SupernovaCharge();

        //Enemies
        getGameWorld().spawn("enemy_red", 1600, 480);
        getGameWorld().spawn("enemy_red", 2500, 550);
        getGameWorld().spawn("enemy_red", 3320, 170);

        getGameWorld().spawn("enemy_blue", 3210, 520);
        getGameWorld().spawn("enemy_blue", 3440, 520);
        getGameWorld().spawn("enemy_blue", 4030, 520);


        //Camera Settings
        getGameScene().getViewport().setBounds(0, 0, 4900, 770);
        getGameScene().getViewport().bindToEntity(player, getWidth() / 2, getHeight() / 2);
    }

    /**
     * Initialize Level: 4
     *
     * @author Earl John Laguardia
     */

    protected void initLevel_4() {

        //Initialize Map
        getMasterTimer().clear();
        getGameWorld().setLevelFromMap("level_4.json");

        initLevelIndicator_4();

        //Initialize Player
        player = getGameWorld().spawn("player", 200, 0);

        //Skill Charge
        canMove = true;
        canFly = true;
        HealthCharge();
        PotionDrinkCharge();
        RankPointsCap();
        SkillUpgradeCap();
        FireballCharge();
        FireblastCharge();
        FlamestrikeCharge();
        SupernovaCharge();

        //Enemies
        getGameWorld().spawn("enemy_red", 550, 490);
        getGameWorld().spawn("enemy_red", 1900, 300);

        getGameWorld().spawn("enemy_blue", 1750, 500);
        getGameWorld().spawn("enemy_blue", 3690, 520);
        getGameWorld().spawn("enemy_blue", 4100, 520);
        getGameWorld().spawn("enemy_blue", 4520, 520);


        //Camera Settings
        getGameScene().getViewport().setBounds(0, 0, 4900, 770);
        getGameScene().getViewport().bindToEntity(player, getWidth() / 2, getHeight() / 2);
    }

    /**
     * Initialize Level: 5
     *
     * @author Earl John Laguardia
     */

    protected void initLevel_5() {

        //Initialize Map
        getMasterTimer().clear();
        getGameWorld().setLevelFromMap("level_5.json");

        initLevelIndicator_5();

        //Initialize Player
        player = getGameWorld().spawn("player", 555, 550);

        //Skill Charge
        canMove = true;
        canFly = true;
        HealthCharge();
        PotionDrinkCharge();
        RankPointsCap();
        SkillUpgradeCap();
        FireballCharge();
        FireblastCharge();
        FlamestrikeCharge();
        SupernovaCharge();

        //Enemies
        getGameWorld().spawn("enemy_red", 555, 300);
        getGameWorld().spawn("enemy_red", 3300, 460);

        getGameWorld().spawn("enemy_blue", 1360, 550);
        getGameWorld().spawn("enemy_blue", 2100, 300);
        getGameWorld().spawn("enemy_blue", 4100, 530);


        //Camera Settings
        getGameScene().getViewport().setBounds(0, 0, 4900, 770);
        getGameScene().getViewport().bindToEntity(player, getWidth() / 2, getHeight() / 2);
    }

    /**
     * Initialize Level: 6
     *
     * @author Earl John Laguardia
     */

    protected void initLevel_6() {

        //Initialize Map
        getMasterTimer().clear();
        getGameWorld().setLevelFromMap("level_6.json");

        initLevelIndicator_6();

        //Initialize Player
        player = getGameWorld().spawn("player", 90, 490);

        //Skill Charge
        canMove = true;
        canFly = true;
        HealthCharge();
        PotionDrinkCharge();
        RankPointsCap();
        SkillUpgradeCap();
        FireballCharge();
        FireblastCharge();
        FlamestrikeCharge();
        SupernovaCharge();

        //Enemies
        getGameWorld().spawn("enemy_red", 1335, 210);
        getGameWorld().spawn("enemy_red", 4020, 420);

        getGameWorld().spawn("enemy_blue", 420, 525);

        getGameWorld().spawn("enemy_green", 1260, 560);
        getGameWorld().spawn("enemy_green", 2360, 560);
        getGameWorld().spawn("enemy_green", 3000, 560);
        getGameWorld().spawn("enemy_green", 2565, 420);
        getGameWorld().spawn("enemy_green", 3425, 210);
        getGameWorld().spawn("enemy_green", 3990, 210);


        //Camera Settings
        getGameScene().getViewport().setBounds(0, 0, 4900, 770);
        getGameScene().getViewport().bindToEntity(player, getWidth() / 2, getHeight() / 2);
    }

    /**
     * Initialize Level: 7
     *
     * @author Earl John Laguardia
     */

    protected void initLevel_7() {

        //Initialize Map
        getMasterTimer().clear();
        getGameWorld().setLevelFromMap("level_7.json");

        initLevelIndicator_7();

        //Initialize Player
        player = getGameWorld().spawn("player", 200, 1);

        //Skill Charge
        canMove = true;
        canFly = true;
        HealthCharge();
        PotionDrinkCharge();
        RankPointsCap();
        SkillUpgradeCap();
        FireballCharge();
        FireblastCharge();
        FlamestrikeCharge();
        SupernovaCharge();

        //Enemies
        getGameWorld().spawn("enemy_red", 1400, 490);
        getGameWorld().spawn("enemy_red", 2490, 350);
        getGameWorld().spawn("enemy_red", 3550, 450);
        getGameWorld().spawn("enemy_red", 3750, 450);

        getGameWorld().spawn("enemy_blue", 1420, 525);

        getGameWorld().spawn("enemy_green", 490, 560);
        getGameWorld().spawn("enemy_green", 2660, 560);
        getGameWorld().spawn("enemy_green", 5100, 560);


        //Camera Settings
        getGameScene().getViewport().setBounds(0, 0, 5600, 770);
        getGameScene().getViewport().bindToEntity(player, getWidth() / 2, getHeight() / 2);
    }

    /**
     * Initialize Level: 8
     *
     * @author Earl John Laguardia
     */

    protected void initLevel_8() {

        //Initialize Map
        getMasterTimer().clear();
        getGameWorld().setLevelFromMap("level_8.json");

        initLevelIndicator_8();

        //Initialize Player
        player = getGameWorld().spawn("player", 10, 200);

        //Skill Charge
        canMove = true;
        canFly = true;
        HealthCharge();
        PotionDrinkCharge();
        RankPointsCap();
        SkillUpgradeCap();
        FireballCharge();
        FireblastCharge();
        FlamestrikeCharge();
        SupernovaCharge();

        //Enemies
        getGameWorld().spawn("enemy_red", 10, 490);
        getGameWorld().spawn("enemy_red", 975, 465);
        getGameWorld().spawn("enemy_red", 3260, 140);
        getGameWorld().spawn("enemy_red", 3175, 520);

        getGameWorld().spawn("enemy_blue", 1580, 610);
        getGameWorld().spawn("enemy_blue", 1820, 610);
        getGameWorld().spawn("enemy_blue", 4325, 420);



        //Camera Settings
        getGameScene().getViewport().setBounds(0, 0, 4900, 770);
        getGameScene().getViewport().bindToEntity(player, getWidth() / 2, getHeight() / 2);
    }

    /**
     * Initialize Level: 9
     *
     * @author Earl John Laguardia
     */

    protected void initLevel_9() {

        //Initialize Map
        getMasterTimer().clear();
        getGameWorld().setLevelFromMap("level_9.json");

        initLevelIndicator_9();

        //Initialize Player
        player = getGameWorld().spawn("player", 80, 490);

        //Skill Charge
        canMove = true;
        canFly = true;
        HealthCharge();
        PotionDrinkCharge();
        RankPointsCap();
        SkillUpgradeCap();
        FireballCharge();
        FireblastCharge();
        FlamestrikeCharge();
        SupernovaCharge();

        //Enemies
        getGameWorld().spawn("enemy_red", 820, 420);
        getGameWorld().spawn("enemy_red", 2900, 480);


        getGameWorld().spawn("enemy_green", 2025, 420);
        getGameWorld().spawn("enemy_green", 3850, 420);
        getGameWorld().spawn("enemy_green", 4175, 180);




        //Camera Settings
        getGameScene().getViewport().setBounds(0, 0, 4900, 770);
        getGameScene().getViewport().bindToEntity(player, getWidth() / 2, getHeight() / 2);
    }

    /**
     * Initialize Level: 11
     *
     * @author Earl John Laguardia
     */

    protected void initLevel_11() {

        //Initialize Map
        getMasterTimer().clear();
        getGameWorld().setLevelFromMap("level_11.json");

        initLevelIndicator_11();

        //Initialize Player
        player = getGameWorld().spawn("player", 140, 15);

        //Skill Charge
        canMove = true;
        canFly = true;
        HealthCharge();
        PotionDrinkCharge();
        RankPointsCap();
        SkillUpgradeCap();
        FireballCharge();
        FireblastCharge();
        FlamestrikeCharge();
        SupernovaCharge();

        //Enemies
        getGameWorld().spawn("enemy_red", 420, 550);
        getGameWorld().spawn("enemy_red", 1100, 550);
        getGameWorld().spawn("enemy_red", 2200, 230);
        getGameWorld().spawn("enemy_red", 2500, 590);
        getGameWorld().spawn("enemy_red", 4000, 590);
        getGameWorld().spawn("enemy_red", 4840, 60);
        getGameWorld().spawn("enemy_red", 5320, 200);
        getGameWorld().spawn("enemy_red", 6750, 500);

        getGameWorld().spawn("enemy_green", 5600, 560);
        getGameWorld().spawn("enemy_green", 3980, 420);
        getGameWorld().spawn("enemy_green", 4835, 280);


        //Camera Settings
        getGameScene().getViewport().setBounds(0, 0, 7000, 770);
        getGameScene().getViewport().bindToEntity(player, getWidth() / 2, getHeight() / 2);
    }

    /**
     * Initialize Level: 12
     *
     * @author Earl John Laguardia
     */

    protected void initLevel_12() {

        //Initialize Map
        getMasterTimer().clear();
        getGameWorld().setLevelFromMap("level_12.json");

        initLevelIndicator_12();

        //Initialize Player
        player = getGameWorld().spawn("player", 20, 330);

        //Skill Charge
        canMove = true;
        canFly = true;
        HealthCharge();
        PotionDrinkCharge();
        RankPointsCap();
        SkillUpgradeCap();
        FireballCharge();
        FireblastCharge();
        FlamestrikeCharge();
        SupernovaCharge();

        //Enemies
        getGameWorld().spawn("enemy_red", 460, 490);
        getGameWorld().spawn("enemy_red", 530, 70);
        getGameWorld().spawn("enemy_red", 1370, 370);
        getGameWorld().spawn("enemy_red", 4060, 350);
        getGameWorld().spawn("enemy_red", 5880, 550);
        getGameWorld().spawn("enemy_red", 6580, 550);

        getGameWorld().spawn("enemy_blue", 1300, 430);
        getGameWorld().spawn("enemy_blue", 3150, 445);
        getGameWorld().spawn("enemy_blue", 3290, 445);
        getGameWorld().spawn("enemy_blue", 3435, 445);

        getGameWorld().spawn("enemy_green", 6160, 140);



        //Camera Settings
        getGameScene().getViewport().setBounds(0, 0, 7000, 770);
        getGameScene().getViewport().bindToEntity(player, getWidth() / 2, getHeight() / 2);
    }

    /**
     * Initialize Level: 13
     *
     * @author Earl John Laguardia
     */

    protected void initLevel_13() {

        //Initialize Map
        getMasterTimer().clear();
        getGameWorld().setLevelFromMap("level_13.json");

        initLevelIndicator_13();

        //Initialize Player
        player = getGameWorld().spawn("player", 70, 130);

        //Skill Charge
        canMove = true;
        canFly = true;
        HealthCharge();
        PotionDrinkCharge();
        RankPointsCap();
        SkillUpgradeCap();
        FireballCharge();
        FireblastCharge();
        FlamestrikeCharge();
        SupernovaCharge();

        //Enemies
        getGameWorld().spawn("enemy_red", 840, 390);
        getGameWorld().spawn("enemy_red", 2070, 230);
        getGameWorld().spawn("enemy_red", 1650, 480);
        getGameWorld().spawn("enemy_red", 2570, 400);
        getGameWorld().spawn("enemy_red", 3940, 510);
        getGameWorld().spawn("enemy_red", 5380, 200);
        getGameWorld().spawn("enemy_red", 6500, 130);

        getGameWorld().spawn("enemy_blue", 400, 530);
        getGameWorld().spawn("enemy_blue", 1400, 200);
        getGameWorld().spawn("enemy_blue", 3710, 210);
        getGameWorld().spawn("enemy_blue", 5180, 2150);

        getGameWorld().spawn("enemy_green", 4900, 560);
        getGameWorld().spawn("enemy_green", 1335, 560);
        getGameWorld().spawn("enemy_green", 3950, 280);

        //Camera Settings
        getGameScene().getViewport().setBounds(0, 0, 7000, 770);
        getGameScene().getViewport().bindToEntity(player, getWidth() / 2, getHeight() / 2);
    }

    /**
     * Initialize Level: 14
     *
     * @author Earl John Laguardia
     */

    protected void initLevel_14() {

        //Initialize Map
        getMasterTimer().clear();
        getGameWorld().setLevelFromMap("level_14.json");

        initLevelIndicator_14();

        //Initialize Player
        player = getGameWorld().spawn("player", 2310, 5);

        //Skill Charge
        canMove = true;
        canFly = true;
        HealthCharge();
        PotionDrinkCharge();
        RankPointsCap();
        SkillUpgradeCap();
        FireballCharge();
        FireblastCharge();
        FlamestrikeCharge();
        SupernovaCharge();

        //Enemies
        getGameWorld().spawn("enemy_red", 2075, 255);
        getGameWorld().spawn("enemy_red", 1400, 130);
        getGameWorld().spawn("enemy_red", 560, 590);
        getGameWorld().spawn("enemy_red", 1960, 590);
        getGameWorld().spawn("enemy_red", 2930, 225);
        getGameWorld().spawn("enemy_red", 3500, 230);
        getGameWorld().spawn("enemy_red", 4160, 100);
        getGameWorld().spawn("enemy_red", 4575, 100);
        getGameWorld().spawn("enemy_red", 4450, 560);
        getGameWorld().spawn("enemy_red", 5600, 150);
        getGameWorld().spawn("enemy_red", 5600, 380);
        getGameWorld().spawn("enemy_red", 6500, 580);

        getGameWorld().spawn("enemy_blue", 5320, 350);
        getGameWorld().spawn("enemy_blue", 6090, 350);

        getGameWorld().spawn("enemy_green", 1355, 420);
        getGameWorld().spawn("enemy_green", 2870, 420);
        getGameWorld().spawn("enemy_green", 3850, 560);
        getGameWorld().spawn("enemy_green", 4655, 420);
        getGameWorld().spawn("enemy_green", 5600, 560);

        //Camera Settings
        getGameScene().getViewport().setBounds(0, 0, 7000, 770);
        getGameScene().getViewport().bindToEntity(player, getWidth() / 2, getHeight() / 2);
    }

    /**
     * Initialize Level: 15
     *
     * @author Earl John Laguardia
     */

    protected void initLevel_15() {

        //Initialize Map
        getMasterTimer().clear();
        getGameWorld().setLevelFromMap("level_15.json");

        initLevelIndicator_15();

        //Initialize Player
        player = getGameWorld().spawn("player", 2, 180);

        //Skill Charge
        canMove = true;
        canFly = true;
        HealthCharge();
        PotionDrinkCharge();
        RankPointsCap();
        SkillUpgradeCap();
        FireballCharge();
        FireblastCharge();
        FlamestrikeCharge();
        SupernovaCharge();

        //Enemies
        getGameWorld().spawn("enemy_red", 490, 490);
        getGameWorld().spawn("enemy_red", 2520, 420);
        getGameWorld().spawn("enemy_red", 3320, 515);
        getGameWorld().spawn("enemy_red", 3750, 515);
        getGameWorld().spawn("enemy_red", 4300, 500);
        getGameWorld().spawn("enemy_red", 4865, 380);
        getGameWorld().spawn("enemy_red", 5385, 380);
        getGameWorld().spawn("enemy_red", 5985, 380);
        getGameWorld().spawn("enemy_red", 6535, 310);

        getGameWorld().spawn("enemy_blue", 140, 350);
        getGameWorld().spawn("enemy_blue", 1100, 350);
        getGameWorld().spawn("enemy_blue", 2100, 350);
        getGameWorld().spawn("enemy_blue", 4020, 300);

        getGameWorld().spawn("enemy_green", 200, 560);
        getGameWorld().spawn("enemy_green", 980, 560);


        //Camera Settings
        getGameScene().getViewport().setBounds(0, 0, 7000, 770);
        getGameScene().getViewport().bindToEntity(player, getWidth() / 2, getHeight() / 2);
    }

    /**
     * Initialize Level: 16
     *
     * @author Earl John Laguardia
     */

    protected void initLevel_16() {

        //Initialize Map
        getMasterTimer().clear();
        getGameWorld().setLevelFromMap("level_16.json");

        initLevelIndicator_16();

        //Initialize Player
        player = getGameWorld().spawn("player", 5, 275);

        //Skill Charge
        canMove = true;
        canFly = true;
        HealthCharge();
        PotionDrinkCharge();
        RankPointsCap();
        SkillUpgradeCap();
        FireballCharge();
        FireblastCharge();
        FlamestrikeCharge();
        SupernovaCharge();

        //Enemies
        getGameWorld().spawn("enemy_red", 1000, 300);
        getGameWorld().spawn("enemy_red", 1260, 600);
        getGameWorld().spawn("enemy_red", 2245, 230);
        getGameWorld().spawn("enemy_red", 3230, 210);
        getGameWorld().spawn("enemy_red", 3800, 120);
        getGameWorld().spawn("enemy_red", 4359, 120);
        getGameWorld().spawn("enemy_red", 5920, 430);
        getGameWorld().spawn("enemy_red", 6100, 430);

        getGameWorld().spawn("enemy_blue", 280, 470);
        getGameWorld().spawn("enemy_blue", 630, 350);
        getGameWorld().spawn("enemy_blue", 770, 350);
        getGameWorld().spawn("enemy_blue", 1890, 420);
        getGameWorld().spawn("enemy_blue", 3570, 350);

        getGameWorld().spawn("enemy_green", 500, 560);
        getGameWorld().spawn("enemy_green", 1370, 420);
        getGameWorld().spawn("enemy_green", 2740, 420);
        getGameWorld().spawn("enemy_green", 2040, 560);
        getGameWorld().spawn("enemy_green", 2640, 560);
        getGameWorld().spawn("enemy_green", 3600, 560);
        getGameWorld().spawn("enemy_green", 4500, 560);

        //Camera Settings
        getGameScene().getViewport().setBounds(0, 0, 7000, 770);
        getGameScene().getViewport().bindToEntity(player, getWidth() / 2, getHeight() / 2);
    }

    /**
     * Initialize Level: 17
     *
     * @author Earl John Laguardia
     */

    protected void initLevel_17() {

        //Initialize Map
        getMasterTimer().clear();
        getGameWorld().setLevelFromMap("level_17.json");

        initLevelIndicator_17();

        //Initialize Player
        player = getGameWorld().spawn("player", 150, 5);

        //Skill Charge
        canMove = true;
        canFly = true;
        HealthCharge();
        PotionDrinkCharge();
        RankPointsCap();
        SkillUpgradeCap();
        FireballCharge();
        FireblastCharge();
        FlamestrikeCharge();
        SupernovaCharge();

        //Enemies
        getGameWorld().spawn("enemy_red", 560, 530);
        getGameWorld().spawn("enemy_red", 1100, 530);
        getGameWorld().spawn("enemy_red", 1500, 530);
        getGameWorld().spawn("enemy_red", 2010, 310);
        getGameWorld().spawn("enemy_red", 2010, 540);
        getGameWorld().spawn("enemy_red", 3600, 540);
        getGameWorld().spawn("enemy_red", 3800, 540);
        getGameWorld().spawn("enemy_red", 5100, 540);
        getGameWorld().spawn("enemy_red", 5400, 140);

        //Camera Settings
        getGameScene().getViewport().setBounds(0, 0, 7000, 770);
        getGameScene().getViewport().bindToEntity(player, getWidth() / 2, getHeight() / 2);
    }

    /**
     * Initialize Level: 18
     *
     * @author Earl John Laguardia
     */

    protected void initLevel_18() {

        //Initialize Map
        getMasterTimer().clear();
        getGameWorld().setLevelFromMap("level_18.json");

        initLevelIndicator_18();

        //Initialize Player
        player = getGameWorld().spawn("player", 15, 340);

        //Skill Charge
        canMove = true;
        canFly = true;
        HealthCharge();
        PotionDrinkCharge();
        RankPointsCap();
        SkillUpgradeCap();
        FireballCharge();
        FireblastCharge();
        FlamestrikeCharge();
        SupernovaCharge();

        //Enemies
        getGameWorld().spawn("enemy_red", 400, 450);
        getGameWorld().spawn("enemy_red", 900, 450);
        getGameWorld().spawn("enemy_red", 1300, 450);
        getGameWorld().spawn("enemy_red", 1700, 450);
        getGameWorld().spawn("enemy_red", 2100, 450);
        getGameWorld().spawn("enemy_red", 2700, 450);
        getGameWorld().spawn("enemy_red", 3500, 450);
        getGameWorld().spawn("enemy_red", 3900, 450);
        getGameWorld().spawn("enemy_red", 4700, 145);
        getGameWorld().spawn("enemy_red", 6000, 340);
        getGameWorld().spawn("enemy_red", 6400, 340);

        //Camera Settings
        getGameScene().getViewport().setBounds(0, 0, 7000, 770);
        getGameScene().getViewport().bindToEntity(player, getWidth() / 2, getHeight() / 2);
    }

    /**
     * Initialize Level: 19
     *
     * @author Earl John Laguardia
     */

    protected void initLevel_19() {

        //Initialize Map
        getMasterTimer().clear();
        getGameWorld().setLevelFromMap("level_19.json");

        initLevelIndicator_19();

        //Initialize Player
        player = getGameWorld().spawn("player", 80, 60);

        //Skill Charge
        canMove = true;
        canFly = true;
        HealthCharge();
        PotionDrinkCharge();
        RankPointsCap();
        SkillUpgradeCap();
        FireballCharge();
        FireblastCharge();
        FlamestrikeCharge();
        SupernovaCharge();

        //Enemies
        getGameWorld().spawn("enemy_red", 300, 500);
        getGameWorld().spawn("enemy_red", 600, 500);
        getGameWorld().spawn("enemy_red", 900, 500);
        getGameWorld().spawn("enemy_red", 2300, 500);
        getGameWorld().spawn("enemy_red", 2600, 500);
        getGameWorld().spawn("enemy_red", 2900, 500);
        getGameWorld().spawn("enemy_red", 4400, 500);
        getGameWorld().spawn("enemy_red", 5100, 500);
        getGameWorld().spawn("enemy_red", 6160, 560);
        getGameWorld().spawn("enemy_red", 5775, 370);
        getGameWorld().spawn("enemy_red", 6585, 370);
        getGameWorld().spawn("enemy_red", 5900, 140);
        getGameWorld().spawn("enemy_red", 6220, 140);

        getGameWorld().spawn("enemy_green", 500, 60);
        getGameWorld().spawn("enemy_green", 2400, 60);
        getGameWorld().spawn("enemy_green", 4500, 60);

        //Camera Settings
        getGameScene().getViewport().setBounds(0, 0, 7000, 770);
        getGameScene().getViewport().bindToEntity(player, getWidth() / 2, getHeight() / 2);
    }

    /**
     * Initialize Level: Dive Boss 1
     *
     * @author Earl John Laguardia
     */

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
        PotionDrinkCharge();
        RankPointsCap();
        SkillUpgradeCap();
        FireballCharge();
        FireblastCharge();
        FlamestrikeCharge();
        SupernovaCharge();

        //Camera Settings
        getGameScene().getViewport().setBounds(0, 0, 2800, 4900);
        getGameScene().getViewport().bindToEntity(player, getWidth() / 2, getHeight() / 2);
    }

    /**
     * Initialize Level: Boss 1
     *
     * @author Earl John Laguardia
     */

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
        initLevelIndicator_BOSSFIGHT();


        //Initialize Player
        player = getGameWorld().spawn("player", 200, 0);

        //Skill Charge
        canMove = true;
        canFly = true;
        HealthCharge();
        PotionDrinkCharge();
        RankPointsCap();
        SkillUpgradeCap();
        FireballCharge();
        FireblastCharge();
        FlamestrikeCharge();
        SupernovaCharge();

        //Enemies
        initRhatbuHP();

        getGameWorld().spawn("boss_rhatbu", 3500, 300);
        getGameWorld().spawn("enemy_blue", 1180, 530);
        getGameWorld().spawn("enemy_blue", 1470, 530);

        //Camera Settings
        getGameScene().getViewport().setBounds(0, 0, 4200, 770);
        getGameScene().getViewport().bindToEntity(player, getWidth() / 2, getHeight() / 2);
    }

    /**
     * Initialize Level: Dive Boss 2
     *
     * @author Earl John Laguardia
     */

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
        PotionDrinkCharge();
        RankPointsCap();
        SkillUpgradeCap();
        FireballCharge();
        FireblastCharge();
        FlamestrikeCharge();
        SupernovaCharge();

        //Camera Settings
        getGameScene().getViewport().setBounds(0, 0, 2800, 4900);
        getGameScene().getViewport().bindToEntity(player, getWidth() / 2, getHeight() / 2);
    }

    /**
     * Initialize Level: Boss 2
     *
     * @author Earl John Laguardia
     */

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
        initLevelIndicator_BOSSFIGHT();

        //Initialize Player
        player = getGameWorld().spawn("player", 190, 0);

        //Skill Charge
        canMove = true;
        canFly = true;
        HealthCharge();
        PotionDrinkCharge();
        RankPointsCap();
        SkillUpgradeCap();
        FireballCharge();
        FireblastCharge();
        FlamestrikeCharge();
        SupernovaCharge();

        //Enemies
        initBedjHP();
        getGameWorld().spawn("boss_bedj", 1880, 370);

        //Camera Settings
        getGameScene().getViewport().setBounds(0, 0, 2800, 770);
        getGameScene().getViewport().bindToEntity(player, getWidth() / 2, getHeight() / 2);
    }

    /**
     * Initialize Level: Dive Final Boss
     *
     * @author Earl John Laguardia
     */

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
        PotionDrinkCharge();
        RankPointsCap();
        SkillUpgradeCap();
        FireballCharge();
        FireblastCharge();
        FlamestrikeCharge();
        SupernovaCharge();

        //Camera Settings
        getGameScene().getViewport().setBounds(0, 0, 2800, 4900);
        getGameScene().getViewport().bindToEntity(player, getWidth() / 2, getHeight() / 2);
    }

    /**
     * Initialize Level: Final Boss
     *
     * @author Earl John Laguardia
     */

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
        initLevelIndicator_BOSSFIGHT();


        //Initialize Player
        player = getGameWorld().spawn("player", 540, 0);

        //Skill Charge
        canMove = true;
        canFly = true;
        HealthCharge();
        PotionDrinkCharge();
        RankPointsCap();
        SkillUpgradeCap();
        FireballCharge();
        FireblastCharge();
        FlamestrikeCharge();
        SupernovaCharge();

        //Enemies
        initGrimHP();
        getGameWorld().spawn("boss_grim", 2660, 280);
        getGameWorld().spawn("fireballFinal", 2960, 280);

        //Camera Settings
        getGameScene().getViewport().setBounds(0, 0, 3850, 770);
        getGameScene().getViewport().bindToEntity(player, getWidth() / 2, getHeight() / 2);
    }

    // ------------------------------------- <<< Inputs >>> -------------------------------------

    /**
     * Initialize User Input for Movement/Skill usage
     *
     * @author Earl John Laguardia
     */

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

                //Cheat Stats
                getGameState().setValue("Rank", 15);
                getGameState().setValue("Points", 15);
                getGameState().setValue("Gold", 9999);
                getGameState().setValue("Potion", 5);

                //Cheat Skills
                getGameState().setValue("FireblastLevel", 1);
                getGameState().setValue("FlamestrikeLevel", 1);
                getGameState().setValue("SupernovaLevel", 1);
                godmode = true;

                //Cheat Map Level Completion
                getGameState().setValue("LvlComplete_Tutorial", 1);
                getGameState().setValue("first_upgrade", 1);

                getGameState().setValue("LvlComplete_Level_1x3", 1);
                getGameState().setValue("LvlComplete_Level_4x6", 1);
                getGameState().setValue("LvlComplete_Level_7x9", 1);
                getGameState().setValue("LvlComplete_Level_11x13", 1);
                getGameState().setValue("LvlComplete_Level_14x16", 1);
                getGameState().setValue("LvlComplete_Level_17x19", 1);

                getGameState().setValue("LvlComplete_Rhatbu", 1);
                getGameState().setValue("LvlComplete_Bedj", 1);
                getGameState().setValue("LvlComplete_Grim", 1);

                getDisplay().showMessageBox("Cheats Activated!");
            }
        }, KeyCode.F6);

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

        getInput().addAction(new UserAction("HealthPotion") {
            @Override
            protected void onAction() {
                canDrinkPotion();
                if(getGameState().getInt("PotionCharge") == 100) {
                    Potion();
                    getGameState().setValue("PotionCharge", 0);
                }
            }
        }, KeyCode.F);

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

    /**
     * Method for Charging Health Percentage
     *
     * @author Earl John Laguardia
     */

    //Player Health
    private void HealthCharge() {

        if (godmode) {
            getMasterTimer().runAtInterval(() -> {
                getGameState().increment("Health", +50);
                if (getGameState().getInt("Health") > 100) {
                    getGameState().setValue("Health", 100);
                }
            }, Duration.seconds(0.1));
        } else {
            getMasterTimer().runAtInterval(() -> {
                getGameState().increment("Health", +1);
                if (getGameState().getInt("Health") > 100) {
                    getGameState().setValue("Health", 100);
                }
            }, Duration.seconds(1.2));
        }
    }

    /**
     * Method for Capping Max Rank Points
     *
     * @author Earl John Laguardia
     */

    //Player Max Rank & Points Cap
    private void RankPointsCap() {
        getMasterTimer().runAtInterval(() -> {

            if (getGameState().getInt("Rank") > 15) {
                getGameState().setValue("Rank", 15);
            }
            if (getGameState().getInt("Points") > 15) {
                getGameState().setValue("Points", 15);
            }
            if(getGameState().getInt("Potion") > 5) {
                getGameState().setValue("Potion", 5);
            }
        }, Duration.seconds(0));
    }

    /**
     * Method for Capping Maximum Skill Upgrade
     *
     * @author Earl John Laguardia
     */

    //Skill Upgrade Cap
    private void SkillUpgradeCap() {
        getMasterTimer().runAtInterval(() -> {
            //Fireblast Max Level
            if (getGameState().getInt("FireblastLevel") > 5) {
            getGameState().setValue("FireblastLevel", 5);
             }

            //Flamestrike Max Level
            if (getGameState().getInt("FlamestrikeLevel") > 5) {
            getGameState().setValue("FlamestrikeLevel", 5);
            }

            //Supernova Max Level
            if (getGameState().getInt("SupernovaLevel") > 5) {
                getGameState().setValue("SupernovaLevel", 5);
            }
        }, Duration.seconds(0));
    }

    /**
     * Method for Using Health Potions
     *
     * @author Earl John Laguardia
     */

    //Potion
    private void Potion() {
        if(canPotion) {
            getGameState().increment("Potion", -1);
            getAudioPlayer().playSound("EnergyBoostGet.wav");
            getGameState().setValue("Health", 100);
        }
    }

    /**
     * Method for validation of Health Potion usage (True & False)
     *
     * @author Earl John Laguardia
     */

    private void canDrinkPotion() {
        if(getGameState().getInt("PotionCharge") != 100 || getGameState().getInt("Potion") <= 0) {
            canPotion = false;
            getAudioPlayer().playSound("NoSound.wav");
        } else {
            canPotion = true;
        }
    }

    /**
     * Method for Charging Health Potion Percentage Cooldown
     *
     * @author Earl John Laguardia
     */

    private void PotionDrinkCharge() {
        getMasterTimer().runAtInterval(() -> {
            getGameState().increment("PotionCharge", +40);
            if(getGameState().getInt("PotionCharge") > 100) {
                getGameState().setValue("PotionCharge", 100);
            }
        }, Duration.seconds(0.2));
    }

    /**
     * Method for validating usage of Fireball (True & False)
     *
     * @author Earl John Laguardia
     */

    //Fireball
    private void canShoot() {
        if(getGameState().getInt("Fireball") != 100) {
            canShootValue = false;
            getAudioPlayer().playSound("NoSound.wav");
        } else {
            canShootValue = true;
        }
    }

    /**
     * Method for Charging Fireball Charge Percentage
     *
     * @author Earl John Laguardia
     */

    public void FireballCharge() {
        getMasterTimer().runAtInterval(() -> {
            getGameState().increment("Fireball", +50);
            if(getGameState().getInt("Fireball") > 100) {
                getGameState().setValue("Fireball", 100);
            }
        }, Duration.seconds(0.1));
    }

    /**
     * Method for Shooting Fireball Right
     *
     * @author Earl John Laguardia
     */

    public void fireballRight() {
        if (canShootValue) {
            spawn("fireballRight", player.getPosition());
            getAudioPlayer().playSound("fireball.wav");
        }
    }

    /**
     * Method for Shooting Fireball Left
     *
     * @author Earl John Laguardia
     */

    public void fireballLeft() {
        if (canShootValue) {
            spawn("fireballLeft", player.getPosition());
            getAudioPlayer().playSound("fireball.wav");
        }
    }

    /**
     * Method for validating Fireblast usage (True & False)
     *
     * @author Earl John Laguardia
     */

    //Fireblast
    private void canFireblast() {
        if(getGameState().getInt("Fireblast") != 100) {
            canFireblastValue = false;
            getAudioPlayer().playSound("NoSound.wav");
        } else {
            canFireblastValue = true;
        }
    }

    /**
     * Method for Charging Fireblast charge Percentage
     *
     * @author Earl John Laguardia
     */

    public void FireblastCharge() {
        getMasterTimer().runAtInterval(() -> {
            getGameState().increment("Fireblast", +6);
            if(getGameState().getInt("Fireblast") > 100) {
                getGameState().setValue("Fireblast", 100);
            }
        }, Duration.seconds(0.3));
    }

    /**
     * Method for Shooting Fireblast Right
     *
     * @author Earl John Laguardia
     */

    public void FireblasRight() {
        if (canFireblastValue) {
            spawn("fireblastRight", player.getPosition());
            getAudioPlayer().playSound("Supernova1.wav");
        }
    }

    /**
     * Method for Shooting Fireblast Left
     *
     * @author Earl John Laguardia
     */

    public void FireblasLeft() {
        if (canFireblastValue) {
            spawn("fireblastLeft", player.getPosition());
            getAudioPlayer().playSound("Supernova1.wav");
        }
    }

    /**
     * Method for validating Flamestrike usage (True & False)
     *
     * @author Earl John Laguardia
     */

    //Flamestrike
    private void canFlamestrike() {
        if(getGameState().getInt("Flamestrike") != 100) {
            canFlamestrikeValue = false;
            getAudioPlayer().playSound("NoSound.wav");
        } else {
            canFlamestrikeValue = true;
        }
    }

    /**
     * Method for Charging Flamestrike Charge Percentage
     *
     * @author Earl John Laguardia
     */

    public void FlamestrikeCharge() {
        getMasterTimer().runAtInterval(() -> {
            getGameState().increment("Flamestrike", +4);
            if(getGameState().getInt("Flamestrike") > 100) {
                getGameState().setValue("Flamestrike", 100);
            }
        }, Duration.seconds(0.3));
    }

    /**
     * Method for Shooting Flamestrike
     *
     * @author Earl John Laguardia
     */

    public void Flamestrike() {
        if (canFlamestrikeValue) {
            spawn("flamestrikeRight", player.getPosition());
            spawn("flamestrikeLeft", player.getPosition());
            getAudioPlayer().playSound("Supernova1.wav");
        }
    }

    /**
     * Method for Validating Supernova Usage (True & False)
     *
     * @author Earl John Laguardia
     */

    //Supernova
    private void canSupernova() {
        if(getGameState().getInt("Supernova") != 100) {
            canSupernovaValue = false;
            getAudioPlayer().playSound("NoSound.wav");
        } else {
            canSupernovaValue = true;
        }
    }

    /**
     * Method for Charging Supernova Charge Percentage
     *
     * @author Earl John Laguardia
     */

    public void SupernovaCharge() {
        getMasterTimer().runAtInterval(() -> {
            getGameState().increment("Supernova", +2);
            if (getGameState().getInt("Supernova") > 100) {
                getGameState().setValue("Supernova", 100);
            }
        }, Duration.seconds(0.3));
    }

    /**
     * Method for Shooting Supernova
     *
     * @author Earl John Laguardia
     */

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

    /**
     * Initializess Physics (Collision, Hitboxes etc...)
     *
     * @author Earl John Laguardia
     */

    @Override
    protected void initPhysics() {

        // ----- PlAYER COLLISIONS COMBAT -----

        //PLAYER & ENEMY
        getPhysicsWorld().addCollisionHandler(new CollisionHandler(GameType.PLAYER, GameType.ENEMY) {

            @Override
            protected void onCollisionBegin(Entity player, Entity enemy) {

                //Reduce Player Health
                getGameState().increment("Health", -50);

                //Check Player Health
                if (getGameState().getInt("Health") <= 0) {
                    getGameState().setValue("Health", 100);
                    getMasterTimer().runOnceAfter(() -> {
                       getDisplay().showMessageBox("Returning Back to Base...");
                       getDisplay().showMessageBox("Mission Failed");
                    }, Duration.seconds(0.21));
                    getMasterTimer().runOnceAfter(() -> {
                        initGame();
                    }, Duration.seconds(0.22));
                } else {
                    initDamageIndicator();
                    getAudioPlayer().playSound("PlayerHealth.wav");
                    getGameScene().getViewport().shakeRotational(0.6);
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
                    getGameState().increment("Gold", +10);
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

                //Level 1 Fireblast
                if (getGameState().getInt("FireblastLevel") == 1) {
                    getGameState().increment("EnemyHealth", -250);
                }

                //Level 2 Fireblast
                if (getGameState().getInt("FireblastLevel") == 2) {
                    getGameState().increment("EnemyHealth", -255);
                }

                //Level 3 Fireblast
                if (getGameState().getInt("FireblastLevel") == 3) {
                    getGameState().increment("EnemyHealth", -260);
                }

                //Level 4 Fireblast
                if (getGameState().getInt("FireblastLevel") == 4) {
                    getGameState().increment("EnemyHealth", -265);
                }

                //Level 5 Fireblast
                if (getGameState().getInt("FireblastLevel") == 5) {
                    getGameState().increment("EnemyHealth", -270);
                }

                if (getGameState().getInt("EnemyHealth") <= 0) {
                    getAudioPlayer().playSound("enemyDeath.wav");
                    getGameState().increment("Gold", +10);
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

                //Level 1 Flamestrike
                if (getGameState().getInt("FlamestrikeLevel") == 1) {
                    getGameState().increment("EnemyHealth", -250);
                }

                //Level 2 Flamestrike
                if (getGameState().getInt("FlamestrikeLevel") == 2) {
                    getGameState().increment("EnemyHealth", -255);
                }

                //Level 3 Flamestrike
                if (getGameState().getInt("FlamestrikeLevel") == 3) {
                    getGameState().increment("EnemyHealth", -260);
                }

                //Level 4 Flamestrike
                if (getGameState().getInt("FlamestrikeLevel") == 4) {
                    getGameState().increment("EnemyHealth", -265);
                }

                //Level 5 Flamestrike
                if (getGameState().getInt("FlamestrikeLevel") == 5) {
                    getGameState().increment("EnemyHealth", -270);
                }

                if (getGameState().getInt("EnemyHealth") <= 0) {
                    getAudioPlayer().playSound("enemyDeath.wav");
                    getGameState().increment("Gold", +10);
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

                //Level 1 Supernova
                if (getGameState().getInt("SupernovaLevel") == 1) {
                    getGameState().increment("EnemyHealth", -250);
                }

                //Level 2 Supernova
                if (getGameState().getInt("SupernovaLevel") == 2) {
                    getGameState().increment("EnemyHealth", -255);
                }

                //Level 3 Supernova
                if (getGameState().getInt("SupernovaLevel") == 3) {
                    getGameState().increment("EnemyHealth", -260);
                }

                //Level 4 Supernova
                if (getGameState().getInt("SupernovaLevel") == 4) {
                    getGameState().increment("EnemyHealth", -265);
                }

                //Level 5 Supernova
                if (getGameState().getInt("SupernovaLevel") == 5) {
                    getGameState().increment("EnemyHealth", -270);
                }

                if (getGameState().getInt("EnemyHealth") <= 0) {
                    getAudioPlayer().playSound("enemyDeath.wav");
                    getGameState().increment("Gold", +10);
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

                    //Map Complete
                    getGameState().setValue("LvlComplete_Tutorial", 1);

                    //Message
                    getMasterTimer().runOnceAfter(() -> {
                        initLevelIndicator_LevelCleared();
                    }, Duration.seconds(0.5));

                    getMasterTimer().runOnceAfter(() -> {
                        initNotification_ReturnToBase();
                    }, Duration.seconds(4.5));

                    getMasterTimer().runOnceAfter(() -> {

                        if (getGameState().getInt("Rank") < 15) {
                            getGameState().increment("Rank", +1);
                            getGameState().increment("Points", +1);
                            getDisplay().showMessageBox("Press 'Esc' to PAUSE and SAVE your progress");
                            getDisplay().showMessageBox("Rank Increased!");
                        }
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

                //Level 1 Fireblast
                if (getGameState().getInt("FireblastLevel") == 1) {
                    getGameState().increment("DarkFlameMasterHealth", -250);
                }

                //Level 2 Fireblast
                if (getGameState().getInt("FireblastLevel") == 2) {
                    getGameState().increment("DarkFlameMasterHealth", -255);
                }

                //Level 3 Fireblast
                if (getGameState().getInt("FireblastLevel") == 3) {
                    getGameState().increment("DarkFlameMasterHealth", -260);
                }

                //Level 4 Fireblast
                if (getGameState().getInt("FireblastLevel") == 4) {
                    getGameState().increment("DarkFlameMasterHealth", -265);
                }

                //Level 5 Fireblast
                if (getGameState().getInt("FireblastLevel") == 5) {
                    getGameState().increment("DarkFlameMasterHealth", -270);
                }

                if (getGameState().getInt("DarkFlameMasterHealth") <= 0) {

                    getAudioPlayer().playSound("enemyDeath.wav");
                    fireblast.removeFromWorld();
                    boss_darkflamemaster.removeFromWorld();
                    getGameState().setValue("DarkFlameMasterHealth", 9000);

                    //Map Complete
                    getGameState().setValue("LvlComplete_Tutorial", 1);

                    //Message
                    getMasterTimer().runOnceAfter(() -> {
                        initLevelIndicator_LevelCleared();
                    }, Duration.seconds(0.5));

                    getMasterTimer().runOnceAfter(() -> {
                        initNotification_ReturnToBase();
                    }, Duration.seconds(4.5));

                    getMasterTimer().runOnceAfter(() -> {

                        if (getGameState().getInt("Rank") < 15) {
                            getGameState().increment("Rank", +1);
                            getGameState().increment("Points", +1);
                            getDisplay().showMessageBox("Press 'Esc' to PAUSE and SAVE your progress");
                            getDisplay().showMessageBox("Rank Increased!");
                        }
                        initGame();
                    }, Duration.seconds(9));

                }
            }
        });

        //Dark Flame Master & Flamestrike
        getPhysicsWorld().addCollisionHandler(new CollisionHandler(GameType.FLAMESTRIKE, GameType.BOSS_DARKFLAMEMASTER) {

            @Override
            protected void onCollisionBegin(Entity flamestrike, Entity boss_darkflamemaster) {

                //Kill Dark Flame Master
                getAudioPlayer().playSound("Hit_Collide.wav");

                //Level 1 Flamestrike
                if (getGameState().getInt("FlamestrikeLevel") == 1) {
                    getGameState().increment("DarkFlameMasterHealth", -250);
                }

                //Level 2 Flamestrike
                if (getGameState().getInt("FlamestrikeLevel") == 2) {
                    getGameState().increment("DarkFlameMasterHealth", -255);
                }

                //Level 3 Flamestrike
                if (getGameState().getInt("FlamestrikeLevel") == 3) {
                    getGameState().increment("DarkFlameMasterHealth", -260);
                }

                //Level 4 Flamestrike
                if (getGameState().getInt("FlamestrikeLevel") == 4) {
                    getGameState().increment("DarkFlameMasterHealth", -265);
                }

                //Level 5 Flamestrike
                if (getGameState().getInt("FlamestrikeLevel") == 5) {
                    getGameState().increment("DarkFlameMasterHealth", -270);
                }

                if (getGameState().getInt("DarkFlameMasterHealth") <= 0) {

                    getAudioPlayer().playSound("enemyDeath.wav");
                    flamestrike.removeFromWorld();
                    boss_darkflamemaster.removeFromWorld();
                    getGameState().setValue("DarkFlameMasterHealth", 9000);

                    //Map Complete
                    getGameState().setValue("LvlComplete_Tutorial", 1);

                    //Message
                    getMasterTimer().runOnceAfter(() -> {
                        initLevelIndicator_LevelCleared();
                    }, Duration.seconds(0.5));

                    getMasterTimer().runOnceAfter(() -> {
                        initNotification_ReturnToBase();
                    }, Duration.seconds(4.5));

                    getMasterTimer().runOnceAfter(() -> {

                        if (getGameState().getInt("Rank") < 15) {
                            getGameState().increment("Rank", +1);
                            getGameState().increment("Points", +1);
                            getDisplay().showMessageBox("Press 'Esc' to PAUSE and SAVE your progress");
                            getDisplay().showMessageBox("Rank Increased!");
                        }
                        initGame();
                    }, Duration.seconds(9));

                }
            }
        });

        //Dark Flame Master & Supernova
        getPhysicsWorld().addCollisionHandler(new CollisionHandler(GameType.SUPERNOVA, GameType.BOSS_DARKFLAMEMASTER) {

            @Override
            protected void onCollisionBegin(Entity supernova, Entity boss_darkflamemaster) {

                //Kill Dark Flame Master
                getAudioPlayer().playSound("Hit_Collide.wav");

                //Level 1 Supernova
                if (getGameState().getInt("SupernovaLevel") == 1) {
                    getGameState().increment("DarkFlameMasterHealth", -150);
                }

                //Level 2 Supernova
                if (getGameState().getInt("SupernovaLevel") == 2) {
                    getGameState().increment("DarkFlameMasterHealth", -155);
                }

                //Level 3 Supernova
                if (getGameState().getInt("SupernovaLevel") == 3) {
                    getGameState().increment("DarkFlameMasterHealth", -160);
                }

                //Level 4 Supernova
                if (getGameState().getInt("SupernovaLevel") == 4) {
                    getGameState().increment("DarkFlameMasterHealth", -165);
                }

                //Level 5 Supernova
                if (getGameState().getInt("SupernovaLevel") == 5) {
                    getGameState().increment("DarkFlameMasterHealth", -170);
                }

                if (getGameState().getInt("DarkFlameMasterHealth") <= 0) {

                    getAudioPlayer().playSound("enemyDeath.wav");
                    supernova.removeFromWorld();
                    boss_darkflamemaster.removeFromWorld();
                    getGameState().setValue("DarkFlameMasterHealth", 9000);

                    //Map Complete
                    getGameState().setValue("LvlComplete_Tutorial", 1);

                    //Message
                    getMasterTimer().runOnceAfter(() -> {
                        initLevelIndicator_LevelCleared();
                    }, Duration.seconds(0.5));

                    getMasterTimer().runOnceAfter(() -> {
                        initNotification_ReturnToBase();
                    }, Duration.seconds(4.5));

                    getMasterTimer().runOnceAfter(() -> {

                        if (getGameState().getInt("Rank") < 15) {
                            getGameState().increment("Rank", +1);
                            getGameState().increment("Points", +1);
                            getDisplay().showMessageBox("Press 'Esc' to PAUSE and SAVE your progress");
                            getDisplay().showMessageBox("Rank Increased!");
                        }
                        initGame();
                    }, Duration.seconds(9));

                }
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
                    getGameState().setValue("Health", 100);
                    getMasterTimer().runOnceAfter(() -> {
                        getGameState().setValue("DarkFlameMasterHealth", 9000);
                        getDisplay().showMessageBox("Returning Back to Base...");
                        getDisplay().showMessageBox("Mission Failed");
                    }, Duration.seconds(0.21));
                    getMasterTimer().runOnceAfter(() -> {
                        initGame();
                    }, Duration.seconds(0.22));
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

                    //Map Level Complete
                    getGameState().setValue("LvlComplete_Rhatbu", 1);

                    //Message
                    getMasterTimer().runOnceAfter(() -> {
                        initLevelIndicator_LevelCleared();
                    }, Duration.seconds(0.5));

                    getMasterTimer().runOnceAfter(() -> {
                        initNotification_ReturnToBase();
                    }, Duration.seconds(4.5));

                    getMasterTimer().runOnceAfter(() -> {
                        if (getGameState().getInt("Rank") < 15) {
                            getDisplay().showMessageBox("Rank Increased!");
                            getGameState().increment("Rank", +1);
                            getGameState().increment("Points", +1);
                        }
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

                //Level 1 Fireblast
                if (getGameState().getInt("FireblastLevel") == 1) {
                    getGameState().increment("RhatbuHealth", -250);
                }

                //Level 2 Fireblast
                if (getGameState().getInt("FireblastLevel") == 2) {
                    getGameState().increment("RhatbuHealth", -255);
                }

                //Level 3 Fireblast
                if (getGameState().getInt("FireblastLevel") == 3) {
                    getGameState().increment("RhatbuHealth", -260);
                }

                //Level 4 Fireblast
                if (getGameState().getInt("FireblastLevel") == 4) {
                    getGameState().increment("RhatbuHealth", -265);
                }

                //Level 5 Fireblast
                if (getGameState().getInt("FireblastLevel") == 5) {
                    getGameState().increment("RhatbuHealth", -270);
                }

                if (getGameState().getInt("RhatbuHealth") <= 0) {

                    getAudioPlayer().playSound("enemyDeath.wav");
                    fireblast.removeFromWorld();
                    boss_rhatbu.removeFromWorld();
                    getGameState().increment("Gold", + 500);
                    getGameState().setValue("RhatbuHealth", 15000);

                    //Map Level Complete
                    getGameState().setValue("LvlComplete_Rhatbu", 1);

                    //Message
                    getMasterTimer().runOnceAfter(() -> {
                        initLevelIndicator_LevelCleared();
                    }, Duration.seconds(0.5));

                    getMasterTimer().runOnceAfter(() -> {
                        initNotification_ReturnToBase();
                    }, Duration.seconds(4.5));

                    getMasterTimer().runOnceAfter(() -> {
                        if (getGameState().getInt("Rank") < 15) {
                            getDisplay().showMessageBox("Rank Increased!");
                            getGameState().increment("Rank", +1);
                            getGameState().increment("Points", +1);
                        }
                        initGame();
                    }, Duration.seconds(9));
                }
            }
        });

        //Rhatbu & Flamestrike
        getPhysicsWorld().addCollisionHandler(new CollisionHandler(GameType.FLAMESTRIKE, GameType.BOSS_RHATBU) {

            @Override
            protected void onCollisionBegin(Entity flamestrike, Entity boss_rhatbu) {

                //Kill Rhatbu
                getAudioPlayer().playSound("Hit_Collide.wav");

                //Level 1 Flamestrike
                if (getGameState().getInt("FlamestrikeLevel") == 1) {
                    getGameState().increment("RhatbuHealth", -250);
                }

                //Level 2 Flamestrike
                if (getGameState().getInt("FlamestrikeLevel") == 2) {
                    getGameState().increment("RhatbuHealth", -255);
                }

                //Level 3 Flamestrike
                if (getGameState().getInt("FlamestrikeLevel") == 3) {
                    getGameState().increment("RhatbuHealth", -260);
                }

                //Level 4 Flamestrike
                if (getGameState().getInt("FlamestrikeLevel") == 4) {
                    getGameState().increment("RhatbuHealth", -265);
                }

                //Level 5 Flamestrike
                if (getGameState().getInt("FlamestrikeLevel") == 5) {
                    getGameState().increment("RhatbuHealth", -270);
                }

                if (getGameState().getInt("RhatbuHealth") <= 0) {

                    getAudioPlayer().playSound("enemyDeath.wav");
                    flamestrike.removeFromWorld();
                    boss_rhatbu.removeFromWorld();
                    getGameState().increment("Gold", + 500);
                    getGameState().setValue("RhatbuHealth", 15000);

                    //Map Level Complete
                    getGameState().setValue("LvlComplete_Rhatbu", 1);

                    //Message
                    getMasterTimer().runOnceAfter(() -> {
                        initLevelIndicator_LevelCleared();
                    }, Duration.seconds(0.5));

                    getMasterTimer().runOnceAfter(() -> {
                        initNotification_ReturnToBase();
                    }, Duration.seconds(4.5));

                    getMasterTimer().runOnceAfter(() -> {
                        if (getGameState().getInt("Rank") < 15) {
                            getDisplay().showMessageBox("Rank Increased!");
                            getGameState().increment("Rank", +1);
                            getGameState().increment("Points", +1);
                        }
                        initGame();
                    }, Duration.seconds(9));

                }
            }
        });

        //Rhatbu & Supernova
        getPhysicsWorld().addCollisionHandler(new CollisionHandler(GameType.SUPERNOVA, GameType.BOSS_RHATBU) {

            @Override
            protected void onCollisionBegin(Entity supernova, Entity boss_rhatbu) {

                //Kill Rhatbu
                getAudioPlayer().playSound("Hit_Collide.wav");

                //Level 1 Supernova
                if (getGameState().getInt("SupernovaLevel") == 1) {
                    getGameState().increment("RhatbuHealth", -150);
                }

                //Level 2 Supernova
                if (getGameState().getInt("SupernovaLevel") == 2) {
                    getGameState().increment("RhatbuHealth", -155);
                }

                //Level 3 Supernova
                if (getGameState().getInt("SupernovaLevel") == 3) {
                    getGameState().increment("RhatbuHealth", -160);
                }

                //Level 4 Supernova
                if (getGameState().getInt("SupernovaLevel") == 4) {
                    getGameState().increment("RhatbuHealth", -165);
                }

                //Level 5 Supernova
                if (getGameState().getInt("SupernovaLevel") == 5) {
                    getGameState().increment("RhatbuHealth", -170);
                }

                if (getGameState().getInt("RhatbuHealth") <= 0) {

                    getAudioPlayer().playSound("enemyDeath.wav");
                    supernova.removeFromWorld();
                    boss_rhatbu.removeFromWorld();
                    getGameState().increment("Gold", + 500);
                    getGameState().setValue("RhatbuHealth", 15000);

                    //Map Level Complete
                    getGameState().setValue("LvlComplete_Rhatbu", 1);

                    //Message
                    getMasterTimer().runOnceAfter(() -> {
                        initLevelIndicator_LevelCleared();
                    }, Duration.seconds(0.5));

                    getMasterTimer().runOnceAfter(() -> {
                        initNotification_ReturnToBase();
                    }, Duration.seconds(4.5));

                    getMasterTimer().runOnceAfter(() -> {
                        if (getGameState().getInt("Rank") < 15) {
                            getDisplay().showMessageBox("Rank Increased!");
                            getGameState().increment("Rank", +1);
                            getGameState().increment("Points", +1);
                        }
                        initGame();
                    }, Duration.seconds(9));

                }
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
                    getGameState().setValue("Health", 100);
                    getMasterTimer().runOnceAfter(() -> {
                        getGameState().setValue("RhatbuHealth", 15000);
                        getDisplay().showMessageBox("Returning Back to Base...");
                        getDisplay().showMessageBox("Mission Failed");
                    }, Duration.seconds(0.21));
                    getMasterTimer().runOnceAfter(() -> {
                        initGame();
                    }, Duration.seconds(0.22));
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
                if (getGameState().getInt("BedjHealth") <= 10000) {

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

                    //Map Level Complete
                    getGameState().setValue("LvlComplete_Bedj", 1);

                    //Message
                    getMasterTimer().runOnceAfter(() -> {
                        initLevelIndicator_LevelCleared();
                    }, Duration.seconds(0.5));

                    getMasterTimer().runOnceAfter(() -> {
                        initNotification_ReturnToBase();
                    }, Duration.seconds(4.5));

                    getMasterTimer().runOnceAfter(() -> {
                        if (getGameState().getInt("Rank") < 15) {
                            getDisplay().showMessageBox("Rank Increased!");
                            getGameState().increment("Rank", +1);
                            getGameState().increment("Points", +1);
                        }
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

                //Level 1 Fireblast
                if (getGameState().getInt("FireblastLevel") == 1) {
                    getGameState().increment("BedjHealth", -250);
                }

                //Level 2 Fireblast
                if (getGameState().getInt("FireblastLevel") == 2) {
                    getGameState().increment("BedjHealth", -255);
                }

                //Level 3 Fireblast
                if (getGameState().getInt("FireblastLevel") == 3) {
                    getGameState().increment("BedjHealth", -260);
                }

                //Level 4 Fireblast
                if (getGameState().getInt("FireblastLevel") == 4) {
                    getGameState().increment("BedjHealth", -265);
                }

                //Level 5 Fireblast
                if (getGameState().getInt("FireblastLevel") == 5) {
                    getGameState().increment("BedjHealth", -270);
                }

                if (getGameState().getInt("BedjHealth") <= 0) {

                    getAudioPlayer().playSound("enemyDeath.wav");
                    fireblast.removeFromWorld();
                    boss_bedj.removeFromWorld();
                    getGameState().increment("Gold", + 1500);
                    getGameState().setValue("BedjHealth", 20000);

                    //Map Level Complete
                    getGameState().setValue("LvlComplete_Bedj", 1);

                    //Message
                    getMasterTimer().runOnceAfter(() -> {
                        initLevelIndicator_LevelCleared();
                    }, Duration.seconds(0.5));

                    getMasterTimer().runOnceAfter(() -> {
                        initNotification_ReturnToBase();
                    }, Duration.seconds(4.5));

                    getMasterTimer().runOnceAfter(() -> {
                        if (getGameState().getInt("Rank") < 15) {
                            getDisplay().showMessageBox("Rank Increased!");
                            getGameState().increment("Rank", +1);
                            getGameState().increment("Points", +1);
                        }
                        initGame();
                    }, Duration.seconds(9));

                }
            }
        });

        //Bedj & Flamestrike
        getPhysicsWorld().addCollisionHandler(new CollisionHandler(GameType.FLAMESTRIKE, GameType.BOSS_BEDJ) {

            @Override
            protected void onCollisionBegin(Entity flamestrike, Entity boss_bedj) {

                //Kill Bedj
                getAudioPlayer().playSound("Hit_Collide.wav");

                //Level 1 Flamestrike
                if (getGameState().getInt("FlamestrikeLevel") == 1) {
                    getGameState().increment("BedjHealth", -250);
                }

                //Level 2 Flamestrike
                if (getGameState().getInt("FlamestrikeLevel") == 2) {
                    getGameState().increment("BedjHealth", -255);
                }

                //Level 3 Flamestrike
                if (getGameState().getInt("FlamestrikeLevel") == 3) {
                    getGameState().increment("BedjHealth", -260);
                }

                //Level 4 Flamestrike
                if (getGameState().getInt("FlamestrikeLevel") == 4) {
                    getGameState().increment("BedjHealth", -265);
                }

                //Level 5 Flamestrike
                if (getGameState().getInt("FlamestrikeLevel") == 5) {
                    getGameState().increment("BedjHealth", -270);
                }

                if (getGameState().getInt("BedjHealth") <= 0) {

                    getAudioPlayer().playSound("enemyDeath.wav");
                    flamestrike.removeFromWorld();
                    boss_bedj.removeFromWorld();
                    getGameState().increment("Gold", + 1500);
                    getGameState().setValue("BedjHealth", 20000);

                    //Map Level Complete
                    getGameState().setValue("LvlComplete_Bedj", 1);

                    //Message
                    getMasterTimer().runOnceAfter(() -> {
                        initLevelIndicator_LevelCleared();
                    }, Duration.seconds(0.5));

                    getMasterTimer().runOnceAfter(() -> {
                        initNotification_ReturnToBase();
                    }, Duration.seconds(4.5));

                    getMasterTimer().runOnceAfter(() -> {
                        if (getGameState().getInt("Rank") < 15) {
                            getDisplay().showMessageBox("Rank Increased!");
                            getGameState().increment("Rank", +1);
                            getGameState().increment("Points", +1);
                        }
                        initGame();
                    }, Duration.seconds(9));

                }
            }
        });

        //Bedj & Supernova
        getPhysicsWorld().addCollisionHandler(new CollisionHandler(GameType.SUPERNOVA, GameType.BOSS_BEDJ) {

            @Override
            protected void onCollisionBegin(Entity supernova, Entity boss_bedj) {

                //Kill Bedj
                getAudioPlayer().playSound("Hit_Collide.wav");

                //Level 1 Supernova
                if (getGameState().getInt("SupernovaLevel") == 1) {
                    getGameState().increment("BedjHealth", -150);
                }

                //Level 2 Supernova
                if (getGameState().getInt("SupernovaLevel") == 2) {
                    getGameState().increment("BedjHealth", -155);
                }

                //Level 3 Supernova
                if (getGameState().getInt("SupernovaLevel") == 3) {
                    getGameState().increment("BedjHealth", -160);
                }

                //Level 4 Supernova
                if (getGameState().getInt("SupernovaLevel") == 4) {
                    getGameState().increment("BedjHealth", -165);
                }

                //Level 5 Supernova
                if (getGameState().getInt("SupernovaLevel") == 5) {
                    getGameState().increment("BedjHealth", -170);
                }

                if (getGameState().getInt("BedjHealth") <= 0) {

                    getAudioPlayer().playSound("enemyDeath.wav");
                    supernova.removeFromWorld();
                    boss_bedj.removeFromWorld();
                    getGameState().increment("Gold", + 1500);
                    getGameState().setValue("BedjHealth", 20000);

                    //Map Level Complete
                    getGameState().setValue("LvlComplete_Bedj", 1);

                    //Message
                    getMasterTimer().runOnceAfter(() -> {
                        initLevelIndicator_LevelCleared();
                    }, Duration.seconds(0.5));

                    getMasterTimer().runOnceAfter(() -> {
                        initNotification_ReturnToBase();
                    }, Duration.seconds(4.5));

                    getMasterTimer().runOnceAfter(() -> {
                        if (getGameState().getInt("Rank") < 15) {
                            getDisplay().showMessageBox("Rank Increased!");
                            getGameState().increment("Rank", +1);
                            getGameState().increment("Points", +1);
                        }
                        initGame();
                    }, Duration.seconds(9));

                }
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
                    getGameState().setValue("Health", 100);
                    getMasterTimer().runOnceAfter(() -> {
                        getGameState().setValue("BedjHealth", 20000);
                        getDisplay().showMessageBox("Returning Back to Base...");
                        getDisplay().showMessageBox("Mission Failed");
                    }, Duration.seconds(0.21));
                    getMasterTimer().runOnceAfter(() -> {
                        initGame();
                    }, Duration.seconds(0.22));
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
                if (getGameState().getInt("GrimHealth") <= 7000) {
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
                if (getGameState().getInt("GrimHealth") <= 7000) {
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

                    //Map Level Complete
                    getGameState().setValue("LvlComplete_Grim", 1);

                    //Message
                    getMasterTimer().runOnceAfter(() -> {
                        initLevelIndicator_LevelCleared();
                    }, Duration.seconds(0.5));

                    getMasterTimer().runOnceAfter(() -> {
                        initNotification_ReturnToBase();
                    }, Duration.seconds(4.5));

                    getMasterTimer().runOnceAfter(() -> {
                        if (getGameState().getInt("Rank") < 15) {
                            getDisplay().showMessageBox("Rank Increased!");
                            getGameState().increment("Rank", +1);
                            getGameState().increment("Points", +1);
                        }
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

                //Level 1 Fireblast
                if (getGameState().getInt("FireblastLevel") == 1) {
                    getGameState().increment("GrimHealth", -250);
                }

                //Level 2 Fireblast
                if (getGameState().getInt("FireblastLevel") == 2) {
                    getGameState().increment("GrimHealth", -255);
                }

                //Level 3 Fireblast
                if (getGameState().getInt("FireblastLevel") == 3) {
                    getGameState().increment("GrimHealth", -260);
                }

                //Level 4 Fireblast
                if (getGameState().getInt("FireblastLevel") == 4) {
                    getGameState().increment("GrimHealth", -265);
                }

                //Level 5 Fireblast
                if (getGameState().getInt("FireblastLevel") == 5) {
                    getGameState().increment("GrimHealth", -270);
                }

                if (getGameState().getInt("GrimHealth") <= 0) {

                    getAudioPlayer().playSound("enemyDeath.wav");
                    fireblast.removeFromWorld();
                    boss_grim.removeFromWorld();
                    getGameState().increment("Gold", + 2500);
                    getGameState().setValue("GrimHealth", 25000);

                    //Map Level Complete
                    getGameState().setValue("LvlComplete_Grim", 1);

                    //Message
                    getMasterTimer().runOnceAfter(() -> {
                        initLevelIndicator_LevelCleared();
                    }, Duration.seconds(0.5));

                    getMasterTimer().runOnceAfter(() -> {
                        initNotification_ReturnToBase();
                    }, Duration.seconds(4.5));

                    getMasterTimer().runOnceAfter(() -> {
                        if (getGameState().getInt("Rank") < 15) {
                            getDisplay().showMessageBox("Rank Increased!");
                            getGameState().increment("Rank", +1);
                            getGameState().increment("Points", +1);
                        }
                        initGame();
                    }, Duration.seconds(9));

                }
            }
        });

        //Grim & Flamestrike
        getPhysicsWorld().addCollisionHandler(new CollisionHandler(GameType.FLAMESTRIKE, GameType.BOSS_GRIM) {

            @Override
            protected void onCollisionBegin(Entity flamestrike, Entity boss_grim) {

                //Kill Grim
                getAudioPlayer().playSound("Hit_Collide.wav");

                //Level 1 Flamestrike
                if (getGameState().getInt("FlamestrikeLevel") == 1) {
                    getGameState().increment("GrimHealth", -250);
                }

                //Level 2 Flamestrike
                if (getGameState().getInt("FlamestrikeLevel") == 2) {
                    getGameState().increment("GrimHealth", -255);
                }

                //Level 3 Flamestrike
                if (getGameState().getInt("FlamestrikeLevel") == 3) {
                    getGameState().increment("GrimHealth", -260);
                }

                //Level 4 Flamestrike
                if (getGameState().getInt("FlamestrikeLevel") == 4) {
                    getGameState().increment("GrimHealth", -265);
                }

                //Level 5 Flamestrike
                if (getGameState().getInt("FlamestrikeLevel") == 5) {
                    getGameState().increment("GrimHealth", -270);
                }

                if (getGameState().getInt("GrimHealth") <= 0) {

                    getAudioPlayer().playSound("enemyDeath.wav");
                    flamestrike.removeFromWorld();
                    boss_grim.removeFromWorld();
                    getGameState().increment("Gold", + 2500);
                    getGameState().setValue("GrimHealth", 25000);

                    //Map Level Complete
                    getGameState().setValue("LvlComplete_Grim", 1);

                    //Message
                    getMasterTimer().runOnceAfter(() -> {
                        initLevelIndicator_LevelCleared();
                    }, Duration.seconds(0.5));

                    getMasterTimer().runOnceAfter(() -> {
                        initNotification_ReturnToBase();
                    }, Duration.seconds(4.5));

                    getMasterTimer().runOnceAfter(() -> {
                        if (getGameState().getInt("Rank") < 15) {
                            getDisplay().showMessageBox("Rank Increased!");
                            getGameState().increment("Rank", +1);
                            getGameState().increment("Points", +1);
                        }
                        initGame();
                    }, Duration.seconds(9));

                }
            }
        });

        //Grim & Supernova
        getPhysicsWorld().addCollisionHandler(new CollisionHandler(GameType.SUPERNOVA, GameType.BOSS_GRIM) {

            @Override
            protected void onCollisionBegin(Entity supernova, Entity boss_grim) {

                //Kill Grim
                getAudioPlayer().playSound("Hit_Collide.wav");

                //Level 1 Supernova
                if (getGameState().getInt("SupernovaLevel") == 1) {
                    getGameState().increment("GrimHealth", -150);
                }

                //Level 2 Supernova
                if (getGameState().getInt("SupernovaLevel") == 2) {
                    getGameState().increment("GrimHealth", -155);
                }

                //Level 3 Supernova
                if (getGameState().getInt("SupernovaLevel") == 3) {
                    getGameState().increment("GrimHealth", -160);
                }

                //Level 4 Supernova
                if (getGameState().getInt("SupernovaLevel") == 4) {
                    getGameState().increment("GrimHealth", -165);
                }

                //Level 5 Supernova
                if (getGameState().getInt("SupernovaLevel") == 5) {
                    getGameState().increment("GrimHealth", -170);
                }

                if (getGameState().getInt("GrimHealth") <= 0) {

                    getAudioPlayer().playSound("enemyDeath.wav");
                    supernova.removeFromWorld();
                    boss_grim.removeFromWorld();
                    getGameState().increment("Gold", + 2500);
                    getGameState().setValue("GrimHealth", 25000);

                    //Map Level Complete
                    getGameState().setValue("LvlComplete_Grim", 1);

                    //Message
                    getMasterTimer().runOnceAfter(() -> {
                        initLevelIndicator_LevelCleared();
                    }, Duration.seconds(0.5));

                    getMasterTimer().runOnceAfter(() -> {
                        initNotification_ReturnToBase();
                    }, Duration.seconds(4.5));

                    getMasterTimer().runOnceAfter(() -> {
                        if (getGameState().getInt("Rank") < 15) {
                            getDisplay().showMessageBox("Rank Increased!");
                            getGameState().increment("Rank", +1);
                            getGameState().increment("Points", +1);
                        }
                        initGame();
                    }, Duration.seconds(9));

                }
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
                    getGameState().setValue("Health", 100);
                    getMasterTimer().runOnceAfter(() -> {
                        getGameState().setValue("GrimHealth", 25000);
                        getDisplay().showMessageBox("Returning Back to Base...");
                        getDisplay().showMessageBox("Mission Failed");
                    }, Duration.seconds(0.21));
                    getMasterTimer().runOnceAfter(() -> {
                        initGame();
                    }, Duration.seconds(0.22));
                }
            }
        });

        // ----- PlAYER COLLISIONS MAIN -----

        /**
         * Player & Coin Collision
         *
         * @author Celina Kai
         */

        //PLAYER & COIN
        getPhysicsWorld().addCollisionHandler(new CollisionHandler(GameType.PLAYER, GameType.COIN) {

            @Override
            protected void onCollisionBegin(Entity player, Entity coin) {

                getGameState().increment("Gold", +1);
                getAudioPlayer().playSound("CoinGet.wav");
                coin.removeFromWorld();
            }
        });

        /**
         * Player & Healthboost Collision
         *
         * @author Celina Kai
         */

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

        //PLAYER & FIRST_UPGRADE
        getPhysicsWorld().addCollisionHandler(new CollisionHandler(GameType.PLAYER, GameType.FIRST_UPGRADE) {

            @Override
            protected void onCollisionBegin(Entity player, Entity tutorial_upgrade) {
                if (getGameState().getInt("first_upgrade") == 0) {
                    getDisplay().showMessageBox("You can earn more Points by Ranking Up");
                    getDisplay().showMessageBox("Each upgrade will Increase your Damage");
                    getDisplay().showMessageBox("Use your POINTS to UGPRADE your SKILLS");
                    getGameState().setValue("first_upgrade", 1);
                }
            }
        });

        //PLAYER & SAVE_REMINDER
        getPhysicsWorld().addCollisionHandler(new CollisionHandler(GameType.PLAYER, GameType.SAVE_REMINDER) {

            @Override
            protected void onCollisionBegin(Entity player, Entity save_reminder) {

                save_reminder.removeFromWorld();
                initNotification_SaveReminder();

            }
        });

        //PLAYER & COMPLETE_TUTORIAL
        getPhysicsWorld().addCollisionHandler(new CollisionHandler(GameType.PLAYER, GameType.COMPLETE_TUTORIAL) {

            @Override
            protected void onCollisionBegin(Entity player, Entity complete_tutorial) {

                if (getGameState().getInt("LvlComplete_Tutorial") < 1) {
                    initNotification_CompleteTutorial();
                } else {
                   complete_tutorial.removeFromWorld();
                }
            }
        });

        //PLAYER & SUPERNOVA_UPGRADE
        getPhysicsWorld().addCollisionHandler(new CollisionHandler(GameType.PLAYER, GameType.SUPERNOVA_UPGRADE) {
            @Override
            protected void onCollisionBegin(Entity player, Entity supernova_upgrade) {

                //Upgrade Supernova
                if (getGameState().getInt("Points") <= 0) {
                    getGameState().setValue("Points", 0);
                    getGameState().increment("SupernovaLevel", +0);

                    //Message
                    getDisplay().showMessageBox("Not Enough Points!");
                    getAudioPlayer().playSound("notenough.wav");
                } else {
                    getGameState().increment("Points", -1);

                    if (getGameState().getInt("Points") < 0) {
                        getGameState().setValue("Points", 0);
                    }

                    getGameState().increment("SupernovaLevel", +1);
                    getAudioPlayer().playSound("EnergyBoostGet.wav");
                }

                if (getGameState().getInt("SupernovaLevel") > 5) {

                    getGameState().increment("Points", + 1);

                    //Message
                    getDisplay().showMessageBox("Already at Max Level");
                    getAudioPlayer().playSound("notenough.wav");
                }
            }
        });

        //PLAYER & FLAMESTRIKE_UPGRADE
        getPhysicsWorld().addCollisionHandler(new CollisionHandler(GameType.PLAYER, GameType.FLAMESTRIKE_UPGRADE) {
            @Override
            protected void onCollisionBegin(Entity player, Entity flamestrike_upgrade) {

                //Upgrade Flamestrike
                if (getGameState().getInt("Points") <= 0) {
                    getGameState().setValue("Points", 0);
                    getGameState().increment("FlamestrikeLevel", +0);

                    //Message
                    getDisplay().showMessageBox("Not Enough Points!");
                    getAudioPlayer().playSound("notenough.wav");
                } else {
                    getGameState().increment("Points", -1);

                    if (getGameState().getInt("Points") < 0) {
                        getGameState().setValue("Points", 0);
                    }

                    getGameState().increment("FlamestrikeLevel", +1);
                    getAudioPlayer().playSound("EnergyBoostGet.wav");
                }

                if (getGameState().getInt("FlamestrikeLevel") > 5) {

                    getGameState().increment("Points", + 1);

                    //Message
                    getDisplay().showMessageBox("Already at Max Level");
                    getAudioPlayer().playSound("notenough.wav");
                }
            }
        });

        //PLAYER & FIREBLAST_UPGRADE
        getPhysicsWorld().addCollisionHandler(new CollisionHandler(GameType.PLAYER, GameType.FIREBLAST_UPGRADE) {
            @Override
            protected void onCollisionBegin(Entity player, Entity fireblast_upgrade) {

                //Upgrade Fireblast
                if (getGameState().getInt("Points") <= 0) {
                    getGameState().setValue("Points", 0);
                    getGameState().increment("FireblastLevel", +0);

                    //Message
                    getDisplay().showMessageBox("Not Enough Points!");
                    getAudioPlayer().playSound("notenough.wav");
                } else {
                    getGameState().increment("Points", -1);

                    if (getGameState().getInt("Points") < 0) {
                        getGameState().setValue("Points", 0);
                    }

                    getGameState().increment("FireblastLevel", +1);
                    getAudioPlayer().playSound("EnergyBoostGet.wav");
                }

                if (getGameState().getInt("FireblastLevel") > 5) {

                    getGameState().increment("Points", + 1);

                    //Message
                    getDisplay().showMessageBox("Already at Max Level");
                    getAudioPlayer().playSound("notenough.wav");
                }
            }
        });

        //PLAYER & POTION_UPGRADE
        getPhysicsWorld().addCollisionHandler(new CollisionHandler(GameType.PLAYER, GameType.POTION_UPGRADE) {
            @Override
            protected void onCollisionBegin(Entity player, Entity potion_upgrade) {

                //Upgrade Fireblast
                if (getGameState().getInt("Gold") < 100) {
                    getGameState().increment("Potion", +0);

                    //Message
                    getDisplay().showMessageBox("Not Enough Gold!");
                    getAudioPlayer().playSound("notenough.wav");
                } else {
                    getGameState().increment("Gold", -100);

                    if (getGameState().getInt("Gold") < 0) {
                        getGameState().setValue("Gold", 0);
                    }
                    getGameState().increment("Potion", +1);
                    getAudioPlayer().playSound("EnergyBoostGet.wav");
                }

                if (getGameState().getInt("Potion") > 5) {

                    getGameState().increment("Gold", + 100);

                    //Message
                    getDisplay().showMessageBox("Already at Max Capacity");
                    getAudioPlayer().playSound("notenough.wav");
                }
            }
        });

        //PLAYER & DOOR_DIVE_TUTORIAL
        getPhysicsWorld().addCollisionHandler(new CollisionHandler(GameType.PLAYER, GameType.DOOR_DIVE_TUTORIAL) {
            @Override
            protected void onCollisionBegin(Entity player, Entity door_dive_tutorial) {

                canMove = false;
                canFly = false;

                //Airship Arrival
                getGameWorld().spawn("Airship", 1810, 250);

                //Message
                initNotification_PrepareForAdventure();
                getMasterTimer().runOnceAfter(() -> {
                    initDiveTutorial();
                }, Duration.seconds(5));
            }
        });

        //PLAYER & DOOR_DIVE_LEVEL_1x3
        getPhysicsWorld().addCollisionHandler(new CollisionHandler(GameType.PLAYER, GameType.DOOR_DIVE_LEVEL_1x3) {
            @Override
            protected void onCollisionBegin(Entity player, Entity door_dive_level_1x3) {

                if (getGameState().getInt("LvlComplete_Tutorial") >= 1) {

                    canMove = false;
                    canFly = false;

                    //Airship Arrival
                    getGameWorld().spawn("Airship", 2810, 180);

                    //Message
                    initNotification_PrepareForAdventure();
                    getMasterTimer().runOnceAfter(() -> {
                        initDiveLevel_1x3();
                    }, Duration.seconds(5));
                } else {
                    getDisplay().showMessageBox("Complete The Tutorial to Unlock");
                }
            }
        });

        //PLAYER & DOOR_DIVE_LEVEL_4x6
        getPhysicsWorld().addCollisionHandler(new CollisionHandler(GameType.PLAYER, GameType.DOOR_DIVE_LEVEL_4x6) {
            @Override
            protected void onCollisionBegin(Entity player, Entity door_dive_level_4x6) {

                if (getGameState().getInt("LvlComplete_Level_1x3") >= 1) {

                    canMove = false;
                    canFly = false;

                    //Airship Arrival
                    getGameWorld().spawn("Airship", 3510, 230);

                    //Message
                    initNotification_PrepareForAdventure();
                    getMasterTimer().runOnceAfter(() -> {
                        initDiveLevel_4x6();
                    }, Duration.seconds(5));
                } else {
                    getDisplay().showMessageBox("Complete The Previous Level to Unlock");
                }
            }
        });

        //PLAYER & DOOR_DIVE_LEVEL_7x9
        getPhysicsWorld().addCollisionHandler(new CollisionHandler(GameType.PLAYER, GameType.DOOR_DIVE_LEVEL_7x9) {
            @Override
            protected void onCollisionBegin(Entity player, Entity door_dive_level_7x9) {

                if (getGameState().getInt("LvlComplete_Level_4x6") >= 1) {

                    canMove = false;
                    canFly = false;

                    //Airship Arrival
                    getGameWorld().spawn("Airship", 4100, -50);

                    //Message
                    initNotification_PrepareForAdventure();
                    getMasterTimer().runOnceAfter(() -> {
                        initDiveLevel_7x9();
                    }, Duration.seconds(5));
                } else {
                    getDisplay().showMessageBox("Complete The Previous Level to Unlock");
                }
            }
        });

        //PLAYER & DOOR_DIVE_LEVEL_11x13
        getPhysicsWorld().addCollisionHandler(new CollisionHandler(GameType.PLAYER, GameType.DOOR_DIVE_LEVEL_11x13) {
            @Override
            protected void onCollisionBegin(Entity player, Entity door_dive_level_11x13) {

                if (getGameState().getInt("LvlComplete_Rhatbu") >= 1) {

                    canMove = false;
                    canFly = false;

                    //Airship Arrival
                    getGameWorld().spawn("Airship", 5690, 90);

                    //Message
                    initNotification_PrepareForAdventure();
                    getMasterTimer().runOnceAfter(() -> {
                        initDiveLevel_11x13();
                    }, Duration.seconds(5));
                } else {
                    getDisplay().showMessageBox("Defeat The Previous Boss to Unlock");
                }
            }
        });

        //PLAYER & DOOR_DIVE_LEVEL_14x16
        getPhysicsWorld().addCollisionHandler(new CollisionHandler(GameType.PLAYER, GameType.DOOR_DIVE_LEVEL_14x16) {
            @Override
            protected void onCollisionBegin(Entity player, Entity door_dive_level_14x16) {

                if (getGameState().getInt("LvlComplete_Level_11x13") >= 1) {

                    canMove = false;
                    canFly = false;

                    //Airship Arrival
                    getGameWorld().spawn("Airship", 6380, 90);

                    //Message
                    initNotification_PrepareForAdventure();
                    getMasterTimer().runOnceAfter(() -> {
                        initDiveLevel_14x16();
                    }, Duration.seconds(5));
                } else {
                    getDisplay().showMessageBox("Complete The Previous Level to Unlock");
                }
            }
        });

        //PLAYER & DOOR_DIVE_LEVEL_17x19
        getPhysicsWorld().addCollisionHandler(new CollisionHandler(GameType.PLAYER, GameType.DOOR_DIVE_LEVEL_17x19) {
            @Override
            protected void onCollisionBegin(Entity player, Entity door_dive_level_17x19) {

                if (getGameState().getInt("LvlComplete_Level_14x16") >= 1) {

                    canMove = false;
                    canFly = false;

                    //Airship Arrival
                    getGameWorld().spawn("Airship", 7095, 230);

                    //Message
                    initNotification_PrepareForAdventure();
                    getMasterTimer().runOnceAfter(() -> {
                        initDiveLevel_17x19();
                    }, Duration.seconds(5));
                } else {
                    getDisplay().showMessageBox("Complete The Previous Level to Unlock");
                }
            }
        });


        //PLAYER & DOOR_DIVE_BOSS1
        getPhysicsWorld().addCollisionHandler(new CollisionHandler(GameType.PLAYER, GameType.DOOR_DIVE_BOSS1) {
            @Override
            protected void onCollisionBegin(Entity player, Entity door_dive_boss1) {

                if (getGameState().getInt("LvlComplete_Level_7x9") >= 1) {

                    canMove = false;
                    canFly = false;

                    //Airship Arrival
                    getGameWorld().spawn("Airship", 4850, 250);

                    //Message
                    initNotification_PrepareForAdventure();
                    getMasterTimer().runOnceAfter(() -> {
                        initDiveBoss1();
                    }, Duration.seconds(5));
                } else {
                    getDisplay().showMessageBox("Complete The Previous Level to Unlock");
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

                if (getGameState().getInt("LvlComplete_Level_17x19") >= 1) {

                    canMove = false;
                    canFly = false;

                    //Airship Arrival
                    getGameWorld().spawn("Airship", 7940, 180);

                    //Message
                    initNotification_PrepareForAdventure();
                    getMasterTimer().runOnceAfter(() -> {
                        initDiveBoss2();
                    }, Duration.seconds(5));
                } else {
                    getDisplay().showMessageBox("Complete The Previous Level to Unlock");
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

                if (getGameState().getInt("LvlComplete_Bedj") >= 1) {

                    canMove = false;
                    canFly = false;

                    //Airship Arrival
                    getGameWorld().spawn("Airship", 9680, 90);

                    //Message
                    initNotification_PrepareForAdventure();
                    getMasterTimer().runOnceAfter(() -> {
                        initDiveBossFinal();
                    }, Duration.seconds(5));
                } else {
                    getDisplay().showMessageBox("Complete ALL previous levels to Unlock");
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

        //PLAYER & FALL_BASE
        getPhysicsWorld().addCollisionHandler(new CollisionHandler(GameType.PLAYER, GameType.FALL_BASE) {
            @Override
            protected void onCollisionBegin(Entity player, Entity fall_base) {

                getDisplay().showMessageBox("You Can't Fly There...");

                getAudioPlayer().playSound("drown.wav");
                getGameScene().getViewport().shakeRotational(0.6);
            }
        });

        /**
         * Player & Fall Collision
         *
         * @author Celina Kai
         */

        //PLAYER & FALL
        getPhysicsWorld().addCollisionHandler(new CollisionHandler(GameType.PLAYER, GameType.FALL) {
            @Override
            protected void onCollisionBegin(Entity player, Entity fall_tutorial) {

                getGameState().increment("Health", -35);
                getAudioPlayer().playSound("drown.wav");

                //Check Player Health
                if (getGameState().getInt("Health") <= 0) {
                    getGameState().setValue("Health", 100);
                    getMasterTimer().runOnceAfter(() -> {
                        getDisplay().showMessageBox("Returning Back to Base...");
                        getDisplay().showMessageBox("Mission Failed");
                    }, Duration.seconds(0.21));
                    getMasterTimer().runOnceAfter(() -> {
                        initGame();
                    }, Duration.seconds(0.22));
                } else {

                    //Reduce Player Health
                    initDamageIndicator();
                    getGameScene().getViewport().shakeRotational(0.6);
                }
            }
        });
        // ----- MAP COLLISIONS [LEVEL RETURN TO BASE] -----

        //PLAYER & DOOR_BASE
        getPhysicsWorld().addCollisionHandler(new CollisionHandler(GameType.PLAYER, GameType.DOOR_BASE) {
            @Override
            protected void onCollisionBegin(Entity player, Entity door_base) {

                    canMove = false;
                    canFly = false;

                    //Airship Arrival
                    getGameWorld().spawn("Airship", 95, -60);

                    //Message
                    initNotification_ReturnToBase();
                    getMasterTimer().runOnceAfter(() -> {
                        if (getGameState().getInt("Rank") < 15) {
                            getDisplay().showMessageBox("Rank Increased!");
                            getGameState().increment("Rank", +1);
                            getGameState().increment("Points", +1);
                        }
                        initGame();
                    }, Duration.seconds(5));
            }
        });

        //PLAYER & LEVEL_CLEAR
        getPhysicsWorld().addCollisionHandler(new CollisionHandler(GameType.PLAYER, GameType.LEVEL_CLEAR) {

            @Override
            protected void onCollisionBegin(Entity player, Entity level_clear) {

                level_clear.removeFromWorld();
                initLevelIndicator_LevelCleared();

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

                if (getGameState().getInt("LvlComplete_Tutorial") == 0) {
                    //Message
                    getDisplay().showMessageBox("Hold down 'UP' to Fly, or 'DOWN' to Descend.");
                }
            }
        });

        //PLAYER & GUIDE2
        getPhysicsWorld().addCollisionHandler(new CollisionHandler(GameType.PLAYER, GameType.GUIDE2) {
            @Override
            protected void onCollisionBegin(Entity player, Entity guide2) {

                guide2.removeFromWorld();

                if (getGameState().getInt("LvlComplete_Tutorial") == 0) {
                    //Message
                    getDisplay().showMessageBox("Press 'SPACE' to Shoot Fireballs.");
                }
            }
        });

        //PLAYER & GUIDE3
        getPhysicsWorld().addCollisionHandler(new CollisionHandler(GameType.PLAYER, GameType.GUIDE3) {
            @Override
            protected void onCollisionBegin(Entity player, Entity guide3) {

                guide3.removeFromWorld();

                if (getGameState().getInt("LvlComplete_Tutorial") == 0) {
                    //Message
                    getDisplay().showMessageBox("Press 'A', 'S', or 'D' to Use Your Skills.");
                }
            }
        });

        // ----- MAP COLLISIONS [TUTORIAL 2] -----
        //PLAYER & GUIDE4
        getPhysicsWorld().addCollisionHandler(new CollisionHandler(GameType.PLAYER, GameType.GUIDE4) {
            @Override
            protected void onCollisionBegin(Entity player, Entity guide4) {

                guide4.removeFromWorld();

                if (getGameState().getInt("LvlComplete_Tutorial") == 0) {

                    //Message
                    getDisplay().showMessageBox("You can Buy more Health Potion Back at Base");
                    getDisplay().showMessageBox("This will Heal you back to Full Health");
                    getDisplay().showMessageBox("Press 'F' to consume a Health Potion");
                }
            }
        });

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
                initDamageIndicator();
                getGameState().increment("Health", -10);
                getAudioPlayer().playSound("PlayerHealth.wav");
                getGameScene().getViewport().shakeRotational(0.6);

                //Check Player Health
                if (getGameState().getInt("Health") <= 0) {
                    getGameState().setValue("Health", 100);
                    getMasterTimer().runOnceAfter(() -> {
                        getDisplay().showMessageBox("Returning Back to Base...");
                        getDisplay().showMessageBox("Mission Failed");
                        getGameState().setValue("DarkFlameMasterHealth", 9000);
                    }, Duration.seconds(0.21));
                    getMasterTimer().runOnceAfter(() -> {
                        initGame();
                    }, Duration.seconds(0.22));
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

        // ----- MAP COLLISIONS [LEVEL CLEAR: JUNGLE -----
        getPhysicsWorld().addCollisionHandler(new CollisionHandler(GameType.PLAYER, GameType.DOOR_LEVELCLEAR_JUNGLE) {
            @Override
            protected void onCollisionBegin(Entity player, Entity door_levelclear_jungle) {
                initLevelClear_Jungle();
            }
        });


        // ----- MAP COLLISIONS [LEVEL CLEAR: CAVE] -----
        //PLAYER & DOOR_LEVELCLEAR_JUNGLE
        getPhysicsWorld().addCollisionHandler(new CollisionHandler(GameType.PLAYER, GameType.DOOR_LEVELCLEAR_CAVE) {
            @Override
            protected void onCollisionBegin(Entity player, Entity door_levelclear_cave) {
                initLevelClear_Cave();
            }
        });



        // ----- MAP COLLISIONS [DIVE LEVEL 1-3] -----

        //PLAYER & DOOR_LEVEL_1
        getPhysicsWorld().addCollisionHandler(new CollisionHandler(GameType.PLAYER, GameType.DOOR_LEVEL_1) {
            @Override
            protected void onCollisionBegin(Entity player, Entity door_level_1) {
                initLevel_1();
            }
        });

        // ----- MAP COLLISIONS [LEVEL 1-3] -----

        //LEVEL: 1
        getPhysicsWorld().addCollisionHandler(new CollisionHandler(GameType.PLAYER, GameType.DOOR_LEVEL_2) {
            @Override
            protected void onCollisionBegin(Entity player, Entity door_level_2) {
                initLevel_2();
            }
        });

        //LEVEL: 2
        getPhysicsWorld().addCollisionHandler(new CollisionHandler(GameType.PLAYER, GameType.DOOR_LEVEL_3) {
            @Override
            protected void onCollisionBegin(Entity player, Entity door_level_3) {
                initLevel_3();
            }
        });

        //LEVEL: 3
        getPhysicsWorld().addCollisionHandler(new CollisionHandler(GameType.PLAYER, GameType.LEVELCOMPLETE_1x3) {
            @Override
            protected void onCollisionBegin(Entity player, Entity levelcomplete_1x3) {

                getGameState().setValue("LvlComplete_Level_1x3", 1);
                levelcomplete_1x3.removeFromWorld();
            }
        });

        // ----- MAP COLLISIONS [DIVE LEVEL 4-6] -----

        //PLAYER & DOOR_LEVEL_4
        getPhysicsWorld().addCollisionHandler(new CollisionHandler(GameType.PLAYER, GameType.DOOR_LEVEL_4) {
            @Override
            protected void onCollisionBegin(Entity player, Entity door_level_4) {
                initLevel_4();
            }
        });

        // ----- MAP COLLISIONS [LEVEL 4-6] -----

        //LEVEL: 4

        getPhysicsWorld().addCollisionHandler(new CollisionHandler(GameType.PLAYER, GameType.DOOR_LEVEL_5) {
            @Override
            protected void onCollisionBegin(Entity player, Entity door_level_5) {
                initLevel_5();
            }
        });

        //LEVEL: 5

        getPhysicsWorld().addCollisionHandler(new CollisionHandler(GameType.PLAYER, GameType.DOOR_LEVEL_6) {
            @Override
            protected void onCollisionBegin(Entity player, Entity door_level_6) {
                initLevel_6();
            }
        });

        //LEVEL: 6
        getPhysicsWorld().addCollisionHandler(new CollisionHandler(GameType.PLAYER, GameType.LEVELCOMPLETE_4x6) {
            @Override
            protected void onCollisionBegin(Entity player, Entity levelcomplete_4x6) {

                getGameState().setValue("LvlComplete_Level_4x6", 1);
                levelcomplete_4x6.removeFromWorld();
            }
        });


        // ----- MAP COLLISIONS [DIVE LEVEL 7-9] -----

        //PLAYER & DOOR_LEVEL_7
        getPhysicsWorld().addCollisionHandler(new CollisionHandler(GameType.PLAYER, GameType.DOOR_LEVEL_7) {
            @Override
            protected void onCollisionBegin(Entity player, Entity door_level_7) {
                initLevel_7();
            }
        });

        // ----- MAP COLLISIONS [LEVEL 7-9] -----

        //LEVEL: 7
        getPhysicsWorld().addCollisionHandler(new CollisionHandler(GameType.PLAYER, GameType.DOOR_LEVEL_8) {
            @Override
            protected void onCollisionBegin(Entity player, Entity door_level_8) {
                initLevel_8();
            }
        });

        //LEVEL: 8
        getPhysicsWorld().addCollisionHandler(new CollisionHandler(GameType.PLAYER, GameType.DOOR_LEVEL_9) {
            @Override
            protected void onCollisionBegin(Entity player, Entity door_level_9) {
                initLevel_9();
            }
        });

        //LEVEL: 9
        getPhysicsWorld().addCollisionHandler(new CollisionHandler(GameType.PLAYER, GameType.LEVELCOMPLETE_7x9) {
            @Override
            protected void onCollisionBegin(Entity player, Entity levelcomplete_7x9) {

                getGameState().setValue("LvlComplete_Level_7x9", 1);
                levelcomplete_7x9.removeFromWorld();
            }
        });

        // ----- MAP COLLISIONS [DIVE LEVEL 11-13] -----

        //PLAYER & DOOR_LEVEL_11
        getPhysicsWorld().addCollisionHandler(new CollisionHandler(GameType.PLAYER, GameType.DOOR_LEVEL_11) {
            @Override
            protected void onCollisionBegin(Entity player, Entity door_level_11) {
                initLevel_11();
            }
        });

        // ----- MAP COLLISIONS [LEVEL 11-13] -----

        //LEVEL: 11
        getPhysicsWorld().addCollisionHandler(new CollisionHandler(GameType.PLAYER, GameType.DOOR_LEVEL_12) {
            @Override
            protected void onCollisionBegin(Entity player, Entity door_level_12) {
                initLevel_12();
            }
        });

        //LEVEL: 12
        getPhysicsWorld().addCollisionHandler(new CollisionHandler(GameType.PLAYER, GameType.DOOR_LEVEL_13) {
            @Override
            protected void onCollisionBegin(Entity player, Entity door_level_13) {
                initLevel_13();
            }
        });

        //LEVEL: 13
        getPhysicsWorld().addCollisionHandler(new CollisionHandler(GameType.PLAYER, GameType.LEVELCOMPLETE_11x13) {
            @Override
            protected void onCollisionBegin(Entity player, Entity levelcomplete_11x13) {

                getGameState().setValue("LvlComplete_Level_11x13", 1);
                levelcomplete_11x13.removeFromWorld();
            }
        });

        // ----- MAP COLLISIONS [DIVE LEVEL 14-16] -----

        //PLAYER & DOOR_LEVEL_14
        getPhysicsWorld().addCollisionHandler(new CollisionHandler(GameType.PLAYER, GameType.DOOR_LEVEL_14) {
            @Override
            protected void onCollisionBegin(Entity player, Entity door_level_14) {
                initLevel_14();
            }
        });

        // ----- MAP COLLISIONS [LEVEL 14-16] -----

        //LEVEL: 14
        getPhysicsWorld().addCollisionHandler(new CollisionHandler(GameType.PLAYER, GameType.DOOR_LEVEL_15) {
            @Override
            protected void onCollisionBegin(Entity player, Entity door_level_15) {
                initLevel_15();
            }
        });

        //LEVEL: 15
        getPhysicsWorld().addCollisionHandler(new CollisionHandler(GameType.PLAYER, GameType.DOOR_LEVEL_16) {
            @Override
            protected void onCollisionBegin(Entity player, Entity door_level_16) {
                initLevel_16();
            }
        });

        //LEVEL: 16
        getPhysicsWorld().addCollisionHandler(new CollisionHandler(GameType.PLAYER, GameType.LEVELCOMPLETE_14x16) {
            @Override
            protected void onCollisionBegin(Entity player, Entity levelcomplete_14x16) {

                getGameState().setValue("LvlComplete_Level_14x16", 1);
                levelcomplete_14x16.removeFromWorld();
            }
        });



        // ----- MAP COLLISIONS [DIVE LEVEL 17-19] -----

        //PLAYER & DOOR_LEVEL_17
        getPhysicsWorld().addCollisionHandler(new CollisionHandler(GameType.PLAYER, GameType.DOOR_LEVEL_17) {
            @Override
            protected void onCollisionBegin(Entity player, Entity door_level_17) {
                initLevel_17();
            }
        });

        // ----- MAP COLLISIONS [LEVEL 17-19] -----

        //LEVEL: 17
        getPhysicsWorld().addCollisionHandler(new CollisionHandler(GameType.PLAYER, GameType.DOOR_LEVEL_18) {
            @Override
            protected void onCollisionBegin(Entity player, Entity door_level_18) {
                initLevel_18();
            }
        });

        //LEVEL: 18
        getPhysicsWorld().addCollisionHandler(new CollisionHandler(GameType.PLAYER, GameType.DOOR_LEVEL_19) {
            @Override
            protected void onCollisionBegin(Entity player, Entity door_level_19) {
                initLevel_19();
            }
        });

        //LEVEL: 19
        getPhysicsWorld().addCollisionHandler(new CollisionHandler(GameType.PLAYER, GameType.LEVELCOMPLETE_17x19) {
            @Override
            protected void onCollisionBegin(Entity player, Entity levelcomplete_17x19) {

                getGameState().setValue("LvlComplete_Level_17x19", 1);
                levelcomplete_17x19.removeFromWorld();
            }
        });



        // ----- MAP COLLISIONS [BOSS 1] -----

        //Player & Rhatbu_Rhatbuball
        getPhysicsWorld().addCollisionHandler(new CollisionHandler(GameType.PLAYER, GameType.RHATBU_RHATBUBALL) {

            @Override
            protected void onCollisionBegin(Entity player, Entity rhatbu_rhatbuball) {

                //Reduce Player Health
                initDamageIndicator();
                getGameState().increment("Health", -20);
                getAudioPlayer().playSound("PlayerHealth.wav");
                getGameScene().getViewport().shakeRotational(0.6);

                //Check Player Health
                if (getGameState().getInt("Health") <= 0) {
                    getGameState().setValue("Health", 100);
                    getMasterTimer().runOnceAfter(() -> {
                        getDisplay().showMessageBox("Returning Back to Base...");
                        getDisplay().showMessageBox("Mission Failed");
                        getGameState().setValue("RhatbuHealth", 15000);
                    }, Duration.seconds(0.21));
                    getMasterTimer().runOnceAfter(() -> {
                        initGame();
                    }, Duration.seconds(0.22));
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
                initDamageIndicator();
                getGameState().increment("Health", -10);
                getAudioPlayer().playSound("PlayerHealth.wav");
                getGameScene().getViewport().shakeRotational(0.6);

                //Check Player Health
                if (getGameState().getInt("Health") <= 0) {
                    getGameState().setValue("Health", 100);
                    getMasterTimer().runOnceAfter(() -> {
                        getDisplay().showMessageBox("Returning Back to Base...");
                        getDisplay().showMessageBox("Mission Failed");
                        getGameState().setValue("BedjHealth", 20000);
                    }, Duration.seconds(0.21));
                    getMasterTimer().runOnceAfter(() -> {
                        initGame();
                    }, Duration.seconds(0.22));
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
                initDamageIndicator();
                getGameState().increment("Health", -10);
                getAudioPlayer().playSound("PlayerHealth.wav");

                //Check Player Health
                if (getGameState().getInt("Health") <= 0) {
                    getGameState().setValue("Health", 100);
                    getMasterTimer().runOnceAfter(() -> {
                        getDisplay().showMessageBox("Returning Back to Base...");
                        getDisplay().showMessageBox("Mission Failed");
                        getGameState().setValue("BedjHealth", 20000);
                    }, Duration.seconds(0.21));
                    getMasterTimer().runOnceAfter(() -> {
                        initGame();
                    }, Duration.seconds(0.22));
                }
            }
        });

        // ----- MAP COLLISIONS [FINAL BOSS] -----

        //Player & Grim_Fireballfinal1
        getPhysicsWorld().addCollisionHandler(new CollisionHandler(GameType.PLAYER, GameType.GRIM_FIREBALLFINAL1) {

            @Override
            protected void onCollisionBegin(Entity player, Entity Grim_FireballFinal1) {

                //Reduce Player Health
                initDamageIndicator();
                getGameState().increment("Health", -10);
                getAudioPlayer().playSound("PlayerHealth.wav");
                getGameScene().getViewport().shakeRotational(0.6);

                //Check Player Health
                if (getGameState().getInt("Health") <= 0) {
                    getGameState().setValue("Health", 100);
                    getMasterTimer().runOnceAfter(() -> {
                        getDisplay().showMessageBox("Returning Back to Base...");
                        getDisplay().showMessageBox("Mission Failed");
                        getGameState().setValue("GrimHealth", 25000);
                    }, Duration.seconds(0.21));
                    getMasterTimer().runOnceAfter(() -> {
                        initGame();
                    }, Duration.seconds(0.22));
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
                initDamageIndicator();
                getGameState().increment("Health", -10);
                getAudioPlayer().playSound("PlayerHealth.wav");
                getGameScene().getViewport().shakeRotational(0.6);

                //Check Player Health
                if (getGameState().getInt("Health") <= 0) {
                    getGameState().setValue("Health", 100);
                    getMasterTimer().runOnceAfter(() -> {
                        getDisplay().showMessageBox("Returning Back to Base...");
                        getDisplay().showMessageBox("Mission Failed");
                        getGameState().setValue("GrimHealth", 25000);
                    }, Duration.seconds(0.21));
                    getMasterTimer().runOnceAfter(() -> {
                        initGame();
                    }, Duration.seconds(0.22));
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
                initDamageIndicator();
                getGameState().increment("Health", -30);
                getGameScene().getViewport().shakeRotational(0.6);

                //Check Player Health
                if (getGameState().getInt("Health") <= 0) {
                    getGameState().setValue("Health", 100);
                    //Check Player Health
                    getMasterTimer().runOnceAfter(() -> {
                        getDisplay().showMessageBox("Returning Back to Base...");
                        getDisplay().showMessageBox("Mission Failed");
                        getGameState().setValue("GrimHealth", 25000);
                    }, Duration.seconds(0.21));
                    getMasterTimer().runOnceAfter(() -> {
                        initGame();
                    }, Duration.seconds(0.22));
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
                initDamageIndicator();
                getGameState().increment("Health", -10);
                getAudioPlayer().playSound("PlayerHealth.wav");

                //Check Player Health
                if (getGameState().getInt("Health") <= 0) {
                    getGameState().setValue("Health", 100);
                    getMasterTimer().runOnceAfter(() -> {
                        getDisplay().showMessageBox("Returning Back to Base...");
                        getDisplay().showMessageBox("Mission Failed");
                        getGameState().setValue("GrimHealth", 25000);
                    }, Duration.seconds(0.21));
                    getMasterTimer().runOnceAfter(() -> {
                        initGame();
                    }, Duration.seconds(0.22));
                }
            }
        });
    }

    // ------------------------------------- <<< Heads Up Display (HUD) >>> -------------------------------------

    /**
     * initialize In-Game HUD / UI Display
     *
     * @author Earl John Laguardia
     */

    @Override
    protected void initUI() {

        //Rank UI
        Text RankText = new Text();
        RankText.setText("Rank:");
        RankText.setFont(Font.font ("Berlin Sans FB Demi", 28));
        RankText.setFill(Color.WHITE);
        RankText.setTranslateX(1120);
        RankText.setTranslateY(50);

        Text Rank = new Text();
        Rank.setFont(Font.font ("Berlin Sans FB Demi", 28));
        Rank.setFill(Color.WHITE);
        Rank.setTranslateX(1215);
        Rank.setTranslateY(50);

        Rectangle RankUI = new Rectangle(150, 35);
        RankUI.setFill(Color.rgb(0, 0, 0, 0.7));
        RankUI.setArcHeight(15);
        RankUI.setArcWidth(15);
        RankUI.setTranslateX(1110);
        RankUI.setTranslateY(23);

        //Points UI
        Text PointsText = new Text();
        PointsText.setText("Points:");
        PointsText.setFont(Font.font ("Berlin Sans FB Demi", 28));
        PointsText.setFill(Color.WHITE);
        PointsText.setTranslateX(1120);
        PointsText.setTranslateY(102);

        Text Points = new Text();
        Points.setFont(Font.font ("Berlin Sans FB Demi", 28));
        Points.setFill(Color.WHITE);
        Points.setTranslateX(1215);
        Points.setTranslateY(102);

        Rectangle PointsUI = new Rectangle(150, 35);
        PointsUI.setFill(Color.rgb(0, 0, 0, 0.7));
        PointsUI.setArcHeight(15);
        PointsUI.setArcWidth(15);
        PointsUI.setTranslateX(1110);
        PointsUI.setTranslateY(75);

        //Control UI
        Texture controlUI = getAssetLoader().loadTexture("ControlUI.png");
        controlUI.setTranslateX(1110);
        controlUI.setTranslateY(620);

        //Health
        Texture HealthIcon = getAssetLoader().loadTexture("HeartIcon.gif");
        HealthIcon.setTranslateX(10);
        HealthIcon.setTranslateY(15);

        Text Health = new Text();
        Health.setFont(Font.font ("Berlin Sans FB Demi", 32));
        Health.setFill(Color.WHITE);
        Health.setTranslateX(80);
        Health.setTranslateY(50);

        Text HealthPercent = new Text();
        HealthPercent.setText("%");
        HealthPercent.setFont(Font.font ("Berlin Sans FB Demi", 32));
        HealthPercent.setFill(Color.WHITE);
        HealthPercent.setTranslateX(140);
        HealthPercent.setTranslateY(50);

        Rectangle HealthUI = new Rectangle(110, 35);
        HealthUI.setFill(Color.rgb(0, 0, 0, 0.7));
        HealthUI.setArcHeight(15);
        HealthUI.setArcWidth(15);
        HealthUI.setTranslateX(70);
        HealthUI.setTranslateY(23);


        //Gold
        Texture GoldIcon = getAssetLoader().loadTexture("GoldIcon.gif");
        GoldIcon.setTranslateX(17);
        GoldIcon.setTranslateY(70);

        Text Gold = new Text();
        Gold.setFont(Font.font ("Berlin Sans FB Demi", 32));
        Gold.setFill(Color.WHITE);
        Gold.setTranslateX(80);
        Gold.setTranslateY(102);

        Rectangle GoldUI = new Rectangle(110, 35);
        GoldUI.setFill(Color.rgb(0, 0, 0, 0.7));
        GoldUI.setArcHeight(15);
        GoldUI.setArcWidth(15);
        GoldUI.setTranslateX(70);
        GoldUI.setTranslateY(75);

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

        //Potion UI
        Text PotionCharge = new Text();
        PotionCharge.setFont(Font.font ("Berlin Sans FB Demi", 30));
        PotionCharge.setFill(Color.WHITE);
        PotionCharge.setTranslateX(951);
        PotionCharge.setTranslateY(458);

        Texture PotionUI = getAssetLoader().loadTexture("PotionUI.png");
        PotionUI.setTranslateX(880);
        PotionUI.setTranslateY(670);

        Text Potion = new Text();
        Potion.setFont(Font.font ("Berlin Sans FB Demi", 30));
        Potion.setFill(Color.WHITE);
        Potion.setTranslateX(951);
        Potion.setTranslateY(658);

        Circle PotionAmountUI = new Circle(18);
        PotionAmountUI.setFill(Color.rgb(0,0,0, 0.6));
        PotionAmountUI.setTranslateX(960);
        PotionAmountUI.setTranslateY(650);

        //Firepower Number
        Text Fireball = new Text();
        Fireball.setFont(Font.font ("Berlin Sans FB Demi", 30));
        Fireball.setFill(Color.WHITE);
        Fireball.setTranslateX(640);
        Fireball.setTranslateY(715);

        //Supernova Number
        Text SupernovaLevel = new Text();
        SupernovaLevel.setFont(Font.font ("Berlin Sans FB Demi", 22));
        SupernovaLevel.setFill(Color.WHITE);
        SupernovaLevel.setTranslateX(55);
        SupernovaLevel.setTranslateY(635);

        Circle SupernovaLevelUI = new Circle(15);
        SupernovaLevelUI.setFill(Color.rgb(0,0,0, 0.6));
        SupernovaLevelUI.setTranslateX(60);
        SupernovaLevelUI.setTranslateY(630);

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
        Text FlamestrikeLevel = new Text();
        FlamestrikeLevel.setFont(Font.font ("Berlin Sans FB Demi", 22));
        FlamestrikeLevel.setFill(Color.WHITE);
        FlamestrikeLevel.setTranslateX(160);
        FlamestrikeLevel.setTranslateY(635);

        Circle FlamestrikeLevelUI = new Circle(15);
        FlamestrikeLevelUI.setFill(Color.rgb(0,0,0, 0.6));
        FlamestrikeLevelUI.setTranslateX(165);
        FlamestrikeLevelUI.setTranslateY(630);

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
        Text FireblastLevel = new Text();
        FireblastLevel.setFont(Font.font ("Berlin Sans FB Demi", 22));
        FireblastLevel.setFill(Color.WHITE);
        FireblastLevel.setTranslateX(260);
        FireblastLevel.setTranslateY(635);

        Circle FireblastLevelUI = new Circle(15);
        FireblastLevelUI.setFill(Color.rgb(0,0,0, 0.6));
        FireblastLevelUI.setTranslateX(265);
        FireblastLevelUI.setTranslateY(630);

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

        //-- Map Level Completion --

        //First time Upgrade
        Text first_upgrade = new Text();
        first_upgrade.setFont(Font.font ("Berlin Sans FB Demi", 20));
        first_upgrade.setFill(Color.WHITE);
        first_upgrade.setTranslateX(600);
        first_upgrade.setTranslateY(40);

        //Tutorial
        Text LvlComplete_Tutorial = new Text();
        LvlComplete_Tutorial.setFont(Font.font ("Berlin Sans FB Demi", 20));
        LvlComplete_Tutorial.setFill(Color.WHITE);
        LvlComplete_Tutorial.setTranslateX(600);
        LvlComplete_Tutorial.setTranslateY(40);

        //Level: 1-3
        Text LvlComplete_Level_1x3 = new Text();
        LvlComplete_Level_1x3.setFont(Font.font ("Berlin Sans FB Demi", 20));
        LvlComplete_Level_1x3.setFill(Color.WHITE);
        LvlComplete_Level_1x3.setTranslateX(600);
        LvlComplete_Level_1x3.setTranslateY(40);

        //Level: 4-6
        Text LvlComplete_Level_4x6 = new Text();
        LvlComplete_Level_4x6.setFont(Font.font ("Berlin Sans FB Demi", 20));
        LvlComplete_Level_4x6.setFill(Color.WHITE);
        LvlComplete_Level_4x6.setTranslateX(600);
        LvlComplete_Level_4x6.setTranslateY(40);

        //Level: 7-9
        Text LvlComplete_Level_7x9 = new Text();
        LvlComplete_Level_7x9.setFont(Font.font ("Berlin Sans FB Demi", 20));
        LvlComplete_Level_7x9.setFill(Color.WHITE);
        LvlComplete_Level_7x9.setTranslateX(600);
        LvlComplete_Level_7x9.setTranslateY(40);

        //Level: 11-13
        Text LvlComplete_Level_11x13 = new Text();
        LvlComplete_Level_11x13.setFont(Font.font ("Berlin Sans FB Demi", 20));
        LvlComplete_Level_11x13.setFill(Color.WHITE);
        LvlComplete_Level_11x13.setTranslateX(600);
        LvlComplete_Level_11x13.setTranslateY(40);

        //Level: 14-16
        Text LvlComplete_Level_14x16 = new Text();
        LvlComplete_Level_14x16.setFont(Font.font ("Berlin Sans FB Demi", 20));
        LvlComplete_Level_14x16.setFill(Color.WHITE);
        LvlComplete_Level_14x16.setTranslateX(600);
        LvlComplete_Level_14x16.setTranslateY(40);

        //Level: 17-19
        Text LvlComplete_Level_17x19 = new Text();
        LvlComplete_Level_17x19.setFont(Font.font ("Berlin Sans FB Demi", 20));
        LvlComplete_Level_17x19.setFill(Color.WHITE);
        LvlComplete_Level_17x19.setTranslateX(600);
        LvlComplete_Level_17x19.setTranslateY(40);

        //Rhatbu
        Text LvlComplete_Rhatbu = new Text();
        LvlComplete_Rhatbu.setFont(Font.font ("Berlin Sans FB Demi", 20));
        LvlComplete_Rhatbu.setFill(Color.WHITE);
        LvlComplete_Rhatbu.setTranslateX(600);
        LvlComplete_Rhatbu.setTranslateY(40);

        //Bedj
        Text LvlComplete_Bedj = new Text();
        LvlComplete_Bedj.setFont(Font.font ("Berlin Sans FB Demi", 20));
        LvlComplete_Bedj.setFill(Color.WHITE);
        LvlComplete_Bedj.setTranslateX(600);
        LvlComplete_Bedj.setTranslateY(35);

        //Grim
        Text LvlComplete_Grim = new Text();
        LvlComplete_Grim.setFont(Font.font ("Berlin Sans FB Demi", 20));
        LvlComplete_Grim.setFill(Color.WHITE);
        LvlComplete_Grim.setTranslateX(600);
        LvlComplete_Grim.setTranslateY(40);

        //Add UI to scene
        getGameScene().addUINode(HealthUI);
        getGameScene().addUINode(Health);
        getGameScene().addUINode(HealthPercent);
        getGameScene().addUINode(HealthIcon);

        getGameScene().addUINode(GoldIcon);
        getGameScene().addUINode(GoldUI);
        getGameScene().addUINode(Gold);

        getGameScene().addUINode(RankUI);
        getGameScene().addUINode(Rank);
        getGameScene().addUINode(RankText);

        getGameScene().addUINode(PointsUI);
        getGameScene().addUINode(Points);
        getGameScene().addUINode(PointsText);

        //Skills
        getGameScene().addUINode(skillsUI);
        getGameScene().addUINode(supernovaIcon);
        getGameScene().addUINode(flamestrikeIcon);
        getGameScene().addUINode(fireblastIcon);

        getGameScene().addUINode(fireballUI);
        getGameScene().addUINode(PotionUI);
        getGameScene().addUINode(PotionAmountUI);
        getGameScene().addUINode(Potion);
        //getGameScene().addUINode(PotionCharge);
        //getGameScene().addUINode(Fireball);

        getGameScene().addUINode(FlamestrikeNumberUI);
        getGameScene().addUINode(Flamestrike);
        getGameScene().addUINode(FlamestrikeCharge);
        getGameScene().addUINode(FlamestrikeLevelUI);
        getGameScene().addUINode(FlamestrikeLevel);

        getGameScene().addUINode(SupernovaNumberUI);
        getGameScene().addUINode(Supernova);
        getGameScene().addUINode(SupernovaCharge);
        getGameScene().addUINode(SupernovaLevelUI);
        getGameScene().addUINode(SupernovaLevel);

        getGameScene().addUINode(FireblastNumberUI);
        getGameScene().addUINode(Fireblast);
        getGameScene().addUINode(FireblastCharge);
        getGameScene().addUINode(FireblastLevelUI);
        getGameScene().addUINode(FireblastLevel);

        getGameScene().addUINode(controlUI);

        //--Enemy Health--
        //getGameScene().addUINode(EnemyHealth);


        //-- Map Level Completion --
        //getGameScene().addUINode(first_upgrade);
        //getGameScene().addUINode(LvlComplete_Tutorial);

        //getGameScene().addUINode(LvlComplete_1x3);
        //getGameScene().addUINode(LvlComplete_4x6);
        //getGameScene().addUINode(LvlComplete_7x9);
        //getGameScene().addUINode(LvlComplete_11x13);
        //getGameScene().addUINode(LvlComplete_14x16);
        //getGameScene().addUINode(LvlComplete_17x19);

        //getGameScene().addUINode(LvlComplete_Rhatbu);
        //getGameScene().addUINode(LvlComplete_Bedj);
        //getGameScene().addUINode(LvlComplete_Grim);

        //Player
        Health.textProperty().bind(getGameState().intProperty("Health").asString());
        Gold.textProperty().bind(getGameState().intProperty("Gold").asString());
        Potion.textProperty().bind(getGameState().intProperty("Potion").asString());
        PotionCharge.textProperty().bind(getGameState().intProperty("PotionCharge").asString());
        Rank.textProperty().bind(getGameState().intProperty("Rank").asString());
        Points.textProperty().bind(getGameState().intProperty("Points").asString());

        //Skills
        Fireball.textProperty().bind(getGameState().intProperty("Fireball").asString());
        Fireblast.textProperty().bind(getGameState().intProperty("Fireblast").asString());
        FireblastLevel.textProperty().bind(getGameState().intProperty("FireblastLevel").asString());
        Flamestrike.textProperty().bind(getGameState().intProperty("Flamestrike").asString());
        FlamestrikeLevel.textProperty().bind(getGameState().intProperty("FlamestrikeLevel").asString());
        Supernova.textProperty().bind(getGameState().intProperty("Supernova").asString());
        SupernovaLevel.textProperty().bind(getGameState().intProperty("SupernovaLevel").asString());

        //Enemy
        EnemyHealth.textProperty().bind(getGameState().intProperty("EnemyHealth").asString());

        //Map Level Completion
        first_upgrade.textProperty().bind(getGameState().intProperty("first_upgrade").asString());
        LvlComplete_Tutorial.textProperty().bind(getGameState().intProperty("LvlComplete_Tutorial").asString());

        LvlComplete_Level_1x3.textProperty().bind(getGameState().intProperty("LvlComplete_Level_1x3").asString());
        LvlComplete_Level_4x6.textProperty().bind(getGameState().intProperty("LvlComplete_Level_4x6").asString());
        LvlComplete_Level_7x9.textProperty().bind(getGameState().intProperty("LvlComplete_Level_7x9").asString());
        LvlComplete_Level_11x13.textProperty().bind(getGameState().intProperty("LvlComplete_Level_11x13").asString());
        LvlComplete_Level_14x16.textProperty().bind(getGameState().intProperty("LvlComplete_Level_14x16").asString());
        LvlComplete_Level_17x19.textProperty().bind(getGameState().intProperty("LvlComplete_Level_17x19").asString());

        LvlComplete_Rhatbu.textProperty().bind(getGameState().intProperty("LvlComplete_Rhatbu").asString());
        LvlComplete_Bedj.textProperty().bind(getGameState().intProperty("LvlComplete_Bedj").asString());
        LvlComplete_Grim.textProperty().bind(getGameState().intProperty("LvlComplete_Grim").asString());

    }
    // ------------------------------------- <<< Heads Up Display (HUD) >>> -------------------------------------

    /**
     * Initialize Damage Indicator
     *
     * @author Earl John Laguardia
     */

    //Damage Indicator
    protected void initDamageIndicator() {

        Rectangle DamageUI = new Rectangle(1280, 770);
        DamageUI.setFill(Color.rgb(250, 0, 0, 0.3));
        DamageUI.setTranslateX(0);
        DamageUI.setTranslateY(0);

        getGameScene().addUINode(DamageUI);

        getMasterTimer().runOnceAfter(() -> {
            getGameScene().removeUINode(DamageUI);
        }, Duration.seconds(0.15));
    }

    /**
     * Show Dark Flame Master Health
     *
     * @author Earl John Laguardia
     */

    //Show Dark Flame Master HP
    protected void initDarkFlameMasterHP() {

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

        getMasterTimer().runAtInterval(() -> {
            if (getGameState().getInt("DarkFlameMasterHealth") < 9000) {
                getGameScene().removeUINode(DarkFlameMasterHealth);
                getGameScene().removeUINode(DarkFlameMasterHealthUI);
                getGameScene().addUINode(DarkFlameMasterHealth);
                getGameScene().addUINode(DarkFlameMasterHealthUI);
            } else if (getGameState().getInt("DarkFlameMasterHealth") == 9000) {
                getGameScene().removeUINode(DarkFlameMasterHealth);
                getGameScene().removeUINode(DarkFlameMasterHealthUI);
            }
        }, Duration.seconds(0));

        DarkFlameMasterHealth.textProperty().bind(getGameState().intProperty("DarkFlameMasterHealth").asString());
    }

    /**
     * Show Rhatbu Health
     *
     * @author Earl John Laguardia
     */

    //Show Rhatbu HP
    protected void initRhatbuHP() {

        //Rhatbu Health Text
        Text RhatbuHealthUI = new Text();
        RhatbuHealthUI .setText("Rhatbu:");
        RhatbuHealthUI .setFont(Font.font ("Berlin Sans FB Demi", 40));
        RhatbuHealthUI .setFill(Color.RED);
        RhatbuHealthUI .setTranslateX(470);
        RhatbuHealthUI .setTranslateY(40);

        //Rhatbu Health Number
        Text RhatbuHealth = new Text();
        RhatbuHealth.setFont(Font.font ("Berlin Sans FB Demi", 40));
        RhatbuHealth.setFill(Color.WHITE);
        RhatbuHealth.setTranslateX(650);
        RhatbuHealth.setTranslateY(40);


        getMasterTimer().runAtInterval(() -> {
            if (getGameState().getInt("RhatbuHealth") < 15000) {
                getGameScene().removeUINode(RhatbuHealth);
                getGameScene().removeUINode(RhatbuHealthUI);
                getGameScene().addUINode(RhatbuHealth);
                getGameScene().addUINode(RhatbuHealthUI);
            } else if (getGameState().getInt("RhatbuHealth") == 15000) {
                getGameScene().removeUINode(RhatbuHealth);
                getGameScene().removeUINode(RhatbuHealthUI);
            }
        }, Duration.seconds(0));

        RhatbuHealth.textProperty().bind(getGameState().intProperty("RhatbuHealth").asString());
    }

    /**
     * Show Bedj Health
     *
     * @author Earl John Laguardia
     */

    //Show Bedj HP
    protected void initBedjHP() {

        //Bedj Health Text
        Text BedjHealthUI = new Text();
        BedjHealthUI.setText("Bedj:");
        BedjHealthUI.setFont(Font.font ("Berlin Sans FB Demi", 40));
        BedjHealthUI.setFill(Color.RED);
        BedjHealthUI.setTranslateX(490);
        BedjHealthUI.setTranslateY(40);

        //Bedj Health Number
        Text BedjHealth = new Text();
        BedjHealth.setFont(Font.font ("Berlin Sans FB Demi", 40));
        BedjHealth.setFill(Color.WHITE);
        BedjHealth.setTranslateX(600);
        BedjHealth.setTranslateY(40);


        getMasterTimer().runAtInterval(() -> {
            if (getGameState().getInt("BedjHealth") < 20000) {
                getGameScene().removeUINode(BedjHealth);
                getGameScene().removeUINode(BedjHealthUI);
                getGameScene().addUINode(BedjHealth);
                getGameScene().addUINode(BedjHealthUI);
            } else if (getGameState().getInt("BedjHealth") == 20000) {
                getGameScene().removeUINode(BedjHealth);
                getGameScene().removeUINode(BedjHealthUI);
            }
        }, Duration.seconds(0));

        BedjHealth.textProperty().bind(getGameState().intProperty("BedjHealth").asString());
    }

    /**
     * Show Grim Health
     *
     * @author Earl John Laguardia
     */

    //Show Grim HP
    protected void initGrimHP() {

        //Grim Health Text
        Text GrimHealthUI = new Text();
        GrimHealthUI.setText("Grim:");
        GrimHealthUI.setFont(Font.font ("Berlin Sans FB Demi", 40));
        GrimHealthUI.setFill(Color.RED);
        GrimHealthUI.setTranslateX(480);
        GrimHealthUI.setTranslateY(40);

        //Grim Health Number
        Text GrimHealth = new Text();
        GrimHealth.setFont(Font.font ("Berlin Sans FB Demi", 40));
        GrimHealth.setFill(Color.WHITE);
        GrimHealth.setTranslateX(600);
        GrimHealth.setTranslateY(40);

        getMasterTimer().runAtInterval(() -> {
            if (getGameState().getInt("GrimHealth") < 25000) {
                getGameScene().removeUINode(GrimHealth);
                getGameScene().removeUINode(GrimHealthUI);
                getGameScene().addUINode(GrimHealth);
                getGameScene().addUINode(GrimHealthUI);
            } else if (getGameState().getInt("GrimHealth") == 25000) {
                getGameScene().removeUINode(GrimHealth);
                getGameScene().removeUINode(GrimHealthUI);
            }
        }, Duration.seconds(0));

        GrimHealth.textProperty().bind(getGameState().intProperty("GrimHealth").asString());
    }

    //---- Map Level Indicator ----
    private Animation<?> animationBorderEnter;
    private Animation<?> animationTextEnter;
    private Animation<?> animationBorderExit;
    private Animation<?> animationTextExit;

    /**
     * Initializes Notification: Welcome Back
     *
     * @author Earl John Laguardia
     */

    //Notification: Welcome Back
    public void initNotification_WelcomeBack() {
        getAudioPlayer().playSound("Notification.wav");

        //Enter Level: Tutorial
        Text EnterLevel_Tutorial = new Text("Welcome Back!");
        EnterLevel_Tutorial.setFont(Font.font ("Berlin Sans FB Demi", 24));
        EnterLevel_Tutorial.setFill(Color.WHITE);
        EnterLevel_Tutorial.setTranslateX(540);
        EnterLevel_Tutorial.setTranslateY(-60);

        //Enter Border
        Texture EnterLevelNotification = getAssetLoader().loadTexture("Notification.png");
        EnterLevelNotification.setTranslateX(0);
        EnterLevelNotification.setTranslateY(-100);

        //Exit Level: Tutorial
        Text ExitLevel_Tutorial = new Text("Welcome Back!");
        ExitLevel_Tutorial.setFont(Font.font ("Berlin Sans FB Demi", 24));
        ExitLevel_Tutorial.setFill(Color.WHITE);
        ExitLevel_Tutorial.setTranslateX(540);
        ExitLevel_Tutorial.setTranslateY(60);

        //Exit Border
        Texture ExitLevelNotification = getAssetLoader().loadTexture("Notification.png");
        ExitLevelNotification.setTranslateX(0);
        ExitLevelNotification.setTranslateY(0);

        //Add to Scene ENTER
        getGameScene().addUINode(EnterLevelNotification);
        getGameScene().addUINode(EnterLevel_Tutorial);

        //Enter Animation
        animationBorderEnter = getUIFactory().translate(EnterLevelNotification, new Point2D(0, 0), Duration.seconds(1));
        animationTextEnter = getUIFactory().translate(EnterLevel_Tutorial, new Point2D(540, 60), Duration.seconds(1));

        //Play Animation ENTER
        animationBorderEnter.startInPlayState();
        animationTextEnter.startInPlayState();

        //Remove Animation ENTER
        getMasterTimer().runOnceAfter(() -> {
            getGameScene().removeUINode(EnterLevelNotification);
            getGameScene().removeUINode(EnterLevel_Tutorial);
        }, Duration.seconds(3));

        //Add Animation EXIT
        getMasterTimer().runOnceAfter(() -> {
            getGameScene().addUINode(ExitLevelNotification);
            getGameScene().addUINode(ExitLevel_Tutorial);
        }, Duration.seconds(3));

        //Exit Animation
        animationBorderExit = getUIFactory().translate(ExitLevelNotification, new Point2D(0, -100), Duration.seconds(1));
        animationTextExit = getUIFactory().translate(ExitLevel_Tutorial, new Point2D(540, -60), Duration.seconds(1));

        //Play Animation EXIT
        getMasterTimer().runOnceAfter(() -> {
            animationBorderExit.startInPlayState();
            animationTextExit.startInPlayState();
        }, Duration.seconds(3));

        //Remove Animation EXIT
        getMasterTimer().runOnceAfter(() -> {
            getGameScene().removeUINode(ExitLevelNotification);
            getGameScene().removeUINode(ExitLevel_Tutorial);
        }, Duration.seconds(4));
    }

    /**
     * Initializes Notification: Save Reminder
     *
     * @author Earl John Laguardia
     */

    //Notification: Save Reminder
    public void initNotification_SaveReminder() {
        getAudioPlayer().playSound("Notification.wav");

        //Enter Level: Tutorial
        Text EnterLevel_Tutorial = new Text("Remember to SAVE your Progress!");
        EnterLevel_Tutorial.setFont(Font.font ("Berlin Sans FB Demi", 24));
        EnterLevel_Tutorial.setFill(Color.WHITE);
        EnterLevel_Tutorial.setTranslateX(450);
        EnterLevel_Tutorial.setTranslateY(-60);

        //Enter Border
        Texture EnterLevelNotification = getAssetLoader().loadTexture("Notification.png");
        EnterLevelNotification.setTranslateX(0);
        EnterLevelNotification.setTranslateY(-100);

        //Exit Level: Tutorial
        Text ExitLevel_Tutorial = new Text("Remember to SAVE your Progress!");
        ExitLevel_Tutorial.setFont(Font.font ("Berlin Sans FB Demi", 24));
        ExitLevel_Tutorial.setFill(Color.WHITE);
        ExitLevel_Tutorial.setTranslateX(450);
        ExitLevel_Tutorial.setTranslateY(60);

        //Exit Border
        Texture ExitLevelNotification = getAssetLoader().loadTexture("Notification.png");
        ExitLevelNotification.setTranslateX(0);
        ExitLevelNotification.setTranslateY(0);

        //Add to Scene ENTER
        getGameScene().addUINode(EnterLevelNotification);
        getGameScene().addUINode(EnterLevel_Tutorial);

        //Enter Animation
        animationBorderEnter = getUIFactory().translate(EnterLevelNotification, new Point2D(0, 0), Duration.seconds(1));
        animationTextEnter = getUIFactory().translate(EnterLevel_Tutorial, new Point2D(450, 60), Duration.seconds(1));

        //Play Animation ENTER
        animationBorderEnter.startInPlayState();
        animationTextEnter.startInPlayState();

        //Remove Animation ENTER
        getMasterTimer().runOnceAfter(() -> {
            getGameScene().removeUINode(EnterLevelNotification);
            getGameScene().removeUINode(EnterLevel_Tutorial);
        }, Duration.seconds(3));

        //Add Animation EXIT
        getMasterTimer().runOnceAfter(() -> {
            getGameScene().addUINode(ExitLevelNotification);
            getGameScene().addUINode(ExitLevel_Tutorial);
        }, Duration.seconds(3));

        //Exit Animation
        animationBorderExit = getUIFactory().translate(ExitLevelNotification, new Point2D(0, -100), Duration.seconds(1));
        animationTextExit = getUIFactory().translate(ExitLevel_Tutorial, new Point2D(450, -60), Duration.seconds(1));

        //Play Animation EXIT
        getMasterTimer().runOnceAfter(() -> {
            animationBorderExit.startInPlayState();
            animationTextExit.startInPlayState();
        }, Duration.seconds(3));

        //Remove Animation EXIT
        getMasterTimer().runOnceAfter(() -> {
            getGameScene().removeUINode(ExitLevelNotification);
            getGameScene().removeUINode(ExitLevel_Tutorial);
        }, Duration.seconds(4));
    }

    /**
     * Initializes Notification: Complete Tutorial Requirement
     *
     * @author Earl John Laguardia
     */

    //Notification: Complete_Tutorial
    public void initNotification_CompleteTutorial() {
        getAudioPlayer().playSound("Notification.wav");

        //Enter Level: Tutorial
        Text EnterLevel_Tutorial = new Text("You must complete the TUTORIAL first");
        EnterLevel_Tutorial.setFont(Font.font ("Berlin Sans FB Demi", 24));
        EnterLevel_Tutorial.setFill(Color.WHITE);
        EnterLevel_Tutorial.setTranslateX(420);
        EnterLevel_Tutorial.setTranslateY(-60);

        //Enter Border
        Texture EnterLevelNotification = getAssetLoader().loadTexture("Notification.png");
        EnterLevelNotification.setTranslateX(0);
        EnterLevelNotification.setTranslateY(-100);

        //Exit Level: Tutorial
        Text ExitLevel_Tutorial = new Text("You must complete the TUTORIAL first");
        ExitLevel_Tutorial.setFont(Font.font ("Berlin Sans FB Demi", 24));
        ExitLevel_Tutorial.setFill(Color.WHITE);
        ExitLevel_Tutorial.setTranslateX(420);
        ExitLevel_Tutorial.setTranslateY(60);

        //Exit Border
        Texture ExitLevelNotification = getAssetLoader().loadTexture("Notification.png");
        ExitLevelNotification.setTranslateX(0);
        ExitLevelNotification.setTranslateY(0);

        //Add to Scene ENTER
        getGameScene().addUINode(EnterLevelNotification);
        getGameScene().addUINode(EnterLevel_Tutorial);

        //Enter Animation
        animationBorderEnter = getUIFactory().translate(EnterLevelNotification, new Point2D(0, 0), Duration.seconds(1));
        animationTextEnter = getUIFactory().translate(EnterLevel_Tutorial, new Point2D(420, 60), Duration.seconds(1));

        //Play Animation ENTER
        animationBorderEnter.startInPlayState();
        animationTextEnter.startInPlayState();

        //Remove Animation ENTER
        getMasterTimer().runOnceAfter(() -> {
            getGameScene().removeUINode(EnterLevelNotification);
            getGameScene().removeUINode(EnterLevel_Tutorial);
        }, Duration.seconds(3));

        //Add Animation EXIT
        getMasterTimer().runOnceAfter(() -> {
            getGameScene().addUINode(ExitLevelNotification);
            getGameScene().addUINode(ExitLevel_Tutorial);
        }, Duration.seconds(3));

        //Exit Animation
        animationBorderExit = getUIFactory().translate(ExitLevelNotification, new Point2D(0, -100), Duration.seconds(1));
        animationTextExit = getUIFactory().translate(ExitLevel_Tutorial, new Point2D(420, -60), Duration.seconds(1));

        //Play Animation EXIT
        getMasterTimer().runOnceAfter(() -> {
            animationBorderExit.startInPlayState();
            animationTextExit.startInPlayState();
        }, Duration.seconds(3));

        //Remove Animation EXIT
        getMasterTimer().runOnceAfter(() -> {
            getGameScene().removeUINode(ExitLevelNotification);
            getGameScene().removeUINode(ExitLevel_Tutorial);
        }, Duration.seconds(4));
    }

    /**
     * Initializes Notification: Airship Arrival Message
     *
     * @author Earl John Laguardia
     */

    //Notification: Preparing for Adventure...
    public void initNotification_PrepareForAdventure() {
        getAudioPlayer().playSound("Notification.wav");

        //Enter Level: Tutorial
        Text EnterLevel_Tutorial = new Text(" Preparing for Adventure...");
        EnterLevel_Tutorial.setFont(Font.font ("Berlin Sans FB Demi", 24));
        EnterLevel_Tutorial.setFill(Color.WHITE);
        EnterLevel_Tutorial.setTranslateX(470);
        EnterLevel_Tutorial.setTranslateY(-60);

        //Enter Border
        Texture EnterLevelNotification = getAssetLoader().loadTexture("Notification.png");
        EnterLevelNotification.setTranslateX(0);
        EnterLevelNotification.setTranslateY(-100);

        //Exit Level: Tutorial
        Text ExitLevel_Tutorial = new Text(" Preparing for Adventure...");
        ExitLevel_Tutorial.setFont(Font.font ("Berlin Sans FB Demi", 24));
        ExitLevel_Tutorial.setFill(Color.WHITE);
        ExitLevel_Tutorial.setTranslateX(470);
        ExitLevel_Tutorial.setTranslateY(60);

        //Exit Border
        Texture ExitLevelNotification = getAssetLoader().loadTexture("Notification.png");
        ExitLevelNotification.setTranslateX(0);
        ExitLevelNotification.setTranslateY(0);

        //Add to Scene ENTER
        getGameScene().addUINode(EnterLevelNotification);
        getGameScene().addUINode(EnterLevel_Tutorial);

        //Enter Animation
        animationBorderEnter = getUIFactory().translate(EnterLevelNotification, new Point2D(0, 0), Duration.seconds(1));
        animationTextEnter = getUIFactory().translate(EnterLevel_Tutorial, new Point2D(470, 60), Duration.seconds(1));

        //Play Animation ENTER
        animationBorderEnter.startInPlayState();
        animationTextEnter.startInPlayState();

        //Remove Animation ENTER
        getMasterTimer().runOnceAfter(() -> {
            getGameScene().removeUINode(EnterLevelNotification);
            getGameScene().removeUINode(EnterLevel_Tutorial);
        }, Duration.seconds(3));

        //Add Animation EXIT
        getMasterTimer().runOnceAfter(() -> {
            getGameScene().addUINode(ExitLevelNotification);
            getGameScene().addUINode(ExitLevel_Tutorial);
        }, Duration.seconds(3));

        //Exit Animation
        animationBorderExit = getUIFactory().translate(ExitLevelNotification, new Point2D(0, -100), Duration.seconds(1));
        animationTextExit = getUIFactory().translate(ExitLevel_Tutorial, new Point2D(470, -60), Duration.seconds(1));

        //Play Animation EXIT
        getMasterTimer().runOnceAfter(() -> {
            animationBorderExit.startInPlayState();
            animationTextExit.startInPlayState();
        }, Duration.seconds(3));

        //Remove Animation EXIT
        getMasterTimer().runOnceAfter(() -> {
            getGameScene().removeUINode(ExitLevelNotification);
            getGameScene().removeUINode(ExitLevel_Tutorial);
        }, Duration.seconds(4));
    }

    /**
     * Initializes Notification: Returing to Base
     *
     * @author Earl John Laguardia
     */

    //Notification: Returning to Base...
    public void initNotification_ReturnToBase() {
        getAudioPlayer().playSound("Notification.wav");

        //Enter Level: Tutorial
        Text EnterLevel_Tutorial = new Text("Returning to Base...");
        EnterLevel_Tutorial.setFont(Font.font ("Berlin Sans FB Demi", 24));
        EnterLevel_Tutorial.setFill(Color.WHITE);
        EnterLevel_Tutorial.setTranslateX(510);
        EnterLevel_Tutorial.setTranslateY(-60);

        //Enter Border
        Texture EnterLevelNotification = getAssetLoader().loadTexture("Notification.png");
        EnterLevelNotification.setTranslateX(0);
        EnterLevelNotification.setTranslateY(-100);

        //Exit Level: Tutorial
        Text ExitLevel_Tutorial = new Text("Returning to Base...");
        ExitLevel_Tutorial.setFont(Font.font ("Berlin Sans FB Demi", 24));
        ExitLevel_Tutorial.setFill(Color.WHITE);
        ExitLevel_Tutorial.setTranslateX(510);
        ExitLevel_Tutorial.setTranslateY(60);

        //Exit Border
        Texture ExitLevelNotification = getAssetLoader().loadTexture("Notification.png");
        ExitLevelNotification.setTranslateX(0);
        ExitLevelNotification.setTranslateY(0);

        //Add to Scene ENTER
        getGameScene().addUINode(EnterLevelNotification);
        getGameScene().addUINode(EnterLevel_Tutorial);

        //Enter Animation
        animationBorderEnter = getUIFactory().translate(EnterLevelNotification, new Point2D(0, 0), Duration.seconds(1));
        animationTextEnter = getUIFactory().translate(EnterLevel_Tutorial, new Point2D(510, 60), Duration.seconds(1));

        //Play Animation ENTER
        animationBorderEnter.startInPlayState();
        animationTextEnter.startInPlayState();

        //Remove Animation ENTER
        getMasterTimer().runOnceAfter(() -> {
            getGameScene().removeUINode(EnterLevelNotification);
            getGameScene().removeUINode(EnterLevel_Tutorial);
        }, Duration.seconds(3));

        //Add Animation EXIT
        getMasterTimer().runOnceAfter(() -> {
            getGameScene().addUINode(ExitLevelNotification);
            getGameScene().addUINode(ExitLevel_Tutorial);
        }, Duration.seconds(3));

        //Exit Animation
        animationBorderExit = getUIFactory().translate(ExitLevelNotification, new Point2D(0, -100), Duration.seconds(1));
        animationTextExit = getUIFactory().translate(ExitLevel_Tutorial, new Point2D(510, -60), Duration.seconds(1));

        //Play Animation EXIT
        getMasterTimer().runOnceAfter(() -> {
            animationBorderExit.startInPlayState();
            animationTextExit.startInPlayState();
        }, Duration.seconds(3));

        //Remove Animation EXIT
        getMasterTimer().runOnceAfter(() -> {
            getGameScene().removeUINode(ExitLevelNotification);
            getGameScene().removeUINode(ExitLevel_Tutorial);
        }, Duration.seconds(4));
    }

    /**
     * Initializes Level Indicator: Level Cleared
     *
     * @author Earl John Laguardia
     */

    //Level: Completed
    public void initLevelIndicator_LevelCleared() {

        getAudioPlayer().playSound("LevelClear.wav");

        //Enter Level: Tutorial
        Text EnterLevel_Tutorial = new Text("Level Cleared!");
        EnterLevel_Tutorial.setFont(Font.font ("Berlin Sans FB Demi", 52));
        EnterLevel_Tutorial.setFill(Color.WHITE);
        EnterLevel_Tutorial.setTranslateX(-490);
        EnterLevel_Tutorial.setTranslateY(260);

        //Enter Border
        Texture EnterLevelNotification = getAssetLoader().loadTexture("LevelNotification.png");
        EnterLevelNotification.setTranslateX(-1280);
        EnterLevelNotification.setTranslateY(200);

        //Exit Level: Tutorial
        Text ExitLevel_Tutorial = new Text("Level Cleared!");
        ExitLevel_Tutorial.setFont(Font.font ("Berlin Sans FB Demi", 52));
        ExitLevel_Tutorial.setFill(Color.WHITE);
        ExitLevel_Tutorial.setTranslateX(490);
        ExitLevel_Tutorial.setTranslateY(260);

        //Exit Border
        Texture ExitLevelNotification = getAssetLoader().loadTexture("LevelNotification.png");
        ExitLevelNotification.setTranslateX(0);
        ExitLevelNotification.setTranslateY(200);

        //Add to Scene ENTER
        getGameScene().addUINode(EnterLevelNotification);
        getGameScene().addUINode(EnterLevel_Tutorial);

        //Enter Animation
        animationBorderEnter = getUIFactory().translate(EnterLevelNotification, new Point2D(0, 200), Duration.seconds(1));
        animationTextEnter = getUIFactory().translate(EnterLevel_Tutorial, new Point2D(490, 260), Duration.seconds(1));

        //Play Animation ENTER
        animationBorderEnter.startInPlayState();
        animationTextEnter.startInPlayState();

        //Remove Animation ENTER
        getMasterTimer().runOnceAfter(() -> {
            getGameScene().removeUINode(EnterLevelNotification);
            getGameScene().removeUINode(EnterLevel_Tutorial);
        }, Duration.seconds(3));

        //Add Animation EXIT
        getMasterTimer().runOnceAfter(() -> {
            getGameScene().addUINode(ExitLevelNotification);
            getGameScene().addUINode(ExitLevel_Tutorial);
        }, Duration.seconds(3));

        //Exit Animation
        animationBorderExit = getUIFactory().translate(ExitLevelNotification, new Point2D(1280, 200), Duration.seconds(1));
        animationTextExit = getUIFactory().translate(ExitLevel_Tutorial, new Point2D(1770, 260), Duration.seconds(1));

        //Play Animation EXIT
        getMasterTimer().runOnceAfter(() -> {
            animationBorderExit.startInPlayState();
            animationTextExit.startInPlayState();
        }, Duration.seconds(3));

        //Remove Animation EXIT
        getMasterTimer().runOnceAfter(() -> {
            getGameScene().removeUINode(ExitLevelNotification);
            getGameScene().removeUINode(ExitLevel_Tutorial);
        }, Duration.seconds(4));
    }

    /**
     * Initializes Level Indicator: Tutorial
     *
     * @author Earl John Laguardia
     */

    //Level: Tutorial
    public void initLevelIndicator_Tutorial() {

        //Enter Level: Tutorial
        Text EnterLevel_Tutorial = new Text("Level: Tutorial");
        EnterLevel_Tutorial.setFont(Font.font ("Berlin Sans FB Demi", 52));
        EnterLevel_Tutorial.setFill(Color.WHITE);
        EnterLevel_Tutorial.setTranslateX(-490);
        EnterLevel_Tutorial.setTranslateY(260);

        //Enter Border
        Texture EnterLevelNotification = getAssetLoader().loadTexture("LevelNotification.png");
        EnterLevelNotification.setTranslateX(-1280);
        EnterLevelNotification.setTranslateY(200);

        //Exit Level: Tutorial
        Text ExitLevel_Tutorial = new Text("Level: Tutorial");
        ExitLevel_Tutorial.setFont(Font.font ("Berlin Sans FB Demi", 52));
        ExitLevel_Tutorial.setFill(Color.WHITE);
        ExitLevel_Tutorial.setTranslateX(490);
        ExitLevel_Tutorial.setTranslateY(260);

        //Exit Border
        Texture ExitLevelNotification = getAssetLoader().loadTexture("LevelNotification.png");
        ExitLevelNotification.setTranslateX(0);
        ExitLevelNotification.setTranslateY(200);

        //Add to Scene ENTER
        getGameScene().addUINode(EnterLevelNotification);
        getGameScene().addUINode(EnterLevel_Tutorial);

        //Enter Animation
        animationBorderEnter = getUIFactory().translate(EnterLevelNotification, new Point2D(0, 200), Duration.seconds(1));
        animationTextEnter = getUIFactory().translate(EnterLevel_Tutorial, new Point2D(490, 260), Duration.seconds(1));

        //Play Animation ENTER
        animationBorderEnter.startInPlayState();
        animationTextEnter.startInPlayState();

        //Remove Animation ENTER
        getMasterTimer().runOnceAfter(() -> {
            getGameScene().removeUINode(EnterLevelNotification);
            getGameScene().removeUINode(EnterLevel_Tutorial);
        }, Duration.seconds(3));

        //Add Animation EXIT
        getMasterTimer().runOnceAfter(() -> {
            getGameScene().addUINode(ExitLevelNotification);
            getGameScene().addUINode(ExitLevel_Tutorial);
        }, Duration.seconds(3));

        //Exit Animation
        animationBorderExit = getUIFactory().translate(ExitLevelNotification, new Point2D(1280, 200), Duration.seconds(1));
        animationTextExit = getUIFactory().translate(ExitLevel_Tutorial, new Point2D(1770, 260), Duration.seconds(1));

        //Play Animation EXIT
        getMasterTimer().runOnceAfter(() -> {
            animationBorderExit.startInPlayState();
            animationTextExit.startInPlayState();
        }, Duration.seconds(3));

        //Remove Animation EXIT
        getMasterTimer().runOnceAfter(() -> {
            getGameScene().removeUINode(ExitLevelNotification);
            getGameScene().removeUINode(ExitLevel_Tutorial);
        }, Duration.seconds(4));
    }

    /**
     * Initializes Level Indicator: Level 1
     *
     * @author Earl John Laguardia
     */

    //Level: 1
    public void initLevelIndicator_1() {

        //Enter Level: Tutorial
        Text EnterLevel_Tutorial = new Text("Level 1");
        EnterLevel_Tutorial.setFont(Font.font ("Berlin Sans FB Demi", 52));
        EnterLevel_Tutorial.setFill(Color.WHITE);
        EnterLevel_Tutorial.setTranslateX(-520);
        EnterLevel_Tutorial.setTranslateY(260);

        //Enter Border
        Texture EnterLevelNotification = getAssetLoader().loadTexture("LevelNotification.png");
        EnterLevelNotification.setTranslateX(-1280);
        EnterLevelNotification.setTranslateY(200);

        //Exit Level: Tutorial
        Text ExitLevel_Tutorial = new Text("Level 1");
        ExitLevel_Tutorial.setFont(Font.font ("Berlin Sans FB Demi", 52));
        ExitLevel_Tutorial.setFill(Color.WHITE);
        ExitLevel_Tutorial.setTranslateX(520);
        ExitLevel_Tutorial.setTranslateY(260);

        //Exit Border
        Texture ExitLevelNotification = getAssetLoader().loadTexture("LevelNotification.png");
        ExitLevelNotification.setTranslateX(0);
        ExitLevelNotification.setTranslateY(200);

        //Add to Scene ENTER
        getGameScene().addUINode(EnterLevelNotification);
        getGameScene().addUINode(EnterLevel_Tutorial);

        //Enter Animation
        animationBorderEnter = getUIFactory().translate(EnterLevelNotification, new Point2D(0, 200), Duration.seconds(1));
        animationTextEnter = getUIFactory().translate(EnterLevel_Tutorial, new Point2D(520, 260), Duration.seconds(1));

        //Play Animation ENTER
        animationBorderEnter.startInPlayState();
        animationTextEnter.startInPlayState();

        //Remove Animation ENTER
        getMasterTimer().runOnceAfter(() -> {
            getGameScene().removeUINode(EnterLevelNotification);
            getGameScene().removeUINode(EnterLevel_Tutorial);
        }, Duration.seconds(3));

        //Add Animation EXIT
        getMasterTimer().runOnceAfter(() -> {
            getGameScene().addUINode(ExitLevelNotification);
            getGameScene().addUINode(ExitLevel_Tutorial);
        }, Duration.seconds(3));

        //Exit Animation
        animationBorderExit = getUIFactory().translate(ExitLevelNotification, new Point2D(1280, 200), Duration.seconds(1));
        animationTextExit = getUIFactory().translate(ExitLevel_Tutorial, new Point2D(1800, 260), Duration.seconds(1));

        //Play Animation EXIT
        getMasterTimer().runOnceAfter(() -> {
            animationBorderExit.startInPlayState();
            animationTextExit.startInPlayState();
        }, Duration.seconds(3));

        //Remove Animation EXIT
        getMasterTimer().runOnceAfter(() -> {
            getGameScene().removeUINode(ExitLevelNotification);
            getGameScene().removeUINode(ExitLevel_Tutorial);
        }, Duration.seconds(4));
    }

    /**
     * Initializes Level Indicator: Level 2
     *
     * @author Earl John Laguardia
     */

    //Level: 2
    public void initLevelIndicator_2() {

        //Enter Level: Tutorial
        Text EnterLevel_Tutorial = new Text("Level 2");
        EnterLevel_Tutorial.setFont(Font.font ("Berlin Sans FB Demi", 52));
        EnterLevel_Tutorial.setFill(Color.WHITE);
        EnterLevel_Tutorial.setTranslateX(-520);
        EnterLevel_Tutorial.setTranslateY(260);

        //Enter Border
        Texture EnterLevelNotification = getAssetLoader().loadTexture("LevelNotification.png");
        EnterLevelNotification.setTranslateX(-1280);
        EnterLevelNotification.setTranslateY(200);

        //Exit Level: Tutorial
        Text ExitLevel_Tutorial = new Text("Level 2");
        ExitLevel_Tutorial.setFont(Font.font ("Berlin Sans FB Demi", 52));
        ExitLevel_Tutorial.setFill(Color.WHITE);
        ExitLevel_Tutorial.setTranslateX(520);
        ExitLevel_Tutorial.setTranslateY(260);

        //Exit Border
        Texture ExitLevelNotification = getAssetLoader().loadTexture("LevelNotification.png");
        ExitLevelNotification.setTranslateX(0);
        ExitLevelNotification.setTranslateY(200);

        //Add to Scene ENTER
        getGameScene().addUINode(EnterLevelNotification);
        getGameScene().addUINode(EnterLevel_Tutorial);

        //Enter Animation
        animationBorderEnter = getUIFactory().translate(EnterLevelNotification, new Point2D(0, 200), Duration.seconds(1));
        animationTextEnter = getUIFactory().translate(EnterLevel_Tutorial, new Point2D(520, 260), Duration.seconds(1));

        //Play Animation ENTER
        animationBorderEnter.startInPlayState();
        animationTextEnter.startInPlayState();

        //Remove Animation ENTER
        getMasterTimer().runOnceAfter(() -> {
            getGameScene().removeUINode(EnterLevelNotification);
            getGameScene().removeUINode(EnterLevel_Tutorial);
        }, Duration.seconds(3));

        //Add Animation EXIT
        getMasterTimer().runOnceAfter(() -> {
            getGameScene().addUINode(ExitLevelNotification);
            getGameScene().addUINode(ExitLevel_Tutorial);
        }, Duration.seconds(3));

        //Exit Animation
        animationBorderExit = getUIFactory().translate(ExitLevelNotification, new Point2D(1280, 200), Duration.seconds(1));
        animationTextExit = getUIFactory().translate(ExitLevel_Tutorial, new Point2D(1800, 260), Duration.seconds(1));

        //Play Animation EXIT
        getMasterTimer().runOnceAfter(() -> {
            animationBorderExit.startInPlayState();
            animationTextExit.startInPlayState();
        }, Duration.seconds(3));

        //Remove Animation EXIT
        getMasterTimer().runOnceAfter(() -> {
            getGameScene().removeUINode(ExitLevelNotification);
            getGameScene().removeUINode(ExitLevel_Tutorial);
        }, Duration.seconds(4));
    }

    /**
     * Initializes Level Indicator: Level 3
     *
     * @author Earl John Laguardia
     */

    //Level: 3
    public void initLevelIndicator_3() {

        //Enter Level: Tutorial
        Text EnterLevel_Tutorial = new Text("Level 3");
        EnterLevel_Tutorial.setFont(Font.font ("Berlin Sans FB Demi", 52));
        EnterLevel_Tutorial.setFill(Color.WHITE);
        EnterLevel_Tutorial.setTranslateX(-520);
        EnterLevel_Tutorial.setTranslateY(260);

        //Enter Border
        Texture EnterLevelNotification = getAssetLoader().loadTexture("LevelNotification.png");
        EnterLevelNotification.setTranslateX(-1280);
        EnterLevelNotification.setTranslateY(200);

        //Exit Level: Tutorial
        Text ExitLevel_Tutorial = new Text("Level 3");
        ExitLevel_Tutorial.setFont(Font.font ("Berlin Sans FB Demi", 52));
        ExitLevel_Tutorial.setFill(Color.WHITE);
        ExitLevel_Tutorial.setTranslateX(520);
        ExitLevel_Tutorial.setTranslateY(260);

        //Exit Border
        Texture ExitLevelNotification = getAssetLoader().loadTexture("LevelNotification.png");
        ExitLevelNotification.setTranslateX(0);
        ExitLevelNotification.setTranslateY(200);

        //Add to Scene ENTER
        getGameScene().addUINode(EnterLevelNotification);
        getGameScene().addUINode(EnterLevel_Tutorial);

        //Enter Animation
        animationBorderEnter = getUIFactory().translate(EnterLevelNotification, new Point2D(0, 200), Duration.seconds(1));
        animationTextEnter = getUIFactory().translate(EnterLevel_Tutorial, new Point2D(520, 260), Duration.seconds(1));

        //Play Animation ENTER
        animationBorderEnter.startInPlayState();
        animationTextEnter.startInPlayState();

        //Remove Animation ENTER
        getMasterTimer().runOnceAfter(() -> {
            getGameScene().removeUINode(EnterLevelNotification);
            getGameScene().removeUINode(EnterLevel_Tutorial);
        }, Duration.seconds(3));

        //Add Animation EXIT
        getMasterTimer().runOnceAfter(() -> {
            getGameScene().addUINode(ExitLevelNotification);
            getGameScene().addUINode(ExitLevel_Tutorial);
        }, Duration.seconds(3));

        //Exit Animation
        animationBorderExit = getUIFactory().translate(ExitLevelNotification, new Point2D(1280, 200), Duration.seconds(1));
        animationTextExit = getUIFactory().translate(ExitLevel_Tutorial, new Point2D(1800, 260), Duration.seconds(1));

        //Play Animation EXIT
        getMasterTimer().runOnceAfter(() -> {
            animationBorderExit.startInPlayState();
            animationTextExit.startInPlayState();
        }, Duration.seconds(3));

        //Remove Animation EXIT
        getMasterTimer().runOnceAfter(() -> {
            getGameScene().removeUINode(ExitLevelNotification);
            getGameScene().removeUINode(ExitLevel_Tutorial);
        }, Duration.seconds(4));
    }

    /**
     * Initializes Level Indicator: Level 4
     *
     * @author Earl John Laguardia
     */

    //Level: 4
    public void initLevelIndicator_4() {

        //Enter Level: Tutorial
        Text EnterLevel_Tutorial = new Text("Level 4");
        EnterLevel_Tutorial.setFont(Font.font ("Berlin Sans FB Demi", 52));
        EnterLevel_Tutorial.setFill(Color.WHITE);
        EnterLevel_Tutorial.setTranslateX(-520);
        EnterLevel_Tutorial.setTranslateY(260);

        //Enter Border
        Texture EnterLevelNotification = getAssetLoader().loadTexture("LevelNotification.png");
        EnterLevelNotification.setTranslateX(-1280);
        EnterLevelNotification.setTranslateY(200);

        //Exit Level: Tutorial
        Text ExitLevel_Tutorial = new Text("Level 4");
        ExitLevel_Tutorial.setFont(Font.font ("Berlin Sans FB Demi", 52));
        ExitLevel_Tutorial.setFill(Color.WHITE);
        ExitLevel_Tutorial.setTranslateX(520);
        ExitLevel_Tutorial.setTranslateY(260);

        //Exit Border
        Texture ExitLevelNotification = getAssetLoader().loadTexture("LevelNotification.png");
        ExitLevelNotification.setTranslateX(0);
        ExitLevelNotification.setTranslateY(200);

        //Add to Scene ENTER
        getGameScene().addUINode(EnterLevelNotification);
        getGameScene().addUINode(EnterLevel_Tutorial);

        //Enter Animation
        animationBorderEnter = getUIFactory().translate(EnterLevelNotification, new Point2D(0, 200), Duration.seconds(1));
        animationTextEnter = getUIFactory().translate(EnterLevel_Tutorial, new Point2D(520, 260), Duration.seconds(1));

        //Play Animation ENTER
        animationBorderEnter.startInPlayState();
        animationTextEnter.startInPlayState();

        //Remove Animation ENTER
        getMasterTimer().runOnceAfter(() -> {
            getGameScene().removeUINode(EnterLevelNotification);
            getGameScene().removeUINode(EnterLevel_Tutorial);
        }, Duration.seconds(3));

        //Add Animation EXIT
        getMasterTimer().runOnceAfter(() -> {
            getGameScene().addUINode(ExitLevelNotification);
            getGameScene().addUINode(ExitLevel_Tutorial);
        }, Duration.seconds(3));

        //Exit Animation
        animationBorderExit = getUIFactory().translate(ExitLevelNotification, new Point2D(1280, 200), Duration.seconds(1));
        animationTextExit = getUIFactory().translate(ExitLevel_Tutorial, new Point2D(1800, 260), Duration.seconds(1));

        //Play Animation EXIT
        getMasterTimer().runOnceAfter(() -> {
            animationBorderExit.startInPlayState();
            animationTextExit.startInPlayState();
        }, Duration.seconds(3));

        //Remove Animation EXIT
        getMasterTimer().runOnceAfter(() -> {
            getGameScene().removeUINode(ExitLevelNotification);
            getGameScene().removeUINode(ExitLevel_Tutorial);
        }, Duration.seconds(4));
    }

    /**
     * Initializes Level Indicator: Level 5
     *
     * @author Earl John Laguardia
     */

    //Level: 5
    public void initLevelIndicator_5() {

        //Enter Level: Tutorial
        Text EnterLevel_Tutorial = new Text("Level 5");
        EnterLevel_Tutorial.setFont(Font.font ("Berlin Sans FB Demi", 52));
        EnterLevel_Tutorial.setFill(Color.WHITE);
        EnterLevel_Tutorial.setTranslateX(-520);
        EnterLevel_Tutorial.setTranslateY(260);

        //Enter Border
        Texture EnterLevelNotification = getAssetLoader().loadTexture("LevelNotification.png");
        EnterLevelNotification.setTranslateX(-1280);
        EnterLevelNotification.setTranslateY(200);

        //Exit Level: Tutorial
        Text ExitLevel_Tutorial = new Text("Level 5");
        ExitLevel_Tutorial.setFont(Font.font ("Berlin Sans FB Demi", 52));
        ExitLevel_Tutorial.setFill(Color.WHITE);
        ExitLevel_Tutorial.setTranslateX(520);
        ExitLevel_Tutorial.setTranslateY(260);

        //Exit Border
        Texture ExitLevelNotification = getAssetLoader().loadTexture("LevelNotification.png");
        ExitLevelNotification.setTranslateX(0);
        ExitLevelNotification.setTranslateY(200);

        //Add to Scene ENTER
        getGameScene().addUINode(EnterLevelNotification);
        getGameScene().addUINode(EnterLevel_Tutorial);

        //Enter Animation
        animationBorderEnter = getUIFactory().translate(EnterLevelNotification, new Point2D(0, 200), Duration.seconds(1));
        animationTextEnter = getUIFactory().translate(EnterLevel_Tutorial, new Point2D(520, 260), Duration.seconds(1));

        //Play Animation ENTER
        animationBorderEnter.startInPlayState();
        animationTextEnter.startInPlayState();

        //Remove Animation ENTER
        getMasterTimer().runOnceAfter(() -> {
            getGameScene().removeUINode(EnterLevelNotification);
            getGameScene().removeUINode(EnterLevel_Tutorial);
        }, Duration.seconds(3));

        //Add Animation EXIT
        getMasterTimer().runOnceAfter(() -> {
            getGameScene().addUINode(ExitLevelNotification);
            getGameScene().addUINode(ExitLevel_Tutorial);
        }, Duration.seconds(3));

        //Exit Animation
        animationBorderExit = getUIFactory().translate(ExitLevelNotification, new Point2D(1280, 200), Duration.seconds(1));
        animationTextExit = getUIFactory().translate(ExitLevel_Tutorial, new Point2D(1800, 260), Duration.seconds(1));

        //Play Animation EXIT
        getMasterTimer().runOnceAfter(() -> {
            animationBorderExit.startInPlayState();
            animationTextExit.startInPlayState();
        }, Duration.seconds(3));

        //Remove Animation EXIT
        getMasterTimer().runOnceAfter(() -> {
            getGameScene().removeUINode(ExitLevelNotification);
            getGameScene().removeUINode(ExitLevel_Tutorial);
        }, Duration.seconds(4));
    }

    /**
     * Initializes Level Indicator: Level 6
     *
     * @author Earl John Laguardia
     */

    //Level: 6
    public void initLevelIndicator_6() {

        //Enter Level: Tutorial
        Text EnterLevel_Tutorial = new Text("Level 6");
        EnterLevel_Tutorial.setFont(Font.font ("Berlin Sans FB Demi", 52));
        EnterLevel_Tutorial.setFill(Color.WHITE);
        EnterLevel_Tutorial.setTranslateX(-520);
        EnterLevel_Tutorial.setTranslateY(260);

        //Enter Border
        Texture EnterLevelNotification = getAssetLoader().loadTexture("LevelNotification.png");
        EnterLevelNotification.setTranslateX(-1280);
        EnterLevelNotification.setTranslateY(200);

        //Exit Level: Tutorial
        Text ExitLevel_Tutorial = new Text("Level 6");
        ExitLevel_Tutorial.setFont(Font.font ("Berlin Sans FB Demi", 52));
        ExitLevel_Tutorial.setFill(Color.WHITE);
        ExitLevel_Tutorial.setTranslateX(520);
        ExitLevel_Tutorial.setTranslateY(260);

        //Exit Border
        Texture ExitLevelNotification = getAssetLoader().loadTexture("LevelNotification.png");
        ExitLevelNotification.setTranslateX(0);
        ExitLevelNotification.setTranslateY(200);

        //Add to Scene ENTER
        getGameScene().addUINode(EnterLevelNotification);
        getGameScene().addUINode(EnterLevel_Tutorial);

        //Enter Animation
        animationBorderEnter = getUIFactory().translate(EnterLevelNotification, new Point2D(0, 200), Duration.seconds(1));
        animationTextEnter = getUIFactory().translate(EnterLevel_Tutorial, new Point2D(520, 260), Duration.seconds(1));

        //Play Animation ENTER
        animationBorderEnter.startInPlayState();
        animationTextEnter.startInPlayState();

        //Remove Animation ENTER
        getMasterTimer().runOnceAfter(() -> {
            getGameScene().removeUINode(EnterLevelNotification);
            getGameScene().removeUINode(EnterLevel_Tutorial);
        }, Duration.seconds(3));

        //Add Animation EXIT
        getMasterTimer().runOnceAfter(() -> {
            getGameScene().addUINode(ExitLevelNotification);
            getGameScene().addUINode(ExitLevel_Tutorial);
        }, Duration.seconds(3));

        //Exit Animation
        animationBorderExit = getUIFactory().translate(ExitLevelNotification, new Point2D(1280, 200), Duration.seconds(1));
        animationTextExit = getUIFactory().translate(ExitLevel_Tutorial, new Point2D(1800, 260), Duration.seconds(1));

        //Play Animation EXIT
        getMasterTimer().runOnceAfter(() -> {
            animationBorderExit.startInPlayState();
            animationTextExit.startInPlayState();
        }, Duration.seconds(3));

        //Remove Animation EXIT
        getMasterTimer().runOnceAfter(() -> {
            getGameScene().removeUINode(ExitLevelNotification);
            getGameScene().removeUINode(ExitLevel_Tutorial);
        }, Duration.seconds(4));
    }

    /**
     * Initializes Level Indicator: Level 7
     *
     * @author Earl John Laguardia
     */

    //Level: 7
    public void initLevelIndicator_7() {

        //Enter Level: Tutorial
        Text EnterLevel_Tutorial = new Text("Level 7");
        EnterLevel_Tutorial.setFont(Font.font ("Berlin Sans FB Demi", 52));
        EnterLevel_Tutorial.setFill(Color.WHITE);
        EnterLevel_Tutorial.setTranslateX(-520);
        EnterLevel_Tutorial.setTranslateY(260);

        //Enter Border
        Texture EnterLevelNotification = getAssetLoader().loadTexture("LevelNotification.png");
        EnterLevelNotification.setTranslateX(-1280);
        EnterLevelNotification.setTranslateY(200);

        //Exit Level: Tutorial
        Text ExitLevel_Tutorial = new Text("Level 7");
        ExitLevel_Tutorial.setFont(Font.font ("Berlin Sans FB Demi", 52));
        ExitLevel_Tutorial.setFill(Color.WHITE);
        ExitLevel_Tutorial.setTranslateX(520);
        ExitLevel_Tutorial.setTranslateY(260);

        //Exit Border
        Texture ExitLevelNotification = getAssetLoader().loadTexture("LevelNotification.png");
        ExitLevelNotification.setTranslateX(0);
        ExitLevelNotification.setTranslateY(200);

        //Add to Scene ENTER
        getGameScene().addUINode(EnterLevelNotification);
        getGameScene().addUINode(EnterLevel_Tutorial);

        //Enter Animation
        animationBorderEnter = getUIFactory().translate(EnterLevelNotification, new Point2D(0, 200), Duration.seconds(1));
        animationTextEnter = getUIFactory().translate(EnterLevel_Tutorial, new Point2D(520, 260), Duration.seconds(1));

        //Play Animation ENTER
        animationBorderEnter.startInPlayState();
        animationTextEnter.startInPlayState();

        //Remove Animation ENTER
        getMasterTimer().runOnceAfter(() -> {
            getGameScene().removeUINode(EnterLevelNotification);
            getGameScene().removeUINode(EnterLevel_Tutorial);
        }, Duration.seconds(3));

        //Add Animation EXIT
        getMasterTimer().runOnceAfter(() -> {
            getGameScene().addUINode(ExitLevelNotification);
            getGameScene().addUINode(ExitLevel_Tutorial);
        }, Duration.seconds(3));

        //Exit Animation
        animationBorderExit = getUIFactory().translate(ExitLevelNotification, new Point2D(1280, 200), Duration.seconds(1));
        animationTextExit = getUIFactory().translate(ExitLevel_Tutorial, new Point2D(1800, 260), Duration.seconds(1));

        //Play Animation EXIT
        getMasterTimer().runOnceAfter(() -> {
            animationBorderExit.startInPlayState();
            animationTextExit.startInPlayState();
        }, Duration.seconds(3));

        //Remove Animation EXIT
        getMasterTimer().runOnceAfter(() -> {
            getGameScene().removeUINode(ExitLevelNotification);
            getGameScene().removeUINode(ExitLevel_Tutorial);
        }, Duration.seconds(4));
    }

    /**
     * Initializes Level Indicator: Level 8
     *
     * @author Earl John Laguardia
     */

    //Level: 8
    public void initLevelIndicator_8() {

        //Enter Level: Tutorial
        Text EnterLevel_Tutorial = new Text("Level 8");
        EnterLevel_Tutorial.setFont(Font.font ("Berlin Sans FB Demi", 52));
        EnterLevel_Tutorial.setFill(Color.WHITE);
        EnterLevel_Tutorial.setTranslateX(-520);
        EnterLevel_Tutorial.setTranslateY(260);

        //Enter Border
        Texture EnterLevelNotification = getAssetLoader().loadTexture("LevelNotification.png");
        EnterLevelNotification.setTranslateX(-1280);
        EnterLevelNotification.setTranslateY(200);

        //Exit Level: Tutorial
        Text ExitLevel_Tutorial = new Text("Level 8");
        ExitLevel_Tutorial.setFont(Font.font ("Berlin Sans FB Demi", 52));
        ExitLevel_Tutorial.setFill(Color.WHITE);
        ExitLevel_Tutorial.setTranslateX(520);
        ExitLevel_Tutorial.setTranslateY(260);

        //Exit Border
        Texture ExitLevelNotification = getAssetLoader().loadTexture("LevelNotification.png");
        ExitLevelNotification.setTranslateX(0);
        ExitLevelNotification.setTranslateY(200);

        //Add to Scene ENTER
        getGameScene().addUINode(EnterLevelNotification);
        getGameScene().addUINode(EnterLevel_Tutorial);

        //Enter Animation
        animationBorderEnter = getUIFactory().translate(EnterLevelNotification, new Point2D(0, 200), Duration.seconds(1));
        animationTextEnter = getUIFactory().translate(EnterLevel_Tutorial, new Point2D(520, 260), Duration.seconds(1));

        //Play Animation ENTER
        animationBorderEnter.startInPlayState();
        animationTextEnter.startInPlayState();

        //Remove Animation ENTER
        getMasterTimer().runOnceAfter(() -> {
            getGameScene().removeUINode(EnterLevelNotification);
            getGameScene().removeUINode(EnterLevel_Tutorial);
        }, Duration.seconds(3));

        //Add Animation EXIT
        getMasterTimer().runOnceAfter(() -> {
            getGameScene().addUINode(ExitLevelNotification);
            getGameScene().addUINode(ExitLevel_Tutorial);
        }, Duration.seconds(3));

        //Exit Animation
        animationBorderExit = getUIFactory().translate(ExitLevelNotification, new Point2D(1280, 200), Duration.seconds(1));
        animationTextExit = getUIFactory().translate(ExitLevel_Tutorial, new Point2D(1800, 260), Duration.seconds(1));

        //Play Animation EXIT
        getMasterTimer().runOnceAfter(() -> {
            animationBorderExit.startInPlayState();
            animationTextExit.startInPlayState();
        }, Duration.seconds(3));

        //Remove Animation EXIT
        getMasterTimer().runOnceAfter(() -> {
            getGameScene().removeUINode(ExitLevelNotification);
            getGameScene().removeUINode(ExitLevel_Tutorial);
        }, Duration.seconds(4));
    }

    /**
     * Initializes Level Indicator: Level 9
     *
     * @author Earl John Laguardia
     */

    //Level: 9
    public void initLevelIndicator_9() {

        //Enter Level: Tutorial
        Text EnterLevel_Tutorial = new Text("Level 9");
        EnterLevel_Tutorial.setFont(Font.font ("Berlin Sans FB Demi", 52));
        EnterLevel_Tutorial.setFill(Color.WHITE);
        EnterLevel_Tutorial.setTranslateX(-520);
        EnterLevel_Tutorial.setTranslateY(260);

        //Enter Border
        Texture EnterLevelNotification = getAssetLoader().loadTexture("LevelNotification.png");
        EnterLevelNotification.setTranslateX(-1280);
        EnterLevelNotification.setTranslateY(200);

        //Exit Level: Tutorial
        Text ExitLevel_Tutorial = new Text("Level 9");
        ExitLevel_Tutorial.setFont(Font.font ("Berlin Sans FB Demi", 52));
        ExitLevel_Tutorial.setFill(Color.WHITE);
        ExitLevel_Tutorial.setTranslateX(520);
        ExitLevel_Tutorial.setTranslateY(260);

        //Exit Border
        Texture ExitLevelNotification = getAssetLoader().loadTexture("LevelNotification.png");
        ExitLevelNotification.setTranslateX(0);
        ExitLevelNotification.setTranslateY(200);

        //Add to Scene ENTER
        getGameScene().addUINode(EnterLevelNotification);
        getGameScene().addUINode(EnterLevel_Tutorial);

        //Enter Animation
        animationBorderEnter = getUIFactory().translate(EnterLevelNotification, new Point2D(0, 200), Duration.seconds(1));
        animationTextEnter = getUIFactory().translate(EnterLevel_Tutorial, new Point2D(520, 260), Duration.seconds(1));

        //Play Animation ENTER
        animationBorderEnter.startInPlayState();
        animationTextEnter.startInPlayState();

        //Remove Animation ENTER
        getMasterTimer().runOnceAfter(() -> {
            getGameScene().removeUINode(EnterLevelNotification);
            getGameScene().removeUINode(EnterLevel_Tutorial);
        }, Duration.seconds(3));

        //Add Animation EXIT
        getMasterTimer().runOnceAfter(() -> {
            getGameScene().addUINode(ExitLevelNotification);
            getGameScene().addUINode(ExitLevel_Tutorial);
        }, Duration.seconds(3));

        //Exit Animation
        animationBorderExit = getUIFactory().translate(ExitLevelNotification, new Point2D(1280, 200), Duration.seconds(1));
        animationTextExit = getUIFactory().translate(ExitLevel_Tutorial, new Point2D(1800, 260), Duration.seconds(1));

        //Play Animation EXIT
        getMasterTimer().runOnceAfter(() -> {
            animationBorderExit.startInPlayState();
            animationTextExit.startInPlayState();
        }, Duration.seconds(3));

        //Remove Animation EXIT
        getMasterTimer().runOnceAfter(() -> {
            getGameScene().removeUINode(ExitLevelNotification);
            getGameScene().removeUINode(ExitLevel_Tutorial);
        }, Duration.seconds(4));
    }

    /**
     * Initializes Level Indicator: Level 11
     *
     * @author Earl John Laguardia
     */

    //Level: 11
    public void initLevelIndicator_11() {

        //Enter Level: Tutorial
        Text EnterLevel_Tutorial = new Text("Level 11");
        EnterLevel_Tutorial.setFont(Font.font ("Berlin Sans FB Demi", 52));
        EnterLevel_Tutorial.setFill(Color.WHITE);
        EnterLevel_Tutorial.setTranslateX(-520);
        EnterLevel_Tutorial.setTranslateY(260);

        //Enter Border
        Texture EnterLevelNotification = getAssetLoader().loadTexture("LevelNotification.png");
        EnterLevelNotification.setTranslateX(-1280);
        EnterLevelNotification.setTranslateY(200);

        //Exit Level: Tutorial
        Text ExitLevel_Tutorial = new Text("Level 11");
        ExitLevel_Tutorial.setFont(Font.font ("Berlin Sans FB Demi", 52));
        ExitLevel_Tutorial.setFill(Color.WHITE);
        ExitLevel_Tutorial.setTranslateX(520);
        ExitLevel_Tutorial.setTranslateY(260);

        //Exit Border
        Texture ExitLevelNotification = getAssetLoader().loadTexture("LevelNotification.png");
        ExitLevelNotification.setTranslateX(0);
        ExitLevelNotification.setTranslateY(200);

        //Add to Scene ENTER
        getGameScene().addUINode(EnterLevelNotification);
        getGameScene().addUINode(EnterLevel_Tutorial);

        //Enter Animation
        animationBorderEnter = getUIFactory().translate(EnterLevelNotification, new Point2D(0, 200), Duration.seconds(1));
        animationTextEnter = getUIFactory().translate(EnterLevel_Tutorial, new Point2D(520, 260), Duration.seconds(1));

        //Play Animation ENTER
        animationBorderEnter.startInPlayState();
        animationTextEnter.startInPlayState();

        //Remove Animation ENTER
        getMasterTimer().runOnceAfter(() -> {
            getGameScene().removeUINode(EnterLevelNotification);
            getGameScene().removeUINode(EnterLevel_Tutorial);
        }, Duration.seconds(3));

        //Add Animation EXIT
        getMasterTimer().runOnceAfter(() -> {
            getGameScene().addUINode(ExitLevelNotification);
            getGameScene().addUINode(ExitLevel_Tutorial);
        }, Duration.seconds(3));

        //Exit Animation
        animationBorderExit = getUIFactory().translate(ExitLevelNotification, new Point2D(1280, 200), Duration.seconds(1));
        animationTextExit = getUIFactory().translate(ExitLevel_Tutorial, new Point2D(1800, 260), Duration.seconds(1));

        //Play Animation EXIT
        getMasterTimer().runOnceAfter(() -> {
            animationBorderExit.startInPlayState();
            animationTextExit.startInPlayState();
        }, Duration.seconds(3));

        //Remove Animation EXIT
        getMasterTimer().runOnceAfter(() -> {
            getGameScene().removeUINode(ExitLevelNotification);
            getGameScene().removeUINode(ExitLevel_Tutorial);
        }, Duration.seconds(4));
    }

    /**
     * Initializes Level Indicator: Level 12
     *
     * @author Earl John Laguardia
     */

    //Level: 12
    public void initLevelIndicator_12() {

        //Enter Level: Tutorial
        Text EnterLevel_Tutorial = new Text("Level 12");
        EnterLevel_Tutorial.setFont(Font.font ("Berlin Sans FB Demi", 52));
        EnterLevel_Tutorial.setFill(Color.WHITE);
        EnterLevel_Tutorial.setTranslateX(-520);
        EnterLevel_Tutorial.setTranslateY(260);

        //Enter Border
        Texture EnterLevelNotification = getAssetLoader().loadTexture("LevelNotification.png");
        EnterLevelNotification.setTranslateX(-1280);
        EnterLevelNotification.setTranslateY(200);

        //Exit Level: Tutorial
        Text ExitLevel_Tutorial = new Text("Level 12");
        ExitLevel_Tutorial.setFont(Font.font ("Berlin Sans FB Demi", 52));
        ExitLevel_Tutorial.setFill(Color.WHITE);
        ExitLevel_Tutorial.setTranslateX(520);
        ExitLevel_Tutorial.setTranslateY(260);

        //Exit Border
        Texture ExitLevelNotification = getAssetLoader().loadTexture("LevelNotification.png");
        ExitLevelNotification.setTranslateX(0);
        ExitLevelNotification.setTranslateY(200);

        //Add to Scene ENTER
        getGameScene().addUINode(EnterLevelNotification);
        getGameScene().addUINode(EnterLevel_Tutorial);

        //Enter Animation
        animationBorderEnter = getUIFactory().translate(EnterLevelNotification, new Point2D(0, 200), Duration.seconds(1));
        animationTextEnter = getUIFactory().translate(EnterLevel_Tutorial, new Point2D(520, 260), Duration.seconds(1));

        //Play Animation ENTER
        animationBorderEnter.startInPlayState();
        animationTextEnter.startInPlayState();

        //Remove Animation ENTER
        getMasterTimer().runOnceAfter(() -> {
            getGameScene().removeUINode(EnterLevelNotification);
            getGameScene().removeUINode(EnterLevel_Tutorial);
        }, Duration.seconds(3));

        //Add Animation EXIT
        getMasterTimer().runOnceAfter(() -> {
            getGameScene().addUINode(ExitLevelNotification);
            getGameScene().addUINode(ExitLevel_Tutorial);
        }, Duration.seconds(3));

        //Exit Animation
        animationBorderExit = getUIFactory().translate(ExitLevelNotification, new Point2D(1280, 200), Duration.seconds(1));
        animationTextExit = getUIFactory().translate(ExitLevel_Tutorial, new Point2D(1800, 260), Duration.seconds(1));

        //Play Animation EXIT
        getMasterTimer().runOnceAfter(() -> {
            animationBorderExit.startInPlayState();
            animationTextExit.startInPlayState();
        }, Duration.seconds(3));

        //Remove Animation EXIT
        getMasterTimer().runOnceAfter(() -> {
            getGameScene().removeUINode(ExitLevelNotification);
            getGameScene().removeUINode(ExitLevel_Tutorial);
        }, Duration.seconds(4));
    }

    /**
     * Initializes Level Indicator: Level 13
     *
     * @author Earl John Laguardia
     */

    //Level: 13
    public void initLevelIndicator_13() {

        //Enter Level: Tutorial
        Text EnterLevel_Tutorial = new Text("Level 13");
        EnterLevel_Tutorial.setFont(Font.font ("Berlin Sans FB Demi", 52));
        EnterLevel_Tutorial.setFill(Color.WHITE);
        EnterLevel_Tutorial.setTranslateX(-520);
        EnterLevel_Tutorial.setTranslateY(260);

        //Enter Border
        Texture EnterLevelNotification = getAssetLoader().loadTexture("LevelNotification.png");
        EnterLevelNotification.setTranslateX(-1280);
        EnterLevelNotification.setTranslateY(200);

        //Exit Level: Tutorial
        Text ExitLevel_Tutorial = new Text("Level 13");
        ExitLevel_Tutorial.setFont(Font.font ("Berlin Sans FB Demi", 52));
        ExitLevel_Tutorial.setFill(Color.WHITE);
        ExitLevel_Tutorial.setTranslateX(520);
        ExitLevel_Tutorial.setTranslateY(260);

        //Exit Border
        Texture ExitLevelNotification = getAssetLoader().loadTexture("LevelNotification.png");
        ExitLevelNotification.setTranslateX(0);
        ExitLevelNotification.setTranslateY(200);

        //Add to Scene ENTER
        getGameScene().addUINode(EnterLevelNotification);
        getGameScene().addUINode(EnterLevel_Tutorial);

        //Enter Animation
        animationBorderEnter = getUIFactory().translate(EnterLevelNotification, new Point2D(0, 200), Duration.seconds(1));
        animationTextEnter = getUIFactory().translate(EnterLevel_Tutorial, new Point2D(520, 260), Duration.seconds(1));

        //Play Animation ENTER
        animationBorderEnter.startInPlayState();
        animationTextEnter.startInPlayState();

        //Remove Animation ENTER
        getMasterTimer().runOnceAfter(() -> {
            getGameScene().removeUINode(EnterLevelNotification);
            getGameScene().removeUINode(EnterLevel_Tutorial);
        }, Duration.seconds(3));

        //Add Animation EXIT
        getMasterTimer().runOnceAfter(() -> {
            getGameScene().addUINode(ExitLevelNotification);
            getGameScene().addUINode(ExitLevel_Tutorial);
        }, Duration.seconds(3));

        //Exit Animation
        animationBorderExit = getUIFactory().translate(ExitLevelNotification, new Point2D(1280, 200), Duration.seconds(1));
        animationTextExit = getUIFactory().translate(ExitLevel_Tutorial, new Point2D(1800, 260), Duration.seconds(1));

        //Play Animation EXIT
        getMasterTimer().runOnceAfter(() -> {
            animationBorderExit.startInPlayState();
            animationTextExit.startInPlayState();
        }, Duration.seconds(3));

        //Remove Animation EXIT
        getMasterTimer().runOnceAfter(() -> {
            getGameScene().removeUINode(ExitLevelNotification);
            getGameScene().removeUINode(ExitLevel_Tutorial);
        }, Duration.seconds(4));
    }

    /**
     * Initializes Level Indicator: Level 14
     *
     * @author Earl John Laguardia
     */

    //Level: 14
    public void initLevelIndicator_14() {

        //Enter Level: Tutorial
        Text EnterLevel_Tutorial = new Text("Level 14");
        EnterLevel_Tutorial.setFont(Font.font ("Berlin Sans FB Demi", 52));
        EnterLevel_Tutorial.setFill(Color.WHITE);
        EnterLevel_Tutorial.setTranslateX(-520);
        EnterLevel_Tutorial.setTranslateY(260);

        //Enter Border
        Texture EnterLevelNotification = getAssetLoader().loadTexture("LevelNotification.png");
        EnterLevelNotification.setTranslateX(-1280);
        EnterLevelNotification.setTranslateY(200);

        //Exit Level: Tutorial
        Text ExitLevel_Tutorial = new Text("Level 14");
        ExitLevel_Tutorial.setFont(Font.font ("Berlin Sans FB Demi", 52));
        ExitLevel_Tutorial.setFill(Color.WHITE);
        ExitLevel_Tutorial.setTranslateX(520);
        ExitLevel_Tutorial.setTranslateY(260);

        //Exit Border
        Texture ExitLevelNotification = getAssetLoader().loadTexture("LevelNotification.png");
        ExitLevelNotification.setTranslateX(0);
        ExitLevelNotification.setTranslateY(200);

        //Add to Scene ENTER
        getGameScene().addUINode(EnterLevelNotification);
        getGameScene().addUINode(EnterLevel_Tutorial);

        //Enter Animation
        animationBorderEnter = getUIFactory().translate(EnterLevelNotification, new Point2D(0, 200), Duration.seconds(1));
        animationTextEnter = getUIFactory().translate(EnterLevel_Tutorial, new Point2D(520, 260), Duration.seconds(1));

        //Play Animation ENTER
        animationBorderEnter.startInPlayState();
        animationTextEnter.startInPlayState();

        //Remove Animation ENTER
        getMasterTimer().runOnceAfter(() -> {
            getGameScene().removeUINode(EnterLevelNotification);
            getGameScene().removeUINode(EnterLevel_Tutorial);
        }, Duration.seconds(3));

        //Add Animation EXIT
        getMasterTimer().runOnceAfter(() -> {
            getGameScene().addUINode(ExitLevelNotification);
            getGameScene().addUINode(ExitLevel_Tutorial);
        }, Duration.seconds(3));

        //Exit Animation
        animationBorderExit = getUIFactory().translate(ExitLevelNotification, new Point2D(1280, 200), Duration.seconds(1));
        animationTextExit = getUIFactory().translate(ExitLevel_Tutorial, new Point2D(1800, 260), Duration.seconds(1));

        //Play Animation EXIT
        getMasterTimer().runOnceAfter(() -> {
            animationBorderExit.startInPlayState();
            animationTextExit.startInPlayState();
        }, Duration.seconds(3));

        //Remove Animation EXIT
        getMasterTimer().runOnceAfter(() -> {
            getGameScene().removeUINode(ExitLevelNotification);
            getGameScene().removeUINode(ExitLevel_Tutorial);
        }, Duration.seconds(4));
    }

    /**
     * Initializes Level Indicator: Level 15
     *
     * @author Earl John Laguardia
     */

    //Level: 15
    public void initLevelIndicator_15() {

        //Enter Level: Tutorial
        Text EnterLevel_Tutorial = new Text("Level 15");
        EnterLevel_Tutorial.setFont(Font.font ("Berlin Sans FB Demi", 52));
        EnterLevel_Tutorial.setFill(Color.WHITE);
        EnterLevel_Tutorial.setTranslateX(-520);
        EnterLevel_Tutorial.setTranslateY(260);

        //Enter Border
        Texture EnterLevelNotification = getAssetLoader().loadTexture("LevelNotification.png");
        EnterLevelNotification.setTranslateX(-1280);
        EnterLevelNotification.setTranslateY(200);

        //Exit Level: Tutorial
        Text ExitLevel_Tutorial = new Text("Level 15");
        ExitLevel_Tutorial.setFont(Font.font ("Berlin Sans FB Demi", 52));
        ExitLevel_Tutorial.setFill(Color.WHITE);
        ExitLevel_Tutorial.setTranslateX(520);
        ExitLevel_Tutorial.setTranslateY(260);

        //Exit Border
        Texture ExitLevelNotification = getAssetLoader().loadTexture("LevelNotification.png");
        ExitLevelNotification.setTranslateX(0);
        ExitLevelNotification.setTranslateY(200);

        //Add to Scene ENTER
        getGameScene().addUINode(EnterLevelNotification);
        getGameScene().addUINode(EnterLevel_Tutorial);

        //Enter Animation
        animationBorderEnter = getUIFactory().translate(EnterLevelNotification, new Point2D(0, 200), Duration.seconds(1));
        animationTextEnter = getUIFactory().translate(EnterLevel_Tutorial, new Point2D(520, 260), Duration.seconds(1));

        //Play Animation ENTER
        animationBorderEnter.startInPlayState();
        animationTextEnter.startInPlayState();

        //Remove Animation ENTER
        getMasterTimer().runOnceAfter(() -> {
            getGameScene().removeUINode(EnterLevelNotification);
            getGameScene().removeUINode(EnterLevel_Tutorial);
        }, Duration.seconds(3));

        //Add Animation EXIT
        getMasterTimer().runOnceAfter(() -> {
            getGameScene().addUINode(ExitLevelNotification);
            getGameScene().addUINode(ExitLevel_Tutorial);
        }, Duration.seconds(3));

        //Exit Animation
        animationBorderExit = getUIFactory().translate(ExitLevelNotification, new Point2D(1280, 200), Duration.seconds(1));
        animationTextExit = getUIFactory().translate(ExitLevel_Tutorial, new Point2D(1800, 260), Duration.seconds(1));

        //Play Animation EXIT
        getMasterTimer().runOnceAfter(() -> {
            animationBorderExit.startInPlayState();
            animationTextExit.startInPlayState();
        }, Duration.seconds(3));

        //Remove Animation EXIT
        getMasterTimer().runOnceAfter(() -> {
            getGameScene().removeUINode(ExitLevelNotification);
            getGameScene().removeUINode(ExitLevel_Tutorial);
        }, Duration.seconds(4));
    }

    /**
     * Initializes Level Indicator: Level 16
     *
     * @author Earl John Laguardia
     */

    //Level: 16
    public void initLevelIndicator_16() {

        //Enter Level: Tutorial
        Text EnterLevel_Tutorial = new Text("Level 16");
        EnterLevel_Tutorial.setFont(Font.font ("Berlin Sans FB Demi", 52));
        EnterLevel_Tutorial.setFill(Color.WHITE);
        EnterLevel_Tutorial.setTranslateX(-520);
        EnterLevel_Tutorial.setTranslateY(260);

        //Enter Border
        Texture EnterLevelNotification = getAssetLoader().loadTexture("LevelNotification.png");
        EnterLevelNotification.setTranslateX(-1280);
        EnterLevelNotification.setTranslateY(200);

        //Exit Level: Tutorial
        Text ExitLevel_Tutorial = new Text("Level 16");
        ExitLevel_Tutorial.setFont(Font.font ("Berlin Sans FB Demi", 52));
        ExitLevel_Tutorial.setFill(Color.WHITE);
        ExitLevel_Tutorial.setTranslateX(520);
        ExitLevel_Tutorial.setTranslateY(260);

        //Exit Border
        Texture ExitLevelNotification = getAssetLoader().loadTexture("LevelNotification.png");
        ExitLevelNotification.setTranslateX(0);
        ExitLevelNotification.setTranslateY(200);

        //Add to Scene ENTER
        getGameScene().addUINode(EnterLevelNotification);
        getGameScene().addUINode(EnterLevel_Tutorial);

        //Enter Animation
        animationBorderEnter = getUIFactory().translate(EnterLevelNotification, new Point2D(0, 200), Duration.seconds(1));
        animationTextEnter = getUIFactory().translate(EnterLevel_Tutorial, new Point2D(520, 260), Duration.seconds(1));

        //Play Animation ENTER
        animationBorderEnter.startInPlayState();
        animationTextEnter.startInPlayState();

        //Remove Animation ENTER
        getMasterTimer().runOnceAfter(() -> {
            getGameScene().removeUINode(EnterLevelNotification);
            getGameScene().removeUINode(EnterLevel_Tutorial);
        }, Duration.seconds(3));

        //Add Animation EXIT
        getMasterTimer().runOnceAfter(() -> {
            getGameScene().addUINode(ExitLevelNotification);
            getGameScene().addUINode(ExitLevel_Tutorial);
        }, Duration.seconds(3));

        //Exit Animation
        animationBorderExit = getUIFactory().translate(ExitLevelNotification, new Point2D(1280, 200), Duration.seconds(1));
        animationTextExit = getUIFactory().translate(ExitLevel_Tutorial, new Point2D(1800, 260), Duration.seconds(1));

        //Play Animation EXIT
        getMasterTimer().runOnceAfter(() -> {
            animationBorderExit.startInPlayState();
            animationTextExit.startInPlayState();
        }, Duration.seconds(3));

        //Remove Animation EXIT
        getMasterTimer().runOnceAfter(() -> {
            getGameScene().removeUINode(ExitLevelNotification);
            getGameScene().removeUINode(ExitLevel_Tutorial);
        }, Duration.seconds(4));
    }

    /**
     * Initializes Level Indicator: Level 17
     *
     * @author Earl John Laguardia
     */

    //Level: 17
    public void initLevelIndicator_17() {

        //Enter Level: Tutorial
        Text EnterLevel_Tutorial = new Text("Level 17");
        EnterLevel_Tutorial.setFont(Font.font ("Berlin Sans FB Demi", 52));
        EnterLevel_Tutorial.setFill(Color.WHITE);
        EnterLevel_Tutorial.setTranslateX(-520);
        EnterLevel_Tutorial.setTranslateY(260);

        //Enter Border
        Texture EnterLevelNotification = getAssetLoader().loadTexture("LevelNotification.png");
        EnterLevelNotification.setTranslateX(-1280);
        EnterLevelNotification.setTranslateY(200);

        //Exit Level: Tutorial
        Text ExitLevel_Tutorial = new Text("Level 17");
        ExitLevel_Tutorial.setFont(Font.font ("Berlin Sans FB Demi", 52));
        ExitLevel_Tutorial.setFill(Color.WHITE);
        ExitLevel_Tutorial.setTranslateX(520);
        ExitLevel_Tutorial.setTranslateY(260);

        //Exit Border
        Texture ExitLevelNotification = getAssetLoader().loadTexture("LevelNotification.png");
        ExitLevelNotification.setTranslateX(0);
        ExitLevelNotification.setTranslateY(200);

        //Add to Scene ENTER
        getGameScene().addUINode(EnterLevelNotification);
        getGameScene().addUINode(EnterLevel_Tutorial);

        //Enter Animation
        animationBorderEnter = getUIFactory().translate(EnterLevelNotification, new Point2D(0, 200), Duration.seconds(1));
        animationTextEnter = getUIFactory().translate(EnterLevel_Tutorial, new Point2D(520, 260), Duration.seconds(1));

        //Play Animation ENTER
        animationBorderEnter.startInPlayState();
        animationTextEnter.startInPlayState();

        //Remove Animation ENTER
        getMasterTimer().runOnceAfter(() -> {
            getGameScene().removeUINode(EnterLevelNotification);
            getGameScene().removeUINode(EnterLevel_Tutorial);
        }, Duration.seconds(3));

        //Add Animation EXIT
        getMasterTimer().runOnceAfter(() -> {
            getGameScene().addUINode(ExitLevelNotification);
            getGameScene().addUINode(ExitLevel_Tutorial);
        }, Duration.seconds(3));

        //Exit Animation
        animationBorderExit = getUIFactory().translate(ExitLevelNotification, new Point2D(1280, 200), Duration.seconds(1));
        animationTextExit = getUIFactory().translate(ExitLevel_Tutorial, new Point2D(1800, 260), Duration.seconds(1));

        //Play Animation EXIT
        getMasterTimer().runOnceAfter(() -> {
            animationBorderExit.startInPlayState();
            animationTextExit.startInPlayState();
        }, Duration.seconds(3));

        //Remove Animation EXIT
        getMasterTimer().runOnceAfter(() -> {
            getGameScene().removeUINode(ExitLevelNotification);
            getGameScene().removeUINode(ExitLevel_Tutorial);
        }, Duration.seconds(4));
    }

    /**
     * Initializes Level Indicator: Level 18
     *
     * @author Earl John Laguardia
     */

    //Level: 18
    public void initLevelIndicator_18() {

        //Enter Level: Tutorial
        Text EnterLevel_Tutorial = new Text("Level 18");
        EnterLevel_Tutorial.setFont(Font.font ("Berlin Sans FB Demi", 52));
        EnterLevel_Tutorial.setFill(Color.WHITE);
        EnterLevel_Tutorial.setTranslateX(-520);
        EnterLevel_Tutorial.setTranslateY(260);

        //Enter Border
        Texture EnterLevelNotification = getAssetLoader().loadTexture("LevelNotification.png");
        EnterLevelNotification.setTranslateX(-1280);
        EnterLevelNotification.setTranslateY(200);

        //Exit Level: Tutorial
        Text ExitLevel_Tutorial = new Text("Level 18");
        ExitLevel_Tutorial.setFont(Font.font ("Berlin Sans FB Demi", 52));
        ExitLevel_Tutorial.setFill(Color.WHITE);
        ExitLevel_Tutorial.setTranslateX(520);
        ExitLevel_Tutorial.setTranslateY(260);

        //Exit Border
        Texture ExitLevelNotification = getAssetLoader().loadTexture("LevelNotification.png");
        ExitLevelNotification.setTranslateX(0);
        ExitLevelNotification.setTranslateY(200);

        //Add to Scene ENTER
        getGameScene().addUINode(EnterLevelNotification);
        getGameScene().addUINode(EnterLevel_Tutorial);

        //Enter Animation
        animationBorderEnter = getUIFactory().translate(EnterLevelNotification, new Point2D(0, 200), Duration.seconds(1));
        animationTextEnter = getUIFactory().translate(EnterLevel_Tutorial, new Point2D(520, 260), Duration.seconds(1));

        //Play Animation ENTER
        animationBorderEnter.startInPlayState();
        animationTextEnter.startInPlayState();

        //Remove Animation ENTER
        getMasterTimer().runOnceAfter(() -> {
            getGameScene().removeUINode(EnterLevelNotification);
            getGameScene().removeUINode(EnterLevel_Tutorial);
        }, Duration.seconds(3));

        //Add Animation EXIT
        getMasterTimer().runOnceAfter(() -> {
            getGameScene().addUINode(ExitLevelNotification);
            getGameScene().addUINode(ExitLevel_Tutorial);
        }, Duration.seconds(3));

        //Exit Animation
        animationBorderExit = getUIFactory().translate(ExitLevelNotification, new Point2D(1280, 200), Duration.seconds(1));
        animationTextExit = getUIFactory().translate(ExitLevel_Tutorial, new Point2D(1800, 260), Duration.seconds(1));

        //Play Animation EXIT
        getMasterTimer().runOnceAfter(() -> {
            animationBorderExit.startInPlayState();
            animationTextExit.startInPlayState();
        }, Duration.seconds(3));

        //Remove Animation EXIT
        getMasterTimer().runOnceAfter(() -> {
            getGameScene().removeUINode(ExitLevelNotification);
            getGameScene().removeUINode(ExitLevel_Tutorial);
        }, Duration.seconds(4));
    }

    /**
     * Initializes Level Indicator: Level 19
     *
     * @author Earl John Laguardia
     */

    //Level: 19
    public void initLevelIndicator_19() {

        //Enter Level: Tutorial
        Text EnterLevel_Tutorial = new Text("Level 19");
        EnterLevel_Tutorial.setFont(Font.font ("Berlin Sans FB Demi", 52));
        EnterLevel_Tutorial.setFill(Color.WHITE);
        EnterLevel_Tutorial.setTranslateX(-520);
        EnterLevel_Tutorial.setTranslateY(260);

        //Enter Border
        Texture EnterLevelNotification = getAssetLoader().loadTexture("LevelNotification.png");
        EnterLevelNotification.setTranslateX(-1280);
        EnterLevelNotification.setTranslateY(200);

        //Exit Level: Tutorial
        Text ExitLevel_Tutorial = new Text("Level 19");
        ExitLevel_Tutorial.setFont(Font.font ("Berlin Sans FB Demi", 52));
        ExitLevel_Tutorial.setFill(Color.WHITE);
        ExitLevel_Tutorial.setTranslateX(520);
        ExitLevel_Tutorial.setTranslateY(260);

        //Exit Border
        Texture ExitLevelNotification = getAssetLoader().loadTexture("LevelNotification.png");
        ExitLevelNotification.setTranslateX(0);
        ExitLevelNotification.setTranslateY(200);

        //Add to Scene ENTER
        getGameScene().addUINode(EnterLevelNotification);
        getGameScene().addUINode(EnterLevel_Tutorial);

        //Enter Animation
        animationBorderEnter = getUIFactory().translate(EnterLevelNotification, new Point2D(0, 200), Duration.seconds(1));
        animationTextEnter = getUIFactory().translate(EnterLevel_Tutorial, new Point2D(520, 260), Duration.seconds(1));

        //Play Animation ENTER
        animationBorderEnter.startInPlayState();
        animationTextEnter.startInPlayState();

        //Remove Animation ENTER
        getMasterTimer().runOnceAfter(() -> {
            getGameScene().removeUINode(EnterLevelNotification);
            getGameScene().removeUINode(EnterLevel_Tutorial);
        }, Duration.seconds(3));

        //Add Animation EXIT
        getMasterTimer().runOnceAfter(() -> {
            getGameScene().addUINode(ExitLevelNotification);
            getGameScene().addUINode(ExitLevel_Tutorial);
        }, Duration.seconds(3));

        //Exit Animation
        animationBorderExit = getUIFactory().translate(ExitLevelNotification, new Point2D(1280, 200), Duration.seconds(1));
        animationTextExit = getUIFactory().translate(ExitLevel_Tutorial, new Point2D(1800, 260), Duration.seconds(1));

        //Play Animation EXIT
        getMasterTimer().runOnceAfter(() -> {
            animationBorderExit.startInPlayState();
            animationTextExit.startInPlayState();
        }, Duration.seconds(3));

        //Remove Animation EXIT
        getMasterTimer().runOnceAfter(() -> {
            getGameScene().removeUINode(ExitLevelNotification);
            getGameScene().removeUINode(ExitLevel_Tutorial);
        }, Duration.seconds(4));
    }

    /**
     * Initializes Level Indicator: Boss Fight
     *
     * @author Earl John Laguardia
     */

    //Level: Boss Fight
    public void initLevelIndicator_BOSSFIGHT() {

        //Enter Level: Boss Fight
        Text EnterLevel_Tutorial = new Text("BOSS FIGHT!");
        EnterLevel_Tutorial.setFont(Font.font ("Berlin Sans FB Demi", 52));
        EnterLevel_Tutorial.setFill(Color.WHITE);
        EnterLevel_Tutorial.setTranslateX(-490);
        EnterLevel_Tutorial.setTranslateY(260);

        //Enter Border
        Texture EnterLevelNotification = getAssetLoader().loadTexture("LevelNotification.png");
        EnterLevelNotification.setTranslateX(-1280);
        EnterLevelNotification.setTranslateY(200);

        //Exit Level: Tutorial
        Text ExitLevel_Tutorial = new Text("BOSS FIGHT!");
        ExitLevel_Tutorial.setFont(Font.font ("Berlin Sans FB Demi", 52));
        ExitLevel_Tutorial.setFill(Color.WHITE);
        ExitLevel_Tutorial.setTranslateX(490);
        ExitLevel_Tutorial.setTranslateY(260);

        //Exit Border
        Texture ExitLevelNotification = getAssetLoader().loadTexture("LevelNotification.png");
        ExitLevelNotification.setTranslateX(0);
        ExitLevelNotification.setTranslateY(200);

        //Add to Scene ENTER
        getGameScene().addUINode(EnterLevelNotification);
        getGameScene().addUINode(EnterLevel_Tutorial);

        //Enter Animation
        animationBorderEnter = getUIFactory().translate(EnterLevelNotification, new Point2D(0, 200), Duration.seconds(1));
        animationTextEnter = getUIFactory().translate(EnterLevel_Tutorial, new Point2D(490, 260), Duration.seconds(1));

        //Play Animation ENTER
        animationBorderEnter.startInPlayState();
        animationTextEnter.startInPlayState();

        //Remove Animation ENTER
        getMasterTimer().runOnceAfter(() -> {
            getGameScene().removeUINode(EnterLevelNotification);
            getGameScene().removeUINode(EnterLevel_Tutorial);
        }, Duration.seconds(3));

        //Add Animation EXIT
        getMasterTimer().runOnceAfter(() -> {
            getGameScene().addUINode(ExitLevelNotification);
            getGameScene().addUINode(ExitLevel_Tutorial);
        }, Duration.seconds(3));

        //Exit Animation
        animationBorderExit = getUIFactory().translate(ExitLevelNotification, new Point2D(1280, 200), Duration.seconds(1));
        animationTextExit = getUIFactory().translate(ExitLevel_Tutorial, new Point2D(1770, 260), Duration.seconds(1));

        //Play Animation EXIT
        getMasterTimer().runOnceAfter(() -> {
            animationBorderExit.startInPlayState();
            animationTextExit.startInPlayState();
        }, Duration.seconds(3));

        //Remove Animation EXIT
        getMasterTimer().runOnceAfter(() -> {
            getGameScene().removeUINode(ExitLevelNotification);
            getGameScene().removeUINode(ExitLevel_Tutorial);
        }, Duration.seconds(4));
    }




    // ------------------------------------- <<< Integer values in Game >>> -------------------------------------

    /**
     * Initializes Game Vars to In-Game HUD/UI
     *
     * @author Earl John Laguardia
     */

    @Override
    protected void initGameVars(Map<String, Object> vars) {

        //View in-game
        vars.put("Health", 100);
        vars.put("Gold", 0);
        vars.put("Potion", 3);
        vars.put("PotionCharge", 0);
        vars.put("Rank", 0);
        vars.put("Points", 0);

        //Skill
        vars.put("Fireball", 100);
        vars.put("Fireblast", 100);
        vars.put("FireblastLevel", 1);
        vars.put("Flamestrike", 100);
        vars.put("FlamestrikeLevel", 1);
        vars.put("Supernova", 100);
        vars.put("SupernovaLevel", 1);

        //Enemy
        vars.put("EnemyHealth", 300);

        //Bosses
        vars.put("DarkFlameMasterHealth", 9000);
        vars.put("RhatbuHealth", 15000);
        vars.put("BedjHealth", 20000);
        vars.put("GrimHealth", 25000);

        //Map Level Completion
        vars.put("first_upgrade", 0);
        vars.put("LvlComplete_Tutorial", 0);

        vars.put("LvlComplete_Level_1x3", 0);
        vars.put("LvlComplete_Level_4x6", 0);
        vars.put("LvlComplete_Level_7x9", 0);
        vars.put("LvlComplete_Level_11x13", 0);
        vars.put("LvlComplete_Level_14x16", 0);
        vars.put("LvlComplete_Level_17x19", 0);

        vars.put("LvlComplete_Rhatbu", 0);
        vars.put("LvlComplete_Bedj", 0);
        vars.put("LvlComplete_Grim", 0);

    }


    // ------------------------------------- <<< Main Launch >>> -------------------------------------

    public static void main(String[] args) {
        launch(args);
    }
}

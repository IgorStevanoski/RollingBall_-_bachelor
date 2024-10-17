package com.example.rollingBall.prepare;

import com.example.rollingBall.Const;
import com.example.rollingBall.Main;
import com.example.rollingBall.arena.*;
import com.example.rollingBall.entities.Player;
import com.example.rollingBall.entities.Terrain;
import com.example.rollingBall.gsonAdapters.*;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.scene.PointLight;
import javafx.scene.shape.Box;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.stage.Screen;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GamePrepare {

    Gson gson;

    public GamePrepare(){
        ExclusionStrategy strategy = new ExclusionStrategy() {
            @Override
            public boolean shouldSkipField(FieldAttributes field) {
                if (field.getDeclaringClass() == PointLight.class && field.getName().equals("maxRange")) {
                    return true;
                }
                return false;
            }

            @Override
            public boolean shouldSkipClass(Class<?> clazz) {
                return false;
            }
        };

        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(PointLight.class, new PointLightAdapter());
        builder.registerTypeAdapter(Podium.class, new PodiumAdapter());
        builder.registerTypeAdapter(Rotate.class, new RotateAdapter());
        builder.registerTypeAdapter(Arena.class, new ArenaAdapter());
        builder.registerTypeAdapter(Translate.class, new TranslateAdapter());
        builder.registerTypeAdapter(Lamp.class, new LampAdapter());
        builder.registerTypeAdapter(Box.class, new BoxAdapter());
        builder.registerTypeAdapter(Ball.class, new BallAdapter());
        builder.registerTypeAdapter(Hole.class, new HoleAdapter());
        builder.registerTypeAdapter(Wall.class, new WallAdapter());
        builder.registerTypeAdapter(RoundWall.class, new RoundWallAdapter());
        builder.registerTypeAdapter(BounceWall.class, new BounceWallAdapter());
        builder.registerTypeAdapter(BadHole.class, new BadHoleAdapter());
        builder.registerTypeAdapter(Coin.class, new CoinAdapter());
        builder.registerTypeAdapter(SpikyBall.class, new SpikyBallAdapter());
        builder.registerTypeAdapter(Stopwatch.class, new StopwatchAdapter());
        builder.registerTypeAdapter(BonusBall.class, new BonusBallAdapter());
        builder.registerTypeAdapter(IceToken.class, new IceTokenAdapter());
        builder.registerTypeAdapter(LocalDate.class, new LocalDateAdapter());
        builder.setPrettyPrinting();
        builder.addSerializationExclusionStrategy(strategy);
        gson = builder.create();
    }

    public List<Terrain> readTerrains(){
        try {
            Files.createDirectories(Paths.get("data"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Terrain[] terrains = new Terrain[10];
        List<Terrain> terrainsList = new ArrayList<>();
        File f = new File("data/terrains.txt");
        if(f.exists() && !f.isDirectory()) {
            try (FileReader fr = new FileReader("data/terrains.txt")){
                terrains = gson.fromJson(fr, Terrain[].class);
                //System.out.println("Uspesno procitano iz fajla!");
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            terrainsList.addAll(Arrays.asList(terrains));
        } else {
//            terrainsList.add(TestTerrain.testTerrain());
//            terrainsList.add(TestTerrain.testTerrain2());
            terrainsList.add(InitTerrain.initTerrain());
        }

        for (Terrain t: terrainsList) {
            //System.out.println("Terrain: " + t.getName());
        }

        return terrainsList;
    }

    public List<Player> readPlayers(){
        List<Player> playersList = new ArrayList<>();
        Player[] players = new Player[10];
        File f = new File("data/players.txt");
        if(f.exists() && !f.isDirectory()) {
            try (FileReader fr = new FileReader("data/players.txt")){
                players = gson.fromJson(fr, Player[].class);
               // System.out.println("Uspesno procitano iz fajla!");
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            playersList.addAll(Arrays.asList(players));
        } else {
            playersList.add( new Player("GUEST", new Terrain(Main.getTerrains().get(0))));
//            playersList.add( new Player("PLAYER1", new Terrain(Main2.getTerrains().get(0))));
//            playersList.add( new Player("PLAYER2", new Terrain(Main2.getTerrains().get(0))));
        }

        for (Player p:playersList) {
            //System.out.println("Igrac: " + p.getName());;
        }

        return playersList;
    }

    public void readMisc(){
        MiscObject mpo = new MiscObject("","", 0, 0,
                0, 0, false, false);
        File f = new File("data/misc.txt");
        if(f.exists() && !f.isDirectory()) {
            try (FileReader fr = new FileReader("data/misc.txt")){
                mpo = gson.fromJson(fr, MiscObject.class);
               // System.out.println("Uspesno procitano iz fajla!");
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            try{
                mpo.currentPlayer = Main.getPlayers().get(0).getName();
                mpo.currentTerrain = Main.getTerrains().get(0).getName();
                double[] resolution = readScreenResolution();
                mpo.screen_width = resolution[0];
                mpo.screen_height = resolution[1];
                mpo.screen_positionX = -1;
                mpo.screen_positionY = -1;
                mpo.fullscreen = false;
                mpo.maximized = false;
            } catch (NullPointerException e) {
            }
        }

        Main.setCurrentPlayer(mpo.currentPlayer);
        Main.setCurrentTerrain(mpo.currentTerrain);
        Main.setCurrent_window_width(mpo.screen_width);
        Main.setCurrent_window_height(mpo.screen_height);
        Main.setScreen_positionX (mpo.screen_positionX);
        Main.setScreen_positionY(mpo.screen_positionY);
        Main.setFullscreen(mpo.fullscreen);
        Main.setMaximized(mpo.maximized);
    }

    public void readConfig(){
        try {
            Files.createDirectories(Paths.get("config"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ConfigObject co = new ConfigObject();
        File f = new File("config/config.txt");
        if(f.exists() && !f.isDirectory()) {
            try (FileReader fr = new FileReader("config/config.txt")){
                co = gson.fromJson(fr, ConfigObject.class);
                co.updateConstants();
               // System.out.println("Uspesno procitano iz fajla!");
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    // Upisivanje igraca u JSON fajl
    public void writePlayers(){
        List<Player> players = Main.getPlayers();

        try (FileWriter writer = new FileWriter("data/players.txt")){
            gson.toJson(players, writer);
        } catch (IOException e){
            System.out.println("Folder \"data\" must exist.");
        }
    }

    // Upisivanje terena u JSON fajl
    public void writeTerrains(){
        List<Terrain> terrains = Main.getTerrains();

        try (FileWriter writer = new FileWriter("data/terrains.txt")){
            gson.toJson(terrains, writer);
        } catch (IOException e){
            System.out.println("Folder \"data\" must exist.");
        }
    }

    public void writeMisc(){
        String terrainName = "";
        if (Main.getCurrentTerrain() != null){
            terrainName = Main.getCurrentTerrain().getName();
        }
        MiscObject mpo = new MiscObject(
                Main.getCurrentPlayer().getName(),
                terrainName,
                Main.getCurrent_window_width(),
                Main.getCurrent_window_height(),
                Main.getScreen_positionX(),
                Main.getScreen_positionY(),
                Main.isFullscreen(),
                Main.isMaximized()
        );

        try (FileWriter writer = new FileWriter("data/misc.txt")){
            gson.toJson(mpo, writer);
        } catch (IOException e){
            System.out.println("Folder \"data\" must exist.");
        }
    }

    public void writeConfig(){
        ConfigObject co = new ConfigObject();
        try (FileWriter writer = new FileWriter("config/config.txt")){
            gson.toJson(co, writer);
        } catch (IOException e){
            System.out.println("Folder \"config\" must exist.");
        }
    }

    private double[] readScreenResolution() {
        int screenResolutionWidth = (int)Screen.getPrimary().getBounds().getWidth();
        int screenResolutionHeight = (int)Screen.getPrimary().getBounds().getHeight();
        String[] arrOfStr = Const.RESOLUTIONS[0].split("x", 2);
        int width = Integer.parseInt(arrOfStr[0]);
        int height = Integer.parseInt(arrOfStr[1]);
        for (int i = 0; i < Const.RESOLUTIONS.length; i++) {
            arrOfStr = Const.RESOLUTIONS[i].split("x", 2);
            if (screenResolutionWidth >= Integer.parseInt(arrOfStr[0])
                    && screenResolutionHeight >=  Integer.parseInt(arrOfStr[1])) {
                width = Integer.parseInt(arrOfStr[0]);
                height = Integer.parseInt(arrOfStr[1]);
            } else {
                break;
            }
        }
        double[] size = {width, height};
        return size;
    }

}

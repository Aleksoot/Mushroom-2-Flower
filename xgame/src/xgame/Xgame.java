/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xgame;

import com.sun.javafx.tk.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import static java.lang.Math.abs;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import java.awt.Rectangle;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import xgame.Tile.Type;

/**
 *
 * @author faete
 */
///////
public class Xgame extends Application{
    
   private long count = 0;
   File resourcesDirectory = new File("src/xgame");
   String os = System.getProperty("os.name").toLowerCase();
   AudioClip mediaPlayer;
   public boolean playOn = true;
   Level level;
   List<Tile> leveltiles;
   Player player;
   String src_slash;
    @Override
    public void start(Stage primaryStage) throws IOException, UnsupportedAudioFileException, LineUnavailableException {
        
        //Level is created
        level = new Level();
        leveltiles = level.getLevelTiles();
        level.createLevel();
        startMusic();
        
        Pane root = new Pane();
        root = level.getRoot();
        System.out.println(level.getRoot());
        
        //Player is created
        player = new Player();
        player.getGameObject().setFill(Color.BLUE);
        player.getGameObject().setX(240);
        player.getGameObject().setY(210);
        
        //Adding player to root
        root.getChildren().add(player.getGameObject());
       
        
        Scene scene = new Scene(root, root.getPrefWidth(), root.getPrefHeight());
        
        //Setting a background color, title, primarystage, keylistener
        
        
        if (os.indexOf("win") >= 0) {
            //if windows
            this.src_slash = "\\";
        
        }else{
            this.src_slash = "/";
        }
        BufferedImage bg = ImageIO.read(new File(resourcesDirectory.getAbsolutePath()+src_slash+"BG.png"));
        Image card = SwingFXUtils.toFXImage(bg, null );
        scene.setFill(new ImagePattern(card));
        primaryStage.setTitle("Spillbrett");
        primaryStage.setScene(scene);
        
        //Keylistener for the controls
        primaryStage.getScene().setOnKeyPressed(e -> {
        
            if (e.getCode() == KeyCode.LEFT) {
                player.setMovingLeft(true);
            }
            if (e.getCode() == KeyCode.RIGHT) {
                player.setMovingRight(true); 
            }
            if (e.getCode() == KeyCode.SPACE) {
                player.jump();
            } 
            
        });
        primaryStage.getScene().setOnKeyReleased(e -> {
       
            if (e.getCode() == KeyCode.LEFT) {
                    player.setMovingLeft(false);
            }
            if (e.getCode() == KeyCode.RIGHT) {
                player.setMovingRight(false);
            }
           
        });
       
        
        primaryStage.show();
        AnimationTimer animator = new AnimationTimer(){
            private long time;
            @Override
            public void handle(long now) {
                try {
                    testGraphic();
                } catch (IOException ex) {
                    Logger.getLogger(Xgame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                }
                player.setFalling(true);
                player.colliding(leveltiles, Type.solid);
                player.fall();
                
                if(player.movingRight()){
                    player.moveRight();
                }
                if(player.movingLeft()){
                    player.moveLeft();
                }
                if(player.isJumping()){
                    player.jump();
                }
            }      
        };

        animator.start();  
    }
    public void testGraphic() throws IOException{
        if(player.movingRight){
            BufferedImage mRight = ImageIO.read(new File(resourcesDirectory.getAbsolutePath()+src_slash+"player"+src_slash+"run.gif"));
            ImageIcon imageIcon = new ImageIcon(mRight);
            Image testr = SwingFXUtils.toFXImage(mRight, null );
            
            player.getGameObject().setFill(new ImagePattern(testr));
        }
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
        //System.out.println("Number of active threads from the given thread: " + Thread.activeCount());
        
    }

    public void startMusic() throws UnsupportedAudioFileException, IOException, LineUnavailableException{
        
        String src_slash;
        if (os.indexOf("win") >= 0) {
            //if windows
            src_slash = "\\";
        
        }else{
            src_slash = "/";
        }
        File in = new File(resourcesDirectory.getAbsolutePath()+src_slash+"test.wav");
        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(in);
        Clip background = AudioSystem.getClip();
        background.open(audioInputStream);
        background.loop(Clip.LOOP_CONTINUOUSLY);
        FloatControl volume= (FloatControl) background.getControl(FloatControl.Type.MASTER_GAIN);
        volume.setValue(-20.4f); // Reduce volume by 10 decibels.
 
        Thread musikk = new Thread(){
            @Override
            public void run() {
                try{
                                
                        background.start();
                    
                }catch(Exception e){
                    System.out.println(e);
                }  
            }
        };
        musikk.start();
        
	}

    
}

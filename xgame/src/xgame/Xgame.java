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
import javafx.geometry.Point3D;
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
   Enemy enemy;
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

        //Enemy is created
        enemy = new Enemy();
        enemy.getGameObject().setFill(Color.RED);
        enemy.getGameObject().setX(450);
        enemy.getGameObject().setY(350);
        enemy.getGameObject().setHeight(100);
        enemy.getGameObject().setWidth(90);
                
        //Adding player & enemy to root
        root.getChildren().add(player.getGameObject());
        root.getChildren().add(enemy.getGameObject());
        
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
            if (e.getCode() == KeyCode.UP) {
                player.setFacingLeft(false);
                player.setFacingRight(false);
            }
            if (e.getCode() == KeyCode.SPACE && player.facingRight()) {
                
                if(!player.isFalling() ){
                    player.setMovingRight(true);
                    player.jump();
                try {
                    jumpSound();
                } catch (UnsupportedAudioFileException ex) {
                    Logger.getLogger(Xgame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(Xgame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                } catch (LineUnavailableException ex) {
                    Logger.getLogger(Xgame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                }
                }
            } 
            if (e.getCode() == KeyCode.SPACE && player.facingLeft()) {
                
                if(!player.isFalling() ){
                    player.setMovingLeft(true);
                    player.jump();
                try {
                    jumpSound();
                } catch (UnsupportedAudioFileException ex) {
                    Logger.getLogger(Xgame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(Xgame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                } catch (LineUnavailableException ex) {
                    Logger.getLogger(Xgame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                }
                }
            }
            if (e.getCode() == KeyCode.SPACE && !player.facingLeft() && !player.facingRight()) {
                
                if(!player.isFalling() ){
                    player.jump();
                try {
                    jumpSound();
                } catch (UnsupportedAudioFileException ex) {
                    Logger.getLogger(Xgame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(Xgame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                } catch (LineUnavailableException ex) {
                    Logger.getLogger(Xgame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                }
                }
            }
            
        });
        primaryStage.getScene().setOnKeyReleased(e -> {
       player.setMovingRight(false);
        player.setMovingLeft(false);
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
                enemy.setFalling(true);
                enemy.colliding(leveltiles, Type.solid);
                enemy.fall();
                
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
        if(player.facingRight() && !player.isFalling()){
            BufferedImage mRight = ImageIO.read(new File(resourcesDirectory.getAbsolutePath()+src_slash+"player"+src_slash+"run.gif"));
            ImageIcon imageIcon = new ImageIcon(mRight);
            Image testr = SwingFXUtils.toFXImage(mRight, null );
            
            player.getGameObject().setFill(new ImagePattern(testr));
        }
        if(player.facingLeft() && !player.isFalling()){
            BufferedImage mLeft = ImageIO.read(new File(resourcesDirectory.getAbsolutePath()+src_slash+"player"+src_slash+"run_left.gif"));
            ImageIcon imageIcon = new ImageIcon(mLeft);
            Image testr = SwingFXUtils.toFXImage(mLeft, null );
            player.getGameObject().setFill(new ImagePattern(testr));
           
        }if(player.isFalling()){
            BufferedImage mLeft = ImageIO.read(new File(resourcesDirectory.getAbsolutePath()+src_slash+"player"+src_slash+"mid_air.gif"));
            ImageIcon imageIcon = new ImageIcon(mLeft);
            Image testr = SwingFXUtils.toFXImage(mLeft, null );
            player.getGameObject().setFill(new ImagePattern(testr));
        }
        if(!player.facingLeft() && !player.facingRight()){
            BufferedImage mLeft = ImageIO.read(new File(resourcesDirectory.getAbsolutePath()+src_slash+"player"+src_slash+"idle.gif"));
            ImageIcon imageIcon = new ImageIcon(mLeft);
            Image testr = SwingFXUtils.toFXImage(mLeft, null );
            player.getGameObject().setFill(new ImagePattern(testr));
        }
        if(enemy.facingRight() && !enemy.isFalling()){
            BufferedImage mRight = ImageIO.read(new File(resourcesDirectory.getAbsolutePath()+src_slash+"enemy"+src_slash+"goblin.gif"));
            ImageIcon imageIcon = new ImageIcon(mRight);
            Image testr = SwingFXUtils.toFXImage(mRight, null );
            
            enemy.getGameObject().setFill(new ImagePattern(testr));
        }
        if(enemy.facingLeft() && !enemy.isFalling()){
            BufferedImage mLeft = ImageIO.read(new File(resourcesDirectory.getAbsolutePath()+src_slash+"enemy"+src_slash+"goblin.gif"));
            ImageIcon imageIcon = new ImageIcon(mLeft);
            Image testr = SwingFXUtils.toFXImage(mLeft, null );
            enemy.getGameObject().setFill(new ImagePattern(testr));
           
        }if(enemy.isFalling()){
            BufferedImage mLeft = ImageIO.read(new File(resourcesDirectory.getAbsolutePath()+src_slash+"enemy"+src_slash+"goblin.gif"));
            ImageIcon imageIcon = new ImageIcon(mLeft);
            Image testr = SwingFXUtils.toFXImage(mLeft, null );
            enemy.getGameObject().setFill(new ImagePattern(testr));
        }
        if(!enemy.facingLeft() && !enemy.facingRight()){
            BufferedImage mLeft = ImageIO.read(new File(resourcesDirectory.getAbsolutePath()+src_slash+"enemy"+src_slash+"goblin.gif"));
            ImageIcon imageIcon = new ImageIcon(mLeft);
            Image testr = SwingFXUtils.toFXImage(mLeft, null );
            enemy.getGameObject().setFill(new ImagePattern(testr));
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
        File in = new File(resourcesDirectory.getAbsolutePath()+src_slash+"music"+src_slash+"test.wav");
        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(in);
        Clip background = AudioSystem.getClip();
        background.open(audioInputStream);
        FloatControl volume= (FloatControl) background.getControl(FloatControl.Type.MASTER_GAIN);
        volume.setValue(-20.4f); // Reduce volume by 10 decibels.
 
        Thread musikk = new Thread(){
            @Override
            public void run() {
                try{
                               
                            background.loop(Clip.LOOP_CONTINUOUSLY);
                        
                    
                }catch(Exception e){
                    System.out.println(e);
                }  
            }
        };
        musikk.start();
        
	}
        public void jumpSound() throws UnsupportedAudioFileException, IOException, LineUnavailableException{
        
        String src_slash;
        if (os.indexOf("win") >= 0) {
            //if windows
            src_slash = "\\";
        
        }else{
            src_slash = "/";
        }
        File in = new File(resourcesDirectory.getAbsolutePath()+src_slash+"music"+src_slash+"jump.wav");
        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(in);
        Clip background = AudioSystem.getClip();
        background.open(audioInputStream);
        FloatControl volume= (FloatControl) background.getControl(FloatControl.Type.MASTER_GAIN);
        volume.setValue(-20.4f); // Reduce volume by 10 decibels.
 
        Thread audio = new Thread(){
            @Override
            public void run() {
                try{
                               
                            background.start();
                        
                    
                }catch(Exception e){
                    System.out.println(e);
                }  
            }
        };
        audio.start();
        
	}

    
}

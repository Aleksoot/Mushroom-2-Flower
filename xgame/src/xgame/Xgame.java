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
import java.io.BufferedReader;
import java.io.FileReader;
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
import javafx.scene.shape.Rectangle;
import java.util.Collections;
import javafx.animation.FadeTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.geometry.Insets;
import javafx.geometry.Point3D;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;
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
   boolean level_1=false;
   boolean level_2=false;
   Level level;
   List<Tile> leveltiles;
   Player player;
   String src_slash;
   Rectangle rect1;
   Rectangle rect2;
   Rectangle fball;
   TranslateTransition ft;
   TranslateTransition ft2;
   TranslateTransition ftfb;
   Pane root = new Pane();
   int frameCount=0;
   SpriteAnimation player_right;
   SpriteAnimation player_left;
   SpriteAnimation player_fall;
   SpriteAnimation dragon_left;
   SpriteAnimation dragon_right;
   SpriteAnimation skeleton_right;
   SpriteAnimation skeleton_left;
   SpriteAnimation fireball_left;
   SpriteAnimation fireball_right;
   public boolean frameChanged=false;
   
   /**
    * A ranged attack for the "dragon" enemy.
    * Will trigger when the player object is within certain bounds. (x-axis, y-axis)
    */
   
   /**
    * Will parse from .txt containing spawn information in arrays
    */
   //public void spawns (){
       //try {
           //File spawns = new File()
       //}
   //}
   
    @Override
    
    public void start(Stage primaryStage) throws IOException, UnsupportedAudioFileException, LineUnavailableException {
        
         level = new Level();
         
        if(!level_1){
        drawLevel(primaryStage, level, 2, root);
        }else{ drawLevel(primaryStage, level, 2, root);}
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
                this.level_1=true;
                if(!player.isFalling() ){
                    player.setMovingRight(true);
                    player.jump();
                try {
                    playAudio(resourcesDirectory.getAbsolutePath()+src_slash+"music"+src_slash+"jump.wav");
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
                    playAudio(resourcesDirectory.getAbsolutePath()+src_slash+"music"+src_slash+"jump.wav");
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
                    playAudio(resourcesDirectory.getAbsolutePath()+src_slash+"music"+src_slash+"jump.wav");
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
           
            long before = System.nanoTime();
            double posy;
            double pos_last;
            double pos_lasty;
            double pos_lastfb;
            double pos_lastfby;
            double pos_last2;
            @Override
            public void handle(long now) {
                
                player.colliding(leveltiles, Type.solid);
                
                 if (now > before + 0.02e+9) {
                     //changes frame every 0.02e+9ns
                     if(frameChanged){
                         //a frame
                         player_right.changeFrame(frameChanged);
                         player_left.changeFrame(frameChanged);
                         player_fall.changeFrame(frameChanged);
                         dragon_left.changeFrame(frameChanged);
                         dragon_right.changeFrame(frameChanged);
                         skeleton_right.changeFrame(frameChanged);
                         skeleton_left.changeFrame(frameChanged);
                         fireball_left.changeFrame(frameChanged);
                         fireball_right.changeFrame(frameChanged);
                         player.collidingEnemy(fball);
                         player.collidingEnemy(rect1);
                         player.collidingEnemy(rect2);
                         player.checkAlive();
                         posy = player.getGameObject().getTranslateY();
                         pos_lasty = rect1.getTranslateY();
                         pos_last = rect1.getTranslateX();
                         pos_lastfb = rect1.getTranslateX();
                         pos_lastfby = rect1.getTranslateY();
                         pos_last2 = rect2.getTranslateX();
                            frameChanged = false;
                     }else{
                         //another frame
                         frameChanged = true;
                     }
                     
                     before = System.nanoTime();
                }
                if(!player.isAlive()){
                    try {
                        playAudio(resourcesDirectory.getAbsolutePath()+src_slash+"music"+src_slash+"sad.wav");
                    } catch (UnsupportedAudioFileException ex) {
                        Logger.getLogger(Xgame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                    } catch (IOException ex) {
                        Logger.getLogger(Xgame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                    } catch (LineUnavailableException ex) {
                        Logger.getLogger(Xgame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                    }
                    Text t = new Text (300, 450, "You are DEAD!");
                    t.setFont(Font.font ("Verdana", 60));
                    t.setFill(Color.RED);
                root.getChildren().add(t);
                    ft.stop();
                    ft2.stop();
                    this.stop();
                }
                double pos_now = rect1.getTranslateX();
                double pos_nowy = rect1.getTranslateY();
                double pos_now2 = rect2.getTranslateX();
                if(pos_now > pos_last){
                    rect1.setFill(dragon_right.getFrame());
                }else if(pos_now < pos_last ){
                    rect1.setFill(dragon_left.getFrame());
                }
                if(pos_now2 > pos_last2){
                    rect2.setFill(skeleton_right.getFrame());
                }else if(pos_now2 < pos_last2){
                    rect2.setFill(skeleton_left.getFrame());
                }
                if(posy >= 300){
                    System.out.println("Fire!");
                    fball.setVisible(true);
                    fball.setFill(fireball_right.getFrame());
                }
                else{
                    System.out.println("Y axis:" + "" + posy);
                    fball.setVisible(false);
                }
                if(player.movingRight()){ 
                    player.moveRight();
                    player.getGameObject().setFill(player_right.getFrame());
                }
                if(player.movingLeft()){
                    player.moveLeft();
                    player.getGameObject().setFill(player_left.getFrame());
                    
                }
                
                if(player.getCollidingYu()){
                    player.setFalling(false);
                    
                }
                if(!player.getCollidingYu() ){
                    player.setFalling(true);
                    player.fall();
                    player.getGameObject().setFill(player_fall.getFrame());
                }
                
//                System.out.println("falling: "+player.isFalling());
//                System.out.println("jumping: "+player.isJumping());
            } 
            
        };

        animator.start();  
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        launch(args);
        //System.out.println("Number of active threads from the given thread: " + Thread.activeCount());
        
    }

    public void startMusic() throws UnsupportedAudioFileException, IOException, LineUnavailableException{
        
        
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
        public void playAudio(String src) throws UnsupportedAudioFileException, IOException, LineUnavailableException{
        
        
        File in = new File(src);
        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(in);
        Clip background = AudioSystem.getClip();
        background.open(audioInputStream);
        FloatControl volume= (FloatControl) background.getControl(FloatControl.Type.MASTER_GAIN);
        volume.setValue(-20.4f); 
 
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

    private void drawLevel(Stage primaryStage, Level level, int s, Pane root) throws IOException {
        //Level is created
        src_slash = src();
        leveltiles = level.getLevelTiles();
        if(s == 1){
            level.createLevel(level.level_1());
            System.out.println("creating level1");
        }else if(s == 2){
            level.createLevel(level.level_2());
            System.out.println("creating level2");
        }
        //startMusic(); 
        
        
        root.getChildren().addAll(level.getRoot());
        
        
        //Player is created
        player = new Player();
        player.getGameObject().setX(240);
        player.getGameObject().setY(210);
        
        rect1 = new Rectangle(250,450,155,155);
        rect2 = new Rectangle(460,360,60,60);
        fball = new Rectangle(250,450,50,50);
        fball.setVisible(false);
        rect1.setFill(Color.RED);
        rect2.setFill(Color.GREEN);
        fball.setFill(Color.YELLOW);
        

        player_right = new SpriteAnimation(addFolderSprites( new File(resourcesDirectory.getAbsolutePath()+src_slash+"player"+src_slash+"runner") ) );
        player_left = new SpriteAnimation(addFolderSprites( new File(resourcesDirectory.getAbsolutePath()+src_slash+"player"+src_slash+"runner_left") ) );
        player_fall = new SpriteAnimation(addFolderSprites( new File(resourcesDirectory.getAbsolutePath()+src_slash+"player"+src_slash+"mid_air") ) );
        dragon_left = new SpriteAnimation(addFolderSprites( new File(resourcesDirectory.getAbsolutePath()+src_slash+"enemy"+src_slash+"dragon_left") ) );
        dragon_right = new SpriteAnimation(addFolderSprites( new File(resourcesDirectory.getAbsolutePath()+src_slash+"enemy"+src_slash+"dragon_right") ) );
        skeleton_right = new SpriteAnimation(addFolderSprites( new File(resourcesDirectory.getAbsolutePath()+src_slash+"skeleton"+src_slash+"walk_right") ) );
        skeleton_left = new SpriteAnimation(addFolderSprites( new File(resourcesDirectory.getAbsolutePath()+src_slash+"skeleton"+src_slash+"walk") ) );
        fireball_left = new SpriteAnimation(addFolderSprites( new File(resourcesDirectory.getAbsolutePath()+src_slash+"enemy"+src_slash+"fireball_left") ) );
        fireball_right = new SpriteAnimation(addFolderSprites( new File(resourcesDirectory.getAbsolutePath()+src_slash+"enemy"+src_slash+"fireball_right") ) );       
        
        //Adding player to root
        ft = new TranslateTransition(Duration.millis(2000), rect1);
        ft.setFromX(0f);
        ft.setByX(180);
        ft.setCycleCount(Timeline.INDEFINITE);
        ft.setAutoReverse(true);
        ft2 = new TranslateTransition(Duration.millis(4000), rect2);
        ft2.setFromX(0f);
        ft2.setByX(200);
        ft2.setCycleCount(1);
        ft2.setAutoReverse(true);
        ftfb = new TranslateTransition(Duration.millis(1000), fball);
        ftfb.setFromX(0f);
        ftfb.setByX(400);
        ftfb.setCycleCount(Timeline.INDEFINITE);
        ftfb.setAutoReverse(false);
        root.getChildren().addAll(player.getGameObject(),rect1,rect2,fball);
        Scene scene = new Scene(root, root.getPrefWidth(), root.getPrefHeight());

        
        ft.play();
        ft2.play();
        ftfb.play();
        
        BufferedImage bg = ImageIO.read(new File(resourcesDirectory.getAbsolutePath()+src_slash+"BG.png"));
        Image card = SwingFXUtils.toFXImage(bg, null );
        scene.setFill(new ImagePattern(card,0,0,900,900,false));
        primaryStage.setTitle("Spillbrett");
        primaryStage.setScene(scene);
        
    }

    public String src(){
        String src_slash;
        if (os.indexOf("win") >= 0) {
            //if windows
            src_slash = "\\";
        
        }else{
            src_slash = "/";
        }
        return src_slash;
    }
    
    public List<BufferedImage> addFolderSprites(final File folder) throws IOException {
        List<BufferedImage> list = new ArrayList<BufferedImage>();
  
        for (final File fileEntry : folder.listFiles()) {
           
           list.add(ImageIO.read(new File(folder+src()+fileEntry.getName())));
        }
        return list;
    
    }
}

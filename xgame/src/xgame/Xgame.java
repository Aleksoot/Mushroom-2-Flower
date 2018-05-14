/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xgame;
   
import java.io.PrintWriter;
import java.util.Scanner;
import java.io.*;
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
   TranslateTransition ft;
   Pane root = new Pane();
   int frameCount=0;
   SpriteAnimation player_right;
   SpriteAnimation player_left;
   SpriteAnimation player_fall;
   public boolean frameChanged=false;
   int score = 0;
   File file = new File(resourcesDirectory.getAbsolutePath()+src_slash+"scores"+src_slash+"score.txt");
   
    @Override
    public void start(Stage primaryStage) throws IOException, UnsupportedAudioFileException, LineUnavailableException {
        
         level = new Level();
         
        if(!level_1){
        drawLevel(primaryStage, level, 1, root);
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
                    score += 2;
                    try{
            PrintWriter output = new PrintWriter(file);
            output.println(score);
            output.close();
        }catch (FileNotFoundException ex){
            System.out.printf("ERROR: %s\n", ex);
        }
         try{
            Scanner input = new Scanner(file);
            int score = input.nextInt();
            System.out.printf("Scores: %d\n", score);
        }catch(IOException ex){
            System.err.println("ERROR");
        }
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
        // determine the high score
    int highScore = 0;
    try {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line = reader.readLine();
        while (line != null)                 // read the score file line by line
        {
            try {
                int point = Integer.parseInt(line.trim());   // parse each line as an int
                if (point > highScore)                       // and keep track of the largest
                { 
                    highScore = point; 
                }
            } catch (NumberFormatException e1) {
                // ignore invalid scores
                //System.err.println("ignoring invalid score: " + line);
            }
            line = reader.readLine();
        }
        reader.close();

    } catch (IOException ex) {
        System.err.println("ERROR reading scores from file");
    }
    // display the high score
    if (score > highScore)
    {    
        System.out.println("You now have the new high score! The previous high score was " + highScore);
    } else if (score == highScore) {
        System.out.println("You tied the high score!");
    } else {
        System.out.println("The all time high score was " + highScore);
    }
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
            double posy = player.getGameObject().getY();
            @Override
            public void handle(long now) {
                
                player.colliding(leveltiles, Type.solid);
                
                 if (now > before + 0.02e+9) {
                     //changes frame every 0.02e+9ns
                     if(frameChanged){
                         //a frame
                        
                         rect1.setFill(Color.YELLOW);
                         player_right.changeFrame(frameChanged);
                         player_left.changeFrame(frameChanged);
                         player_fall.changeFrame(frameChanged);
                         
                    
                            frameChanged = false;
                     }else{
                         //another frame
                       
                         rect1.setFill(Color.PINK);
                         frameChanged = true;
                     }
                     
                     before = System.nanoTime();
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
                if(!player.getCollidingYu() || player.getCollidingYo() ){
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
        public void jumpSound() throws UnsupportedAudioFileException, IOException, LineUnavailableException{
        
        
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
        
        rect1 = new Rectangle(160,390,30,30);

        rect1.setArcHeight(20);
        rect1.setArcWidth(20);
        rect1.setFill(Color.RED);
        
BufferedImage player1 = ImageIO.read(new File(resourcesDirectory.getAbsolutePath()+src_slash+"player"+src_slash+"runner"+src_slash+"runner-1.png"));
BufferedImage player2 = ImageIO.read(new File(resourcesDirectory.getAbsolutePath()+src_slash+"player"+src_slash+"runner"+src_slash+"runner-2.png"));
BufferedImage player3 = ImageIO.read(new File(resourcesDirectory.getAbsolutePath()+src_slash+"player"+src_slash+"runner"+src_slash+"runner-3.png"));
BufferedImage player4 = ImageIO.read(new File(resourcesDirectory.getAbsolutePath()+src_slash+"player"+src_slash+"runner"+src_slash+"runner-4.png"));
BufferedImage player5 = ImageIO.read(new File(resourcesDirectory.getAbsolutePath()+src_slash+"player"+src_slash+"runner"+src_slash+"runner-5.png"));       
BufferedImage player6 = ImageIO.read(new File(resourcesDirectory.getAbsolutePath()+src_slash+"player"+src_slash+"runner"+src_slash+"runner-6.png"));
BufferedImage player7 = ImageIO.read(new File(resourcesDirectory.getAbsolutePath()+src_slash+"player"+src_slash+"runner"+src_slash+"runner-7.png"));
BufferedImage player8 = ImageIO.read(new File(resourcesDirectory.getAbsolutePath()+src_slash+"player"+src_slash+"runner"+src_slash+"runner-8.png"));
List<BufferedImage> player_sprites = new ArrayList<BufferedImage>();
        player_sprites.add(player1);
        player_sprites.add(player2);
        player_sprites.add(player3);
        player_sprites.add(player4);
        player_sprites.add(player5);
        player_sprites.add(player6);
        player_sprites.add(player7);
        player_sprites.add(player8);
        player_right = new SpriteAnimation(player_sprites);

BufferedImage player11 = ImageIO.read(new File(resourcesDirectory.getAbsolutePath()+src_slash+"player"+src_slash+"runner_left"+src_slash+"runner_left-1.png"));
BufferedImage player22 = ImageIO.read(new File(resourcesDirectory.getAbsolutePath()+src_slash+"player"+src_slash+"runner_left"+src_slash+"runner_left-2.png"));
BufferedImage player33 = ImageIO.read(new File(resourcesDirectory.getAbsolutePath()+src_slash+"player"+src_slash+"runner_left"+src_slash+"runner_left-3.png"));
BufferedImage player44 = ImageIO.read(new File(resourcesDirectory.getAbsolutePath()+src_slash+"player"+src_slash+"runner_left"+src_slash+"runner_left-4.png"));
BufferedImage player55 = ImageIO.read(new File(resourcesDirectory.getAbsolutePath()+src_slash+"player"+src_slash+"runner_left"+src_slash+"runner_left-5.png"));       
BufferedImage player66 = ImageIO.read(new File(resourcesDirectory.getAbsolutePath()+src_slash+"player"+src_slash+"runner_left"+src_slash+"runner_left-6.png"));
BufferedImage player77 = ImageIO.read(new File(resourcesDirectory.getAbsolutePath()+src_slash+"player"+src_slash+"runner_left"+src_slash+"runner_left-7.png"));
BufferedImage player88 = ImageIO.read(new File(resourcesDirectory.getAbsolutePath()+src_slash+"player"+src_slash+"runner_left"+src_slash+"runner_left-8.png"));
List<BufferedImage> player_sprites2 = new ArrayList<BufferedImage>();
        player_sprites2.add(player11);
        player_sprites2.add(player22);
        player_sprites2.add(player33);
        player_sprites2.add(player44);
        player_sprites2.add(player55);
        player_sprites2.add(player66);
        player_sprites2.add(player77);
        player_sprites2.add(player88);
        player_left = new SpriteAnimation(player_sprites2);
BufferedImage playerfall = ImageIO.read(new File(resourcesDirectory.getAbsolutePath()+src_slash+"player"+src_slash+"mid_air"+src_slash+"mid_air-1.png"));
BufferedImage playerfall2 = ImageIO.read(new File(resourcesDirectory.getAbsolutePath()+src_slash+"player"+src_slash+"mid_air"+src_slash+"mid_air-2.png"));
List<BufferedImage> player_sprites3 = new ArrayList<BufferedImage>();
player_sprites3.add(playerfall);
player_sprites3.add(playerfall2);
player_fall = new SpriteAnimation(player_sprites3);
        //Adding player to root
        ft = new TranslateTransition(Duration.millis(2000), rect1);
        ft.setFromX(0f);
        ft.setByX(90);
        ft.setCycleCount(Timeline.INDEFINITE);
        ft.setAutoReverse(true);
        root.getChildren().addAll(player.getGameObject(),rect1);
        Scene scene = new Scene(root, root.getPrefWidth(), root.getPrefHeight());

        
ft.play();
        
        BufferedImage bg = ImageIO.read(new File(resourcesDirectory.getAbsolutePath()+src_slash+"BG.png"));
        Image card = SwingFXUtils.toFXImage(bg, null );
        scene.setFill(new ImagePattern(card));
        primaryStage.setTitle("Spillbrett");
        primaryStage.setScene(scene);

// append the last score to the end of the file
    try {
        BufferedWriter output = new BufferedWriter(new FileWriter(file, true));
        output.newLine();
        output.append("" + score);
        output.close();

    } catch (IOException ex1) {
        System.out.printf("ERROR writing score to file: %s\n", ex1);
    }
        
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
}

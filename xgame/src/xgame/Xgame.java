/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xgame;

import com.sun.javafx.geom.BaseBounds;
import com.sun.javafx.geom.transform.BaseTransform;
import com.sun.javafx.jmx.MXNodeAlgorithm;
import com.sun.javafx.jmx.MXNodeAlgorithmContext;
import com.sun.javafx.sg.prism.NGNode;
import com.sun.javafx.tk.Toolkit;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
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
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.util.BitSet;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;
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
   boolean level_menu=true, level_1=false, level_2=false;
   Level level, level1, level2;
   List<Tile> leveltiles, leveltiles1, leveltiles2;
   List<Rectangle> enemies, enemies1, enemies2;
   Player player, player1, player2;
   String src_slash;
   Text points;
   Rectangle rect1,health,health1,health2;
   TranslateTransition ft;
   Pane panemenu, pane1, pane2, pane3;
   Button btnscene1, btnscene2;
   Button start, load, save, highscore, exit;
    Label lblscene1, lblscene2;
    Scene scenemenu, scene1, scene2, scene3;
    Stage stage;
   int frameCount=0;
   SpriteAnimation player_right,player_left,player_fall,player_fall_left,player_idle,player_idle_left,skeleton_right,skeleton_left,dragon_right,dragon_left,fireball_left,fireball_right;
   AnimationTimer animator;
   boolean animating = true;
   public boolean frameChanged=false;
   public boolean frameChanged2=false;
   public long playerY;
   public boolean playerJump, s1, s2;
   public int jumpTick;
    @Override
    public void start(Stage primaryStage) throws IOException, UnsupportedAudioFileException, LineUnavailableException {
        
        stage = primaryStage;
        
        stage.setResizable(false);
        enemies=new ArrayList();
        enemies1=new ArrayList();
        enemies2=new ArrayList();
        level = new Level();
        level1 = new Level();
        level2 = new Level();
        player = new Player();
        
        player1 = new Player(); 
        player1.getGameObject().setX(60);
        player1.getGameObject().setY(810);
        player1.setLevel(1);
        
        player2 = new Player(); 
        player2.getGameObject().setX(60);
        player2.getGameObject().setY(810);
        player2.setLevel(2);
     
        btnscene1=new Button("next level");
        btnscene2=new Button("previous level");
        btnscene1.setOnAction(e-> testClicked(e));
        btnscene2.setOnAction(e-> testClicked(e));
        lblscene1=new Label("Scene 1");
        
        level1.createLevel(level1.level_1());
        level2.createLevel(level2.level_2());
        leveltiles1 = level1.getLevelTiles();
        leveltiles2 = level2.getLevelTiles();
        pane1 = drawLevel1(level1);
        pane2 = drawLevel2(level2);
        health = new Rectangle(200,20);
        health.setFill(Color.GREEN);
        health.setX(30); health.setY(5);
       pane1.getChildren().add(health);
        Rectangle logo = new Rectangle(300,300);
        logo.setX(300); logo.setY(200);
        File logofile = new File(resourcesDirectory.getAbsolutePath()+src_slash+"logo.png");
        Image logoimg = new Image(logofile.toURI().toString());
        logo.setFill(new ImagePattern(logoimg));
        panemenu = new Pane();
        File fil = new File(resourcesDirectory.getAbsolutePath()+src_slash+"sky.jpg");
        ImageView background = new ImageView(new Image(fil.toURI().toString(), 930, 930, false, false));
        panemenu.getChildren().addAll(background, logo, menuCreator() );
        scenemenu = new Scene(panemenu,900,900);
        scene1 = new Scene(pane1,900,900);
        scene2 = new Scene(pane2,900,900);
        
        stage.setScene(scenemenu);
        stage.setTitle("Hello World!");
        
        stage.show();
        
        //Animations
        player_right = new SpriteAnimation(addFolderSprites( new File(resourcesDirectory.getAbsolutePath()+src_slash+"player"+src_slash+"runner") ) );
        player_left = new SpriteAnimation(addFolderSprites( new File(resourcesDirectory.getAbsolutePath()+src_slash+"player"+src_slash+"runner_left") ) );
        player_fall = new SpriteAnimation(addFolderSprites( new File(resourcesDirectory.getAbsolutePath()+src_slash+"player"+src_slash+"mid_air") ) );
        player_fall_left = new SpriteAnimation(addFolderSprites( new File(resourcesDirectory.getAbsolutePath()+src_slash+"player"+src_slash+"mid_air_left") ) );
        player_idle = new SpriteAnimation(addFolderSprites( new File(resourcesDirectory.getAbsolutePath()+src_slash+"player"+src_slash+"idle_right") ) );
        player_idle_left = new SpriteAnimation(addFolderSprites( new File(resourcesDirectory.getAbsolutePath()+src_slash+"player"+src_slash+"idle_left") ) );
        skeleton_right = new SpriteAnimation(addFolderSprites( new File(resourcesDirectory.getAbsolutePath()+src_slash+"skeleton"+src_slash+"walk_right") ) );
        skeleton_left = new SpriteAnimation(addFolderSprites( new File(resourcesDirectory.getAbsolutePath()+src_slash+"skeleton"+src_slash+"walk") ) );
        dragon_right = new SpriteAnimation(addFolderSprites( new File(resourcesDirectory.getAbsolutePath()+src_slash+"enemy"+src_slash+"dragon_right") ) );
        dragon_left = new  SpriteAnimation(addFolderSprites( new File(resourcesDirectory.getAbsolutePath()+src_slash+"enemy"+src_slash+"dragon_right") ) );
        fireball_right = new SpriteAnimation(addFolderSprites( new File(resourcesDirectory.getAbsolutePath()+src_slash+"enemy"+src_slash+"fireball_right") ) );
        fireball_left = new SpriteAnimation(addFolderSprites( new File(resourcesDirectory.getAbsolutePath()+src_slash+"enemy"+src_slash+"fireball_left") ) );
                
        animator = new AnimationTimer(){
           
            long before = System.nanoTime();
            long before2 = before;
            double pos_last;
            @Override
            public void handle(long now) {
                if(!level_menu){
                long now2 = now;
                levelCheck();
                controls();
                //controls2();
                player.colliding(leveltiles, Type.solid);
                health.setWidth( player.getHealth()*2);

                 if (now > before + 0.02e+9) {
                     //changes frame every 0.02e+9ns
                     if(frameChanged){
                         //a frame
                         player_right.changeFrame(frameChanged);
                         player_left.changeFrame(frameChanged);
                         player_fall.changeFrame(frameChanged);
                         player_fall_left.changeFrame(frameChanged);
                         player_idle.changeFrame(frameChanged);
                         player_idle_left.changeFrame(frameChanged);
                         skeleton_right.changeFrame(frameChanged);
                         skeleton_left.changeFrame(frameChanged);
                         if(playerJump){
                            jumpTick++;
                        }
                         if(player.collideObject(enemies1)){
                            player.changeHealth(-0.1);
                            if(player.getHealth()>60){
                                health.setFill(Color.GREEN);
                            }else if(player.getHealth()>30 && player.getHealth()<60){
                                health.setFill(Color.ORANGE);
                            }else{
                                health.setFill(Color.RED);
                            }
                        }else{System.out.println("im good");}
                         player.checkAlive();
                         pos_last = rect1.getTranslateX();
                            frameChanged = false;
                     }else{
                         //another frame
                         if(jumpTick>3){jumpTick=0; playerJump=false; player.setFalling(true);}
                         frameChanged = true;
                     }
                     
                     before = System.nanoTime();
                }
                 
                
                if(player.getGameObject().getBoundsInLocal().intersects(level.getEnd().getBoundsInLocal())){
                    changeLevel();
                }
                
                  
                 if(playerJump ){
                        
                        player.getGameObject().setY(player.getGameObject().getY()-6);
                        
                     
                 }
//                if(!player.isAlive()){
//                    try {
//                        playAudio(resourcesDirectory.getAbsolutePath()+src_slash+"music"+src_slash+"sad.wav");
//                    } catch (UnsupportedAudioFileException ex) {
//                        Logger.getLogger(Xgame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//                    } catch (IOException ex) {
//                        Logger.getLogger(Xgame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//                    } catch (LineUnavailableException ex) {
//                        Logger.getLogger(Xgame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//                    }
//                    Text t = new Text (300, 450, "You are DEAD!");
//                    t.setFont(Font.font ("Verdana", 60));
//                    t.setFill(Color.RED);
//                    if(player.getLevel() == 1){
//                        pane1.getChildren().add(t);
//                    }
//                    if(player.getLevel() == 2){
//                        pane1.getChildren().add(t);
//                    }
//                    if(player.getLevel() == 3){
//                        pane1.getChildren().add(t);
//                    }
//                    ft.stop();
//                    this.stop();
//                }
                double pos_now = rect1.getTranslateX();
                if(pos_now > pos_last){
                    rect1.setFill(skeleton_right.getFrame());
                }else if(pos_now < pos_last ){
                    rect1.setFill(skeleton_left.getFrame());
                }
                
                
                if(player.movingRight()){ 
                    player.moveRight();
                    player.getGameObject().setFill(player_right.getFrame());
                }
                if(player.movingLeft()){
                    player.moveLeft();
                    player.getGameObject().setFill(player_left.getFrame());
                    
                }
                if(!player.movingLeft() && !player.movingRight()){
                   if(player.facingRight()){
                       player.getGameObject().setFill(player_idle.getFrame());
                   }else{
                       player.getGameObject().setFill(player_idle_left.getFrame());
                   }
                    
                    
                }
                if(!playerJump){
                     player.setFalling(true);
                    player.fall();
                }
                
                if(player.getCollidingYu()){
                    player.setFalling(false);
                    
                }
                if(!player.getCollidingYu() ){
                   
                    if(player.facingRight()){
                        player.getGameObject().setFill(player_fall.getFrame());
                    }else{
                        player.getGameObject().setFill(player_fall_left.getFrame());
                    }
                    
                }
            } 
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

    private Pane drawLevel1(Level level) throws IOException {
        //Level is created
        Pane s = new Pane();
        src_slash = src();
        leveltiles = level.getLevelTiles();

        //startMusic(); 

        s.getChildren().addAll(level.getRoot(), player.getGameObject());
        points = new Text (500, 20, "Score: "+player.getScore());
        points.setFont(Font.font ("Verdana", 20));
        
        rect1 = new Rectangle(160,810,60,60);

        rect1.setFill(Color.RED);
        enemies1.add(rect1);
        ft = new TranslateTransition(Duration.millis(2000), rect1);
        ft.setFromX(0f);
        ft.setByX(90);
        ft.setCycleCount(Timeline.INDEFINITE);
        ft.setAutoReverse(true);
        s.getChildren().addAll(rect1,points);
        ft.play();
        return s;
    }
    private Pane drawLevel2(Level level) throws IOException {
        //Level is created
        Pane s = new Pane();
        src_slash = src();
        leveltiles = level.getLevelTiles();

        //startMusic(); 

        s.getChildren().addAll(level.getRoot(), player.getGameObject());
        points = new Text (500, 20, "Score: "+player.getScore());
        points.setFont(Font.font ("Verdana", 20));
        
        rect1 = new Rectangle(160,810,60,60);

        rect1.setFill(Color.RED);
        enemies2.add(rect1);
        ft = new TranslateTransition(Duration.millis(2000), rect1);
        ft.setFromX(0f);
        ft.setByX(90);
        ft.setCycleCount(Timeline.INDEFINITE);
        ft.setAutoReverse(true);
        s.getChildren().addAll(rect1,points);
        ft.play();
        return s;
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
    public void testClicked(ActionEvent e){
        if (e.getSource()==btnscene1){
            player = player2;
            pane1.getChildren().removeAll(player.getGameObject(),player1.getGameObject(), player2.getGameObject());
            pane2.getChildren().removeAll(player.getGameObject(),player1.getGameObject(), player2.getGameObject());
            stage.setScene(scene2);
            stage.show();
        }
        else{
            player = player1;
            pane1.getChildren().removeAll(player.getGameObject(),player1.getGameObject(), player2.getGameObject());
            pane2.getChildren().removeAll(player.getGameObject(),player1.getGameObject(), player2.getGameObject());
            pane1.getChildren().add(player.getGameObject());
            stage.setScene(scene1);
            stage.show();
        }
    }
    public void controls2(){
        stage.getScene().setOnKeyPressed(e ->  {
            
        }
        );}
    public void controls(){
        stage.getScene().setOnKeyPressed(e -> {
            
            if (e.getCode() == KeyCode.LEFT) {
                player.setMovingLeft(true);
                s1 = true;
            }
            if (e.getCode() == KeyCode.F9) {
                
                stage.setScene(scenemenu);
                stage.show();
            }
            
            if (e.getCode() == KeyCode.RIGHT) {
                player.setMovingRight(true); 
                
            }
            if (e.getCode() == KeyCode.UP) {
                s2 = true;
                player.setFacingLeft(false);
                player.setFacingRight(false);
                
            }
            if (e.getCode() == KeyCode.SPACE && player.facingRight()) {
                
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
        stage.getScene().setOnKeyReleased(e -> {
       player.setMovingRight(false);
        player.setMovingLeft(false);
        
           if (e.getCode() == KeyCode.F8) {
                playerJump = true;
           }
        
           if (e.getCode() == KeyCode.LEFT) {
                    player.setMovingLeft(false);
           }
           if (e.getCode() == KeyCode.RIGHT) {
                player.setMovingRight(false);
           }
                      
        });
    }
    public void levelCheck(){
        if(player.getLevel() == 1){
            level = level1;
            leveltiles = leveltiles1;
            player = player1;
        }
        if(player.getLevel() == 2){
            level = level2;
            leveltiles = leveltiles2;
            player = player2;
        }else{
            //animator.stop();
        }
    }
    public void changeLevel(){
        if(player.getLevel() == 1){
            level = level2;
            leveltiles = leveltiles2;
            player = player2;
            pane1.getChildren().removeAll(player.getGameObject(),player1.getGameObject(), player2.getGameObject());
            pane2.getChildren().removeAll(player.getGameObject(),player1.getGameObject(), player2.getGameObject());
            level_menu = false; level_1 = false; level_2 = true;
            stage.setScene(scene2);
            stage.show();
            pane2.getChildren().add(player.getGameObject());
        }
    }
    public VBox menuCreator(){
        start=new Button("Start");
        load=new Button("Load Game");
        save=new Button("Save Game");
        highscore=new Button("Highscore");
        exit = new Button("Exit");
        
        start.setOnAction(e-> {
            try {
                menuLogic(e);
            } catch (UnsupportedEncodingException ex) {
                Logger.getLogger(Xgame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Xgame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(Xgame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            }
        });
        load.setOnAction(e-> {
            try {
                menuLogic(e);
            } catch (UnsupportedEncodingException ex) {
                Logger.getLogger(Xgame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Xgame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(Xgame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            }
        });
        highscore.setOnAction(e-> {
            try {
                menuLogic(e);
            } catch (UnsupportedEncodingException ex) {
                Logger.getLogger(Xgame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Xgame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(Xgame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            }
        });
        save.setOnAction(e-> {
            try {
                menuLogic(e);
            } catch (UnsupportedEncodingException ex) {
                Logger.getLogger(Xgame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Xgame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(Xgame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            }
        });
        exit.setOnAction(e-> {
            try {
                menuLogic(e);
            } catch (UnsupportedEncodingException ex) {
                Logger.getLogger(Xgame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Xgame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(Xgame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            }
        });
        VBox box = new VBox();
        box.getChildren().addAll(start,load,save,highscore,exit);
        box.setTranslateX(400);
        box.setTranslateY(600);
        return box;
    }
    public void menuLogic(ActionEvent e) throws UnsupportedEncodingException, FileNotFoundException, IOException{
        
        if (e.getSource()==start){
            player = player1;
            
            level.createLevel(level1.level_1());
            pane1.getChildren().removeAll(player.getGameObject(),player1.getGameObject(), player2.getGameObject());
            pane2.getChildren().removeAll(player.getGameObject(),player1.getGameObject(), player2.getGameObject());
            level_menu = false;
            
            stage.setScene(scene1);
            stage.show();
            pane1.getChildren().add(player.getGameObject());
        }
        if (e.getSource()==load){
            loadGame();
        }
        if (e.getSource()==save){
            //System.out.println("save");
            if(player.getLevel() != 0){
                saveGame();
            }else{
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("whoops!");
                alert.setHeaderText(null);
                alert.setContentText("You need to start playing first!");
                alert.showAndWait();
            }
        }
        if (e.getSource()==highscore){
            //System.out.println("highscore");
        }
        if (e.getSource()==exit){
            System.exit(0);
        }
    }
    public void saveGame() throws UnsupportedEncodingException, FileNotFoundException{
        
        int lvl = player.getLevel();
        double playerX = player.getGameObject().getX();
        double playerY = player.getGameObject().getY();
        
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);
        fileChooser.setTitle("Save game");
        fileChooser.setInitialDirectory(new File(resourcesDirectory.getAbsolutePath()+src_slash+"saves"));
        
        File file = fileChooser.showSaveDialog(stage);
        if (file != null) {
                PrintWriter writer = new PrintWriter(file, "UTF-8");
                writer.println( "lvl:"+player.getLevel() );
                writer.println( "playerX:"+player.getGameObject().getX() );
                writer.println( "playerY:"+player.getGameObject().getY() );
                writer.println( "score:"+player.getScore() );
                writer.close();
            }
    }
    public void loadGame() throws FileNotFoundException, IOException{
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);
        fileChooser.setTitle("Save game");
        fileChooser.setInitialDirectory(new File(resourcesDirectory.getAbsolutePath()+src_slash+"saves"));
        
        File file = fileChooser.showOpenDialog(stage);
        
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                if(line.startsWith("lvl:")){
                   String[] part= line.split(":");  System.out.println(part[1]);
                }
                if(line.startsWith("playerX:")){
                   String[] part= line.split(":");  System.out.println(part[1]);
                }
                if(line.startsWith("playerY:")){
                   String[] part= line.split(":");  System.out.println(part[1]);
                }
                if(line.startsWith("score:")){
                   String[] part= line.split(":");  System.out.println(part[1]);
                }
            }
}
    }
    private void saveTextToFile(String content, File file) {
        try {
            PrintWriter writer;
            writer = new PrintWriter(file);
            writer.println(content);
            writer.close();
        } catch (IOException ex) {
            
        }
    }
}

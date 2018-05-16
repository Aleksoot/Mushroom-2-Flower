/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xgame;


import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import javafx.animation.AnimationTimer;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import javax.imageio.IIOException;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javafx.fxml.FXMLLoader;
import static javafx.scene.input.DataFormat.URL;
import javax.sound.sampled.TargetDataLine;
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
   Level level=new Level(), level1=new Level(), level2=new Level();
   List<Tile> leveltiles=new ArrayList(), leveltiles1=new ArrayList(), leveltiles2=new ArrayList();
   List<Rectangle> enemies=new ArrayList(), enemies1=new ArrayList(), enemies2=new ArrayList();
   Player player=new Player(), player1=new Player(), player2=new Player();
   String src_slash=new String();String currentScore=new String();
   Text points=new Text(), points1=new Text(), points2=new Text();
   Rectangle rect1=new Rectangle(),health=new Rectangle(),health1=new Rectangle(),health2=new Rectangle();
   TranslateTransition ft=new TranslateTransition();
   Pane panemenu=new Pane(), paneHighscore=new Pane(), pane1=new Pane(), pane2=new Pane();
   Button back=new Button(),start=new Button(), load=new Button(), save=new Button(), highscore=new Button(), exit=new Button();
   Image bg1,bg2;
    Scene scenemenu, sceneHighscore, scene1, scene2, scene3;
    Stage stage;
   int frameCount=0;
   SpriteAnimation player_right,player_left,player_fall,player_fall_left,player_idle,player_idle_left,skeleton_right,skeleton_left,dragon_right,dragon_left,fireball_left,fireball_right;
   AnimationTimer animator;
   boolean animating = true;
   public boolean frameChanged=false;
   public boolean musicPlaying = false;
   public long playerY;
   public boolean playerJump, disableJumping, s1, s2;
   public int jumpTick;
   Clip backgroundMusic;
   GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
   int screenHeight = gd.getDisplayMode().getHeight();
   double tilesize = 30; double scale=1;

    @Override
    public void start(Stage primaryStage) throws IIOException, IOException, URISyntaxException {
        
        src_slash=src();
        stage = primaryStage;
        if(screenHeight < 1080){
            scale = 0.666;
            tilesize = 30*scale;
           
        }
        stage.setResizable(false);

        player1.getGameObject().setX(25*tilesize); player1.getGameObject().setY(3*tilesize); player1.setLevel(1);
        player2.getGameObject().setX(60); player2.getGameObject().setY(810); player2.setLevel(2);
     
        points = new Text (500, 20, "Score: "); points.setFont(Font.font ("Verdana", 20));
        points1 = new Text (500, 20, "Score: "); points1.setFont(Font.font ("Verdana", 20));
        points2 = new Text (500, 20, "Score: "); points2.setFont(Font.font ("Verdana", 20));
       
       
        pane1 = (Pane)level1.createLevel(level1.level_1()); leveltiles1 = level1.getLevelTiles();
        pane2 = level2.createLevel(level2.level_2()); leveltiles2 = level2.getLevelTiles();
        
        pane1 = (Pane)drawLevel1(level1); pane2 = (Pane)drawLevel2(level2); panemenu = new Pane(); paneHighscore = new Pane();
   
        health = new Rectangle(scale*tilesize, (scale*tilesize)-(scale*tilesize*0.3));
        health.setFill(Color.GREEN); health.setX( 30*scale ); health.setY( 5*scale);
        health1 = new Rectangle(scale*tilesize, (scale*tilesize)-(scale*tilesize*0.3));
        health1.setFill(Color.GREEN); health1.setX( 30*scale ); health1.setY( 5*scale);
        health2 = new Rectangle(scale*tilesize, (scale*tilesize)-(scale*tilesize*0.3));
        health2.setFill(Color.GREEN); health2.setX( 30*scale ); health2.setY( 5*scale);
        
        
        InputStream sky = this.getClass().getResourceAsStream("sky.jpg");
        InputStream b1 = this.getClass().getResourceAsStream("forest.jpg");
        InputStream b2 = this.getClass().getResourceAsStream("BG.png");
        InputStream log = this.getClass().getResourceAsStream("logo.png");
        Rectangle logo = new Rectangle(tilesize*10,tilesize*10);
        logo.setX(tilesize*10); logo.setY(200);

        logo.setFill(new ImagePattern(new Image(log)));
        
        ImageView backgroundmenu = new ImageView(new Image(sky, (tilesize*30)+30,(tilesize*30)+30, false, false));
        ImageView backgroundlvl1 = new ImageView(new Image(b1, (tilesize*30)+30,(tilesize*30)+30, false, false));
        ImageView backgroundlvl2 = new ImageView(new Image(b2, (tilesize*30)+30,(tilesize*30)+30, false, false));
        
        panemenu.getChildren().addAll(backgroundmenu, logo, menuCreator() );
        paneHighscore.getChildren().addAll(highScores());
        
        pane1.getChildren().addAll(backgroundlvl1); backgroundlvl1.toBack();
        pane2.getChildren().addAll(backgroundlvl2); backgroundlvl2.toBack();
        
        scenemenu = new Scene(panemenu,tilesize*30,tilesize*30);
        sceneHighscore = new Scene(paneHighscore,tilesize*30,tilesize*30);
        scene1 = new Scene(pane1,tilesize*30,tilesize*30);
        scene2 = new Scene(pane2,tilesize*30,tilesize*30);
        
        stage.setScene(scenemenu);
        stage.setTitle("Mushroom 2 flower");
        
        stage.show();
        new Thread() {
            public void run() {
        
                try {
                    player_left = new SpriteAnimation(addFolderSprites( "runner_left_1" ) );
                    player_right = new SpriteAnimation(addFolderSprites( "runner_right_1" ) );
                    player_fall_left = new SpriteAnimation(addFolderSprites( "mid_air-left" ) );
                    player_fall = new SpriteAnimation(addFolderSprites( "mid_air-right" ) );
                    player_idle = new SpriteAnimation(addFolderSprites( "idle_rights" ) );
                    player_idle_left = new SpriteAnimation(addFolderSprites( "idle_00" ) );
                    skeleton_left = new SpriteAnimation(addFolderSprites( "go_sr" ) );
                    skeleton_right = new SpriteAnimation(addFolderSprites( "go_rrr" ) );
                } catch (URISyntaxException ex) {
                    Logger.getLogger(Xgame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(Xgame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                }
           
        }
    }.start();
        //addFolderSprites("sdas");
        //Animations

//        dragon_right = new SpriteAnimation(addFolderSprites( new File(resourcesDirectory.getAbsolutePath()+src_slash+"enemy"+src_slash+"dragon_right") ) );
//        dragon_left = new  SpriteAnimation(addFolderSprites( new File(resourcesDirectory.getAbsolutePath()+src_slash+"enemy"+src_slash+"dragon_right") ) );
//        fireball_right = new SpriteAnimation(addFolderSprites( new File(resourcesDirectory.getAbsolutePath()+src_slash+"enemy"+src_slash+"fireball_right") ) );
//        fireball_left = new SpriteAnimation(addFolderSprites( new File(resourcesDirectory.getAbsolutePath()+src_slash+"enemy"+src_slash+"fireball_left") ) );
                
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
                currentScore = Integer.toString( player.getScore() );
                points.setText("Score: "+currentScore);
                if(player.isFalling()) player.fall(); player.stopY = false;
                player.colliding(leveltiles, Type.solid);
                health.setWidth( player.getHealth()*2*scale);
                
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
                            for(int i=0;i<3;i++){
                                playerJump = true;
                                disableJumping = true;
                                player.setFalling(false);
                            }
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
                        }else{}
                         player.checkAlive();
                         pos_last = rect1.getTranslateX();
                            frameChanged = false;
                     }else{
                         //another frame
                         if(jumpTick>3){jumpTick=0; playerJump=false; disableJumping=false;}
                         frameChanged = true;
                     }
                     
                     before = System.nanoTime();
                }
                 
                
                if(player.getGameObject().getBoundsInLocal().intersects(level.getEnd().getBoundsInLocal())){
                    changeLevel(player.getLevel()+1);
                }
                
                  
                 if(playerJump ){
                     
                        player.getGameObject().setY(player.getGameObject().getY()-8);
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
                if(player.getCollidingYu()){
                    player.setFalling(false);
                    player.stopY = true;
                    
                }else{
                    player.setFalling(true);
                    disableJumping=true;
                }
                if(player.isJumping()){
                    
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

    

    public void startMusic(InputStream is) throws UnsupportedAudioFileException, IOException, LineUnavailableException{

        InputStream buffer = new BufferedInputStream(is);  
        AudioInputStream stream = AudioSystem.getAudioInputStream(buffer);
        backgroundMusic = AudioSystem.getClip();
        backgroundMusic.open(stream);
        FloatControl volume= (FloatControl) backgroundMusic.getControl(FloatControl.Type.MASTER_GAIN);
        volume.setValue(-20.4f); // Reduce volume by 10 decibels.
 
        Thread musikk = new Thread(){
            @Override
            public void run() {
                try{          
                    backgroundMusic.loop(Clip.LOOP_CONTINUOUSLY);  
                }catch(Exception e){
                    System.out.println(e);
                }  
            }
        };
        if(level_1){
            musikk.start();
        }
    }
    public void playAudio(InputStream is) throws UnsupportedAudioFileException, IOException, LineUnavailableException{

        InputStream buffer = new BufferedInputStream(is);  
        AudioInputStream stream = AudioSystem.getAudioInputStream(buffer);
        Clip background = AudioSystem.getClip();
        background.open(stream);
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
        leveltiles = level.getLevelTiles();

        

        s.getChildren().addAll(level.getRoot());
        
        rect1 = new Rectangle(160,810,60,60);
        Enemy bad = new Enemy();
//        bad.getGameObject().setX(40); bad.getGameObject().setY(60);
//        pane1.getChildren().add(bad.getGameObject());
        rect1.setFill(Color.RED);
        enemies1.add(rect1);
        ft = new TranslateTransition(Duration.millis(2000), rect1);
        ft.setFromX(0f);
        ft.setByX(90);
        ft.setCycleCount(Timeline.INDEFINITE);
        ft.setAutoReverse(true);
        s.getChildren().addAll(rect1);
        ft.play();
        return s;
    }
    private Pane drawLevel2(Level level) throws IOException {
        //Level is created
        Pane s = new Pane();
        leveltiles = level.getLevelTiles();

        

        s.getChildren().addAll(level.getRoot());
        
        
        rect1 = new Rectangle(160,810,60,60);

        rect1.setFill(Color.RED);
        enemies2.add(rect1);
        ft = new TranslateTransition(Duration.millis(2000), rect1);
        ft.setFromX(0f);
        ft.setByX(90);
        ft.setCycleCount(Timeline.INDEFINITE);
        ft.setAutoReverse(true);
        s.getChildren().addAll(rect1);
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
     public List<BufferedImage> addFolderSprites(String file) throws URISyntaxException, IOException{
         String jar = new File(Xgame.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getPath();
         List<BufferedImage> imgs = new ArrayList();
         ZipFile zipFile = new ZipFile(jar);
        ZipInputStream stream = new ZipInputStream(new BufferedInputStream(new FileInputStream(jar)));
        ZipEntry entry = null;
        
        
        while ((entry = stream.getNextEntry()) != null ) {
          if(entry.getName().contains(file)){  
              InputStream inputStream = zipFile.getInputStream(entry);
              imgs.add( ImageIO.read( inputStream ));
          }
        
          
        }
       return imgs;
     }
     

    public void controls(){
        stage.getScene().setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.F8) {
                playerJump = true;
                player.addScore(10);
            }
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
            if (e.getCode() == KeyCode.UP && !player.collisionYo) {
                s2 = true;
                player.setFacingLeft(false);
                player.setFacingRight(false);
                
            }
            if (e.getCode() == KeyCode.SPACE && player.facingRight() && !player.collisionYo) {
                
                if(!player.isFalling() ){
                    player.setMovingRight(true);
                    player.jump();
                    InputStream boing = this.getClass().getResourceAsStream("jump.wav");
                    try {
                        playAudio(boing);
                    } catch (UnsupportedAudioFileException ex) {
                        Logger.getLogger(Xgame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                    } catch (IOException ex) {
                        Logger.getLogger(Xgame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                    } catch (LineUnavailableException ex) {
                        Logger.getLogger(Xgame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                    }
                    
                
                }
            } 
            if (e.getCode() == KeyCode.SPACE && player.facingLeft() && !player.collisionYo) {
                
                if(!player.isFalling() ){
                    player.setMovingLeft(true);
                    player.jump();
                    InputStream boing = this.getClass().getResourceAsStream("jump.wav");
                    try {
                        playAudio(boing);
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
                    InputStream boing = this.getClass().getResourceAsStream("jump.wav");
                    try {
                        playAudio(boing);
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
                disableJumping = true;
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
    public void changeLevel(int lvl){
        if(lvl < 2){
            
            level = level1; leveltiles = leveltiles1;
            player = player1; health = health1; points = points1;
            pane1.getChildren().removeAll(player.getGameObject(),player1.getGameObject(), player2.getGameObject(),health,points);
            pane2.getChildren().removeAll(player.getGameObject(),player1.getGameObject(), player2.getGameObject(),health,points);
            level_menu = false; level_1 = true; level_2 = false;
            stage.setScene(scene1);
            stage.show();
            pane1.getChildren().addAll(player.getGameObject(),health,points);
        }
        if(lvl == 2){
            level = level2; leveltiles = leveltiles2;
            player2.setHealth( player1.getHealth() ); player2.setScore( player1.getScore() );
            player = player2; health = health2; points = points2;
            pane1.getChildren().removeAll(player.getGameObject(),player1.getGameObject(), player2.getGameObject(),health,points);
            pane2.getChildren().removeAll(player.getGameObject(),player1.getGameObject(), player2.getGameObject(),health,points);
            level_menu = false; level_1 = false; level_2 = true;
            stage.setScene(scene2);
            stage.show();
            pane2.getChildren().addAll(player.getGameObject(),health,points);
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
        box.setTranslateX(400*scale);
        box.setTranslateY(600*scale);
        return box;
    }
    public Pane highScores(){
        
        Pane pane = new Pane();
        
        Button back = new Button("Back"); back.setOnAction(e-> {
            stage.setScene(scenemenu);
            stage.show();
        });
        
        VBox box = new VBox();
        
        Text headline = new Text (500*scale, 20*scale, "The hall of FAME");
        headline.setFill(Color.BLACK);
        headline.setFont(Font.font ("Verdana", 30*scale));
        
        Text h1 = new Text (500*scale, 20*scale, "1. josef333: 666");
        h1.setFont(Font.font ("Verdana", 20*scale));
        h1.setFill(Color.WHITE);
        box.getChildren().addAll(headline,h1);
        box.setTranslateX(250*scale);
        box.setTranslateY(200*scale);
        pane.getChildren().addAll(back, box);
        return pane;
    
    }
    public void menuLogic(ActionEvent e) throws UnsupportedEncodingException, FileNotFoundException, IOException{
        
        if (e.getSource()==start){
            if(player.getLevel() < 2){
                
                changeLevel(1);
            }else{
                changeLevel(2);
            }
            
        }
        if (e.getSource()==load){
            loadGame();
        }
        if (e.getSource()==save){
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
            stage.setScene(sceneHighscore);
            stage.show();
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
        int lvl=0;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            
            while ((line = br.readLine()) != null) {
                String[] part;
                if(line.startsWith("lvl:")){
                   part= line.split(":");  lvl=Integer.parseInt(part[1]);
                   
                }
                if(line.startsWith("playerX:")){
                   part= line.split(":");
                   if(lvl==1){
                        player1.getGameObject().setX(Double.parseDouble(part[1]));
                   }
                   if(lvl==2){
                        player2.getGameObject().setX(Double.parseDouble(part[1]));
                   }
                }
                if(line.startsWith("playerY:")){
                   part= line.split(":");  
                   if(lvl==1){
                        player1.getGameObject().setY(Double.parseDouble(part[1])-5);
                        player = player1;
                   }
                   if(lvl==2){
                       pane2.getChildren().remove(player2);
                        player2.getGameObject().setY(Double.parseDouble(part[1])-5);
                        player = player2;
                   }
                   
                }
                if(line.startsWith("score:")){
                    if(lvl==1){
                        part= line.split(":");  System.out.println(part[1]);
                        player1.setScore(Integer.parseInt(part[1]));
                    }
                }
            }
            
        }catch(IOException ex){ 
            
        }finally {
            changeLevel(lvl);
            level_menu = false;
        }
        
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        launch(args);
        //System.out.println("Number of active threads from the given thread: " + Thread.activeCount());
        
    }
    
}

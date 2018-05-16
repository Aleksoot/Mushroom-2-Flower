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
import javafx.scene.layout.Background;
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
   Enemy enemy, enemy11, enemy12, enemy13, enemy21, enemy22, enemy23, enemyfb, enemyfb2;
   String src_slash=new String();String currentScore=new String();
   Text points=new Text(), points1=new Text(), points2=new Text(), loading=new Text(), information=new Text(), information2=new Text();
   Rectangle rect1=new Rectangle(),health=new Rectangle(),health1=new Rectangle(),health2=new Rectangle();

   TranslateTransition ft=new TranslateTransition(); TranslateTransition ft21=new TranslateTransition(); TranslateTransition ft22=new TranslateTransition(); TranslateTransition ft23=new TranslateTransition();; TranslateTransition ft2=new TranslateTransition(); TranslateTransition ft13=new TranslateTransition(); TranslateTransition ftfb=new TranslateTransition(); TranslateTransition ftfb2=new TranslateTransition();
   
   Pane panemenu=new Pane(), paneHighscore=new Pane(), pane1=new Pane(), pane2=new Pane(), paneEnd=new Pane();
   Button back=new Button(),start=new Button(), load=new Button(), save=new Button(), highscore=new Button(), exit=new Button(), exit2=new Button(), musicB = new Button();

   Image bg1,bg2;
    Scene scenemenu, sceneHighscore, scene1, scene2, scene3, sceneEnd;
    Stage stage;
   int frameCount=0;
   SpriteAnimation player_right,player_left,player_fall,player_fall_left,player_idle,player_idle_left,skeleton_right,skeleton_left,dragon_right,dragon_left,fireball_left,fireball_right;
   AnimationTimer animator;
   boolean animating = true;
   public boolean frameChanged=false;
   public boolean musicPlaying = false;
   public long playerY;
   public boolean disableJumping, s1, s2, levelChanged;
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

        player1.getGameObject().setX(2*tilesize); player1.getGameObject().setY(27*tilesize); player1.setLevel(1);
        player2.getGameObject().setX(60); player2.getGameObject().setY(810); player2.setLevel(2);
     
        enemy = new Enemy(); enemy11 = new Enemy(); enemy12 = new Enemy(); enemy13 = new Enemy(); enemy21 = new Enemy(); enemy22 = new Enemy(); enemy23 = new Enemy(); enemyfb = new Enemy(); enemyfb2 = new Enemy();
        
        points = new Text (500, 20, "Score: "); points.setFont(Font.font ("Verdana", 20));
        points1 = new Text (500, 20, "Score: "); points1.setFont(Font.font ("Verdana", 20));
        points2 = new Text (500, 20, "Score: "); points2.setFont(Font.font ("Verdana", 20));
        loading = new Text (500, 20, "Loading ..."); loading.setFont(Font.font ("Verdana", 20));
        loading.setX(13*tilesize); loading.setY(19*tilesize);
        information = new Text(500, 15, "Control with arrows and space. Press F9 for menu."); information.setFill(Color.WHITE);
        information.setX(300*scale); information.setY(30*scale);
        information2 = new Text(500, 15, "Music by Ole-Christian Apeland - ooapeland@haugnett.no"); 
        information2.setX(300*scale); information2.setY(850*scale);
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
        
        panemenu.getChildren().addAll(backgroundmenu, logo, menuCreator(), loading, information, information2 );
        paneHighscore.getChildren().addAll(highScores());
        
        pane1.getChildren().addAll(backgroundlvl1); backgroundlvl1.toBack();
        pane2.getChildren().addAll(backgroundlvl2); backgroundlvl2.toBack();
        
        scenemenu = new Scene(panemenu,tilesize*30,tilesize*30);
        sceneHighscore = new Scene(paneHighscore,tilesize*30,tilesize*30);
        scene1 = new Scene(pane1,tilesize*30,tilesize*30);
        scene2 = new Scene(pane2,tilesize*30,tilesize*30);
        sceneEnd = new Scene(paneEnd,tilesize*30,tilesize*30);
        stage.setScene(scenemenu);
        stage.setTitle("Mushroom 2 flower");
        
        stage.show();
        new Thread() {
            public void run() {
        
                try {
                    start.setDisable(true);
                    player_left = new SpriteAnimation(addFolderSprites( "runner_left_1" ) );
                    player_right = new SpriteAnimation(addFolderSprites( "runner_right_1" ) );
                    player_fall_left = new SpriteAnimation(addFolderSprites( "mid_air-left" ) );
                    player_fall = new SpriteAnimation(addFolderSprites( "mid_air-right" ) );
                    player_idle = new SpriteAnimation(addFolderSprites( "idle_rights" ) );
                    player_idle_left = new SpriteAnimation(addFolderSprites( "idle_00" ) );
                    skeleton_left = new SpriteAnimation(addFolderSprites( "go_sr" ) );
                    skeleton_right = new SpriteAnimation(addFolderSprites( "go_rrr" ) );
                    dragon_left = new SpriteAnimation(addFolderSprites( "dleft" ) );
                    dragon_right = new SpriteAnimation(addFolderSprites( "dright" ) );
                    fireball_left = new SpriteAnimation(addFolderSprites( "f_left" ) );
                    fireball_right = new SpriteAnimation(addFolderSprites( "f_right" ) );
                } catch (URISyntaxException ex) {
                    Logger.getLogger(Xgame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(Xgame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                }
                start.setDisable(false);
                loading.setText(null);
                
        }
    }.start();
       
        animator = new AnimationTimer(){
           
            long before = System.nanoTime();
            long before2 = before;
            double pos_last;
            double pos_last2;
            double pos_last3;
            double pos_lastfb;
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

                         player.addScore(-1);


                         dragon_left.changeFrame(frameChanged);
                         dragon_right.changeFrame(frameChanged);
                         fireball_left.changeFrame(frameChanged);
                         fireball_right.changeFrame(frameChanged);

                         if(player.collideObject(enemies)){
                            player.changeHealth(-0.2);
                            
                            enemyfb.getGameObject().setVisible(false);
                            
                            ftfb.stop();

                            player.addScore(-5);

                            if(player.getHealth()>60){
                                health.setFill(Color.GREEN);
                            }else if(player.getHealth()>30 && player.getHealth()<60){
                                health.setFill(Color.ORANGE);
                            }else{
                                health.setFill(Color.RED);
                            }
                        }else{}
                         player.checkAlive();
                         pos_last = enemy11.getGameObject().getTranslateX();
                         pos_last2 = enemy12.getGameObject().getTranslateX();
                         pos_last3 = enemy13.getGameObject().getTranslateX();
                         pos_lastfb = enemyfb.getGameObject().getTranslateX();
                            frameChanged = false;
                     }else{
                         //another frame
                         frameChanged = true;
                     }
                     
                     before = System.nanoTime();
                }
                 
                if(!player.isAlive()){
                    
                    InputStream sad = this.getClass().getResourceAsStream("jump.wav");
                    
                    Text t = new Text (22*tilesize, 15*tilesize, "You are DEAD!");
                    t.setFont(Font.font ("Verdana", 60));
                    t.setFill(Color.RED);
                    if(player.getLevel() == 1){
                        pane1.getChildren().add(t);
                    }
                    if(player.getLevel() == 2){
                        pane1.getChildren().add(t);
                    }
                    if(player.getLevel() == 3){
                        pane1.getChildren().add(t);
                    }
                    
                    this.stop();
                    reStart();
                    
                }
                if(player.getGameObject().getBoundsInLocal().intersects(level.getEnd().getBoundsInLocal())){
                        changeLevel(player.getLevel()+1);
                }
                
                double pos_now = enemy11.getGameObject().getTranslateX();
                double pos_now2 = enemy12.getGameObject().getTranslateX();
                double pos_now3 = enemy13.getGameObject().getTranslateX();
                double pos_nowfb = enemyfb.getGameObject().getTranslateX();
                if(pos_now > pos_last){   
                    enemy11.getGameObject().setFill(dragon_right.getFrame());
                    enemy21.getGameObject().setFill(dragon_right.getFrame());
                }else if(pos_now < pos_last){
                    
                    enemy11.getGameObject().setFill(dragon_left.getFrame());
                    enemy21.getGameObject().setFill(dragon_left.getFrame());
                }
                if(pos_now2 > pos_last2){
                    enemy12.getGameObject().setFill(skeleton_right.getFrame()); 
                    enemy22.getGameObject().setFill(skeleton_right.getFrame());
                }
                else if(pos_now2 < pos_last2){
                    enemy12.getGameObject().setFill(skeleton_left.getFrame());
                    enemy22.getGameObject().setFill(skeleton_left.getFrame());
                }
                if(pos_now3 > pos_last3){
                    enemy13.getGameObject().setFill(skeleton_right.getFrame());
                    enemy23.getGameObject().setFill(skeleton_right.getFrame());
                }
                if(pos_now3 < pos_last3){
                    enemy13.getGameObject().setFill(skeleton_left.getFrame());
                    enemy23.getGameObject().setFill(skeleton_left.getFrame());
                }
                double dragonY =  enemy11.getGameObject().getY();
                double playerY = player.getGameObject().getY();
                if( playerY >= dragonY-15 || playerY-15 >= dragonY ){
                    ftfb.play();
                   
                    if(pos_now > pos_last && pos_nowfb > pos_now){
                        
                        enemyfb.getGameObject().setVisible(true);
                        ftfb.play();
                        ftfb.setCycleCount(1);
                        enemyfb.getGameObject().setFill(fireball_right.getFrame());
                       
                    }
                    else if(pos_now < pos_last){
                        ftfb.stop();
                        enemyfb.getGameObject().setVisible(false);
                    }
                    
                }
                else{
                    enemyfb.getGameObject().setVisible(false);
                    enemyfb.getGameObject().setFill(fireball_right.getFrame());
                    ftfb.stop();
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

    

    public void toggleMusic() throws UnsupportedAudioFileException, IOException, LineUnavailableException{
        
        
        InputStream play1 = new BufferedInputStream(this.getClass().getResourceAsStream("menu.wav"));
        
        AudioInputStream stream = AudioSystem.getAudioInputStream(play1);
        Clip backgroundMusic = AudioSystem.getClip();
        backgroundMusic.open(stream);
        FloatControl volume= (FloatControl) backgroundMusic.getControl(FloatControl.Type.MASTER_GAIN);
        volume.setValue(-20.4f); // Reduce volume by 10 decibels.
 
        Thread musikk = new Thread(){
            @Override
            public void run() {
                    
                    backgroundMusic.loop(Clip.LOOP_CONTINUOUSLY);  
                    
            }
        };
        if(level_1){
            musikk.start();
        }else{
                    backgroundMusic.stop();
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
        rect1.setFill(Color.RED);
        
        enemy11 = new Enemy();
        enemy11.getGameObject().setX(100 * scale);
        enemy11.getGameObject().setY(440 * scale);
        enemy11.getGameObject().setHeight(160 * scale);
        enemy11.getGameObject().setWidth(160 * scale);
        enemies1.add(enemy11.getGameObject());

        enemy12 = new Enemy();
        enemy12.getGameObject().setX(200 * scale);
        enemy12.getGameObject().setY(610 * scale);
        enemy12.getGameObject().setHeight(80 * scale);
        enemy12.getGameObject().setWidth(80 * scale);
        enemies1.add(enemy12.getGameObject());
        
        enemy13 = new Enemy();
        enemy13.getGameObject().setX(312 * scale);
        enemy13.getGameObject().setY(290 * scale);
        enemy13.getGameObject().setHeight(70 * scale);
        enemy13.getGameObject().setWidth(70 * scale);
        enemies1.add(enemy13.getGameObject());
        
        enemyfb = new Enemy();
        enemyfb.getGameObject().setFill(Color.RED);
        enemyfb.getGameObject().setX(enemy11.getGameObject().getX());
        enemyfb.getGameObject().setY(enemy11.getGameObject().getY() + 20);
        enemyfb.getGameObject().setHeight(80 * scale);
        enemyfb.getGameObject().setWidth(80 * scale);
        enemies1.add(enemyfb.getGameObject());
        
        ft = new TranslateTransition(Duration.millis(2000), enemy11.getGameObject());
        ft.setFromX(0f);
        ft.setByX(150);
        ft.setCycleCount(Timeline.INDEFINITE);
        ft.setAutoReverse(true);
        
        ft13 = new TranslateTransition(Duration.millis(2000), enemy13.getGameObject());
        ft13.setFromX(0f);
        ft13.setByX(50);
        ft13.setCycleCount(Timeline.INDEFINITE);
        ft13.setAutoReverse(true);
        
        ft2 = new TranslateTransition(Duration.millis(2500), enemy12.getGameObject());
        ft2.setFromX(0f);
        ft2.setByX(100);
        ft2.setCycleCount(Timeline.INDEFINITE);
        ft2.setAutoReverse(true);
        
        ftfb = new TranslateTransition(Duration.millis(2000), enemyfb.getGameObject());
        ftfb.setFromX(0f);
        ftfb.setByX(600);
        ftfb.setAutoReverse(false);
        
        s.getChildren().addAll(points,enemy11.getGameObject(),enemy12.getGameObject(),enemy13.getGameObject(),enemyfb.getGameObject());
        ft.play();
        ft2.play();
        ft13.play();
        ftfb.play();
        return s;
    }
    private Pane drawLevel2(Level level) throws IOException {
        //Level is created
        Pane s = new Pane();
        leveltiles = level.getLevelTiles();
        s.getChildren().addAll(level.getRoot());
     
        enemy21 = new Enemy();
        enemy21.getGameObject().setX(7 * tilesize);
        enemy21.getGameObject().setY(16 *tilesize);
        enemy21.getGameObject().setHeight(160 * scale);
        enemy21.getGameObject().setWidth(160 * scale);
        enemies2.add(enemy21.getGameObject());

        enemy22 = new Enemy();
        enemy22.getGameObject().setX(11*tilesize);
        enemy22.getGameObject().setY(23*tilesize);
        enemy22.getGameObject().setHeight(90 * scale);
        enemy22.getGameObject().setWidth(90 * scale);
        enemies2.add(enemy22.getGameObject());
        
        enemy23 = new Enemy();
        enemy23.getGameObject().setX(10*tilesize);
        enemy23.getGameObject().setY(9*tilesize-10);
        enemy23.getGameObject().setHeight(70 * scale);
        enemy23.getGameObject().setWidth(70 * scale);
        enemies2.add(enemy23.getGameObject());
        
        ft21 = new TranslateTransition(Duration.millis(2000), enemy21.getGameObject());
        ft21.setFromX(0f);
        ft21.setByX(150);
        ft21.setCycleCount(Timeline.INDEFINITE);
        ft21.setAutoReverse(true);
        
        ft23 = new TranslateTransition(Duration.millis(2000), enemy23.getGameObject());
        ft23.setFromX(0f);
        ft23.setByX(50);
        ft23.setCycleCount(Timeline.INDEFINITE);
        ft23.setAutoReverse(true);
        
        ft22 = new TranslateTransition(Duration.millis(2500), enemy22.getGameObject());
        ft22.setFromX(0f);
        ft22.setByX(100);
        ft22.setCycleCount(Timeline.INDEFINITE);
        ft22.setAutoReverse(true);
        
        
        s.getChildren().addAll(points,enemy21.getGameObject(),enemy22.getGameObject(),enemy23.getGameObject());
        ft21.play();
        ft22.play();
        ft23.play();
        return s;
    }
    public void reStart(){
        final String javaBin = System.getProperty("java.home") + File.separator + "bin" + File.separator + "java";
        File currentJar = null;
        try {
            currentJar = new File(Xgame.class.getProtectionDomain().getCodeSource().getLocation().toURI());
        } catch (URISyntaxException ex) {
            Logger.getLogger(Xgame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        final ArrayList<String> command = new ArrayList<String>();
        command.add(javaBin);
        command.add("-jar");
        command.add(currentJar.getPath());
        final ProcessBuilder builder = new ProcessBuilder(command);
        try {
            builder.start();
        } catch (IOException ex) {
            Logger.getLogger(Xgame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        System.exit(0);
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
     public List<BufferedImage> addFolderSprites(String search) throws URISyntaxException, IOException{
         String jarfil = new File(Xgame.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getPath();
         List<BufferedImage> imgs = new ArrayList();
         ZipFile zipFile = new ZipFile(jarfil);
         ZipInputStream stream = new ZipInputStream(new BufferedInputStream(new FileInputStream(jarfil)));
         ZipEntry entry = null;
        
         while ((entry = stream.getNextEntry()) != null ) {
            if(entry.getName().contains(search)){  
                InputStream inputStream = zipFile.getInputStream(entry);
                System.out.println(entry);
                imgs.add( ImageIO.read( inputStream ));
            }
          
        }
       return imgs;
     }
     

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
           player.setMovingLeft(false);
           player.setMovingRight(false);
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
            enemies = enemies1;
            enemy = enemy11;
            enemy = enemy12;
            enemy = enemy13;
            enemy = enemyfb; 
   
        }
        if(player.getLevel() == 2){
            level = level2;
            leveltiles = leveltiles2;
            player = player2;
            enemies = enemies2;
            enemy = enemy21;
            enemy = enemy22;
            enemy = enemy23;
            enemy = enemyfb2;
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
           
            try {
                    toggleMusic();
                } catch (UnsupportedAudioFileException ex) {
                    Logger.getLogger(Xgame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(Xgame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                } catch (LineUnavailableException ex) {
                    Logger.getLogger(Xgame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                }
            
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
        if(lvl == 3){
            
            animator.stop();
//            checkHighScore();
            VBox results = new VBox();
            Text finish = new Text("You have completed the game!"); finish.setFill(Color.BLACK); finish.setFont(Font.font ("Verdana", 30*scale));
            Text score = new Text(); score.setFill(Color.WHITE); score.setFont(Font.font ("Verdana", 30*scale));
            exit2 = new Button("Exit");
            exit2.setOnAction(e-> {
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
            score.setText("You scored a respectable: "+player.getScore());
            
            InputStream ending = this.getClass().getResourceAsStream("sky.jpg");
            ImageView end = new ImageView(new Image(ending, (tilesize*30)+30,(tilesize*30)+30, false, false));
            results.setTranslateX(250*scale);
            results.setTranslateY(400*scale);
            results.getChildren().addAll(finish, score, exit2);
            paneEnd.getChildren().addAll(end,results);
            stage.setScene(sceneEnd);
            stage.show();
            
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
        if (e.getSource()==exit || e.getSource()==exit2){
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
                        part= line.split(":");  
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

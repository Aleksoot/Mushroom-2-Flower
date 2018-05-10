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
import javafx.scene.Node;
import javafx.scene.control.Label;
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
   List<Tile> leveltiles, leveltiles1, leveltiles2;
   Player player, player1, player2;
   String src_slash;
   Rectangle rect1;
   TranslateTransition ft;
   Pane pane1, pane2, pane3;
   Button btnscene1, btnscene2;
    Label lblscene1, lblscene2;
    Scene scene, scene2, scene3;
    Stage stage;
   int frameCount=0;
   SpriteAnimation player_right;
   SpriteAnimation player_left;
   SpriteAnimation player_fall;
   SpriteAnimation skeleton_right;
   SpriteAnimation skeleton_left;
   public boolean frameChanged=false;
   
    @Override
    public void start(Stage primaryStage) throws IOException, UnsupportedAudioFileException, LineUnavailableException {
        
        stage = primaryStage;
        Level level = new Level();
        Level level2 = new Level();
        player = new Player();
        player.getGameObject().setX(240);
        player.getGameObject().setY(210);
      
        
        
        btnscene1=new Button("next level");
        btnscene2=new Button("previous level");
        btnscene1.setOnAction(e-> ButtonClicked(e));
        btnscene2.setOnAction(e-> ButtonClicked(e));
        lblscene1=new Label("Scene 1");
        pane1 =  new Pane();
        pane2 = new Pane();
        pane1 = drawLevel(level);
        pane2 = drawLevel(level2);
        pane1.getChildren().addAll(btnscene1,player.getGameObject());
        pane2.getChildren().addAll(btnscene2);
        
        scene = new Scene(pane1,900,900);
       
        
         //level = new Level();
         
//        if(!level_1){
//        drawLevel(primaryStage, level, 2, root);
//        }else{ drawLevel(primaryStage, level, 2, root);}

        primaryStage.setScene(scene);
        primaryStage.setTitle("Hello World!");
        
        primaryStage.show();
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
       
        
        AnimationTimer animator = new AnimationTimer(){
           
            long before = System.nanoTime();
            double posy = player.getGameObject().getY();
            double pos_last;
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
                         skeleton_right.changeFrame(frameChanged);
                         skeleton_left.changeFrame(frameChanged);
                         player.collidingEnemy(rect1);
                         player.checkAlive();
                         pos_last = rect1.getTranslateX();
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
                //root.getChildren().add(t);
                    ft.stop();
                    this.stop();
                }
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

    private Pane drawLevel(Level level) throws IOException {
        //Level is created
        Pane s = new Pane();
        src_slash = src();
        leveltiles = level.getLevelTiles();
        
            level.createLevel(level.level_2());
        
        //startMusic(); 
//        player = new Player();
//        player.getGameObject().setX(240);
//        player.getGameObject().setY(210);
        
        s.getChildren().addAll(level.getRoot());
        
        
        //Player is created
        
        
        rect1 = new Rectangle(160,360,60,60);

        rect1.setFill(Color.RED);

        player_right = new SpriteAnimation(addFolderSprites( new File(resourcesDirectory.getAbsolutePath()+src_slash+"player"+src_slash+"runner") ) );
        player_left = new SpriteAnimation(addFolderSprites( new File(resourcesDirectory.getAbsolutePath()+src_slash+"player"+src_slash+"runner_left") ) );
        player_fall = new SpriteAnimation(addFolderSprites( new File(resourcesDirectory.getAbsolutePath()+src_slash+"player"+src_slash+"mid_air") ) );
        skeleton_right = new SpriteAnimation(addFolderSprites( new File(resourcesDirectory.getAbsolutePath()+src_slash+"skeleton"+src_slash+"walk_right") ) );
        skeleton_left = new SpriteAnimation(addFolderSprites( new File(resourcesDirectory.getAbsolutePath()+src_slash+"skeleton"+src_slash+"walk") ) );
        
        //Adding player to root
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
    
    public List<BufferedImage> addFolderSprites(final File folder) throws IOException {
        List<BufferedImage> list = new ArrayList<BufferedImage>();
  
        for (final File fileEntry : folder.listFiles()) {
           
           list.add(ImageIO.read(new File(folder+src()+fileEntry.getName())));
        }
        return list;
    
    }
    public void ButtonClicked(ActionEvent e)
    {
        if (e.getSource()==btnscene1){
            
            pane1.getChildren().remove(player.getGameObject());
            pane2.getChildren().addAll(player.getGameObject());
            scene.setRoot(pane2);
            player.colliding(leveltiles, Type.solid);
        }
        else{
            
            pane1.getChildren().add(player.getGameObject());
            pane2.getChildren().remove(player.getGameObject());
            scene.setRoot(pane1);
            player.colliding(leveltiles, Type.solid);
        }
    }
}

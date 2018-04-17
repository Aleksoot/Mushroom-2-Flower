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
import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.stage.Stage;
import javax.imageio.ImageIO;

/**
 *
 * @author faete
 */
///////
public class Xgame extends Application {
    
    @Override
    public void start(Stage primaryStage) throws IOException {
        
        //Level is created
        Level level = new Level();
        List<GameObject> tilelist = new ArrayList();
        level.createLevel();
        
        //Creating root setting it to be the level
        Pane root = new Pane();
        root = level.getRoot();
        System.out.println(level.getRoot());
        
        //Player is created
        Player player = new Player();
        player.getGameObject().setFill(Color.BLUE);
        player.getGameObject().setX(50);
        player.getGameObject().setY(50);
        
        //Adding player to root
        root.getChildren().add(player.getGameObject());
        Scene scene = new Scene(root, root.getPrefWidth(), root.getPrefHeight());
        
        //Setting a background color, title, primarystage, keylistener
        File resourcesDirectory = new File("src/xgame");
        BufferedImage bg = ImageIO.read(new File(resourcesDirectory.getAbsolutePath()+"\\BG.png"));
        Image card = SwingFXUtils.toFXImage(bg, null );
        scene.setFill(new ImagePattern(card));
        primaryStage.setTitle("Testbrett");
        primaryStage.setScene(scene);
        
        //Keylistener for the controls
        primaryStage.getScene().setOnKeyPressed(e -> {
            
            if (e.getCode() == KeyCode.LEFT) {
               player.moveLeft();
            }
            if (e.getCode() == KeyCode.RIGHT) {
                 player.moveRight();
                
            }
            if (e.getCode() == KeyCode.SPACE) {
               player.moveJump();
            } 
        });
        //
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}

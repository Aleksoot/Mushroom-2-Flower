/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xgame;

import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 *
 * @author faete
 */
public class Xgame extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        
        Level level = new Level();
        List<GameObject> tilelist = new ArrayList();
        level.createLevel();
        
        Pane root = new Pane();
        root = level.getRoot();
        System.out.println(level.getRoot());
        Player player = new Player();
        player.getGameObject().setFill(Color.BLUE);
        player.getGameObject().setX(50);
        player.getGameObject().setY(50);
        
        
        
        root.getChildren().add(player.getGameObject());
        System.out.println(root.getHeight());
        Scene scene = new Scene(root, root.getPrefWidth(), root.getPrefHeight());
        scene.setFill(Color.AQUAMARINE);
        primaryStage.setTitle("Testbrett");
        primaryStage.setScene(scene);
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
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}

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
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 *
 * @author
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
        Button btn = new Button();
        btn.setText("Say 'Hello World'");
        btn.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Hello World!");
            }
        });
        
        
        root.getChildren().add(player.getGameObject());
        System.out.println(root.getHeight());
        Scene scene = new Scene(root, root.getPrefWidth(), root.getPrefHeight());
        scene.setFill(Color.AQUAMARINE);
        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}

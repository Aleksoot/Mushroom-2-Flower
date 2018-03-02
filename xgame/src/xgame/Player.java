/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xgame;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import static xgame.GameObject.Type.PLAYER;

/**
 *
 * @author faete
 */
public class Player extends GameObject{
    
    Type player = Type.PLAYER;
    private boolean alive = true;
    
    
    public Player() {
       super();
    }
//    public Rectangle getPlayer(){
//        super();
//    }
    public void moveLeft(){
//    player moves left with "LEFT" directional input from keyboard
    }
    public void moveRight(){
//    player moves right with "RIGHT" directional input from keyboard 
    }
    public void moveJump(){
//   player jumps with "UP" directional input from keyboard
    }
}

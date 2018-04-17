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
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Date;
import javax.swing.Timer;

/** test
 *
 * @author josef
 */
public class Player extends GameObject{
    
    /**
     * Type.PLAYER comes from the enum in GameObject
     * xPos & yPos defined for the player object
     * player is "alive" (which implies properties for alive objects)
     */
    
    public int xPos;  
    public int yPos;
    public int count;
    Type player = Type.PLAYER;
    private boolean alive = true;
    private boolean collisionx;
    private boolean collisiony;
    
    /**
     * Inheriting the necessary parameters from GameObject (specifically setX/setY)
     * Defining the x and y Positions at the start of movement.
     */
    
    public Player() {
       super();
       xPos = 200;
       yPos = 200;

    }
    
    /**
     * @param xPos 
     * Obtaining the positions for the x and y values 
     * This allows the movement methods to function
     */
    
    
    public void getX (int xPos){
        this.xPos = xPos;
    }
    
    public void getY (int yPos) {
        this.yPos = yPos;
    }
    
    public int setX (int xPos) {
        return xPos;
    }
    
    public int setY (int yPos) {
        return yPos;
    }

//    public Rectangle getPlayer(){
//        super();
//    }
    
    /**
     * movement methods that allow the player object to move
     * X direction for horizontal movement (tied with "A" and "D")
     * Jump for temporarily moving in the vertical direction (using "SPACE")
     */
    
    public void moveLeft(){
        tile.setX(xPos-=30);
    }
    public void moveRight(){
        tile.setX(xPos+=30);
    }
    public void moveJump() {
        tile.setY(yPos-=60);
            
        /**
         * Since jumping is only a temporary movement in the y direction,
         * I used a timer to allow a brief amount of time in the air.
         * Control is also possible while airborne.
         * "500" means 500 milliseconds in the air before you return back to the ground.
         */
        
        new java.util.Timer().schedule( 
        new java.util.TimerTask() {
            @Override
            public void run() {
                tile.setY(yPos+=60);
            }
        }, 
        500 
        );
    }
}

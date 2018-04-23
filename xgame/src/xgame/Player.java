/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xgame;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Date;
import java.util.List;
import javafx.animation.AnimationTimer;
import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import static javafx.application.Platform.exit;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.ImagePattern;
import javafx.util.Duration;
import javax.imageio.ImageIO;
import javax.swing.Timer;
import xgame.Tile.Type;

/** player
 *
 * @author josef
 */
public class Player extends GameObject{
    
    /**
     * Type.PLAYER comes from the enum in GameObject
     * xPos & yPos defined for the player object
     * player is "alive" (which implies properties for alive objects)
     */
    File resourcesDirectory = new File("src/xgame");

    public int i;
    boolean jumping;
    private boolean alive = true;
    boolean collisionXr = false;
    boolean collisionXl = false;
    boolean collisionX = false;
    boolean collisiony = false;
    boolean movingRight = false;
    boolean movingLeft = false;
    boolean facingRight = false;
    boolean facingLeft = false;
    boolean falling = true;
    
    
    /**
     * Inheriting the necessary parameters from GameObject (specifically setX/setY)
     * Defining the x and y Positions at the start of movement.
     * Text confirming the player object's existence
     * Gave the player an Id of 1
     */
    
    public Player() {
       super();
      
       System.out.println("Player has spawned");
       id = 1; 
    }
    
    public boolean facingRight(){
        return this.facingRight;
    }
    public void setFacingRight(boolean x){
        this.facingRight = x;
    }
    public boolean facingLeft(){
        return this.facingLeft;
    }
    public void setFacingLeft(boolean x){
        this.facingLeft = x;
    }
    public boolean movingRight(){
        return this.movingRight;
    }
    public void setMovingRight(boolean s){
        this.movingRight = s;
    }
    public boolean movingLeft(){
        return this.movingLeft;
    }
    public void setMovingLeft(boolean s){
        this.movingLeft = s;
    }
    public boolean isFalling() {
        return falling;
    }

    public void setFalling(boolean falling) {
        this.falling = falling;
    }
    public boolean isJumping() {
        return jumping;
    }

    public void setJumping(boolean jumping) {
        this.jumping = jumping;
    }
    //Collisions x-axis
    public void setCollidingX(boolean collision){
        this.collisionX = collision;
    }
    public boolean getCollidingX(){
        return this.collisionX;
    }
    public void setCollidingXr(boolean collision){
        this.collisionXr = collision;
    }
    public boolean getCollidingXr(){
        return this.collisionXr;
    }
    public void setCollidingXl(boolean collision){
        this.collisionXl = collision;
    }
    public boolean getCollidingXl(){
        return this.collisionXl;
    }
    //Collisions y-axis
    public void setCollidingY(boolean collision){
        this.collisiony = collision;
    }
    
    public boolean getCollidingY(){
        return this.collisiony;
    }
    public void fall(){
        if(!collisiony && falling){
            this.getGameObject().setY(this.getGameObject().getY()+3);
        }else if(jumping){
            this.getGameObject().setY(this.getGameObject().getY()-10);
        }
    }

    
    /**
     * movement methods that allow the player object to move
     * X direction for horizontal movement (tied with "A" and "D")
     * Jump for temporarily moving in the vertical direction (using "SPACE")
     */
    
    public void moveLeft(){
        if(collisionXl){
            this.getGameObject().setX(this.getGameObject().getX());
        }else{
            this.facingLeft = true;
            this.facingRight = false;
            this.getGameObject().setX(this.getGameObject().getX()-3);
        }
    }
    public void moveRight(){
        
      if(collisionXr){
          this.getGameObject().setX(this.getGameObject().getX());
        }else{
          this.facingLeft = false;
            this.facingRight = true;
            this.getGameObject().setX(this.getGameObject().getX()+3);
        }
    }
 
    public void jump(){
        if(!falling){
            this.getGameObject().setY(this.getGameObject().getY()-90);
        }   
    }
    public void stopX(){
        this.getGameObject().setX(this.getGameObject().getX());
    }
    
    public void colliding(List<Tile> leveltiles, Type type ){
        
        double playerSize = this.getTilesize();
        double xPos = this.getGameObject().getX();
        double xPosMin = xPos - playerSize;
        double yPos = this.getGameObject().getY();
        double yPosMin = yPos - playerSize;
        
        for (Tile tile : leveltiles) {
            
            if( tile.getType() == type){
                double tileY = tile.getGameObject().getY();
                double tileYmin = tileY - playerSize;
                double tileX = tile.getGameObject().getX();
                double tileXmin = tileX - playerSize;
               
       
                //Collision right of player
                if( yPos-1 >= tileYmin && yPosMin+1 <= tileY && xPos == tileXmin){
                    tile.getGameObject().applyCss();
                    //tile.getGameObject().setFill(Color.RED);
                    this.collisionXr = true;
                    this.movingRight = false; 
                }else{
                    this.collisionXr = false;
                } 
                //Collision left of player
                if( yPos-1 >= tileYmin && yPosMin+1 <= tileY && xPosMin == tileX){
                    //tile.getGameObject().setFill(Color.GREEN);
                    this.collisionXl = true;
                    this.movingLeft = false;
               }else{
                    this.collisionXl = false;
                }
                
                //Collision under player 
                if( yPos == tileYmin && xPos >= tileXmin+1 && xPosMin <= tileX-1 ){  
                    //tile.getGameObject().setFill(Color.RED);
                    this.falling = false;
                    
                } 
                if( yPosMin == tileY && xPos >= tileXmin+1 && xPosMin <= tileX-1 ){  
                    tile.getGameObject().setFill(Color.YELLOWGREEN);
                    
                    this.falling = true;
                    
                }
            }
        }
}
    

    
}

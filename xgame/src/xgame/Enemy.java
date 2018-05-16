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
/**
 * 
 * @author josef
 */
/** Class: Enemy 
 * This class allows for the enemies to be created.
 * This class inherits specific properties from the GameObject class
 * for easier implementation into the main (xGame) class.
 * Created enemies serve as obstacles for the player as they can attack, injure and defeat the player.
 */
 /**
  * Booleans will be used for implementing certain methods or actions once an
  * enemy is created in the main xGame class.
  */
public class Enemy extends GameObject{
    
    
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
    
    
    /** Constructor for Enemy class
     * Inheriting the variables and methods from GameObject()
     * Since enemies are rectangles with more functionality and possibilities
     * Many of the methods inherit from the rectangles in the GameObject() class
     * for easier implementation and definition.
     */
    
    public Enemy() {
       super();   
    }
    /**
     * Method: facingRight
     * @return returns the current boolean for an enemy, facing right.
     * Facing right is when the object is facing towards the east.
     */
    public boolean facingRight(){
        return this.facingRight;
    }
    /**
     * Method: setFacingRight
     * @param x set if facingRight is true or false
     * Set to true if you want an enemy to be facingRight
     */
    public void setFacingRight(boolean x){
        this.facingRight = x;
    }
    /**
     * Method: facingLeft
     * @return returns the current boolean for an enemy, facing left.
     * Facing left is when the object is facing towards the west.
     */
    public boolean facingLeft(){
        return this.facingLeft;
    }
    /**
     * Method: setFacingLeft
     * @param x set if facingRight is true or false
     * Set to true if you want an enemy to be facingLeft
     */
    public void setFacingLeft(boolean x){
        this.facingLeft = x;
    }
    /**
     * Method: movingRight
     * @return returns the current boolean for an enemy, moving right.
     * Moving Right is when the object is moving towards the east.
     */
    public boolean movingRight(){
        return this.movingRight;
    }
    /**
     * Method: setMovingRight
     * @param s set if movingRight is true or false
     * Set to true if you want an enemy to be setMovingRight
     */
    public void setMovingRight(boolean s){
        this.movingRight = s;
    }
    /**
     * Method: movingLeft
     * @return returns the current boolean for an enemy, moving left.
     * Set to true if you want an enemy to be movingLeft
     * Moving Left is when the object is moving towards the west.
     */
    public boolean movingLeft(){
        return this.movingLeft;
    }
    /**
     * Method: setMovingLeft
     * @param s set if movingLeft is true or false
     * Set to true if you want an enemy to be setMovingLeft
     */
    public void setMovingLeft(boolean s){
        this.movingLeft = s;
    }
    /**
     * Method: isFalling
     * @return returns the current boolean for an enemy, if falling.
     * Falling is when the specified object is falling downwards.
     */
    public boolean isFalling() {
        return falling;
    }
    /** 
     * Method: setFalling
     * @param falling set if isFalling is true or false 
     * Set to true if you want an enemy to be setFalling
     */
    public void setFalling(boolean falling) {
        this.falling = falling;
    }
    
    /**
     * Method: setCollidingX
     * @param collision set if setCollidingX is true or false
     * This method is for setting collisions along the x-axis
     */
    public void setCollidingX(boolean collision){
        this.collisionX = collision;
    }
    /**
     * Method: getCollidingX
     * @return returns whether or not collisions are along the x-axis
     * for an enemy are true or false
     */
    public boolean getCollidingX(){
        return this.collisionX;
    }
    /**
     * * Method: setCollidingXr
     * @param collision set if setCollidingXr is true or false
     * This method is for setting collisions along the x-axis from the direction "right"
     */
    public void setCollidingXr(boolean collision){
        this.collisionXr = collision;
    }
    /**
     * Method: getCollidingXr
     * @return returns whether or not collisions are along the x-axis from the "right" direction
     * for an enemy are true or false
     */
    public boolean getCollidingXr(){
        return this.collisionXr;
    }
    /**
     * Method: setCollidingXl
     * @param collision set if setCollidingXl is true or false
     * This method is for setting collisions along the x-axis from the direction "left"
     */
    public void setCollidingXl(boolean collision){
        this.collisionXl = collision;
    }
    /**
     * Method: getCollidingXl
     * @return returns whether or not collisions are along the x-axis from the "left" direction
     * for an enemy are true or false
     */
    public boolean getCollidingXl(){
        return this.collisionXl;
    }
    /**
     * Method: setCollidingY
     * @param collision set if setCollidingY is true or false
     * This method is for setting collisions along the y-axis
     */
    public void setCollidingY(boolean collision){
        this.collisiony = collision;
    }
    /**
     * Method: getCollidingY
     * @return returns whether or not collisions are along the y-axis
     * for an enemy are true or false
     */
    public boolean getCollidingY(){
        return this.collisiony;
    }
    /**
     * Method: fall
     * If nothing is colliding with the object & the object is falling,
     * Have the object fall by calling its dimensions (inside GameObject)
     * and have it fall 3 units downwards (+3)(Y increases as you go downwards)
     */
    public void fall(){
        if(!collisiony && falling){
            this.getGameObject().setY(this.getGameObject().getY()+3);
        }
    }

    
    /**
     * Method: move
     * movement methods that allow the enemy object to move (automatic input) 
     */
    public void move(){
        this.getGameObject().setX(this.getGameObject().getX()+2);
    }
    /**
     * Method: moveLeft
     * If there is collision from the left on the x-axis, the object will not
     * move towards the left.
     * Normally, the object will move 3 units left.
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
    /**
     * Method: moveRight
     * If there is collision from the right on the x-axis, the object will not
     * move towards the right.
     * Normally, the object will move 3 units right.
     */
    public void moveRight(){
        
      if(collisionXr){
          this.getGameObject().setX(this.getGameObject().getX());
        }else{
          this.facingLeft = false;
            this.facingRight = true;
            this.getGameObject().setX(this.getGameObject().getX()+3);
        }
    }
    /**
     * Method: stopX
     * When the object is stopped, the setX and getX will be the same
     * since the object did not move in ant direction.
     */
    public void stopX(){
        this.getGameObject().setX(this.getGameObject().getX());
    }
    
    public void colliding(List<Tile> leveltiles, Type type ){
        
        double enemySize = this.getTilesize();
        double xPos = this.getGameObject().getX();
        double xPosMin = xPos - enemySize;
        double yPos = this.getGameObject().getY();
        double yPosMin = yPos - enemySize;
        
        for (Tile tile : leveltiles) {
            
            if( tile.getType() == type){
                double tileY = tile.getGameObject().getY();
                double tileYmin = tileY - enemySize;
                double tileX = tile.getGameObject().getX();
                double tileXmin = tileX - enemySize;
               
       
                //Collision right of enemy
                if( yPos-1 >= tileYmin && yPosMin+1 <= tileY && xPos == tileXmin){
                    tile.getGameObject().applyCss();
                    //tile.getGameObject().setFill(Color.RED);
                    this.collisionXr = true;
                    this.movingRight = false; 
                }else{
                    this.collisionXr = false;
                } 
                //Collision left of enemy
                if( yPos-1 >= tileYmin && yPosMin+1 <= tileY && xPosMin == tileX){
                    //tile.getGameObject().setFill(Color.GREEN);
                    this.collisionXl = true;
                    this.movingLeft = false;
               }else{
                    this.collisionXl = false;
                }
                
                //Collision under enemy 
                if( yPos == tileYmin && xPos >= tileXmin+1 && xPosMin <= tileX-1 ){  
                    //tile.getGameObject().setFill(Color.RED);
                    this.falling = false;
                    this.collisiony = false;
                } 
                if( yPosMin == tileY && xPos >= tileXmin+1 && xPosMin <= tileX-1 ){  
                    tile.getGameObject().setFill(Color.YELLOWGREEN);
                    
                    this.falling = true;
                   
                }
            }
        }
}
    

    
}

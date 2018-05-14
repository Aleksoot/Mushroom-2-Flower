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
import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.animation.Transition;
import static javafx.application.Platform.exit;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Bounds;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.ImagePattern;
import javafx.util.Duration;
import javax.imageio.ImageIO;
import xgame.Tile.Type;
import static xgame.Tile.Type.end;

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
String os = System.getProperty("os.name").toLowerCase();
    
    boolean jumping;
    
    public int level = 0;
    public int score = 0;
    public int playerHealth = 100;
    public boolean jumpingTick=false;
    private boolean alive = true;
    boolean collisionXr, collisionXl, collisionYo, collisionYu;
    boolean stopY=false;
    
    boolean movingRight, movingLeft, facingRight=true, facingLeft;
    boolean falling = true;
    public ImageView sprite;
    
    
    double tilesize = this.getTilesize();
    //Outer hitbox
    public Rectangle hitTop = new Rectangle();
    public Rectangle hitRight = new Rectangle();
    public Rectangle hitDown = new Rectangle();
    public Rectangle hitLeft = new Rectangle();
    //Inner hitbox
    public Rectangle playerBox = new Rectangle();
    public double scale = this.getScale();
    public double playerBoxSize = 0.666*tilesize;
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
   
    public Rectangle playerBox(){
        return this.playerBox;
    }
    public Rectangle hitTop(){
        return this.hitTop;
    }
    public Rectangle hitDown(){
        return this.hitDown;
    }
    public Rectangle hitLeft(){
        return this.hitLeft;
    }
    public Rectangle hitRight(){
        return this.hitRight;
    }
    public int getScore(){
        return this.score;
    }
    public void addScore(int increment){
        this.score += increment;
    }
    public void setLevel(int i){
        this.level = i;
    }
    public int getLevel(){
        return this.level;
    }
    public void JumpTick(boolean tick){
        this.jumpingTick = tick;
    }
    public boolean isAlive(){
        return this.alive;
    }
    public ImageView getSprite(){
        return this.sprite;
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
    public void setCollidingYo(boolean collision){
        this.collisionYo = collision;
    }
    
    public boolean getCollidingYo(){
        return this.collisionYo;
    }
     public void setCollidingYu(boolean collision){
        this.collisionYu = collision;
    }
    
    public boolean getCollidingYu(){
        return this.collisionYu;
    }
    public void fall(){
        if(!this.collisionYu ){
            this.getGameObject().setY(this.getGameObject().getY()+(3*scale));
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
            this.getGameObject().setX(this.getGameObject().getX()-(3*scale));
        }
    }
    public void moveRight(){
        
      if(collisionXr){
          this.getGameObject().setX(this.getGameObject().getX());
        }else{
          this.facingLeft = false;
            this.facingRight = true;
            this.getGameObject().setX(this.getGameObject().getX()+(3*scale));
        }
    }
 
    public void jump(){
        if(!falling){
            this.getGameObject().setY(this.getGameObject().getY()-(90*scale));
        } 
        
    }
    


    public void colliding(List<Tile> leveltiles, Type type ){
        System.out.println("scale: "+scale);
        this.hitTop.setX(this.getGameObject().getX()+2);
        this.hitTop.setY(this.getGameObject().getY()-2);
        this.hitTop.setWidth(tilesize-4);
        this.hitTop.setHeight(1);
        
        this.hitDown.setX(this.getGameObject().getX()+2);
        this.hitDown.setY(this.getGameObject().getY()+tilesize);
        this.hitDown.setWidth(tilesize-4);
        this.hitDown.setHeight(1);
        
        this.hitLeft.setX(this.getGameObject().getX());
        this.hitLeft.setY(this.getGameObject().getY()+2);
        this.hitLeft.setWidth(1);
        this.hitLeft.setHeight(tilesize-4);
        
        this.hitRight.setX(this.getGameObject().getX()+tilesize);
        this.hitRight.setY(this.getGameObject().getY()+2);
        this.hitRight.setWidth(1);
        this.hitRight.setHeight(tilesize-4);
        
        this.playerBox.setX(this.getGameObject().getX() + (playerBoxSize / 4));
        
        this.playerBox.setY(this.getGameObject().getY() + (playerBoxSize / 4));
        this.playerBox.setWidth(playerBoxSize);
        this.playerBox.setHeight(playerBoxSize);
        playerBox.setFill(Color.RED);
        
        
        
        double playerSize = this.getTilesize();
        double xPos = this.getGameObject().getX();
        double xPosMin = xPos - playerSize;
        double yPos = this.getGameObject().getY();
        double yPosMin = yPos - playerSize;
        
        for (Tile tile : leveltiles) {
            
            if(colliding( tile.getGameObject(), hitDown() ) && colliding( tile.getGameObject(), hitTop() ) && this.jumping){
                this.fall();
            }
            if( tile.getType() == type){
                
                double tileY = tile.getGameObject().getY();
                double tileYmin = tileY - playerSize;
                double tileX = tile.getGameObject().getX();
                double tileXmin = tileX - playerSize;
               
                
                //Under
                if( !colliding( tile.getGameObject(), hitDown() )  && !stopY ){
                    this.collisionYu = false;
                    
                }else{
                    
                    this.stopY = true;
                    this.collisionYu = true;
                    
                }
                //over
                if( !colliding( tile.getGameObject(), hitTop() ) ){
                    
                   this.collisionYo = false;
                }else{
                   
                    
                    this.collisionYo = true;
                   
                    
                }
                if( colliding( tile.getGameObject(), playerBox() ) ){
                    this.stopY = false;
                    this.fall();
                }
                //right
                if( !colliding( tile.getGameObject(), hitRight() ) ){
                    
                   this.collisionXr = false;
                   
                }else{
                    this.movingRight = false; 
                    this.collisionXr = true;
                }
                 //left
                if( !colliding( tile.getGameObject(), hitLeft() ) ){
                    
                   this.collisionXl = false;
                   
                }else{
                    this.movingLeft = false; 
                    this.collisionXl = true;
                }
//                //Collision right of player
//                if( yPos-1 >= tileYmin && yPosMin+1 <= tileY && xPos == tileXmin){
//                    
//                    //tile.getGameObject().setFill(Color.RED);
//                    this.collisionXr = true;
//                    this.movingRight = false; 
//                }else{
//                    this.collisionXr = false;
//                } 
                //Collision left of player
//                if( yPos-1 >= tileYmin && yPosMin+1 <= tileY && xPosMin == tileX){
//                    //tile.getGameObject().setFill(Color.GREEN);
//                    this.collisionXl = true;
//                    this.movingLeft = false;
//               }else{
//                    this.collisionXl = false;
//                }
//                System.out.println("tileY: "+yPos);
//                System.out.println("tileYmin: "+yPosMin);
                //Collision under player 
//                if(  xPos >= tileXmin+1 && xPosMin <= tileX-1 ){  
//                    if(yPos == tileYmin){
////                    tile.getGameObject().setFill(Color.RED);
//                    this.falling = false;
//                    this.collisionYu = true;
//                    }
//                    if(yPos > tileYmin ){this.collisionYu = false;}
//                }
//                //Collision over player 
//                if( xPos >= tileXmin+1 && xPosMin <= tileX-1 ){  
//                    if(yPosMin != tileY){
//                        this.collisionYo = false;
//                    }
//                    else{
//                    //tile.getGameObject().setFill(Color.YELLOWGREEN);
//                    this.collisionYo = true;
//                    this.falling = true;
////                    this.jumping = false;
//                    }
//                }
                    
            }
        }
}
    public boolean collideObject(List<Rectangle> rectangles){
        for (Rectangle object : rectangles) {
            if(this.getGameObject().getBoundsInParent().intersects(object.getBoundsInParent())){
                return true;
            }
        }
        return false;
    }
    public boolean colliding(Rectangle rect1, Rectangle rect2){
        if(rect1.getBoundsInParent().intersects(rect2.getBoundsInParent())){
                return true;
            }
        return false;
    }
    public void checkAlive(){
        if(this.playerHealth > 0){
            this.alive = true;
        }else{
            this.alive = false;
        }
        
    }
    
    public int getHealth(){
        return this.playerHealth;
    }
    public void changeHealth(double increment){
        this.playerHealth += increment;
    }
     
    
    public void reset(){
        this.collisionXr = false;
        this.collisionXl = false;
    this.collisionYo = false;
    this.collisionYu=false;
    this.movingRight = false;
    this.movingLeft = false;
    this.facingRight = false;
    this.facingLeft = false;
    this.falling = false;
    }
    
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xgame;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.util.List;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import static javax.swing.Spring.height;

/**
 * @author josef
 */
public class GameObject {
    public Rectangle tile = new Rectangle();
    public int id;
    public int tiles=30;
    public double tilesize=0;
    public double scale=1;
    public Image bg;
    public Node node;
    
    GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
   int screenHeight=0;
    public GameObject(){
        setScale();
        setTileSize();
        tile.setHeight(tilesize);
        tile.setWidth(tilesize);
        
       
    }
    public Rectangle getGameObject(){
        return this.tile;
    }
    public void fillGameObject(Image x){
        
        this.tile.setFill(new ImagePattern(x));
     
    }
    public void setTileSize(){
        screenHeight = gd.getDisplayMode().getHeight();
        if(screenHeight < 1080){
            this.scale = 0.666;
            this.tilesize = 30*this.scale;
        }else{
            this.scale = 1;
            this.tilesize = 30;
        }
    }
    public boolean isColliding(GameObject other) {
        return getGameObject().getBoundsInParent().intersects(other.getGameObject().getBoundsInParent());
    }
    public void setScale(){
        if(this.tilesize < 30){
            this.scale = 0.666;
        }else{
            this.scale=1;
        }
        
    }
    public double getScale(){
        return this.scale;
    }
    public int getTiles() {
        return tiles;
    }
    public double getTilesize() {
        return this.tilesize;
    }
    public void setTilesize(int tiles) {
        this.tilesize = tiles;
    }

     public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public void GameObjectToScene(Pane root, double x, double y){
        this.tile.setX(x - this.tilesize );
        this.tile.setY(y - this.tilesize );
        root.getChildren().add( this.tile );
    }
    public boolean colliding(Rectangle first, Rectangle second ){
        return first.getBoundsInParent().intersects(second.getBoundsInParent());
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xgame;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import static javax.swing.Spring.height;

/**
 *
 * @author josef
 */
public class GameObject {
    public Rectangle tile = new Rectangle();
    public int id;
    public int tilesize=30;
    public Image bg;
    public Node node;
    public GameObject(){
        tile.setHeight(tilesize);
        tile.setWidth(tilesize);
 
        //System.out.println("new gameobject");
    }
    public Rectangle getGameObject(){
        return this.tile;
    }
    public void fillGameObject(Image x){
        
        this.tile.setFill(new ImagePattern(x));
     
    }
    public boolean isColliding(GameObject other) {
        return getGameObject().getBoundsInParent().intersects(other.getGameObject().getBoundsInParent());
    }

    public int getTilesize() {
        return tilesize;
    }

    public void setTilesize(int tilesize) {
        this.tilesize = tilesize;
    }
    
//    public enum Type {
//    PLAYER,
//    ENEMY,
//    LEVEL,
//    ITEM	
//    }
     public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public void GameObjectToScene(Pane root, double x, double y){
        this.tile.setTranslateX(x - this.tilesize );
        this.tile.setTranslateY(y - this.tilesize );
        root.getChildren().add( this.tile );
    }
}

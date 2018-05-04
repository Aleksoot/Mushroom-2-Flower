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

/**
 *
 * @author faete
 */
public class Tile extends GameObject{
        
    public Tile(){
        super();
        
    }
  
    public Type type = Type.empty; //DEFAULT 

    public enum Type {
        player,empty,solid,spawn,enemy,loot;
    }
    public Type getType(){
        return this.type;
    }
    public void setType(Type type){
        this.type = type;
    }
    public Rectangle getTile(){
        return this.tile;
    }
    
    public void TileToScene(Pane root, double x, double y){
        this.tile.setTranslateX(x - this.tilesize );
        
        this.tile.setTranslateX(x - this.tilesize );
         
        root.getChildren().add( this.tile );
    }
}

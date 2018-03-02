/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xgame;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
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
    
    public Rectangle getTile(){
        return this.tile;
    }
    public void TileToScene(Pane root, double x, double y){
        this.tile.setTranslateX(x - this.tilesize );
        this.tile.setTranslateX(x - this.tilesize );
        root.getChildren().add( this.tile );
    }
}

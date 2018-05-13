package xgame;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author faete Josef
 */
/** Class: public class Tile extends GameObject
 *  Tile is another GameObject that has its own properties outside of the GameObject class
 *  A tile is a "sqaure" on the gameboard.
 *  These squares are divided into 32x32 pixel areas.
 *  Each of these areas has an property based on the type of tile that is set on the x and y pixel values.
 */
public class Tile extends GameObject{
    /** Method: public Tile()
     * Inherits all the properties and variables from the parent class (gameObjects).
     */    
    public Tile(){
        super();
        
    }
    /**
     * "type" starts as empty since none of the tiles are defined until the gameboard is created and read.
     */
    public Type type = Type.empty; //DEFAULT 
    /**
     * The different types of tile type corresponding to what is at that 32x32 peice of the board.
     * each type in this enum has different properties which behave differntly on the board and when things collide.
    */
    public enum Type {
        empty,solid,spawn,enemy,end;
    }
    /**
     * @return returns the type of tile for the pixel values so the gameboard knows the properties of that tile.
    */
    public Type getType(){
        return this.type;
    }
    /**
    * based on Type selected in the enum, the type will be set for that pixel value.
    */
    public void setType(Type type){
        this.type = type;
    }
    /**
     * @return the tile is finally retrieved once its type are set and getted. 
     */
    public Rectangle getTile(){
        return this.tile;
    }

}

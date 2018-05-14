/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xgame;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.awt.image.PixelGrabber;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static javafx.application.Platform.exit;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javax.imageio.ImageIO;
import xgame.Tile.Type;


/**
 *
 * @author faete Josef
 */
/** Class: level
 * This class creates levels inside the gameboard.
 * It does this by creating a new pane for the gameboard.
 * It also creates a leveltiles list that stores an array 
 * containing the tile ids for every 30 x 30 tile (tilesize = 30)
 * level1 uses the array of tile ids to create a level based on where the ids are inside the array (from left to right)
 * each tile is defined as a rectangle with the given x and y sizes of the tilesize integer.
 */
public class Level {
    
    public Pane root = new Pane();
    private List<Tile> leveltiles = new ArrayList<>();
    private int[] level1;
    private double tilesize = 0;
    private int tiles = 30;
    GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
    int screenHeight=0;
    public Rectangle end = new Rectangle(tiles,tiles);
/**
 * the board are the borders for the level hence tilesize-1
 * on board creation, no images are assigned to it
 */   
    public GameObject[][] board = new GameObject[tiles-1][tiles-1];
    
    BufferedImage testbilde = null;
    Image image = null;
    
    public void setTileSize(){
        screenHeight = gd.getDisplayMode().getHeight();
        if(screenHeight < 1080){
            this.tilesize = 30*0.666;
        }else{
            this.tilesize = 30.0;
        }
       
    }
    
    /**
     * @return returns the root where anything can be placed on this specific pane
     * can be used to apply the pane to a specific created level
     */
    public Pane getRoot() {
        return root;
    }
    /** Method: public Pane createLevel(int[] level)
     * @param level , this is the level value that when called, will initiate a level given the correct 
     * level value (eg. 1 or 2)
     * This method creates the level.
     * the level is created based on the size of the pane "root" 
     * (which is then given the correct tilesize so the whole pane's dimensions can be calculated)
     * gbValue is the level id value
     * Image[] bilder retrieves and image and is given the total possible ids for the tiles
     * bilder gets those specific tile ids
     * count starts at 0 since no level int has been given yet
     * @return returns the root pane that the level is created in
     */
    public Pane createLevel(int[] level){
         setTileSize();
        root.setPrefSize(tilesize*tiles, tiles*tilesize);
        int gbValue = 0;
        Image[] bilder = new Image[768];
        bilder = getSprites(768);
        int count = 0; 

        for(int i=0; i < tiles; i++){
            
           for(int j=0; j < tiles; j++ ){
                
                if(level[count]>0){
              
                    gbValue = level[count]-1;
                }else{ gbValue=65;}
                
                testbilde = SwingFXUtils.fromFXImage(bilder[gbValue], null);
                image = SwingFXUtils.toFXImage(testbilde, null);
               
                Tile tile = new Tile();
                /**
                 * This means that all tile ids defined in the gameboard that meet the if statement 
                 * will be defined as solid objects 
                 * ids 0,65 & 567 are reserved
                 */
                tile.setId( gbValue );
                int co = 0;
                if(gbValue > 0 && gbValue != 65 && gbValue != 567){ 
                    tile.setType(Type.solid);
                }
                
                
                if(gbValue != 65){
                tile.fillGameObject(image);
                }
                else{tile.getGameObject().setFill(Color.TRANSPARENT);}
                /**
                 * Once the ids are all read (up to 565) a message confirming this will display.
                 * Afterwards, its get the end values for the x and y positions
                 */
               
                tile.getGameObject().setX(j*tilesize);
                tile.getGameObject().setY(i*tilesize);
                if( gbValue == 565){
                    System.out.println("created end");
                    end.setX(tile.getGameObject().getX());
                    end.setY(tile.getGameObject().getY());
                }
                /**
                 * Here, it adds the tiles to the level
                 * It also inherits the tile properties from GameObject class 
                 * for every tile added, the counter counts up.
                 */
                leveltiles.add(tile);
                root.getChildren().add(tile.getGameObject());
                
                count++;  
                
           }     
        }
        System.out.println("created level");
        return root;
    }
    public Rectangle getEnd(){
        return this.end;
    }
    public List getLevelTiles(){
        return this.leveltiles;
    }
    public void removeLevelTile(Tile tile){
        leveltiles.remove(tile);
    }
    public int[] level_1(){
        return this.gameboard_1;
    }
    public int[] level_2(){
        return this.gameboard_2;
    }
    /** Method: public Image[] getSprites(int antall)
     * This method is able to retrieve the image file needed to display certain sprites (images)
     * Based on the id that they were read from.
     * @param antall , the number of sprites that will be read from the image
     * @return returns the read and split image file into their own sprites to be used for the level
     */
    public Image[] getSprites(int antall) {
        BufferedImage source = null;
        File resourcesDirectory = new File("src/xgame");
        String src_slash;
        String os = System.getProperty("os.name").toLowerCase();
        if (os.indexOf("win") >= 0) {
            //if windows
            src_slash = "\\";
        
        }else{
            src_slash = "/";
        }
        /**
         * This is the image where the sprites come from
         * The .png file is read using an ImageIO (input/output) object that is able to read from a directory
         */
        try {
            source= (ImageIO.read(new File(resourcesDirectory.getAbsolutePath()+src_slash+"level.png")));
                
        } catch (IOException ex) {
            
        }
        /**
         * Having read the image file. It will now be seperating the squares 
         * by 32 pixels in the x direction and 32 pixels in the y direction
         */
        Image[] sprites = new Image[antall];
        Image sprite;
        int z = 0;
        
        for (int y = 0; y < source.getHeight()-32; y += 32) {
            
            for (int x = 0; x < source.getWidth(); x += 32) {
              
                    sprites[z] = SwingFXUtils.toFXImage(source.getSubimage(x, y, 32, 32), null);
                    z++;
              
            }
        }
        return sprites;
    }
/**
 * This is level 1
 * This is where the level is created from the ids inside the Image[] array
 * each id corresponds the a 32x32 rectangle of the read .png image
 * For example, ID 217 represents a solid well (the if statement approved id 217 as a solid object)
 * ID 217 also corresponds to a rectangle of dirt from the .png image
 * IDs that are 0 are empty spaces with no collision
 */
  int[] gameboard_1 = new int[]{
        217,217,217,217,217,217,217,217,217,217,217,217,217,217,217,217,217,217,217,217,217,217,217,217,217,217,217,217,217,217,
217,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,217,
217,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,566,217,
217,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,183,185,217,
217,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,217,
217,0,0,0,0,0,0,183,185,185,188,0,183,185,185,185,185,188,0,183,185,185,185,185,185,188,0,0,0,217,
217,0,183,185,185,188,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,217,
217,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,217,
217,185,185,185,185,185,188,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,217,
217,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,217,
217,0,0,0,0,0,0,0,183,185,188,0,0,0,0,0,0,0,152,0,0,0,0,0,0,0,0,0,0,217,
217,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,217,
217,0,0,0,0,0,0,0,0,0,0,183,185,185,188,0,0,0,0,0,0,0,0,0,0,0,0,0,0,217,
217,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,217,
217,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,183,185,185,185,185,185,185,188,0,0,0,0,0,217,
217,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,217,
217,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,183,185,188,0,217,
217,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,217,
217,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,183,185,185,185,185,188,0,0,0,0,217,
217,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,217,
217,0,0,0,0,0,0,0,0,0,0,0,183,185,185,185,185,188,0,0,0,0,0,0,0,0,0,0,0,217,
217,0,0,0,0,0,0,0,0,0,152,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,217,
217,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,217,
217,0,0,152,0,0,183,185,185,185,185,185,188,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,217,
217,152,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,217,
217,0,0,0,0,0,0,0,0,0,0,0,0,183,185,185,185,185,188,0,0,0,0,0,0,0,0,0,0,217,
217,0,152,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,217,
217,0,0,0,152,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,183,185,185,185,185,185,185,188,0,217,
217,567,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,217,
217,185,185,185,185,185,185,185,185,185,185,185,185,185,185,185,185,185,185,185,185,185,185,185,185,185,185,185,185,217
};
 /**
  * This level works the same way as the 1st level does
  * IDs representing each solid and empty space inside the pane
  */
int[] gameboard_2 = new int[]{
    217,217,217,217,217,217,217,217,217,217,217,217,217,217,217,217,217,217,217,217,217,217,217,217,217,217,217,217,217,217,
217,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,217,
217,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,217,
217,566,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,217,
217,185,185,188,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,217,
217,0,0,0,0,0,183,185,185,185,185,185,185,185,185,188,0,0,0,0,0,0,0,0,0,0,0,0,0,217,
217,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,183,185,185,185,185,185,188,0,0,0,0,0,217,
217,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,217,
217,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,183,185,185,217,
217,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,217,
217,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,183,185,185,185,188,0,0,0,0,0,0,0,0,217,
217,0,0,0,0,0,0,0,183,185,185,185,185,188,0,0,0,0,0,0,0,0,183,185,185,188,0,0,0,217,
217,0,0,0,0,0,0,0,0,0,0,0,0,0,0,152,0,0,0,0,0,0,0,0,0,0,0,0,0,217,
217,0,183,185,185,188,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,217,
217,0,0,0,0,0,0,152,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,217,
217,0,0,0,0,0,0,0,0,0,0,0,0,0,0,183,185,185,185,188,0,0,0,0,0,0,0,0,0,217,
217,0,0,0,0,0,183,185,185,185,185,185,185,188,0,0,0,0,0,0,0,0,0,0,0,0,152,0,0,217,
217,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,183,185,185,188,0,0,0,0,217,
217,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,152,217,
217,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,217,
217,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,183,185,185,217,
217,0,0,0,0,0,0,0,0,183,185,185,185,188,0,183,185,185,185,188,0,0,0,0,0,0,0,0,0,217,
217,0,183,185,185,185,188,0,0,0,0,0,0,0,0,0,0,0,0,0,0,183,185,185,185,188,0,0,0,217,
217,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,217,
217,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,217,
217,0,0,0,0,183,185,185,185,188,0,0,0,0,0,0,0,0,0,0,152,0,0,0,0,0,0,0,0,217,
217,0,152,0,0,0,0,0,0,0,0,183,185,185,185,185,188,0,0,0,0,0,0,0,0,0,0,0,0,217,
217,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,183,185,185,188,0,0,0,0,0,0,217,
217,567,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,185,185,185,217,
217,185,185,185,185,185,185,185,185,185,185,185,185,185,185,185,185,185,185,185,185,185,185,185,185,185,217,217,217,217

};

}

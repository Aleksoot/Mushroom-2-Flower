/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xgame;

import java.awt.image.BufferedImage;
import java.awt.image.PixelGrabber;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
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
import xgame.GameObject.Type;

/**
 *
 * @author faete
 */
public class Level {
    
    public Pane root = new Pane();
    private List<GameObject> tiles = new ArrayList<>();
    Type level = Type.LEVEL;
    private int[] level1;
    private int tilesize = 30;
    Rectangle[][] test = new Rectangle[tilesize][tilesize];
   
    public GameObject[][] board = new GameObject[tilesize-1][tilesize-1];
    TilePane grid = new TilePane();
    BufferedImage testbilde = null;
    Image image = null;
    
    
    public Type getLevel() {
        return level;
    }

    public void setLevel(Type level) {
        this.level = level;
    }

    public Pane getRoot() {
        return root;
    }
    
    public Pane createLevel(){
        
        root.setPrefSize(tilesize*tilesize, tilesize*tilesize);
        int gbValue = 0;
        Image[] bilder = new Image[360];
        bilder = getSprites(360);
        int count = 0; 

        for(int i=0; i < tilesize; i++){
            
           for(int j=0; j < tilesize; j++ ){
                
                if(gameboard_1[count]>0){
              
                    gbValue = gameboard_1[count]-1;
                }else{ gbValue=65;}
                
                testbilde = SwingFXUtils.fromFXImage(bilder[gbValue], null);
                image = SwingFXUtils.toFXImage(testbilde, null);
               
                GameObject tile = new GameObject();
                
                tile.setId( gbValue );
                if(gbValue != 65){
                tile.fillGameObject(image);
                }else{tile.getGameObject().setFill(Color.TRANSPARENT);}
                
                tile.getGameObject().setX(j*tilesize);
                tile.getGameObject().setY(i*tilesize);
                
                root.getChildren().add(tile.getGameObject());
                count++;  
                
                
           }     
        }
        System.out.println("created level");
        return root;
    }
    
    public String level_1(){
        return "lvl1";
    }
    public static BufferedImage makeBufferedImage(BufferedImage i){
      BufferedImage result;
      PixelGrabber pg = new PixelGrabber(i, 0, 0, 1, 1, false);
      boolean alpha;

      if(i instanceof BufferedImage)
      {
         return((BufferedImage)i);
      }
      try
      {
         pg.grabPixels();
         alpha = pg.getColorModel().hasAlpha();
      }
      catch(InterruptedException e)
      {
         alpha = false;
      }

      result = new BufferedImage
      (
         i.getWidth(null),
         i.getHeight(null),
         alpha ? BufferedImage.TYPE_INT_ARGB : BufferedImage.TYPE_INT_RGB
      );
      result.getGraphics().drawImage(i,0,0,null);
      return(result);
   }
    public Image[] getSprites(int antall) {
        BufferedImage source = null;
        BufferedImage source2 = null;
        File resourcesDirectory = new File("src/xgame");
        String src_slash;
        String os = System.getProperty("os.name").toLowerCase();
        if (os.indexOf("win") >= 0) {
            //if windows
            src_slash = "\\";
        
        }else{
            src_slash = "/";
        }
        try {
            source2 = ImageIO.read(new File(resourcesDirectory.getAbsolutePath()+src_slash+"magecity.png"));
            source= makeBufferedImage(source2);
                
        } catch (IOException ex) {
            
        }
       
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

  int[] gameboard_1 = new int[]{
        49,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,51,
57,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,154,
57,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,334,0,334,0,334,0,162,
57,0,0,0,0,0,0,0,0,0,0,0,0,299,299,0,0,0,0,0,334,0,0,299,0,299,0,299,0,59,
57,0,0,0,0,0,0,0,0,0,299,299,0,0,0,0,299,299,0,0,299,0,0,0,0,0,0,0,0,59,
57,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,59,
57,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,59,
57,0,0,0,0,0,0,0,0,0,0,0,299,299,299,0,0,0,0,0,299,299,0,0,0,0,0,0,0,59,
57,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,59,
57,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,299,299,299,0,0,0,0,0,0,0,0,0,0,59,
57,0,0,0,0,0,0,299,299,299,299,0,0,0,0,0,0,0,0,0,0,0,299,299,299,0,0,0,0,59,
57,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,59,
57,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,59,
57,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,299,299,299,299,0,0,0,0,0,0,0,59,
57,0,0,0,0,299,299,299,299,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,59,
57,0,0,0,0,0,0,0,0,0,0,334,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,59,
57,0,0,0,0,0,0,0,0,0,0,299,0,0,299,299,299,0,0,0,0,0,0,0,0,0,0,0,0,59,
57,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,59,
57,0,0,0,0,0,0,0,0,0,0,0,0,334,0,0,299,299,299,0,0,0,0,0,0,0,0,0,0,59,
57,0,0,0,0,0,0,0,0,299,299,0,0,299,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,59,
57,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,59,
57,0,0,0,0,0,0,0,0,0,0,0,0,0,0,299,299,299,0,0,0,0,0,0,0,0,0,0,0,59,
57,0,0,0,299,299,299,299,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,59,
57,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,299,299,299,0,0,0,0,0,0,0,59,
57,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,59,
57,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,59,
57,0,0,0,0,0,0,0,299,299,299,299,299,299,299,0,0,0,0,0,0,0,0,0,0,0,0,0,0,59,
57,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,59,
57,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,59,
53,53,53,53,53,53,53,53,53,53,53,53,53,53,53,53,53,53,53,53,53,53,53,53,53,53,53,53,53,53};
    
}

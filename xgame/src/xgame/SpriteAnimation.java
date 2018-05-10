/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xgame;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 *
 * @author faete
 */
public class SpriteAnimation {
    String os = System.getProperty("os.name").toLowerCase();
    int sprites = 0;
    public BufferedImage visible = null;
    public List<BufferedImage> images = null;
    int controll = 0;
    
    public SpriteAnimation(List<BufferedImage> images) {
        
        this.sprites = images.size();
        this.images = images;
        this.visible = images.get(0);
    }
   
    public void changeFrame(boolean framechange){
        
        if(framechange && controll < sprites){
            visible = images.get(controll);
            controll++;
            
        }else if(controll == sprites){
            controll = 0;
            visible = images.get(controll);
        }
    }
    public ImagePattern getFrame(){
            return new ImagePattern(SwingFXUtils.toFXImage(visible, null ));
    }
    
    public String src(){
        
        String src_slash;
        if (os.indexOf("win") >= 0) {
            //if windows
            src_slash = "\\";
        
        }else{
            src_slash = "/";
        }
        return src_slash;
    }   
    
}

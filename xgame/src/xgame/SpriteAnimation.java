/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xgame;

import java.awt.image.BufferedImage;
import java.io.File;
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
    
    int sprites = 0;
    public BufferedImage visible = null;
    public List<BufferedImage> images = null;
    public boolean frameChanged = false;
    int controll = 0;
    
    public SpriteAnimation(List<BufferedImage> images) {
        
        this.sprites = images.size();
        this.images = images;
        this.visible = images.get(0);
    }
    public boolean setFrameChange(){
        return this.frameChanged;
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
            Image testr = SwingFXUtils.toFXImage(visible, null );
            return new ImagePattern(testr);
    }
    
    
}

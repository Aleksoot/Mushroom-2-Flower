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
/** Class: public class SpriteAnimation
 * This class allows the searching, creating & implementation of images/gifs
 * By routing to a directory, identifying images/gifs, storing them into an array
 * and finally using them upon the creation of an object with methods and the updating of frames.
 *
 * "String os" retrieving os name and converting to lower case for easier reading and identifying
 * "int sprites" starts at 0 but increases as new objects add new images or folders containing images
 * "public BufferedImage visible" as long as no object or image is created/retrieved, nothing will be visible
 * "public List<BufferedImage> images" This is the list of images from a certain path/name of file where 
 * the entire image set will be retrieved from, then animated using frame changing and an animation time
 * "controll" is an integer that keeps track of the number of sprites for change/modification. 
*/
public class SpriteAnimation {
    String os = System.getProperty("os.name").toLowerCase();
    int sprites = 0;
    public BufferedImage visible;
    public List<BufferedImage> images;
    int controll = 0;
    /* Method: public SpriteAnimation(List<BufferedImage> images)
     * A method that adds images (or a folder of images) to a list for the specified created object.
     * As the method get these images from the source directory,
     * It retrieves its dimensions and image identity.
     * Images are only visible when an object calles a getFrame method with the specified object with a valid path to the images.
    */
    public SpriteAnimation(List<BufferedImage> images) {
        
        this.sprites = images.size();
        this.images = images;
        this.visible = images.get(0);
    }
        /** Method: public void changeFrame(boolean framechange)
        * boolean framechange this is to check when a framechange is true for a created SpriteAnimation object.
        *This method allows the framechanger to work in conjunction with the animation timer
        *which updates the current image object's frame. 
        *If any changes are made via a called method,
        *then a frame change tied to the method will then take effect on the next frame.
        *The "if" statement is controll keeping track of the sprites.
        *When the sprites must be updated for the next frame, controll is then added on so it knows how many
        *sprite updates will occur for the next frame.
        *It resets itself to zero once the frame change requires no new sprites or changes. 
        */
    public void changeFrame(boolean framechange){
        
        if(framechange && controll < sprites){
            visible = images.get(controll);
            controll++;
            
        }else if(controll == sprites){
            controll = 0;
            visible = images.get(controll);
        }
    }
    /** Method: public ImagePattern getFrame()
    * This method uses a created SpriteAnimation object (image or gif)
    * Calling this method with a created object will use the image (or images in a specific folder)
    * will then get the selected "frame" based on the image's path.
    * @return this returns the current frame for an object 
    */
    public ImagePattern getFrame(){
            return new ImagePattern(SwingFXUtils.toFXImage(visible, null ));
    }
    /** Method: public String src()
    * Splitting the different pathing.
    * Windows amd Mac use different symbols to split each level of a directory
    * If statement detects Windows or Mac and will use the appropriate directory splitter
    * @return returns src_slash as a standard sting to indentify for directing to a path.
    */
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

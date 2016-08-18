package world;

import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class World {
	
	
	private ArrayList<BufferedImage> image_gallery;
	private ArrayList<Element> world_obj;
	private int selectedIndex;
	
	public World(){
		image_gallery = new ArrayList<BufferedImage>();
		world_obj = new ArrayList<Element>();
	}
	
	public ArrayList<BufferedImage> getGallery(){
		return image_gallery;
	}
	
	public ArrayList<Element> getWorld() {
		return world_obj;
	}
	
	/**
	 * Moves the selected object one 'set' closer to the user.
	 * @return If the operation was successful
	 */
	public boolean moveForward(){
		if(selectedIndex == 0)
			return false;
		Element e = world_obj.remove(selectedIndex);
		world_obj.add(--selectedIndex, e);
		return true;	
	}
	/**
	 * Returns the element at a give point in 2D space.
	 * @param x The horizontal co-ordinate
	 * @param y The vertical co-ordinate
	 * @return If an object was found and selected at the given co-ordinate.
	 */
	public boolean selectElementAt(int x, int y){
		for(Element e : world_obj){
			int width = e.getImage().getWidth();
			int height = e.getImage().getHeight();
			Point p = e.getLocation();
			
			
			if(p.getX() < x && 					//Horizontal minimum
					(p.getX() + width) > x &&	//Horizontal Maximum
					p.getY() < y &&				//Vertical Minimum
					(p.getY() + height) <= y){	//Vertical Maximum
				selectedIndex = world_obj.indexOf(e);
				return true;
			};
		}
		return false;
	}
	
	/**
	 * Moves the selected object one 'set' away from the user.
	 * @return If the operation was successful.
	 */
	public boolean moveBackward(){
		if(selectedIndex >= world_obj.size())
			return false;
		Element e = world_obj.remove(selectedIndex);
		world_obj.add(++selectedIndex, e);
		return true;
	}
	
	public Element getSelectedElement(){
		return world_obj.get(selectedIndex);
	}
	
	/**
	 * Scale the image based on the passed double value.
	 * @param scale A multiplier scale on the current image, where '1' maintains the current size. 
	 * 	Has to be greater than 0.
	 */
	public void resizeImage(double scale){
		if(scale <= 0)
			throw new IllegalArgumentException("Scale has to be greater than 0");
		
		BufferedImage img = world_obj.get(selectedIndex).getImage();
		
		world_obj.get(selectedIndex).setImage(
				(BufferedImage)(img.getScaledInstance((int)(img.getWidth() * scale), 
						(int)(img.getHeight() * scale), 
						BufferedImage.SCALE_DEFAULT)));
	}
	
	public void loadImages(String folder){
		// File representing the folder that you select using a FileChooser
	    File dir = new File(folder);
	    	    
	    FilenameFilter IMAGE_FILTER = new FilenameFilter() {

	        @Override
	        public boolean accept(final File dir, final String name) {
	        	// array of supported extensions (use a List if you prefer)
	    	    String[] EXTENSIONS = new String[]{
	    	        "gif", "png", "bmp", "jpg" // and other formats you need
	    	    };
	    	    
	            for (final String ext : EXTENSIONS) {
	                if (name.endsWith("." + ext)) {
	                    return (true);
	                }
	            }
	            return (false);
	        }
	    };
	    
	    
	    
	    for(File f : dir.listFiles(IMAGE_FILTER)){
	    	try {
				image_gallery.add(ImageIO.read(f));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
	    
	    
	}
}

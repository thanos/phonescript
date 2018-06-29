/*
 * Created on 06.11.2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package javax.microedition.lcdui;


import org.me4se.impl.lcdui.DeviceComponent;


class ScmImage extends DeviceComponent {
		Image image;
    	int layout;
    	
    	ScmImage(Image image, int layout){
    		super("image", false);
			this.image = image;
			this.layout = layout;
//    		setFocusable(false);
    	}
    	
        public void paint(java.awt.Graphics g) {
            if (image != null){
            	int xp = 0;

            	if ((layout & 2) != 0)
            		xp = getParent().getWidth()-image.getWidth();

				if((layout & ImageItem.LAYOUT_CENTER)==ImageItem.LAYOUT_CENTER)
					xp /= 2;            
            
                g.drawImage(image._image, xp, 0, null);
            }
        }

        public java.awt.Dimension getMinimumSize() {
            if (image == null) return new java.awt.Dimension (0,0);
            
            return new java.awt.Dimension(
            	image.getWidth(), image.getHeight());
        }
        
		public void focusGained() {
			getParent().focusGained();
		}


        public java.awt.Dimension getPreferredSize() {
            return getMinimumSize();
        }
    }
package org.me4se.impl.lcdui;

import java.awt.*;
import java.awt.event.*;
//import java.util.Vector;
import javax.microedition.lcdui.Item;
import javax.microedition.midlet.ApplicationManager;

import org.kobjects.scm.*;
import org.kobjects.util.Csv;

/**
 * @author Stefan Haustein
 *
 * TODO: Work on a "real" ScmItem with a 1:1 connection to Item
 */
public class DeviceComponent extends ScmComponent {

    protected FontInfo[] fontInfo;
    public boolean selectButtonRequired;
    String type;
    /** The Item that owns this device component */
    public Item item;  
    
    /** 
     * Stores the entry of the form type=x,y,w,h,type-specific-info if available.
     * Does not use the entry defaulting mechanism because coordinate defaulting
     * does not make much sense in most cases. 
     * Use a null test to determine whether the component has fixed coordinates.*/
    
	public String[] location;

    public DeviceComponent(String type, boolean focusable) {

		type = type.toLowerCase();
        this.type = type;

        fontInfo =
            new FontInfo[] {
                FontInfo.getFontInfo(type),
                FontInfo.getFontInfo(type + ".focus")};

        //        updateStyle();

        setFocusable(focusable);
        
        String props = (ApplicationManager.manager.getProperty(type));
        if(props != null){
        	//System.out.println("props: "+props);
        	location = Csv.decode(props);
        	setBounds(
        		Integer.parseInt(location[0]),
        		Integer.parseInt(location[1]),
				Integer.parseInt(location[2]),
				Integer.parseInt(location[3]));
		}
    }


    public void paint(Graphics g) {

		super.paint(g);
       
		int fillX = 0;
		int fillY = 0;
        int w = getWidth();
        int h = getHeight();

		FontInfo fi = getFontInfo();
		int round = ((fi.decoration & FontInfo.ROUND) != 0) ? 1 : 0;
		
		if((fi.decoration & FontInfo.SHADOW) != 0){
			g.setColor(fi.shadow);
			g.drawLine(2+round, h-1, w-1-round, h-1);
			g.drawLine(w-1, 2+round, w-1, h-1-round);
			g.drawLine(w-1-round, h-1-round, w-1-round, h-1-round);
			w--;
			h--;
		}
		
		int fillW = w;
		int fillH = h;
		
		if((fi.decoration & FontInfo.BORDER) != 0){
			g.setColor(fi.border);
			if((fi.decoration & FontInfo.LEFT) != 0){
				g.drawLine(0, round, 0, h-round-1);
				fillX++;
				fillW--;
			}
			if((fi.decoration & FontInfo.RIGHT) != 0) {
				g.drawLine(w-1, round, w-1, h-round-1);
				fillW--;
			}
			if((fi.decoration & FontInfo.TOP) != 0){
				g.drawLine(round, 0, w-round-1, 0);
				fillY++;
				fillH--;
			}
			if((fi.decoration & FontInfo.BOTTOM) != 0){
				g.drawLine(round, h-1, w-round-1, h-1);
				fillH--;
			}
			
		}
		
		if(fi.background != null){
			g.setColor(fi.background);
			g.fillRect(fillX, fillY, fillW, fillH);			
		}
		
		g.setColor(fi.foreground);
    }

    public boolean keyPressed(int code, int mask) {
        //System.out.println("keypressed!");

        int dir = 0;
        if (code == KeyEvent.VK_UP)
            dir = -1;
        else if (code == KeyEvent.VK_DOWN)
            dir = 1;

        if (dir == 0)
            return false;

        ScmContainer parent = getParent();
        // how can parent be null?!?!? perhaps focus still attached to invisible el.
        if(parent == null) return false;
        int count = parent.getComponentCount();
        int i0 = parent.indexOf(this);
        int i = i0;
		boolean cycle = ApplicationManager.manager.getFlag("cycleList");
        do {
            i += dir;
            
            if (i < 0)  // if not cycle and end reached, go back to original element
                i = cycle ? count - 1 : i0;
            else if (i >= count)
                i = cycle ? 0 : i0;

            ScmComponent c = parent.getComponent(i);
            if (parent.getY() + c.getY() < 0
                || parent.getY() + c.getY() + c.getHeight()
                    > parent.getParent().getHeight()
                || c.getFocusable())
                break;
        }
        while (i != i0);

        parent.getComponent(i).requestFocus();
        return true;
    }

    public void focusGained() {
        getParent().focusGained();
    }

    public void setBounds(int x, int y, int w, int h) {
        super.setBounds(x, y, w, h);
    }

    protected FontInfo getFontInfo() {
        return fontInfo[(hasFocus() && getFocusable()) ? 1 : 0];
    }

    public Dimension getMinimumSize() {
        return new Dimension(0, getFontInfo().height);
    }
}

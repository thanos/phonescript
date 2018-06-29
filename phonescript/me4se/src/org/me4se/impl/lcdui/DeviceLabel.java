package org.me4se.impl.lcdui;

import java.awt.*;
import java.awt.event.*;

import javax.microedition.lcdui.Item;
import javax.microedition.midlet.ApplicationManager;
import java.util.Vector;
//import org.kobjects.scm.*;

/** A kind of label that carries additiona formatting information */

public class DeviceLabel extends DeviceComponent {

    //    String type;
    protected String label;
    
    /* overrides the fontinfo alignment if != 0 
    protected int align; */

    Vector actionListeners;
    public boolean selected;
	public boolean selectOnFocus;

    public String actionCommand;
    public boolean compact;
    public boolean highlight;
    public boolean checkbox;
    public Vector group;
    public Image image;
    public Object object;

	public DeviceLabel(String type, Item item, boolean focusable){
        //this.type = type;
        super(type, focusable);
        this.item = item;
    }
    

    /*        addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent me) {
                    if (!DeviceLabel.this.focusTraversable)
                        me.consume();
                }
                public void mouseClicked(MouseEvent me) {
                    action();
                }
            });
    
            addKeyListener(new KeyAdapter() {
                public void keyTyped(KeyEvent ke) {
                    if (ke.getKeyChar() == ' '
                        || ke.getKeyChar() == '\r'
                        || ke.getKeyChar() == '\n')
                        action();
                }
            });*/

    public void action() {
        select(group != null | !selected);
        
        //System.out.println ("Action!!");
        
        if (actionListeners != null)
            for (int i = 0; i < actionListeners.size(); i++) {
                (
                    (ActionListener) actionListeners.elementAt(
                        i)).actionPerformed(
                    new ActionEvent(
                        DeviceLabel.this,
                        ActionEvent.ACTION_PERFORMED,
                        actionCommand == null ? label : actionCommand));
            }
//        repaint();
    }

 /*   public boolean isFocusable() {
        return focusTraversable && getSize().height > 0;
        //actionListeners != null;
    }
*/
    public void addActionListener(ActionListener l) {
        if (actionListeners == null) {
            actionListeners = new Vector();

        }
        actionListeners.addElement(l);
    }


   public boolean keyTyped (char c) {
        if (c == ' ' || c == '\n' || c == '\r') {
            action ();
            return true;
        }
        else 
            return super.keyTyped (c);
    }
    
	public void focusGained(){
		super.focusGained();
		if(selectOnFocus){
			select(true);	    
		}
	}
    

	/** 
	 * Important: if the focus is just gained, do not interpret the mouse click as
	 * "call for action": Otherwise, it is not possible to chose other commands!
	 */

    public boolean mouseClicked (int button, int x, int y, int modifiers, int clicks) {
      //  if (!super.mouseClicked (button, x, y, modifiers, clicks))
      if (!(super.mouseClicked (button, x, y, modifiers, clicks) && selectOnFocus))      
      	action ();

      return true;
    }


    public void paint(java.awt.Graphics g) {

        super.paint (g);

        int x = getFocusable() ? 3 : 0;
        int w = getWidth();
        int h = getHeight();        	
        int y = h / 2 - 1;

        FontInfo fi = fontInfo[hasFocus() ? 1 : 0];


        g.setColor (fi.foreground);

        PhysicalFont pf = fi.font;
		ApplicationManager manager = ApplicationManager.manager;

        if (checkbox) {
            if (group == null) {
                g.setColor(
                    new java.awt.Color(
                        manager.getDeviceColor(
                            0x0808080)));

                g.drawRect(x, y - 4, 8, 8);
                g.setColor(Color.black);
                if (selected) {
                    g.drawLine(x, y, x + 4, y + 4);
                    g.drawLine(x + 1, y, x + 4, y + 4);
                    g.drawLine(x + 4, y + 4, x + 9, y - 5);
                    g.drawLine(x + 4, y + 4, x + 10, y - 5);
                }
            }
            else {
                g.drawOval(x, y - 4, 7, 7);

                if (selected) {
                    g.drawOval(x + 2, y - 2, 3, 3);
                    g.fillOval(x + 2, y - 2, 3, 3);
                }
            }
            x += 11;
        }

        if (label == null)
            return;

        if (x == 0) {
            switch (fi.align) {
                case FontInfo.CENTER :
                    x = (w - pf.stringWidth(label)) / 2;
                    break;

                case FontInfo.BORDER :
                    if (getX() == 0)
                    	break;

                case FontInfo.RIGHT :
                    x = (w - pf.stringWidth(label));
                    break;
            }

            if (x < 0)
                x = 0;
        }

		// IMPROVE Currently every image is scaled down in oder to fit...
        // also, images are only displayed for left aligned items
        // because in other cases position calculation would be wrong
        
		if (image != null && fi.align == FontInfo.LEFT) {
			int imgH = manager.getImageHeight(image, "list item image");
            if (imgH > h 
            || manager.getImageWidth(image, "list item image") > h)
    			g.drawImage(image, x, y-h/2, h, h, manager.awtContainer); 
            else
                manager.drawImage(g, image, x+(h-imgH)/2, y-imgH/2, "list item image"); 

			x += h+3;
		}

        y = (h - pf.height) / 2 + pf.ascent;
        getFontInfo().drawString(g, label, x, y);
        //y += fm.getAscent () + 1;
        //        if (fi.underline)
        //          g.drawLine(x, y + 1, x + fi.stringWidth(label), y + 1);

    }

/*
    void updateStyle() {
        FontInfo fi = fontInfo[hasFocus ? 1 : 0];

        //setFont(fi.font);
        // setBackground(fi.background);
        //setForeground(fi.foreground);

        repaint();
    }
*/

    public void setActionCommand(String cmd) {
        this.actionCommand = cmd;
    }

    public String getText() {
        return label;
    }

    public void setText(String l) {
        this.label = l;
        /*    if (getParent() != null) {
                invalidate();
                getParent().validate();
                repaint();
            }*/
        repaint();
    }

    public Dimension getMinimumSize() {
        if (compact && !getFocusable () && (label == null || label.length() == 0))
            return new Dimension(0, 0);
	else
	    return super.getMinimumSize ();
    }


    public boolean selected() {
        return selected;
    }

    public void select(boolean state) {
        if (group == null) {
            if (selected != state) {
                selected = state;
                repaint();
            }
        }
        else
            for (int i = 0; i < group.size(); i++) {
                DeviceLabel dl = (DeviceLabel) group.elementAt(i);
                if (dl.selected != (dl == this)) {
                    dl.selected = !dl.selected;
                    dl.repaint();
                }
            }

    }
    
    
    public String toString(){
        return "DeviceLabel type "+type+" text: "+label+ " class "+getClass();
    }

}

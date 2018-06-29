// ME4SE - A MicroEdition Emulation for J2SE 
//
// Copyright (C) 2001 Stefan Haustein, Oberhausen (Rhld.), Germany
//
// Contributors:
//
// STATUS: API Complete
//
// This program is free software; you can redistribute it and/or
// modify it under the terms of the GNU General Public License as
// published by the Free Software Foundation; either version 2 of the
// License, or (at your option) any later version. This program is
// distributed in the hope that it will be useful, but WITHOUT ANY
// WARRANTY; without even the implied warranty of MERCHANTABILITY or
// FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public
// License for more details. You should have received a copy of the
// GNU General Public License along with this program; if not, write
// to the Free Software Foundation, Inc., 59 Temple Place - Suite 330,
// Boston, MA 02111-1307, USA.

package javax.microedition.lcdui;

import java.awt.event.KeyEvent;

import javax.microedition.midlet.ApplicationManager;

/**
 * @API MIDP-1.0
 * @API MIDP-2.0
 */
public abstract class Canvas extends Displayable {

	/**
	 * @API MIDP-1.0
	 */
    public static final int DOWN = 6;

	/**
	 * @API MIDP-1.0
	 */
    public static final int LEFT = 2;

	/**
	 * @API MIDP-1.0
	 */
    public static final int RIGHT = 5;
    
	/**
	 * @API MIDP-1.0
	 */
    public static final int UP = 1;
    
	/**
	 * @API MIDP-1.0
	 */
    public static final int FIRE = 8;
    
	/**
     * @API MIDP-1.0
     */
	public static final int GAME_A = 9;
    
	/**
	 * @API MIDP-1.0
	 */
    public static final int GAME_B = 10;
    
	/**
	 * @API MIDP-1.0
	 */
    public static final int GAME_C = 11;
    
	/**
	 * @API MIDP-1.0
	 */
    public static final int GAME_D = 12;

	/**
	 * @API MIDP-1.0
	 */
    public static final int KEY_NUM0 = 48;
    
	/**
	 * @API MIDP-1.0
	 */
    public static final int KEY_NUM1 = 49;
	
	/**
	 * @API MIDP-1.0
	 */
    public static final int KEY_NUM2 = 50;
    
	/**
	 * @API MIDP-1.0
	 */
    public static final int KEY_NUM3 = 51;
    
	/**
	 * @API MIDP-1.0
	 */
    public static final int KEY_NUM4 = 52;
    
	/**
	 * @API MIDP-1.0
	 */
    public static final int KEY_NUM5 = 53;
    
	/**
	 * @API MIDP-1.0
	 */
    public static final int KEY_NUM6 = 54;
    
	/**
	 * @API MIDP-1.0
	 */
    public static final int KEY_NUM7 = 55;
    
	/**
	 * @API MIDP-1.0
	 */
    public static final int KEY_NUM8 = 56;
    
	/**
	 * @API MIDP-1.0
	 */
    public static final int KEY_NUM9 = 57;

	/**
	 * @API MIDP-1.0
	 */
    public static final int KEY_POUND = '#';
    
	/**
	 * @API MIDP-1.0
	 */
    public static final int KEY_STAR = '*';


    ScmCanvas component = new ScmCanvas(this);
    boolean hasPointerEvents = !"false".equalsIgnoreCase(ApplicationManager.manager.getProperty("touch_screen"));;

	/**
	 * @API MIDP-1.0
	 */
    protected Canvas() {
        Display.check();

        container = new ScmDisplayable(this);
        container.setMain(component);
    }

	/**
	 * @ME4SE INTERNAL
	 */
    void _showNotify() {
        component.requestFocus();
        showNotify();
        repaint(); // sets repaintPending to true
    }

	/**
	 * For Nokia
	 * @ME4SE INTERNAL
	 */
	protected boolean _isFullCanvas(){
		return false;
	}

	/**
	 * @API MIDP-1.0
	 */
    protected abstract void paint(Graphics g);

	/**
	 * @API MIDP-1.0
	 */
    public int getGameAction(int keyCode) {
        //if ((keyCode & 0x08000) != 0)
        // keyCode = keyCode | 0x0ffff0000;
        //System.out.println ("decode: "+keyCode);
        switch (keyCode) {

            case -59 :
            case -KeyEvent.VK_UP - 100 :
                return UP;

            case -60 :
            case -KeyEvent.VK_DOWN - 100 :
                return DOWN;

            case -61 :
            case -KeyEvent.VK_LEFT - 100 :
                return LEFT;

            case -62 :
            case -KeyEvent.VK_RIGHT - 100 :
                return RIGHT;

            case -1 :
            case -4 :
            case -11 :
            case 32 :
                return FIRE;

            case 'a' :
            case 'A' :
            case -KeyEvent.VK_F1 - 100 :
                return GAME_A;

            case 's' :
            case 'S' :
            case -KeyEvent.VK_F2 - 100 :
                return GAME_B;

            case 'd' :
            case 'D' :
                return GAME_C;

            case 'f' :
            case 'F' :
                return GAME_D;

            default :
                return 0;
        }
    }

	/**
	 * @API MIDP-1.0
	 */
    public int getKeyCode(int game) {
        switch (game) {
            case UP :
                return -KeyEvent.VK_UP;
            case DOWN :
                return -KeyEvent.VK_DOWN;
            case LEFT :
                return -KeyEvent.VK_LEFT;
            case RIGHT :
                return -KeyEvent.VK_RIGHT;
            case FIRE :
                return 32;
            case GAME_A :
                return 'a';
            case GAME_B :
                return 's';
            case GAME_C :
                return 'd';
            case GAME_D :
                return 'f';
        }
        throw new IllegalArgumentException();
    }

	/**
	 * @API MIDP-1.0
	 */
    public String getKeyName(int keyCode) {
        return (keyCode > 0)
            ? ("" + (char) keyCode)
            : KeyEvent.getKeyText(-keyCode);
    }

	/**
	 * @API MIDP-1.0
	 */
    public int getHeight() {
        return component.getHeight();
    }

	/**
	 * @API MIDP-1.0
	 */
    public int getWidth() {
        return component.getWidth();
    }

	/**
	 * @API MIDP-1.0
	 */
    public boolean hasPointerEvents() {
        return hasPointerEvents;
    }

    public boolean hasPointerMotionEvents() {
        return hasPointerEvents();
    }

	/**
	 * @API MIDP-1.0
	 */
    public boolean hasRepeatEvents() {
        return true;
    }

	/**
	 * @API MIDP-1.0
	 */
    public boolean isDoubleBuffered() {
        return true;
    }

	/**
	 * @API MIDP-1.0
	 */
    protected void hideNotify() {
    }

	/**
	 * @ME4SE INTERNAL
	 */
    void handleCommand (Command cmd, Item item) {
        if (cmd == ScmDisplayable.GAME_COMMAND1) 
            keyPressed(-1);
        else if (cmd == ScmDisplayable.GAME_COMMAND2) {
            keyPressed(-4);
        }
        else super.handleCommand (cmd, item);
    }

	/**
	 * @API MIDP-1.0
	 */
    protected void keyPressed(int code) {
    }

	/**
	 * @API MIDP-1.0
	 */
    protected void keyReleased(int code) {
    }

	/**
	 * @API MIDP-1.0
	 */
    protected void keyRepeated(int code) {
    }

	/**
	 * @API MIDP-1.0
	 */
    protected void pointerDragged(int x, int y) {
    }

	/**
	 * @API MIDP-1.0
	 */
    protected void pointerPressed(int x, int y) {
    }

	/**
	 * @API MIDP-1.0
	 */
    protected void pointerReleased(int x, int y) {
    }
	/**
	 * @API MIDP-1.0
	 */
    public void repaint() {
        component.repaintPending = true;
        component.repaint();
    }

	/**
	 * @API MIDP-1.0
	 */
    public void serviceRepaints() {
        if (component.repaintPending)
            synchronized (component.repaintLock) {
                java.awt.Graphics g = component.getGraphics();
                if (g != null)
                    component.paint(g);
            }
    }

	/**
	 * @API MIDP-1.0
	 */
    public void repaint(int x, int y, int w, int h) {
        component.repaintPending = true;
        component.repaint(x, y, w, h);
    }

	/**
	 * @API MIDP-1.0
	 */
    protected void showNotify() {
    }
    
	/**
	 * Called when the drawable area of the Canvas has been changed. This method 
	 * has augmented semantics compared to Displayable.sizeChanged. 
	 * In addition to the causes listed in Displayable.sizeChanged, a size 
	 * change can occur on a Canvas because of a change between normal and 
	 * full-screen modes. If the size of a Canvas changes while it is actually 
	 * visible on the display, it may trigger an automatic repaint request. If 
	 * this occurs, the call to sizeChanged will occur prior to the call to paint. 
	 * If the Canvas has become smaller, the implementation may choose not to trigger 
	 * a repaint request if the remaining contents of the Canvas have been preserved. 
	 * Similarly, if the Canvas has become larger, the implementation may choose to 
	 * trigger a repaint only for the new region. In both cases, the preserved contents
	 * must remain stationary with respect to the origin of the Canvas. If the size change 
	 * is significant to the contents of the Canvas, the application must explicitly 
	 * issue a repaint request for the changed areas. Note that the application's repaint 
	 * request should not cause multiple repaints, since it can be coalesced with repaint 
	 * requests that are already pending.
     * 
     * If the size of a Canvas changes while it is not visible, the implementation may 
     * choose to delay calls to sizeChanged until immediately prior to the call to showNotify. 
     * In that case, there will be only one call to sizeChanged, regardless of the number 
     * of size changes.
     * 
     * An application that is sensitive to size changes can update instance variables in 
     * its implementation of sizeChanged. These updated values will be available to the code 
     * in the showNotify, hideNotify, and paint methods.
	 * 
	 * Overrides: sizeChanged in class Displayable
	 *
	 * @param w the new width in pixels of the drawable area of the Canvas
	 * @param h the new height in pixels of the drawable area of the Canvas
	 * 
	 * @API MIDP-2.0
	 * @ME4SE UNIMPLEMENTED
	 */
    protected void sizeChanged(int w, int h) {
    }
    
    /**
     * Controls whether the Canvas is in full-screen mode or in normal mode.
     * @param mode true if the Canvas is to be in full screen mode, false otherwise
     * 
     * @API MIDP-2.0
     * @ME4SE UNIMPLEMENTED
     */
	public void setFullScreenMode(boolean mode) {
		
	}
}
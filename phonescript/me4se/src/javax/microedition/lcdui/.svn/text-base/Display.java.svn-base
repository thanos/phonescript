// ME4SE - A MicroEdition Emulation for J2SE 
//
// Copyright (C) 2001 Stefan Haustein, Oberhausen (Rhld.), Germany
//
// Contributors:
//
// STATUS: API complete
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

// should we remove the dependency on the application manager?

package javax.microedition.lcdui;

import java.util.Hashtable;
import java.util.Vector;

import javax.microedition.midlet.ApplicationManager;
import javax.microedition.midlet.MIDlet;

/**
 * @API MIDP-1.0
 * @API MIDP-2.0  
 */
public class Display {

	/**
	 * @API MIDP-2.0
	 */
	public static final int LIST_ELEMENT = 1;
	
	/**
	 * @API MIDP-2.0
	 */
	public static final int CHOICE_GROUP_ELEMENT = 2;

	/**
	 * @API MIDP-2.0
	 */
	public static final int ALERT = 3;
	
	/**
	 * @API MIDP-2.0
	 */
	public static final int COLOR_BACKGROUND = 0;
	
	/**
	 * @API MIDP-2.0
	 */
	public static final int COLOR_FOREGROUND = 1;
	
	/**
	 * @API MIDP-2.0
	 */
	public static final int COLOR_HIGHLIGHTED_BACKGROUND = 2;
	
	/**
	 * @API MIDP-2.0
	 */
	public static final int COLOR_HIGHLIGHTED_FOREGROUND = 3;
	
	/**
	 * @API MIDP-2.0
	 */
	public static final int COLOR_BORDER = 4;

	/**
	 * @API MIDP-2.0
	 */	
	public static final int COLOR_HIGHLIGHTED_BORDER = 5;
	
	private static Hashtable midlets = new Hashtable();

	private MIDlet midlet;
	protected Displayable current;
	private static ScmDisplayable currentContainer;
	protected Vector callSerially = new Vector();
	protected TickerThread tickerThread = new TickerThread(this);

	/**
	 * @ME4SE INTERNAL
	 */
	protected Display(MIDlet midlet) {
		this.midlet = midlet;
		tickerThread.start();
	}

	/**
	 * @API MIDP-1.0 
	 */
	public void callSerially(Runnable r) {
		callSerially.addElement(r);
		if (current != null)
			current.container.repaint();
	}

	/**
	 * Requests a flashing effect for the device's backlight. The flashing 
	 * effect is intended to be used to attract the user's attention or as 
	 * a special effect for games. Examples of flashing are cycling the backlight 
	 * on and off or from dim to bright repeatedly. The return value indicates if 
	 * the flashing of the backlight can be controlled by the application. 
	 * The flashing effect occurs for the requested duration, or it is switched 
	 * off if the requested duration is zero. This method returns immediately; 
	 * that is, it must not block the caller while the flashing effect is running.
     *
	 * Calls to this method are honored only if the Display is in the foreground. 
	 * This method MUST perform no action and return false if the Display is in 
	 * the background. 
     *
     * The device MAY limit or override the duration. For devices that do not 
     * include a controllable backlight, calls to this method return false.
     * 
     * @param duration the number of milliseconds the backlight should be flashed, 
     *                 or zero if the flashing should be stopped
     * @return true if the backlight can be controlled by the application and this 
     *              display is in the foreground, false otherwise
     * @throws IllegalArgumentException - if duration is negative
     * 
     * @API MIDP-2.0
     * @ME4SE UNIMPLEMENTED
	 */
	public boolean flashBacklight(int duration) {
		System.out.println("Display.flashBacklight() called with no effect !");
		return false;
	}

	/**
	 * Requests operation of the device's vibrator. The vibrator is intended 
	 * to be used to attract the user's attention or as a special effect for games. 
	 * The return value indicates if the vibrator can be controlled by the application. 
	 * This method switches on the vibrator for the requested duration, or switches it off
	 * if the requested duration is zero. If this method is called while the vibrator 
	 * is still activated from a previous call, the request is interpreted as setting 
	 * a new duration. It is not interpreted as adding additional time to the original 
	 * request. This method returns immediately; that is, it must not block the caller 
	 * while the vibrator is running.
	 * 
	 * Calls to this method are honored only if the Display is in the foreground. 
	 * This method MUST perform no action and return false if the Display is in 
	 * the background.
	 * 
	 * The device MAY limit or override the duration. For devices that do not include a 
	 * controllable vibrator, calls to this method return false.
	 * 
	 * @param duration - the number of milliseconds the vibrator should be run, 
	 *                   or zero if the vibrator should be turned off
	 * @return true if the vibrator can be controlled by the application and this
	 *         display is in the foreground, false otherwise
	 * @throws IllegalArgumentException - if duration is negative
	 * 
	 * @API MIDP-2.0
	 * @ME4SE UNIMPLEMENTED
	 */
	public boolean vibrate(int duration) {
		System.out.println("Display.vibrate() called with no effect !");
		return false;		
	}

	/**
	 * @API MIDP-1.0 
	 */
	public static Display getDisplay(MIDlet midlet) {

		Display display = (Display) midlets.get(midlet);
		if (display == null) {
			display = new Display(midlet);
			midlets.put(midlet, display);
		}

		return display;
	}

	/**
	 * @API MIDP-1.0 
	 */
	public synchronized void setCurrent(Displayable d) {

		ApplicationManager manager = ApplicationManager.manager;

		if (current == d)
			return;

		if (ApplicationManager.manager.active != midlet)
			return;

		if (currentContainer != null)
			manager.displayContainer.remove(currentContainer);

		if (current instanceof Canvas)
			 ((Canvas) current).hideNotify();

		if (d == null) {
			current = null;
			currentContainer = null;
		} else {
			d.container.setX(manager.getIntProperty("screen.x", 0));
			d.container.setY(manager.getIntProperty("screen.y", 0));

			if (d instanceof Alert) {
				Alert alert = (Alert) d;
				if (alert.next == null)
					alert.next = current;
			}

			manager.displayContainer.add(d.container);
			d.display = this;
			current = d;
			currentContainer = d.container;
			current._showNotify();
			//System.out.println("Display.Current: " + current);
			manager.wrapper.requestFocus();
			currentContainer.repaint();
		}
	}

	/**
	 * @API MIDP-1.0 
	 */
	public void setCurrent(Alert alert, Displayable next) {
		alert.next = next;
		setCurrent(alert);
	}

	/**
	 * Requests that the Displayable that contains this Item be made current, scrolls the 
	 * Displayable so that this Item is visible, and possibly assigns the focus to this Item. 
	 * The containing Displayable is first made current as if setCurrent(Displayable) had been 
	 * called. When the containing Displayable becomes current, or if it is already current, it 
	 * is scrolled if necessary so that the requested Item is made visible. Then, if the 
	 * implementation supports the notion of input focus, and if the Item accepts the input focus, 
	 * the input focus is assigned to the Item. This method always returns immediately, without 
	 * waiting for the switching of the Displayable, the scrolling, and the assignment of input 
	 * focus to take place.
	 * It is an error for the Item not to be contained within a container. It is also an error 
	 * if the Item is contained within an Alert.
	 * @param item the item that should be made visible
	 * 
	 * @API MIDP-2.0
	 */
	public void setCurrentItem(Item item) {
		System.out.println("Display.setCurrentItem() with no effect!");
	}
 

	/**
	 * @API MIDP-1.0 
	 */
	public boolean isColor() {
		return true;
	}

	/**
	 * @API MIDP-1.0 
	 */
	public int numColors() {
		return 1 << (java.awt.Toolkit.getDefaultToolkit().getColorModel().getPixelSize() - 1);
	}

	/**
	 * @API MIDP-1.0 
	 */
	public Displayable getCurrent() {
		return current;
	}

	/**
	 * Returns the best image width for a given image type. The image type must be one 
	 * of LIST_ELEMENT, CHOICE_GROUP_ELEMENT, or ALERT.
	 * 
	 * @param imageType the image type
	 * @return the best image width for the image type, may be zero if there is 
	 *         no best size; must not be negative
	 * @throws IllegalArgumentException - if imageType is illegal
	 * 
	 * @API MIDP-2.0
	 * @ME4SE UNIMPLEMENTED
	 */
	public int getBestImageWidth(int imageType) {
		System.out.println("Display.getBestImageWidth() with no effect!");
		return 0;
	}
	
	/**
	 * Returns the best image height for a given image type. The image type 
	 * must be one of LIST_ELEMENT, CHOICE_GROUP_ELEMENT, or ALERT.
	 * 
	 * @param imageType the image type
	 * @return the best image height for the image type, may be zero if there 
	 *         is no best size; must not be negative
	 * @throws IllegalArgumentException - if imageType is illegal
	 * 
	 * @API MIDP-2.0
	 * @ME4SE UNIMPLEMENTED
	 */
	public int getBestImageHeight(int imageType) {
		System.out.println("Display.getBestImageHeight() with no effect!");
		return 0;
	}

	/**
	 * @ME4SE INTERNAL
	 */
	protected static void check() {
		if (ApplicationManager.manager.frame != null && !ApplicationManager.manager.frame.isVisible()) {
			java.awt.Frame frame = ApplicationManager.manager.frame;
			frame.pack();
			frame.show();
			frame.setResizable(false);
		}
	}

}

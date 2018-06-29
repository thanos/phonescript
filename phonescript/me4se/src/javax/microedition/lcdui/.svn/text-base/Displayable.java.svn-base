// ME4SE - A MicroEdition Emulation for J2SE 
//
// Copyright (C) 2001 Stefan Haustein, Oberhausen (Rhld.), Germany
//
// Contributors:
//
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

import java.util.Vector;

import javax.microedition.midlet.ApplicationManager;

import org.kobjects.scm.ScmComponent;
import org.me4se.impl.lcdui.DeviceLabel;

/**
 * @API MIDP-1.0
 * @API MIDP-2.0 
 */
public abstract class Displayable {

	CommandListener commandListener;
	private java.util.Vector commands = new Vector();

	Display display;
	ScmDisplayable container;

	DeviceLabel titleComponent = new DeviceLabel("title", null, false);
	DeviceLabel tickerComponent = new DeviceLabel("ticker", null, false);

	/**
		 * The title is stored separately from the titleLable because the
		 * ticker may alter the titleLabel text.
		 */

	String title;
	Ticker ticker;
	


	//   set to true if soft buttons shall not be shown on a Canvas 
	//  boolean hideSoftButtons;

	/** set to true if the device does not provide a select button */
	boolean noSelectButton;

	/** set to true if the current form or *item* requires a select button */
	boolean selectButtonRequired;

	Displayable() {

		Display.check();

		noSelectButton = ApplicationManager.manager.skin != null && ApplicationManager.manager.getProperty("button.select") == null;

	}

	/**
	 * notify the Displayable that it is displayed on the screen now
	 */

	void _showNotify() {
	}

	/** 
	 * Overwritten in Form
	 * @ME4SE Internal */
	
	Item getCurrentItem(){
		return null;	
	}


	/** 
	 * Returns a Vector of CmdInfo objects for this Displayable and the
	 * focused item.
	 * 
	 * @ME4SE Internal */


	Vector getCommandInfoList(){
		Vector v = new Vector();
		
		Item item = getCurrentItem();
		if(item != null && item.commands != null){
			for(int i = 0; i < item.commands.size(); i++) {
				v.addElement(new CmdInfo((Command) item.commands.elementAt(i), item));
			}
		}
		
		for(int i = 0; i < commands.size(); i++) {
			v.addElement(new CmdInfo((Command) commands.elementAt(i), null));
		}
		
		return v;
	}


	/**
	 * @API MIDP-1.0 
	 */
	public synchronized void addCommand(Command cmd) {

		if (commands.indexOf(cmd) != -1)
			return;
		for (int i = 0; i < commands.size(); i++) {
			Command curCmd = (Command) commands.elementAt(i);
			if (cmd.getPriority() < curCmd.getPriority()) {
				commands.insertElementAt(cmd, i);
				container.updateButtons();
				return;
			}
		}
		commands.insertElementAt(cmd, commands.size());
		container.updateButtons();
	}

	/**
	 * @ME4SE INTERN 
	 */
	void handleCommand(Command cmd, Item item) {

		if (cmd == ScmDisplayable.MENU_COMMAND)
			display.setCurrent(new CommandList(this));
		else if (cmd == ScmDisplayable.SELECT_COMMAND) {
			ScmComponent f = container.getFocusOwner();
			if (f instanceof DeviceLabel) {
				 ((DeviceLabel) f).action();
			}
		}
		else if(cmd != null){
			if(item != null){
				if(item.listener != null){
					item.listener.commandAction(cmd, item);
				}
			}	
			else if (commandListener != null)
				commandListener.commandAction(cmd, this);
		}

	}

	/**
	 * @API MIDP-1.0 
	 */
	public boolean isShown() {
		return display != null && display.current == this;
	}

	/**
	 * @API MIDP-1.0 
	 */
	public void setCommandListener(CommandListener commandListener) {
		this.commandListener = commandListener;
	}

	/**
	 * @API MIDP-1.0 
	 */
	public void removeCommand(Command cmd) {
		try {
			int idx = commands.indexOf(cmd);
			if (idx == -1)
				return;
			commands.removeElementAt(idx);

			container.updateButtons();
		}
		catch (Exception ex) { 
		}
	}

	/**
	 * Gets the title of the Displayable. Returns null if there is no title.
	 * @return the title of the instance, or null if no title
	 * 
	 * @API MIDP-2.0
	 */
	public String getTitle() {
		return title;
	}
	
	/**
	 * Sets the title of the Displayable. If null is given, removes the title. 
	 * If the Displayable is actually visible on the display, the implementation 
	 * should update the display as soon as it is feasible to do so.
	 * 
	 * The existence of a title may affect the size of the area available for
	 * Displayable content. Addition, removal, or the setting of the title text 
	 * at runtime may dynamically change the size of the content area. This is 
	 * most important to be aware of when using the Canvas class. If the available 
	 * area does change, the application will be notified via a call to sizeChanged(). 
	 *
	 * @param s  the new title, or null for no title
	 *  
	 * @API MIDP-2.0
	 */
	public void setTitle(String title) {
		this.title = title;
		titleComponent.setText(title);
		container.invalidate();
	}
	
	/**
	 * Gets the ticker used by this Displayable.
	 * 
	 * @return ticker object used, or null if no ticker is present
	 * 
	 * @API MIDP-2.0
	 */
	public Ticker getTicker() {
		return ticker;        
	}
	
	/**
	 * Sets a ticker for use with this Displayable, replacing any previous
	 * ticker. If null, removes the ticker object from this Displayable. 
	 * The same ticker may be shared by several Displayable objects within 
	 * an application. This is done by calling setTicker() with the same 
	 * Ticker object on several different Displayable objects. If the 
	 * Displayable is actually visible on the display, the implementation 
	 * should update the display as soon as it is feasible to do so. 
	 * The existence of a ticker may affect the size of the area available 
	 * for Displayable's contents. Addition, removal, or the setting of the 
	 * ticker at runtime may dynamically change the size of the content area. 
	 * This is most important to be aware of when using the Canvas class. If 
	 * the available area does change, the application will be notified via a 
	 * call to sizeChanged().
	 * 
	 * @param ticker the ticker object used on this screen
	 * 
	 * @API MIDP-2.0
	 */
	public void setTicker(Ticker ticker) {
		this.ticker = ticker;        
	}
	
	/**
	 * Gets the width in pixels of the displayable area available to the 
	 * application. The value returned is appropriate for the particular 
	 * Displayable subclass. This value may depend on how the device uses 
	 * the display and may be affected by the presence of a title, a ticker, 
	 * or commands. This method returns the proper result at all times, even 
	 * if the Displayable object has not yet been shown.
	 * 
	 * @return width of the area available to the application
	 * 
	 * @API MIDP-2.0
	 */
	public int getWidth() {
		return container.main.getWidth();
	}
	
	/**
	 * Gets the height in pixels of the displayable area available to the 
	 * application. The value returned is appropriate for the particular 
	 * Displayable subclass. This value may depend on how the device uses 
	 * the display and may be affected by the presence of a title, a ticker, 
	 * or commands. This method returns the proper result at all times, even 
	 * if the Displayable object has not yet been shown.
	 * 
	 * @return height of the area available to the application
	 * 
	 * @API MIDP-2.0
	 */
	public int getHeight() {
		return container.main.getHeight();
	}
	
	/**
	 * The implementation calls this method when the available area of the 
	 * Displayable has been changed. The "available area" is the area of the 
	 * display that may be occupied by the application's contents, such as 
	 * Items in a Form or graphics within a Canvas. It does not include space 
	 * occupied by a title, a ticker, command labels, scroll bars, system status 
	 * area, etc. A size change can occur as a result of the addition, removal, 
	 * or changed contents of any of these display features. 
	 * This method is called at least once before the Displayable is shown 
	 * for the first time. If the size of a Displayable changes while it is 
	 * visible, sizeChanged will be called. If the size of a Displayable changes 
	 * while it is not visible, calls to sizeChanged may be deferred. If the size 
	 * had changed while the Displayable was not visible, sizeChanged will be called 
	 * at least once at the time the Displayable becomes visible once again.
	 * 
	 * The default implementation of this method in Displayable and its subclasses 
	 * defined in this specification must be empty. This method is intended solely 
	 * for being overridden by the application. This method is defined on Displayable 
	 * even though applications are prohibited from creating direct subclasses of 
	 * Displayable. It is defined here so that applications can override it in subclasses 
	 * of Canvas and Form. This is useful for Canvas subclasses to tailor their graphics 
	 * and for Forms to modify Item sizes and layout directives in order to fit their 
	 * contents within the the available display area.
     *
	 * @param w the new width in pixels of the available area
	 * @param h the new height in pixels of the available area
	 * 
	 * @API MIDP-2.0
	 */
	protected void sizeChanged(int w, int h) {
	}

}

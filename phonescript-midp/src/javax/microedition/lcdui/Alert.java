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

package javax.microedition.lcdui;

import org.me4se.impl.lcdui.*;


//TODO Timeouts are not working.
/** 
 * @API MIDP-1.0 
 * @API MIDP-2.0
 */
public class Alert extends Screen {

	private static final Command ALERT_COMMAND = new Command("Ok", Command.OK, -9999999);

	/**
	 * @API MIDP-2.0
	 */
	public static final Command DISMISS_COMMAND = new Command("", 4, 0);   

	/**
	 * @API MIDP-1.0
	 */
	public static final int FOREVER = -2;

	private int timeout;
	private AlertType type;
	private TextComponent text = new TextComponent("alert", false);
	private ScmDeviceList lst = new ScmDeviceList(this);
	private ScmImage image = new ScmImage(null, ImageItem.LAYOUT_CENTER);
	private Thread terminator;

	Displayable next;

	/**
	 * @API MIDP-1.0
	 */
	public Alert(String title, String alertText, Image image, AlertType type) {
		this(title);
		setString(alertText);
		this.image.image = image;
		this.image.invalidate();
		this.type = type;
	}

	/**
	 * @API MIDP-1.0
	 */
	public Alert(String title) {
		super(title);
		lst.add(image);
		lst.add(text);
		container.setMain(lst);
		super.addCommand(ALERT_COMMAND);
	}

	/**
	 * @ME4SE INTERNAL
	 */
	void handleCommand(Command cmd, Item item) {
		if (cmd == ALERT_COMMAND || cmd == DISMISS_COMMAND){
			terminator = null;
			if(next != null){
				display.setCurrent(next);
				next = null;
			}
		}
		else
			super.handleCommand(cmd, item);
	}

	/**
	 * @API MIDP-1.0
	 */
	public Image getImage() {
		return image.image;
	}

	/**
	 * Gets the activity indicator for this Alert.
	 * @return 	a reference to this Alert's activity indicator, or null if there is none
     *
     * @API MIDP-2.0
     * @ME4SE UNIMPLEMENTED
	 */
	public Gauge getIndicator() {
		System.out.println("Alert.getIndicator() called with no effect!");
		return null;
	}

	/**
	 * @API MIDP-1.0
	 */
	public String getString() {
		return text.getText();
	}

	/**
	 * @remark The timer can be set, but is not yet supported by ME4SE
     * @API MIDP-1.0
	 * @ME4SE UNIMPLEMENTED
	 */
	public int getTimeout() {
		return timeout;
	}

	/**
	 * @API MIDP-1.0
	 */
	public int getDefaultTimeout() {
		return FOREVER;
	}

	/**
	 * @API MIDP-1.0
	 */
	public void setTimeout(int time) {
		this.timeout = time;
		super.removeCommand(ALERT_COMMAND);
		
		if(timeout != FOREVER){
			terminator = new Thread(){
				public  void run(){
					try{
						sleep(timeout);
						
						if(terminator == this){
							handleCommand(ALERT_COMMAND, null);
						}

					}
					catch(InterruptedException e){
						
					}
				}
			};
			terminator.start();
		}
		else {
			terminator = null;
			super.addCommand(ALERT_COMMAND);
		}
		
	}

	/**
	 * @API MIDP-1.0
	 */
	public void setImage(Image image) {
		this.image.image = image;
		this.image.invalidate();
		lst.doLayout();
	}

	/**
	 * Sets an activity indicator on this Alert. The activity indicator 
	 * is a Gauge object. It must be in a restricted state in order for 
	 * it to be used as the activity indicator for an Alert. The restrictions 
	 * are listed above. If the Gauge object violates any of these restrictions, 
	 * IllegalArgumentException is thrown. 
	 *
     * If indicator is null, this removes any activity indicator present on this Alert.
     * @param indicator the activity indicator for this Alert, or null if there is to be none
	 * @throws IllegalArgumentException if indicator does not meet the restrictions for its use in an Alert
	 * 
	 * @API MIDP-2.0
	 * @ME4SE UNIMPLEMENTED
	 */

	public void setIndicator(Gauge indicator) {
		System.out.println("Alert.setIndicator() called with no effect!");
	}

	/**
	 * @API MIDP-1.0
	 */
	public void setString(String text) {
		this.text.setText(text);
		this.text.invalidate();
		lst.doLayout();
	}

	/**
	 * @API MIDP-1.0
	 */
	public void setType(AlertType type) {
		this.type = type;
	}

	/**
	 * @API MIDP-1.0
	 */
	public void addCommand(Command cmd) {
		throw new java.lang.IllegalStateException();
	}

	/**
	 * @API MIDP-1.0
	 */
	public AlertType getType() {
		return type;
	}

	/**
	 * @API MIDP-1.0
	 */
	public void setCommandListener(CommandListener l) {
		throw new java.lang.IllegalStateException();
	}
	
	/**
	 * @ME4SE INTERNAL
	 */
	public void _showNotify() {
		lst.doLayout();
	}
}

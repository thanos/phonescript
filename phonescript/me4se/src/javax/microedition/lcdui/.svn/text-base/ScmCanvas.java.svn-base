package javax.microedition.lcdui;


/**
 * @author Stefan Haustein
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 */

import org.kobjects.scm.*;
import javax.microedition.midlet.ApplicationManager;
import java.awt.event.KeyEvent;

class ScmCanvas extends ScmComponent {

	Canvas canvas;
	boolean first = true;
	int keyCode;
	Object repaintLock = new Object();
	boolean repaintPending;

	ScmCanvas(Canvas canvas) {
		this.canvas = canvas;
	}

	public void paint(java.awt.Graphics g) {

		//  g.drawLine (0, 50, 50, 0);
		//System.out.println ("paint called");

		try {
			synchronized (repaintLock) {

				ApplicationManager manager = ApplicationManager.manager;

				//java.awt.Dimension d = getSize();

				if (manager.offscreen == null) {
					manager.offscreen =
						ApplicationManager.manager.awtContainer.createImage(
							manager.screenWidth,
							manager.screenHeight);
				}

				if (repaintPending) {
					Graphics mg =
						new Graphics(
							canvas,
							null,
							manager.offscreen.getGraphics());
					if (mg != null) {
						repaintPending = false;  // moved up here to allow the request of a repaint in paint
						canvas.paint(mg);  // thanks for the fix to Steven Lagerweij
						mg.stale = true;
					}
					else repaint ();
				}


				g.drawImage(
					manager.offscreen,
					0,
					0,
					ApplicationManager.manager.awtContainer);

				repaintLock.notify();
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		//System.out.println ("paint left");
		//g.drawLine (0, 0, 999, 999);
	}

	public int decode(int code) {

		switch (code) {
			case KeyEvent.VK_F1 :
				return -1;
			case KeyEvent.VK_F2 :
				return -4;
	  /*      case KeyEvent.VK_SPACE :
				return -11; */
			case KeyEvent.VK_UP :
				return -59;
			case KeyEvent.VK_DOWN :
				return -60;
			case KeyEvent.VK_LEFT :
				return -61;
			case KeyEvent.VK_RIGHT :
				return -62;

			default :
				return 0;
		}

	}

	/*
	public boolean isFocusTraversable () {
		return false;
	}
	*/



	public boolean mouseDragged(int x, int y, int modifiers) {
		if(canvas.hasPointerEvents)
			canvas.pointerDragged(x, y);
		return true;
	}

	public boolean mousePressed(int button, int x, int y, int modifiers) {
		if (button != 1) return false;
		if(canvas.hasPointerEvents)
			canvas.pointerPressed(x, y);
		return true;
	}

	public boolean mouseReleased(int button, int x, int y, int modifiers) {
		if (button != 1) return false;
		if(canvas.hasPointerEvents)
			canvas.pointerReleased(x, y);
		return true;
	}

	public boolean keyPressed(int code, int modifiers) {
		//System.out.println ("key: "+ev+" decoded: "+decode(ev));
		code = decode(code);
		if(code != 0) {
			keyCode = code;
			canvas.keyPressed(keyCode);        	
		}
		first = true;
		return true;
	}

	public boolean keyReleased(int code, int modifiers) {
		code = decode(code);
		if(code != 0)	
			canvas.keyReleased(code);
		else
			canvas.keyReleased(keyCode);
	    	
		return true;
	}

	public boolean keyTyped(char c) {
		if (first) {
			first = false;
			keyCode = (int) c;
			canvas.keyPressed((int) c);
		}
		else
			canvas.keyRepeated((int) c);

		return true;
	}
}



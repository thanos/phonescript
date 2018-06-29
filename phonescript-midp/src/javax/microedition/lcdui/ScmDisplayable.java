package javax.microedition.lcdui;

/**
 * @author Stefan Haustein
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 */

import org.kobjects.scm.*;
import org.me4se.impl.lcdui.*;
import javax.microedition.midlet.*;
import java.awt.event.*;
import java.util.Vector;

class ScmDisplayable extends ScmContainer {

	static String[] TYPES =
		{ "null", "SCREEN", "BACK", "CANCEL", "OK", "HELP", "STOP", "EXIT", "ITEM" };

	static final Command MENU_COMMAND = new Command("Options", 0, 0);
	static final Command SELECT_COMMAND = new Command("Select", 0, 0);
	static final Command GAME_COMMAND1 = new Command("Action1", 0, 0);
	static final Command GAME_COMMAND2 = new Command("Action2", 0, 0);

	/** The displayable represented by this container */

	Displayable displayable;

	SoftButton[] buttons;

	/** 
	 * The title component. The title is not contained in main, 
	 * main contains only the scrollable components.
	 */

	DeviceLabel title;

	/** The ticker component */

	DeviceLabel ticker;

	/** The scrollable main area of the displayable object */

	ScmComponent main;

	/* 
	 * If this flag is set to true, the full screen area is used
	 * for the main component
	 */

	boolean hideSoftButtons;
	boolean fullCanvas;
	
	int best;

	/** 
	 * This component makes sure that the size of main
	 * is allways correct, regardles of the "displayed"
	 * status.
	 */

	ScmDisplayable(Displayable displayable) {

		ApplicationManager manager = ApplicationManager.manager;

		this.displayable = displayable;

		setWidth(manager.screenWidth);
		setHeight(manager.screenHeight);

		if (displayable instanceof Screen) {
			Screen scr = (Screen) displayable;
			this.title = scr.titleComponent;
			this.ticker = scr.tickerComponent;

			add(title);

			if (title.location == null) {
				title.setHeight(title.getMinimumSize().height);
				title.setWidth(getWidth());
			}

			if (ticker.location != null) {
				add(ticker);
			}
			
			if(scr.iconUp != null)
				add(scr.iconUp);
			if(scr.iconDown != null)
				add(scr.iconDown);
		}
		else {
			fullCanvas = ((Canvas) displayable)._isFullCanvas();
			hideSoftButtons = fullCanvas | manager.getFlag("canvasHideSoftButtons");
		}

		int cnt;
		if (manager.getProperty("skin") == null)
			cnt = 2;
		else {
			cnt = 0;
			while (manager.getProperty("softbutton." + (cnt)) != null) {
				cnt++;
			}
		}

		buttons = new SoftButton[cnt];

		for (int i = 0; i < buttons.length; i++) {
			buttons[i] = new SoftButton(this, i);
			if (!hideSoftButtons)
				add(buttons[i]);
		}
	}

	public void paint(java.awt.Graphics g) {

		Vector toRun = null;

		if (displayable.display != null && displayable.display.callSerially.size() > 0) {
			toRun = displayable.display.callSerially;
			displayable.display.callSerially = new Vector();
		}

		super.paint(g);

		if (toRun != null)
			for (int i = 0; i < toRun.size(); i++)
				 ((Runnable) toRun.elementAt(i)).run();
	}

	public boolean keyPressed(int code, int modifiers) {
		if (code >= KeyEvent.VK_F1 && code < KeyEvent.VK_F1 + buttons.length) {
			//            System.out.println(
			//              "Function key F"
			//                + (code - KeyEvent.VK_F1 + 1)
			//              + " intercepted!");
			SoftButton bi = buttons[code - KeyEvent.VK_F1];
			displayable.handleCommand(bi.command, bi.item);
			return true;
		}
		else
			return super.keyPressed(code, modifiers);

	}

	void setMain(ScmComponent main) {
		if (this.main != null)
			remove(this.main);

		this.main = main;
		add(main);
		doLayout();
	}

	/*    void handleSoftButton(int index) {
	
	        Command cmd = buttons[index].command;
	*/

	protected void updateButtons() {

		Vector commands = displayable.getCommandInfoList();
		Item item = displayable.getCurrentItem();
		
		int available = buttons.length;
		for (int i = 0; i < available; i++)
			buttons[i].setCommand(null, null);

		if (hideSoftButtons) {
			buttons[0].setCommand(GAME_COMMAND1, null);
			available--;
			if (commands.size() == 0) {
				buttons[1].setCommand(GAME_COMMAND2, null);
				available--;
			}
		}
		else if (displayable.noSelectButton && displayable.selectButtonRequired) {
			buttons[buttons.length - 1].setCommand(SELECT_COMMAND, null);
			available--;
		}
		else if(item != null && item.defaultCommand != null){
			buttons[buttons.length - 1].setCommand(item.defaultCommand, item);
			available--;
		}

		if (available < commands.size()) {

			buttons[(
				displayable.noSelectButton
					&& ((displayable instanceof List) || (displayable instanceof Form)))
				? 0
				: buttons.length - 1].setCommand(MENU_COMMAND, null);
			available--;
		}

		// assign remaining buttons by type

		boolean[] assigned = new boolean[commands.size()];
		for (int i = 0; i < commands.size(); i++) {
			CmdInfo cmd = (CmdInfo) commands.elementAt(i);
			String preferred =
				ApplicationManager.manager.getProperty("commands.keys." + TYPES[cmd.command.getCommandType()]);

			if (preferred != null && preferred.startsWith("SOFT")) {
				int index = Integer.parseInt(preferred.substring(4)) - 1;
				if (buttons[index].command == null) {
					buttons[index].setCommand(cmd.command, cmd.item);
					assigned[i] = true;
				}
			}
		}

		// assign remaining buttons by priority

		int cmdIndex = 0;
		for (int i = 0; i < buttons.length && cmdIndex < commands.size(); i++)
			if (buttons[i].command == null && !assigned[cmdIndex]){
				CmdInfo ci = (CmdInfo) commands.elementAt(cmdIndex++);
				buttons[i].setCommand(ci.command, ci.item);
			}
	}

	public void doLayout() {

		ApplicationManager manager = ApplicationManager.manager;

		int x = 0;
		int y = 0;
		int w = manager.screenWidth;
		int h = manager.screenHeight;

		if (!fullCanvas) {
			x = manager.getIntProperty("screenPaintableRegion.x", x);
			y = manager.getIntProperty("screenPaintableRegion.y", y);
			w = manager.getIntProperty("screenPaintableRegion.width", w);
			h = manager.getIntProperty("screenPaintableRegion.height", h);

			if (displayable instanceof Canvas) {
				x = manager.getIntProperty("canvasPaintableRegion.x", x);
				y = manager.getIntProperty("canvasPaintableRegion.y", y);
				w = manager.getIntProperty("canvasPaintableRegion.width", w);
				h = manager.getIntProperty("canvasPaintableRegion.height", h);
			}
		}

		if (manager.getProperty("skin") == null
			&& h == manager.screenHeight
			&& buttons.length > 0
			&& buttons[0] != null) {
			h -= buttons[0].getHeight();
		}

		if (title != null && title.location == null) {
			title.setBounds(x, y, w, title.getHeight());
			y += title.getHeight() + 1;
			h -= y;
		}

		boolean change = w != main.getWidth() || h != main.getHeight();
		main.setBounds(x, y, w, h);
		
		if(change)
			displayable.sizeChanged(w, h);

		super.doLayout();
	}

}

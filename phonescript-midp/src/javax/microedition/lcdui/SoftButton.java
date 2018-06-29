package javax.microedition.lcdui;

import javax.microedition.midlet.*;
import org.me4se.impl.lcdui.*;

class SoftButton extends DeviceLabel {

	ScmDisplayable owner;
	Command command;
	Item item;
	int index;

	SoftButton(ScmDisplayable owner, int index) {
		super("softButton."+index, null, false);
		this.index = index;
		this.owner = owner;

		compact = false;
		highlight = false;
		setText("");

		int cnt = owner.buttons.length;
/*
		String propStr =
			ApplicationManager.manager.getProperty(
				"softbutton." + index);

		if (propStr != null) {
			String[] props = Csv.decode(propStr);
			setBounds(
				Integer.parseInt(props[0]),
				Integer.parseInt(props[1]),
				Integer.parseInt(props[2]),
				Integer.parseInt(props[3]));
*/				
		if(location != null){

			if(location.length >= 6){
				String al = location[5].toLowerCase();
				if("left".equals(al)) fontInfo[0].align = FontInfo.LEFT;
				else if("right".equals(al)) fontInfo[0].align = FontInfo.RIGHT;
				else if("center".equals(al)) fontInfo[0].align=FontInfo.CENTER;
			}
		}
		else {
			int w = (owner.getWidth() - 5 * (cnt - 1)) / cnt;
			int h = getMinimumSize().height;
			int x = (w + 5) * index;

			setBounds(x, owner.getHeight() - h, w, h);
		}
	}

	/*
	void setLabel (String s) {
	setText (s);
	
	}*/

	public void paint(java.awt.Graphics g){
		java.awt.Color fib = fontInfo[0].background;
		if(fib != null && (label == null || label.length() == 0) &&
			fib.getRed()+fib.getGreen()+fib.getBlue() < 3*128){
				g.setColor(new java.awt.Color(
					ApplicationManager.manager.getDeviceColor(
						new java.awt.Color(
							Math.min(fib.getRed()*2,255), 
							Math.min(fib.getGreen()*2,255), 
							Math.min(fib.getBlue()*2,255)).getRGB())));

			g.fillRect(0,0, getWidth(), getHeight());
		}
		else
			super.paint(g);
	}

	public void setText(String s) {
		java.awt.Color fib = fontInfo[0].background;
	/*	if(fib != null){
//			System.out.println("color: "+fontInfo[0].background.getRGB()&0x0ff00000);
		if ((s == null || s.length() == 0) 
			&& (fib.getRed()+fib.getGreen()+fib.getBlue() < 128*3))
			setBackground(
				new java.awt.Color(
					ApplicationManager.manager.getDeviceColor(
						new java.awt.Color(
							fib.getRed()/2, fib.getGreen()/2, fib.getBlue()/2).getRGB())));
		else
			setBackground(fontInfo[0].background);
		}*/
		super.setText(s);
		//System.out.println ("softb-settext "+s);

	}
	/*
	public void update (java.awt.Graphics g) {
	System.out.println ("paintsoftbutton; label: "+label);
	super.paint (g);
	}
	public void paint (java.awt.Graphics g) {
	System.out.println ("paintsoftbutton; label: "+getText ());
	super.paint (g);
	}
	*/

	void setCommand(Command cmd, Item item) {
		this.item = item;
		command = cmd;
		if (cmd == null)
			setText("");
		else 
			setText(cmd.getLabel());

	//	invalidate();
	}

	public void action() {
		((ScmDisplayable) getParent()).displayable.handleCommand(command, item);
	}
}

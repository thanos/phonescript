package javax.microedition.lcdui;

import javax.microedition.midlet.ApplicationManager;
import org.me4se.impl.lcdui.*;

/**
 * @API MIDP-1.0
 * @API MIDP-2.0 
 */
public class StringItem extends Item {

    String text;

	/**
	 * @API MIDP-1.0
	 */	
    public StringItem(String label, String text) {
    	this(label, text, Item.PLAIN);
    }

	/**
	 * @API MIDP-2.0
	 */	
	public StringItem(String label, String text, int appearanceMode) {
		super(label, appearanceMode);
		setText(text);
	}

	/**
	 * @API MIDP-1.0
	 */	
    public void setText(String text) {
        this.text = text;
        update();
    }
    
    void update(){
    	super.update();
        int p = delete();

        for (int i = lines.size() - 1; i > 0; i--)
            lines.removeElementAt(i);

		String type;
		switch(appearanceMode){
			case HYPERLINK: type = "hyperlink"; break;
			case BUTTON: type = "button"; break;
			default:type = "default";
		}
		
        if (text != null && text.length() > 0) {
            WordWrap ww =
                new WordWrap(
                    FontInfo.getFontInfo(type).font,
                    text,
                    ApplicationManager.manager.getIntProperty(
                        "screenPaintableRegion.width",
                        ApplicationManager.manager.screenWidth));

            int p0 = 0;

            while (true) {
                int p1 = ww.next();
                if (p1 == -1)
                    break;
                int p2 = p1;

                while (p2 > p0 && text.charAt(p2 - 1) <= ' ')
                    p2--;



                DeviceLabel l = new DeviceLabel(type, this, commands != null && commands.size() > 0);
                l.highlight = false;
                
                l.setText(text.substring(p0, p2));
                lines.addElement(l);

                p0 = p1;
            }
        }

        readd(p);
    }

	/**
	 * @API MIDP-1.0
	 */	
    public String getText() {
        return text;
    }
    
	/**
	 * @API MIDP-2.0
	 */	    

    public int getAppearanceMode() {
    	return appearanceMode;
    }
    
	/**
	 * @API MIDP-2.0
	 * @ME4SE UNIMPLEMENTED
	 */	
	public Font getFont() {
		System.out.println("StringItem.getFont() called with no effect!");
		return null;
	}
	
	/**
	 * @API MIDP-2.0
	 * @ME4SE UNIMPLEMENTED
	 */	
	public void setFont() {
		System.out.println("StringItem.setFont() called with no effect!");
	}

	/**
	 * @API MIDP-2.0
	 * @ME4SE UNIMPLEMENTED
	 */	
	public void setPreferredSize(int width, int height) {
		System.out.println("StringItem.setPreferredSize() called with no effect!");
	}
}

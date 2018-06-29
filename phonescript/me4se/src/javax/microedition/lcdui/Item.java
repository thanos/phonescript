package javax.microedition.lcdui;

import java.util.Vector;

import org.me4se.impl.lcdui.DeviceLabel;


/**
 * @API MIDP-1.0
 * @API MIDP-2.0 
 */
public abstract class Item {

	/**
	 * @API MIDP-2.0
	 */
	public static final int LAYOUT_DEFAULT = 0;
	
	/**
     * @API MIDP-2.0
	 */
	public static final int LAYOUT_LEFT = 1;
	
	/**
	 * @API MIDP-2.0
	 */
	public static final int LAYOUT_RIGHT = 2;
	
	/**
	 * @API MIDP-2.0
	 */
	public static final int LAYOUT_CENTER = 3;
	
	/**
	 * @API MIDP-2.0
	 */
	public static final int LAYOUT_TOP = 4;
	
	/**
	 * @API MIDP-2.0
	 */
	public static final int LAYOUT_BOTTOM = 0x20;

	/**
	 * @API MIDP-2.0
	 */
	public static final int LAYOUT_VCENTER = 0x30;
	
	/**
	 * @API MIDP-2.0
	 */
	public static final int LAYOUT_NEWLINE_BEFORE = 0x100;
	
	/**
	 * @API MIDP-2.0
	 */
	public static final int LAYOUT_NEWLINE_AFTER = 0x200;
	
	/**
	 * @API MIDP-2.0
	 */
	public static final int LAYOUT_SHRINK = 0x400;
	
	/**
	 * @API MIDP-2.0
	 */
	public static final int LAYOUT_EXPAND = 0x800;
	
	/**
	 * @API MIDP-2.0
	 */
	public static final int LAYOUT_VSHRINK = 0x1000;
	
	/**
	 * @API MIDP-2.0
	 */
	public static final int LAYOUT_VEXPAND = 0x2000;
	
	/**
	 * @API MIDP-2.0
	 */
	public static final int LAYOUT_2 = 0x4000;
	
	/**
	 * @API MIDP-2.0
	 */	
	public static final int PLAIN = 0;
	
	/**
	 * @API MIDP-2.0
	 */
	public static final int HYPERLINK = 1;
	
	/**
	 * @API MIDP-2.0
	 */	
	public static final int BUTTON = 2;
	
    Form form;
    Form saveForm;
    Vector commands = null;
    int layout;
    ItemCommandListener listener;
    Command defaultCommand;

    DeviceLabel label = new DeviceLabel("label", null, false);
    
    /** 
     * The lines are stored without ScmContainer and are
     * inserted separately in the form component in order
     * to simplify scrolling */
    
    Vector lines = new Vector();
	int appearanceMode;

    Item() {
        label.compact = true;
        label.highlight = false;
        lines.addElement(label);
    }

    Item(String lbl) {
        this(lbl, PLAIN);
        setLabel(lbl);
    }
    
    Item(String lbl, int appearanceMode){
    	this();
    	this.appearanceMode = appearanceMode;
    	setLabel(lbl);
    }

    int delete() {
        saveForm = form;
        return form == null ? -1 : form.delete(this);
    }
    
    
    void notifyChanged() {
        if (form != null && form.itemStateListener != null) 
            form.itemStateListener.itemStateChanged(this);
    }

    void readd(int index) {
        if (index == -1)
            return;
        saveForm.insert(index, this);
    }

	/**
	 * @API MIDP-1.0
	 */	
    public void setLabel(String lbl) {
        label.setText(lbl == null ? null : lbl.replace('\n', ' '));
        label.invalidate();
		update();
    }

	/**
	 * @API MIDP-1.0
	 */	
    public String getLabel() {
        return label.getText();
    }
    
    /**
     * Gets the layout directives used for placing the item.
     * @return a combination of layout directive values
	 * 
	 * @API MIDP-2.0
     */
    public int getLayout() {
    	return layout;
    }

	/**
	 * Sets the layout directives for this item. 
	 * It is illegal to call this method if this Item is 
	 * contained within an Alert.
	 * 
	 * 
	 * @param layout a combination of layout directive values for this item
	 * @throws IllegalArgumentException if the value of layout is not a bit-wise OR
	 *                                   combination of layout directives
	 * @throws IllegalStateException if this Item is contained within an Alert
	 * 
	 * @API MIDP-2.0
	 * @remark Currently the layout is stored, but it has no effect.
	 */
	public void setLayout(int layout) {
		this.layout = layout;
		update();
	}
	
	/**
	 * @API MIDP-2.0
	 */
	public void addCommand(Command cmd) {
		if(commands == null)
			commands = new Vector();
			
		commands.addElement(cmd);
		update();
	}

	/**
	 * @API MIDP-2.0
	 */	
	public void removeCommand(Command cmd) {
		if(commands != null)
			commands.remove(cmd);
			
		update();
	}
	
	/**
	 * @API MIDP-2.0
	 */		
	public void setItemCommandListener(ItemCommandListener l) {
		listener = l;
	}
	
	/**
	 * @API MIDP-2.0
	 * @ME4SE UNIMPLEMENTED 
	 */		
	public int getPreferredWidth() {
		System.out.println("Item.getPreferredWidth() called with no effect!");
		return -4711;
	}
	
	/**
	 * @API MIDP-2.0
	 * @ME4SE UNIMPLEMENTED 
	 */		
	public int getPreferredHeight() {
		System.out.println("Item.getPreferredHeight() called with no effect!");
		return -4711;
	}

	/**
	 * @API MIDP-2.0
	 * @ME4SE UNIMPLEMENTED 
	 */			
	public void setPreferredSize(int width, int height) {
		System.out.println("Item.setPreferredSize() called with no effect!");	
	}
	
	/**
	 * @API MIDP-2.0
	 * @ME4SE UNIMPLEMENTED 
	 */		
	public int getMinimumWidth() {
		System.out.println("Item.getMinimumWidth() called with no effect!");
		return -4711;
	}
	
	/**
	 * @API MIDP-2.0
	 * @ME4SE UNIMPLEMENTED 
	 */		
	public int getMinimumHeight() {
		System.out.println("Item.getMinimumHeight() called with no effect!");
		return -4711;
	}
	
	/**
	 * @API MIDP-2.0
	 */	
	public void setDefaultCommand(Command cmd) {
		removeCommand(cmd);
		addCommand(cmd);
		defaultCommand = cmd;
	}
	
	void update(){
		if(form != null)
			form.container.updateButtons();
	}
	
	/**
	 * @API MIDP-2.0
	 * @ME4SE UNIMPLEMENTED 
	 */		
	public void notifyStateChanged() {
		System.out.println("Item.notyfyStateChanged() called with no effect!");
	}
}

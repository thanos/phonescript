// replaced by impl. based on ScmGauge

package javax.microedition.lcdui;

import javax.microedition.midlet.ApplicationManager;

/**
 * @API MIDP-1.0
 * @API MIDP-2.0
 */
public class Gauge extends Item {

	/**
	 * @API MIDP-2.0 
	 */
	public static final int INDEFINITE = -1;
	
	/**
	 * @API MIDP-2.0 
	 */
	public static final int CONTINUOUS_IDLE = 0;

	/**
	 * @API MIDP-2.0 
	 */
	public static final int INCREMENTAL_IDLE = 1;

	/**
	 * @API MIDP-2.0 
	 */
	public static final int CONTINUOUS_RUNNING = 2;

	/**
	 * @API MIDP-2.0 
	 */
	public static final int INCREMENTAL_UPDATING = 3;

	int value;
	int maximum;
	ScmGauge component;

	/**
	 * @API MIDP-1.0 
	 */
	public Gauge(java.lang.String label, boolean interactive, int maxValue, int initialValue) throws IllegalArgumentException {
		super(label);

		this.maximum = maxValue;
		this.value = initialValue;

		component = (ScmGauge) ApplicationManager.manager.getComponent(interactive ? "item.gauge.interactive" : "item.gauge");

		if (component == null)
			component = new ScmGauge(interactive);

		component.init(this);

		lines.addElement(component);
	}

	/**
	 * @API MIDP-1.0 
	 */
	public int getMaxValue() {
		return maximum;
	}

	/**
	 * Gets the current value of this Gauge object.
     *
	 * @API MIDP-1.0 
	 */
	public int getValue() {
		return value;
	}

	/**
	 * @API MIDP-1.0 
	 */
	public boolean isInteractive() {
		return component.getFocusable();
	}

	/**
	 * @API MIDP-1.0 
	 */
	public void setMaxValue(int maxValue) {
		maximum = maxValue;
		component.repaint();
	}

	/**
	 * @API MIDP-1.0 
	 */
	public void setValue(int value) {
		this.value = value;
		component.repaint();
	}
	
	/**
	 * Sets the layout directives for this item. 
	 * It is illegal to call this method if this Item is 
	 * contained within an Alert.
	 * 
	 * Overrides: setLayout in class Item
	 * @param layout a combination of layout directive values for this item
	 * @throws IllegalArgumentException if the value of layout is not a valid 
	 *                                  combination of layout directives
	 * @throws IllegalStateException if this Item is contained within an Alert
	 * 
	 * @API MIDP-2.0 
	 * @ME4SE UNIMPLEMENTED
	 */	
	public void setLayout(int layout) {
		System.out.println("Gauge.setLayout() called with no effect!");
	}
	
	/**
	 * Adds a context sensitive Command to the item. The semantic type of 
	 * Command should be ITEM. The implementation will present the command 
	 * only when the the item is active, for example, highlighted. 
	 * If the added command is already in the item (tested by comparing the 
	 * object references), the method has no effect. If the item is actually 
	 * visible on the display, and this call affects the set of visible commands, 
	 * the implementation should update the display as soon as it is feasible 
	 * to do so.
	 * 
	 * It is illegal to call this method if this Item is contained within an Alert.
	 * 
	 * Overrides: addCommand in class Item
	 * 
	 * @param cmd the command to be added
	 * @throws IllegalStateException if this Item is contained within an Alert
	 * @throws NullPointerException if cmd is null
	 *
	 * @API MIDP-2.0
	 * @API ME4SE UNIMPLEMENTED
	 *
	 */
	public void addCommand(Command cmd) {
		System.out.println("Gauge.setCommand() called with no effect!");
	}
	
	/**
	 * Sets a listener for Commands to this Item, replacing any previous 
	 * ItemCommandListener. A null reference is allowed and has the effect 
	 * of removing any existing listener. 
	 * It is illegal to call this method if this Item is contained within an Alert.
	 * 
	 * Overrides: setItemCommandListener in class Item
	 * @param l the new listener, or null.
	 * @throws IllegalStateException if this Item is contained within an Alert
	 * 
	 * @API MIDP-2.0
	 * @ME4SE UNIMPLENTED
	 */
	public void setItemCommandListener(ItemCommandListener l) {
	}
	
	/**
	 * Sets the preferred width and height for this Item. Values for width and 
	 * height less than -1 are illegal. If the width is between zero and the minimum 
	 * width, inclusive, the minimum width is used instead. If the height is between 
	 * zero and the minimum height, inclusive, the minimum height is used instead. 
	 * Supplying a width or height value greater than the minimum width or height locks 
	 * that dimension to the supplied value. The implementation may silently enforce a
	 * maximum dimension for an Item based on factors such as the screen size. Supplying 
	 * a value of -1 for the width or height unlocks that dimension. See Item Sizes for 
	 * a complete discussion.
	 * 
	 * It is illegal to call this method if this Item is contained within an Alert.
	 * 
	 * Overrides: setPreferredSize in class Item
	 * @param width the value to which the width should be locked, or -1 to unlock
	 * @param height the value to which the height should be locked, or -1 to unlock
	 * @throws IllegalArgumentException if width or height is less than -1
	 * @throws IllegalStateException if this Item is contained within an Alert
	 *
	 * @API MIDP-2.0
	 * @ME4SE UNIMPLEMENTED
	 */
	public void setPreferredSize(int width, int height) {
		System.out.println("Gauge.setPrefferedSize() called with no effect!");
	}
	
	/**
	 * Sets default Command for this Item. If the Item previously had a default 
	 * Command, that Command is no longer the default, but it remains present 
	 * on the Item. If not null, the Command object passed becomes the default 
	 * Command for this Item. If the Command object passed is not currently present 
	 * on this Item, it is added as if addCommand(javax.microedition.lcdui.Command) 
	 * had been called before it is made the default Command.
	 * 
	 * If null is passed, the Item is set to have no default Command. The previous 
	 * default Command, if any, remains present on the Item.
	 * 
	 * It is illegal to call this method if this Item is contained within an Alert.
	 * 
	 * Overrides: setDefaultCommand in class Item
	 * @param cmd the command to be used as this Item's default Command, or null 
	 *            if there is to be no default command
	 * 
	 * @API MIDP-2.0
	 * @ME4SE UNIMPLEMENTED
	 */
	public void setDefaultCommand(Command cmd) {
		System.out.println("Gauge.setDefaultCommand() called with no effect!");
	}
}

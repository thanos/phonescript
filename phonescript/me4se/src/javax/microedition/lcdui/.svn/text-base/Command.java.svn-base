package javax.microedition.lcdui;

/**
 * @API MIDP-1.0
 * @API MIDP-2.0
 */
public class Command {

	/**
	 * @API MIDP-1.0
	 */
	public static final int SCREEN = 1;

	/**
	 * @API MIDP-1.0
	 */
	public static final int BACK = 2;

	/**
	 * @API MIDP-1.0
	 */
	public static final int CANCEL = 3;

	/**
	 * @API MIDP-1.0
	 */
	public static final int OK = 4;

	/**
	 * @API MIDP-1.0
	 */
	public static final int HELP = 5;

	/**
	 * @API MIDP-1.0
	 */
	public static final int STOP = 6;

	/**
	 * @API MIDP-1.0
	 */
	public static final int EXIT = 7;

	/**
	 * @API MIDP-1.0
	 */
	public static final int ITEM = 8;

	private String label;
	private int type;
	private int priority;
	private String longLabel;

	/**
	 * @API MIDP-1.0
	 */
	public Command(String label, int type, int priority) {
		this.label = label;
		this.type = type;
		if (type < 0 || type > 8)
			throw new IllegalArgumentException();
		this.priority = priority;
	}

	/**
	 * @API MIDP-2.0
	 */
	public Command(String shortLabel, String longLabel, int commandType, int priority) {
		this(shortLabel, commandType, priority);
		this.longLabel = longLabel;
	}

	/**
	 * @API MIDP-1.0
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * Gets the long label of the command.
	 * @return the Command's long label, or null if the Command has no long label
	 * 
	 * @API MIDP-2.0
	 */
	public String getLongLabel() {
		return longLabel;
	}

	/**
	 * @API MIDP-1.0
	 */
	public int getCommandType() {
		return type;
	}

	/**
	 * @API MIDP-1.0
	 */
	public int getPriority() {
		return priority;
	}
}

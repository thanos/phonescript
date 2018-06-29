package javax.microedition.lcdui;

/**
 * @API MIDP-1.0
 * @API MIDP-2.0
 */
public interface Choice {

	/**
	 * @API MIDP-1.0
	 */
    public static final int EXCLUSIVE = 1;
    
    /**
     * @API MIDP-1.0
     */
    public static final int MULTIPLE = 2;
    
    /**
     * @API MIDP-1.0
     */
    public static final int IMPLICIT = 3;

	/**
	 * POPUP is a choice having exactly one element selected 
	 * at a time. The selected element is always shown. The other 
	 * elements should be hidden until the user performs a particular 
	 * action to show them. When the user performs this action, all 
	 * elements become accessible. For example, an implementation could 
	 * use a popup menu to display the elements of a ChoiceGroup of type POPUP.
	 * 
	 * The POPUP type is not valid for List objects.
	 * 
	 * Value 4 is assigned to POPUP.
	 * 
	 * @API MIDP-2.0
	 */
	public static final int POPUP = 4;
	
	/**
	 * Constant for indicating that the application has no preference 
	 * as to wrapping or truncation of text element contents and that 
	 * the implementation should use its default behavior. 
	 * 
	 * Field has the value 0.
	 *
	 * @API MIDP-2.0
	 */
	public static final int TEXT_WRAP_DEFAULT = 0;
	
	/**
	 * Constant for hinting that text element contents should be wrapped 
	 * to to multiple lines if necessary to fit available content space. 
	 * The Implementation may limit the maximum number of lines that it 
	 * will actually present.
	 * 
	 * Field has the value 1.
	 *
	 * @API MIDP-2.0 
	 */
	public static final int TEXT_WRAP_ON = 1;
	
	/**
	 * Constant for hinting that text element contents should be limited 
	 * to a single line. Line ending is forced, for example by cropping, 
	 * if there is too much text to fit to the line. The implementation 
	 * should provide some means to present the full element contents. 
	 * This may be done, for example, by using a special pop-up window 
	 * or by scrolling the text of the focused element.
	 * Implementations should indicate that cropping has occurred, 
	 * for example, by placing an ellipsis at the point where the text 
	 * contents have been cropped.
	 * 
	 * Field has the value 2.
	 * 
	 * @API MIDP-2.0
	 */
	public static final int TEXT_WRAP_OFF = 2;
	
	/**
	 * @API MIDP-1.0 
	 */
    public int append(String s, Image i);
   
	/**
	 * @API MIDP-1.0 
	 */
    public void delete(int index);
    
	/**
	 * @API MIDP-1.0 
	 */
    public Image getImage(int index);

	/**
	 * @API MIDP-1.0 
	 */
    public int getSelectedFlags(boolean[] flags);

	/**
	 * @API MIDP-1.0 
	 */
    public int getSelectedIndex();
    
	/**
	 * @API MIDP-1.0 
	 */
    public String getString(int index);

	/**
	 * @API MIDP-1.0 
	 */
    public void insert(int index, String stringItem, Image imageItem);

	/**
	 * @API MIDP-1.0 
	 */
    public boolean isSelected(int index);

	/**
	 * @API MIDP-1.0 
	 */
    public void set(int index, String str, Image img);
 
	/**
	 * @API MIDP-1.0 
	 */
    public void setSelectedFlags(boolean [] flags);

	/**
	 * @API MIDP-1.0 
	 */
    public void setSelectedIndex(int i, boolean state);
    
    /**
     * @API MIDP-1.0 
     */
    public int size();
    
    /**
     * Deletes all elements from this Choice, leaving it with zero elements. 
     * This method does nothing if the Choice is already empty. 
     *
     * @API MIDP-2.0
     */
	public void deleteAll();

	/**
	 * Sets the application's preferred policy for fitting Choice element 
	 * contents to the available screen space. The set policy applies 
	 * for all elements of the Choice object. Valid values are TEXT_WRAP_DEFAULT, 
	 * TEXT_WRAP_ON, and TEXT_WRAP_OFF. Fit policy is a hint, and the implementation
	 * may disregard the application's preferred policy.
	 * 
	 * @param fitPolicy preferred content fit policy for choice elements
	 * @throws IllegalArgumentException - if fitPolicy is invalid
	 * 
	 * @API MIDP-2.0
	 */
	public void setFitPolicy(int fitPolicy);

	/**
	 * Gets the application's preferred policy for fitting Choice element contents 
	 * to the available screen space. The value returned is the policy that had been 
	 * set by the application, even if that value had been disregarded by the implementation.
	 * @return one of TEXT_WRAP_DEFAULT, TEXT_WRAP_ON, or TEXT_WRAP_OFF
	 * 
	 * @API MIDP-2.0
	 */
	public int getFitPolicy();
	
	/**
	 * Sets the application's preferred font for rendering the specified element 
	 * of this Choice. An element's font is a hint, and the implementation may disregard 
	 * the application's preferred font.
	 * The elementNum parameter must be within the range [0..size()-1], inclusive.
	 * The font parameter must be a valid Font object or null. If the font parameter 
	 * is null, the implementation must use its default font to render the element.
	 * 
	 * @param elementNum the index of the element, starting from zero
	 * @param font the preferred font to use to render the element
	 * @throws IndexOutOfBoundsException if elementNum is invalid
	 * 
	 * @API MIDP-2.0
	 */
	public void setFont(int elementNum,	Font font);
	
	/**
	 * Gets the application's preferred font for rendering the specified element 
	 * of this Choice. The value returned is the font that had been set by the 
	 * application, even if that value had been disregarded by the implementation. 
	 * If no font had been set by the application, or if the application explicitly 
	 * set the font to null, the value is the default font chosen by the implementation.
	 * 
	 * The elementNum parameter must be within the range [0..size()-1], inclusive.
	 * 
	 * @param elementNum the index of the element, starting from zero
	 * @return the preferred font to use to render the element
	 * @throws IndexOutOfBoundsException if elementNum is invalid
	 * 
	 * @API MIDP-2.0
	 */
	public Font getFont(int elementNum);
}









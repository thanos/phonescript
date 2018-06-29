// ME4SE - A MicroEdition Emulation for J2SE 
//
// Copyright (C) 2001 Stefan Haustein, Oberhausen (Rhld.), Germany
//
// Contributors:
//
// STATUS:
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

import javax.microedition.midlet.ApplicationManager;

import org.me4se.impl.lcdui.DrawImageFilter;
import org.me4se.impl.lcdui.PhysicalFont;

/**
 * @API MIDP-1.0
 * @API MIDP-2.0 
 */
public class Graphics {

	/**
	 * @API MIDP-1.0
	 */
	public static final int HCENTER = 1;

	/**
	 * @API MIDP-1.0
	 */
	public static final int VCENTER = 2;

	/**
	 * @API MIDP-1.0
	 */
	public static final int LEFT = 4;

	/**
	 * @API MIDP-1.0
	 */
	public static final int RIGHT = 8;

	/**
	 * @API MIDP-1.0
	 */
	public static final int TOP = 16;

	/**
	 * @API MIDP-1.0
	 */
	public static final int BOTTOM = 32;

	/**
	 * @API MIDP-1.0
	 */
	public static final int BASELINE = 64;

	/**
	 * @API MIDP-1.0
	 */
	public static final int SOLID = 0;

	/**
	 * @API MIDP-1.0
	 */
	public static final int DOTTED = 1;

	/**
	 * @ME4SE INTERNAL
	 */
	public int translateX = 0;

	/**
	 * @ME4SE INTERNAL
	 */
	public int translateY = 0;

	/**
	 * @ME4SE INTERNAL
	 */
	public int strokeStyle = SOLID;

	boolean stale;

	private java.awt.Graphics awtGraphics;

	Font font;
	java.awt.Color awtColor = java.awt.Color.white;

	/** @ME4SE INTERNAL 
	 * @remark Required for Nokia direct Graphics support */

	public int _argbColor = 0x0ffffffff;
	Canvas canvas;
	Image image;

	private boolean transparent() {
		return _argbColor > 0; // first bit not set -> not transparent
	}

	/**
	 * @ME4SE INTERNAL
	 */
	protected Graphics(Canvas canvas, Image image, java.awt.Graphics g) {
		this.awtGraphics = g;
		this.canvas = canvas;
		this.image = image;
		setFont(Font.getDefaultFont());
	}

	/**
	 * Returns an AWT graphics object. Indirect access is neccessary to support modifyable
	 * transparent images to some extent.
	 * 
	 * @ME4SE INTERNAL
	 */

	public java.awt.Graphics _getAwtGraphics() {
		if (awtGraphics == null) {

			if (image._transparent) { // getGraphics will destroy transparency...
				java.awt.Image mutable =
					ApplicationManager.manager.awtContainer.createImage(
						image.getWidth(),
						image.getHeight());

				awtGraphics = mutable.getGraphics();

				ApplicationManager.manager.drawImage(awtGraphics, image._image, 0, 0, image.name);
				image._image = mutable;
				image._transparent = false;
			}
			else
				awtGraphics = image._image.getGraphics();
		}

		return awtGraphics;
	}

	/**
	 * @ME4SE INTERNAL 
	 * @remark Added in order to allow pixel grabbing in the Nokia API*/

	public java.awt.Image _getAwtImage(int[] xy0) {
		return image != null ? image._image : ApplicationManager.manager.offscreen;

	}

	private void checkStale() {
		if (stale && canvas != null) {
			canvas.component.paint(canvas.component.getGraphics());
		}
	}

	/**
	 * @API MIDP-1.0
	 */
	public void drawArc(int x, int y, int w, int h, int sa, int aa) {
		if (transparent())
			return;

		_getAwtGraphics().drawArc(x, y, w, h, sa, aa);
		checkStale();
	}

	/**
	 * @API MIDP-1.0
	 */
	public void drawChars(char[] chars, int ofs, int len, int x, int y, int align) {
		drawString(new String(chars, ofs, len), x, y, align);
	}

	/**
	 * @API MIDP-1.0
	 * IMPROVE: In order to support mutable transparent nokia/siemens images,
	 * we need an image filter supporting this functionality, including
	 * a clipping area.... 
	 */
	public void drawImage(Image img, int x, int y, int align) {
		ApplicationManager manager = ApplicationManager.manager;

		int w = manager.getImageWidth(img._image, img.name);
		int h = manager.getImageHeight(img._image, img.name);
		x = normalizeX(x, w, align);
		y = normalizeY(y, h, align);

		if (image != null && image._transparent) {
			// TODO: Test this... is it correct to add the translation? what about clipping?

			x += getTranslateX();
			y += getTranslateY();

			int[] data = new int[w * h];
			img.getRGB(data, 0, w, 0, 0, w, h);

			image._image =
				java.awt.Toolkit.getDefaultToolkit().createImage(
					new java.awt.image.FilteredImageSource(
						image._image.getSource(),
						new DrawImageFilter(data, x, y, w, h)));
		}
		else {
			manager.drawImage(_getAwtGraphics(), img._image, x, y, img.name);
		}

		//g.drawRect (x, y, img.getWidth (), img.getHeight ());

		//System.out.println ("drawimg stale: "+stale);
		checkStale();
	}

	/**
	 * @API MIDP-1.0
	 */
	public void drawLine(int x0, int y0, int x1, int y1) {

		////System.out.println ("enter drawline"+strokeStyle);

		if (transparent())
			return;

		if (strokeStyle == SOLID) {
			_getAwtGraphics().drawLine(x0, y0, x1, y1);
		}
		else {
			int dx = x1 - x0;
			int dy = y1 - y0;
			int steps = Math.max(Math.abs(dx), Math.abs(dy)) / 4;
			dx = (dx << 16) / steps;
			dy = (dy << 16) / steps;
			x0 = x0 << 16;
			y0 = y0 << 16;

			while (steps > 0) {
				_getAwtGraphics().drawLine(x0 >> 16, y0 >> 16, (x0 + dx) >> 16, (y0 + dy) >> 16);
				x0 += dx + dx;
				y0 += dy + dy;
				steps -= 2;
			}
		}

		//	//System.out.println ("leave drawline");

		checkStale();
	}

	/**
	 * @API MIDP-1.0
	 */
	public void drawString(String text, int x, int y, int align) {

		if (transparent())
			return;

		PhysicalFont fm = font.info.font;

		int cut = text.indexOf('\n');
		if (cut != -1) {
			drawString(text.substring(cut + 1), x, y + fm.height, align);
			text = text.substring(0, cut);
		}

		//y--;

		switch (align & (TOP | BOTTOM | BASELINE | VCENTER)) {
			case TOP :
				y += fm.ascent;
				break;
			case BOTTOM :
				y -= fm.descent;
				break;
			case BASELINE :
				break;
			case VCENTER :
				y = y + fm.ascent - fm.height / 2;
				break;
			default :
				throw new IllegalArgumentException();
		}

		font.info.drawString(
			_getAwtGraphics(),
			text,
			normalizeX(x, fm.stringWidth(text), align),
			y);
		checkStale();
	}

	/**
	 * @API MIDP-1.0
	 */
	public void drawRect(int x, int y, int w, int h) {
		if (transparent())
			return;
		_getAwtGraphics().drawRect(x, y, w, h);
		checkStale();
	}

	/**
	 * @API MIDP-1.0
	 */
	public void drawRoundRect(int x, int y, int w, int h, int r1, int r2) {
		if (transparent())
			return;
		_getAwtGraphics().drawRoundRect(x, y, w, h, r1, r2);
		checkStale();
	}

	/**
	 * @API MIDP-1.0
	 */
	public void fillArc(int x, int y, int w, int h, int sa, int aa) {
		if (transparent())
			return;
		_getAwtGraphics().fillArc(x, y, w, h, sa, aa);
		checkStale();
	}

	/**
	 * @API MIDP-1.0
	 */
	public void fillRect(int x, int y, int w, int h) {
		if (transparent())
			return;
		_getAwtGraphics().fillRect(x, y, w, h);
		checkStale();
	}

	/**
	 * @API MIDP-1.0
	 */
	public void fillRoundRect(int x, int y, int w, int h, int r1, int r2) {
		_getAwtGraphics().fillRoundRect(x, y, w, h, r1, r2);
		checkStale();
	}

	/**
	 * @API MIDP-1.0
	 */
	public Font getFont() {
		return font;
	}

	/**
	 * @API MIDP-1.0
	 */
	public int getClipX() {
		java.awt.Rectangle r = _getAwtGraphics().getClipBounds();
		return r == null ? 0 : r.x;
	}

	/**
	 * @API MIDP-1.0
	 */
	public int getClipY() {
		java.awt.Rectangle r = _getAwtGraphics().getClipBounds();
		return r == null ? 0 : r.y;
	}

	/**
	 * @API MIDP-1.0
	 */
	public int getClipWidth() {
		java.awt.Rectangle r = _getAwtGraphics().getClipBounds();
		return r == null ? canvas.getWidth() : r.width;
	}

	/**
	 * @API MIDP-1.0
	 */
	public int getClipHeight() {
		java.awt.Rectangle r = _getAwtGraphics().getClipBounds();
		return r == null ? canvas.getHeight() : r.height;
	}

	/**
	 * @API MIDP-1.0
	 */
	public int getColor() {
		return _argbColor;
	}

	/**
	 * @API MIDP-1.0
	 */
	public void setFont(Font font) {
		if (font == null)
			font = Font.getDefaultFont();
		//g.setFont(font.info.font);
		this.font = font;
	}

	/**
	 * @API MIDP-1.0
	 */
	public void translate(int x, int y) {
		translateX += x;
		translateY += y;

		_getAwtGraphics().translate(x, y);
	}

	/**
	 * @API MIDP-1.0
	 */
	public void clipRect(int x, int y, int w, int h) {
		_getAwtGraphics().clipRect(x, y, w, h);
	}

	/**
	 * @API MIDP-1.0
	 */
	public void setClip(int x, int y, int w, int h) {
		_getAwtGraphics().setClip(x, y, w, h);
	}

	/**
	 * @API MIDP-1.0
	 */
	public void setColor(int c) {

		_argbColor = c | 0x0ff000000;
		awtColor = new java.awt.Color(ApplicationManager.manager.getDeviceColor(c) & 0x0ffffff);

		_getAwtGraphics().setColor(awtColor);
	}

	/**
	 * @API MIDP-1.0
	 */
	public void setColor(int cr, int cg, int cb) {
		setColor((cr << 16) | (cg << 8) | cb);
	}

	/**
	 * @API MIDP-1.0
	 */
	public void setGrayScale(int gsc) {
		setColor(gsc, gsc, gsc);
	}

	/**
	 * @API MIDP-1.0
	 */
	public void drawSubstring(String str, int offset, int len, int x, int y, int anchor) {

		drawString(str.substring(offset, offset + len - 1), x, y, anchor);
	}

	/**
	 * @API MIDP-1.0
	 */
	public void drawChar(char character, int x, int y, int anchor) {
		char characters[] = new char[1];
		characters[0] = character;
		drawString(new String(characters), x, y, anchor);
	}

	/**
	 * @API MIDP-1.0
	 */
	public int getBlueComponent() {
		return _getAwtGraphics().getColor().getBlue();
	}

	/**
	 * @API MIDP-1.0
	 */
	public int getGreenComponent() {
		return _getAwtGraphics().getColor().getGreen();
	}

	/**
	 * @API MIDP-1.0
	 */
	public int getRedComponent() {
		return _getAwtGraphics().getColor().getRed();
	}

	/**
	 * @API MIDP-1.0
	 */
	public int getGrayScale() {
		return getRedComponent();
	}

	/**
	 * @API MIDP-1.0
	 */
	public int getTranslateX() {
		return translateX;
	}

	/**
	 * @API MIDP-1.0
	 */
	public int getTranslateY() {
		return translateY;
	}

	/**
	 * @API MIDP-1.0
	 */
	public void setStrokeStyle(int style) {
		strokeStyle = style;
	}

	/**
	 * @API MIDP-1.0
	 */
	public int getStrokeStyle() {
		return strokeStyle;
	}

	private int normalizeX(int x, int w, int anchor) {

		switch (anchor & (LEFT | RIGHT | HCENTER)) {
			case 0 :
			case LEFT :
				return x;
			case RIGHT :
				return x - w;
			case HCENTER :
				return x - w / 2;
		}
		throw new IllegalArgumentException();
	}

	private int normalizeY(int y, int h, int anchor) {

		switch (anchor & (TOP | BOTTOM | BASELINE | VCENTER)) {
			case 0 :
			case TOP :
				return y;
			case BOTTOM :
				return y - h;
			case VCENTER :
				return y - h / 2;
		}
		throw new IllegalArgumentException();
	}

	/**
	 * Copies a region of the specified source image to a location within the 
	 * destination, possibly transforming (rotating and reflecting) the image 
	 * data using the chosen transform function. 
	 * The destination, if it is an image, must not be the same image as the 
	 * source image. If it is, an exception is thrown. This restriction is 
	 * present in order to avoid ill-defined behaviors that might occur if 
	 * overlapped, transformed copies were permitted.
	 * 
	 * The transform function used must be one of the following, as defined 
	 * in the Sprite class:
	 * 
	 * Sprite.TRANS_NONE - causes the specified image region to be 
	 *                     copied unchanged
	 * Sprite.TRANS_ROT90 - causes the specified image region to be rotated 
	 *                      clockwise by 90 degrees.
	 * Sprite.TRANS_ROT180 - causes the specified image region to be rotated 
	 *                       clockwise by 180 degrees.
	 * Sprite.TRANS_ROT270 - causes the specified image region to be rotated 
	 *                       clockwise by 270 degrees.
	 * Sprite.TRANS_MIRROR - causes the specified image region to be reflected 
	 *                       about its vertical center.
	 * Sprite.TRANS_MIRROR_ROT90 - causes the specified image region to be 
	 *                             reflected about its vertical center and then 
	 *                             rotated clockwise by 90 degrees.
	 * Sprite.TRANS_MIRROR_ROT180 - causes the specified image region to be 
	 *                              reflected about its vertical center and then 
	 *                              rotated clockwise by 180 degrees.
	 * Sprite.TRANS_MIRROR_ROT270 - causes the specified image region to be reflected 
	 *                              about its vertical center and then rotated 
	 *                              clockwise by 270 degrees.
	 * 
	 * If the source region contains transparent pixels, the corresponding pixels 
	 * in the destination region must be left untouched. If the source region contains 
	 * partially transparent pixels, a compositing operation must be performed with the 
	 * destination pixels, leaving all pixels of the destination region fully opaque.
	 * 
	 * The (x_src, y_src) coordinates are relative to the upper left corner of the source 
	 * image. The x_src, y_src, width, and height parameters specify a rectangular region 
	 * of the source image. It is illegal for this region to extend beyond the bounds of 
	 * the source image. This requires that:
	 * 
	 * x_src >= 0
	 * y_src >= 0
	 * x_src + width <= source width
	 * y_src + height <= source height
	 * 
	 * The (x_dest, y_dest) coordinates are relative to the coordinate system of this 
	 * Graphics object. It is legal for the destination area to extend beyond the bounds 
	 * of the Graphics object. Pixels outside of the bounds of the Graphics object will 
	 * not be drawn.
	 * 
	 * The transform is applied to the image data from the region of the source image, 
	 * and the result is rendered with its anchor point positioned at location 
	 * (x_dest, y_dest) in the destination.
	 * 
	 * @param src the source image to copy from
	 * @param x_src the x coordinate of the upper left corner of the region within 
	 *              the source image to copy
	 * @param y_src the y coordinate of the upper left corner of the region within 
	 *              the source image to copy
	 * @param width the width of the region to copy
	 * @param height the height of the region to copy
	 * @param transform the desired transformation for the selected region being copied
	 * @param x_dest the x coordinate of the anchor point in the destination drawing area
	 * @param y_dest the y coordinate of the anchor point in the destination drawing area
	 * @param anchor the anchor point for positioning the region within the destination image
	 * @throws IllegalArgumentException if src is the same image as the destination of this Graphics object
	 * @throws NullPointerException if src is null
	 * @throws IllegalArgumentException if transform is invalid
	 * @throws IllegalArgumentException if anchor is invalid
	 * @throws IllegalArgumentException if the region to be copied exceeds the bounds of the source image
	 * 
	 * @API MIDP-2.0
	 */
	public void drawRegion(
		Image src,
		int x_src,
		int y_src,
		int width,
		int height,
		int transform,
		int x_dest,
		int y_dest,
		int anchor) {
		Image trans = Image.createImage(src, x_src, y_src, width, height, transform);
		drawImage(trans, x_dest, y_dest, anchor);
	}

	/**
	 * Copies the contents of a rectangular area (x_src, y_src, width, height) to a 
	 * destination area, whose anchor point identified by anchor is located at 
	 * (x_dest, y_dest). The effect must be that the destination area contains 
	 * an exact copy of the contents of the source area immediately prior to the 
	 * invocation of this method. This result must occur even if the source and 
	 * destination areas overlap. The points (x_src, y_src) and (x_dest, y_dest) 
	 * are both specified relative to the coordinate system of the Graphics object. 
	 * It is illegal for the source region to extend beyond the bounds of the graphic 
	 * object. This requires that:
	 * 
	 * x_src + tx >= 0
	 * y_src + ty >= 0
	 * x_src + tx + width <= width of Graphics object's destination
	 * y_src + ty + height <= height of Graphics object's destination
	 * 
	 * where tx and ty represent the X and Y coordinates of the translated origin 
	 * of this graphics object, as returned by getTranslateX() and getTranslateY(), 
	 * respectively.
	 * 
	 * However, it is legal for the destination area to extend beyond the bounds of 
	 * the Graphics object. Pixels outside of the bounds of the Graphics object will 
	 * not be drawn.
	 * 
	 * The copyArea method is allowed on all Graphics objects except those whose destination 
	 * is the actual display device. This restriction is necessary because allowing a copyArea 
	 * method on the display would adversely impact certain techniques for implementing 
	 * double-buffering.
	 * 
	 * Like other graphics operations, the copyArea method uses the Source Over Destination 
	 * rule for combining pixels. However, since it is defined only for mutable images, 
	 * which can contain only fully opaque pixels, this is effectively the same as pixel
	 *  replacement.
	 * 
	 * @param x_src the x coordinate of upper left corner of source area
	 * @param y_src the y coordinate of upper left corner of source area
	 * @param width the width of the source area
	 * @param height the height of the source area
	 * @param x_dest the x coordinate of the destination anchor point
	 * @param y_dest the y coordinate of the destination anchor point
	 * @param anchor the anchor point for positioning the region within the destination image
	 * @throws IllegalStateException if the destination of this Graphics object is the display device
	 * @throws IllegalArgumentException if the region to be copied exceeds the bounds of the source image
	 * 
	 * @API MIDP-2.0
	 */
	public void copyArea(
		int x_src,
		int y_src,
		int width,
		int height,
		int x_dest,
		int y_dest,
		int anchor) {
		if (image == null)
			throw new RuntimeException("Only valid for images");
		_getAwtGraphics().copyArea(
			x_src,
			y_src,
			width,
			height,
			normalizeX(x_dest, width, anchor),
			normalizeY(y_dest, height, anchor));
		//		System.out.println("Graphics.copyArea() called with no effect!");
	}

	/**
	 * Fills the specified triangle will the current color. The lines connecting each 
	 * pair of points are included in the filled triangle.
	 * 
	 * @param x1 the x coordinate of the first vertex of the triangle
	 * @param y1 the y coordinate of the first vertex of the triangle
	 * @param x2 coordinate of the second vertex of the triangle
	 * @param y2 coordinate of the second vertex of the triangle
	 * @param x3 coordinate of the third vertex of the triangle
	 * @param y3 coordinate of the third vertex of the triangle
	 * 
	 * @API MIDP-2.0
	 * @ME4SE UNIMPLEMENTED
	 */
	public void fillTriangle(int x1, int y1, int x2, int y2, int x3, int y3) {
		_getAwtGraphics().fillPolygon(new int[] { x1, x2, x3 }, new int[] { y1, y2, y3 }, 3);
	}

	/**
	 * Renders a series of device-independent RGB+transparency values in a 
	 * specified region. The values are stored in rgbData in a format with 
	 * 24 bits of RGB and an eight-bit alpha value (0xAARRGGBB), with the first 
	 * value stored at the specified offset. The scanlength specifies the 
	 * relative offset within the array between the corresponding pixels of
	 * consecutive rows. Any value for scanlength is acceptable (even negative 
	 * values) provided that all resulting references are within the bounds of the 
	 * rgbData array. The ARGB data is rasterized horizontally from left to right 
	 * within each row. The ARGB values are rendered in the region specified by x, y,
	 * width and height, and the operation is subject to the current clip region and 
	 * translation for this Graphics object.
	 * 
	 * Consider P(a,b) to be the value of the pixel located at column a and row b 
	 * of the Image, where rows and columns are numbered downward from the top 
	 * starting at zero, and columns are numbered rightward from the left starting 
	 * at zero. This operation can then be defined as:
	 * 
	 * P(a, b) = rgbData[offset + (a - x) + (b - y) * scanlength]
	 * 
	 * for
	 * 
	 * x <= a < x + width
	 * y <= b < y + height
	 * 
	 * This capability is provided in the Graphics class so that it can be used 
	 * to render both to the screen and to offscreen Image objects. The ability 
	 * to retrieve ARGB values is provided by the Image.getRGB(int[], int, int, 
	 * int, int, int, int) method. 
	 * 
	 * If processAlpha is true, the high-order byte of the ARGB format specifies 
	 * opacity; that is, 0x00RRGGBB specifies a fully transparent pixel and 0xFFRRGGBB 
	 * specifies a fully opaque pixel. Intermediate alpha values specify semitransparency. 
	 * If the implementation does not support alpha blending for image rendering operations, 
	 * it must remove any semitransparency from the source data prior to performing any 
	 * rendering. (See Alpha Processing for further discussion.) If processAlpha is false, 
	 * the alpha values are ignored and all pixels must be treated as completely opaque.
	 * 
	 * The mapping from ARGB values to the device-dependent pixels is platform-specific and 
	 * may require significant computation.
	 * 
	 * @param rgbData an array of ARGB values in the format 0xAARRGGBB
	 * @param offset the array index of the first ARGB value
	 * @param scanlength the relative array offset between the corresponding pixels in 
	 *                   consecutive rows in the rgbData array
	 * @param x the horizontal location of the region to be rendered
	 * @param y the vertical location of the region to be rendered
	 * @param width the width of the region to be rendered
	 * @param height the height of the region to be rendered
	 * @param processAlpha true if rgbData has an alpha channel, false if all 
	 *                     pixels are fully opaque
	 * @throws ArrayIndexOutOfBoundsException if the requested operation will attempt 
	 *                                         to access an element of rgbData whose index 
	 *                                         is either negative or beyond its length
	 * @throws NullPointerException - if rgbData is null
	 * 
	 * @API MIDP-2.0
	 * @ME4SE UNIMPLEMENTED
	 */
	public void drawRGB(
		int[] rgbData,
		int offset,
		int scanlength,
		int x,
		int y,
		int width,
		int height,
		boolean processAlpha) {
		int[] imageData = new int[width * height];
		int target = 0;

		for (int i = 0; i < height; i++) {
			System.arraycopy(rgbData, offset, imageData, target, width);
			offset += scanlength;
			target += width;
		}

		Image img = Image.createRGBImage(imageData, width, height, processAlpha);

		drawImage(img, x, y, TOP | LEFT);
	}

	/**
	 * Gets the color that will be displayed if the specified color is requested. 
	 * This method enables the developer to check the manner in which RGB values 
	 * are mapped to the set of distinct colors that the device can actually display. 
	 * For example, with a monochrome device, this method will return either 0xFFFFFF 
	 * (white) or 0x000000 (black) depending on the brightness of the specified color.
	 * 
	 * @param color the desired color (in 0x00RRGGBB format, the high-order byte is ignored)
	 * @return the corresponding color that will be displayed on the device's screen (in 0x00RRGGBB format)
	 * 
	 * @API MIDP-2.0
	 * @ME4SE UNIMPLEMENTED
	 */
	public int getDisplayColor(int color) {
		return ApplicationManager.manager.getDeviceColor(color);
	}

}
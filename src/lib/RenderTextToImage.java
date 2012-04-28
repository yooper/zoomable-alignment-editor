/*
	Copyright (C) 2009  Dan Cardin redbeardtechnologies@gmail.com

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
package lib;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.font.FontRenderContext;
import java.awt.font.TextAttribute;
import java.awt.font.TextLayout;
import java.awt.image.BufferedImage;
import java.text.AttributedCharacterIterator;
import java.text.AttributedString;

public class RenderTextToImage {
	
	private static RenderTextToImage instance;
	private final Font font;
	private int vertical_draw_height;
	private final FontRenderContext frc;
	
	
	public final FontRenderContext getFRC(){ 
		return frc; 
	}
	
	public final Font getFont(){ 
		return font; 
	}
	
	
	/**
	 * Set default values for local parameters
	 */
	private RenderTextToImage(){
		
		font = new Font("Monospaced", Font.BOLD, 10);
		frc = new FontRenderContext(null, true, true);
		this.vertical_draw_height = 5;
	}
	
	public void setVerticalDrawHeight(int vertical_draw_height){
		
		this.vertical_draw_height = vertical_draw_height;
	}
	/**
	 * Create the singleton and return it
	 * @return RenderTextToImage instance
	 */
	public static RenderTextToImage getInstance(){
		if(instance == null){
			instance = new RenderTextToImage();
		}
		return instance;
	}
	
	public final BufferedImage createImage(String data)
	{		
		TextLayout layout = null;
		AttributedCharacterIterator itr = null;
		
		if (data != null && data.length() > 0){			
			AttributedString atString = colorSequence(data);
			atString.addAttribute(TextAttribute.FONT, this.font);
			itr = atString.getIterator();
			layout =  new TextLayout(itr, frc);
		}	
		
		if(layout == null){
			return null; 
		}
	    
		BufferedImage fgImage = new BufferedImage((int)layout.getVisibleAdvance(),font.getSize(), BufferedImage.TYPE_INT_RGB);
	    Graphics2D graphics = fgImage.createGraphics();
	    
	    graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	    graphics.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
	    graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
	    graphics.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
	    graphics.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
	    
	    graphics.setColor(Color.BLACK);
	    graphics.drawString(itr, 0, this.vertical_draw_height);
	    graphics.dispose();
	    return fgImage;	
	}
	
	private final AttributedString colorSequence(String text)
	{	
		int i = 0;
		int j;
		final int len = text.length();
		AttributedString sequence_string = new AttributedString(text);
		text = text.toUpperCase();
		
		for(; i< len; i++){
			
			for(j = i+1; j<text.length();j++){
				if(!ColorSchemaManager.getInstance().getColor(text.charAt(i)).equals(ColorSchemaManager.getInstance().getColor(text.charAt(j)))){
					 break;
				}
			}
			sequence_string.addAttribute(TextAttribute.BACKGROUND, ColorSchemaManager.getInstance().getColor(text.charAt(i)), i, j);
			i = j - 1;
		}
		return sequence_string;	
	}
	
}

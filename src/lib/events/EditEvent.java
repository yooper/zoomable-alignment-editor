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

package lib.events;

import java.awt.font.TextHitInfo;
import java.awt.font.TextLayout;

import lib.CanvasManager;
import lib.RenderTextToImage;

import edu.umd.cs.piccolo.event.PBasicInputEventHandler;
import edu.umd.cs.piccolo.event.PInputEvent;
import edu.umd.cs.piccolo.event.PPanEventHandler;
import edu.umd.cs.piccolo.event.PZoomEventHandler;

public class EditEvent extends PBasicInputEventHandler {

	public static CanvasManager canvas_manager;
	protected static PPanEventHandler ppan;
	protected static PZoomEventHandler pzoom;
	private static int xpos;
	private static int prev_xpos;
	private int row_index;
	
	public EditEvent(int row_index)
	{
		this.row_index = row_index;
	}
	
	protected static final int getHitIndex(int row_index, float x, float y)
	{

		String sequence_data = canvas_manager.getSequencePImages().get(row_index).getSequence().getSequenceData();
		
		final TextLayout textLayout = 
			new TextLayout(sequence_data, RenderTextToImage.getInstance().getFont(), 
					RenderTextToImage.getInstance().getFRC());
		
		final TextHitInfo text_hit_info = textLayout.hitTestChar(x,y);
		
		return text_hit_info.getInsertionIndex();
		
	}
	
	public void mousePressed(PInputEvent e) {
	
		super.mousePressed(e);		
		
		if(e.isShiftDown() && e.isLeftMouseButton()){
			canvas_manager.disablePanAndZoom();
	
			prev_xpos = getHitIndex(row_index, (float) e.getPosition().getX(), (float) e.getPosition().getY()); 	
		}
	}
	
	public void mouseDragged(PInputEvent e){
		
		super.mouseDragged(e);
		
		if(e.isShiftDown() && e.isLeftMouseButton())
		{
			xpos = getHitIndex(row_index, (float) e.getPosition().getX(), (float) e.getPosition().getY()); 
			// add gaps if user drags mouse to the right 
			if(prev_xpos < xpos){
				addGap(prev_xpos);
			}
				
			// remove gaps if the user drags mouse to the left 
			else if(prev_xpos > xpos){
				removeGap(prev_xpos);
			}
			prev_xpos = xpos;
		}
	}
	
	public void mouseReleased(PInputEvent e){
		super.mousePressed(e);
		canvas_manager.enablePanAndZoom();
	}
	
	public void addGap(int column_index){
		canvas_manager.editCanvasEvent(row_index, column_index, true);	 	 
	}
	
	public void removeGap(int column_index){
		canvas_manager.editCanvasEvent(row_index, column_index, false);
	}
}

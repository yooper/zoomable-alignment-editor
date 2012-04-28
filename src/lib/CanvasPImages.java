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

import java.util.Vector;
import lib.events.EditEvent;
import edu.umd.cs.piccolo.nodes.PImage;

public class CanvasPImages extends Thread{

	protected CanvasManager canvas_manager;
	protected Vector<SequencePImage> sequence_pimages = null;
	
	public CanvasPImages(CanvasManager canvas_manager) {
		this.canvas_manager = canvas_manager;
		EditEvent.canvas_manager = canvas_manager;
	}
	

	public void run(){
		updateCanvas();
	}
	
	/*
	 * 
	 */
	public void updateCanvas(){
		
		if(canvas_manager.getSequencePImages() == null || canvas_manager.getSequencePImages().isEmpty()){
			return; 
		}
		else{

			canvas_manager.getPCanvas().getLayer().removeAllChildren();
			
			for(int index=0;index<canvas_manager.getSequencePImages().size();index++){

				updateCanvasNode(index);
				
				//add mouse listeners to image nodes
					
				//((PImage)visualized_image_nodes.get(i)).addInputEventListener(new EditGapImageEvent(i));}
			}
		}
		
	}
	
	
	
	public void updateCanvasNode(int index){
			
		PImage node = canvas_manager.getSequencePImages().get(index).updateSequencePImage();
			
		if(node.getOffset().getY() == 0){
			node.offset(0, index * canvas_manager.getVerticalAxisOffSet());
			node.addInputEventListener(new EditEvent(index));
		}
		
		node.addAttribute("sequence_name", canvas_manager.getSequencePImages().get(index).getSequence().getSequenceName());
			
		canvas_manager.getPCanvas().getLayer().addChild(node);
		
		node.repaint();
			
	}	
}

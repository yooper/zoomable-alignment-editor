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


import edu.umd.cs.piccolo.PCanvas;
import edu.umd.cs.piccolo.event.PPanEventHandler;
import edu.umd.cs.piccolo.event.PZoomEventHandler;
import edu.umd.cs.piccolo.nodes.PImage;

public class CanvasManager{

	protected PCanvas canvas;
	protected float image_height;
	protected float image_width;
	protected double vertical_axis_offset;
	protected CanvasPImages canvas_pimages;
	protected CanvasPPathes canvas_ppaths;
	protected Vector<SequencePImage> sequence_pimages;
	protected String search_key;
	protected Thread ppathes_thread;
	protected Thread pimages_thread;
	protected PPanEventHandler pan_event_handler;
	protected PZoomEventHandler zoom_event_handler;
	
	
	public CanvasManager(PCanvas canvas, float image_height, float image_width, float vertical_axis_offset){	
		this.canvas = canvas;
		this.image_height = image_height;
		this.image_width = image_width;
		this.vertical_axis_offset = vertical_axis_offset;
		canvas_pimages = new CanvasPImages(this);
		canvas_ppaths = new CanvasPPathes(this);
		
		pan_event_handler = canvas.getPanEventHandler();
		zoom_event_handler = canvas.getZoomEventHandler();
		
	}

	public PCanvas getPCanvas(){
		return canvas;
	}
	
	public void editCanvasEvent(int row_index, int column_index, boolean add_gap){
	
		if(add_gap){
			getSequencePImages().get(row_index).getSequence().addGap(column_index);
		}
		else{
			getSequencePImages().get(row_index).getSequence().removeGap(column_index);
		}
		
		canvas_pimages.updateCanvasNode(row_index);
		invokePPathsUpdate();
	}
	
	public void disablePanAndZoom(){
		canvas.setPanEventHandler(null);
		canvas.setZoomEventHandler(null);
	}
	
	public void enablePanAndZoom(){
		if(canvas.getPanEventHandler() == null && canvas.getZoomEventHandler() == null){
			canvas.setPanEventHandler(pan_event_handler);
			canvas.setZoomEventHandler(zoom_event_handler);
		}
	}
	
	
	public double getVerticalAxisOffSet(){
		return vertical_axis_offset;
	}
	
	public float getImageHeight(){
		return image_height;
	}
	
	public float getImageWidth(){
		return image_width;
	}
	
	public Vector<SequencePImage> getSequencePImages(){
		return sequence_pimages;
	}
	
	public void setSequencePImages(Vector<SequencePImage> sequence_pimages){
		this.sequence_pimages = sequence_pimages;
	}
	
	public void setSearchKey(String search_key){
		this.search_key = search_key;
	}
	
	public String getSearchKey(){
		return search_key;
	}
	
	public void invokePImagesUpdate(){
		if(pimages_thread != null && pimages_thread.isAlive())
		{
			try {
				pimages_thread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			finally{
				pimages_thread = null;
			}
		}	
		pimages_thread = new Thread(canvas_pimages);
		pimages_thread.start();
	}
	
	public void invokePPathsUpdate(){
		if(ppathes_thread != null && ppathes_thread.isAlive())
		{
			try {
				ppathes_thread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			finally{
				ppathes_thread = null;
			}
		}	
		ppathes_thread = new Thread(canvas_ppaths);
		ppathes_thread.start();
	}
	
	
}

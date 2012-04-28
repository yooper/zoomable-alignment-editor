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

import java.awt.BasicStroke;
import java.awt.Color;
import java.util.Vector;
import edu.umd.cs.piccolo.nodes.PPath;

public class CanvasPPathes implements Runnable {

	protected CanvasManager canvas_manager;
	protected SearchMatchManager search_match_manager;
	private static Color strokeColor = Color.black;
	protected Vector<PPath> ppathes_vector = new Vector<PPath>();


	public CanvasPPathes(CanvasManager canvas_manager) {
		this.canvas_manager = canvas_manager;
		search_match_manager = new SearchMatchManager();
	}

	public void run() {
		
		canvas_manager.getPCanvas().getLayer().removeChildren(ppathes_vector);
		
		ppathes_vector.clear();
		
		if(canvas_manager.getSearchKey() == null || canvas_manager.getSearchKey() == ""){
			return;
		}
		
		search_match_manager.searchForMatches(canvas_manager.getSearchKey(), canvas_manager.getSequencePImages());
		
		displayAllSearchMatches();
	}
	
	
	protected void displayAllSearchMatches(){
		
		Vector<Vector<SearchMatch>> search_matches = search_match_manager.getSearchMatches();
		
		for(int row = 0; row < search_matches.size(); row++){
			for(int column = 0; column < search_matches.get(row).size(); column++){
				SearchMatch search_match = search_matches.get(row).get(column);
				ppathes_vector.addElement(getSearchMatchPPathNode(search_match));
			}
		}
		canvas_manager.getPCanvas().getLayer().addChildren(ppathes_vector);
	}
	
	public PPath getSearchMatchPPathNode(SearchMatch search_match)
	{
		
		double x1,x2,y;
		final float midpt = canvas_manager.getImageHeight()/2f;
		final float stroke_size = 3.5f;
		
		//add striping over search hits
		x1 =  search_match.getColumn() * canvas_manager.getImageWidth();
		//this changes for each operating system
		y = search_match.getRow() * canvas_manager.getImageHeight() + midpt;
		x2 = (search_match.getColumn() + search_match.getLength()) * canvas_manager.getImageWidth();
				
		PPath node = PPath.createLine((float)x1,(float)y,(float)x2,(float)y);
		node.setStrokePaint(strokeColor);
		node.setStroke(new BasicStroke(stroke_size));
		return node;
		
	}
}

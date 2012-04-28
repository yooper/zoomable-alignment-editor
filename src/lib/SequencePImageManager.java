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

public class SequencePImageManager {

	protected Vector<SequencePImage> sequence_pimages;
	
	public SequencePImageManager(){
		
		sequence_pimages = new Vector<SequencePImage>();
	}
	
	public void createSequencePImageVector(Vector<Sequence> sequences){
		
		if(sequence_pimages.size() > 0){
			deleteSequencePImagesVector();
		}
		
		for(int i=0; i <sequences.size(); i++){
			
			sequence_pimages.addElement(new SequencePImage( sequences.get(i)));
		}		
		
	}
	
	public void deleteSequencePImagesVector() {
		
		for(int i = 0; i < sequence_pimages.size(); i++){
			sequence_pimages.get(i).finalize();
		}
		
	}

	public Vector<SequencePImage> getSequencePImageVector(){
		return sequence_pimages;
	}
	
	public void finalize(){
		deleteSequencePImagesVector();
		sequence_pimages.clear();
	}
	
}

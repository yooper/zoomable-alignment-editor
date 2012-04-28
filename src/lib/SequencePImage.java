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

import java.awt.image.BufferedImage;

import edu.umd.cs.piccolo.nodes.PImage;

public class SequencePImage{

	protected PImage sequence_image = null;
	protected Sequence sequence = null;
	
	public SequencePImage(Sequence sequence){
		this.sequence = sequence;
	}
	
	public PImage getSequencePImage(){
		if(sequence_image == null){
			sequence_image = new PImage(getSequenceBufferedImage());
		}
		return sequence_image;
	}
	
	public PImage updateSequencePImage(){
		if(sequence_image != null){
			sequence_image.setImage(getSequenceBufferedImage());
		}
		return getSequencePImage();
		
	}
	
	public Sequence getSequence(){
		return sequence;
	}
	
	public void finalize(){
		sequence.finalize();
	}
	
	protected BufferedImage getSequenceBufferedImage(){
		return RenderTextToImage.getInstance().createImage(sequence.getSequenceData());
	}
	
}

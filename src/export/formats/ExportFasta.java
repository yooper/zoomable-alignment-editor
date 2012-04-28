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


package export.formats;

import java.util.Vector;

import lib.Sequence;
import lib.SequencePImage;
import interfaces.ExportFormat;


public class ExportFasta implements ExportFormat{

	public ExportFasta(){
		
	}
	
	public String exportFormatToString(Vector<SequencePImage> sequence_pimages){
		
		
		final int newline_every = 80;
		
		StringBuffer buffer = new StringBuffer();
		Sequence sequence = null; 
		
		StringBuffer formatter = new StringBuffer();
		
		for(int index=0; index< sequence_pimages.size(); index++){
			
			sequence = sequence_pimages.get(index).getSequence();
			buffer.append(">"+sequence.getSequenceName());
			
			formatter.append(sequence.getSequenceData());
			
			for(int ii=0;ii<formatter.length();ii++)
			{
				if(ii%newline_every==0)
					formatter.insert(ii, '\n');
				
			}
			buffer.append(formatter+"\n");
			formatter.delete(0, formatter.length()-1);
		}
		
		return buffer.toString();
	}
}

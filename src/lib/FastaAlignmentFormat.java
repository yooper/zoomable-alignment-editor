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

import interfaces.AlignmentFormatType;

import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import exceptions.IllegalAlignmentFormatException;

public class FastaAlignmentFormat implements AlignmentFormatType {


	/**
	 * 
	 */
	public Vector<Sequence> getSequences(StringBuffer alignment_data_buffer) throws IllegalAlignmentFormatException {
	
		String [] lines = alignment_data_buffer.toString().split("\n");
        String sequence_name = null;
        StringBuffer sequence_data = new StringBuffer();
		Vector<Sequence> sequences = new Vector<Sequence>();
        
        Pattern pattern = Pattern.compile("\\s*^>");
	    Matcher match = pattern.matcher("");
		Sequence sequence = null;
	    
	    for(int i=0;i<lines.length;i++){
	    	match = match.reset(lines[i]);
	        if(match.find()){
	        	if(sequence_name != null){
	                
					sequence = new Sequence(new String(sequence_name.substring(1)), new String(sequence_data.toString()));
	                
					sequences.add(sequence);	
	                
	                sequence_name = lines[i].substring(0,lines[i].length());
	                sequence_data.setLength(0);
				}
				
	            else{
	            	sequence_name = lines[i];
	            }
	        }	
			
			/*improperly formated FASTA file*/
			
	        else if(sequence_name==null){
	        	throw new IllegalAlignmentFormatException(
	        			"Alignment Source line "+i+" has an error. Sequence name does not exist.");
	        }
				
	        else{
	        	sequence_data.append(lines[i]);				
	        }
	    }

	    sequence = new Sequence(new String(sequence_name.substring(1)), new String(sequence_data.toString()));	
		//get the last sequence
        sequences.add(sequence);		
		
		return sequences;
	}
}

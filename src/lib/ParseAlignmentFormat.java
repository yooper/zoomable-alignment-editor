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

import exceptions.IllegalAlignmentFormatException;
import exceptions.UnknownAlignmentFormatException;

public class ParseAlignmentFormat {

	StringBuffer alignment_data_buffer;
	AlignmentFormatType alignment_format;
	
	public ParseAlignmentFormat(StringBuffer alignment_data_buffer)
	{
		this.alignment_data_buffer = alignment_data_buffer;
	}
	
	public Vector<Sequence> getSequences() throws UnknownAlignmentFormatException, IllegalAlignmentFormatException
	{
		String alignment_format_type = detectAlignmentFormatType();
		
		if(alignment_format_type == "FASTA"){
			alignment_format = new FastaAlignmentFormat();
		}
		else if(alignment_format_type == "NEXUS"){
			alignment_format = new NexusAlignmentFormat();
		}
		
		return alignment_format.getSequences(this.alignment_data_buffer);
	}

    /**
     * This method is intended to detect the format of the alignment file being
     * Inputed by the user through the text dialog or by being a loaded based upon the
     * in file parameter. 
     * 
     * @param lines a sequence alignment files that is stored in an array of Strings
     * @return String containing the format of the sequence alignment supplied
     */
    public String detectAlignmentFormatType() throws UnknownAlignmentFormatException {
        
    	String [] lines = alignment_data_buffer.toString().split("\n");
    	
    	for(int i=0; i<lines.length; i++)
        {
            if(lines[i].startsWith(">")){
                return "FASTA";
            }
			else if(lines[i].indexOf("#NEXUS") >= 0 || lines[i].indexOf("#nexus") >= 0){
			    return "NEXUS";
            }
        }	
        throw new UnknownAlignmentFormatException("Unknown Format"); // return null if unknown format... Should throw an exception
    }
}

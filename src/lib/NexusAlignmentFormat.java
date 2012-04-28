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

import java.util.Hashtable;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import exceptions.IllegalAlignmentFormatException;
import interfaces.AlignmentFormatType;

public class NexusAlignmentFormat implements AlignmentFormatType {

	@Override
	public Vector<Sequence> getSequences(StringBuffer alignment_data_buffer) throws IllegalAlignmentFormatException {
	
		/*
		 * Remove Quotes
		 */
		for(int index=0; index < alignment_data_buffer.length(); index++){
			if(alignment_data_buffer.charAt(index) == '\''){
				alignment_data_buffer.setCharAt(index, ' ');
			}
		}
		
		return parseSequences(alignment_data_buffer.toString().split("\n"));
	}
	
	private String [] removeContentBetweenBraces(String [] lines)
	{
		
		boolean multiline = false;
		int left;
		int right;
		
		for(int i=0;i<lines.length;i++)
		{
			/*remove all braces from a single line*/
			String tmp = lines[i].replaceAll("\\[.*\\]", "");
			
			if((left = tmp.indexOf("[")) >= 0)
			{
				multiline = true;
				
				if(left == 0) tmp = "";
				
				else tmp = tmp.substring(0,left-1);
			}
			/*no closing brace found yet*/
			else if(multiline && (right = tmp.indexOf("]")) >= 0)
			{
				multiline = false;
				tmp = tmp.substring(right+1);
			}
			else if(multiline)
			{
				//remove entire line
				tmp = "";
			}
			
			lines[i] = tmp;

		}	
		return lines;		
	}
	
	private String [] preFilterLines(String [] lines)
	{		
		lines = removeContentBetweenBraces(lines);
		
		lines = removeEmptyLines(lines);
		
		return lines;
	}
	
	private String[] removeEmptyLines(String[] lines) {
		// TODO Auto-generated method stub
		String [] tmp = new String[lines.length];
		
		Pattern pattern = Pattern.compile("^\\s*^");
		Matcher match;
		int new_size = 0;
		
		for(int i=0;i<lines.length;i++)
		{
			match = pattern.matcher(lines[i]);
			
			//include if an empty line is not found
			if(!match.matches())
			{
				tmp[new_size++] = lines[i];
			}
			
			
		}
		
		//make a final copy 
		String [] new_lines = new String[new_size];
		for(int i=0;i<new_size;i++)
		{
			new_lines[i] = new String(tmp[i]);
		}

		return new_lines;
	}

	public Vector<Sequence> parseSequences(String[] lines) {
		
		Hashtable<String, Integer> lookup = new Hashtable<String, Integer>();
		Vector<Sequence> sequences = new Vector<Sequence>();
		
		final int SEQUENCE_NAME = 1;
		String sequence_name;
		final int SEQUENCE_DATA = 2;
		String sequence_data;
		int counter = 0;
		
		lines = preFilterLines(lines);
		Pattern sequence = Pattern.compile("\\s*(\\S+)\\s+(\\S+)");
		Matcher match_sequence;
		
		Pattern end = Pattern.compile("end;$",Pattern.CASE_INSENSITIVE);
		
		Matcher match_end;
		
		Pattern matrix = Pattern.compile("\\s*matrix", Pattern.CASE_INSENSITIVE);
		Matcher match_matrix;
		
		for(int i=0;i<lines.length;i++)
		{
			
			match_matrix = matrix.matcher(lines[i]);
			
			if(match_matrix.matches())
			{
				
				for(int j=i+1;j<lines.length;j++)
				{
					
					
					match_end = end.matcher(lines[j]);
					//match end; statement 
					if(match_end.matches() || lines[j].startsWith(";")) break;
					
					match_sequence = sequence.matcher(lines[j]);
					
					//should find a name and the sequence data
					if(match_sequence.find())	
					{
						
					    sequence_name = match_sequence.group(SEQUENCE_NAME);
						sequence_data = match_sequence.group(SEQUENCE_DATA);
						
						if(lookup.containsKey(((String)sequence_name)) )
						{
							int index = ((Integer)lookup.get(sequence_name)).intValue();
							Sequence seq = (Sequence) sequences.get(index);
							seq.append_data(sequence_data);
							
						}
							
						//Insert new record into hashtable 
						else
						{
							lookup.put(sequence_name,Integer.valueOf(counter));
							//Insert record into vector
							sequences.add(new Sequence(new String(sequence_data.toString()),new String(sequence_name)));
							
						}
				
					}
					
					else ; //error?
			
				}
			}
		}
		return sequences;
	}
}

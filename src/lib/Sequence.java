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

/**
 * 
 * @author 
 * This class stores text and the name of the text
 *
 */
public class Sequence {

	protected String sequence_name = null;
	protected static char gap_symbol = '-';
	protected StringBuffer sequence_data;
	protected static int max_of_sequences_lengths = 0;

	/**
	 * 
	 * @param sequence_name if null assign a name 'unknown sequence'
	 * @param sequence_data text data 
	 */
	public Sequence(String sequence_name, String sequence_data) 
	{		
        if(sequence_name == null){
        	this.sequence_name = new String("unknown sequence");
        }
        else{
             this.sequence_name = sequence_name;
        }
        
        this.sequence_data = new StringBuffer(sequence_data);
	}
	
	public void append_data(String append_sequence_data){
		this.sequence_data.append(append_sequence_data);
		this.checkMaxSequenceLength();
	}
	
	public void addGap(int column_index){
		
		if(column_index <= sequence_data.length()){
			this.sequence_data.insert(column_index,gap_symbol);
			this.checkMaxSequenceLength();
		}
	}
	
	protected void checkMaxSequenceLength()
	{		
		if(this.sequence_data.length() > max_of_sequences_lengths){
			max_of_sequences_lengths = this.getSequenceDataLength();
		}		
	}
	
	static public int getMaxOfSequencesLength(){
        return Sequence.max_of_sequences_lengths;
	}
	
	public boolean removeGap(int column_index){
		
        if(column_index <= sequence_data.length() && 
        	sequence_data.charAt(column_index) == Sequence.gap_symbol){
        	
        	this.sequence_data.deleteCharAt(column_index);
        	return true;       	
        }
        return false;
	}
	
	public String getSequenceData(){
        return this.sequence_data.toString();
	}
	
	public int getSequenceDataLength(){
        return this.sequence_data.length();	
    }
	
	public String getSequenceName(){
		return sequence_name;
	}	
	
	/**
	 * 
	 * @param gs The gap symbol character. In most cases this is the '-' character
	 * '-'
	 */
	public static void setGapSymbol(char gs){ gap_symbol = gs; }
	
	/**
	 * 
	 * @return char that is the current gap symbol
	 */
	public static char getGapSymbol(){ return gap_symbol; }
	
	/**
	 * Remove all data from the StringBuffer
	 */
	public void finalize(){
		this.sequence_data.delete(0, this.getSequenceDataLength());
		this.sequence_name = null;
	}
}

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

import interfaces.AlignmentData;
import interfaces.AlignmentDataFactory;
import interfaces.AlignmentFormatType;

import java.util.Vector;

import exceptions.IllegalAlignmentFormatException;
import exceptions.UnknownAlignmentFormatException;

public class AlignmentFormatManager implements AlignmentDataFactory{
	
	protected boolean remote_data_source = false;
	protected StringBuffer alignment_data_buffer;
	
	protected Vector<Sequence> sequences;
	AlignmentData alignment_data;
	
	public AlignmentFormatManager(String source, boolean remote_data_source){

		this.remote_data_source = remote_data_source;
		alignment_data = getAlignmentData();
		alignment_data_buffer = alignment_data.loadAlignmentData(source);
	}
	
	public StringBuffer getAlignmentDataBuffer(){
		return alignment_data_buffer;
	}
	
	public Vector<Sequence> getSequences() 
		throws UnknownAlignmentFormatException, IllegalAlignmentFormatException{
		
		ParseAlignmentFormat new_alignment_format = new ParseAlignmentFormat(alignment_data_buffer);
		sequences =  new_alignment_format.getSequences();

		return sequences;
	}
	
	public void finalize()
	{
		
		if(sequences != null){ 
			sequences.clear();
		}
		if(alignment_data_buffer != null){
			alignment_data_buffer.delete(0, alignment_data_buffer.length());
		}
		
	}

	@Override
	public AlignmentData getAlignmentData() {
		
		if(remote_data_source){
			return new RemoteFileAlignmentData();
		}
		else{
			return new DialogAlignmentData();
		}
	}
}

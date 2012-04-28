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

import java.util.Hashtable;
import java.util.Vector;

import lib.Sequence;
import lib.SequencePImage;
import interfaces.ExportFormat;

public class ExportNexus implements ExportFormat {

	public String exportFormatToString(Vector<SequencePImage> sequence_pimages) {
		final int max_name_length = getMaxNameLength(sequence_pimages);
		final int white_space_separation = 2;
		final int max_sequence_length = getMaxSequenceDataLength(sequence_pimages);
		int len = 0;
		
		StringBuffer buffer = new StringBuffer();
		Sequence sequence = null; 
		
		
		buffer.append("#NEXUS\n");
		buffer.append("BEGIN DATA;\n");
		buffer.append("dimensions ntax="+sequence_pimages.size()+" nchar="+max_sequence_length+"\n");
		buffer.append("datatype="+guessAcidType(sequence_pimages)+"\n");
		buffer.append("\nmatrix\n");
		
		
		for(int index=0; index< sequence_pimages.size(); index++)
		{
			sequence = sequence_pimages.get(index).getSequence();
			buffer.append(sequence.getSequenceName());
			len = sequence.getSequenceName().length();
			
			for(int jj=max_name_length-len+white_space_separation;jj>0;jj--){
				buffer.append(' ');
			}
					
			buffer.append(sequence.getSequenceData());
			
			
			/*insure all sequence lengths are equal*/
			if(sequence.getSequenceDataLength() < max_sequence_length){
				for(int ii=0;ii<=max_sequence_length-sequence.getSequenceDataLength();ii++){
					buffer.append(Sequence.getGapSymbol());
				}
			}
			buffer.append('\n');
			
		}
		buffer.append(";\nend;\n");
		
		return buffer.toString();
	}

	protected int getMaxNameLength(Vector<SequencePImage> sequence_pimages){
		int max = -1;
		
		for(int index=0; index < sequence_pimages.size(); index++){
			if(max < sequence_pimages.get(index).getSequence().getSequenceName().length()){
				max = sequence_pimages.get(index).getSequence().getSequenceName().length();
			}
		}
		return max;
	}
	
	protected int getMaxSequenceDataLength(Vector<SequencePImage> sequence_pimages){
		int max = -1;
		
		for(int index=0; index < sequence_pimages.size(); index++){
			if(max < sequence_pimages.get(index).getSequence().getSequenceDataLength()){
				max = sequence_pimages.get(index).getSequence().getSequenceDataLength();
			}
		}
		return max;		
	}
	
	protected String guessAcidType(Vector<SequencePImage> sequence_pimages){
		
		int in_common = 0;
		String acid_type;
			
		for(int index=0;index<sequence_pimages.size();index++){
			
			String uc_sequence_data = sequence_pimages.get(index).getSequence().getSequenceData().toUpperCase();
			
				for(int j=0;j<uc_sequence_data.length();j++){
					
					if(dna_hash.containsKey(""+uc_sequence_data.charAt(j))){
						in_common++;
					}
					if(rna_hash.containsKey(""+uc_sequence_data.charAt(j))){
						in_common++;
					}
					if(aa_hash.containsKey(""+uc_sequence_data.charAt(j))){
						in_common++;
					}
					
					if(in_common == 1)
					{
						if(aa_hash.containsKey(""+uc_sequence_data.charAt(j))){
							return "AA";
						}
							 
						else if(rna_hash.containsKey(""+uc_sequence_data.charAt(j))){
							return "RNA";
						}	
					}
					else{
						in_common = 0;
					}
				}
			}
			return "DNA";	
	}
	
	private static Hashtable<String, Boolean> dna_hash;
	
	static {
		
		dna_hash = new Hashtable<String, Boolean>(15,.75f);
		dna_hash.put("A",Boolean.TRUE);
		dna_hash.put("C",Boolean.TRUE);
		dna_hash.put("T",Boolean.TRUE);
		dna_hash.put("G",Boolean.TRUE);
		dna_hash.put("W",Boolean.TRUE);
		dna_hash.put("R",Boolean.TRUE);
		dna_hash.put("K",Boolean.TRUE);
		dna_hash.put("Y",Boolean.TRUE);
		dna_hash.put("S",Boolean.TRUE);
		dna_hash.put("M",Boolean.TRUE);
		dna_hash.put("B",Boolean.TRUE);
		dna_hash.put("H",Boolean.TRUE);
		dna_hash.put("D",Boolean.TRUE);
		dna_hash.put("V",Boolean.TRUE);
		dna_hash.put("N",Boolean.TRUE);
		dna_hash.put("M",Boolean.TRUE);
		dna_hash.put("X",Boolean.TRUE);
	}
	private static Hashtable<String, Boolean> rna_hash;
	static {
		
		rna_hash = new Hashtable<String, Boolean>(4,.75f);
		rna_hash.put("A",Boolean.TRUE);
		rna_hash.put("C",Boolean.TRUE);
		rna_hash.put("G",Boolean.TRUE);
		rna_hash.put("U",Boolean.TRUE);
		
	}
	private static Hashtable<String, Boolean> aa_hash;
	
	static {
		
		aa_hash = new Hashtable<String, Boolean>(20,.75f);
		aa_hash.put("A",Boolean.TRUE);
		aa_hash.put("R",Boolean.TRUE);
		aa_hash.put("N",Boolean.TRUE);
		aa_hash.put("D",Boolean.TRUE);
		aa_hash.put("C",Boolean.TRUE);
		aa_hash.put("E",Boolean.TRUE);
		aa_hash.put("Q",Boolean.TRUE);
		aa_hash.put("G",Boolean.TRUE);
		aa_hash.put("H",Boolean.TRUE);
		aa_hash.put("I",Boolean.TRUE);
		aa_hash.put("L",Boolean.TRUE);
		aa_hash.put("K",Boolean.TRUE);
		aa_hash.put("M",Boolean.TRUE);
		aa_hash.put("F",Boolean.TRUE);
		aa_hash.put("P",Boolean.TRUE);
		aa_hash.put("S",Boolean.TRUE);
		aa_hash.put("T",Boolean.TRUE);
		aa_hash.put("W",Boolean.TRUE);
		aa_hash.put("X",Boolean.TRUE);
		aa_hash.put("V",Boolean.TRUE);
	}
}

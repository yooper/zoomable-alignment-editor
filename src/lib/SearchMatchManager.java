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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SearchMatchManager {

	protected static SearchMatchManager instance = null;
	protected String search_key = "";
	protected Vector<Vector<SearchMatch>> search_matches = null;
	
	
	public SearchMatchManager(){
	}

	public Vector<Vector<SearchMatch>> getSearchMatches(){
		return search_matches;
	}
	
	/**
	 * Set the internal search key for this data object. 
	 * @param search_key
	 */
	protected void setSearchKey(String search_key){
		this.search_key = search_key;
	}
	
	/**
	 * 
	 * @param search_key
	 * @param manager
	 */
	public void searchForMatches(String search_key, Vector<SequencePImage> sequence_pimages){

		if(search_key == null || search_key == ""){	
			//remove all matches
			return;
		}
		else{
			this.setSearchKey(search_key);
		}
		
		searchAllSequences(sequence_pimages);
		
	}
	
	
	protected void searchAllSequences(Vector<SequencePImage> sequence_pimages){
		
		search_matches = 
				new Vector<Vector<SearchMatch>>(sequence_pimages.size());
		
		for(int index = 0; index < sequence_pimages.size(); index++){
			search_matches.addElement(getSequenceSearchMatches(index, 
				sequence_pimages.get(index))
			);
		}
	}
	
	/**
	 * 
	 * @param index
	 * @return Vector containing all of the search matches...
	 */
	protected Vector<SearchMatch> getSequenceSearchMatches(int index, SequencePImage sequence_pimage){
		
		Pattern pattern = Pattern.compile(search_key, Pattern.CASE_INSENSITIVE);
		String sequence_data = (String) sequence_pimage.getSequence().getSequenceData();
		
		Vector<SearchMatch> row_search_matches = new Vector<SearchMatch>();
		
		Matcher match = pattern.matcher(sequence_data);
			
		while(match.find()){
			row_search_matches.addElement(new SearchMatch (index, match.start(), match.end()-match.start()));
		}
			
		return row_search_matches;
	}
	
	
	
	
}

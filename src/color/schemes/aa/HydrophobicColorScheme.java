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

package color.schemes.aa;

import java.awt.Color;
import java.util.Hashtable;
import java.util.Iterator;


import lib.AminoAcids;

import interfaces.ColorScheme;

public class HydrophobicColorScheme extends AminoAcids implements ColorScheme {

	public String getAcidType() {
		return "AA";
	}

	public String getColorSchemeName() {
		return "Hydrophobic";
	}

	public Hashtable<Character, Color> getColorTable() {
		Hashtable<Character, Color> color_table = new Hashtable<Character, Color>(aminos.size());
		Iterator<String> itr = aminos.iterator();
		float [] hydrophobic_values = getHydrophobicValues();
		int hydrophobic_index = 0;
		while(itr.hasNext()) {
		    String element = itr.next(); 
		    float lerp_value = lerp(hydrophobic_values[hydrophobic_index++], getMinValue(hydrophobic_values), getMaxValue(hydrophobic_values));		    
		    color_table.put(new Character(element.charAt(0)), makeColor(lerp_value));
		}
		return color_table;
	}
		
	protected float [] getHydrophobicValues(){
		// This is hydropathy index
		// Kyte, J., and Doolittle, R.F., J. Mol. Biol.
		// 1157, 105-132, 1982
		final float[] hydrophobic =
		  { 1.8f, -4.5f, -3.5f, -3.5f, 2.5f, -3.5f, -3.5f, -0.4f, -3.2f, 4.5f, 3.8f, -3.9f,
		      1.9f, 2.8f, -1.6f, -0.8f, -0.7f, -0.9f, -1.3f, 4.2f, -3.5f, -3.5f, -0.49f, 0.0f };
		
		return hydrophobic;
	}

	protected Color makeColor(float c){
		return new Color(c, 0.0f, 1.0f - c);
	}
}

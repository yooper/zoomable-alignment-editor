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
package color.schemes.dna;

import interfaces.ColorScheme;

import java.awt.Color;
import java.util.Hashtable;

public class PurinesAndPyrimidinesColorScheme implements ColorScheme{
	
	public PurinesAndPyrimidinesColorScheme(){	
	}
	
	public String getAcidType() {
		return "DNA";
	}
	
	public String getColorSchemeName() {
		return "Purines And Pyrimidines";
	}
	
	public Hashtable<Character, Color> getColorTable() {
		
		Hashtable<Character, Color> color_table = new Hashtable<Character, Color>();
		color_table.put(new Character('A'), new Color(255, 255, 0));
		color_table.put(new Character('G'), new Color(255, 255, 0));
		color_table.put(new Character('T'), new Color(0, 10, 255));
		color_table.put(new Character('C'), new Color(0, 10, 255));
		return color_table;
	}

}

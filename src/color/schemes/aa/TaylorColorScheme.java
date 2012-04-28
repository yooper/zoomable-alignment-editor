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

import lib.AminoAcids;

import interfaces.ColorScheme;

public class TaylorColorScheme extends AminoAcids implements ColorScheme {

	public String getAcidType() {
		return "AA";
	}

	public String getColorSchemeName() {
		return "Taylor";
	}

	public Hashtable<Character, Color> getColorTable() {
		
		Hashtable<Character, Color> color_table = new Hashtable<Character, Color>();
		color_table.put(new Character('A'), new Color(204, 255, 0));
		color_table.put(new Character('R'), new Color(0, 0, 255));
		color_table.put(new Character('N'), new Color(204, 0, 255));
		color_table.put(new Character('D'), new Color(255, 0, 0));
		color_table.put(new Character('C'), new Color(255, 255, 0));
		color_table.put(new Character('Q'), new Color(255, 0, 204));
		color_table.put(new Character('E'), new Color(255, 0, 102));
		color_table.put(new Character('G'), new Color(255, 153, 0));
		color_table.put(new Character('H'), new Color(0, 102, 255));
		color_table.put(new Character('I'), new Color(102, 255, 0));
		color_table.put(new Character('L'), new Color(51, 255, 0));
		color_table.put(new Character('K'), new Color(102, 0, 255));
		color_table.put(new Character('M'), new Color(0, 255, 0));
		color_table.put(new Character('F'), new Color(0, 255, 102));
		color_table.put(new Character('P'), new Color(255, 204, 0));
		color_table.put(new Character('S'), new Color(255, 51, 0));
		color_table.put(new Character('T'), new Color(255, 102, 0));	
		color_table.put(new Character('W'), new Color(0, 204, 255));
		color_table.put(new Character('Y'), new Color(0, 255, 204));
		color_table.put(new Character('V'), new Color(153, 255, 0));
		return color_table;
	}

}

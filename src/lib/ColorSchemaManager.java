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

import interfaces.ColorScheme;

import java.awt.Color;
import java.util.Hashtable;

public class ColorSchemaManager {

	protected static ColorSchemaManager instance = null;
	protected String active_color_scheme_name;
	protected String acid_type;
	protected Hashtable<Character, Color>active_color_hash_table;
	
	protected Hashtable<String, Hashtable<Character, Color>> dna_color_scheme_hash_table;
	protected Hashtable<String, Hashtable<Character, Color>> aa_color_scheme_hash_table;
	
	String dna_prefix = "color.schemes.dna.";
	String aa_prefix = "color.schemes.aa.";
	
	String [] dna_color_scheme_class_names = {
		"NucleotideColorScheme", 
		"PurinesAndPyrimidinesColorScheme"
	}; 
	
	String [] aa_color_scheme_class_names = {
		"TaylorColorScheme",
		"HydrophobicColorScheme"
	};	
	
	public static ColorSchemaManager getInstance(){
		if(instance == null){
			instance = new ColorSchemaManager();
		}
		return instance;
	}
	
	private ColorSchemaManager(){
		
		dna_color_scheme_hash_table = new Hashtable<String, Hashtable<Character, Color>>();
		aa_color_scheme_hash_table = new Hashtable<String, Hashtable<Character, Color>>();
		
	}
	
	public void setActiveColorScheme(String color_scheme_name){
		
		if(dna_color_scheme_hash_table.containsKey(color_scheme_name)){
			this.active_color_hash_table = dna_color_scheme_hash_table.get(color_scheme_name);
			this.active_color_scheme_name = color_scheme_name;
		}
		else if(aa_color_scheme_hash_table.containsKey(color_scheme_name)){
			this.active_color_hash_table = aa_color_scheme_hash_table.get(color_scheme_name);
			this.active_color_scheme_name = color_scheme_name;			
		}
		else{
			//TO DO throw an exception..
		}
	}
	
	public String [] getColorSchemeNames(String acid_type){
		
		if(acid_type == "DNA"){
			return getColorSchemeNames(dna_color_scheme_hash_table);
		}
		else{
			return getColorSchemeNames(aa_color_scheme_hash_table);
		}	
	}
	
	public void loadAllColorSchemes(){
		loadAASchemes();
		loadDNASchemes();
	}

	
	public String [] getColorSchemeNames(Hashtable<String, Hashtable<Character, Color>> hash_table){
		Object [] names = hash_table.keySet().toArray();
		
		String [] scheme_names = new String [names.length]; 
		
		for(int i=0;i<names.length;i++)
		{
			scheme_names[i] = names[i].toString();
		}
		
		return scheme_names;
	}
	
	protected void loadAASchemes() {
		
		loadColorSchemeClasses(aa_prefix, aa_color_scheme_class_names);
	}

	protected void loadColorSchemeClasses(String class_path_prefix, String [] class_names){
		
		ColorScheme scheme = null;
	
		for(int index = 0; index < class_names.length; index++){

			try {	
				scheme = (ColorScheme) Class.forName(class_path_prefix+class_names[index]).newInstance();
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if(class_path_prefix.compareTo(dna_prefix)==0){
				dna_color_scheme_hash_table.put(scheme.getColorSchemeName(), scheme.getColorTable());
			}
			else if(class_path_prefix.compareTo(aa_prefix)==0){
				aa_color_scheme_hash_table.put(scheme.getColorSchemeName(), scheme.getColorTable());
			}
		}
	}
	
	protected void loadDNASchemes() {
		
		loadColorSchemeClasses(dna_prefix, dna_color_scheme_class_names);
		this.setActiveColorScheme("Nucleotide");
	}
	
	public String getActiveColorSchemaName(){ 
		return active_color_scheme_name; 
	}
	
	public Color getColor(char character){
		
		Color color = active_color_hash_table.get(Character.toUpperCase(character));
		
		if(color == null){
			return Color.WHITE;
		}
		else{
			return color; 
		}	
	}
	
	public void finalize(){
		active_color_hash_table.clear();
	}
	
}

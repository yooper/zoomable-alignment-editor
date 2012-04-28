package lib;

import java.util.Arrays;
import java.util.Collections;
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
import java.util.Vector;

public class AminoAcids {

	public Vector<String> aminos;
	
	public AminoAcids(){
		aminos = new Vector<String>();
		
		aminos.add("R");   
		aminos.add("K");  
		aminos.add("Q");   
		aminos.add("N");   
		aminos.add("E");   
		aminos.add("D");   
		aminos.add("H");   
		aminos.add("P");   
		aminos.add("Y");   
		aminos.add("W");   
		aminos.add("S");   
		aminos.add("T");   
		aminos.add("G");   
		aminos.add("A");   
		aminos.add("M");   
		aminos.add("C");   
		aminos.add("F");   
		aminos.add("L");   
		aminos.add("V");   
		aminos.add("I");   
		
		Collections.sort(aminos);		
	}
	
	public Vector<String> getAminoAcids(){
		return aminos;
	}
	
	protected float lerp(float position, float min, float max){
		return (position - min) / (max - min); 
	}
	
	protected float getMaxValue(float [] values){
		
		return sortedArray(values)[values.length-1];	
	}
	
	protected float getMinValue(float [] values){
		
		return sortedArray(values)[0];
	}
	
	private float [] sortedArray(float [] values){
		
		float [] new_array_of_floats = Arrays.copyOf(values, values.length);
		Arrays.sort(new_array_of_floats);
		return new_array_of_floats;
	}
}

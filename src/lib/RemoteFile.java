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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class RemoteFile {

	private RemoteFile(){
		
	}
	
	public static StringBuffer getFile(String source){
		StringBuffer data_buffer = new StringBuffer();
		
		URL host = null;
	    BufferedReader br = null;
	    
	    try {
	    	host = new URL(source);
		    
			br = new BufferedReader(new InputStreamReader(host.openStream()));
			
			String line;
			
			while ((line = br.readLine()) != null){
				data_buffer.append(line).append("\n");
			}
				
			br.close();
			
		}catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}	
		
		return data_buffer;
	}
}

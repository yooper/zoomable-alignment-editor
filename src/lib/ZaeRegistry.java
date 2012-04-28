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

import java.util.HashMap;

public class ZaeRegistry
{
    protected HashMap<Object, Object> registry = null;
    protected static ZaeRegistry instance = null;
	
    private ZaeRegistry (){
    	
        registry = new HashMap();	
    }
    
    static public void addToRegistry(Object key, Object value){
    	if(instance == null)
    	{
    	    instance = new ZaeRegistry();
    	}
    	instance.registry.put(key, value);
    }
    
    static public Object getFromRegistry(Object key){
    	
    	if(instance == null)
    	{
    	    instance = new ZaeRegistry();
    	}  
    	return instance.registry.get(key);
    }
    
    static public boolean registryKeyExists(Object key){
    	if(instance == null){
    		return false;
    	}
    	else{
    		return instance.registry.containsKey(key);
    	}
    }
    
    static public void clear(){
    	
    	if(instance != null){
    		instance.registry.clear();
    		instance = null;
    	}
    }
}

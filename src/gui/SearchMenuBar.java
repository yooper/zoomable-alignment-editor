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
package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import lib.ZaeRegistry;
import lib.ZaeResourceManager;

public class SearchMenuBar extends Observable{

	protected static SearchMenuBar instance = null; 
	
	protected SearchMenuBar(){}
	
	public static SearchMenuBar getInstance(){
		
		if(instance == null){
			instance = new SearchMenuBar();
		}
		return instance;
	}
	
	public JMenuBar getMenuBar(){

		JMenuBar  menu_bar = new JMenuBar();

		JMenu menu = new JMenu("Search");
		menu.getAccessibleContext().setAccessibleDescription(
		        "Search Multiple Sequence Alignments using text matching or regular expressions");
	
		JMenuItem menu_item = new JMenuItem("Input Search Key");
		menu_item.getAccessibleContext().setAccessibleDescription(
		        "Search the sequences case-insetive or regular expressions");
		
		/*Action Listener for importing a Sequence*/
		menu_item.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent event){
				
				String the_search_key = (String)ZaeRegistry.getFromRegistry("the_search_key");
				the_search_key = JOptionPane.showInputDialog(null, "Search using text patterns or Regular Expressions", the_search_key );
				
				/* user hit cancel or they reused the same search key*/
				if(the_search_key != null)
				{
					if(!ZaeRegistry.registryKeyExists("the_search_key")){
						sendEvent(the_search_key);
					}
					else if(ZaeRegistry.registryKeyExists("the_search_key") && 
						the_search_key.compareTo((String)ZaeRegistry.getFromRegistry("the_search_key")) != 0){
						sendEvent(the_search_key);
					}
				}
				
		}
			protected void sendEvent(String the_search_key){
                ZaeRegistry.addToRegistry("the_search_key", the_search_key);
				setChanged();
                notifyObservers(the_search_key);
			}
		});
		
		menu.add(menu_item);
		menu_bar.add(menu);
		
		return menu_bar;
	}
}

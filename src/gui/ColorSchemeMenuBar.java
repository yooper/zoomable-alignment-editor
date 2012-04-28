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
import java.util.Hashtable;
import java.util.Observable;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import lib.ColorSchemaManager;

public class ColorSchemeMenuBar extends Observable{

	protected static ColorSchemeMenuBar instance = null;
	protected JMenuBar color_scheme_menu_bar;
	
	private ColorSchemeMenuBar(){}
	
	public static ColorSchemeMenuBar getInstance(){
		
		if(instance == null){
			instance = new ColorSchemeMenuBar();
		}
		return instance;
	}
	
	public JMenuBar getMenuBar()
	{
		color_scheme_menu_bar = new JMenuBar();
	
		JMenu menu = new JMenu("Color Schemes");
		menu.getAccessibleContext().setAccessibleDescription(
				"Select from a variety of color schemes that depict physiochemical properties");
		
		color_scheme_menu_bar.add(menu);
		
		ColorSchemaManager.getInstance().loadAllColorSchemes();
		
		setupSubMenu();
		
		return color_scheme_menu_bar;
	}
	
	public void setupSubMenu(){
			
		JMenu dna_sub_menu = new JMenu("DNA");
		
		JMenuItem [] menu_items = setupSubMenuItems("DNA");
		
		addSubMenuItemsToMenu(dna_sub_menu, menu_items);
		
		JMenu aa_sub_menu = new JMenu("Amino Acids");

		menu_items = setupSubMenuItems("AA");
		
		addSubMenuItemsToMenu(aa_sub_menu, menu_items);
		
		color_scheme_menu_bar.getMenu(0).add(dna_sub_menu);
		color_scheme_menu_bar.getMenu(0).add(aa_sub_menu);
	}
	
	protected void addSubMenuItemsToMenu(JMenu menu, JMenuItem [] menu_items){
		
		for(int i = 0;i < menu_items.length; i++){
			menu.add(menu_items[i]);
		}
		
	}
	
	protected JMenuItem [] setupSubMenuItems(String acid_type){
		
		final String [] scheme_names = ColorSchemaManager.getInstance().getColorSchemeNames(acid_type);	
		
		JMenuItem [] menu_items = new JMenuItem[scheme_names.length]; 
		
		for(int i=0;i< scheme_names.length; i++){
			
			final int j = i;
			
			menu_items[i] = new JMenuItem(scheme_names[i]);

			menu_items[i].addActionListener(new ActionListener(){
	        	
				public void actionPerformed(ActionEvent evt) {
						
					setChanged();
	                notifyObservers(scheme_names[j]);
				}});			
		}
		
		return menu_items;
	}
}

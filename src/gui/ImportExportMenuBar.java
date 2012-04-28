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

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Observable;
import java.util.Set;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class ImportExportMenuBar extends Observable{
	
	private static ImportExportMenuBar instance = null;
	protected JMenuBar menu_bar;
	protected Hashtable<String, String> key_pair_menu_class = new Hashtable<String, String>();
	
	private ImportExportMenuBar(){
		
		key_pair_menu_class.put("FASTA Format", "ExportFasta");
		key_pair_menu_class.put("NEXUS Format", "ExportNexus");
	}
	
	public static ImportExportMenuBar getInstance(){
		
		if(instance == null){
			instance = new ImportExportMenuBar();
		}
		return instance;
	}
	
	public JMenuBar getMenuBar(){
        
		menu_bar = new JMenuBar();
        JMenu menu = new JMenu("File");
        
        menu_bar.add(menu);
        
        addImportSubMenuItem();
        addExportSubMenuItems();
        
        return menu_bar;
	}
	
	
	protected void addImportSubMenuItem(){
		
		JMenuItem menu_item = new JMenuItem("Import Alignment (FASTA/NEXUS)");

        menu_item.getAccessibleContext().setAccessibleDescription(
        "Copy and Paste your multiple sequence alignment into the ZAE software");

		/*Action Listener for importing a Sequence Alignment via a dialog box*/
        menu_item.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent event) {
				String message = "Copy and past your multiple sequence alignment into this window.\nFASTA & Nexus formats are supported.";

				JTextArea jta = new JTextArea(message, 25, 80);
				jta.setFont(new Font("Monospaced", Font.BOLD, 14));
				JScrollPane pane = new JScrollPane(jta);
				jta.selectAll();
				JOptionPane.showMessageDialog(null, pane, 
						"Import Your Multiple Sequence Alignment", JOptionPane.INFORMATION_MESSAGE);
				
				/* Do nothing if the user opened the box */ 
				if(message.compareTo(jta.getText())==0 || jta.getText().length()==0){
					return;
				}
				else{
				
					jta.append("\n");
					setChanged();
	                notifyObservers(jta.getText().toString());
				}
			}
			
			
		});
        
       menu_bar.getMenu(0).add(menu_item);
	}
	
	protected void addExportSubMenuItems(){

		JMenu export_menu = new JMenu("Export Alignment");
		
		Set<String> set = key_pair_menu_class.keySet();

	    Iterator<String> itr = set.iterator();
	    
	    while (itr.hasNext()) {
	    
	    	final String key = itr.next();
			JMenuItem menu_item = new JMenuItem(key);
			
			menu_item.addActionListener(new ActionListener(){
				
				public void actionPerformed(ActionEvent event) {

					setChanged();
	                notifyObservers(key_pair_menu_class.get(key));
				}
			});
			
			export_menu.add(menu_item);      
	      
	    }
		

		
        menu_bar.getMenu(0).add(export_menu);
		
	}

}

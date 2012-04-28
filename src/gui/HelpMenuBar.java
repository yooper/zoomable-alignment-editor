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

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

public class HelpMenuBar {

	private HelpMenuBar(){}
	
	public static JMenuBar getMenuBar(){
		
		JMenuBar menuBar = new JMenuBar();
		
		JMenu menu = new JMenu("Help");
		
		menu.getAccessibleContext().setAccessibleDescription("A Tutorial of the user interface & supported features");
			
		menu.add(HelpMenuBar.userManualMenuItem());
		
		menu.add(HelpMenuBar.faqMenuItem());
		
		menuBar.add(menu);

		return menuBar;	
	}
	
	protected static JMenuItem userManualMenuItem(){
		JMenuItem menu_item = new JMenuItem("User Manual");	

		menu_item.getAccessibleContext().setAccessibleDescription(
        "User Manual");

		/*Action Listener for Displaying the tutorial*/
		menu_item.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event)
			{
				JOptionPane.showMessageDialog(null, "go to http://zae.sourceforge.net");
			}	
		});
		
		return menu_item;
	}
	
	protected static JMenuItem faqMenuItem(){
	
		JMenuItem menu_item = new JMenuItem("Frequently Asked Questions");
				
		/*Action Listener for Displaying the tutorial*/
		menu_item.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				JOptionPane.showMessageDialog(null, 
						"Add a gap - hold down the shift key and left mouse button; drag the mouse to the right\n" +
						"Remove a gap - hold down the shift key and left mouse button; drag the mouse to the left\n" +
						"Zoom in - hold down the right mouse button and drag it to the right\n" +
						"Zoom out - hold down the right mouse button and drag it to the left\n" +
						"Pan - hold down left mouse button and move mouse\n",
						"Frequently Asked Questions",JOptionPane.PLAIN_MESSAGE
				);
			}	
		});
		
		return menu_item;
	}
}

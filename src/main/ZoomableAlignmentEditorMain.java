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


package main;

import java.awt.BorderLayout;
import java.awt.font.TextLayout;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JScrollPane;
import lib.CanvasManager;
import lib.RenderTextToImage;
import lib.ZaeResourceManager;
import edu.umd.cs.piccolo.PNode;
import edu.umd.cs.piccolo.event.PBasicInputEventHandler;
import edu.umd.cs.piccolo.event.PInputEvent;
import edu.umd.cs.piccolox.PApplet;
import edu.umd.cs.piccolox.swing.PScrollPane;
import edu.umd.cs.piccolox.swing.PViewport;
import gui.ColorSchemeMenuBar;
import gui.HelpMenuBar;
import gui.ImportExportMenuBar;
import gui.SearchMenuBar;
import gui.SouthernNavigationPanel;

/**
 * Main Applet class that is launched by the end-users browser
 */
public class ZoomableAlignmentEditorMain extends PApplet{
	    
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/*
	 * The menu bars 
	 */
	ZaeResourceManager zae_resource_manager;
	JLabel sequence_name_label = new JLabel(" ");

    
    /**
	* This method is used to capture parameter values and setup any resources
	* prior to instantiating the GUI 
	* @return      void
	*/
    public void beforeInitialize(){
    	
    	super.beforeInitialize();
    	
    	CanvasManager canvas_manager = getOperatingSystemDependentVariables();
    	
    	zae_resource_manager = new ZaeResourceManager(canvas_manager);	
    }
    
	protected void initializePScrollPane()
	{
		
		/*set up scroll bar interface */
		final PScrollPane scrollPane = new PScrollPane(getCanvas(),
			JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
			JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS
		);
		
		final PViewport viewport = (PViewport) scrollPane.getViewport();
		
		scrollPane.revalidate();
		getContentPane().validate();
		
		viewport.fireStateChanged();
		getContentPane().add(scrollPane);
		scrollPane.revalidate();
	
	}

    /**
     * Setups operating specific variables to by used by the graphic overlays
     * @return CanvasManager
     */
    protected CanvasManager getOperatingSystemDependentVariables() {

		float image_height, image_width;
		
		Rectangle2D bounds = RenderTextToImage.getInstance().getFont().getMaxCharBounds(RenderTextToImage.getInstance().getFRC());
		TextLayout layout = new TextLayout("string",RenderTextToImage.getInstance().getFont(),RenderTextToImage.getInstance().getFRC());
		
    	if(System.getProperty("os.name").matches("(?i)mac.*")){
    		image_height = (float) bounds.getHeight(); 
    		image_width = layout.getAdvance() / layout.getCharacterCount();			
		}	
		else if(System.getProperty("os.name").matches("(?i)win.*")){
			image_height = RenderTextToImage.getInstance().getFont().getSize2D();
			image_width = layout.getAdvance() / layout.getCharacterCount();			
		}
		else{
			image_height = RenderTextToImage.getInstance().getFont().getSize2D();
			image_width =  (float) bounds.getWidth();			
		}
		
    	CanvasManager canvas_manager = new CanvasManager(this.getCanvas(), image_height,
    			image_width, RenderTextToImage.getInstance().getFont().getSize2D());
					
		RenderTextToImage.getInstance().setVerticalDrawHeight((int)layout.getAscent());
	
		return canvas_manager;
	}

	/**
     * All Piccolo specific initialization code should be contained in this
     * method.
     * @return void
    */
    public void initialize(){
    	
    	super.initialize();
    	
    	initializePScrollPane();
    	
    	setUpTooltip();
    	
    	initializeMenuBars();
    	
    	initializeZaeResourceManagerObservers();
    	
    	initializeAlignmentVisualization();
    }

    public void initializeMenuBars(){
    	JMenuBar menu_bar = new JMenuBar();
    	
    	menu_bar.add(ImportExportMenuBar.getInstance().getMenuBar());
    	
    	menu_bar.add(ColorSchemeMenuBar.getInstance().getMenuBar());
    	
    	menu_bar.add(SearchMenuBar.getInstance().getMenuBar());
    	    	
    	menu_bar.add(HelpMenuBar.getMenuBar());
    	
        getContentPane().add(BorderLayout.NORTH, menu_bar);
        
        SouthernNavigationPanel.getInstance().add(sequence_name_label);
        
        getContentPane().add(BorderLayout.SOUTH, SouthernNavigationPanel.getInstance());
        
        getContentPane().validate();   	
    }

	/*This code was borrowed from a piccolo example */
	private void setUpTooltip(){
	
		getCanvas().getCamera().addInputEventListener(new PBasicInputEventHandler() {	
			public void mouseMoved(PInputEvent event) {
				updateToolTip(event);
				
			}

			public void mouseDragged(PInputEvent event) {
				updateToolTip(event);
			}

			public void updateToolTip(PInputEvent event) {
				
				PNode n = event.getInputManager().getMouseOver().getPickedNode();
				Point2D p = event.getCanvasPosition();
				
				event.getPath().canvasToLocal(p, getCanvas().getCamera());
				
				if(((String)n.getAttribute("sequence_name")) != null){
					sequence_name_label.setText((String)n.getAttribute("sequence_name"));	
				}
					
				else{
					sequence_name_label.setText(" ");
				}			
			}
		});
		
	}
    
    public void initializeDisplayValues(){
    	
    }
    
    /**
     * Initialize the alignment data at start up...
     */
    public void initializeAlignmentVisualization(){
    	
    	//String source = "file:/home/dcardin/workspace/ZAE/tests/fasta_test_file_2.fasta";
    	
    	//zae_resource_manager.loadMultipleSequenceAlignmentData(source, true);
    	 
    }
    
    public void initializeZaeResourceManagerObservers(){
    	
        // subscribe the observer to the event source
        SearchMenuBar.getInstance().addObserver( zae_resource_manager );
        
        ColorSchemeMenuBar.getInstance().addObserver( zae_resource_manager );
        
        ImportExportMenuBar.getInstance().addObserver( zae_resource_manager );

    }

    /**
     * This method runs immediately after initialize. The GUI is visable by
     * the time this method is invoked.
     */
    public void start(){
    	
    	super.start();
    }

    
    /**
     * Parameter info that is available to the end-user. The parameter info details
     * what is 
     */
    public String[][] getParameterInfo(){
        String param_info[][] = { 
            { "data_file", "path name", "Optional path name to alignment data file on server" },
        };
        return param_info;
    }
    
    /**
     * About the Applet
     */
    public String getAppletInfo(){
        return "Zoomable Alignment Editor Applet, Version 0.0.1\n"
          + "A zoomable user interface that can visualize and edit multiple sequence alignment editors.";
    }
}

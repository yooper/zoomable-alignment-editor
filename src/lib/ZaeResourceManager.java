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

import interfaces.ExportFormat;

import java.awt.Font;
import java.util.Observable;
import java.util.Observer;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import exceptions.IllegalAlignmentFormatException;
import exceptions.UnknownAlignmentFormatException;

public class ZaeResourceManager implements Observer{

	protected SequencePImageManager sequence_pimage_manager = null;
	protected CanvasManager canvas_manager = null;
	protected Thread canvas_manager_thread;
	/**
	 * Initialize Program specific resources
	 */
	public ZaeResourceManager(CanvasManager canvas_manager) {
	
		this.canvas_manager = canvas_manager;
	}
	
	public void update(Observable obj, Object arg) {

		/*
		 * Event generated from gui
		 */
		if(obj.getClass().getName().startsWith("gui")){
			processGuiEvent(obj, arg);
		}	
	}
	
	protected void processGuiEvent(Observable obj, Object arg) {
		
		if(obj.getClass().getName().indexOf("SearchMenuBar") != -1){
			canvas_manager.setSearchKey((String)arg);
			canvas_manager.invokePPathsUpdate();
		}
		else if(obj.getClass().getName().indexOf("ColorSchemeMenuBar") != -1){
			ColorSchemaManager.getInstance().setActiveColorScheme((String)arg);
			canvas_manager.invokePImagesUpdate();
		}
		else if(obj.getClass().getName().indexOf("ImportExportMenuBar") != -1){
			if(arg.toString().startsWith("Export")){
				processExportRequest((String) arg);
			}
			else{
				loadMultipleSequenceAlignmentData((String)arg, false);
			}
		}		
		else{
			
		}
	}

	/**
	 * This method is invoked when the following occurs
	 * @param source
	 * @param remote_data_source
	 */
	public void loadMultipleSequenceAlignmentData(String source, boolean remote_data_source){
				
		AlignmentFormatManager alignment_format_manager = new AlignmentFormatManager(source, remote_data_source);
		
		Vector<Sequence> sequences = new Vector<Sequence>();
		
		try {
			sequences = alignment_format_manager.getSequences();
		} catch (UnknownAlignmentFormatException e) {
			e.printStackTrace();
		} catch (IllegalAlignmentFormatException e) {
			e.printStackTrace();
		}
		
		if(sequence_pimage_manager == null){
			sequence_pimage_manager = new SequencePImageManager();
		}
		else{
			sequence_pimage_manager.finalize();
		}
		
		sequence_pimage_manager.createSequencePImageVector(sequences);
		
		sequences.clear();
				
		canvas_manager.setSequencePImages(sequence_pimage_manager.getSequencePImageVector());
		canvas_manager.invokePImagesUpdate();
	}
	
	/**
	 * This method uses reflection to instantiate the export class. A pop-up
	 * box appears with the modified multiple sequence alignment.
	 * @param export_format_class The class name of the export class to call.
	 * 
	 */
	protected void processExportRequest(String export_format_class){
		
		String export_library_prefix = "export.formats.";
		
		ExportFormat exporter = null;
		
		try {
			exporter = (ExportFormat) Class.forName(export_library_prefix+export_format_class).newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		String export_string = exporter.exportFormatToString(sequence_pimage_manager.getSequencePImageVector());
		
		JTextArea jta = new JTextArea(export_string, 25, 80);
		jta.setFont(new Font("Monospaced", Font.BOLD, 14));
		jta.selectAll();
		JOptionPane.showMessageDialog(null, new JScrollPane(jta), 
				"Copy & paste your sequence into another form", JOptionPane.INFORMATION_MESSAGE);

	}

}

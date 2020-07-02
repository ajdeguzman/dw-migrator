package com.ajdeguzman.dw;

import com.ajdeguzman.dw.Constants;

import javax.swing.*;
import javax.swing.SwingUtilities;
import java.awt.*;
import javax.swing.filechooser.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static org.mule.weave.v2.V2LangMigrant.migrateToV2;

public class DWMigrator extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;
	JButton btnConvert, btnClear = new JButton();
	JPanel dwPanel;
	JMenu mnuFile, mnuHelp;
	JMenuItem mnuItemImport;
	JFileChooser fileChoose;
	
	public DWMigrator(){
        super(new BorderLayout());
        
		JMenuBar mnuBar = new JMenuBar();
		mnuFile = new JMenu(Constants.Menu.FILE);
		mnuHelp = new JMenu(Constants.Menu.HELP);

		JFileChooser fileChoose = new JFileChooser();
		
		mnuBar.add(mnuFile);
		mnuBar.add(mnuHelp);

		mnuItemImport = new JMenuItem(Constants.Menu.IMPORT);
		mnuItemImport.addActionListener(this);
		mnuFile.add(mnuItemImport);
		dwPanel = new JPanel();
		btnConvert = new JButton(Constants.Labels.CONVERT);
		btnClear = new JButton(Constants.Labels.CLEAR);

		btnConvert.addActionListener(this);
		btnClear.addActionListener(this);
		dwPanel.add(btnConvert);
		dwPanel.add(btnClear);
		
		add(dwPanel, BorderLayout.SOUTH);
		add(mnuBar, BorderLayout.NORTH);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == btnConvert) {
			
		} else if (e.getSource() == btnClear) {
			// Clear Text Areas
		} else if (e.getSource() == mnuItemImport) {
			fileChoose.showOpenDialog(this);
		}

	}


	private static void createAndShowGUI() {

		JFrame dwFrame = new JFrame(Constants.APP_NAME);
		dwFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		dwFrame.add(new DWMigrator());
		dwFrame.pack();
		dwFrame.setSize(1000, 600);
		dwFrame.setLocationRelativeTo(null);
		dwFrame.setVisible(true);
	}
	
	public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				UIManager.put("swing.boldMetal", Boolean.FALSE); 
                createAndShowGUI();
			}
		});
	}

}

package com.ajdeguzman.dw;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.filechooser.FileFilter;

import org.apache.commons.io.FileUtils;
import static org.mule.weave.v2.V2LangMigrant.migrateToV2;

public class DWMigrator extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;
    static private final String newline = "\n";
	JButton btnClear = new JButton();
	JPanel dwPanel;
	JMenu mnuFile, mnuHelp;
	JMenuItem mnuItemImport;
	JFileChooser fileChooser;
    JTextArea txtScript;
    
    
    private List<String> filePaths;
    private String filesDir;
    private String outputDir;
	
	public DWMigrator(){
        super(new BorderLayout());
        Font font = new Font("Consolas", Font.BOLD, 20);
        txtScript = new JTextArea(5,20);
        txtScript.setFont(font);
        txtScript.setMargin(new Insets(5,5,5,5));
        JScrollPane logScrollPane = new JScrollPane(txtScript);
        
		JMenuBar mnuBar = new JMenuBar();
		mnuFile = new JMenu(Constants.Menu.FILE);
		mnuHelp = new JMenu(Constants.Menu.HELP);

		fileChooser = new JFileChooser();
		
		mnuBar.add(mnuFile);
		mnuBar.add(mnuHelp);

		mnuItemImport = new JMenuItem(Constants.Menu.IMPORT);
		mnuItemImport.addActionListener(this);
		mnuFile.add(mnuItemImport);
		dwPanel = new JPanel();
		//btnConvert = new JButton(Constants.Labels.CONVERT);
		btnClear = new JButton(Constants.Labels.CLEAR);

		//btnConvert.addActionListener(this);
		btnClear.addActionListener(this);
		//dwPanel.add(btnConvert);
		dwPanel.add(btnClear);
		
		add(dwPanel, BorderLayout.SOUTH);
        add(logScrollPane, BorderLayout.CENTER);
		add(mnuBar, BorderLayout.NORTH);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			
			Path originalPath;
	        String dwOriginalScript;
	        String dwMigratedScript;
	        
	        //fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
	        fileChooser.setFileFilter(new FileFilter() {

	        	   public String getDescription() {
	        	       return "DataWeave Scripts (*.dwl)";
	        	   }

	        	   public boolean accept(File f) {
	        	       if (f.isDirectory()) {
	        	           return true;
	        	       } else {
	        	           String filename = f.getName().toLowerCase();
	        	           return filename.endsWith(".dwl");
	        	       }
	        	   }
	        	});
	        
			if (e.getSource() == btnClear) {
				txtScript.setText("");
			} else if (e.getSource() == mnuItemImport) {
				int returnVal = fileChooser.showOpenDialog(DWMigrator.this);
				
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fileChooser.getSelectedFile();
					  	
						BufferedReader in;
				        in = new BufferedReader(new FileReader(file));
				        String line = in.readLine();
				        
				        while (line != null) {
				            txtScript.setText(txtScript.getText() + "\n" + line);
				            line = in.readLine();
				        }
				        originalPath 		= Paths.get(file.getAbsolutePath());
				        dwOriginalScript 	= new String(Files.readAllBytes(originalPath));
				        dwMigratedScript 	= migrateToV2("");
				        in.close();
	                
	            }
	            txtScript.setCaretPosition(txtScript.getDocument().getLength());
			}
		} catch (Exception e1) {
			e1.printStackTrace();
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
		dwFrame.setResizable(false);
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
	
	public List<String> getFilePaths(String rootDirectoryPath) throws Exception {

        File rootDirectory = new File(rootDirectoryPath);
        List<File> files = (List<File>) FileUtils.listFiles(rootDirectory, new String[] { "dwl" }, true);
        List<String> filesPaths = new ArrayList<>();

        for (File file : files) {
            filesPaths.add(file.getAbsolutePath());
        }

        return filesPaths;
    }

    public void setFilePaths(List<String> filePaths) {
        this.filePaths = filePaths;
    }

    public void setFilesDir(String filesDir) {
        this.filesDir = filesDir;
    }

    public void setOutputDir(String outputDir) {
        this.outputDir = outputDir;
    }

}

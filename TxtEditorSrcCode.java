import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.undo.UndoManager;

public class TxtEditor extends JFrame implements ActionListener{
	
	JTextArea textArea;
	JScrollPane scrollPane;
	JLabel fontLabel;
	JSpinner fontSizeSpinner;
	JButton fontColorButton;
	JComboBox fontBox;
	JMenuBar menu;
	JMenu fileMenu;
	JMenu edit;
	
	//JMenuItem newItm;
	JMenuItem openItem;
	JMenuItem saveItem;
	JMenuItem exitItem;
	
	JMenuItem copyItem;
	JMenuItem cutItem;
	JMenuItem pasteItem;
	JMenuItem undoItem;
	
	UndoManager undo;
	
	
	TxtEditor(){
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("My Text Editor");
		this.setSize(500, 500);
		this.setLayout(new FlowLayout());
		this.setLocationRelativeTo(null);
		
		
		
		textArea = new JTextArea();
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);  //doubt
		textArea.setFont(new Font("Arial", Font.PLAIN, 70));
		
		scrollPane = new JScrollPane(textArea);
		scrollPane.setPreferredSize(new Dimension(450, 450));
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		
		fontLabel = new JLabel("FontSize");
		
		fontSizeSpinner = new JSpinner();
		fontSizeSpinner.setPreferredSize(new Dimension(50, 25));
		fontSizeSpinner.setValue(20);
		fontSizeSpinner.addChangeListener( new ChangeListener() {
			
			

			@Override
			public void stateChanged(ChangeEvent e) {
				// TODO Auto-generated method stub
				textArea.setFont(new Font(textArea.getFont().getFamily(), Font.PLAIN, (int) fontSizeSpinner.getValue()));
				
			}
		});
		
		fontColorButton = new JButton("Change Color");
		fontColorButton.addActionListener(this);
		
		String[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
		fontBox = new JComboBox(fonts);
		
		fontBox.addActionListener(this);
		fontBox.setSelectedItem("Arial");
		
		
		////////menu
		
		menu = new JMenuBar();
		fileMenu = new JMenu("File");
		edit = new JMenu("Edit");
		//newItm = new JMenu("new");
		openItem = new JMenuItem("open");
		saveItem = new JMenuItem("Save");
		exitItem = new JMenuItem("Exit");
		copyItem = new JMenuItem("copy");
		cutItem = new JMenuItem("cut");
		pasteItem = new JMenuItem("paste");
		undo =  new UndoManager();
		textArea.getDocument().addUndoableEditListener(undo);
		undoItem = new JMenuItem("undo");
		
		//newItm.addActionListener(this);
		openItem.addActionListener(this);
		saveItem.addActionListener(this);
		exitItem.addActionListener(this);
		copyItem.addActionListener(this);
		undoItem.addActionListener(this);
		
		//fileMenu.add(newItm);
		fileMenu.add(openItem);
		fileMenu.add(saveItem);
		fileMenu.add(exitItem);
		edit.add(cutItem);
		edit.add(copyItem);
		edit.add(pasteItem);
		edit.add(undoItem);
		menu.add(fileMenu);
		menu.add(edit);
		
		/////////menu
		
		this.setJMenuBar(menu);
		this.add(fontColorButton);
		this.add(fontLabel);
		this.add(fontSizeSpinner);
		this.add(fontBox);
		this.add(scrollPane);
		this.setVisible(true);
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		if(e.getSource() == fontColorButton) {
			JColorChooser colorChooser = new JColorChooser();
			Color color = colorChooser.showDialog(null, "Choose a color", Color.black);
			
			textArea.setForeground(color);
		}
		
		if(e.getSource() == fontBox) {
			textArea.setFont(new Font((String)fontBox.getSelectedItem(),Font.PLAIN, textArea.getFont().getSize()));
		}
		
		/*if(e.getSource() == newItm) {
			textArea.setText(null);
		}*/
		
		if(e.getSource() == openItem) {
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setCurrentDirectory(new File("."));
			//if you want you can put a directory
			
			FileNameExtensionFilter filter = new FileNameExtensionFilter("Text files", "txt");
			fileChooser.setFileFilter(filter);
			
			int response = fileChooser.showOpenDialog(null);
			
			if(response == JFileChooser.APPROVE_OPTION) {
				File file = new File(fileChooser.getSelectedFile().getAbsolutePath());
				Scanner fileIn = null;
				
				try {
					fileIn = new Scanner(file);
					if(file.isFile()) {
						while(fileIn.hasNextLine()) {
							String line = fileIn.nextLine() + "\n";
							textArea.append(line);
						}
					}
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				finally {
					fileIn.close();
				}
			}
			
			
		}
		
		if(e.getSource() == saveItem) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setCurrentDirectory(new File("."));
				
				int response = fileChooser.showSaveDialog(null);
				
				if(response == JFileChooser.APPROVE_OPTION) {
					File file;
					PrintWriter fileOut = null;
					
					file = new File(fileChooser.getSelectedFile().getAbsolutePath());
					try {
						fileOut = new PrintWriter(file);
						fileOut.println(textArea.getText());
					} catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					finally {
						fileOut.close();
					}
				}
		}
		
		if(e.getSource() == exitItem) {
			System.exit(0);
		}
		
		if(e.getSource() == cutItem) {
			textArea.cut();
		}
		
		if(e.getSource() == copyItem) {
			textArea.copy();
		}
		
		if(e.getSource() == pasteItem) {
			textArea.paste();
		}
		
		if(e.getSource() == undoItem) {
			undo.undo();
		}
		
	}
}

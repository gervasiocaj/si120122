package br.edu.ufcg.over9000.gui;

import java.awt.EventQueue;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JButton;

import br.edu.ufcg.over9000.over9000.FilesScraper;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JLabel;
import java.awt.Component;
import javax.swing.Box;
import java.awt.Panel;
import java.io.IOException;

public class MainWindow {

	private JFrame mainFrame, dirFrame;
	private FilesScraper fs;
	private JFileChooser file;
	private JLabel lblDiretrioEscolhido;
	private JButton chooseDir;
	private Panel mainPanel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow window = new MainWindow();
					window.mainFrame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		fs = new FilesScraper();
		
		mainFrame = new JFrame();
		mainFrame.setBounds(100, 100, 640, 480);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		mainPanel = new Panel();
		lblDiretrioEscolhido = new JLabel("Diretório escolhido:" + (" - "));
		chooseDir = new JButton("Escolher diretório...");
		
		mainPanel.add(lblDiretrioEscolhido);
		mainPanel.add(chooseDir);
		mainFrame.getContentPane().add(mainPanel, BorderLayout.NORTH);
		
		file = new JFileChooser();
		file.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		file.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				dirFrame.setVisible(false);
				if (arg0.equals(JFileChooser.APPROVE_SELECTION))
					try {
						setDir();
					} catch (IOException e) {
						e.printStackTrace();
					}
			}
		});
		
		
		chooseDir.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dirFrame.setVisible(true);
			}
		});
		
		dirFrame = new JFrame();
		dirFrame.setResizable(false);
		dirFrame.setBounds(100, 100, 640, 480);
		dirFrame.getContentPane().add(file, BorderLayout.PAGE_START);
	
	}

	protected void setDir() throws IOException {
		lblDiretrioEscolhido.setText("Diretório escolhimdo:" + (!fs.hasDir() ? " - " : file.getSelectedFile().getCanonicalPath()));//FIXME not working
		mainPanel.repaint();
		fs.setDirectory(file.getSelectedFile());
	}

}

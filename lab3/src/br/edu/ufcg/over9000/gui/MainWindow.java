package br.edu.ufcg.over9000.gui;

import java.awt.EventQueue;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Window.Type;

public class MainWindow {

	private JFrame mainFrame, dirFrame;

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
		JButton chooseDir = new JButton("opa");
		chooseDir.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dirFrame.setVisible(true);
			}
		});
		
		JFileChooser file = new JFileChooser();
		file.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		file.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (arg0.equals(JFileChooser.APPROVE_SELECTION)) {
					dirFrame.setVisible(false);
					//TODO
				}
			}
		});
		
		mainFrame = new JFrame();
		mainFrame.setBounds(100, 100, 640, 480);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.getContentPane().add(chooseDir, BorderLayout.NORTH);
		
		dirFrame = new JFrame();
		dirFrame.setResizable(false);
		dirFrame.setBounds(100, 100, 640, 480);
		dirFrame.getContentPane().add(file, BorderLayout.PAGE_START);
	
	}

}

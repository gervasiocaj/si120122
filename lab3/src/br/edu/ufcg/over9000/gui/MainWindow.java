package br.edu.ufcg.over9000.gui;

import br.edu.ufcg.over9000.over9000.FilesScraper;
import javax.swing.*;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Panel;
import java.awt.event.*;
import java.util.concurrent.Executors;

public class MainWindow {

	private JFrame mainFrame, dirFrame;
	private FilesScraper fs;
	private Panel choicePanel, poolPanel, resultsPanel, finalPanel;
	private JLabel labelChosenDir, labelPoolSize;
	private JFileChooser dirChooser;
	private JButton chooseDir;
	private JSpinner spinner;
	private JButton analizeButton;
	private JLabel fileStatus;

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

		// -----------------------------------------------------

		choicePanel = new Panel();
		labelChosenDir = new JLabel("Diretório escolhido:" + (" - "));
		chooseDir = new JButton("Escolher diretório...");

		choicePanel.add(labelChosenDir);
		choicePanel.add(chooseDir);
		mainFrame.getContentPane().add(choicePanel, BorderLayout.NORTH);

		// -----------------------------------------------------

		poolPanel = new Panel();
		labelPoolSize = new JLabel("Quantidade de threads a usar (escolha 0 para indefinidas threads): ");
		spinner = new JSpinner(new SpinnerNumberModel(0, 0, null, 1));

		poolPanel.add(labelPoolSize);
		poolPanel.add(spinner);
		mainFrame.getContentPane().add(poolPanel, BorderLayout.WEST);

		// -----------------------------------------------------

		finalPanel = new Panel();
		analizeButton = new JButton("Analisar");
		analizeButton.setEnabled(fs.hasDir());
		
		finalPanel.add(analizeButton);
		mainFrame.getContentPane().add(finalPanel, BorderLayout.EAST);

		// -----------------------------------------------------

		resultsPanel = new Panel();
		fileStatus = new JLabel("0 de 0 arquivos");
		// TODO mostrar contagem de palavras

		resultsPanel.add(fileStatus, BorderLayout.SOUTH);
		mainFrame.getContentPane().add(resultsPanel, BorderLayout.SOUTH);

		// -----------------------------------------------------

		dirFrame = new JFrame();
		dirFrame.setResizable(false);
		dirFrame.setBounds(100, 100, 640, 480);

		chooseDir.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dirFrame.setVisible(true);
			}
		});

		dirChooser = new JFileChooser();
		dirChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		dirFrame.getContentPane().add(dirChooser);

		dirChooser.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				dirFrame.setVisible(false);
				switch (arg0.getActionCommand()) {
				case JFileChooser.APPROVE_SELECTION:
					setDir();
					break;
				default:
					break;
				}
			}
		});
		
		// -----------------------------------------------------
		
		analizeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				fs.setThreadAmount((int) spinner.getValue());
				analizeOnThread();
				while (true) {
					analizeButton.setEnabled(fs.hasFinished());
					fileStatus.setText(fs.getThreadsFinishedAmount() + " de " + fs.getFileAmount() + " arquivos");
					// TODO dar update nas qtds de palavras
					if (fs.hasFinished())
						break;
				}
				
			}

		});
		
	}

	protected void setDir() {
		fs.setDirectory(dirChooser.getSelectedFile());
		String newText = "Diretório escolhido: ";
		newText = newText.concat(fs.hasDir() ? dirChooser.getSelectedFile()
				.getAbsolutePath() : " - ");
		labelChosenDir.setText(newText);
		fileStatus.setText("0 de " + fs.getFileAmount() + " arquivos");
		analizeButton.setEnabled(fs.hasDir());
		mainFrame.repaint();
	}

	private void analizeOnThread() {
		Executors.newSingleThreadExecutor().execute(new Runnable() {
			@Override
			public void run() {
				fs.runWordCount();
			}
		});
	}
}

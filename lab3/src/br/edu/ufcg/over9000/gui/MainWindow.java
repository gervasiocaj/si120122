package br.edu.ufcg.over9000.gui;

import br.edu.ufcg.over9000.over9000.FilesScraper;
import javax.swing.*;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Panel;
import java.awt.event.*;
import java.util.concurrent.Executors;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.RowSpec;

public class MainWindow {

	private JFrame mainFrame, dirFrame;
	private FilesScraper fs;
	private Panel choicePanel, resultsPanel;
	private JLabel labelChosenDir, labelPoolSize, fileStatus;
	private JButton directoryButton, analizeButton;
	private JFileChooser dirChooser;
	private JSpinner spinner;
	private JScrollPane scrollPane;
	private JTextArea textArea;

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
		choicePanel.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.UNRELATED_GAP_COLSPEC, ColumnSpec.decode("448px"),
				ColumnSpec.decode("157px"), }, new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC, RowSpec.decode("23px"),
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("max(17dlu;default)"),
				FormFactory.RELATED_GAP_ROWSPEC, }));
		labelChosenDir = new JLabel("Diretório escolhido:" + (" - "));

		choicePanel.add(labelChosenDir, "2, 2, left, center");

		mainFrame.getContentPane().add(choicePanel, BorderLayout.NORTH);
		directoryButton = new JButton("Escolher diretório...");
		choicePanel.add(directoryButton, "3, 2, left, top");

		directoryButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dirFrame.setVisible(true);
			}
		});

		labelPoolSize = new JLabel(
				"Quantidade de threads a usar (escolha 0 para indefinidas threads): ");
		choicePanel.add(labelPoolSize, "2, 4, left, center");
		spinner = new JSpinner(new SpinnerNumberModel(0, 0, null, 1));
		choicePanel.add(spinner, "3, 4");
		analizeButton = new JButton("Analisar");
		choicePanel.add(analizeButton, "3, 6");

		// -----------------------------------------------------

		analizeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				analisisAction();

			}

		});

		// -----------------------------------------------------

		resultsPanel = new Panel();
		fileStatus = new JLabel(getFooterText());

		resultsPanel.add(fileStatus, BorderLayout.SOUTH);
		mainFrame.getContentPane().add(resultsPanel, BorderLayout.SOUTH);

		// -----------------------------------------------------

		textArea = new JTextArea();
		scrollPane = new JScrollPane(textArea);

		mainFrame.getContentPane().add(scrollPane, BorderLayout.CENTER);

		// -----------------------------------------------------

		dirFrame = new JFrame();
		dirFrame.setResizable(false);
		dirFrame.setBounds(100, 100, 640, 480);

		dirChooser = new JFileChooser();
		dirChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		dirFrame.getContentPane().add(dirChooser);

		dirChooser.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				dirFrame.setVisible(false);
				switch (arg0.getActionCommand()) {
				case JFileChooser.APPROVE_SELECTION:
					directoryAction();
					break;
				default:
					break;
				}
			}
		});

	}

	protected void directoryAction() {
		updateFrame();
		directoryButton.setEnabled(false);
		
		scrapeDirsWithThreads();
		
		while (!fs.hasFinishedScraping())
			updateFrame();
		
		directoryButton.setEnabled(true);
	}
	
	private void analisisAction() {
		directoryButton.setEnabled(false);
		analizeButton.setEnabled(false);
		
		fs.setThreadAmount((int) spinner.getValue());
		analizeWithThreads();
		while (!fs.hasFinishedCounting())
			updateFrame();
		
		textArea.setText(fs.generateMessage());
		directoryButton.setEnabled(true);
		analizeButton.setEnabled(true);
	}

	private void scrapeDirsWithThreads() {
		Executors.newSingleThreadExecutor().execute(new Runnable() {
			@Override
			public void run() {
				fs.setDirectory(dirChooser.getSelectedFile());
			}
		});
	}

	private void analizeWithThreads() {
		Executors.newSingleThreadExecutor().execute(new Runnable() {
			@Override
			public void run() {
				fs.runWordCount();
			}
		});
	}

	private void updateFrame() {
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {}
		labelChosenDir.setText(getHeaderText());
		//analizeButton.setEnabled(fs.hasDir());
		fileStatus.setText(getFooterText());
		mainFrame.repaint();
	}

	private String getHeaderText() {
		return "Diretório escolhido: "
				+ (fs.hasDir() ? dirChooser.getSelectedFile().getAbsolutePath() : " - ");
	}

	private String getFooterText() {
		return fs.getThreadsFinishedAmount() + " de " + fs.getFileAmount()
				+ " arquivos - " + fs.getTimeSpent()
				+ " milisegundos para a execução";
	}
}

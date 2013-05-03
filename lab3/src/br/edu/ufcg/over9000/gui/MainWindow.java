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
	private JLabel labelChosenDir, labelPoolSize;
	private JFileChooser dirChooser;
	private JButton chooseDir;
	private JSpinner spinner;
	private JButton analizeButton;
	private JLabel fileStatus;
	private JScrollPane scrollPane;
	private JList<String> wordList;

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
		chooseDir = new JButton("Escolher diretório...");
		choicePanel.add(chooseDir, "3, 2, left, top");

		chooseDir.addActionListener(new ActionListener() {
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
		analizeButton.setEnabled(fs.hasDir());

		// -----------------------------------------------------

		analizeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				fs.setThreadAmount((int) spinner.getValue());
				analizeOnThread();
				while (true) {
					analizeButton.setEnabled(fs.hasFinished());
					fileStatus.setText(fs.getThreadsFinishedAmount() + " de "
							+ fs.getFileAmount() + " arquivos");
					// TODO dar update nas qtds de palavras
					if (fs.hasFinished())
						break;
				}

			}

		});

		// -----------------------------------------------------

		resultsPanel = new Panel();
		fileStatus = new JLabel("0 de 0 arquivos");
		// TODO mostrar contagem de palavras

		resultsPanel.add(fileStatus, BorderLayout.SOUTH);
		mainFrame.getContentPane().add(resultsPanel, BorderLayout.SOUTH);

		// -----------------------------------------------------

		scrollPane = new JScrollPane();
		wordList = new JList<String>();
		
		scrollPane.setViewportView(wordList);
		
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
					setDir();
					break;
				default:
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

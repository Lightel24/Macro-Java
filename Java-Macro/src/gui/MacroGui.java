package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JSplitPane;
import javax.swing.JList;
import javax.swing.JOptionPane;

import java.awt.Component;
import javax.swing.Box;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.GridLayout;
import java.awt.Dimension;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;
import javax.swing.border.LineBorder;

import core.Action;
import core.Macro;
import core.Manager;
import core.ManagerObserver;
import fr.lightel24.gitupdater.GitUpdater;

import javax.swing.JButton;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;

import java.awt.Rectangle;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.border.BevelBorder;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.Stack;
import java.awt.event.ActionEvent;
import javax.swing.event.ListSelectionListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.event.ListSelectionEvent;
import javax.swing.JSlider;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JSpinner;
import javax.swing.JMenuItem;
import javax.swing.JTextField;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JProgressBar;
import javax.swing.JTree;

public class MacroGui extends JFrame implements ManagerObserver{

	private JPanel contentPane;
	private JPanel Liste = new JPanel();
	private DefaultListModel<String> listModel = new DefaultListModel<String>();
	private JList list = new JList(listModel);
	private JLabel lblLogOp = new JLabel("Log : ");
	
	
	private Logger logger = LoggerFactory.getLogger(MacroGui.class);
	private Manager manager;
	private Stack<LogMessage> logStack = new Stack<LogMessage>();
	private Thread logStackThread;
	protected boolean running=false;
	private String[] macroNames;
	
	public static void main(String[] args) {
		new MacroGui(new Manager()).setVisible(true);
	}
	
	/**
	 * Create the frame.
	 * @param manager2 
	 */
	public MacroGui(Manager manager2) {
		setTitle("Macros");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setBounds(100, 100, 442, 547);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnOptions = new JMenu("Options");
		menuBar.add(mnOptions);
		
		JPanel panel_5 = new JPanel();
		mnOptions.add(panel_5);
		
		JLabel lblFrquenceDenregistrementsouris = new JLabel("Fr\u00E9quence d'enregistrement (souris)");
		panel_5.add(lblFrquenceDenregistrementsouris);
		
		JSlider slider = new JSlider();
		slider.setMinorTickSpacing(1);
		slider.setSnapToTicks(true);
		slider.setMaximum(500);
		slider.setMinimum(1);
		mnOptions.add(slider);
		
		JPanel panel_4 = new JPanel();
		mnOptions.add(panel_4);
		panel_4.setLayout(new BoxLayout(panel_4, BoxLayout.X_AXIS));
		
		JLabel label = new JLabel("1");
		panel_4.add(label);
		
		Component horizontalStrut = Box.createHorizontalStrut(200);
		panel_4.add(horizontalStrut);
		
		JLabel label_1 = new JLabel("500");
		panel_4.add(label_1);
		
		JSeparator separator = new JSeparator();
		mnOptions.add(separator);
		
		JMenuItem mntmBenchmark = new JMenuItem("Benchmark");
		mntmBenchmark.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int rep = JOptionPane.showConfirmDialog(MacroGui.this, "Le benchmark va trouver les paramètres optimaux pour votre configuration\nIl vous avertira de tout problème éventuel, vous devrez bouger votre souris et taper sur votre clavier\nLes actions seront ensuite repetées\nLe benchmark durera 10 secondes", "Message", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
				if(rep == JOptionPane.YES_OPTION) {
					manager.benchmark();
				}
			}
		});
		mnOptions.add(mntmBenchmark);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel proprietes = new JPanel();
		proprietes.setBorder(new LineBorder(new Color(0, 0, 0)));
		contentPane.add(proprietes, BorderLayout.NORTH);
		
		JLabel lblPropiete = new JLabel("Propri\u00E9t\u00E9es :");
		lblPropiete.setFont(new Font("Arial", Font.PLAIN, 13));
		lblPropiete.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		JLabel lblNewLabel = new JLabel("Date : ");
		lblNewLabel.setToolTipText("Date de r\u00E9alisation de la macro");
		GroupLayout gl_proprietes = new GroupLayout(proprietes);
		gl_proprietes.setHorizontalGroup(
			gl_proprietes.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_proprietes.createSequentialGroup()
					.addComponent(lblPropiete)
					.addGap(30)
					.addComponent(lblNewLabel)
					.addGap(185))
		);
		gl_proprietes.setVerticalGroup(
			gl_proprietes.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_proprietes.createSequentialGroup()
					.addGroup(gl_proprietes.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblPropiete, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(lblNewLabel))
					.addGap(22))
		);
		proprietes.setLayout(gl_proprietes);
		
		JSplitPane splitPane = new JSplitPane();
		splitPane.setBackground(Color.GRAY);
		contentPane.add(splitPane, BorderLayout.CENTER);
		
		JPanel panel = new JPanel();
		splitPane.setLeftComponent(panel);
		panel.setLayout(new BorderLayout(0, 0));
		
		list.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent arg0) {
				//On récupère la liste des actions de la macro selectionnée
				refreshActionList();	//On met à jour l'affichage
			}
		});
		
		JScrollPane scrollPane_1 = new JScrollPane();
		panel.add(scrollPane_1, BorderLayout.CENTER);
		list.setBackground(Color.GRAY);
		list.setBorder(new LineBorder(new Color(0, 0, 0)));
		scrollPane_1.setViewportView(list);
		
		JLabel lblListeMacros = new JLabel("Liste macros");
		lblListeMacros.setForeground(Color.WHITE);
		lblListeMacros.setOpaque(true);
		lblListeMacros.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblListeMacros.setBackground(Color.GRAY);
		lblListeMacros.setFont(new Font("Arial", Font.BOLD, 13));
		lblListeMacros.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel.add(lblListeMacros, BorderLayout.NORTH);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(Color.GRAY);
		panel.add(panel_2, BorderLayout.SOUTH);
		panel_2.setLayout(new GridLayout(0, 2, 0, 0));
		
		JButton button_1 = new JButton("-");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				manager.stopRecording();
			}
		});
		button_1.setFont(new Font("Tahoma", Font.PLAIN, 13));
		button_1.setForeground(Color.WHITE);
		button_1.setBackground(Color.LIGHT_GRAY);
		panel_2.add(button_1);
		
		JButton button = new JButton("+");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String rep = JOptionPane.showInputDialog(MacroGui.this, "Entrez le nom de la macro", "Entrez un nom", JOptionPane.OK_CANCEL_OPTION);
				if(!rep.contentEquals("null")) {
					if(!listModel.contains(rep)) {
						listModel.addElement(rep);
					}else {
						JOptionPane.showConfirmDialog(MacroGui.this, "Une macro du même nom existe deja", "Erreur", JOptionPane.OK_OPTION);
					}
				}
			}
		});
		button.setForeground(Color.WHITE);
		button.setBackground(Color.LIGHT_GRAY);
		panel_2.add(button);
		
		JButton btnEnregistrer = new JButton("\u2022");
		btnEnregistrer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String selectedMacro = (String) list.getSelectedValue();
				if(selectedMacro != null) {
					manager.startRecording(selectedMacro);
				}
			}
		});
		btnEnregistrer.setForeground(Color.WHITE);
		btnEnregistrer.setBackground(Color.LIGHT_GRAY);
		panel_2.add(btnEnregistrer);
		
		JPanel panel_1 = new JPanel();
		splitPane.setRightComponent(panel_1);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		Liste.setBorder(new EmptyBorder(5, 5, 5, 5));
		Liste.setBackground(Color.GRAY);
		Liste.setAutoscrolls(true);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportView(Liste);
		Liste.setLayout(new BoxLayout(Liste, BoxLayout.Y_AXIS));
		
		
		JPanel panel_3 = new JPanel();
		panel_3.setBackground(Color.GRAY);
		panel_3.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.add(panel_3, BorderLayout.SOUTH);
		panel_3.setLayout(new BoxLayout(panel_3, BoxLayout.X_AXIS));
		
		lblLogOp.setForeground(Color.WHITE);
		panel_3.add(lblLogOp);
		
		running = true;
		startLogMessageService();
		manager = manager2;
	}
	
	/*
	 * Reforme le visuel des actions (les JPanels) à partir de la linkedList de la macro selectionnée
	 * */
	public void refreshActionList() {
		Liste.removeAll();
		String d = (String) list.getSelectedValue();
		Macro mac = manager.getMacroByName((String) list.getSelectedValue()); 
		if(mac!=null) {
			ArrayList<Action> actionlist = mac.getListe();
			for(int i =0; i<actionlist.size();i++) {
				Action cu = actionlist.get(i);
				Liste.add(new Item(i, cu.getType(), ""+ i));
			}
			Liste.revalidate();
		}
	}
	
	public void shutdown() {
		running = false;
	}
	
	
	//-----------------------------------Gestion des logs fenetre---------------------------------------------
	
	/*
	 * Ajoute un message sur le haut de la pile de messages a afficher
	 * */
	public void pushLogMessage(LogMessage message) {
		logStack.push(message);
	}
	
	/*
	 * Démarre un thread qui va vider la pile de log jusqu'a ce qu'elle soit vide.
	 * */
	public void startLogMessageService() {
		if(logStackThread== null || !logStackThread.isAlive()) {
			logStackThread = new LogMessageThread("LogMessage-Thread");
			logStackThread.start();
		}else {
			logger.warn("LogMessageService est deja lancé!");
		}
	}
	//Thread associé.
	class LogMessageThread extends Thread{
		private static final int WAIT = 200;
		private int sleep = 0;	//(s)
		private int counter = 0; //(ms)
		private LogMessage current;
		public LogMessageThread(String string) {
			super(string);
		}
		@Override
		public void run() {//TODO
			while(running) {
				if(current == null) {
					try {
						current = logStack.pop();
						init();
						lblLogOp.setText("Log : "+current.getMessage());
						sleep = current.getDuration();
						process();
					}catch(EmptyStackException ex) {
						if(sleep>=0) {
							lblLogOp.setText("Log : ");
						}
					}
				}else {
					process();
				}
				try {
					Thread.sleep(WAIT);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		private void init() {
			sleep = 0;
			counter = 0; //(ms)
		}
		
		private void process() {
			if(counter>=sleep*1000) {
				current = null;
			}
			if(sleep>=0) {
				counter +=WAIT;
			}
		}
	}
	
	/*----------------------------Gestion des evenements-------------------------
	*
	*-----------------------------Gestion du manager-----------------------------*/
	@Override
	public void saveCreationFailed() {
		SwingUtilities.invokeLater(new Runnable() {
		      public void run() {
		  		JOptionPane.showMessageDialog(MacroGui.this,"C'est la merde c'est tout cassé\n Impossible de créer le fichier de sauvegarde!", "C'est cassé!",JOptionPane.ERROR_MESSAGE);
		      }
		    });
	}

	@Override
	public void log(LogMessage logMessage) {
		pushLogMessage(logMessage);
	}

	@Override
	public void macroListUpddated() {
		macroNames = manager.getMacroNames();
		refreshActionList();
	}
}

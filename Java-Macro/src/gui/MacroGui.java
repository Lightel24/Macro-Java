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

import core.Manager;
import core.ManagerObserver;
import fr.lightel24.gitupdater.GitUpdater;

import javax.swing.JButton;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;
import javax.swing.BoxLayout;
import java.awt.Rectangle;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.border.BevelBorder;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.EmptyStackException;
import java.util.Stack;
import java.awt.event.ActionEvent;
import javax.swing.event.ListSelectionListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.event.ListSelectionEvent;

public class MacroGui extends JFrame implements ManagerObserver{

	private JPanel contentPane;
	private Manager manager;
	private Stack<LogMessage> logStack = new Stack<LogMessage>();
	private Thread logStackThread;
	private JLabel lblLogOp = new JLabel("Log : ");
	private Logger logger = LoggerFactory.getLogger(MacroGui.class);
	protected boolean running=false;
	
	public static void main(String[] args) {
		new MacroGui().setVisible(true);
	}
	
	/**
	 * Create the frame.
	 */
	public MacroGui() {
		setTitle("Macros");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setBounds(100, 100, 362, 460);
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
					.addGap(181))
		);
		gl_proprietes.setVerticalGroup(
			gl_proprietes.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_proprietes.createSequentialGroup()
					.addGroup(gl_proprietes.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblPropiete, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(lblNewLabel))
					.addGap(16))
		);
		proprietes.setLayout(gl_proprietes);
		
		JSplitPane splitPane = new JSplitPane();
		splitPane.setBackground(Color.GRAY);
		contentPane.add(splitPane, BorderLayout.CENTER);
		
		JPanel panel = new JPanel();
		splitPane.setLeftComponent(panel);
		panel.setLayout(new BorderLayout(0, 0));
		
		JList list = new JList();
		list.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent arg0) {
				
			}
		});
		list.setBackground(Color.GRAY);
		list.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel.add(list);
		
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
		button_1.setFont(new Font("Tahoma", Font.PLAIN, 13));
		button_1.setForeground(Color.WHITE);
		button_1.setBackground(Color.LIGHT_GRAY);
		panel_2.add(button_1);
		
		JButton button = new JButton("+");
		button.setForeground(Color.WHITE);
		button.setBackground(Color.LIGHT_GRAY);
		panel_2.add(button);
		
		JButton btnEnregistrer = new JButton("\u2022");
		btnEnregistrer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				manager.loadFromFile(new File("blabla"));
				
			}
		});
		btnEnregistrer.setForeground(Color.WHITE);
		btnEnregistrer.setBackground(Color.LIGHT_GRAY);
		panel_2.add(btnEnregistrer);
		
		JPanel panel_1 = new JPanel();
		splitPane.setRightComponent(panel_1);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		JPanel Liste = new JPanel();
		Liste.setBorder(new EmptyBorder(5, 5, 5, 5));
		Liste.setBackground(Color.GRAY);
		Liste.setAutoscrolls(true);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportView(Liste);
		Liste.setLayout(new BoxLayout(Liste, BoxLayout.Y_AXIS));
		
		Item item1 = new Item(0, "Action", "1");
		Liste.add(item1);
		
		Component verticalStrut_1 = Box.createVerticalStrut(5);
		Liste.add(verticalStrut_1);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		panel_1.add(scrollPane, BorderLayout.CENTER);
		splitPane.setDividerLocation(100);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBackground(Color.GRAY);
		panel_3.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.add(panel_3, BorderLayout.SOUTH);
		panel_3.setLayout(new BoxLayout(panel_3, BoxLayout.X_AXIS));
		
		lblLogOp.setForeground(Color.WHITE);
		panel_3.add(lblLogOp);
		
		running = true;
		startLogMessageService();
		manager = new Manager();
		manager.setObserver(this);
	}
	
	/*
	 * Reforme le visuel des actions (les JPanels) à partir de la linkedList de la macro selectionnée
	 * */
	public void refreshActionList() {
		
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
			logStackThread = new LogMessageThread("IO-Thread");
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
		public void run() {
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

}

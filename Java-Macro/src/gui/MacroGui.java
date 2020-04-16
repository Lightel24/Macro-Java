package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JSplitPane;
import javax.swing.JList;
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

import core.ServiceObserver;

import javax.swing.JButton;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.BoxLayout;
import java.awt.Rectangle;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;

public class MacroGui extends JFrame implements ServiceObserver{

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MacroGui frame = new MacroGui();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MacroGui() {
		setTitle("Macros");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 362, 460);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JSplitPane splitPane = new JSplitPane();
		contentPane.add(splitPane, BorderLayout.CENTER);
		
		JPanel panel = new JPanel();
		splitPane.setLeftComponent(panel);
		panel.setLayout(new BorderLayout(0, 0));
		
		JList list = new JList();
		list.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel.add(list);
		
		JLabel lblListeMacros = new JLabel("Liste macros");
		lblListeMacros.setOpaque(true);
		lblListeMacros.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblListeMacros.setBackground(Color.WHITE);
		lblListeMacros.setFont(new Font("Arial", Font.BOLD, 13));
		lblListeMacros.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel.add(lblListeMacros, BorderLayout.NORTH);
		
		JPanel panel_2 = new JPanel();
		panel.add(panel_2, BorderLayout.SOUTH);
		panel_2.setLayout(new GridLayout(0, 2, 0, 0));
		
		JButton button_1 = new JButton("-");
		panel_2.add(button_1);
		
		JButton button = new JButton("+");
		panel_2.add(button);
		
		JButton btnEnregistrer = new JButton("\u2022");
		panel_2.add(btnEnregistrer);
		
		JPanel panel_1 = new JPanel();
		splitPane.setRightComponent(panel_1);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		JPanel Liste = new JPanel();
		Liste.setBorder(new EmptyBorder(5, 5, 5, 5));
		Liste.setBackground(Color.DARK_GRAY);
		Liste.setAutoscrolls(true);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportView(Liste);
		Liste.setLayout(new BoxLayout(Liste, BoxLayout.Y_AXIS));
		
		JPanel Children = new JPanel();
		Children.setBounds(new Rectangle(5, 5, 5, 5));
		Children.setBorder(new EmptyBorder(5, 5, 5, 5));
		Liste.add(Children);
		Children.setLayout(new BoxLayout(Children, BoxLayout.X_AXIS));
		
		JLabel nbInstruction = new JLabel("1");
		Children.add(nbInstruction);
		
		Component horizontalStrut = Box.createHorizontalStrut(20);
		horizontalStrut.setPreferredSize(new Dimension(5, 0));
		horizontalStrut.setMinimumSize(new Dimension(5, 0));
		horizontalStrut.setMaximumSize(new Dimension(5, 0));
		Children.add(horizontalStrut);
		
		JLabel actionLabel = new JLabel("Action");
		Children.add(actionLabel);
		
		Component horizontalGlue = Box.createHorizontalGlue();
		Children.add(horizontalGlue);
		
		Component verticalStrut = Box.createVerticalStrut(5);
		Liste.add(verticalStrut);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		panel_1.add(scrollPane, BorderLayout.CENTER);
		splitPane.setDividerLocation(100);
		
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
	}

	@Override
	public void onServiceEnd() {
		// TODO Auto-generated method stub
	}
}

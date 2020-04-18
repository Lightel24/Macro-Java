package gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;
import javax.swing.ImageIcon;
import java.awt.Component;
import java.awt.EventQueue;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingUtilities;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class UpdateGui extends JFrame{
	
	
	private JLabel lblVrificationDeVersion = new JLabel("V\u00E9rification de version");  
	private JButton btnSkip;
	
	public UpdateGui() {
		this.setTitle("Verification de version");
		this.setUndecorated(true);
		this.setSize(320,230);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		JPanel EnTete = new JPanel();
		EnTete.setBackground(Color.GRAY);
		this.getContentPane().add(EnTete, BorderLayout.NORTH);
		
		lblVrificationDeVersion = new JLabel("V\u00E9rification de version");
		lblVrificationDeVersion.setForeground(Color.WHITE);
		lblVrificationDeVersion.setFont(new Font("Tahoma", Font.PLAIN, 20));
		EnTete.add(lblVrificationDeVersion);
		
		JPanel Centre = new JPanel();
		Centre.setBackground(Color.WHITE);
		this.getContentPane().add(Centre, BorderLayout.CENTER);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(UpdateGui.class.getResource("/res/loading.gif")));
		Centre.add(lblNewLabel);
		
		JPanel Action = new JPanel();
		Action.setBackground(Color.GRAY);
		this.getContentPane().add(Action, BorderLayout.SOUTH);
		
		btnSkip = new JButton("Skip");
		btnSkip.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		GroupLayout gl_Action = new GroupLayout(Action);
		gl_Action.setHorizontalGroup(
			gl_Action.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_Action.createSequentialGroup()
					.addContainerGap(387, Short.MAX_VALUE)
					.addComponent(btnSkip, GroupLayout.PREFERRED_SIZE, 63, GroupLayout.PREFERRED_SIZE))
		);
		gl_Action.setVerticalGroup(
			gl_Action.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_Action.createSequentialGroup()
					.addContainerGap()
					.addComponent(btnSkip))
		);
		Action.setLayout(gl_Action);
	}
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UpdateGui frame = new UpdateGui();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	public void setEnTete(String string) {
		SwingUtilities.invokeLater(new Runnable() {
		      public void run() {
		  		lblVrificationDeVersion.setText(string);
		      }
		    });
	}
	public void changeSkip(ActionListener action) {
		SwingUtilities.invokeLater(new Runnable() {
		      public void run() {
		    	btnSkip = new JButton("Arrière-plan");
				btnSkip.addActionListener(action);
		      }
		    });
		
	}
}

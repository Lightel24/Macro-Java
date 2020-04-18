package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class Item extends JPanel {
	private int ID;
	private JLabel nbInstruction = new JLabel();
	
	public Item(int ID,String actionText,String actionNumber) {
		this.ID = ID;
		this.setBorder(new EmptyBorder(5, 5, 5, 5));
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		nbInstruction.setText(actionNumber);
		this.add(nbInstruction);
		
		Component horizontalStrut_1 = Box.createHorizontalStrut(20);
		horizontalStrut_1.setPreferredSize(new Dimension(5, 0));
		horizontalStrut_1.setMinimumSize(new Dimension(5, 0));
		horizontalStrut_1.setMaximumSize(new Dimension(5, 0));
		this.add(horizontalStrut_1);
		
		JLabel lblAction = new JLabel(actionText);
		this.add(lblAction);
		
		Component horizontalGlue_1 = Box.createHorizontalGlue();
		this.add(horizontalGlue_1);
		
		JButton btnEditer_1 = new JButton("Editer");
		this.add(btnEditer_1);
		btnEditer_1.setBackground(new Color(255, 215, 0));
	}
	
	public int getID() {
		return ID;
	}
}

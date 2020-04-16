package gui;

import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JDialog;
import javax.swing.JFrame;

import com.toedter.calendar.JCalendar;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class DateChooser extends JDialog{
	private static boolean test = false;
	
	JCalendar calendar = new JCalendar();
	JHourMinuteChooser timeChooser = new JHourMinuteChooser();
	Calendar date;
	Calendar hour;
	private final JPanel panel_1 = new JPanel();
	private final JButton btnValider = new JButton("Valider");
	
	public DateChooser(){
		init();
	}
	
	public DateChooser(JFrame parent) {
		super(parent);
		init();
	}
	
	private void init() {
		this.setSize(400, 400);
		this.setTitle("Choisir une date");
		this.setModal(true);
		this.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		date = Calendar.getInstance();
		
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(new BorderLayout(0, 0));
		panel.add(calendar);
		calendar.addPropertyChangeListener(new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent arg0) {
				if (arg0.getPropertyName().equals("calendar")) {
					date = (Calendar) arg0.getNewValue();
					System.out.println(getSelectedDate());
				    }
			}
		});
		
		getContentPane().add(panel_1, BorderLayout.SOUTH);
		panel_1.add(timeChooser);
		timeChooser.setBorder(new LineBorder(new Color(0, 0, 0)));
		btnValider.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
				if(test) {
					System.exit(0);
				}
			}
		});
		btnValider.setBackground(Color.GRAY);
		
		panel_1.add(btnValider);

		this.setVisible(true);
	}

	public Date getSelectedDate() {
		Calendar date = Calendar.getInstance();
		if(timeChooser.getHorario() == "AM") {
			date.set(Calendar.AM_PM, Calendar.AM);
		}else {
			date.set(Calendar.AM_PM, Calendar.PM);
		}
		date.set(Calendar.HOUR, timeChooser.getHour());
		date.set(Calendar.MINUTE, timeChooser.getMinute());
		date.set(Calendar.SECOND, 0);
		return date.getTime();
	}
	
	public static void main(String[] args) {
		DateChooser.test = true;
		new DateChooser();
	}
}

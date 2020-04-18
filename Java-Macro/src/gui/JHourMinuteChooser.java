package gui;

	/* 
	 * Copyright (C) 2019 Ruslan Lopez Carro.
	 *
	 * This library is free software; you can redistribute it and/or
	 * modify it under the terms of the GNU Lesser General Public
	 * License as published by the Free Software Foundation; either
	 * version 2.1 of the License, or (at your option) any later version.
	 *
	 * This library is distributed in the hope that it will be useful,
	 * but WITHOUT ANY WARRANTY; without even the implied warranty of
	 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
	 * Lesser General Public License for more details.
	 *
	 * You should have received a copy of the GNU Lesser General Public
	 * License along with this library; if not, write to the Free Software
	 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
	 * MA 02110-1301  USA
	 */
	import javax.swing.JCheckBox;
	import javax.swing.JSpinner;
	import javax.swing.SpinnerListModel;
	import javax.swing.SpinnerNumberModel;
	import javax.swing.event.ChangeEvent;
	import javax.swing.event.ChangeListener;
	import java.awt.FlowLayout;
	import java.awt.Font;
	import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;
	import java.util.concurrent.Executors;
	import java.util.concurrent.ScheduledExecutorService;
	import java.util.concurrent.TimeUnit;
	import java.util.logging.Logger;

	/**
	 *
	 * @author Ruslan López Carro
	 */
	public final class JHourMinuteChooser extends javax.swing.JPanel implements Runnable {

	    private Calendar currentTime;
	    private static final Logger LOGGER = Logger.getLogger(JHourMinuteChooser.class.getName());
	    // Variables declaration - do not modify//GEN-BEGIN:variables
	    private JCheckBox currentTimeChk;
	    private JSpinner hourSpin;
	    private JSpinner meridianSpin;
	    private JSpinner minuteSpin;
	    // End of variables declaration//GEN-END:variables

	    /**
	     * Creates a Hour and Minute Chooser with the specified values.
	     *
	     * @param hour 24 hour format hour
	     * @param minute minutes
	     */
	    public JHourMinuteChooser(int hour, int minute) {
	        setName("JHourMinuteChooser");
	        initComponents();
	        currentTime = Calendar.getInstance();
	        currentTime.set(Calendar.HOUR, hour);
	        currentTime.set(Calendar.MINUTE, minute);
	        updateCurrentTimeInGUI();
	        
	        startTimerThread();
	    }
	    
	    /**
	     * Creates a Hour and Minute Chooser with the current time set.
	     */
	    public JHourMinuteChooser() {
	        setName("JHourMinuteChooser");
	        initComponents();
	        setCurrentTime();
	        startTimerThread();
	    }
	    
	    private void startTimerThread() {
	        Thread timingThread = new Thread(this);
	        timingThread.start();
	    }

	    @Override
	    public void setEnabled(boolean enable) {
	        hourSpin.setEnabled(enable);
	        minuteSpin.setEnabled(enable);
	        meridianSpin.setEnabled(enable);
	        currentTimeChk.setEnabled(enable);
	    }

	    public void setCurrentTime() {
	        currentTime = Calendar.getInstance();
	        updateCurrentTimeInGUI();
	    }

	    private void updateCurrentTimeInGUI() {
	        LOGGER.finest(currentTime.toString());
	        if (currentTime.get(Calendar.HOUR) >= 0 && currentTime.get(Calendar.HOUR) < 12) {
	            if (currentTime.get(Calendar.HOUR) == 0) {
	                hourSpin.setValue(12);
	            } else {
	                hourSpin.setValue(currentTime.get(Calendar.HOUR));
	            }
	            meridianSpin.setValue("AM");
	        } else if (currentTime.get(Calendar.HOUR) >= 12 && currentTime.get(Calendar.HOUR) <= 23) {
	            if (currentTime.get(Calendar.HOUR) == 12) {
	                hourSpin.setValue(12);
	            } else {
	                hourSpin.setValue(currentTime.get(Calendar.HOUR) - 12);
	            }
	            meridianSpin.setValue("PM");
	        }

	        minuteSpin.setValue(currentTime.get(Calendar.MINUTE));
	    }

	    public static String numberFormat(long src, String fmt) {//Format : ###.####        
	        DecimalFormat df = new DecimalFormat(fmt.replaceAll("#", "0"));
	        return df.format(src);
	    }

	    public int getHour() {
	    	try {
	    		hourSpin.commitEdit();
			} catch ( java.text.ParseException e ) {}
	        return (Integer) hourSpin.getValue();
	    }

	    public int getMinute() {
	    	try {
	    		minuteSpin.commitEdit();
			} catch ( java.text.ParseException e ) {}
	        return (Integer) minuteSpin.getValue();
	    }

	    public String getHorario() {
	        return meridianSpin.getValue().toString();
	    }

	    public String getTime() {
	        return numberFormat(Integer.parseInt(hourSpin.getValue().toString().trim()), "##") + ":" + numberFormat(Integer.parseInt(minuteSpin.getValue().toString().trim()), "##") + " " + meridianSpin.getValue().toString();
	    }

	    @SuppressWarnings("unchecked")
	    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
	    private void initComponents() {

	        hourSpin = new JSpinner();
	        minuteSpin = new JSpinner();
	        meridianSpin = new JSpinner();
	        currentTimeChk = new JCheckBox();

	        setLayout(new FlowLayout(FlowLayout.LEFT, 3, 0));

	        hourSpin.setFont(new Font("Tahoma", 1, 11)); // NOI18N
	        hourSpin.setModel(new SpinnerNumberModel(1, 1, 12, 1));
	        add(hourSpin);

	        minuteSpin.setFont(new Font("Tahoma", 1, 11)); // NOI18N
	        minuteSpin.setModel(new SpinnerNumberModel(1, 0, 59, 1));
	        add(minuteSpin);

	        meridianSpin.setFont(new Font("Tahoma", 1, 11)); // NOI18N
	        meridianSpin.setModel(new SpinnerListModel(new String[] {"AM", "PM"}));
	        add(meridianSpin);

	        currentTimeChk.setText("heure actuelle");
	        currentTimeChk.addChangeListener(new ChangeListener() {
	            public void stateChanged(ChangeEvent evt) {
	                currentTimeChkStateChanged(evt);
	            }
	        });
	        add(currentTimeChk);
	    }// </editor-fold>//GEN-END:initComponents

	    private void currentTimeChkStateChanged(ChangeEvent evt) {//GEN-FIRST:event_currentTimeChkStateChanged
	        if (currentTimeChk.isSelected()) {
	            hourSpin.setEnabled(false);
	            minuteSpin.setEnabled(false);
	            meridianSpin.setEnabled(false);
	        } else {
	            hourSpin.setEnabled(true);
	            minuteSpin.setEnabled(true);
	            meridianSpin.setEnabled(true);
	        }
	    }//GEN-LAST:event_currentTimeChkStateChanged
	    
	    @Override
	    public void run() {
	        final ScheduledExecutorService executorService
	                = Executors.newSingleThreadScheduledExecutor();
	        executorService.scheduleAtFixedRate(
	                new Runnable() {
	            @Override
	            public void run() {
	                if (isEnabled() && currentTimeChk.isSelected()) {
	                    setCurrentTime();
	                }
	            }
	        }, 0, 1, TimeUnit.SECONDS
	        );
	    }

	    public Date getCurrentTime() {
	        return currentTime.getTime();
	    }
	}


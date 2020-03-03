package timeTracker;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.sql.Timestamp;
import java.time.LocalDate;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import java.awt.Color;
import javax.swing.SwingConstants;
import java.awt.SystemColor;

@SuppressWarnings("serial")
public class TimeStamp extends JFrame {
	private JPanel contentPane;
	private JLabel startLbl;
	private JLabel stopLbl;
	private JTextField clientTxtField;
	private int startHour;
	private int startMin;
	private int startSec;
	private int stopHour;
	private int stopMin;
	private int stopSec;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TimeStamp frame = new TimeStamp();
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
	public TimeStamp() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 400, 215);
		contentPane = new JPanel();
		setResizable(false);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel startStopPnl = creatStartStopPanel();
		contentPane.add(startStopPnl, BorderLayout.NORTH);
		
		JPanel txtFieldPnl = createTxtFieldPnl();
		contentPane.add(txtFieldPnl, BorderLayout.CENTER);
		
		JPanel savePnl  = createSavePnl();
		contentPane.add(savePnl, BorderLayout.SOUTH);
	}
	
	/**
	 * Creates the panel with 
	 * the save button that writes to 
	 * a file TimeStamp.txt
	 * @return
	 */
	private JPanel createSavePnl() {
		JPanel savePnl = new JPanel();
		JButton save = new JButton("Save");
		save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try(BufferedWriter writer = new BufferedWriter(new FileWriter("TimeStamp.txt", true))){
					writer.append("Client: " + clientTxtField.getText() +" - Time in: ( " + startLbl.getText() 
									+ " ) Time out: ( " + stopLbl.getText() + ") " +  totalTime(startHour,startMin,startSec,stopHour,stopMin,stopSec) +"\n");
					startLbl.setText("");
					stopLbl.setText("");
					setClient();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				JOptionPane.showMessageDialog(null, "Saved to TimeStamp.txt", "Saved", JOptionPane.INFORMATION_MESSAGE);
			}
		});
		savePnl.add(save);
		
		return savePnl;
	}
	
	/**
	 * Creates the text field panel
	 * that displays the time stamps and
	 * you can add a client name to
	 * @return
	 */
	private JPanel createTxtFieldPnl() {
		JPanel txtFieldPnl = new JPanel();
		
		txtFieldPnl.setLayout(new GridLayout(2, 0, 0, 0));
		
		JPanel startStopTxtFieldPnl = createStartStopTxtField();
		txtFieldPnl.add(startStopTxtFieldPnl);
		
		clientTxtField = new JTextField();
		clientTxtField.setHorizontalAlignment(SwingConstants.CENTER);
		clientTxtField.setBackground(Color.LIGHT_GRAY);
		setClient();
		txtFieldPnl.add(clientTxtField);
		
		return txtFieldPnl;
	}

	private void setClient() {
		clientTxtField.setText("Enter Client");
		startLbl.setBackground(SystemColor.window);
		stopLbl.setBackground(SystemColor.window);
	}
	
	/**
	 * Creates the panel that holds two text fields that
	 * will display the start and stop time stamps
	 * @return
	 */
	private JPanel createStartStopTxtField() {
		JPanel startStopTxtFieldPnl = new JPanel();
		
		startStopTxtFieldPnl.setLayout(new GridLayout(0, 2, 15, 0));
		
		startLbl = new JLabel();
		startLbl.setOpaque(true);
		startLbl.setHorizontalAlignment(SwingConstants.CENTER);
		startLbl.setBackground(SystemColor.window);
		startStopTxtFieldPnl.add(startLbl);
		//startTxtField.setColumns(10);
		
		stopLbl = new JLabel();
		stopLbl.setOpaque(true);
		stopLbl.setHorizontalAlignment(SwingConstants.CENTER);
		stopLbl.setBackground(SystemColor.window);
		startStopTxtFieldPnl.add(stopLbl);
		//stopTxtField.setColumns(10);
		return startStopTxtFieldPnl;
	}

	private JPanel creatStartStopPanel() {
		JPanel startStopPnl = new JPanel();
		
		startStopPnl.setLayout(new GridLayout(0, 2, 100, 0));
		
		JButton start = createStartBtn();
		startStopPnl.add(start);
		
		JButton stop = createStopBtn();
		startStopPnl.add(stop);
		
		return startStopPnl;
	}
	
	/**
	 * creates the stop button
	 * with action listener to assign to stop
	 * text field
	 * @return
	 */
	private JButton createStopBtn() {
		JButton stop = new JButton("Stop Time");
		stop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				stopLbl.setText(date() + "  " + getStopTime());
				stopLbl.setBackground(Color.RED);
				clientTxtField.setText("");
				
			}
		});
		return stop;
	}
	
	/**
	 * creates the start button with an
	 * action listener to assign to the 
	 * start text field
	 * @return
	 */
	private JButton createStartBtn() {
		JButton start = new JButton("Start Time");
		start.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				startLbl.setText(date() + "  " + getStartTime());
				startLbl.setBackground(Color.GREEN);
			}
		});
		return start;
	}
	
	/**
	 * returns a current time stamp
	 * time depending on when the user 
	 * clicks start or stop
	 * @return
	 */
	@SuppressWarnings("deprecation")
	private String getStartTime() {
		Timestamp time = new Timestamp(new java.util.Date().getTime());
		startHour = time.getHours();
		startMin = time.getMinutes();
		startSec = time.getSeconds();
		return String.format("%02d:%02d:%02d ", time.getHours(), time.getMinutes(), time.getSeconds());
		
	}
	
	@SuppressWarnings("deprecation")
	private String getStopTime() {
		Timestamp time = new Timestamp(new java.util.Date().getTime());
		stopHour = time.getHours();
		stopMin = time.getMinutes();
		stopSec = time.getSeconds();
		return String.format("%02d:%02d:%02d ", time.getHours(), time.getMinutes(), time.getSeconds());
		
	}
	
	private String totalTime(int startHour, int startMin, int startSec, int stopHour, int stopMin, int stopSec) {
		int totHour = stopHour - startHour;
		int totMin = stopMin - startMin;
		int totSec = stopSec - startSec;
		return String.format("Total Time: %02d:%02d:%02d", totHour,totMin,totSec);
	}
	
	private LocalDate date() {
		return LocalDate.now();
	}
	
}

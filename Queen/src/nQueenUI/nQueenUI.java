package nQueenUI;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.nqueen.ProcessQueen;

/**
 * Takes the input from user and processes further.
 * @author apoorva
 *
 */
public class nQueenUI{

	static JTextField inputField;
	public static void main(String args[]){
		JFrame frame = new JFrame();
		final JPanel panel = new JPanel();
		JLabel lbl = new JLabel("Enter the number of n");
		inputField= new JTextField();
		inputField.setColumns(10);

		JButton btnRun = new JButton("Run");

		// on button click
		btnRun.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				String inputElement=inputField.getText();
				int inputNo = 0;
				if(inputElement!=null)
					inputNo=Integer.parseInt(inputElement);
				ProcessQueen procQueen = new ProcessQueen();
				System.out.println(inputNo);
				procQueen.generateRandomQueen(inputNo); //generate random position of queen on board
							
			}
		});

		panel.add(lbl);
		panel.add(inputField);
		panel.add(btnRun);
		frame.add(panel);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(new Dimension(200, 200));

	}

}

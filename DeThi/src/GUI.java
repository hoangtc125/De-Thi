import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.AbstractAction;
import java.awt.event.ActionEvent;
import javax.swing.Action;

public class GUI implements ActionListener {

	private JFrame frame;
	private JTextArea jtaDeBai = new JTextArea(5, 0);
	private JTextField JfdiemSo = new JTextField();
	private JRadioButton [] jrDapAn = new JRadioButton[10];
	private JButton btnSubmit = new JButton();
	private ArrayList<CauHoi> cauHoi = new ArrayList<CauHoi>();
	private int score = 0;
	private int numberOfAnswers;
	private JTextArea [] jtaDapAn = new JTextArea[10];
	private final Action action = new SwingAction();
	private ButtonGroup btnGroup = new ButtonGroup();
	private int chooser;
	private int current = 0;
	private JButton btnNext = new JButton();
	private JPanel choosePanel = new JPanel();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI window = new GUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * @throws IOException 
	 */
	public GUI() throws IOException {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 * @throws IOException 
	 */
	private void initialize() throws IOException {
		frame = new JFrame();
		frame.setTitle("Câu hỏi ôn tập TTHCM");
		frame.setAlwaysOnTop(true);
		frame.setBounds(100, 100, 745, 581);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout());
		
		cauHoi = DocDuLieu.doc();
		frame.getContentPane().add(jtaDeBai, BorderLayout.PAGE_START);
		
		JfdiemSo.setText("Score:" + score + "");
		JfdiemSo.setEditable(false);
		frame.getContentPane().add(JfdiemSo, BorderLayout.WEST);		
		
		btnNext = new JButton("Next");
		btnNext.addActionListener(this);
		btnNext.setEnabled(false);
		frame.getContentPane().add(btnNext, BorderLayout.EAST);
		
		btnSubmit = new JButton("Submit");
		btnSubmit.setAction(action);
		frame.getContentPane().add(btnSubmit, BorderLayout.PAGE_END);
		
		display();
	}
	
	private JRadioButton createRadioButton(String text, boolean select) {
		JRadioButton rad = new JRadioButton(text, select);
		rad.setBackground(Color.white);
		rad.addActionListener(this);
		return rad;
	}

	private class SwingAction extends AbstractAction {
		public SwingAction() {
			putValue(NAME, "Submit");
			putValue(SHORT_DESCRIPTION, "Some short description");
		}
		public void actionPerformed(ActionEvent e) {
			for(int i = 0; i < numberOfAnswers; i++) {
				jrDapAn[i].setBackground(Color.white);
			}
			for(int i = 0; i < cauHoi.get(current).getDapAnDung().size(); i++) {
				jrDapAn[cauHoi.get(current).getDapAnDung().get(i)].setBackground(Color.green);
				if(chooser == cauHoi.get(current).getDapAnDung().get(i)) {
					cauHoi.get(current).setCorrect(1);
				}
			}
			if(cauHoi.get(current).getCorrect() == 1) {
				score++;
				JfdiemSo.setText("score: " + score + "");
			} else {
				jrDapAn[chooser].setBackground(Color.red);
			}
			btnSubmit.setEnabled(false);
			btnNext.setEnabled(true);
		}
	}
	
	private void display() {
		jtaDeBai.setText(cauHoi.get(current).getDebai());
		jtaDeBai.setEditable(false);

		numberOfAnswers = cauHoi.get(current).getDapAn().size();
		btnGroup = new ButtonGroup();
		choosePanel.removeAll();
		choosePanel = new JPanel(new GridLayout(numberOfAnswers, 1, 5, 5));
		for(int i = 0; i < numberOfAnswers; i++) { 
			choosePanel.add(jrDapAn[i] = createRadioButton(cauHoi.get(current).getDapAn().get(i).getString(), true));
			btnGroup.add(jrDapAn[i]);
		}
		frame.getContentPane().add(choosePanel, BorderLayout.CENTER);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		for(int i = 0; i < numberOfAnswers; i++) {
			if(jrDapAn[i].isSelected()) {
				chooser = i;
			}
		}
		if(e.getSource() == btnNext) {
			current++;
			for(int i = 0; i < numberOfAnswers; i++) {
				jrDapAn[i].setBackground(Color.white);
			}
			display();
			btnSubmit.setEnabled(true);
			btnNext.setEnabled(false);
		}
	}
}

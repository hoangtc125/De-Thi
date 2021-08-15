import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
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
	private ArrayList<Integer> chooser = new ArrayList<Integer>();
	private int current = 229;
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
		frame.setBounds(100, 100, 832, 572);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout());
		
		cauHoi = DocDuLieu.doc();
		
		JfdiemSo.setText("Score: 0");
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
			}
			for(int i = 0; i < numberOfAnswers; i++) {
				if(jrDapAn[i].isSelected()) {
					chooser.add(i);
				}
			}
			if(cauHoi.get(current).getCorrectAnswer() == chooser.size()) {
				boolean mark = true;
				for(int i = 0; i < chooser.size(); i++) {
					if(!cauHoi.get(current).getDapAnDung().contains(chooser.get(i))) {
						jrDapAn[chooser.get(i)].setBackground(Color.red);
						mark = false;
					}
				}
				if(mark) {
					score++;
					JfdiemSo.setText("Score: " + score);
				}
			} else {
				for(int i = 0; i < chooser.size(); i++) {
					if(!cauHoi.get(current).getDapAnDung().contains(chooser.get(i))) {
						jrDapAn[chooser.get(i)].setBackground(Color.red);
					}
				}
			}
			btnSubmit.setEnabled(false);
			btnNext.setEnabled(true);
		}
	}
	
	private String cutString(String string) {
		StringBuilder stringBuilder = new StringBuilder();
		int count = 1;
		for(int i = 0; i < string.length(); i++) {
			stringBuilder.append(string.charAt(i));
			if(string.indexOf(" ", count * 140) == i) {
				stringBuilder.append("\n");
				count++;
			}
		}
		return stringBuilder.toString();
	}
	
	private void display() {
		jtaDeBai.setText(cutString(cauHoi.get(current).getDebai()) + (cauHoi.get(current).getCorrectAnswer() > 1 ? "\n(Có thể chọn nhiều hơn 1 đáp án)" : "\n(Chỉ chọn 1 đáp án)"));
		jtaDeBai.setEditable(false);
		
		numberOfAnswers = cauHoi.get(current).getDapAn().size();
		btnGroup = new ButtonGroup();
		choosePanel.removeAll();
		choosePanel = new JPanel(new GridLayout(numberOfAnswers, 1, 5, 5));
		for(int i = 0; i < numberOfAnswers; i++) { 
			choosePanel.add(jrDapAn[i] = createRadioButton(cutString(cauHoi.get(current).getDapAn().get(i).getString()), false));
			if(cauHoi.get(current).getCorrectAnswer() <= 1) {
				btnGroup.add(jrDapAn[i]);
			}
		}
		frame.getContentPane().add(jtaDeBai, BorderLayout.NORTH);
		frame.getContentPane().add(choosePanel, BorderLayout.CENTER);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == btnNext) {
			current++;
			for(int i = 0; i < numberOfAnswers; i++) {
				jrDapAn[i].setBackground(Color.white);
			}
			display();
			chooser = new ArrayList<Integer>();
			btnSubmit.setEnabled(true);
			btnNext.setEnabled(false);
		}
	}
}

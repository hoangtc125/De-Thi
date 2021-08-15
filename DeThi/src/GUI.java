import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.BreakIterator;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.Icon;
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
	private ArrayList<CauHoi> cauHoi = new ArrayList<CauHoi>();
	private JTextArea [] jtaDapAn = new JTextArea[10];
	private final Action action = new SwingAction();
	private ButtonGroup btnGroup = new ButtonGroup();
	private ArrayList<Integer> chooser = new ArrayList<Integer>();
	private JButton btnNext = new JButton("Next Question"),
			btnSubmit = new JButton("Submit"),
			btnPrev = new JButton("Previous Question"),
			btnMenuNext = new JButton("Next Page"),
			btnMenuPrev = new JButton("Previous Page");
	private JPanel answerPanel = new JPanel(),
					leftPanel = new JPanel(new BorderLayout()),
					endLeftPanel = new JPanel();
	private JButton [] btnNumber = new JButton[250];
	private JPanel [] menuPanel = new JPanel[5];
	private int	numberOfAnswers,
				current = 0,
				score = 0,
				currentPage = 0;

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
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout());
		
		cauHoi = DocDuLieu.doc();
		
		JfdiemSo.setText("Score: 0");
		JfdiemSo.setEditable(false);
		for(int i = 0; i < menuPanel.length; i++) {
			menuPanel[i] = new JPanel(new GridLayout(10, 5, 5, 5));
		}
		for(int i = 0; i < btnNumber.length; i++) {
			btnNumber[i] = new JButton((i + 1) + "");
			btnNumber[i].addActionListener(this);
			btnNumber[i].setBackground(Color.white);
			menuPanel[i / 50].add(btnNumber[i]);
		}
		btnMenuPrev.addActionListener(this);
		btnMenuPrev.setBackground(Color.white);
		btnMenuNext.addActionListener(this);
		btnMenuNext.setBackground(Color.white);
		leftPanel.add(menuPanel[currentPage], BorderLayout.CENTER);
		leftPanel.add(JfdiemSo, BorderLayout.PAGE_START);
		endLeftPanel = new JPanel(new GridLayout(1, 2, 5, 5));
//		endLeftPanel.add(new JLabel());
		endLeftPanel.add(btnMenuPrev);
//		endLeftPanel.add(new JLabel());
		endLeftPanel.add(btnMenuNext);
//		endLeftPanel.add(new JLabel());
		leftPanel.add(endLeftPanel, BorderLayout.PAGE_END);
		leftPanel.setPreferredSize(new Dimension(300, 500));
		frame.getContentPane().add(leftPanel, BorderLayout.WEST);		

		btnSubmit.setAction(action);
		btnSubmit.setBackground(Color.white);
		btnNext.addActionListener(this);
		btnNext.setEnabled(false);
		btnNext.setBackground(Color.white);
		btnPrev.addActionListener(this);
		btnPrev.setBackground(Color.white);
		btnPrev.setEnabled(false);
		JPanel endPanel = new JPanel(new GridLayout(1, 5, 5, 5));
		endPanel.add(new JLabel());
		endPanel.add(btnPrev);
		endPanel.add(new JLabel());
		endPanel.add(btnSubmit);
		endPanel.add(new JLabel());
		endPanel.add(btnNext);
		endPanel.add(new JLabel());
		frame.getContentPane().add(endPanel, BorderLayout.PAGE_END);
		
		display();
		frame.setBounds(100, 100, 1200, 600);
	}
	
	private JRadioButton createRadioButton(String text, boolean select) {
		JRadioButton rad = new JRadioButton(text, select);
		rad.setBackground(Color.white);
		rad.addActionListener(this);
		return rad;
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
		btnNumber[current].setBackground(Color.yellow);
		
		numberOfAnswers = cauHoi.get(current).getDapAn().size();
		btnGroup = new ButtonGroup();
		answerPanel.removeAll();
		answerPanel = new JPanel(new GridLayout(numberOfAnswers, 1, 5, 5));
		for(int i = 0; i < numberOfAnswers; i++) { 
			answerPanel.add(jrDapAn[i] = createRadioButton(cutString(cauHoi.get(current).getDapAn().get(i).getString()), false));
			if(cauHoi.get(current).getCorrectAnswer() <= 1) {
				btnGroup.add(jrDapAn[i]);
			}
		}
		frame.getContentPane().add(jtaDeBai, BorderLayout.NORTH);
		frame.getContentPane().add(answerPanel, BorderLayout.CENTER);
	}
	
	private void displayPage() {
		leftPanel.removeAll();
		leftPanel = new JPanel(new BorderLayout());
		leftPanel.add(JfdiemSo, BorderLayout.PAGE_START);
		leftPanel.add(endLeftPanel, BorderLayout.PAGE_END);
		leftPanel.add(menuPanel[currentPage], BorderLayout.CENTER);
		frame.getContentPane().add(leftPanel, BorderLayout.WEST);
	}
	
	private void setUp() {
		for(int i = 0; i < numberOfAnswers; i++) {
			jrDapAn[i].setBackground(Color.white);
		}
		display();
		chooser = new ArrayList<Integer>();
		btnSubmit.setEnabled(true);
		btnNext.setEnabled(false);
		btnPrev.setEnabled(false);
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
					btnNumber[current].setBackground(Color.green);
				} else {
					btnNumber[current].setBackground(Color.red);
					for(int i = 0; i < chooser.size(); i++) {
						if(!cauHoi.get(current).getDapAnDung().contains(chooser.get(i))) {
							jrDapAn[chooser.get(i)].setBackground(Color.red);
						}
					}
				}
			} else {
				btnNumber[current].setBackground(Color.red);
				for(int i = 0; i < chooser.size(); i++) {
					if(!cauHoi.get(current).getDapAnDung().contains(chooser.get(i))) {
						jrDapAn[chooser.get(i)].setBackground(Color.red);
					}
				}
			}
			btnSubmit.setEnabled(false);
			btnNext.setEnabled(true);
			if(current > 0) btnPrev.setEnabled(true);
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == btnNext && current < 250) {
			current++;
			setUp();
		}
		if(e.getSource() == btnPrev && current > 0) {
			current--;
			setUp();
		}
		if(e.getSource() == btnMenuNext && currentPage < 5) {
			currentPage++;
			displayPage();
		}
		if(e.getSource() == btnMenuPrev && currentPage > 0) {
			currentPage--;
			displayPage();
		}
		for(int i = 0; i < btnNumber.length; i++) {
			if(e.getSource() == btnNumber[i]) {
				btnNumber[current].setBackground(Color.white);
				current = i;
				setUp();
			}
		}
	}
}

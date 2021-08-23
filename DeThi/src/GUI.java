import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.Scanner;

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
import javax.swing.SwingConstants;

public class GUI implements ActionListener {

	private JFrame frame;
	private JTextArea jaDeBai = new JTextArea(5, 0);
	private JTextField jdDiemso = new JTextField(),
					   jdInput = new JTextField(),
					   jdOutput = new JTextField();
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
					rightPanel = new JPanel(new BorderLayout()),
					centerRightPanel = new JPanel(new BorderLayout()),
					endRightPanel = new JPanel();
//	private JButton [] btnNumber = new JButton[250];
	private ArrayList<JButton> btnNumber = new ArrayList<JButton>();
	private JPanel [] menuPanel = new JPanel[5];
	private int	numberOfAnswers,
				current = 0,
				score = 0,
				currentPage = 0;
	private Scanner scanner = new Scanner(jdInput.getText());
	private Font font = new Font("Aria", Font.BOLD, 20);
	private ArrayList<Integer> doneAnswer = new ArrayList<Integer>();

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
		
		jdDiemso.setText("Score: 0");
		jdDiemso.setEditable(false);
		jdDiemso.setFont(font);
		jdDiemso.setBackground(Color.white);
		for(int i = 0; i < menuPanel.length; i++) {
			menuPanel[i] = new JPanel(new GridLayout(10, 5, 0, 0));
		}
		for(int i = 0; i < DocDuLieu.numbersOfQuestion; i++) {
			btnNumber.add(new JButton((i + 1) + ""));
			btnNumber.get(i).addActionListener(this);
//			btnNumber.get(i).addMouseListener(new customMouseListener(i, -1));
			btnNumber.get(i).setBackground(Color.white);
		}
		btnMenuPrev.addActionListener(this);
		btnMenuPrev.setBackground(Color.white);
		btnMenuNext.addActionListener(this);
		btnMenuNext.setBackground(Color.white);
//		rightPanel.add(menuPanel[currentPage], BorderLayout.CENTER);
		rightPanel.add(centerRightPanel, BorderLayout.CENTER);
		rightPanel.add(jdDiemso, BorderLayout.PAGE_START);
		endRightPanel = new JPanel(new GridLayout(3, 1, 0, 0));
		endRightPanel.setBackground(Color.white);
//		endRightPanel.add(new JLabel(" "));
		JLabel label_2 = new JLabel("Page sẽ tự sang trang kế khi làm qua câu cuối cùng");
		label_2.setHorizontalAlignment(SwingConstants.CENTER);
		endRightPanel.add(label_2);
		JLabel label_1 = new JLabel("Sau khi submit hãy bấm Next/Previous Question ");
		label_1.setHorizontalAlignment(SwingConstants.CENTER);
		endRightPanel.add(label_1);
		JLabel label = new JLabel("Numbers of question: " + DocDuLieu.numbersOfQuestion);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		endRightPanel.add(label);
//		endRightPanel.add(btnMenuPrev);
//		endRightPanel.add(jdInput);
//		jdInput.addMouseListener(new customMouseListener());
//		endRightPanel.add(btnMenuNext);
//		endRightPanel.add(jdOutput);
		rightPanel.add(endRightPanel, BorderLayout.PAGE_END);
		rightPanel.setPreferredSize(new Dimension(300, 500));
		frame.getContentPane().add(rightPanel, BorderLayout.EAST);		

		btnSubmit.setAction(action);
		btnSubmit.setBackground(Color.white);
		btnSubmit.setFont(font);
		btnNext.addActionListener(this);
		btnNext.setEnabled(false);
		btnNext.setBackground(Color.white);
		btnNext.setFont(font);
		btnPrev.addActionListener(this);
		btnPrev.setBackground(Color.white);
		btnPrev.setEnabled(false);
		btnPrev.setFont(font);
//		JPanel endPanel = new JPanel(new GridLayout(1, 5, 0, 0));
		JPanel endPanel = new JPanel(new BorderLayout());
		endPanel.setBackground(Color.white);
		endPanel.add(btnPrev, BorderLayout.WEST);
		endPanel.add(btnSubmit, BorderLayout.CENTER);
		endPanel.add(btnNext, BorderLayout.EAST);
		frame.getContentPane().add(endPanel, BorderLayout.PAGE_END);
		
		display();
		displayPage();
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
			if(string.indexOf(" ", count * 110) == i) {
				stringBuilder.append("\n");
				count++;
			}
		}
		return stringBuilder.toString();
	}
	
	private void display() {
		
		jaDeBai.setText(cutString(cauHoi.get(current).getDebai()) + (cauHoi.get(current).getCorrectAnswer() > 1 ? "\n\n(Có thể chọn nhiều hơn 1 đáp án)" : "\n\n(Chỉ chọn 1 đáp án)"));
		jaDeBai.setEditable(false);
		jaDeBai.setFont(font);
		btnNumber.get(current).setBackground(Color.yellow);
		
		numberOfAnswers = cauHoi.get(current).getDapAn().size();
		btnGroup = new ButtonGroup();
		answerPanel.removeAll();
		answerPanel = new JPanel(new GridLayout(numberOfAnswers, 1, 0, 0));
		answerPanel.setBackground(Color.white);
		for(int i = 0; i < numberOfAnswers; i++) { 
			answerPanel.add(jrDapAn[i] = createRadioButton(cutString(cauHoi.get(current).getDapAn().get(i).getString().trim()), false));
			jrDapAn[i].setFont(font);
//			jrDapAn[i].addMouseListener(new customMouseListener(-1, i));
			if(cauHoi.get(current).getCorrectAnswer() <= 1) {
				btnGroup.add(jrDapAn[i]);
			}
		}
		frame.getContentPane().add(jaDeBai, BorderLayout.NORTH);
		frame.getContentPane().add(answerPanel, BorderLayout.CENTER);
	}
	
	private void displayPage() {
		centerRightPanel.removeAll();
		centerRightPanel = new JPanel(new GridLayout(10, 5, 0, 0));
		centerRightPanel.setBackground(Color.white);
		for(int i = 50 * currentPage; i < (50 * currentPage + 50 < DocDuLieu.numbersOfQuestion ? 50 * currentPage + 50 : DocDuLieu.numbersOfQuestion) ; i++) {
			centerRightPanel.add(btnNumber.get(i));
		}
		rightPanel.add(centerRightPanel, BorderLayout.CENTER);
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
			doneAnswer.add(current);
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
					jdDiemso.setText("Score: " + score);
					btnNumber.get(current).setBackground(Color.green);
				} else {
					btnNumber.get(current).setBackground(Color.red);
					for(int i = 0; i < chooser.size(); i++) {
						if(!cauHoi.get(current).getDapAnDung().contains(chooser.get(i))) {
							jrDapAn[chooser.get(i)].setBackground(Color.red);
						}
					}
				}
			} else {
				btnNumber.get(current).setBackground(Color.red);
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
	
	class customMouseListener implements MouseListener {
		private int i = -1;
		private int j = -1;
		public customMouseListener(int i, int j) {
			// TODO Auto-generated constructor stub
			this.i = i;
			this.j = j;
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			if(i > -1) btnNumber.get(i).setBackground(Color.yellow);
			if(j > -1 && !jrDapAn[j].isSelected() && btnSubmit.isEnabled()) jrDapAn[j].setBackground(Color.yellow);
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			if(i > -1) btnNumber.get(i).setBackground(Color.white);
			if(j > -1 && !jrDapAn[j].isSelected() && btnSubmit.isEnabled()) jrDapAn[j].setBackground(Color.white);
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == btnNext && current < DocDuLieu.numbersOfQuestion) {
			current++;
			setUp();
			if(current / 50 > currentPage) {
				currentPage++;
				displayPage();
			}
		}
		if(e.getSource() == btnPrev && current > 0) {
			current--;
			setUp();
			if(current / 50 < currentPage) {
				currentPage--;
				displayPage();
			}
		}
		if(e.getSource() == btnMenuNext && currentPage < DocDuLieu.numbersOfQuestion / 50) {
			currentPage++;
			displayPage();
		}
		if(e.getSource() == btnMenuPrev && currentPage > 0) {
			currentPage--;
			displayPage();
		}
		for(int i = 0; i < DocDuLieu.numbersOfQuestion; i++) {
			if(e.getSource() == btnNumber.get(i)) {
				if(current == i) break;
				btnNumber.get(current).setBackground(Color.white);
				current = i;
				setUp();
			}
		}
		for(int i = 0; i < numberOfAnswers; i++) {
			if(jrDapAn[i].isSelected()) {
				jrDapAn[i].setBackground(Color.yellow);
			} else {
				jrDapAn[i].setBackground(Color.white);
			}
		}
	}
}

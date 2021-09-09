package typing;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import java.awt.GridBagLayout;
import javax.swing.JTextField;
import java.awt.GridLayout;
import javax.swing.JTextArea;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.JLabel;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.awt.Color;
import javax.swing.DropMode;
import java.awt.Font;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import org.eclipse.wb.swing.FocusTraversalOnArray;
import java.awt.Component;

public class Typing {

	private JFrame myFrame;
	private JTextField jfIntroduce;
	private JTextField jfAuthor;
	private JTextArea jaDeBai;
	private JPanel TypePanel;
	private JButton btnTime;
	private JButton btnReset;
	private JLabel lbRes;
	private JLabel lbPWM;
	private JLabel lbToHopPhim;
	private JLabel lbCorrect;
	private JLabel lbWrong;
	private JLabel lbPercent;
	private JPanel panel;
	private JTextArea jaTyping;
	private JTextArea jaTyped;
	private boolean typed = false,
					isReset = false;
	private int delay = 1000;
    private int period = 1000;
    private int interval = 60;
    private int count = 0,
    			correct = 0,
    			wrong = 0,
    			percent = 0;
    private Timer timer;
    private String dataString = "các bạn có biết sự chênh lệch giàu nghèo của các nước trên thế giới thể hiện qua điều gì không, đó là chất lượng sống, các bạn có thể thấy, ở hoa kỳ, người ta có thể vứt bỏ những con gà rán béo ngậy vào thùng rác, chỉ vì nó không đạt tiêu chuẩn gì đó,nhưng ở một số nơi ở châu phi hay nam á, vẫn có hiện tượng thiếu lương thực và nạn đói, ở những nước phát triển người ta thích du lịch tận hưởng cuộc sống và thích những cái đẹp, còn ở những nước nghèo, có những người thậm chí chưa tiếp xúc với máy tính, thang máy";
    private ArrayList<String> dataArrayList = new ArrayList<String>();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Typing window = new Typing();
					window.myFrame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Typing() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		myFrame = new JFrame();
		myFrame.setBackground(Color.LIGHT_GRAY);
		myFrame.getContentPane().setBackground(Color.LIGHT_GRAY);
		myFrame.setFont(new Font("Dialog", Font.BOLD, 24));
		myFrame.setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\ADMIN\\Pictures\\meme\\221904831_368200178046990_5328942062708133551_n.jpg"));
		myFrame.setTitle("Kiểm tra tốc độ đánh máy");
		myFrame.setBounds(100, 100, 909, 309);
		myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		myFrame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel ResPanel = new JPanel();
		ResPanel.setBorder(new MatteBorder(0, 0, 0, 1, (Color) new Color(0, 0, 0)));
		ResPanel.setBackground(Color.LIGHT_GRAY);
		myFrame.getContentPane().add(ResPanel, BorderLayout.WEST);
		ResPanel.setLayout(new GridLayout(6, 0, 0, 0));
		
		lbRes = new JLabel("Kết quả");
		lbRes.setBackground(Color.LIGHT_GRAY);
		lbRes.setFont(new Font("Tahoma", Font.BOLD, 18));
		lbRes.setHorizontalAlignment(SwingConstants.CENTER);
		ResPanel.add(lbRes);
		
		lbPWM = new JLabel("x PWM (từ mỗi phút)");
		lbPWM.setBackground(Color.GRAY);
		lbPWM.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lbPWM.setHorizontalAlignment(SwingConstants.LEFT);
		ResPanel.add(lbPWM);
		
		lbPercent = new JLabel("Độ chính xác x%");
		lbPercent.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lbPercent.setHorizontalAlignment(SwingConstants.LEFT);
		ResPanel.add(lbPercent);
		
		lbToHopPhim = new JLabel("Tổ hợp phím (x|x) x");
		lbToHopPhim.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lbToHopPhim.setHorizontalAlignment(SwingConstants.LEFT);
		ResPanel.add(lbToHopPhim);
		
		lbCorrect = new JLabel("Những từ đúng x");
		lbCorrect.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lbCorrect.setHorizontalAlignment(SwingConstants.LEFT);
		ResPanel.add(lbCorrect);
		
		lbWrong = new JLabel("Những từ sai x");
		lbWrong.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lbWrong.setEnabled(true);
		lbWrong.setHorizontalAlignment(SwingConstants.LEFT);
		ResPanel.add(lbWrong);
		ResPanel.setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{lbRes, lbPWM, lbPercent, lbToHopPhim, lbCorrect, lbWrong}));
		
		JPanel TypingPanel = new JPanel();
		TypingPanel.setBorder(new MatteBorder(0, 0, 0, 0, (Color) new Color(0, 0, 0)));
		myFrame.getContentPane().add(TypingPanel, BorderLayout.CENTER);
		TypingPanel.setLayout(new GridLayout(0, 1, 0, 0));
		
		jaDeBai = new JTextArea();
		jaDeBai.setFont(new Font("Monospaced", Font.PLAIN, 23));
		jaDeBai.setBackground(Color.LIGHT_GRAY);
		jaDeBai.setEditable(false);
		String [] strings = dataString.split(" ");
		for (String string : strings) {
			dataArrayList.add(string);
		}
		setDeBai();
		TypingPanel.add(jaDeBai);
		
		TypePanel = new JPanel();
		TypePanel.setBorder(new MatteBorder(0, 0, 0, 0, (Color) new Color(0, 0, 0)));
		TypePanel.setBackground(Color.LIGHT_GRAY);
		TypingPanel.add(TypePanel);
		TypePanel.setLayout(new BoxLayout(TypePanel, BoxLayout.X_AXIS));
		
		panel = new JPanel();
		panel.setBorder(new MatteBorder(1, 0, 0, 0, (Color) new Color(0, 0, 0)));
		TypePanel.add(panel);
		panel.setLayout(new GridLayout(2, 0, 0, 0));
		
		jaTyping = new JTextArea();
		jaTyping.setForeground(Color.WHITE);
		jaTyping.setFont(new Font("Monospaced", Font.PLAIN, 30));
		jaTyping.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub

			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				if(typed == false && e.getKeyCode() != KeyEvent.VK_SPACE && jaTyping.getText().trim().length() > 0) {
					setLock();
					typed = true;
					isReset = false;
				}

				if(jaTyping.getText().length() > 0 && e.getKeyCode() != KeyEvent.VK_BACK_SPACE) {
					jaTyped.append(jaTyping.getText().charAt(jaTyping.getText().length() - 1) + "");
					if(jaTyped.getText().length() % (panel.getWidth() / 6) == 0) {
						jaTyped.append("\n");
					}
				}
				
				if (e.getKeyCode() == KeyEvent.VK_SPACE && jaTyping.getText().trim().length() > 0) {
					if(dataArrayList.get(count).compareTo(jaTyping.getText().trim()) == 0) {
						correct++;
					} else {
						wrong++;
					}
					count++;
					if(count % 21 == 0) {
						setDeBai();
					}
					jaTyping.setText(null);
					jaTyped.append(" ");

	    	        lbCorrect.setText("Những từ đúng " + correct);
	    	        lbWrong.setText("Những từ sai " + wrong);
	    	        lbPercent.setText("Độ chính xác " + (correct / count * 100.0) + "%");
	    	        lbPWM.setText(count + " PWM (từ mỗi phút)");
	            }
				
				if(e.getKeyCode() == KeyEvent.VK_SPACE && jaTyping.getText().trim().length() == 0) {
					jaTyping.setText(null);
				}
				
				if(e.getKeyCode() == KeyEvent.VK_BACK_SPACE && jaTyping.getText().length() > 0) {
					if(jaTyped.getText().length() > 13) {
						jaTyped.setText(jaTyped.getText().substring(0, jaTyped.getText().length() - 1));
					}
				}
			}
		});
		jaTyping.setWrapStyleWord(true);
		jaTyping.setLineWrap(true);
		jaTyping.setBackground(Color.GRAY);
		panel.add(jaTyping);
		
		jaTyped = new JTextArea();
		jaTyped.setBackground(Color.LIGHT_GRAY);
		jaTyped.setText("Bạn đã nhập: ");
		jaTyped.setEditable(false);
		panel.add(jaTyped);
		
		btnTime = new JButton("60");
		btnTime.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 16));
		btnTime.setBackground(Color.LIGHT_GRAY);
		TypePanel.add(btnTime);
		
		btnReset = new JButton("Reset");
		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				isReset = true;
				jaTyped.setText("Bạn đã nhập: ");
				jaDeBai.setText(null);
				jaTyping.setText(null);
				jaTyping.setEditable(true);
				lbPWM.setText("x PWM (từ mỗi phút)");
				lbPercent.setText("Độ chính xác x%");
				lbToHopPhim.setText("Tổ hợp phím (x|x) x");
				lbCorrect.setText("Những từ đúng x");
				lbWrong.setText("Những từ sai x");
				interval = 60;
				btnTime.setText("60");
				count = correct = wrong = percent = 0;
				typed = false;
				setDeBai();
			}
		});
		btnReset.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 16));
		btnReset.setBackground(Color.LIGHT_GRAY);
		TypePanel.add(btnReset);
		
		jfIntroduce = new JTextField();
		jfIntroduce.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 20));
		jfIntroduce.setBackground(Color.LIGHT_GRAY);
		jfIntroduce.setHorizontalAlignment(SwingConstants.CENTER);
		jfIntroduce.setText("Phần mềm hỗ trợ kiểm tra tốc độ đánh máy của bạn");
		jfIntroduce.setEditable(false);
		myFrame.getContentPane().add(jfIntroduce, BorderLayout.NORTH);
		jfIntroduce.setColumns(10);
		
		jfAuthor = new JTextField();
		jfAuthor.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 20));
		jfAuthor.setBackground(Color.LIGHT_GRAY);
		jfAuthor.setText("MadeBy Trần Công Hoàng 20194060");
		jfAuthor.setHorizontalAlignment(SwingConstants.CENTER);
		jfAuthor.setEditable(false);
		myFrame.getContentPane().add(jfAuthor, BorderLayout.SOUTH);
		jfAuthor.setColumns(10);
	}
	
	private void setDeBai() {
		jaDeBai.setText(null);
		for(int index = count; index <= count + 20; index++) {
			jaDeBai.append(dataArrayList.get(index) + " ");
			if(index == count + 10) {
				jaDeBai.append("\n");
			}
		}
	}
	
	private void setLock() {
	    timer = new Timer();
	    timer.scheduleAtFixedRate(new TimerTask() {
	 
	        public void run() {
	        	interval--;
    	    	btnTime.setText((interval > 9 ? interval : "0" + interval) + "");
	        	if(isReset) {
	    	        timer.cancel();
	        		return;
	        	}
	    	    if (interval == 0) {
	    	        timer.cancel();
	    	        jaTyping.setEditable(false);
	    	        lbCorrect.setText("Những từ đúng " + correct);
	    	        lbWrong.setText("Những từ sai " + wrong);
	    	        lbPercent.setText("Độ chính xác " + (correct / (count > 0 ? count : 0) * 100.0) + "%");
	    	        lbPWM.setText(count + " PWM (từ mỗi phút)");
	    	    }
	        }
	    }, delay, period);
	}

}

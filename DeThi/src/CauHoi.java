import java.awt.List;
import java.util.ArrayList;

public class CauHoi {
	private int correctAnswer;
	private String debai;
	private ArrayList<DapAn> dapAn = new ArrayList<DapAn>();
	private ArrayList<Integer> dapAnDung = new ArrayList<Integer>();
	private int correct = 0;
		
	public int getCorrect() {
		return correct;
	}
	public void setCorrect(int correct) {
		this.correct = correct;
	}
	public int getCorrectAnswer() {
		return correctAnswer;
	}
	public void setCorrectAnswer(int correctAnswer) {
		this.correctAnswer = correctAnswer;
	}
	public ArrayList<DapAn> getDapAn() {
		return dapAn;
	}
	public void setDapAn(ArrayList<DapAn> dapAn) {
		this.dapAn = dapAn;
	}
	public ArrayList<Integer> getDapAnDung() {
		return dapAnDung;
	}
	public void setDapAnDung(ArrayList<Integer> dapAnDung) {
		this.dapAnDung = dapAnDung;
	}
	public String getDebai() {
		return debai;
	}
	public void setDebai(String debai) {
		this.debai = debai;
	}
	
	public CauHoi() {
		super();
	}
	
	public void addDapAn(DapAn d) {
		dapAn.add(d);
		if(d.getDapan() == 1) {
			this.correctAnswer++;
			dapAnDung.add(dapAn.size() - 1);
		}
	}
}

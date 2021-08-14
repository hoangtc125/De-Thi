import java.util.ArrayList;

public class BoDeThi {
	private int score,
				begin,
				end;
	private ArrayList<CauHoi> cauhoi = new ArrayList<CauHoi>();
	
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public int getBegin() {
		return begin;
	}
	public void setBegin(int begin) {
		this.begin = begin;
	}
	public int getEnd() {
		return end;
	}
	public void setEnd(int end) {
		this.end = end;
	}
	public ArrayList<CauHoi> getCauhoi() {
		return cauhoi;
	}
	public void setCauhoi(ArrayList<CauHoi> cauhoi) {
		this.cauhoi = cauhoi;
	}
	
	public BoDeThi() {
		super();
	}
	
	public void addCauHoi(CauHoi c) {
		cauhoi.add(c);
	}
	
	public ArrayList<Integer> ChamDiem(int i) {
		return cauhoi.get(i).getDapAnDung();
	}
}

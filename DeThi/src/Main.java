import java.io.IOException;
import java.util.ArrayList;

public class Main {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		ArrayList<CauHoi> cauHoi = new ArrayList<CauHoi>();
		cauHoi = DocDuLieu.doc();
		for(int i = 0; i < cauHoi.size(); i++) {
			System.out.println(cauHoi.get(i).getDebai());
			for(int j = 0; j < cauHoi.get(i).getDapAn().size(); j++) {
				System.out.println(cauHoi.get(i).getDapAn().get(j).getString());				
			}
		}
	}

}

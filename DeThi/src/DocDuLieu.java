import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class DocDuLieu {
	static public ArrayList<CauHoi> doc() throws IOException {
		ArrayList<CauHoi> cauHoi = new ArrayList<CauHoi>();
		FileReader fr = new FileReader("src\\ngan hang cau hoi.txt");
        BufferedReader br = new BufferedReader(fr);
  
        String string;
        int count = 0;
        while ((string = br.readLine()) != null) {
        	System.out.println(string.substring(0, 4));
            if(string.substring(0, 4).compareTo("Câu") == 0) {
            	CauHoi c = new CauHoi();
            	c.setDebai(string);
            	cauHoi.add(c);
            	count++;
            }
            if(string.charAt(0) == '*' || string.charAt(1) == '.') {
        		DapAn d = new DapAn();
        		if(string.charAt(0) == '*') {
        			d.setString(string.substring(1));
        			d.setDapan(1);
        		} else {
					d.setString(string);
					d.setDapan(0);
				}
        		cauHoi.get(count - 1).addDapAn(d);
        	}
        }
        br.close();
        fr.close();
		return cauHoi;
	}
}

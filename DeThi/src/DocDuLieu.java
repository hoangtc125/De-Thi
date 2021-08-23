import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class DocDuLieu {
	static public int numbersOfQuestion = 0;
	static public ArrayList<CauHoi> doc() throws IOException {
		ArrayList<CauHoi> cauHoi = new ArrayList<CauHoi>();
		FileReader fr = new FileReader("src\\ngan hang cau hoi 2.txt");
        BufferedReader br = new BufferedReader(fr);
  
        String string;
        while ((string = br.readLine()) != null) {
        	if(string.length() <= 0) {
        		continue;
        	}
            if(string.indexOf("Câu") >= 0 && string.substring(string.indexOf("Câu"), string.indexOf("Câu") + 4).compareTo("Câu ") == 0) {
            	try {
            		Integer.parseInt(string.charAt(4 + string.indexOf("Câu")) + "");
                 	CauHoi c = new CauHoi();
                 	c.setDebai(string);
                 	cauHoi.add(c);
                 	numbersOfQuestion++;
				} catch (Exception e) {
					// TODO: handle exception
					continue;
				}
            } else {
            	StringBuilder stringBuilder = new StringBuilder(string);
                stringBuilder.insert(0, " ");
                int count = 0;
                ArrayList<Integer> index = new ArrayList<Integer>();
                while(stringBuilder.toString().indexOf(".", count) != -1 && count < stringBuilder.toString().length() - 1) {
                	count = stringBuilder.toString().indexOf(".", count);
                	if(count != stringBuilder.toString().length() - 1) index.add(count);
                	count++;
                }
                if(index.size() > 1) {
                	for(int i = 0; i < index.size(); i++) {
                    	DapAn d = new DapAn();
            			d.setString(stringBuilder.toString().substring(index.get(i) - 2, (i + 1) < index.size() ? index.get(i + 1) - 2 : stringBuilder.toString().length()));
            			if(d.getString().contains("*")) {
            				d.setDapan(1);
            				d.setString(d.getString().substring(1));
            			} else {
        					d.setDapan(0);
        					d.setString(d.getString().substring(0));
        				}
                		cauHoi.get(numbersOfQuestion - 1).addDapAn(d);
                    }
                } else {
                	if(string.charAt(0) == '*' || string.charAt(1) == '.' || string.charAt(1) == ')') {
                		DapAn d = new DapAn();
                		if(string.charAt(0) == '*') {
                			d.setString(string.substring(1));
                			d.setDapan(1);
                		} else {
        					d.setString(string);
        					d.setDapan(0);
        				}
                		cauHoi.get(numbersOfQuestion - 1).addDapAn(d);
                	}
    			}
			}
            
        }
        br.close();
        fr.close();
		return cauHoi;
	}
}

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
//mathematicaで最尤法をするためのプログラム
public class kankaku_para{

    public static void main(String[] args) throws IOException{

    	BufferedReader br = new BufferedReader(new FileReader("filelist_kankaku.txt"));//読み取りたいファイル名の記入
        String txtFileName;

    	while((txtFileName = br.readLine()) != null) {
    		//String Index[] = new String[300000];
    		String Index;
            //int buffer1[] = new int[300000];
    		int day = 0;
            //String buffer2[] = new String[300000];
    		double time = 0;
            int count_day = 0;
            int day_dummy = 0;
            /*int t=0;
            int n=0;*/

            FileReader fr = new FileReader(txtFileName);
            BufferedReader brtxt = new BufferedReader(fr);
            String line ="";

            String[] filename = txtFileName.split("\\.");

         	File file = new File(
         			//filename[0] + "_para.txt");
         			filename[0] + "_para.txt");
         	PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(file)));

            while ((line = brtxt.readLine()) != null) {
            	Index = line;

            	String d1 = Index.substring(0,8);
        		day = Integer.parseInt(d1);
        		String d2 = Index.substring(9);
        		time = Double.parseDouble(d2);



    			if(count_day==0){
    				day_dummy = day;
    				pw.print(day + " " + time);
    				count_day++;
    			}
    			else if(day_dummy == day){
    				pw.print(" " + time);
    			}
    			else if(day_dummy != day){
    				pw.println();
    				pw.print(day + " " +time);
    				day_dummy = day;
    			}
            }

            pw.println();

            brtxt.close();
            fr.close();
            pw.close();
    	}
    	br.close();
    }
}



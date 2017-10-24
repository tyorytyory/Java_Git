import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
//final_tradeから約定と指値を抽出するプログラム
public class Limit_extraction{

    public static void main(String[] args) throws IOException{

    	String Index;
        String Kolmo[][] = new String [30][300];
        int Kolmo_count[] = new int [30];
        int number = 1;
        String Kolmo_natural = "0.0";
        String[] filename = new String [20];
        int nodata_number = 0;


        BufferedReader br = new BufferedReader(new FileReader("filelist.txt"));//読み取りたいファイル名の記入
        String txtFileName;

        while((txtFileName = br.readLine()) != null) {




        FileReader fr = new FileReader(txtFileName);
        BufferedReader brtxt = new BufferedReader(fr);
        String line ="";

        filename = txtFileName.split("_");

        File file = new File(filename[0] 
        		+ "_limit_" +
        		filename[2] + "_" + filename[2] + "_" + filename[3] + ".csv");
        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(file)));

        while ((line = brtxt.readLine()) != null) {
        	String JNIc_split[] = line.split(",", 0);//final_trade
        	
        	if(0 < Integer.parseInt(JNIc_split[2])){
        		pw.println(line);
        	}
        	
        	
        }
        brtxt.close();
        fr.close();
        pw.close();
        }
        br.close();
    }
}

        

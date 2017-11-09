import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

public class Data_1500{
//日付毎にファイルを出力するプラグラム
    public static void main(String[] args) throws IOException{



    	String data_day_before = "";
    	int data_number = 0;//data_insertの配列にナンバリングする変数
    	String data_insert[] = new String [1000000];//書き込みをする際に使用する変数


        BufferedReader br = new BufferedReader(new FileReader("filelist.txt"));//読み取りたいファイル名の記入
        String txtFileName;

        while((txtFileName = br.readLine()) != null) {






        	FileReader fr = new FileReader(txtFileName);
            BufferedReader brtxt = new BufferedReader(fr);
            String line ="";


            String[] filename = txtFileName.split("\\.");

            File file_time = new File(filename[0] + "_1500.csv");
         	PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(file_time)));



            while ((line = brtxt.readLine()) != null) {

            	String[] JNIc_split = line.split(",", 0);

            	double hour = Double.parseDouble(JNIc_split[1].substring(0, 2));
            	double minute = Double.parseDouble(JNIc_split[1].substring(3, 5));
            	double second = Double.parseDouble(JNIc_split[1].substring(6));
            	//System.out.println(hour + "," + minute + "," + second);
            	double time_total = hour*3600 + minute*60 + second;
            	
            	if((JNIc_split[0].equals("20070104") || JNIc_split[0].equals("20071228")) && time_total <= 39600){
            		System.out.println(JNIc_split[0] + ";:"+ time_total);
            		pw.println(line);
            	}
            	else if((!(JNIc_split[0].equals("20070104")) && !(JNIc_split[0].equals("20071228"))) && time_total <= 54000){
        			pw.println(line);
        		}
            }


            pw.close();

            brtxt.close();
            fr.close();

        }

    }
}
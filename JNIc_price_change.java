import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

public class JNIc_price_change{
//日付毎にファイルを出力するプラグラム
    public static void main(String[] args) throws IOException{



    	


        BufferedReader br = new BufferedReader(new FileReader("filelist.txt"));//読み取りたいファイル名の記入
        String txtFileName;

        while((txtFileName = br.readLine()) != null) {






        	FileReader fr = new FileReader(txtFileName);
            BufferedReader brtxt = new BufferedReader(fr);
            String line ="";

            int max_price = 0;//売板の最高値
            int min_price = 1000000;//売板の最安値
            String day = null;//日にち
            boolean morning_or_afternoon = false;//前場(false) or 後場(true) 
            
            String[] filename = txtFileName.split("\\.");
            String JNIc_split [] = new String[200];
            File file_time = new File(filename[0] + "_price_change.txt");
         	PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(file_time)));



            while ((line = brtxt.readLine()) != null) {

            	JNIc_split = line.split(",", 0);

            	double hour = Double.parseDouble(JNIc_split[1].substring(0, 2));
            	double minute = Double.parseDouble(JNIc_split[1].substring(3, 5));
            	double second = Double.parseDouble(JNIc_split[1].substring(6));
            	//System.out.println(hour + "," + minute + "," + second);
            	double time_total = hour*3600 + minute*60 + second;
            	
            	
            	if(day == null){
            		day = JNIc_split[0];
            	}
            	else if(!(day.equals(JNIc_split[0])) || (45000 <= time_total && Integer.parseInt(day) < 20110214 && morning_or_afternoon == false)){
            		if(day.equals("20070104") || day.equals("20071228")){//半日オークションの場合
            			pw.println(day + ",morning," + max_price + "," + min_price);
            			pw.println(day + ",afternoon,NaN,NaN,");
            			System.out.println(day);
            			//morning_or_afternoon = true;
            		}
            		else if(morning_or_afternoon == false && Integer.parseInt(day) < 20110214 && day.equals(JNIc_split[0])){//前場の書き込み
            			pw.println(day + ",morning," + max_price + "," + min_price);
            			morning_or_afternoon = true;
            		}
            		else if(morning_or_afternoon == true && Integer.parseInt(day) < 20110214){//後場の書き込み
            			pw.println(day + ",afternoon," + max_price + "," + min_price);
            			morning_or_afternoon = false;

            		}
            		else if(20110214 <= Integer.parseInt(day)){//昼休みが廃止されたとき
            			
            		}
            		day = JNIc_split[0];
            		max_price = 0;
            		min_price = 1000000;
            	}
            	
            	if(Integer.parseInt(JNIc_split[5]) < min_price){
            		min_price = Integer.parseInt(JNIc_split[5]);
            	}
            	
            	if(max_price < Integer.parseInt(JNIc_split[5])){
            		max_price = Integer.parseInt(JNIc_split[5]);
            	}
            	//System.out.println(JNIc_split[5]);
            	
            	
            }
            
            if(day.equals("20070104") || day.equals("20071228")){//半日オークションの場合
    			pw.println(day + ",morning," + max_price + "," + min_price);
    			pw.println(day + ",afternoon,NaN,NaN,");
    			System.out.println(day);
    			//morning_or_afternoon = true;
    		}
    		else if(morning_or_afternoon == false && Integer.parseInt(day) < 20110214 && day.equals(JNIc_split[0])){//前場の書き込み
    			pw.println(day + ",morning," + max_price + "," + min_price);
    			morning_or_afternoon = true;
    		}
    		else if(morning_or_afternoon == true && Integer.parseInt(day) < 20110214){//後場の書き込み
    			pw.println(day + ",afternoon," + max_price + "," + min_price);
    			morning_or_afternoon = false;

    		}
    		else if(20110214 <= Integer.parseInt(day)){//昼休みが廃止されたとき
    			
    		}


            pw.close();

            brtxt.close();
            fr.close();

        }

    }
}
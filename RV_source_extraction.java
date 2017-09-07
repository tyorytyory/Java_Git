import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
//2006_market_order2_donation.csvからRV算出で必要な約定値を抽出するプログラム

public class RV_source_extraction{

    public static void main(String[] args) throws IOException{




        BufferedReader br = new BufferedReader(new FileReader("filelist_rv.txt"));//読み取りたいファイル名の記入
        String txtFileName;

        while((txtFileName = br.readLine()) != null) {

        	FileReader fr = new FileReader(txtFileName);
            BufferedReader brtxt = new BufferedReader(fr);
            String line ="";

            double time_for = 32400;//for文の初期値
            double time_intervals = 300;//計算したいRVの時間間隔
            int day = 0;
            String[] filename = txtFileName.split("\\_");

         	File file_rv = new File(filename[0].substring(0,4) + "_RV_5min_source.csv");
          	PrintWriter pw_rv = new PrintWriter(new BufferedWriter(new FileWriter(file_rv)));





            while ((line = brtxt.readLine()) != null) {


            	String JNIc_split[] = line.split(",", 0);


            	double hour = Double.parseDouble(JNIc_split[1].substring(0, 2));
            	double minute = Double.parseDouble(JNIc_split[1].substring(3, 5));
            	double second = Double.parseDouble(JNIc_split[1].substring(6));
            	double time_total = hour*3600 + minute*60 + second;

            	if(day == 0){
            		day = Integer.parseInt(JNIc_split[0]);
            	}
            	else if(day != Integer.parseInt(JNIc_split[0])){
            		if(Integer.parseInt(JNIc_split[0]) < 20160719){
            			time_for = 32400;
            		}
            		else{
            			time_for = 31500;
            		}
            		day = Integer.parseInt(JNIc_split[0]);
            	}

            	if(time_intervals <= (time_total - time_for) && time_total < 54900){//区間内にデータがないとき
            		do{
                		int hour_null = (int)(time_for)/3600;
                		int minute_null = ((int)(time_for)%3600)/60;
                		String hour_null_output = String.valueOf(hour_null);
                		String minute_null_output = String.valueOf(minute_null);
                		if(hour_null_output.length() == 1){
                			hour_null_output = 0 + hour_null_output;
                		}
                		if(minute_null_output.length() == 1){
                			minute_null_output = 0 + minute_null_output;
                		}
                		pw_rv.println(day  + "," + hour_null_output + ":" + minute_null_output + ":00.000000," + "0,0,0");
                		time_for += time_intervals;
            		}while(time_intervals <= (time_total - time_for));
            		//System.out.println(line);
            	}

            	if(time_for <= time_total && !(JNIc_split[4].equals("donation")) && !(JNIc_split[4].equals("error1")) && !(JNIc_split[4].equals("error2"))
            			&& (((day != 20060104 && day != 20061229 && day != 20070104 && day != 20071228 && day != 20080104 && day != 20081230 && day != 20090105) && time_total < 54600)
            			|| ((day == 20060104 || day == 20061229 || day == 20070104 || day == 20071228 || day == 20080104 || day == 20081230 || day == 20090105) && time_total < 40200))){

            		if((day == 20060104 || day == 20061229 || day == 20070104 || day == 20071228 || day == 20080104 || day == 20081230 || day == 20090105) && time_total > 40200){
            			System.out.println(line);
            		}

            		pw_rv.println(JNIc_split[0] + "," + JNIc_split[1] + "," + JNIc_split[2] + "," + JNIc_split[3] + "," + JNIc_split[4]);

            		if(!(day == 20060104 || day == 20061229 || day == 20070104 || day == 20071228 || day == 20080104 || day == 20081230 || day == 20090105) && Integer.parseInt(JNIc_split[0]) < 20110214 && time_for == 39300){
            			time_for += 5700;//昼休み

            		}
            		else{
            			time_for += time_intervals;
            		}
            	}

            }


            brtxt.close();
            fr.close();
            pw_rv.close();



        }


        br.close();
    }
}


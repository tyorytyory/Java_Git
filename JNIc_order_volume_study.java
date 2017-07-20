import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class JNIc_order_volume_study{
//Li Mengの論文に基づき、λとμを求めるプログラム

    public static void main(String[] args) throws IOException{

        BufferedReader br = new BufferedReader(new FileReader("filelist.txt"));//読み取りたいファイル名の記入
        String txtFileName;


        while((txtFileName = br.readLine()) != null) {



        	FileReader fr = new FileReader(txtFileName);
            BufferedReader brtxt = new BufferedReader(fr);
            String line ="";
            double bid_time_minute = 0;
            String bid_time = "";
            int bid_volume_sum = 0;
            String bid_day = "";

            double ask_time_minute = 0;
            String ask_time = "";
            int ask_volume_sum = 0;
            String ask_day = "";



            String[] filename = txtFileName.split("\\.");
            File file1 = new File(filename[0] + "_bid_volume_check.csv");
         	PrintWriter pw_bid = new PrintWriter(new BufferedWriter(new FileWriter(file1)));

            File file2 = new File(filename[0] + "_ask_volume_check.csv");
         	PrintWriter pw_ask = new PrintWriter(new BufferedWriter(new FileWriter(file2)));


            while ((line = brtxt.readLine()) != null) {

            	String JNIc_split[] = line.split(",", 0);

            	double hour = Double.parseDouble(JNIc_split[1].substring(0, 2));
            	double minute = Double.parseDouble(JNIc_split[1].substring(3, 5));
            	double second = Double.parseDouble(JNIc_split[1].substring(6));
            	double time_total = hour*3600 + minute*60 + second;

            	if(JNIc_split[4].equals("bid")){
            		if(bid_day == null){
                		bid_day = JNIc_split[0];
                	}
                	else if(!(bid_day.equals(JNIc_split[0]))){
                		bid_day = JNIc_split[0];
                		bid_time_minute = 0;
                		bid_volume_sum = 0;
                	}


            		if(bid_time_minute == 0){
                		bid_time_minute = time_total;
                		bid_time = JNIc_split[1];
                	}
                	else if(time_total - bid_time_minute <= 60){
                		bid_volume_sum += Integer.parseInt(JNIc_split[2]);
                	}
                	else if(time_total - bid_time_minute > 60){
                		pw_bid.println(bid_day + "," + bid_time + "," + bid_volume_sum + ",bid,,,,,");
                		bid_volume_sum = 0;
                		bid_volume_sum += Integer.parseInt(JNIc_split[2]);
                		bid_time_minute = time_total;
                		bid_time = JNIc_split[1];
                	}
            	}
            	else if(JNIc_split[4].equals("ask")){
            		if(ask_day == null){
                		ask_day = JNIc_split[0];
                	}
                	else if(!(ask_day.equals(JNIc_split[0]))){
                		ask_day = JNIc_split[0];
                		ask_time_minute = 0;
                		ask_volume_sum = 0;
                	}


            		if(ask_time_minute == 0){
                		ask_time_minute = time_total;
                		ask_time = JNIc_split[1];
                	}
                	else if(time_total - ask_time_minute <= 60){
                		ask_volume_sum += Integer.parseInt(JNIc_split[2]);
                	}
                	else if(time_total - ask_time_minute > 60){
                		pw_ask.println(ask_day + "," + ask_time + "," + ask_volume_sum + ",ask,,,,,");
                		ask_volume_sum = 0;
                		ask_volume_sum += Integer.parseInt(JNIc_split[2]);
                		ask_time_minute = time_total;
                		ask_time = JNIc_split[1];
                	}
            	}



            }


    		bid_time_minute = 0;
    		bid_volume_sum = 0;
    		ask_time_minute = 0;
    		ask_volume_sum = 0;
    		bid_day = "";
    		ask_day = "";


            brtxt.close();
            fr.close();
            pw_bid.close();
            pw_ask.close();




        }

        br.close();
    }
}


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

        BufferedReader br = new BufferedReader(new FileReader("filelist1.txt"));//読み取りたいファイル名の記入
        String txtFileName;


        while((txtFileName = br.readLine()) != null) {



        	FileReader fr = new FileReader(txtFileName);
            BufferedReader brtxt = new BufferedReader(fr);
            String line ="";
            double bid_time_minute = 0;
            double bid_time_minute_sum = 0;
            String bid_time = "";
            double bid_volume_sum = 0;
            String bid_day = "";

            double ask_time_minute = 0;
            double ask_time_minute_sum = 0;



            String ask_time = "";
            double ask_volume_sum = 0;
            String ask_day = "";

            double bid_volume_total = 0;
            double ask_volume_total = 0;
            int bid_trade_number = 0;
            int ask_trade_number = 0;

            String day = "";
            double time_first = 0;
            double time_last = 0;
            double time_before = 0;
            double time_sum = 0;



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

            	if(day == null){
            		day = JNIc_split[0];
            		time_first = time_total;
            	}
            	else if(!(day.equals(JNIc_split[0]))){

            		day = JNIc_split[0];
            		//System.out.println(time_before - time_first);
            		if(Integer.parseInt(JNIc_split[0]) < 20110214){
            			time_sum += time_before - time_first - 5400;
            		}
            		else{
            			time_sum += time_before - time_first;
            		}

            		time_first = time_total;
            	}

            	time_before = time_total;

            	//System.out.println(line);

            	if(JNIc_split[4].equals("bid")){
            		if(bid_day == null){
                		bid_day = JNIc_split[0];
                	}
                	else if(!(bid_day.equals(JNIc_split[0]))){
                		bid_time_minute_sum += bid_time_minute;
                		bid_day = JNIc_split[0];

                	}

            		bid_volume_sum += Integer.parseInt(JNIc_split[2]);
            		bid_trade_number++;

            		if(bid_time_minute == 0){
            			bid_time_minute = time_total;
            		}
            		else{
            			bid_time_minute = time_total;
            		}

            		/*if(bid_time_minute == 0){
                		bid_time_minute = time_total;
                		bid_time = JNIc_split[1];
                		bid_volume_sum += Integer.parseInt(JNIc_split[2]);
                	}
                	else if(time_total - bid_time_minute <= 60){
                		bid_volume_sum += Integer.parseInt(JNIc_split[2]);
                	}
                	else if(time_total - bid_time_minute > 60){
                		pw_bid.println(bid_day + "," + bid_time + "," + bid_volume_sum + ",bid,,,,,");
                		bid_volume_total += bid_volume_sum/30;
                		bid_trade_number++;
                		bid_volume_sum = 0;
                		bid_volume_sum += Integer.parseInt(JNIc_split[2]);
                		bid_time_minute = time_total;
                		bid_time = JNIc_split[1];
                	}*/
            	}
            	else if(JNIc_split[4].equals("ask")){
            		if(ask_day == null){
                		ask_day = JNIc_split[0];
                	}
                	else if(!(ask_day.equals(JNIc_split[0]))){
                		ask_time_minute_sum += ask_time_minute;
                		ask_day = JNIc_split[0];
                	}

            		ask_volume_sum += Integer.parseInt(JNIc_split[2]);
            		ask_trade_number++;

            		if(ask_time_minute == 0){
            			ask_time_minute = time_total;
            		}
            		else{
            			ask_time_minute = time_total;
            		}




            		/*if(ask_time_minute == 0){
                		ask_time_minute = time_total;
                		ask_time = JNIc_split[1];
                		ask_volume_sum += Integer.parseInt(JNIc_split[2]);
                		//System.out.println(line);
                	}
                	else if(time_total - ask_time_minute <= 60){
                		ask_volume_sum += Integer.parseInt(JNIc_split[2]);
                	}
                	else if(time_total - ask_time_minute > 60){
                		pw_ask.println(ask_day + "," + ask_time + "," + ask_volume_sum + ",ask,,,,,");
                		ask_volume_total += ask_volume_sum/30;
                		ask_trade_number++;
                		ask_volume_sum = 0;
                		ask_volume_sum += Integer.parseInt(JNIc_split[2]);
                		ask_time_minute = time_total;
                		ask_time = JNIc_split[1];
                	}*/
            	}





            }





            System.out.println(filename[0]);
            System.out.println(bid_volume_sum/bid_trade_number + "," + ask_volume_sum/ask_trade_number);
            System.out.println(bid_volume_sum + "," + ask_volume_sum);
            //System.out.println("買注文到着率(1)," + bid_volume_sum/(time_sum) + ",売注文到着率(1)," + ask_volume_sum/(time_sum));




            /*System.out.println(filename[0]);
            System.out.println("1分ごとにまとめた買注文量の合計," + bid_volume_total + ",1分ごとにまとめた買注文の回数の合計," + bid_trade_number);
            System.out.println("1分ごとにまとめた売注文量の合計," + ask_volume_total + ",1分ごとにまとめた売注文の回数の合計," + ask_trade_number);*/


            bid_volume_total = 0;
            ask_volume_total = 0;
            bid_trade_number = 0;
            ask_trade_number = 0;
            time_sum = 0;

            bid_time_minute_sum = 0;
            ask_time_minute_sum = 0;

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


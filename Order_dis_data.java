import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Order_dis_data{
//20XX_limit_order.csvから出来高・注文回数などを算出するプログラム
    public static void main(String[] args) throws IOException{


        BufferedReader br = new BufferedReader(new FileReader("filelist.txt"));//読み取りたいファイル名の記入
        String txtFileName;


        int bid_number = 0;
        int ask_number = 0;
        int bid_volume = 0;
        int ask_volume = 0;
        String year = "";







        while((txtFileName = br.readLine()) != null) {






        	FileReader fr = new FileReader(txtFileName);
            BufferedReader brtxt = new BufferedReader(fr);
            String line ="";
            int insert_bid[] = new int [10000];
            int bid_count = 0;
            int insert_ask[] = new int [10000];
            int ask_count = 0;

            int day = 0;

            String[] filename = txtFileName.split("\\.");


            File file_bid = new File("bid_" + filename[0] + ".csv");
            PrintWriter pw_bid = new PrintWriter(new BufferedWriter(new FileWriter(file_bid)));

            File file_ask = new File("ask_" + filename[0] + ".csv");
            PrintWriter pw_ask = new PrintWriter(new BufferedWriter(new FileWriter(file_ask)));


            while ((line = brtxt.readLine()) != null) {

            	String JNc_split[] = line.split(",", 0);


            	//System.out.println(line);

            	double hour = Double.parseDouble(JNc_split[1].substring(0, 2));
            	double minute = Double.parseDouble(JNc_split[1].substring(3, 5));
            	double second = Double.parseDouble(JNc_split[1].substring(6));
            	double time_total = hour*3600 + minute*60 + second;




            	if(day == 0){
            		day = Integer.parseInt(JNc_split[0]);


            		if(JNc_split[4].equals("bid")
            				//&& JNc_split.length == 5//成り行き注文

            				){

            			if(0 <= Integer.parseInt(JNc_split[2])){
            				insert_bid[bid_count++] = Integer.parseInt(JNc_split[2]);
            			}
            			else if(Integer.parseInt(JNc_split[2]) <= 0){
            				insert_bid[bid_count++] = -1*Integer.parseInt(JNc_split[2]);
            			}

            		}
            		else if(JNc_split[4].equals("ask")
            				&& JNc_split.length == 5//成り行き注文

            				){
            			if(0 <= Integer.parseInt(JNc_split[2])){
            				insert_ask[ask_count++] = Integer.parseInt(JNc_split[2]);
            			}
            			else if(Integer.parseInt(JNc_split[2]) <= 0){
            				insert_ask[ask_count++] = -1*Integer.parseInt(JNc_split[2]);
            			}

            		}
            	}
            	else if(day != Integer.parseInt(JNc_split[0])){

            		pw_bid.print(day + ",");
            		pw_ask.print(day + ",");

            		for(int i = 0;i < bid_count;i++){
            			pw_bid.print(insert_bid[i] + ",");
            			insert_bid[i] = 0;
            			if(i == bid_count - 1){
            				//System.out.println(line + "+++");
            				pw_bid.println();
            			}
            		}

            		bid_count = 0;

            		for(int i = 0;i < ask_count;i++){
            			pw_ask.print(insert_ask[i] + ",");
            			insert_ask[i] = 0;
            			if(i == ask_count - 1){
            				pw_ask.println();
            			}
            		}

            		ask_count = 0;



            		day = Integer.parseInt(JNc_split[0]);

            		if(JNc_split[4].equals("bid")
            				//&& JNc_split.length == 5//成り行き注文

            				){

            			if(0 <= Integer.parseInt(JNc_split[2])){
            				insert_bid[bid_count++] = Integer.parseInt(JNc_split[2]);
            			}
            			else if(Integer.parseInt(JNc_split[2]) <= 0){
            				insert_bid[bid_count++] = -1*Integer.parseInt(JNc_split[2]);
            			}

            		}
            		else if(JNc_split[4].equals("ask")
            				//&& JNc_split.length == 5//成り行き注文

            				){
            			if(0 <= Integer.parseInt(JNc_split[2])){
            				insert_ask[ask_count++] = Integer.parseInt(JNc_split[2]);
            			}
            			else if(Integer.parseInt(JNc_split[2]) <= 0){
            				insert_ask[ask_count++] = -1*Integer.parseInt(JNc_split[2]);
            			}

            		}
            	}
            	else if(day == Integer.parseInt(JNc_split[0])){
            		if(JNc_split[4].equals("bid")
            				//&& JNc_split.length == 5//成り行き注文

            				){
            			if(0 <= Integer.parseInt(JNc_split[2])){
            				insert_bid[bid_count++] = Integer.parseInt(JNc_split[2]);
            			}
            			else if(Integer.parseInt(JNc_split[2]) <= 0){
            				insert_bid[bid_count++] = -1*Integer.parseInt(JNc_split[2]);
            			}
            		}
            		else if(JNc_split[4].equals("ask")
            				//&& JNc_split.length == 5//成り行き注文

            				){
            			if(0 <= Integer.parseInt(JNc_split[2])){
            				insert_ask[ask_count++] = Integer.parseInt(JNc_split[2]);
            			}
            			else if(Integer.parseInt(JNc_split[2]) <= 0){
            				insert_ask[ask_count++] = -1*Integer.parseInt(JNc_split[2]);
            			}

            		}
            	}


            }



            pw_bid.close();
            pw_ask.close();
    		brtxt.close();
            fr.close();



        }



		bid_number = 0;
		ask_number = 0;
		bid_volume = 0;
		ask_volume = 0;




    }
}


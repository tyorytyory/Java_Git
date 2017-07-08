import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class limit_order_volume_etc{
//20XX_limit_order.csvから出来高・注文回数などを算出するプログラム
    public static void main(String[] args) throws IOException{


        BufferedReader br = new BufferedReader(new FileReader("filelist.txt"));//読み取りたいファイル名の記入
        String txtFileName;


        int bid_number = 0;
        int ask_number = 0;
        int bid_volume = 0;
        int ask_volume = 0;
        String year = "";

        File file = new File("200602-201602_limit_info_data.csv");
        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(file)));

        pw.println("year,the number of bid order,the number of ask order,the mount of bid order,the mount of ask order,");



        while((txtFileName = br.readLine()) != null) {






        	FileReader fr = new FileReader(txtFileName);
            BufferedReader brtxt = new BufferedReader(fr);
            String line ="";





            while ((line = brtxt.readLine()) != null) {

            	String limit_split[] = line.split(",", 0);

            	if(year.equals("")
            			&& Integer.parseInt(limit_split[0]) >= 20060227//年数指定
            			){
            		//System.out.println("123");
            		year = limit_split[0].substring(0,4);
            	}
            	else if(!(year.equals(limit_split[0].substring(0,4)))){
            		pw.println(year + "," + bid_number + "," + ask_number + "," + bid_volume + "," + ask_volume + ",");
            		year = limit_split[0].substring(0, 4);
            		bid_number = 0;
            		ask_number = 0;
            		bid_volume = 0;
            		ask_volume = 0;
            	}

            	Double hour = Double.parseDouble(limit_split[1].substring(0,2));
            	Double time = Double.parseDouble(limit_split[1].substring(3,5));
            	Double second = Double.parseDouble(limit_split[1].substring(6,15));
            	Double time_sum = hour*3600 + time*60 + second;
            	//System.out.println(hour + "," + time + "," + second + "," + time_sum);


            	if(Integer.parseInt(limit_split[0]) >= 20060227 && Integer.parseInt(limit_split[0]) <= 20110210 & time_sum <= 54000){
            		if(time_sum <= 39600 || time_sum >= 45000){
            			if(limit_split[4].equals("bid")){
                    		bid_number++;
                    		bid_volume += Integer.parseInt(limit_split[2]);
                    	}
                    	else if(limit_split[4].equals("ask")){
                    		ask_number++;
                    		ask_volume += Integer.parseInt(limit_split[2]);
                    	}
            		}
            	}
            	else if(Integer.parseInt(limit_split[0]) >= 20060227 && Integer.parseInt(limit_split[0]) < 20160300 && time_sum <= 54000){
            		if(limit_split[4].equals("bid")){
                		bid_number++;
                		bid_volume += Integer.parseInt(limit_split[2]);
                	}
                	else if(limit_split[4].equals("ask")){
                		ask_number++;
                		ask_volume += Integer.parseInt(limit_split[2]);
                	}
            	}

            }



    		brtxt.close();
            fr.close();



        }


        pw.println(year + "," + bid_number + "," + ask_number + "," + bid_volume + "," + ask_volume + ",");
		bid_number = 0;
		ask_number = 0;
		bid_volume = 0;
		ask_volume = 0;

		pw.close();


    }
}


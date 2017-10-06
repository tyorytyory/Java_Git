import java.io.BufferedReader;
import java.math.BigDecimal;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.Arrays;
//RV_source_extraction.javaから抽出したファイルから、仲値のデータを「2006_limit.txt」から抽出するプログラム
//2006_RV_5min_source.csv→2006_limit.txtの順でファイルを読み込ませる。
public class RV_source_extraction_nakane{

    public static void main(String[] args) throws IOException{

    	double rv_data[][][] = new double [300][6000][4];




        BufferedReader br = new BufferedReader(new FileReader("fililist_rv_nakane.txt"));//読み取りたいファイル名の記入
        String txtFileName;

        while((txtFileName = br.readLine()) != null) {

        	FileReader fr = new FileReader(txtFileName);
            BufferedReader brtxt = new BufferedReader(fr);
            String line ="";

           
            int day = 0;
            String[] filename = txtFileName.split("\\_");

            int rv_day = 0;
            int rv_number = 0;

         	File file_rv = new File(filename[0].substring(0,4) + "_RV_5min_source_nakane3.csv");
          	PrintWriter pw_rv = new PrintWriter(new BufferedWriter(new FileWriter(file_rv)));





            while ((line = brtxt.readLine()) != null) {







            	if(filename[1].equals("RV")){

            		String JNIc_split[] = line.split(",", 0);



            		double hour = Double.parseDouble(JNIc_split[1].substring(0, 2));
                	double minute = Double.parseDouble(JNIc_split[1].substring(3, 5));
                	double second = Double.parseDouble(JNIc_split[1].substring(6));
                	double time_total = hour*3600 + minute*60 + second;


            		if(day == 0){
            			//Arrays.fill(rv_data, 0);
                		day = Integer.parseInt(JNIc_split[0]);
                	}
                	else if(day != Integer.parseInt(JNIc_split[0])){
                		day = Integer.parseInt(JNIc_split[0]);
                		rv_day++;
                		rv_number = 0;
                	}

            		rv_data[rv_day][rv_number][0] = day;//日にち
            		rv_data[rv_day][rv_number][1] = time_total;
            		if(JNIc_split[2].equals("NaN")){
            			rv_data[rv_day][rv_number][2] = 0;
            			rv_data[rv_day][rv_number][3] = 0;
            		}
            		else{
            			rv_data[rv_day][rv_number][2] = Integer.parseInt(JNIc_split[3]);
            			if(JNIc_split[4].equals("donation") || JNIc_split[5].equals("final trade")){//寄り付きと最終約定をそのまま約定値を使用するとき。
            				System.out.println(line + "****" +rv_data[rv_day][rv_number][2]);
            				rv_data[rv_day][rv_number][3] = 500;
            			}
            			
            		}

            		


            		//System.out.println(rv_data[rv_day][rv_number][0]);
            		//System.out.println(rv_data[rv_day][rv_number][1]);
            		rv_number++;
            	}
            	else if(filename[1].equals("limit.txt")){
            		String JNIc_split[] = line.split(",", 0);


            		//System.out.println(line);

            		double hour = Double.parseDouble(JNIc_split[3].substring(0, 2));
                	double minute = Double.parseDouble(JNIc_split[3].substring(3, 5));
                	double second = Double.parseDouble(JNIc_split[3].substring(6));
                	double time_total = hour*3600 + minute*60 + second;

                	if(day == 0){
                		day = Integer.parseInt(JNIc_split[2]);

                	}
                	else if(day != Integer.parseInt(JNIc_split[2])){
                		day = Integer.parseInt(JNIc_split[2]);
                		rv_day++;
                		rv_number = 0;
                	}
                	//System.out.println(rv_data[rv_day][rv_number][0]);

                	if(rv_data[rv_day][rv_number][0] == day){

                		if(rv_data[rv_day][rv_number][2] == 0){
                			while(rv_data[rv_day][rv_number][2] == 0 && rv_data[rv_day][rv_number][0] == day){
                				int hour_null = (int)(rv_data[rv_day][rv_number][1])/3600;
                        		int minute_null = ((int)(rv_data[rv_day][rv_number][1])%3600)/60;
                        		String hour_null_output = String.valueOf(hour_null);
                        		String minute_null_output = String.valueOf(minute_null);
                        		if(hour_null_output.length() == 1){
                        			hour_null_output = 0 + hour_null_output;
                        		}
                        		if(minute_null_output.length() == 1){
                        			minute_null_output = 0 + minute_null_output;
                        		}
                        		pw_rv.println(BigDecimal.valueOf(rv_data[rv_day][rv_number][0]).toPlainString()  + "," + hour_null_output + ":" + minute_null_output + ":00.000000,NaN,NaN,NaN,NaN");
                        		//System.out.println(BigDecimal.valueOf(rv_data[rv_day][rv_number][0]).toPlainString());
                    			rv_number++;
                			}
                		}
                		/*else if(rv_data[rv_day][rv_number][3] == 500){//寄り付きと最終約定をそのまま約定値を使用するとき。
                			int hour_null = (int)(rv_data[rv_day][rv_number][1])/3600;
                    		int minute_null = ((int)(rv_data[rv_day][rv_number][1])%3600)/60;
                    		double second_null = (double)(rv_data[rv_day][rv_number][1])%60;
                    		
                    		String hour_null_output = String.valueOf(hour_null);
                    		String minute_null_output = String.valueOf(minute_null);
                    		String second_null_output = String.valueOf(second_null);
                    		BigDecimal bd = new BigDecimal(second_null_output);
                    		BigDecimal second_null_output2 = bd.setScale(6, BigDecimal.ROUND_HALF_UP);
                    		second_null_output = String.valueOf(second_null_output2);
                    		if(hour_null_output.length() == 1){
                    			hour_null_output = 0 + hour_null_output;
                    		}
                    		if(minute_null_output.length() == 1){
                    			minute_null_output = 0 + minute_null_output;
                    		}
                    		if(second_null_output.length() == 8){
                    			second_null_output = 0 + second_null_output;
                    		}
                			pw_rv.println(JNIc_split[2] + "," + hour_null_output + ":" + minute_null_output +":" + second_null_output + "," + rv_data[rv_day][rv_number][2] + ",," + rv_data[rv_day][rv_number][2] + ",," );
                			rv_data[rv_day][rv_number][1] = 0;
                			rv_number++;
                		}*/
                		else if(rv_data[rv_day][rv_number][1] <= time_total && JNIc_split[4].equals("Quote")){
                			pw_rv.println(JNIc_split[2] + "," + JNIc_split[3] + "," + JNIc_split[8] + "," + JNIc_split[9] + "," + JNIc_split[10] + "," + JNIc_split[11]);
                			rv_data[rv_day][rv_number][1] = 0;
                			rv_number++;
                		}

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


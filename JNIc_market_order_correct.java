import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

public class JNIc_market_order_correct{
//market_orderから寄付きの情報を抜くっプログラム

    public static void main(String[] args) throws IOException{

        BufferedReader br = new BufferedReader(new FileReader("filelist1.txt"));//読み取りたいファイル名の記入
        String txtFileName;


        int delete_comb_row = 1;

        int input_file = 0;

        int day_comb = 0;
        int delete_comb[][] = new int[20161200][3];

        boolean comb_donation = false;
        boolean comb_final_trade = false;

        String file_input[] = new String[1000000];
        int file_input_number = 0;
        String JNIc_split[] = new String[50];

        int day_market = 0;


        while((txtFileName = br.readLine()) != null) {



        	FileReader fr = new FileReader(txtFileName);
            BufferedReader brtxt = new BufferedReader(fr);
            String line ="";





            String[] filename = txtFileName.split("\\_");





            File file = new File(filename[0] + "_" + filename[1]  + "_" + filename[2].substring(0, 5) + "_donation.csv");
            PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(file)));




            while ((line = brtxt.readLine()) != null) {


            	//-----------------------(初期J-GATE以外ここから)--------------------------------------

            	/*JNIc_split = line.split(",", 0);



            	if(day_market == 0){
            		day_market = Integer.parseInt(JNIc_split[0]);
            	}
            	else if(day_market != Integer.parseInt(JNIc_split[0])){

            		for(int i = 0;i < file_input_number;i++){
            			if(i == file_input_number - 1){
            				String file_write_final_trade_split[] = file_input[i].split(",", 0);
            				pw.println(file_write_final_trade_split[0] + "," + file_write_final_trade_split[1] + "," + file_write_final_trade_split[2] + "," + file_write_final_trade_split[3] +
            						"," + file_write_final_trade_split[4] + ",final trade,,,");
            			}
            			else{
            				pw.println(file_input[i]);
            			}
            		}
            		Arrays.fill(file_input, null);//初期化
            		file_input_number = 0;
            		day_market = Integer.parseInt(JNIc_split[0]);

            	}

            	if(day_market < 20110214 || 20160715 < day_market){
            		if(file_input_number == 0){
            			file_input[file_input_number] = JNIc_split[0] + "," + JNIc_split[1] + "," + JNIc_split[2] + "," + JNIc_split[3] + ",donation,,,,";
            			file_input_number++;
            		}
            		else{
            			file_input[file_input_number] = line;
            			file_input_number++;
            		}

            	}*/

            	//-----------------------(初期J-GATE以外ここまで)--------------------------------------



            	//-----------------------(初期J-GATEここから)--------------------------------------

            	if(filename[0].substring(0, 3).equals("JNI")){


            		if(!(line.substring(0,1).equals("-")) && day_comb == 0){
            			day_comb = Integer.parseInt(line.substring(4, 12));
            			delete_comb[day_comb][1]++;
            			delete_comb[day_comb][2]++;
            		}
            		else if( !(line.substring(0,1).equals("-")) && day_comb != Integer.parseInt(line.substring(4, 12))){
            			day_comb = Integer.parseInt(line.substring(4, 12));
            			delete_comb_row++;
            			delete_comb[day_comb][1]++;
            			delete_comb[day_comb][2]++;
            			comb_donation = false;
            			comb_final_trade = false;
            		}
            		else if(line.substring(0,1).equals("-") && comb_donation == false){
            			delete_comb[day_comb][1]++;
            		}
            		else{
            			comb_donation = true;
            		}

            		if((line.substring(52,55).equals("128") || line.substring(52,55).equals("160")) && comb_final_trade == false){
            			comb_final_trade = true;
            		}
            		else if(comb_final_trade == true && line.substring(0,1).equals("-")){
            			delete_comb[day_comb][2]++;
            		}

            	}


            	if(filename[1].equals("market")){


                	JNIc_split = line.split(",", 0);

                	if(day_market == 0){
                		day_market = Integer.parseInt(JNIc_split[0]);
                	}
                	else if(day_market != Integer.parseInt(JNIc_split[0])){
                		for(int i = 0;i < file_input_number;i++){
                			if(file_input_number - delete_comb[day_market][2] <= i){
                				String file_write_final_trade_split[] = file_input[i].split(",", 0);
                				pw.println(file_write_final_trade_split[0] + "," + file_write_final_trade_split[1] + "," + file_write_final_trade_split[2] + "," + file_write_final_trade_split[3] +
                						"," + file_write_final_trade_split[4] + ",final trade,,,");
                			}
                			else{
                				pw.println(file_input[i]);
                			}
                		}
                		Arrays.fill(file_input, null);//初期化
                		file_input_number = 0;
                		day_market = Integer.parseInt(JNIc_split[0]);
                	}




            		if(20110214 <= day_market && day_market <= 20160715){

                		if(delete_comb[day_market][1] > 0){
                			file_input[file_input_number] = JNIc_split[0] + "," + JNIc_split[1] + "," + JNIc_split[2] + "," + JNIc_split[3] + ",donation,,,,";
                			file_input_number++;
                			delete_comb[day_market][1]--;

                		}
                		else if(delete_comb[day_market][1] == 0){
                			file_input[file_input_number] = line;
                			file_input_number++;
                		}
                		else{
                			System.out.println("1231534");
                		}

            		}

            	}

            	//-----------------------(初期J-GATEここまで)--------------------------------------


            }

          //-----------------------(初期J-GATEここから)--------------------------------------

           if(filename[1].equals("market")){

        		   for(int i = 0;i < file_input_number;i++){
              			if(file_input_number - delete_comb[day_market][2] <= i){
              				String file_write_final_trade_split[] = file_input[i].split(",", 0);
              				pw.println(file_write_final_trade_split[0] + "," + file_write_final_trade_split[1] + "," + file_write_final_trade_split[2] + "," + file_write_final_trade_split[3] +
              						"," + file_write_final_trade_split[4] + ",final trade,,,");
              			}
              			else{
              				pw.println(file_input[i]);
              			}
              		}

          		Arrays.fill(file_input, null);//初期化
          		file_input_number = 0;
          		day_market = 0;


           }

         //-----------------------(初期J-GATEここまで)--------------------------------------

          //-----------------------(初期J-GATE以外ここから)--------------------------------------

            /*for(int i = 0;i < file_input_number;i++){
    			if(i == file_input_number - 1){
    				String file_write_final_trade_split[] = file_input[i].split(",", 0);
    				pw.println(file_write_final_trade_split[0] + "," + file_write_final_trade_split[1] + "," + file_write_final_trade_split[2] + "," + file_write_final_trade_split[3] +
    						"," + file_write_final_trade_split[4] + ",final trade,,,");
    			}
    			else{
    				pw.println(file_input[i]);
    			}
    		}
    		Arrays.fill(file_input, null);//初期化
    		file_input_number = 0;
    		day_market = 0;*/

    		//-----------------------(初期J-GATE以外ここまで)--------------------------------------


            brtxt.close();
            fr.close();
            pw.close();



        }

        br.close();
    }
}


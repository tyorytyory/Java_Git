import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class JNIc_order_volume_study{
//Li Mengの論文に基づき、指値注文と成行注文からλとμを求めるプログラム

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

            String bid_day = null;
            double ask_time_minute = 0;
            double ask_time_minute_sum = 0;

            String JNIc_split[] = null;



            String ask_time = null;

            String ask_day = null;

            double bid_volume_total = 0;
            double ask_volume_total = 0;



            String day = null;
            double time_first = 0;
            double time_last = 0;
            double time_before = 0;
            double time_sum = 0;


            double bid_volume_sum_morning = 0;
            double bid_volume_sum_afternoon = 0;
            double bid_volume_sum = 0;
            double bid_cancel_sum = 0;//買指値注文のキャンセルの総量（2011/2/14以降）
            double bid_cancel_sum_morning = 0;//買指値注文のキャンセルの総量（前場）
            double bid_cancel_sum_afternoon = 0;//買指値注文のキャンセルの総量（後場）
            //int bid_trade_number = 0;
            int bid_trade_number_morning_subtotal = 0;//買い注文の総回数（前場）
            int bid_trade_number_afternoon_subtotal = 0;//買い注文の総回数（後場）
            int bid_trade_number_subtotal = 0;//買い注文の総回数（2011/2/14以降）
            int bid_cancel_number = 0;//買い指値注文のキャンセル回数（2011/2/14以降）
            int bid_cancel_number_morning_subtotal = 0;//買い指値注文のキャンセル回数（前場）
            int bid_cancel_number_afternoon_subtotal = 0;//買い指値注文のキャンセル回数（後場）
            double bid_arrival_freq_morning = 0;//買い注文の総量（成行注文だったら出来高）（前場）
            double bid_arrival_freq_afternoon = 0;//買い注文の総量（成行注文だったら出来高）（後場）
            double bid_arrival_freq = 0;//買い注文の総量（成行注文だったら出来高）（2011/2/14以降）




            /*double ask_volume_sum_morning = 0;
            double ask_volume_sum_afternoon = 0;
            double ask_volume_sum = 0;*/
            double ask_cancel_sum = 0;//売指値注文のキャンセルの総量（2011/2/14以降）
            double ask_cancel_sum_morning = 0;//売指値注文のキャンセルの総量（前場）
            double ask_cancel_sum_afternoon = 0;//売指値注文のキャンセルの総量（後場）
            //int ask_trade_number = 0;
            int ask_trade_number_morning_subtotal = 0;//売り注文の総回数（前場）
            int ask_trade_number_afternoon_subtotal = 0;//売り注文の総回数（後場）
            int ask_trade_number_subtotal = 0;//売り注文の総回数（2011/2/14以降）
            int ask_cancel_number = 0;//売り指値注文のキャンセル回数（2011/2/14以降）
            int ask_cancel_number_morning_subtotal = 0;//売り指値注文のキャンセル回数（前場）
            int ask_cancel_number_afternoon_subtotal = 0;//売り指値注文のキャンセル回数（後場）
            double ask_arrival_freq_morning = 0;//売り注文の総量（成行注文だったら出来高）（前場）
            double ask_arrival_freq_afternoon = 0;//売り注文の総量（成行注文だったら出来高）（後場）
            double ask_arrival_freq = 0;//売り注文の総量（成行注文だったら出来高）（2011/2/14以降）





            String[] filename = txtFileName.split("\\.");

            File file_freq = new File(filename[0] + "_arrival_freq.csv");
         	PrintWriter pw_freq = new PrintWriter(new BufferedWriter(new FileWriter(file_freq)));

         	pw_freq.println("day,devide,bid volume,bid number,ask volume,ask number,bid cancel sum,bid cancel number,ask cancel sum,ask cancel number,,,");//ラベルの書き込み




            while ((line = brtxt.readLine()) != null) {

            	JNIc_split = line.split(",", 0);

            	double hour = Double.parseDouble(JNIc_split[1].substring(0, 2));//時間
            	double minute = Double.parseDouble(JNIc_split[1].substring(3, 5));//分
            	double second = Double.parseDouble(JNIc_split[1].substring(6));//秒
            	double time_total = hour*3600 + minute*60 + second;//時間を秒換算

            	if(day == null){//日付がnullのとき
            		day = JNIc_split[0];
            		time_first = time_total;
            	}
            	else if(!(day.equals(JNIc_split[0]))){
            		if(Integer.parseInt(day) < 20110214){//昼休み廃止以前
            			if(bid_arrival_freq_morning == 0){//前場にデータを含まないとき
            				pw_freq.println(day + ",morning,NaN,NaN,NaN,NaN,NaN,NaN,NaN,NaN,,,,");
            			}
            			else if(bid_arrival_freq_morning != 0){//前場にデータを含むとき
            				pw_freq.println(day + ",morning," + bid_arrival_freq_morning + "," + bid_trade_number_morning_subtotal + "," + ask_arrival_freq_morning + "," + ask_trade_number_morning_subtotal + ","
            						+ bid_cancel_sum_morning + "," + bid_cancel_number_morning_subtotal + "," + ask_cancel_sum_morning + "," + ask_cancel_number_morning_subtotal + ",,,,," );
            			}
            			else{
            				System.out.println(line);
            			}

            			if(bid_arrival_freq_afternoon == 0){//後場にデータを含まないとき
            				pw_freq.println(day + ",afternoon,NaN,NaN,NaN,NaN,NaN,NaN,NaN,NaN,,,");
            			}
            			else if(bid_arrival_freq_afternoon != 0){//後場にデータを含むとき
            				pw_freq.println(day + ",afternoon," + bid_arrival_freq_afternoon + "," + bid_trade_number_afternoon_subtotal + "," + ask_arrival_freq_afternoon + "," + ask_trade_number_afternoon_subtotal + ","
            						+ bid_cancel_sum_afternoon + "," + bid_cancel_number_afternoon_subtotal + "," + ask_cancel_sum_afternoon + "," + ask_cancel_number_afternoon_subtotal + ",,,,," );
            			}
            			else{
            				System.out.println(line);
            			}
            		}
            		else if(Integer.parseInt(day) >= 20110214){//昼休み廃止後
            			pw_freq.println(day + ",no noon recess," + bid_arrival_freq + "," + bid_trade_number_subtotal + "," + ask_arrival_freq  + "," + ask_trade_number_subtotal + ","
            					+ bid_cancel_sum + "," + bid_cancel_number + "," + ask_cancel_sum + "," + ask_cancel_number + ",,,,,,");
            		}




            		//----------------------------初期化-----------------------------------
            		bid_arrival_freq_morning = 0;
        			bid_arrival_freq_afternoon = 0;
        			bid_arrival_freq = 0;
        			bid_trade_number_morning_subtotal = 0;
        			bid_trade_number_afternoon_subtotal = 0;
        			bid_trade_number_subtotal = 0;
        			bid_cancel_sum_morning = 0;
        			bid_cancel_number_morning_subtotal = 0;
        			bid_cancel_sum_afternoon = 0;
        			bid_cancel_number_afternoon_subtotal = 0;
        			bid_cancel_sum = 0;
        			bid_cancel_number = 0;

        			ask_arrival_freq_morning = 0;
        			ask_arrival_freq_afternoon = 0;
        			ask_arrival_freq = 0;
        			ask_trade_number_morning_subtotal = 0;
        			ask_trade_number_afternoon_subtotal = 0;
        			ask_trade_number_subtotal = 0;
        			ask_cancel_sum_morning = 0;
        			ask_cancel_number_morning_subtotal = 0;
        			ask_cancel_sum_afternoon = 0;
        			ask_cancel_number_afternoon_subtotal = 0;
        			ask_cancel_sum = 0;
        			ask_cancel_number = 0;
        			//----------------------------初期化-----------------------------------

            		day = JNIc_split[0];


            	}





            	if(JNIc_split[4].equals("bid")){





            		if(bid_time_minute == 0){
            			bid_time_minute = time_total;
            		}
            		else{
            			bid_time_minute = time_total;
            		}




            		if(Integer.parseInt(day) < 20110214 && time_total <= 39600 && Integer.parseInt(JNIc_split[2]) > 0){//昼休みがあるときの前場。買い注文
            			bid_arrival_freq_morning += Integer.parseInt(JNIc_split[2]);
            			//bid_volume_sum_morning += Integer.parseInt(JNIc_split[2]);
            			bid_trade_number_morning_subtotal++;
            		}
            		else if(Integer.parseInt(day) < 20110214 && time_total >= 45000 && time_total <= 54600 && Integer.parseInt(JNIc_split[2]) > 0){//昼休みがあるときの後場。買い注文
            			bid_arrival_freq_afternoon += Integer.parseInt(JNIc_split[2]);
            			//bid_volume_sum_afternoon += Integer.parseInt(JNIc_split[2]);
            			bid_trade_number_afternoon_subtotal++;
            		}
            		else if(Integer.parseInt(day) < 20110214 && time_total <= 39600 && Integer.parseInt(JNIc_split[2]) < 0){//昼休みがあるときの前場。買い指値注文のキャンセル
            			bid_cancel_sum_morning += (-1*Integer.parseInt(JNIc_split[2]));
            			bid_cancel_number_morning_subtotal++;
            		}
            		else if(Integer.parseInt(day) < 20110214 && time_total >= 45000 && time_total <= 54600 && Integer.parseInt(JNIc_split[2]) < 0){//昼休みがあるときの後場。買い指値注文のキャンセル
            			bid_cancel_sum_afternoon += (-1*Integer.parseInt(JNIc_split[2]));
            			bid_cancel_number_afternoon_subtotal++;
            		}
            		else if(Integer.parseInt(day) >= 20110214 && time_total <= 54600 && Integer.parseInt(JNIc_split[2]) > 0){//昼休みが廃止後。買い注文
            			bid_arrival_freq += Integer.parseInt(JNIc_split[2]);
            			//bid_volume_sum += Integer.parseInt(JNIc_split[2]);
            			bid_trade_number_subtotal++;
            		}
            		else if(Integer.parseInt(day) >= 20110214 && time_total <= 54600 && Integer.parseInt(JNIc_split[2]) < 0){//昼休みが廃止後。買い指値注文のキャンセル
            			bid_cancel_sum += (-1*Integer.parseInt(JNIc_split[2]));
            			bid_cancel_number++;
            		}
            		else if(Integer.parseInt(day) < 20110214 && time_total > 39600 && time_total < 45000 && Integer.parseInt(JNIc_split[2]) > 0){
            			//System.out.println(line);
            		}


            	}
            	else if(JNIc_split[4].equals("ask")){



            		if(ask_time_minute == 0){
            			ask_time_minute = time_total;
            		}
            		else{
            			ask_time_minute = time_total;
            		}


            		if(Integer.parseInt(day) < 20110214 && time_total <= 39600 && Integer.parseInt(JNIc_split[2]) > 0){//昼休みがあるときの前場。売り注文
            			ask_arrival_freq_morning += Integer.parseInt(JNIc_split[2]);
            			//ask_volume_sum_morning += Integer.parseInt(JNIc_split[2]);
            			ask_trade_number_morning_subtotal++;
            		}
            		else if(Integer.parseInt(day) < 20110214 && time_total >= 45000 && time_total <= 54600 && Integer.parseInt(JNIc_split[2]) > 0){//昼休みがあるときの後場。売り注文
            			ask_arrival_freq_afternoon += Integer.parseInt(JNIc_split[2]);
            			//ask_volume_sum_afternoon += Integer.parseInt(JNIc_split[2]);
            			ask_trade_number_afternoon_subtotal++;
            		}
            		else if(Integer.parseInt(day) < 20110214 && time_total <= 39600 && Integer.parseInt(JNIc_split[2]) < 0){//昼休みがあるときの前場。売り指値注文のキャンセル
            			ask_cancel_sum_morning += (-1*Integer.parseInt(JNIc_split[2]));
            			ask_cancel_number_morning_subtotal++;
            		}
            		else if(Integer.parseInt(day) < 20110214 && time_total >= 45000 && time_total <= 54600 && Integer.parseInt(JNIc_split[2]) < 0){//昼休みがあるときの後場。売り指値注文のキャンセル
            			ask_cancel_sum_afternoon += (-1*Integer.parseInt(JNIc_split[2]));
            			ask_cancel_number_afternoon_subtotal++;
            		}
            		else if(Integer.parseInt(day) >= 20110214 && Integer.parseInt(JNIc_split[2]) > 0){//昼休みがあるときの後場。売り注文
            			ask_arrival_freq += Integer.parseInt(JNIc_split[2]);
            			//ask_volume_sum += Integer.parseInt(JNIc_split[2]);
            			ask_trade_number_subtotal++;
            		}
            		else if(Integer.parseInt(day) >= 20110214 && time_total <= 54600 && Integer.parseInt(JNIc_split[2]) < 0){//昼休みが廃止後。売り指値注文のキャンセル
            			ask_cancel_sum += (-1*Integer.parseInt(JNIc_split[2]));
            			ask_cancel_number++;
            		}
            		else if(Integer.parseInt(day) < 20110214 && time_total > 39600 && time_total < 45000 && Integer.parseInt(JNIc_split[2]) > 0){
            			//System.out.println(line);
            		}

            	}

            }



            //------------------------------------------最後の書き込み------------------------------------
            if(Integer.parseInt(day) < 20110214){//昼休み廃止以前
    			if(bid_arrival_freq_morning == 0){//前場にデータを含まないとき
    				pw_freq.println(day + ",morning,NaN,NaN,NaN,NaN,NaN,NaN,NaN,NaN,,,,");
    			}
    			else if(bid_arrival_freq_morning != 0){//前場にデータを含むとき
    				pw_freq.println(day + ",morning," + bid_arrival_freq_morning + "," + bid_trade_number_morning_subtotal + "," + ask_arrival_freq_morning + "," + ask_trade_number_morning_subtotal + ","
    						+ bid_cancel_sum_morning + "," + bid_cancel_number_morning_subtotal + "," + ask_cancel_sum_morning + "," + ask_cancel_number_morning_subtotal + ",,,,," );
    			}
    			else{
    				System.out.println(line);
    			}

    			if(bid_arrival_freq_afternoon == 0){//後場にデータを含まないとき
    				pw_freq.println(day + ",afternoon,NaN,NaN,NaN,NaN,NaN,NaN,NaN,NaN,,,");
    			}
    			else if(bid_arrival_freq_afternoon != 0){//後場にデータを含むとき
    				pw_freq.println(day + ",afternoon," + bid_arrival_freq_afternoon + "," + bid_trade_number_afternoon_subtotal + "," + ask_arrival_freq_afternoon + "," + ask_trade_number_afternoon_subtotal + ","
    						+ bid_cancel_sum_afternoon + "," + bid_cancel_number_afternoon_subtotal + "," + ask_cancel_sum_afternoon + "," + ask_cancel_number_afternoon_subtotal + ",,,,," );
    			}
    			else{
    				System.out.println(line);
    			}
    		}
    		else if(Integer.parseInt(day) >= 20110214){//昼休み廃止時
    			pw_freq.println(day + ",no noon recess," + bid_arrival_freq + "," + bid_trade_number_subtotal + "," + ask_arrival_freq  + "," + ask_trade_number_subtotal + ","
    					+ bid_cancel_sum + "," + bid_cancel_number + "," + ask_cancel_sum + "," + ask_cancel_number + ",,,,,,");
    		}
          //------------------------------------------最後の書き込み------------------------------------




            //----------------------------初期化-----------------------------------
    		bid_arrival_freq_morning = 0;
			bid_arrival_freq_afternoon = 0;
			bid_arrival_freq = 0;
			bid_trade_number_morning_subtotal = 0;
			bid_trade_number_afternoon_subtotal = 0;
			bid_trade_number_subtotal = 0;
			bid_cancel_sum_morning = 0;
			bid_cancel_number_morning_subtotal = 0;
			bid_cancel_sum_afternoon = 0;
			bid_cancel_number_afternoon_subtotal = 0;
			bid_cancel_sum = 0;
			bid_cancel_number = 0;

			ask_arrival_freq_morning = 0;
			ask_arrival_freq_afternoon = 0;
			ask_arrival_freq = 0;
			ask_trade_number_morning_subtotal = 0;
			ask_trade_number_afternoon_subtotal = 0;
			ask_trade_number_subtotal = 0;
			ask_cancel_sum_morning = 0;
			ask_cancel_number_morning_subtotal = 0;
			ask_cancel_sum_afternoon = 0;
			ask_cancel_number_afternoon_subtotal = 0;
			ask_cancel_sum = 0;
			ask_cancel_number = 0;



			//----------------------------初期化-----------------------------------







            brtxt.close();
            fr.close();
            pw_freq.close();






        }

        br.close();
    }
}


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
//JNc_2006_gcheck_data_from0227_oyaorder_micro_time18.txtとかから、取引回数や出来高を算出するプログラム

public class JNc_number_ita_move{

    public static void main(String[] args) throws IOException {

    	String Index = null;

    	BufferedReader br = new BufferedReader(new FileReader("filelist.txt"));//読み取りたいファイル名の記入
        String txtFileName;

        while((txtFileName = br.readLine()) != null) {
        	//String Index[] = new String[400000];




        	int day = 0;//日付
        	int day_array [] = new int [3000];
        	int count_number [][][] = new int[3000][400][6];

        	int count_bid_number_trade[] = new int[400];//買　取引回数
        	int count_ask_number_trade[] = new int[400];//売　取引回数
        	int count_bid_volume[] = new int[400];//買　出来高
        	int count_ask_volume[] = new int[400];//売　出来高
        	int count_error_number_trade[] = new int[400];//その他の取引回数
        	int count_error_volume[] = new int[400];//その他の出来高
        	int time_count = 0;//配列の２番目に用いる変数*/
        	int day_count = 0;
        	int if_time[] = new int[400];//サーキットブレイカーのときに必要になる文章。
        	int time_dif = 900;//time_difを変えることにより、時間間隔を変える。
        	int pw_enter_point = 0;//書き込みの際に改行するもの



            FileReader fr = new FileReader(txtFileName);
            BufferedReader brtxt = new BufferedReader(fr);
            String line ="";

            String[] filename = txtFileName.split("\\.");

            File file = new File(filename[0] + "_down_15min.csv");//時間差に0を含むときは0を記入
         	PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(
         			//"F:\\個別株\\TICST120\\201602\\" +
         	file)));

         	for(int i = 0;i<=((54600-31500)/time_dif)-1;i++){
         		if_time[i] = 31500 + i*time_dif;
         		System.out.println(i + "++++" + if_time[i]);
         		pw_enter_point = i;
        	}


            while ((line = brtxt.readLine()) != null) {

            	String JNc_split[] = line.split(",", 0);
            	//System.out.println(line);

            	double hour = Double.parseDouble(JNc_split[1].substring(0, 2));
            	double minute = Double.parseDouble(JNc_split[1].substring(3, 5));
            	double second = Double.parseDouble(JNc_split[1].substring(6));
            	double time_total = hour*3600 + minute*60 + second;
            	time_count = 0;

            	for(double i = 31500;i<=54000;i+=time_dif){
            		if(i <= time_total && time_total < (i + time_dif)){
            			//System.out.println(i);
            			break;
            		}
            		else{
            			time_count++;
            		}
            	}

            	if(day == 0){
            		day = Integer.parseInt(JNc_split[0]);
            		day_array[day_count] = day;

            		if(//JNc_split[2].equals("up") || JNc_split[2].equals("up not Trade")//板の上昇
            				JNc_split[2].equals("down") || JNc_split[2].equals("down not Trade")//板の下降
            				){
            			count_bid_number_trade[time_count]++;
            			count_bid_volume[time_count] += Integer.parseInt(JNc_split[4]);

            			count_number[day_count][time_count][0]++;//買い注文回数
            			count_number[day_count][time_count][1] += Integer.parseInt(JNc_split[4]);;//買い注文量

            			count_ask_number_trade[time_count]++;
            			count_ask_volume[time_count] += Integer.parseInt(JNc_split[6]);

            			//count_number[day_count][time_count][2]++;//売り注文回数
            			count_number[day_count][time_count][2] += Integer.parseInt(JNc_split[6]);//売り注文量


            		}
            		else if(//JNc_split[2].equals("up") || JNc_split[2].equals("up not Trade")//板の上昇
            					JNc_split[2].equals("down") || JNc_split[2].equals("down not Trade")//板の下降
            				){

            		}
            		/*else if(JNc_split[4].equals("error1") || JNc_split[4].equals("error2")){//成行注文のとき
            			count_error_number_trade[time_count]++;
                    	count_error_volume[time_count] += Integer.parseInt(JNc_split[2]);

                    	count_number[day_count][time_count][4]++;//error注文回数
            			count_number[day_count][time_count][5] += Integer.parseInt(JNc_split[2]);;//error注文量
            		}*/

            	}
            	else if(day != Integer.parseInt(JNc_split[0])){

            		day_count++;
            		day = Integer.parseInt(JNc_split[0]);
            		day_array[day_count] = day;

            		if(//JNc_split[2].equals("up") || JNc_split[2].equals("up not Trade")//板の上昇
            				JNc_split[2].equals("down") || JNc_split[2].equals("down not Trade")//板の下降
            				){
            			count_bid_number_trade[time_count]++;
            			count_bid_volume[time_count] += Integer.parseInt(JNc_split[4]);

            			count_number[day_count][time_count][0]++;//買い注文回数
            			count_number[day_count][time_count][1] += Integer.parseInt(JNc_split[4]);;//買い注文量

            			count_ask_number_trade[time_count]++;
            			count_ask_volume[time_count] += Integer.parseInt(JNc_split[6]);

            			//count_number[day_count][time_count][2]++;//売り注文回数
            			count_number[day_count][time_count][2] += Integer.parseInt(JNc_split[6]);//売り注文量


            		}
            		else if(//JNc_split[2].equals("up") || JNc_split[2].equals("up not Trade")//板の上昇
            				JNc_split[2].equals("down") || JNc_split[2].equals("down not Trade")//板の下降
            				){


            		}
            		/*else if(JNc_split[4].equals("error1") || JNc_split[4].equals("error2")){//成行注文のとき
            			count_error_number_trade[time_count]++;
                    	count_error_volume[time_count] += Integer.parseInt(JNc_split[2]);

                    	count_number[day_count][time_count][4]++;//error注文回数
            			count_number[day_count][time_count][5] += Integer.parseInt(JNc_split[2]);;//error注文量
            		}*/

            	}
            	else if(day == Integer.parseInt(JNc_split[0])){
            		if(//JNc_split[2].equals("up") || JNc_split[2].equals("up not Trade")//板の上昇
            				JNc_split[2].equals("down") || JNc_split[2].equals("down not Trade")//板の下降
            				){
            			count_bid_number_trade[time_count]++;
            			count_bid_volume[time_count] += Integer.parseInt(JNc_split[4]);

            			count_number[day_count][time_count][0]++;//買い注文回数
            			count_number[day_count][time_count][1] += Integer.parseInt(JNc_split[4]);;//買い注文量

            			count_ask_number_trade[time_count]++;
            			count_ask_volume[time_count] += Integer.parseInt(JNc_split[6]);

            			//count_number[day_count][time_count][2]++;//売り注文回数
            			count_number[day_count][time_count][2] += Integer.parseInt(JNc_split[6]);//売り注文量


            		}
            		else if(//JNc_split[2].equals("up") || JNc_split[2].equals("up not Trade")//板の上昇
            				JNc_split[2].equals("down") || JNc_split[2].equals("down not Trade")//板の下降
            				){


            		}
            		/*else if(JNc_split[4].equals("error1") || JNc_split[4].equals("error2")){//成行注文のとき
            			count_error_number_trade[time_count]++;
                    	count_error_volume[time_count] += Integer.parseInt(JNc_split[2]);

                    	count_number[day_count][time_count][4]++;//error注文回数
            			count_number[day_count][time_count][5] += Integer.parseInt(JNc_split[2]);;//error注文量
            		}*/
            	}

            }

            for(int h=0;h<=2;h++){//買い注文と売り注文で区別している。
            	if(h == 0){
            		pw.println("move number,8:45:00,9:00:00,9:15:00,9:30:00,9:45:00,10:00:00,10:15:00,10:30:00,10:45:00,11:00:00,11:15:00,11:30:00,11:45:00,12:00:00,12:15:00,12:30:00,12:45:00,13:00:00,13:15:00,13:30:00,13:45:00,14:00:00,14:15:00,14:30:00,14:45:00,15:00:00");
            	}
            	else if(h == 1){
            		pw.println("bid depth sum,8:45:00,9:00:00,9:15:00,9:30:00,9:45:00,10:00:00,10:15:00,10:30:00,10:45:00,11:00:00,11:15:00,11:30:00,11:45:00,12:00:00,12:15:00,12:30:00,12:45:00,13:00:00,13:15:00,13:30:00,13:45:00,14:00:00,14:15:00,14:30:00,14:45:00,15:00:00");
            	}
            	//else if(h == 2){
            		//pw.println("ask move number,8:45:00,9:00:00,9:15:00,9:30:00,9:45:00,10:00:00,10:15:00,10:30:00,10:45:00,11:00:00,11:15:00,11:30:00,11:45:00,12:00:00,12:15:00,12:30:00,12:45:00,13:00:00,13:15:00,13:30:00,13:45:00,14:00:00,14:15:00,14:30:00,14:45:00,15:00:00");
            	//}
            	else if(h == 2){
            		pw.println("ask depth sum,8:45:00,9:00:00,9:15:00,9:30:00,9:45:00,10:00:00,10:15:00,10:30:00,10:45:00,11:00:00,11:15:00,11:30:00,11:45:00,12:00:00,12:15:00,12:30:00,12:45:00,13:00:00,13:15:00,13:30:00,13:45:00,14:00:00,14:15:00,14:30:00,14:45:00,15:00:00");
            	}
            	for(int j=0;j<=260;j++){
            		if(day_array[j] != 0){
            			pw.print(day_array[j] + ",");
            		}
            		else if(day_array[j] == 0){
            			pw.print("NaN,");
            		}

            		for(int i=0;i<=25;i++){
            			if(9 <= i && i <= 14 && day_array[j] < 20110214 && day_array[j] != 20060104 && day_array[j] != 20061229 && day_array[j] != 20070104 && day_array[j] != 20071228 && day_array[j] != 20080104 && day_array[j] != 20081230 && day_array[j] != 20090105){
            				pw.print("NaN,");
            			}
            			else if(day_array[j] == 0){
            				pw.print("NaN,");
            			}
            			else if((day_array[j] == 20061227) ||//ロイター社のデータエラー
            					(day_array[j] == 20081222) ||//ロイター社のデータエラー
            					(day_array[j] == 20081010) ||//サーキットブレイカー
            					(day_array[j] == 20081014) ||//サーキットブレイカー
            					(day_array[j] == 20081016) ||//サーキットブレイカー
            					(day_array[j] == 20110314) ||//サーキットブレイカー
            					(day_array[j] == 20110315) ||//サーキットブレイカー
            					(day_array[j] == 20130304) ||//サーキットブレイカー
            					(day_array[j] == 20130523) ||//サーキットブレイカー
            					(day_array[j] == 20140304) ||//システムエラー
            					(day_array[j] == 20160714)//ロイター社のデータエラー
            					){
            				//System.out.println(day_array[j]);
            				pw.print("NaN,");
                		}
            			//else if((day_array[j] == 20061227 && 15 <= i) ||//ロイター社のデータエラー
            					//(day_array[j] == 20081222 && 0 <= i && i <= 8) ||//ロイター社のデータエラー
            					//(day_array[j] == 20081010 && 1 <= i && i <= 2) ||//サーキットブレイカー
            					//(day_array[j] == 20081014 && 1 <= i && i <= 2) ||//サーキットブレイカー
            					//(day_array[j] == 20081016 && 1 <= i && i <= 2) ||//サーキットブレイカー
            					//(day_array[j] == 20110314 && 1 <= i && i <= 2) ||//サーキットブレイカー
            					//(day_array[j] == 20110315 && 9 <= i && i <= 11) ||//サーキットブレイカー
            					//(day_array[j] == 20110315 && 13 <= i && i <= 13) ||//サーキットブレイカー
            					//(day_array[j] == 20110315 && 16 <= i && i <= 16) ||//サーキットブレイカー
            					//(day_array[j] == 20130304 && 9 <= i && i <= 21) ||//サーキットブレイカー
            					//(day_array[j] == 20130523 && 22 <= i && i <= 23) ||//サーキットブレイカー
            					//(day_array[j] == 20140304 && 9 <= i && i <= 10) ||//システムエラー
            					//(day_array[j] == 20160714)//ロイター社のデータエラー
            					//){
            				//System.out.println(day_array[j]);
            				//pw.print("NaN,");
                		//}
            			else{
            				if(i == 0 && day_array[j] < 20160719){
                				pw.print("NaN,");
                			}
                			else if(10 <= i && (day_array[j] == 20060104 || day_array[j] == 20061229 || day_array[j] == 20070104 || day_array[j] == 20071228 || day_array[j] == 20080104 || day_array[j] == 20081230 || day_array[j] == 20090105)){
            					pw.print("NaN,");
            				}
            				else{

            					/*if(h%2 == 1){//指値キャンセルのとき
            						pw.print(-1*count_number[j][i][h] + ",");
            					}
            					else{//指値キャンセルの以外のとき*/
            						pw.print(count_number[j][i][h] + ",");
            					//}
            				}
            			}
            			if(i == 25){
            				pw.print("\n");
            			}
            		}
            	}
            	pw.print("\n\n\n\n\n");
            }



            /*for(int h=0;h<=2;h += 2){//成り行きと指値の時でかえる．注文一回あたりの平均注文量のプログラム
            	if(h == 0){
            		pw.println("bid average volume per 1 trade,8:45:00,9:00:00,9:15:00,9:30:00,9:45:00,10:00:00,10:15:00,10:30:00,10:45:00,11:00:00,11:15:00,11:30:00,11:45:00,12:00:00,12:15:00,12:30:00,12:45:00,13:00:00,13:15:00,13:30:00,13:45:00,14:00:00,14:15:00,14:30:00,14:45:00,15:00:00");
            	}
            	else if(h == 2){
            		pw.println("ask average volume per 1 trade,8:45:00,9:00:00,9:15:00,9:30:00,9:45:00,10:00:00,10:15:00,10:30:00,10:45:00,11:00:00,11:15:00,11:30:00,11:45:00,12:00:00,12:15:00,12:30:00,12:45:00,13:00:00,13:15:00,13:30:00,13:45:00,14:00:00,14:15:00,14:30:00,14:45:00,15:00:00");
            	}
            	for(int j=0;j<=260;j++){
            		if(day_array[j] != 0){
            			pw.print(day_array[j] + ",");
            		}
            		else if(day_array[j] == 0){
            			pw.print("NaN,");
            		}
            		for(int i=0;i<=25;i++){
            			if(9 <= i && i <= 14 && day_array[j] < 20110214 && day_array[j] != 20060104 && day_array[j] != 20061229 && day_array[j] != 20070104 && day_array[j] != 20071228 && day_array[j] != 20080104 && day_array[j] != 20081230 && day_array[j] != 20090105){
            				pw.print("NaN,");
            			}
            			else if(day_array[j] == 0){
            				pw.print("NaN,");
            			}
            			else if((day_array[j] == 20061227) ||//ロイター社のデータエラー
            					(day_array[j] == 20081222) ||//ロイター社のデータエラー
            					(day_array[j] == 20081010) ||//サーキットブレイカー
            					(day_array[j] == 20081014) ||//サーキットブレイカー
            					(day_array[j] == 20081016) ||//サーキットブレイカー
            					(day_array[j] == 20110314) ||//サーキットブレイカー
            					(day_array[j] == 20110315) ||//サーキットブレイカー
            					(day_array[j] == 20130304) ||//サーキットブレイカー
            					(day_array[j] == 20130523) ||//サーキットブレイカー
            					(day_array[j] == 20140304) ||//システムエラー
            					(day_array[j] == 20160714)//ロイター社のデータエラー
            					){
            				System.out.println(day_array[j]);
            				pw.print("NaN,");
                		}
            			else{
            				if(i == 0 && day_array[j] < 20160719){
                				pw.print("NaN,");
                			}
                			else if(10 <= i && (day_array[j] == 20060104 || day_array[j] == 20061229 || day_array[j] == 20070104 || day_array[j] == 20071228 || day_array[j] == 20080104 || day_array[j] == 20081230 || day_array[j] == 20090105)){
            					pw.print("NaN,");
            				}
            				else{
            					if(count_number[j][i][h+1] == 0 || count_number[j][i][h] == 0){
            						pw.print("0,");
            					}
            					else{
            						//pw.print((double)((count_number[j][i][h+1]))/count_number[j][i][h] + ",");//指値キャンセル以外
                					pw.print((double)((-1*count_number[j][i][h+1]))/count_number[j][i][h] + ",");//指値キャンセル
            					}

            				}
            			}
            			if(i == 25){
            				pw.print("\n");
            			}
            		}
            	}
            	pw.print("\n\n\n\n\n");
            }*/


            /*for(int h=0;h<=1;h++){//買い注文と売り注文で区別しない。
            	if(h == 0){
            		pw.print("number,");
            		for(double i = 31500;i<54600;i+=time_dif){
            			int hour_null = (int)(i)/3600;
                		int minute_null = ((int)(i)%3600)/60;
                		String hour_null_output = String.valueOf(hour_null);
                		String minute_null_output = String.valueOf(minute_null);
                		if(hour_null_output.length() == 1){
                			hour_null_output = 0 + hour_null_output;
                		}
                		if(minute_null_output.length() == 1){
                			minute_null_output = 0 + minute_null_output;
                		}
            			pw.print(hour_null_output + ":" + minute_null_output +":00,");
                	}
            		pw.print("\n");
            	}
            	else if(h == 1){
            		pw.print("volume,");
            		for(double i = 31500;i<54600;i+=time_dif){
            			int hour_null = (int)(i)/3600;
                		int minute_null = ((int)(i)%3600)/60;
                		String hour_null_output = String.valueOf(hour_null);
                		String minute_null_output = String.valueOf(minute_null);
                		if(hour_null_output.length() == 1){
                			hour_null_output = 0 + hour_null_output;
                		}
                		if(minute_null_output.length() == 1){
                			minute_null_output = 0 + minute_null_output;
                		}
            			pw.print(hour_null_output + ":" + minute_null_output +":00,");
                	}
            		pw.print("\n");
            	}
            	for(int j=0;j<=2460;j++){
            		if(day_array[j] != 0){
            			pw.print(day_array[j] + ",");
            		}
            		else if(day_array[j] == 0){
            			pw.print("NaN,");
            		}

            		for(int i=0;i<=pw_enter_point;i++){//時間間隔によりiを変える。15min->25,1min->374,5min->74
            			if(39600 <= if_time[i] && if_time[i] < 45000 && day_array[j] < 20110214 && day_array[j] != 20060104 && day_array[j] != 20061229 && day_array[j] != 20070104 && day_array[j] != 20071228 && day_array[j] != 20080104 && day_array[j] != 20081230 && day_array[j] != 20090105){
            				pw.print("NaN,");
            			}
            			else if(day_array[j] == 0){
            				pw.print("NaN,");
            			}
            			/*else if((day_array[j] == 20061227) ||//ロイター社のデータエラー
            					(day_array[j] == 20081222) ||//ロイター社のデータエラー
            					(day_array[j] == 20081010) ||//サーキットブレイカー
            					(day_array[j] == 20081014) ||//サーキットブレイカー
            					(day_array[j] == 20081016) ||//サーキットブレイカー
            					(day_array[j] == 20110314) ||//サーキットブレイカー
            					(day_array[j] == 20110315) ||//サーキットブレイカー
            					(day_array[j] == 20130304) ||//サーキットブレイカー
            					(day_array[j] == 20130523) ||//サーキットブレイカー
            					(day_array[j] == 20140304) ||//システムエラー
            					(day_array[j] == 20160714)//ロイター社のデータエラー
            					){
            				//System.out.println(day_array[j]);
            				pw.print("NaN,");
                		}*/
            			/*else if((day_array[j] == 20061227 && 38700 <= if_time[i]) ||//ロイター社のデータエラー
            					(day_array[j] == 20081222 && 31500 <= if_time[i] && if_time[i] <= 39600) ||//ロイター社のデータエラー
            					(day_array[j] == 20081010 && (9*3600 + 8*60 + 0) <= if_time[i] && if_time[i] <= (9*3600 + 23*60 + 4)) ||//サーキットブレイカー
            					(day_array[j] == 20081014 && (9*3600 + 10*60 + 0) <= if_time[i] && if_time[i] <= (9*3600 + 25*60 + 31)) ||//サーキットブレイカー
            					(day_array[j] == 20081016 && (9*3600 + 9*60 + 0) <= if_time[i] && if_time[i] <= (9*3600 + 24*60 + 30)) ||//サーキットブレイカー
            					(day_array[j] == 20110314 && (9*3600 + 0*60 + 0) <= if_time[i] && if_time[i] <= (9*3600 + 1*60 + 55)) ||//サーキットブレイカー
            					(day_array[j] == 20110315 && (11*3600 + 8*60 + 0) <= if_time[i] && if_time[i] <= (11*3600 + 24*60 + 46)) ||//サーキットブレイカー
            					(day_array[j] == 20110315 && (11*3600 + 24*60 +0) <= if_time[i] && if_time[i] <= (11*3600 + 40*60 + 16)) ||//サーキットブレイカー
            					(day_array[j] == 20110315 && (12*3600 + 13*60 + 0) <= if_time[i] && if_time[i] <= (12*3600 + 14*60 + 12)) ||//サーキットブレイカー
            					(day_array[j] == 20110315 && (12*3600 + 46*60 + 0) <= if_time[i] && if_time[i] <= (12*3600 + 47*60 + 10)) ||//サーキットブレイカー
            					(day_array[j] == 20130304 && (11*3600 + 4*60 + 0) <= if_time[i] && if_time[i] <= (14*3600 + 10*60 + 5)) ||//サーキットブレイカー
            					(day_array[j] == 20130523 && (14*3600 + 28*60 + 0) <= if_time[i] && if_time[i] <= (14*3600 + 43*60 + 40)) ||//サーキットブレイカー
            					(day_array[j] == 20140304 && (11*3600 + 5*60 + 0) <= if_time[i] && if_time[i] <= (11*3600 + 30*60 + 0)) ||//システムエラー
            					(day_array[j] == 20160714)//ロイター社のデータエラー
            					){
            				System.out.println(day_array[j]);
            				pw.print("NaN,");
                		}
            			else{
            				if(if_time[i] < 32400 && day_array[j] < 20160719){
                				pw.print("NaN,");
                			}
                			else if(40200 <= if_time[i] && (day_array[j] == 20060104 || day_array[j] == 20061229 || day_array[j] == 20070104 || day_array[j] == 20071228 || day_array[j] == 20080104 || day_array[j] == 20081230 || day_array[j] == 20090105)){
            					pw.print("NaN,");
            				}
            				else{

            					/*if(h%2 == 1){//指値キャンセルのとき
            						pw.print(-1*(count_number[j][i][h]+count_number[j][i][h+2]) + ",");
            					}
            					else{//指値キャンセルの以外のとき*/
            						/*pw.print((count_number[j][i][h]+count_number[j][i][h+2]) + ",");
            					//}
            				/*}
            			}
            			if(i == pw_enter_point){//時間間隔によりiを変える。15min->25,1min->374
            				pw.print("\n");
            			}
            		}
            	}
            	pw.print("\n\n\n\n\n");
            }*/


            /*pw.print(day + ",");

    		for(int i = 0;i<=25;i++){

    			if(i == 0 && day < 20160719){
    				pw.print("NaN,NaN,NaN,NaN,NaN,NaN,");
    			}
    			else if(9 <= i && i <= 14 && day < 20110214 && day != 20060104 && day != 20061229 && day != 20070104 && day != 20071228 && day != 20080104 && day != 20081230 && day != 20090105){
    				pw.print("NaN,NaN,NaN,NaN,NaN,NaN,");
    			}
    			else{
    				pw.print(count_bid_number_trade[i] + "," + count_bid_volume[i] + "," + count_ask_number_trade[i] + "," + count_ask_volume[i] + "," + count_error_number_trade[i] + "," + count_error_volume[i] + ",");
    			}
    			if(i == 25){
    				pw.print("\n");
    			}

    			count_ask_number_trade[i] = 0;//売　取引回数
            	count_bid_volume[i] = 0;//買　出来高
            	count_ask_volume[i] = 0;//売　出来高
            	count_error_number_trade[i] = 0;
            	count_error_volume[i] = 0;
    		}*/


    		 brtxt.close();
             fr.close();
             pw.close();
        }
        br.close();
    }
}














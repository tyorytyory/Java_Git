import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
//JNc_2006_gcheck_data_from0227_oyaorder_micro_time18.txtとかから、取引回数や出来高を算出するプログラム

public class JNc_number_extraction{

    public static void main(String[] args) throws IOException {

    	String Index = null;

    	BufferedReader br = new BufferedReader(new FileReader("filelist.txt"));//読み取りたいファイル名の記入
        String txtFileName;

        while((txtFileName = br.readLine()) != null) {
        	//String Index[] = new String[400000];


        	
        	
        	int day = 0;//日付
        	int day_array [] = new int [300];
        	int count_number [][][] = new int[300][30][6];
        	
        	int count_bid_number_trade[] = new int[30];//買　取引回数
        	int count_ask_number_trade[] = new int[30];//売　取引回数
        	int count_bid_volume[] = new int[30];//買　出来高
        	int count_ask_volume[] = new int[30];//売　出来高
        	int count_error_number_trade[] = new int[30];//その他の取引回数
        	int count_error_volume[] = new int[30];//その他の出来高
        	int time_count = 0;//配列の２番目に用いる変数*/
        	int day_count = 0;
        	
        	

            FileReader fr = new FileReader(txtFileName);
            BufferedReader brtxt = new BufferedReader(fr);
            String line ="";

            String[] filename = txtFileName.split("\\.");
            
            File file = new File(filename[0] + "_number_cancel.csv");//時間差に0を含むときは0を記入
         	PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(
         			//"F:\\個別株\\TICST120\\201602\\" +
         	file)));
                                	

            while ((line = brtxt.readLine()) != null) {

            	String JNc_split[] = line.split(",", 0);
            	//System.out.println(line);          	
            	
            	double hour = Double.parseDouble(JNc_split[1].substring(0, 2));
            	double minute = Double.parseDouble(JNc_split[1].substring(3, 5));
            	double second = Double.parseDouble(JNc_split[1].substring(6));
            	double time_total = hour*3600 + minute*60 + second;
            	time_count = 0;
            	
            	for(double i = 31500;i<=54000;i+=900){
            		if(i <= time_total && time_total < (i + 900)){
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
            		
            		if(JNc_split[4].equals("bid") 
            				//&& JNc_split.length == 5//成り行き注文
            				&& 0 >= Integer.parseInt(JNc_split[2])//指値注文
            				){            			
            			count_bid_number_trade[time_count]++;
            			count_bid_volume[time_count] += Integer.parseInt(JNc_split[2]);
            			
            			count_number[day_count][time_count][0]++;//買い注文回数
            			count_number[day_count][time_count][1] += Integer.parseInt(JNc_split[2]);;//買い注文量
            			
            			
            		}
            		else if(JNc_split[4].equals("ask")
            				//&& JNc_split.length == 5//成り行き注文
            				&& 0 >= Integer.parseInt(JNc_split[2])//指値注文            				
            				){
            			count_ask_number_trade[time_count]++;
            			count_ask_volume[time_count] += Integer.parseInt(JNc_split[2]);
            			
            			count_number[day_count][time_count][2]++;//売り注文回数
            			count_number[day_count][time_count][3] += Integer.parseInt(JNc_split[2]);//売り注文量
            			
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
            		
            		if(JNc_split[4].equals("bid") 
            				//&& JNc_split.length == 5//成り行き注文
            				&& 0 >= Integer.parseInt(JNc_split[2])//指値注文
            				){            			
            			count_bid_number_trade[time_count]++;
            			count_bid_volume[time_count] += Integer.parseInt(JNc_split[2]);
            			
            			count_number[day_count][time_count][0]++;//買い注文回数
            			count_number[day_count][time_count][1] += Integer.parseInt(JNc_split[2]);;//買い注文量
            			
            			
            		}
            		else if(JNc_split[4].equals("ask")
            				//&& JNc_split.length == 5//成り行き注文
            				&& 0 >= Integer.parseInt(JNc_split[2])//指値注文            				
            				){
            			count_ask_number_trade[time_count]++;
            			count_ask_volume[time_count] += Integer.parseInt(JNc_split[2]);
            			
            			count_number[day_count][time_count][2]++;//売り注文回数
            			count_number[day_count][time_count][3] += Integer.parseInt(JNc_split[2]);//売り注文量
            			
            		}
            		/*else if(JNc_split[4].equals("error1") || JNc_split[4].equals("error2")){//成行注文のとき	
            			count_error_number_trade[time_count]++;
                    	count_error_volume[time_count] += Integer.parseInt(JNc_split[2]);
                    	
                    	count_number[day_count][time_count][4]++;//error注文回数
            			count_number[day_count][time_count][5] += Integer.parseInt(JNc_split[2]);;//error注文量                   	
            		}*/
            		
            	}
            	else if(day == Integer.parseInt(JNc_split[0])){
            		if(JNc_split[4].equals("bid") 
            				//&& JNc_split.length == 5//成り行き注文
            				&& 0 >= Integer.parseInt(JNc_split[2])//指値注文
            				){            			
            			count_bid_number_trade[time_count]++;
            			count_bid_volume[time_count] += Integer.parseInt(JNc_split[2]);
            			
            			count_number[day_count][time_count][0]++;//買い注文回数
            			count_number[day_count][time_count][1] += Integer.parseInt(JNc_split[2]);;//買い注文量
            			
            			
            		}
            		else if(JNc_split[4].equals("ask")
            				//&& JNc_split.length == 5//成り行き注文
            				&& 0 >= Integer.parseInt(JNc_split[2])//指値注文            				
            				){
            			count_ask_number_trade[time_count]++;
            			count_ask_volume[time_count] += Integer.parseInt(JNc_split[2]);
            			
            			count_number[day_count][time_count][2]++;//売り注文回数
            			count_number[day_count][time_count][3] += Integer.parseInt(JNc_split[2]);//売り注文量
            			
            		}
            		/*else if(JNc_split[4].equals("error1") || JNc_split[4].equals("error2")){//成行注文のとき	
            			count_error_number_trade[time_count]++;
                    	count_error_volume[time_count] += Integer.parseInt(JNc_split[2]);
                    	
                    	count_number[day_count][time_count][4]++;//error注文回数
            			count_number[day_count][time_count][5] += Integer.parseInt(JNc_split[2]);;//error注文量                   	
            		}*/
            	}
            	            	            	
            }
            
            for(int h=0;h<=3;h++){//成り行きと指値の時でかえる．
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
            			else{
            				if(i == 0 && day_array[j] < 20160719){
                				pw.print("NaN,");
                			}
                			else if(10 <= i && (day_array[j] == 20060104 || day_array[j] == 20061229 || day_array[j] == 20070104 || day_array[j] == 20071228 || day_array[j] == 20080104 || day_array[j] == 20081230 || day_array[j] == 20090105)){
            					pw.print("NaN,");
            				}
            				else{
            					
            					if(h%2 == 1){//指値キャンセルのとき
            						pw.print(-1*count_number[j][i][h] + ",");
            					}
            					else{//指値キャンセルのとき
            						pw.print(count_number[j][i][h] + ",");
            					}
            				}
            			}
            			if(i == 25){
            				pw.print("\n");
            			}
            		}
            	}
            	pw.print("\n");
            }
            
            for(int h=0;h<=2;h += 2){//成り行きと指値の時でかえる．
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
            			else{
            				if(i == 0 && day_array[j] < 20160719){
                				pw.print("NaN,");
                			}
                			else if(10 <= i && (day_array[j] == 20060104 || day_array[j] == 20061229 || day_array[j] == 20070104 || day_array[j] == 20071228 || day_array[j] == 20080104 || day_array[j] == 20081230 || day_array[j] == 20090105)){
            					pw.print("NaN,");
            				}
            				else{
            					//pw.print((double)((count_number[j][i][h+1]))/count_number[j][i][h] + ",");//指値キャンセル以外
            					pw.print((double)((-1*count_number[j][i][h+1]))/count_number[j][i][h] + ",");//指値キャンセル
            					
            				}
            			}
            			if(i == 25){
            				pw.print("\n");
            			}
            		}
            	}
            	pw.print("\n");
            }
            
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














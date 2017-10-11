import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class JNIc_market_order_count{
//market_orderから時間ごとの約定枚数の平均・分散を算出するプロラグム

    public static void main(String[] args) throws IOException{

        BufferedReader br = new BufferedReader(new FileReader("filelist.txt"));//読み取りたいファイル名の記入
        String txtFileName;

        String JNIc_split[];




        while((txtFileName = br.readLine()) != null) {




        	FileReader fr = new FileReader(txtFileName);
            BufferedReader brtxt = new BufferedReader(fr);
            String line ="";




            
            

            String[] filename = txtFileName.split("\\.");

            double hour = 0;//時間
        	double minute = 0;//分
        	double second = 0;//秒
        	double time_total = 0;//時間を秒換算
        	double time_total_before = 0;

        	int market_order_count [][] = new int[3][30];//[1][]は累積枚数、[2][]は約定回数
        	int market_order_count_half[][] = new int[3][30];//[1][]は累積枚数、[2][]は約定回数(20060104,20061229,20070104,20071228,20080104,20081230,20090105の半日オークションで用いる)
        	int market_count_half = 1;//market_order_countで用いる変数（時間毎）
        	int market_count = 1;//market_order_countで用いる変数（時間毎）

        	int order_insert_half [][] = new int[30][5000000];
        	int order_insert [][] = new int[30][5000000];
        	int count_number_half = 0;//market_order_countで用いる変数（個数毎）
        	int count_number = 0;//market_order_countで用いる変数（個数毎）
        	int day = 0;


        	int market_count_for = 1;

        	int market_for_number [] = new int [30];
        	int for_time = 32400;
        	int for_count_number [] = new int [30];//時間ごとの約定枚数について、とりあえず格納する変数
        	int for_count_half_number [] = new int[30];//時間ごとの約定枚数について、とりあえず格納する変数


        	int day_if = 0;
        	
        	int count_26 = 0;



            while ((line = brtxt.readLine()) != null) {

            	JNIc_split = line.split(",", 0);

            	day = Integer.parseInt(JNIc_split[0]);
            	hour = Double.parseDouble(JNIc_split[1].substring(0, 2));//時間
            	minute = Double.parseDouble(JNIc_split[1].substring(3, 5));//分
            	second = Double.parseDouble(JNIc_split[1].substring(6));//秒
            	time_total = hour*3600 + minute*60 + second;//時間を秒換算
            	
            	


            	if(day_if == 0){
            		day_if = day;
            	}
            	else if(day_if != day){
            		if(market_count == 26 && 
            				(((JNIc_split.length == 5 && (JNIc_split[4].equals("bid") || JNIc_split[4].equals("ask"))) && Integer.parseInt(JNIc_split[2]) >= 0 
                        	)	//||	(JNIc_split.length == 6 && !(JNIc_split[5].equals("final trade")))
                        			)
            				){
            			
            			/*if(time_total_before == time_total){//(複合注文を仮定するとき)ここから
    						order_insert[market_count][count_number] += Integer.parseInt(JNIc_split[2]);
    					}
    					else{//(複合注文を仮定するとき)ここまで*/
    						
    						order_insert[market_count][count_number] = Integer.parseInt(JNIc_split[2]);
    						market_order_count[2][26]++;
    						count_number++;
    					//}
            			
            			
    					market_order_count[1][26] += Integer.parseInt(JNIc_split[2]);
    					market_for_number[26] = count_number;
    					
    					//System.out.println("happy");
    					
                		

            		}
            		else if(market_count != 1){
            			market_for_number[26] = count_number;
            		}
            		
            		if(market_count_half == 10 &&
            				(((JNIc_split.length == 5 && (JNIc_split[4].equals("bid") || JNIc_split[4].equals("ask"))) && Integer.parseInt(JNIc_split[2]) >= 0
            						)// || 	(JNIc_split.length == 6 && !(JNIc_split[5].equals("final trade")))
                        			)
            				){
            			//System.out.println("aaa");
            			/*if(time_total_before == time_total){//(複合注文を仮定するとき)ここから
            				order_insert_half[10][count_number_half] += Integer.parseInt(JNIc_split[2]);
            			}
            			else{//(複合注文を仮定するとき)ここまで*/
            				order_insert_half[10][count_number_half] = Integer.parseInt(JNIc_split[2]);
            				count_number_half++;
            				market_order_count_half[2][10]++; 
            			//}
            			
            			
            			
            			market_order_count_half[1][10] += Integer.parseInt(JNIc_split[2]);
                		
                		
                		for_count_half_number[10] = count_number_half;
            		}
            		else if(market_count_half != 1){
            			
            			for_count_half_number[10] = count_number_half;
            		}
            		
            		

            		day_if = day;

            		for_time = 32400;
            		//market_count_for = 1;
            		market_count = 1;
            		
            		market_count_half = 1;
            		

            		/*for(int i = 1;i<=26;i++){
            			for_count_number[i] = market_for_number[i];
            			//System.out.println(market_for_number[i]);
            			//System.out.println(line);

            		}*/
            		
            		count_number_half = for_count_number[1];

            		count_number = for_count_number[1];
            	}

            	if((day == 20060104 || day == 20061229 || day == 20070104 || day == 20071228 || day == 20080104 || day == 20081230 || day == 20090105) && 
            			(((JNIc_split.length == 5 && (JNIc_split[4].equals("bid") || JNIc_split[4].equals("ask"))) && Integer.parseInt(JNIc_split[2]) >= 0
            					) //|| (JNIc_split.length == 6 && !(JNIc_split[5].equals("final trade")))
            			)){
            		
            		
            				
            		if(for_time < time_total){
            			
            			//System.out.println(market_count_half + "aaa" + line);
            			
            			for_count_half_number[market_count_half] = count_number_half;
            			
            			market_count_half++;
            			count_number_half = 0;
            			for_time += 900;
            			
            			count_number_half = for_count_half_number[market_count_half];
            			
            			market_order_count_half[1][market_count_half] += Integer.parseInt(JNIc_split[2]);
                		
                		
                		
                		order_insert_half[market_count_half][count_number_half] = Integer.parseInt(JNIc_split[2]);
                		market_order_count_half[2][market_count_half]++;
                    	count_number_half++;
                		
                			
            			
        			}
            		else{
            			//System.out.println(line);
            			
            			market_order_count_half[1][market_count_half] += Integer.parseInt(JNIc_split[2]);
            			/*if(time_total_before == time_total){//(複合注文を仮定するとき)ここから
                			order_insert_half[market_count_half][count_number_half] += Integer.parseInt(JNIc_split[2]);
                		}
                		else{//(複合注文を仮定するとき)ここまで*/
                			order_insert_half[market_count_half][count_number_half] = Integer.parseInt(JNIc_split[2]);
                			market_order_count_half[2][market_count_half]++;
                    		count_number_half++;
                		//}
                		
            		}
            		
            		
            		
            	}
            	else if((day != 20060104 && day != 20061229 && day != 20070104 && day != 20071228 && day != 20080104 && day != 20081230 && day != 20090105) && 
            			((JNIc_split.length == 5 && (JNIc_split[4].equals("bid") || JNIc_split[4].equals("ask")) && Integer.parseInt(JNIc_split[2]) >= 0
            					) //|| (JNIc_split.length == 6 && !(JNIc_split[5].equals("final trade")))
            			)){
            		//for(int j = 31500;j<=54000;j+=900){

            			//if(j <= time_total && time_total < (j + 900) && (JNIc_split[4].equals("bid") || JNIc_split[4].equals("ask")) && Integer.parseInt(JNIc_split[2]) >= 0
            				//	&& (JNIc_split.length == 6 && !(JNIc_split[5].equals("final trade")))){
            				//market_order_count[1][market_count] += Integer.parseInt(JNIc_split[2]);
            				//market_order_count[2][market_count]++;

            		//System.out.println(for_time);

            				if(for_time <= time_total && time_total <= 54900){
            					
            					
            					if(day_if < 20110214 && for_time == 39600 && 39600 <= time_total){
            						
            						//System.out.println(line);
            						market_for_number[market_count] = count_number;
        							for_time += 900;
        							market_count++;
        							//market_count_for += 6;
        							count_number = market_for_number[market_count];
        							
        							
        							
        							order_insert[market_count][count_number] = Integer.parseInt(JNIc_split[2]);
                					market_order_count[1][market_count] += Integer.parseInt(JNIc_split[2]);
                					market_order_count[2][market_count]++;
                					count_number++;
        							
        						}
            					else if(day_if < 20110214 && for_time == 40500){
            						
            						market_for_number[market_count] = count_number;
        							for_time += 5400;
        							market_count += 6;
        							//market_count_for += 6;
        							count_number = market_for_number[market_count];
        							
        							
        							
        							order_insert[market_count][count_number] = Integer.parseInt(JNIc_split[2]);
                					market_order_count[1][market_count] += Integer.parseInt(JNIc_split[2]);
                					market_order_count[2][market_count]++;
                					count_number++;
        							
            					}
        						else{
        							
        							
        							
        							      							
        							market_for_number[market_count] = count_number;
        							for_time += 900;
        							market_count++;
        							//market_count_for++;
        							count_number = market_for_number[market_count];
        							
        							order_insert[market_count][count_number] = Integer.parseInt(JNIc_split[2]);
                					market_order_count[1][market_count] += Integer.parseInt(JNIc_split[2]);
                					market_order_count[2][market_count]++;
                					count_number++;
        							
        						}
            					
            					
            					
            					if(for_time <= time_total && for_time <= 54900){
            						while(for_time <= time_total && for_time <= 54900){
            							if(day_if < 20110214 && for_time == 39600 && 39600 <= time_total){
                    						//System.out.println(line);
                    						
                							for_time += 900;
                							market_count++;
                							//market_count_for += 6;
                							count_number = market_for_number[market_count];
                						}
                    					else if(day_if < 20110214 && for_time == 40500){
                    						
                							for_time += 5400;
                							market_count += 6;
                							//market_count_for += 6;
                							count_number = market_for_number[market_count];
                    					}
                						else{      							
                							for_time += 900;
                							market_count++;
                							//market_count_for++;
                							count_number = market_for_number[market_count];
                						}
            							
                					}
            						
            						order_insert[market_count][count_number] = Integer.parseInt(JNIc_split[2]);
                    				market_order_count[1][market_count] += Integer.parseInt(JNIc_split[2]);
                    				market_order_count[2][market_count]++;
                    				count_number++;
            						
            						
            					}
            						            					
            					//System.out.println(line + "+++" + for_time);
		
            					//System.out.println(market_count + ",,,,," + line + "***" + for_time);			
            					
            				}
            				else{
            					if(market_count == 10){
            						System.out.println(line);
            					}
            					
            					/*if(time_total_before == time_total){//(複合注文を仮定するとき)ここから
            						order_insert[market_count][count_number] += Integer.parseInt(JNIc_split[2]);
            						//System.out.println(order_insert[market_count][count_number]);
            					}
            					else{//(複合注文を仮定するとき)ここまで*/
            						
            						order_insert[market_count][count_number] = Integer.parseInt(JNIc_split[2]);
            						market_order_count[2][market_count]++;
            						count_number++;
            					//}
            					
            					
            					market_order_count[1][market_count] += Integer.parseInt(JNIc_split[2]);
            					
            					
            						
            				}
            			}
            	
            	if(54000 < time_total && time_total < 54900){
            		count_26++;
            	}
            			
            	time_total_before = time_total;
            }
            
            //System.out.println(count_26 + "----------");

            System.out.println("adsasd" + count_number_half);
            
            market_for_number[market_count] = count_number;
            for_count_half_number[market_count_half] = count_number_half;
            market_count_for = 1;
			market_count = 1;
			count_number = 1;
			for_time = 32400;

            File file = new File(filename[0] + "_count.csv");
            PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(file)));
            //System.out.println(filename[0]);

            if(market_order_count_half[2][market_count_half] != 0){
            	for(market_count_half = 1;market_count_half<=26;market_count_half++){
  		
            		double value_ave = (double)market_order_count_half[1][market_count_half]/market_order_count_half[2][market_count_half];
            		double value_va = 0;

            		System.out.println(for_count_half_number[market_count_half] + "aa" + market_count_half);
            		
            		for(int i = 0;i < for_count_half_number[market_count_half];i++){
            			
            			value_va += (order_insert_half[market_count_half][i] - value_ave)*(order_insert_half[market_count_half][i] - value_ave);;//分散の算出
            			order_insert_half[market_count_half][i] = 0;
            		}    		

            		pw.print(market_count_half + "," +(double)market_order_count_half[1][market_count_half]/market_order_count_half[2][market_count_half] + ",");
            		pw.print(value_va/market_order_count_half[2][market_count_half] + ",");
            		pw.print(market_order_count_half[2][market_count_half]);
            		pw.print("\n");

            		market_order_count_half[1][market_count_half] = 0;
            		market_order_count_half[2][market_count_half] = 0;
            	}
            	
            }
            //System.out.println("mmm");


            for(market_count = 1;market_count<=26;market_count++){

            	
            	double value_ave = (double)market_order_count[1][market_count]/market_order_count[2][market_count];
            	double value_va = 0;
            	
            	
            	//System.out.println(market_for_number[market_count]);

            	if(market_count == 26){
            		System.out.println(market_for_number[market_count]);
            	}
            	
            	for(int i = 0;i < market_for_number[market_count];i++){
            		
        			value_va += (order_insert[market_count][i] - value_ave)*(order_insert[market_count][i] - value_ave);//分散の算出
        			order_insert[market_count][i] = 0;
        		}
            	
            	

        		pw.print(market_count + "," + (double)market_order_count[1][market_count]/market_order_count[2][market_count] + ",");
        		pw.print((double)value_va/market_order_count[2][market_count] + ",");
        		pw.print(market_order_count[2][market_count] + "," + market_order_count[1][market_count]);
        		pw.print("\n");

        		market_order_count[1][market_count] = 0;
        		market_order_count[2][market_count] = 0;

        		count_number = 0;
        	}

            pw.print("\n");

            brtxt.close();
            fr.close();
            pw.close();



        }

        br.close();
    }
}


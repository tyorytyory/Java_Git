import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;

public class simuletor_new_pareto {//11/10更新

	public static void main(String args[])throws IOException{
		Sfmt ransu = new Sfmt(1);

		int count_pw = 1;//ファイル出力用
		int count_nariyuki = 0;//取引回数のカウント
		int count_sasine = 0;
		int count_up = 0;//板の移動
		int count_down = 0;
		int count_up_sum = 0;//板の移動の合計
		int count_down_sum = 0;
		/*File file = new File("simulater.txt");//ファイルを一つまとめて出力する
    	PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(file)));*/

		do{
			
			double total_pareto = 0.0;
			double time =21600.0;
			double trade_time1[] = new double[9000000];
			double trade_timing1[] = new double[9000000];
			double alpha = 0.0;
			double beta = 0.0;
			int i1 = 0;
			int i2 = 0;
			double dummy_c = 0;
			//double dummy_c2;
			double c = 0;
			int hour = 9;
			int minute = 0;
			double second = 0.0;
			//int dummy = 0;
			double for_time_alpha = 0.0;
			double alpha_array[] = new double[24];//alphaの時間帯による変化
			alpha_array[0] = 0.77;//9:00~9:15
			alpha_array[1] = 0.87;//9:15~9:30
			alpha_array[2] = 0.97;//9:30~9:45
			alpha_array[3] = 0.97;
			alpha_array[4] = 0.97;
			alpha_array[5] = 0.97;
			alpha_array[6] = 0.97;
			alpha_array[7] = 0.97;
			alpha_array[8] = 0.97;
			alpha_array[9] = 0.97;
			alpha_array[10] = 0.97;
			alpha_array[11] = 0.97;
			alpha_array[12] = 0.97;
			alpha_array[13] = 0.97;
			alpha_array[14] = 0.97;
			alpha_array[15] = 0.97;
			alpha_array[16] = 0.97;
			alpha_array[17] = 0.97;
			alpha_array[18] = 0.97;
			alpha_array[19] = 0.97;
			alpha_array[20] = 0.97;
			alpha_array[21] = 0.97;
			alpha_array[22] = 0.97;
			alpha_array[23] = 0.93;//14:45~15:00


			do{
				if(total_pareto > 7200 && total_pareto < 12600){//昼休み
					total_pareto = 12600;
				}
				else{
					/*dummy_c = Math.random();
					dummy_c = (dummy_c)*100+1;
					int dummy_c1 = (int)dummy_c;

					for(int i=0;i<=dummy_c1;i++){*/
						c = ransu.NextUnif();
					//}

					for_time_alpha=0.0;//初期化
					for(int i=0;i<24;i++){
						if(total_pareto >=for_time_alpha && total_pareto<(for_time_alpha+900.0)){
							alpha = alpha_array[i];
							//System.out.println(i + " " + total);
							break;
						}
						for_time_alpha += 900.0;
					}

					beta = 0.6;


						double x1 = c/(1-c);
						double x2 = Math.pow(x1,beta);
						double x3 = x2*alpha;
						BigDecimal x4 = new BigDecimal(x3);
			    		double x = x4.setScale(3, BigDecimal.ROUND_UP).doubleValue();
			    		trade_time1[i1] = x;
			    		total_pareto = total_pareto + x;
						double total1 = total_pareto + x;
						BigDecimal total2 = new BigDecimal(total1);
			    		total_pareto = total2.setScale(3, BigDecimal.ROUND_UP).doubleValue();
						trade_timing1[i1] = total_pareto;
						i1++;
						/*if(c>0.9){
							System.out.println(c+":"+x3);
						}*/
						if(x==0){
							System.out.println(x);
						}

				}

			}while(total_pareto < time);


			double trade_time2[] = new double[900000];
			double trade_timing2[] = new double[900000];

			double for_time_pareto = 0.0;

			/*alpha_array[0] = 0.70;//9:00~9:15//小数点第２位
			alpha_array[1] = 0.76;//9:15~9:30
			alpha_array[2] = 0.79;//9:30~9:45
			alpha_array[3] = 0.80;
			alpha_array[4] = 0.79;
			alpha_array[5] = 0.83;
			alpha_array[6] = 0.88;
			alpha_array[7] = 0.89;
			alpha_array[8] = 0.7;
			alpha_array[9] = 0.7;
			alpha_array[10] = 0.7;
			alpha_array[11] = 0.7;
			alpha_array[12] = 0.7;
			alpha_array[13] = 0.7;
			alpha_array[14] = 0.81;
			alpha_array[15] = 1.05;
			alpha_array[16] = 0.64;
			alpha_array[17] = 0.94;
			alpha_array[18] = 0.92;
			alpha_array[19] = 0.88;
			alpha_array[20] = 0.88;
			alpha_array[21] = 0.86;
			alpha_array[22] = 0.84;
			alpha_array[23] = 0.85;//14:45~15:00

			double beta_array[] = new double[24];//betaの時間帯による変化
			beta_array[0] = 0.51;//9:00~9:15//小数点第２位
			beta_array[1] = 0.57;//9:15~9:30
			beta_array[2] = 0.60;//9:30~9:45
			beta_array[3] = 0.60;
			beta_array[4] = 0.57;
			beta_array[5] = 0.63;
			beta_array[6] = 0.62;
			beta_array[7] = 0.60;
			beta_array[8] = 0.7;
			beta_array[9] = 0.7;
			beta_array[10] = 0.7;
			beta_array[11] = 0.7;
			beta_array[12] = 0.7;
			beta_array[13] = 0.7;
			beta_array[14] = 0.64;
			beta_array[15] = 0.51;
			beta_array[16] = 0.78;
			beta_array[17] = 0.54;
			beta_array[18] = 0.52;
			beta_array[19] = 0.62;
			beta_array[20] = 0.59;
			beta_array[21] = 0.64;
			beta_array[22] = 0.65;
			beta_array[23] = 0.62;//14:45~15:00*/

			alpha_array[0] = 0.68;//9:00~9:15 0.001s
			alpha_array[1] = 0.73;//9:15~9:30
			alpha_array[2] = 0.76;//9:30~9:45
			alpha_array[3] = 0.78;
			alpha_array[4] = 0.76;
			alpha_array[5] = 0.79;
			alpha_array[6] = 0.84;
			alpha_array[7] = 0.86;
			alpha_array[8] = 0.7;
			alpha_array[9] = 0.7;
			alpha_array[10] = 0.7;
			alpha_array[11] = 0.7;
			alpha_array[12] = 0.7;
			alpha_array[13] = 0.7;
			alpha_array[14] = 0.81;
			alpha_array[15] = 1.05;
			alpha_array[16] = 0.64;
			alpha_array[17] = 0.94;
			alpha_array[18] = 0.92;
			alpha_array[19] = 0.88;
			alpha_array[20] = 0.88;
			alpha_array[21] = 0.86;
			alpha_array[22] = 0.84;
			alpha_array[23] = 0.85;//14:45~15:00

			double beta_array[] = new double[24];//betaの時間帯による変化
			beta_array[0] = 0.65;//9:00~9:15//0.001s
			beta_array[1] = 0.71;//9:15~9:30
			beta_array[2] = 0.74;//9:30~9:45
			beta_array[3] = 0.68;
			beta_array[4] = 0.71;
			beta_array[5] = 0.77;
			beta_array[6] = 0.76;
			beta_array[7] = 0.69;
			beta_array[8] = 0.7;
			beta_array[9] = 0.7;
			beta_array[10] = 0.7;
			beta_array[11] = 0.7;
			beta_array[12] = 0.7;
			beta_array[13] = 0.7;
			beta_array[14] = 0.76;
			beta_array[15] = 0.79;
			beta_array[16] = 0.72;
			beta_array[17] = 0.81;
			beta_array[18] = 0.79;
			beta_array[19] = 0.74;
			beta_array[20] = 0.79;
			beta_array[21] = 0.72;
			beta_array[22] = 0.73;
			beta_array[23] = 0.76;//14:45~15:00

			total_pareto = 0;

			i1  = 0;
			int i3 = 0;

			do{
				if(total_pareto > 7200 && total_pareto < 12600){//昼休み
					total_pareto = 12600;
				}
				else{
					/*dummy_c = Math.random();
					dummy_c = (dummy_c)*100+1;
					int dummy_c1 = (int)dummy_c;

					for(int i=0;i<=dummy_c1;i++){*/
						c = ransu.NextUnif();
					//}

					for_time_pareto=0.0;//初期化
					for(int i=0;i<24;i++){
						if(total_pareto >=for_time_pareto && total_pareto<(for_time_pareto+900.0)){
							alpha = alpha_array[i];
							//System.out.println(i + " " + total);
							break;
						}
						for_time_pareto += 900.0;
					}
					for_time_pareto=0.0;//初期化
					for(int i=0;i<24;i++){
						if(total_pareto >=for_time_pareto && total_pareto<(for_time_pareto+900.0)){
							beta = beta_array[i];
							//System.out.println(i + " " + total);
							break;
						}
						for_time_pareto += 900.0;
					}

					double x1 = c/(1-c);
					double x2 = Math.pow(x1,beta);
					double x3 = x2*alpha;
					BigDecimal x4 = new BigDecimal(x3);
		    		double x = x4.setScale(3, BigDecimal.ROUND_UP).doubleValue();
		    		trade_time1[i1] = x;
		    		total_pareto = total_pareto + x;
					double total1 = total_pareto + x;
					BigDecimal total2 = new BigDecimal(total1);
		    		total_pareto = total2.setScale(3, BigDecimal.ROUND_UP).doubleValue();
					trade_timing2[i1] = total_pareto;
					i1++;
				}

			}while(total_pareto < time);


			int bid_depth = 190;
			int ask_depth = 225;
			int bid_trade = 28;
			int ask_trade = 28;
			int bid_limit_order = 9;
			int ask_limit_order = 9;
			int bid_price = 15000;
			int ask_price = 15010;
			double limit_order_time = 1383;//秒単位(1000倍する)
			double limit_order_time_dif = 1383;//秒単位(1000倍する)
	        double bid_or_ask;
	        int bid_or_ask_Sfmt;
	        String second_string = null;
	        int second_length = 0;
	        String second_konma = null;
	        double for_time_order = 0.0;
	        int count_simulate = count_pw;
	        String count_simulate_length = null;
	        int count_simulate_zero = 0;

			 //try {
		        	//File file = new File("simulater_pareto" + count_pw + ".txt");//ファイルを一つ一つ出力する
		        	//PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(file)));
		        	for_time_order = time*1000;

		        	//pw.print("SIMn8,");

		        	count_simulate_length = String.valueOf(count_simulate);
		        	count_simulate_zero = count_simulate_length.length();
		        	for(int i=1;i<=8-count_simulate_zero;i++){
			        	//pw.print("0");
		        	}
		        	//pw.print(count_simulate + ",");


		        	//pw.println("09:00:00.000,,,,,," + bid_price + "," + bid_depth + "," + ask_price + "," + ask_depth + ",,,,,,,,,,");


		        	for(double d = 0;d<=for_time_order;d++){
		    			double d1 = d/1000;
		    			if(d1 != 0 && d1%60==0){//秒を分に、分を時間にする
	                        minute++;
	                        if(minute == 60){
	                            hour++;
	                            minute = 0;
	                        }
	                    }
		    			second = d1;

		    			if(d1 == trade_timing1[i2]){
		    				count_nariyuki++;//取引回数のカウント


		    				//pw.print("SIMn8,");
		    				count_simulate_length = String.valueOf(count_simulate);
		    				count_simulate_zero = count_simulate_length.length();
				        	for(int i=1;i<=8-count_simulate_zero;i++){
					        	//pw.print("0");
				        	}
				        	//pw.print(count_simulate + ",");

		    				for(double n = 1;n<50000;n++){
	        					if(second >= n*60 && second < (n+1)*60){
	        						second = second - n*60.0;
	        					}
	        				}
	                        BigDecimal second1 = new BigDecimal(second);
	        	    		second = second1.setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue();
	                        if(1==Integer.toString(hour).length()){
	                            //pw.print("0"+ hour + ":");
	                        }
	                        else{
	                            //pw.print(hour + ":");
	                        }
	                        if(1==Integer.toString(minute).length()){
	                            //pw.print("0"+ minute + ":");
	                        }
	                        else{
	                            //pw.print(minute + ":");
	                        }
	                        second_string = String.valueOf(second);
	                        second_length = Double.toString(second).length();
	                        second_konma = second_string.substring(1,2);
	                        if(second_konma.equals(".")){
	                        	if(second_length == 3){
	                        		//pw.print("0"+ second + "00");
	                        	}
	                        	else if(second_length == 4){
	                        		//pw.print("0"+ second + "0");
	                        	}
	                        	else{
	                        		//pw.print("0"+ second);
	                        	}
	                        }
	                        else{
	                        	if(second_length == 4){
	                        		//pw.print(second + "00");
	                        	}
	                        	else if(second_length == 5){
	                        		//pw.print(second + "0");
	                        	}
	                        	else{
	                        		//pw.print(second);
	                        	}
	                        }
	                        /*bid_or_ask = Math.random();
	                        bid_or_ask = (dummy_c)*100+1;
	        				bid_or_ask_Sfmt = (int)bid_or_ask;
	        				for(int i=0;i<=bid_or_ask_Sfmt;i++){*/
	        					bid_or_ask = ransu.NextUnif();
	        				//}


	                        if(bid_or_ask<=0.46){//買い注文
	                        	ask_depth -=bid_trade;
	                        	if(ask_depth <= 0){
	                        		//pw.print(",Trade,," + bid_price + "," + (bid_trade+ask_depth));// + " " + bid_depth + " " + ask_depth);
	                        	}
	                        	else{
	                        		//pw.print(",Trade,," + bid_price + "," + bid_trade);// + " " + bid_depth + " " + ask_depth);
	                        	}
	                        }
	                        else{//売り注文
	                            bid_depth -=ask_trade;
	                            if(bid_depth <= 0){
	                            	//pw.print(",Trade,," + ask_price + "," + (ask_trade+bid_depth));// + " "+ bid_depth + " " + ask_depth);
	                            }
	                            else{
	                            	//pw.print(",Trade,," + ask_price + "," + ask_trade );//+ " "+ bid_depth + " " + ask_depth);
	                            }

	                        }

	                        i2++;
	                        if(bid_depth <= 0){
	                        	ask_depth = 41 + (-1*bid_depth);
	                        	bid_depth = 307;
	                        	bid_price -=10;
	                        	ask_price -=10;
	                        	count_down++;
	                        }
	                        if(ask_depth <= 0){
	                        	bid_depth = 41 + (-1*ask_depth);
	                        	ask_depth = 302;
	                        	bid_price +=10;
	                        	ask_price +=10;
	                        	count_up++;
	                        }

	                        //pw.println(",,,,,,,,,,,,,");
		    			}
		    			if(d1 > 7200 && d1 <12600){//昼休み
		    				bid_depth = 190;
		    				ask_depth = 225;
		    				bid_price = 15000;
		    				ask_price = 15010;

		    			}
		    			if(trade_timing2[i3] == d1){
		    				count_sasine++;//取引回数のカウント
		    				i3++;
		    				limit_order_time +=limit_order_time_dif;
		    				//pw.print("SIMn8,");

		    				count_simulate_length = String.valueOf(count_simulate);
		    				count_simulate_zero = count_simulate_length.length();
				        	for(int i=1;i<=8-count_simulate_zero;i++){
					        	//pw.print("0");
				        	}
				        	//pw.print(count_simulate + ",");

		    				for(double n = 1;n<50000;n++){
	        					if(second >= n*60 && second < (n+1)*60){
	        						second = second - n*60.0;
	        					}
	        				}
		    				BigDecimal second1 = new BigDecimal(second);
	        	    		second = second1.setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue();
		    				if(1==Integer.toString(hour).length()){
	                            //pw.print("0"+ hour + ":");
	                        }
	                        else{
	                            //pw.print(hour + ":");
	                        }
	                        if(1==Integer.toString(minute).length()){
	                            //pw.print("0"+ minute + ":");
	                        }
	                        else{
	                            //pw.print(minute + ":");
	                        }
	                        second_string = String.valueOf(second);
	                        second_length = Double.toString(second).length();
	                        second_konma = second_string.substring(1,2);
	                        if(second_konma.equals(".")){
	                        	if(second_length == 3){
	                        		//pw.print("0"+ second + "00");
	                        	}
	                        	else if(second_length == 4){
	                        		//pw.print("0"+ second + "0");
	                        	}
	                        	else{
	                        		//pw.print("0"+ second);
	                        	}
	                        }
	                        else{
	                        	if(second_length == 4){
	                        		//pw.print(second + "00");
	                        	}
	                        	else if(second_length == 5){
	                        		//pw.print(second + "0");
	                        	}
	                        	else{
	                        		//pw.print(second);
	                        	}
	                        }
	                        /*bid_or_ask = Math.random();
	                        bid_or_ask = (dummy_c)*100+1;
	        				bid_or_ask_Sfmt = (int)bid_or_ask;
	        				for(int i=0;i<=bid_or_ask_Sfmt;i++){*/
	        					bid_or_ask = ransu.NextUnif();
	        				//}
		    				if(bid_or_ask<=0.48){//買い注文
		    					bid_depth += bid_limit_order;
		    				}
		    				else{//売り注文
		    					ask_depth += ask_limit_order;
		    				}
		    				//pw.print(",Quote,,,,," + bid_price + "," + bid_depth + "," + ask_price + "," + ask_depth);

	                        //pw.println(",,,,,,,,,,,,,,");
		    				//System.out.println(limit_order_time);
		    			}
		        	}


		        		//pw.close();//ファイルを一つ一つ書き込むもの

		        /*}catch(IOException e){
		        	System.out.println(e);
		        }*/
		        /*System.out.println(count_nariyuki);//取引回数のカウント
		        System.out.println(count_sasine);//取引回数のカウント
		        System.out.println(count_up);//板の移動
 		        System.out.println(count_down);//板の移動*/
 		       count_up_sum += count_up;
		        count_down_sum += count_down;
		        count_nariyuki = 0;
		        count_sasine = 0;
		        count_up=0;
 		        count_down=0;
			 count_pw++;
			 


		}while(count_pw <= 100);
		//pw.close();//まとめて出力
		System.out.println(count_up_sum + " " + count_down_sum);

}
	public static double Ram(){


			double b_random = Math.random();


			return b_random;
			}
	}




import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;

public class simulater_new {//11/10更新

	public static void main(String args[]){
		int count = 0;
		do{

		Sfmt ransu = new Sfmt(1);
		double total = 0.0;
		double time =21600.0;
		double trade_time[] = new double[9000000];
		double trade_timing[] = new double[9000000];
		double alpha = 0.0;
		double beta = 0.0;
		int i1 = 0;
		int i2 = 0;
		double dummy_c = 0;
		double dummy_c2;
		double c = 0;
		int hour = 9;
		int minute = 0;
		double second = 0.0;
		int dummy = 0;
		double for_time = 0.0;
		int for_count[] = new int[24];
		double alpha_array[] = new double[24];
		alpha_array[0] = 0.72;
		alpha_array[1] = 1.07;
		alpha_array[2] = 1.25;
		alpha_array[3] = 1.43;
		alpha_array[4] = 1.5;
		alpha_array[5] = 1.67;
		alpha_array[6] = 1.66;
		alpha_array[7] = 1.97;
		alpha_array[8] = 5.39;
		alpha_array[9] = 8.49;
		alpha_array[10] = 12.6;
		alpha_array[11] = 13.6;
		alpha_array[12] = 10.11;
		alpha_array[13] = 7.04;
		alpha_array[14] = 1.22;
		alpha_array[15] = 1.82;
		alpha_array[16] = 2.16;
		alpha_array[17] = 2.36;
		alpha_array[18] = 2.33;
		alpha_array[19] = 2.3;
		alpha_array[20] = 1.99;
		alpha_array[21] = 1.93;
		alpha_array[22] = 1.68;
		alpha_array[23] = 1.34;


		do{


			dummy_c = Math.random();
			dummy_c = (dummy_c)*100+1;
			int dummy_c1 = (int)dummy_c;
			for(int i=0;i<=dummy_c1;i++){
				c = ransu.NextUnif();
			}

			for(int i=0;i<24;i++){
				for_time=0.0;
				for_count[i] = 0;//初期化
				if(total >= for_time && total<900.0){
					for_count[i]++;
				}
				if(for_count[i]==1){
					alpha = alpha_array[i];
				}
				for_time += 900.0;
			}

			beta = 1.17;

			double x1 = c/(1-c);
			double x2 = Math.pow(x1,beta);
			double x3 = x2*alpha;
			BigDecimal x4 = new BigDecimal(x3);
    		double x = x4.setScale(3, BigDecimal.ROUND_UP).doubleValue();
    		trade_time[i1] = x;
    		total = total + x;
			double total1 = total + x;
			BigDecimal total2 = new BigDecimal(total1);
    		total = total2.setScale(3, BigDecimal.ROUND_UP).doubleValue();
			trade_timing[i1] = total;
			i1++;
			/*if(c>0.9){
				System.out.println(c+":"+x3);
			}*/
			if(x==0){
				System.out.println(x);
			}

		}while(total < time);


		int bid_depth = 100;
		int ask_depth = 100;
		int bid_price = 15000;
		int ask_price = 15010;
		double limit_order_time = 10;

        double bid_or_ask;
        int count_trade = 0;
        int count_limit = 0;
        String second_string = null;
        int second_length = 0;
        String second_konma = null;


		 try {
	        	File file = new File("simulater" + count + ".txt");
	        	PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(file)));
	        	double for_time2 = time*1000;

	        	pw.println("09:00:00.000,,,,,," + bid_price + "," + bid_depth + "," + ask_price + "," + ask_depth + ",,,,,,,,,,");


	        	for(double d = 0;d<=for_time2;d++){
	    			double d1 = d/1000;
	    			if(d1 != 0 && d1%60==0){//秒を分に、分を時間にする
                        minute++;
                        if(minute == 60){
                            hour++;
                            minute = 0;
                        }
                    }
	    			second = d1;

	    			if(d1 == trade_timing[i2]){

	    				for(double n = 1;n<50000;n++){
        					if(second >= n*60 && second < (n+1)*60){
        						second = second - n*60.0;
        					}
        				}
                        BigDecimal second1 = new BigDecimal(second);
        	    		second = second1.setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue();
                        if(1==Integer.toString(hour).length()){
                            pw.print("0"+ hour + ":");
                        }
                        else{
                            pw.print(hour + ":");
                        }
                        if(1==Integer.toString(minute).length()){
                            pw.print("0"+ minute + ":");
                        }
                        else{
                            pw.print(minute + ":");
                        }
                        second_string = String.valueOf(second);
                        second_length = Double.toString(second).length();
                        second_konma = second_string.substring(1,2);
                        if(second_konma.equals(".")){
                        	if(second_length == 3){
                        		pw.print("0"+ second + "00");
                        	}
                        	else if(second_length == 4){
                        		pw.print("0"+ second + "0");
                        	}
                        	else{
                        		pw.print("0"+ second);
                        	}
                        }
                        else{
                        	if(second_length == 4){
                        		pw.print(second + "00");
                        	}
                        	else if(second == 5){
                        		pw.print(second + "0");
                        	}
                        	else{
                        		pw.print(second);
                        	}
                        }
                        bid_or_ask = Math.random();
                        if(bid_or_ask<=0.5){//買い注文
                        	ask_depth -= 30;
                        	pw.print(",Trade,," + bid_price + ",30");
                        }
                        else{//売り注文
                            bid_depth -= 30;
                            pw.print(",Trade,," + ask_price + ",30");
                        }

                        i2++;
                        if(bid_depth <= 0){
                        	bid_depth = 100;
                        	ask_depth = 50;
                        	bid_price -=10;
                        	ask_price -=10;
                        }
                        if(ask_depth <= 0){
                        	bid_depth = 50;
                        	ask_depth = 100;
                        	bid_price +=10;
                        	ask_price +=10;
                        }

                        pw.println(",,,,,,,,,,,,,");
	    			}
	    			if((limit_order_time/10) == d1){
	    				limit_order_time +=10;
	    				for(double n = 1;n<50000;n++){
        					if(second >= n*60 && second < (n+1)*60){
        						second = second - n*60.0;
        					}
        				}
	    				BigDecimal second1 = new BigDecimal(second);
        	    		second = second1.setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue();
	    				if(1==Integer.toString(hour).length()){
                            pw.print("0"+ hour + ":");
                        }
                        else{
                            pw.print(hour + ":");
                        }
                        if(1==Integer.toString(minute).length()){
                            pw.print("0"+ minute + ":");
                        }
                        else{
                            pw.print(minute + ":");
                        }
                        second_string = String.valueOf(second);
                        second_length = Double.toString(second).length();
                        second_konma = second_string.substring(1,2);
                        if(second_konma.equals(".")){
                        	if(second_length == 3){
                        		pw.print("0"+ second + "00");
                        	}
                        	else if(second_length == 4){
                        		pw.print("0"+ second + "0");
                        	}
                        	else{
                        		pw.print("0"+ second);
                        	}
                        }
                        else{
                        	if(second_length == 4){
                        		pw.print(second + "00");
                        	}
                        	else if(second == 5){
                        		pw.print(second + "0");
                        	}
                        	else{
                        		pw.print(second);
                        	}
                        }
	    				bid_or_ask = Math.random();
	    				if(bid_or_ask<=0.5){//買い注文
	    					bid_depth += 3;
	    				}
	    				else{//売り注文
	    					ask_depth += 3;
	    				}
	    				pw.print(",,,,,Quote," + bid_price + "," + bid_depth + "," + ask_price + "," + ask_depth);

                        pw.println(",,,,,,,,,,,,,,");
	    				//System.out.println(limit_order_time);
	    			}


                    count_trade = 0;
                    count_limit = 0;

	        	}


	        		pw.close();

	        }catch(IOException e){
	        	System.out.println(e);
	        }
		 count++;
		}while(count <= 2);

}
	public static double Ram(){


			double b_random = Math.random();


			return b_random;
			}
	}




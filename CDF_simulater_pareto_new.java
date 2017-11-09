import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.Random;
//pareto3で注文を発生させるプログラム
public class CDF_simulater_pareto_new{

    public static void main(String[] args) throws IOException{

        BufferedReader br = new BufferedReader(new FileReader("filelist.txt"));//読み取りたいファイル名の記入
        String txtFileName;
        
        String market_inerval_para [] []= new String [500][30];//成行注文，時間間隔のパラメータ
        int day [] = new int[500];//日付を格納する配列
        int first_number = 1;//配列で用いる関数（１つめ）
        int second_number = 1;//配列で用いる関数（２つめ）
        
        
        
        while((txtFileName = br.readLine()) != null) {
        	String[] filename = txtFileName.split("_");
        	
        	boolean first_row = true;
    		
    		FileReader fr = new FileReader(txtFileName);
            BufferedReader brtxt = new BufferedReader(fr);
            
            String line = "";
            first_number = 1;
            second_number = 1;
            
            while ((line = brtxt.readLine()) != null) {
            	
            	String para_split[] = line.split(",", 0);
            	
            	if(first_row == true){
            		first_row = false;
            	}
            	else{
            		if(filename[1].equals("interval")){            			
            			for(second_number = 1;second_number <= (para_split.length-1) ;second_number++){
            				market_inerval_para [first_number][second_number] = para_split[second_number];
            			}
            			day[first_number] = Integer.parseInt(para_split[0]);
            			first_number++;
            		}
            	}
            }
            brtxt.close();
            fr.close();
    	}
        br.close();
        	
        first_number = 1;//二つの配列の１つめ
     

    	File file = new File("simulater_test.txt");
     	PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(file)));
     	
     	
    	
     	
     	
     	

        	String Index;
        	Random rnd = new Random();//Math Random
        	int Sfmt_number = rnd.nextInt(100000);//メルセンヌ・ツイスタの引数
        	Sfmt ransu = new Sfmt(Sfmt_number);//メルセンヌ・ツイスタ乱数．引数を変えることで，乱数の発生パターンを変更
        	CDF_birth cdf = new CDF_birth();//様々な累積分布関数から時間間隔や注文量を出力するオブジェクト
        	
        	int bid_depth = 30;//買板の累積枚数
        	int ask_depth = 30;//売板の累積枚数
        	int bid_price = 10000;//最良買気配の価格
        	int ask_price = 10010;//最良売気配の価格
        	int bid_market_order = 0;//買い成行注文量
        	int ask_market_order = 0;//売り成行注文量
        	int bid_limit_order = 0;//買い指値注文量
        	int ask_limit_order = 0;//売り指値注文量
        	int bid_limit_cancel = 0;//買い指値キャンセル量
        	int ask_limit_cancel = 0;//売り指値キャンセル量       	
        	double market_order_intervals = 0;//成行注文の注文間隔
        	double limit_order_intervals = 0;//指値注文の注文間隔
        	double limit_cancel_intervals = 0;//指値キャンセルの注文間隔
        	double market_order_intervals_sum = 0;//成行注文の注文間隔の累積
        	double limit_order_intervals_sum = 0;//指値注文の注文間隔の累積
        	double limit_cancel_intervals_sum = 0;//指値キャンセルの注文間隔の累積
        	boolean next_market_order = false;//次の注文の種類
        	boolean next_limit_order = false;//次の注文の種類
        	boolean next_limit_cancel = false;//次の注文の種類
        	double bid_prob = 0;//買い注文の確率
        	double ask_prob = 0;//売り注文の確率
        	
        	int hour = 0;//時（出力時に用いる）
        	int minute = 0;//分（出力時に用いる）
        	double second = 0;//秒（出力時に用いる）
        	String hour_out = "";//出力用の時間
        	String minute_out = "";//出力用の時間
        	String second_out = "";//出力用の時間
        	
        	int output_number = 1;//出力された数（いらない可能性）
        	
        	
        	
        	        
        	
        	//System.out.println(y);
        	for(int i = 0;i<=0;i++){//テスト用
        		double y = ransu.NextUnif();
        		y = 0.5;
        		//System.out.println(y + "乱数");
            	//double test = cdf.poisson(y,5);	
            	//System.out.println(test + "採用");
            	
        	}
        	
        	while(first_number <= 2 && market_order_intervals_sum <= 54000 && limit_order_intervals_sum <= 54000 && limit_cancel_intervals_sum <= 54000){
        		if(market_order_intervals_sum == 0 || limit_order_intervals_sum == 0 || limit_cancel_intervals_sum == 0){
        			System.out.println(market_inerval_para[1][1]);
        			
        			String para_split_simu[] = market_inerval_para[first_number][1].split("	", 0);
        			
            		market_order_intervals_sum = 32400 + cdf.pareto3(ransu.NextUnif(), Double.parseDouble(para_split_simu[0].substring(1)), Double.parseDouble(para_split_simu[1].substring(0,15)));
            		limit_order_intervals_sum = 32400 + cdf.expo(ransu.NextUnif(), 0.96);
            		limit_cancel_intervals_sum = 32400 + cdf.expo(ransu.NextUnif(), 0.53);
            		System.out.println(market_order_intervals_sum + "+" + limit_order_intervals_sum + "+" + limit_cancel_intervals_sum);
            	}
            	
            	
            	 double min_intervals = market_order_intervals_sum;//min_intervalsは時間間隔の累積で一番小さいものを選ぶ変数．この最小値が次に到着する注文となる．
            	  if(limit_order_intervals_sum <= min_intervals){
            		  min_intervals = limit_order_intervals_sum;   		  
            	  }
            	  if(limit_cancel_intervals_sum <= min_intervals){
            		  min_intervals = limit_cancel_intervals_sum;
            	  }
            	  
            	  next_market_order = false;//次の注文の種類 初期化
              	  next_limit_order = false;//次の注文の種類 初期化
              	  next_limit_cancel = false;//次の注文の種類 初期化
            	  if(min_intervals == market_order_intervals_sum){
            		  next_market_order = true;
            	  }
            	  if(min_intervals == limit_order_intervals_sum){
            		  next_limit_order = true;
            	  }
            	  if(min_intervals == limit_cancel_intervals_sum){
            		  next_limit_cancel = true;
            	  }
            	  
            	  if(next_market_order == true){//次の注文が約定の場合．
            		  bid_prob = ransu.NextUnif();//買い注文・売り注文の決定
            		  
            		  System.out.println(market_order_intervals_sum);
            		  
            		  //-------------（時刻を出力するための行）----------------
            		  hour = (int)(market_order_intervals_sum)/3600;//時間に変換
            		  minute = (int)market_order_intervals_sum % 3600 / 60;//分に変換
            		  second = market_order_intervals_sum % 60;//秒に変換
            		  BigDecimal x4 = new BigDecimal(second);
            		  second = x4.setScale(6, BigDecimal.ROUND_HALF_UP).doubleValue();//秒を四捨五入
            		  if(String.valueOf(hour).length() == 1){
            			  hour_out = 0 + String.valueOf(hour);
            		  }
            		  else{
            			  hour_out = String.valueOf(hour);
            		  }
            		  if(String.valueOf(minute).length() == 1){
            			  minute_out = 0 + String.valueOf(minute);
            		  }
            		  else{
            			  minute_out = String.valueOf(minute);
            		  }
            		  second_out = String.format("%.6f", second);
            		  if(second_out.length() == 8){
            			  second_out = 0 + second_out;
            		  }
            		 
            		  //-------------（時刻を出力するための行）----------------
            		  //System.out.println(hour + ":" + minute + ":" + second);
            		  
            		  second_number = (int)((market_order_intervals_sum - 32400)/900) + 1;
            		  if((day[first_number] == 20070104 || day[first_number] == 20071228) && second_number == 9){
            			  first_number++;
            			  //second_number = 1;
            			  market_order_intervals_sum = 0;
            		  }
            		  else if(second_number == 9){
            			  second_number += 5;
            			  market_order_intervals_sum = 45000;
            			  limit_order_intervals_sum = 45000;
            			  limit_cancel_intervals_sum = 45000;
            		  }
            		  else{
            			  System.out.println((int)((market_order_intervals_sum - 32400)/900) + 1 + "++++" + market_order_intervals_sum);
            			  System.out.println(hour_out + ":" + minute_out + ":" + second_out );
            			  String para_split_simu[] = market_inerval_para[first_number][second_number].split("	", 0);
                		  
                		  
                		  market_order_intervals_sum += cdf.pareto3(ransu.NextUnif(), Double.parseDouble(para_split_simu[0].substring(1)), Double.parseDouble(para_split_simu[1].substring(0,15)));//次の成行注文の到着時間
                		  
            		  }
            		  
            		  if(0.5 < bid_prob){//買い注文
            			  bid_market_order = (int)cdf.negabio(ransu.NextUnif(),2,0.3);
            			  bid_market_order = bid_market_order*(int)cdf.negabio(ransu.NextUnif(),2,0.3);//複合過程
            			  pw.println("Naoki," + day[first_number] + "," + hour_out + ":" + minute_out + ":" + second_out + ",Trade," + ",," + ask_price + "," + bid_market_order + ",,,,,m");       			  
            			  ask_depth -= bid_market_order;
            			  //pw.println("bid Trade," + bid_price + "," + bid_depth + "," + ask_price + "," + ask_depth);
            			  
            			  if(ask_depth <= 0){//板の上昇
            				  ask_depth = 100;
            				  bid_depth = 10;
            				  bid_price += 10;
            				  ask_price += 10;
            			  }
            			  
            		  }
            		  else{//売り注文
            			  ask_market_order = (int)cdf.negabio(ransu.NextUnif(),2,0.3);
            			  ask_market_order = ask_market_order*(int)cdf.negabio(ransu.NextUnif(),2,0.3);//複合過程
            			  pw.println("Naoki," + day[first_number] + "," + hour_out + ":" + minute_out + ":" + second_out + ",Trade," + ",," + bid_price + "," + ask_market_order + ",,,,,m");
            			  bid_depth -= ask_market_order;
            			  //pw.println("ask Trade," + bid_price + "," + bid_depth + "," + ask_price + "," + ask_depth);
            			  
            			  if(bid_depth <= 0){//板の下降
            				  ask_depth = 10;
            				  bid_depth = 100;
            				  bid_price -= 10;
            				  ask_price -= 10;
            			  }
            		  }
            	  }
            	  if(next_limit_order == true){//指値注文の到着
            		  bid_prob = ransu.NextUnif();
            		  
            		  
            		//-------------（時刻を出力するための行）----------------
            		  hour = (int)(limit_order_intervals_sum)/3600;//時間に変換
            		  minute = (int)limit_order_intervals_sum % 3600 / 60;//分に変換
            		  second = limit_order_intervals_sum % 60;//秒に変換
            		  BigDecimal x4 = new BigDecimal(second);
            		  second = x4.setScale(6, BigDecimal.ROUND_HALF_UP).doubleValue();//秒を四捨五入
            		  if(String.valueOf(hour).length() == 1){
            			  hour_out = 0 + String.valueOf(hour);
            		  }
            		  else{
            			  hour_out = String.valueOf(hour);
            		  }
            		  if(String.valueOf(minute).length() == 1){
            			  minute_out = 0 + String.valueOf(minute);
            		  }
            		  else{
            			  minute_out = String.valueOf(minute);
            		  }
            		  second_out = String.format("%.6f", second);
            		  if(second_out.length() == 8){
            			  second_out = 0 + second_out;
            		  }
            		  //-------------（時刻を出力するための行）----------------
            		  
            		  
            		  second_number = (int)((market_order_intervals_sum - 32400)/900) + 1;
            		
            		  if((day[first_number] == 20070104 || day[first_number] == 20071228) && second_number == 9){
            			  first_number++;
            			  //second_number = 1;
            			  market_order_intervals_sum = 0;
            		  }
            		  else if(second_number == 9){
            			  second_number += 5;
            			  market_order_intervals_sum = 45000;
            			  limit_order_intervals_sum = 45000;
            			  limit_cancel_intervals_sum = 45000;
            		  }
            		  else{          			 
            			  limit_order_intervals_sum += cdf.expo(ransu.NextUnif(), 0.96);//次の指値注文の到着時間                		  
            		  }

            		  
            		  if(0.5 < bid_prob){//買い指値注文
            			  bid_limit_order = (int)cdf.negabio(ransu.NextUnif(),2,0.3);
            			  bid_depth += bid_limit_order;
            			  pw.println("Naoki," + day[first_number] + "," + hour_out + ":" + minute_out + ":" + second_out + ",Quote," + ",,,," + bid_price + "," + bid_depth + "," + ask_price + "," + ask_depth +",l");
            			  //pw.println("bid limit," + bid_limit_order + ","+ bid_price + "," + bid_depth + "," + ask_price + "," + ask_depth);
            		  }
            		  else{//売り指値注文
            			  ask_limit_order = (int)cdf.negabio(ransu.NextUnif(),2,0.3);
            			  ask_depth += ask_limit_order;
            			  pw.println("Naoki," + day[first_number] + "," + hour_out + ":" + minute_out + ":" + second_out + ",Quote," + ",,,," + bid_price + "," + bid_depth + "," + ask_price + "," + ask_depth + ",l");
            			  //pw.println("ask limit," + ask_limit_order + ","+ bid_price + "," + bid_depth + "," + ask_price + "," + ask_depth);
            		  }
            	  }
            	  
            	  if(next_limit_cancel == true){//指値キャンセルの到着
            		  bid_prob = ransu.NextUnif();//買い注文・売り注文の決定
            		  
            		  
            		//-------------（時刻を出力するための行）----------------
            		  hour = (int)(limit_cancel_intervals_sum)/3600;//時間に変換
            		  minute = (int)limit_cancel_intervals_sum % 3600 / 60;//分に変換
            		  second = limit_cancel_intervals_sum % 60;//秒に変換
            		  BigDecimal x4 = new BigDecimal(second);
            		  second = x4.setScale(6, BigDecimal.ROUND_HALF_UP).doubleValue();//秒を四捨五入
            		  if(String.valueOf(hour).length() == 1){
            			  hour_out = 0 + String.valueOf(hour);
            		  }
            		  else{
            			  hour_out = String.valueOf(hour);
            		  }
            		  if(String.valueOf(minute).length() == 1){
            			  minute_out = 0 + String.valueOf(minute);
            		  }
            		  else{
            			  minute_out = String.valueOf(minute);
            		  }
            		  second_out = String.format("%.6f", second);
            		  if(second_out.length() == 8){
            			  second_out = 0 + second_out;
            		  }
            		  //-------------（時刻を出力するための行）----------------
            		  
            		  second_number = (int)((market_order_intervals_sum - 32400)/900) + 1;
              		
            		  if((day[first_number] == 20070104 || day[first_number] == 20071228) && second_number == 9){
            			  first_number++;
            			  //second_number = 1;
            			  market_order_intervals_sum = 0;
            		  }
            		  else if(second_number == 9){
            			  second_number += 5;
            			  market_order_intervals_sum = 45000;
            			  limit_order_intervals_sum = 45000;
            			  limit_cancel_intervals_sum = 45000;
            		  }
            		  else{          			 
            			   limit_cancel_intervals_sum += cdf.expo(ransu.NextUnif(), 0.53);//次の指値キャンセルの到着時間  		  
            		  }
            		  
            		  
            		  
            		  if(0.5 < bid_prob){
            			  bid_limit_cancel = (int)cdf.negabio(ransu.NextUnif(),2,0.3);
            			  bid_depth -= bid_limit_cancel;
            			  
            			  
            			  if(bid_depth <= 0){//板の下降
            				  ask_depth = 10;
            				  bid_depth = 100;
            				  bid_price -= 10;
            				  ask_price -= 10;
            			  }
            			  pw.println("Naoki," + day[first_number] + "," + hour_out + ":" + minute_out + ":" + second_out + ",Quote," + ",,,," + bid_price + "," + bid_depth + "," + ask_price + "," + ask_depth + ",c");
            			  //pw.println("bid cancel," + bid_limit_cancel + ","+ bid_price + "," + bid_depth + "," + ask_price + "," + ask_depth);
            			  
            		  }
            		  else{
            			  ask_limit_cancel = (int)cdf.negabio(ransu.NextUnif(),2,0.3);
            			  ask_depth -= ask_limit_cancel;
            			  
            			  if(ask_depth <= 0){//板の上昇
            				  ask_depth = 100;
            				  bid_depth = 10;
            				  bid_price += 10;
            				  ask_price += 10;
            			  }
            			  pw.println("Naoki," + day[first_number] + "," + hour_out + ":" + minute_out + ":" + second_out + ",Quote," + ",,,," + bid_price + "," + bid_depth + "," + ask_price + "," + ask_depth+ ",c");
            			  //pw.println("ask cancel," + ask_limit_cancel + ","+ bid_price + "," + bid_depth + "," + ask_price + "," + ask_depth);
            		  }
            		  
            	  }
            	  
              	
              	
            	  
            	  //System.out.println(next_market_order + "+" + next_limit_order + "+" + next_limit_cancel);
            		  
            	
            	
            	

        	}

            
            pw.close();

        	// txtファイル名を一行ずつロードする



        //}

        
    }
}


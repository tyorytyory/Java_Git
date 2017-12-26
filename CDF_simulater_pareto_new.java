import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.Random;
//注文を発生させるプログラム
public class CDF_simulater_pareto_new{

    public static void main(String[] args) throws IOException{


    	BufferedReader br_first = new BufferedReader(new FileReader("filelist_simu.txt"));//読み取りたいファイル名の記入

    	String txtFileName_first;



    	while((txtFileName_first = br_first.readLine()) != null) {

    		String txtFileName_first_array[] = txtFileName_first.split("_");//filelist_simu.txtで読み込んだファイル名を分割
    		txtFileName_first_array = txtFileName_first_array[1].split("\\.");//filelist_simu.txtで読み込んだファイル名を分割

    		String txtFileName;
    		String filename_constant_market = "";
    		String filename_constant_limit = "";
    		String filename_constant_cancel = "";
            BufferedReader br = new BufferedReader(new FileReader(txtFileName_first));//読み取りたいファイル名の記入
            String filename_pw [] = new String [30];//書き込むファイル名に用いる配列
            String market_intervals_para [] []= new String [500][30];//成行注文，時間間隔のパラメータ
            String limit_intervals_para [] []= new String [500][30];//指値注文，時間間隔のパラメータ
            String cancel_intervals_para [] []= new String [500][30];//指値キャンセル，時間間隔のパラメータ
            String ask_market_volume_para [] []= new String [500][30];//売り成行注文，注文量のパラメータ
            String bid_market_volume_para [] []= new String [500][30];//買い成行注文，注文量のパラメータ
            String ask_limit_volume_para [] []= new String [500][30];//売り指値注文，注文量のパラメータ
            String bid_limit_volume_para [] []= new String [500][30];//買い指値注文，注文量のパラメータ
            String ask_cancel_volume_para [] []= new String [500][30];//売り指値キャンセル，注文量のパラメータ
            String bid_cancel_volume_para [] []= new String [500][30];//買い指値キャンセル，注文量のパラメータ
            String market_volume_same_para [] []= new String [500][30];//成行注文，同一注文のパラメータ
            double market_ask_trade_prob [] [] = new double [500][30];//成行注文の買い注文率
            double limit_ask_trade_prob [] [] = new double [500][30];//指値注文の買い注文率
            double cancel_ask_trade_prob [] [] = new double [500][30];//指値キャンセルの買い注文率
            int down_ask_depth [] [] = new int [500][30];//板が下降した後の売板の枚数
            int down_bid_depth [] [] = new int [500][30];//板が下降した後の買板の枚数
            int up_ask_depth [] [] = new int [500][30];//板が上昇した後の売板の枚数
            int up_bid_depth [] [] = new int [500][30];//板が上昇した後の買板の枚数
            int first_ask_depth [] [] = new int [500][3];//一番最初の売板の枚数
            int first_bid_depth [] [] = new int [500][3];//一番最初の買板の枚数



            String market_interval_pareto3_para [] [] = new String [500][30];//成行注文，時間間隔のパラメータ（パレート３限定）
            int day [] = new int[500];//日付を格納する配列
            int first_number = 1;//２次元配列で用いる関数（１つめ）
            int second_number = 1;//２次元配列で用いる関数（２つめ）

            boolean ita_15min = true;//板の枚数を15分間隔にするか、前場・後場で定数にするか（true→15分、false→定数）



            System.out.println(txtFileName_first);

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
                		if(filename[1].equals("market") && filename[2].equals("intervals")){//成行注文の時間間隔
                			filename_pw[0] = filename[4].substring(0, 4);
                			filename_pw[1] = filename[3];
                			for(second_number = 1;second_number <= (para_split.length-1) ;second_number++){
                				market_intervals_para [first_number][second_number] = para_split[second_number];
                			}
                			day[first_number] = Integer.parseInt(para_split[0]);//このときだけ日付を抽出する．
                			first_number++;
                		}
                		else if(filename[1].equals("limit") && filename[2].equals("intervals")){//指値注文の時間間隔
                			filename_pw[2] = filename[3];
                			for(second_number = 1;second_number <= (para_split.length-1) ;second_number++){
                				limit_intervals_para [first_number][second_number] = para_split[second_number];
                			}
                			first_number++;
                		}
                		else if(filename[1].equals("cancel") && filename[2].equals("intervals")){//指値キャンセルの時間間隔
                			filename_pw[3] = filename[3];
                			for(second_number = 1;second_number <= (para_split.length-1) ;second_number++){
                				cancel_intervals_para [first_number][second_number] = para_split[second_number];
                			}
                			first_number++;
                		}
                		else if(filename[1].equals("market") && filename[2].equals("ask") && filename[3].equals("volume")){//売り成行注文量
                			filename_pw[4] = filename[4];
                			filename_constant_market = filename[5];
                			for(second_number = 1;second_number <= (para_split.length-1) ;second_number++){
                				ask_market_volume_para [first_number][second_number] = para_split[second_number];
                			}
                			first_number++;
                		}
                		else if(filename[1].equals("market") && filename[2].equals("bid") && filename[3].equals("volume")){//買い成行注文量
                			for(second_number = 1;second_number <= (para_split.length-1) ;second_number++){
                				bid_market_volume_para [first_number][second_number] = para_split[second_number];
                			}
                			first_number++;
                		}
                		else if(filename[1].equals("limit") && filename[2].equals("ask") && filename[3].equals("volume")){//売り指値注文量
                			filename_pw[5] = filename[4];
                			filename_constant_limit = filename[5];
                			for(second_number = 1;second_number <= (para_split.length-1) ;second_number++){
                				ask_limit_volume_para [first_number][second_number] = para_split[second_number];
                			}
                			first_number++;
                		}
                		else if(filename[1].equals("limit") && filename[2].equals("bid") && filename[3].equals("volume")){//買い指値注文量
                			for(second_number = 1;second_number <= (para_split.length-1) ;second_number++){
                				bid_limit_volume_para [first_number][second_number] = para_split[second_number];
                			}
                			first_number++;
                		}
                		else if(filename[1].equals("cancel") && filename[2].equals("ask") && filename[3].equals("volume")){//売り指値キャンセル注文量
                			filename_pw[6] = filename[4];
                			filename_constant_cancel = filename[5];
                			for(second_number = 1;second_number <= (para_split.length-1) ;second_number++){
                				ask_cancel_volume_para [first_number][second_number] = para_split[second_number];
                			}
                			first_number++;
                		}
                		else if(filename[1].equals("cancel") && filename[2].equals("bid") && filename[3].equals("volume")){//買い指値キャンセル注文量
                			for(second_number = 1;second_number <= (para_split.length-1) ;second_number++){
                				bid_cancel_volume_para [first_number][second_number] = para_split[second_number];
                			}
                			first_number++;
                		}
                		else if(filename[1].equals("market") && filename[2].equals("volume") && filename[3].equals("same")){//成行注文の同一注文の分布
                			filename_pw[7] = filename[4];
                			//System.out.println("noo");
                			for(second_number = 1;second_number <= (para_split.length-1) ;second_number++){
                				market_volume_same_para [first_number][second_number] = para_split[second_number];
                			}
                			first_number++;
                		}
                		else if(filename[1].equals("market") && filename[2].equals("ask") && filename[3].equals("prob")){//売り成行注文の確率
                			for(second_number = 1;second_number <= (para_split.length-1) ;second_number++){
                				if(second_number == 9){
                					second_number +=6;
                				}
                				market_ask_trade_prob [first_number][second_number] = Double.parseDouble(para_split[second_number]);
                			}
                			first_number++;
                		}
                		else if(filename[1].equals("limit") && filename[2].equals("ask") && filename[3].equals("prob")){//売り指値注文の確率

                			for(second_number = 1;second_number <= (para_split.length-1) ;second_number++){
                				if(second_number == 9){
                					second_number +=6;
                				}
                				limit_ask_trade_prob [first_number][second_number] = Double.parseDouble(para_split[second_number]);
                			}
                			first_number++;
                		}
                		else if(filename[1].equals("cancel") && filename[2].equals("ask") && filename[3].equals("prob")){//売り指値キャンセルの確率

                			for(second_number = 1;second_number <= (para_split.length-1) ;second_number++){
                				if(second_number == 9){
                					second_number +=6;
                				}
                				cancel_ask_trade_prob [first_number][second_number] = Double.parseDouble(para_split[second_number]);
                			}
                			first_number++;
                		}
                		else if(filename[1].equals("depth") && filename[2].equals("info")){//板の厚さの情報(板の枚数を前場・後場で定数にするとき）
                			int depth_number = 1;//下のfor文のためにある変数
                			for(second_number = 1;second_number <= (para_split.length-1) ;second_number+=6){
                				//System.out.println(second_number);
                				down_ask_depth[first_number][depth_number] = (int)Double.parseDouble(para_split[second_number]);
                    			down_bid_depth[first_number][depth_number] = (int)Double.parseDouble(para_split[second_number+1]);
                    			up_ask_depth[first_number][depth_number] = (int)Double.parseDouble(para_split[second_number+2]);
                    			up_bid_depth[first_number][depth_number] = (int)Double.parseDouble(para_split[second_number+3]);
                    			first_ask_depth[first_number][depth_number] = (int)Double.parseDouble(para_split[second_number+4]);
                    			first_bid_depth[first_number][depth_number] = (int)Double.parseDouble(para_split[second_number+5]);
                    			depth_number++;
                			}
                			//System.out.println(first_bid_depth[2][2]);
                			depth_number = 1;
                			first_number++;
                		}
                		else if(filename[1].equals("down") && filename[2].equals("ask") && filename[3].equals("depth")){//板の枚数を15分間隔にするとき（買板の下落）
                			for(second_number = 1;second_number <= (para_split.length-1) ;second_number++){
                				if(second_number == 9){
                					second_number +=6;
                				}
                				down_ask_depth [first_number][second_number] = (int)Double.parseDouble(para_split[second_number]);

                			}
                			first_number++;
                		}
                		else if(filename[1].equals("down") && filename[2].equals("bid") && filename[3].equals("depth")){//板の枚数を15分間隔にするとき（売板の下落）
                			for(second_number = 1;second_number <= (para_split.length-1) ;second_number++){
                				if(second_number == 9){
                					second_number +=6;
                				}
                				down_bid_depth [first_number][second_number] = (int)Double.parseDouble(para_split[second_number]);

                			}
                			first_number++;
                		}
                		else if(filename[1].equals("up") && filename[2].equals("ask") && filename[3].equals("depth")){//板の枚数を15分間隔にするとき（買板の上昇）
                			for(second_number = 1;second_number <= (para_split.length-1) ;second_number++){
                				if(second_number == 9){
                					second_number +=6;
                				}
                				up_ask_depth [first_number][second_number] = (int)Double.parseDouble(para_split[second_number]);

                			}
                			first_number++;
                		}
                		else if(filename[1].equals("up") && filename[2].equals("bid") && filename[3].equals("depth")){//板の枚数を15分間隔にするとき（売板の上昇）
                			for(second_number = 1;second_number <= (para_split.length-1) ;second_number++){
                				if(second_number == 9){
                					second_number +=6;
                				}
                				up_bid_depth [first_number][second_number] = (int)Double.parseDouble(para_split[second_number]);

                			}
                			first_number++;
                		}
                		else if(filename[1].equals("Pareto3")){//成行注文のパレート分布３に限ったver（平均値を用いる）
                			//System.out.println(para_split[1]);
                			market_interval_pareto3_para [first_number][1] = para_split[1];//alpha
                			market_interval_pareto3_para [first_number][2] = para_split[2];//alpha
                			market_interval_pareto3_para [first_number][3] = para_split[3];//alpha
                			market_interval_pareto3_para [first_number][4] = para_split[4];//alpha
                			market_interval_pareto3_para [first_number][5] = para_split[5];//alpha
                			day[first_number] = Integer.parseInt(para_split[0]);
                			first_number++;
                		}
                	}
                }
                brtxt.close();
                fr.close();
        	}
            br.close();

            System.out.println(filename_constant_market);
            System.out.println(filename_constant_limit);
            System.out.println(filename_constant_cancel);


            //書き込みデータの順番
            //年度_成行注文の時間間隔の分布_指値注文の時間間隔の分布_指値キャンセルの時間間隔の分布_成行注文量の分布_指値注文量の分布_指値キャンセルの分布_同一注文の分布

        	/*File file_simu = new File(txtFileName_first_array[0] + "_" + filename_pw[0] + "_" + filename_pw[1] + "_" + filename_pw[2] + "_" + filename_pw[3] + "_" +
         			filename_pw[4] + "_" + filename_pw[5] + "_" + filename_pw[6] + "_" + filename_pw[7] + "_" + "simulater_timeseries.txt");//時系列データの発生
         	PrintWriter pw_simu = new PrintWriter(new BufferedWriter(new FileWriter(file_simu)));*/

         	File file_ita_move = new File(txtFileName_first_array[0] + "_" + filename_pw[0] + "_" + filename_pw[1] + "_" + filename_pw[2] + "_" + filename_pw[3] + "_" +
         			filename_pw[4] + "_" + filename_pw[5] + "_" + filename_pw[6] + "_" + filename_pw[7] + "_" + "simulater_move_data_ita15.txt");//板の移動回数の記録
         	PrintWriter pw_ita_move = new PrintWriter(new BufferedWriter(new FileWriter(file_ita_move)));

         	File file_ita_corr = new File(txtFileName_first_array[0] + "_" + filename_pw[0] + "_" + filename_pw[1] + "_" + filename_pw[2] + "_" + filename_pw[3] + "_" +
         			filename_pw[4] + "_" + filename_pw[5] + "_" + filename_pw[6] + "_" + filename_pw[7] + "_" + "simulater_ita_corr_data_ita15.txt");//板の共分散を算出
         	PrintWriter pw_ita_corr = new PrintWriter(new BufferedWriter(new FileWriter(file_ita_corr)));

         	File file_li_imb = new File(txtFileName_first_array[0] + "_" + filename_pw[0] + "_" + filename_pw[1] + "_" + filename_pw[2] + "_" + filename_pw[3] + "_" +
         			filename_pw[4] + "_" + filename_pw[5] + "_" + filename_pw[6] + "_" + filename_pw[7] + "_" + "simulater_li_imb_data_ita15.txt");//板の共分散を算出
         	PrintWriter pw_li_imb = new PrintWriter(new BufferedWriter(new FileWriter(file_li_imb)));

         	File file_markov = new File(txtFileName_first_array[0] + "_" + filename_pw[0] + "_" + filename_pw[1] + "_" + filename_pw[2] + "_" + filename_pw[3] + "_" +
         			filename_pw[4] + "_" + filename_pw[5] + "_" + filename_pw[6] + "_" + filename_pw[7] + "_" + "simulater_markov_data_ita15.txt");//板の共分散を算出
         	PrintWriter pw_markov = new PrintWriter(new BufferedWriter(new FileWriter(file_markov)));


         	//買い指値注文量の総量_買い指値キャンセル量の総量_買い成行注文量の総量_売り指値注文量の総量_売り指値キャンセル量の総量_売り成行注文量の総量_終値（買板）_初値（買板）_終値（売板）_初値（売板）

         	pw_li_imb.println("day,AM or PM,limit buy order volume,cancel buy order volume,market buy order volume,limit sell order volume,cancel sell order volume,market sell order volume,last price(bid),first price(bid),last price(ask),first price(ask)");

         	pw_markov.println("day,AM or PM,up_up_count,up_down_count,down_up_count,down_down_count,");



            	//String Index;
            	Random rnd = new Random();//Math Random
            	CDF_birth cdf = new CDF_birth();//様々な累積分布関数から時間間隔や注文量を出力するオブジェクト

            	int ask_depth = 0;//売板の累積枚数
            	int bid_depth = 0;//買板の累積枚数
            	int simu_first_ask_price = 20010;//シミュレーション上の最初の売板の値段
            	int simu_first_bid_price = 20000;//シミュレーション上の最初の買板の値段
            	int ask_price = simu_first_ask_price;//最良売気配の価格
            	int bid_price = simu_first_bid_price;//最良買気配の価格
            	int ask_market_order = 0;//売り成行注文量
            	int bid_market_order = 0;//買い成行注文量
            	int ask_limit_order = 0;//売り指値注文量
            	int bid_limit_order = 0;//買い指値注文量
            	int ask_limit_cancel = 0;//売り指値キャンセル量
            	int bid_limit_cancel = 0;//買い指値キャンセル量
            	//double market_order_intervals = 0;//成行注文の注文間隔
            	//double limit_order_intervals = 0;//指値注文の注文間隔
            	//double limit_cancel_intervals = 0;//指値キャンセルの注文間隔
            	double market_order_intervals_sum = 0;//成行注文の注文間隔の累積
            	double limit_order_intervals_sum = 0;//指値注文の注文間隔の累積
            	double limit_cancel_intervals_sum = 0;//指値キャンセルの注文間隔の累積
            	boolean next_market_order = false;//次の注文の種類
            	boolean next_limit_order = false;//次の注文の種類
            	boolean next_limit_cancel = false;//次の注文の種類
            	double ask_prob = 0;//買い注文の確率


            	int hour = 0;//時（出力時に用いる）
            	int minute = 0;//分（出力時に用いる）
            	double second = 0;//秒（出力時に用いる）
            	String hour_out = "";//出力用の時間
            	String minute_out = "";//出力用の時間
            	String second_out = "";//出力用の時間

            	boolean ita_up = false;//板が上昇したもの
            	boolean ita_down = false;//板が下降したもの

            	int ita_up_up_count = 0;//板が上昇した後に上昇するものの数
            	int ita_up_down_count = 0;//板が上昇した後に下降するものの数
            	int ita_down_up_count = 0;//板が下降した後に上昇するものの数
            	int ita_down_down_count = 0;//板が下降した後に下降するものの数


            	//---------------統計量の変数-------------------
            	int count_sell_market_order_volume = 0;//売り成行注文の数
            	int count_buy_market_order_volume = 0;//買い成行注文の数
            	int count_sell_limit_order_volume = 0;//売り指値注文の数
            	int count_buy_limit_order_volume = 0;//買い指値注文の数
            	int count_sell_cancel_order_volume = 0;//売り指値キャンセルの数
            	int count_buy_cancel_order_volume = 0;//買い指値キャンセルの数
            	int first_ask_price = 0;//初値（売板）
            	int first_bid_price = 0;//初値（買板）
            	int up_count = 0;//板が上昇した回数
            	int down_count = 0;//板が下降した回数
            	int max_price = 0;//その日の最高値
            	int min_price = 1000000;//その日の最安値
            	double up_time_sum = 0;//板が上昇した時間の総和
            	double down_time_sum = 0;//板が下落した時間の総和
            	double move_time_check = 0;//板の移動時間算出で用いる変数
            	//---------------統計量の変数-------------------

            	//int output_number = 1;//出力された数（いらない可能性）

            	String para_split_simu [] = new String[3];



            	//System.out.println(y);
            	/*for(int i = 0;i<=0;i++){//テスト用
            		double y = ransu.NextUnif();
            		y = 0.5;
            		//System.out.println(y + "乱数");
                	//double test = cdf.poisson(y,5);
                	//System.out.println(test + "採用");

            	}*/

            	first_number = 1;//二つの配列の１つめ（これでなにから始めるか決まる）

            	while(//first_number ==  227//一部分
            			day[first_number] != 0//最後まで
            			//&& market_order_intervals_sum <= 54000 && limit_order_intervals_sum <= 54000 && limit_cancel_intervals_sum <= 54000
            			){

            		int Sfmt_number = rnd.nextInt(1000);//メルセンヌ・ツイスタの引数
                	Sfmt ransu = new Sfmt(Sfmt_number);//メルセンヌ・ツイスタ乱数．引数を変えることで，乱数の発生パターンを変更


            		if(market_order_intervals_sum == 0 || limit_order_intervals_sum == 0 || limit_cancel_intervals_sum == 0){//初期値の代入
            			//System.out.println(market_interval_pareto3_para[1][5]);

            			ask_depth = first_ask_depth[first_number][1];//最初の売板の枚数（前場）
            			bid_depth = first_bid_depth[first_number][1];//最初の買板の枚数（前場）
            			ask_price = simu_first_ask_price;//最良売気配の価格
                    	bid_price = simu_first_bid_price;//最良買気配の価格
            			//System.out.println(ask_price + "," + bid_price);
            			first_ask_price = ask_price;
            			first_bid_price = bid_price;
            			//System.out.println(ask_depth + ",いた," + bid_depth);


            			para_split_simu = market_intervals_para[first_number][1].split("	", 0);//一般的な分布で用いる
            			if(para_split_simu.length == 1){//指数分布
            				market_order_intervals_sum = 32400 + cdf.expo(ransu.NextUnif(), Double.parseDouble(para_split_simu[0].substring(1)));//指数分布のとき
            			}
            			else if(para_split_simu.length == 2){//パレート分布３
            				market_order_intervals_sum = 32400 + cdf.pareto3(ransu.NextUnif(), Double.parseDouble(para_split_simu[0].substring(1)), Double.parseDouble(para_split_simu[1].substring(0,para_split_simu[1].length()-1)));//成行注文の時間間隔
            			}


                		//market_order_intervals_sum = 32400 + cdf.pareto3(ransu.NextUnif(),Double.parseDouble(market_interval_pareto3_para [first_number][1]),Double.parseDouble(market_interval_pareto3_para [first_number][5]));//成り行き注文のパレートブ分布３のみ

                		para_split_simu = limit_intervals_para[first_number][1].split("	", 0);//一般的な分布で用いる
                		if(para_split_simu.length == 1){//指数分布
                			limit_order_intervals_sum = 32400 + cdf.expo(ransu.NextUnif(), Double.parseDouble(para_split_simu[0].substring(1)));//指数分布のとき
                		}
                		else if(para_split_simu.length == 2){//パレート分布３
                			limit_order_intervals_sum = 32400 + cdf.pareto3(ransu.NextUnif(), Double.parseDouble(para_split_simu[0].substring(1)), Double.parseDouble(para_split_simu[1].substring(0,para_split_simu[1].length()-1)));//指値注文の時間間隔　パレート分布３のとき
                		}
                		//System.out.println(para_split_simu[0] + "lll" + para_split_simu[1]);


                		para_split_simu = cancel_intervals_para[first_number][1].split("	", 0);//一般的な分布で用いる
                		if(para_split_simu.length == 1){//指数分布
                			limit_cancel_intervals_sum = 32400 + cdf.expo(ransu.NextUnif(), Double.parseDouble(para_split_simu[0].substring(1)));//指数分布のとき
                		}
                		else if(para_split_simu.length == 2){//パレート分布３

                			limit_cancel_intervals_sum = 32400 + cdf.pareto3(ransu.NextUnif(), Double.parseDouble(para_split_simu[0].substring(1)), Double.parseDouble(para_split_simu[1].substring(0,para_split_simu[1].length()-1)));//指値キャンセルの時間間隔
                		}


                		//System.out.println(market_order_intervals_sum + "+" + limit_order_intervals_sum + "+" + limit_cancel_intervals_sum);
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

                	  //System.out.println(market_order_intervals_sum + "+" + limit_order_intervals_sum + "+" + limit_cancel_intervals_sum);
                	  //System.out.println("直後" + next_market_order + "+" + next_limit_order + "+" + next_limit_cancel);


                	  if((next_market_order == true && 39600 <= market_order_intervals_sum && market_order_intervals_sum < 45000) ||
                			  (next_limit_order == true && 39600 <= limit_order_intervals_sum && limit_order_intervals_sum < 45000) ||
                			  (next_limit_cancel == true && 39600 <= limit_cancel_intervals_sum && limit_cancel_intervals_sum < 45000)){//昼休み
                		  //System.out.println("OK");

                		  next_market_order = false;
                		  next_limit_order = false;
                		  next_limit_cancel = false;

                		  if((day[first_number] == 20070104 || day[first_number] == 20071228)){//半日オークションの場合
                			  //System.out.println("END");
                			  pw_ita_move.println(day[first_number] + ",morning," + down_count + "," + up_count + "," + (down_time_sum/down_count) + "," + (up_time_sum/up_count) + "," + max_price + "," + min_price);
                			  pw_ita_move.println(day[first_number] + ",afternoon,NaN,NaN");

                			  pw_li_imb.println(day[first_number] + ",morning," + count_buy_limit_order_volume + "," + count_buy_cancel_order_volume + "," + count_buy_market_order_volume + "," +
                					  count_sell_limit_order_volume + "," + count_sell_cancel_order_volume + "," + count_sell_market_order_volume + "," +
                					  bid_price + "," + first_bid_price + "," + ask_price + "," + first_ask_price);

                			  pw_markov.println(day[first_number] + ",morning," + ita_up_up_count + "," + ita_up_down_count + "," + ita_down_up_count + "," + ita_down_down_count);

                			  //System.out.println(day[first_number] + ",morning," + up_count + "," + down_count);
                			  first_number++;
                			  //System.out.println(first_number);
                			  market_order_intervals_sum = 0;
                			  limit_order_intervals_sum = 0;
                			  limit_cancel_intervals_sum = 0;
                			  //System.out.println(day[first_number] + "+" + count_market_order + "+" + count_limit_order + "+" + count_cancel_order);

                			  //-------------初期化----------------
                			  up_count = 0;
                			  down_count = 0;
                			  up_time_sum = 0;
                			  down_time_sum = 0;
                			  count_sell_market_order_volume = 0;//売り成行注文の数
                          	  count_buy_market_order_volume = 0;//買い成行注文の数
                          	  count_sell_limit_order_volume = 0;//売り指値注文の数
                          	  count_buy_limit_order_volume = 0;//買い指値注文の数
                          	  count_sell_cancel_order_volume = 0;//売り指値キャンセルの数
                          	  count_buy_cancel_order_volume = 0;//買い指値キャンセルの数
                          	  ita_up_up_count = 0;
                          	  ita_up_down_count = 0;
                          	  ita_down_up_count = 0;
                          	  ita_down_down_count = 0;
                          	  ita_up = false;
                          	  ita_down = false;
                			  max_price = 0;
                			  min_price = 1000000;
                			//-------------初期化----------------
                		  }
                		  else{//昼休み
                			  //System.out.println(day[first_number]);
                			  pw_ita_move.println(day[first_number] + ",morning," + down_count + "," + up_count + "," + (down_time_sum/down_count) + "," + (up_time_sum/up_count) + "," + max_price + "," + min_price);

                			  pw_li_imb.println(day[first_number] + ",morning," + count_buy_limit_order_volume + "," + count_buy_cancel_order_volume + "," + count_buy_market_order_volume + "," +
                					  count_sell_limit_order_volume + "," + count_sell_cancel_order_volume + "," + count_sell_market_order_volume + "," +
                					  bid_price + "," + first_bid_price + "," + ask_price + "," + first_ask_price);

                			  pw_markov.println(day[first_number] + ",morning," + ita_up_up_count + "," + ita_up_down_count + "," + ita_down_up_count + "," + ita_down_down_count);


                			  //-------------初期化----------------
                			  first_ask_price = ask_price;
                			  first_bid_price = bid_price;
                			  up_count = 0;
                    		  down_count = 0;
                    		  up_time_sum = 0;
                			  down_time_sum = 0;
                			  max_price = 0;
                			  min_price = 1000000;
                			  count_sell_market_order_volume = 0;//売り成行注文の数
                          	  count_buy_market_order_volume = 0;//買い成行注文の数
                          	  count_sell_limit_order_volume = 0;//売り指値注文の数
                          	  count_buy_limit_order_volume = 0;//買い指値注文の数
                          	  count_sell_cancel_order_volume = 0;//売り指値キャンセルの数
                          	  count_buy_cancel_order_volume = 0;//買い指値キャンセルの数
                    		  ask_depth = first_ask_depth[first_number][2];//最初の売板の枚数（後場）
                  			  bid_depth = first_bid_depth[first_number][2];//最初の買板の枚数（後場）
                  			  ita_up_up_count = 0;
                        	  ita_up_down_count = 0;
                        	  ita_down_up_count = 0;
                        	  ita_down_down_count = 0;
                        	  ita_up = false;
                        	  ita_down = false;
                  			//-------------初期化----------------

                  			  //System.out.println(ask_depth + ",いた," + bid_depth);


                			  para_split_simu = market_intervals_para[first_number][15].split("	", 0);//一般的な分布で用いる（成り行き注文）
                			  if(para_split_simu.length == 1){//指数分布
                				market_order_intervals_sum = 45000 + cdf.expo(ransu.NextUnif(), Double.parseDouble(para_split_simu[0].substring(1)));//指数分布のとき
                			  }
                			  else if(para_split_simu.length == 2){//パレート分布３
                				market_order_intervals_sum = 45000 + cdf.pareto3(ransu.NextUnif(), Double.parseDouble(para_split_simu[0].substring(1)), Double.parseDouble(para_split_simu[1].substring(0,15)));//一般的な分布で用いる（成り行き注文） ;
                			  }


                    		  para_split_simu = limit_intervals_para[first_number][15].split("	", 0);
                    		  if(para_split_simu.length == 1){//指数分布
                    			 limit_order_intervals_sum = 45000 + cdf.expo(ransu.NextUnif(), Double.parseDouble(para_split_simu[0].substring(1)));//指数分布のとき
                			  }
                			  else if(para_split_simu.length == 2){//パレート分布３
                				limit_order_intervals_sum = 45000 + cdf.pareto3(ransu.NextUnif(), Double.parseDouble(para_split_simu[0].substring(1)), Double.parseDouble(para_split_simu[1].substring(0,para_split_simu[1].length()-1)));//パレート分布Ⅲのとき
                			  }


                    		  para_split_simu = cancel_intervals_para[first_number][15].split("	", 0);
                    		  if(para_split_simu.length == 1){//指数分布
                    			 limit_cancel_intervals_sum = 45000 + cdf.expo(ransu.NextUnif(), Double.parseDouble(para_split_simu[0].substring(1)));//指数分布のとき
                			  }
                			  else if(para_split_simu.length == 2){//パレート分布３
                				limit_cancel_intervals_sum = 45000 + cdf.pareto3(ransu.NextUnif(), Double.parseDouble(para_split_simu[0].substring(1)), Double.parseDouble(para_split_simu[1].substring(0,para_split_simu[1].length() - 1)));//パレート分布３のとき
                			  }



                		  }
                	  }

                	  if((next_market_order == true && 54000 <= market_order_intervals_sum) ||
                			  (next_limit_order == true && 54000 <= limit_order_intervals_sum) ||
                			  (next_limit_cancel == true && 54000 <= limit_cancel_intervals_sum)){//取引の終了(15:00)
                		  //System.out.println(market_order_intervals_sum + "+" + limit_order_intervals_sum + "+" + limit_cancel_intervals_sum);
                		  //System.out.println(next_market_order + "+" + next_limit_order + "+" + next_limit_cancel);
                		  //System.out.println(day[first_number] + "+" + count_market_order + "+" + count_limit_order + "+" + count_cancel_order);
                		  pw_ita_move.println(day[first_number] + ",afternoon," + down_count + "," + up_count + "," + (down_time_sum/down_count) + "," + (up_time_sum/up_count) + "," + max_price + "," + min_price);
                		  //System.out.println(day[first_number] + ",afternooon," + up_count + "," + down_count);

                		  pw_li_imb.println(day[first_number] + ",afternoon," + count_buy_limit_order_volume + "," + count_buy_cancel_order_volume + "," + count_buy_market_order_volume + "," +
            					  count_sell_limit_order_volume + "," + count_sell_cancel_order_volume + "," + count_sell_market_order_volume + "," +
            					  bid_price + "," + first_bid_price + "," + ask_price + "," + first_ask_price);


                		  pw_markov.println(day[first_number] + ",afternoon," + ita_up_up_count + "," + ita_up_down_count + "," + ita_down_up_count + "," + ita_down_down_count);


                		//-------------初期化----------------
                		  up_count = 0;
                		  down_count = 0;
                		  count_sell_market_order_volume = 0;//売り成行注文の数
                      	  count_buy_market_order_volume = 0;//買い成行注文の数
                      	  count_sell_limit_order_volume = 0;//売り指値注文の数
                      	  count_buy_limit_order_volume = 0;//買い指値注文の数
                      	  count_sell_cancel_order_volume = 0;//売り指値キャンセルの数
                      	  count_buy_cancel_order_volume = 0;//買い指値キャンセルの数
            			  up_time_sum = 0;
            			  down_time_sum = 0;
            			  max_price = 0;
            			  min_price = 1000000;
            			  market_order_intervals_sum = 0;
                		  limit_order_intervals_sum = 0;
                		  limit_cancel_intervals_sum = 0;
                		  next_market_order = false;
                		  next_limit_order = false;
                		  next_limit_cancel = false;
                		  ita_up_up_count = 0;
                    	  ita_up_down_count = 0;
                    	  ita_down_up_count = 0;
                    	  ita_down_down_count = 0;
                    	  ita_up = false;
                    	  ita_down = false;
            			//-------------初期化----------------
                		  first_number++;
                		  /*if(first_number == 227){//検証するデータのジャンプ（20071203）
                			  pw_ita_move.println(day[first_number] + ",morning,NaN,NaN");
                			  pw_ita_move.println(day[first_number] + ",afternoon,NaN,NaN");
                			  //pw_ita_corr.println(day[first_number] + ",0,0");
                			  first_number++;
                		  }*/

                	  }

                	  second_number = (int)((min_intervals - 32400)/900) + 1;

                	  if(next_market_order == true){//次の注文が約定の場合．
                		  ask_prob = ransu.NextUnif();//買い注文・売り注文の決定




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

                		  //---------成行注文の一般的なパラメータ----------




                		//---------成行注文の一般的なパラメータ----------

                		//---------成行注文のパレート分布３----------

                		  /*if(market_order_intervals_sum < 33300){
                			  market_order_intervals_sum += cdf.pareto3(ransu.NextUnif(),Double.parseDouble(market_interval_pareto3_para [first_number][1]),Double.parseDouble(market_interval_pareto3_para [first_number][5]));//成り行き注文のパレートブ分布３のみ
                			  System.out.println(market_order_intervals_sum);
                		  }
                		  else if(33300 <= market_order_intervals_sum && market_order_intervals_sum < 34200){
                			  market_order_intervals_sum += cdf.pareto3(ransu.NextUnif(),Double.parseDouble(market_interval_pareto3_para [first_number][2]),Double.parseDouble(market_interval_pareto3_para [first_number][5]));//成り行き注文のパレートブ分布３のみ
                			  System.out.println(market_order_intervals_sum);
                		  }
                		  else if((day[first_number] == 20070104 || day[first_number] == 20071228) && 39600 <= market_order_intervals_sum){
                			  first_number++;
                			  market_order_intervals_sum = 0;
                		  }
                		  else if(39600 <= market_order_intervals_sum && market_order_intervals_sum < 45000){
                			  market_order_intervals_sum = 45000;
                			  limit_order_intervals_sum = 45000;
                			  limit_cancel_intervals_sum = 45000;
                		  }
                		  else if((34200 <= market_order_intervals_sum && market_order_intervals_sum < 39600) || (45000 <= market_order_intervals_sum && market_order_intervals_sum < 53100)){
                			  market_order_intervals_sum += cdf.pareto3(ransu.NextUnif(),Double.parseDouble(market_interval_pareto3_para [first_number][3]),Double.parseDouble(market_interval_pareto3_para [first_number][5]));//成り行き注文のパレートブ分布３のみ
                			  System.out.println(market_order_intervals_sum);
                		  }
                		  else if(53100 <= market_order_intervals_sum){
                			  market_order_intervals_sum += cdf.pareto3(ransu.NextUnif(),Double.parseDouble(market_interval_pareto3_para [first_number][4]),Double.parseDouble(market_interval_pareto3_para [first_number][5]));//成り行き注文のパレートブ分布３のみ
                		  }*/
                		//---------成行注文のパレート分布３----------


                		  if(ask_prob < market_ask_trade_prob [first_number][second_number]){//売り注文
                			  para_split_simu = ask_market_volume_para[first_number][second_number].split("	", 0);
                    		  if(filename_constant_market.equals("constant.csv")){//定数
                    			  //System.out.println(para_split_simu[0].substring(1,para_split_simu[0].length()));
                    			  ask_market_order = Integer.parseInt(para_split_simu[0].substring(1,para_split_simu[0].length()));
                    		  }
                    		  else if(para_split_simu.length == 1){//ゼータ分布

                    			  ask_market_order = (int)cdf.zeta(ransu.NextUnif(), Double.parseDouble(para_split_simu[0].substring(1)));//ゼータ分布のとき
                    			  para_split_simu = market_volume_same_para[first_number][second_number].split("	", 0);//複合過程
                    			  ask_market_order = ask_market_order*(int)cdf.negabio(ransu.NextUnif(),Double.parseDouble(para_split_simu[0].substring(1)),Double.parseDouble(para_split_simu[1].substring(0,para_split_simu[1].length()-1)));//複合過程

                    		  }
                    		  else if(para_split_simu.length == 2){//負の二項分布
                    			  //System.out.println(para_split_simu);
                    			  ask_market_order = (int)cdf.negabio(ransu.NextUnif(),Double.parseDouble(para_split_simu[0].substring(1)),Double.parseDouble(para_split_simu[1].substring(0,para_split_simu[1].length()-1)));
                    			  para_split_simu = market_volume_same_para[first_number][second_number].split("	", 0);//複合過程
                    			  ask_market_order = ask_market_order*(int)cdf.negabio(ransu.NextUnif(),Double.parseDouble(para_split_simu[0].substring(1)),Double.parseDouble(para_split_simu[1].substring(0,para_split_simu[1].length()-1)));//複合過程

                    		  }
                			  //ask_market_order = (int)cdf.negabio(ransu.NextUnif(),2,0.3);

                			  count_sell_market_order_volume += ask_market_order;//売り成行注量文のカウント

                			  //pw_simu.println("Naoki," + day[first_number] + "," + hour_out + ":" + minute_out + ":" + second_out + ",Trade," + ",," + bid_price + "," + ask_market_order + ",,,,,m");
                			  bid_depth -= ask_market_order;
                			  //pw_simu.println("ask Trade," + bid_price + "," + bid_depth + "," + ask_price + "," + ask_depth);

                			  if(bid_depth <= 0){//板の下降
                				  down_count++;
                				  if(ita_15min == false){//板の枚数が前場・後場で定数
                					  if(second_number <= 8){//前場の板が移動した後の板の厚み
                    					  ask_depth = down_ask_depth[first_number][1];
                    					  bid_depth = down_bid_depth[first_number][1];
                    					  //System.out.println(ask_depth + "," + bid_depth);
                    				  }
                    				  else{//後場の板が移動した後の板の厚み
                    					  ask_depth = down_ask_depth[first_number][2];
                    					  bid_depth = down_bid_depth[first_number][2];
                    					  //System.out.println(ask_depth + "," + bid_depth);
                    				  }
                				  }
                				  else if(ita_15min == true){//板の枚数が15分間隔で定数
                					  ask_depth = down_ask_depth[first_number][second_number];
                					  bid_depth = down_bid_depth[first_number][second_number];
                				  }

                				  if(up_time_sum == 0 && down_time_sum == 0){//板の移動時間の計算
                					  down_time_sum += market_order_intervals_sum;
                					  move_time_check = market_order_intervals_sum;
                				  }
                				  else{//板の移動時間の計算
                					  down_time_sum += (market_order_intervals_sum - move_time_check);
                					  move_time_check = market_order_intervals_sum;
                				  }

                				  //遷移確率の計算
                				  if(ita_up == false && ita_down == false){//初期値がどちらもfalse
                					  ita_down = true;
                				  }
                				  else if(ita_up == true){//ita_down_up
                					  ita_up_down_count++;
                					  ita_up = false;
                					  ita_down = true;
                				  }
                				  else if(ita_down == true){//ita_down_down
                					  ita_down_down_count++;
                					  ita_up = false;
                					  ita_down = true;
                				  }



                				  pw_ita_corr.println(day[first_number] + "," + hour_out + ":" + minute_out + ":" + second_out + ",-1");//板の移動（板の共分散）
                				  bid_price -= 10;
                				  ask_price -= 10;

                				  if(ask_price < min_price){//最安値
                					  min_price = ask_price;
                				  }
                			  }


                		  }
                		  else{//買い注文



                			  //System.out.println(day[first_number]);
                			  para_split_simu = bid_market_volume_para[first_number][second_number].split("	", 0);
                			  if(filename_constant_market.equals("constant.csv")){//定数
                    			  bid_market_order = Integer.parseInt(para_split_simu[0].substring(1,para_split_simu[0].length()));
                    		  }
                			  else if(para_split_simu.length == 1){//ゼータ分布

                    			  bid_market_order = (int)cdf.zeta(ransu.NextUnif(), Double.parseDouble(para_split_simu[0].substring(1)));//ゼータ分布のとき
                    			  para_split_simu = market_volume_same_para[first_number][second_number].split("	", 0);//複合過程
                        		  bid_market_order = bid_market_order*(int)cdf.negabio(ransu.NextUnif(),Double.parseDouble(para_split_simu[0].substring(1)),Double.parseDouble(para_split_simu[1].substring(0,para_split_simu[1].length()-1)));//複合過程

                    		  }
                    		  else if(para_split_simu.length == 2){//負の二項分布

                    			  bid_market_order = (int)cdf.negabio(ransu.NextUnif(),Double.parseDouble(para_split_simu[0].substring(1)),Double.parseDouble(para_split_simu[1].substring(0,para_split_simu[1].length()-1)));
                    			  para_split_simu = market_volume_same_para[first_number][second_number].split("	", 0);//複合過程
                        		  bid_market_order = bid_market_order*(int)cdf.negabio(ransu.NextUnif(),Double.parseDouble(para_split_simu[0].substring(1)),Double.parseDouble(para_split_simu[1].substring(0,para_split_simu[1].length()-1)));//複合過程

                    		  }

                    		  count_buy_market_order_volume += bid_market_order;//買い成行注量文のカウント

                			  //bid_market_order = bid_market_order*(int)cdf.negabio(ransu.NextUnif(),2,0.3);//複合過程
                			  //pw_simu.println("Naoki," + day[first_number] + "," + hour_out + ":" + minute_out + ":" + second_out + ",Trade," + ",," + ask_price + "," + bid_market_order + ",,,,,m");
                			  ask_depth -= bid_market_order;
                			  //pw_simu.println("bid Trade," + bid_price + "," + bid_depth + "," + ask_price + "," + ask_depth);

                			  if(ask_depth <= 0){//板の上昇
                				  up_count++;

                				  if(ita_15min == false){//板の枚数が前場・後場で定数
                					  if(second_number <= 8){//前場の板が移動した後の板の厚み
                    					  ask_depth = up_ask_depth[first_number][1];
                    					  bid_depth = up_bid_depth[first_number][1];
                    				  }
                    				  else{//後場の板が移動した後の板の厚み
                    					  ask_depth = up_ask_depth[first_number][2];
                    					  bid_depth = up_bid_depth[first_number][2];
                    				  }
                				  }
                				  else if(ita_15min == true){//板の枚数が15分間隔で定数
                					  ask_depth = up_ask_depth[first_number][second_number];
                					  bid_depth = up_bid_depth[first_number][second_number];
                				  }

                				  if(up_time_sum == 0 && down_time_sum == 0){//板の移動時間の計算
                					  up_time_sum += market_order_intervals_sum;
                					  move_time_check = market_order_intervals_sum;
                				  }
                				  else{//板の移動時間の計算
                					  up_time_sum += (market_order_intervals_sum - move_time_check);
                					  move_time_check = market_order_intervals_sum;
                				  }

                				//遷移確率の計算
                				  if(ita_up == false && ita_down == false){//初期値がどちらもfalse
                					  ita_up = true;
                				  }
                				  else if(ita_up == true){//ita_down_up
                					  ita_up_up_count++;
                					  ita_down = false;
                					  ita_up = true;
                				  }
                				  else if(ita_down == true){//ita_down_down
                					  ita_down_up_count++;
                					  ita_down = false;
                					  ita_up = true;
                				  }


                				  pw_ita_corr.println(day[first_number] + "," + hour_out + ":" + minute_out + ":" + second_out + ",+1");//板の移動（板の共分散）
                				  bid_price += 10;
                				  ask_price += 10;

                				  if(max_price < ask_price){//最高値
                					  max_price = ask_price;
                				  }

                			  }
                		  }
                		//System.out.println((int)((market_order_intervals_sum - 32400)/900) + 1 + "++++" + market_order_intervals_sum+ "++++" + second_number);
                		  //System.out.println(hour_out + ":" + minute_out + ":" + second_out );
                		  para_split_simu = market_intervals_para[first_number][second_number].split("	", 0);
                		  if(para_split_simu.length == 1){//指数分布

                			  market_order_intervals_sum += cdf.expo(ransu.NextUnif(), Double.parseDouble(para_split_simu[0].substring(1)));//指数分布のとき
                		  }
                		  else if(para_split_simu.length == 2){//パレート分布３

                			market_order_intervals_sum += cdf.pareto3(ransu.NextUnif(), Double.parseDouble(para_split_simu[0].substring(1)), Double.parseDouble(para_split_simu[1].substring(0,para_split_simu[1].length()-1)));//次の成行注文の到着時間
                		  }


                	  }
                	  if(next_limit_order == true){//指値注文の到着
                		  ask_prob = ransu.NextUnif();


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


                		  if(ask_prob < limit_ask_trade_prob [first_number][second_number]){//売り指値注文
                			  para_split_simu = ask_limit_volume_para[first_number][second_number].split("	", 0);
                			  if(filename_constant_limit.equals("constant.csv")){//定数
                				  ask_limit_order = Integer.parseInt(para_split_simu[0].substring(1,para_split_simu[0].length()));
                    		  }
                			  else if(para_split_simu.length == 1){//ゼータ分布

                    			  ask_limit_order = (int)cdf.zeta(ransu.NextUnif(), Double.parseDouble(para_split_simu[0].substring(1)));//ゼータ分布のとき
                    		  }
                    		  else if(para_split_simu.length == 2){//負の二項分布

                    			  ask_limit_order = (int)cdf.negabio(ransu.NextUnif(),Double.parseDouble(para_split_simu[0].substring(1)),Double.parseDouble(para_split_simu[1].substring(0,para_split_simu[1].length()-1)));
                    		  }



                    		  count_sell_limit_order_volume += ask_limit_order;//売り指値注文量のカウント

                			  //ask_limit_order = (int)cdf.negabio(ransu.NextUnif(),2,0.3);
                			  ask_depth += ask_limit_order;
                			  //pw_simu.println("Naoki," + day[first_number] + "," + hour_out + ":" + minute_out + ":" + second_out + ",Quote," + ",,,," + bid_price + "," + bid_depth + "," + ask_price + "," + ask_depth + ",l");
                			  //pw_simu.println("ask limit," + ask_limit_order + ","+ bid_price + "," + bid_depth + "," + ask_price + "," + ask_depth);

                		  }
                		  else{//買い指値注文
                			  para_split_simu = bid_limit_volume_para[first_number][second_number].split("	", 0);
                			  if(filename_constant_limit.equals("constant.csv")){//定数
                				  bid_limit_order = Integer.parseInt(para_split_simu[0].substring(1,para_split_simu[0].length()));
                    		  }
                			  else if(para_split_simu.length == 1){//ゼータ分布

                    			  bid_limit_order = (int)cdf.zeta(ransu.NextUnif(), Double.parseDouble(para_split_simu[0].substring(1)));//ゼータ分布のとき
                    		  }
                    		  else if(para_split_simu.length == 2){//負の二項分布

                    			  bid_limit_order = (int)cdf.negabio(ransu.NextUnif(),Double.parseDouble(para_split_simu[0].substring(1)),Double.parseDouble(para_split_simu[1].substring(0,para_split_simu[1].length()-1)));
                    		  }



                    		  count_buy_limit_order_volume += bid_limit_order;//買い指値注文量のカウント

                			  //bid_limit_order = (int)cdf.negabio(ransu.NextUnif(),2,0.3);
                			  bid_depth += bid_limit_order;
                			  //pw_simu.println("Naoki," + day[first_number] + "," + hour_out + ":" + minute_out + ":" + second_out + ",Quote," + ",,,," + bid_price + "," + bid_depth + "," + ask_price + "," + ask_depth +",l");
                			  //pw_simu.println("bid limit," + bid_limit_order + ","+ bid_price + "," + bid_depth + "," + ask_price + "," + ask_depth);

                		  }



                		  para_split_simu = limit_intervals_para[first_number][second_number].split("	", 0);//次の注文の時間の計算
                		  if(para_split_simu.length == 1){//指数分布
                			  limit_order_intervals_sum += cdf.expo(ransu.NextUnif(), Double.parseDouble(para_split_simu[0].substring(1)));//指数分布のとき
                		  }
                		  else if(para_split_simu.length == 2){//パレート分布3
                			  limit_order_intervals_sum += cdf.pareto3(ransu.NextUnif(), Double.parseDouble(para_split_simu[0].substring(1)), Double.parseDouble(para_split_simu[1].substring(0,para_split_simu[1].length()-1)));//パレート分布３ののとき
                		  }
                	  }

                	  if(next_limit_cancel == true){//指値キャンセルの到着
                		  ask_prob = ransu.NextUnif();//買い注文・売り注文の決定


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


                		  if(ask_prob < cancel_ask_trade_prob [first_number][second_number]){//売り指値キャンセル
                			  para_split_simu = ask_cancel_volume_para[first_number][second_number].split("	", 0);
                			  if(filename_constant_cancel.equals("constant.csv")){//定数
                				  ask_limit_cancel = Integer.parseInt(para_split_simu[0].substring(1,para_split_simu[0].length()));
                    		  }
                			  else if(para_split_simu.length == 1){//ゼータ分布

                    			  ask_limit_cancel = (int)cdf.zeta(ransu.NextUnif(), Double.parseDouble(para_split_simu[0].substring(1)));//ゼータ分布のとき
                    		  }
                    		  else if(para_split_simu.length == 2){//負の二項分布

                    			  ask_limit_cancel = (int)cdf.negabio(ransu.NextUnif(),Double.parseDouble(para_split_simu[0].substring(1)),Double.parseDouble(para_split_simu[1].substring(0,para_split_simu[1].length()-1)));
                    		  }



                    		  count_sell_cancel_order_volume += ask_limit_cancel;//売り指値キャンセル量のカウント

                			  //ask_limit_cancel = (int)cdf.negabio(ransu.NextUnif(),2,0.3);
                			  ask_depth -= ask_limit_cancel;

                			  if(ask_depth <= 0){//板の上昇
                				  up_count++;

                				  if(ita_15min == false){//板の枚数が前場・後場で定数
                					  if(second_number <= 8){//前場の板が移動した後の板の厚み
                    					  ask_depth = up_ask_depth[first_number][1];
                    					  bid_depth = up_bid_depth[first_number][1];
                    				  }
                    				  else{//後場の板が移動した後の板の厚み
                    					  ask_depth = up_ask_depth[first_number][2];
                    					  bid_depth = up_bid_depth[first_number][2];
                    				  }
                				  }
                				  else if(ita_15min == true){//板の枚数が15分間隔で定数
                					  ask_depth = up_ask_depth[first_number][second_number];
                					  bid_depth = up_bid_depth[first_number][second_number];
                				  }

                				  if(up_time_sum == 0 && down_time_sum == 0){//板の移動時間の計算
                					  up_time_sum += limit_cancel_intervals_sum;
                					  move_time_check = limit_cancel_intervals_sum;
                				  }
                				  else{//板の移動時間の計算
                					  up_time_sum += (limit_cancel_intervals_sum - move_time_check);
                					  move_time_check = limit_cancel_intervals_sum;
                				  }

                				  //遷移確率の計算
                				  if(ita_up == false && ita_down == false){//初期値がどちらもfalse
                					  ita_up = true;
                				  }
                				  else if(ita_up == true){//ita_down_up
                					  ita_up_up_count++;
                					  ita_down = false;
                					  ita_up = true;
                				  }
                				  else if(ita_down == true){//ita_down_down
                					  ita_down_up_count++;
                					  ita_down = false;
                					  ita_up = true;
                				  }

                				  pw_ita_corr.println(day[first_number] + "," + hour_out + ":" + minute_out + ":" + second_out + ",+1");//板の移動（板の共分散）

                				  bid_price += 10;
                				  ask_price += 10;

                				  if(max_price < ask_price){//最高値
                					  max_price = ask_price;
                				  }
                			  }
                			  //pw_simu.println("Naoki," + day[first_number] + "," + hour_out + ":" + minute_out + ":" + second_out + ",Quote," + ",,,," + bid_price + "," + bid_depth + "," + ask_price + "," + ask_depth+ ",c");
                			  //pw_simu.println("ask cancel," + ask_limit_cancel + ","+ bid_price + "," + bid_depth + "," + ask_price + "," + ask_depth);


                		  }
                		  else{//買い指値キャンセル
                			  para_split_simu = bid_cancel_volume_para[first_number][second_number].split("	", 0);
                			  if(filename_constant_cancel.equals("constant.csv")){//定数
                				  bid_limit_cancel = Integer.parseInt(para_split_simu[0].substring(1,para_split_simu[0].length()));
                    		  }
                			  else if(para_split_simu.length == 1){//ゼータ分布

                    			  bid_limit_cancel = (int)cdf.zeta(ransu.NextUnif(), Double.parseDouble(para_split_simu[0].substring(1)));//ゼータ分布のとき
                    		  }
                    		  else if(para_split_simu.length == 2){//負の二項分布

                    			  bid_limit_cancel = (int)cdf.negabio(ransu.NextUnif(),Double.parseDouble(para_split_simu[0].substring(1)),Double.parseDouble(para_split_simu[1].substring(0,para_split_simu[1].length()-1)));
                    		  }



                    		  count_buy_cancel_order_volume += bid_limit_cancel;//売り指値キャンセル量のカウント

                			  bid_depth -= bid_limit_cancel;


                			  if(bid_depth <= 0){//板の下降
                				  down_count++;



                				  if(ita_15min == false){//板の枚数が前場・後場で定数
                					  if(second_number <= 8){//前場の板が移動した後の板の厚み
                    					  ask_depth = down_ask_depth[first_number][1];
                    					  bid_depth = down_bid_depth[first_number][1];
                    					  //System.out.println(ask_depth + "," + bid_depth);
                    				  }
                    				  else{//後場の板が移動した後の板の厚み
                    					  ask_depth = down_ask_depth[first_number][2];
                    					  bid_depth = down_bid_depth[first_number][2];
                    					  //System.out.println(ask_depth + "," + bid_depth);
                    				  }
                				  }
                				  else if(ita_15min == true){//板の枚数が15分間隔で定数
                					  ask_depth = down_ask_depth[first_number][second_number];
                					  bid_depth = down_bid_depth[first_number][second_number];
                				  }

                				  if(up_time_sum == 0 && down_time_sum == 0){//板の移動時間の計算
                					  down_time_sum += limit_cancel_intervals_sum;
                					  move_time_check = limit_cancel_intervals_sum;
                				  }
                				  else{//板の移動時間の計算
                					  down_time_sum += (limit_cancel_intervals_sum - move_time_check);
                					  move_time_check = limit_cancel_intervals_sum;
                				  }

                				  //遷移確率の計算
                				  if(ita_up == false && ita_down == false){//初期値がどちらもfalse
                					  ita_down = true;
                				  }
                				  else if(ita_up == true){//ita_down_up
                					  ita_up_down_count++;
                					  ita_up = false;
                					  ita_down = true;
                				  }
                				  else if(ita_down == true){//ita_down_down
                					  ita_down_down_count++;
                					  ita_up = false;
                					  ita_down = true;
                				  }

                				  pw_ita_corr.println(day[first_number] + "," + hour_out + ":" + minute_out + ":" + second_out + ",-1");//板の移動（板の共分散）

                				  bid_price -= 10;
                				  ask_price -= 10;

                				  if(ask_price < min_price){//最安値
                					  min_price = ask_price;
                				  }

                			  }
                			  //pw_simu.println("Naoki," + day[first_number] + "," + hour_out + ":" + minute_out + ":" + second_out + ",Quote," + ",,,," + bid_price + "," + bid_depth + "," + ask_price + "," + ask_depth + ",c");
                			  //pw_simu.println("bid cancel," + bid_limit_cancel + ","+ bid_price + "," + bid_depth + "," + ask_price + "," + ask_depth);


                		  }



                		//second_number = (int)((market_order_intervals_sum - 32400)/900) + 1;


                		  //limit_cancel_intervals_sum += cdf.expo(ransu.NextUnif(), 0.53);//次の指値キャンセルの到着時間

                		  //second_number = (int)((limit_cancel_intervals_sum - 32400)/900) + 1;
                		  //System.out.println((int)((limit_cancel_intervals_sum - 32400)/900) + 1 + "++++" + limit_cancel_intervals_sum+ "++++" + second_number);
                		  //System.out.println(hour_out + ":" + minute_out + ":" + second_out );
                		  //System.out.println(cancel_intervals_para[first_number][second_number] + "ijioi" + second_number);
                		  para_split_simu = cancel_intervals_para[first_number][second_number].split("	", 0);

                		  if(para_split_simu.length == 1){//指数分布
                			  limit_cancel_intervals_sum += cdf.expo(ransu.NextUnif(), Double.parseDouble(para_split_simu[0].substring(1)));//指数分布のとき
                		  }
                		  else if(para_split_simu.length == 2){//パレート分布3
                			  limit_cancel_intervals_sum += cdf.pareto3(ransu.NextUnif(), Double.parseDouble(para_split_simu[0].substring(1)), Double.parseDouble(para_split_simu[1].substring(0,para_split_simu[1].length()-1)));//次の成行注文の到着時間
                		  }


                	  }


            	}//while文の終わり

            	//pw_simu.close();
            	pw_ita_move.close();
            	pw_ita_corr.close();
            	pw_li_imb.close();
            	pw_markov.close();
    	}

    	br_first.close();
    }


}


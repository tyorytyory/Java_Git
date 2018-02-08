import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class depth_matrix{
	//JNIc_limit_order.javaから作成したdepthファイルから、板の移動回数や最初の板の枚数を計算するプログラム
	////(inlcude only)は「買板だけ下落し、その後売板だけが下落する」「売板だけ上昇し、その後買板だけが上昇する(inlcude only)」を考慮したいとき、if文を挿入する。

	public static void main(String[] args) throws IOException{

		BufferedReader br = new BufferedReader(new FileReader("filelist_depth.txt"));//読み取りたいファイル名の記入
		String txtFileName;


		double hour = 0;//時間
		double minute = 0;//分
		double second = 0;//秒
		double time_total = 0;//累積時間（秒）





		while((txtFileName = br.readLine()) != null) {

			String day = null;



			FileReader fr = new FileReader(txtFileName);
			BufferedReader brtxt = new BufferedReader(fr);
			String line ="";

			String[] filename = txtFileName.split("\\.");

			File file = new File(filename[0] + "_matrix.csv");
			PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(file)));

			pw.println("day,divide,count_up_up,count_up_down,count_down_up,count_down_down,"+
					"up_up_move_before_market_order,up_up_move_before_bid_depth,up_up_move_before_ask_depth,up_up_move_time," +
					"up_down_move_before_market_order,up_down_move_before_bid_depth,up_down_move_before_ask_depth,up_down_move_time,"+
					"down_up_move_before_market_order,down_up_move_before_bid_depth,down_up_move_before_ask_depth,down_up_move_time,"+
					"down_down_move_before_market_order,down_down_move_before_bid_depth,down_down_move_before_ask_depth,down_down_move_time,Trade_count,not_Trade_count,,");

			String  JNIc_split[] = null;

			int before_move_condition = 0;//直前の板の移動の情報を書き込む
			int count_up_up = 0;//板が上昇し、その後も上昇する回数
			int count_up_down = 0;//板が上昇し、その後は下落する回数
			int count_down_down = 0;//板が下落し、その後も下落する回数
			int count_down_up = 0;//板が下落し、その後は上昇する回数

			double up_up_move_before_bid_depth = 0;//板がup,upしたときの、移動する直前の買板の厚み
			double up_up_move_before_ask_depth = 0;//板がup,upしたときの、移動する直前の売板の厚み
			double up_up_move_before_market_order= 0;//板がup,upしたときの、移動する直前の約定枚数
			double up_up_move_time = 0.0;////板がup,upするまでの移動時間
			int count_up_up_market = 0;//約定により板がup,upした回数

			double up_down_move_before_bid_depth = 0;//板がup,downしたときの、移動する直前の買板の厚み
			double up_down_move_before_ask_depth = 0;//板がup,downしたときの、移動する直前の売板の厚み
			double up_down_move_before_market_order= 0;//板がup,downしたときの、移動する直前の約定枚数
			double up_down_move_time = 0.0;////板がup,downするまでの移動時間
			int count_up_down_market = 0;//約定により板がup,downした回数

			double down_up_move_before_bid_depth = 0;//板がdown,upしたときの、移動する直前の買板の厚み
			double down_up_move_before_ask_depth = 0;//板がdown,upしたときの、移動する直前の売板の厚み
			double down_up_move_before_market_order= 0;//板がdown,upしたときの、移動する直前の約定枚数
			double down_up_move_time = 0.0;////板がdown,upするまでの移動時間
			int count_down_up_market = 0;//約定により板がdown,upした回数

			double down_down_move_before_bid_depth = 0;//板がdown,downしたときの、移動する直前の買板の厚み
			double down_down_move_before_ask_depth = 0;//板がdown,downしたときの、移動する直前の売板の厚み
			double down_down_move_before_market_order= 0;//板がdown,downしたときの、移動する直前の約定枚数
			double down_down_move_time = 0.0;////板がdown,downするまでの移動時間
			int count_down_down_market = 0;//約定により板がdown,downした回数

			int Trade_count = 0;//Tradeで遷移した個数
			int not_Trade_count = 0;//not Tradeで遷移した個数


			double move_beofore_time = 0.0;//move_timeを求める際に使う変数




			boolean morning_or_afternoon = false;//前場(false) or 後場(true)


			while ((line = brtxt.readLine()) != null) {

				JNIc_split = line.split(",", 0);

				hour = Double.parseDouble(JNIc_split[1].substring(0, 2));
				minute = Double.parseDouble(JNIc_split[1].substring(3, 5));
				second = Double.parseDouble(JNIc_split[1].substring(6));
				time_total = hour*3600 + minute*60 + second;



				if(day == null){
					day = JNIc_split[0];
				}
				else if(!(day.equals(JNIc_split[0])) || (45000 <= time_total && Integer.parseInt(day) < 20110214 && morning_or_afternoon == false)){
					if(count_up_up_market != 0){
						up_up_move_before_market_order = up_up_move_before_market_order/count_up_up_market;//平均に変換
						up_up_move_before_bid_depth = up_up_move_before_bid_depth/count_up_up_market;//平均に変換
						up_up_move_before_ask_depth = up_up_move_before_ask_depth/count_up_up_market;//平均に変換
						up_up_move_time = up_up_move_time/count_up_up_market;//平均に変換
					}
					if(count_up_down_market != 0){
						up_down_move_before_market_order = up_down_move_before_market_order/count_up_down_market;//平均に変換
						up_down_move_before_bid_depth = up_down_move_before_bid_depth/count_up_down_market;//平均に変換
						up_down_move_before_ask_depth = up_down_move_before_ask_depth/count_up_down_market;//平均に変換
						up_down_move_time = up_down_move_time/count_up_down_market;//平均に変換
					}
					if(count_down_up_market != 0){
						down_up_move_before_market_order = down_up_move_before_market_order/count_down_up_market;//平均に変換
						down_up_move_before_bid_depth = down_up_move_before_bid_depth/count_down_up_market;//平均に変換
						down_up_move_before_ask_depth = down_up_move_before_ask_depth/count_down_up_market;//平均に変換
						down_up_move_time = down_up_move_time/count_down_up_market;//平均に変換
					}
					if(count_down_down_market != 0){
						down_down_move_before_market_order = down_down_move_before_market_order/count_down_down_market;//平均に変換
						down_down_move_before_bid_depth = down_down_move_before_bid_depth/count_down_down_market;//平均に変換
						down_down_move_before_ask_depth = down_down_move_before_ask_depth/count_down_down_market;//平均に変換
						down_down_move_time = down_down_move_time/count_down_down_market;//平均に変換
					}

					if(morning_or_afternoon == false && Integer.parseInt(day) < 20110214 && day.equals(JNIc_split[0])){//前場の書き込み
						pw.println(day + ",morning," + count_up_up + "," + count_up_down + "," + count_down_up + "," + count_down_down + "," +
								up_up_move_before_market_order + "," + up_up_move_before_bid_depth + "," + up_up_move_before_ask_depth + "," + up_up_move_time + "," +
								up_down_move_before_market_order + "," + up_down_move_before_bid_depth + "," + up_down_move_before_ask_depth + "," + up_down_move_time + "," +
								down_up_move_before_market_order + "," + down_up_move_before_bid_depth + "," + down_up_move_before_ask_depth + "," + down_up_move_time + "," +
								down_down_move_before_market_order + "," + down_down_move_before_bid_depth + "," + down_down_move_before_ask_depth + "," + down_down_move_time + "," +
								Trade_count + "," + not_Trade_count);
						morning_or_afternoon = true;

					}
					else if(morning_or_afternoon == false && Integer.parseInt(day) < 20110214 && !(day.equals(JNIc_split[0]))){//半日オークションの場合
						pw.println(day + ",morning," + count_up_up + "," + count_up_down + "," + count_down_up + "," + count_down_down + "," +
								up_up_move_before_market_order + "," + up_up_move_before_bid_depth + "," + up_up_move_before_ask_depth + "," + up_up_move_time + "," +
								up_down_move_before_market_order + "," + up_down_move_before_bid_depth + "," + up_down_move_before_ask_depth + "," + up_down_move_time + "," +
								down_up_move_before_market_order + "," + down_up_move_before_bid_depth + "," + down_up_move_before_ask_depth + "," + down_up_move_time + "," +
								down_down_move_before_market_order + "," + down_down_move_before_bid_depth + "," + down_down_move_before_ask_depth + "," + down_down_move_time + "," +
								Trade_count + "," + not_Trade_count);
					}
					else if(morning_or_afternoon == true && Integer.parseInt(day) < 20110214){//後場の書き込み
						pw.println(day + ",afternoon," + count_up_up + "," + count_up_down + "," + count_down_up + "," + count_down_down + "," +
								up_up_move_before_market_order + "," + up_up_move_before_bid_depth + "," + up_up_move_before_ask_depth + "," + up_up_move_time + "," +
								up_down_move_before_market_order + "," + up_down_move_before_bid_depth + "," + up_down_move_before_ask_depth + "," + up_down_move_time + "," +
								down_up_move_before_market_order + "," + down_up_move_before_bid_depth + "," + down_up_move_before_ask_depth + "," + down_up_move_time + "," +
								down_down_move_before_market_order + "," + down_down_move_before_bid_depth + "," + down_down_move_before_ask_depth + "," + down_down_move_time + "," +
								Trade_count + "," + not_Trade_count);
						morning_or_afternoon = false;

					}
					else if(20110214 <= Integer.parseInt(day)){//昼休みが廃止されたとき
						pw.println(day + ",no noon recess," + count_up_up + "," + count_up_down + "," + count_down_up + "," + count_down_down + "," +
								up_up_move_before_market_order + "," + up_up_move_before_bid_depth + "," + up_up_move_before_ask_depth + "," + up_up_move_time + "," +
								up_down_move_before_market_order + "," + up_down_move_before_bid_depth + "," + up_down_move_before_ask_depth + "," + up_down_move_time + "," +
								down_up_move_before_market_order + "," + down_up_move_before_bid_depth + "," + down_up_move_before_ask_depth + "," + down_up_move_time + "," +
								down_down_move_before_market_order + "," + down_down_move_before_bid_depth + "," + down_down_move_before_ask_depth + "," + down_down_move_time + "," +
								Trade_count + "," + not_Trade_count);
					}
					//------------初期化--------------
					day = JNIc_split[0];
					before_move_condition = 0;
					count_up_up = 0;
					count_up_down = 0;
					count_down_up = 0;
					count_down_down = 0;
					up_up_move_before_market_order = 0;
					up_up_move_before_bid_depth = 0;
					up_up_move_before_ask_depth = 0;
					up_up_move_time = 0;
					count_up_up_market = 0;
					up_down_move_before_market_order = 0;
					up_down_move_before_bid_depth = 0;
					up_down_move_before_ask_depth = 0;
					up_down_move_time = 0;
					count_up_down_market = 0;
					down_up_move_before_market_order = 0;
					down_up_move_before_bid_depth = 0;
					down_up_move_before_ask_depth = 0;
					down_up_move_time = 0;
					count_down_up_market = 0;
					down_down_move_before_market_order = 0;
					down_down_move_before_bid_depth = 0;
					down_down_move_before_ask_depth = 0;
					down_down_move_time = 0;
					count_down_down_market = 0;
					Trade_count = 0;
					not_Trade_count = 0;
					//------------初期化--------------
				}



				if(before_move_condition == 0){//一番最初とき
					if(JNIc_split[2].equals("up") || JNIc_split[2].equals("up not Trade")){//買板・売板の上昇
						before_move_condition = 1;
						move_beofore_time = time_total;
					}
					else if(JNIc_split[2].equals("down") || JNIc_split[2].equals("down not Trade")){//買板・売板の下落
						before_move_condition = 2;
						move_beofore_time = time_total;
					}
					/*else if(JNIc_split[2].equals("down only bid") || JNIc_split[2].equals("down only bid not Trade")){//(inlcude only)
                		before_move_condition = 3;
                	}
                	else if(JNIc_split[2].equals("up only ask") || JNIc_split[2].equals("up only ask not Trade")){//(inlcude only)
                		before_move_condition = 4;
                	}*/
				}
				else if(before_move_condition == 1 && (JNIc_split[2].equals("up") || JNIc_split[2].equals("up not Trade"))){//板が上昇した後の、板の上昇
					count_up_up++;
					if(JNIc_split[2].equals("up")){
						up_up_move_before_market_order += Integer.parseInt(JNIc_split[7]);
						up_up_move_before_bid_depth += Integer.parseInt(JNIc_split[8]);
						up_up_move_before_ask_depth += Integer.parseInt(JNIc_split[9]);
						up_up_move_time += time_total - move_beofore_time;
						move_beofore_time = time_total;
						Trade_count++;
						count_up_up_market++;
					}
					else{
						not_Trade_count++;
					}
					before_move_condition = 1;
				}
				else if(before_move_condition == 1 && (JNIc_split[2].equals("down") || JNIc_split[2].equals("down not Trade"))){//板が上昇した後の、板の下落
					count_up_down++;
					if(JNIc_split[2].equals("down")){
						up_down_move_before_market_order += Integer.parseInt(JNIc_split[7]);
						up_down_move_before_bid_depth += Integer.parseInt(JNIc_split[8]);
						up_down_move_before_ask_depth += Integer.parseInt(JNIc_split[9]);
						up_down_move_time += time_total - move_beofore_time;
						move_beofore_time = time_total;
						Trade_count++;
						count_up_down_market++;
					}
					else{
						not_Trade_count++;
					}
					before_move_condition = 2;
				}
				else if(before_move_condition == 2 && (JNIc_split[2].equals("up") || JNIc_split[2].equals("up not Trade"))){//板が下落した後の、板の上昇
					count_down_up++;
					if(JNIc_split[2].equals("up")){
						down_up_move_before_market_order += Integer.parseInt(JNIc_split[7]);
						down_up_move_before_bid_depth += Integer.parseInt(JNIc_split[8]);
						down_up_move_before_ask_depth += Integer.parseInt(JNIc_split[9]);
						down_up_move_time += time_total - move_beofore_time;
						move_beofore_time = time_total;
						Trade_count++;
						count_down_up_market++;
					}
					else{
						not_Trade_count++;
					}
					before_move_condition = 1;
				}
				else if(before_move_condition == 2 && (JNIc_split[2].equals("down") || JNIc_split[2].equals("down not Trade"))){//板が下落した後の、板の下落
					count_down_down++;
					if(JNIc_split[2].equals("down")){
						down_down_move_before_market_order += Integer.parseInt(JNIc_split[7]);
						down_down_move_before_bid_depth += Integer.parseInt(JNIc_split[8]);
						down_down_move_before_ask_depth += Integer.parseInt(JNIc_split[9]);
						down_down_move_time += time_total - move_beofore_time;
						move_beofore_time = time_total;
						Trade_count++;
						count_down_down_market++;
					}
					else{
						not_Trade_count++;
					}
					before_move_condition = 2;
				}
				/*else if(before_move_condition == 3 && (JNIc_split[2].equals("down only ask") || JNIc_split[2].equals("down only ask not Trade"))){//買板だけ下落し、その後売板だけが下落する(inlcude onlyここから)
            		before_move_condition = 2;
            		move_beofore_time = time_total;
            	}
            	else if(before_move_condition == 4 && (JNIc_split[2].equals("up only bid") || JNIc_split[2].equals("up only bid not Trade"))){//売板だけ上昇し、その後買板だけが上昇する(inlcude only)
            		before_move_condition = 1;
            		move_beofore_time = time_total;
            	}
            	else if(before_move_condition == 1 && (JNIc_split[2].equals("up only ask") || JNIc_split[2].equals("up only ask not Trade"))){//upの後の動き
            		before_move_condition = 5;
            	}
            	else if(before_move_condition == 5 && (JNIc_split[2].equals("up only bid") || JNIc_split[2].equals("up only bid not Trade"))){//up,up only ask,up only bid
            		before_move_condition = 1;
            		up_up_move_time += time_total - move_beofore_time;
            		move_beofore_time = time_total;
            		count_up_up++;
            	}
            	else if(before_move_condition == 1 && (JNIc_split[2].equals("down only bid") || JNIc_split[2].equals("down only bid not Trade"))){//upの後の動き
            		before_move_condition = 6;
            	}
            	else if(before_move_condition == 6 && (JNIc_split[2].equals("down only ask") || JNIc_split[2].equals("down only ask not Trade"))){//up,down only bid,down only askの後の動き
            		before_move_condition = 2;
            		up_down_move_time += time_total - move_beofore_time;
            		move_beofore_time = time_total;
            		count_up_down++;
            	}
            	else if(before_move_condition == 2 && (JNIc_split[2].equals("up only ask") || JNIc_split[2].equals("up only ask not Trade"))){//downの後の動き
            		before_move_condition = 7;
            	}
            	else if(before_move_condition == 7 && (JNIc_split[2].equals("up only bid") || JNIc_split[2].equals("up only bid not Trade"))){//down,up only ask,up only bid
            		before_move_condition = 1;
            		down_up_move_time += time_total - move_beofore_time;
            		move_beofore_time = time_total;
            		count_down_up++;
            	}
            	else if(before_move_condition == 2 && (JNIc_split[2].equals("down only bid") || JNIc_split[2].equals("down only bid not Trade"))){//downの後の動き
            		before_move_condition = 8;
            	}
            	else if(before_move_condition == 8 && (JNIc_split[2].equals("down only ask") || JNIc_split[2].equals("down only ask not Trade"))){//down,down only bidの後の動き
            		before_move_condition = 2;
            		down_down_move_time += time_total - move_beofore_time;
            		move_beofore_time = time_total;
            		count_down_down++;
            	}//(inlcude onlyここまで)*/
				else{
					before_move_condition = 0;
				}


			}

			if(count_up_up_market != 0){
				up_up_move_before_market_order = up_up_move_before_market_order/count_up_up_market;//平均に変換
				up_up_move_before_bid_depth = up_up_move_before_bid_depth/count_up_up_market;//平均に変換
				up_up_move_before_ask_depth = up_up_move_before_ask_depth/count_up_up_market;//平均に変換
				up_up_move_time = up_up_move_time/count_up_up_market;//平均に変換
			}
			if(count_up_down_market != 0){
				up_down_move_before_market_order = up_down_move_before_market_order/count_up_down_market;//平均に変換
				up_down_move_before_bid_depth = up_down_move_before_bid_depth/count_up_down_market;//平均に変換
				up_down_move_before_ask_depth = up_down_move_before_ask_depth/count_up_down_market;//平均に変換
				up_down_move_time = up_down_move_time/count_up_down_market;//平均に変換
			}
			if(count_down_up_market != 0){
				down_up_move_before_market_order = down_up_move_before_market_order/count_down_up_market;//平均に変換
				down_up_move_before_bid_depth = down_up_move_before_bid_depth/count_down_up_market;//平均に変換
				down_up_move_before_ask_depth = down_up_move_before_ask_depth/count_down_up_market;//平均に変換
				down_up_move_time = down_up_move_time/count_down_up_market;//平均に変換
			}
			if(count_down_down_market != 0){
				down_down_move_before_market_order = down_down_move_before_market_order/count_down_down_market;//平均に変換
				down_down_move_before_bid_depth = down_down_move_before_bid_depth/count_down_down_market;//平均に変換
				down_down_move_before_ask_depth = down_down_move_before_ask_depth/count_down_down_market;//平均に変換
				down_down_move_time = down_down_move_time/count_down_down_market;//平均に変換
			}

			if(morning_or_afternoon == false && Integer.parseInt(day) < 20110214 && day.equals(JNIc_split[0])){//半日オークションの場合
				pw.println(day + ",morning," + count_up_up + "," + count_up_down + "," + count_down_up + "," + count_down_down + "," +
						up_up_move_before_market_order + "," + up_up_move_before_bid_depth + "," + up_up_move_before_ask_depth + "," + up_up_move_time + "," +
						up_down_move_before_market_order + "," + up_down_move_before_bid_depth + "," + up_down_move_before_ask_depth + "," + up_down_move_time + "," +
						down_up_move_before_market_order + "," + down_up_move_before_bid_depth + "," + down_up_move_before_ask_depth + "," + down_up_move_time + "," +
						down_down_move_before_market_order + "," + down_down_move_before_bid_depth + "," + down_down_move_before_ask_depth + "," + down_down_move_time + ","+
						Trade_count + "," + not_Trade_count);
				morning_or_afternoon = true;
			}
			else if(morning_or_afternoon == false && Integer.parseInt(day) < 20110214 && !(day.equals(JNIc_split[0]))){//前場の書き込み
				pw.println(day + ",morning," + count_up_up + "," + count_up_down + "," + count_down_up + "," + count_down_down + "," +
						up_up_move_before_market_order + "," + up_up_move_before_bid_depth + "," + up_up_move_before_ask_depth + "," + up_up_move_time + "," +
						up_down_move_before_market_order + "," + up_down_move_before_bid_depth + "," + up_down_move_before_ask_depth + "," + up_down_move_time + "," +
						down_up_move_before_market_order + "," + down_up_move_before_bid_depth + "," + down_up_move_before_ask_depth + "," + down_up_move_time + "," +
						down_down_move_before_market_order + "," + down_down_move_before_bid_depth + "," + down_down_move_before_ask_depth + "," + down_down_move_time + ","+
						Trade_count + "," + not_Trade_count);
				morning_or_afternoon = false;
			}
			else if(morning_or_afternoon == true && Integer.parseInt(day) < 20110214){//後場の書き込み
				pw.println(day + ",afternoon," + count_up_up + "," + count_up_down + "," + count_down_up + "," + count_down_down + "," +
						up_up_move_before_market_order + "," + up_up_move_before_bid_depth + "," + up_up_move_before_ask_depth + "," + up_up_move_time + "," +
						up_down_move_before_market_order + "," + up_down_move_before_bid_depth + "," + up_down_move_before_ask_depth + "," + up_down_move_time + "," +
						down_up_move_before_market_order + "," + down_up_move_before_bid_depth + "," + down_up_move_before_ask_depth + "," + down_up_move_time + "," +
						down_down_move_before_market_order + "," + down_down_move_before_bid_depth + "," + down_down_move_before_ask_depth + "," + down_down_move_time + ","+
						Trade_count + "," + not_Trade_count);
				morning_or_afternoon = false;

			}
			else if(20110214 <= Integer.parseInt(day)){//昼休みが廃止されたとき
				pw.println(day + ",morning," + count_up_up + "," + count_up_down + "," + count_down_up + "," + count_down_down + "," +
						up_up_move_before_market_order + "," + up_up_move_before_bid_depth + "," + up_up_move_before_ask_depth + "," + up_up_move_time + "," +
						up_down_move_before_market_order + "," + up_down_move_before_bid_depth + "," + up_down_move_before_ask_depth + "," + up_down_move_time + "," +
						down_up_move_before_market_order + "," + down_up_move_before_bid_depth + "," + down_up_move_before_ask_depth + "," + down_up_move_time + "," +
						down_down_move_before_market_order + "," + down_down_move_before_bid_depth + "," + down_down_move_before_ask_depth + "," + down_down_move_time + ","+
						Trade_count + "," + not_Trade_count);
			}



			brtxt.close();
			fr.close();
			pw.close();





		}

		br.close();
	}
}


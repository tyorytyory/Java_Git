import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Order_dis_data{
	//20XX_limit_order.csvから出来高・注文回数などを算出するプログラム
	public static void main(String[] args) throws IOException{


		BufferedReader br = new BufferedReader(new FileReader("filelist_dekidaka.txt"));//読み取りたいファイル名の記入
		String txtFileName;


		int bid_number = 0;
		int ask_number = 0;
		int bid_volume = 0;
		int ask_volume = 0;
		String year = "";







		while((txtFileName = br.readLine()) != null) {






			FileReader fr = new FileReader(txtFileName);
			BufferedReader brtxt = new BufferedReader(fr);
			String line ="";
			int insert_bid[] = new int [1000000];
			int bid_count = 0;
			int insert_ask[] = new int [1000000];
			int ask_count = 0;

			int day = 0;
			double time_total = 0;

			String[] filename = txtFileName.split("\\.");


			File file_bid = new File("bid_" + filename[0] + ".csv");
			PrintWriter pw_bid = new PrintWriter(new BufferedWriter(new FileWriter(file_bid)));

			File file_ask = new File("ask_" + filename[0] + ".csv");
			PrintWriter pw_ask = new PrintWriter(new BufferedWriter(new FileWriter(file_ask)));

			//pw_bid.println(filename[0].substring(0, 4));//年間で行うとき
			//pw_ask.println(filename[0].substring(0, 4));//年間で行うとき


			while ((line = brtxt.readLine()) != null) {

				String JNc_split[] = line.split(",", 0);


				//System.out.println(line);

				double hour = Double.parseDouble(JNc_split[1].substring(0, 2));
				double minute = Double.parseDouble(JNc_split[1].substring(3, 5));
				double second = Double.parseDouble(JNc_split[1].substring(6));
				time_total = hour*3600 + minute*60 + second;


				if((JNc_split[0].equals("20081010") && 32400 <= time_total && time_total <= 34200) ||
						(JNc_split[0].equals("20081014") && 32400 <= time_total && time_total <= 34200) ||
						(JNc_split[0].equals("20081016") && 32400 <= time_total && time_total <= 34200) ||
						(JNc_split[0].equals("20110314") && 32400 <= time_total && time_total <= 33300) ||
						(JNc_split[0].equals("20110315") && 39600 <= time_total && time_total <= 42300) ||
						(JNc_split[0].equals("20110315") && 43200 <= time_total && time_total <= 44100) ||
						(JNc_split[0].equals("20110315") && 45900 <= time_total && time_total <= 46800) ||
						(JNc_split[0].equals("20130304") && 39600 <= time_total && time_total <= 51300) ||
						(JNc_split[0].equals("20130523") && 51300 <= time_total && time_total <= 53100) ||
						(JNc_split[0].equals("20140304") && 39600 <= time_total && time_total <= 41400)
						){
				}//サーキットブレイカーorシステムエラー
				else{
					if(Integer.parseInt(JNc_split[0]) < 20110214 && 39600 <= time_total && time_total <= 45000){
						//昼休み
					}
					else{
						if(JNc_split[4].equals("bid")&&  time_total <= 54000
								//&& JNc_split.length == 5 //成行注文のとき
								){//年間で行うとき
							if(0 <= Integer.parseInt(JNc_split[2])){
								pw_bid.println(Integer.parseInt(JNc_split[2]));
							}
							else if(Integer.parseInt(JNc_split[2]) <= 0){
								pw_bid.println(-1*Integer.parseInt(JNc_split[2]));
							}
						}
						else if(JNc_split[4].equals("ask") &&  time_total <= 54000
								//&& JNc_split.length == 5//成行注文のとき
								){//年間で行うとき
							if(0 <= Integer.parseInt(JNc_split[2])){
								pw_ask.println(Integer.parseInt(JNc_split[2]));
							}
							else if(Integer.parseInt(JNc_split[2]) <= 0){
								pw_ask.println(-1*Integer.parseInt(JNc_split[2]));
							}
						}
					}

				}


				//System.out.println(JNc_split[4]);



				if(day == 0){//1500までにするときは54000を条件にいれる
					day = Integer.parseInt(JNc_split[0]);


					if(JNc_split[4].equals("bid")
							//&& JNc_split.length == 5//成り行き注文

							){

						if(0 <= Integer.parseInt(JNc_split[2])){
							insert_bid[bid_count++] = Integer.parseInt(JNc_split[2]);
						}
						else if(Integer.parseInt(JNc_split[2]) <= 0){
							insert_bid[bid_count++] = -1*Integer.parseInt(JNc_split[2]);
						}

					}
					else if(JNc_split[4].equals("ask")
							//&& JNc_split.length == 5//成り行き注文

							){
						if(0 <= Integer.parseInt(JNc_split[2])){
							insert_ask[ask_count++] = Integer.parseInt(JNc_split[2]);
						}
						else if(Integer.parseInt(JNc_split[2]) <= 0){
							insert_ask[ask_count++] = -1*Integer.parseInt(JNc_split[2]);
						}

					}
				}
				else if(day != Integer.parseInt(JNc_split[0])){//1500までにするときは54000を条件にいれる

					if(day < 20110214 && 39600 <= time_total && time_total <= 45000){
						//昼休みがある期間
					}
					else{
						//pw_bid.print(day + ",");//年間のときは出力しない
						//pw_ask.print(day + ",");//年間のときは出力しない

						for(int i = 0;i < bid_count;i++){
							//pw_bid.print(insert_bid[i] + ",");
							insert_bid[i] = 0;
							/*if(i == bid_count - 1){
                				//System.out.println(line + "+++");

                			}*/
						}
						//pw_bid.println();//年間のときは出力しない

						bid_count = 0;

						for(int i = 0;i < ask_count;i++){
							//pw_ask.print(insert_ask[i] + ",");
							insert_ask[i] = 0;
							/*if(i == ask_count - 1){

                			}*/
						}
						//pw_ask.println();//年間のときは出力しない

						ask_count = 0;
					}




					day = Integer.parseInt(JNc_split[0]);
					if((JNc_split[0].equals("20081010") && 32400 <= time_total && time_total <= 34200) ||
							(JNc_split[0].equals("20081014") && 32400 <= time_total && time_total <= 34200) ||
							(JNc_split[0].equals("20081016") && 32400 <= time_total && time_total <= 34200) ||
							(JNc_split[0].equals("20110314") && 32400 <= time_total && time_total <= 33300) ||
							(JNc_split[0].equals("20110315") && 39600 <= time_total && time_total <= 42300) ||
							(JNc_split[0].equals("20110315") && 43200 <= time_total && time_total <= 44100) ||
							(JNc_split[0].equals("20110315") && 45900 <= time_total && time_total <= 46800) ||
							(JNc_split[0].equals("20130304") && 39600 <= time_total && time_total <= 51300) ||
							(JNc_split[0].equals("20130523") && 51300 <= time_total && time_total <= 53100) ||
							(JNc_split[0].equals("20140304") && 39600 <= time_total && time_total <= 41400)
							){


					}//サーキットブレイカーorシステムエラー
					else{
						if(JNc_split[4].equals("bid")
								//&& JNc_split.length == 5//成り行き注文

								){

							if(0 <= Integer.parseInt(JNc_split[2])){
								insert_bid[bid_count++] = Integer.parseInt(JNc_split[2]);
							}
							else if(Integer.parseInt(JNc_split[2]) <= 0){
								insert_bid[bid_count++] = -1*Integer.parseInt(JNc_split[2]);
							}

						}
						else if(JNc_split[4].equals("ask")
								//&& JNc_split.length == 5//成り行き注文

								){
							if(0 <= Integer.parseInt(JNc_split[2])){
								insert_ask[ask_count++] = Integer.parseInt(JNc_split[2]);
							}
							else if(Integer.parseInt(JNc_split[2]) <= 0){
								insert_ask[ask_count++] = -1*Integer.parseInt(JNc_split[2]);
							}

						}
					}


				}
				else if(day == Integer.parseInt(JNc_split[0])){//1500までにするときは54000を条件にいれる

					if((JNc_split[0].equals("20081010") && 32400 <= time_total && time_total <= 34200) ||
							(JNc_split[0].equals("20081014") && 32400 <= time_total && time_total <= 34200) ||
							(JNc_split[0].equals("20081016") && 32400 <= time_total && time_total <= 34200) ||
							(JNc_split[0].equals("20110314") && 32400 <= time_total && time_total <= 33300) ||
							(JNc_split[0].equals("20110315") && 39600 <= time_total && time_total <= 42300) ||
							(JNc_split[0].equals("20110315") && 43200 <= time_total && time_total <= 44100) ||
							(JNc_split[0].equals("20110315") && 45900 <= time_total && time_total <= 46800) ||
							(JNc_split[0].equals("20130304") && 39600 <= time_total && time_total <= 51300) ||
							(JNc_split[0].equals("20130523") && 51300 <= time_total && time_total <= 53100) ||
							(JNc_split[0].equals("20140304") && 39600 <= time_total && time_total <= 41400)
							){
						System.out.println(line);

					}//サーキットブレイカーorシステムエラー
					else{
						if(JNc_split[4].equals("bid")
								//&& JNc_split.length == 5//成り行き注文
								){
							if(0 <= Integer.parseInt(JNc_split[2])){
								insert_bid[bid_count++] = Integer.parseInt(JNc_split[2]);
							}
							else if(Integer.parseInt(JNc_split[2]) <= 0){
								insert_bid[bid_count++] = -1*Integer.parseInt(JNc_split[2]);
							}
						}
						else if(JNc_split[4].equals("ask")
								//&& JNc_split.length == 5//成り行き注文

								){
							if(0 <= Integer.parseInt(JNc_split[2])){
								insert_ask[ask_count++] = Integer.parseInt(JNc_split[2]);
							}
							else if(Integer.parseInt(JNc_split[2]) <= 0){
								insert_ask[ask_count++] = -1*Integer.parseInt(JNc_split[2]);
							}

						}
					}

				}


			}

			if(day < 20110214 && 39600 <= time_total && time_total <= 45000){
				//昼休みがある期間
			}
			else{
				//pw_bid.print(day + ",");//年間のときは出力しない
				//pw_ask.print(day + ",");//年間のときは出力しない

				for(int i = 0;i < bid_count;i++){
					//pw_bid.print(insert_bid[i] + ",");
					insert_bid[i] = 0;
					/*if(i == bid_count - 1){
        				//System.out.println(line + "+++");

        			}*/
				}
				//pw_bid.println();//年間のときは出力しない

				bid_count = 0;

				for(int i = 0;i < ask_count;i++){
					//pw_ask.print(insert_ask[i] + ",");
					insert_ask[i] = 0;
					/*if(i == ask_count - 1){

        			}*/
				}
				//pw_ask.println();//年間のときは出力しない

				ask_count = 0;
			}



			pw_bid.close();
			pw_ask.close();
			brtxt.close();
			fr.close();



		}



		bid_number = 0;
		ask_number = 0;
		bid_volume = 0;
		ask_volume = 0;




	}
}


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

//HTICDT_change_JNIc.javaで作成したchangeファイル(HTICDT)とJNIcデータを合体させ、どのデータが対応しているか否かをチェックする。出力されるファイルは２つ。
public class HTICDT_JNc_comb2{



	public static void main(String[] args) throws IOException{



		int number_JNIc = 0;
		int number_JNIc_end = 0;
		int number_HTICDT_end = 0;
		int number_HTICDT = 0;

		int count = 1;

		String JNIc_data [] = new String[9000000];//比較するデータ
		String HTICDT_data [] = new String[9000000];//比較するデータ
		Arrays.fill(HTICDT_data, null);//初期化
		String JNIc_line [] = new String[9000000];//読み込んだデータの挿入(JNIc)
		String HTICDT_line [] = new String[9000000];//読み込んだデータの挿入(HTICDT)

		Arrays.fill(JNIc_data, null);//初期化
		Arrays.fill(HTICDT_data, null);//初期化
		Arrays.fill(JNIc_line, null);//初期化
		Arrays.fill(HTICDT_line, null);//初期化

		String JNIc_before_day = "";//そのデータの一つ前のデータの日付



		boolean open_session = false;//HTICDTで寄付きが起きたかどうか示す変数

		BufferedReader br = new BufferedReader(new FileReader(//"../data/" +
				"filelist.txt"));//読み取りたいファイル名の記入
		String txtFileName;

		while((txtFileName = br.readLine()) != null) {

			FileReader fr = new FileReader(
					txtFileName);
			BufferedReader brtxt = new BufferedReader(fr);
			String line ="";


			while ((line = brtxt.readLine()) != null) {
				if(1 == count%2){//JNIcの読み込み


					String[] JNIc_split = line.split(",", 0);

					if(open_session == true && !(JNIc_split[2].equals(JNIc_before_day)) && !(JNIc_before_day.equals(""))){//Settlementが無いデータに対する対応。これによりopne_sessionを初期化
						open_session = false;
						JNIc_before_day = JNIc_split[2];
						//do{
						for(int i = 1;i<=10000;i++){//最終約定のまえの取引の削除
							if((JNIc_data[number_JNIc - i].substring(0,5)).equals("Trade")){
								number_JNIc = number_JNIc - i + 1;
								break;
							}
							else{
								JNIc_data[number_JNIc - i] = null;
							}
						}
						//}while((JNIc_data[number_JNIc].substring(0,5)).equals("Trade"));


					}
					//System.out.println(line);

					if(open_session == false && JNIc_split.length == 13 && (JNIc_split[12].equals("Open|High|Low[USER]") || JNIc_split[12].equals("Open|High|Low|Open 1st[USER]")
							|| JNIc_split[12].equals("\" [TRADE_TONE]\"") || JNIc_split[12].equals("\" [TRADE_TONE];High[USER]\"") || JNIc_split[12].equals("\" [TRADE_TONE];Low[USER]\"")
							|| JNIc_split[12].equals("\"   [PRC_QL_CD]; [TRADE_TONE]\"") || JNIc_split[12].equals("SPR[PRC_QL_CD]; [TRADE_TONE]")
							|| JNIc_split[12].equals("\"   [PRC_QL_CD]\"")
							//|| JNIc_split[12].equals("\"   [PRC_QL_CD]\"")
							)){//寄り付き

						open_session = true;
						JNIc_before_day = JNIc_split[2];

					}
					else if(open_session == false && (JNIc_split[2].equals("20071016")
							|| (JNIc_split[2].equals("20160714") && JNIc_split[4].equals("Trade") && JNIc_split[12].equals("Low[USER]")))){//2007年10月16日だけJNIcのデータがちょん切れてる
						open_session = true;
						JNIc_before_day = JNIc_split[2];
					}
					else if(open_session == true){
						if(JNIc_split[4].equals("Settlement Price") && JNIc_split[3].substring(0,2).equals("15")){//引け(Settlement Priceは昼休み終了時にもあるので注意
							open_session = false;
							//System.out.println(line);
							//do{
							for(int i = 1;i<=10000;i++){//最終約定のまえの取引(Quoteなど)の削除
								/*if((JNIc_data[number_JNIc - i].substring(0,5)).equals("Trade")){//TradeとQuoteを抽出する場合
            							number_JNIc = number_JNIc - i + 1;
            							break;
            						}*/
								if((JNIc_data[number_JNIc - i].substring(0,5)).equals("Quote")){//Quoteのみ抽出する場合
									number_JNIc = number_JNIc - i + 1;
									break;
								}
								else{
									JNIc_data[number_JNIc - i] = null;//
								}
							}
							//}while((JNIc_data[number_JNIc].substring(0,5)).equals("Trade"));//引けからその前の約定の間のQuoteは削除
						}
						else{


							/*if(JNIc_split[4].equals("Trade")){//約定
                    			JNIc_data[number_JNIc] = JNIc_split[4] + JNIc_split[5] + JNIc_split[6];
                    			JNIc_line[number_JNIc] = line;
                        		number_JNIc++;
                        		JNIc_before_day = JNIc_split[2];
                    		}*/
							if(JNIc_split[4].equals("Quote")){//最良気配（Tradeを入れるために上のif文を入れる場合は、これをelse ifにかえる）
								JNIc_data[number_JNIc] = JNIc_split[4] + JNIc_split[8] + JNIc_split[9] + JNIc_split[10] + JNIc_split[11];
								JNIc_line[number_JNIc] = line;
								number_JNIc++;
								JNIc_before_day = JNIc_split[2];
							}
							else if(!(JNIc_split[4].equals("Trade")) && !(JNIc_split[4].equals("Quote"))){
								JNIc_data[number_JNIc] = JNIc_split[3];//その他のデータ
								JNIc_line[number_JNIc] = line;
								number_JNIc++;
								JNIc_before_day = JNIc_split[2];
							}

						}
					}
				}
				else if(0 == count%2){//HTICDTの読み込み

					String[] HTICDT_split = line.split(",", 0);

					//System.out.println(line);

					/*if(HTICDT_split[4].equals("Trade")){//約定
            			HTICDT_data[number_HTICDT] = HTICDT_split[4] + HTICDT_split[5] + HTICDT_split[6];
            			HTICDT_line[number_HTICDT] = line;
            			number_HTICDT++;
            		}*/
					if(HTICDT_split[4].equals("Quote")){//最良気配（Tradeを入れるために上のif文を入れる場合は、これをelse ifにかえる）
						HTICDT_data[number_HTICDT] = HTICDT_split[4] + HTICDT_split[8] + HTICDT_split[9] + HTICDT_split[10] + HTICDT_split[11];
						HTICDT_line[number_HTICDT] = line;
						number_HTICDT++;
					}

				}
			}



			if(0 == count%2){//データの書き込み



				String[] filename = txtFileName.split("_");
				File file = new File(filename[0].substring(0,4) +
						"comb/quote_only_comb_" + filename[0].substring(8,16)+ ".csv");//結合データ
				PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(file)));
				File file1 = new File(filename[0].substring(0,4) +
						"comb/JNIc_delete_quote_only_" + filename[0].substring(8,16)+  ".csv");//JNIcで削除したデータ
				PrintWriter pw1 = new PrintWriter(new BufferedWriter(new FileWriter(file1)));
				File file2 = new File(filename[0].substring(0,4) +
						"comb/HTICDT_delete_quote_only_" + filename[0].substring(8,16)+  ".csv");//HTICDTで削除したデータ
				PrintWriter pw2 = new PrintWriter(new BufferedWriter(new FileWriter(file2)));

				/*String[] filename = txtFileName.split("/");
         		File file = new File("C:/Users/Hashimoto/Documents/pleiades/workspace/Git/" +  filename[8].substring(0,4) +	"comb2/" + filename[8].substring(0,8) + "_quote_comb.csv");//結合データ
              	PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(file)));
              	File file1 = new File("C:/Users/Hashimoto/Documents/pleiades/workspace/Git/" +  filename[8].substring(0,4) + "comb2/" + filename[8].substring(0,8) + "_JNIc_delete.csv");//JNIcで削除したデータ
              	PrintWriter pw1 = new PrintWriter(new BufferedWriter(new FileWriter(file1)));
              	File file2 = new File("C:/Users/Hashimoto/Documents/pleiades/workspace/Git/" +  filename[8].substring(0,4) + "comb2/" + filename[8].substring(0,8) + "_HTICDT_delete.csv");//HTICDTで削除したデータ
              	PrintWriter pw2 = new PrintWriter(new BufferedWriter(new FileWriter(file2)));*/


				//System.out.println(number_JNIc);

				boolean for_delete_end;
				for_delete_end = false;

				number_JNIc_end = number_JNIc;
				number_HTICDT_end = number_HTICDT;



				number_JNIc = 0;//初期化
				number_HTICDT = 0;//初期化

				int JNIc_delete = 0;//JNIcのデータの削除を行うための変数
				int HTICDT_delete = 0;//HTICDTのデータの削除を行うための変数


				do{



					if(HTICDT_data[number_HTICDT].equals(JNIc_data[number_JNIc])){//データが同じとき書き込み
						pw.println(HTICDT_line[number_HTICDT] + "," + JNIc_line[number_JNIc]);
						number_JNIc++;
						number_HTICDT++;
					}
					else{
						/*while(!(HTICDT_data[number_HTICDT].equals(JNIc_data[number_JNIc + JNIc_delete]))){//HTICDTを基準にデータの捜索・削除(ここから)

              				if(HTICDT_data[number_HTICDT] == null){//HTICDTがnullのためwhile文から抜ける。
                  				break;
              				}

              				String[] JNIc_split = JNIc_line[number_JNIc + JNIc_delete].split(",", 0);
              				String[] HTICDT_split = HTICDT_line[number_HTICDT].split(",", 0);

              				if(JNIc_data[number_JNIc + JNIc_delete + 1] != null){
              					JNIc_delete++;
              				}
              				else if(JNIc_data[number_JNIc + JNIc_delete + 1] == null){
              					for_delete_end = true;
              				}
                 				 if(JNIc_delete == 200 || for_delete_end == true){//ここの数字のよって結果が異なる。探索する行の数
                 					JNIc_delete = 0;//初期化
                 					for_delete_end = false;
                 					JNIc_split = JNIc_line[number_JNIc].split(",", 0);
             						HTICDT_split = HTICDT_line[number_HTICDT].split(",", 0);
                 					if(JNIc_split[2].equals(HTICDT_split[2])){//日付が同じとき
                     					pw2.println(HTICDT_line[number_HTICDT]);//削除したデータの書き込み(HTICDT)
                     					number_HTICDT++;
                 					}
                 					else if(!(JNIc_split[2].equals(HTICDT_split[2]))){//日付が異なるとき
                 						if(Integer.parseInt(HTICDT_split[2]) < Integer.parseInt(JNIc_split[2])){//JNIcの方にデータが不足している。
                 							while(!(HTICDT_split[2].equals(JNIc_split[2]))){
                 								pw2.println(HTICDT_line[number_HTICDT]);//削除したデータの書き込み(HTICDT)
                     							number_HTICDT++;
                 								HTICDT_split = HTICDT_line[number_HTICDT].split(",", 0);
                     						}
                 						}
                 						else if(Integer.parseInt(HTICDT_split[2]) > Integer.parseInt(JNIc_split[2])){//HTICDTの方にデータが不足している。
                 							while(!(HTICDT_split[2].equals(JNIc_split[2]))){
                 								pw1.println(JNIc_line[number_JNIc]);//削除したデータの書き込み(JNIc)
                 								number_JNIc++;
                 								JNIc_split = JNIc_line[number_JNIc].split(",", 0);
                     						}
                 						}
                 					}
                 				 }
              			 }

              			if(HTICDT_data[number_HTICDT] == null){//JNIcが空になったとき
              				while(JNIc_data[number_JNIc] != null){
                  				pw2.println(JNIc_line[number_JNIc]);//削除したデータの書き込み(JNIc)
                  				//System.out.println(HTICDT_line[number_HTICDT]);
                  				number_JNIc++;
                  			}
              				break;
              			}


              			 pw.println(HTICDT_line[number_HTICDT] + "," + JNIc_line[number_JNIc + JNIc_delete]);
              			for(int i = 0;i < JNIc_delete;i++){
              				pw1.println(JNIc_line[number_JNIc + i]);//削除したデータの書き込み(JNIc)
              			}
              			number_JNIc = number_JNIc + JNIc_delete + 1;
              			number_HTICDT++;
              			JNIc_delete = 0;//初期化
						 */
						//HTICDTを基準にデータの捜索・削除(ここまで)


						while(!(HTICDT_data[number_HTICDT + HTICDT_delete].equals(JNIc_data[number_JNIc]))){//JNIcデータを基準にデータの捜索・削除（ここから）

							//System.out.println(JNIc_line[number_JNIc]);
							if(JNIc_data[number_JNIc] == null){//JNIcがnullのためwhile文から抜ける。
								break;
							}


							String[] JNIc_split = JNIc_line[number_JNIc].split(",", 0);
							String[] HTICDT_split = HTICDT_line[number_HTICDT + HTICDT_delete].split(",", 0);

							double JNIc_hour = Double.parseDouble(JNIc_split[3].substring(0,2));//JNIcの「時間」を取得
							double JNIc_minute = Double.parseDouble(JNIc_split[3].substring(3,5));//JNIcの「分」を取得
							double JNIc_second = Double.parseDouble(JNIc_split[3].substring(6,15));//JNIcの「秒」を取得
							double JNIc_sum_time = JNIc_hour*3600 + JNIc_minute*60 + JNIc_second;//JNIcの時間を秒に変換
							//System.out.println("JNIc " + JNIc_hour + " " + JNIc_minute + " " + JNIc_second);

							double HTICDT_hour = Double.parseDouble(HTICDT_split[3].substring(0,2));//HTICDTの「時間」を取得
							double HTICDT_minute = Double.parseDouble(HTICDT_split[3].substring(3,5));//HTICDTの「分」を取得
							double HTICDT_second = Double.parseDouble(HTICDT_split[3].substring(6,15));//HTICDTの「秒」を取得
							double HTICDT_sum_time = HTICDT_hour*3600 + HTICDT_minute*60 + HTICDT_second;//HTICDTの時間を秒に変換
							//System.out.println("HTICDT " + HTICDT_hour + " " + HTICDT_minute + " " + HTICDT_second);

							double JNIc_HTICDT_dif = JNIc_sum_time - HTICDT_sum_time;//HTICDTとJNIcの時間差を計算
							if(JNIc_HTICDT_dif < 0){
								//System.out.println(JNIc_HTICDT_dif);
								JNIc_HTICDT_dif = JNIc_HTICDT_dif*-1;//符号がマイナスになっているものをプラスにする。
							}




							if(HTICDT_data[number_HTICDT + HTICDT_delete + 1] != null){
								HTICDT_delete++;
							}
							else if(HTICDT_data[number_HTICDT + HTICDT_delete + 1] == null){
								for_delete_end = true;
							}
							if(HTICDT_delete == 1000000 || for_delete_end == true || JNIc_HTICDT_dif > 1000){//ここの数字のよって結果が異なる。「探索する行の数」と「行の秒数の誤差の許容範囲」
								JNIc_HTICDT_dif = 0;//初期化
								//System.out.println("yeah");
								//System.out.println(HTICDT_line[number_HTICDT + JNIc_delete]);
								for_delete_end = false;
								HTICDT_delete = 0;//初期化
								JNIc_split = JNIc_line[number_JNIc].split(",", 0);
								HTICDT_split = HTICDT_line[number_HTICDT].split(",", 0);
								if(JNIc_split[2].equals(HTICDT_split[2])){//日付が同じとき
									//System.out.println(JNIc_line[number_JNIc]);
									pw1.println(JNIc_line[number_JNIc]);//削除したデータの書き込み(JNIc)
									number_JNIc++;
								}
								else if(!(JNIc_split[2].equals(HTICDT_split[2]))){//日付が異なるとき

									if(Integer.parseInt(HTICDT_split[2]) < Integer.parseInt(JNIc_split[2])){//JNIcの方にデータが不足している。
										while(!(HTICDT_split[2].equals(JNIc_split[2]))){
											pw2.println(HTICDT_line[number_HTICDT]);//削除したデータの書き込み(HTICDT)

											number_HTICDT++;

											HTICDT_split = HTICDT_line[number_HTICDT].split(",", 0);
										}
									}
									else if(Integer.parseInt(HTICDT_split[2]) > Integer.parseInt(JNIc_split[2])){//HTICDTの方にデータが不足している。
										while(!(HTICDT_split[2].equals(JNIc_split[2]))){
											pw1.println(JNIc_line[number_JNIc]);//削除したデータの書き込み(JNIc)
											number_JNIc++;
											JNIc_split = JNIc_line[number_JNIc].split(",", 0);
										}
									}
								}
							}
						}



						if(JNIc_data[number_JNIc] == null){//JNIcが空になったとき
							while(HTICDT_data[number_HTICDT] != null){
								pw2.println(HTICDT_line[number_HTICDT]);//削除したデータの書き込み(HTICDT)
								//System.out.println(HTICDT_line[number_HTICDT]);
								number_HTICDT++;
							}
							break;
						}
						pw.println(HTICDT_line[number_HTICDT + HTICDT_delete] + "," + JNIc_line[number_JNIc]);
						for(int i = 0;i < HTICDT_delete;i++){
							pw2.println(HTICDT_line[number_HTICDT + i]);//削除したデータの書き込み(JNIc)
						}
						number_HTICDT = number_HTICDT + HTICDT_delete + 1;

						number_JNIc++;


						HTICDT_delete = 0;//初期化

						//JNIcデータを基準にデータの捜索・削除（ここまで）

					}

				}while(HTICDT_data[number_HTICDT] != null);
				pw.close();
				pw1.close();
				pw2.close();
				number_HTICDT = 0;//初期化
				number_JNIc = 0;//初期化
				number_JNIc_end = 0;//初期化
				for_delete_end = false;//初期化
				open_session = false;//初期化
				Arrays.fill(JNIc_data, null);//初期化
				Arrays.fill(HTICDT_data, null);//初期化
				Arrays.fill(JNIc_line, null);//初期化
				Arrays.fill(HTICDT_line, null);//初期化
				JNIc_before_day = "";//初期化
			}

			count++;

			brtxt.close();
			fr.close();

		}

	}
}
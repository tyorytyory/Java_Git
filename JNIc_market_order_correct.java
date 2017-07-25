import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

public class JNIc_market_order_correct{
//market_orderから寄付きの情報や最終約定のデータを入力するプログラム
//ただし、ロイター社にはデータが不足していることから、出力後いくつかのデータは修正する必要がある。
//初期J-GATEのデータを扱う際は、最初にcombファイル、その次にmarket_orderファイルを読み込ませる
    public static void main(String[] args) throws IOException{

        BufferedReader br = new BufferedReader(new FileReader("filelist1.txt"));//読み取りたいファイル名の記入
        String txtFileName;


        int delete_comb_row = 1;//使用していない？？

        int input_file = 0;//使用していない？？

        int day_comb = 0;//delete_combで使用する変数
        int delete_comb[][] = new int[20161200][3];//初期J-GATEのデータの読み込み（[][1]→寄付データの行数、[][2]→最終約定データの行数）

        boolean comb_donation = false;//combデータから寄付データの有無を示す変数（false→ある、true→ない）
        boolean comb_final_trade = false;//combデータから最終約定データの有無を示す変数（true→ある、false→ない）

        String file_input[] = new String[1000000];//maeketデータを格納する変数。あとでこれをもとに書き込みを行う。
        int file_input_number = 0;//読み込んだファイルの数を格納する変数。file_inputでも使う。
        String JNIc_split[] = new String[50];//「,」で区切ったlineを格納する変数

        int day_market = 0;//初期J-GATEではないデータを用いるときに使う年月日の変数


        while((txtFileName = br.readLine()) != null) {



        	FileReader fr = new FileReader(txtFileName);
            BufferedReader brtxt = new BufferedReader(fr);
            String line ="";





            String[] filename = txtFileName.split("\\_");





            File file = new File(filename[0] + "_" + filename[1]  + "_" + filename[2].substring(0, 5) + "_donation.csv");
            PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(file)));




            while ((line = brtxt.readLine()) != null) {


            	//-----------------------(初期J-GATE以外ここから)--------------------------------------

            	/*JNIc_split = line.split(",", 0);



            	if(day_market == 0){
            		day_market = Integer.parseInt(JNIc_split[0]);
            	}
            	else if(day_market != Integer.parseInt(JNIc_split[0])){

            		for(int i = 0;i < file_input_number;i++){
            			if(i == file_input_number - 1){//最終約定の書き出し
            				String file_write_final_trade_split[] = file_input[i].split(",", 0);
            				pw.println(file_write_final_trade_split[0] + "," + file_write_final_trade_split[1] + "," + file_write_final_trade_split[2] + "," + file_write_final_trade_split[3] +
            						"," + file_write_final_trade_split[4] + ",final trade,,,");
            			}
            			else{//約定の書き出し
            				pw.println(file_input[i]);
            			}
            		}
            		Arrays.fill(file_input, null);//初期化
            		file_input_number = 0;//初期化
            		day_market = Integer.parseInt(JNIc_split[0]);

            	}

            	if(day_market < 20110214 || 20160715 < day_market){//初期J-GATE以外のデータの読み込み
            		if(file_input_number == 0){//寄付きの書き込み
            			file_input[file_input_number] = JNIc_split[0] + "," + JNIc_split[1] + "," + JNIc_split[2] + "," + JNIc_split[3] + ",donation,,,,";
            			file_input_number++;
            		}
            		else{//約定の書き込み
            			file_input[file_input_number] = line;
            			file_input_number++;
            		}

            	}
            	*/

            	//-----------------------(初期J-GATE以外ここまで)--------------------------------------




            	//-----------------------(初期J-GATEここから)--------------------------------------

            	if(filename[0].substring(0, 3).equals("JNI")){//combデータの読み込み


            		if(!(line.substring(0,1).equals("-")) && day_comb == 0){//初期データの読み込み
            			day_comb = Integer.parseInt(line.substring(4, 12));//日付の挿入
            			delete_comb[day_comb][1]++;//寄付データの行数のカウント
            			delete_comb[day_comb][2]++;//最終約定データののカウント
            		}
            		else if( !(line.substring(0,1).equals("-")) && day_comb != Integer.parseInt(line.substring(4, 12))){//日付がかわったとき
            			day_comb = Integer.parseInt(line.substring(4, 12));
            			delete_comb_row++;
            			delete_comb[day_comb][1]++;//寄付データの行数のカウント
            			delete_comb[day_comb][2]++;//最終約定データののカウント
            			comb_donation = false;//初期化
            			comb_final_trade = false;//初期化
            		}
            		else if(line.substring(0,1).equals("-") && comb_donation == false){
            			delete_comb[day_comb][1]++;//寄付データの行数のカウント
            		}
            		else{
            			comb_donation = true;//寄付データの終了
            		}

            		if((line.substring(52,55).equals("128") || line.substring(52,55).equals("160")) && comb_final_trade == false){
            			comb_final_trade = true;//最終約定の開始
            		}
            		else if(comb_final_trade == true && line.substring(0,1).equals("-")){
            			delete_comb[day_comb][2]++;//最終約定データののカウント
            		}

            	}


            	if(filename[1].equals("market")){//marketデータの読み込み。ここからファイル書き込みを行う


                	JNIc_split = line.split(",", 0);

                	if(day_market == 0){//日付データの読み込み
                		day_market = Integer.parseInt(JNIc_split[0]);
                	}
                	else if(day_market != Integer.parseInt(JNIc_split[0])){//日付が変わった
                		for(int i = 0;i < file_input_number;i++){
                			if(file_input_number - delete_comb[day_market][2] <= i){//最終約定の書き込み
                				String file_write_final_trade_split[] = file_input[i].split(",", 0);
                				pw.println(file_write_final_trade_split[0] + "," + file_write_final_trade_split[1] + "," + file_write_final_trade_split[2] + "," + file_write_final_trade_split[3] +
                						"," + file_write_final_trade_split[4] + ",final trade,,,");
                			}
                			else{//寄付・約定データの書き込み
                				pw.println(file_input[i]);
                			}
                		}
                		Arrays.fill(file_input, null);//初期化
                		file_input_number = 0;//初期化
                		day_market = Integer.parseInt(JNIc_split[0]);
                	}




            		if(20110214 <= day_market && day_market <= 20160715){

                		if(delete_comb[day_market][1] > 0){//寄付データの格納
                			file_input[file_input_number] = JNIc_split[0] + "," + JNIc_split[1] + "," + JNIc_split[2] + "," + JNIc_split[3] + ",donation,,,,";
                			file_input_number++;
                			delete_comb[day_market][1]--;//初期化を意味する（値を0にしている）。

                		}
                		else if(delete_comb[day_market][1] == 0){//約定・最終約定データの格納
                			file_input[file_input_number] = line;
                			file_input_number++;
                		}
                		else{
                			System.out.println("1231534");
                		}

            		}

            	}

            	//-----------------------(初期J-GATEここまで)--------------------------------------


            }



            //-----------------------(初期J-GATE以外ここから)--------------------------------------

            /*
            //最後の書き込み
              for(int i = 0;i < file_input_number;i++){
      			if(i == file_input_number - 1){//最終約定の書き込み
      				String file_write_final_trade_split[] = file_input[i].split(",", 0);
      				pw.println(file_write_final_trade_split[0] + "," + file_write_final_trade_split[1] + "," + file_write_final_trade_split[2] + "," + file_write_final_trade_split[3] +
      						"," + file_write_final_trade_split[4] + ",final trade,,,");
      			}
      			else{//約定の書き込み
      				pw.println(file_input[i]);
      			}
      		}
      		Arrays.fill(file_input, null);//初期化
      		file_input_number = 0;//初期化
      		day_market = 0;//初期化

      		*/

      		//-----------------------(初期J-GATE以外ここまで)--------------------------------------


          //-----------------------(初期J-GATEここから)--------------------------------------

           if(filename[1].equals("market")){

        		   for(int i = 0;i < file_input_number;i++){
              			if(file_input_number - delete_comb[day_market][2] <= i){//最終約定の書き込み
              				String file_write_final_trade_split[] = file_input[i].split(",", 0);
              				pw.println(file_write_final_trade_split[0] + "," + file_write_final_trade_split[1] + "," + file_write_final_trade_split[2] + "," + file_write_final_trade_split[3] +
              						"," + file_write_final_trade_split[4] + ",final trade,,,");
              			}
              			else{//約定・最終約定データの格納
              				pw.println(file_input[i]);
              			}
              		}

          		Arrays.fill(file_input, null);//初期化
          		file_input_number = 0;//初期化
          		day_market = 0;//初期化


           }

         //-----------------------(初期J-GATEここまで)--------------------------------------


            brtxt.close();
            fr.close();
            pw.close();



        }

        br.close();
    }
}


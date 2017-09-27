import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class JNIc_market_order_count{
//market_orderから寄付きの情報や最終約定のデータを入力するプログラム
//ただし、ロイター社にはデータが不足していることから、出力後いくつかのデータは修正する必要がある。
//初期J-GATEのデータを扱う際は、最初にcombファイル、その次にmarket_orderファイルを読み込ませる
    public static void main(String[] args) throws IOException{

        BufferedReader br = new BufferedReader(new FileReader("filelist_market_count.txt"));//読み取りたいファイル名の記入
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

        	int market_order_count [][] = new int[3][30];//[1][]は累積枚数、[2][]は約定回数
        	int market_order_count_half[][] = new int[3][30];//[1][]は累積枚数、[2][]は約定回数(20060104,20061229,20070104,20071228,20080104,20081230,20090105の半日オークションで用いる)
        	int market_count_half = 1;//market_order_countで用いる変数（時間毎）
        	int market_count = 1;//market_order_countで用いる変数（時間毎）

        	int order_insert_half [][] = new int[30][5000000];
        	int order_insert [][] = new int[30][5000000];
        	int count_number_half = 1;//market_order_countで用いる変数（個数毎）
        	int count_number = 1;//market_order_countで用いる変数（個数毎）
        	int day = 0;


        	int market_count_for = 1;

        	int market_for_number [] = new int [30];
        	int for_time = 32400;
        	int for_count_number [] = new int [30];


        	int day_if = 0;



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
            		market_for_number[26] = count_number;

            		day_if = day;

            		for_time = 32400;
            		market_count_for = 1;

            		for(int i = 1;i<=26;i++){
            			for_count_number[i] = market_for_number[i];
            			//System.out.println(market_for_number[i]);
            			//System.out.println(line);

            		}

            		count_number = for_count_number[1];
            	}

            	if(day == 20060104 || day == 20061229 || day == 20070104 || day == 20071228 || day == 20080104 || day == 20081230 || day == 20090105){
            		for(int j = 31500;j<=40500;j+=900){
            			if(j <= time_total && time_total < (j + 900) && (JNIc_split[4].equals("bid") || JNIc_split[4].equals("ask")) && Integer.parseInt(JNIc_split[2]) >= 0
            					){
            				market_order_count_half[1][market_count_half] += Integer.parseInt(JNIc_split[2]);
            				market_order_count_half[2][market_count_half]++;
            				/*order_insert_half[market_count_half][count_number_half] = Integer.parseInt(JNIc_split[2]);
            				count_number_half++;*/
            			}
            			market_count_half++;
            		}
            	}
            	else{
            		for(int j = 31500;j<=54000;j+=900){

            			if(j <= time_total && time_total < (j + 900) && (JNIc_split[4].equals("bid") || JNIc_split[4].equals("ask")) && Integer.parseInt(JNIc_split[2]) >= 0
            					&& (JNIc_split.length == 6 && !(JNIc_split[5].equals("final trade")))){
            				market_order_count[1][market_count] += Integer.parseInt(JNIc_split[2]);
            				market_order_count[2][market_count]++;


            				if(54900 <= time_total){

            				}


            				if(for_time <= time_total && time_total <= 54900){

            					market_for_number[market_count_for] = count_number;


            					while(for_time <= time_total && for_time < 54000){
            						market_count_for++;
            						if(day_if < 20110214 && for_time == 39600){
            							for_time += 5400;
            						}
            						else{
            							for_time += 900;
            						}
            					}


            					count_number = for_count_number[market_count_for];
            					order_insert[market_count][count_number] = Integer.parseInt(JNIc_split[2]);
            				}
            				else{
            					order_insert[market_count][count_number] = Integer.parseInt(JNIc_split[2]);
            					//System.out.println(order_insert[market_count][count_number]);
            					count_number++;
            				}
            			}
            			market_count++;
            		}
           		}
            	market_count = 1;
            	market_count_half = 1;
            }

            market_for_number[market_count_for] = count_number;
            market_count_for = 1;
			market_count = 1;
			count_number = 1;
			for_time = 32400;

            File file = new File(filename[0] + "_count.csv");
            PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(file)));

            if(market_order_count_half[2][market_count] != 0){
            	for(market_count = 1;market_count<=26;market_count++){
            		//System.out.println(market_order_count_half[1][market_count]);

            		/*double value_ave = (double)market_order_count_half[1][market_count]/market_order_count_half[2][market_count];
            		double value_va = 0;

            		for(int i = 0;i < count_number_half;i++){
            			value_va += (order_insert_half[market_count_half][i] - value_ave)*(order_insert_half[market_count_half][i] - value_ave);;//分散の算出
            			order_insert_half[market_count_half][i] = 0;
            		}
            		count_number_half = 0;*/

            		pw.print((double)market_order_count_half[1][market_count]/market_order_count_half[2][market_count] + ",");
            		//pw.print(value_va/market_order_count_half[2][market_count]);

            		market_order_count_half[1][market_count] = 0;
            		market_order_count_half[2][market_count] = 0;
            	}
            	pw.print("\n");
            }


            for(market_count = 1;market_count<=26;market_count++){

            	double value_ave = (double)market_order_count[1][market_count]/market_order_count[2][market_count];
            	double value_va = 0;



            	//System.out.println(market_for_number[market_count]);


            	for(int i = 1;i < market_for_number[market_count];i++){

        			value_va += (order_insert[market_count][i] - value_ave)*(order_insert[market_count][i] - value_ave);//分散の算出
        			order_insert[market_count][i] = 0;


        		}

            	//System.out.println(value_va);


        		pw.print((double)market_order_count[1][market_count]/market_order_count[2][market_count] + ",");
        		pw.print((double)value_va/market_order_count[2][market_count]);
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


//JNIcから抽出したG:\data\2016_2017\約定データ\2016年入手マイクロデータ\出来高\マイクロ間隔にある「data_oyaoorder」からmathematicaでパラメータ推定する際に必要なデータを抽出する。

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

public class JNIc_Cox_para_data{

    public static void main(String[] args) throws IOException{
    	//String Index;
        int order [] = new int[31];//注文の種類（親注文１、親注文２、親注文３、、、）
        int order_count = 3;//order[]の配列に使うもの。3の理由は、読み込んだ行データをスペースで区切った際に最小値が３であるため。
        int day = 0;//年月日
        int day_before = 0;//1つ前のデータの年月日
        int count_math_order = 1;//mathematica用のデータを格納するための変数

        BufferedReader br = new BufferedReader(new FileReader("filelist.txt"));//読み取りたいファイル名の記入
        String txtFileName;

        while((txtFileName = br.readLine()) != null) {

        	FileReader fr = new FileReader(txtFileName);
            BufferedReader brtxt = new BufferedReader(fr);
            String line = "";

            String[] filename = txtFileName.split("_");
    		File file = new File(filename[0] + "_" + filename[1] + "_oyaordercheck.csv");//同一注文の数を確認
         	PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(file)));
         	File file2 = new File(filename[0] + "_" + filename[1] + "_oyaordermath.csv");
         	PrintWriter pw2 = new PrintWriter(new BufferedWriter(new FileWriter(file2)));

            while ((line = brtxt.readLine()) != null) {
            	String[] fileroad = line.split(" ");//ファイルを1行ずつ読み込み
            	//System.out.println(fileroad.length);//配列の長さを計算

            	day = Integer.parseInt(fileroad[0]);//日付の代入
            	if(day_before == 0){
            		day_before = day;
            	}
            	else if(day_before != day){
            		pw.print(day_before);
            		pw2.print(day_before);

            		for(int i = 1;i<=30;i++){
                  	   pw.print(" " + order[i]);
                  	   for(int j = 1;j<=order[i];j++){
                  			   pw2.print(" " + count_math_order);
                  	   }
                  	   count_math_order++;
            		}
            		pw.println();
            		pw2.println();
            		count_math_order = 1;//初期化
            		Arrays.fill(order, 0);//初期化
            		day_before = day;//日付の代入
            	}

            	for(int i = 1;i<=30;i++){
            		if(fileroad.length == order_count){
            			order[order_count - 2]++;
                	}
            		order_count++;
            	}
            	order_count = 0;
            }


            pw.print(day);
    		pw2.print(day);

    		for(int i = 1;i<=30;i++){
          	   pw.print(" " + order[i]);
          	   for(int j = 1;j<=order[i];j++){
          		   pw2.print(" " + count_math_order);
          	   }
          	   count_math_order++;
    		}
    		pw.println();
    		pw2.println();



    		pw.close();
    		pw2.close();
            brtxt.close();
            fr.close();

            /*for(int i = 1;i<=30;i++){
         	   System.out.println(order[i]);
            }*/








        	// txtファイル名を一行ずつロードする
        }
        br.close();

    }



}


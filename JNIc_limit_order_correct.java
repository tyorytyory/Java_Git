import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class JNIc_limit_order_correct{
	//limit_orderからザラバ時間での指値注文・指値注文のキャンセルを抽出するプログラム
	//market_donationを先に読み込ませ、その日の最終約定（もしくは最終取引の時間）を格納する。
	//その次にlimit_orderを読み込ませ、ファイルを出力させる。
	//first_depthファイルも修正できる
	public static void main(String[] args) throws IOException{

		BufferedReader br = new BufferedReader(new FileReader("filelist_limit.txt"));//読み取りたいファイル名の記入
		String txtFileName;


		String JNIc_split[] = new String[50];//「,」で区切ったlineを格納する変数
		double market_final_time_total [] = new double[20162000];//market_donationから読み込んだデータの一番下の時間のtime_totalを格納
		int day_market = 0;//market_donationから読み込んだデータの日にちを格納




		while((txtFileName = br.readLine()) != null) {




			FileReader fr = new FileReader(txtFileName);
			BufferedReader brtxt = new BufferedReader(fr);
			String line ="";





			String[] filename = txtFileName.split("\\_");

			double hour = 0;//時間
			double minute = 0;//分
			double second = 0;//秒
			double time_total = 0;//時間を秒換算








			File file = new File(filename[0] + "_" + filename[1]  + "_" + filename[2].substring(0, 6) + "_final_trade.csv");
			PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(file)));




			while ((line = brtxt.readLine()) != null) {

				JNIc_split = line.split(",", 0);

				hour = Double.parseDouble(JNIc_split[1].substring(0, 2));//時間
				minute = Double.parseDouble(JNIc_split[1].substring(3, 5));//分
				second = Double.parseDouble(JNIc_split[1].substring(6));//秒
				time_total = hour*3600 + minute*60 + second;//時間を秒換算

				if(filename[1].equals("market")){
					if(day_market == 0){
						day_market = Integer.parseInt(JNIc_split[0]);
					}
					else if(day_market == Integer.parseInt(JNIc_split[0])){
						market_final_time_total[day_market] = time_total;
						//System.out.println(market_final_time_total [day_market]);
					}
					else if(day_market != Integer.parseInt(JNIc_split[0])){
						day_market = Integer.parseInt(JNIc_split[0]);
					}
				}
				//else if(filename[1].equals("limit")){//指値注文
				else if(filename[1].equals("frist")){//最初の板
					if(20061227 == Integer.parseInt(JNIc_split[0]) || 20160714 == Integer.parseInt(JNIc_split[0])){
						pw.println(line);
					}
					else if(time_total <= market_final_time_total[Integer.parseInt(JNIc_split[0])]){
						pw.println(line);
					}
					else{
						System.out.println(line);
					}
				}

			}




			brtxt.close();
			fr.close();
			pw.close();



		}

		br.close();
	}
}


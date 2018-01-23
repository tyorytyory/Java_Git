import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
//final_tradeから約定と指値を抽出するプログラム
//とか色々
public class Li_price_ex{

	public static void main(String[] args) throws IOException{

		String Index;
		String Kolmo[][] = new String [30][300];
		int Kolmo_count[] = new int [30];
		int number = 1;
		String Kolmo_natural = "0.0";
		String[] filename = new String [20];
		int nodata_number = 0;


		BufferedReader br = new BufferedReader(new FileReader("filelist.txt"));//読み取りたいファイル名の記入
		String txtFileName;

		boolean first = false;
		boolean noon_first = false;
		boolean noon_last = false;
		int day_new = 0;
		int motomeru = 10;//求めるやつ
		int before_bid = 0;
		int before_ask = 0;
		int beforeyear = 0;

		int bid_price = 0;
		int ask_price = 0;

		while((txtFileName = br.readLine()) != null) {




			FileReader fr = new FileReader(txtFileName);
			BufferedReader brtxt = new BufferedReader(fr);
			String line ="";

			filename = txtFileName.split("\\.");

			File file = new File("bid_" + filename[0]+ "_first.csv");
			PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(file)));

			while ((line = brtxt.readLine()) != null) {
				String JNIc_split[] = line.split(",", 0);//final_trade


				int day = Integer.parseInt(JNIc_split[2]);
				double hour = Double.parseDouble(JNIc_split[3].substring(0, 2));//時間
				double minute = Double.parseDouble(JNIc_split[3].substring(3, 5));//分
				double second = Double.parseDouble(JNIc_split[3].substring(6));//秒
				double time_sum = hour*3600 + minute*60 + second;//時間を秒換算
				if(JNIc_split[4].equals("Quote") && line.length() >= 8){
					bid_price = Integer.parseInt(JNIc_split[8]);
					ask_price = Integer.parseInt(JNIc_split[10]);
				}

				//System.out.println(line);
				if(JNIc_split[4].equals("Quote") && first == false && (ask_price - bid_price) >= 10){
					if(!(JNIc_split[motomeru].equals(""))){
						//pw.println(JNIc_split[motomeru]);//初値（買）（前場）
						
						first = true;
						day_new = day;
					}

				}

				if(day_new != day && JNIc_split[4].equals("Quote")){
					if(!(JNIc_split[motomeru].equals("")) && (ask_price - bid_price) >= 10){
						day_new = day;
						noon_first = false;
						noon_last = false;
						//pw.println(JNIc_split[motomeru]);//初値（買）
						if(day != 20070105){
							pw.println(beforeyear + "," + before_ask);//終値（買）（後場）
						}
					}

				}

				if(JNIc_split[4].equals("Quote") && noon_last == false && 39600 <= time_sum){
					if(!(JNIc_split[motomeru].equals("")) && (ask_price - bid_price) >= 10){
						//pw.println(JNIc_split[motomeru]);//終値（買）前場
						noon_last = true;
					}					
				}

				if(JNIc_split[4].equals("Quote") && 45000 <= time_sum && noon_first == false && (ask_price - bid_price) >= 10){
					if(!(JNIc_split[motomeru].equals(""))){
						//pw.println(JNIc_split[motomeru]);//初値（買）（後場）

						noon_first = true;
					}

				}
				
				if((ask_price - bid_price) >= 10){
					before_bid = bid_price;
					before_ask = ask_price;
					beforeyear = day;
				}


			}
			brtxt.close();
			fr.close();
			pw.close();
		}
		br.close();
	}
}



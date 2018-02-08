import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
//final_tradeから約定と指値を抽出するプログラム
public class market_number_dis{

	public static void main(String[] args) throws IOException{


		String[] filename = new String [20];
		int market_number [] = new int [80];
		int count_for = 0;
		boolean noon_recess = false;


		File file = new File("market_transaction_number1.csv");
		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(file)));


		BufferedReader br = new BufferedReader(new FileReader("filelist_market.txt"));//読み取りたいファイル名の記入
		String txtFileName;

		while((txtFileName = br.readLine()) != null) {




			FileReader fr = new FileReader(txtFileName);
			BufferedReader brtxt = new BufferedReader(fr);
			String line ="";






			while ((line = brtxt.readLine()) != null) {
				String JNIc_split[] = line.split(",", 0);//final_trade


				int day = Integer.parseInt(JNIc_split[2]);
				double hour = Double.parseDouble(JNIc_split[3].substring(0, 2));//時間
				double minute = Double.parseDouble(JNIc_split[3].substring(3, 5));//分
				double second = Double.parseDouble(JNIc_split[3].substring(6));//秒
				double time_sum = hour*3600 + minute*60 + second;//時間を秒換算

				if(20110214 == day && noon_recess == false){

					System.out.println(line);

					for(int i = 0;i<=75;i++){

						pw.print(market_number[i] + ",");
						market_number[i] = 0;

						//count_for++;
					}

					pw.println();

					noon_recess = true;


				}

				for(int i = 32400;i<=53700;i+=300){
					if(i <= time_sum && time_sum <= (i + 300)){
						market_number[count_for]++;
						//System.out.println(line + "lllll" + count_for);
					}
					count_for++;
				}
				count_for = 0;

			}
			brtxt.close();
			fr.close();

		}


		for(int i = 0;i<=75;i++){
			pw.print(market_number[i] + ",");
			market_number[i] = 0;

			//count_for++;
		}

		pw.println();


		pw.close();

		br.close();
	}
}



import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
//final_tradeから約定と指値を抽出するプログラム
//とか色々
public class Limit_extraction{

	public static void main(String[] args) throws IOException{

		String Index;
		String Kolmo[][] = new String [30][300];
		int Kolmo_count[] = new int [30];
		int number = 1;
		String Kolmo_natural = "0.0";
		String[] filename = new String [20];
		int nodata_number = 0;


		BufferedReader br = new BufferedReader(new FileReader("filelist_dekidaka.txt"));//読み取りたいファイル名の記入
		String txtFileName;

		while((txtFileName = br.readLine()) != null) {




			FileReader fr = new FileReader(txtFileName);
			BufferedReader brtxt = new BufferedReader(fr);
			String line ="";

			filename = txtFileName.split("\\.");

			File file = new File("bid_" + filename[0]+ "_1500.csv");
			PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(file)));

			while ((line = brtxt.readLine()) != null) {
				String JNIc_split[] = line.split(",", 0);//final_trade


				int day = Integer.parseInt(JNIc_split[0]);
				double hour = Double.parseDouble(JNIc_split[1].substring(0, 2));//時間
				double minute = Double.parseDouble(JNIc_split[1].substring(3, 5));//分
				double second = Double.parseDouble(JNIc_split[1].substring(6));//秒
				double time_sum = hour*3600 + minute*60 + second;//時間を秒換算

				/*if(0 < Integer.parseInt(JNIc_split[2])){
        		pw.println(line);
        	}*/

				if(time_sum <= 54000){
					pw.println(line);
				}


			}
			brtxt.close();
			fr.close();
			pw.close();
		}
		br.close();
	}
}



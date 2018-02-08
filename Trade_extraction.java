import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
//JNIcから約定（Trade)のデータを抽出するプログラム
public class Trade_extraction{

	public static void main(String[] args) throws IOException{

		BufferedReader br = new BufferedReader(new FileReader("filelist.txt"));//読み取りたいファイル名の記入
		String txtFileName;

		int count1 = 0;
		int count2 = 0;
		int count3 = 0;
		int count4 = 0;

		//String JNIc_split[] = new String[9000000];

		while((txtFileName = br.readLine()) != null) {


			FileReader fr = new FileReader(txtFileName);
			BufferedReader brtxt = new BufferedReader(fr);
			String line ="";

			String[] filename = txtFileName.split("_");
			//String[] filename = txtFileName.split("\\.");




			System.out.println(txtFileName);
			System.out.println(filename[0]);


			File file1 = new File( filename[0] + "_"+ filename[1] + "_" + filename[3] + ".txt");
			//File file1 = new File("../data/" + filename[0] + "_Trade.txt");

			PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(file1)));

			while ((line = brtxt.readLine()) != null) {

				String JNIc_split[] = line.split(",", 0);//JNIc用

				/*if(JNIc_split[4].equals("Trade")){//JNIc用
        		pw.println(line);
        	}*/

				/*if((line.substring(0,2)).equals("31")){
        		pw.println(line);
        	}*/

				String d1 = line.substring(12,20);
				int day = Integer.parseInt(d1);

				String d4 = line.substring(21,23);
				double hour2 = Double.parseDouble(d4);
				String d5 = line.substring(24,26);
				double minute2 = Double.parseDouble(d5);
				String d6 = line.substring(27,36);
				double second2 = Double.parseDouble(d6);
				double k_all_time = hour2*60*60 + minute2*60 + (second2/1000000);
				BigDecimal x1 = new BigDecimal(k_all_time);
				k_all_time = x1.setScale(6, BigDecimal.ROUND_HALF_UP).doubleValue();
				//2016年取得ロイター通信社データ

				if(k_all_time <= 54300 && day <= 20110210){
					count1++;
				}
				else if(k_all_time <= 54300 && 20110214 <= day){
					count2++;
				}
				else if(54300 < k_all_time &&  day <= 20110210){
					count3++;
				}
				else if(54300 < k_all_time &&  20110214 <= day){
					count4++;
				}




			}


			brtxt.close();
			fr.close();
			pw.close();

		}
		System.out.println((double)count1/1216 + "," + (double)count2/1238  + "," + (double)count3/1216 + "," + (double)count4/1238);
	}
}

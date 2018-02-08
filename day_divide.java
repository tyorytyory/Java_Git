import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

public class day_divide{
	//日付毎にファイルを出力するプラグラム
	public static void main(String[] args) throws IOException{



		String data_day_before = "";
		int data_number = 0;//data_insertの配列にナンバリングする変数
		String data_insert[] = new String [1000000];//書き込みをする際に使用する変数


		BufferedReader br = new BufferedReader(new FileReader("filelist.txt"));//読み取りたいファイル名の記入
		String txtFileName;

		while((txtFileName = br.readLine()) != null) {






			FileReader fr = new FileReader(txtFileName);
			BufferedReader brtxt = new BufferedReader(fr);
			String line ="";





			while ((line = brtxt.readLine()) != null) {

				String[] data_split = line.split(",", 0);

				if(data_day_before.equals("")){
					data_day_before = data_split[0];
					data_insert[data_number] = line;
					data_number++;
					System.out.println(data_day_before);
				}
				else if(data_day_before.equals(data_split[0])){
					data_insert[data_number] = line;
					data_number++;
				}
				else if(!(data_day_before.equals(data_split[0]))){

					String[] filename = txtFileName.split("\\.");
					filename = txtFileName.split("_");
					//File file = new File(data_day_before +	"_HTICDT_market_limit_gcheck_change.csv");//HDTCDT用
					File file = new File(data_day_before  + "_" + filename[1] + ".csv");//JNIc用
					PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("../kklab_Github/Test/" + file)));

					for(int i = 0;i<data_number;i++){//書き込み
						pw.println(data_insert[i]);
					}
					pw.close();

					Arrays.fill(data_insert, null);//初期化

					data_day_before = data_split[0];
					data_number = 0;
					data_insert[data_number] = line;
					data_number++;

				}
			}

			String[] filename = txtFileName.split("\\.");
			filename = txtFileName.split("_");
			//File file = new File(data_day_before +	"_HTICDT_market_limit_gcheck_change.csv");//HTICDT用
			File file = new File(data_day_before + "_" + filename[1] + ".csv");//JNIc用
			PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("../kklab_Github/Test/" + file)));

			for(int i = 0;i<data_number;i++){//書き込み
				pw.println(data_insert[i]);
			}

			Arrays.fill(data_insert, null);//初期化
			data_day_before = "";//初期化
			data_number = 0;//初期化

			pw.close();

			brtxt.close();
			fr.close();

		}

	}
}
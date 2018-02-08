import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
//varファイルからコンマ秒と秒のデータがどの程度遅れているかヒストグラムをだすプログラム


public class time_lag_distribution{

	public static void main(String[] args) throws IOException{

		BufferedReader br = new BufferedReader(new FileReader("filelist.txt"));//読み取りたいファイル名の記入
		String txtFileName;

		while((txtFileName = br.readLine()) != null) {




			double buffer1[] = new double[100000];
			int buffer2[] = new int[100000];
			int count[] = new int[100000];
			int total_count[] = new int[50];
			int n = 0;
			int zero_total = 0;
			int total = 0;
			int number = 1;
			int natural = 0;

			double time_dif = 0;
			String Index;

			int count2 = 0;

			FileReader fr = new FileReader(txtFileName);
			BufferedReader brtxt = new BufferedReader(fr);
			String line ="";

			String[] filename = txtFileName.split("_");

			File file = new File(filename[0] +"_"+ filename[1]+	"_" + filename[2] + "_" + filename[3] +  "_0.1_distribution_new.txt");
			PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(file)));

			while ((line = brtxt.readLine()) != null) {

				Index = line;

				//String d1 = Index.substring(22);//月毎
				//String d1 = Index.substring(0);//年毎
				String d1 = Index.substring(25);//var_dayファイル


				time_dif = Double.parseDouble(d1);
				BigDecimal x1 = new BigDecimal(time_dif);
				time_dif = x1.setScale(6, BigDecimal.ROUND_HALF_UP).doubleValue();//四捨五入

				//System.out.println(time_dif);

				/*if(time_dif < 0){
    			time_dif = -1 * time_dif;

    		}*/

				/*if(time_dif<=0){
    			System.out.println(time_dif);
    		}//時間差がマイナスの時の処理*/



				if(time_dif>=30){
					count2++;
				}//時間差が範囲を超す時の処理


				if(time_dif==0){
					zero_total++;
				}

				if(time_dif < 0){
					count[0]++;
				}//時間差がマイナスの時の処理

				for(double quarter = 1.0;quarter <= 1000;quarter+=1.0){//何秒単位でヒストグラムを出すか
					if(time_dif>=((quarter - 1.0)/10) && time_dif<(quarter/10)){//気をつけて！！範囲が変わる。
						count[number]++;
					}
					number++;
				}
				natural++;

				number = 1;


			}
			//System.out.println(zero_total);

			for(int i = 0;i<=300;i++){
				pw.print(i+1);
				String s = Integer.toString(i+1);
				if(s.length()==1){
					pw.print("    ");
				}
				else if(s.length()==2){
					pw.print("   ");
				}
				else if(s.length()==3){
					pw.print("  ");
				}
				else if(s.length()==4){
					pw.print(" ");
				}

				pw.println(count[i]);


				total = total + count[i];


			}
			for(int i = 0;i<=99999;i++){
				count[i] = 0;
			}

			total = total + zero_total;

			pw.println("出力結果のデータ数 "+ total);
			pw.println("30秒以上ラグがあったデータ "+ count2);
			pw.println("実際のデータ数 " + natural);
			natural = 0;
			total = 0;

			brtxt.close();
			fr.close();
			pw.close();
			//System.out.println(count2);

		}
	}
}


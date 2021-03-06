import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
//板の相関係数を求めるプログラム
public class JNIc_ita_corr{

	public static void main(String[] args) throws IOException{

		BufferedReader br = new BufferedReader(new FileReader("filelist_corr.txt"));//読み取りたいファイル名の記入
		String txtFileName;

		while((txtFileName = br.readLine()) != null) {

			String Index;



			int day = 0;//年月日

			int count_if = 0;//データ抽出で必要な変数
			int length_konma = 0;//データ抽出で必要な変数
			int i1 = 0;//データ抽出で必要な変数
			int count1 = 0;//データ抽出で必要な変数

			//int value = 0;//板のup,downの変数(+1 or -1)

			int count_dummy = 0;
			int day_now = 0;

			double value[] = new double [200000];//板のup,downの変数(+1 or -1)
			double number_of_value = 0;//1日の中で板が板が移動した回数
			double value_sum = 0.0;//valueの合計値
			double value_ave = 0.0;//valueの平均値
			double value_cova = 0.0;//valueの1次の自己共分散
			double value_va = 0.0;//valueの分散
			double value_cor = 0.0;//valueの一次の自己相関
			double significance  = 0.0;//95有意水準の値;
			boolean am_or_pm = false;//2011/2/10までの前場・後場を判断するプログラム
			double hour = 0;//時間
			double minute = 0;//分
			double second = 0;//秒
			double time_total = 0;//時間を秒換算



			FileReader fr = new FileReader(txtFileName);
			BufferedReader brtxt = new BufferedReader(fr);
			String line ="";

			String[] filename = txtFileName.split("\\.");



			File file = new File(filename[0]  + 	"_corr_result.csv");
			PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(file)));

			pw.println("日付,前場後場,平均値,分散,1次自己共分散,1次自己相関,±1.96√T");


			while ((line = brtxt.readLine()) != null) {

				Index = line;


				String JNIc_split[] = line.split(",", 0);


				String d2 = JNIc_split[0];
				day = Integer.parseInt(d2);

				//System.out.println(JNIc_split[0] + JNIc_split[1]);
				hour = Double.parseDouble(JNIc_split[1].substring(0, 2));//時間
				minute = Double.parseDouble(JNIc_split[1].substring(3, 5));//分
				second = Double.parseDouble(JNIc_split[1].substring(6));//秒
				time_total = hour*3600 + minute*60 + second;//時間を秒換算


				if(count_dummy == 0){
					day_now = day;
					count_dummy++;
				}
				else if(day_now != day ||
						(((20060104 < day_now && day_now < 20061229) || (20070104 < day_now && day_now < 20071228) || (20080104 < day_now && day_now < 20081230) || (20090105 < day_now && day_now < 20110214)) && time_total > 45000 && am_or_pm == false)){


					//number_of_value = number_of_value+1;//プログラム上1つ多く加算されるため-1する。

					value_ave = value_sum/(number_of_value);//平均値の算出


					for(int i=0;i<=(int)(number_of_value-2);i++){
						value_cova += ((value[i] - value_ave)*(value[i+1] - value_ave));//1次の共分散の算出
						//System.out.println(((value[(int)(number_of_value)] - value_ave)*(value[(int)(number_of_value+1)] - value_ave)));
					}


					value_cova = value_cova/number_of_value;//1次の共分散の算出

					for(int i=0;i<=(int)(number_of_value-1);i++){
						value_va += (value[i] - value_ave)*(value[i] - value_ave);;//分散の算出
						value[i] = 0;
					}
					value_va = value_va/number_of_value;//分散の算出

					value_cor = value_cova/value_va;//自己相関の算出

					significance = 1.96/Math.sqrt(number_of_value);//95%有意水準



					if(day_now == 20060104 || day_now == 20061229 || day_now == 20070104 || day_now == 20071228 || day_now == 20080104 || day_now == 20081230 || day_now == 20090105){//半日オークション
						pw.println(day_now + ",morning," + value_ave + "," + value_va + "," + value_cova +  "," + value_cor + "," + significance + ",");
					}
					else if(day_now < 20110214 && time_total > 45000 && am_or_pm == false){//前場
						pw.println(day_now + ",morning," + value_ave + "," + value_va + "," + value_cova +  "," + value_cor + "," + significance + ",");
						am_or_pm = true;
					}
					else if(day_now < 20110214 && day_now != day){//後場
						pw.println(day_now + ",afternoon," + value_ave + "," + value_va + "," + value_cova +  "," + value_cor + "," + significance + ",");
						am_or_pm = false;
					}
					else if(20110214 <= day_now){//2011/2/14以降（昼休みの廃止）
						pw.println(day_now + ",no noon recess," + value_ave + "," + value_va + "," + value_cova +  "," + value_cor + "," + significance + ",");
					}
					else{
						System.out.println(line);
					}

					if(Math.abs(value_cor) > Math.abs(significance)){
						//pw.println("採択される");
					}
					else if(Math.abs(value_cor) < Math.abs(significance)){
						//pw.println("棄却される");
					}

					/*System.out.println(day_now);
        		System.out.println("平均値" + value_ave);
        		System.out.println("分散" + value_va);
        		System.out.println("共分散" + value_cova);
        		System.out.println("自己相関" + value_cor);
        		System.out.println("有意水準" + significance);
        		System.out.println(number_of_value);
        		System.out.println();*/


					day_now = day;
					number_of_value = 0;
					value_va = 0;
					value_cova = 0;
					value_ave = 0;
					value_sum = 0;
				}



				//length_konma = Index.length();
				//System.out.println(length_konma);

				/*for(i1=0;i1<length_konma;i1++){
                String a = Index.substring(i1,i1+1);
                //System.out.println(a);
                if(count_if == 3 && !(a.equals(",")) && count1 == 0){
                	String d1 = Index.substring(i1);
                	value[(int)(number_of_value)] = Double.parseDouble(d1);
            		value_sum += value[(int)(number_of_value)];//valueの合計値を算出
            		//System.out.println(value[(int)(number_of_value)]);
            		number_of_value++;
                	count1++;
                }
                if(a.equals(",")){
                    count_if++;

                }
            }
    		count_if = 0;
    		count1 = 0;
				 */
				//System.out.println(JNIc_split[7]);
				value[(int)(number_of_value)] = Double.parseDouble(JNIc_split[2]);
				value_sum += value[(int)(number_of_value)];//valueの合計値を算出

				number_of_value++;

			}


			//number_of_value = number_of_value+1;//プログラム上1つ多く加算されるため-1する。

			value_ave = value_sum/(number_of_value+1);//平均値の算出


			for(int i=0;i<=(int)(number_of_value-1);i++){
				value_cova += ((value[i] - value_ave)*(value[i+1] - value_ave));//1次の共分散の算出
				//System.out.println(((value[(int)(number_of_value)] - value_ave)*(value[(int)(number_of_value+1)] - value_ave)));
			}


			value_cova = value_cova/number_of_value;//1次の共分散の算出

			for(int i=0;i<=(int)(number_of_value);i++){
				value_va += (value[i] - value_ave)*(value[i] - value_ave);;//分散の算出
				value[i] = 0;
			}
			value_va = value_va/number_of_value;//分散の算出

			value_cor = value_cova/value_va;//自己相関の算出

			significance = 1.96/Math.sqrt(number_of_value);//95%有意水準

			if(day_now == 20060104 || day_now == 20061229 || day_now == 20070104 || day_now == 20071228 || day_now == 20080104 || day_now == 20081230 || day_now == 20090105){//半日オークション
				pw.print(day_now + ",morning," + value_ave + "," + value_va + "," + value_cova +  "," + value_cor + "," + significance + ",");
			}
			else if(day_now < 20110214 && time_total > 45000 && am_or_pm == false){//前場
				pw.print(day_now + ",morning," + value_ave + "," + value_va + "," + value_cova +  "," + value_cor + "," + significance + ",");
				am_or_pm = true;
			}
			else if(day_now < 20110214 && day_now != day){//後場
				pw.print(day_now + ",afternoon," + value_ave + "," + value_va + "," + value_cova +  "," + value_cor + "," + significance + ",");
				am_or_pm = false;
			}
			else if(20110214 <= day_now){//2011/2/14以降（昼休みの廃止）
				pw.print(day_now + ",no noon recess," + value_ave + "," + value_va + "," + value_cova +  "," + value_cor + "," + significance + ",");
			}
			else{
				//System.out.println(line);
			}

			if(Math.abs(value_cor) > Math.abs(significance)){
				//pw.println("採択される");
			}
			else if(Math.abs(value_cor) < Math.abs(significance)){
				//pw.println("棄却される");
			}



			day_now = day;
			number_of_value = 0;
			value_va = 0;
			value_cova = 0;
			value_ave = 0;
			value_sum = 0;
			am_or_pm = false;




			brtxt.close();
			fr.close();
			pw.close();

		}
	}
}


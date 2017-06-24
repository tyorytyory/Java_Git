import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
//個別株データ(201XXX_contract.txt)のマイクロ秒について確認するプログラム
public class kobetsu_diftime{

    public static void main(String[] args) throws IOException{


        BufferedReader br = new BufferedReader(new FileReader("filelist.txt"));//読み取りたいファイル名の記入
        String txtFileName;

        while((txtFileName = br.readLine()) != null) {

        	String Index;

        	/*String record1;//取引所コード
        	String security;//証券種別
        	String code1;//銘柄コード(a,b)
        	String code2;//銘柄コード(限月)
        	String code3;//銘柄コード(c,d)
        	String record2;//(レコード種別)
        	int day;//日付*/
        	//int count_code=0;
        	//int count_meigara = 0;

        	int length_konma = 0;//コンマデータの長さの確認
        	int count1 = 0;//for文
        	int count2 = 0;//for文
        	int count3 = 0;//for文
        	int count4 = 0;//for文


        	int count_if = 0;//for文

        	//int number1 = 0;
        	//int number2 = 0;
        	String a;
        	int i1 = 0;
        	int i2 = 0;
        	int i3 = 0;
        	int i4 = 0;
        	int i5 = 0;


        	int day = 0;

        	double hour = 0.0;
        	double minute = 0.0;
        	double second = 0.0;
        	double micro = 0.0;
        	double milli = 0.0;

        	double time_sum = 0.0;
        	double time_sum1 = 0.0;
        	double time_sum2 = 0.0;
        	double time_dif = 0.0;

        	int count_dif = 0;


        	int day_dummy = 0;




        	/*int count_a = 0;
        	int count_b = 0;
        	int count_c = 0;*/






        	FileReader fr = new FileReader(txtFileName);
            BufferedReader brtxt = new BufferedReader(fr);
            String line ="";

            String[] filename = txtFileName.split("\\_");

         	File file = new File("dif_" + filename[0] + "_" + filename[1] + "_" + filename[4]);//時間差に0を含むときは0を記入
         	PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(
         			//"F:\\個別株\\TICST120\\201602\\" +
         	file)));


            while ((line = brtxt.readLine()) != null) {


            	Index = line;

            	/*length_konma = Index.length();//個別株の場合　ここから

        		for(i1=0;i1<length_konma;i1++){
                    a = Index.substring(i1,i1+1);

                    if(count_if == 1 && !(a.equals(",")) && count1 == 0){
                    	i2 = i1+1;
                    	count1++;
                    }
                    if(count_if == 2 && count1 != 0 && count2 == 0){
                    	i3 = i1-2;
                    	count2++;
                    	String d0 = Index.substring(i2,i3);
                    	day = Integer.parseInt(d0);
                    }
                    if(count_if == 9 && !(a.equals(",")) && count3 == 0){
                    	i4 = i1+1;
                    	count3++;
                    }
                    if(count_if == 10 && count3 != 0 && count4 == 0){
                    	i5 = i1-2;
                    	count4++;
                    	String d1 = Index.substring(i4,i5-10);
                    	String d2 = Index.substring(i4+2,i5-8);
                    	String d3 = Index.substring(i4+4,i5-6);
                    	String d4 = Index.substring(i4+6,i5);
                    	//System.out.println(d1 + "" + d2 + "" + d3 + "" + d4);

                    	hour = Double.parseDouble(d1);
                    	minute = Double.parseDouble(d2);
                    	second = Double.parseDouble(d3);
                    	micro = Double.parseDouble(d4);

                    	hour = hour*60*60;
                    	minute = minute*60;
                    	micro = micro/1000000;
                    	time_sum = hour + minute + second + micro;
                    }
                    if(a.equals(",")){
                        count_if++;
                    }
                }
        		count_if = 0;
        		count1 = 0;
        		count2 = 0;
        		count3 = 0;
        		count4 = 0;

        		i2 = 0;
                i3 = 0;
                i4 = 0;
                i5 = 0;//個別株の場合　ここまで*/

            	length_konma = Index.length();//個別株の場合　ここから

        		for(i1=0;i1<length_konma;i1++){//2016年取得ロイター　ここから
                    a = Index.substring(i1,i1+1);

                    if(count_if == 2 && !(a.equals(",")) && count1 == 0){
                    	i2 = i1;
                    	count1++;
                    }
                    if(count_if == 3 && count1 != 0 && count2 == 0){
                    	i3 = i1-1;
                    	count2++;
                    	String d0 = Index.substring(i2,i3);
                    	day = Integer.parseInt(d0);
                    }
                    if(count_if == 3 && !(a.equals(",")) && count3 == 0){
                    	i4 = i1;
                    	count3++;
                    }
                    if(count_if == 4 && count3 != 0 && count4 == 0){
                    	i5 = i1-1;
                    	count4++;
                    	String d1 = Index.substring(i4,i5-13);
                    	String d2 = Index.substring(i4+3,i5-10);
                    	String d3 = Index.substring(i4+6,i5-7);
                    	String d4 = Index.substring(i4+9,i5);//マイクロ秒
                    	//String d4 = Index.substring(i4+9,i5-3);//ミリ秒
                    	//System.out.println(d1 + "" + d2 + "" + d3 + "" + d4);

                    	hour = Double.parseDouble(d1);
                    	minute = Double.parseDouble(d2);
                    	second = Double.parseDouble(d3);
                    	micro = Double.parseDouble(d4);//マイクロ秒
                    	//milli = Double.parseDouble(d4);//ミリ秒

                    	hour = hour*60*60;
                    	minute = minute*60;
                    	micro = micro/1000000;
                    	milli =milli/1000;
                    	time_sum = hour + minute + second + micro;//マイクロ秒
                    	//time_sum = hour + minute + second + milli;//ミリ秒

                    }
                    if(a.equals(",")){
                        count_if++;
                    }
                }
        		count_if = 0;
        		count1 = 0;
        		count2 = 0;
        		count3 = 0;
        		count4 = 0;

        		i2 = 0;
                i3 = 0;
                i4 = 0;
                i5 = 0;//2016年取得ロイター　ここまで

                if(count_dif == 0){
                	time_sum1 = time_sum;
                	count_dif++;
                	day_dummy = day;
                }
                else if(day_dummy == day){
                	time_sum2 = time_sum;
                	time_dif = time_sum2 - time_sum1;
                	BigDecimal x1 = new BigDecimal(time_dif);
            		time_dif = x1.setScale(6, BigDecimal.ROUND_HALF_UP).doubleValue();
            		//time_dif = x1.setScale(3, BigDecimal.ROUND_DOWN).doubleValue();//ミリ秒

            		if(time_dif > 0){//時間差に0を含むか含まないか　これは非常に重要
            			pw.println(day + " " + time_dif);
            		}
                	time_sum1 = time_sum;
                }
                else if(day_dummy != day){
                	time_sum1 = time_sum;
                	day_dummy = day;
                }
            }





            brtxt.close();
            fr.close();
            pw.close();

        	// txtファイル名を一行ずつロードする
        }
        br.close();

    }



}



import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
//2016年に入手したJNcファイルに対し、データを使う使用に限月のデータを抽出するプログラム
public class JNc_gcheck{

    public static void main(String[] args) throws IOException{

        BufferedReader br = new BufferedReader(new FileReader("filelist.txt"));//読み取りたいファイル名の記入
        String txtFileName;

        while((txtFileName = br.readLine()) != null) {

        	String Index;


        	int year = 0;
        	int month = 0;
        	int day = 0;

        	int length_konma = 0;
        	int count_if = 0;
        	int count1 = 0;
        	int count2 = 0;
        	int count_week;
        	int day_contract;


        	int i1 = 0;
        	int i2 = 0;
        	int i3 = 0;




        FileReader fr = new FileReader(txtFileName);
        BufferedReader brtxt = new BufferedReader(fr);
        String line ="";

        String[] filename = txtFileName.split("\\.");



     	File file = new File(filename[0]  + 	"_gcheck.txt");
     	PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(file)));


        while ((line = brtxt.readLine()) != null) {

        	Index = line;

        	length_konma = Index.length();

    		for(i1=0;i1<length_konma;i1++){
                String a = Index.substring(i1,i1+1);
                if(count_if == 2 && !(a.equals(",")) && count1 == 0){//時間を取り出すプログラム
                	i2 = i1;
                	count1++;
                }
                if(count_if == 3 && count1 != 0 && count2 == 0){
                	i3 = i1-1;
                	//System.out.println(i2 + " " + i3);
                	count2++;
                	String d1 = Index.substring(i2,i3-4);
                	String d2 = Index.substring(i2+4,i3-2);
                	String d3 = Index.substring(i2+6,i3);

                	year = Integer.parseInt(d1);//年
                	month = Integer.parseInt(d2);//月
                	day =  Integer.parseInt(d3);//日にち
                	//System.out.println(year + " " + month + " " + day);


                }

                if(a.equals(",")){
                    count_if++;
                }
            }
    		count_if = 0;
    		count1 = 0;
    		count2 = 0;

    		i2 = 0;
            i3 = 0;

            Calendar calendar = Calendar.getInstance();
            //calendar.set(Calendar.YEAR, year);
            calendar.set(year,month-1,1);
            //int a = calendar.get(Calendar.YEAR);//年の取得
            //int b =calendar.get(Calendar.MONTH) + 1;//月の取得
            //int c = calendar.get(Calendar.DATE);//日付の取得
            int week = calendar.get(Calendar.DAY_OF_WEEK);//曜日の取得(0:日曜日,1:月曜日,2:火曜日...)

            count_week = 5;
            day_contract = 1;
            for(int i=1;i<=6;i++){
            	if(week == i){
            		day_contract = day_contract + count_week + 7;//日付の決定
            	}
            	count_week--;
            }
            if(week == 7){
        		day_contract = day_contract + 6 + 7;//日付の決定
        	}




            /*if(day >= day_contract){//限月処理　その２　JNc1のとき
            	pw.println(Index);
            }*/

            if(day < day_contract){//限月処理　その１　JNc2のとき
        	pw.println(Index);
            }


        }


        brtxt.close();
        fr.close();
        pw.close();

        }
    }
}


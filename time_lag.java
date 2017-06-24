import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
//comb2ファイルから日経NEEDSとコンマ秒の時間差を抽出するプログラム
public class time_lag{

    public static void main(String[] args) throws IOException{

        BufferedReader br = new BufferedReader(new FileReader("../data/filelist.txt"));//読み取りたいファイル名の記入
        String txtFileName;

        while((txtFileName = br.readLine()) != null) {

        	String Index;
        	double hour1 =0.0;
        	double minute1 =0.0;
        	double second1 =0.0;
        	double hour2;
        	double minute2;
        	double second2;

        	double n_all_time = 0;
        	double k_all_time;
        	double sum_difference = 0;
        	double difference;
        	int number = 0;
        	double count = 0;
        	String day;
        	String time;




        	double average = 0;


        	FileReader fr = new FileReader("../data/" + txtFileName);
            BufferedReader brtxt = new BufferedReader(fr);
            String line ="";

            String[] filename = txtFileName.split("\\.");

         	File file = new File("../data/" + filename[0] +	"_var.txt");
         	PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(file)));

            while ((line = brtxt.readLine()) != null) {

            	Index = line;

            	String d0 = Index.substring(0,1);

            	if(!(d0.equals("-"))){
            		String d1 = Index.substring(30,32);
                	hour1 = Double.parseDouble(d1);
                	String d2 = Index.substring(32,34);
                	minute1 = Double.parseDouble(d2);
                	String d3 = Index.substring(36,38);
                	second1 =  Double.parseDouble(d3);
                	n_all_time = hour1*60*60 + minute1*60 + second1;
                	//System.out.println(d1 + " " + d2 + " " + d3);
            	}

            	//System.out.println(hour1 + " " + minute1 + " " + second1);


            	/*String d4 = Index.substring(85,87);
            	hour2 = Double.parseDouble(d4);
            	String d5 = Index.substring(88,90);
            	minute2 = Double.parseDouble(d5);
            	String d6 = Index.substring(91,97);
            	second2 = Double.parseDouble(d6);
            	day = Index.substring(76,84);
            	time = Index.substring(85,97);//2006年～2010年*/

            	/*String d4 = Index.substring(88,90);
            	hour2 = Double.parseDouble(d4);
            	String d5 = Index.substring(91,93);
            	minute2 = Double.parseDouble(d5);
            	String d6 = Index.substring(94,100);
            	second2 = Double.parseDouble(d6);
            	day = Index.substring(76,87);
            	time = Index.substring(88,100);*///2011年～

            	String d4 = Index.substring(91,93);
            	hour2 = Double.parseDouble(d4);
            	String d5 = Index.substring(94,96);
            	minute2 = Double.parseDouble(d5);
            	String d6 = Index.substring(97,106);
            	second2 = Double.parseDouble(d6);
            	day = Index.substring(82,90);
            	time = Index.substring(91,106);//2016年取得ロイター通信社のデータ

            	//System.out.println(hour2 + " " + minute2 + " " + second2);



            	k_all_time = hour2*60*60 + minute2*60 + second2;


            	sum_difference += k_all_time - n_all_time;//時間差を加算

            	difference =  k_all_time - n_all_time;

            	pw.println(day + " " + time + " " +difference);

            	number++;
            	count++;




            	}

            average = sum_difference/count;//平均を求めている。

            for(int n = 0;n<=number;n++){

            }

            System.out.println(average);

            sum_difference = 0;
            number = 0;




            brtxt.close();
            fr.close();
            pw.close();

        	// txtファイル名を一行ずつロードする
        }
        br.close();

    }



}



import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
//JNc_2006_gcheck_data_from0227_oyaorder_micro_time18.txtとかから、取引回数や出来高を算出するプログラム

public class JNc_number_extraction{

    public static void main(String[] args) throws IOException {

    	String Index = null;

    	BufferedReader br = new BufferedReader(new FileReader("filelist_number.txt"));//読み取りたいファイル名の記入
        String txtFileName;

        while((txtFileName = br.readLine()) != null) {
        	//String Index[] = new String[400000];



        	int day = 0;//日付
        	int count_number_trade = 0;//取引回数
        	int count_volume = 0;//出来高

            FileReader fr = new FileReader(txtFileName);
            BufferedReader brtxt = new BufferedReader(fr);
            String line ="";

            String[] filename = txtFileName.split("\\.");

         	File file = new File(filename[0] + "_number.csv");//時間差に0を含むときは0を記入
         	PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(
         			//"F:\\個別株\\TICST120\\201602\\" +
         	file)));


            while ((line = brtxt.readLine()) != null) {


            	String JNc_split[] = line.split(" ", 0);




            	if(day == 0){
            		day = Integer.parseInt(JNc_split[0]);
            		count_number_trade++;
            	}
            	else if(day != Integer.parseInt(JNc_split[0])){
            		pw.println(day + "," + count_number_trade + "," + count_volume);

            		day = Integer.parseInt(JNc_split[0]);

            		for(int i = 2; i < JNc_split.length;i++){
                		System.out.println(JNc_split[i]);
                		count_volume += Integer.parseInt(JNc_split[i]);
                	}

            		count_number_trade = 1;
            	}
            	else if(day == Integer.parseInt(JNc_split[0])){
            		count_number_trade++;
            	}

            	for(int i = 2; i < JNc_split.length;i++){
            		//System.out.println(JNc_split[i]);
            		count_volume += Integer.parseInt(JNc_split[i]);
            	}



            }

            pw.println(day + "," + count_number_trade + "," + count_volume);






    		 brtxt.close();
             fr.close();
             pw.close();
        }
        br.close();
    }
}














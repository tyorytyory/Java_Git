import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
//RV_source_extraction.java と RV_source_extraction_nakane.java により抜き出したデータから、RVを算出するプログラム
public class RV_calc{

    public static void main(String[] args) throws IOException{




        BufferedReader br = new BufferedReader(new FileReader("filelist_rv_calc.txt"));//読み取りたいファイル名の記入
        String txtFileName;

        while((txtFileName = br.readLine()) != null) {

        	FileReader fr = new FileReader(txtFileName);
            BufferedReader brtxt = new BufferedReader(fr);
            String line ="";

            double P_one = 0;//Pi-1
            double P_two = 0;//Pi
            double P_sum = 0;//sum
            boolean error = false;//数値がないときに出力するプログラム
            int day = 0;
            String[] filename = txtFileName.split("\\.");

         	File file_rv = new File(filename[0] + "_calc.csv");
          	PrintWriter pw_rv = new PrintWriter(new BufferedWriter(new FileWriter(file_rv)));


            while ((line = brtxt.readLine()) != null) {


            	String JNIc_split[] = line.split(",", 0);


            	if(day == 0){//(約定値ver)ここから
            		day = Integer.parseInt(JNIc_split[0]);
            		P_one = Math.log(Integer.parseInt(JNIc_split[3]));
            	}
            	else if(day == Integer.parseInt(JNIc_split[0]) && Integer.parseInt(JNIc_split[3]) == 0){
            		error = true;
            		P_sum = 0;
            	}
            	else if(day == Integer.parseInt(JNIc_split[0]) && error == false){
            		P_two = Math.log(Integer.parseInt(JNIc_split[3]));
            		P_sum += P_two - P_one;
            		P_one = Math.log(Integer.parseInt(JNIc_split[3]));
            	}
            	else if(day != Integer.parseInt(JNIc_split[0])){
            		if(error == true){
            			pw_rv.println(day + ",NaN");
            		}
            		else{
            			pw_rv.println(day + "," + P_sum);
            		}
            		P_one = Math.log(Integer.parseInt(JNIc_split[3]));
            		day = Integer.parseInt(JNIc_split[0]);
            		error = false;
            		P_sum = 0;
            		if(Integer.parseInt(JNIc_split[3]) == 0){
                		error = true;
                	}
            	}//(約定値ver)ここまで

            	//System.out.println(line);

            	/*if(day == 0){//(仲値ver)ここから
            		day = Integer.parseInt(JNIc_split[0]);
            		P_one = Math.log((Integer.parseInt(JNIc_split[2]) + Integer.parseInt(JNIc_split[4])) / 2);
            	}
            	else if(day == Integer.parseInt(JNIc_split[0]) && Integer.parseInt(JNIc_split[3]) == 0){
            		error = true;
            		P_sum = 0;
            	}
            	else if(day == Integer.parseInt(JNIc_split[0]) && error == false){
            		P_two = Math.log((Integer.parseInt(JNIc_split[2]) + Integer.parseInt(JNIc_split[4])) / 2);
            		P_sum += P_two - P_one;
            		P_one = Math.log((Integer.parseInt(JNIc_split[2]) + Integer.parseInt(JNIc_split[4])) / 2);
            	}
            	else if(day != Integer.parseInt(JNIc_split[0])){
            		if(error == true){
            			pw_rv.println(day + ",NaN");
            		}
            		else{
            			pw_rv.println(day + "," + P_sum);
            		}
            		//System.out.println(day + "," + error);
            		P_one = Math.log((Integer.parseInt(JNIc_split[2]) + Integer.parseInt(JNIc_split[4])) / 2);
            		day = Integer.parseInt(JNIc_split[0]);
            		error = false;
            		P_sum = 0;
            		if(Integer.parseInt(JNIc_split[3]) == 0){
                		error = true;
                	}
            	}//(仲値ver)ここまで*/

            }

            if(error == true){
    			pw_rv.println(day + ",NaN");
    		}
    		else{
    			pw_rv.println(day + "," + P_sum);
    		}


            brtxt.close();
            fr.close();
            pw_rv.close();



        }


        br.close();
    }
}


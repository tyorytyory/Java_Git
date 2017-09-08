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
            boolean morning_or_afternoon = false;
            String  JNIc_split[] = null;

            String[] filename = txtFileName.split("\\.");

         	File file_rv = new File(filename[0] + "_calc.csv");
          	PrintWriter pw_rv = new PrintWriter(new BufferedWriter(new FileWriter(file_rv)));

          	pw_rv.println("day,divide,RV");


            while ((line = brtxt.readLine()) != null) {

            	JNIc_split = line.split(",", 0);

            	double hour = Double.parseDouble(JNIc_split[1].substring(0, 2));
            	double minute = Double.parseDouble(JNIc_split[1].substring(3, 5));
            	double second = Double.parseDouble(JNIc_split[1].substring(6));
            	double time_total = hour*3600 + minute*60 + second;





            	/*if(day == 0){//(約定値ver)ここから
            		day = Integer.parseInt(JNIc_split[0]);
            		P_one = Math.log(Integer.parseInt(JNIc_split[3]));
            	}
            	else if(day != Integer.parseInt(JNIc_split[0]) || (45000 <= time_total && day < 20110214 && morning_or_afternoon == false)){
            		if(morning_or_afternoon == false && day < 20110214 && day == Integer.parseInt(JNIc_split[0])){//前場の書き込み
            			if(error == true){
                			pw_rv.println(day + ",morning,NaN");
                		}
                		else{
                			pw_rv.println(day + ",morning," + P_sum);
                		}
            			morning_or_afternoon = true;

            		}
            		else if(morning_or_afternoon == false && day < 20110214 && day != Integer.parseInt(JNIc_split[0])){//半日オークションの場合
            			if(error == true){
                			pw_rv.println(day + ",morning,NaN");
                		}
                		else{
                			pw_rv.println(day + ",morning," + P_sum);
                		}
            			morning_or_afternoon = false;

            		}
            		else if(morning_or_afternoon == true && day < 20110214){//後場の書き込み

            			if(error == true){
                			pw_rv.println(day + ",afternoon,NaN");
                		}
                		else{
                			pw_rv.println(day + ",afternoon," + P_sum);
                		}
            			morning_or_afternoon = false;
            		}
            		else if(20110214 <= day){//昼休みが廃止されたとき
            			if(error == true){
                			pw_rv.println(day + ",no noon recess,NaN");
                		}
                		else{
                			pw_rv.println(day + ",no noon recess," + P_sum);
                		}
            		}


            		P_one = Math.log(Integer.parseInt(JNIc_split[3]));
            		day = Integer.parseInt(JNIc_split[0]);
            		error = false;
            		P_sum = 0;
            		if(Integer.parseInt(JNIc_split[3]) == 0){
                		error = true;
                	}
            	}
            	else if(day == Integer.parseInt(JNIc_split[0]) && Integer.parseInt(JNIc_split[3]) == 0){
            		error = true;
            		P_sum = 0;
            	}
            	else if(day == Integer.parseInt(JNIc_split[0]) && error == false){
            		P_two = Math.log(Integer.parseInt(JNIc_split[3]));
            		P_sum += P_two - P_one;
            		P_one = Math.log(Integer.parseInt(JNIc_split[3]));
            	}//(約定値ver)ここまで*/



            	//System.out.println(line);

            	if(day == 0){//(仲値ver)ここから
            		day = Integer.parseInt(JNIc_split[0]);
            		P_one = Math.log((Integer.parseInt(JNIc_split[2]) + Integer.parseInt(JNIc_split[4])) / 2);
            	}
            	else if(day != Integer.parseInt(JNIc_split[0]) || (45000 <= time_total && day < 20110214 && morning_or_afternoon == false)){
            		if(morning_or_afternoon == false && day < 20110214 && day == Integer.parseInt(JNIc_split[0])){//前場の書き込み
            			if(error == true){
                			pw_rv.println(day + ",morning,NaN");
                		}
                		else{
                			pw_rv.println(day + ",morning," + P_sum);
                		}
            			morning_or_afternoon = true;

            		}
            		else if(morning_or_afternoon == false && day < 20110214 && day != Integer.parseInt(JNIc_split[0])){//半日オークションの場合
            			if(error == true){
                			pw_rv.println(day + ",morning,NaN");
                		}
                		else{
                			pw_rv.println(day + ",morning," + P_sum);
                		}
            			morning_or_afternoon = false;

            		}
            		else if(morning_or_afternoon == true && day < 20110214){//後場の書き込み

            			if(error == true){
                			pw_rv.println(day + ",afternoon,NaN");
                		}
                		else{
                			pw_rv.println(day + ",afternoon," + P_sum);
                		}
            			morning_or_afternoon = false;
            		}
            		else if(20110214 <= day){//昼休みが廃止されたとき
            			if(error == true){
                			pw_rv.println(day + ",no noon recess,NaN");
                		}
                		else{
                			pw_rv.println(day + ",no noon recess," + P_sum);
                		}
            		}
            		P_one = Math.log((Integer.parseInt(JNIc_split[2]) + Integer.parseInt(JNIc_split[4])) / 2);
            		day = Integer.parseInt(JNIc_split[0]);
            		error = false;
            		P_sum = 0;
            		if(Integer.parseInt(JNIc_split[3]) == 0){
                		error = true;
                	}
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
            	}//(仲値ver)ここまで

            }

            if(morning_or_afternoon == false && day < 20110214 && day == Integer.parseInt(JNIc_split[0])){//前場の書き込み
    			if(error == true){
        			pw_rv.println(day + ",morning,NaN");
        		}
        		else{
        			pw_rv.println(day + ",morning," + P_sum);
        		}
    			morning_or_afternoon = true;

    		}
    		else if(morning_or_afternoon == false && day < 20110214 && day != Integer.parseInt(JNIc_split[0])){//半日オークションの場合
    			if(error == true){
        			pw_rv.println(day + ",morning,NaN");
        		}
        		else{
        			pw_rv.println(day + ",morning," + P_sum);
        		}
    			morning_or_afternoon = false;

    		}
    		else if(morning_or_afternoon == true && day < 20110214){//後場の書き込み

    			if(error == true){
        			pw_rv.println(day + ",afternoon,NaN");
        		}
        		else{
        			pw_rv.println(day + ",afternoon," + P_sum);
        		}
    			morning_or_afternoon = false;
    		}
    		else if(20110214 <= day){//昼休みが廃止されたとき
    			if(error == true){
        			pw_rv.println(day + ",no noon recess,NaN");
        		}
        		else{
        			pw_rv.println(day + ",no noon recess," + P_sum);
        		}
    		}


            brtxt.close();
            fr.close();
            pw_rv.close();



        }


        br.close();
    }
}


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

          	//pw_rv.println("day,divide,RV");

          	

            while ((line = brtxt.readLine()) != null) {

            	JNIc_split = line.split(",", 0);

            	double hour = Double.parseDouble(JNIc_split[1].substring(0, 2));
            	double minute = Double.parseDouble(JNIc_split[1].substring(3, 5));
            	double second = Double.parseDouble(JNIc_split[1].substring(6));
            	double time_total = hour*3600 + minute*60 + second;





            	if(day == 0){//(約定値ver)ここから
            		day = Integer.parseInt(JNIc_split[0]);

            		pw_rv.print(day + ",");
            		
            		if(JNIc_split[2].equals("NaN")){
            			pw_rv.print("NaN,");//時間ごとの計算結果を記述するように変更されている。
            		}
            		else{
            			P_one = Math.log(Integer.parseInt(JNIc_split[3]));
            		}
            	}
            	else if(day != Integer.parseInt(JNIc_split[0]) || (45000 <= time_total && day < 20110214 && morning_or_afternoon == false)){
            		if(morning_or_afternoon == false && day < 20110214 && day == Integer.parseInt(JNIc_split[0])){//前場の書き込み
            			pw_rv.print(day + ",morning," + P_sum + ",");//時間ごとの計算結果を記述するように変更されている。

            			morning_or_afternoon = true;

            		}
            		else if(morning_or_afternoon == false && day < 20110214 && day != Integer.parseInt(JNIc_split[0])){//半日オークションの場合
            			pw_rv.println(day + ",morning," + P_sum);
            			

            			morning_or_afternoon = false;
            			
            			day = Integer.parseInt(JNIc_split[0]);
            			pw_rv.print(day + ",");

            		}
            		else if(morning_or_afternoon == true && day < 20110214){//後場の書き込み

            			pw_rv.println(day + ",afternoon," + P_sum);

            			morning_or_afternoon = false;
            			
            			day = Integer.parseInt(JNIc_split[0]);
            			pw_rv.print(day + ",");
            		}
            		else if(20110214 <= day){//昼休みが廃止されたとき
            			pw_rv.println(day + ",no noon recess," + P_sum);
            			
            			day = Integer.parseInt(JNIc_split[0]);
            			pw_rv.print(day + ",");

            		}



            		

            		P_sum = 0;

            		if(JNIc_split[2].equals("NaN")){
            			pw_rv.print("NaN,");//時間ごとの計算結果を記述するように変更されている。
            			P_one = 0;
            		}
            		else{
            			P_one = Math.log(Integer.parseInt(JNIc_split[3]));
            		}
            	}
            	else if(day == Integer.parseInt(JNIc_split[0]) && error == false){

            		if(JNIc_split[2].equals("NaN")){
            			pw_rv.print("NaN,");//時間ごとの計算結果を記述するように変更されている。
            		}
            		else{
            			if(P_one == 0){
            				P_one = Math.log(Integer.parseInt(JNIc_split[3]));
            			}
            			else{
            				P_two = Math.log(Integer.parseInt(JNIc_split[3]));
            				pw_rv.print(Math.pow(P_two - P_one,2) + ",");//時間ごとの計算結果を記述するように変更されている。
            				P_sum += Math.pow(P_two - P_one,2);

                    		P_one = Math.log(Integer.parseInt(JNIc_split[3]));
            			}

            		}

            	}//(約定値ver)ここまで



            	

            	/*if(day == 0){//(仲値ver)ここから
            		day = Integer.parseInt(JNIc_split[0]);
            		
            		pw_rv.print(day + ",");
            		
            		if(JNIc_split[2].equals("NaN")){

            		}
            		else{
            			System.out.println(Double.parseDouble(JNIc_split[2]));
            			
            			P_one = Math.log((Double.parseDouble(JNIc_split[2]) + Double.parseDouble(JNIc_split[4])) / 2);
            		}

            	}
            	else if(day != Integer.parseInt(JNIc_split[0]) || (45000 <= time_total && day < 20110214 && morning_or_afternoon == false)){
            		if(morning_or_afternoon == false && day < 20110214 && day == Integer.parseInt(JNIc_split[0])){//前場の書き込み
            			pw_rv.print(day + ",morning," + P_sum + ",");
            			
            			

            			morning_or_afternoon = true;

            		}
            		else if(morning_or_afternoon == false && day < 20110214 && day != Integer.parseInt(JNIc_split[0])){//半日オークションの場合
            			pw_rv.println(day + ",morning," + P_sum);
            			
            			day = Integer.parseInt(JNIc_split[0]);
            			
            			pw_rv.print(day + ",");
            			
            			morning_or_afternoon = false;

            		}
            		else if(morning_or_afternoon == true && day < 20110214){//後場の書き込み

            			pw_rv.println(day + ",afternoon," + P_sum);
            			
            			day = Integer.parseInt(JNIc_split[0]);
            			
            			pw_rv.print(day + ",");

            			morning_or_afternoon = false;
            		}
            		else if(20110214 <= day){//昼休みが廃止されたとき
            			pw_rv.println(day + ",no noon recess," + P_sum);
            			
            			day = Integer.parseInt(JNIc_split[0]);
            			
            			pw_rv.print(day + ",");

            		}
            		if(JNIc_split[2].equals("NaN")){
            			pw_rv.print("NaN,");//時間ごとの計算結果を記述するように変更されている。
            			P_one = 0;
            		}
            		else{
            			P_one = Math.log((Double.parseDouble(JNIc_split[2]) + Double.parseDouble(JNIc_split[4])) / 2);
            		}
            		

            		P_sum = 0;

            	}
            	else if(day == Integer.parseInt(JNIc_split[0]) && error == false){
            		if(JNIc_split[2].equals("NaN")){
            			pw_rv.print("NaN,");//時間ごとの計算結果を記述するように変更されている。
            		}
            		else{
            			if(P_one == 0){
            				P_one = Math.log((Double.parseDouble(JNIc_split[2]) + Double.parseDouble(JNIc_split[4])) / 2);
            			}
            			else{
            				P_two = Math.log((Double.parseDouble(JNIc_split[2]) + Double.parseDouble(JNIc_split[4])) / 2);
            				pw_rv.print(Math.pow(P_two - P_one,2) + ",");//時間ごとの計算結果を記述するように変更されている。
            				P_sum += Math.pow(P_two - P_one,2);

                    		P_one = Math.log((Double.parseDouble(JNIc_split[2]) + Double.parseDouble(JNIc_split[4])) / 2);
            			}

            		}

            	}
            	//(仲値ver)ここまで*/
            	 
            	 

            }

            if(morning_or_afternoon == false && day < 20110214 && day == Integer.parseInt(JNIc_split[0])){//前場の書き込み
    			pw_rv.println(day + ",morning," + P_sum);

    			morning_or_afternoon = true;

    		}
    		else if(morning_or_afternoon == false && day < 20110214 && day != Integer.parseInt(JNIc_split[0])){//半日オークションの場合
    			pw_rv.println(day + ",morning," + P_sum);

    			morning_or_afternoon = false;

    		}
    		else if(morning_or_afternoon == true && day < 20110214){//後場の書き込み

    			pw_rv.println(day + ",afternoon," + P_sum);

    			morning_or_afternoon = false;
    		}
    		else if(20110214 <= day){//昼休みが廃止されたとき
    			pw_rv.println(day + ",no noon recess," + P_sum);

    		}


            brtxt.close();
            fr.close();
            pw_rv.close();



        }


        br.close();
    }
}


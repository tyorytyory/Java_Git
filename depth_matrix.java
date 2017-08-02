import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class depth_matrix{
//JNIc_limit_order.javaから作成したdepthファイルから、板の移動回数や最初の板の枚数を計算するプログラム

    public static void main(String[] args) throws IOException{

        BufferedReader br = new BufferedReader(new FileReader("filelist_depth.txt"));//読み取りたいファイル名の記入
        String txtFileName;


        double hour = 0;//時間
        double minute = 0;//分
        double second = 0;//秒
        double time_total = 0;//累積時間（秒）





        while((txtFileName = br.readLine()) != null) {

            String day = null;



        	FileReader fr = new FileReader(txtFileName);
            BufferedReader brtxt = new BufferedReader(fr);
            String line ="";

            String[] filename = txtFileName.split("\\.");

            File file = new File(filename[0] + "_matrix.csv");
         	PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(file)));

         	pw.println(day + ",morning,count_up_up,count_up_down,count_down_up,count_down_down,,,,");

         	String  JNIc_split[] = null;

         	int before_move_condition = 0;
         	int now_move_condition = 0;
         	int count_up_up = 0;
         	int count_up_down = 0;
         	int count_down_down = 0;
         	int count_down_up = 0;
         	boolean morning_or_afternoon = false;


            while ((line = brtxt.readLine()) != null) {

            	JNIc_split = line.split(",", 0);

            	hour = Double.parseDouble(JNIc_split[1].substring(0, 2));
            	minute = Double.parseDouble(JNIc_split[1].substring(3, 5));
            	second = Double.parseDouble(JNIc_split[1].substring(6));
            	time_total = hour*3600 + minute*60 + second;



            	if(day == null){
            		day = JNIc_split[0];
            	}
            	else if(!(day.equals(JNIc_split[0])) || (45000 <= time_total && Integer.parseInt(day) < 20110214 && morning_or_afternoon == false)){
            		if(morning_or_afternoon == false && Integer.parseInt(day) < 20110214 && day.equals(JNIc_split[0])){
            			pw.println(day + ",morning," + count_up_up + "," + count_up_down + "," + count_down_up + "," + count_down_down + ",,,,,,,");
            			morning_or_afternoon = true;
            		}
            		else if(morning_or_afternoon == false && Integer.parseInt(day) < 20110214 && !(day.equals(JNIc_split[0]))){
            			pw.println(day + ",morning," + count_up_up + "," + count_up_down + "," + count_down_up + "," + count_down_down + ",,,,,,,");
            			morning_or_afternoon = false;
            		}
            		else if(morning_or_afternoon == true && Integer.parseInt(day) < 20110214){
            			pw.println(day + ",afternoon," + count_up_up + "," + count_up_down + "," + count_down_up + "," + count_down_down + ",,,,,,,");
            			morning_or_afternoon = false;
            			System.out.println(line);
            		}
            		else if(20110214 <= Integer.parseInt(day)){
            			pw.println(day + ",no noon recess," + count_up_up + "," + count_up_down + "," + count_down_up + "," + count_down_down + ",,,,,,,");
            		}
            		day = JNIc_split[0];
            		before_move_condition = 0;
            		count_up_up = 0;
            		count_up_down = 0;
            		count_down_up = 0;
            		count_down_down = 0;

            	}

            	if(before_move_condition == 0){
            		if(JNIc_split[2].equals("up") || JNIc_split[2].equals("up not Trade")){//買板・売板の上昇
            			before_move_condition = 1;

                	}
                	else if(JNIc_split[2].equals("down") || JNIc_split[2].equals("down not Trade")){//買板・売板の下落
                		now_move_condition = 2;
                	}
            	}
            	else if(before_move_condition == 1 && (JNIc_split[2].equals("up") || JNIc_split[2].equals("up not Trade"))){
            		count_up_up++;
            		before_move_condition = 1;
            	}
            	else if(before_move_condition == 1 && (JNIc_split[2].equals("down") || JNIc_split[2].equals("down not Trade"))){
            		count_up_down++;
            		before_move_condition = 2;
            	}
            	else if(before_move_condition == 2 && (JNIc_split[2].equals("up") || JNIc_split[2].equals("up not Trade"))){
            		count_down_up++;
            		before_move_condition = 1;
            	}
            	else if(before_move_condition == 2 && (JNIc_split[2].equals("down") || JNIc_split[2].equals("down not Trade"))){
            		count_down_down++;
            		before_move_condition = 2;
            	}


            }

            if(morning_or_afternoon == false && Integer.parseInt(day) < 20110214){
    			pw.println(day + ",morning," + count_up_up + "," + count_up_down + "," + count_down_up + "," + count_down_down + ",,,,,,,");
    			morning_or_afternoon = true;
    		}
    		else if(morning_or_afternoon == true && Integer.parseInt(day) < 20110214){
    			pw.println(day + ",afternoon," + count_up_up + "," + count_up_down + "," + count_down_up + "," + count_down_down + ",,,,,,,");
    			morning_or_afternoon = false;
    		}
    		else if(20110214 <= Integer.parseInt(day)){
    			pw.println(day + ",no noon recess," + count_up_up + "," + count_up_down + "," + count_down_up + "," + count_down_down + ",,,,,,,");
    		}



            brtxt.close();
            fr.close();
            pw.close();





        }

        br.close();
    }
}


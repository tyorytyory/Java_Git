import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class JNIc_ita_data{
//JNIc_limit_order.javaから作成したdepthファイルから、板の移動回数や最初の板の枚数を計算するプログラム
////(inlcude only)は「買板だけ下落し、その後売板だけが下落する」「売板だけ上昇し、その後買板だけが上昇する(inlcude only)」を考慮したいとき、if文を挿入する。

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

            File file = new File(filename[0] + "_ita_move.csv");
         	PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(file)));



         	String  JNIc_split[] = null;

         	int before_move_condition = 0;//直前の板の移動の情報を書き込む


         	boolean morning_or_afternoon = false;//前場(false) or 後場(true)


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

            		//------------初期化--------------

            		before_move_condition = 0;

            		//------------初期化--------------
            	}





            	if(JNIc_split[2].equals("up") || JNIc_split[2].equals("up not Trade")){//板が上昇した後の、板の上昇
            		pw.println(line + "," + "+1");
            	}
            	else if(JNIc_split[2].equals("down") || JNIc_split[2].equals("down not Trade")){//板が上昇した後の、板の下落
            		pw.println(line + "," + "-1");
            	}
            	else if(JNIc_split[2].equals("down only bid") || JNIc_split[2].equals("down only bid not Trade")){//(inlcude only)
            		before_move_condition = 1;
            	}
            	else if(JNIc_split[2].equals("up only ask") || JNIc_split[2].equals("up only ask not Trade")){//(inlcude only)
            		before_move_condition = 2;
            	}
            	else if(before_move_condition == 1 && JNIc_split[2].equals("down only ask") || JNIc_split[2].equals("down only ask not Trade")){//買板だけ下落し、その後売板だけが下落する(inlcude only)
            		before_move_condition = 0;
            		pw.println(line + "," + "-1");
            	}
            	else if(before_move_condition == 2 && JNIc_split[2].equals("up only bid") || JNIc_split[2].equals("up only bid not Trade")){//売板だけ上昇し、その後買板だけが上昇する(inlcude only)
            		before_move_condition = 0;
            		pw.println(line + "," + "+1");
            	}
            	else{
            		before_move_condition = 0;

            	}


            }





            brtxt.close();
            fr.close();
            pw.close();





        }

        br.close();
    }
}


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class depth_count{
//JNIc_limit_order.javaから作成したdepthファイルから、板の移動回数や最初の板の枚数を計算するプログラム

    public static void main(String[] args) throws IOException{

        BufferedReader br = new BufferedReader(new FileReader("filelist_depth.txt"));//読み取りたいファイル名の記入
        String txtFileName;


        double hour = 0;//時間
        double minute = 0;//分
        double second = 0;//秒
        double time_total = 0;//累積時間（秒）
        int up_count = 0;//板が上昇した回数（2011/2/14以降）
        int up_count_morning = 0;//板が上昇した回数（前場）
        int up_count_afternoon = 0;//板が上昇した回数（後場）
        int up_bid_depth_count = 0;//板が上昇したときの累積買板枚数(2011/2/14以降)
        int up_bid_depth_count_morning = 0;//板が上昇したときの累積買板枚数(前場）
        int up_bid_depth_count_afternoon = 0;//板が上昇したときの累積買板枚数（後場）
        int up_ask_depth_count = 0;//板が上昇したときの累積売板枚数(2011/2/14以降)
        int up_ask_depth_count_morning = 0;//板が上昇したときの累積売板枚数(前場）
        int up_ask_depth_count_afternoon = 0;//板が上昇したときの累積売板枚数（後場）
        int down_count = 0;//板が下落した回数（2011/2/14以降）
        int down_count_morning = 0;//板が下落した回数（前場）
        int down_count_afternoon = 0;//板が下落した回数（後場）
        int down_bid_depth_count = 0;//板が下落したときの累積買板枚数(2011/2/14以降)
        int down_bid_depth_count_morning = 0;//板が下落したときの累積買板枚数(前場）
        int down_bid_depth_count_afternoon = 0;//板が下落したときの累積買板枚数（後場）
        int down_ask_depth_count = 0;//板が下落したときの累積売板枚数(2011/2/14以降)
        int down_ask_depth_count_morning = 0;//板が下落したときの累積売板枚数(前場）
        int down_ask_depth_count_afternoon = 0;//板が下落したときの累積売板枚数（後場）



        while((txtFileName = br.readLine()) != null) {

            String day = null;



        	FileReader fr = new FileReader(txtFileName);
            BufferedReader brtxt = new BufferedReader(fr);
            String line ="";

            String[] filename = txtFileName.split("\\.");

            File file_up = new File(filename[0] + "_up_count.csv");
         	PrintWriter pw_up = new PrintWriter(new BufferedWriter(new FileWriter(file_up)));
         	File file_down = new File(filename[0] + "_down_count.csv");
         	PrintWriter pw_down = new PrintWriter(new BufferedWriter(new FileWriter(file_down)));

         	pw_up.println("day,divide,up or down,up_count,up_bid_depth_count,up_ask_depth_count,,,,,,");//ラベルの書き込み
         	pw_down.println("day,divide,up or down,down_count,down_bid_depth_count,down_ask_depth_count,,,,,,");//ラベルの書き込み


         	String  JNIc_split[] = null;


            while ((line = brtxt.readLine()) != null) {




            	JNIc_split = line.split(",", 0);

            	if(day == null){
            		day = JNIc_split[0];
            	}
            	else if(!(day.equals(JNIc_split[0]))){
            		if(Integer.parseInt(day) < 20110214){



            			if(up_count_morning != 0){//前場データの書き込み
                			pw_up.println(day + ",morning,up," + up_count_morning + "," + up_bid_depth_count_morning + "," + up_ask_depth_count_morning + ",,,,,");
                    		pw_down.println(day + ",morning,down," + down_count_morning + "," + down_bid_depth_count_morning + "," + down_ask_depth_count_morning + ",,,,,");
                		}
                		else if(up_count_morning == 0){//前場データがない場合

                			pw_up.println(day + ",morning,up,NaN,NaN,NaN,,,,,,");
                    		pw_down.println(day + ",morning,down,NaN,NaN,NaN,,,,,,");
                		}

                		if(up_count_afternoon != 0){//後場データの書き込み
                			pw_up.println(day + ",afternoon,up," + up_count_afternoon + "," + up_bid_depth_count_afternoon + "," + up_ask_depth_count_afternoon + ",,,,,");
                    		pw_down.println(day + ",afternoon,down," + down_count_afternoon + "," + down_bid_depth_count_afternoon + "," + down_ask_depth_count_afternoon + ",,,,,");
                		}
                		else if(up_count_afternoon == 0){//後場データがない場合
                			pw_up.println(day + ",afternoon,up,NaN,NaN,NaN,,,,,,");
                    		pw_down.println(day + ",afternoon,down,NaN,NaN,NaN,,,,,,");
                		}
            		}
            		else if(20110214 <= Integer.parseInt(day)){//2011/2/14以降
            			pw_up.println(day + ",no noon recess,up," + up_count + "," + up_bid_depth_count + "," + up_ask_depth_count + ",,,,,");
                		pw_down.println(day + ",no noon recess,down," + down_count + "," + down_bid_depth_count + "," + down_ask_depth_count + ",,,,,");
            		}

            		day = JNIc_split[0];

                    up_count = 0;
                    up_count_morning = 0;
                    up_count_afternoon = 0;
                    up_bid_depth_count = 0;
                    up_bid_depth_count_morning = 0;
                    up_bid_depth_count_afternoon = 0;
                    up_ask_depth_count = 0;
                    up_ask_depth_count_morning = 0;
                    up_ask_depth_count_afternoon = 0;
                    down_count = 0;
                    down_count_morning = 0;
                    down_count_afternoon = 0;
                    down_bid_depth_count = 0;
                    down_bid_depth_count_morning = 0;
                    down_bid_depth_count_afternoon = 0;
                    down_ask_depth_count = 0;
                    down_ask_depth_count_morning = 0;
                    down_ask_depth_count_afternoon = 0;

            	}

            	hour = Double.parseDouble(JNIc_split[1].substring(0, 2));
            	minute = Double.parseDouble(JNIc_split[1].substring(3, 5));
            	second = Double.parseDouble(JNIc_split[1].substring(6));
            	time_total = hour*3600 + minute*60 + second;

            	if((Integer.parseInt(JNIc_split[0]) < 20110214 && (time_total <= 40500 || 45000 <= time_total)) ||
            			(20110214 <= Integer.parseInt(JNIc_split[0]))){//昼休みを含まず、9:00-15:10の間のデータを取り除く

                	if(JNIc_split[2].equals("up") || JNIc_split[2].equals("up not Trade")){//買板・売板の上昇
                		if(Integer.parseInt(JNIc_split[0]) < 20110214 && time_total <= 40500){//前場
                			up_count_morning++;
                    		up_bid_depth_count_morning += Integer.parseInt(JNIc_split[4]);
                    		up_ask_depth_count_morning += Integer.parseInt(JNIc_split[6]);
                		}
                		else if(Integer.parseInt(JNIc_split[0]) < 20110214 && 45000 <= time_total){//後場
                			up_count_afternoon++;
                    		up_bid_depth_count_afternoon += Integer.parseInt(JNIc_split[4]);
                    		up_ask_depth_count_afternoon += Integer.parseInt(JNIc_split[6]);
                		}
                		else if(20110214 <= Integer.parseInt(JNIc_split[0])){//昼休みが廃止された
                			up_count++;
                    		up_bid_depth_count += Integer.parseInt(JNIc_split[4]);
                    		up_ask_depth_count += Integer.parseInt(JNIc_split[6]);
                		}
                	}
                	else if(JNIc_split[2].equals("down") || JNIc_split[2].equals("down not Trade")){//買板・売板の下落
                		if(Integer.parseInt(JNIc_split[0]) < 20110214 && time_total <= 40500){//前場
                			down_count_morning++;
                    		down_bid_depth_count_morning += Integer.parseInt(JNIc_split[4]);
                    		down_ask_depth_count_morning += Integer.parseInt(JNIc_split[6]);
                		}
                		else if(Integer.parseInt(JNIc_split[0]) < 20110214 && 45000 <= time_total){//後場
                			down_count_afternoon++;
                    		down_bid_depth_count_afternoon += Integer.parseInt(JNIc_split[4]);
                    		down_ask_depth_count_afternoon += Integer.parseInt(JNIc_split[6]);
                		}
                		else if(20110214 <= Integer.parseInt(JNIc_split[0])){//昼休みが廃止された
                			down_count++;
                    		down_bid_depth_count += Integer.parseInt(JNIc_split[4]);
                    		down_ask_depth_count += Integer.parseInt(JNIc_split[6]);
                		}
                	}


            	}
            	else{
            		System.out.println(line);
            	}

            }

            //------------------------------------最後の書き込み-----------------------------------------------
            if(Integer.parseInt(day) < 20110214){
    			if(up_count_morning != 0){//前場データの書き込み
        			pw_up.println(day + ",morning,up," + up_count_morning + "," + up_bid_depth_count_morning + "," + up_ask_depth_count_morning + ",,,,,");
            		pw_down.println(day + ",morning,down," + down_count_morning + "," + down_bid_depth_count_morning + "," + down_ask_depth_count_morning + ",,,,,");
        		}
        		else if(up_count_morning == 0){//前場データがない場合
        			pw_up.println(day + ",morning,up,NaN,NaN,NaN,,,,,,");
            		pw_down.println(day + ",morning,down,NaN,NaN,NaN,,,,,,");
        		}

        		if(up_count_afternoon != 0){//後場データの書き込み
        			pw_up.println(day + ",afternoon,up," + up_count_afternoon + "," + up_bid_depth_count_afternoon + "," + up_ask_depth_count_afternoon + ",,,,,");
            		pw_down.println(day + ",afternoon,down," + down_count_afternoon + "," + down_bid_depth_count_afternoon + "," + down_ask_depth_count_afternoon + ",,,,,");
        		}
        		else if(up_count_afternoon == 0){//後場データがない場合
        			pw_up.println(day + ",afternoon,up,NaN,NaN,NaN,,,,,,");
            		pw_down.println(day + ",afternoon,down,NaN,NaN,NaN,,,,,,");
        		}
    		}
    		else if(20110214 <= Integer.parseInt(day)){//2011/2/14以降
    			pw_up.println(day + ",no noon recess,up," + up_count + "," + up_bid_depth_count + "," + up_ask_depth_count + ",,,,,");
        		pw_down.println(day + ",no noon recess,down," + down_count + "," + down_bid_depth_count + "," + down_ask_depth_count + ",,,,,");
    		}


    		//------------------------------------最後の書き込み-----------------------------------------------



    		//---------------------------------------初期化-------------------------------------------
    		day = null;
            up_count = 0;
            up_count_morning = 0;
            up_count_afternoon = 0;
            up_bid_depth_count = 0;
            up_bid_depth_count_morning = 0;
            up_bid_depth_count_afternoon = 0;
            up_ask_depth_count = 0;
            up_ask_depth_count_morning = 0;
            up_ask_depth_count_afternoon = 0;
            down_count = 0;
            down_count_morning = 0;
            down_count_afternoon = 0;
            down_bid_depth_count = 0;
            down_bid_depth_count_morning = 0;
            down_bid_depth_count_afternoon = 0;
            down_ask_depth_count = 0;
            down_ask_depth_count_morning = 0;
            down_ask_depth_count_afternoon = 0;
            //---------------------------------------初期化-------------------------------------------








            brtxt.close();
            fr.close();
            pw_up.close();
            pw_down.close();




        }

        br.close();
    }
}


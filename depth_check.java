import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class depth_check{

    public static void main(String[] args) throws IOException{

        BufferedReader br = new BufferedReader(new FileReader("filelist.txt"));//読み取りたいファイル名の記入
        String txtFileName;

        int up_count = 0;
        int up_only_bid_count = 0;
        int up_only_ask_count = 0;
        int up_price_same_count = 0;
        int up_not10_count = 0;
        int up_else_count = 0;

        int down_count = 0;
        int down_only_bid_count = 0;
        int down_only_ask_count = 0;
        int down_price_same_count = 0;
        int down_not10_count = 0;
        int down_else_count = 0;

        int move_count = 0;



        while((txtFileName = br.readLine()) != null) {

        	String Index;




        	FileReader fr = new FileReader(txtFileName);
            BufferedReader brtxt = new BufferedReader(fr);
            String line ="";

            //String[] filename = txtFileName.split("\\_");


         	/*File file_depth = new File(filename[1].substring(0,6) + "_depth_sum.csv");
          	PrintWriter pw_depth = new PrintWriter(new BufferedWriter(new FileWriter(file_depth)));*/

            while ((line = brtxt.readLine()) != null) {

            	Index = line;


            	String JNIc_split[] = line.split(",", 0);

            	if(JNIc_split[2].equals("down") || JNIc_split[2].equals("down not Trade")){
            		down_count++;
            	}
            	else if(JNIc_split[2].equals("down price dif not 10") || JNIc_split[2].equals("down not Trade not 10") ){
            		down_not10_count++;
            	}
            	else if(JNIc_split[2].equals("down only ask") || JNIc_split[2].equals("down only ask not Trade")){
            		down_only_ask_count++;
            	}
            	else if(JNIc_split[2].equals("down only bid") || JNIc_split[2].equals("down only bid not Trade")){
            		down_only_bid_count++;
            	}
            	else if(JNIc_split[2].equals("down price same")){
            		down_price_same_count++;
            	}
            	else if(JNIc_split[2].equals("down else")){
            		down_else_count++;
            	}
            	else if(JNIc_split[2].equals("up") || JNIc_split[2].equals("up not Trade")){
            		up_count++;
            	}
            	else if(JNIc_split[2].equals("up price dif not 10") || JNIc_split[2].equals("up not Trade not 10") ){
            		up_not10_count++;
            	}
            	else if(JNIc_split[2].equals("up only ask") || JNIc_split[2].equals("up only ask not Trade")){
            		up_only_ask_count++;
            	}
            	else if(JNIc_split[2].equals("up only bid") || JNIc_split[2].equals("up only bid not Trade")){
            		up_only_bid_count++;
            	}
            	else if(JNIc_split[2].equals("up price same")){
            		up_price_same_count++;
            	}
            	else if(JNIc_split[2].equals("up else")){
            		up_else_count++;
            	}
            	else if(JNIc_split[2].equals("move")){
            		move_count++;
            	}




            }




            System.out.println(txtFileName);
            System.out.println("up," + up_count);
            System.out.println("up only bid," + up_only_bid_count);
            System.out.println("up only ask," + up_only_ask_count);
            System.out.println("up price same," + up_price_same_count);
            System.out.println("up price not 10," + up_not10_count);
            System.out.println("up else," + up_else_count);
            System.out.println("down," + down_count);
            System.out.println("down only bid," + down_only_bid_count);
            System.out.println("down only ask," + down_only_ask_count);
            System.out.println("down price same," + down_price_same_count);
            System.out.println("down price not 10," + down_not10_count);
            System.out.println("down else," + down_else_count);
            System.out.println("move," + move_count);



            up_count = 0;
            up_only_bid_count = 0;
            up_only_ask_count = 0;
            up_price_same_count = 0;
            up_not10_count = 0;
            up_else_count = 0;

            down_count = 0;
            down_only_bid_count = 0;
            down_only_ask_count = 0;
            down_price_same_count = 0;
            down_not10_count = 0;
            down_else_count = 0;

            move_count = 0;





            brtxt.close();
            fr.close();
            //pw.close();
            //pw_depth.close();


        }

        br.close();
    }
}


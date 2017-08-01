import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class depth_check{
//JNIc_limit_order.javaから作成したdepthファイルから、板の遷移の分布を書き出すプログラム

    public static void main(String[] args) throws IOException{

        BufferedReader br = new BufferedReader(new FileReader("filelist_depth.txt"));//読み取りたいファイル名の記入
        String txtFileName;

        int up_count = 0;//板がともに上昇したもの（価格差が10円）
        int up_only_bid_count = 0;//買板のみ上に移動した数を数える
        int up_only_ask_count = 0;//売板のみ上に移動した数を数える
        int up_price_same_count = 0;//板がともに上昇したものの価格が変わらなかったもの（約定の後だけ）を数える
        int up_not10_count = 0;//板がともに上昇した（価格差は20円以上）を数える
        int up_else_count = 0;//その他の上昇を数える

        int down_count = 0;//板がともに下降したもの（価格差が10円）を数える
        int down_only_bid_count = 0;//買板のみ下に移動した数を数える
        int down_only_ask_count = 0;//売板のみ下に移動した数を数える
        int down_price_same_count = 0;//板がともに下降したものの価格が変わらなかったもの（約定の後だけ）を数える
        int down_not10_count = 0;//板がともに下降した（価格差は20円以上）を数える
        int down_else_count = 0;//その他の下降を数える

        int up_only_ask = 0;//売板のみ上昇したことを示す変数（移動した→１、移動しなかった→０）
        int down_only_bid = 0;//買板のみ下降したことを示す変数（移動した→１、移動しなかった→０）

        int ask_up_bid_up_count = 0;//売板のみ上昇して、その後、買板のみ上昇したものを数える変数
        int ask_up_ask_down_count = 0;//売板のみ上昇して、その後、売板のみ下降したものを数える変数
        int ask_up_else_count = 0;//売板のみ上昇して、その後、上記に該当しない動きをしたものを数える変数

        int bid_down_ask_down_count = 0;//買板のみ下降して、その後、売板のみ下降したものを数える変数
        int bid_down_bid_up_count = 0;//買板のみ下降して、その後、買板のみ上昇したものを数える変数
        int bid_down_else_count = 0;


        int move_count = 0;//その他、わけのわからない移動をしたものを数える変数

        double hour = 0;//時間
        double minute = 0;//分
        double second = 0;//秒
        double time_total = 0;//時間を秒換算

        double only_ask_up_time = 0;//売板だけ上昇したときの時間
        double only_bid_down_time = 0;//買板だけ下降したときの時間

        double ask_up_bid_up_time_sum = 0;//売板のみ上昇して、その後買板のみが上昇するまでの時間の総和
        double ask_up_ask_down_time_sum = 0;//売板のみ上昇して、その後売板のみが下降するまでの時間の総和
        double ask_up_else_time_sum = 0;//売板のみ上昇して、その後上記に当てはまらない動きをするまでの時間の総和
        double bid_down_ask_down_time_sum = 0;//買板のみ下降して、その後売板のみが下降するまでの時間の総和
        double bid_down_bid_up_time_sum = 0;//買板のみ下降して、その後買板のみが上昇するまでの時間の総和
        double bid_down_else_time_sum = 0;//買板のみ下降して、その後上記に当てはまらない動きをするまでの時間の総和



        int ask_up_bid_up_dis[] = new int[20];//売板のみ上昇後、買板のみ上昇するまでにかかった時間の分布の変数
        int ask_up_ask_down_dis[] = new int[20];//売板のみ上昇後、売板のみ下降するまでにかかった時間の分布の変数
        int bid_down_ask_down_dis[] = new int[20];//買板のみ下降後、売板のみ下降するまでにかかった時間の分布の変数
        int bid_down_bid_up_dis[] = new int[20];//買板のみ下降後、買板のみ上昇するまでにかかった時間の分布の変数


        String day = "";

        while((txtFileName = br.readLine()) != null) {



        	FileReader fr = new FileReader(txtFileName);
            BufferedReader brtxt = new BufferedReader(fr);
            String line ="";


            while ((line = brtxt.readLine()) != null) {




            	String JNIc_split[] = line.split(",", 0);

            	hour = Double.parseDouble(JNIc_split[1].substring(0, 2));
            	minute = Double.parseDouble(JNIc_split[1].substring(3, 5));
            	second = Double.parseDouble(JNIc_split[1].substring(6));
            	//System.out.println(hour + "," + minute + "," + second);
            	time_total = hour*3600 + minute*60 + second;
            	//System.out.println(time_total);

            		//System.out.println(line);
            		if(day == ""){
                		day = JNIc_split[0];
                	}
                	else if(!(day.equals(JNIc_split[0]))){
                		day = JNIc_split[0];
                		up_only_ask = 0;
                		down_only_bid = 0;
                	}

                	if(up_only_ask == 1){
                		if(JNIc_split[2].equals("up only bid not Trade")){
                			ask_up_bid_up_count++;
                			ask_up_bid_up_time_sum += time_total - only_ask_up_time;

                			for(double i = 0;i<=9;i++){//板の移動の分布を格納するfor文
                				if(i <= time_total - only_ask_up_time && time_total - only_ask_up_time < i + 1){//iの条件により範囲を指定
                					ask_up_bid_up_dis[(int)(i)]++;
                				}
                			}
                			if(10 <= time_total - only_ask_up_time){//条件により範囲を指定
            					ask_up_bid_up_dis[10]++;
            				}
                		}
                		else if(JNIc_split[2].equals("down only ask not Trade")){
                			ask_up_ask_down_count++;
                			ask_up_ask_down_time_sum += time_total - only_ask_up_time;

                			for(double i = 0;i<=9;i++){//板の移動の分布を格納するfor文
                				if(i <= time_total - only_ask_up_time && time_total - only_ask_up_time < i + 1){//iの条件により範囲を指定
                					ask_up_ask_down_dis[(int)(i)]++;
                				}
                			}
                			if(10 <= time_total - only_ask_up_time){//条件により範囲を指定
            					ask_up_ask_down_dis[10]++;
            				}
                		}
                		else{
                			ask_up_else_count++;
                			ask_up_else_time_sum += time_total - only_ask_up_time;
                			//System.out.println(Index);
                		}
                		up_only_ask = 0;
                	}
                	if(down_only_bid == 1){
                		if(JNIc_split[2].equals("down only ask not Trade")){
                			bid_down_ask_down_count++;
                			bid_down_ask_down_time_sum += time_total - only_bid_down_time;
                			for(double i = 0;i<=9;i++){//板の移動の分布を格納するfor文
                				if(i <= time_total - only_bid_down_time && time_total - only_bid_down_time < i + 1){//iの条件により範囲を指定
                					bid_down_ask_down_dis[(int)(i)]++;
                				}
                			}
                			if(10 <= time_total - only_bid_down_time){//条件により範囲を指定
            					bid_down_ask_down_dis[10]++;
            				}
                		}
                		else if( JNIc_split[2].equals("up only bid not Trade")){
                			bid_down_bid_up_count++;
                			bid_down_bid_up_time_sum += time_total - only_bid_down_time;
                			for(double i = 0;i<=9;i++){//板の移動の分布を格納するfor文
                				if(i <= time_total - only_bid_down_time && time_total - only_bid_down_time < i + 1){//iの条件により範囲を指定
                					bid_down_bid_up_dis[(int)(i)]++;
                				}
                			}
                			if(10 <= time_total - only_bid_down_time){//条件により範囲を指定
            					bid_down_bid_up_dis[10]++;
            				}
                		}
                		else{
                			bid_down_else_count++;
                			bid_down_else_time_sum += time_total - only_bid_down_time;
                		}
                		down_only_bid = 0;
                	}

                	if(JNIc_split[2].equals("down") || JNIc_split[2].equals("down not Trade")){
                		down_count++;
                	}
                	else if(JNIc_split[2].equals("down price dif not 10") || JNIc_split[2].equals("down not Trade not 10") ){
                		down_not10_count++;
                	}
                	else if(JNIc_split[2].equals("down only ask not Trade")){
                		down_only_ask_count++;
                	}
                	else if(JNIc_split[2].equals("down only bid") || JNIc_split[2].equals("down only bid not Trade")){
                		down_only_bid_count++;
                		down_only_bid = 1;
                		only_bid_down_time = time_total;
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
                		up_only_ask = 1;
                		only_ask_up_time = time_total;
                	}
                	else if(JNIc_split[2].equals("up only bid not Trade")){
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
            System.out.println("up price not 10," + up_not10_count);
            System.out.println("up only bid," + up_only_bid_count);



            System.out.println("up only ask," + up_only_ask_count);
            System.out.println("ask up bid up," + ask_up_bid_up_count);
            System.out.println("ask up ask down," + ask_up_ask_down_count);
            System.out.println("ask up else," + ask_up_else_count);
            System.out.println("ask_up_bid_up_time_sum," + ask_up_bid_up_time_sum);
            System.out.println("ask_up_ask_down_time_sum," + ask_up_ask_down_time_sum);
            System.out.println("ask_up_else_time_sum," + ask_up_else_time_sum);



            System.out.println("up price same," + up_price_same_count);
            System.out.println("up else," + up_else_count);




            System.out.println("down," + down_count);
            System.out.println("down price not 10," + down_not10_count);



            System.out.println("down only bid," + down_only_bid_count);
            System.out.println("down only ask," + down_only_ask_count);
            System.out.println("bid down ask down," + bid_down_ask_down_count);
            System.out.println("bid down bid up," + bid_down_bid_up_count);
            System.out.println("bid down else," + bid_down_else_count);
            System.out.println("bid_down_ask_down_time_sum," + bid_down_ask_down_time_sum);
            System.out.println("bid_down_bid_up_time_sum," + bid_down_bid_up_time_sum);
            System.out.println("bid_down_else_time_sum," + bid_down_else_time_sum);




            System.out.println("down price same," + down_price_same_count);
            System.out.println("down else," + down_else_count);



            System.out.println("move," + move_count);





            for(double i = 0;i<=10;i++){
				System.out.println("ask_up_bid_up_dis" + i + "," + ask_up_bid_up_dis[(int)(i)]);
				ask_up_bid_up_dis[(int)(i)] = 0;//初期化
			}

            for(double i = 0;i<=10;i++){
				System.out.println("ask_up_ask_down_dis" + i + "," + ask_up_ask_down_dis[(int)(i)]);
				ask_up_ask_down_dis[(int)(i)] = 0;//初期化
			}

            for(double i = 0;i<=10;i++){
				System.out.println("bid_down_ask_down_dis" + i + "," + bid_down_ask_down_dis[(int)(i)]);
				bid_down_ask_down_dis[(int)(i)] = 0;//初期化
			}

            for(double i = 0;i<=10;i++){
				System.out.println("bid_down_bid_up_dis" + i + "," + bid_down_bid_up_dis[(int)(i)]);
				bid_down_bid_up_dis[(int)(i)] = 0;//初期化
			}



            //初期化----------------------------
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

            ask_up_bid_up_count = 0;
            ask_up_ask_down_count = 0;
            ask_up_else_count = 0;
            bid_down_bid_up_count = 0;
            bid_down_ask_down_count = 0;
            bid_down_else_count = 0;
            down_only_bid = 0;
            up_only_ask = 0;

            ask_up_bid_up_time_sum = 0;
            ask_up_ask_down_time_sum = 0;
            ask_up_else_time_sum = 0;
            bid_down_ask_down_time_sum = 0;
            bid_down_bid_up_time_sum = 0;
            bid_down_else_time_sum = 0;

            //初期化----------------------------



            brtxt.close();
            fr.close();



        }

        br.close();
    }
}


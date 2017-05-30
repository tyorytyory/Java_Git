import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
//HTICDT-ファイルから「_market_limit_gcheck」を抽出後、指値注文を抽出するプラグラム
//約定と約定の間にある指値を数えるプログラム
public class HTICDT_limit_order{

    public static void main(String[] args) throws IOException{

        BufferedReader br = new BufferedReader(new FileReader("filelist.txt"));//読み取りたいファイル名の記入
        String txtFileName;

        while((txtFileName = br.readLine()) != null) {

        	String Index;

        	int number1 = 0;//bidとaskの最良気配の価格決定で必要なもの

        	//文字の抽出のプログラムで必要（ここから）
        	String a;
            int count = 0;
            int count1 = 0;
            int count2 = 0;
            int count3 = 0;
            int count4 = 0;
            int count5 = 0;
            int count6 = 0;
            int count7 = 0;
            int count8 = 0;
            int count9 = 0;
            int count10 = 0;
            int count11 = 0;
            int count12 = 0;
            int count13 = 0;//初期値設定のために必要な関数
            //文字の抽出のプログラムで必要（ここまで）
            int count_trade_before = 0;//指値注文の直前に約定があったかどうか（同時刻に）
            int count_hiruma1 = 0;//昼休みのfor文で必要な関数
            String day = null;//日付
            String transaction = null;//Quote or Trade
            String time = null;//時間
            double time1 = 0.0;//時
            double time2 = 0.0;//分
            double time3 = 0.0;//秒
            double time_total1 = 0.0;//時間を秒で表示(time1,time2,time3を使いながら)
            String day1 = null;//日付
            int day_number = 0;//年月日
            String bid_volume;//最良買気配の累積枚数
            int bid_volume1 = 0;//最良買気配の累積枚数
            int bid_volume2 = 0;//1つ前の最良買気配の累積枚数
            int bid_volume_dif = 0;//前の買いの指値注文からの増減
            String ask_volume = null;//最良売気配の累積枚数
            int ask_volume1 = 0;//最良売気配の累積枚数
            int ask_volume2 = 0;//1つ前の最良売気配の累積枚数
            int ask_volume_dif = 0;//前の売りの指値注文からの増減
            String trade_price;//約定価格
            int trade_price1 = 0;//約定価格
            String trade_volume;//出来高
            int trade_volume1 = 0;//出来高
            String bid;//最良買気配の値段
            int bid1[] = new int[800000];//最良買気配の値段
            String ask;//最良売気配の値段
            int ask1[] = new int[800000];//最良売気配の値段
            int i1 = 0;
            int i2 = 0;
            int i3 = 0;
            int i4 = 0;
            int i5 = 0;
            int i6 = 0;
            int i7 = 0;
            int i8 = 0;
            int i9 = 0;
            int i10 = 0;

            double trade_time = 0.0;//約定の時間（計算結果）

            int ita_change = 0;//直前に板の移動があったかどうか示す変数（１→有り、０→なし）
            int bid_ask_initialization = 0;//板の移動があった場合の初期化変数
            int bid_price_before = 0;//１つ前の最良買気配の値段
            int ask_price_before = 0;//１つ前の最良売気配の値段


        	//FileReader fr = new FileReader("/Volumes/HASHIMOTO3/data/2016/約定・指値データ/昼間のデータ(900-1510)/月毎/" + txtFileName);//Macの場合
            //FileReader fr = new FileReader("/Volumes/HASHIMOTO3/data/2016/約定・指値データ/昼間のデータ(900-1510)/月毎(限月調整3,6,9,12)/結合データ/" + txtFileName);//Macの場合
        	FileReader fr = new FileReader("C:/Users/Hashimoto/Documents/pleiades/workspace/Git/2006/" + txtFileName);//Windowsの場合
            BufferedReader brtxt = new BufferedReader(fr);
            String line ="";

            String[] filename = txtFileName.split("\\_");

            File file = new File("C:/Users/Hashimoto/Documents/pleiades/workspace/Git/2006/" + filename[0].substring(0,6) + "_HTICDT_limit_order.csv");//Windows
         	//File file = new File("/Volumes/HASHIMOTO3/data/2016/指値データ/ロイター通信社指値注文/月毎(900-1510)/JNIc_" + filename[1].substring(0,6) + "_limit_order.csv");//Mac
         	PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(file)));

            while ((line = brtxt.readLine()) != null) {

            	Index = line;

            	i2 = Index.length();

            	if(!(Index.substring(0,1).equals("#"))){

                	day = line.substring(4,12);//年月日
                	day_number = Integer.parseInt(day);//年月日
                	time = line.substring(30,34);//時間
                	transaction = line.substring(34,36);//Quote(33) or Trade( 0)
            		String c1 = Index.substring(30,32);//時
            		String c2 = Index.substring(32,34);//分
            		String c3 = Index.substring(36,38);//秒
            		String record = Index.substring(49,52);//最良売り気配(  0)or最良買い気配(128)or寄り付き（  1)
            		time1 = Double.parseDouble(c1);//時
            		time2 = Double.parseDouble(c2);//分
            		time3 = Double.parseDouble(c3);//秒
            		time_total1 = time1*3600 + time2*60 + time3;//時間を秒で表示

                	if(!(day.equals(day1))){//日付が変わった際に初期化する箇所
                		count_hiruma1 = 0;//初期化
                		number1 = 0;//初期化
                		bid_volume1 = 0;//初期化
                		ask_volume1 = 0;//初期化
                    	bid1[0]=0;//初期化
                    	ask1[0]=0;//初期化
                    	bid1[1]=0;//初期化
                    	ask1[1]=0;//初期化
                    	count13 = 0;//初期化
                	}

                	if(time_total1 > 39600.0 && count_hiruma1 == 0 && day_number < 20110214){//2012/2/14までは昼休みがあるのでそこに関して調整する箇所
                		number1 = 0;//初期化
                		bid_volume1 = 0;//初期化
                		ask_volume1 = 0;//初期化
                    	bid1[0]=0;//初期化
                    	ask1[0]=0;//初期化
                    	bid1[1]=0;//初期化
                    	ask1[1]=0;//初期化
                		count_hiruma1++;//このfor文を回避するためのもの
                		count13 = 0;//初期化
                	}

                    if(transaction.equals(" 0")){
                    	trade_price = Index.substring(41,47);//Tradeの約定価格
                    	trade_price1 =Integer.parseInt(trade_price);
                    	trade_volume = Index.substring(56,66);//Tradeの出来高
                    	trade_volume1 =Integer.parseInt(trade_volume);
                    	count_trade_before = 0;
                    	count_trade_before++;
                    }
                    else if((transaction.equals("33")) && record.equals("128")){
                    	bid=Index.substring(41,47);//最良買気配の値段
                        bid1[number1] = Integer.parseInt(bid);
                        bid_volume = Index.substring(56,66);//最良売気配の累積枚数
                    	bid_volume1 =Integer.parseInt(bid_volume);
                    	count_trade_before++;
                    }
                    else if((transaction.equals("33")) && record.equals("  0")){
                    	//System.out.println(Quote);
                    	ask=Index.substring(41,47);//最良売気配の値段
                        ask1[number1] = Integer.parseInt(ask);
                        ask_volume = Index.substring(56,66);//最良売気配の累積枚数
                    	ask_volume1 =Integer.parseInt(ask_volume);
                    	count_trade_before++;
                    }


                    if(bid1[1]>ask1[1] && bid1[1] != 0 && ask1[1] != 0){//意味の分からないことが起きていないか確認(買値＞売値)

                    	//System.out.println(day + " " + time + " " + bid1[1] + " " + ask1[1]);
                    }
                    if(transaction.equals(" 0")){

                    	trade_time = time_total1;
                    	count_trade_before = 0;

                    	if(bid1[1] == trade_price1){
                    		bid_volume2 = bid_volume2 - trade_volume1;
                    		if(bid_volume2  == 0){//板の移動
                    			ita_change = 1;
                    		}
                    	}

                    	if(ask1[1] == trade_price1){
                    		ask_volume2 = ask_volume2 - trade_volume1;
                    		if(ask_volume2 == 0){//板の移動
                    			ita_change = 1;
                    		}
                    	}

                    	if(bid1[1] != trade_price1 && ask1[1] != trade_price1 && bid1[1] != 0 && ask1[1] != 0){
                    		System.out.println(Index + "+++" + bid1[1] + "," + ask1[1]);
                    	}

                    }

                    if(record.equals("  1") && count13 == 0){//寄り付きが終了したときの初期値の設定
                    	count13++;
                    	number1 = 1;
                    	day1 = day;
                    	bid_volume2 = bid_volume1;
                    	ask_volume2 = ask_volume1;
                    	bid_ask_initialization = 0;
                    	//System.out.println(day + " " + bid_volume2 + " " + ask_volume2 );
                    	//System.out.println(Index + " " + ask_volume + " " + ask1[0]);
                    }
                    if((count13 >= 1 && count13 < 3) || bid_ask_initialization == 1){
                    	if(record.equals("  0")){
                    		ask_volume2 = ask_volume1;
                    		if(count13 < 3){
                    			ask1[1] = ask1[0];
                    		}
                    		count13++;
                    	}
                    	if(record.equals("128")){
                    		bid_volume2 = bid_volume1;
                    		if(count13 < 3){
                    			bid1[1] = bid1[0];
                    		}
                    		count13++;
                    		bid_ask_initialization = 0;
                    	}
                    }

                    else if(count13 >= 3){//共通

                    		if(bid_volume1 != 0 && ask_volume1 != 0 && !(transaction.equals(" 0"))){
                    			//System.out.println(count13);

                    			if(record.equals("128")){
                    				if(trade_time == time_total1 && trade_price1 == bid1[1] && count_trade_before == 3){//約定と同時に起きた指値注文
                            			if(bid_volume2 < bid_volume1){
                            				bid_volume_dif = bid_volume1 - bid_volume2;
                            			}
                            			else{//注文の取り消しとか
                            				bid_volume_dif = 0;
                            			}
                            		}
                            		/*else if(trade_volume1 == bid_volume2){//板の移動
                            			bid_volume_dif = 0;
                            		}*/
                            		else{//指値注文
                            			bid_volume_dif = bid_volume1 - bid_volume2;
                            		}
                            		bid_volume2 = bid_volume1;//1つ前の注文にしている。
                            		bid_price_before = bid1[1];//1つ前の最良買気配の値段の代入

                            		if(bid_volume_dif > 0){
                            			pw.println(day + "," + time + "," + bid_volume_dif + "," + bid1[1] + ",bid,,,,,");//指値買注文の書き込み
                            			//pw.println("bid," + Index + "," + bid_volume_dif);

                            		}
                    			}

                    			if(record.equals("  0")){
                    				if(trade_time == time_total1 && trade_price1 == ask1[1] && count_trade_before == 2){//約定と同時に起きた指値注文
                            			if(ask_volume2 < ask_volume1){
                            				ask_volume_dif = ask_volume1 - ask_volume2;
                            			}
                            			else{//注文の取り消しとか
                            				ask_volume_dif = 0;
                            			}
                            		}
                            		/*else if(trade_volume1 == ask_volume2){//板の移動
                            			ask_volume_dif = 0;
                            		}*/
                            		else{//指値注文
                            			ask_volume_dif = ask_volume1 - ask_volume2;
                            		}

                            		ask_volume2 = ask_volume1;//1つ前の注文にしている。
                            		ask_price_before = ask1[1];//1つ前の最良売気配の値段の代入

                            		if(ask_volume_dif > 0){
                            			pw.println(day + "," + time + "," + ask_volume_dif + "," + ask1[1] + ",ask,,,,,");//指値売注文の書き込み
                            			//pw.println("ask," + Index + "," + ask_volume_dif);
                            		}
                    			}


                        	}
                    		if(ita_change == 1){
                    			bid_ask_initialization = 1;
                    			ita_change = 0;
                    			//System.out.println(Index);
                    		}

                    }


                    count1=0;
                    count2=0;
                    count3=0;
                    count4=0;
                    count5=0;
                    count6=0;
                    count7=0;
                    count8=0;
                    count9=0;
                    count10=0;
                    count11=0;
                    count12=0;
                    count=0;
                    i3 = 0;
                    i4 = 0;
                    i5 = 0;
                    i6 = 0;
                    i7 = 0;
                    i8 = 0;
                    i9 = 0;
                    i10 = 0;

            	}


            }


            brtxt.close();
            fr.close();
            pw.close();


        }

        br.close();
    }
}


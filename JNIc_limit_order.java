import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
//新しいTick Dataでの指値注文の注文量と間隔を求めるプログラム　約定と約定の間にある指値を数えるプログラム
public class JNIc_limit_order{

    public static void main(String[] args) throws IOException{

        BufferedReader br = new BufferedReader(new FileReader("filelist2.txt"));//読み取りたいファイル名の記入
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
            int count13 = 0;
            //文字の抽出のプログラムで必要（ここまで）
            int count_hiruma1 = 0;//昼休みのfor文で必要な関数
            String day = null;//日付
            String transaction = null;//Quote or Trade
            String time = null;//時間
            double time1 = 0.0;//時
            double time2 = 0.0;//分
            double time3 = 0.0;//秒
            double time_total1 = 0.0;//時間を秒で表示(time1,time2,time3を使いながら)
            String day1 = null;
            int day_number = 0;
            String bid_volume;//最良買気配の累積枚数
            int bid_volume1 = 0;//最良買気配の累積枚数
            int bid_volume2 = 0;//1つ前の最良買気配の累積枚数
            int bid_volume_dif = 0;//前の買いの指値注文からの増減
            String ask_volume = null;//最良売気配の累積枚数
            int ask_volume1 = 0;//最良売気配の累積枚数
            int ask_volume2 = 0;//1つ前の最良売気配の累積枚数
            int ask_volume_dif = 0;//前の売りの指値注文からの増減
            String trade_price;
            int trade_price1 = 0;
            String trade_volume;
            int trade_volume1 = 0;
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

        	FileReader fr = new FileReader("/Volumes/HASHIMOTO3/data/2016/約定・指値データ/昼間のデータ(900-1510)/月毎/" + txtFileName);//Macの場合
        	//FileReader fr = new FileReader("/Volumes/HASHIMOTO3/data/2016/約定・指値データ/昼間のデータ(900-1510)/月毎(限月調整3,6,9,12)/結合データ/" + txtFileName);//Macの場合
        	//FileReader fr = new FileReader(txtFileName);//Windowsの場合
            BufferedReader brtxt = new BufferedReader(fr);
            String line ="";

            String[] filename = txtFileName.split("\\_");

         	File file = new File("/Volumes/HASHIMOTO3/data/2016/指値データ/ロイター通信社指値注文/月毎(900-1510)/JNIc_" + filename[1].substring(0,6) + "_limit_order.csv");//JNIc1用
         	PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(file)));

            while ((line = brtxt.readLine()) != null) {

            	Index = line;

            	i2 = Index.length();

            	if(!(Index.substring(0,1).equals("#"))){

                	day = line.substring(12,20);//年月日
                	day_number = Integer.parseInt(day);//年月日
                	time = line.substring(21,36);//時間
                	transaction = line.substring(37,42);//Quote or Trade
            		String c1 = Index.substring(21,23);//時
            		String c2 = Index.substring(24,26);//分
            		String c3 = Index.substring(27,36);//秒
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
                	}

                	if(time_total1 > 45000.0 && count_hiruma1 == 0 && day_number < 20110214){//2012/2/14までは昼休みがあるのでそこに関して調整する箇所
                		number1 = 0;//初期化
                		bid_volume1 = 0;//初期化
                		ask_volume1 = 0;//初期化
                    	bid1[0]=0;//初期化
                    	ask1[0]=0;//初期化
                    	bid1[1]=0;//初期化
                    	ask1[1]=0;//初期化
                		count_hiruma1++;//このfor文を回避するためのもの
                	}


                    for(i1=0;i1<i2;i1++){//文字の抽出
                        a = Index.substring(i1,i1+1);
                        if(count == 5 && !(a.equals(",")) && count1 == 0){

                        	i7 = i1;
                        	count1++;
                        }
                        if(count == 6 && count1 != 0 && count2 == 0){
                        	i8 = i1-1;
                        	trade_price = Index.substring(i7,i8);//Tradeの約定価格
                        	trade_price1 =Integer.parseInt(trade_price);
                        	count2++;
                        }
                        if(count == 6 && !(a.equals(",")) && count3 == 0){
                        	i9 = i1;
                        	count3++;
                        }
                        if(count == 7 && count3 != 0 && count4 == 0){
                        	i10 = i1-1;
                        	trade_volume = Index.substring(i9,i10);//Tradeの出来高
                        	trade_volume1 =Integer.parseInt(trade_volume);
                        	count4++;
                        }

                        if(count == 8 && !(a.equals(",")) && count5 == 0){
                            i3 = i1;
                            count5++;
                        }

                        if(count == 9 && count5 != 0 && count6 == 0){
                            count6++;
                            i4 = i1-1;
                            bid=Index.substring(i3,i4);//最良買気配の値段
                            if(bid.equals(",")){
                                bid1[number1] = 0;
                            }
                            else{
                                bid1[number1] = Integer.parseInt(bid);
                            }
                        }
                        if(count == 9 && !(a.equals(",")) && count7 == 0){
                        	count7++;
                        	i5 = i1;
                        }
                        if(count == 10 && count7 != 0 && count8 == 0){
                        	i6 = i1-1;
                        	bid_volume = Index.substring(i5,i6);//最良売気配の累積枚数
                        	bid_volume1 =Integer.parseInt(bid_volume);
                        	count8++;
                        }
                        if(count == 10 && !(a.equals(",")) && count9 == 0){
                            i3 = i1;
                            count9++;
                        }
                        if(count == 11 && count9 != 0 && count10 == 0){
                            count10++;
                            i4 = i1-1;
                            ask=Index.substring(i3,i4);//最良売気配の値段
                            ask1[number1] = Integer.parseInt(ask);

                        }
                        if(count == 11 && !(a.equals(",")) && count11 == 0){
                        	i5 = i1;
                        	count11++;
                        }
                        if((count == 12 && count11 != 0 && count12 == 0) || (count == 11 && count11 != 0 && i1 ==(i2 - 1))){
                        	i6 = i1-1;
                        	if(i1 == (i2 - 1)){
                        		i6 = i1;
                        	}
                        	ask_volume = Index.substring(i5,i6);//最良売気配の累積枚数
                        	ask_volume1 =Integer.parseInt(ask_volume);
                        	count12++;
                        }
                        if(a.equals(",")){
                            count++;
                        }
                    }
                    if(bid1[1]>ask1[1] && bid1[1] != 0 && ask1[1] != 0){//意味の分からないことが起きていないか確認

                    	System.out.println(day + " " + time + " " + bid1[1] + " " + ask1[1]);
                    }
                    if(transaction.equals("Trade")){
                    	trade_time = time_total1;
                    }

                    if(bid1[0] != ask1[0] && ask1[0] != 0 && bid1[0] != 0 && count13 == 0){//寄り付きが終了したときの初期値の設定
                    	count13++;
                    	number1++;
                    	day1 = day;
                    	bid_volume2 = bid_volume1;
                    	ask_volume2 = ask_volume1;
                    	//System.out.println(day + " " + bid_volume2 + " " + ask_volume2 );
                    	//System.out.println(Index + " " + ask_volume + " " + ask1[0]);
                    }

                    else if(count13 != 0){//共通

                    		if(bid_volume1 != 0 && ask_volume1 != 0 && !(transaction.equals("Trade"))){
                    			//System.out.println(time_total1);
                        		if(trade_time == time_total1 && trade_price1 == bid1[1]){
                        			if(trade_volume1 > (bid_volume2 - bid_volume1)){
                        				bid_volume_dif = trade_volume1 - (bid_volume2 - bid_volume1);
                        			}
                        			else{
                        				bid_volume_dif = 0;
                        			}
                        		}
                        		else if(trade_volume1 == bid_volume2){//板の移動
                        			bid_volume_dif = 0;
                        		}
                        		else{
                        			bid_volume_dif = bid_volume1 - bid_volume2;
                        		}
                        		bid_volume2 = bid_volume1;

                        		if(trade_time == time_total1 && trade_price1 == ask1[1]){
                        			if((trade_volume1 > (ask_volume2 - ask_volume1))){//指値注文
                        				ask_volume_dif = trade_volume1 - (ask_volume2 - ask_volume1);
                        				/*if(day.equals("20060105")){
                        					System.out.println(trade_volume1 + " " + ask_volume2 + " " + ask_volume1);
                        					System.out.println(Index);
                        				}*/

                        			}
                        			else{//注文の取り消しとか
                        				ask_volume_dif = 0;
                        			}
                        		}
                        		else if(trade_volume1 == ask_volume2){//板の移動
                        			ask_volume_dif = 0;
                        		}
                        		else{
                        			ask_volume_dif = ask_volume1 - ask_volume2;
                        		}

                        		ask_volume2 = ask_volume1;

                        		int q1 = 0;
                        		int q2 = 0;
                        		if(bid_volume_dif > 0){
                        			pw.println(day + "," + time + "," + bid_volume_dif + "," + bid1[1] + ",bid,,,,,");//指値買注文の書き込み
                        			q1++;
                        		}
                        		if(ask_volume_dif > 0){
                        			pw.println(day + "," + time + "," + ask_volume_dif + "," + ask1[1] + ",ask,,,,,");//指値売注文の書き込み
                        			q2++;
                        		}
                        		if(q1 == 1 && q2 == 1){
                        			//System.out.println("なんてこった。ぱんたこった。");
                        		}

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


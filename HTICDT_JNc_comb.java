import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
//日経ニーズデータとコンマ秒データとの対応を、出来高から判定するプログラム
//指値注文にも対応させる
public class HTICDT_JNc_comb{

    public static void main(String[] args) throws IOException{
    	String Index;

    	int i1 = 0;


    	int number1 = 0;
    	int number2 = 0;


    	int count = 1;//
    	int count1 = 0;//for文
    	int count2 = 0;//for文
    	int count3 = 0;
    	int count4 = 0;
    	int count5 = 0;
    	int count6 = 0;
    	int count_if = 0;//for文

    	int i2 = 0;
    	int i3 = 0;


    	String nikkei_HTICDT[] = new String[9000000];//NIKKEI NEEDS

    	String nikkei_JNc[] = new String[9000000];//コンマ秒(JNc)

    	int volume = 0;


    	int volume_JNc [] = new int [9000000];//コンマデータの出来高
    	int volume_nikkei_HTICDT[] = new int[9000000];//NIKKEI NEEDS(出来高)
    	int sum_volume = 0;//出来高の総計(コンマデータ)

    	int count_konma = 0;//コンマデータの行の確認。
    	int length_konma = 0;//コンマデータの長さの確認

    	String time_HTICDT;//取引時間
    	String time_JNc;//取引時間

    	int price_HTICDT []  = new int [9000000];////NIKKEI NEEDS(価格)
    	int price_JNc [] = new int [9000000];////JNc(価格)

    	String bid_or_ask_HTICDT [] =  new String[9000000];//NIKKEI NEEDS(bid or ask)
    	String bid_or_ask_JNc [] =  new String[9000000];//JNc(bid or ask)


    	String a = null;
    	String db1 = null;
    	String db2 = null;
    	String db3 = null;
    	String db4 = null;




        BufferedReader br = new BufferedReader(new FileReader("filelist3.txt"));//読み取りたいファイル名の記入
        String txtFileName;

        while((txtFileName = br.readLine()) != null) {





        	FileReader fr = new FileReader("C:/Users/Hashimoto/Documents/pleiades/workspace/Git/2006/" + txtFileName);
            BufferedReader brtxt = new BufferedReader(fr);
            String line ="";




            while ((line = brtxt.readLine()) != null) {
            	Index = line;
            	if(1 == count%2){




            		length_konma = Index.length();


            		time_HTICDT = Index.substring(9,13);
            		//System.out.println(time_HTICDT);


            		for(i1=0;i1<length_konma;i1++){
                        a = Index.substring(i1,i1+1);

                        if(count_if == 2 && !(a.equals(",")) && count1 == 0){
                        	i2 = i1;
                        	count1++;
                        }
                        if(count_if == 3 && count1 != 0 && count2 == 0){
                        	i3 = i1-1;
                        	count2++;

                        	//if((!(time_HTICDT.equals("1510"))) && (!(time_HTICDT.equals("1509"))) && (!(time_HTICDT.equals("1100")))){

                        		nikkei_HTICDT[number1] = Index;
                        		db1 = Index.substring(i2,i3);
                        		//volume_nikkei_HTICDT[number1] = Integer.parseInt(db1);
                            	//number1++;
                        	//}

                        }
                        if(count_if == 3 && !(a.equals(",")) && count3 == 0){
                        	i2 = i1;
                        	count3++;
                        }
                        if(count_if == 4 && count3 != 0 && count4 == 0){
                        	i3 = i1-1;
                        	count4++;
                        	db2 = Index.substring(i2,i3);
                        	//System.out.println(price_HTICDT [number1]);
                        }
                        if(count_if == 4 && !(a.equals(",")) && count5 == 0){
                        	i2 = i1;
                        	count5++;
                        }
                        if(count_if == 5 && count5 != 0 && count6 == 0){
                        	i3 = i1-1;
                        	count6++;

                        	//System.out.println(bid_or_ask_HTICDT[number1]);
                        }
                        if(a.equals(",")){
                            count_if++;
                        }
                    }
            		count_if = 0;
            		count1 = 0;
            		count2 = 0;
            		count3 = 0;
            		count4 = 0;
            		count5 = 0;
            		count6 = 0;

            		//if((!(time_HTICDT.equals("1510"))) && (!(time_HTICDT.equals("1509"))) && (!(time_HTICDT.equals("1100")))){
            			volume_nikkei_HTICDT[number1] = Integer.parseInt(db1);
            			price_HTICDT [number1] = Integer.parseInt(db2);
            			bid_or_ask_HTICDT[number1] = Index.substring(i2,i3);
                    	number1++;
                	//}




            	}
            	else if(0 == count%2){

            		//nikkei_JNc[number2] = Index;
            		time_JNc = Index.substring(9,14);
            		//System.out.println(time_JNc);

            		length_konma = Index.length();

            		for(i1=0;i1<length_konma;i1++){
                        a = Index.substring(i1,i1+1);

                        if(count_if == 2 && !(a.equals(",")) && count1 == 0){
                        	i2 = i1;
                        	count1++;
                        }
                        if(count_if == 3 && count1 != 0 && count2 == 0){
                        	i3 = i1-1;
                        	count2++;
                    		nikkei_JNc[number2] = Index;
                    		db3 = nikkei_JNc[number2].substring(i2,i3);
                        }
                        if(count_if == 3 && !(a.equals(",")) && count3 == 0){
                        	i2 = i1;
                        	count3++;
                        }
                        if(count_if == 4 && count3 != 0 && count4 == 0){
                        	i3 = i1-1;
                        	count4++;
                        	db4 = Index.substring(i2,i3);

                        	//System.out.println(price_JNc [number1]);
                        }
                        if(count_if == 4 && !(a.equals(",")) && count5 == 0){
                        	i2 = i1;
                        	count5++;
                        }
                        if(count_if == 5 && count5 != 0 && count6 == 0){
                        	i3 = i1-1;
                        	count6++;

                        	//System.out.println(bid_or_ask_JNc[number1]);
                        }
                        if(a.equals(",")){
                            count_if++;
                        }
                    }
            		count_if = 0;
            		count1 = 0;
            		count2 = 0;
            		count3 = 0;
            		count4 = 0;
            		count5 = 0;
            		count6 = 0;

            		//if((!(time_JNc.equals("15:10"))) && (!(time_JNc.equals("15:09"))) && (!(time_JNc.equals("11:00")))){
            			volume_JNc[number2] = Integer.parseInt(db3);
            			price_JNc [number2] = Integer.parseInt(db4);
            			bid_or_ask_JNc[number2] = Index.substring(i2,i3);
            			number2++;
            		//}

            		//number2++;

            	}
            }

            count_konma = number2 - 1;



        	if(0 == count%2){
        		String[] filename = txtFileName.split("\\.");
        		File file = new File(filename[0] +	"_comb2.csv");
             	PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(file)));
             	File file1 = new File(filename[0] +	"_HTICDT_delete.csv");
             	PrintWriter pw1 = new PrintWriter(new BufferedWriter(new FileWriter(file1)));
             	File file2 = new File(filename[0] +	"_JNc_delete.csv");
             	PrintWriter pw2 = new PrintWriter(new BufferedWriter(new FileWriter(file2)));
             	File file3 = new File(filename[0] +	"_comb_not_data_match.csv");
             	PrintWriter pw3 = new PrintWriter(new BufferedWriter(new FileWriter(file3)));

        		number1 = 0;
        		number2 = 0;
        		while(number2 <= count_konma){
        			//コンマ秒データ2010年まで（ここから）


            		if(volume_nikkei_HTICDT[number1] == volume_JNc[number2]){
            			pw.println(nikkei_HTICDT[number1] + " " + nikkei_JNc[number2]);
            			if(price_HTICDT[number1] != price_JNc[number2] || !(bid_or_ask_HTICDT [number1].equals(bid_or_ask_JNc[number2]))){
            				pw3.println(nikkei_HTICDT[number1] + " " + nikkei_JNc[number2]);

            			}

            			//System.out.println(nikkei_HTICDT[number1] + " " + nikkei_JNc[number2] + "happy");
            			number1++;
    					number2++;
            		}
            		else if(volume_nikkei_HTICDT[number1] != volume_JNc[number2]){


            			if(volume_nikkei_HTICDT[number1] < volume_JNc[number2] || volume_nikkei_HTICDT[number1] > volume_JNc[number2]){//HTTICDTとJNcのデータの不一致を検出→そこからどちらを削除すれば(1行削除して済む範囲に限るが)つじつまが合うか調査



            				/*int number_123 = 0;
            				int number11 = 0;

            				while(number_123 == 0){
            					for(int d1 = 1;d1<=2001;d1++){
            						if(number11 == 1){
            							number1++;
                						d1 = 1;
            						}
            						else if(volume_nikkei_HTICDT[number1] == volume_JNc[number2 + d1] && price_HTICDT[number1] == price_JNc[number2 + d1] && bid_or_ask_HTICDT[number1].equals(bid_or_ask_JNc[number2 + d1])){

                						pw.println(nikkei_HTICDT[number1] + " " + nikkei_JNc[number2 + d1]);
                						number_123 = d1;
                						if(d1 == 2000){
                							number11++;
                						}
                					}

                				}
            				}

            				number2 = number2 + number_123;*/


            				if(volume_nikkei_HTICDT[number1 + 1] == volume_JNc[number2 + 1] && volume_nikkei_HTICDT[number1 + 2] == volume_JNc[number2 + 2] &&  volume_nikkei_HTICDT[number1 + 3] == volume_JNc[number2 + 3]
            						&& price_HTICDT[number1 + 1] == price_JNc[number2 + 1] && bid_or_ask_HTICDT[number1 + 1].equals(bid_or_ask_JNc[number2 + 1])
            						&& price_HTICDT[number1 + 2] == price_JNc[number2 + 2] && bid_or_ask_HTICDT[number1 + 2].equals(bid_or_ask_JNc[number2 + 2])
            						&& price_HTICDT[number1 + 3] == price_JNc[number2 + 3] && bid_or_ask_HTICDT[number1 + 3].equals(bid_or_ask_JNc[number2 + 3])
            						){//どちらも1行消去
            					//System.out.println(number1 + " "+ number2);
            					//System.out.println("ダブル" + nikkei_HTICDT[number1] + " " + nikkei_JNc[number2]);
            					pw1.println(nikkei_HTICDT[number1]);
            					pw2.println(nikkei_JNc[number2]);
            					//System.out.println(nikkei_HTICDT[number1] + " " + nikkei_JNc[number2]+ " " + number1 + " " + number2);
            					number1++;
            					number2++;
            					pw.println(nikkei_HTICDT[number1] + " " + nikkei_JNc[number2]);
            				}
            				else if(volume_nikkei_HTICDT[number1 + 1] == volume_JNc[number2] && volume_nikkei_HTICDT[number1 + 2] == volume_JNc[number2 + 1] && volume_nikkei_HTICDT[number1 + 3] == volume_JNc[number2 + 2]//HTICDTを1行削除
            						&& price_HTICDT[number1 + 1] == price_JNc[number2] && bid_or_ask_HTICDT [number1 + 1].equals(bid_or_ask_JNc[number2])
            						&& price_HTICDT[number1 + 2] == price_JNc[number2 + 1] && bid_or_ask_HTICDT [number1 + 2].equals(bid_or_ask_JNc[number2 + 1])
            						&& price_HTICDT[number1 + 3] == price_JNc[number2 + 2] && bid_or_ask_HTICDT [number1 + 3].equals(bid_or_ask_JNc[number2 + 2])
            						){
            					//System.out.println("シングル" + nikkei_HTICDT[number1] + " " + nikkei_JNc[number2]);
            					pw1.println(nikkei_HTICDT[number1]);
            					number1++;
            					pw.println(nikkei_HTICDT[number1] + " " + nikkei_JNc[number2]);

            				}
            				else if(volume_nikkei_HTICDT[number1] == volume_JNc[number2 + 1] && volume_nikkei_HTICDT[number1 + 1] == volume_JNc[number2 + 2] && volume_nikkei_HTICDT[number1 + 2] == volume_JNc[number2 + 3]//JNcを1行削除
            						&& price_HTICDT[number1] == price_JNc[number2 + 1] && bid_or_ask_HTICDT [number1].equals(bid_or_ask_JNc[number2 + 1])
            						&& price_HTICDT[number1 + 1] == price_JNc[number2 + 2] && bid_or_ask_HTICDT [number1 + 1].equals(bid_or_ask_JNc[number2 + 2])
            						&& price_HTICDT[number1 + 2] == price_JNc[number2 + 3] && bid_or_ask_HTICDT [number1 + 2].equals(bid_or_ask_JNc[number2 + 3])
            						){
            					//System.out.println("シングル2" + nikkei_HTICDT[number1] + " " + nikkei_JNc[number2]);
            					pw2.println(nikkei_JNc[number2]);
            					number2++;
            					pw.println(nikkei_HTICDT[number1] + " " + nikkei_JNc[number2]);
            				}
            				else if(volume_nikkei_HTICDT[number1] == volume_JNc[number2 + 1] && price_HTICDT[number1] == price_JNc[number2 + 1] && bid_or_ask_HTICDT [number1].equals(bid_or_ask_JNc[number2 + 1])
            						&& volume_nikkei_HTICDT[number1 + 1] == volume_JNc[number2] && price_HTICDT[number1+1] == price_JNc[number2] && bid_or_ask_HTICDT [number1 + 1].equals(bid_or_ask_JNc[number2])){//交差しているとき（入れ替え）
            					pw.println(nikkei_HTICDT[number1 + 1] + " " + nikkei_JNc[number2]);
            					pw.println(nikkei_HTICDT[number1] + " " + nikkei_JNc[number2 + 1]);
            					//System.out.println(nikkei_HTICDT[number1 + 1] + " " + nikkei_JNc[number2]);
            					//System.out.println(nikkei_HTICDT[number1] + " " + nikkei_JNc[number2 + 1] + "\n");
            					number1 = number1 + 2;
            					number2 = number2 + 2;
            				}
            				else{
            					if(price_HTICDT[number1] == price_JNc[number2] && bid_or_ask_HTICDT [number1].equals(bid_or_ask_JNc[number2])){
            						pw.println(nikkei_HTICDT[number1] + " " + nikkei_JNc[number2]);
                				}
            					else{
            						System.out.println(nikkei_HTICDT[number1]  + " " + nikkei_JNc[number2]);
            						//System.out.println(nikkei_HTICDT[number1 + 1] + " " + nikkei_JNc[number2 + 1]);
            						pw.close();
                    				pw1.close();
                            		pw2.close();
                            		pw3.close();
            						return;
            					}




                    			//System.out.println(nikkei_HTICDT[number1] + " " + nikkei_JNc[number2]);
                    			//System.out.println(nikkei_HTICDT[number1] + " " + nikkei_JNc[number2]);

            					if(volume_nikkei_HTICDT[number1] > volume_JNc[number2]){
            						while(volume_nikkei_HTICDT[number1] > sum_volume){

                        				//System.out.println("come on");

                        				sum_volume += volume_JNc[number2];


                        				number2++;
                        				if(volume_nikkei_HTICDT[number1] > sum_volume){
                        					pw.println(",,,,,,,,," + nikkei_JNc[number2]);
                        				}
                        				if(number2 > count_konma){

                        				}
                        			}
            						number1++;
            					}
            					else if(volume_nikkei_HTICDT[number1] < volume_JNc[number2]){
            						while(volume_JNc[number2] > sum_volume){

                        				//System.out.println("come on");

                        				sum_volume += volume_nikkei_HTICDT[number1];


                        				number1++;
                        				if(volume_JNc[number2] > sum_volume && price_HTICDT[number1] == price_JNc[number2] && bid_or_ask_HTICDT [number1].equals(bid_or_ask_JNc[number2])){
                        					pw.println(nikkei_HTICDT[number1] + ",,,,,,,,, ");
                        				}
                        				if(number2 > count_konma){

                        				}
                        			}
            						number2++;
            					}



                    			if(volume_nikkei_HTICDT[number1] == volume_JNc[number2]){
                    				//System.out.println("ok");
                    			}
                    			else if(volume_nikkei_HTICDT[number1] != volume_JNc[number2]){
                    				System.out.println("NG");
                    				//System.out.println(volume_nikkei_HTICDT[number1]);
                    				//System.out.println(volume_JNc[number2]);

                    				System.out.println(nikkei_HTICDT[number1] + " " + nikkei_JNc[number2]);
                    				System.out.println(nikkei_HTICDT[number1 +1 ] + " " + nikkei_JNc[number2 +1 ]);
                    				pw.close();
                    				pw1.close();
                            		pw2.close();
                            		pw3.close();
                    				return;
                    			}

            				}
            			}

            			sum_volume = 0;
            			number1++;
            			number2++;

            		}

            		//コンマ秒データ2010年まで（ここまで）






        			/*String d1 =  buffer1[number1].substring(56,66);//コンマ秒データ2011年まで（ここから）
            		volume_nikkei = Integer.parseInt(d1);

            		length_konma = buffer2[number2].length();

            		for(i1=0;i1<length_konma;i1++){
                        a = buffer2[number2].substring(i1,i1+1);

                        if(count_if == 6 && !(a.equals(",")) && count1 == 0){
                        	i2 = i1;
                        	count1++;
                        }
                        if(count_if == 7 && count1 != 0 && count2 == 0){
                        	i3 = i1-1;
                        	count2++;
                        	String db = buffer2[number2].substring(i2,i3);
                        	volume_konma = Integer.parseInt(db);
                        }
                        if(a.equals(",")){
                            count_if++;
                        }
                    }
            		count_if = 0;
            		count1 = 0;
            		count2 = 0;
            		//System.out.println(volume_konma);
            		if(volume_nikkei == volume_konma){
            			pw.println(buffer1[number1] + " " + buffer2[number2]);
            			//System.out.println(buffer1[number1] + " " + buffer2[number2] + "happy");
            			number1++;
    					number2++;
            		}
            		else if(volume_nikkei != volume_konma){
            			pw.println(buffer1[number1] + " " + buffer2[number2]);
            			//System.out.println(buffer1[number1] + " " + buffer2[number2]);

            			while(volume_nikkei > sum_volume){
            				//System.out.println("come on");

            				sum_volume += volume_konma;


            				number2++;
            				if(volume_nikkei > sum_volume){
            					pw.println("--------------------------------------------------------------------- " + buffer2[number2]);
            				}
            				if(number2 > count_konma){

            				}
            				else{
            					length_konma = buffer2[number2].length();


                        		for(i1=0;i1<length_konma;i1++){
                                    a = buffer2[number2].substring(i1,i1+1);

                                    if(count_if == 6 && !(a.equals(",")) && count1 == 0){
                                    	i2 = i1;
                                    	count1++;
                                    }
                                    if(count_if == 7 && count1 != 0 && count2 == 0){
                                    	i3 = i1-1;
                                    	count2++;
                                    	String db = buffer2[number2].substring(i2,i3);
                                    	volume_konma = Integer.parseInt(db);
                                    }
                                    if(a.equals(",")){
                                        count_if++;
                                    }
                                }
                        		count_if = 0;
                        		count1 = 0;
                        		count2 = 0;
            				}

            			}
            			if(volume_nikkei == sum_volume){
            				//System.out.println("ok");
            			}
            			else if(volume_nikkei != sum_volume){
            				System.out.println("NG");
            				System.out.println(volume_nikkei);
            				System.out.println(sum_volume);

            				System.out.println(buffer1[number1] + " " + buffer2[number2]);
            				pw.close();
            				return;
            			}
            			sum_volume = 0;
            			number1++;
            		}//コンマ秒データ2011年から（ここまで）*/




            	}
        		pw.close();
        		pw1.close();
        		pw2.close();
        		pw3.close();

                for(int i=0;i<=number1;i++){
            		nikkei_HTICDT[i] = null;
            	}
                for(int i=0;i<=count_konma + 1;i++){
            		nikkei_JNc[i] = null;
            	}
                number1 = 0;
                number2 = 0;


        	}
            count++;
            brtxt.close();
            fr.close();









        	// txtファイル名を一行ずつロードする
        }
        br.close();

    }



}



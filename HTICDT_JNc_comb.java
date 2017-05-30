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
    	int count_if = 0;//for文

    	int i2 = 0;
    	int i3 = 0;


    	String buffer1[] = new String[9000000];//NIKKEI NEEDS

    	String buffer2[] = new String[9000000];//コンマ秒

    	int volume = 0;

    	int volume_nikkei = 0;//日経ニーズの出来高
    	int volume_JNc [] = new int [9000000];//コンマデータの出来高
    	int volume_nikkei_HTICDT[] = new int[9000000];//NIKKEI NEEDS(出来高)
    	int sum_volume = 0;//出来高の総計(コンマデータ)

    	int count_konma = 0;//コンマデータの行の確認。
    	int length_konma = 0;//コンマデータの長さの確認

    	String time_HTICDT;//取引時間
    	String time_JNc;//取引時間

    	String a;




        BufferedReader br = new BufferedReader(new FileReader("filelist.txt"));//読み取りたいファイル名の記入
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

                        		buffer1[number1] = Index;
                        		String db1 = Index.substring(i2,i3);
                        		volume_nikkei_HTICDT[number1] = Integer.parseInt(db1);
                            	number1++;
                        	//}

                        }
                        if(a.equals(",")){
                            count_if++;
                        }
                    }
            		count_if = 0;
            		count1 = 0;
            		count2 = 0;




            		/*String d1 =  Index.substring(56,66);
            		volume = Integer.parseInt(d1);
            		if(volume != 0){
                		buffer1[number1] = Index;
                		number1++;
            		}*/


            	}
            	else if(0 == count%2){

            		//buffer2[number2] = Index;
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
                        	//if((!(time_JNc.equals("15:10"))) && (!(time_JNc.equals("15:09"))) && (!(time_JNc.equals("11:00")))){
                    			buffer2[number2] = Index;
                    			String db = buffer2[number2].substring(i2,i3);
                            	volume_JNc[number2] = Integer.parseInt(db);
                        		number2++;
                    		//}


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

            count_konma = number2 - 1;



        	if(0 == count%2){
        		String[] filename = txtFileName.split("\\.");
        		File file = new File(filename[0] +	"_comb2.csv");
             	PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(file)));
             	File file1 = new File(filename[0] +	"_HTICDT_delete.csv");
             	PrintWriter pw1 = new PrintWriter(new BufferedWriter(new FileWriter(file1)));
             	File file2 = new File(filename[0] +	"_JNc_delete.csv");
             	PrintWriter pw2 = new PrintWriter(new BufferedWriter(new FileWriter(file2)));

        		number1 = 0;
        		number2 = 0;
        		while(number2 <= count_konma){
        			//コンマ秒データ2010年まで（ここから）


            		if(volume_nikkei_HTICDT[number1] == volume_JNc[number2]){
            			pw.println(buffer1[number1] + " " + buffer2[number2]);

            			//System.out.println(buffer1[number1] + " " + buffer2[number2] + "happy");
            			number1++;
    					number2++;
            		}
            		else if(volume_nikkei_HTICDT[number1] != volume_JNc[number2]){
            			//System.out.println("132465");

            			if(volume_nikkei < volume_JNc[number2] || volume_nikkei > volume_JNc[number2]){//HTTICDTとJNcのデータの不一致を検出→そこからどちらを削除すれば(1行削除して済む範囲に限るが)つじつまが合うか調査
            				int volume_delete_HTICDT = volume_nikkei_HTICDT[number1 + 1];
            				int volume_delete_JNc = volume_JNc[number2 + 1];

            				//System.out.println(buffer1[number1] + " " + buffer2[number2] + "," + number1);

            				if(volume_delete_HTICDT == volume_delete_JNc && volume_nikkei_HTICDT[number1 + 2] == volume_JNc[number2 + 2]){//どちらも1行消去
            					//System.out.println("ダブル");
            					//System.out.println("ダブル" + buffer1[number1] + " " + buffer2[number2]);
            					pw1.println(buffer1[number1]);
            					pw2.println(buffer2[number2]);
            					number1++;
            					number2++;
            					pw.println(buffer1[number1] + " " + buffer2[number2]);
            				}
            				else if(volume_delete_HTICDT == volume_JNc[number2] && volume_nikkei_HTICDT[number1 + 2] == volume_JNc[number2 + 1] ){
            					//System.out.println("シングル" + buffer1[number1] + " " + buffer2[number2]);
            					pw1.println(buffer1[number1]);
            					number1++;
            					pw.println(buffer1[number1] + " " + buffer2[number2]);


            				}
            				else if(volume_delete_JNc == volume_nikkei_HTICDT[number1] && volume_nikkei_HTICDT[number1 + 1] == volume_JNc[number2 + 2] ){
            					//System.out.println("シングル2" + buffer1[number1] + " " + buffer2[number2]);
            					pw2.println(buffer2[number2]);
            					number2++;
            					pw.println(buffer1[number1] + " " + buffer2[number2]);

            				}
            			}
            			else{
            					pw.println(buffer1[number1] + " " + buffer2[number2]);

                    			//System.out.println(buffer1[number1] + " " + buffer2[number2]);
                    			//System.out.println(buffer1[number1] + " " + buffer2[number2]);

                    			while(volume_nikkei_HTICDT[number1] > sum_volume){

                    				//System.out.println("come on");

                    				sum_volume += volume_JNc[number2];


                    				number2++;
                    				if(volume_nikkei_HTICDT[number1] > sum_volume){
                    					pw.println("--------------------------------------------------------------------- " + buffer2[number2]);
                    				}
                    				if(number2 > count_konma){

                    				}
                    				else{
                    					/*length_konma = buffer2[number2].length();


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
                                		count2 = 0;*/
                    				}

                    			}

                    			if(volume_nikkei_HTICDT[number1] == sum_volume){
                    				//System.out.println("ok");
                    			}
                    			else if(volume_nikkei_HTICDT[number1] != sum_volume){

                    			}
                    			else if(volume_nikkei_HTICDT[number1] != sum_volume){
                    				System.out.println("NG");
                    				System.out.println(volume_nikkei);
                    				System.out.println(sum_volume);

                    				System.out.println(buffer1[number1] + " " + buffer2[number2]);
                    				System.out.println(buffer1[number1 +1 ] + " " + buffer2[number2 +1 ]);
                    				pw.close();
                    				return;
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

                for(int i=0;i<=number1;i++){
            		buffer1[i] = null;
            	}
                for(int i=0;i<=count_konma + 1;i++){
            		buffer2[i] = null;
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



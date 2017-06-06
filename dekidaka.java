import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
//同じ時間の注文を一つの注文にするためにプログラム(親注文)同一注文の抽出(JNIcデータ)
import java.math.BigDecimal;
public class dekidaka{

    public static void main(String[] args) throws IOException {

    	String Index = null;

    	BufferedReader br = new BufferedReader(new FileReader("filelist.txt"));//読み取りたいファイル名の記入
        String txtFileName;

        while((txtFileName = br.readLine()) != null) {
        	//String Index[] = new String[400000];

            //String buffer1[] = new String[400000];
            int day = 0;//日付
            int day_before1 = 0;//日付
            int day_before2 = 0;//日付
            /*double time1[] = new double[400000];
            double time2[] = new double[400000];
            double time3[] = new double[400000];
            double time4[] = new double[400000];
            double time5[] = new double[400000];*/
            double time1;
            double time2;
            double time3;
            double time4;
            //double time_total[] = new double[400000];
            double time_total = 0.0;
            //int dekidaka[] = new int[400000];
            int dekidaka = 0;
            int dekidaka_before1 = 0;
            int dekidaka_before2 = 0;
            //double time_tmp1 = 0;
            //double time_tmp2 = 0;

            int count1 = 0;
            int count2 = 0;
            int total_count[] = new int[100];
            int n = 0;
            double z = 0.0;
            int s_total = 0;
            int total = 0;
            double para = 0;


            int count_if = 0;
            int count_a = 0;
            int count_b = 0;
            int count_c = 0;
            int count_d = 0;
            int i1;
            int i2 = 0;
            int i3 = 0;
            int i4 = 0;
            int i5 = 0;
            int length_micro;

        	double time_sum1 = 0.0;
        	double time_sum2 = 0.0;
        	double time_dif = 0.0;
        	int count_dif = 0;
        	int day_dummy = 0;

        	int count_for1 = 0;
        	int count_for2 = 0;

        	String time = null;
        	String time_before1 = null;
        	String time_before2 = null;


            FileReader fr = new FileReader(txtFileName);
            BufferedReader brtxt = new BufferedReader(fr);
            String line ="";

            String[] filename = txtFileName.split("\\.");

         	File file = new File(filename[0] + "_oyaorder_micro.txt");//時間差に0を含むときは0を記入
         	PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(
         			//"F:\\個別株\\TICST120\\201602\\" +
         	file)));


            while ((line = brtxt.readLine()) != null) {
            	Index = line;


                		//String d5 = Index.substring(0,8);//ミリ秒のとき
            			String d5 = Index.substring(12,20);//2016年取得ロイター通信社データ
                		//System.out.println(line);

                		//String d5 = Index.substring(8,16);//個別株のとき

            			day =  Integer.parseInt(d5);




                    	/*String d1 = Index.substring(9,11);//日経平均先物　ここから
                    	String d2 = Index.substring(12,14);
                    	String d3 = Index.substring(15,17);
                    	//String d4 = Index.substring(18,21);//日経平均先物　普通バージョン
                    	String d4 = Index.substring(18,20);//日経平均先物　0.01s単位バージョン




                    	time1 = Double.parseDouble(d1);
                    	time2 = Double.parseDouble(d2);
                    	time3 = Double.parseDouble(d3);
                    	time4 = Double.parseDouble(d4);

                    	time_total=time1*3600 + time2*60 + time3 + time4/100;//日経平均先物　0.01s単位バージョン
                    	BigDecimal x1 = new BigDecimal(time_total);//日経平均先物　0.01s単位バージョン
                    	time_total = x1.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();//日経平均先物　0.01s単位バージョン

                    	//System.out.println(x1);
                    	//System.out.println(time_total);
                    	//System.out.println(d1 + "" + d2 + "" + d3 + "" + d4);

                    	String db = Index.substring(22);
                    	dekidaka = Integer.parseInt(db);//日経平均先物　ここまで*/

                    	String d1 = Index.substring(21,23);//2016年取得ロイター通信社データ　ここから
                    	String d2 = Index.substring(24,26);
                    	String d3 = Index.substring(27,29);
                    	String d4 = Index.substring(30,36);
                    	time = Index.substring(21,36);//マイクロ秒
                    	//String d4 = Index.substring(30,33);//ミリ秒





                    	time1 = Double.parseDouble(d1);
                    	time2 = Double.parseDouble(d2);
                    	time3 = Double.parseDouble(d3);
                    	time4 = Double.parseDouble(d4);


                    	time_total =time1*3600 + time2*60 + time3 + time4/1000000;//マイクロ秒
                    	//time_total =time1*3600 + time2*60 + time3 + time4/1000;//ミリ秒

                    	BigDecimal x1 = new BigDecimal(time_total);
                    	time_total = x1.setScale(6, BigDecimal.ROUND_HALF_UP).doubleValue();//ミリ秒(3)かマイクロ秒(6)どちらかで変更


                    	//System.out.println(x1);
                    	//System.out.println(time_total);
                    	//System.out.println(d1 + " " + d2 + " " + d3 + " " + d4);

                    	int length_konma = Index.length();

                    	for(i1=0;i1<length_konma;i1++){
                            String a = Index.substring(i1,i1+1);
                            if(count_if == 6 && !(a.equals(",")) && count_for1 == 0){//時間を取り出すプログラム
                            	i2 = i1;
                            	count_for1++;
                            }
                            if(count_if == 7 && count_for1 != 0 && count_for2 == 0){
                            	i3 = i1-1;
                            	//System.out.println(i2 + " " + i3);
                            	count_for2++;
                            	String db = Index.substring(i2,i3);
                            	dekidaka = Integer.parseInt(db);

                            }

                            if(a.equals(",")){
                                count_if++;
                            }
                        }
                		count_if = 0;
                		count_for1 = 0;
                		count_for2 = 0;



                		i2 = 0;
                        i3 = 0;//2016年取得ロイター通信社データ　ここまで









                		/*length_micro = Index.length();//個別株式のとき（ここから）
                        for(i1=0;i1<length_micro;i1++){
                            String a = Index.substring(i1,i1+1);
                            if(count_if == 9 && !(a.equals(",")) && count_a == 0){
                            	i2 = i1+1;
                            	count_a++;
                            }
                            if(count_if == 10 && count_a != 0 && count_b == 0){
                            	i3 = i1-2;
                            	count_b++;
                            	String d1 = Index.substring(i2,i3-10);
                            	String d2 = Index.substring(i2+2,i3-8);
                            	String d3 = Index.substring(i2+4,i3-6);
                            	String d4 = Index.substring(i2+6,i3);//個別株　普通バージョン
                            	//String d4 = Index.substring(i2+6,i3-3);//個別株　ミリ秒バージョン
                            	//

                            	time1 = Double.parseDouble(d1);
                            	time2 = Double.parseDouble(d2);
                            	time3 = Double.parseDouble(d3);
                            	time4 = Double.parseDouble(d4);

                            	time_total=time1*3600 + time2*60 + time3 + time4/1000000;//個別株　普通バージョン
                            	BigDecimal x1 = new BigDecimal(time_total);//個別株　普通バージョン
                            	time_total = x1.setScale(6, BigDecimal.ROUND_HALF_UP).doubleValue();//個別株　普通バージョン

                            	/*time_total=time1*3600 + time2*60 + time3 + time4/1000;//個別株　ミリ秒バージョン
                            	BigDecimal x1 = new BigDecimal(time_total);//個別株　ミリ秒バージョン
                            	time_total = x1.setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue();*///個別株　ミリ秒バージョン



                            	//System.out.println(x1);
                            	//System.out.println(time_total);
                            	//System.out.println(d1 + "" + d2 + "" + d3 + "" + d4);
                            /*}
                            if(count_if==14 && count_c == 0){
                                i4 = i1;
                                count_c++;
                            }
                            if(count_if==15 && count_c != 0 && count_d == 0){
                                i5 = i1-1;
                                count_d++;
                                String db = Index.substring(i4,i5);

                                //System.out.println(i4 + " " + i5);
                                if(i4 == i5){
                                	dekidaka = 0;
                                }
                                else if(i4 != i5){
                                	dekidaka = Integer.parseInt(db);
                                }

                            }

                            if(a.equals(",")){
                                count_if++;
                            }
                        }
                        count_a = 0;
                        count_b = 0;
                        count_c = 0;
                        count_d = 0;
                        count_if = 0;
                        i2 = 0;
                        i3 = 0;
                        i4 = 0;
                        i5 = 0;*///個別株のとき　ここまで
                        //System.out.println(dekidaka[i]);

                        if(count_dif == 0){
                        	time_sum1 = time_total;
                        	count_dif++;
                        	//day_dummy = day;
                        }
                        else if(count_dif != 0){
                        	time_sum2 = time_total;
                        	time_dif = time_sum2 - time_sum1;


                        	//BigDecimal x2 = new BigDecimal(time_dif);//日経平均先物　普通バージョン
                    		//time_dif = x2.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();//日経平均先物　普通バージョン


                    		BigDecimal x2 = new BigDecimal(time_dif);//日経平均先物　普通バージョン
                    		time_dif = x2.setScale(6, BigDecimal.ROUND_HALF_UP).doubleValue();//2016年取得ロイター通信社」
                    		//ミリ秒(3)かマイクロ秒(6)どちらかで変更


                    		//System.out.println(x2);
                    		//System.out.println(time_dif);


                        	/*BigDecimal x1 = new BigDecimal(time_dif);//個別株　普通バージョン
                    		time_dif = x1.setScale(6, BigDecimal.ROUND_HALF_UP).doubleValue();*///個別株　普通バージョン

                    		/*BigDecimal x1 = new BigDecimal(time_dif);//ミリ秒バージョン
                    		time_dif = x1.setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue();*///個別株　ミリ秒バージョン

                    		//System.out.println(x1);
                    		//System.out.println(time_dif);


                    		//if(time_dif > 0){//時間差に0を含むか含まないか　これは非常に重要
                    			//pw.println(day + " " + time_dif);
                    		//}
                        	time_sum1 = time_total;
                        }
                        /*else if(day_dummy != day){
                        	time_sum1 = time_total;
                        	//day_dummy = day;
                        }*/


                        /*File file = new File("201510_ufj_dekidaka.txt");
                    	PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(file)));*/

                    	//for(int i=0;i<=n-1;i++){
                    		//time_tmp1 = time_total[i+1] - time_total[i];


                    	if(day != day_before1 && day != day_before2){

                    	}

                        if(count_dif == 1){
                        	//System.out.println("1");
                        	dekidaka_before1 = dekidaka;
                        	day_before1 = day;
                        	time_before1 = time;
                        }
                        else{

                        	//System.out.println(time_dif);

                        	if(Index != null && time_dif != 0.0){

                    			count1++;

                    		}
                    		if(Index != null && count1 > 1){

                    			pw.println(day_before2 + " "+ time_before2 + " " + dekidaka_before2);
                    			pw.println(day_before1 + " "+ time_before1 + " " + dekidaka_before1);
                    			count1 = 0;
                    		}
                    		if(Index != null && count2 == 1 && time_dif != 0){

                    			pw.println(" "+ dekidaka_before1);
                    			count1 = 0;
                    			count2 = 0;
                    		}

                    		if(Index != null && count1 == 1 && time_dif == 0){

                    			pw.println(day_before2 + " "+ time_before2 + " " + dekidaka_before2);
                    			pw.print(day_before1 + " "+ time_before1 + " " + dekidaka_before1);
                    			count1=0;
                    			count2++;
                    		}
                    		else if(Index != null && count1 == 0 && count2 == 0 && time_dif == 0){

                    			pw.print(day_before1 + " "+ time_before1 + " " + dekidaka_before1);

                    			count2++;
                    		}
                    	    else if(Index != null && count2 == 1 && time_dif == 0){

                    			pw.print(" "+ dekidaka_before1);
                    		}
                    		dekidaka_before2 = dekidaka_before1;
                        	day_before2 = day_before1;
                        	time_before2 = time_before1;
                    		dekidaka_before1 = dekidaka;
                        	day_before1 = day;
                        	time_before1 = time;


                        }

                		/*if(count_dif == 1){
                        	count1++;
                        	count_dif++;
                        }*/



                        //if(count_dif == 1){

                        //}

                        	count_dif++;



                    	}



    		/*if(time_dif == 0){
    			pw.println(" " + dekidaka_before1);
    		}
    		else if(count1 == 1 && day_before1 != day_before2){
    			pw.println(day_before2 + " " +dekidaka_before2);
    			pw.println(day_before1 + " " +dekidaka_before1);
    		}
    		/*else if(count1 == 1 && day_before1 != day_before2){
    			pw.println(day_before2 + " " +dekidaka_before2);
    			pw.println(day_before1 + " " +dekidaka_before1);
    		}*/
    		/*else{
    			pw.println(day_before1 + " " +dekidaka_before1);
    		}*/

            if(day != day_before1 && day != day_before2){

        	}

            if(count_dif == 1){
            	//System.out.println("1");
            	dekidaka_before1 = dekidaka;
            	day_before1 = day;
            	time_before1 = time;
            }
            else{

            	//System.out.println(time_dif);

            	if(Index != null && time_dif != 0.0){

        			count1++;

        		}
        		if(Index != null && count1 > 1){

        			pw.println(day_before2 + " "+ time_before2 + " " + dekidaka_before2);
        			pw.println(day_before1 + " "+ time_before1 + " " + dekidaka_before1);
        			count1 = 0;
        		}
        		if(Index != null && count2 == 1 && time_dif != 0){

        			pw.println(" "+ dekidaka_before1);
        			count1 = 0;
        			count2 = 0;
        		}

        		if(Index != null && count1 == 1 && time_dif == 0){

        			pw.println(day_before2 + " "+ time_before2 + " " + dekidaka_before2);
        			pw.print(day_before1 + " "+ time_before1 + " " + dekidaka_before1);
        			count1=0;
        			count2++;
        		}
        		else if(Index != null && count1 == 0 && count2 == 0 && time_dif == 0){

        			pw.print(day_before1 + " "+ time_before1 + " " + dekidaka_before1);

        			count2++;
        		}
        	    else if(Index != null && count2 == 1 && time_dif == 0){

        			pw.print(" "+ dekidaka_before1);
        		}
        		dekidaka_before2 = dekidaka_before1;
            	day_before2 = day_before1;
            	time_before2 = time_before1;
        		dekidaka_before1 = dekidaka;
            	day_before1 = day;
            	time_before1 = time;


            }

            if(time_dif != 0 && count1 != 0){
            	pw.println(day_before2 + " "+ time_before2 + " " + dekidaka_before2);
            }




    		 brtxt.close();
             fr.close();
             pw.close();
        }
        br.close();
    }
}







        /*try {

            FileReader fr = new FileReader("201510_ufj.txt");
            BufferedReader br = new BufferedReader(fr);
            String line ="";
            while ((line = br.readLine()) != null) {

            	Index[n] = line;
            	n++;
            	}
            br.close();
            fr.close();
         } catch (IOException ex) {
        	 ex.printStackTrace();
        	 }*/









import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;

public class time_interval{

    public static void main(String[] args) throws IOException{

        BufferedReader br = new BufferedReader(new FileReader("filelist_dekidaka.txt"));//読み取りたいファイル名の記入
        String txtFileName;

        while((txtFileName = br.readLine()) != null) {

        	String Index;

        	double hour2;
        	double minute2;
        	double second2;

        	double micro2;


        	double k_all_time = 0.0;

        	int length_konma = 0;
        	int count_if = 0;
        	int count1 = 0;
        	int count2 = 0;
        	int i1 = 0;
        	int i2 = 0;
        	int i3 = 0;





        FileReader fr = new FileReader(txtFileName);
        BufferedReader brtxt = new BufferedReader(fr);
        String line ="";

        String[] filename = txtFileName.split("\\.");




     	/*File file1 = new File(filename[0] +"_comb2"+	"_time01.txt");//comb2の場合（ここから）
     	PrintWriter pw1 = new PrintWriter(new BufferedWriter(new FileWriter(file1)));
     	File file2 = new File(filename[0] +"_comb2"+	"_time02.txt");
     	PrintWriter pw2 = new PrintWriter(new BufferedWriter(new FileWriter(file2)));
     	File file3 = new File(filename[0] +"_comb2"+	"_time03.txt");
     	PrintWriter pw3 = new PrintWriter(new BufferedWriter(new FileWriter(file3)));
     	File file4 = new File(filename[0] +"_comb2"+	"_time04.txt");
     	PrintWriter pw4 = new PrintWriter(new BufferedWriter(new FileWriter(file4)));
     	File file5 = new File(filename[0] +"_comb2"+	"_time05.txt");
     	PrintWriter pw5 = new PrintWriter(new BufferedWriter(new FileWriter(file5)));
     	File file6 = new File(filename[0] +"_comb2"+	"_time06.txt");
     	PrintWriter pw6 = new PrintWriter(new BufferedWriter(new FileWriter(file6)));
     	File file7 = new File(filename[0] +"_comb2"+	"_time07.txt");
     	PrintWriter pw7 = new PrintWriter(new BufferedWriter(new FileWriter(file7)));
     	File file8 = new File(filename[0] +"_comb2"+	"_time08.txt");
     	PrintWriter pw8 = new PrintWriter(new BufferedWriter(new FileWriter(file8)));
     	File file9 = new File(filename[0] +"_comb2"+	"_time09.txt");
     	PrintWriter pw9 = new PrintWriter(new BufferedWriter(new FileWriter(file9)));
     	File file10 = new File(filename[0] +"_comb2"+	"_time10.txt");
     	PrintWriter pw10 = new PrintWriter(new BufferedWriter(new FileWriter(file10)));
     	File file11 = new File(filename[0] +"_comb2"+	"_time11.txt");
     	PrintWriter pw11 = new PrintWriter(new BufferedWriter(new FileWriter(file11)));
     	File file12 = new File(filename[0] +"_comb2"+	"_time12.txt");
     	PrintWriter pw12 = new PrintWriter(new BufferedWriter(new FileWriter(file12)));
     	File file13 = new File(filename[0] +"_comb2"+	"_time13.txt");
     	PrintWriter pw13 = new PrintWriter(new BufferedWriter(new FileWriter(file13)));
     	File file14 = new File(filename[0] +"_comb2"+	"_time14.txt");
     	PrintWriter pw14 = new PrintWriter(new BufferedWriter(new FileWriter(file14)));
     	File file15 = new File(filename[0] +"_comb2"+	"_time15.txt");
     	PrintWriter pw15 = new PrintWriter(new BufferedWriter(new FileWriter(file15)));
     	File file16 = new File(filename[0] +"_comb2"+	"_time16.txt");
     	PrintWriter pw16 = new PrintWriter(new BufferedWriter(new FileWriter(file16)));
     	File file17 = new File(filename[0] +"_comb2"+	"_time17.txt");
     	PrintWriter pw17 = new PrintWriter(new BufferedWriter(new FileWriter(file17)));
     	File file18 = new File(filename[0] +"_comb2"+	"_time18.txt");
     	PrintWriter pw18 = new PrintWriter(new BufferedWriter(new FileWriter(file18)));
     	File file19 = new File(filename[0] +"_comb2"+	"_time19.txt");
     	PrintWriter pw19 = new PrintWriter(new BufferedWriter(new FileWriter(file19)));
     	File file20 = new File(filename[0] +"_comb2"+	"_time20.txt");
     	PrintWriter pw20 = new PrintWriter(new BufferedWriter(new FileWriter(file20)));
     	File file21 = new File(filename[0] +"_comb2"+	"_time21.txt");
     	PrintWriter pw21 = new PrintWriter(new BufferedWriter(new FileWriter(file21)));
     	File file22 = new File(filename[0] +"_comb2"+	"_time22.txt");
     	PrintWriter pw22 = new PrintWriter(new BufferedWriter(new FileWriter(file22)));
     	File file23 = new File(filename[0] +"_comb2"+	"_time23.txt");
     	PrintWriter pw23 = new PrintWriter(new BufferedWriter(new FileWriter(file23)));
     	File file24 = new File(filename[0] +"_comb2"+	"_time24.txt");
     	PrintWriter pw24 = new PrintWriter(new BufferedWriter(new FileWriter(file24)));
     	File file25 = new File(filename[0] +"_comb2"+	"_time25.txt");
     	PrintWriter pw25 = new PrintWriter(new BufferedWriter(new FileWriter(file25)));
     	File file26 = new File(filename[0] +"_comb2"+	"_time26.txt");
     	PrintWriter pw26 = new PrintWriter(new BufferedWriter(new FileWriter(file26)));//comb2の場合（ここまで）*/

     	/*File file1 = new File(filename[0]  + 	"_time01.txt");//個別株の場合の場合（ここから）
     	PrintWriter pw1 = new PrintWriter(new BufferedWriter(new FileWriter(file1)));
     	File file2 = new File(filename[0]  + 	"_time02.txt");
     	PrintWriter pw2 = new PrintWriter(new BufferedWriter(new FileWriter(file2)));
     	File file3 = new File(filename[0]  + 	"_time03.txt");
     	PrintWriter pw3 = new PrintWriter(new BufferedWriter(new FileWriter(file3)));
     	File file4 = new File(filename[0]  + 	"_time04.txt");
     	PrintWriter pw4 = new PrintWriter(new BufferedWriter(new FileWriter(file4)));
     	File file5 = new File(filename[0]  + 	"_time05.txt");
     	PrintWriter pw5 = new PrintWriter(new BufferedWriter(new FileWriter(file5)));
     	File file6 = new File(filename[0]  + 	"_time06.txt");
     	PrintWriter pw6 = new PrintWriter(new BufferedWriter(new FileWriter(file6)));
     	File file7 = new File(filename[0]  + 	"_time07.txt");
     	PrintWriter pw7 = new PrintWriter(new BufferedWriter(new FileWriter(file7)));
     	File file8 = new File(filename[0]  + 	"_time08.txt");
     	PrintWriter pw8 = new PrintWriter(new BufferedWriter(new FileWriter(file8)));
     	File file9 = new File(filename[0]  + 	"_time09.txt");
     	PrintWriter pw9 = new PrintWriter(new BufferedWriter(new FileWriter(file9)));
     	File file10 = new File(filename[0]  + 	"_time10.txt");
     	PrintWriter pw10 = new PrintWriter(new BufferedWriter(new FileWriter(file10)));
     	File file11 = new File(filename[0]  + 	"_time11.txt");
     	PrintWriter pw11 = new PrintWriter(new BufferedWriter(new FileWriter(file11)));
     	File file12 = new File(filename[0]  + 	"_time12.txt");
     	PrintWriter pw12 = new PrintWriter(new BufferedWriter(new FileWriter(file12)));
     	File file13 = new File(filename[0]  + 	"_time13.txt");
     	PrintWriter pw13 = new PrintWriter(new BufferedWriter(new FileWriter(file13)));
     	File file14 = new File(filename[0]  + 	"_time14.txt");
     	PrintWriter pw14 = new PrintWriter(new BufferedWriter(new FileWriter(file14)));
     	File file15 = new File(filename[0]  + 	"_time15.txt");
     	PrintWriter pw15 = new PrintWriter(new BufferedWriter(new FileWriter(file15)));
     	File file16 = new File(filename[0]  + 	"_time16.txt");
     	PrintWriter pw16 = new PrintWriter(new BufferedWriter(new FileWriter(file16)));
     	File file17 = new File(filename[0]  + 	"_time17.txt");
     	PrintWriter pw17 = new PrintWriter(new BufferedWriter(new FileWriter(file17)));
     	File file18 = new File(filename[0]  + 	"_time18.txt");
     	PrintWriter pw18 = new PrintWriter(new BufferedWriter(new FileWriter(file18)));
     	File file19 = new File(filename[0]  + 	"_time19.txt");
     	PrintWriter pw19 = new PrintWriter(new BufferedWriter(new FileWriter(file19)));
     	File file20 = new File(filename[0]  + 	"_time20.txt");
     	PrintWriter pw20 = new PrintWriter(new BufferedWriter(new FileWriter(file20)));
     	File file21 = new File(filename[0]  + 	"_time21.txt");
     	PrintWriter pw21 = new PrintWriter(new BufferedWriter(new FileWriter(file21)));
     	File file22 = new File(filename[0]  + 	"_time22.txt");
     	PrintWriter pw22 = new PrintWriter(new BufferedWriter(new FileWriter(file22)));
     	File file23 = new File(filename[0]  + 	"_time23.txt");
     	PrintWriter pw23 = new PrintWriter(new BufferedWriter(new FileWriter(file23)));
     	File file24 = new File(filename[0]  + 	"_time24.txt");
     	PrintWriter pw24 = new PrintWriter(new BufferedWriter(new FileWriter(file24)));
     	//File file25 = new File(filename[0]  + 	"_time25.txt");
     	//PrintWriter pw25 = new PrintWriter(new BufferedWriter(new FileWriter(file25)));
     	//File file26 = new File(filename[0]  + 	"_time26.txt");
     	//PrintWriter pw26 = new PrintWriter(new BufferedWriter(new FileWriter(file26)));//個別株の場合（ここまで）*/

        File file0 = new File(filename[0] +	"_time00.txt");//oyaorderなどの場合（ここから）
     	PrintWriter pw0 = new PrintWriter(new BufferedWriter(new FileWriter(file0)));
        File file1 = new File(filename[0] +	"_time01.txt");//oyaorderなどの場合（ここから）
     	PrintWriter pw1 = new PrintWriter(new BufferedWriter(new FileWriter(file1)));
     	File file2 = new File(filename[0] +	"_time02.txt");
     	PrintWriter pw2 = new PrintWriter(new BufferedWriter(new FileWriter(file2)));
     	File file3 = new File(filename[0] +	"_time03.txt");
     	PrintWriter pw3 = new PrintWriter(new BufferedWriter(new FileWriter(file3)));
     	File file4 = new File(filename[0] +	"_time04.txt");
     	PrintWriter pw4 = new PrintWriter(new BufferedWriter(new FileWriter(file4)));
     	File file5 = new File(filename[0] +	"_time05.txt");
     	PrintWriter pw5 = new PrintWriter(new BufferedWriter(new FileWriter(file5)));
     	File file6 = new File(filename[0] +	"_time06.txt");
     	PrintWriter pw6 = new PrintWriter(new BufferedWriter(new FileWriter(file6)));
     	File file7 = new File(filename[0] +	"_time07.txt");
     	PrintWriter pw7 = new PrintWriter(new BufferedWriter(new FileWriter(file7)));
     	File file8 = new File(filename[0] +	"_time08.txt");
     	PrintWriter pw8 = new PrintWriter(new BufferedWriter(new FileWriter(file8)));
     	File file9 = new File(filename[0] +	"_time09.txt");
     	PrintWriter pw9 = new PrintWriter(new BufferedWriter(new FileWriter(file9)));
     	File file10 = new File(filename[0] +	"_time10.txt");
     	PrintWriter pw10 = new PrintWriter(new BufferedWriter(new FileWriter(file10)));
     	File file11 = new File(filename[0] +	"_time11.txt");
     	PrintWriter pw11 = new PrintWriter(new BufferedWriter(new FileWriter(file11)));
     	File file12 = new File(filename[0] +	"_time12.txt");
     	PrintWriter pw12 = new PrintWriter(new BufferedWriter(new FileWriter(file12)));
     	File file13 = new File(filename[0] +	"_time13.txt");
     	PrintWriter pw13 = new PrintWriter(new BufferedWriter(new FileWriter(file13)));
     	File file14 = new File(filename[0] +	"_time14.txt");
     	PrintWriter pw14 = new PrintWriter(new BufferedWriter(new FileWriter(file14)));
     	File file15 = new File(filename[0] +	"_time15.txt");
     	PrintWriter pw15 = new PrintWriter(new BufferedWriter(new FileWriter(file15)));
     	File file16 = new File(filename[0] +	"_time16.txt");
     	PrintWriter pw16 = new PrintWriter(new BufferedWriter(new FileWriter(file16)));
     	File file17 = new File(filename[0] +	"_time17.txt");
     	PrintWriter pw17 = new PrintWriter(new BufferedWriter(new FileWriter(file17)));
     	File file18 = new File(filename[0] +	"_time18.txt");
     	PrintWriter pw18 = new PrintWriter(new BufferedWriter(new FileWriter(file18)));
     	File file19 = new File(filename[0] +	"_time19.txt");
     	PrintWriter pw19 = new PrintWriter(new BufferedWriter(new FileWriter(file19)));
     	File file20 = new File(filename[0] +	"_time20.txt");
     	PrintWriter pw20 = new PrintWriter(new BufferedWriter(new FileWriter(file20)));
     	File file21 = new File(filename[0] +	"_time21.txt");
     	PrintWriter pw21 = new PrintWriter(new BufferedWriter(new FileWriter(file21)));
     	File file22 = new File(filename[0] +	"_time22.txt");
     	PrintWriter pw22 = new PrintWriter(new BufferedWriter(new FileWriter(file22)));
     	File file23 = new File(filename[0] +	"_time23.txt");
     	PrintWriter pw23 = new PrintWriter(new BufferedWriter(new FileWriter(file23)));
     	File file24 = new File(filename[0] +	"_time24.txt");
     	PrintWriter pw24 = new PrintWriter(new BufferedWriter(new FileWriter(file24)));
     	File file25 = new File(filename[0] +	"_time25.txt");
     	PrintWriter pw25 = new PrintWriter(new BufferedWriter(new FileWriter(file25)));
     	File file26 = new File(filename[0] +	"_time26.txt");
     	PrintWriter pw26 = new PrintWriter(new BufferedWriter(new FileWriter(file26)));//comb2の場合（ここまで）


        while ((line = brtxt.readLine()) != null) {

        	Index = line;

        	/*String d4 = Index.substring(85,87);
        	hour2 = Double.parseDouble(d4);
        	String d5 = Index.substring(88,90);
        	minute2 = Double.parseDouble(d5);
        	String d6 = Index.substring(91,97);
        	second2 = Double.parseDouble(d6);
        	k_all_time = hour2*60*60 + minute2*60 + second2;
        	//2006年～2010年（comb2）*/

        	/*String d4 = Index.substring(88,90);
        	hour2 = Double.parseDouble(d4);
        	String d5 = Index.substring(91,93);
        	minute2 = Double.parseDouble(d5);
        	String d6 = Index.substring(94,100);
        	second2 = Double.parseDouble(d6);
        	k_all_time = hour2*60*60 + minute2*60 + second2;
        	//2011年～（comb2）*/

        	/*String d4 = Index.substring(21,23);
        	hour2 = Double.parseDouble(d4);
        	String d5 = Index.substring(24,26);
        	minute2 = Double.parseDouble(d5);
        	String d6 = Index.substring(27,36);
        	second2 = Double.parseDouble(d6);
        	k_all_time = hour2*60*60 + minute2*60 + (second2/1000000);
        	BigDecimal x1 = new BigDecimal(k_all_time);
        	k_all_time = x1.setScale(6, BigDecimal.ROUND_HALF_UP).doubleValue();*/
        	//2016年取得ロイター通信社データ



        	/*String d4 = Index.substring(91,93);
        	hour2 = Double.parseDouble(d4);
        	String d5 = Index.substring(94,96);
        	minute2 = Double.parseDouble(d5);
        	String d6 = Index.substring(97,106);
        	second2 = Double.parseDouble(d6);
        	k_all_time = hour2*60*60 + minute2*60 + (second2/1000000);
        	BigDecimal x1 = new BigDecimal(k_all_time);
        	k_all_time = x1.setScale(6, BigDecimal.ROUND_HALF_UP).doubleValue();
        	//System.out.println(d4 + " " + d5 + " " + d6  );*/
        	//2016年取得ロイター通信社データ(comb2)データ

        	String d4 = Index.substring(9,11);
        	hour2 = Double.parseDouble(d4);
        	String d5 = Index.substring(12,14);
        	minute2 = Double.parseDouble(d5);
        	String d6 = Index.substring(15,24);
        	second2 = Double.parseDouble(d6);
        	k_all_time = hour2*60*60 + minute2*60 + (second2/1000000);
        	BigDecimal x1 = new BigDecimal(k_all_time);
        	k_all_time = x1.setScale(6, BigDecimal.ROUND_HALF_UP).doubleValue();
        	//oyaorderなどの場合
        	//System.out.println(d4 + ":" + d5  + ":" + d6);







        	/*length_konma = Index.length();//個別株(ここから)

    		for(i1=0;i1<length_konma;i1++){
                String a = Index.substring(i1,i1+1);
                if(count_if == 9 && !(a.equals(",")) && count1 == 0){
                	i2 = i1+1;
                	count1++;
                }
                if(count_if == 10 && count1 != 0 && count2 == 0){
                	i3 = i1-2;
                	count2++;
                	String d1 = Index.substring(i2,i3-10);
                	String d2 = Index.substring(i2+2,i3-8);
                	String d3 = Index.substring(i2+4,i3-6);
                	String d4 = Index.substring(i2+6,i3);
                	//System.out.println(d1 + "" + d2 + "" + d3 + "" + d4);

                	hour2 = Double.parseDouble(d1);
                	minute2 = Double.parseDouble(d2);
                	second2 = Double.parseDouble(d3);
                	micro2 = Double.parseDouble(d4);

                	hour2 = hour2*60*60;
                	minute2 = minute2*60;
                	micro2 = micro2/1000000;
                	k_all_time = hour2 + minute2 + second2 + micro2;
                	BigDecimal x1 = new BigDecimal(k_all_time);
                	k_all_time = x1.setScale(6, BigDecimal.ROUND_HALF_UP).doubleValue();
                	//System.out.println(d1 + "" + d2 + "" + d3 + "" + d4);
                }
                if(a.equals(",")){
                    count_if++;
                }
            }
    		count_if = 0;
    		count1 = 0;
    		count2 = 0;


    		i2 = 0;
            i3 = 0;//個別株(ここまで)*/



        	//(limit_order2_final_tradeのとき）（ここから）
        	/*String JNIc_split[] = line.split(",", 0);
        	hour2 = Double.parseDouble(JNIc_split[1].substring(0, 2));//時間
        	minute2 = Double.parseDouble(JNIc_split[1].substring(3, 5));//分
        	second2 = Double.parseDouble(JNIc_split[1].substring(6));//秒
        	k_all_time = hour2*3600 + minute2*60 + second2;//時間を秒換算*/
        	//(limit_order2_final_tradeのとき）（ここまで）



        	if(31500 <= k_all_time && k_all_time < 32400){//8:45-9:00
        		pw0.println(Index);
        	}
        	else if(32400 <= k_all_time && k_all_time < 33300){
        		pw1.println(Index);
        	}
        	else if(33300 <= k_all_time && k_all_time < 34200){
        		pw2.println(Index);
        	}
        	else if(34200 <= k_all_time && k_all_time < 35100){
        		pw3.println(Index);
        	}
        	else if(35100 <= k_all_time && k_all_time < 36000){
        		pw4.println(Index);
        	}
        	else if(36000 <= k_all_time && k_all_time < 36900){
        		pw5.println(Index);
        	}
        	else if(36900 <= k_all_time && k_all_time < 37800){
        		pw6.println(Index);
        	}
        	else if(37800 <= k_all_time && k_all_time < 38700){
        		pw7.println(Index);
        	}
        	else if(38700 <= k_all_time && k_all_time < 39600){//11:45-12:00
        		pw8.println(Index);
        	}
        	else if(39600 <= k_all_time && k_all_time < 40500){
        		pw9.println(Index);
        	}
        	else if(40500 <= k_all_time && k_all_time < 41400){//個別株取引終了時間（昼休みに入る）後半を等号にすること
        		pw10.println(Index);
        	}
        	else if(41400 <= k_all_time && k_all_time < 42300){
        		pw11.println(Index);
        	}
        	else if(42300 <= k_all_time && k_all_time < 43200){
        		pw12.println(Index);
        	}
        	else if(43200 <= k_all_time && k_all_time < 44100){
        		pw13.println(Index);
        	}
        	else if(44100 <= k_all_time && k_all_time < 45000){
        		pw14.println(Index);
        	}
        	else if(45000 <= k_all_time && k_all_time < 45900){
        		pw15.println(Index);
        	}
        	else if(45900 <= k_all_time && k_all_time < 46800){
        		pw16.println(Index);
        	}
        	else if(46800 <= k_all_time && k_all_time < 47700){
        		pw17.println(Index);
        	}
        	else if(47700 <= k_all_time && k_all_time < 48600){
        		pw18.println(Index);
        	}
        	else if(48600 <= k_all_time && k_all_time < 49500){
        		pw19.println(Index);
        	}
        	else if(49500 <= k_all_time && k_all_time < 50400){
        		pw20.println(Index);
        	}
        	else if(50400 <= k_all_time && k_all_time < 51300){
        		pw21.println(Index);
        	}
        	else if(51300 <= k_all_time && k_all_time < 52200){
        		pw22.println(Index);
        	}
        	else if(52200 <= k_all_time && k_all_time < 53100){
        		pw23.println(Index);
        	}
        	else if(53100 <= k_all_time && k_all_time < 54000){//個別株取引終了時間　後半を等号にすること(14:45-15:00)
        		pw24.println(Index);
        	}
        	else if(54000 <= k_all_time && k_all_time < 54900){
        		//pw25.println(Index);
        	}
        	else if(54900 <= k_all_time && k_all_time < 55800){
        		//pw26.println(Index);
        	}
        	else{
        		System.out.println("error");
        	}



        }


        brtxt.close();
        fr.close();
        pw0.close();
        pw1.close();
        pw2.close();
        pw3.close();
        pw4.close();
        pw5.close();
        pw6.close();
        pw7.close();
        pw8.close();
        pw9.close();
        pw10.close();
        pw11.close();
        pw12.close();
        pw13.close();
        pw14.close();
        pw15.close();
        pw16.close();
        pw17.close();
        pw18.close();
        pw19.close();
        pw20.close();
        pw21.close();
        pw22.close();
        pw23.close();
        pw24.close();
        pw25.close();
        //pw26.close();
        }
    }
}


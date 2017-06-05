import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
//2016年に入手したJNcファイルに対し、「約定」「日中の取引」などを限定して出力するプログラム
public class JNc_nittyuu{

    public static void main(String[] args) throws IOException{

        BufferedReader br = new BufferedReader(new FileReader("filelist.txt"));//読み取りたいファイル名の記入
        String txtFileName;

        while((txtFileName = br.readLine()) != null) {

        	String Index;

        	double hour = 0.0;
        	double minute = 0.0;
        	double second = 0.0;

        	double micro = 0.0;


        	double k_all_time = 0.0;
        	int day = 0;

        	int length_konma = 0;
        	int count_if = 0;
        	int count1 = 0;
        	int count2 = 0;
        	int count3 = 0;
        	int count4 = 0;
        	int i1 = 0;
        	int i2 = 0;
        	int i3 = 0;
        	int i4 = 0;
        	int i5 = 0;



        FileReader fr = new FileReader(txtFileName);
        BufferedReader brtxt = new BufferedReader(fr);
        String line ="";

        String[] filename = txtFileName.split("\\.");



     	File file = new File(filename[0]  + 	"_data.txt");
     	PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(file)));


        while ((line = brtxt.readLine()) != null) {

        	Index = line;



        	length_konma = Index.length();

        	if(!(Index.substring(0,1).equals("#"))){
        		for(i1=0;i1<length_konma;i1++){
                    String a = Index.substring(i1,i1+1);
                    if(count_if == 3 && !(a.equals(",")) && count1 == 0){//時間を取り出すプログラム
                    	i2 = i1;
                    	count1++;
                    }
                    if(count_if == 4 && count1 != 0 && count2 == 0){
                    	i3 = i1-1;
                    	count2++;
                    	String d1 = Index.substring(i2,i3-13);//時間
                    	String d2 = Index.substring(i2+3,i3-10);//分
                    	String d3 = Index.substring(i2+6,i3);//秒

                    	hour = Double.parseDouble(d1);//時間
                    	minute = Double.parseDouble(d2);//分
                    	second = Double.parseDouble(d3);//秒
                    }
                    /*if(count_if == 4 && !(a.equals(",")) && count3 == 0){//約定を取り出すプログラム
                    	i4 = i1;
                    	count3++;
                    }
                    if(count_if == 5 && count3 != 0 && count4 == 0){
                    	i5 = i1-1;
                    	count4++;
                    	String d5 = Index.substring(i4,i5);
                    	//System.out.println(d5);
                    	if(d5.equals("Trade")){
                    		pw.println(Index);
                    	}
                    }*/
                    if(a.equals(",")){
                        count_if++;
                    }
                }
        		count_if = 0;
        		count1 = 0;
        		count2 = 0;
        		count3 = 0;
        		count4 = 0;

        		i2 = 0;
                i3 = 0;
                i4 = 0;
                i5 = 0;

                String d5 = Index.substring(12,20);
                day = Integer.parseInt(d5);


                hour = hour*60*60;//時間を秒に換算
            	minute = minute*60;//時間を分に換算
            	k_all_time = hour + minute + second;//その取引を秒で換算
            	if((day < 20110214) &&
            			((k_all_time >= 32400 && k_all_time<=39600) || (k_all_time >= 45000 && k_all_time <= 56700))){//56700にする理由は15時15分以降の微妙のタイムラグも取得したいからである。
            		pw.println(Index);
            	}
            	else if((day >= 20110214) &&
            			(k_all_time >= 32400 && k_all_time <= 56700)){
            		pw.println(Index);
            	}

        	}

        }


        brtxt.close();
        fr.close();
        pw.close();

        }
    }
}


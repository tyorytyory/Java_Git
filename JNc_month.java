import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
//2016年に新たに入手したロイター通信社のデータから月によりデータを分けるプログラム
public class JNc_month{

    public static void main(String[] args) throws IOException{

        BufferedReader br = new BufferedReader(new FileReader("filelist.txt"));//読み取りたいファイル名の記入
        String txtFileName;

        while((txtFileName = br.readLine()) != null) {

        	String Index;

        	int month = 0;

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

        String[] filename = txtFileName.split("\\_");



     	File file1 = new File(filename[0]  + 	"_" + filename[1] + "01.txt");
     	PrintWriter pw1 = new PrintWriter(new BufferedWriter(new FileWriter(file1)));
     	File file2 = new File(filename[0]  + 	"_" + filename[1] + "02.txt");
     	PrintWriter pw2 = new PrintWriter(new BufferedWriter(new FileWriter(file2)));
     	File file3 = new File(filename[0]  + 	"_" + filename[1] + "03.txt");
     	PrintWriter pw3 = new PrintWriter(new BufferedWriter(new FileWriter(file3)));
     	File file4 = new File(filename[0]   + 	"_" + filename[1] + "04.txt");
     	PrintWriter pw4 = new PrintWriter(new BufferedWriter(new FileWriter(file4)));
     	File file5 = new File(filename[0]   + 	"_" + filename[1] + "05.txt");
     	PrintWriter pw5 = new PrintWriter(new BufferedWriter(new FileWriter(file5)));
     	File file6 = new File(filename[0]   + 	"_" + filename[1] + "06.txt");
     	PrintWriter pw6 = new PrintWriter(new BufferedWriter(new FileWriter(file6)));
     	File file7 = new File(filename[0]  + 	"_" + filename[1] + "07.txt");
     	PrintWriter pw7 = new PrintWriter(new BufferedWriter(new FileWriter(file7)));
     	File file8 = new File(filename[0]   + 	"_" + filename[1] + "08.txt");
     	PrintWriter pw8 = new PrintWriter(new BufferedWriter(new FileWriter(file8)));
     	File file9 = new File(filename[0]   + 	"_" + filename[1] + "09.txt");
     	PrintWriter pw9 = new PrintWriter(new BufferedWriter(new FileWriter(file9)));
     	File file10 = new File(filename[0]   + 	"_" + filename[1] + "10.txt");
     	PrintWriter pw10 = new PrintWriter(new BufferedWriter(new FileWriter(file10)));
     	File file11 = new File(filename[0]   + 	"_" + filename[1] + "11.txt");
     	PrintWriter pw11 = new PrintWriter(new BufferedWriter(new FileWriter(file11)));
     	File file12 = new File(filename[0]   + 	"_" + filename[1] + "12.txt");
     	PrintWriter pw12 = new PrintWriter(new BufferedWriter(new FileWriter(file12)));



        while ((line = brtxt.readLine()) != null) {

        	Index = line;


        	length_konma = Index.length();

    		for(i1=0;i1<length_konma;i1++){
                String a = Index.substring(i1,i1+1);
                if(count_if == 2 && !(a.equals(",")) && count1 == 0){
                	i2 = i1;
                	count1++;
                }
                if(count_if == 3 && count1 != 0 && count2 == 0){
                	i3 = i1-1;
                	count2++;
                	String d1 = Index.substring(i2+4,i3-2);
                	month = Integer.parseInt(d1);//月の抽出
                }
                if(a.equals(",")){
                    count_if++;
                }
            }
    		count_if = 0;
    		count1 = 0;
    		count2 = 0;


    		i2 = 0;
            i3 = 0;





        	if(month == 1){
        		pw1.println(Index);
        	}
        	else if(month == 2){
        		pw2.println(Index);
        	}
        	else if(month == 3){
        		pw3.println(Index);
        	}
        	else if(month == 4){
        		pw4.println(Index);
        	}
        	else if(month == 5){
        		pw5.println(Index);
        	}
        	else if(month == 6){
        		pw6.println(Index);
        	}
        	else if(month == 7){
        		pw7.println(Index);
        	}
        	else if(month == 8){
        		pw8.println(Index);
        	}
        	else if(month == 9){
        		pw9.println(Index);
        	}
        	else if(month == 10){
        		pw10.println(Index);
        	}
        	else if(month == 11){
        		pw11.println(Index);
        	}
        	else if(month == 12){
        		pw12.println(Index);
        	}

        	else{
        		System.out.println("error");
        	}



        }


        brtxt.close();
        fr.close();
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

        }
    }
}


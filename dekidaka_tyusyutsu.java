import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
//読み込み・書き込みファイルのディレクトリーを変更できるかどうか確認するテストプログラム
public class dekidaka_tyusyutsu{

    public static void main(String[] args) throws IOException{

        BufferedReader br = new BufferedReader(new FileReader("filelist.txt"));//読み取りたいファイル名の記入
        String txtFileName;

        String Index;
        double time1 = 0.0;

        int count = 0;
        int count_micro = 0;

        while((txtFileName = br.readLine()) != null) {
        	//System.out.println(txtFileName);

        	int length_konma = 0;

        	int count_space = 0;


        	int count_1 = 0;
        	int count_2 = 0;
        	int count_3 = 0;
        	int count_4 = 0;
        	int count_5 = 0;
        	int count_6 = 0;
        	int count_7 = 0;
        	int count_8 = 0;
        	int count_9 = 0;
        	int count_10 = 0;
        	int count_11 = 0;
        	int count_12 = 0;
        	int count_13 = 0;
        	int count_14 = 0;
        	int count_15 = 0;
        	int count_16 = 0;
        	int count_17 = 0;
        	int count_18 = 0;
        	int count_19 = 0;
        	int count_20 = 0;
        	int count_21 = 0;


        	int count_over = 0;





        	FileReader fr = new FileReader(txtFileName);
            BufferedReader brtxt = new BufferedReader(fr);
            String line ="";

            String[] filename = txtFileName.split("\\.");

            /*File file1 = new File(filename[0]  + 	"_01.txt");//個別株の場合の場合（ここから）
         	PrintWriter pw1 = new PrintWriter(new BufferedWriter(new FileWriter(file1)));
         	File file2 = new File(filename[0]  + 	"_02.txt");
         	PrintWriter pw2 = new PrintWriter(new BufferedWriter(new FileWriter(file2)));
         	File file3 = new File(filename[0]  + 	"_03.txt");
         	PrintWriter pw3 = new PrintWriter(new BufferedWriter(new FileWriter(file3)));
         	File file4 = new File(filename[0]  + 	"_04.txt");
         	PrintWriter pw4 = new PrintWriter(new BufferedWriter(new FileWriter(file4)));
         	File file5 = new File(filename[0]  + 	"_05.txt");
         	PrintWriter pw5 = new PrintWriter(new BufferedWriter(new FileWriter(file5)));
         	File file6 = new File(filename[0]  + 	"_06.txt");
         	PrintWriter pw6 = new PrintWriter(new BufferedWriter(new FileWriter(file6)));
         	File file7 = new File(filename[0]  + 	"_07.txt");
         	PrintWriter pw7 = new PrintWriter(new BufferedWriter(new FileWriter(file7)));
         	File file8 = new File(filename[0]  + 	"_08.txt");
         	PrintWriter pw8 = new PrintWriter(new BufferedWriter(new FileWriter(file8)));
         	File file9 = new File(filename[0]  + 	"_09.txt");
         	PrintWriter pw9 = new PrintWriter(new BufferedWriter(new FileWriter(file9)));
         	File file10 = new File(filename[0]  + 	"_10.txt");
         	PrintWriter pw10 = new PrintWriter(new BufferedWriter(new FileWriter(file10)));
         	File file11 = new File(filename[0]  + 	"_11.txt");
         	PrintWriter pw11 = new PrintWriter(new BufferedWriter(new FileWriter(file11)));
         	File file12 = new File(filename[0]  + 	"_12.txt");
         	PrintWriter pw12 = new PrintWriter(new BufferedWriter(new FileWriter(file12)));
         	File file13 = new File(filename[0]  + 	"_13.txt");
         	PrintWriter pw13 = new PrintWriter(new BufferedWriter(new FileWriter(file13)));
         	File file14 = new File(filename[0]  + 	"_14.txt");
         	PrintWriter pw14 = new PrintWriter(new BufferedWriter(new FileWriter(file14)));
         	File file15 = new File(filename[0]  + 	"_15.txt");
         	PrintWriter pw15 = new PrintWriter(new BufferedWriter(new FileWriter(file15)));
         	File file16 = new File(filename[0]  + 	"_16.txt");
         	PrintWriter pw16 = new PrintWriter(new BufferedWriter(new FileWriter(file16)));
         	File file17 = new File(filename[0]  + 	"_17.txt");
         	PrintWriter pw17 = new PrintWriter(new BufferedWriter(new FileWriter(file17)));
         	File file18 = new File(filename[0]  + 	"_18.txt");
         	PrintWriter pw18 = new PrintWriter(new BufferedWriter(new FileWriter(file18)));
         	File file19 = new File(filename[0]  + 	"_19.txt");
         	PrintWriter pw19 = new PrintWriter(new BufferedWriter(new FileWriter(file19)));
         	File file20 = new File(filename[0]  + 	"_20.txt");
         	PrintWriter pw20 = new PrintWriter(new BufferedWriter(new FileWriter(file20)));
         	File file21 = new File(filename[0]  + 	"_21.txt");
         	PrintWriter pw21 = new PrintWriter(new BufferedWriter(new FileWriter(file21)));*/

            while ((line = brtxt.readLine()) != null) {

            	Index = line;



            	length_konma = Index.length();//個別株(ここから)

            	/*String d1 = Index.substring(9,11);//データに時間があるとき
            	double hour1 = Double.parseDouble(d1);
            	String d2 = Index.substring(12,14);
            	double minute1 = Double.parseDouble(d2);
            	String d3 = Index.substring(15,24);
            	double second1 =  Double.parseDouble(d3);
            	double n_all_time = hour1*60*60 + minute1*60 + second1;*///データに時間があるとき

            	//if(n_all_time > 31500 && 54000 > n_all_time){//データに時間があるとき
            		for(int i1=0;i1<length_konma;i1++){//i1=24にしている理由は、その間に時刻があるため
                        String a = Index.substring(i1,i1+1);
                        if(a.equals(" ")){
                        	count_space++;
                        }
                    }

            		if(count_space == 1){
            			count_1++;
            			//pw1.println(Index);
            		}
            		else if(count_space == 2){
            			count_2++;
            			//pw2.println(Index);
            		}
            		else if(count_space == 3){
            			count_3++;
            			//pw3.println(Index);
            		}
            		else if(count_space == 4){
            			count_4++;
            			//pw4.println(Index);
            		}
            		else if(count_space == 5){
            			count_5++;
            			//pw5.println(Index);
            		}
            		else if(count_space == 6){
            			count_6++;
            			//pw6.println(Index);
            		}
            		else if(count_space == 7){
            			count_7++;
            			//pw7.println(Index);
            		}
            		else if(count_space == 8){
            			count_8++;
            			//pw8.println(Index);
            		}
            		else if(count_space == 9){
            			count_9++;
            			//pw9.println(Index);
            		}
            		else if(count_space == 10){
            			count_10++;
            			//pw10.println(Index);
            		}
            		else if(count_space == 11){
            			count_11++;
            			//pw11.println(Index);
            		}
            		else if(count_space == 12){
            			count_12++;
            			//pw12.println(Index);
            		}
            		else if(count_space == 13){
            			count_13++;
            			//pw13.println(Index);
            		}
            		else if(count_space == 14){
            			count_14++;
            			//pw14.println(Index);
            		}
            		else if(count_space == 15){
            			count_15++;
            			//pw15.println(Index);
            		}
            		else if(count_space == 16){
            			count_16++;
            			//pw16.println(Index);
            		}
            		else if(count_space == 17){
            			count_17++;
            			//pw17.println(Index);
            		}
            		else if(count_space == 18){
            			count_18++;
            			//pw18.println(Index);
            		}
            		else if(count_space == 19){
            			count_19++;
            			//pw19.println(Index);
            		}
            		else if(count_space == 20){
            			count_20++;
            			//pw20.println(Index);
            		}
            		else if(count_space >= 21){
            			count_21++;
            			//pw21.println(Index);
            			count_over += count_space;
            		}
            		else{
            			System.out.println(Index);
            		}


            		count_space = 0;
            	//}//データに時間があるとき





            	}
            System.out.println(txtFileName);
            System.out.println("親注文1の数 " + count_1);
            System.out.println("親注文2の数 " + count_2);
            System.out.println("親注文3の数 " + count_3);
            System.out.println("親注文4の数 " + count_4);
            System.out.println("親注文5の数 " + count_5);
            System.out.println("親注文6の数 " + count_6);
            System.out.println("親注文7の数 " + count_7);
            System.out.println("親注文8の数 " + count_8);
            System.out.println("親注文9の数 " + count_9);
            System.out.println("親注文10の数 " + count_10);
            System.out.println("親注文11の数 " + count_11);
            System.out.println("親注文12の数 " + count_12);
            System.out.println("親注文13の数 " + count_13);
            System.out.println("親注文14の数 " + count_14);
            System.out.println("親注文15の数 " + count_15);
            System.out.println("親注文16の数 " + count_16);
            System.out.println("親注文17の数 " + count_17);
            System.out.println("親注文18の数 " + count_18);
            System.out.println("親注文19の数 " + count_19);
            System.out.println("親注文20の数 " + count_20);
            System.out.println("親注文21以上の数 " + count_21);
            System.out.println("親注文21以上の注文数 " + count_over);
            System.out.println("\n\n");






            brtxt.close();
            fr.close();
            /*pw1.close();
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
            pw21.close();*/

        	// txtファイル名を一行ずつロードする
        }
        br.close();

    }



}



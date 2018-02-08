import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class HTICDT_first_depth{
	//HTICDTデータから最初の板の枚数を抽出するプログラム
	public static void main(String[] args) throws IOException{


		BufferedReader br = new BufferedReader(new FileReader("filelist.txt"));//読み取りたいファイル名の記入
		String txtFileName;

		while((txtFileName = br.readLine()) != null) {

			boolean yoritsuki = false;
			String first_depth = "";
			int count_yoritsuki = 0;

			FileReader fr = new FileReader(txtFileName);
			BufferedReader brtxt = new BufferedReader(fr);
			String line ="";

			String[] filename = txtFileName.split("¥.");
			File file = new File(filename[0] + "_first_depth.txt");//結合データ
			PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(file)));


			while ((line = brtxt.readLine()) != null) {

				if(yoritsuki == true){
					if(count_yoritsuki == 0){           			
						if(Integer.parseInt(line.substring(30,32)) < 10){
							first_depth = Integer.toString(Integer.parseInt(line.substring(4,12))) + ",morning," + Integer.toString(Integer.parseInt(line.substring(56,66))) + ",";
						}
						else{
							first_depth = Integer.toString(Integer.parseInt(line.substring(4,12))) + ",afternoon," + Integer.toString(Integer.parseInt(line.substring(56,66))) + ",";
						}

						count_yoritsuki++;
						//System.out.println(line + ";;" + count_yoritsuki);
					}
					else if(count_yoritsuki == 1){
						first_depth += Integer.toString(Integer.parseInt(line.substring(56,66)));

						pw.println(first_depth);
						first_depth = "";
						count_yoritsuki = 0;
						yoritsuki = false;
						//System.out.println(line);
					}

				}



				if(line.substring(49,52).equals("  1") && Integer.parseInt(line.substring(30,32)) <= 15){
					System.out.println(line);
					yoritsuki = true;
				}
			}

			pw.close();


			brtxt.close();
			fr.close();

		}
		br.close();

	}
}

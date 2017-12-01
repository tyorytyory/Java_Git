import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class HTICDT_Circuit{
//HTICDTデータからサーキットブレイカーがおきたかどうか調べるプログラム
    public static void main(String[] args) throws IOException{


        BufferedReader br = new BufferedReader(new FileReader("filelist.txt"));//読み取りたいファイル名の記入
        String txtFileName;

        while((txtFileName = br.readLine()) != null) {






        	FileReader fr = new FileReader(txtFileName);
            BufferedReader brtxt = new BufferedReader(fr);
            String line ="";

            System_print System_print1 = new System_print();





            while ((line = brtxt.readLine()) != null) {

            	System_print1.Circuit(line);
            	//System_print1.Print(line);

            	/*if(Integer.parseInt(line.substring(56,66))==0){
            		System.out.println(line);
            	}*/

            }


            brtxt.close();
            fr.close();

        }

    }
}

class System_print//class
{
	void Circuit(String a)
	{
		String Circuit = a.substring(52,55);

		if(Circuit.equals("  4") || Circuit.equals(" 12") || Circuit.equals("  8") || Circuit.equals(" 18") || Circuit.equals(" 19") ||
				Circuit.equals(" 20") || Circuit.equals(" 21") || Circuit.equals(" 23") || Circuit.equals(" 64")){
    		System.out.println(a);
    	}
	}

	void Print(String a)
	{
    	System.out.println(a);
	}

}
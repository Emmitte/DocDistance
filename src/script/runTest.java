package script;

import java.io.IOException;
import java.util.List;

import algorithm.Model;

import util.Tool;
import word2vec.main.java.com.ansj.vec.Word2VEC;

public class runTest {

	/**
	 * 程序运行入口
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		String dir,inputPath1,inputPath2,outputPath,word2vecModel,str1,str2;
		long start,end,dur;
		start = System.currentTimeMillis();
		dir = "data/test/";
		inputPath1 = dir + "doc3.txt";
		inputPath2 = dir + "doc3.txt";
		dir = "data/result/";
		outputPath = dir + "out3_3.txt";
		word2vecModel = "model/vectors.bin";
		
		FileHandler fh = new FileHandler();
		List<String> docList1 = fh.putFileToList(inputPath1);
		List<String> docList2 = fh.putFileToList(inputPath2);
		Tool.initWriter1(outputPath);
		
		Word2VEC w = new Word2VEC() ;
        w.loadGoogleModel(word2vecModel) ;
        
        Model model = new Model();
        model.run(docList1, docList2 ,w);
        
        end = System.currentTimeMillis();
        dur = end - start;
        System.out.println("dur time = " + (1.0 * dur / 1000) + " s");
        System.out.println("dur time = " + (1.0 * dur / (1000 * 60)) + " min");
        
	}

}

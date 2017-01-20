package algorithm;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import script.FileHandler;
import script.StringHandler;
import util.Conf;
import util.Tool;
import word2vec.main.java.com.ansj.vec.Word2VEC;

public class Model {

	/**
	 * input:docList1,docList2 主方法入口及控制器
	 */
	public void run(List<String> docList1, List<String> docList2, Word2VEC w) {
		int i, j, n1, n2;
		double[] similarArr;
		int[] locArr;
		double max, temp;
		int loc;
		n1 = docList1.size();
		n2 = docList2.size();
		similarArr = new double[n1];
		locArr = new int[n1];
		for (i = 0; i < n1; i++) {
			max = 0.0;
			temp = 0.0;
			loc = 0;
			for (j = 0; j < n2; j++) {
				try {
					temp = getSimilar(docList1.get(i), docList2.get(j), w);
				} catch (IOException e) {
					temp = 0.0;
					e.printStackTrace();
				}
				if (temp > max) {
					max = temp;
					loc = j;
				}
			}
			similarArr[i] = max;
			locArr[i] = loc;
		}
		Tool.output(docList1, docList2, locArr, similarArr);
	}

	/**
	 * input:str1,str2 计算2个字符串之间的相似度
	 */
	public double getSimilar(String str1, String str2, Word2VEC w) throws IOException {

		double ret = 0.0;
		// 创建向量空间模型，使用map实现，主键为词项，值为长度为2的数组，存放着对应词项在字符串中的出现次数
		Map<String, int[]> vectorSpace = new HashMap<String, int[]>();
		int[] itemCountArray = null;// 为了避免频繁产生局部变量，所以将itemCountArray声明在此
		Iterator iter;
		double vector1Modulo = 0.00;// 向量1的模
		double vector2Modulo = 0.00;// 向量2的模
		double vectorProduct = 0.00; // 向量积
		List<String> list1,list1_temp,list2,list2_temp,temp1,temp2;
		
		StringHandler sh = new StringHandler();
		
		list1_temp = sh.stringToArray(str1);
		list2_temp = sh.stringToArray(str2);
		
		/*
		//使用word2vec扩充语义
		temp1 = sh.stringToArray(str1);
		temp2 = sh.stringToArray(str2);
		list1 = sh.extendWord(w, temp1);
		list2 = sh.extendWord(w, temp2);
		*/
		list1 = sh.deleteStopWords(list1_temp, Conf.stopWordsPath);
		list2 = sh.deleteStopWords(list2_temp, Conf.stopWordsPath);
		
		int i,n;
		n = list1.size();
		for (i = 0; i < n; ++i) {
			if (vectorSpace.containsKey(list1.get(i)))
				++(vectorSpace.get(list1.get(i))[0]);
			else {
				itemCountArray = new int[2];
				itemCountArray[0] = 1;
				itemCountArray[1] = 0;
				vectorSpace.put(list1.get(i), itemCountArray);
			}
		}
		
		// 对str2处理

		n = list2.size();
		for (i = 0; i < n; ++i) {
			if (vectorSpace.containsKey(list2.get(i)))
				++(vectorSpace.get(list2.get(i))[1]);
			else {
				itemCountArray = new int[2];
				itemCountArray[0] = 0;
				itemCountArray[1] = 1;
				vectorSpace.put(list2.get(i), itemCountArray);
			}
		}

		// 计算相似度
		iter = vectorSpace.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			itemCountArray = (int[]) entry.getValue();
			vector1Modulo += itemCountArray[0] * itemCountArray[0];
			vector2Modulo += itemCountArray[1] * itemCountArray[1];
			vectorProduct += itemCountArray[0] * itemCountArray[1];
		}
		vector1Modulo = Math.sqrt(vector1Modulo);
		vector2Modulo = Math.sqrt(vector2Modulo);
		// 返回相似度
		ret  = (vectorProduct / (vector1Modulo * vector2Modulo));
		return ret;

	}

}

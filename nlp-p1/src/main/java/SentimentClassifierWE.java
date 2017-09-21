import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Scanner;

/**
* Soorya Krishna Pillai  ----- sp835
* Elizabeth Perk         ----- ejp82
*/


class SentimentClassifierWE {

	String posFile,negFile,excerptFile,outputPath;
	int layerSize;
	public SentimentClassifierWE(String posFile, String negFile, String excerptFile, String outputPath)
	{
		this.posFile = posFile;
		this.negFile = negFile;
		this.excerptFile = excerptFile;
		this.outputPath = outputPath;
		layerSize = 100;
		run();
	}
	
	public void run(){
		System.out.println("Computing positive Vector...");
		Word2VecExample WE = new Word2VecExample(posFile);
		double[] posVec = WE.run("train");
		System.out.println("Computing negative Vector...");
		WE = new Word2VecExample(negFile);
		double[] negVec = WE.run("train");
		System.out.println("Computing excerpt Vector...");
		WE = new Word2VecExample(excerptFile);
		double[] expVec = WE.run("test");
		System.out.println("Computing Cosine Similarities...");
		try{
			FileWriter outputWriter = new FileWriter(outputPath+File.separator+"result.csv");
		    BufferedWriter bw = new BufferedWriter(outputWriter);
		    PrintWriter out = new PrintWriter(bw);
			out.print("Id,Prediction\n");
			out.close();
			bw.close();
			outputWriter.close();
			
			outputWriter = new FileWriter(outputPath+File.separator+"result.csv",true);
		    bw = new BufferedWriter(outputWriter);
		    out = new PrintWriter(bw);
		    
		    ArrayList<double[]> vector =new ArrayList<>();
		    
			int lineNum = 0;
			expVec = new double[layerSize];
			LinkedHashMap<String,double[]> reviewVecMap;
			// iterate over all review vector
			for(int i = 0; i<WE.reviewVectors.size(); i++)
			{
				reviewVecMap = WE.reviewVectors.get(i);

				for(int w = 0; w < layerSize; w++)
				{
					int nWords = reviewVecMap.size();
					// for each review vector sum up word vectors 
					for(String word: reviewVecMap.keySet())
					{
						expVec[w] += reviewVecMap.get(word)[w];
					}
					// calculates average of the embeddings of all words inside the review 
					expVec[w] = (nWords == 0)?0:(expVec[w]/nWords);
				}
				
				double negSim = cosineSimilarity(negVec, expVec);
				double posSim = cosineSimilarity(posVec, expVec);
				
				// if similarity to positive vector is more then review is positive
				if(Math.abs(posSim) > Math.abs(negSim))
				{
					out.print((i+1)+","+0+"\n");
				}
				else
				{
					out.print((i+1)+","+1+"\n");
				}
			}
			out.close();
			bw.close();
			outputWriter.close();
			System.out.println("Output file result.csv saved at ..."+outputPath);
		}
		catch(Exception E)
		{
			System.out.println(E.getMessage() +"\n"+E.getStackTrace());
		}
	}
	
	public double cosineSimilarity(double[] trainV, double[] testV) {
	    double dotP = 0.0;
	    double trainVal = 0.0;
	    double testVal = 0.0;
	    for (int i = 0; i < trainV.length; i++) {
	    	// dot product
	        dotP += trainV[i] * testV[i];
	        // towards product of magnitudes/eucledian distance
	        trainVal += Math.pow(trainV[i], 2);
	        testVal += Math.pow(testV[i], 2);
	    }   
	    if(trainVal < 0.0 || testVal < 0.0)
	    	return 0.0;
	    return dotP/ (Math.sqrt(trainVal) * Math.sqrt(testVal));
	}
	
	public static void main(String[] args){
		if(args.length != 4){
			System.out.println("Please provide absolute path");
			System.out.println("Usage: java SentimentClassifier [positive input file] [negative input file] [test file] [output folder path]");
			System.exit(1);
		}
		SentimentClassifierWE SC = new SentimentClassifierWE(args[0], args[1], args[2],args[3]);
	}
	
}

import org.deeplearning4j.models.word2vec.Word2Vec;
import org.deeplearning4j.text.sentenceiterator.SentenceIterator;
import org.deeplearning4j.text.sentenceiterator.SentencePreProcessor;
import org.deeplearning4j.text.sentenceiterator.LineSentenceIterator;
import org.deeplearning4j.text.tokenization.tokenizer.preprocessor.CommonPreprocessor;
import org.deeplearning4j.text.tokenization.tokenizerfactory.DefaultTokenizerFactory;
import org.deeplearning4j.text.tokenization.tokenizerfactory.TokenizerFactory;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Scanner;

/**
 * Functions run() and getCorpusLevelVector() developed by 
 * Soorya Krishna Pillai  ----- sp835
 * Elizabeth Perk         ----- ejp82
 * Uses api from Word2Vec class created by agibsonccc on 10/9/14.
 * https://deeplearning4j.org/word2vec.html
 */

public class Word2VecExample 
{
	String fileName;
	int layerSize;
	public ArrayList<LinkedHashMap<String,double[]>> reviewVectors = new ArrayList<>();
	
	//constructor
	public Word2VecExample(String file)
	{
		this.fileName = file;
		layerSize  = 100;
	}
	
	public double[] run(String type)
	{
        SentenceIterator iter = new LineSentenceIterator(new File(fileName));
        iter.setPreProcessor(new SentencePreProcessor() {
            @Override
            public String preProcess(String sentence) {
                return sentence.toLowerCase();
            }
        });

        // Split on white spaces in the line to get words
        TokenizerFactory t = new DefaultTokenizerFactory();
        t.setTokenPreProcessor(new CommonPreprocessor(){
            @Override
            public String preProcess(String sentence) {
            	// sentence=sentence.replaceAll("[^a-zA-Z ]", "");
                return sentence.toLowerCase();
            }
        });

        // configure Word2vec neural net
        Word2Vec vec = new Word2Vec.Builder()
                .minWordFrequency(1)
                .iterations(8)
                .layerSize(layerSize)
                .seed(37)
                .windowSize(5)
                .iterate(iter)
                .tokenizerFactory(t)
                .build();
        // to begin training on configured net 
        vec.fit();
        
        // convert vec into easily iterable dictionary of review vectors
        getReviewVectors(vec);
        
        double[] vector = null;
        
        if(type.equals("train")){
        	System.out.println("Creating Single Prototype Vector...");
			vector = getCorpusLevelVector();
		}
		return vector;
	}
	
	// returns dictionary that maps words to their corresponding vectors for every review
	public void getReviewVectors(Word2Vec vec){
		File F;
        Scanner S,W;
        String word="";
        LinkedHashMap<String,double[]> map = new LinkedHashMap<>();
        double[] doubArr = new double[layerSize];
		try {  
			F = new File(fileName);
			S = new Scanner(F);
	        while(S.hasNextLine())
	        {
	        	W = new Scanner(S.nextLine());
	        	while(W.hasNext())
	        	{
	        		word = W.next();
	        		if(vec.hasWord(word))
	        		{
		        		doubArr = vec.getWordVector(word);
		        		map.put(word, doubArr);
	        		}
	        	}
	        	reviewVectors.add(map);
	        	map = new LinkedHashMap<>();
	        }
		}
		catch (IOException e) 
		{
		    System.err.println(e);
		}
	}	
	
	// returns single prototype vector for entire corpus
	public double[] getCorpusLevelVector()
	{
		
		HashMap<String,double[]> eachReviewList = new HashMap<>();
		ArrayList<double[]> eachReviewResultList = new ArrayList<>();
		
		double[] eachReviewVec = new double[layerSize];
		double[] totalReviewVec = new double[layerSize];
		
		int nReviews = reviewVectors.size();		
		int nWords = 0;

		// iterate over each line/review
		for(int review = 0; review < nReviews; review++)
		{
			eachReviewList = reviewVectors.get(review);
			nWords = eachReviewList.size();
			// iterate over every word vector for given review
			for(int i = 0; i < layerSize; i++)
			{
				// sum up word vectors
				for(String word: eachReviewList.keySet())
				{
					eachReviewVec[i] += eachReviewList.get(word)[i];
					if(Double.isNaN(eachReviewVec[i])){
						System.exit(1);
					}
				}
				// calculates average of the embeddings of all words inside the review 
				eachReviewVec[i] = (nWords == 0?0.0:eachReviewVec[i]/nWords);
				if(Double.isNaN(eachReviewVec[i])){
					System.exit(1);
				}
			}
			eachReviewResultList.add(eachReviewVec);
			eachReviewVec = new double[layerSize];
		}
		
		// calculates average of the encoded reviews inside the corpus/file
		for(int i = 0; i < layerSize; i++)
		{
			for(int reviews = 0; reviews < nReviews; reviews++)
			{
				totalReviewVec[i] += eachReviewResultList.get(reviews)[i];
			}
			totalReviewVec[i] = totalReviewVec[i]/(double)nReviews;
		}
		return totalReviewVec;
	}
	
}
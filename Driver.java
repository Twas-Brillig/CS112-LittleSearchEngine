package lse;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

public class Driver {
	
	public static void main(String[] args) throws FileNotFoundException {
	//create occurrence objects with frequencies in this order
	//12, 8, 7, 5, 3, 2, *6*
//	LittleSearchEngine lse = new LittleSearchEngine();
	ArrayList<Occurrence> lastOccurrenceTest = new ArrayList<Occurrence>();
//	
//
//
//
//	lastOccurrenceTest.add(new Occurrence("red", 21));
//	lastOccurrenceTest.add(new Occurrence("blue", 19));
//	lastOccurrenceTest.add(new Occurrence("green", 15));
//	lastOccurrenceTest.add(new Occurrence("purple", 6));
//	lastOccurrenceTest.add(new Occurrence("orange", 4));
//	lastOccurrenceTest.add(new Occurrence("green", 3));
//	lastOccurrenceTest.add(new Occurrence("yellow", 2));
//	lastOccurrenceTest.add(new Occurrence("pink", 8));
//	lastOccurrenceTest.add(new Occurrence("pink", 254));
//
//	lse.insertLastOccurrence(lastOccurrenceTest);
//	

	
	
	
	
		//LittleSearchEngine lse = new LittleSearchEngine();
		//lse.makeIndex("docs.txt","noisewords.txt");
		//lse.mergeKeywords(lse.loadKeywordsFromDocument("WowCh1.txt"));
		
//		for (Map.Entry<String, ArrayList<Occurrence>> entry : lse.keywordsIndex.entrySet()) {
//		    String key = entry.getKey();
//		    Object value = entry.getValue();
//		    System.out.println(key);
//		    for(Occurrence s: entry.getValue()) {
//		    	System.out.println(s.frequency);
//		    }
//		    
//		    
//		}
		
		
		
		//remember , telescope
		LittleSearchEngine lse = new LittleSearchEngine();
		lse.makeIndex("docs.txt", "noisewords.txt");
		
		lse.top5search("people", "children");
//		HashSet<String> noise = new HashSet<String>();
//		Scanner noisey = new Scanner(new File("noiseWords.txt"));
//		while(noisey.hasNext()) {
//			lse.noiseWords.add(noisey.next());
//		}
		
//		lastOccurrenceTest.add(new Occurrence("red", 12));
//		lastOccurrenceTest.add(new Occurrence("blue", 8));
//		lastOccurrenceTest.add(new Occurrence("green", 7));
//		lastOccurrenceTest.add(new Occurrence("purple", 5));
//		lastOccurrenceTest.add(new Occurrence("orange", 3));
//		lastOccurrenceTest.add(new Occurrence("green", 2));
//		lastOccurrenceTest.add(new Occurrence("yellow", 0));
//		lse.insertLastOccurrence(lastOccurrenceTest);
		
		
//		lse.mergeKeywords(lse.loadKeywordsFromDocument("andone.txt"));
//		lse.mergeKeywords(lse.loadKeywordsFromDocument("alltwo.txt"));
//		lse.top5search("castlevania", "riddick");
//		lse.mergeKeywords(lse.loadKeywordsFromDocument("WowCh1.txt"));
//		lse.mergeKeywords(lse.loadKeywordsFromDocument("crime.txt"));
//		lse.top5search("girl", "");



		
		
		
		
		//happened thought, falling, no
		
		
		
		
//		System.out.println();
//		System.out.println();
		System.out.println();


		
		
		//lse.loadKeywordsFromDocument("AliceCh1.txt");
//		for (Map.Entry<String, Occurrence> entry : lse.loadKeywordsFromDocument("AliceCh1.txt").entrySet()) {
//		    String key = entry.getKey();
//		    Object value = entry.getValue();
//		    
//		    System.out.print(key);
//		    System.out.println("<--- occurred this many times: " + value);
//		    
//		    
//		}
		
//		ArrayList<String> myStringList = lse.top5search("alice", "jubilant");
//		
//		for(String stringy: myStringList) {
//			System.out.println("Found in this text file: " + stringy);
//		}
//		
	   
		

	
		
		

	}

}

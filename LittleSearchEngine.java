	package lse;

import java.io.*;
import java.util.*;

/**
 * This class builds an index of keywords. Each keyword maps to a set of pages in
 * which it occurs, with frequency of occurrence in each page.
	 *’Twas brillig, and the slithy toves 
	      Did gyre and gimble in the wabe: 
	All mimsy were the borogoves, 
	      And the mome raths outgrabe. 
	
	“Beware the Jabberwock, my son! 
	      The jaws that bite, the claws that catch! 
	Beware the Jubjub bird, and shun 
	      The frumious Bandersnatch!” 
	
	He took his vorpal sword in hand; 
	      Long time the manxome foe he sought— 
	So rested he by the Tumtum tree 
	      And stood awhile in thought. 
	
	And, as in uffish thought he stood, 
	      The Jabberwock, with eyes of flame, 
	Came whiffling through the tulgey wood, 
	      And burbled as it came! 
	
	One, two! One, two! And through and through 
	      The vorpal blade went snicker-snack! 
	He left it dead, and with its head 
	      He went galumphing back. 
	
	“And hast thou slain the Jabberwock? 
	      Come to my arms, my beamish boy! 
	O frabjous day! Callooh! Callay!” 
	      He chortled in his joy. 
	
	’Twas brillig, and the slithy toves 
	      Did gyre and gimble in the wabe: 
	All mimsy were the borogoves, 
	      And the mome raths outgrabe.
 *
 *
 *
 *   Nelson Vargas
 *   RUID: 184-00-3905
 *   Netid: nvv11
 *   Date: 4/16/19
 *   FINAL VERSION TESTED
 *   
 *   
 */
public class LittleSearchEngine {
	
	/**
	 * This is a hash table of all keywords. The key is the actual keyword, and the associated value is
	 * an array list of all occurrences of the keyword in documents. The array list is maintained in 
	 * DESCENDING order of frequencies.
	 */
	HashMap<String,ArrayList<Occurrence>> keywordsIndex;
	
	/**
	 * The hash set of all noise words.
	 */
	HashSet<String> noiseWords;
	
	/**
	 * Creates the keyWordsIndex and noiseWords hash tables.
	 */
	public LittleSearchEngine() {
		keywordsIndex = new HashMap<String,ArrayList<Occurrence>>(1000,2.0f);
		noiseWords = new HashSet<String>(100,2.0f);
	}
	
	
	/**
	 * Scans a document, and loads all keywords found into a hash table of keyword occurrences
	 * in the document. Uses the getKeyWord method to separate keywords from other words.
	 * 
	 * @param docFile Name of the document file to be scanned and loaded
	 * @return Hash table of keywords in the given document, each associated with an Occurrence object
	 * @throws FileNotFoundException If the document file is not found on disk
	 */
	public HashMap<String,Occurrence> loadKeywordsFromDocument(String docFile) 
	throws FileNotFoundException {
		
		
		//loads up either keywords or ignores a null when given a text file
		Scanner docScanned = new Scanner(new File(docFile));
		HashMap<String, Occurrence> keywordsDoc = new  HashMap<String, Occurrence>();
		
		while(docScanned.hasNext()) {
			
			String nextWord = docScanned.next();
			String testedKeyWord = getKeyword(nextWord);
			
			if (testedKeyWord != null) {
				
				if(keywordsDoc.containsKey(testedKeyWord)) {
					 
					keywordsDoc.get(testedKeyWord).frequency++;
					
				} else {
					
					keywordsDoc.put(testedKeyWord, new Occurrence(docFile, 1));
					
				}
			} 
		}
		docScanned.close();
		return keywordsDoc;
	}

	
	/**
	 * Merges the keywords for a single document into the master keywordsIndex
	 * hash table. For each keyword, its Occurrence in the current document
	 * must be inserted in the correct place (according to descending order of
	 * frequency) in the same keyword's Occurrence list in the master hash table. 
	 * This is done by calling the insertLastOccurrence method.
	 * 
	 * @param kws Keywords hash table for a document
	 */
	
	public void mergeKeywords(HashMap<String,Occurrence> kws) {
		
		// must implement the insertLastOccurence method
		for(Map.Entry<String,Occurrence> entry: kws.entrySet()) {
			String entryKey = entry.getKey();
			Occurrence entryValue = entry.getValue();
			
			if(!keywordsIndex.containsKey(entryKey)) {
				
				ArrayList<Occurrence> firstInstance = new ArrayList<Occurrence>();
				firstInstance.add(entryValue);
				keywordsIndex.put(entryKey, firstInstance);
				
				
			} else {
				//more than one occurrence of an object already established
				
				keywordsIndex.get(entryKey).add(entryValue);
				insertLastOccurrence(keywordsIndex.get(entryKey));

			}			
		}
	}
	
	
	
	/**
	 * Given a word, returns it as a keyword if it passes the keyword test,
	 * otherwise returns null. A keyword is any word that, after being stripped of any
	 * trailing punctuation(s), consists only of alphabetic letters, and is not
	 * a noise word. All words are treated in a case-INsensitive manner.
	 * 
	 * Punctuation characters are the following: '.', ',', '?', ':', ';' and '!'
	 * NO OTHER CHARACTER SHOULD COUNT AS PUNCTUATION
	 * 
	 * If a word has multiple trailing punctuation characters, they must all be stripped
	 * So "word!!" will become "word", and "word?!?!" will also become "word"
	 * 
	 * See assignment description for examples
	 * 
	 * @param word Candidate word
	 * @return Keyword (word without trailing punctuation, LOWER CASE)
	 */
	public String getKeyword(String word) {
	
		//Checks if all the punctuations are legal and filters out words w/ special characters
		
		String punctuations = ".,?:;!";
		String excluded = "-'@#$%^&*(){}[]~`";
		String lowerCaseCheck = word.toLowerCase();
		
		boolean punctuationsAreLegal  = true;
		char[] wordAsCharArray = lowerCaseCheck.toCharArray();
		
		for(int i = 0; i < wordAsCharArray.length; i++) {
			
			if(punctuations.contains(Character.toString(wordAsCharArray[i])) &&
			  (i + 1 < wordAsCharArray.length)) {
				
				if(Character.isLetter(wordAsCharArray[i + 1])) {
					punctuationsAreLegal = false;
				}
				
			}
			
			if(excluded.contains(Character.toString(wordAsCharArray[i]))) {
				return null;
			}
			
		}
	
		String edgeCaseCheck = lowerCaseCheck.replaceAll("\\s*\\p{Punct}+\\s*$", "");
		if(!noiseWords.contains(edgeCaseCheck) && !word.matches(".*\\d.*")
		   && punctuationsAreLegal 
		   && edgeCaseCheck.chars().allMatch(Character::isLetter)) {	
			
			return edgeCaseCheck;
			
		}
		
		return null;
	}

	
	/**
	 * Inserts the last occurrence in the parameter list in the correct position in the
	 * list, based on ordering occurrences on descending frequencies. The elements
	 * 0..n-2 in the list are already in the correct order. Insertion is done by
	 * first finding the correct spot using binary search, then inserting at that spot.
	 * 
	 * @param occs List of Occurrences
	 * @return Sequence of mid point indexes in the input list checked by the binary search process,
	 *         null if the size of the input list is 1. This returned array list is only used to test
	 *         your code - it is not used elsewhere in the program.
	 */
	public ArrayList<Integer> insertLastOccurrence(ArrayList<Occurrence> occs) {

		if(occs.size() == 1 || occs.isEmpty()) {
			return null;
		}

		ArrayList<Integer> integerList = new ArrayList<Integer>();
		int left = 0;
		int right = occs.size() - 2;
		int target = occs.get(occs.size()-1).frequency;
		
		
		while(left <= right) {
			
			int mid = left + ((right-left) / 2);
			integerList.add(mid);
			
			if(occs.get(mid).frequency == target) {
				break;
				
			} else if (target < occs.get(mid).frequency) {
				left = mid + 1;
				
			} else {
				right = mid - 1;
			}
			
		}
		
		int indexToReplace = integerList.get(integerList.size()-1);
		
		if(occs.get(indexToReplace).frequency <  target) {
			
			occs.add(indexToReplace, occs.remove(occs.size()-1));
			
		} else {
			
			occs.add(indexToReplace + 1, occs.remove(occs.size()-1));
			
		}
		
		return integerList;
	}
	
	/**
	 * This method indexes all keywords found in all the input documents. When this
	 * method is done, the keywordsIndex hash table will be filled with all keywords,
	 * each of which is associated with an array list of Occurrence objects, arranged
	 * in decreasing frequencies of occurrence.
	 * 
	 * @param docsFile Name of file that has a list of all the document file names, one name per line
	 * @param noiseWordsFile Name of file that has a list of noise words, one noise word per line
	 * @throws FileNotFoundException If there is a problem locating any of the input files on disk
	 */
	public void makeIndex(String docsFile, String noiseWordsFile) 
	throws FileNotFoundException {
		// load noise words to hash table
		Scanner sc = new Scanner(new File(noiseWordsFile));
		while (sc.hasNext()) {
			String word = sc.next();
			noiseWords.add(word);
		}
		
		// index all keywords
		sc = new Scanner(new File(docsFile));
		while (sc.hasNext()) {
			String docFile = sc.next();
			HashMap<String,Occurrence> kws = loadKeywordsFromDocument(docFile);
			mergeKeywords(kws);
		}
		
		sc.close();
	}
	
	/**
	 * Search result for "kw1 or kw2". A document is in the result set if kw1 or kw2 occurs in that
	 * document. Result set is arranged in descending order of document frequencies. 
	 * 
	 * Note that a matching document will only appear once in the result. 
	 * 
	 * Ties in frequency values are broken in favor of the first keyword. 
	 * That is, if kw1 is in doc1 with frequency f1, and kw2 is in doc2 also with the same 
	 * frequency f1, then doc1 will take precedence over doc2 in the result. 
	 * 
	 * The result set is limited to 5 entries. If there are no matches at all, result is null.
	 * 
	 * See assignment description for examples
	 * 
	 * @param kw1 First keyword
	 * @param kw1 Second keyword
	 * @return List of documents in which either kw1 or kw2 occurs, arranged in descending order of
	 *         frequencies. The result size is limited to 5 documents. If there are no matches, 
	 *         returns null or empty array list.
	 */
	public ArrayList<String> top5search(String kw1, String kw2) {
		/** COMPLETE THIS METHOD **/
		
		
		
		ArrayList<String> docsWithKeywords = new ArrayList<String>();
		ArrayList<Occurrence> kw1Occurrences = new ArrayList<Occurrence>();
		ArrayList<Occurrence> kw2Occurrences = new ArrayList<Occurrence>();
		boolean updateIsNeeded = false;
		
		
		
		kw1 = kw1.toLowerCase();
		kw2 = kw2.toLowerCase();
		

		if(keywordsIndex.containsKey(kw1)) {
			kw1Occurrences = keywordsIndex.get(kw1);
			
			for(Occurrence n: kw1Occurrences) {
				if(docsWithKeywords.size() < 6) {
					docsWithKeywords.add(n.document);
				}	
			}
			
		}
		//Populates solely with kw2Occs if kw1Occs had NIL
		if(keywordsIndex.containsKey(kw2)) {
			kw2Occurrences = keywordsIndex.get(kw2);
			
			if(docsWithKeywords.isEmpty()) {
				for(Occurrence m: kw2Occurrences) {
					
					if(docsWithKeywords.size() < 6) {
						docsWithKeywords.add(m.document);
					}
				}	
				return docsWithKeywords;
			} 
			
			else {
				updateIsNeeded = true;
			}
			
		}
		
		ArrayList<Occurrence> sortedOccurrences = new ArrayList<Occurrence>();
		
		if(updateIsNeeded == true) {
			
			sortedOccurrences.addAll(kw1Occurrences);
			sortedOccurrences.addAll(kw2Occurrences);
			//clear to impose new precedence
			docsWithKeywords.clear();
			
			Occurrence[] occurrenceArray = new Occurrence[sortedOccurrences.size()];
			
			int r = 0;
			for(Occurrence a: sortedOccurrences) {
				occurrenceArray[r] = a;
				r++;
			}
			
			//bubble sort on the occurrence array 
			int arrayLength = occurrenceArray.length; 
	        for (int i = 0; i < arrayLength-1; i++)  {
	        	for (int j = 0; j < arrayLength-i-1; j++) {
	            	if (occurrenceArray[j].frequency < occurrenceArray[j+1].frequency) { 
	                    Occurrence temp = occurrenceArray[j]; 
	                    occurrenceArray[j] = occurrenceArray[j + 1]; 
	                    occurrenceArray[j + 1] = temp; 
	                    
	                } 
	            }
	        }
	        
	        for(int x = 0; x < occurrenceArray.length; x ++ ) {
	        	String docRedundant = occurrenceArray[x].document;
	        	
	        	if(docsWithKeywords.size() < 6) {
	        		if(!docsWithKeywords.contains(docRedundant)) {
	        			docsWithKeywords.add(occurrenceArray[x].document);
	        		}
	        	}
	        	
	        	
	        }
		}
				
		if(docsWithKeywords.isEmpty()) { return null; }
		return docsWithKeywords;
	
	}

}

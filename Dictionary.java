import java.util.ArrayList;
import java.util.LinkedList;

/**
 * A dictionary of calculated hash values.
 * Takes a list of words, generates their hashes and
 * checks for collisions.
 * 
 * @author n-c0de-r
 * @author jonasblome
 * @author GitYusuf
 * @version 17.07.2021
 */
public class Dictionary {
	private final int HASH_MULTIPLIER;
	private ArrayList<LinkedList<String>> wordHashes;

	public Dictionary() {
		GeneratePrimes p = new GeneratePrimes();
		HASH_MULTIPLIER = p.generatePrime();

		setupDictionary();
	}

	/**
	 * Generate a new dictionary with hash values.
	 */
	private void setupDictionary() {
		int collisions = 0;
		
		WordParser w = new WordParser();
		ArrayList<String> words = w.getWords();

		wordHashes = new ArrayList<>();
		
		//Set the size array list to half the length of available words.
		for (int i = 0; i < words.size() * 0.5; i++) {
			wordHashes.add(i, null);
		}
		
		//Longest chain counter
		int longestChain = 0;
		
		for (String word : words) {		
			// Generate hash values and set as current index
			int index = generateHash(word);

			// Assign the words to the list at the position of hash values
			if (hasLinkedList(wordHashes.get(index))) {
				
				//if collisions occur increment
				collisions++;
				
				// Check if the word is already in the LinkedList
				if (!hasWord(wordHashes.get(index), word)) {
					int currentChainLength = 0;
					wordHashes.get(index).add(word);
					currentChainLength =  wordHashes.get(index).size()+1;
					
					//If the newly found chain is longer, update the value
					if (currentChainLength > longestChain) {
						longestChain = currentChainLength;
					}
				}
			} else {
				//if there's no linked list already, create a new one
				wordHashes.add(index, new LinkedList<String>());
				wordHashes.get(index).add(word);
			}
		}
		System.out.println("There are " + collisions + " collisions in the table.");
		System.out.println("Longest chain is " + longestChain);
	}
	
	//Helper Methods start here
	
	/**
	 * Helper method to generate a word's hash value.
	 * 
	 * @param word	Word to calculate the has for.
	 * @return	The integer hash value.
	 */
	private int generateHash(String word) {
		int h = 0;
		for (int i = 0; i < word.length(); i++) {
			h = HASH_MULTIPLIER * h + word.charAt(i);
		}
		h = Math.abs(h % wordHashes.size());
		return h;
	}
	
	/**
	 * Checks it a linked list exists in the array.
	 * 
	 * @param list	List to be checked
	 * @return	True or false, if it exists
	 */
	private boolean hasLinkedList(LinkedList<String> list) {
		return list != null;
	}

	/**
	 * Checks if a linked list has a certain word.
	 * 
	 * @param list	List to be checked
	 * @param word	Word to be found
	 * @return	True or false, if it exists
	 */
	private boolean hasWord(LinkedList<String> list, String word) {
		return list.contains(word);
	}
}

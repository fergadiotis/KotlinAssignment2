/*
fun areAnagrams(word1: String, word2: String): Boolean {
    /**
     * Check if two words are anagrams of each other
     * @param word1 the First word
     * @param word2 the second word
     * @return True if the words are anagrams, else False
     */
    // Convert to lowercase and sort the characters
    val sortedWord1 = word1.lowercase().toCharArray().sorted().joinToString("")
    val sortedWord2 = word2.lowercase().toCharArray().sorted().joinToString("")

    // Compare the sorted strings
    return sortedWord1 == sortedWord2
}

fun main() {
    println(areAnagrams("dusty", "study"))  // Should print: true
    println(areAnagrams("Love", "strings"))  // Should print: false

    // Testing additional examples from your comment
    println(areAnagrams("below", "elbow"))  // Should print: true
    println(areAnagrams("night", "thing"))  // Should print: true
    println(areAnagrams("act", "cat"))      // Should print: true
    println(areAnagrams("dessert", "stressed"))  // Should print: true
    println(areAnagrams("bad credit", "debit card"))  // Should print: true
    println(areAnagrams("gainly", "laying"))  // Should print: true
}
*/

/*
fun isSubString(mainStr: String, subStr: String): Boolean {
    /**
     * Check if subStr is a substring of the main String (mainStr) without using the contains method
     * @param mainStr The main string to search within
     * @param subStr The substring to check for
     * @return True if the subStr is a substring of mainStr, otherwise false
     */
    // Early return if subStr is longer than mainStr
    if (subStr.length > mainStr.length) return false

    // Iterate through potential starting positions in mainStr
    for (i in 0..mainStr.length - subStr.length) {
        // Extract a substring of the same length as subStr and compare
        if (mainStr.substring(i, i + subStr.length) == subStr) {
            return true // Match found
        }
    }

    // No match found after checking all possible positions
    return false
}

fun main() {
    // Test cases
    println(isSubString("Hello World!", "Hello World!")) // true - exact match
    println(isSubString("Hello!", "hellooo"))            // false - different text

    // Additional test cases
    println(isSubString("Hello World!", "World"))        // true - substring in the middle
    println(isSubString("Hello World!", "Hello"))        // true - substring at the beginning
    println(isSubString("Hello World!", "!"))            // true - single character substring
    println(isSubString("Hello World!", ""))             // true - empty string is always a substring
    println(isSubString("Hello World!", "world"))        // false - case sensitive
    println(isSubString("", "test"))                     // false - empty main string
}
 */

/*
fun isSubString(mainStr: String, subStr: String): Boolean {
    /**
     * Check if subStr is a substring of the main String (mainStr) without using the contains method
     * @param mainStr The main string to search within
     * @param subStr The substring to check for
     * @return True if the subStr is a substring of mainStr, if not it will be false
     */
    if(subStr.length > mainStr.length) return false
    // if subStr is longer, it cannot be contained

    for(i in 0..mainStr.length - subStr.length) {
        if(mainStr.substring(i, i + subStr.length) == subStr){
            return true // match found
        }
    }
    return false
}

fun main() {
    println(isSubString("Hello World!", "Hello World!")) // Output: true
    println(isSubString("Hello!", "hellooo"))            // Output: false
    println(isSubString("abcdefg", "cde"))              // Output: true
    println(isSubString("abcdef", "xyz"))               // Output: false
    println(isSubString("Kotlin", "Kot"))               // Output: true
}
 */

/**
 * Caesar cipher implementation that can encrypt and decrypt messages.
 * The cipher works by shifting each letter in the text by a specified number of positions in the alphabet.
 */

/**
 * Encrypts a message using the Caesar cipher.
 *
 * @param message The plaintext to encrypt
 * @param shift The number of positions to shift each character (can be positive or negative)
 * @return The encrypted message
 */
fun encrypt(message: String, shift: Int): String {
    // Normalize the shift value to be within 0-25 range
    // This handles negative shifts and shifts larger than 26
    // Example: -1 becomes 25, 27 becomes 1
    val normalizedShift = (shift % 26 + 26) % 26

    // Create a StringBuilder to efficiently build the result string
    // Using StringBuilder is more efficient than string concatenation in a loop
    val result = StringBuilder()

    // Process each character in the message
    for (char in message) {
        val encryptedChar = when {
            // Handle lowercase letters
            char.isLowerCase() -> {
                // Convert char to its ASCII code, subtract 'a' to get 0-25 position in alphabet
                // Add the shift, then mod 26 to wrap around the alphabet if needed
                // Finally, add 'a' back to get the new ASCII code
                val shifted = (char.code - 'a'.code + normalizedShift) % 26 + 'a'.code
                // Convert the new ASCII code back to a character
                shifted.toChar()
            }
            // Handle uppercase letters (same logic as lowercase but using 'A' as base)
            char.isUpperCase() -> {
                val shifted = (char.code - 'A'.code + normalizedShift) % 26 + 'A'.code
                shifted.toChar()
            }
            // Leave non-alphabetic characters (spaces, punctuation, numbers) unchanged
            else -> char
        }
        // Add the encrypted character to our result
        result.append(encryptedChar)
    }

    // Convert StringBuilder back to a regular String and return it
    return result.toString()
}

/**
 * Decrypts a message that was encrypted using the Caesar cipher.
 *
 * @param encryptedMessage The encrypted text to decrypt
 * @param shift The number of positions that were used to shift each character during encryption
 * @return The decrypted (original) message
 */
fun decrypt(encryptedMessage: String, shift: Int): String {
    // To decrypt, we simply shift in the opposite direction (negative shift)
    // Reusing the encrypt function avoids duplicating code
    return encrypt(encryptedMessage, -shift)
}

/**
 * Attempts to crack a Caesar-encrypted message by trying all possible shifts
 * and returning all possible decryptions.
 *
 * @param encryptedMessage The encrypted message to crack
 * @return A list of all 26 possible decryptions with their corresponding shift values
 */
fun crack(encryptedMessage: String): List<Pair<Int, String>> {
    // Try all 26 possible shift values (0-25)
    return (0..25).map { shift ->
        // For each shift, create a pair of (shift value, decrypted message)
        // This uses the 'to' infix function to create a Pair
        shift to decrypt(encryptedMessage, shift)
    }
    // The result is a list of 26 pairs, one for each possible decryption
}

/**
 * Analyzes a string and returns the longest word in that string.
 * Words are considered to be separated by spaces.
 *
 * @param text The input string to analyze
 * @return The longest word found in the string
 */
fun analyzeForLongestWord(text: String): String {
    // Split the input text into words using space as separator
    val words = text.split(" ")

    // Start with an empty result
    var result = ""

    // Check each word
    for (word in words) {
        // If current word is longer than our current result, update the result
        if (word.length > result.length) {
            result = word
        }
    }

    // Return the longest word found
    return result
}

/**
 * Checks if two strings are anagrams of each other.
 * Anagrams are words that use the same letters in different orders.
 *
 * @param word1 The first word to check
 * @param word2 The second word to check
 * @return True if the words are anagrams, false otherwise
 */
fun areAnagrams(word1: String, word2: String): Boolean {
    // Convert both words to lowercase to make comparison case-insensitive
    // Convert strings to character arrays
    // Sort the characters in each array
    // Compare the sorted arrays - if they're the same, the words are anagrams
    return word1.lowercase().toCharArray().sorted() == word2.lowercase().toCharArray().sorted()
}

/**
 * Checks if subStr is a substring of mainStr.
 * This is a manual implementation without using the built-in 'contains' method.
 *
 * @param mainStr The main string to search within
 * @param subStr The substring to look for
 * @return True if subStr is contained within mainStr, false otherwise
 */
fun isSubString(mainStr: String, subStr: String): Boolean {
    // Quick check: if subStr is longer than mainStr, it can't be a substring
    if (subStr.length > mainStr.length) return false

    // Check each possible starting position in mainStr
    for (i in 0..mainStr.length - subStr.length) {
        // Extract a substring of the same length as subStr
        // Compare it to subStr - if they match, we found it
        if (mainStr.substring(i, i + subStr.length) == subStr) {
            return true // Match found, return immediately
        }
    }

    // If we checked all positions and found no match, return false
    return false
}

fun main() {
    // Test text for our functions
    val plaintext = "Hello, World!"
    val shift = 3

    println("Original text: $plaintext")

    // Test encrypt function
    val encrypted = encrypt(plaintext, shift)
    println("Encrypted (shift = $shift): $encrypted")

    // Test decrypt function
    val decrypted = decrypt(encrypted, shift)
    println("Decrypted: $decrypted")

    // Test crack function
    println("\nCracking example:")
    val crackResults = crack("Khoor, Zruog!") // This is "Hello, World!" encrypted with shift=3
    println("First few possible decryptions:")
    // Show just the first 5 results to avoid cluttering the output
    crackResults.take(5).forEach { (shift, result) ->
        println("Shift $shift: $result")
    }

    // Test other utility functions
    println("\nTesting other functions:")
    println("Longest word in 'The quick brown fox': " + analyzeForLongestWord("The quick brown fox"))
    println("'study' and 'dusty' are anagrams: " + areAnagrams("study", "dusty"))
    println("'Hello' is a substring of 'Hello World': " + isSubString("Hello World", "Hello"))
}

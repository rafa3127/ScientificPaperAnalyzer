/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repositories;

import models.Summary;
import basicdatastructures.HashTable;
import basicdatastructures.AVLTree;
import basicdatastructures.LinkedList;
import basicdatastructures.Node;
import io.SummaryParser;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.text.Normalizer;
import utils.MergeSort;

/**
 * Central repository managing all data structures for the scientific paper analyzer.
 * Handles summaries (HashTable), authors (AVL), and keywords (AVL).
 * 
 * Key normalization:
 * - All keys are stored in normalized form: lowercase, no accents, ñ->n
 * - Original values (with proper capitalization and accents) are preserved in Summary objects
 * - Mapping tables maintain the relationship between normalized keys and display names
 * 
 * Structure:
 * - Summaries: HashTable for O(1) lookup by title
 * - Authors: AVL Tree for O(log n) search and O(n) ordered listing
 * - Keywords: AVL Tree for O(log n) search and O(n) ordered listing
 * 
 * @author rafaelc3127
 */
public class Repository {
    
    // ==================== ATTRIBUTES ====================
    
    private HashTable<String, SummaryRecord> summariesTable;
    private AVLTree<String, LinkedList<Summary>> authorsAVL;
    private AVLTree<String, LinkedList<Summary>> keywordsAVL;
    
    // Mapping tables: normalized key -> original display name
    private HashTable<String, String> authorOriginalNames;
    private HashTable<String, String> keywordOriginalNames;
    
    private String dataDirectory;
    
    // ==================== CONSTRUCTOR ====================
    
    /**
     * Creates a new Repository.
     * 
     * @param dataDirectory Path to directory where summary files are stored
     */
    public Repository(String dataDirectory) {
        this.summariesTable = new HashTable<>();
        this.authorsAVL = new AVLTree<>();
        this.keywordsAVL = new AVLTree<>();
        this.authorOriginalNames = new HashTable<>();
        this.keywordOriginalNames = new HashTable<>();
        this.dataDirectory = dataDirectory;
        
        // Ensure data directory exists
        File dir = new File(dataDirectory);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }
    
    // ==================== NORMALIZATION ====================
    
    /**
     * Normalizes a string for use as a key in data structures.
     * 
     * Normalization rules:
     * - Converts to lowercase
     * - Removes all accent marks (á->a, é->e, etc.)
     * - Converts ñ to n
     * - Preserves spaces and other characters
     * 
     * Examples:
     * - "José García" -> "jose garcia"
     * - "Año" -> "ano"
     * - "Árbol" -> "arbol"
     * 
     * @param text Text to normalize
     * @return Normalized string suitable for searching/indexing
     */
    private String normalizeKey(String text) {
        if (text == null) {
            return null;
        }
        
        // Convert to lowercase first
        String lowercased = text.toLowerCase();
        
        // Decompose characters: separate base character from accents
        // NFD = Canonical Decomposition
        String decomposed = Normalizer.normalize(lowercased, Normalizer.Form.NFD);
        
        // Remove all accent marks (Unicode category Mark)
        // This regex removes combining diacritical marks
        String withoutAccents = decomposed.replaceAll("\\p{M}", "");
        
        return withoutAccents;
    }
    
    // ==================== LOAD/SAVE ====================
    
    /**
     * Loads all summaries from the data directory.
     * Parses each .txt file and indexes by title, authors, and keywords.
     * 
     * Time complexity: O(n log n) where n is number of files
     * 
     * @return Number of summaries successfully loaded
     * @throws IOException if directory cannot be read
     */
    public int loadAll() throws IOException {
        File dir = new File(dataDirectory);
        File[] files = dir.listFiles((d, name) -> name.endsWith(".txt"));
        
        int loadedCount = 0;
        
        if (files != null) {
            for (File file : files) {
                try {
                    Summary summary = SummaryParser.parseFromFile(file.getPath());
                    SummaryRecord record = new SummaryRecord(summary, file.getPath());
                    
                    // Add to summaries table (normalized key)
                    String titleKey = normalizeKey(summary.getTitle());
                    summariesTable.put(titleKey, record);
                    
                    // Index by authors
                    indexByAuthors(summary);
                    
                    // Index by keywords
                    indexByKeywords(summary);
                    
                    loadedCount++;
                    
                } catch (Exception e) {
                    System.err.println("Error loading file: " + file.getName() + " - " + e.getMessage());
                }
            }
        }
        
        return loadedCount;
    }
    
    /**
     * Adds a new summary by copying the source file to the data directory.
     * Automatically indexes by title, authors, and keywords.
     * Validates that the summary doesn't already exist.
     * 
     * Time complexity: O(log n)
     * 
     * @param sourceFilepath Path to the source summary file
     * @return The created Summary object
     * @throws IOException if file operations fail
     * @throws IllegalArgumentException if summary already exists
     */
    public Summary add(String sourceFilepath) throws IOException {
        // Parse the source file
        Summary summary = SummaryParser.parseFromFile(sourceFilepath);
        
        // Check if already exists (normalized key)
        String titleKey = normalizeKey(summary.getTitle());
        if (summariesTable.containsKey(titleKey)) {
            throw new IllegalArgumentException("A summary with this title already exists: " + summary.getTitle());
        }
        
        // Generate unique filename using timestamp
        String timestamp = String.valueOf(System.currentTimeMillis());
        String destinationFilename = "resumen_" + timestamp + ".txt";
        String destinationPath = dataDirectory + File.separator + destinationFilename;
        
        // Copy file to data directory
        File source = new File(sourceFilepath);
        File destination = new File(destinationPath);
        Files.copy(source.toPath(), destination.toPath(), StandardCopyOption.REPLACE_EXISTING);
        
        // Add to summaries table
        SummaryRecord record = new SummaryRecord(summary, destinationPath);
        summariesTable.put(titleKey, record);
        
        // Index by authors
        indexByAuthors(summary);
        
        // Index by keywords
        indexByKeywords(summary);
        
        return summary;
    }
    
    // ==================== SUMMARIES ====================
    
    /**
     * Finds a summary by its title (case-insensitive, accent-insensitive).
     * 
     * Time complexity: O(1)
     * 
     * @param title Title to search for
     * @return Summary if found, null otherwise
     */
    public Summary findByTitle(String title) {
        String titleKey = normalizeKey(title);
        SummaryRecord record = summariesTable.get(titleKey);
        return record != null ? record.getSummary() : null;
    }
    
    /**
     * Checks if a summary with the given title exists.
     * 
     * Time complexity: O(1)
     * 
     * @param title Title to check
     * @return true if exists, false otherwise
     */
    public boolean containsTitle(String title) {
        return summariesTable.containsKey(normalizeKey(title));
    }
    
    /**
     * Returns all summary titles in alphabetical order.
     * The titles are sorted by their normalized form but displayed in original form.
     * 
     * Time complexity: O(n log n) due to sorting
     * 
     * @return LinkedList of all titles, ordered alphabetically
     */
    public LinkedList<String> getAllTitlesSorted() {
        LinkedList<String> titles = new LinkedList<>();
        
        // Get all keys from hash table
        LinkedList<String> keys = summariesTable.getKeys();
        
        // Convert to array for sorting
        Node<String> current = keys.getHead();
        int size = 0;
        Node<String> temp = current;
        while (temp != null) {
            size++;
            temp = temp.getNext();
        }
        
        String[] titleArray = new String[size];
        int index = 0;
        current = keys.getHead();
        while (current != null) {
            SummaryRecord record = summariesTable.get(current.getData());
            titleArray[index++] = record.getSummary().getTitle();
            current = current.getNext();
        }
        
        // Sort alphabetically using our MergeSort implementation
        MergeSort.sort(titleArray);
        
        // Add to result list
        for (String title : titleArray) {
            titles.add(title);
        }
        
        return titles;
    }
    
    /**
     * Returns all summaries (unordered).
     * 
     * Time complexity: O(n)
     * 
     * @return LinkedList of all summaries
     */
    public LinkedList<Summary> getAllSummaries() {
        LinkedList<Summary> summaries = new LinkedList<>();
        
        LinkedList<String> keys = summariesTable.getKeys();
        Node<String> current = keys.getHead();
        
        while (current != null) {
            SummaryRecord record = summariesTable.get(current.getData());
            summaries.add(record.getSummary());
            current = current.getNext();
        }
        
        return summaries;
    }
    
    /**
     * Returns the number of summaries in the repository.
     * 
     * Time complexity: O(1)
     * 
     * @return Number of summaries
     */
    public int getSummaryCount() {
        return summariesTable.size();
    }
    
    // ==================== AUTHORS ====================
    
    /**
     * Gets all summaries by a specific author.
     * Search is case-insensitive and accent-insensitive.
     * 
     * Time complexity: O(log n)
     * 
     * @param author Author name to search for
     * @return LinkedList of summaries by this author, or empty list if not found
     */
    public LinkedList<Summary> getSummariesByAuthor(String author) {
        String authorKey = normalizeKey(author);
        LinkedList<Summary> summaries = authorsAVL.search(authorKey);
        return summaries != null ? summaries : new LinkedList<>();
    }
    
    /**
     * Returns all author names in alphabetical order.
     * Uses AVL's inorder traversal which automatically provides sorted order.
     * 
     * Time complexity: O(n)
     * 
     * @return LinkedList of all authors, ordered alphabetically
     */
    public LinkedList<String> getAllAuthorsSorted() {
        LinkedList<String> authors = new LinkedList<>();
        
        // Get sorted keys from AVL (inorder traversal provides sorted order)
        LinkedList<String> sortedKeys = authorsAVL.inorderTraversal();
        Node<String> current = sortedKeys.getHead();
        
        while (current != null) {
            String normalizedKey = current.getData();
            // Get the original display name for this key
            String originalName = authorOriginalNames.get(normalizedKey);
            if (originalName != null) {
                authors.add(originalName);
            }
            current = current.getNext();
        }
        
        return authors;
    }
    
    /**
     * Returns the number of unique authors.
     * 
     * Time complexity: O(1)
     * 
     * @return Number of authors
     */
    public int getAuthorCount() {
        return authorsAVL.size();
    }
    
    // ==================== KEYWORDS ====================
    
    /**
     * Gets all summaries that contain a specific keyword.
     * Search is case-insensitive and accent-insensitive.
     * 
     * Time complexity: O(log n)
     * 
     * @param keyword Keyword to search for
     * @return LinkedList of summaries containing this keyword, or empty list if not found
     */
    public LinkedList<Summary> getSummariesByKeyword(String keyword) {
        String keywordKey = normalizeKey(keyword);
        LinkedList<Summary> summaries = keywordsAVL.search(keywordKey);
        return summaries != null ? summaries : new LinkedList<>();
    }
    
    /**
     * Returns all keywords in alphabetical order.
     * Uses AVL's inorder traversal which automatically provides sorted order.
     * 
     * Time complexity: O(n)
     * 
     * @return LinkedList of all keywords, ordered alphabetically
     */
    public LinkedList<String> getAllKeywordsSorted() {
        LinkedList<String> keywords = new LinkedList<>();
        
        // Get sorted keys from AVL (inorder traversal provides sorted order)
        LinkedList<String> sortedKeys = keywordsAVL.inorderTraversal();
        Node<String> current = sortedKeys.getHead();
        
        while (current != null) {
            String normalizedKey = current.getData();
            // Get the original display name for this key
            String originalName = keywordOriginalNames.get(normalizedKey);
            if (originalName != null) {
                keywords.add(originalName);
            }
            current = current.getNext();
        }
        
        return keywords;
    }
    
    /**
     * Returns the number of unique keywords.
     * 
     * Time complexity: O(1)
     * 
     * @return Number of keywords
     */
    public int getKeywordCount() {
        return keywordsAVL.size();
    }
    
    /**
     * Gets the frequency of a keyword (how many summaries contain it).
     * 
     * Time complexity: O(log n) for search + O(k) for counting
     * 
     * @param keyword Keyword to check
     * @return Number of summaries containing this keyword
     */
    public int getKeywordFrequency(String keyword) {
        LinkedList<Summary> summaries = getSummariesByKeyword(keyword);
        int count = 0;
        Node<Summary> current = summaries.getHead();
        while (current != null) {
            count++;
            current = current.getNext();
        }
        return count;
    }
    
    // ==================== PRIVATE HELPERS ====================
    
    /**
     * Indexes a summary by all its authors in the AVL tree.
     * Also stores the original author name for display purposes.
     * 
     * @param summary Summary to index
     */
    private void indexByAuthors(Summary summary) {
        Node<String> author = summary.getAuthors().getHead();
        
        while (author != null) {
            String authorOriginal = author.getData();
            String authorKey = normalizeKey(authorOriginal);
            
            // Store original name if not already stored
            if (!authorOriginalNames.containsKey(authorKey)) {
                authorOriginalNames.put(authorKey, authorOriginal);
            }
            
            // Get or create the list for this author
            LinkedList<Summary> summaries = authorsAVL.search(authorKey);
            
            if (summaries == null) {
                // Author doesn't exist yet, create new list
                summaries = new LinkedList<>();
                summaries.add(summary);
                authorsAVL.insert(authorKey, summaries);
            } else {
                // Author exists, add to existing list
                summaries.add(summary);
            }
            
            author = author.getNext();
        }
    }
    
    /**
     * Indexes a summary by all its keywords in the AVL tree.
     * Also stores the original keyword for display purposes.
     * 
     * @param summary Summary to index
     */
    private void indexByKeywords(Summary summary) {
        Node<String> keyword = summary.getKeywords().getHead();
        
        while (keyword != null) {
            String keywordOriginal = keyword.getData();
            String keywordKey = normalizeKey(keywordOriginal);
            
            // Store original name if not already stored
            if (!keywordOriginalNames.containsKey(keywordKey)) {
                keywordOriginalNames.put(keywordKey, keywordOriginal);
            }
            
            // Get or create the list for this keyword
            LinkedList<Summary> summaries = keywordsAVL.search(keywordKey);
            
            if (summaries == null) {
                // Keyword doesn't exist yet, create new list
                summaries = new LinkedList<>();
                summaries.add(summary);
                keywordsAVL.insert(keywordKey, summaries);
            } else {
                // Keyword exists, add to existing list
                summaries.add(summary);
            }
            
            keyword = keyword.getNext();
        }
    }
    
    /**
     * Checks if the repository is empty.
     * 
     * @return true if no summaries, false otherwise
     */
    public boolean isEmpty() {
        return summariesTable.isEmpty();
    }
}

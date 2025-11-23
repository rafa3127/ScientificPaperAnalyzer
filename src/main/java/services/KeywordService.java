/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import models.Summary;
import models.KeywordDetails;
import repositories.Repository;
import basicdatastructures.LinkedList;

/**
 * Service layer for managing and retrieving keyword information.
 * 
 * Responsibilities:
 * - Get lists of all keywords
 * - Get detailed information about specific keywords
 * - Provide keyword statistics
 * 
 * @author rafaelc3127
 */
public class KeywordService {
    
    private Repository repository;
    
    /**
     * Creates a new KeywordService.
     * 
     * @param repository The repository to use for data access
     * @throws IllegalArgumentException if repository is null
     */
    public KeywordService(Repository repository) {
        if (repository == null) {
            throw new IllegalArgumentException("Repository cannot be null");
        }
        this.repository = repository;
    }
    
    /**
     * Returns all keywords in alphabetical order.
     * 
     * Time complexity: O(n) - inorder traversal of AVL
     * 
     * @return LinkedList of all keywords, ordered alphabetically
     */
    public LinkedList<String> getAllKeywordsSorted() {
        return repository.getAllKeywordsSorted();
    }
    
    /**
     * Gets detailed information about a specific keyword.
     * Includes the keyword, frequency (number of summaries containing it),
     * and the list of summaries.
     * 
     * Search is case-insensitive and accent-insensitive.
     * 
     * Time complexity: O(log n) - AVL search
     * 
     * @param keyword Keyword to get details for
     * @return KeywordDetails object, or null if keyword not found
     */
    public KeywordDetails getKeywordDetails(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return null;
        }
        
        // Get summaries containing this keyword
        LinkedList<Summary> summaries = repository.getSummariesByKeyword(keyword);
        
        if (summaries.isEmpty()) {
            return null;
        }
        
        // Get frequency (could also use repository.getKeywordFrequency)
        int frequency = repository.getKeywordFrequency(keyword);
        
        return new KeywordDetails(keyword, frequency, summaries);
    }
    
    /**
     * Returns the number of unique keywords in the repository.
     * 
     * Time complexity: O(1)
     * 
     * @return Number of unique keywords
     */
    public int getKeywordCount() {
        return repository.getKeywordCount();
    }
    
    /**
     * Gets the frequency of a keyword (how many summaries contain it).
     * Search is case-insensitive and accent-insensitive.
     * 
     * Time complexity: O(log n) for search + O(k) for counting
     * 
     * @param keyword Keyword to check
     * @return Number of summaries containing this keyword, or 0 if not found
     */
    public int getKeywordFrequency(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return 0;
        }
        
        return repository.getKeywordFrequency(keyword);
    }
    
    /**
     * Checks if a keyword exists in the repository.
     * Search is case-insensitive and accent-insensitive.
     * 
     * Time complexity: O(log n)
     * 
     * @param keyword Keyword to check
     * @return true if keyword exists, false otherwise
     */
    public boolean keywordExists(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return false;
        }
        
        return getKeywordFrequency(keyword) > 0;
    }
}

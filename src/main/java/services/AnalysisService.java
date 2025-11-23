/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import models.Summary;
import models.AnalysisResult;
import repositories.Repository;
import basicdatastructures.HashTable;
import basicdatastructures.LinkedList;
import basicdatastructures.Node;
import java.text.Normalizer;

/**
 * Service layer for analyzing summaries and calculating keyword frequencies.
 * 
 * Responsibilities:
 * - Analyze a summary's body text
 * - Count occurrences of each keyword from the repository
 * - Return analysis results with frequency data
 * 
 * @author rafaelc3127
 */
public class AnalysisService {
    
    private Repository repository;
    
    /**
     * Creates a new AnalysisService.
     * 
     * @param repository The repository to use for data access
     * @throws IllegalArgumentException if repository is null
     */
    public AnalysisService(Repository repository) {
        if (repository == null) {
            throw new IllegalArgumentException("Repository cannot be null");
        }
        this.repository = repository;
    }
    
    /**
     * Analyzes a summary and counts how many times each keyword from the repository
     * appears in the summary's body text.
     * 
     * The analysis is case-insensitive and accent-insensitive.
     * Only counts keywords that exist in the repository.
     * 
     * Time complexity: O(k * m) where k = number of keywords in repository,
     *                            m = length of summary body
     * 
     * @param summary Summary to analyze
     * @return AnalysisResult containing title, authors, and keyword frequencies
     * @throws IllegalArgumentException if summary is null
     */
    public AnalysisResult analyzeKeywordFrequencies(Summary summary) {
        if (summary == null) {
            throw new IllegalArgumentException("Summary cannot be null");
        }
        
        // Get all keywords from repository
        LinkedList<String> allKeywords = repository.getAllKeywordsSorted();
        
        // Normalize the summary body for comparison
        String normalizedBody = normalizeText(summary.getBody());
        
        // Count frequency of each keyword
        HashTable<String, Integer> frequencies = new HashTable<>();
        
        Node<String> currentKeyword = allKeywords.getHead();
        while (currentKeyword != null) {
            String keyword = currentKeyword.getData();
            String normalizedKeyword = normalizeText(keyword);
            
            // Count occurrences of this keyword in the body
            int count = countOccurrences(normalizedBody, normalizedKeyword);
            
            // Store with original keyword (not normalized)
            frequencies.put(keyword, count);
            
            currentKeyword = currentKeyword.getNext();
        }
        
        return new AnalysisResult(summary.getTitle(), summary.getAuthors(), frequencies);
    }
    
    /**
     * Analyzes a summary by title.
     * 
     * Time complexity: O(1) for lookup + O(k * m) for analysis
     * 
     * @param title Title of the summary to analyze
     * @return AnalysisResult if summary found, null otherwise
     */
    public AnalysisResult analyzeByTitle(String title) {
        if (title == null || title.trim().isEmpty()) {
            return null;
        }
        
        Summary summary = repository.findByTitle(title);
        if (summary == null) {
            return null;
        }
        
        return analyzeKeywordFrequencies(summary);
    }
    
    /**
     * Normalizes text for comparison.
     * Converts to lowercase and removes accents.
     * 
     * @param text Text to normalize
     * @return Normalized text
     */
    private String normalizeText(String text) {
        if (text == null) {
            return "";
        }
        
        String lowercased = text.toLowerCase();
        String decomposed = Normalizer.normalize(lowercased, Normalizer.Form.NFD);
        String withoutAccents = decomposed.replaceAll("\\p{M}", "");
        
        return withoutAccents;
    }
    
    /**
     * Counts how many times a keyword appears in a text.
     * Uses word boundary matching to count whole words only.
     * 
     * For example:
     * - "realidad" in "realidad virtual" → 1 occurrence
     * - "realidad" in "realidad y realidad virtual" → 2 occurrences
     * - "realidad" in "irrealidad" → 0 occurrences (not a whole word)
     * 
     * Time complexity: O(n) where n is the length of the text
     * 
     * @param text Text to search in (should be normalized)
     * @param keyword Keyword to search for (should be normalized)
     * @return Number of occurrences
     */
    private int countOccurrences(String text, String keyword) {
        if (text == null || text.isEmpty() || keyword == null || keyword.isEmpty()) {
            return 0;
        }
        
        int count = 0;
        int index = 0;
        
        // Search for keyword as a whole word
        while ((index = text.indexOf(keyword, index)) != -1) {
            // Check if it's a whole word (not part of a larger word)
            boolean isWordStart = (index == 0) || !Character.isLetterOrDigit(text.charAt(index - 1));
            boolean isWordEnd = (index + keyword.length() >= text.length()) || 
                                !Character.isLetterOrDigit(text.charAt(index + keyword.length()));
            
            if (isWordStart && isWordEnd) {
                count++;
            }
            
            index += keyword.length();
        }
        
        return count;
    }
}

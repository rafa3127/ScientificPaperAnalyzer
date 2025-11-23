/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import basicdatastructures.HashTable;
import basicdatastructures.LinkedList;

/**
 * Data Transfer Object containing the results of analyzing a summary's keywords.
 * Used to pass analysis data from AnalysisService to UI layer.
 * 
 * Contains:
 * - Summary title
 * - List of authors
 * - Frequency count for each keyword found in the summary body
 * 
 * Immutable after construction.
 * 
 * @author rafaelc3127
 */
public class AnalysisResult {
    
    private final String title;
    private final LinkedList<String> authors;
    private final HashTable<String, Integer> keywordFrequencies;
    
    /**
     * Creates a new AnalysisResult.
     * 
     * @param title Title of the analyzed summary
     * @param authors List of authors
     * @param keywordFrequencies Map of keyword → frequency count in the summary body
     * @throws IllegalArgumentException if any parameter is null
     */
    public AnalysisResult(String title, LinkedList<String> authors, HashTable<String, Integer> keywordFrequencies) {
        if (title == null) {
            throw new IllegalArgumentException("Title cannot be null");
        }
        if (authors == null) {
            throw new IllegalArgumentException("Authors cannot be null");
        }
        if (keywordFrequencies == null) {
            throw new IllegalArgumentException("Keyword frequencies cannot be null");
        }
        
        this.title = title;
        this.authors = authors;
        this.keywordFrequencies = keywordFrequencies;
    }
    
    /**
     * Returns the title of the analyzed summary.
     * 
     * @return Summary title
     */
    public String getTitle() {
        return title;
    }
    
    /**
     * Returns the list of authors.
     * 
     * @return Authors list
     */
    public LinkedList<String> getAuthors() {
        return authors;
    }
    
    /**
     * Returns the keyword frequencies map.
     * Each entry maps a keyword to the number of times it appears in the summary body.
     * 
     * @return HashTable mapping keyword → frequency
     */
    public HashTable<String, Integer> getKeywordFrequencies() {
        return keywordFrequencies;
    }
}

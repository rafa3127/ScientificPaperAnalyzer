/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import basicdatastructures.LinkedList;

/**
 * Data Transfer Object containing details about a specific keyword.
 * Used to pass keyword information from KeywordService to UI layer.
 * 
 * Contains:
 * - The keyword itself
 * - Frequency (number of summaries containing this keyword)
 * - List of summaries that contain this keyword
 * 
 * Immutable after construction.
 * 
 * @author rafaelc3127
 */
public class KeywordDetails {
    
    private final String keyword;
    private final int frequency;
    private final LinkedList<Summary> summaries;
    
    /**
     * Creates a new KeywordDetails.
     * 
     * @param keyword The keyword
     * @param frequency Number of summaries containing this keyword
     * @param summaries List of summaries that contain this keyword
     * @throws IllegalArgumentException if keyword is null/empty or frequency is negative
     */
    public KeywordDetails(String keyword, int frequency, LinkedList<Summary> summaries) {
        if (keyword == null || keyword.trim().isEmpty()) {
            throw new IllegalArgumentException("Keyword cannot be null or empty");
        }
        if (frequency < 0) {
            throw new IllegalArgumentException("Frequency cannot be negative");
        }
        if (summaries == null) {
            throw new IllegalArgumentException("Summaries list cannot be null");
        }
        
        this.keyword = keyword;
        this.frequency = frequency;
        this.summaries = summaries;
    }
    
    /**
     * Returns the keyword.
     * 
     * @return The keyword
     */
    public String getKeyword() {
        return keyword;
    }
    
    /**
     * Returns the frequency (number of summaries containing this keyword).
     * 
     * @return Frequency count
     */
    public int getFrequency() {
        return frequency;
    }
    
    /**
     * Returns the list of summaries containing this keyword.
     * 
     * @return LinkedList of summaries
     */
    public LinkedList<Summary> getSummaries() {
        return summaries;
    }
}

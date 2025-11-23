/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import models.Summary;
import repositories.Repository;
import basicdatastructures.LinkedList;
import java.io.IOException;

/**
 * Service layer for managing scientific paper summaries.
 * Handles business logic for adding, retrieving, and validating summaries.
 * 
 * Responsibilities:
 * - Add new summaries with validation
 * - Check for duplicate summaries
 * - Retrieve summaries by title
 * - Get lists of summaries (sorted or unsorted)
 * - Provide summary statistics
 * 
 * @author rafaelc3127
 */
public class SummaryService {
    
    private Repository repository;
    
    /**
     * Creates a new SummaryService.
     * 
     * @param repository The repository to use for data access
     * @throws IllegalArgumentException if repository is null
     */
    public SummaryService(Repository repository) {
        if (repository == null) {
            throw new IllegalArgumentException("Repository cannot be null");
        }
        this.repository = repository;
    }
    
    /**
     * Adds a new summary from a file.
     * Validates that the summary doesn't already exist before adding.
     * 
     * Time complexity: O(log n) - dominated by AVL insertions
     * 
     * @param filepath Path to the summary file to add
     * @return The created Summary object
     * @throws IOException if file cannot be read or copied
     * @throws IllegalArgumentException if summary already exists or file is invalid
     */
    public Summary addSummary(String filepath) throws IOException {
        if (filepath == null || filepath.trim().isEmpty()) {
            throw new IllegalArgumentException("Filepath cannot be null or empty");
        }
        
        // Repository.add() already validates duplicates and parses the file
        return repository.add(filepath);
    }
    
    /**
     * Checks if a summary with the given title already exists.
     * Search is case-insensitive and accent-insensitive.
     * 
     * Time complexity: O(1)
     * 
     * @param title Title to check
     * @return true if a summary with this title exists, false otherwise
     */
    public boolean summaryExists(String title) {
        if (title == null || title.trim().isEmpty()) {
            return false;
        }
        return repository.containsTitle(title);
    }
    
    /**
     * Retrieves a summary by its title.
     * Search is case-insensitive and accent-insensitive.
     * 
     * Time complexity: O(1)
     * 
     * @param title Title of the summary to retrieve
     * @return Summary object if found, null otherwise
     */
    public Summary getSummaryByTitle(String title) {
        if (title == null || title.trim().isEmpty()) {
            return null;
        }
        return repository.findByTitle(title);
    }
    
    /**
     * Returns all summary titles in alphabetical order.
     * Uses MergeSort for O(n log n) sorting with proper Spanish collation.
     * 
     * Time complexity: O(n log n)
     * 
     * @return LinkedList of all titles, ordered alphabetically
     */
    public LinkedList<String> getAllTitlesSorted() {
        return repository.getAllTitlesSorted();
    }
    
    /**
     * Returns all summaries (unordered).
     * 
     * Time complexity: O(n)
     * 
     * @return LinkedList of all summaries
     */
    public LinkedList<Summary> getAllSummaries() {
        return repository.getAllSummaries();
    }
    
    /**
     * Returns the total number of summaries in the repository.
     * 
     * Time complexity: O(1)
     * 
     * @return Number of summaries
     */
    public int getSummaryCount() {
        return repository.getSummaryCount();
    }
    
    /**
     * Checks if the repository has any summaries.
     * 
     * Time complexity: O(1)
     * 
     * @return true if repository is empty, false otherwise
     */
    public boolean isEmpty() {
        return repository.isEmpty();
    }
    
    /**
     * Loads all summaries from the data directory.
     * This should be called at application startup.
     * 
     * Time complexity: O(n log n) where n is number of files
     * 
     * @return Number of summaries successfully loaded
     * @throws IOException if directory cannot be read
     */
    public int loadAllSummaries() throws IOException {
        return repository.loadAll();
    }
}

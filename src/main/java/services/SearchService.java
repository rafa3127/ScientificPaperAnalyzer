/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import models.Summary;
import repositories.Repository;
import basicdatastructures.LinkedList;

/**
 * Service layer for searching summaries by different criteria.
 * 
 * Responsibilities:
 * - Search summaries by keyword
 * - Search summaries by author
 * - Get lists of authors for UI dropdowns
 * 
 * @author rafaelc3127
 */
public class SearchService {
    
    private Repository repository;
    
    /**
     * Creates a new SearchService.
     * 
     * @param repository The repository to use for data access
     * @throws IllegalArgumentException if repository is null
     */
    public SearchService(Repository repository) {
        if (repository == null) {
            throw new IllegalArgumentException("Repository cannot be null");
        }
        this.repository = repository;
    }
    
    /**
     * Searches for summaries containing a specific keyword.
     * Search is case-insensitive and accent-insensitive.
     * 
     * Time complexity: O(log n) - AVL search
     * 
     * @param keyword Keyword to search for
     * @return LinkedList of summaries containing this keyword, or empty list if none found
     */
    public LinkedList<Summary> searchByKeyword(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return new LinkedList<>();
        }
        
        return repository.getSummariesByKeyword(keyword);
    }
    
    /**
     * Searches for summaries by a specific author.
     * Search is case-insensitive and accent-insensitive.
     * Returns summaries where the person is listed as author or co-author.
     * 
     * Time complexity: O(log n) - AVL search
     * 
     * @param author Author name to search for
     * @return LinkedList of summaries by this author, or empty list if none found
     */
    public LinkedList<Summary> searchByAuthor(String author) {
        if (author == null || author.trim().isEmpty()) {
            return new LinkedList<>();
        }
        
        return repository.getSummariesByAuthor(author);
    }
    
    /**
     * Returns all author names in alphabetical order.
     * Useful for populating UI dropdowns/lists.
     * 
     * Time complexity: O(n) - inorder traversal of AVL
     * 
     * @return LinkedList of all authors, ordered alphabetically
     */
    public LinkedList<String> getAllAuthorsSorted() {
        return repository.getAllAuthorsSorted();
    }
    
    /**
     * Returns the number of unique authors in the repository.
     * 
     * Time complexity: O(1)
     * 
     * @return Number of unique authors
     */
    public int getAuthorCount() {
        return repository.getAuthorCount();
    }
    
    /**
     * Checks if an author exists in the repository.
     * Search is case-insensitive and accent-insensitive.
     * 
     * Time complexity: O(log n)
     * 
     * @param author Author name to check
     * @return true if author exists, false otherwise
     */
    public boolean authorExists(String author) {
        if (author == null || author.trim().isEmpty()) {
            return false;
        }
        
        LinkedList<Summary> summaries = repository.getSummariesByAuthor(author);
        return !summaries.isEmpty();
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
        
        LinkedList<Summary> summaries = repository.getSummariesByKeyword(keyword);
        return !summaries.isEmpty();
    }
}

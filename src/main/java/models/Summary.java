/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import basicdatastructures.LinkedList;
import basicdatastructures.Node;
import java.text.Collator;
import java.util.Locale;

/**
 * Represents a scientific paper abstract/resume.
 * Contains title, authors, body text, and keywords.
 * Immutable after construction - no setters provided.
 * 
 * @author rafaelc3127
 */
public class Summary {
    
    private String title;
    private LinkedList<String> authors;
    private String body;
    private LinkedList<String> keywords;
    
    /**
     * Creates a new Summary with all required information.
     * 
     * @param title Title of the paper
     * @param authors List of author names
     * @param body Abstract/resume body text
     * @param keywords List of keywords
     * @throws IllegalArgumentException if any parameter is null or title/body empty
     */
    public Summary(String title, LinkedList<String> authors, String body, LinkedList<String> keywords) {
        // Validations
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Title cannot be null or empty");
        }
        if (authors == null) {
            throw new IllegalArgumentException("Authors list cannot be null");
        }
        if (body == null || body.trim().isEmpty()) {
            throw new IllegalArgumentException("Body cannot be null or empty");
        }
        if (keywords == null) {
            throw new IllegalArgumentException("Keywords list cannot be null");
        }
        
        this.title = title.trim();
        this.authors = authors;
        this.body = body.trim();
        this.keywords = keywords;
    }
    
    /**
     * Returns the title of the paper.
     * 
     * @return Paper title
     */
    public String getTitle() {
        return title;
    }
    
    /**
     * Returns the list of authors.
     * 
     * @return LinkedList of author names
     */
    public LinkedList<String> getAuthors() {
        return authors;
    }
    
    /**
     * Returns the body/abstract text.
     * 
     * @return Body text of the abstract
     */
    public String getBody() {
        return body;
    }
    
    /**
     * Returns the list of keywords.
     * 
     * @return LinkedList of keywords
     */
    public LinkedList<String> getKeywords() {
        return keywords;
    }
    
    /**
     * Returns a string representation of this Summary with all data.
     * Format:
     * Title: [title]
     * Authors: [author1, author2, ...]
     * Body: [body]
     * Keywords: [keyword1, keyword2, ...]
     * 
     * @return String representation of the summary
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        
        // Title
        sb.append("Title: ").append(title).append("\n");
        
        // Authors
        sb.append("Authors: ");
        Node<String> currentAuthor = authors.getHead();
        boolean firstAuthor = true;
        while (currentAuthor != null) {
            if (!firstAuthor) {
                sb.append(", ");
            }
            sb.append(currentAuthor.getData());
            firstAuthor = false;
            currentAuthor = currentAuthor.getNext();
        }
        sb.append("\n");
        
        // Body
        sb.append("Body: ").append(body).append("\n");
        
        // Keywords
        sb.append("Keywords: ");
        Node<String> currentKeyword = keywords.getHead();
        boolean firstKeyword = true;
        while (currentKeyword != null) {
            if (!firstKeyword) {
                sb.append(", ");
            }
            sb.append(currentKeyword.getData());
            firstKeyword = false;
            currentKeyword = currentKeyword.getNext();
        }
        
        return sb.toString();
    }
    
    /**
     * Compares this Summary with another object for equality.
     * Two summaries are considered equal if their titles are equal
     * (case-insensitive, ignoring accents) using Spanish locale.
     * 
     * @param obj The object to compare with
     * @return true if the titles are equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Summary)) {
            return false;
        }
        
        Summary other = (Summary) obj;
        
        // Use Collator for proper Spanish alphabetical comparison
        Collator collator = Collator.getInstance(new Locale("es", "ES"));
        collator.setStrength(Collator.PRIMARY); // Ignore case and accents
        
        return collator.compare(this.title, other.title) == 0;
    }
    
    /**
     * Returns a hash code for this Summary based on its title.
     * Uses lowercase to ensure consistency with equals() method.
     * 
     * @return Hash code value
     */
    @Override
    public int hashCode() {
        return title.toLowerCase().hashCode();
    }
}

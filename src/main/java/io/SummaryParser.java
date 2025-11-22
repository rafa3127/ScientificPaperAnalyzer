/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package io;

import java.io.IOException;

import basicdatastructures.LinkedList;
import basicdatastructures.Node;
import models.Summary;

/**
 * Parser specifically designed for scientific paper abstracts.
 * Handles the format provides with sections: Title, Autores, Resumen, Palabras claves.
 * 
 * @author rafaelc3127
 */
public class SummaryParser {
    
    /**
     * Parses a summary file from the format in Anexo 1.
     * 
     * Format expected:
     * - First non-empty line: Title
     * - Section "Autores": author names (one per line or comma-separated)
     * - Section "Resumen": abstract body (multiple lines)
     * - Line starting with "Palabras claves:": comma-separated keywords
     * 
     * @param filepath Path to the summary file
     * @return Summary object
     * @throws IOException if file cannot be read
     * @throws IllegalArgumentException if format is invalid
     */
    public static Summary parseFromFile(String filepath) throws IOException {
        LinkedList<String> lines = FileIO.readFile(filepath);
        
        if (lines.isEmpty()) {
            throw new IllegalArgumentException("File is empty");
        }
        
        // Step 1: Extract title (first non-empty line)
        String title = null;
        Node<String> current = lines.getHead();
        
        while (current != null && title == null) {
            String line = current.getData().trim();
            if (!line.isEmpty() && !line.equals("Autores") && !line.equals("Resumen")) {
                title = line;
                break;
            }
            current = current.getNext();
        }
        
        if (title == null) {
            throw new IllegalArgumentException("No title found");
        }
        
        // Step 2: Find and extract authors section
        LinkedList<String> authors = new LinkedList<>();
        boolean inAutoresSection = false;
        
        current = lines.getHead();
        while (current != null) {
            String line = current.getData().trim();
            
            if (line.equals("Autores")) {
                inAutoresSection = true;
                current = current.getNext();
                continue;
            }
            
            if (inAutoresSection) {
                // Stop if we hit the Resumen section
                if (line.equals("Resumen")) {
                    break;
                }
                
                // Add author (handle comma-separated or line-separated)
                if (!line.isEmpty()) {
                    String[] parts = line.split(",");
                    for (String author : parts) {
                        String authorTrimmed = author.trim();
                        if (!authorTrimmed.isEmpty()) {
                            authors.add(authorTrimmed);
                        }
                    }
                }
            }
            
            current = current.getNext();
        }
        
        // Step 3: Find and extract resume body
        StringBuilder bodyBuilder = new StringBuilder();
        boolean inResumenSection = false;
        
        current = lines.getHead();
        while (current != null) {
            String line = current.getData().trim();
            
            if (line.equals("Resumen")) {
                inResumenSection = true;
                current = current.getNext();
                continue;
            }
            
            if (inResumenSection) {
                // Stop if we find keywords line
                if (line.startsWith("Palabras claves:") || line.startsWith("Palabras Claves:")) {
                    break;
                }
                
                // Add line to body
                if (!line.isEmpty()) {
                    if (bodyBuilder.length() > 0) {
                        bodyBuilder.append(" ");
                    }
                    bodyBuilder.append(line);
                }
            }
            
            current = current.getNext();
        }
        
        String body = bodyBuilder.toString();
        
        if (body.isEmpty()) {
            throw new IllegalArgumentException("No resume body found");
        }
        
        // Step 4: Extract keywords
        LinkedList<String> keywords = new LinkedList<>();
        
        current = lines.getHead();
        while (current != null) {
            String line = current.getData().trim();
            
            if (line.startsWith("Palabras claves:") || line.startsWith("Palabras Claves:")) {
                // Extract keywords after the colon
                int colonIndex = line.indexOf(":");
                if (colonIndex != -1 && colonIndex < line.length() - 1) {
                    String keywordsStr = line.substring(colonIndex + 1).trim();
                    
                    // Remove trailing period if present
                    if (keywordsStr.endsWith(".")) {
                        keywordsStr = keywordsStr.substring(0, keywordsStr.length() - 1).trim();
                    }
                    
                    // Split by comma and clean
                    String[] keywordArray = keywordsStr.split(",");
                    for (String keyword : keywordArray) {
                        String keywordTrimmed = keyword.trim();
                        if (!keywordTrimmed.isEmpty()) {
                            keywords.add(keywordTrimmed);
                        }
                    }
                }
                break;
            }
            
            current = current.getNext();
        }
        
        return new Summary(title, authors, body, keywords);
    }
    
    /**
     * Saves a Summary to a text file in the format from Anexo 1.
     * 
     * @param summary Summary to save
     * @param filepath Path where to save
     * @throws IOException if file cannot be written
     */
    public static void saveToFile(Summary summary, String filepath) throws IOException {
        LinkedList<String> lines = new LinkedList<>();
        
        // Add title
        lines.add(summary.getTitle());
        
        // Add Autores section
        lines.add("Autores");
        Node<String> currentAuthor = summary.getAuthors().getHead();
        while (currentAuthor != null) {
            lines.add(currentAuthor.getData());
            currentAuthor = currentAuthor.getNext();
        }
        
        // Add Resumen section
        lines.add("Resumen");
        lines.add(summary.getBody());
        
        // Add keywords
        StringBuilder keywordsLine = new StringBuilder("Palabras claves: ");
        Node<String> currentKeyword = summary.getKeywords().getHead();
        boolean first = true;
        
        while (currentKeyword != null) {
            if (!first) {
                keywordsLine.append(", ");
            }
            keywordsLine.append(currentKeyword.getData());
            first = false;
            currentKeyword = currentKeyword.getNext();
        }
        
        lines.add(keywordsLine.toString());
        
        // Write to file
        FileIO.writeFile(filepath, lines);
    }
}

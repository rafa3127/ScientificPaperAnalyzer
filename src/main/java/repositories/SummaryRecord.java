/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repositories;

import models.Summary;

/**
 * Wrapper that associates a Summary with its file path.
 * Used by SummaryRepository to track where each summary is stored.
 * 
 * @author rafaelc3127
 */
public class SummaryRecord {
    
    private Summary summary;
    private String filepath;
    
    /**
     * Creates a new SummaryRecord.
     * 
     * @param summary The Summary object
     * @param filepath The file path where this summary is stored
     */
    public SummaryRecord(Summary summary, String filepath) {
        if (summary == null) {
            throw new IllegalArgumentException("Summary cannot be null");
        }
        if (filepath == null || filepath.trim().isEmpty()) {
            throw new IllegalArgumentException("Filepath cannot be null or empty");
        }
        
        this.summary = summary;
        this.filepath = filepath;
    }
    
    /**
     * Returns the Summary object.
     * 
     * @return The summary
     */
    public Summary getSummary() {
        return summary;
    }
    
    /**
     * Returns the file path.
     * 
     * @return The filepath where the summary is stored
     */
    public String getFilepath() {
        return filepath;
    }
    
    /**
     * Returns a string representation of this record.
     * 
     * @return String with summary title and filepath
     */
    @Override
    public String toString() {
        return "SummaryRecord{title='" + summary.getTitle() + "', filepath='" + filepath + "'}";
    }
}

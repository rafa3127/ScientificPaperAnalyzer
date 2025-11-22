/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.graph.scientificpaperanalyzer;

import io.SummaryParser;
import models.Summary;
import basicdatastructures.Node;

/**
 * Main class for Scientific Paper Analyzer.
 * Currently contains temporary tests for SummaryParser.
 * TODO: Remove test code when UI is implemented.
 * 
 * @author rafaelc3127
 */
public class ScientificPaperAnalyzer {

    public static void main(String[] args) {
        System.out.println("=== SCIENTIFIC PAPER ANALYZER - PARSER TEST ===");
        System.out.println();
        
        // Test all 6 sample files
        String[] testFiles = {
            "data/resumen1.txt",
            "data/resumen2.txt",
            "data/resumen3.txt",
            "data/resumen4.txt",
            "data/resumen5.txt",
            "data/resumen6.txt"
        };
        
        int successCount = 0;
        int errorCount = 0;
        
        for (String filepath : testFiles) {
            System.out.println("\n" + "=".repeat(80));
            System.out.println("Testing file: " + filepath);
            System.out.println("=".repeat(80));
            
            try {
                // Parse file
                Summary summary = SummaryParser.parseFromFile(filepath);
                
                // Display results
                System.out.println();
                System.out.println("Title: " + summary.getTitle());
                
                System.out.print("Authors: ");
                Node<String> author = summary.getAuthors().getHead();
                boolean first = true;
                while (author != null) {
                    if (!first) System.out.print(", ");
                    System.out.print(author.getData());
                    first = false;
                    author = author.getNext();
                }
                System.out.println();
                
                System.out.println("\nBody (first 100 chars): " + 
                    (summary.getBody().length() > 100 ? 
                        summary.getBody().substring(0, 100) + "..." : 
                        summary.getBody()));
                
                System.out.print("\nKeywords: ");
                Node<String> keyword = summary.getKeywords().getHead();
                first = true;
                while (keyword != null) {
                    if (!first) System.out.print(", ");
                    System.out.print(keyword.getData());
                    first = false;
                    keyword = keyword.getNext();
                }
                System.out.println();
                
                System.out.println("\n✅ SUCCESS - File parsed correctly");
                successCount++;
                
            } catch (Exception e) {
                System.err.println("\n❌ ERROR: " + e.getMessage());
                e.printStackTrace();
                errorCount++;
            }
        }
        
        // Summary
        System.out.println("\n" + "=".repeat(80));
        System.out.println("TEST SUMMARY");
        System.out.println("=".repeat(80));
        System.out.println("Total files tested: " + testFiles.length);
        System.out.println("✅ Successful: " + successCount);
        System.out.println("❌ Errors: " + errorCount);
        
        if (errorCount == 0) {
            System.out.println("\n🎉 ALL TESTS PASSED! Parser is working correctly.");
        } else {
            System.out.println("\n⚠️ Some tests failed. Please review errors above.");
        }
    }
}

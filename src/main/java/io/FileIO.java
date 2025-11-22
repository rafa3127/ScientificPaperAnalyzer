/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package io;

import basicdatastructures.LinkedList;
import basicdatastructures.Node;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Utility class for reading and writing text files.
 * Provides simple methods to read/write files line by line.
 * 
 * @author rafaelc3127
 */
public class FileIO {
    
    /**
     * Reads all lines from a text file.
     * 
     * @param filepath Path to the file to read
     * @return LinkedList containing all lines from the file
     * @throws IOException if file cannot be read
     */
    public static LinkedList<String> readFile(String filepath) throws IOException {
        LinkedList<String> lines = new LinkedList<>();
        BufferedReader reader = new BufferedReader(new FileReader(filepath));
        
        String line;
        while ((line = reader.readLine()) != null) {
            lines.add(line);
        }
        
        reader.close();
        return lines;
    }
    
    /**
     * Writes lines to a text file.
     * Each element in the list is converted to String using toString().
     * If any element cannot be converted, an exception is thrown and nothing is written.
     * 
     * @param filepath Path where to write the file
     * @param lines LinkedList of elements to write (will be converted to String)
     * @throws IOException if file cannot be written
     * @throws IllegalArgumentException if any element cannot be converted to String
     */
    public static void writeFile(String filepath, LinkedList<?> lines) throws IOException {
        // Validate and convert all elements to String first
        LinkedList<String> stringLines = new LinkedList<>();
        
        Node<?> current = lines.getHead();
        while (current != null) {
            Object data = current.getData();
            
            if (data == null) {
                throw new IllegalArgumentException("Cannot write null element to file");
            }
            
            try {
                String line = data.toString();
                if (line == null) {
                    throw new IllegalArgumentException("Element toString() returned null");
                }
                stringLines.add(line);
            } catch (Exception e) {
                throw new IllegalArgumentException("Element cannot be converted to String: " + e.getMessage());
            }
            
            current = current.getNext();
        }
        
        // All validations passed, now write to file
        BufferedWriter writer = new BufferedWriter(new FileWriter(filepath));
        
        Node<String> stringCurrent = stringLines.getHead();
        while (stringCurrent != null) {
            writer.write(stringCurrent.getData());
            writer.newLine();
            stringCurrent = stringCurrent.getNext();
        }
        
        writer.close();
    }
}

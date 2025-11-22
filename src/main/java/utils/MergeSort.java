/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import basicdatastructures.LinkedList;
import basicdatastructures.Node;
import java.text.Collator;
import java.util.Locale;

/**
 * Utility class providing MergeSort implementation for sorting.
 * Uses divide-and-conquer strategy to achieve O(n log n) time complexity.
 * 
 * @author rafaelc3127
 */
public class MergeSort {
    
    private static final Collator SPANISH_COLLATOR;
    
    static {
        SPANISH_COLLATOR = Collator.getInstance(new Locale("es", "ES"));
        SPANISH_COLLATOR.setStrength(Collator.PRIMARY);
    }
    
    /**
     * Sorts an array of strings using MergeSort algorithm.
     * Uses Spanish collator for proper ordering of accented characters.
     * 
     * Time complexity: O(n log n)
     * Space complexity: O(n) for temporary arrays
     * 
     * @param array Array to sort (will be modified in place)
     */
    public static void sort(String[] array) {
        if (array == null || array.length <= 1) {
            return;
        }
        
        String[] temp = new String[array.length];
        mergeSort(array, temp, 0, array.length - 1);
    }
    
    /**
     * Sorts a LinkedList of strings using MergeSort algorithm.
     * Converts to array, sorts, and converts back to LinkedList.
     * Uses Spanish collator for proper ordering of accented characters.
     * 
     * Time complexity: O(n log n)
     * Space complexity: O(n)
     * 
     * @param list LinkedList to sort
     * @return New sorted LinkedList (original list is not modified)
     */
    public static LinkedList<String> sort(LinkedList<String> list) {
        if (list == null || list.isEmpty()) {
            return new LinkedList<>();
        }
        
        // Convert LinkedList to array
        int size = 0;
        Node<String> current = list.getHead();
        while (current != null) {
            size++;
            current = current.getNext();
        }
        
        String[] array = new String[size];
        current = list.getHead();
        int index = 0;
        while (current != null) {
            array[index++] = current.getData();
            current = current.getNext();
        }
        
        // Sort the array
        sort(array);
        
        // Convert back to LinkedList
        LinkedList<String> sortedList = new LinkedList<>();
        for (String element : array) {
            sortedList.add(element);
        }
        
        return sortedList;
    }
    
    /**
     * Recursive merge sort implementation.
     * Divides the array into halves, sorts each half, and merges them.
     * 
     * @param array Array to sort
     * @param temp Temporary array for merging
     * @param left Left boundary of current subarray
     * @param right Right boundary of current subarray
     */
    private static void mergeSort(String[] array, String[] temp, int left, int right) {
        if (left < right) {
            // Find the middle point
            int mid = left + (right - left) / 2;
            
            // Sort first half
            mergeSort(array, temp, left, mid);
            
            // Sort second half
            mergeSort(array, temp, mid + 1, right);
            
            // Merge the sorted halves
            merge(array, temp, left, mid, right);
        }
    }
    
    /**
     * Merges two sorted subarrays into one sorted subarray.
     * Uses Spanish collator for string comparison.
     * 
     * @param array Original array
     * @param temp Temporary array for merging
     * @param left Starting index of left subarray
     * @param mid Ending index of left subarray (mid+1 is start of right)
     * @param right Ending index of right subarray
     */
    private static void merge(String[] array, String[] temp, int left, int mid, int right) {
        // Copy data to temporary array
        for (int i = left; i <= right; i++) {
            temp[i] = array[i];
        }
        
        int i = left;        // Index for left subarray
        int j = mid + 1;     // Index for right subarray
        int k = left;        // Index for merged array
        
        // Merge the two subarrays back into array[left...right]
        while (i <= mid && j <= right) {
            // Use Spanish collator for proper comparison
            if (SPANISH_COLLATOR.compare(temp[i], temp[j]) <= 0) {
                array[k] = temp[i];
                i++;
            } else {
                array[k] = temp[j];
                j++;
            }
            k++;
        }
        
        // Copy remaining elements of left subarray, if any
        while (i <= mid) {
            array[k] = temp[i];
            i++;
            k++;
        }
        
        // Copy remaining elements of right subarray, if any
        while (j <= right) {
            array[k] = temp[j];
            j++;
            k++;
        }
    }
}

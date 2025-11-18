/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package basicdatastructures;

/**
 *
 * @author rafaelc3127
 */

/**
 * Represents a node of data with pointer to next element.
 * 
 * @param <T> The type of data stored in the node
 */
public class Node<T> {
    T data;
    Node<T> next;
    
    /**
     * Constructor that creates a node with the specified data.
     * 
     * @param data The data to store in this node
     */
    public Node(T data) {
        this.data = data;
        this.next = null;
    }
    
    /**
     * Returns the data stored in this node.
     * 
     * @return The data of this node
     */
    public T getData() {
        return this.data;
    }
    
    /**
     * Returns the next node in the list.
     * 
     * @return The next node, or null if this is the last node
     */
    public Node<T> getNext() {
        return this.next;
    }
}

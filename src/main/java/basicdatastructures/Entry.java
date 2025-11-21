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
 * Represents a key-value entry in a hash map with chaining for collision handling.
 * Each entry contains a key, a value, and a reference to the next entry in the chain.
 * 
 * @param <K> The type of the key
 * @param <V> The type of the value
 */
public class Entry<K,V> {
    public K key;
    public V value;
    public Entry<K,V> next;
    
    /**
     * Constructor that creates an entry with the specified key and value.
     * The next reference is initialized to null.
     * 
     * @param key The key for this entry
     * @param value The value associated with the key
     */
    public Entry(K key, V value) {
        this.key = key;
        this.value = value;
        this.next = null;
    }
}

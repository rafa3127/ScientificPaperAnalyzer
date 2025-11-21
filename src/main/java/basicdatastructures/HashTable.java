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
 * Represents a hash table with generic key-value pairs implemented using separate chaining.
 * Uses an array of linked lists (buckets) to handle collisions.
 * Automatically resizes when load factor exceeds 0.75.
 * 
 * @param <K> The type of keys maintained by this table
 * @param <V> The type of mapped values
 */
public class HashTable<K,V> {
    private int currentCapacity = 16;
    private Entry<K,V>[] buckets;
    private int elementCount;
   
    /**
     * Constructor that initializes an empty hash table with default capacity.
     */
    public HashTable() {
        this.buckets = (Entry<K,V>[]) new Entry[currentCapacity];
        this.elementCount = 0;
    }
    
    /**
     * Calculates the bucket index for a given key using the current capacity.
     * 
     * @param key The key to hash
     * @return The bucket index where the key should be stored
     */
    private int hash(K key) {
        return hash(key, currentCapacity);
    }
    
    /**
     * Calculates the bucket index for a given key using a specific capacity.
     * Uses the FNV-1a (Fowler-Noll-Vo) hash algorithm, which provides excellent
     * distribution and avalanche effect properties.
     * 
     * The FNV-1a algorithm:
     * 1. Starts with FNV offset basis (2166136261 for 32-bit)
     * 2. For each byte: XOR with byte, then multiply by FNV prime (16777619)
     * 3. This creates good avalanche effect where small input changes cause large output changes
     * 
     * @param key The key to hash
     * @param capacity The capacity to use for calculating the index
     * @return The bucket index where the key should be stored (range: 0 to capacity-1)
     */
    private int hash(K key, int capacity) {
        if (key == null) {
            return 0;
        }
        
        String keyStr = key.toString();
        
        // FNV-1a algorithm
        int hash = 0x811c9dc5; // FNV offset basis (2166136261 in decimal)
        int fnvPrime = 0x01000193; // FNV prime (16777619 in decimal)
        
        for (int i = 0; i < keyStr.length(); i++) {
            hash ^= keyStr.charAt(i); // XOR with character
            hash *= fnvPrime;          // Multiply by FNV prime
        }
        
        // Ensure positive value and map to capacity range
        hash = Math.abs(hash);
        return hash % capacity;
    }

    /**
     * Doubles the capacity of the hash table and rehashes all existing entries.
     * Called automatically when the load factor exceeds 0.75.
     * All entries are redistributed across the new bucket array.
     */
    private void requestMemory() {
        int newCapacity = currentCapacity * 2;
        Entry<K,V>[] newBuckets = (Entry<K,V>[]) new Entry[newCapacity];
    
        for(int i = 0; i < this.buckets.length; i++) {
            Entry<K,V> currentEntry = this.buckets[i];
            while (currentEntry != null) {
                K key = currentEntry.key;
                V value = currentEntry.value;
                int newHash = this.hash(key, newCapacity);
                if (newBuckets[newHash] == null) {
                    newBuckets[newHash] = new Entry<>(key, value);
                } else {
                    Entry<K,V> placeToSetEntry = newBuckets[newHash];
                    while(placeToSetEntry.next != null) {
                        placeToSetEntry = placeToSetEntry.next;
                    }
                    placeToSetEntry.next = new Entry<>(key, value);
                }
                currentEntry = currentEntry.next;
            }
        }
        this.buckets = newBuckets;
        this.currentCapacity = newCapacity;
    }
    
    /**
     * Associates the specified value with the specified key in this table.
     * If the table previously contained a mapping for the key, the old value is replaced.
     * Automatically triggers resize if load factor exceeds 0.75.
     * 
     * @param key The key with which the specified value is to be associated
     * @param value The value to be associated with the specified key
     */
    public void put(K key, V value) {
        if (elementCount >= buckets.length * 0.75) {
            requestMemory();
        }
        int newHash = hash(key);
        if (this.buckets[newHash] == null){
            this.buckets[newHash] = new Entry<>(key, value);
            this.elementCount++;
        } else {
            Entry<K,V> current = this.buckets[newHash];
            while ( current != null ){
                if(current.key.equals(key)) {
                    current.value = value;
                    return;
                }
                if (current.next == null){
                    current.next = new Entry<>(key, value);
                    this.elementCount++;
                    return;
                }
                current = current.next;
            }
        }
    }

    /**
     * Returns the value to which the specified key is mapped.
     * Returns null if this table contains no mapping for the key.
     * 
     * @param key The key whose associated value is to be returned
     * @return The value to which the specified key is mapped, or null if the key is not found
     */
    public V get(K key) {
        int hashToFind = this.hash(key);
        Entry<K, V> current =this.buckets[hashToFind];
        if ( current == null ) {
            return null;
        }
        
        while ( current != null ){
            if(current.key.equals(key)) {
                return current.value;
            }
            current = current.next;
        }
        return null;
        
    }
    
    /**
     * Removes the mapping for the specified key from this table if present.
     * If the key is found, decrements the element count.
     * Does nothing if the key is not found in the table.
     * 
     * @param key The key whose mapping is to be removed from the table
     */
    public void remove(K key) {
        int hashToFind = this.hash(key);
        Entry<K, V> current =this.buckets[hashToFind];
        Entry <K, V> prev = null;
        
        while ( current != null ){
            if(current.key.equals(key)) {
                if ( prev != null ){
                    prev.next = current.next;
                    this.elementCount--;
                    return;
                } else {
                    this.buckets[hashToFind] = current.next;
                    this.elementCount--;
                    return;
                }
            }
            prev = current;
            current = current.next;
        }
        
    }
    
    /**
     * Returns true if this table contains a mapping for the specified key.
     * 
     * @param key The key whose presence in this table is to be tested
     * @return true if this table contains a mapping for the specified key, false otherwise
     */
    public boolean containsKey(K key) {
        int hashToFind = this.hash(key);
        Entry<K,V> current = this.buckets[hashToFind];
        
        while (current != null) {
            if (current.key.equals(key)) {
                return true;
            }
            current = current.next;
        }
        return false;
    }
    
    /**
     * Returns the number of key-value mappings in this table.
     * 
     * @return The number of key-value mappings in this table
     */
    public int size() {
        return this.elementCount;
    }
    
    /**
     * Returns true if this table contains no key-value mappings.
     * 
     * @return true if this table contains no key-value mappings, false otherwise
     */
    public boolean isEmpty() {
        return this.elementCount == 0;
    }
    
    /**
     * Returns a list of all keys in this table.
     * 
     * @return A LinkedList containing all keys present in the table
     */
    public LinkedList<K> getKeys() {
        LinkedList<K> keys = new LinkedList<>();
        
        for (int i = 0; i < buckets.length; i++) {
            Entry<K,V> current = buckets[i];
            while (current != null) {
                keys.add(current.key);
                current = current.next;
            }
        }
        
        return keys;
    }
}

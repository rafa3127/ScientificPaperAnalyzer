/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package basicdatastructures;


/**
 * Represents a node in an AVL tree.
 * Each node stores a key-value pair, references to its children,
 * height for efficient balance calculations, and balance factor for quick imbalance detection.
 * 
 * Storing both height and balance factor provides:
 * - O(1) height queries (no recursive calculation needed)
 * - O(1) balance factor access for rotation decisions
 * - Efficient updates after rotations
 * 
 * Balance Factor (BF) = height(left subtree) - height(right subtree)
 * Valid balance factors are: -1, 0, 1
 * If |BF| > 1, the tree requires rebalancing through rotations.
 * 
 * @param <K> The type of the key (must be Comparable for ordering)
 * @param <V> The type of the value stored in the node
 */
public class AVLTreeNode<K extends Comparable<K>, V> {
    K key;
    V value;
    AVLTreeNode<K, V> leftChild;
    AVLTreeNode<K, V> rightChild;
    int height;
    int balanceFactor;
    
    /**
     * Creates a new AVL tree node with the given key and value.
     * Initial height is 0 (leaf node).
     * Initial balance factor is 0 (balanced).
     * Both children are initialized to null.
     * 
     * @param key The key for this node
     * @param value The value associated with the key
     */
    public AVLTreeNode(K key, V value) {
        this.key = key;
        this.value = value;
        this.leftChild = null;
        this.rightChild = null;
        this.height = 0;
        this.balanceFactor = 0;
    }
    
    /**
     * Returns the key stored in this node.
     * 
     * @return The key of this node
     */
    public K getKey() {
        return key;
    }
    
    /**
     * Returns the value stored in this node.
     * 
     * @return The value of this node
     */
    public V getValue() {
        return value;
    }
    
    /**
     * Sets the value for this node.
     * Used when updating an existing key.
     * 
     * @param value The new value to store
     */
    public void setValue(V value) {
        this.value = value;
    }
    
    /**
     * Returns the left child of this node.
     * 
     * @return The left child, or null if no left child exists
     */
    public AVLTreeNode<K, V> getLeftChild() {
        return leftChild;
    }
    
    /**
     * Returns the right child of this node.
     * 
     * @return The right child, or null if no right child exists
     */
    public AVLTreeNode<K, V> getRightChild() {
        return rightChild;
    }
    
    /**
     * Returns the height of this node.
     * Height is defined as the longest path from this node to a leaf.
     * Leaf nodes have height 0.
     * 
     * @return The height of this node
     */
    public int getHeight() {
        return height;
    }
    
    /**
     * Returns the balance factor of this node.
     * Balance factor indicates the height difference between left and right subtrees.
     * 
     * BF = height(left) - height(right)
     * - BF = -1: right subtree is 1 level taller
     * - BF = 0: balanced (same height)
     * - BF = 1: left subtree is 1 level taller
     * - |BF| > 1: requires rebalancing
     * 
     * @return The balance factor of this node
     */
    public int getBalanceFactor() {
        return balanceFactor;
    }
}

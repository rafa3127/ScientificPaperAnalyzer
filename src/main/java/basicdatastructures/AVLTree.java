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
 * Implementation of a self-balancing AVL tree.
 * An AVL tree maintains balance by ensuring that for every node,
 * the heights of its left and right subtrees differ by at most 1.
 * 
 * This implementation uses balance factors stored in each node to
 * detect imbalance and perform rotations when necessary.
 * 
 * Time complexities:
 * - Insert: O(log n)
 * - Search: O(log n)
 * - Delete: O(log n)
 * - Inorder traversal: O(n)
 * 
 * @param <K> The type of keys (must be Comparable)
 * @param <V> The type of values
 */
public class AVLTree<K extends Comparable<K>, V> {
    private AVLTreeNode<K, V> root;
    private int size;
    
    /**
     * Creates an empty AVL tree.
     */
    public AVLTree() {
        this.root = null;
        this.size = 0;
    }

    /**
     * Returns the number of key-value pairs in this tree.
     * 
     * @return The size of the tree
     */
    public int size() {
        return size;
    }
    
    /**
     * Returns true if this tree contains no elements.
     * 
     * @return true if the tree is empty, false otherwise
     */
    public boolean isEmpty() {
        return root == null;
    }
    
    /**
     * Returns the height of a node in O(1) time.
     * Height is stored in the node, no recursive calculation needed.
     * 
     * Convention:
     * - null nodes have height -1
     * - leaf nodes have height 0
     * 
     * @param node The node to get height from
     * @return The height of the node, or -1 if node is null
     */
    private int getHeight(AVLTreeNode<K, V> node) {
        if (node == null) {
            return -1;
        }
        return node.height;
    }
    
    /**
     * Returns the height of the entire tree.
     * This is a convenience method that gets the height from the root.
     * An empty tree has height -1.
     * 
     * @return The height of the tree
     */
    public int getHeight() {
        return getHeight(root);
    }
    
    /**
     * Updates the height of a node based on its children's heights.
     * Formula: height = 1 + max(height(left), height(right))
     * 
     * Time complexity: O(1)
     * 
     * @param node The node to update
     */
    private void updateHeight(AVLTreeNode<K, V> node) {
        if (node == null) {
            return;
        }
        node.height = 1 + Math.max(getHeight(node.leftChild), getHeight(node.rightChild));
    }
    
    /**
     * Updates the balance factor of a node based on its children's heights.
     * Formula: balanceFactor = height(left) - height(right)
     * 
     * Time complexity: O(1)
     * 
     * @param node The node to update
     */
    private void updateBalanceFactor(AVLTreeNode<K, V> node) {
        if (node == null) {
            return;
        }
        node.balanceFactor = getHeight(node.leftChild) - getHeight(node.rightChild);
    }
    
    /**
     * Updates both height and balance factor of a node.
     * Should be called after any structural change (insertion, rotation, deletion).
     * 
     * Time complexity: O(1)
     * 
     * @param node The node to update
     */
    private void updateNode(AVLTreeNode<K, V> node) {
        if (node == null) {
            return;
        }
        updateHeight(node);
        updateBalanceFactor(node);
    }

    /**
     * Performs a right rotation on the given node.
     * Used for Left-Left (LL) case when left subtree of left child causes imbalance.
     * 
     * Visual representation:
     *     z (BF=2)         y
     *    / \              / \
     *   y   T4    =>     x   z
     *  / \                  / \
     * x   T3              T3  T4
     * 
     * Time complexity: O(1)
     * 
     * @param z The node to rotate right
     * @return The new root after rotation (y becomes new root)
     */
    private AVLTreeNode<K, V> rotateRight(AVLTreeNode<K, V> z) {
        AVLTreeNode<K, V> y = z.leftChild;
        AVLTreeNode<K, V> T3 = y.rightChild;

        // Perform rotation
        y.rightChild = z;
        z.leftChild = T3;

        // Update heights and balance factors (order matters: z first, then y)
        updateNode(z);
        updateNode(y);

        return y; // New root of subtree
    }

    /**
     * Performs a left rotation on the given node.
     * Used for Right-Right (RR) case when right subtree of right child causes imbalance.
     * 
     * Visual representation:
     *   z (BF=-2)          y
     *  / \                / \
     * T1  y       =>     z   x
     *    / \            / \
     *   T2  x          T1  T2
     * 
     * Time complexity: O(1)
     * 
     * @param z The node to rotate left
     * @return The new root after rotation (y becomes new root)
     */
    private AVLTreeNode<K, V> rotateLeft(AVLTreeNode<K, V> z) {
        AVLTreeNode<K, V> y = z.rightChild;
        AVLTreeNode<K, V> T2 = y.leftChild;

        // Perform rotation
        y.leftChild = z;
        z.rightChild = T2;

        // Update heights and balance factors (order matters: z first, then y)
        updateNode(z);
        updateNode(y);

        return y; // New root of subtree
    }

    /**
     * Performs a left-right double rotation.
     * Used for Left-Right (LR) case when right subtree of left child causes imbalance.
     * This is a composition of two single rotations:
     * 1. Rotate left child to the left
     * 2. Rotate the node to the right
     * 
     * Visual representation:
     *     z (BF=2)       z           x
     *    / \            / \         / \
     *   y   T4   =>    x   T4  =>  y   z
     *  / \            / \             / \
     * T1  x          y   T3         T1 T2 T3 T4
     *    / \        / \
     *   T2 T3      T1 T2
     * 
     * Time complexity: O(1)
     * 
     * @param node The node to perform double rotation on
     * @return The new root after double rotation
     */
    private AVLTreeNode<K, V> rotateLeftRight(AVLTreeNode<K, V> node) {
        node.leftChild = rotateLeft(node.leftChild);  // First: left rotation on left child
        return rotateRight(node);                      // Second: right rotation on node
    }

    /**
     * Performs a right-left double rotation.
     * Used for Right-Left (RL) case when left subtree of right child causes imbalance.
     * This is a composition of two single rotations:
     * 1. Rotate right child to the right
     * 2. Rotate the node to the left
     * 
     * Visual representation:
     *   z (BF=-2)      z              x
     *  / \            / \            / \
     * T1  y     =>   T1  x    =>    z   y
     *    / \            / \         / \
     *   x  T4          T2  y       T1 T2 T3 T4
     *  / \                / \
     * T2 T3              T3 T4
     * 
     * Time complexity: O(1)
     * 
     * @param node The node to perform double rotation on
     * @return The new root after double rotation
     */
    private AVLTreeNode<K, V> rotateRightLeft(AVLTreeNode<K, V> node) {
        node.rightChild = rotateRight(node.rightChild); // First: right rotation on right child
        return rotateLeft(node);                         // Second: left rotation on node
    }
    
    /**
     * Inserts a key-value pair into the AVL tree.
     * If the key already exists, updates its value.
     * After insertion, the tree is automatically rebalanced if necessary.
     * 
     * Time complexity: O(log n)
     * 
     * @param key The key to insert
     * @param value The value associated with the key
     */
    public void insert(K key, V value) {
        root = insert(root, key, value);
    }

    /**
     * Recursive helper method to insert a key-value pair into the AVL tree.
     * Handles insertion and automatic rebalancing through rotations.
     * 
     * The algorithm follows these steps:
     * 1. Standard BST insertion (recursive)
     * 2. Update height and balance factor of current node
     * 3. Check balance factor to detect imbalance
     * 4. Apply appropriate rotation if needed (4 cases: LL, RR, LR, RL)
     * 
     * Time complexity: O(log n)
     * 
     * @param node The current node in the recursion
     * @param key The key to insert
     * @param value The value associated with the key
     * @return The root of the subtree after insertion and balancing
     */
    private AVLTreeNode<K, V> insert(AVLTreeNode<K, V> node, K key, V value) {
        // 1. Standard BST insertion
        if (node == null) {
            size++;
            return new AVLTreeNode<>(key, value);
        }

        int comparison = key.compareTo(node.key);

        if (comparison < 0) {
            // Key is smaller, go to left subtree
            node.leftChild = insert(node.leftChild, key, value);
        } else if (comparison > 0) {
            // Key is larger, go to right subtree
            node.rightChild = insert(node.rightChild, key, value);
        } else {
            // Key already exists, update value
            node.value = value;
            return node;
        }

        // 2. Update height and balance factor of current node
        updateNode(node);

        // 3. Get balance factor to check if node became unbalanced
        int balance = node.balanceFactor;

        // 4. If node is unbalanced, there are 4 cases to handle

        // Left-Left Case (LL)
        // Tree is left-heavy and insertion was in left subtree of left child
        if (balance > 1 && key.compareTo(node.leftChild.key) < 0) {
            return rotateRight(node);
        }

        // Right-Right Case (RR)
        // Tree is right-heavy and insertion was in right subtree of right child
        if (balance < -1 && key.compareTo(node.rightChild.key) > 0) {
            return rotateLeft(node);
        }

        // Left-Right Case (LR)
        // Tree is left-heavy and insertion was in right subtree of left child
        if (balance > 1 && key.compareTo(node.leftChild.key) > 0) {
            return rotateLeftRight(node);
        }

        // Right-Left Case (RL)
        // Tree is right-heavy and insertion was in left subtree of right child
        if (balance < -1 && key.compareTo(node.rightChild.key) < 0) {
            return rotateRightLeft(node);
        }

        // Node is balanced, no rotation needed
        return node;
    }
    
    /**
     * Searches for a value associated with the given key.
     * 
     * Time complexity: O(log n)
     * 
     * @param key The key to search for
     * @return The value associated with the key, or null if not found
     */
    public V search(K key) {
        return search(root, key);
    }

    /**
     * Recursive helper method to search for a key in the tree.
     * Uses standard BST search algorithm.
     * 
     * Time complexity: O(log n)
     * 
     * @param node The current node in the recursion
     * @param key The key to search for
     * @return The value associated with the key, or null if not found
     */
    private V search(AVLTreeNode<K, V> node, K key) {
        if (node == null) {
            return null; // Key not found
        }

        int comparison = key.compareTo(node.key);

        if (comparison < 0) {
            // Key is smaller, search in left subtree
            return search(node.leftChild, key);
        } else if (comparison > 0) {
            // Key is larger, search in right subtree
            return search(node.rightChild, key);
        } else {
            // Key found
            return node.value;
        }
    }
    
    /**
     * Checks if the tree contains the given key.
     * 
     * Time complexity: O(log n)
     * 
     * @param key The key to check for
     * @return true if the key exists in the tree, false otherwise
     */
    public boolean contains(K key) {
        return search(key) != null;
    }
    
    /**
     * Returns a list of all keys in the tree in sorted order (inorder traversal).
     * This is useful for displaying words or authors alphabetically.
     * 
     * Time complexity: O(n)
     * 
     * @return LinkedList containing all keys in ascending order
     */
    public LinkedList<K> inorderTraversal() {
        LinkedList<K> result = new LinkedList<>();
        inorderTraversal(root, result);
        return result;
    }

    /**
     * Recursive helper method for inorder traversal.
     * Visits nodes in order: left subtree -> current node -> right subtree
     * This produces keys in ascending (alphabetical) order.
     * 
     * Time complexity: O(n)
     * 
     * @param node The current node in the recursion
     * @param result The list to store the keys in order
     */
    private void inorderTraversal(AVLTreeNode<K, V> node, LinkedList<K> result) {
        if (node == null) {
            return;
        }

        // 1. Visit left subtree (smaller keys)
        inorderTraversal(node.leftChild, result);

        // 2. Visit current node
        result.add(node.key);

        // 3. Visit right subtree (larger keys)
        inorderTraversal(node.rightChild, result);
    }
    
    /**
     * Returns a list of all key-value pairs in the tree in sorted order by key.
     * Useful when you need both the key and its associated value.
     * 
     * Time complexity: O(n)
     * 
     * @return LinkedList containing Entry objects with key-value pairs in ascending order
     */
    public LinkedList<Entry<K, V>> getAllEntries() {
        LinkedList<Entry<K, V>> result = new LinkedList<>();
        getAllEntries(root, result);
        return result;
    }

    /**
     * Recursive helper method to collect all key-value pairs.
     * 
     * @param node The current node in the recursion
     * @param result The list to store the entries
     */
    private void getAllEntries(AVLTreeNode<K, V> node, LinkedList<Entry<K, V>> result) {
        if (node == null) {
            return;
        }

        getAllEntries(node.leftChild, result);
        result.add(new Entry<>(node.key, node.value));
        getAllEntries(node.rightChild, result);
    }
    
    /**
     * Removes all elements from the tree.
     * After this operation, the tree will be empty.
     * 
     * Time complexity: O(1)
     */
    public void clear() {
        root = null;
        size = 0;
    }
    
    /**
     * Simple class to hold key-value pairs for returning entries.
     */
    public static class Entry<K, V> {
        public K key;
        public V value;

        public Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }
    }
}

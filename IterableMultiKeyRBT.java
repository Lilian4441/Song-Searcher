// --== CS400 Fall 2023 File Header Information ==--
// Name: Lilian Huang
// Email: llhuang@wisc.edu
// Group: C06
// TA: Binwei Yao
// Lecturer: Florian
// Notes to Grader: <optional extra notes>

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Stack;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

/**
 * Implements RBT iterator that goes through an RBT tree
 *
 * @param <T> extends and implements classes with type T
 */
public class IterableMultiKeyRBT<T extends Comparable<T>> extends RedBlackTree<KeyListInterface<T>> implements IterableMultiKeySortedCollectionInterface<T> {
    protected Comparable<T> iterationStartPoint; // the iteration start point
    protected int numKeysInTree; // counter for total number of key values in the tree

    /**
     * Inserts value into tree that can store multiple objects per key by keeping
     * lists of objects in each node of the tree.
     *
     * @param key object to insert
     * @return true if a new node was inserted, false if the key was added into an existing node
     */
    @Override
    public boolean insertSingleKey(T key) {
        // create a new KeyList with the given key
        KeyList<T> keyList = new KeyList<>(key);

        // finds the node containing the key list (if any)
        Node<KeyListInterface<T>> containsList = findNode(keyList);

        // if no existing node found, insert a new node with the key list
        if (containsList == null) {
            insert(keyList); // insert the key list into a new node
            numKeysInTree++; // increment the number of keys in the tree
            return true; // indicates succesful insertion of a new node
        } else {
            containsList.data.addKey(key); // add the key to the existing key list in the node
            numKeysInTree++; // increment the number of keys in the tree
            return false; // indicates insertion into an existing node
        }
    }

    /**
     * retrieves the total number of values in the tree
     *
     * @return the number of values in the tree.
     */
    @Override
    public int numKeys() {
        return this.numKeysInTree;
    }

    /**
     * Returns an iterator that does an in-order iteration over the tree.
     */
    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            // stack to maintain nodes during traversal
            Stack<Node<KeyListInterface<T>>> nodeStack = getStartStack();

            // iterator for the current key list
            Iterator<T> keyListIterator = nodeStack.isEmpty() ? null : nodeStack.peek().data.iterator();

            @Override
            public boolean hasNext() {
                // checks if there are more nodes to traverse
                return !nodeStack.empty();
            }


            @Override
            public T next() {
                if (!hasNext()) {
                    throw new NoSuchElementException("no more elements to iterate (key list is empty).");
                }

                Node<KeyListInterface<T>> currentNode = nodeStack.peek(); // saves value of node at top of stack
                T nextKey = keyListIterator.next(); // saves value of the next key in the keylist

                if (!keyListIterator.hasNext()) {
                    nodeStack.pop(); // pops the node if the key list is exhausted

                    // explores the right subtree (if it exists)
                    if (currentNode.down[1] != null) {
                        currentNode = currentNode.down[1]; // current node value set to it's right child
                        nodeStack.push(currentNode); // current node is pushed onto stack

                        // traverse down to the leftmost node in the right subtree
                        while (currentNode.down[0] != null) {
                            currentNode = currentNode.down[0]; // current node value set to it's left child
                            nodeStack.push(currentNode); // current node is pushed onto stack
                        }
                    }

                    // set iterator for the next node (if available)
                    if (this.hasNext()) {
                        keyListIterator = nodeStack.peek().data.iterator();
                    }
                }
                return nextKey; // returns the next key in the traversal
            }
        };
    }

    /**
     * returns a stack representing the starting point for tree traversal.
     * if iterationStartPoint is provided, the stack contains nodes starting from the closest node to
     * the specified point. otherwise, it contains nodes for an in-order traversal.
     *
     * @return a stack representing the starting point for tree traversal
     */
    private Stack<Node<KeyListInterface<T>>> getStartStack() {
        Stack<Node<KeyListInterface<T>>> stack = new Stack<>();
        Node<KeyListInterface<T>> node = root;

        // if iterationStartPoint is null, perform an in-order traversal
        if (iterationStartPoint == null) {
            while (node != null) {
                stack.push(node);
                node = node.down[0];
            }
            return stack;
        } else {
            // traverse the tree based on iterationStartPoint
            while (node != null) {
                int cmp = iterationStartPoint.compareTo(node.data.iterator().next());

                if (cmp <= 0) {
                    stack.push(node);
                    node = node.down[0];
                } else {
                    node = node.down[1];
                }
            }
        }
        return stack;
    }

    /**
     * Sets the starting point for iterations. Future iterations will start at the
     * starting point or the key closest to it in the tree. This setting is remembered
     * until it is reset. Passing in null disables the starting point.
     *
     * @param startPoint the start point to set for iterations
     */
    @Override
    public void setIterationStartPoint(Comparable<T> startPoint) {
        // set the iteration start point based on the given parameter
        this.iterationStartPoint = startPoint;
    }

    /**
     * clears all the contents of the KeyList and resets the count of keys in the tree.
     */
    @Override
    public void clear() {
        super.clear(); // clear the contents using the superclass method
        this.numKeysInTree = 0; // reset the count of keys in the tree to zero
    }

    /**
     * Test case to verify the insertion of a single key into the IterableMultiKeyRBT.
     * Scenario 1: Inserts the first key into an empty list.
     * 1. Creates an IterableMultiKeyRBT object.
     * 2. Inserts the first key into an empty list.
     * 3. Asserts that the insertion operation returns true, indicating successful insertion.
     * Scenario 2: Inserts a single key into a node with a non-empty list.
     * 1. Attempts to insert the same key into a node that already contains the key.
     * 2. Asserts that the insertion operation returns false, indicating that the key was inserted into an existing node.
     */
    @Test
    public void testInsertSingleKey() {
        // Creates an IterableMultiKeyRBT object
        IterableMultiKeyRBT<Integer> tree1 = new IterableMultiKeyRBT<>();

        // Inserts the first key into an empty list
        Assertions.assertTrue(tree1.insertSingleKey(1)); // Expects true (new node is created)

        // Inserts a single key into a node with a non-empty list
        Assertions.assertFalse(tree1.insertSingleKey(1)); // Expects false (key is inserted into an existing node)
    }

    /**
     * Test case to verify the correctness of the numKeys() method in the IterableMultiKeyRBT class.
     * Scenario: Inserts multiple keys into an IterableMultiKeyRBT object and checks the number of keys.
     * 1. Creates an IterableMultiKeyRBT object.
     * 2. Inserts keys (1, 2, and 3) into the tree.
     * 3. Retrieves the actual number of keys using the numKeys() method.
     * 4. Sets the expected number of keys to 5.
     * 5. Asserts that the actual number of keys matches the expected number of keys (5).
     */
    @Test
    public void testNumKeys() {
        // Creates an IterableMultiKeyRBT object
        IterableMultiKeyRBT<Integer> tree2 = new IterableMultiKeyRBT<>();

        // Inserts keys into the tree
        tree2.insertSingleKey(1);
        tree2.insertSingleKey(1); // Inserts a duplicate of the previous key
        tree2.insertSingleKey(2);
        tree2.insertSingleKey(3);
        tree2.insertSingleKey(3); // Inserts a duplicate of the previous key

        int expectedValue = 5; // Expected return value of numKeys

        int actualValue = tree2.numKeys(); // Actual return value of numKeys

        Assertions.assertEquals(expectedValue, actualValue); // Returns false if not equal
    }

    /**
     * Test case to verify the functionality of the iterator with a specified start point.
     * 1. Creates an IterableMultiKeyRBT object.
     * 2. Inserts keys (2, 34, and 41) into the tree.
     * 3. Sets the iteration start point as 34 and checks if the iterator starts from the correct point.
     * 4. Sets the iteration start point as 41 and checks if the iterator starts from the correct point.
     * 5. Resets the iteration start point (null) and checks if the iterator starts from the default point.
     */
    @Test
    public void testIteratorWithStartPoint() {
        // Creates an IterableMultiKeyRBT object
        IterableMultiKeyRBT<Integer> tree = new IterableMultiKeyRBT<>();

        // Inserts keys into the tree
        tree.insertSingleKey(2);
        tree.insertSingleKey(34);
        tree.insertSingleKey(41);

        // Sets the iteration start point as 34 and checks if the iterator starts from the correct point
        tree.setIterationStartPoint(34);
        Assertions.assertEquals(34, tree.iterator().next());

        // Sets the iteration start point as 41 and checks if the iterator starts from the correct point
        tree.setIterationStartPoint(41);
        Assertions.assertEquals(41, tree.iterator().next());

        // Resets the iteration start point (null) and checks if the iterator starts from the default point
        tree.setIterationStartPoint(null);
        Assertions.assertEquals(2, tree.iterator().next());
    }
}



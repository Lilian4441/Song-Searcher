// --== CS400 Fall 2023 File Header Information ==--
// Name: Lilian Huang
// Email: llhuang@wisc.edu
// Group: C06
// TA: Binwei Yao
// Lecturer: Florian Heimerl
// Notes to Grader: <optional extra notes>
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

/**
 * this class represents a Red-Black Tree data structure, an extension of Binary Search Tree
 * that maintains balance and ensures that the RBT stays valid after insertion by enforcing certain properties.
 */
public class RedBlackTree<T extends Comparable<T>> extends BinarySearchTree<T> {

    /**
     * RBTNode is a protected static nested class representing a node in a Red-Black Tree.
     * each node contains data, parent, left child, right child, and black height information.
     */
    protected static class RBTNode<T> extends Node<T> {
        public int blackHeight = 0;

        public RBTNode(T data) {
            super(data);
        }

        public RBTNode<T> getUp() {
            return (RBTNode<T>) this.up;
        }

        public RBTNode<T> getDownLeft() {
            return (RBTNode<T>) this.down[0];
        }

        public RBTNode<T> getDownRight() {
            return (RBTNode<T>) this.down[1];
        }
    }

    /**
     * enforces the Red-Black Tree properties after inserting a new red node.
     * if the tree properties are violated, it performs necessary rotations and recoloring
     * to restore the Red-Black Tree properties.
     *
     * @param newRedNode the new red node that was inserted into the Red-Black Tree.
     * @throws AssertionError if the Red-Black Tree properties are not restored after the insertion.
     */
    protected void enforceRBTreePropertiesAfterInsert(RBTNode<T> newRedNode) {
        // base case 1: the parent is null, meaning new node is root
        if (newRedNode.getUp() == null){
            this.root = newRedNode; // sets new node as root
            newRedNode.blackHeight = 1; // sets root node color to black
            return;
        } else {
            // base case 2: parent is black, tree properties are not violated
            if (newRedNode.getUp().blackHeight == 1){
                return;
            }
        }

        RBTNode<T> parent = newRedNode.getUp();
        RBTNode<T> grandparent = newRedNode.getUp().getUp();
        RBTNode<T> aunt = null;

        // checks to see which child the aunt node is
        if (grandparent.getDownLeft() == null && grandparent.getDownRight().equals(parent)) {
            aunt = null;
        }
        else if (grandparent.getDownRight() == null && grandparent.getDownLeft().equals(parent)) {
            aunt = null;
        }
        else if (grandparent.getDownLeft() != null && grandparent.getDownRight().equals(parent)){
            aunt = grandparent.getDownLeft();
        }
        else if (grandparent.getDownRight() != null && grandparent.getDownLeft().equals(parent)){
            aunt = grandparent.getDownRight();
        }

        // recursive case 1: the parent and aunt are both red nodes, recolor them and check grandparent
        if (aunt != null && aunt.blackHeight == 0) {
            parent.blackHeight = 1; // re-colors parent to be black
            aunt.blackHeight = 1; // re-colors aunt to be black
            grandparent.blackHeight = 0; // re-colors grandparent to be red

            // fixes violation of property if grandparent's parent was red
            this.enforceRBTreePropertiesAfterInsert(grandparent);
        }

        // recursive case 2: parent is red and on same side as newRedNode, but aunt is null
        else if (aunt == null && newRedNode == parent.getDownRight() && parent == grandparent.getDownRight()) {
            this.rotate(parent, grandparent);

            // recoloring after rotations
            parent.blackHeight = 1;
            grandparent.blackHeight = 0;

            // fix violations after rotations
            this.enforceRBTreePropertiesAfterInsert(parent);
        } else if (aunt == null && newRedNode == parent.getDownLeft() && parent == grandparent.getDownLeft()) {
            this.rotate(parent, grandparent);

            // recoloring after rotations
            parent.blackHeight = 1;
            grandparent.blackHeight = 0;

            // fix violations after rotations
            this.enforceRBTreePropertiesAfterInsert(parent);
        }

        // recursive case 3: parent is red and on same side as newRedNode, but aunt is black
        else if (newRedNode == parent.getDownRight() && parent == grandparent.getDownRight()) {
            this.rotate(parent, grandparent);

            // recoloring after rotations
            parent.blackHeight = 1;
            grandparent.blackHeight = 0;

            // fix violations after rotations
            this.enforceRBTreePropertiesAfterInsert(parent);

        } else if (newRedNode == parent.getDownLeft() && parent == grandparent.getDownLeft()) {
            this.rotate(parent, grandparent);

            // recoloring after rotations
            parent.blackHeight = 1;
            grandparent.blackHeight = 0;

            // fix violations after rotations
            this.enforceRBTreePropertiesAfterInsert(parent);
        }

        // recursive case 4: parent is red and on different side as newRedNode, but aunt is null
        else if (aunt == null && newRedNode == parent.getDownRight() && parent == grandparent.getDownLeft()){ // parent: left, new node: right
            // stores node to fix after rotating
            RBTNode<T> originalRedParent = newRedNode.getUp();

            // rotate child and parent
            this.rotate(newRedNode, parent);

            // fix violations after rotations
            this.enforceRBTreePropertiesAfterInsert(originalRedParent);
        } else if (aunt == null && newRedNode == parent.getDownLeft() && parent == grandparent.getDownRight()){ // parent: right, new node: left
            // stores node to fix after rotating
            RBTNode<T> originalRedParent = newRedNode.getUp();

            // rotate child and parent
            this.rotate(newRedNode, parent);

            // fix violations after rotations
            this.enforceRBTreePropertiesAfterInsert(originalRedParent);
        }

        // recursive case 5: parent is red and on different side as newRedNode, but aunt is black
        else {
            if (newRedNode == parent.getDownRight() && parent == grandparent.getDownLeft()){ // parent: left, new node: right
                // stores node to fix after rotating
                RBTNode<T> originalRedParent = newRedNode.getUp();

                // rotate child and parent
                this.rotate(newRedNode, parent);

                // fix violations after rotations
                this.enforceRBTreePropertiesAfterInsert(originalRedParent);

            } else if (newRedNode == parent.getDownLeft() && parent == grandparent.getDownRight()) { // parent: right, new node: left
                // stores node to fix after rotating
                RBTNode<T> originalRedParent = newRedNode.getUp();

                // rotate child and parent
                this.rotate(newRedNode, parent);

                // fix violations after rotations
                this.enforceRBTreePropertiesAfterInsert(originalRedParent);
            }
        }
    }

    /**
     * inserts a new node with the specified data into the Red-Black Tree.
     * overrides the insert method in the BinaryTree class.
     *
     * @param data the data to be inserted into the red black tree
     * @return true if the insertion is successful, false if not
     * @throws NullPointerException if provided data is null, an exception is thrown
     * @throws AssertionError if the Red-Black Tree properties are not maintained after insertion.
     *                               this method ensures that the Red-Black Tree properties are enforced
     *                               by calling the enforceRBTreePropertiesAfterInsert method.
     */
    @Override
    public boolean insert(T data) throws NullPointerException {
        // instantiates a new RBT node
        RBTNode<T> newNode = new RBTNode<>(data);

        // checks if data is valid
        if (data == null) {
            throw new NullPointerException("Cannot insert data value null into the tree.");
        }

        // inserts new node into red-black tree
        if (this.insertHelper(newNode)) {
            newNode.blackHeight = 0; // new node is red
            this.enforceRBTreePropertiesAfterInsert(newNode);

            // set root node color to black
            RBTNode<T> rbtRoot = (RBTNode<T>) this.root;
            rbtRoot.blackHeight = 1;

            return true;
        }

        return false; // insertion failed
    }

    /**
     * junit test method to verify the behavior of the Red-Black Tree when both the parent and aunt nodes are red.
     * it constructs a specific Red-Black Tree configuration and then inserts a new node into the tree,
     * ensuring that the tree properties are maintained after insertion. the test checks the level order
     * traversal, size, and blackHeight of nodes to confirm the correctness of the tree structure.
     *
     * the test case covers the scenario where both the parent and aunt nodes are red, and the necessary rotations
     * and recoloring operations are performed to restore the Red-Black Tree properties.
     *
     * @throws AssertionError if the expected level order does not match the actual level order, if the expected
     *                               blackHeights of any node do not match the actual blackHeights, or if the expected
     *                               size does not match the actual size
     */

    @Test
    public void testsParentAuntBothRed() {
        // creation of nodes to be inserted into the tree
        RBTNode<Integer> root = new RBTNode<>(7); // root
        RBTNode<Integer> node1 = new RBTNode<>(5); // root left child
        RBTNode<Integer> node2 = new RBTNode<>(9); // root right child
        RBTNode<Integer> node3 = new RBTNode<>(3); // node1's left child
        RBTNode<Integer> node4 = new RBTNode<>(6); // node1's right child
        RBTNode<Integer> node5 = new RBTNode<>(8); // node2's left child
        RBTNode<Integer> node6 = new RBTNode<>(10); // node2's right child
        RBTNode<Integer> node7 = new RBTNode<>(2); // node3's left child
        RBTNode<Integer> node8 = new RBTNode<>(4); // node3's right child

        RedBlackTree<Integer> tree = new RedBlackTree<>(); // a new rbt is created
        tree.root = root; // the tree's root is set as the root node (created above)

        // connections between nodes are made (tree construction)
        root.down[0] = node1; // setting connection from parent to child
        node1.up = root; // setting connection from child to parent
        root.down[1] = node2;
        node2.up = root;

        node2.down[0] = node5;
        node5.up = node2;
        node2.down[1] = node6;
        node6.up = node2;

        node1.down[0] = node3;
        node3.up = node1;
        node1.down[1] = node4;
        node4.up = node1;

        node3.down[0] = node7;
        node7.up = node3;
        node3.down[1] = node8;
        node8.up = node3;

        // blackHeight is recorded
        root.blackHeight = 1;
        node1.blackHeight = 0;
        node2.blackHeight = 1;
        node3.blackHeight = 1;
        node4.blackHeight = 1;
        node5.blackHeight = 0;
        node6.blackHeight = 0;
        node7.blackHeight = 0;
        node8.blackHeight = 0;

        tree.size = 9; // tree size before insertion of new node

        tree.insert(1); // inserting a new node into the tree as left child of node7

        // checks that the level order of the test tree after insertion is correct
        Assertions.assertEquals("[ 5, 3, 7, 2, 4, 6, 9, 1, 8, 10 ]", tree.toLevelOrderString());

        // checks that the size of the tree after insertion is correct
        Assertions.assertEquals(10, tree.size);

        // checks that the blackHeight of all nodes is correct
        RBTNode<Integer> rbtRoot = (RBTNode<Integer>) tree.root;
        Assertions.assertEquals(1, rbtRoot.blackHeight);
        Assertions.assertEquals(0, rbtRoot.getDownLeft().blackHeight);
        Assertions.assertEquals(0, rbtRoot.getDownRight().blackHeight);
        Assertions.assertEquals(1, rbtRoot.getDownLeft().getDownLeft().blackHeight);
        Assertions.assertEquals(1, rbtRoot.getDownLeft().getDownRight().blackHeight);
        Assertions.assertEquals(1, rbtRoot.getDownRight().getDownLeft().blackHeight);
        Assertions.assertEquals(1, rbtRoot.getDownRight().getDownRight().blackHeight);
        Assertions.assertEquals(0, rbtRoot.getDownLeft().getDownLeft().getDownLeft().blackHeight);
        Assertions.assertEquals(0, rbtRoot.getDownRight().getDownRight().getDownLeft().blackHeight);
        Assertions.assertEquals(0, rbtRoot.getDownRight().getDownRight().getDownRight().blackHeight);
    }


    /**
     * junit test method to verify the behavior of the Red-Black Tree when the aunt node is black,
     * and the parent node is red, both parent node and new red node being on the same side of the grandparent.
     * it constructs a specific Red-Black Tree configuration and then inserts a new node into the tree,
     * ensuring that the tree properties are maintained after insertion. the test checks the level order
     * traversal, size, and blackHeight of nodes to confirm the correctness of the tree structure.
     *
     * the test case covers the scenario where the aunt node is black, and rotations and recoloring operations are
     * performed to restore the Red-Black Tree properties after the insertion.
     *
     * @throws AssertionError if the expected level order does not match the actual level order, if the expected
     *                               blackHeights of any node do not match the actual blackHeights, or if the expected
     *                               size does not match the actual size
     */
    @Test
    public void testsBlackAuntRedParentSameSide() {
        // creation of nodes to be inserted into the tree
        RBTNode<Integer> root = new RBTNode<>(14); // root
        RBTNode<Integer> node1 = new RBTNode<>(7); // root's left child
        RBTNode<Integer> node2 = new RBTNode<>(20); // root's right child
        RBTNode<Integer> node3 = new RBTNode<>(1); // node1's left child
        RBTNode<Integer> node4 = new RBTNode<>(11); // node1's right child
        RBTNode<Integer> node5 = new RBTNode<>(18); // node2's left child
        RBTNode<Integer> node6 = new RBTNode<>(23); // node2's right child
        RBTNode<Integer> node7 = new RBTNode<>(29); // node6's right child

        RedBlackTree<Integer> tree = new RedBlackTree<>(); // a new rbt is created
        tree.root = root; // the tree's root is set as the root node (created above)

        root.down[0] = node1; // setting connection from parent to child
        root.down[0].up = root; // setting connection from child to parent
        root.down[1] = node2;
        root.down[1].up = node2;

        node1.down[0] = node3;
        node1.down[0].up = node1;
        node1.down[1] = node4;
        node1.down[1].up = node1;

        node2.down[0] = node5;
        node2.down[0].up = node2;
        node2.down[1] = node6;
        node2.down[1].up = node2;

        node6.down[1] = node7;
        node6.down[1].up = node6;

        // blackHeight is recorded
        root.blackHeight = 1;
        node1.blackHeight = 1;
        node2.blackHeight = 0;
        node3.blackHeight = 0;
        node4.blackHeight = 0;
        node5.blackHeight = 1;
        node6.blackHeight = 1;
        node7.blackHeight = 0;

        tree.size = 8; // tree size before insertion

        tree.insert(30); // inserting a new node into the tree as right child of node7

        // checks that the level order of the test tree after insertion is correct
        Assertions.assertEquals("[ 14, 7, 20, 1, 11, 18, 29, 23, 30 ]", tree.toLevelOrderString());

        // checks that the size of the tree after insertion is correct
        Assertions.assertEquals(9, tree.size);

        // checks that the blackHeight of all nodes is correct
        RBTNode<Integer> rbtRoot = (RBTNode<Integer>) tree.root;
        Assertions.assertEquals(1, rbtRoot.blackHeight);
        Assertions.assertEquals(1, rbtRoot.getDownLeft().blackHeight);
        Assertions.assertEquals(0, rbtRoot.getDownRight().blackHeight);
        Assertions.assertEquals(0, rbtRoot.getDownLeft().getDownLeft().blackHeight);
        Assertions.assertEquals(0, rbtRoot.getDownLeft().getDownRight().blackHeight);
        Assertions.assertEquals(1, rbtRoot.getDownRight().getDownLeft().blackHeight);
        Assertions.assertEquals(1, rbtRoot.getDownRight().getDownRight().blackHeight);
        Assertions.assertEquals(0, rbtRoot.getDownRight().getDownRight().getDownLeft().blackHeight);
        Assertions.assertEquals(0, rbtRoot.getDownRight().getDownRight().getDownRight().blackHeight);
    }

    /**
     * junit test method to validate the Red-Black Tree's behavior when the aunt node is black,
     * and the parent node is red, with the parent node and new red node being on different sides of the grandparent.
     * it constructs a specific Red-Black Tree configuration and inserts a new node into the tree,
     * ensuring that the tree properties are maintained after insertion. the test checks the level order
     * traversal, size, and blackHeight of nodes to confirm the correctness of the tree structure.
     *
     * this test case covers the scenario where the aunt node is black, and rotations and recoloring operations
     * are performed to restore the Red-Black Tree properties after the insertion, in cases where the parent and
     * new node are on opposite sides of the grandparent.
     *
     * @throws AssertionError if the expected level order does not match the actual level order, if the expected
     *                               blackHeights of any node do not match the actual blackHeights, or if the expected size
     *                               does not match the actual size
     */
    @Test
    public void testsBlackAuntRedParentDifferentSides() {
        // creation of nodes to be inserted into tree
        RBTNode<Integer> root = new RBTNode<>(6);
        RBTNode<Integer> node1 = new RBTNode<>(3);
        RBTNode<Integer> node2 = new RBTNode<>(7);
        RBTNode<Integer> node3 = new RBTNode<>(2);
        RBTNode<Integer> node4 = new RBTNode<>(5);
        RBTNode<Integer> node5 = new RBTNode<>(9);

        RedBlackTree<Integer> tree = new RedBlackTree<>(); // a new rbt is created
        tree.root = root; // the tree's root is set as the root node (created above)

        root.down[0] = node1; // setting connection from parent to child
        root.down[0].up = root; // setting connection from child to parent
        root.down[1] = node2;
        root.down[1].up = root;

        node1.down[0] = node3;
        node1.down[0].up = node1;
        node1.down[1] = node4;
        node1.down[1].up = node1;

        node2.down[1] = node5;
        node2.down[1].up = node2;

        // blackHeight is recorded
        root.blackHeight = 1;
        node1.blackHeight = 1;
        node2.blackHeight = 1;
        node3.blackHeight = 0;
        node4.blackHeight = 0;
        node5.blackHeight = 0;

        tree.size = 6; // tree's size before insertion

        tree.insert(8); // inserting a new node into the tree as left child of node5

        // checks that the level order of the test tree after insertion is correct
        Assertions.assertEquals("[ 6, 3, 8, 2, 5, 7, 9 ]", tree.toLevelOrderString());

        // checks that the size of the tree after insertion is correct
        Assertions.assertEquals(7, tree.size);

        // checks that the blackHeight of all nodes is correct
        RBTNode<Integer> rbtRoot = (RBTNode<Integer>) tree.root;
        Assertions.assertEquals(1, rbtRoot.blackHeight);
        Assertions.assertEquals(1, rbtRoot.getDownLeft().blackHeight);
        Assertions.assertEquals(1, rbtRoot.getDownRight().blackHeight);
        Assertions.assertEquals(0, rbtRoot.getDownLeft().getDownLeft().blackHeight);
        Assertions.assertEquals(0, rbtRoot.getDownLeft().getDownRight().blackHeight);
        Assertions.assertEquals(0, rbtRoot.getDownRight().getDownLeft().blackHeight);
        Assertions.assertEquals(0, rbtRoot.getDownRight().getDownRight().blackHeight);
    }
}

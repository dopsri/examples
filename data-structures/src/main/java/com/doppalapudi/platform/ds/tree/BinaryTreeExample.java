package com.doppalapudi.platform.ds.tree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Queue;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BinaryTreeExample {
	
	private static final <T> Function<TreeNode<T>, List<TreeNode<T>>> getLeftView() {
		return treeNode -> Stream.of(treeNode.left, treeNode.right).filter(Objects::nonNull).collect(Collectors.toList());
	}
	
	private static final <T> Function<TreeNode<T>, List<TreeNode<T>>> getRightView() {
		return treeNode -> Stream.of(treeNode.right, treeNode.left).filter(Objects::nonNull).collect(Collectors.toList());
	}
	
	public static void main(String[] args) throws Exception {
		
		
		Integer[] nodeValues = {1, 2, 3, 4, 5, null, 6, 7, 8, 9, null, null, null, null, null, 10};
		BinaryTree<Integer> binaryTree = createBinaryTree(nodeValues);
		printTree(binaryTree.root);
		
		
		
		BinarySearchTree<String> bst = new BinarySearchTree<>();
		String[] values = {"99", "77", "55", "33", "88", "66", "11", "22", "75", "85", "13", "56", "45"};
		Stream.of(values).forEach(value -> bst.add(value));
		
		printTree(bst.root);		
		
		bst.delete("99");
		
		printTree(bst.root);	
		
	}
	
	private static <T> void printTree(TreeNode<T> root) {
		traverseInOrder(root);
		System.out.println();
		traversePreOrder(root);
		System.out.println();
		traversePostOrder(root);
		System.out.println();		
		traverseLevelOrder(root);
		System.out.println();
		printView(Arrays.asList(root), getLeftView());
		System.out.println();
		printView(Arrays.asList(root), getRightView());
		System.out.println();	
		System.out.println();
	}
	
	private static <T> BinaryTree<T> createBinaryTree(T[] nodeValues) {
		BinaryTree<T> binaryTree = new BinaryTree<>();
		if(Objects.nonNull(nodeValues) && nodeValues.length > 0) {
			TreeNode<T> root = new TreeNode<T>(nodeValues[0]);
			binaryTree.root = root;
			createBinaryTree(Arrays.asList(root), 1, nodeValues);
		}		
		return binaryTree;		
	}

	private static <T> void createBinaryTree(List<TreeNode<T>> nodeList, int index, T[] nodeValues) {
		
		if(nodeList.isEmpty()) {
			return;
		}
		
		List<TreeNode<T>> newNodes = new ArrayList<TreeNode<T>>();
		
		for(TreeNode<T> treeNode : nodeList) {
			
			if(index != nodeValues.length) {
				TreeNode<T> left = getTreeNode(index++, nodeValues);
				if(left != null) {
					treeNode.left = left;
					newNodes.add(left);
				}
			}
			
			if(index != nodeValues.length) {
				TreeNode<T> right = getTreeNode(index++, nodeValues);
				if(right != null) {
					treeNode.right = right;
					newNodes.add(right);
				}
			}
		}
		
		createBinaryTree(newNodes, index, nodeValues);		
	}
	

	public static <T> TreeNode<T> getTreeNode(int index, T[] nodeValues) {
		T nextValue = nodeValues[index];
		if(nextValue != null) {
			return new TreeNode<T>(nextValue);
		}
		return null;		
	}
	
	
	
	public static <T> void printView(List<TreeNode<T>> nodeList, Function<TreeNode<T>, List<TreeNode<T>>> function) {
		if(nodeList.isEmpty()) {
			return;
		} 
		nodeList.stream().map(node -> " " + node.value).findFirst().ifPresent(System.out::print);	
		List<TreeNode<T>> childList = nodeList.stream()
				.map(function)
				.collect(Collectors.toList())
				.stream().flatMap(List::stream).collect(Collectors.toList());
		printView(childList, function);
	}
	
	
	
	
	public static void traverseInOrder(TreeNode<?> node) {
	    if (node != null) {
	        traverseInOrder(node.left);
	        System.out.print(" " + node.value);
	        traverseInOrder(node.right);
	    } 
	}
	
	public static void traversePreOrder(TreeNode<?> node) {
	    if (node != null) {
	        System.out.print(" " + node.value);
	        traversePreOrder(node.left);
	        traversePreOrder(node.right);
	    }
	}
	
	public static void traversePostOrder(TreeNode<?> node) {
	    if (node != null) {
	        traversePostOrder(node.left);
	        traversePostOrder(node.right);
	        System.out.print(" " + node.value);
	    }
	}
	
	public static <T> void  traverseLevelOrder(TreeNode<T> rootNode) {
	    if (rootNode == null) {
	        return;
	    }
	 
	    Queue<TreeNode<T>> nodes = new LinkedList<>();
	    nodes.add(rootNode);
	 
	    while (!nodes.isEmpty()) {
	 
	    	TreeNode<T> node = nodes.remove();
	 
	        System.out.print(" " + node.value);
	 
	        if (node.left != null) {
	            nodes.add(node.left);
	        }
	 
	        if (node.right!= null) {
	            nodes.add(node.right);
	        }
	    }
	}
	
	
	

}


class TreeNode<T> {	
	
	public T value;		
	public TreeNode<T> left;		
	public TreeNode<T> right;
	
	public TreeNode(T value) {
		this.value = value;
	}
}

class BinaryTree<T> {
	public TreeNode<T> root;
	
	public BinaryTree() {		
	}
	
	public BinaryTree(TreeNode<T> root) {
		this.root = root;
	}
}

class BinarySearchTree<T> extends BinaryTree<T> {
	
	public BinarySearchTree() {	
		super();
	}
	
	public BinarySearchTree(TreeNode<T> root) {	
		super(root);
	}
	
	private TreeNode<T> addRecursive(TreeNode<T> current, T value, Comparator<? super T> comparator) {
	    if (current == null) {
	        return new TreeNode<T>(value);
	    }
	 
	    if (Objects.compare(value, current.value, comparator) <= 0) {
	        current.left = addRecursive(current.left, value, comparator);
	    } else if (Objects.compare(value, current.value, comparator) > 0) {
	        current.right = addRecursive(current.right, value, comparator);
	    } 
	 
	    return current;
	}
	
	public void add(T value) {
	    root = addRecursive(root, value, (T a, T b) -> a.toString().compareTo(b.toString()));
	}
	
	public void delete(T value) {
        root = deleteRecursive(root, value, (T a, T b) -> a.toString().compareTo(b.toString()));
    }

    private TreeNode<T> deleteRecursive(TreeNode<T> current, T value, Comparator<? super T> comparator) {
        if (current == null) {
            return null;
        }

        if(Objects.compare(value, current.value, comparator) == 0) {
            // Case 1: no children
            if (current.left == null && current.right == null) {
                return null;
            }

            // Case 2: only 1 child
            if (current.right == null) {
                return current.left;
            }

            if (current.left == null) {
                return current.right;
            }

            // Case 3: 2 children
            T smallestValue = findSmallestValue(current.right);
            current.value = smallestValue;
            current.right = deleteRecursive(current.right, smallestValue, comparator);
            return current;
        }
        //if (value < current.value) {
        
        if(Objects.compare(value, current.value, comparator) < 0) {
            current.left = deleteRecursive(current.left, value, comparator);
            return current;
        }

        current.right = deleteRecursive(current.right, value, comparator);
        return current;
    }

    private T findSmallestValue(TreeNode<T> root) {
        return root.left == null ? root.value : findSmallestValue(root.left);
    }
	
}

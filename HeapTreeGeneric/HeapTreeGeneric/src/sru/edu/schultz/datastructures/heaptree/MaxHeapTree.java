package sru.edu.schultz.datastructures.heaptree;
/*
 * Group Member Names: Adam Schultz, David Lane
 * als1054, dwl1003
 * Name: Generic Heap Trees
 * Name of the file: MaxHeapTree.java
 * Status: Correctly-Works Completely
 * Start: 4/6/21
 * End: 4/18/21
 * Description: Max heap tree using generic input data types while still satisfying the rules of a heap tree (ordered and complete) while also adding heapsort through popping each root node until the heap is empty
 * Resources:
 * https://docs.oracle.com/javase/8/docs/api/java/lang/Comparable.html
 * http://www.dgp.toronto.edu/~jstewart/378notes/09treeHeight/
 * https://sites.fas.harvard.edu/~cscie119/lectures/heaps.pdf
 * http://www.cs.williams.edu/~bailey/JavaStructures/Book.html
 * https://www.cpp.edu/~ftang/courses/CS241/notes/heap.htm
 * http://www.btechsmartclass.com/data_structures/max-heap.html
 * https://www.cs.cmu.edu/~tcortina/15-121sp10/Unit06B.pdf
 * https://www.cs.auckland.ac.nz/software/AlgAnim/heaps.html
 * https://docs.oracle.com/javase/7/docs/api/java/util/Stack.html
 * https://docs.oracle.com/javase/8/docs/technotes/guides/language/generics.html
 * https://www.oreilly.com/library/view/java-generics-and/0596527756/ch03.html
 * https://docs.oracle.com/javase/7/docs/api/java/lang/Comparable.html
 * https://dzone.com/articles/binary-trees-part-1
 * http://www.dgp.toronto.edu/~jstewart/378notes/09treeHeight/
 * http://btv.melezinek.cz/binary-heap.html
 * Percentage Done by us: 87%
 * Percentage used from resources: 13%
 *•	Java comparable class (https://docs.oracle.com/javase/8/docs/api/java/lang/Comparable.html)
 *•	Formula responsible for returning the height instead of recursively calling method(http://www.dgp.toronto.edu/~jstewart/378notes/09treeHeight/)
 *•	Level Order (https://sites.fas.harvard.edu/~cscie119/lectures/heaps.pdf)
 *•	HeapTreeNode based on BinaryTreeNode implementation from class
 *"This program was done entirely by me and no part of this program was plagiarized, intentionally or unintentionally, from anybody or any location. I would be held accountable and penalized if any part of this program was plagiarized."
 */


import sru.edu.schultz.datastructures.AbstractTreeGeneric;
import java.lang.Math;
import java.util.Stack;
public class MaxHeapTree <T extends Comparable<T>> extends AbstractTreeGeneric <T> 
{
	//Initial size variable for default constructor
    private final int MAXSIZE = 20;
    //master heap tree node variable. All changes made to heapAr are reflected in the root variable
	private HeapTreeNode<T> root;
	//for easy navigation through sorting values and swapping if necessary. heapAr[1] acts similarly to the root variable, while each consecutive index maintains the tree structure
	private HeapTreeNode<T>[] heapAr;
	//node count initialized to 1 to reduce arithmetic complexity in getting parent/child
	private int count;
	//set equal to MAXSIZE or custom size parameter passed in overloaded constructor
	private int size;
	//Initialized to 0 during constructor initialization, and then called/set during the height method
	private int height;
	//variable set when getParentNode is called
	private int parentNode;
	//variable set when getLeftChildNode is called
	private int leftChildNode;
	//variable set when getRightChildNode is called
	private int rightChildNode;
	//buffer node set during constructor
	private static final HeapTreeNode EMPTY = new HeapTreeNode(null);
	//variable name set instead of calling heapAr[1] (reduces magic numbers within code)
	private static final int ROOT = 1;
	
	//default constructor
	public MaxHeapTree()
	{	
		root = EMPTY;
		//keep heapAr[0] for necessary swaps but also using index 1 allows for parent and child arithmetic operations to require one less operation
		count = 1;
		height = 0;
		size = MAXSIZE;
		//initialize generic array with size of 20 (we could resize array or use an arraylist/linkedlist, but for now sticking with arrays will demonstrate our point)
		heapAr = (HeapTreeNode<T>[]) new HeapTreeNode[MAXSIZE];
	}
	
	//overloaded constructor
	public MaxHeapTree(int maxSize)
	{	
		root = EMPTY;
		//keep heapAr[0] for necessary swaps
		count = 1;
		height = 0;
		size = maxSize;
		//Initialize generic array with size passed in by the maxSize variable
		heapAr = (HeapTreeNode<T>[]) new HeapTreeNode[size];
	}
	
	//getter method for root node
	public HeapTreeNode<T> getRoot()
	{
		return root;
	}

	//for generic comparison to check if node passed is of type string
	boolean isString(T arrayVal)
	{
		//takes in value of array (heapAr[i].getData()) and simply returns true if the given value is an instanceof a string.
		if(arrayVal instanceof String)
		{
			return true;
		}
		return false;
	}
	//removing nodes within a heap tree always involves finding the last leaf and swapping that node with the node to be deleted
	public HeapTreeNode<T> findLastLeaf()
	{
		//begin at heapAr[i] and increment i until node count is reached
		for(int i = 1; i < count; ++i)
		{
			//check to see if the node is initialized to a value
			if(heapAr[i].getData() == null)
			{
				//check to see if the node passed is not the root
				if(i > 0)
				{
					System.out.println("Good");
					return heapAr[i-1];
				}
				else
				{
					//root node was uninitialized, and should not return the last leaf (heap tree is empty)
					System.out.println("Bad");
					return null;
				}
			}
			//leaf node will always be 1-count. Since we initialize count to 1, we must always subtract 1 from the count value to get the real last node (otherwise the value will be null)
			else 
			{
				return heapAr[count-1];
			}
		}
		return null;
	}
	//the parent node can be calculated by taking the index passed divided by 2. Since we start at index 1 we don't need to worry about the extra subtraction from the index if we started at 0
	public int getParentNode(int element)
	{
		//we floor the value since we divide odd numbers by 2 in some instances
		parentNode = (int) Math.floor(element / 2);
		return parentNode;
		
	}
	//the left child node can be calculated by taking the index passed and multiplying it by 2. If we started at index 0 the formula would be (2 * element) +1
	public int getLeftChildNode(int element)
	{
		leftChildNode = 2 * element ;
		return leftChildNode;
	}
	//the right child node can be calculated by taking the index passed multipled by two and then added by 1. If we started at index 0 the formula would be (2 * element) + 2
	public int getRightChildNode(int element)
	{
		rightChildNode = (2 * element) + 1;
		return rightChildNode;
	}
	
	//links with level order traversal and also useful when simply needing to get the height of the tree
	public int height()
	{
		//base case where root is only node initialized
		if(root.getRight() == null && root.getLeft() == null)
		{
			height = 1;
		}
		//if the heap as 2 to 3 nodes then the height is 2. Since we start at index 1 in the heapAr we would need to either use count-1 or compare to 3 & 4 noticing that the actual count is count-1
		else if (count == 3 || count == 4)
		{
			height = 2;
		}
		//if the heap has 4 to 8 nodes then the height is 3. Since we start at index 1 in the heapAr we would need to either use count-1 or compare from 5 & 8 noticing that the actual count is count-1
		else if (count >= 5 || count <= 8)
		{
			height = 3;
		}
		else {
			
		//formula for computing height of any tree given node count. We use math.ceil to round log 
		//computation up to nearest whole number since any decimal larger than the closes int is on a new level.
		height = (int) Math.ceil(Math.log((count-1) + 1) / Math.log(2))-1;
		}
		return height;
	}

	//getter method for count. Again we are aware that heapAr begins at index 1 so count-1 returns the actual node count.
	public int nodeCount()
	{
		return count-1;
	}
	//method for percolating up the tree when a node is inserted
	public void heapify (HeapTreeNode<T> root)
	{
		//so basically we gonna for loop from count backwards
		//if it has a child or children, check to see which is larger and proceed to swap
		//if it doesn't no swap needed
		
		HeapTreeNode<T> swapNode; //swapNode to point to the node to swap
		T tempP; //temporary node set to the data value of the parent
		int i = count; //necessary for starting at count, and then changing the value of i to the parent node during each iteration
		swapNode = heapAr[i];
			//check for children on node, big nested ifs for comparison sorry dr sam
			//no necessary loops needed when count of nodes inserted is at most 3
			if(count <=3)
			{
				
				if(heapAr[ROOT].getLeft().getData() != null || heapAr[ROOT].getRight().getData() != null) //if either children exist
				{
					if(heapAr[ROOT].getLeft().getData() != null && heapAr[ROOT].getRight().getData() != null) //if both children exist
					{
						if(heapAr[ROOT].getLeft().getData().compareTo(heapAr[ROOT].getRight().getData()) > 0) //if left child is larger
						{
							swapNode = heapAr[ROOT].getLeft();
							if(swapNode.getData().compareTo(swapNode.getParent().getData()) > 0) //if left child is larger than parent, do the swap
							{
								//note count has not been incremented at this point, and is only incremented after the insert method. Thus we don't have to worry about count-1
								tempP = heapAr[count].getParent().getData(); //placeholder variable to store the value of heapAr[count]s parent
								heapAr[count].getParent().setData(swapNode.getData()); //set the data of the parent to the data value of the node that we are swapping
								heapAr[count].getParent().getLeft().setData(tempP); // now set the left child's data value (the node that we swapped) to the placeholder variable
								tempP = null; //reset tempP
							}
						}
						else //right child is larger
						{
							swapNode = heapAr[ROOT].getRight(); //get the right node since the right is larger
							tempP = heapAr[ROOT].getLeft().getData(); //set placeholder to store the left node's data value
							if(swapNode.getData().compareTo(swapNode.getParent().getData()) > 0) //if right child is larger than parent, do swap
							{
								tempP = heapAr[count].getParent().getData(); //placeholder should now be the parent node's data value
								heapAr[count].getParent().setData(swapNode.getData()); //set the parent node's data to the node that is to be swaped's data value
								heapAr[count].getParent().getRight().setData(tempP); // now set the right node's data to the placeholder variable
								tempP = null;//reset tempP
							}
						}
					}
					else //only other option when children exist is if there is only a left, cannot be only a right child
					{
						swapNode = heapAr[count].getParent().getLeft(); //swap left if it is larger than parent
						if(swapNode.getData().compareTo(swapNode.getParent().getData()) > 0) // left child is larger than parent
						{
							tempP = heapAr[count].getParent().getData(); //placeholder set to the parent node's data value
							heapAr[count].getParent().setData(swapNode.getData());// set the parent node's data to the node that is to be swaped's data value
							heapAr[count].getParent().getLeft().setData(tempP);//now set the left nopde's data to the placeholder variable
							tempP = null;//reset tempP
						}
					}
				}
			}
			else // height of tree is greater than 2 (or node count is greater than 3)
			{
	
				while(i>0) // make sure we aren't going out of bounds of heapAr[i]
				{
					if(swapNode.getParent().getData() == null) //we hit the root node 
					{
						break;//no further iteration needed
					}
					else if(swapNode.getData().compareTo(swapNode.getParent().getData()) > 0) //the node we are swapping's data value is greater than the parent's value (we need to heapify)
					{
						tempP = heapAr[i].getParent().getData(); //placeholder get's set to the parent node's data value
						heapAr[getParentNode(i)].setData(swapNode.getData()); //swap the parent node's data with the swapNode's data
						heapAr[i].setData(tempP); //now set the data of the swapNode to the placeholder variable
					}
					else
					//(swapNode.getData().compareTo(swapNode.getParent().getData()) < 0)
					{
						/*tempP = heapAr[i].getParent().getData(); //placeholder gets set to the parent node's data value
						heapAr[getParentNode(i)].setData(swapNode.getData()); // swap the parent node's data with the swapNode's data
						heapAr[i].setData(tempP); //now set the data of the swapNode to the placeholder variable*/
						break;
					}
					i = getParentNode(i); //i is set to the next parent node to have comparison done
					swapNode = heapAr[getParentNode(i)]; //swapNode is also set to the next parent node to have comparison done
				}
					
				//conditions for if heapAr[1] is getting compared
				if(heapAr[ROOT].getLeft().getData().compareTo(swapNode.getData()) > 0)
				{
					//lies on the left
					tempP = heapAr[ROOT].getData();//placeholder gets set to the parent node's data value
					heapAr[ROOT].setData(heapAr[ROOT].getLeft().getData()); //swap the root's data value with the value of the left node.
					heapAr[ROOT].getLeft().setData(tempP);//set the left node's data to the placeholder value
					tempP =null; //reset tempP
				}
				else
				{
					//lies on the right
					tempP = heapAr[ROOT].getData();//placeholder gets set to the parent node's data value
					heapAr[ROOT].setData(heapAr[ROOT].getRight().getData()); //swap the root's data value with the value of the right node
					heapAr[ROOT].getRight().setData(tempP); //set the right nbode's data to the placeholder value
					tempP = null; //reset tempP
				}
			}
	}
	
	//method for percolating down the tree 
	public void heapifyDown(HeapTreeNode<T> deleteNode)
	{
		T temp = deleteNode.getData(); //placeholder variable
		int i = ROOT; //beginning at i=1
		HeapTreeNode<T> leftNode = deleteNode.getLeft(); //for readability we set leftNode and rightNode to the left and right attributes of deleteNode
		HeapTreeNode<T> rightNode = deleteNode.getRight();
		
		//base case for root
		if(deleteNode.getLeft().getData() == null && deleteNode.getRight() == null)
		{
			return;
		}
		
		//left && right larger, but right is larger than left
		if(leftNode.getData() != null && rightNode.getData() != null && leftNode.getData().compareTo(deleteNode.getData()) > 0 && rightNode.getData().compareTo(deleteNode.getData()) > 0 && rightNode.getData().compareTo(leftNode.getData()) > 0)
		{
			//verify that there are nodes to be swapped
			while(heapAr[i].getRight() != null || heapAr[i].getLeft() != null) {
				if(heapAr[i].getRight().getData() != null && heapAr[i].getRight().getData().compareTo(heapAr[i].getData()) > 0) //right node is larger
				{
					temp = heapAr[i].getData(); //temp is set to the data value of heapAr
					heapAr[i].setData(heapAr[i].getRight().getData()); // we set the heapAr value to the right node
					heapAr[i].getRight().setData(temp); //now set the right node to the value stored in temp
				}
				//we have reached a point where there is no comparison needed
				else
				{
					break;
				}
			i++;
			}
		}
		else if(leftNode.getData() != null && leftNode.getData().compareTo(deleteNode.getData()) > 0) //leftNode is larger
		{
			while(i > 0 && (heapAr[i].getLeft().getData() != null || heapAr[i].getRight().getData() != null)) //again make sure we are performing our heapify actions on nodes and not uninitialized array indexes
			{	
				if(heapAr[i].getLeft().getData().compareTo(heapAr[i].getData()) > 0) //left is still larger than parent
				{
					temp = heapAr[i].getData();//temp is set to the data value of heapAr
					heapAr[i].setData(heapAr[i].getLeft().getData());//we set the heapAr value to the left node
					heapAr[i].getLeft().setData(temp);//now set the left node to the value stored in temp
				}
				else //we have reached a point where there is no comparison needed
				{
					break;
				}
				i++;
			}
		}
		else if(rightNode.getData() != null && (rightNode.getData().compareTo(deleteNode.getData()) > 0)) //rightnode is larger
		{
			while(heapAr[i].getRight().getData() != null || heapAr[i].getLeft().getData() != null && heapAr[i].getData() != null) {//make sure we are performing our heapify actions on nodes and not uninitialized array indexes
				if(heapAr[i].getRight().getData() != null && heapAr[i].getRight().getData().compareTo(heapAr[i].getData()) > 0)//right is still larger that parent
				{
					temp = heapAr[i].getData();//temp is set to the data value of heapAr
					heapAr[i].setData(heapAr[i].getRight().getData());//we set the heapAr value to the right node
					heapAr[i].getRight().setData(temp);//now set the right node 
				}
				else//we have reached a point where there is no comparison needed
				{
					break;
				}
			i++;
			}
		}
		else //last leaf satisfies the heap rules
		{
			return;
		}
		
	}
	
	public void insert(T value)
	{
		if(isFull()) //isFull comparison
		{
			System.out.println("Maxsize of heap has been reached!");
			return; //no need to continue evaluating the insert method
		}
		else 
		{
			HeapTreeNode<T> newNode = new HeapTreeNode<T>(value); // create new node with data value that is to be inserted
			if(root.isEmpty())//base case
			{
				if(count == 0)
				{
					count = 1;
				}
				root = newNode; //root now is set to the node that we are to be inserting
				heapAr[count] = newNode; //make sure heapAr[ROOT] reflects the new changes
				count++;
				//don't need to percolate up since root is our only node
				return;
			}
			else//node is not the root
			{
				heapAr[count] = newNode;
				heapAr[count].setParent(heapAr[getParentNode(count)]); //set the parent node to the evaluation of calling the getParentNode method
				if(heapAr[getParentNode(count)].getLeft().getData() == null) //if the left node is null (hasn't been initialized yet)
				{
					heapAr[getParentNode(count)].setLeft(newNode); // set the left attribute for the parent node to the newNode that we are inserting
					heapify(root); //percolate up until the rules of the heap tree are satisfied
				}
				else
				{
					heapAr[getParentNode(count)].setRight(newNode); //set the right attribute for the parent node to the newNode that we are inserting
					heapify(root);//Percolate up until the rules of the heap tree are satisfied
				}
				count++;
			}
		}
	
	}
	
	public T remove(T value)
	{
		//find the root to use with search method
		HeapTreeNode<T> deleteNode = search(getRoot(), value);
		//find the last leaf within the heap tree
		HeapTreeNode<T> tempNode = findLastLeaf();
		//set the data for the node that we are deleting to the last leaf (saves us an extra step in the heapifyDown method)
		deleteNode.setData(tempNode.getData());
		//decrement count to reflect actual node count (keeps us from having to do count-1)
		count--;
		//set the last leaf's node to null (we do not need to utilize this node anymore)
		heapAr[count]=null;
		//clear the tempNode as well so that there truly is no data attributes linked to the last leaf node.
		tempNode.clearNode();
		//percolate down until the rules of a heap tree are satisfied
		heapifyDown(deleteNode);
		return null;
	}
	
	//returns the array index of where a given node is found within heapAr
	public HeapTreeNode<T> search(HeapTreeNode root, T value)
	{
		//base case for if heap is empty
		if(heapAr[ROOT].isEmpty())
		{
			return null;
		}
		else
		{
			//loop only through all nodes that have been initialized in the heap tree (saves search time)
			for(int i = 1; i < count; ++i)
			{
				//System.out.println("XXXXXX");
				//System.out.println(heapAr[i].getData());
				
				//case for if we have found the node with the given value parameter that was passed
				if(heapAr[i].getData() == value)
				{
					//return the index
					return heapAr[i];
				}
			}
			System.out.println("Value not found\n");
			return null;
		}
	}
	
	//similar search method that also prints the index in which the location has been found. More useful for the user, but only works for string values
	public Object searchArray(T value)
	{
		HeapTreeNode<T> location;//variable for wherever we find the heapAr index with matching data values
		int i = 1; //beginning at root
		location = heapAr[ROOT];
		if (contains(value)) //only works for string values
		{
			while(heapAr[i] != null) //make sure that we aren't indexing out of bounds or into uninitialized values
			{
				if(heapAr[i].getData() == value) // we have found the value
				{
					System.out.println("Location found at index " + i); //print out index
					location = heapAr[i]; //set location to the node in which the value was found
				}
			 i++;
			}
		}
		else 
		{
			location = null; //we did not find the value
			System.out.println("Value not in heap tree");
			return location;
		}
		
		return location.getData(); //return the value of the location's data for easy visualization. does not return the node
	}
	//find maximum value within the heap tree
	public T findMax()
	{
		HeapTreeNode<T> max = heapAr[ROOT]; //assume that heapify rules have been satisfied and that all that is needed is simply fetching the root node
		int i = 2;
		if(isString(heapAr[ROOT].getData()) == true)//compareTo values can sometimes be different when comparing strings vs integers, if the isString method passes a true value then evaluate as a string
		{
			
		while(heapAr[i] != null)//make sure we are iterating through only initialized nodes within the heap tree
		{
			if(heapAr[i].getData().compareTo(max.getData()) == -1)
			//return value of -1 means heapAr[i] is the larger value
			{
				max = heapAr[i]; //max is now the next value that is not the root node
			}
			i++;
		}
		}
		else
		{
			while(heapAr[i] != null) // make sure we are iterating through only initialized nodes within the heap tree
			{
				if(heapAr[i].getData().compareTo(max.getData()) == 1)
				//return value of 1 means heapAr[i] is the larger value
				{ 
					max = heapAr[i]; //max is now the next value that is not the root node
				}
				i++;
			}
		}
		return max.getData();
	}
	//similar concept to findMax. Find minimum value within heap tree
	public T findMin()
	{
		HeapTreeNode<T> min = heapAr[ROOT]; //min set to root node
		int i = 2;
		if(isString(root.getData()) == true)//compareTo values can sometimes be different when comparing strings vs integers, if the isString method passes a true value then evaluate as a string
		{
			
			while(heapAr[i] != null) //make sure we are iterating through only initialized nodes within the heap tree
			{
				if(heapAr[i].getData().compareTo(min.getData()) == 1)
				//return value of 1 means heapAr[i] is a lower value
				{
					min = heapAr[i]; //min is now the next value that is not the root node
				}
				i++;
			}
		}
		else
		{
			while(heapAr[i] != null)//make sure we are iterating through only initialized nodes within the heap tree
			{
				if(heapAr[i].getData().compareTo(min.getData()) == -1)
				//return value of -1 means heapAr[i] is a lower value
				{
					min = heapAr[i]; //min is now the next value that is not the root node
				}
				i++;
			}
		}
		return min.getData();
	}
	//returns true if value within heapAr[] is found. 
	public boolean contains(T value)
	{
		int i = 1;//starting condition set to root, and then evaluates through each node until value is found
		while(heapAr[i] != null) //verifying that we aren't performing comparison on an uninitialized value within heapAr[]
		{
			if(heapAr[i].getData().compareTo(value) == 0)
			//0 denotes that both values are equal
			{
				return true;
			}
			i++;
		}
		return false;
	}
	
	//an empty heap tree is one in which there are no nodes inserted. To search for an empty condition we simply check to see if the root node has been initialized to a value, if not then it hasn't been inserted yet and thus is empty
	public boolean isEmpty(){
		if(root.getData() == null)
		{
			return true;
		}
		return false;
	}
	
	//a full heap tree is one in which the number of nodes added has reached the size count originally set in the MaxHeapTree
	public boolean isFull() {
		if(count-1 == size) //count-1 since we start at i=1 in our heap tree
		{
			return true;
		}
		return false;
	}

	//sort strategy that removes the root one at a time until no values are left within the heap tree
	public Object heapSort()
	{
		Object[] sortedArr = new Object[count-1]; //resorted to array of objects since an array of generics is not supported by java. Sorted Arr is the final array returned once all root nodes have been removed
		HeapTreeNode<T> tempTree[]; //used as a copy of heapAr in order to maintain the structure of heapAr after the heapSort method has been called
		tempTree = (HeapTreeNode<T>[]) new HeapTreeNode[size];
		int j = 0;
		
		//copy nodes initialized from heapAr[i] to tempTree[i]
		for(int i = 1; i < count; i++)
		{
			tempTree[i] = heapAr[i];
		}
		
		//beginning at count and working our way down, we remove each root, making sure to push the root value into the sortedArr
		while(count > 1)
		{
			sortedArr[j] = getRoot().getData();
			remove(getRoot().getData());
			j++;
		}
	
		//revert the heapAr structure back to how it was before the heapSort method was called
		for(int i = 1; i < count; i++)
		{
			heapAr[i] = tempTree[i];
		}
		
		return sortedArr;
	}
	
	public void printSortedArray() //prints the array received from heapSort()
	{
		Object[] sortedArr = new Object[count];
		sortedArr = (Object[]) heapSort(); //creates new array and sets it to the sorted array
		
		System.out.printf("Sorted Array: "); //prints out the array
		for(int i = 0; i < sortedArr.length; i++)
		{
			System.out.print(sortedArr[i]+ " ");
		}
		System.out.println("\n");
	}
	//print the structure of the tree for visualization purposes
	public void printTree()
	{
		//print elements 2^n to elements 2^(n+1)
		double n = 0;
		int rows = height();
		for(int j = 1; j <= height; j++)
		{
			for(int i = (int)Math.pow(2, n); i < (int)Math.pow(2, n+1); i++) //takes every element from 2^n to 2^(n+1)
			{
				if(heapAr[i] != null) //many ifs just for simplicity sake, made quick print statement for the tree display as tree
				{
					if(j == 1 && !heapAr[i].isEmpty())
					{
						System.out.printf("                  "+heapAr[i].getData()+"     "); //print first line
					}
					else if(j == 2 && !heapAr[i].isEmpty())
					{
						System.out.printf("         "+heapAr[i].getData()+"          "); //print second line
					}
					else if(j == 3 && !heapAr[i].isEmpty())
					{
						System.out.printf("    "+heapAr[i].getData()+"     "); //print third line
					} 
					else if(j == 4 && !heapAr[i].isEmpty())
					{
						System.out.printf("  "+heapAr[i].getData()+"   "); //print fourth line
					}
					else
					{
						break;
					}
				}
			}
			n++;
			System.out.printf("\n\n");
		}
		System.out.println("=====================================");
	}
	//given the height of the heap tree, we call printGivenLevel for each height index until height is reached
	public void levelOrder()
	{
		int i = 1;
		
		while(i<=height())
		{
			printGivenLevel(root, i);
			i++;
		}
		System.out.println("================================");
	}
	//starting at the level parameter set by method call in levelOrder, we go through recursively printing out each node within the level, passing in left or right as well as the level-1 in order to ensure we are actually fetching the next node in the level
	public void printGivenLevel(HeapTreeNode<T> root, int level)
	{
		//base case for if the root is empty
		if(root.isEmpty())
		{
			return;
		}
		//case for if the height is only 1 and there are no other children
		if(level == 1)
		{
			System.out.println(root.getData() + " ");
		}
		else if(level > 1)
		{
			printGivenLevel(root.getLeft(), level-1);
			printGivenLevel(root.getRight(), level-1);
		}
	}
	
	//similar to searchArray method, so return type is simply a call to the searchArray method.
	@Override
	public T indexOf(T value) {
		return (T) searchArray(value);
	}
	
	//returns the size of the heap tree. Since we already have the nodeCount method that operates in a similar fashion we just return a call to the nodeCount method.
	@Override
	public int size() {
		return nodeCount();
	}
	//remove all nodes within the heap tree
	@Override
	public boolean clear() {
		if(!heapAr[ROOT].isEmpty())//base case to ensure that there are nodes to be cleared
		{
			int i =ROOT;//start at the root
			while(heapAr[i] != null)//until there are no more nodes left (until last leaf has been reached)
			{
				heapAr[i].clearNode();//make sure the clearNode method also removes the node
				heapAr[i]=null; //set the index of the node cleared to null	
				count--; //decrement count since we are removing such nodes
				i++;
			}
			heapAr[ROOT] = root; //apply the changes made to heapAr over to root.
			return true;
		}
		return false;
	}
	
	public static void main(String[] args)
	{
		
		MaxHeapTree<Integer> exampleHeapTree = new MaxHeapTree<Integer>();
	
		System.out.println(exampleHeapTree.isEmpty());
		exampleHeapTree.insert(1);
		System.out.println(exampleHeapTree.isEmpty());
		exampleHeapTree.insert(2);
		System.out.println(exampleHeapTree.contains(1));
		exampleHeapTree.insert(3);
		exampleHeapTree.levelOrder();
		exampleHeapTree.insert(4);
		exampleHeapTree.insert(5);
		exampleHeapTree.insert(6);
		exampleHeapTree.insert(7);
		exampleHeapTree.printTree();
		exampleHeapTree.remove(7);
		exampleHeapTree.remove(6);
		exampleHeapTree.printTree();
		exampleHeapTree.remove(1);
		exampleHeapTree.remove(2);
		exampleHeapTree.remove(3);
		exampleHeapTree.printTree();
		exampleHeapTree.insert(7);
		exampleHeapTree.insert(12);
		exampleHeapTree.printTree();
		exampleHeapTree.remove(7);
		exampleHeapTree.printTree();
		exampleHeapTree.insert(55);
		exampleHeapTree.insert(6);
		exampleHeapTree.insert(7);
		exampleHeapTree.printTree();
		//exampleHeapTree.clear();
		//System.out.println(exampleHeapTree.nodeCount());
		//exampleHeapTree.insert(1);
		//exampleHeapTree.levelOrder();
		exampleHeapTree.printSortedArray();
	
	}
}

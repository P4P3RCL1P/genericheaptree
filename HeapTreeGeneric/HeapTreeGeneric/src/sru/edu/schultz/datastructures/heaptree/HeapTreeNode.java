package sru.edu.schultz.datastructures.heaptree;



public class HeapTreeNode <T extends Comparable<T>> {
    //set up nodes within heap tree
    //parent/left/right
    //clearing nodes
    //isEmpty
    //Get parent/set parent/set left/set right
    private T data;
    private HeapTreeNode<T> parent;
    private HeapTreeNode<T> leftChild;
    private HeapTreeNode<T> rightChild;

    
    //root node initialization
    private static final HeapTreeNode EMPTY = new HeapTreeNode(null);
    
    
    public HeapTreeNode (T data)
    {
        this.data = data;
        parent = EMPTY;
        leftChild = EMPTY;
        rightChild = EMPTY;
    }

    public HeapTreeNode (T data, HeapTreeNode<T> parent)
    {
        this.data = data;
        this.parent = parent;
        leftChild = EMPTY;
        rightChild = EMPTY;
    }
    
    HeapTreeNode()
    {
    	this.data = null;
    	parent = EMPTY;
    	leftChild = this;
    	rightChild = this;
    }

   public boolean isEmpty()
   {
	   if(this.getData() == null)
	   {
		   return true;
	   }
	   return false;
   }
   public boolean isEMPTY()
   {
	   if(this == EMPTY)
	   {
		   return true;
	   }
	   return false;
   }
    
   public boolean clearNode()
   {
	   this.data = null;
	   parent = EMPTY;
	   leftChild = this;
	   rightChild = this;
	   return true;
   }
    
    public T getData()
    {
        return data;
    }

    public void setData(T data)
    {
        this.data = data;
    }

    public HeapTreeNode<T> getParent()
    {
        return parent;
    }

    public void setParent(HeapTreeNode<T> parent)
    {
        this.parent = parent;
    }

    public HeapTreeNode<T> getLeft()
    {
        return leftChild;
    }

    public void setLeft(HeapTreeNode<T> left)
    {
        this.leftChild = left;
    }

    public HeapTreeNode<T> getRight()
    {
        return rightChild;
    }

    public void setRight(HeapTreeNode<T> right)
    {
        this.rightChild = right;
    }

}

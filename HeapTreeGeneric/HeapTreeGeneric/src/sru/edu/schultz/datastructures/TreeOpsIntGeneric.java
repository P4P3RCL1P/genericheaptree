package sru.edu.schultz.datastructures;

import sru.edu.schultz.datastructures.heaptree.*;

public interface TreeOpsIntGeneric <T> extends BaseOpsIntGeneric <T> {

	
	public int height();
	
	public int nodeCount();
	
	public void insert(T value);
	
	public T remove();
	
	public HeapTreeNode search(HeapTreeNode root,T value);
	
	public T findMax();
	
	public T findMin();
	
	public void levelOrder();
	
	

}

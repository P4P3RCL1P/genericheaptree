package sru.edu.schultz.datastructures;

public interface BaseOpsIntGeneric <T> {
	//Get the size
		public int size();
		
		//is it empty
		public boolean isEmpty();
		
		//is it full
		public boolean isFull();
		
		//clear out all the values
		public boolean clear();
		
		//check if value exists in the structure
		public boolean contains(T value);
		
		//add to end of structure
		public boolean add(T value);
		
		//remove at the end of structure
		public T remove();
		
		//index of first occurrence of value
		public T indexOf(T value);
}

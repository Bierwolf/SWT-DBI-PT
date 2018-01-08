package verwaltung;

import java.util.Arrays;
import java.util.List;

public class GenericBubbleSortList<T extends Comparable<T>> 
{	
	List<T> list; 
	@SuppressWarnings("unchecked")
	public GenericBubbleSortList(T ... args)
	{
		list = Arrays.asList(args);
	}
	
	public void sort()
	{
		 int n = list.toArray().length;  
	     T temp = null;  
         for(int i=0; i < n; i++){  
                 for(int j=1; j < (n-i); j++){  
                          if(list.get(j-1).compareTo(list.get(j)) > 0){  
                                 temp = list.get(j-1);  
                                 list.set(j-1, list.get(j));  
                                 list.set(j, temp);
                         }  
                          
                 }  
         } 
	}
}

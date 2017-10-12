package sys.util;

import java.util.Comparator;

import sys.model.PubMenu;




public class MyComparator implements Comparator{
	 // 按照节点编号比较  
	  public int compare(Object o1, Object o2) {  
	   int j1 = ((PubMenu)o1).getMenuOrder();  
	      int j2 = ((PubMenu)o2).getMenuOrder();  
	      return (j1 < j2 ? -1 : (j1 == j2 ? 0 : 1));  
	  }   
}

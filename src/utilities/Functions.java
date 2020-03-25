package utilities;

import java.util.function.BiFunction;

public class Functions {
	 public static <T> void Sort( T[] data,int start,int end,BiFunction<T,T,Boolean> f )
	{
		 
		 if(end>start)
		 {
			 int mid = (start+end)/2;
			 if(mid!=start)
			 {
				 Sort(data,start,mid,f);
				 Sort(data,mid+1,end,f);
				 merge(data,start,mid,end,f);
			 }
			 else
			 {
				 if(!f.apply(data[start],data[end]))
				 {
					 T x =data[start];
					 data[start] = data[end];
					 data[end] = x;
				 }
			 }
		 }
		 
		
	}
	 private static <T> void merge(T[] data,int start,int mid,int end,BiFunction<T,T,Boolean> f)
	 {
		int leftsize = mid-start+1,rightsize = end-mid;
		@SuppressWarnings("unchecked")
		T[] left = (T[]) new Object[leftsize];
		@SuppressWarnings("unchecked")
		T[] right = (T[]) new Object[rightsize];
		for(int i=0;i<leftsize;i++)
		{
			left[i] = data[start+i];
		}
		for(int i=0;i<rightsize;i++)
		{
			right[i] = data[mid+1+i];
		}
		int r=0,l=0;
		int i;
		for(i=start;r < rightsize && l<leftsize;i++)
		{
			if(f.apply(right[r], left[l]))
			{
				data[i]=right[r];
				r++;
			}
			else
			{
				data[i]=left[l];
				l++;
			}
			
		}
		while(l<leftsize)
		{
			data[i]=left[l];
			l++;
			i++;
		}
		while(r<rightsize)
		{
			data[i]=right[r];
			r++;
			i++;
		}

 
	 }


}

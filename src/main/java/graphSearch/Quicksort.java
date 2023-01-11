package graphSearch;

import java.util.ArrayList;

public class Quicksort {

    public static void sort(ArrayList<Long> arr, int low, int high) {
	if(low < high) {
		int pivot = partition(arr, low, high);
		sort(arr, low, pivot - 1);
		sort(arr, pivot + 1, high);
	}
    }

    public static int partition(ArrayList<Long> arr, int low, int high) {
		long pivot = arr.get(high);
		int i = low - 1;

		for (int j = low; j < high; j++) {
		    if(arr.get(j) < pivot) {
				i++;
				long temp = arr.get(i);
				arr.set(i, arr.get(j));
				arr.set(j, temp);
		    }
		}
		long temp = arr.get(i + 1);
		arr.set(i + 1, arr.get(high));
		arr.set(high, temp);

		return i+1;
    }
}


/**
 *
 * September 18, 2019
 * CSC 226 - Fall 2019
 * Vicky Nguyen - 2 Oct, 2019
 */
import java.util.*;
import java.io.*;

public class Quickselect {

	public static int Quickselect(int[] A, int k) {
		// Write your code here
		return selectK(A, 0, A.length - 1, k);
	}

	public static int selectK(int[] A, int start, int end, int k) {
		int group = 9;
		int n = end - start + 1;

		if ((k > 0) && (k <= n)) {
			int capacity = (n % group == 0) ? (int) (n / group) : (int) (n / group) + 1;
			int[] medianList = new int[capacity];
			int i = start;
			for (i = 0; i < capacity - 1; i++) {
				medianList[i] = median(Arrays.copyOfRange(A, i * group + start, i * group + start + group - 1), group);
			}
			medianList[i] = (n % group == 0)
					? median(Arrays.copyOfRange(A, i * group + start, i * group + start + group), group)
					: median(Arrays.copyOfRange(A, i * group + start, i * group + start + n % group), n % group);
			i += 1;
			int trueMedian = (i == 1) ? medianList[0] : selectK(medianList, start, i - 1, i / 2);
			int pivotIndex = partition(A, start, end, trueMedian);
			if (pivotIndex - start == k - 1)
				return A[pivotIndex];
			if (pivotIndex - start > k - 1)
				return selectK(A, start, pivotIndex - 1, k);
			else
				return selectK(A, pivotIndex + 1, end, k - pivotIndex - 1 + start);
		} else
			return -1;
	}

	// return median
	public static int median(int[] A, int n) {
		Arrays.sort(A);
		return A[n / 2];
	}

	// return index of pivot
	public static int partition(int arr[], int p, int right, int pivot) {
		int pivotIndex = 0;
		for (pivotIndex = 0; pivotIndex <= right; pivotIndex++) {
			if (arr[pivotIndex] == pivot) {
				swap(arr, right, pivotIndex);
				break;
			}
		}
		int x = arr[right];
		for (int j = p; j < right; j++) {
			if (arr[j] <= x) {
				swap(arr, p, j);
				p++;
			}
		}
		swap(arr, p, right);
		return p;
	}

	public static void swap(int arr[], int i, int j) {
		if (i == j)
			return;
		int temp = arr[i];
		arr[i] = arr[j];
		arr[j] = temp;
	}

	public static void main(String[] args) {
		Scanner s;
		int[] array;
		int k;
		if (args.length > 0) {
			try {
				s = new Scanner(new File(args[0]));
				int n = s.nextInt();
				array = new int[n];
				for (int i = 0; i < n; i++) {
					array[i] = s.nextInt();
				}
			} catch (java.io.FileNotFoundException e) {
				System.out.printf("Unable to open %s\n", args[0]);
				return;
			}
			System.out.printf("Reading input values from %s.\n", args[0]);
		} else {
			s = new Scanner(System.in);
			System.out.printf("Enter a list of non-negative integers. Enter a negative value to end the list.\n");
			int temp = s.nextInt();
			ArrayList<Integer> a = new ArrayList<Integer>();
			while (temp >= 0) {
				a.add(temp);
				temp = s.nextInt();
			}
			array = new int[a.size()];
			for (int i = 0; i < a.size(); i++) {
				array[i] = a.get(i);
			}

			System.out.println("Enter k");
		}
		k = s.nextInt();
		System.out.println("The " + k + "th smallest number is the list is " + Quickselect(array, k));
	}
}

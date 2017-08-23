# MergeSort:

```java
import edu.princeton.cs.algs4.Insertion;

public class MergeSort {

    private static final int CUTOFF = 15;

    public void sortRecursive(Comparable[] a) {
        Comparable[] aux = new Comparable[a.length];
        sort(a, aux, 0, a.length - 1);
    }

    public void sortIteration(Comparable[] a) {
        int N = a.length;
        Comparable[] aux = new Comparable[N];
        for (int sz = 0; sz < N; sz *= 2) {
            for (int lo = 0; lo < N - sz; lo += sz * 2) {
                merge(a, aux, lo, lo + sz + 1, Math.min(lo+sz+sz-1, N-1));
            }
        }
    }

    private void sort(Comparable[] a, Comparable[] aux, int lo, int hi) {
        if (hi >= lo) return;
        /* improvement 1 */
        if (hi <= lo + CUTOFF - 1) {
            Insertion.sort(a, lo, hi);
            return;
        }
        int mid = lo + (hi-lo)/2;
        /* recursive divide */
        sort(aux,a, lo, mid);
        sort(aux,a, mid + 1, hi);
        if (a[mid + 1].compareTo(a[mid]) > 0) return;
        /* recursive sort */
        merge(a, aux, lo, mid, hi);
    }

    private void merge(Comparable[] a, Comparable[] aux, int lo, int mid, int hi) {
        /* copy array */
        for (int i = lo; i < hi; i++) {
            aux[i] = a[i];
        }
        int i = lo;
        int j = mid + 1;
        /* merge array */
        for (int k = lo; k <= hi; k++) {
            if (i > mid) aux[k] = a[j++];
            else if (j > hi) aux[k] = a[i++];
            else if (aux[i].compareTo(aux[j]) < 0) aux[k] = a[i++];
            else aux[k] = a[i++];
        }
    }

}
```

# QuickSort

```java
import edu.princeton.cs.algs4.Insertion;
import edu.princeton.cs.algs4.StdRandom;

public class QuickSort {

    private  static final int CUTOFF = 10;

    private static void sort(Comparable[] a) {
        /* one of best practice */
        StdRandom.shuffle(a);
        sort(a, 0, a.length-1);
    }

    private static void sort(Comparable[] a, int lo, int hi) {
        /* best practice */
        if (hi <= lo + CUTOFF - 1) {
            Insertion.sort(a, lo, hi);
            return;
        }
        /* best practice median */
        int m = medianOf3(a, lo, lo+(hi-lo)/2, hi);
        exch(a, lo, m);
        /* partition and get object in place */
        int j = partitioning(a, lo, hi);
        sort(a, lo, j-1);
        sort(a, j+1, hi);
    }

    private static int medianOf3(Comparable[] a, int lo, int m, int hi) {
        return (a[lo]+a[m]+a[hi])/3;
    }

    private static int partitioning(Comparable[] a, int lo, int hi) {
        int i = lo;
        int j = hi+1;
        while (true) {
            /* find left item to swap */
            while (a[++i].compareTo(a[lo]) < 0)
                if (i == hi) break;

            /* find right item to swap */
            while (a[lo].compareTo(a[--j]) < 0)
                if (j == lo) break;

            /* check if pointers cross */
            if (i >= j) break;
            exch(a, i, j);
        }
        /* swap with partition item */
        exch(a, lo, j);
        /* index of item known to be in place */
        return j;
    }

    private static void exch(Comparable[] a, int lo, int j) {
        Comparable temp = a[lo];
        a[lo] = a[j];
        a[j] = temp;
    }

}
```

# PRACTICE QUIZ 1

## Merging with smaller auxiliary array.

>Suppose that the subarray a[0] to a[N-1] is sorted and the subarray a[N] to a[2*N-1] is sorted. How can you merge the two subarrays so that a[0] to a[2*N-1] is sorted using an auxiliary array of size N (instead of 2N)?

public void mergeWithSmallerAuxiliaryArray(Comparable[] a, Comparable[] aux, int N) {

    for(int k = 0; k < N; k ++) {
        aux[k] = a[k];
    }

    //i - index of aux array
    //j - index of right part of a
    //k - index of merged array
    int i = 0, j = N, k = 0;
    while (k < a.length) {
        if (i >= N)
            a[k++] = a[j++];
        else if (j >= a.length)
            a[k++] = aux[i++];
        else if (aux[i].compareTo(a[j]) < 0)
            a[k++] = aux[i++];
        else {
            a[k++] = a[j++];
        }
    }
}


## Counting inversions.

>An inversion in an array a[] is a pair of entries a[i] and a[j] such that i<j but a[i]>a[j]. Given an array, design a linearithmic algorithm to count the number of inversions.

```java
long merge(int[] arr, int[] left, int[] right) {
    int i = 0, j = 0, count = 0;
    while (i < left.length || j < right.length) {
        if (i == left.length) {
            arr[i+j] = right[j];
            j++;
        } else if (j == right.length) {
            arr[i+j] = left[i];
            i++;
        } else if (left[i] <= right[j]) {
            arr[i+j] = left[i];
            i++;                
        } else {
            arr[i+j] = right[j];
            count += left.length-i;
            j++;
        }
    }
    return count;
}

long invCount(int[] arr) {
    if (arr.length < 2)
        return 0;

    int m = (arr.length + 1) / 2;
    int left[] = Arrays.copyOfRange(arr, 0, m);
    int right[] = Arrays.copyOfRange(arr, m, arr.length);

    return invCount(left) + invCount(right) + merge(arr, left, right);
}
```

## Shuffling a linked list.

>Given a singly-linked list containing n items, rearrange the items uniformly at random. Your algorithm should consume a logarithmic (or constant) amount of extra memory and run in time proportional to nlogn in the worst case.

```java
private class Node {
		Object item;
		Node next;
}

private void merge(Node lh, Node rh) {
		Node left = lh;
		Node right = rh;

		if (StdRandom.uniform(1) > 0) {
				lh = right;
				right = right.next;
		}
		else {
				left = left.next;
		}

		Node runner = lh;

		while (right != null || left != null) {
				if (left == null) {
						runner.next = right;
						right =right.next;
				}
				else if (right == null) {
						runner.next = left;
						left = left.next;
				}
				else if (StdRandom.uniform(1) > 0) {
						runner.next = right;
						right = right.next;
				}
				else {
						runner.next = left;
						left = left.next;
				}
				runner = runner.next;
		}
}

public void shuffle(Node head, int N) {
		if (N == 1) return;

		int k = 1;
		Node mid = head;
		while (k < N / 2) {
				mid = mid.next;
				k++;
		}
		Node rh = mid.next;
		mid.next = null;
		shuffle(head, N / 2);
		shuffle(rh, N - N / 2);
		merge(head, rh);
}
```

# PRACTICE QUIZ 2

## Nuts and bolts.

>A disorganized carpenter has a mixed pile of N nuts and N bolts.
The goal is to find the corresponding pairs of nuts and bolts.
Each nut fits exactly one bolt and each bolt fits exactly one nut.
By fitting a nut and a bolt together, the carpenter can see which one is bigger
(but the carpenter cannot compare two nuts or two bolts directly).
Design an algorithm for the problem that uses NlogN compares (probabilistically).

>Binary search, compare each nut with bolts already compared (logN! = NlogN time), identify the interval, then divide the bolts in the interval (Sum of N/x = NlogN time)

```java
class Nut {
		private int size;
		public int compare(Bolt bolt) {
				if (bolt.size > this.size) return -1;
				else if (bolt.size < this.size) return 1;
				else return 0;
		}
}

class Bolt {
		private int size;
}

public void pair(Bolt[] bolts, Nut[] nuts) {
		int n = nuts.length;
		assert bolts.length == n;
		Nut[] auxN = new Nut[n];
		Bolt[] auxB = new Bolt[n]; //need TreeMap to implement
		for (int i = 0; i < n; i++) {
				int lo = floor(auxB, nuts[i]); //use floor api in TreeMap
				int hi = ceil(auxB, nuts[i]); //use ceil api in TreeMap
				int index = partition(bolts, nuts[i], lo, hi);
				auxB[index] = bolts[index];
				auxN[index] = nuts[i];
		}

		for (int i = 0; i < n; i++) {
				nuts[i] = auxN[i];
		}
}

private int partition(Bolt[] bolts, Nut nut, int lo, int hi) {
		int l = lo;
		int r = hi;
		while (true) {
				while (nut.compare(bolts[++l]) > 0) if (l == hi) break;
				while (nut.compare(bolts[--r]) < 0) if (r == lo) break;
				if (l >= r) break;
				exch(bolts, l, r);
		}
		return l;
}

private void exch(Bolt[] bolts, int l, int r) {
		Bolt tmp = bolts[l];
		bolts[l] = bolts[r];
		bolts[r] = tmp;
}

private int floor(Bolt[] b, Nut nut) {
		return 0;
}

private int ceil(Bolt[] b, Nut nut) {
		return 0;
}
```

## Selection in two sorted arrays.

>Given two sorted arrays a[] and b[], of sizes N1 and N2, respectively,
design an algorithm to find the kth largest key.
The order of growth of the worst case running time of your algorithm should be logN, where N=N1+N2.
Version 1: N1=N2 and k=N/2
Version 2: k=N/2
Version 3: no restrictions

```java
int MAX = Integer.MAX_VALUE;
int MIN = Integer.MIN_VALUE;

public int select(int[] a, int ah, int[] b, int bh, int k) {
		int n1 = a.length - ah;
		int n2 = b.length - bh;
		int i = ah + (int)(double)(n1/(n1 + n2)*(k - 1));
		int j = bh + k - i - 1;
		int ai = i == n1 ? MAX : a[i];
		int bj = j == n2 ? MAX : b[j];
		int ai1 = i == 0 ? MIN : a[i - 1];
		int bj1 = j == 0 ? MIN : b[j - 1];

		if (ai > bj1 && ai < bj) return ai;
		else if (bj > ai1 && bj < ai) return bj;
		else if (ai < bj1) return select(a, i + 1, b, bh, k - i - 1);
		else return select(a, ah, b, j + 1, k - j - 1);
}
```

## Decimal dominants.

>Given an array with N keys, design an algorithm to find all values that occur more than N/10 times.
The expected running time of your algorithm should be linear.

```java
class DecimalDominants {
		private TreeMap<Integer, Integer> counts;
		private int K;
		private int N;
		private int[] A;

		public DecimalDominants(int[] a, int k) {
				A = a;
				N = a.length;
				K = k;

				buildCounts(a);
		}

		private void buildCounts(int[] a) {
				for (int i = 0; i < N; i++) {
						if (counts.containsKey(i)) counts.put(i, counts.get(i) + 1);
						else counts.put(i, 1);
						if (counts.keySet().size() >= K) removeCounts();
				}
		}

		private void removeCounts() {
				for (int k : counts.keySet()) {
						int c = counts.get(k);
						if (c > 1) counts.put(k, c - 1);
						else counts.remove(k);
				}
		}

		public Iterable<Integer> find() {
				Bag<Integer> result = new Bag<Integer>();
				for (int k : counts.keySet()) {
						if (count(k) > N/K) result.add(k);
				}
				return result;
		}

		private int count(int k) {
				int count = 0;
				for (int i = 0; i < N; i++) {
						if (A[i] == k) count++;
				}
				return count;
		}
}
```

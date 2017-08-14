# Binary search:

>If key is high -- high lo
If key is low -- low hi

```java
int search(int[] array, int key) {
  int lo = 0;
  int hi = array.length -1;
  while(lo <= hi) {
    int mid = lo + (hi-lo)/2;
    if (key > array[mid]) {
      lo = mid + 1;
    } else if (key < array[mid]) {
      hi = mid - 1;
    } else {
      return mid;
    }
  }
  return -1;
}
```

# 3sum:

>Shows sums where a + b + c = key

```java
void sum(int[] array, int key) {
  sort(array);
  for (int mid = 1; mid < array.length - 1; mid++) {
    int lo = min - 1;
    int hi = min + 1;
    while(lo > -1 && hi < array.length) {
      int n = array[lo] + array[mid] + array[hi];
      if (n > key) {
        hi++;
      } else if (n < key) {
        lo--;
      } else {
        sout("$lo $mid $hi")
      }
    }
  }
}
```

# Dinamic connectivity:

>Find id of a and change all a ids to b id.

```java
public class QuickFind {

  private int[] id;

  public QuickFind(int N) {
    id = new int[N];
    for (int i = 0; i < id.length; i++) {
      id[i] = i;
    }
  }

  public boolean connected(int a, int b) {
    return id[a] == id[b];
  }

  public void union (int a, int b) {
    int aId = id[a];
    int bId = id[b];
    for (int i = 0; i < id.length; i++) {
      if (id[i] == aId) {
        id[i] = bId;    
      }
    }
  }

}
```

>All elements have a parent.

```java
public class QuickUnion {

  private int[] id;

  public QuickFind(int N) {
    id = new int[N];
    for (int i = 0; i < id.length; i++) {
      id[i] = i;
    }
  }

  public int root(int i) {
    while (i != id[i]) {
      i = id[i];
    }
    return i;
  }

  public boolean connected(int a, int b) {
    return root(a) == root(b);
  }

  /* only root elements must be modified
  so it will have new parent */
  public void union (int a, int b) {
    int a = root(a);
    int b = root(b);
    id[a] = b;
  }

}
```

>All elements have a sum of a size of his children

```java
public class WeightedQuickUnion {

  private int[] id;
  private int[] sz;

  public WeightedQuickUnion(int N) {
    id = new int[N];
    for (int i = 0; i < id.length; i++) {
      id[i] = i;
      sz[i] = 1;
    }
  }

  public int root(int i) {
    while (i != id[i]) {
      i = id[i];
    }
    return i;
  }

  public boolean connected(int a, int b) {
    return root(a) == root(b);
  }

  /* only root elements must be modified
  so it will have new parent */
  public void union (int a, int b) {
    int a = root(a);
    int b = root(b);
    if (a == b) return;
    if (sz[a] < sz[b]) {
        id[a] = b;
        sz[b] += sz[a];
    } else {
      id[b] = a;
      sz[a] += sz[b];
    }
  }

}
```
>Make tree with minimum height

```java
public class WeitedQuickUnionwithPathCompression {

  private int[] id;
  private int[] sz;

  public WeitedQuickUnionwithPathCompression(int N) {
    id = new int[N];
    for (int i = 0; i < id.length; i++) {
      id[i] = i;
      sz[i] = 1;
    }
  }

  public int root(int i) {
    while (i != id[i]) {
      /* path compression with single line
      dad becomes father */
      id[i]=id[id[i]];
      i = id[i];
    }
    return i;
  }

  public boolean connected(int a, int b) {
    return root(a) == root(b);
  }

  /* only root elements must be modified
  so it will have new parent */
  public void union (int a, int b) {
    int a = root(a);
    int b = root(b);
    if (a == b) return;
    if (sz[a] < sz[b]) {
        id[a] = b;
        sz[b] += sz[a];
    } else {
      id[b] = a;
      sz[a] += sz[b];
    }
  }

}
```

# PRACTICE QUIZ 1

## Social network connectivity.

>Given a social network containing n members and a log file containing m timestamps at which times pairs of members formed friendships, design an algorithm to determine the earliest time at which all members are connected (i.e., every member is a friend of a friend of a friend ... of a friend). Assume that the log file is sorted by timestamp and that friendship is an equivalence relation. The running time of your algorithm should be mlogn or better and use extra space proportional to n.

When you add a friendship to the union-find datastructure, you can note if it results in two graph components being joined.
Simply keep adding edges until N-1 of these merging events have happened.

```java
UF = UnionFind(1..N)
count = 0
for timestamp, p1, p2 in friendships {
    if !UF.Find(p1, p2) {
        UF.Union(p1, p2)
        count+++
        if count == N-1 {
            return timestamp
        }
    }
}
return +infinity
```

## Union-find with specific canonical element.

>Add a method find() to the union-find data type so that find(i) returns the largest element in the connected component containing i. The operations, union(), connected(), and find() should all take logarithmic time or better.

```java
public class UnionFindLargest {

  private int[] id;
  private int[] sz;
  private int[] large;

  public UnionFindLargest(int N) {
    id = new int[N];
    for (int i = 0; i < id.length; i++) {
      id[i] = i;
      sz[i] = 1;
      large[i] = i;
    }
  }

  public int root(int i) {
    while (i != id[i]) {
      i = id[i];
    }
    return i;
  }

  public boolean connected(int a, int b) {
    return root(a) == root(b);
  }

  /* only root elements must be modified
  so it will have new parent */
  public void union (int a, int b) {
    int a = root(a);
    int b = root(b);
    int max = large[a] > large[b] ? large[a] : large[b];
    if (a == b) return;
    if (sz[a] < sz[b]) {
        id[a] = b;
        sz[b] += sz[a];        
        large[b] = max;        
    } else {
      id[b] = a;
      sz[a] += sz[b];
      large[a] = max;
    }
  }

  public int find (int a) {
    return large[a];
  }

}
```

## Successor with delete.

>Given a set of n integers S={0,1,...,n−1} and a sequence of requests of the following form:
Remove x from S. Find the successor of x: the smallest y in S such that y≥x.
design a data type so that all operations (except construction) take logarithmic time or better in the worst case.

```java
public class WeitedQuickUnionwithPathCompression {

  private int[] id;

  public WeitedQuickUnionwithPathCompression(int N) {
    id = new int[N];
    for (int i = 0; i < id.length; i++) {
      id[i] = i;
    }
  }

  public int root(int i) {
    while (i != id[i]) {
      /* path compression with single line
      dad becomes father */
      id[i]=id[id[i]];
      i = id[i];
    }
    return i;
  }

  public boolean connected(int a, int b) {
    return root(a) == root(b);
  }

  /* set max(a, b) as a new root min(a, b) */
  public void union (int a, int b) {
    int a = root(a);
    int b = root(b);
    if (a == b) return;
    if (a < b) {
      id[a] = b;
    } else {
      id[b] = a;
    }
  }

  public void delete(x) {
    union(x, x+1);
  }

  public int successor(x) {
    return root(x);
  }

}
```

# PRACTICE QUIZ 2

# 3sum:

>Design an algorithm for the 3-SUM problem that takes time proportional to n2 in the worst case. You may assume that you can sort the n integers in time proportional to n2 or better.

```java
void sum(int[] array, int key) {
  sort(array);
  for (int mid = 1; mid < array.length - 1; mid++) {
    int lo = min - 1;
    int hi = min + 1;
    while(lo > -1 && hi < array.length) {
      int n = array[lo] + array[mid] + array[hi];
      if (n > key) {
        hi++;
      } else if (n < key) {
        lo--;
      } else {
        sout("$lo $mid $hi")
      }
    }
  }
}
```

# Search in a bitonic array:

>An array is bitonic if it is comprised of an increasing sequence of integers followed immediately by a decreasing sequence of integers.  Write a program that, given a bitonic array of N distinct integer values, determines whether a given integer is in the array. Standard version: Use ∼3lgN compares in the worst case. Signing bonus: Use ∼2lgN compares in the worst case (and prove that no algorithm can guarantee to perform fewer than ∼2lgN compares in the worst case).

```java
private boolean binsearch(int[] a, int left, int right, int key) {
    if (a[left] < a[right]) {
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (a[mid] < key) left = mid + 1;
            else if (a[mid] > key) right = mid - 1;
            else return true;
        }
    }
    else {
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (a[mid] < key) right = mid - 1;
            else if (a[mid] > key) left = mid + 1;
            else return true;
        }
    }
    return false;
}
```

# Egg drop:

>Suppose that you have an N-story building (with floors 1 through N) and plenty of eggs.
An egg breaks if it is dropped from floor T or higher and does not break otherwise.
Your goal is to devise a strategy to determine the value of T given the following limitations on the number of eggs and tosses:
- Version 0: 1 egg, ≤T tosses.
- Version 1: ∼1lgN eggs and ∼1lgN tosses.
- Version 2: ∼lgT eggs and ∼2lgT tosses.
- Version 3: 2 eggs and ∼2√‾n‾ tosses.
- Version 4: 2 eggs and ≤c√‾T‾ tosses for some fixed constant c.

Version 0 : try from floor 1 until it's broken
Version 1: binary search
Version 2 : try from 1 , 2, 4, ... the first number that is bigger than T , totally ~lgT tosses , then do binary search another ~lgT tosses
Version 3 :  try from n^0.5 , 2*n^0.5 , 3*n^0.5 , ... the first number that is bigger than T, used 1 egg and at most ~n^0.5 tosses. then we know T is between k*n^0.5 and (k+1)\*n^0.5  , then we try from lower bound to upper bound , at most n^0.5 tosses and use another egg
Version 4: try from 1, 1+2 , 1+2+3 , ..., 1+2+3 + ... +k the first number that is bigger than T, used 1 egg and  k tosses , then try in last k floor, so totally 2k tosses, 0.5 \*k * (k + 1) = T , so k = 2^0.5 T^0.5

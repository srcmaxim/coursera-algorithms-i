# Stack:

```java
public class Stack {

    private Node first;

    private static class Node {
        String data;
        Node next;  
    }

    public isEmpty() {
      return first == null;
    }

    public void push(String item) {
      Node oldFirst = first;
      first = new Node();
      first.data = item;
      first.next = oldFirst;
    }

    public String pop() {
      String data = first.data;
      first = first.next;
      return data;
    }

}
```

```java
public class Stack {

    private String[] data;
    private int next = 0;

    public Stack(int capacity) {
        data = new String[capacity];
    }

    public isEmpty() {
        return next == 0;
    }

    public void push(String item) {
        if(next + 1 < data.length) {
            data[next++];
        }    
    }

    public String pop() {
      if (next - 1 >= 0) {
         String item = data[--next];
         data[next] = null;
         return item;
      }

    }

}
```

```java
public class Stack {

    private String[] data;
    private int next = 0;

    public Stack(int capacity) {
        data = new String[capacity];
    }

    public isEmpty() {
        return next == 0;
    }

    public void push(String item) {
        if(next + 1 >= data.length) {
            resize(data.length*2)
        }    
        data[next++];
    }

    public void resize(String count) {
      String[] newData = new String[count];
      data = System.arrayCopy(data,newData,...);
    }

    public String pop() {
      if (next - 1 <= data.length / 4) {
        resize(data.length / 2)
      }
      if (next - 1 >= 0) {
         String item = data[--next];
         data[next] = null;
         return item;
      }
    }

}
```

Delete last node in the list:
```java
Node x = first;
while (x.next.next != null) {
    x = x.next;
}
x.next = null;
```

# Queue:

```java
public class Queue {

    private Node first, last;

    private static class Node {
        String data;
        Node next;  
    }

    public isEmpty() {
      return first == null;
    }

    public void enqueue(String data) {
      Node oldLast = last;
      last = new Node();
      last.data = data;
      last.next = null;
      if (isEmpty()) {
          first = last;
      } else {
          oldLast.next = last;
      }
    }

    public String dequeue() {
        String data = first.data;
        first = first.next;
        if(isEmpty()) {
            last == null;
        }
        return data;
    }

}
```

```java
public class Queue {

    private String[] data;
    private int last;
    private int next;

    public Stack(int capacity) {
        data = new String[capacity];
    }

    public isEmpty() {
        return next != last;
    }

    public void enqueue(String data) {
      if (isEmpty()) {
          data[next++] = data;
          next = nextPoint(next);
      }
    }

    public String dequeue() {
        String data = data[last];
        data[last] = null;
        last = nextPoint(last);
        return data;
    }

    private int nextPoint(int point) {
        return ++point % data.length;
    }

}
```

# Dijcstra math:

```java
public class Dijcstra {
  public static void main(String[] args) {
    Stack<String> ops = new Stack<>();
    Stack<Double> vals = new Stack<>();
    while (!StdIn.isEmpty()) {
      String s = StdIn.readString();
      switch (s) {
        case "+" :
        case "*" : op.push(s);
        case "(" : break;
        case ")" :
            eval();
            break;
        default :
            vals.push(s);
      }
    }
  }
  private Double eval() {
    switch (ops.pop()) {
      case "+" :          
          vals.push(vals.pop()+vals.pop());
          break;
      case "*" :
          vals.push(vals.pop()*vals.pop());
          break;
    }

  }
}
```

# Selection sort:

n^2/2, sorts sorted, n^2 for desc

>for i=0:n-1
for min(j=i:n-1) swap i min

```java
for (int i = 0; i < a.length: i++) {
    int min = i;
    for (int j = i+1; j < a.length: j++) {
      if (a[min] > a[j]) min = j;
    }
    swap(a, min);
}
```

# Insertion sort:

 n^2/4, not sort sorted, partial sort, n^2 for desc

>for i=0:n-1
for j=i:1
if (j j-1) swap j j-1

```java
for (int i = 0; i < a.length; i++) {
    for (j = i; j < 0; j--) {
        if (a[j] > a[j-1]) {
            swap(j,j-1);
        }    
    }    
}
```

# Shell sort: n^1.5

```java
public void sort(int arr[]) {}
    int n = arr.length;
    for (int gap = n/2; gap > 0; gap /= 2)  {
        for (int i = gap; i < n; i += 1) {
            int temp = arr[i];
            int j;
            for (j = i; j >= gap && arr[j - gap] > temp; j -= gap)
                arr[j] = arr[j - gap];
            arr[j] = temp;
        }
    }
}
```

#Knuth Shuffle:

>i = 1:n
j = rand(0:i+1)
swap(i,j)

```java
private Item[] knuthShuffle() {
    Item[] q = Arrays.copyOf(queue, next);
    for (int i = 1; j < q.length; i++) {
        int j = StdRandom.uniform(i + 1);
        swap(q, i, j);
    }
    return q;
}
```


# PRACTICE QUIZ 1

## Queue with two stacks.

>Implement a queue with two stacks so that each queue operations takes a constant amortized number of stack operations.

```java
public class Queue {
    private Stack s1 = ...;
    private Stack s2 = ...;

    pubblic void enqueue(String data) {
      s1.push(data);
      s2.push(s1.pop());
    }
    pubblic String dequeue() {
      return s2.pop();
    }
}
```

## Stack with max.

>Create a data structure that efficiently supports the stack operations (push and pop) and also a return-the-maximum operation. Assume the elements are reals numbers so that you can compare them.

```java
public class Stack<T extends Comparable> {
    private Stack st = ...;
    private Stack max = ...;

    pubblic void push(T data) {
      st.push(data);
      if (max.peek().compare(data) < 0) {
        max.push(data);
      }
    }
    pubblic T pop() {
      T data = st.pop()
      if (data.compare(max.peek()) == 0) {
        max.pop();
      }
      return data;
    }

    pubblic T max() {
      return max.pop();
    }
}
```

## Java generics.

Explain why Java prohibits generic array creation.

Generic type has a compile type visibility , type erasure at runtime, so it's prohibited to use type T in:
- new T[]
- (T) t
- t instance of T

# PRACTICE QUIZ 2

## Intersection of two sets.

>Given two arrays a[] and b[], each containing n distinct 2D points in the plane, design a subquadratic algorithm to count the number of points that are contained both in array a[] and array b[].

```java
public int countIntersection(T[] a, T[] b) {
    Shell.sort(a);
    Shell.sort(b);

    int i = 0;
    int j = 0;
    int count = 0;

    while (i < a.length && j < b.length) {
        if (a[i].compareTo(b[j]) == 0) {
            count++;
            i++;
            j++;
            continue;
        }
        if (a[i].compareTo(b[j]) < 0) {
            i++;
        } else {
            j++;
        }
    }
    return count;
}
```

## Permutation.

>Given two integer arrays of size n, design a subquadratic algorithm to determine whether one is a permutation of the other. That is, do they contain exactly the same entries but, possibly, in a different order.

```java
public boolean isPerm(Integer[] a, Integer[] b) {
    if (a.length != b.length) return false;
    Shell.sort(a);
    Shell.sort(b);

    for (int i = 0; i < a.length; i++) {
        if (a[i] != b[i]) return false;
    }
    return true;
}
```

## Dutch national flag.

>Given an array of N buckets, each containing a red, white, or blue pebble, sort them by color. The allowed operations are:
    swap(i,j): swap the pebble in bucket i with the pebble in bucket j.
    color(i): color of pebble in bucket i.

```java
public void sort(Character[] flags) {  
    int red = 0;// Initial index for red
    int blue = flags.length - 1;//Initial index for blue
    int i = 0;
    while (i <= blue) {
        if (flags[i] == 'r') {
            swap(flags, red++, i++);
        } else  if (flags[i] == 'b') {
            swap(flags, blue--, i);
            //Here don't increase i, since after swap, the flags[i] has not been checked.
        } else {
            i ++;
        }
    }
}
```















.

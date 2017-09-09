
# PRACTICE QUIZ 1

## 4-SUM.

>Given an array a[] of n integers, the 4-SUM problem is to determine if there exist distinct indices i, j, k, and l such that a[i]+a[j]=a[k]+a[l]. Design an algorithm for the 4-SUM problem that takes time proportional to n2 (under suitable technical assumptions).

```java
public class FourSum {
	static class Pair {
		int i, j;

		public Pair(int i, int j) {
			this.i = Math.min(i, j);
			this.j = Math.max(i, j);
		}

		public int hashCode() {
			return 31 * i + 47 * j;
		}

		public boolean equals(Object o) {
			if (!(o instanceof Pair))
				return false;

			Pair other = (Pair) o;
			return this.i == other.i && this.j == other.j;
		}

		public String toString() {
			return "Pair<" + i + ", " + j + ">";
		}
	}

	public static void main(String[] args) {
		Map<Integer, Set<Pair>> sums = new HashMap<Integer, Set<Pair>>();
		int[] input = { 2, 3, 1, 0, -4, -1 };
		for (int i = 0; i < input.length; i++) {
			for (int j = 0; j < input.length; j++) {
				if (i == j)
					continue;

				int sum = input[i] + input[j];
				if (!sums.containsKey(sum)) {
					sums.put(sum, new HashSet<Pair>());
				}
				sums.get(sum).add(new Pair(input[i], input[j]));
			}
		}

		for (int sum : sums.keySet()) {
			Set<Pair> pairs = sums.get(sum);
			if (pairs.size() > 1) {
				System.out.println(pairs);
				for (Pair pair : pairs) {
					System.out.println("sum: " + sum + ", " + pair.i + ", "
							+ pair.j);
				}
			}
		}
	}
}
```

## Hashing with wrong hashCode() or equals().

>Suppose that you implement a data type OlympicAthlete for use in a java.util.HashMap.
a1 = OlympicAthlete()
a2 = OlympicAthlete()
equals() and hashCode() must be consistent:
case 1: if a1.equals(a2) is True => a1.hashCode() == a2.hashCode() is True
case 2: if a1.hashCode() != a2.hashCode() => a1.equals(a2) is False
case 3: if a1.hashCode() == a2.hashCode() is True => must check equality with equals()

Q: Describe what happens if you override hashCode() but not equals().
A: case 3 will not give the answer you expect (equals tests for equal-identity)
Q: Describe what happens if you override equals() but not hashCode().
A: a1.hashCode() returns memory address of a1 so a1.hashCode() will never equal
a2.hashCode() even if a1 equals a2 case 1 and case 2 will fail
Q: Describe what happens if you override hashCode() but implement
public boolean equals(OlympicAthlete that)
instead of
public boolean equals(Object that).
A: equals must be able to compare objects of different classes
if keys are of different data types; equals must accept NULL as an argument

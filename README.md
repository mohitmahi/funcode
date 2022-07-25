# funcode

# Graph
> Question to ask:
* is Directed ?
* is Connected ? or multiple component
* is Cyclic ? or Acyclic ? How many cycle
* is Weighted ? -ve weight allowed ?
* Dense Graph ? Matrix vs AdjList rep

## Topological Sort:
* > O(V+E): Modify DFS to have one more stack and once DFS of all child are completed, push current node to stack. Stack will have Topological order 
* > O(V+E): Kahn's Algo: InOrder => Queue with O-in-degree = > Start with 0 and -1 all adjacent and continue 
## Shortest Path
(source vertex is always present for SP problem)
* > unweighted : O(V + E) time modified BFS
* > weighted :: Dijkstra’s algorithm (Greedy) O((V+E)LogV)  (Use min-PQ for edges weight) same for Prim's MST 
* > Bellman-Ford (Graph with -ve value) O(VE) - Run |V|-1 times loop to update Shortest distance
* > For DAG: O(V+E) simply topological sort and then process in same order and check each adjacent node
* > O(V3) Floyd Warshall Algorithm "All Pairs Shortest Path" Three loops of "V" 
    
## Longest Path 
(w/o source)
* > Do DFS from each node, (N * N) and take max. 
  > Do DFS from each node with a DP[] and visited[] => O(N)

(w/ a source and weighted)
* > DAG: -1 * Edge_Weight then use Topological Sorting to find shortest path and revert edge value

(w/ a source and non-weighted)
* > Simpy DFS from that source
* > If it's a tree, the height path will be longest from root.
  > 

# Tree
* >Rooted vs Non-Rooted Tree 
* > LCA: Least(Lowest) common ancestor:  Find Path from Root to both A, B and find the junction.
* > LCA: Do DFS from root, if root is either A/B return node, if both left/right is non-null return node, else go in one direction.
* > BST/AVL/Red-Black: BST can be skewed and worst case can be N, which AVl/RB are BST which enforce balancing
  * >> AVL is more strict balanced so come with cost of rotation
  * >> Between tree-map and heap, some time tree-map can be more useful if we need both min/max from with minimal memory footprint. 
  * >> A heap is easily implemented in contiguous memory, i.e. an array. A red-black tree is typically constructed with a separate heap allocation for each node. The red-black tree ends up accessing memory all over the heap for each tree traversal.
  * >> Heap is simpler and take less memory overhead to get build, while RB BST Tree need pointer and coloring bits per node. 
  * >> TreeMap is based on Red-Black BST Tree: Easy to get Min/Max both using getFirst/getLast key O(1) else O(logN) for all other
  * >> PQueue- min/max Heap can just tell min/max in O(1) but finding any random number is O(N) 
# DP
* > Place Tiles: Tiles [1,2,4,1] ==>  Frequency Map => [1:2, 2:1, 4:1] ==> O(2``N) Brute force 
  > f(n) = f(n-1) + f(n-2) for Tiles [1:[]] [2: [][]] For fn(6) => find fn(1), fn(2), fn(3), fn(5), fn(8)
  > DP: For (i:1 => n) => For each tiles length T*m(times) dp[i] += dp[i-T]*m
  > dp[n] ==> Total number of ways to place T tiles over N places. 

# String
* > Longest Increasing Sub-sequence => [1,3,6,2,8,7] ==>  1,3,6,7 (4)  Build DAG(N2) and find longest path or use LogN for Binary Search keeping a sub array from left to right
  >> Use dp[i] (all "1") If num[i] > num[j] && dp[j] + 1 > dp[i]  then dp[i] = dp[j] + 1 
  >> LIS[i] = MAX(1, 1+ LIS[j]) j < i && num[i] > num[j]
  `````` 
  O(N*N)
  Arrays.fill(dp, 1);
        for (int i = 1; i < nums.length; i++) {
            for (int j = 0; j < i; j++) {
                if (nums[i] > nums[j]) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
        }
* > O(N*N) Longest Pallindromic SubString ==> expand around corner (i & i+1)
* > Longest Common Seq (LCS) between s1/s2=> N * N matrix and traverse bottom up.
  >> If Match dp[i][j] = 1 + dp[i-1][j-1]; else max(dp[i-1][j], dp[i][j-1]);
* > Longest Pallindromic SubSeq => Reverse "S" and find LCS
* > Longest Common SubString between S1/S2 (consecutive)
  >> If Match dp[i][j] = 1 + dp[i-1][j-1]; else dp[i][j] = 0;

# funcode

# Graph
> Question to ask:
* is Directed ?
* is Connected ? or multiple component
* is Cyclic ? or Acyclic ? How many cycle
* is Weighted ? -ve weight allowed ?
* Dense Graph ? Matrix vs AdjList rep

# Topological Sort:
* > Modify DFS to have one more stack and once DFS of all child are completed, push current node to stack. Stack will have Topological order 

# Shortest Path
(source vertex is always present for a SP problem)
*  > Dijkstra’s algorithm (Greedy) O((V+E)LogV)
*  > Bellman-Ford (Graph with -ve value) O(VE) - Run |V|-1 times loop to update Shortest distance
*  > For DAG: O(V+E) simply topological sort and then process in same order and check each adjacent node
*   > O(V3) Floyd Warshall Algorithm "All Pairs Shortest Path" Three loops of "V" 
    > 

# Longest Path 
(w/o source)
* > Do DFS from each node, (N * N) and take max. 
  > Do DFS from each node with a DP[] and visited[] => O(N)

(w/ a source and weighted)
* > DAG: -1 * Edge_Weight then use Topological Sorting to find shortest path and revert edge value

(w/ a source and non-weighted)
* > Simpy DFS from that source
* > If it's a tree, the height path will be longest from root.
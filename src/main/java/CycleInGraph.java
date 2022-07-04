//DFS
// Directed => If new node already visited.  => Cycle
//NonDirected ==> If new node already visited and not parent => Cycle

// Check for all tree in forest (all connected component)

//Detect a negative cycle in a Graph | (Bellman Ford)
// * First run a loop |V|-1 times to get shortedPath for each V for max V-1 edges. (Shortest Path)
// * Run a loop once more and shortest distance comes, means there is a negative cycle.

//Detect Cycle in a directed graph using colors with DFS
// * Color WHITE [Unvisited]
// * Color GREY [VISITING]
// * Color BLACK [BLACk]

//Shortest cycle in an undirected unweighted graph ==> Dijkstra (Shortest) + Cycle (Visited)
/**
Approach: For every vertex, we check if it is possible to get the shortest cycle involving this vertex. For every vertex first, push current vertex into the queue and then it’s neighbours and if the vertex which is already visited comes again then the cycle is present.
        Apply the above process for every vertex and get the length of the shortest cycle.
 **/
public class CycleInGraph {
}

//DFS
// Directed => If new node already visited.  => Cycle
//NonDirected ==> If new node already visited and not parent => Cycle

// Check for all tree in forest (all connected component)

//Shortest cycle in an undirected unweighted graph ==> Dijkstra
/**
Approach: For every vertex, we check if it is possible to get the shortest cycle involving this vertex. For every vertex first, push current vertex into the queue and then it’s neighbours and if the vertex which is already visited comes again then the cycle is present.
        Apply the above process for every vertex and get the length of the shortest cycle.
 **/
public class CycleInGraph {
}

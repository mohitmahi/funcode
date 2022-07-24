import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class SnapShotArray {
    /*
    Implement a SnapshotArray that supports the following interface:
* SnapshotArray(int length) initializes an array-like data structure with the given length. Initially, each element equals 0.
* void set(index, val) sets the element at the given index to be equal to val.
* int snap() takes a snapshot of the array and returns the snap_id: the total number of times we called snap() minus 1.
* int get(index, snap_id) returns the value at the given index, at the time we took the snapshot with the given snap_id
     */
// Option 1: List<TreeMap<>>  List[i] ==> TreeMap<SnapId, Value>  get O(Log(SnapCount)) set O(log(SnapCount) but will O(1) as SnapCount will be largest so far(increasing)

// Option 2: List<HashMap>  List[i] ==> HashMap<SnapId, Value>    get O(SnapCount)  set O(1) // Good if SnapCount is small

// Option 2.2: List<HashMap> and a Map<Index,lastSnapId>  to optimize get with additional memory cost, 1) lastSnapId == passedSnapId. 2) lastSnapId > passedSnapId (same N) 3) lastSnapId < passedSnapId => O(1)

    List<TreeMap<Integer, Integer>> A; //array of Tree Map or PQ
    int snap_id = 0;
    public SnapShotArray(int length) {
        A = new ArrayList<>(length);
        for (int i = 0; i < length; i++) {
            A.add(new TreeMap<>());
            A.get(i).put(0, 0); //default
        }
    }

    public void set(int index, int val) {
        A.get(index).put(snap_id, val);
    }

    public int snap() {
        return snap_id++;
    }

    public int get(int index, int snap_id) {
        return A.get(index).floorEntry(snap_id).getValue();
    }
}

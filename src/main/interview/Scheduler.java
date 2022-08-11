public class Scheduler {

    // A user's task is defined a function.
    void MyTask() {
        // Runs on an arbitrary CPU core.
    }

    // Schedule the task for execution.
   // PostTask(&MyTask);


   /* Functional Requirements:
    Able to submit a Task
    Able to find a machine slot to execute
    Able to get a callback from task execution
    Life Cycle of Task (Good to have)
    Now vs Future (asap)
    Priority (same for all vs High/low)

    Non-Functional Req:
    Multi-tenant
    Elastic (Highly scalable)
    Lower Dwell Time (Good to have)



    PostTask(&MyTask) ⇒ return Type Callable(TaskStatus)

    Asynchronous method

    BIO  NIO/AIO
    Netty (NIO) With just a few 100 thread we can manage 1K connection.

    Goal: Minimize max parallel thread*/

//    Interface: JobScheduler
//    Interface: UsageCache
//    Interface: ClientIdentifier
//    Interface: CallbackUpdater

//    Class: TaskJobScheduler
//    Class: CPUUsageCache / MemoryUsageCache
//    Class: ClientIdentiferBuilder
//    Class: TaskCallbackUpdater

//    ENUM TaskStatus {
//        SUBMITTED,
//        PROCESSING,
//        COMPLETED
//    }

//    TaskJobScheduler ⇒
//    Public PostTask(Task id) ⇒
//    Move the task to SUBMITTED state and return Future and invoke AcceptATask(Id)
//    Private AcceptATask()
//    AcceptATask ⇒
//            (1)Find a Slot (CPUUsageCache) given current available slot.
//            (2) Take a lock on a given slot Id. (Optimistic vs Pessimistic)
//     If we fail to acquire a lock then run (2) & (3).
//            (2) Assign a slot to given task.
//            (3) Update the cache with slot locked status.
//            (4) Update the Task status. (PROCESSING)



//<ClientId, TaskId>,
//<ClientId, TotalRunningTaskCount>
// <TaskId, ClientId>


   // UsageCache

//    findCurrentOpenSlot() List<SlotId>
//    tryToAcquireASlot(SlotId id)
//    tryToReleaseLock(SlotId)


//    ClientIdentifier
  //  getClientId(Task taskRequest)  String (UUID)


//    TaskCallbackUpdater

//    SubscribeToAQueue
//    Accepting all completion Report from Slots
//    CompletionReport:  TaskId
//    Which CustomerId this taskId belong to and submit the Invoke registered callback.
//            tryToReleaseLock(SlotId)





  //          —-------------- C O D E —-------------------

    // Final Map<String, TaskStatus> taskStatusMap = new HashMap<>();
    // Final Map<String, String> taskClientMap = new HashMap<>();


    /*ENUM TaskStatus {
        SUBMITTED,
        PROCESSING,
        COMPLETED
    }*/

//    Class JobTask extends Runnable {
//        Public run() {
//
//        }
//    }

//    Interface ClientIdentifier<T> {
//        Public String getClientId(T taskType);
//    }
//    Interface JobScheduler<T> {
//        Public Future <T> postTask(T task);
//    }
//
//    Interface UsageCache {
//
//        Integer findCurrentOpenSlot();
//        Boolean tryToAcquireASlot(Integer id);
//        Boolean tryToReleaseLock(Integer id);
//    }

//    Class CPUSlotUsageCache implements UsageCache {
//        List<Boolean> slotIdCache = new ArrayList<>();
//
//        CPUSlotUsageCache () {
//            Int totalCore = System.getAvailableSlot();
//            for(int i=0; i < totalCore; i++) {
//                slotId.add(Boolean.TRUE);
//            }
//        }
//
//        Integer findCurrentOpenSlot() {
//            Int counter = 0;
//            while(counter < slotIdCache.siz()) {
//                if(slotIdCache.get(counter++) == Boolean.TRUE) return counter;
//            }
//            return -1;
//        }
//
//        Boolean synchronized tryToAcquireASlot(Integer id) {
//            if(slotIdCache.get(id) == Boolean.TRUE) {
//                slotIdCache.add(id, Boolean.False);
//            } else {
//                Return false;
//            }
//        }
//        Boolean synchronized tryToReleaseLock(Integer id) {
//            if(slotIdCache.get(id) == Boolean.FALSE) {
//                slotIdCache.add(id, Boolean.TRUE);
//            } else {
//                Return false;
//            }
//        }
//
//    }

//    Class ClientIdentiferBuilder<T> implements ClientIdentifier {
//        String getClientId(T taskType) {
//            return hash(taskType); //MD5
//        }
//    }

//    Class TaskJobScheduler<T> implements JobScheduler {
//
//        final ClientIdentiferBuilder idBuilder = new ClientIdentiferBuilder();
//
//        Public Future <T> postTask(T task) {
//            final String clientId = dBuilder.getClientId(task);
//            Final String taskId = UUID.random();
//            taskStatusMap.put(taskId, TaskStatus.SUBMITTED);
//            taskClientMap.put(taskId, clientId);
//            acceptTheTask(task);
//        }
//
//        Private void acceptTheTask(T task) {
//            Integer currentOpenSLot = findCurrentOpenSlot();
//            while(true) {
//                Boolean status = tryToAcquireASlot(currentOpenSLot);
//                if(status) {
//                    taskStatusMap.put(taskId, TaskStatus.PROCESSING);
//                    break;
//                }
//                currentOpenSLot = findCurrentOpenSlot()
//            }
//
//        }
//    }

// Mutex / Semaphore / CAS

}

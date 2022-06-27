// Banks
// 26 Bank (Fake Association)
// Implement Netting Function
// List of <Transaction>
// Transaction : A => B  $10

// Input : A ==> B ==> $$$$ {Transaction}
// Input is a list of Transaction
// Output is a List of Transaction
// 26 banks , each bank has a relation with other 25 bank A ==> B  ==  B ==> A
// A ==> B 10$ ==> AB10
// B ==> A -10$ ==> AB10

import org.junit.Assert;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


// Central Bank algo ==> One bank will be central (lets "A")
public class Plaid {

    final static Map<BANK, Integer> netBalanceMap = new HashMap<>();

    enum BANK {
        A, B, C, D, E, F
    }

    static class Transaction {
        BANK sender;
        BANK receiver;
        Integer amount; //BigInteger

        Transaction(BANK sender, BANK receiver, Integer amount) {
            this.sender = sender;
            this.receiver = receiver;
            this.amount = amount;
        }
    }

    static List<String> processNettingWithString(List<String> input) {
        for(String eachT: input) {
            String sender = eachT.substring(0, 1);
            String receiver = eachT.substring(1, 2);
            Integer amount = Integer.valueOf(eachT.substring(2));

            handleSender(BANK.valueOf(sender), amount);
            handleReceiver(BANK.valueOf(receiver), amount);
        }
        List<Transaction> output =  processMinimumTransaction();

        List<String> transactionList = new ArrayList<>();
        for(Transaction eachT: output) {
            String transactionOutPut = eachT.sender.name() + eachT.receiver.name() + Math.abs(eachT.amount);
            transactionList.add(transactionOutPut);
        }
        return transactionList;
    }

    static List<Transaction> processNetting (List<Transaction> input) {
        for(Transaction eachT: input) {
            BANK sender = eachT.sender;
            BANK receiver = eachT.receiver;
            Integer amount = eachT.amount;

            handleSender(sender, amount);
            handleReceiver(receiver, amount);
        }
        return processMinimumTransaction();
    }

    private static List<Transaction> processMinimumTransaction() {
        final List<Transaction> output = new ArrayList<>();

        for(Map.Entry<BANK, Integer> entry : netBalanceMap.entrySet()) {
            BANK currentBank = entry.getKey();
            Integer outstandingValue = entry.getValue();

            final Transaction newTransaction;

            if(!currentBank.equals(BANK.A)) {
                if (outstandingValue > 0) {
                    newTransaction = new Transaction(BANK.A, currentBank, outstandingValue);
                } else {
                    newTransaction = new Transaction(currentBank, BANK.A, outstandingValue);
                }
                output.add(newTransaction);
            }
        }
        return output;
    }

    private static void handleSender(BANK sender, Integer amount) {
        if(netBalanceMap.containsKey(sender)) {
            netBalanceMap.put(sender, netBalanceMap.get(sender) - amount);
        } else {
            netBalanceMap.put(sender, -amount);
        }
    }

    private static void handleReceiver(BANK receiver, Integer amount) {
        if(netBalanceMap.containsKey(receiver)) {
            netBalanceMap.put(receiver, netBalanceMap.get(receiver) + amount);
        } else {
            netBalanceMap.put(receiver, amount);
        }
    }

    public static void main(String[] args) {
        Transaction first = new Transaction(BANK.A, BANK.B, 1);
        Transaction second = new Transaction(BANK.A, BANK.C, 2);
        Transaction third = new Transaction(BANK.A, BANK.D, 1);

        List<Transaction> input = new ArrayList<>();

        List<String> inputS = new ArrayList<>();
        input.add(first); inputS.add("AB1");
        input.add(second); inputS.add("AC2");
        input.add(third); inputS.add("AD1");

        List<Transaction> output = processNetting(input);

        netBalanceMap.clear();

        List<String> outputS = processNettingWithString(inputS);

        for(Transaction transaction: output) {
            System.out.println(transaction.sender.name() + transaction.receiver.name() + Math.abs(transaction.amount));
            Assert.assertEquals(transaction.sender.name(), BANK.A.name());
        }

        System.out.println("");

        for(String transaction: outputS) {
            System.out.println(transaction);
        }
    }

    // Test Case:
    //1. Empty List of Transaction ==> Response will be Empty
    //2. With just one transaction ==> Response will be same
    //3. If we have more than one transaction==>
    //3.1 Input with all sender as 'A'  ==> each transaction receiver will be "A"
    //3.2 Input with all receiver as 'A' ==>  each transaction sender will be "A"
    //3.3
}


package org.example;

import org.example.handlers.AuthorizationHandler;
import org.example.handlers.CaptureHandler;
import org.example.handlers.RefundHandler;
import org.example.handlers.ReversalHandler;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * What is this ? Developing a Transaction Processor for a payments system
 * Why? Used for practicing creating a simple transaction processor for interviews
 * How? Parsing is done through a file,
 */

public class TransactionProcessorApp {
    ProcessReport processReport = new ProcessReport();
    Parser parser = new Parser();
    Validator validator = new Validator();
    IdempotencyStore idempotencyStore = new IdempotencyStore();
    InMemoryState state = new InMemoryState();

    AuthorizationHandler authorizationHandler = new AuthorizationHandler(state);
    ReversalHandler reversalHandler = new ReversalHandler(state);

    CaptureHandler captureHandler = new CaptureHandler(state);

    RefundHandler refundHandler = new RefundHandler(state);

    public ProcessReport processTransactions(String[] transactions, String processType) {
//        parser = new Parser();
//        validator = new Validator();
        String result;
        List<TransactionMsg> sortedTransactions = new ArrayList<>();

        System.out.println("Starting Transaction Processor");
        for (String transaction : transactions) {
            // System.out.print("input: ");
            // System.out.println(transaction);
            processReport.inputs.add(transaction);
            TransactionMsg msg = null;
            try {
                if(processType.equals("csv")){
                    msg = parser.parseCSV(transaction);
                }

            } catch (Exception e) {
                System.out.println("PARSING_ERROR: " + e.getMessage());
                processReport.failures.add(e.getMessage());
                continue;
            }

            try {
                validator.validateCSV(msg);
            } catch (Exception e) {
                System.out.println("VALIDATION_ERROR" + e.getMessage());
                processReport.failures.add(e.getMessage());
                continue;

            }
            //System.out.println("parsing & validation successful");
            //How do we make sure we maintain consistency ? No duplicates ?
            //Here we can add sorting logic to make sure everything is ok
            sortedTransactions.add(msg);



    }
        sortedTransactions.sort(Comparator.comparingLong(transaction -> transaction.transactionTimeInMilliseconds));
        for (TransactionMsg sortedTransactionMsg: sortedTransactions){
            if (!idempotencyStore.wasProcessed(sortedTransactionMsg.msgId)) {
                System.out.println(sortedTransactionMsg.msgId + " was processed");
                idempotencyStore.markProcessed(sortedTransactionMsg.msgId);
            } else {
                System.out.println("DUPLICATE: " + sortedTransactionMsg.msgId);
                processReport.duplicates.add("DUPLICATE FOUND: " + sortedTransactionMsg.msgId);
            }
            //handlers !

            switch (sortedTransactionMsg.transactionType) {
                case AUTHORIZATION:
                    System.out.println("found authorization");
                    result = authorizationHandler.handle(sortedTransactionMsg);
                    System.out.println("result is: " + result);
                    processReport.results.add(sortedTransactionMsg.msgId + ":" +result);
                    break;
                case REVERSAL:
                    System.out.println("found reversal");
                    result = reversalHandler.handle(sortedTransactionMsg);
                    processReport.results.add((sortedTransactionMsg.msgId+ ":"+ result));
                case CAPTURE:
                    System.out.println(" found capture");
                    result = captureHandler.handle(sortedTransactionMsg);
                    processReport.results.add(sortedTransactionMsg.msgId+":"+result);
                    break;
                case REFUND:
                    System.out.println("found refund");
                    result = refundHandler.handle(sortedTransactionMsg);
                    processReport.results.add(sortedTransactionMsg.msgId+":"+result);
                    break;
                default:
                    System.out.println("found unknown");
//                    result = refundHandler.handle(msg);

            }


        }
        System.out.println("end inputs");
        return processReport;

    }
}

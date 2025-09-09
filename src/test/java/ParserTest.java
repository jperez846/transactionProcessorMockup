import org.example.Parser;
import org.example.TransactionMsg;
import org.example.TransactionType;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*; // import static so we do not need to append the class name to the static methods I want to use
public class ParserTest {
    private Parser parser;
    private TransactionMsg transactionMsg;
    @Test
    public void csvParserTest(){
        parser = new Parser();
        String csvInput = "msg-1,AUTHORIZATION,order-1,acct-1,10000,USD,1620000000000";
        transactionMsg = parser.parseCSV(csvInput);
        System.out.println("finished parsing the csv file");
        assertTrue(transactionMsg.getMsgId().equals("msg-1"));
        assertTrue(transactionMsg.getCorrelationId().equals("order-1"));
        assertTrue(transactionMsg.getTransactionType().equals(TransactionType.AUTHORIZATION));
        assertTrue(transactionMsg.getCurrencyType().equals("USD"));
        assertTrue(transactionMsg.getPaymentAmountMin() == 10000);
    }
    @Test
    public void jsonParserTest(){
        parser = new Parser();
        String json = "{"
                + "\"msgId\":\"msg-1\","
                + "\"transactionType\":\"AUTHORIZATION\","
                + "\"correlationId\":\"order-1\","
                + "\"accountId\":\"acct-1\","
                + "\"currencyType\":\"USD\","
                + "\"paymentAmountMin\":10000,"
                + "\"transactionTimeInMilliseconds\":1620000000000"
                + "}";
        transactionMsg = parser.parseJSON(json);
        System.out.println("json parser test" + transactionMsg.getMsgId());
        assertTrue(transactionMsg.getMsgId().equals("msg-1"));
        assertTrue(transactionMsg.getCorrelationId().equals("order-1"));
        assertTrue(transactionMsg.getTransactionType().equals(TransactionType.AUTHORIZATION));
        assertTrue(transactionMsg.getCurrencyType().equals("USD"));
        assertTrue(transactionMsg.getPaymentAmountMin() == 10000);




    }
    @Test
    public void stringParserTest(){
        parser = new Parser();
        String bankingString = "10100712345670000200000";
        transactionMsg = parser.parseString(bankingString);
        //transactionMsg.getMsgId();
        System.out.println("String parser finished");
        assertTrue(transactionMsg.getMsgId().equals("1010"));
        assertTrue(transactionMsg.getTransactionType().equals(TransactionType.DEPOSIT));
        assertTrue(transactionMsg.getAccountId().equals("1234567"));
        assertTrue(transactionMsg.getCorrelationId().equals("1234567"));
        assertTrue(transactionMsg.getCurrencyType().equals("USD"));
        assertTrue(transactionMsg.getPaymentAmountMin() == 200000);




    }

}

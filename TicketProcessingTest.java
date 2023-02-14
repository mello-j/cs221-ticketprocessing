package lesson;

import org.testng.annotations.Test;
import static org.testng.Assert.*;

import java.io.FileNotFoundException;

/**
 * Tests TicketProcessing class
 *  
 * Verifies the following test cases:
 * - TicketProcessing can successfully read a file containing one ticket
 * - TicketProcessing's toString method returns a valid string
 * - TicketProcessing's getRevenue method returns valid revenue as double
 * - TicketProcessing's totalTickets sold method returns a valid int
 * - TicketProcessing can successfully read and create new ticket file
 * - TicketProcessing can read non-existent file and throws FileNotFoundException
 * - TicketProcessing can empty ticket file and throws IllegalArgumentException
 * - TicketProcessing can successfully throw InvalidDate exception for day
 * - TicketProcessing can successfully throw InvalidDate exception for month
 * - TicketProcessing can successfully throw InvalidDate exception for year
 * - TicketProcessing can successfully throw InvalidLocation exception for zone
 * - TicketProcessing can successfully throw InvalidLocation exception for box
 * - TicketProcessing throws correct message with InvalidLocation exceptions
 * - TicketProcessing can successfully throw IllegalArgument exception for missing Event Data
 * - TicketProccessing throws correct message with Illegal Argument exceptions
 * - TicketProcessing can successfully throw IllegalArgument exception for missing Date Data
 * - TicketProcessing can successfully throw IllegalArgument exception for missing Name Data
 * - TicketProcessing can successfully throw IllegalArgument exception for missing Location Data
 * @author krodgers, Justin Mello
 */ 
public class TicketProcessingTest {

    // Base cost of a ticket
    private final double BASE_COST = 16;

    /**
     * Helper method to test total tickets and total revenue is correct
     * Is not a stand alone test method, but is to be called from other
     * tests.  
     * 
     * DO NOT MODIFY THIS METHOD
     *
     * @param actual - TicketProcessing object for which to test getters
     * @param expectedTickets - the expected total number of tickets
     * @param expectedTotalRevenue - the expected total revenue
     */
    private void getterHelperTest(TicketProcessing actual, int expectedTickets,
				  double expectedTotalRevenue)
    {
	    assertEquals(actual.totalTicketsSold(), expectedTickets);
	    assertEquals(actual.getTotalRevenue(), expectedTotalRevenue);
    }

    // ensure valid file doesn't throw any exceptions
    @Test
    public void validFileOneTicketTest() throws FileNotFoundException
    {
        TicketProcessing process = new TicketProcessing("validOneTicketData.txt");
	    getterHelperTest(process, 1, BASE_COST * 2);
    }

    // verify toString is correct
    @Test
    public void toStringTest() throws FileNotFoundException
    {
        TicketProcessing process = new TicketProcessing("validMultipleTicketData.txt");
        String expected = "Total number of tickets sold: 3\nTotal Revenue: $192.00\nTotal Sports Revenue: $64.00\nTotal Performance Revenue: $32.00\nTotal Concert Revenue: $96.00";
        assertEquals(process.toString(), expected);
    }

    // Tests default revenue values are correct
    @Test
    public void getRevenueTest() throws FileNotFoundException
    {
        TicketProcessing process = new TicketProcessing("validMultipleTicketData.txt");
        double expectedCost = BASE_COST * 12;
        assertEquals(process.totalTicketsSold(), 3, "Total tickets incorrect");
        assertEquals(process.getTotalRevenue(), expectedCost, "Total revenue incorrect");
        assertEquals(process.getRevenue(TicketInterface.Event.SPORT), BASE_COST * 4);
        assertEquals(process.getRevenue(TicketInterface.Event.CONCERT), BASE_COST * 6);
        assertEquals(process.getRevenue(TicketInterface.Event.PERFORMANCE), BASE_COST * 2);
    }

    // verify reading a new file doesn't throw exceptions
    // verify data has been cleared
    @Test
    public void newTicketFileTest() throws FileNotFoundException
    {
	TicketProcessing process = new TicketProcessing("validOneTicketData.txt");
    	process.readTicketData("validMultipleTicketData.txt");
	    getterHelperTest(process, 3, BASE_COST * 12);
    }
    
    
    // read from file that does not exist
    // ensure FileNotFoundException is thrown
    @Test(expectedExceptions = FileNotFoundException.class)
    public void fileMissingTest() throws FileNotFoundException
    {
    	TicketProcessing process = new TicketProcessing("dne.txt");
    }

    // Should throw an IllegalArgumentException - it's missing data
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void emptyTicketFileTest() throws FileNotFoundException
    {
        TicketProcessing process = new TicketProcessing("emptyTicketData.txt");
    }

    // read from invalid file
    // ensure invalid date exception thrown correctly
    @Test(expectedExceptions = InvalidDateException.class)
    public void invalidDateDayTest() throws FileNotFoundException
    {
        TicketProcessing process = new TicketProcessing("invalidDateDayData.txt");
    }

    // read from invalid file
    // ensure invalid date exception thrown correctly
    @Test(expectedExceptions = InvalidDateException.class)
    public void invalidDateMonthTest() throws FileNotFoundException
    {
        TicketProcessing process = new TicketProcessing("invalidDateMonthData.txt");
    }

    // read from invalid file
    // ensure invalid date exception thrown correctly
    @Test(expectedExceptions = InvalidDateException.class)
    public void invalidDateYearTest() throws FileNotFoundException
    {
        TicketProcessing process = new TicketProcessing("invalidDateYearData.txt");
    }


    
    // read from invalid file
    // ensure NoSuchLocationException thrown correctly
    @Test(expectedExceptions = NoSuchLocationException.class)
    public void noSuchZoneTest() throws FileNotFoundException
    {
	TicketProcessing process = new TicketProcessing("invalidZoneData.txt");
    }

    // read from invalid file
    // ensure NoSuchLocationException thrown correctly
    // ensure NoSuchLocationException message is correct
    @Test
    public void noSuchBoxTest() throws FileNotFoundException
    {
	try{
            TicketProcessing process = new TicketProcessing("invalidBoxData.txt");
        } catch(NoSuchLocationException e){
            assertEquals(e.getMessage(), "NoSuchLocationException: Box or Zone value is invalid");
        }
    }


    // read from file with missing event
    // ensure IllegalArgumentException is thrown
    // ensure IllegalArgumentException has the correct message
    @Test
    public void fileMissingEventTest() throws FileNotFoundException
    {
        try
        {
            TicketProcessing process = new TicketProcessing("invalidMissingEventData.txt");
        } 
        catch(IllegalArgumentException e) 
        {
            assertEquals(e.getMessage(), "Datafile is missing information");
        }
    }

    /**
       For each method, choose one of invalidMissingDateData.txt,
       invalidMissingNameData.txt, or invalidMissingLocationData.txt
       as appropriate.  You should use each file exactly once. 

       Construct a TicketProcessing object to ensure the proper
       exception is thrown.
     */

    
    // read from file with missing date
    // ensure IllegalArgumentException is thrown
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void fileMissingDateTest() throws FileNotFoundException
    {
        TicketProcessing process = new TicketProcessing("invalidMissingDateData.txt");
    }
    
    // read from file with missing name
    // ensure IllegalArgumentException is thrown
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void fileMissingNameTest() throws FileNotFoundException
    {
        TicketProcessing process = new TicketProcessing("invalidMissingNameData.txt");
    }
    

    // read from file with missing seat/box
    // ensure IllegalArgumentException is thrown
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void fileMissingLocationTest() throws FileNotFoundException
    {
        TicketProcessing process = new TicketProcessing("invalidMissingLocationData.txt");
    }
}

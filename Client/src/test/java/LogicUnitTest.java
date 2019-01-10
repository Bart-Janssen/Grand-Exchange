import org.junit.Before;
import org.junit.Test;
import sample.Factory.ClientFactory;
import sample.Logic.GrandExchangeSendLogic;
import sample.Logic.ICalculateLogic;
import sample.Logic.IGrandExchangeSendLogic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class LogicUnitTest
{
    private ICalculateLogic logic;
    private String input;
    private int itemPrice;

    @Before
    public void setup()
    {
        logic = ClientFactory.getInstance().makeNewCalculateLogic();
    }

    @Test
    public void testPriceInputValid()
    {
        input = "15000";
        itemPrice = 10000;
        String result = logic.checkPriceInput(input, itemPrice);
        assertEquals("15.000", result);
    }

    @Test
    public void testPriceInputNotValid()
    {
        input = "not valid price";
        itemPrice = 10000;
        String result = logic.checkPriceInput(input, itemPrice);
        assertEquals("10.000", result);
    }

    @Test
    public void testPriceValidInputHigherThenIntLength()
    {
        input = "123456789123456";
        itemPrice = 10000;
        String result = logic.checkPriceInput(input, itemPrice);
        assertEquals("2.147.483.647", result);
    }

    @Test
    public void testPriceInputNotValidNegative()
    {
        input = "-1";
        itemPrice = 10000;
        String result = logic.checkPriceInput(input, itemPrice);
        assertEquals("10.000", result);
    }

    @Test
    public void testLetter_K_ForThousand()
    {
        input = "20k";
        itemPrice = 10000;
        String result = logic.checkPriceInput(input, itemPrice);
        assertEquals("20.000", result);
    }

    @Test
    public void testCapitalLetter_K_ForThousand()
    {
        input = "20K";
        itemPrice = 10000;
        String result = logic.checkPriceInput(input, itemPrice);
        assertEquals("20.000", result);
    }

    @Test
    public void testLetter_M_ForMillion()
    {
        input = "20m";
        itemPrice = 10000;
        String result = logic.checkPriceInput(input, itemPrice);
        assertEquals("20.000.000", result);
    }

    @Test
    public void testCapitalLetter_M_ForMillion()
    {
        input = "20M";
        itemPrice = 10000;
        String result = logic.checkPriceInput(input, itemPrice);
        assertEquals("20.000.000", result);
    }

    @Test
    public void testMultiple_K_Letters()
    {
        input = "20kk";
        itemPrice = 10000;
        String result = logic.checkPriceInput(input, itemPrice);
        assertEquals("10.000", result);
    }

    @Test
    public void testMultipleCapital_K_Letters()
    {
        input = "20KK";
        itemPrice = 10000;
        String result = logic.checkPriceInput(input, itemPrice);
        assertEquals("10.000", result);
    }

    @Test
    public void testMultiple_M_Letters()
    {
        input = "20mm";
        itemPrice = 10000;
        String result = logic.checkPriceInput(input, itemPrice);
        assertEquals("10.000", result);
    }

    @Test
    public void testMultipleCapital_M_Letters()
    {
        input = "20MM";
        itemPrice = 10000;
        String result = logic.checkPriceInput(input, itemPrice);
        assertEquals("10.000", result);
    }

    @Test
    public void testLetter_K_OnWrongPlace()
    {
        input = "2k0";
        itemPrice = 10000;
        String result = logic.checkPriceInput(input, itemPrice);
        assertEquals("10.000", result);
    }

    @Test
    public void testLetter_M_OnWrongPlace()
    {
        input = "2m0";
        itemPrice = 10000;
        String result = logic.checkPriceInput(input, itemPrice);
        assertEquals("10.000", result);
    }

    @Test
    public void testDecimalInput()
    {
        input = "10,0";
        itemPrice = 10000;
        String result = logic.checkPriceInput(input, itemPrice);
        assertEquals("10.000", result);
    }

    @Test
    public void testValidPriceWithDotsInInput()
    {
        input = "20.000";
        itemPrice = 10000;
        String result = logic.checkPriceInput(input, itemPrice);
        assertEquals("20.000", result);
    }

    @Test
    public void testValidInputWithDotOnWrongPlace()
    {
        input = "10.00";
        itemPrice = 10000;
        String result = logic.checkPriceInput(input, itemPrice);
        assertEquals("1.000", result);
    }

    @Test
    public void testUnValidInputExtremelyLongInput()
    {
        input = "sdkjhfdjhgkshkjsdhfgsildgujfiksdgflksdjgflkdsjgklsdjgklsdjgklsdjgsdkjhfdjhgkshkjsdhfgsildgujfiksdgflksdjgflkdsjgklsdjg" +
                "klsdjgklsdjgsdkjhfdjhgkshkjsdhfgsildgujfiksdgflksdjgflkdsjgklsdjgklsdjgklsdjgsdkjhfdjhgkshkjsdhfgsildgujfiksdgflksdjgf" +
                "lkdsjgklsdjgklsdjgklsdjgsdkjhfdjhgkshkjsdhfgsildgujfiksdgflksdjgflkdsjgklsdjgklsdjgklsdjgsdkjhfdjhgkshkjsdhfgsildgujfi" +
                "ksdgflksdjgflkdsjgklsdjgklsdjgklsdjgsdkjhfdjhgkshkjsdhfgsildgujfiksdgflksdjgflkdsjgklsdjgklsdjgklsdjgzkfcjksdhjkshfsdj";
        itemPrice = 10000;
        String result = logic.checkPriceInput(input, itemPrice);
        assertEquals("10.000", result);
    }
}
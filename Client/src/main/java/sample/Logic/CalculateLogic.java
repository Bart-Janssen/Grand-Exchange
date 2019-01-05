package sample.Logic;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CalculateLogic implements ICalculateLogic
{
    public String checkPriceInput(String newPrice, int oldPrice)
    {
        if (!newPrice.isEmpty())
        {
            if (newPrice.matches("\\d{0,10000}([.]\\d{0,4})?[MmKk]?"))
            {
                if (newPrice.contains("m")) newPrice = newPrice.replace("m", "000000");
                if (newPrice.contains("M")) newPrice = newPrice.replace("M", "000000");
                if (newPrice.contains("k")) newPrice = newPrice.replace("k", "000");
                if (newPrice.contains("K")) newPrice = newPrice.replace("K", "000");
                if (newPrice.length() <= 10)
                {
                    if (newPrice.contains(".")) newPrice = newPrice.replace(".", "");
                    if (Long.parseLong(newPrice) < Integer.MAX_VALUE)
                    {
                        newPrice = NumberFormat.getIntegerInstance().format(Integer.parseInt(newPrice));
                        return newPrice;
                    }
                }
                newPrice = Integer.toString(Integer.MAX_VALUE);
                return newPrice;
            }
        }
        return Integer.toString(oldPrice);
    }

    public String makeMessageWithDate(String message)
    {
        return "[" + new SimpleDateFormat("hh:mm:ss").format(Calendar.getInstance().getTime()) + "]: " + message;
    }
}
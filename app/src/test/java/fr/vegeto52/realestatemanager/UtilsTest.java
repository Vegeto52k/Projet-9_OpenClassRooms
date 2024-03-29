package fr.vegeto52.realestatemanager;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import fr.vegeto52.realestatemanager.utils.Utils;

/**
 * Created by Vegeto52-PC on 11/01/2024.
 */
public class UtilsTest {

    @Test
    public void testConvertEuroToDollar() {
        int euros = 100;
        int expectedDollars = (int) Math.round(euros / 0.812);

        int actualDollars = Utils.convertEurotoDollar(euros);

        assertEquals(expectedDollars, actualDollars);
    }

    @Test
    public void testGetTodayDate2() throws ParseException {
        String expectedDateFormat = "dd/MM/yyyy";
        DateFormat dateFormat = new SimpleDateFormat(expectedDateFormat);

        String expectedDateString = dateFormat.format(new Date());
        String actualDateString = Utils.getTodayDate2();

        assertEquals(expectedDateString, actualDateString);

        Date actualDate = dateFormat.parse(actualDateString);
        assert actualDate != null;
        String reconvertedDateString = dateFormat.format(actualDate);

        assertEquals(expectedDateString, reconvertedDateString);
    }
}

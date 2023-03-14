package MainPackage;

/*   enhetsTest ÅTER-DATUM       */

import javax.microedition.midlet.MIDlet;
import java.util.Calendar;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Display;
import java.util.TimeZone;
import java.util.Date;
import javax.microedition.lcdui.DateField;
import javax.microedition.lcdui.TextField;
import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.AlertType;

public class DateTEST
    extends MIDlet implements CommandListener {

  // FIELDS.......FOR PRIVATE INSTANCE OR ATTRIBUTE....................

  private Calendar calender;
  private Command okeyCommand, exitCommand;
  private Form dateForm;
  private Display displayen;
  //private StringItem msg = null;
  private Date today;
  private DateField datefield;
  private TextField textFieldClockDate, textFieldDate;
  private TimeZone tz = TimeZone.getTimeZone("GMT+1");
  private Alert alertError;
  private String mounth, inputMounth, inputDate, year, mobileDay;
  private int prefix = -1;

  //private int v = calender.get(Calendar.DATE);

  private String Jan = "Jan", Feb = "Feb", Mar = "Mar", Apr = "Apr", May =
      "May",
  Jun = "Jun", Jul = "Jul", Aug = "Aug", Sep = "Sep", Oct = "Oct", Nov = "Nov",
  Dec = "Dec";

  private String JanDa = "01", FebDa = "02", MarDa = "03", AprDa = "04", MajDa =
      "05",
  JunDa = "06", JulDa = "07", AugDa = "08", SepDa = "09", OktDa = "10", NovDa =
      "11", DecDa = "12";

  private int JanD = 31, FebD = 28, MarD = 31, AprD = 30, MajD = 31,
  JunD = 30, JulD = 31, AugD = 31, SepD = 30, OktD = 31, NovD = 30, DecD = 31;

  public DateTEST() { // constructor

    this.tz = tz;
    calender = Calendar.getInstance(tz);

    dateForm = new Form("Åter Datum! ");

    textFieldClockDate = new TextField("hhmm: Ange Tid!", "", 4,
                                       TextField.NUMERIC);
    textFieldDate = new TextField("ddmm: Ange Datum!", "", 4, TextField.NUMERIC);
    today = new Date();
    today.getTime();

    System.out.println(today);
    today.toString();
    datefield = new DateField("Tid och Datum", DateField.DATE_TIME);
    datefield.setDate(today);

    dateForm.append(textFieldDate);
    dateForm.append(textFieldClockDate);
    dateForm.append(datefield);

    okeyCommand = new Command("Set Time", Command.OK, 1);
    exitCommand = new Command("Exit", Command.EXIT, 2);

    dateForm.addCommand(okeyCommand);
    dateForm.addCommand(exitCommand);
    dateForm.setCommandListener(this);

  }

  public void clockTryAgain() {
    Alert alertError = new Alert("InputNumber Incorrect", "Please try again", null,
                                 AlertType.ERROR);
    alertError.setTimeout(Alert.FOREVER);
    textFieldClockDate.setString("");

    displayen.setCurrent(alertError, dateForm);
  }
  public String toString(String b) {

    String s = b;

    return s;
  }
  public void setConvertInt(int totalMinutes) {

    int convertINT = totalMinutes;

    StringBuffer checkMobileNumber = new StringBuffer(convertINT); // convert int to string-object

    String sendToSMS = checkMobileNumber.toString().trim();

    //sendSMS(sendToSMS);

  }


  public void setDate() throws NumberFormatException {

    String mobileClock = today.toString(); // Tilldelar mobileClock 'todays' datumvärde, skickar och gör om till en string av java.lang.string-typ

    String inputTimMin = textFieldClockDate.getString();

    String setMobileInputTim = inputTimMin.substring(0, 2); // plockar ut 'TIM' tecken ur klockan
    String setMobileInputMin = inputTimMin.substring(2, 4); // plockar ut 'TIM' tecken ur klockan

    String convertSetMobileIputTim = setMobileInputTim.trim();
    String convertSetMobileIputMin = setMobileInputMin.trim();

    String setMobileDateCharacterPart1 = mobileClock.substring(11, 13); // plockar ut 'TIM' tecken ur klockan
    String setMobileDateCharacterPart2 = mobileClock.substring(14, 16); // plockar ut 'MIN' tecken ur klockan

    Integer convertInputMobilePartNumber11 = new Integer(0); // Gör om strängar till integer
    int mobileInputTIM = convertInputMobilePartNumber11.parseInt(
        convertSetMobileIputTim);

    Integer convertInputMobilePartNumber12 = new Integer(0); // Gör om strängar till integer
    int mobileInputMIN = convertInputMobilePartNumber12.parseInt(
        convertSetMobileIputMin);

    Integer convertMobilePartNumber11 = new Integer(0); // Gör om strängar till integer
    int mobileTIM = convertMobilePartNumber11.parseInt(
        setMobileDateCharacterPart1);

    Integer convertMobilePartNumber12 = new Integer(0); // Gör om strängar till integer
    int mobileMIN = convertMobilePartNumber12.parseInt(
        setMobileDateCharacterPart2);

    int dygn = 1440;
    int minut = 60;

    int summaBerakning1 = (mobileTIM * minut) + mobileMIN;
    int summaInputclock = (mobileInputTIM * minut) + mobileInputMIN;

    System.out.println("visar summan av tiden input > " + summaInputclock);

    int xMinuter = dygn - summaBerakning1; // xMinuter innehåller första delen i beräkningen.
    System.out.println("visar summa av xmin " + xMinuter);

    String setMobileDateMounth = mobileClock.substring(4, 7); // plockar ut 'MÅNAD' tecken ur klockan

    String dateField = textFieldDate.getString();

    String setInputDateCharacterPart1 = dateField.substring(0, 2); // plockar ut 'DAY' tecken ur input date

    String setInputDateCharacterPart2 = dateField.substring(2, 4); // plockar ut 'Månad' tecken ur input date

    String setInputyear = mobileClock.substring(24, 28); // plockar ut 'year' tecken ur klockan

    String setInputDay = mobileClock.substring(8, 11); // plockar ut 'year' tecken ur klockan

    this.year = setInputyear; // används för skottår
    this.inputDate = setInputDateCharacterPart1; // görs om till en int för att räkna med
    this.inputMounth = setInputDateCharacterPart2; // används för att jämföra med
    this.mounth = setMobileDateMounth; // används för att jämföra med
    this.mobileDay = setInputDay.trim();

    Integer convertInputDate = new Integer(0); // Gör om strängar till integer
    int checkInputDate = convertInputDate.parseInt(inputDate); // inmatat datum

    Integer convertMobileDay = new Integer(0); // Gör om strängar till integer
    int checkMobileDay = convertMobileDay.parseInt(mobileDay); // klockans dag to integer

// -------------------------- J A N U A R I ------------------------------------------------------------------------------------------

    if (mounth.trim().equals(Jan) && inputMounth.trim().equals(JanDa)) { // om Jan är lika med Jan 'och' inputen är lika med 01 (januari)

      int summaJanToJanDays = checkInputDate - checkMobileDay - 1; // jan = 31 dagar  - 1 - inskrivet värde

      if (summaJanToJanDays == 0 || summaJanToJanDays < 0) { // om värdet är noll så utför följande kod

        int totalMinutes = xMinuter + summaInputclock; // plus delsumma 3
        setConvertInt(totalMinutes);

        System.out.println(
            "Totala summan i minuter fram till midnatt om dagar är noll > " +
            totalMinutes);

      }
      else { // om värdet är mer än noll så utför följande kod....

        int summaJanToJanMinutes = summaJanToJanDays * 24 * 60;
        int SummaJanToJanTotalMinutes = summaJanToJanMinutes + xMinuter +
            summaInputclock; // plus delsumma 3
        setConvertInt(SummaJanToJanTotalMinutes);

        System.out.println("Summan av i minuter i jan - jan > " +
                           SummaJanToJanTotalMinutes);

      }
    }
    else if (mounth.trim().equals(Jan) && inputMounth.trim().equals(FebDa)) {

      int summaJanToFebDays = 31 - checkMobileDay - 1 + checkInputDate; //checkMobileDay är datumet på mobilen, checkInputDate är det inmatade datumet

      if (summaJanToFebDays == 0 || summaJanToFebDays < 0) { // om värdet är noll så utför följande kod

        int totalMinutes = xMinuter + summaInputclock; // plus delsumma 3
        setConvertInt(totalMinutes);
        System.out.println(
            "Totala summan i minuter fram tills midnat om summa är noll jan - feb > " +
            totalMinutes);

      }
      else {

        int summaJanToFebMinutes = summaJanToFebDays * 24 * 60;
        int SummaJanToFebTotalMinutes = summaJanToFebMinutes + xMinuter +
            summaInputclock; // plus delsumma 3
        setConvertInt(SummaJanToFebTotalMinutes);

        System.out.println("Summan i minuter från jan - feb > " +
                           SummaJanToFebTotalMinutes);
      }

    }
    else if (mounth.trim().equals(Jan) && inputMounth.trim().equals(MarDa)) {

      int summaJanToMarDays = 31 - checkMobileDay - 1 + 28 + checkInputDate;
      int summaJanToMarMinutes = summaJanToMarDays * 24 * 60;
      int SummaJanToMarTotalMinutes = summaJanToMarMinutes + xMinuter +
          summaInputclock; // plus delsumma 3
      setConvertInt(SummaJanToMarTotalMinutes);
      System.out.println("Den totala summan i minuter från jan - mars > " +
                         SummaJanToMarTotalMinutes);

    }

// -------------------------- F E B R U A R I ------------------------------------------------------------------------------------------

    if (mounth.trim().equals(Feb) && inputMounth.trim().equals(FebDa)) { // om Jan är lika med Jan 'och' inputen är lika med 01 (januari)

      int summaFebToFebDays = checkInputDate - checkMobileDay - 1; // feb = 28 dagar  - 1 - inskrivet värde

      if (summaFebToFebDays == 0 || summaFebToFebDays < 0) { // om värdet är noll så utför följande kod

        int totalMinutes = xMinuter + summaInputclock; // plus delsumma 3
        setConvertInt(totalMinutes);

        System.out.println(
            "Totala summan i minuter fram till midnatt om dagar är noll 'februari' > " +
            totalMinutes);

      }
      else { // om värdet är mer än noll så utför följande kod....

        int summaFebToFebMinutes = summaFebToFebDays * 24 * 60;
        int SummaFebToFebTotalMinutes = summaFebToFebMinutes + xMinuter +
            summaInputclock; // plus delsumma 3
        setConvertInt(SummaFebToFebTotalMinutes);

        System.out.println("Summan av i minuter i feb - feb > " +
                           SummaFebToFebTotalMinutes);

      }
    }
    else if (mounth.trim().equals(Feb) && inputMounth.trim().equals(MarDa)) {

      int summaFebToMarDays = 28 - checkMobileDay - 1 + checkInputDate; //checkMobileDay är datumet på mobilen, checkInputDate är det inmatade datumet

      if (summaFebToMarDays == 0 || summaFebToMarDays < 0) { // om värdet är noll så utför följande kod

        int totalMinutes = xMinuter + summaInputclock; // plus delsumma 3
        setConvertInt(totalMinutes);
        System.out.println(
            "Totala summan i minuter fram tills midnat om summa är noll feb - mars > " +
            totalMinutes);

      }
      else {

        int summaFebToMarMinutes = summaFebToMarDays * 24 * 60;
        int SummaFebToMarTotalMinutes = summaFebToMarMinutes + xMinuter +
            summaInputclock; // plus delsumma 3
        setConvertInt(SummaFebToMarTotalMinutes);

        System.out.println("Summan i minuter från feb - mars > " +
                           SummaFebToMarTotalMinutes);
      }

    }
    else if (mounth.trim().equals(Feb) && inputMounth.trim().equals(AprDa)) {

      int summaFebToAprDays = 28 - checkMobileDay - 1 + 31 + checkInputDate;
      int summaFebToAprMinutes = summaFebToAprDays * 24 * 60;
      int SummaFebToAprTotalMinutes = summaFebToAprMinutes + xMinuter +
          summaInputclock; // plus delsumma 3
      setConvertInt(SummaFebToAprTotalMinutes);
      System.out.println("Den totala summan i minuter från Feb - April > " +
                         SummaFebToAprTotalMinutes);

    }

// -------------------------- M A R S ------------------------------------------------------------------------------------------

    if (mounth.trim().equals(Mar) && inputMounth.trim().equals(MarDa)) { // om Jan är lika med Jan 'och' inputen är lika med 01 (januari)

      int summaMarToMarDays = checkInputDate - checkMobileDay - 1; // feb = 28 dagar  - 1 - inskrivet värde

      if (summaMarToMarDays == 0 || summaMarToMarDays < 0) { // om värdet är noll så utför följande kod

        int totalMinutes = xMinuter + summaInputclock; // plus delsumma 3
        setConvertInt(totalMinutes);

        System.out.println(
            "Totala summan i minuter fram till midnatt om dagar är noll 'februari' > " +
            totalMinutes);

      }
      else { // om värdet är mer än noll så utför följande kod....

        int summaMarToMarMinutes = summaMarToMarDays * 24 * 60;
        int SummaMarToMarTotalMinutes = summaMarToMarMinutes + xMinuter +
            summaInputclock; // plus delsumma 3
        setConvertInt(SummaMarToMarTotalMinutes);

        System.out.println("Summan av i minuter i feb - feb > " +
                           SummaMarToMarTotalMinutes);

      }
    }
    else if (mounth.trim().equals(Mar) && inputMounth.trim().equals(AprDa)) {

      int summaMarToAprDays = 31 - checkMobileDay - 1 + checkInputDate; //checkMobileDay är datumet på mobilen, checkInputDate är det inmatade datumet

      if (summaMarToAprDays == 0 || summaMarToAprDays < 0) { // om värdet är noll så utför följande kod

        int totalMinutes = xMinuter + summaInputclock; // plus delsumma 3
        setConvertInt(totalMinutes);
        System.out.println(
            "Totala summan i minuter fram tills midnat om summa är noll feb - mars > " +
            totalMinutes);

      }
      else {

        int summaMarToAprMinutes = summaMarToAprDays * 24 * 60;
        int SummaMarToAprTotalMinutes = summaMarToAprMinutes + xMinuter +
            summaInputclock; // plus delsumma 3
        setConvertInt(SummaMarToAprTotalMinutes);

        System.out.println("Summan i minuter från feb - mars > " +
                           SummaMarToAprTotalMinutes);
      }

    }
    else if (mounth.trim().equals(Mar) && inputMounth.trim().equals(MajDa)) {

      int summaMarToMajDays = 31 - checkMobileDay - 1 + 30 + checkInputDate;
      int summaMarToMajMinutes = summaMarToMajDays * 24 * 60;
      int SummaMarToMajTotalMinutes = summaMarToMajMinutes + xMinuter +
          summaInputclock; // plus delsumma 3
      setConvertInt(SummaMarToMajTotalMinutes);
      System.out.println("Den totala summan i minuter från Feb - April > " +
                         SummaMarToMajTotalMinutes);

    }

    // -------------------------- A P R I L ------------------------------------------------------------------------------------------

    if (mounth.trim().equals(Apr) && inputMounth.trim().equals(AprDa)) { // om Jan är lika med Jan 'och' inputen är lika med 01 (januari)

      int summaAprToAprDays = checkInputDate - checkMobileDay - 1; // feb = 28 dagar  - 1 - inskrivet värde

      if (summaAprToAprDays == 0 || summaAprToAprDays < 0) { // om värdet är noll så utför följande kod

        int totalMinutes = xMinuter + summaInputclock; // plus delsumma 3
        setConvertInt(totalMinutes);

        System.out.println(
            "Totala summan i minuter fram till midnatt om dagar är noll 'februari' > " +
            totalMinutes);

      }
      else { // om värdet är mer än noll så utför följande kod....

        int summaAprToAprMinutes = summaAprToAprDays * 24 * 60;
        int SummaAprToAprTotalMinutes = summaAprToAprMinutes + xMinuter +
            summaInputclock; // plus delsumma 3
        setConvertInt(SummaAprToAprTotalMinutes);

        System.out.println("Summan av i minuter i feb - feb > " +
                           SummaAprToAprTotalMinutes);

      }
    }
    else if (mounth.trim().equals(Apr) && inputMounth.trim().equals(MajDa)) {

      int summaAprToMajDays = 30 - checkMobileDay - 1 + checkInputDate; //checkMobileDay är datumet på mobilen, checkInputDate är det inmatade datumet

      if (summaAprToMajDays == 0 || summaAprToMajDays < 0) { // om värdet är noll så utför följande kod

        int totalMinutes = xMinuter + summaInputclock; // plus delsumma 3
        setConvertInt(totalMinutes);
        System.out.println(
            "Totala summan i minuter fram tills midnat om summa är noll feb - mars > " +
            totalMinutes);

      }
      else {

        int summaAprToMajMinutes = summaAprToMajDays * 24 * 60;
        int SummaAprToMajTotalMinutes = summaAprToMajMinutes + xMinuter +
            summaInputclock; // plus delsumma 3
        setConvertInt(SummaAprToMajTotalMinutes);

        System.out.println("Summan i minuter från feb - mars > " +
                           SummaAprToMajTotalMinutes);
      }

    }
    else if (mounth.trim().equals(Apr) && inputMounth.trim().equals(JunDa)) {

      int summaAprToJunDays = 30 - checkMobileDay - 1 + 31 + checkInputDate;
      int summaAprToJunMinutes = summaAprToJunDays * 24 * 60;
      int SummaAprToJunTotalMinutes = summaAprToJunMinutes + xMinuter +
          summaInputclock; // plus delsumma 3
      setConvertInt(SummaAprToJunTotalMinutes);
      System.out.println("Den totala summan i minuter från Feb - April > " +
                         SummaAprToJunTotalMinutes);

    }

    // -------------------------- M A J ------------------------------------------------------------------------------------------

    if (mounth.trim().equals(May) && inputMounth.trim().equals(MajDa)) { // om Jan är lika med Jan 'och' inputen är lika med 01 (januari)

      int summaMajToMajDays = checkInputDate - checkMobileDay - 1; // feb = 28 dagar  - 1 - inskrivet värde

      if (summaMajToMajDays == 0 || summaMajToMajDays < 0) { // om värdet är noll så utför följande kod

        int totalMinutes = xMinuter + summaInputclock; // plus delsumma 3
        setConvertInt(totalMinutes);

        System.out.println(
            "Totala summan i minuter fram till midnatt om dagar är noll 'februari' > " +
            totalMinutes);

      }
      else { // om värdet är mer än noll så utför följande kod....

        int summaMajToMajMinutes = summaMajToMajDays * 24 * 60;
        int SummaMajToMajTotalMinutes = summaMajToMajMinutes + xMinuter +
            summaInputclock; // plus delsumma 3
        setConvertInt(SummaMajToMajTotalMinutes);

        System.out.println("Summan av i minuter i feb - feb > " +
                           SummaMajToMajTotalMinutes);

      }
    }
    else if (mounth.trim().equals(May) && inputMounth.trim().equals(JunDa)) {

      int summaMajToJunDays = 31 - checkMobileDay - 1 + checkInputDate; //checkMobileDay är datumet på mobilen, checkInputDate är det inmatade datumet

      if (summaMajToJunDays == 0 || summaMajToJunDays < 0) { // om värdet är noll så utför följande kod

        int totalMinutes = xMinuter + summaInputclock; // plus delsumma 3
        setConvertInt(totalMinutes);
        System.out.println(
            "Totala summan i minuter fram tills midnat om summa är noll feb - mars > " +
            totalMinutes);

      }
      else {

        int summaMajToJunMinutes = summaMajToJunDays * 24 * 60;
        int SummaMajToJunTotalMinutes = summaMajToJunMinutes + xMinuter +
            summaInputclock; // plus delsumma 3
        setConvertInt(SummaMajToJunTotalMinutes);

        System.out.println("Summan i minuter från feb - mars > " +
                           SummaMajToJunTotalMinutes);
      }

    }
    else if (mounth.trim().equals(May) && inputMounth.trim().equals(JulDa)) {

      int summaMajToJulDays = 31 - checkMobileDay - 1 + 30 + checkInputDate;
      int summaMajToJulMinutes = summaMajToJulDays * 24 * 60;
      int SummaMajToJulTotalMinutes = summaMajToJulMinutes + xMinuter +
          summaInputclock; // plus delsumma 3
      setConvertInt(SummaMajToJulTotalMinutes);
      System.out.println("Den totala summan i minuter från Feb - April > " +
                         SummaMajToJulTotalMinutes);

    }

    // -------------------------- J U N I ------------------------------------------------------------------------------------------

    if (mounth.trim().equals(Jun) && inputMounth.trim().equals(JunDa)) { // om Jan är lika med Jan 'och' inputen är lika med 01 (januari)

      int summaJunToJunDays = checkInputDate - checkMobileDay - 1; // feb = 28 dagar  - 1 - inskrivet värde

      if (summaJunToJunDays == 0 || summaJunToJunDays < 0) { // om värdet är noll så utför följande kod

        int totalMinutes = xMinuter + summaInputclock; // plus delsumma 3
        setConvertInt(totalMinutes);

        System.out.println(
            "Totala summan i minuter fram till midnatt om dagar är noll 'februari' > " +
            totalMinutes);

      }
      else { // om värdet är mer än noll så utför följande kod....

        int summaJunToJunMinutes = summaJunToJunDays * 24 * 60;
        int SummaJunToJUnTotalMinutes = summaJunToJunMinutes + xMinuter +
            summaInputclock; // plus delsumma 3
        setConvertInt(SummaJunToJUnTotalMinutes);

        System.out.println("Summan av i minuter i feb - feb > " +
                           SummaJunToJUnTotalMinutes);

      }
    }
    else if (mounth.trim().equals(Jun) && inputMounth.trim().equals(JulDa)) {

      int summaJunToJulDays = 30 - checkMobileDay - 1 + checkInputDate; //checkMobileDay är datumet på mobilen, checkInputDate är det inmatade datumet

      if (summaJunToJulDays == 0 || summaJunToJulDays < 0) { // om värdet är noll så utför följande kod

        int totalMinutes = xMinuter + summaInputclock; // plus delsumma 3
        setConvertInt(totalMinutes);
        System.out.println(
            "Totala summan i minuter fram tills midnat om summa är noll feb - mars > " +
            totalMinutes);

      }
      else {

        int summaJunToJulMinutes = summaJunToJulDays * 24 * 60;
        int SummaJunToJulTotalMinutes = summaJunToJulMinutes + xMinuter +
            summaInputclock; // plus delsumma 3
        setConvertInt(SummaJunToJulTotalMinutes);

        System.out.println("Summan i minuter från feb - mars > " +
                           SummaJunToJulTotalMinutes);
      }

    }
    else if (mounth.trim().equals(Jun) && inputMounth.trim().equals(AugDa)) {

      int summaMajToJulDays = 30 - checkMobileDay - 1 + 31 + checkInputDate;
      int summaMajToJulMinutes = summaMajToJulDays * 24 * 60;
      int SummaMajToJulTotalMinutes = summaMajToJulMinutes + xMinuter +
          summaInputclock; // plus delsumma 3
      setConvertInt(SummaMajToJulTotalMinutes);
      System.out.println("Den totala summan i minuter från Feb - April > " +
                         SummaMajToJulTotalMinutes);

    }

    // -------------------------- J U L I ------------------------------------------------------------------------------------------

    if (mounth.trim().equals(Jul) && inputMounth.trim().equals(JulDa)) { // om Jan är lika med Jan 'och' inputen är lika med 01 (januari)

      int summaJulToJulDays = checkInputDate - checkMobileDay - 1; // feb = 28 dagar  - 1 - inskrivet värde

      if (summaJulToJulDays == 0 || summaJulToJulDays < 0) { // om värdet är noll så utför följande kod

        int totalMinutes = xMinuter + summaInputclock; // plus delsumma 3
        setConvertInt(totalMinutes);

        System.out.println(
            "Totala summan i minuter fram till midnatt om dagar är noll 'februari' > " +
            totalMinutes);

      }
      else { // om värdet är mer än noll så utför följande kod....

        int summaJulToJulMinutes = summaJulToJulDays * 24 * 60;
        int SummaJulToJulTotalMinutes = summaJulToJulMinutes + xMinuter +
            summaInputclock; // plus delsumma 3
        setConvertInt(SummaJulToJulTotalMinutes);

        System.out.println("Summan av i minuter i feb - feb > " +
                           SummaJulToJulTotalMinutes);

      }
    }
    else if (mounth.trim().equals(Jul) && inputMounth.trim().equals(AugDa)) {

      int summaJulToAugDays = 31 - checkMobileDay - 1 + checkInputDate; //checkMobileDay är datumet på mobilen, checkInputDate är det inmatade datumet

      if (summaJulToAugDays == 0 || summaJulToAugDays < 0) { // om värdet är noll så utför följande kod

        int totalMinutes = xMinuter + summaInputclock; // plus delsumma 3
        setConvertInt(totalMinutes);
        System.out.println(
            "Totala summan i minuter fram tills midnat om summa är noll feb - mars > " +
            totalMinutes);

      }
      else {

        int summaJulToAugMinutes = summaJulToAugDays * 24 * 60;
        int SummaJulToAugTotalMinutes = summaJulToAugMinutes + xMinuter +
            summaInputclock; // plus delsumma 3
        setConvertInt(SummaJulToAugTotalMinutes);

        System.out.println("Summan i minuter från feb - mars > " +
                           SummaJulToAugTotalMinutes);
      }

    }
    else if (mounth.trim().equals(Jul) && inputMounth.trim().equals(SepDa)) {

      int summaJulToSepDays = 31 - checkMobileDay - 1 + 31 + checkInputDate;
      int summaJulToSepMinutes = summaJulToSepDays * 24 * 60;
      int SummaJulToSepTotalMinutes = summaJulToSepMinutes + xMinuter +
          summaInputclock; // plus delsumma 3
      setConvertInt(SummaJulToSepTotalMinutes);
      System.out.println("Den totala summan i minuter från Feb - April > " +
                         SummaJulToSepTotalMinutes);

    }

    // -------------------------- A U G U S T I ------------------------------------------------------------------------------------------

    if (mounth.trim().equals(Aug) && inputMounth.trim().equals(AugDa)) { // om Jan är lika med Jan 'och' inputen är lika med 01 (januari)

      int summaAugToAugDays = checkInputDate - checkMobileDay - 1; // feb = 28 dagar  - 1 - inskrivet värde

      if (summaAugToAugDays == 0 || summaAugToAugDays < 0) { // om värdet är noll så utför följande kod

        int totalMinutes = xMinuter + summaInputclock; // plus delsumma 3
        setConvertInt(totalMinutes);

        System.out.println(
            "Totala summan i minuter fram till midnatt om dagar är noll 'februari' > " +
            totalMinutes);

      }
      else { // om värdet är mer än noll så utför följande kod....

        int summaAugToAugMinutes = summaAugToAugDays * 24 * 60;
        int SummaAugToAugTotalMinutes = summaAugToAugMinutes + xMinuter +
            summaInputclock; // plus delsumma 3
        setConvertInt(SummaAugToAugTotalMinutes);

        System.out.println("Summan av i minuter i feb - feb > " +
                           SummaAugToAugTotalMinutes);

      }
    }
    else if (mounth.trim().equals(Aug) && inputMounth.trim().equals(SepDa)) {

      int summaAugToSepDays = 31 - checkMobileDay - 1 + checkInputDate; //checkMobileDay är datumet på mobilen, checkInputDate är det inmatade datumet

      if (summaAugToSepDays == 0 || summaAugToSepDays < 0) { // om värdet är noll så utför följande kod

        int totalMinutes = xMinuter + summaInputclock; // plus delsumma 3
        setConvertInt(totalMinutes);
        System.out.println(
            "Totala summan i minuter fram tills midnat om summa är noll feb - mars > " +
            totalMinutes);

      }
      else {

        int summaAugToSepMinutes = summaAugToSepDays * 24 * 60;
        int SummaAugToSepTotalMinutes = summaAugToSepMinutes + xMinuter +
            summaInputclock; // plus delsumma 3
        setConvertInt(SummaAugToSepTotalMinutes);

        System.out.println("Summan i minuter från feb - mars > " +
                           SummaAugToSepTotalMinutes);
      }

    }
    else if (mounth.trim().equals(Aug) && inputMounth.trim().equals(OktDa)) {

      int summaAugToOktDays = 31 - checkMobileDay - 1 + 30 + checkInputDate;
      int summaAugToOktMinutes = summaAugToOktDays * 24 * 60;
      int SummaAugToOktTotalMinutes = summaAugToOktMinutes + xMinuter +
          summaInputclock; // plus delsumma 3
      setConvertInt(SummaAugToOktTotalMinutes);
      System.out.println("Den totala summan i minuter från Feb - April > " +
                         SummaAugToOktTotalMinutes);

    }

    // -------------------------- S E P T E M B E R ------------------------------------------------------------------------------------------

    if (mounth.trim().equals(Sep) && inputMounth.trim().equals(SepDa)) { // om Jan är lika med Jan 'och' inputen är lika med 01 (januari)

      int summaSepToSepDays = checkInputDate - checkMobileDay - 1; // feb = 28 dagar  - 1 - inskrivet värde

      if (summaSepToSepDays == 0 || summaSepToSepDays < 0) { // om värdet är noll så utför följande kod

        int totalMinutes = xMinuter + summaInputclock; // plus delsumma 3
        setConvertInt(totalMinutes);

        System.out.println(
            "Totala summan i minuter fram till midnatt om dagar är noll 'februari' > " +
            totalMinutes);

      }
      else { // om värdet är mer än noll så utför följande kod....

        int summaSepToSepMinutes = summaSepToSepDays * 24 * 60;
        int SummaSepToSepTotalMinutes = summaSepToSepMinutes + xMinuter +
            summaInputclock; // plus delsumma 3
        setConvertInt(SummaSepToSepTotalMinutes);

        System.out.println("Summan av i minuter i feb - feb > " +
                           SummaSepToSepTotalMinutes);

      }
    }
    else if (mounth.trim().equals(Sep) && inputMounth.trim().equals(OktDa)) {

      int summaSepToOktDays = 30 - checkMobileDay - 1 + checkInputDate; //checkMobileDay är datumet på mobilen, checkInputDate är det inmatade datumet

      if (summaSepToOktDays == 0 || summaSepToOktDays < 0) { // om värdet är noll så utför följande kod

        int totalMinutes = xMinuter + summaInputclock; // plus delsumma 3
        setConvertInt(totalMinutes);
        System.out.println(
            "Totala summan i minuter fram tills midnat om summa är noll feb - mars > " +
            totalMinutes);

      }
      else {

        int summaSepToOktMinutes = summaSepToOktDays * 24 * 60;
        int SummaSepToOktTotalMinutes = summaSepToOktMinutes + xMinuter +
            summaInputclock; // plus delsumma 3
        setConvertInt(SummaSepToOktTotalMinutes);

        System.out.println("Summan i minuter från feb - mars > " +
                           SummaSepToOktTotalMinutes);
      }

    }
    else if (mounth.trim().equals(Sep) && inputMounth.trim().equals(NovDa)) {

      int summaSepToNovDays = 30 - checkMobileDay - 1 + 31 + checkInputDate;
      int summaSepToNovMinutes = summaSepToNovDays * 24 * 60;
      int SummaSepToNovTotalMinutes = summaSepToNovMinutes + xMinuter +
          summaInputclock; // plus delsumma 3
      setConvertInt(SummaSepToNovTotalMinutes);
      System.out.println("Den totala summan i minuter från Feb - April > " +
                         SummaSepToNovTotalMinutes);

    }

    // -------------------------- O K T O B E R ------------------------------------------------------------------------------------------

    if (mounth.trim().equals(Oct) && inputMounth.trim().equals(OktDa)) { // om Jan är lika med Jan 'och' inputen är lika med 01 (januari)

      int summaOctToOctDays = checkInputDate - checkMobileDay - 1; // feb = 28 dagar  - 1 - inskrivet värde

      if (summaOctToOctDays == 0 || summaOctToOctDays < 0) { // om värdet är noll så utför följande kod

        int totalMinutes = xMinuter + summaInputclock; // plus delsumma 3
        setConvertInt(totalMinutes);

        System.out.println(
            "Totala summan i minuter fram till midnatt om dagar är noll 'februari' > " +
            totalMinutes);

      }
      else { // om värdet är mer än noll så utför följande kod....

        int summaOctToOctMinutes = summaOctToOctDays * 24 * 60;
        int SummaOctToOctTotalMinutes = summaOctToOctMinutes + xMinuter +
            summaInputclock; // plus delsumma 3
        setConvertInt(SummaOctToOctTotalMinutes);

        System.out.println("Summan av i minuter i feb - feb > " +
                           SummaOctToOctTotalMinutes);

      }
    }
    else if (mounth.trim().equals(Oct) && inputMounth.trim().equals(NovDa)) {

      int summaOctToNovDays = 31 - checkMobileDay - 1 + checkInputDate; //checkMobileDay är datumet på mobilen, checkInputDate är det inmatade datumet

      if (summaOctToNovDays == 0 || summaOctToNovDays < 0) { // om värdet är noll så utför följande kod

        int totalMinutes = xMinuter + summaInputclock; // plus delsumma 3
        setConvertInt(totalMinutes);
        System.out.println(
            "Totala summan i minuter fram tills midnat om summa är noll feb - mars > " +
            totalMinutes);

      }
      else {

        int summaOctToNovMinutes = summaOctToNovDays * 24 * 60;
        int SummaOctToNovTotalMinutes = summaOctToNovMinutes + xMinuter +
            summaInputclock; // plus delsumma 3
        setConvertInt(SummaOctToNovTotalMinutes);

        System.out.println("Summan i minuter från feb - mars > " +
                           SummaOctToNovTotalMinutes);
      }

    }
    else if (mounth.trim().equals(Oct) && inputMounth.trim().equals(DecDa)) {

      int summaOctToDecDays = 31 - checkMobileDay - 1 + 30 + checkInputDate;
      int summaOctToDecMinutes = summaOctToDecDays * 24 * 60;
      int SummaOctToDecTotalMinutes = summaOctToDecMinutes + xMinuter +
          summaInputclock; // plus delsumma 3
      setConvertInt(SummaOctToDecTotalMinutes);
      System.out.println("Den totala summan i minuter från Feb - April > " +
                         SummaOctToDecTotalMinutes);

    }

    // -------------------------- N O V E M B E R ------------------------------------------------------------------------------------------

    if (mounth.trim().equals(Nov) && inputMounth.trim().equals(NovDa)) { // om Jan är lika med Jan 'och' inputen är lika med 01 (januari)

      int summaNovToNovDays = checkInputDate - checkMobileDay - 1; // feb = 28 dagar  - 1 - inskrivet värde

      if (summaNovToNovDays == 0 || summaNovToNovDays < 0) { // om värdet är noll så utför följande kod

        int totalMinutes = xMinuter + summaInputclock; // plus delsumma 3
        setConvertInt(totalMinutes);

        System.out.println(
            "Totala summan i minuter fram till midnatt om dagar är noll 'februari' > " +
            totalMinutes);

      }
      else { // om värdet är mer än noll så utför följande kod....

        int summaNovToNovMinutes = summaNovToNovDays * 24 * 60;
        int SummaNovToNovTotalMinutes = summaNovToNovMinutes + xMinuter +
            summaInputclock; // plus delsumma 3
        setConvertInt(SummaNovToNovTotalMinutes);

        System.out.println("Summan av i minuter i feb - feb > " +
                           SummaNovToNovTotalMinutes);

      }
    }
    else if (mounth.trim().equals(Nov) && inputMounth.trim().equals(DecDa)) {

      int summaNovToDecDays = 30 - checkMobileDay - 1 + checkInputDate; //checkMobileDay är datumet på mobilen, checkInputDate är det inmatade datumet

      if (summaNovToDecDays == 0 || summaNovToDecDays < 0) { // om värdet är noll så utför följande kod

        int totalMinutes = xMinuter + summaInputclock; // plus delsumma 3
        setConvertInt(totalMinutes);
        System.out.println(
            "Totala summan i minuter fram tills midnat om summa är noll feb - mars > " +
            totalMinutes);

      }
      else {

        int summaNovToDecMinutes = summaNovToDecDays * 24 * 60;
        int SummaNovToDecTotalMinutes = summaNovToDecMinutes + xMinuter +
            summaInputclock; // plus delsumma 3
        setConvertInt(SummaNovToDecTotalMinutes);

        System.out.println("Summan i minuter från feb - mars > " +
                           SummaNovToDecTotalMinutes);
      }

    }
    else if (mounth.trim().equals(Nov) && inputMounth.trim().equals(JanDa)) {

      int summaNovToJanDays = 30 - checkMobileDay - 1 + 31 + checkInputDate;
      int summaNovToJanMinutes = summaNovToJanDays * 24 * 60;
      int SummaNovToJanTotalMinutes = summaNovToJanMinutes + xMinuter +
          summaInputclock; // plus delsumma 3
      setConvertInt(SummaNovToJanTotalMinutes);
      System.out.println("Den totala summan i minuter från Feb - April > " +
                         SummaNovToJanTotalMinutes);

    }

    // -------------------------- D E C E M B E R ------------------------------------------------------------------------------------------

    if (mounth.trim().equals(Dec) && inputMounth.trim().equals(DecDa)) { // om Jan är lika med Jan 'och' inputen är lika med 01 (januari)

      int summaDecToDecDays = checkInputDate - checkMobileDay - 1; // feb = 28 dagar  - 1 - inskrivet värde

      if (summaDecToDecDays == 0 || summaDecToDecDays < 0) { // om värdet är noll så utför följande kod

        int totalMinutes = xMinuter + summaInputclock; // plus delsumma 3
        setConvertInt(totalMinutes);

        System.out.println(
            "Totala summan i minuter fram till midnatt om dagar är noll 'februari' > " +
            totalMinutes);

      }
      else { // om värdet är mer än noll så utför följande kod....

        int summaDecToDecMinutes = summaDecToDecDays * 24 * 60;
        int SummaDecToDecTotalMinutes = summaDecToDecMinutes + xMinuter +
            summaInputclock; // plus delsumma 3
        setConvertInt(SummaDecToDecTotalMinutes);

        System.out.println("Summan av i minuter i feb - feb > " +
                           SummaDecToDecTotalMinutes);

      }
    }
    else if (mounth.trim().equals(Dec) && inputMounth.trim().equals(JanDa)) {

      int summaDecToJanDays = 31 - checkMobileDay - 1 + checkInputDate; //checkMobileDay är datumet på mobilen, checkInputDate är det inmatade datumet

      if (summaDecToJanDays == 0 || summaDecToJanDays < 0) { // om värdet är noll så utför följande kod

        int totalMinutes = xMinuter + summaInputclock; // plus delsumma 3
        setConvertInt(totalMinutes);
        System.out.println(
            "Totala summan i minuter fram tills midnat om summa är noll feb - mars > " +
            totalMinutes);

      }
      else {

        int summaDecToJanMinutes = summaDecToJanDays * 24 * 60;
        int SummaDecToJanTotalMinutes = summaDecToJanMinutes + xMinuter +
            summaInputclock; // plus delsumma 3
        setConvertInt(SummaDecToJanTotalMinutes);

        System.out.println("Summan i minuter från feb - mars > " +
                           SummaDecToJanTotalMinutes);
      }

    }
    else if (mounth.trim().equals(Dec) && inputMounth.trim().equals(FebDa)) {

      int summaDecToFebDays = 31 - checkMobileDay - 1 + 31 + checkInputDate;
      int summaDecToFebMinutes = summaDecToFebDays * 24 * 60;
      int SummaDecToFebTotalMinutes = summaDecToFebMinutes + xMinuter +
          summaInputclock; // plus delsumma 3
      setConvertInt(SummaDecToFebTotalMinutes);
      System.out.println("Den totala summan i minuter från Feb - April > " +
                         SummaDecToFebTotalMinutes);

    }

  }

  public void startApp() { // MAIN - METOD .........................

    if (displayen == null) {
      displayen = Display.getDisplay(this);
      displayen.setCurrent(dateForm);
    }

  }

  public void pauseApp() {}

  public void destroyApp(boolean unconditional) {}

  public void commandAction(Command command, Displayable displayable) {
    if (command == exitCommand) { // om det är exitcommandot så avsluta å stäng igen connection och applikationen

      notifyDestroyed();
    }
    else if (command == okeyCommand) { // eller om det är sendCommand så kör appliktationen

      setDate();
      //testTime();

    }
  }
}

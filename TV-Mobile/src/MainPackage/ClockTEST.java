package MainPackage;

/*   EnhetsTest ÅTER-KLOCKAN       */

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


public class ClockTEST
    extends MIDlet implements CommandListener {

  // FIELDS.......FOR PRIVATE INSTANCE OR ATTRIBUTE....................

  private Command okeyCommand, exitCommand;
  private Form clockForm;
  private Display displayen;
  private Date today;
  private DateField datefield;
  private TextField textFieldClock;
  private TimeZone tz = TimeZone.getTimeZone("GMT+1");
  private Alert alertError;


  public ClockTEST() { // constructor

    this.tz = TimeZone.getTimeZone("GMT+1");

    clockForm = new Form("Åter KLockan! ");

    textFieldClock = new TextField("hhmm: Ange Tid!","",4,TextField.NUMERIC);

    today = new Date();
    today.getTime();

    System.out.println(today);
    today.toString();
    datefield = new DateField("Tid och Datum", DateField.DATE_TIME);
    datefield.setDate(today);

    clockForm.append(textFieldClock);
    clockForm.append(datefield);

    okeyCommand = new Command("Set Time", Command.OK, 1);
    exitCommand = new Command("Exit", Command.EXIT, 2);

    clockForm.addCommand(okeyCommand);
    clockForm.addCommand(exitCommand);
    clockForm.setCommandListener(this);

  }
  public void clockTryAgain() {
  Alert alertError = new Alert("InputNumber Incorrect", "Please try again", null, AlertType.ERROR);
   alertError.setTimeout(Alert.FOREVER);
    textFieldClock.setString("");

    displayen.setCurrent(alertError, clockForm);
}

  public String toString(String b){

  String s = b;

    return s;
  }

  public void setTime()throws NumberFormatException {


    String mobileClock = today.toString(); // Tilldelar mobileClock 'todays' datumvärde, skickar och gör om till en string av java.lang.string-typ

    String setMobileClockCharacterPart1 = mobileClock.substring(11,13);// plockar ut tecken ur klockan
    String setMobileClockCharacterPart2 = mobileClock.substring(14,16);// plockar ut tecken ur klockan

    StringBuffer checkMobileNumber = new StringBuffer(setMobileClockCharacterPart1);// skapar en bufferstring och sätter ihop värde för att kunna jämföra
    checkMobileNumber.append(setMobileClockCharacterPart2);

    String ConvertcheckMobileNumber = checkMobileNumber.toString();// skickar buffestringen till toString() för att skapa ett string-objekt

    Integer convertMobilePartNumber1 = new Integer(0); // Gör om strängar till integer
    int mobileNumberPart1 = convertMobilePartNumber1.parseInt(setMobileClockCharacterPart1);

    Integer convertMobilePartNumber2 = new Integer(0);// Gör om strängar till integer
    int mobileNumberPart2 = convertMobilePartNumber2.parseInt(setMobileClockCharacterPart2);

    int summaMobilePartNumber1 = mobileNumberPart1 * 60; // utför matematisk tidräkning för tidenheter
    int summaMobilePartNumber2 = mobileNumberPart2;
    int totalSummaMobilePartNumber = summaMobilePartNumber1 + summaMobilePartNumber2;

    //------------------------------------------------------------------------------------------------------------------------------------

    String inputMobileClock = textFieldClock.getString(); // tilldelar inputMobileclock den inmatade strängen

    String inputCheckMobileClock = inputMobileClock; // skapar en sträng för att typändra till int och jämföra med checkMobileNumber

    Integer checkMobileNumberToInt = new Integer(0);// konverterar till typen > integer
    int MobileTimeClock = checkMobileNumberToInt.parseInt(ConvertcheckMobileNumber);

    Integer checkInputMobileNumberToInt = new Integer(0);// konverterar till typen > integer
    int MobileInputTimeClock = checkInputMobileNumberToInt.parseInt(inputCheckMobileClock);

    System.out.println("Skriver ut MobileInputTimeClock > "+ MobileInputTimeClock + "\n Skriver ut MobileTimeClock > " + MobileTimeClock);

    //----------------- LOGISKA UTTRYCK FÖR INMATNINGEN ----------------------------------------------------------------------------------

    String checkNumberType = inputMobileClock;

    String TypeOfNumberCheck = checkNumberType.substring(0,1);// plockar ut tecken ut strängen
    String TypeOfNumberCheck2 = checkNumberType.substring(0,2);// plockar ut tecken ut strängen
    String TypeOfNumberCheck3 = checkNumberType.substring(2,3);// plockar ut tecken ut strängen

    Integer checkInputMobilePartNumber1 = new Integer(0);
    int convertedInputNumberPart1 = checkInputMobilePartNumber1.parseInt(TypeOfNumberCheck);

    Integer checkInputMobilePartNumber2 = new Integer(0);
    int convertedInputNumberPart2 = checkInputMobilePartNumber2.parseInt(TypeOfNumberCheck2);

    Integer checkInputMobilePartNumber3 = new Integer(0);
    int convertedInputNumberPart3 = checkInputMobilePartNumber3.parseInt(TypeOfNumberCheck3);

    if(convertedInputNumberPart1 >= 3 || convertedInputNumberPart2 >= 24 || convertedInputNumberPart3 >= 6
                    || MobileTimeClock == MobileInputTimeClock || MobileTimeClock > MobileInputTimeClock  ){

      clockTryAgain();

    }
    else{ // eller om allting är korrekt så utför tidsberäkningen

    //-------------------------------------------------------------------------------------------------------------------------------------

    String setMobileInputClockCharacterPart1 = inputMobileClock.substring(0,2);// plockar ut tecken ut strängen
    String setMobileInputClockCharacterPart2 = inputMobileClock.substring(2,4);// plockar ut tecken ut strängen

    Integer convertInputMobilePartNumber1 = new Integer(0);
    int mobileInputNumberPart1 = convertInputMobilePartNumber1.parseInt(setMobileInputClockCharacterPart1);

    Integer convertInputMobilePartNumber2 = new Integer(0);
    int mobileInputNumberPart2 = convertInputMobilePartNumber2.parseInt(setMobileInputClockCharacterPart2);

    int summaInputMobilePartNumber1 = mobileInputNumberPart1 * 60;
    int summaInputMobilePartNumber2 = mobileInputNumberPart2;
    int totalInputSummaMobilePartNumber = summaInputMobilePartNumber1 + summaInputMobilePartNumber2;

    int totalMinuter = totalInputSummaMobilePartNumber - totalSummaMobilePartNumber;

    if(totalMinuter < 0){ // om totalMinuter är negativt så gånga med minus ett för att göra totalMinuter positivt

     totalMinuter = totalMinuter * (-1);
    }

    System.out.println("visar den totala summan av typen int > " + totalMinuter);

    String tvConvertToString = Integer.toString(totalMinuter);
    String sendTimePlus = "6:";
    String sendTimePlus2 = "m";

    String sendTimeClock = sendTimePlus + tvConvertToString + sendTimePlus2;
    sendTimeClock.trim();
    //setSms(sendTimeClock); // skickar tiden till Tv-Mobile-SERVER

    System.out.println("visar den totala summan 'av typen String' som skickas till Tv-Mobile > " + sendTimeClock);

    }

  }
  public void startApp() { // MAIN - METOD .........................

    if (displayen == null) {
      displayen = Display.getDisplay(this);
      displayen.setCurrent(clockForm);
    }

  }
  public void pauseApp() {}

  public void destroyApp(boolean unconditional) {}

  public void commandAction(Command command, Displayable displayable) {
    if (command == exitCommand) { // om det är exitcommandot så avsluta å stäng igen connection och applikationen

          notifyDestroyed();
    }
    else if (command == okeyCommand) { // eller om det är sendCommand så kör appliktationen

      setTime();

    }
  }
}

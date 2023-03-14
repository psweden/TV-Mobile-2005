package MainPackage;

import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.Displayable;
import ConnectFactory.ConnectServer;
import javax.microedition.lcdui.Form;
import javax.microedition.rms.RecordStore;
import javax.microedition.rms.RecordStoreNotOpenException;
import javax.microedition.rms.InvalidRecordIDException;
import javax.microedition.rms.RecordStoreException;
import javax.microedition.rms.RecordEnumeration;
import java.util.Calendar;
import java.util.TimeZone;
import java.util.Date;
import javax.microedition.lcdui.DateField;
import javax.microedition.lcdui.TextField;
import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.AlertType;


public class TotalViewMobile
    extends MIDlet
    implements CommandListener, Runnable { // programmet inneh�ller tr�dar som startar med sk. synkad start

  private Display displayen; // instansvariabler
  private List listan, lunchList;

  private Calendar calendar;


  private Command      // KOMMANDON
      exitListCommand, sendListCommand, goToListHelpFormCommand, // kommandon f�r huvudf�nstret listan
      goToListAboutFormCommand, goToListLoginLogInFormCommand,

      helpFormBackCommand, // kommando f�r hj�lp

      aboutFormBackCommand, // kommando f�r om Tv-Mobile

       logInbackCommand, loggInCommand, // kommandon f�r login-f�nstret

       settingsCancelCommand, settingsChangeCommand, // kommandon f�r setting-f�nstret

      editSettingCancelCommand, editSettingSaveCommand, editSettingBackCommand, // kommandon f�r edit-vyn-f�nstret
      editInfoSettingBackCommand, editSettingInfoCommand,

      lunchListSendCommand, lunchListBackCommand, lunchListInfoCommand, // kommandon f�r lunch-vyn flervals-alternativlista
      lunchInfoBackCommand,

      aterKlockanSendCommand, aterKlockanBackCommand, aterKlockanInfoCommand,// kommandon f�r �terklockan-status
       infoAterKlockanBackCommand,

      aterDatumSendCommand, aterDatumBackCommand, aterDatumInfoCommand, // kommandon f�r �terdatum-status
       infoAterDatumBackCommand;


  private ImageItem imageItem1;
  private Command thCmd;
  private ConnectServer server;

  private Form helpForm, aboutForm, loginForm, settingsForm, editSettingForm
          , aterDatumForm, infoAterKlockanForm,
          infoAterDatumForm, lunchInfoForm, editSettingsInfoForm, clockForm;

  private Alert alertSender, alertLoggOff, alertEditSettings, alertError;
  private Font fontFace;
  private Date today;
  private DateField datefieldClock, datefieldDate;
  private TimeZone tz = TimeZone.getTimeZone("GMT+1");

// ------------------ INSTANSER F�R RMS-STORE ----------------------------------

  public RecordStore recStore = null;
  static final String REC_STORE = "DataStores"; // ska heta DataStore sen

  private TextField userName, userPassWord;
  private TextField editUserName, editUserPassWord, editSmsNumber;
  private TextField aterKlockanTim, aterKlockanMin, textFieldClock;
  private TextField aterDatum;
  private String  Uname, passWord;
  public String smsNumber;

  private Calendar calender;
  private Command okeyCommand, exitCommand;
  private Form dateForm;
  private TextField textFieldClockDate, textFieldDate;
  private String mounth, inputMounth, inputDate, year, mobileDay;
  private int prefix = -1;


  private String Jan = "Jan", Feb = "Feb", Mar = "Mar", Apr = "Apr", May = "May",
  Jun = "Jun", Jul = "Jul", Aug = "Aug", Sep = "Sep", Oct = "Oct", Nov = "Nov",
  Dec = "Dec";

  private String JanDa = "01", FebDa = "02", MarDa = "03", AprDa = "04", MajDa =
      "05",
  JunDa = "06", JulDa = "07", AugDa = "08", SepDa = "09", OktDa = "10", NovDa =
      "11", DecDa = "12";

  private int JanD = 31, FebD = 28, MarD = 31, AprD = 30, MajD = 31,
  JunD = 30, JulD = 31, AugD = 31, SepD = 30, OktD = 31, NovD = 30, DecD = 31;


// ----------- CONTSTRUKTOR s�tter egenskaper ----------------------------------

  public TotalViewMobile() throws RecordStoreNotOpenException { // Constructor f�r att s�tta attributens egenskaper

    this.tz = tz;

    today = new Date();
    today.getTime();
    today.toString();

    System.out.println(today);

    calender = Calendar.getInstance(tz);

    this.smsNumber = smsNumber;
    this.Uname = Uname;
    this.passWord = passWord;


//----------------- FONT -------------------------------------------------------
    this.fontFace = fontFace;

//---------------- �TER-DATUM-FORM ---------------------------------------------

    /*aterDatumForm = new Form("�ter Datum?");

    aterDatum     = new TextField("Ange dagar: ","",2,TextField.NUMERIC);

    aterDatumSendCommand  = new Command("S�nd SMS", Command.OK, 1);
    aterDatumBackCommand  = new Command("Tillbaka", Command.BACK, 2);
    aterDatumInfoCommand   = new Command("Info",  Command.HELP, 3);

    aterDatumForm.addCommand(aterDatumSendCommand);
    aterDatumForm.addCommand(aterDatumBackCommand);
    aterDatumForm.addCommand(aterDatumInfoCommand);
    aterDatumForm.setCommandListener(this);

    aterDatumSendCommand  = new Command("S�nd SMS", Command.OK, 1);
    aterDatumBackCommand  = new Command("Tillbaka", Command.BACK, 2);
    aterDatumInfoCommand   = new Command("Info",  Command.HELP, 3);

    aterDatumForm.addCommand(aterDatumSendCommand);
    aterDatumForm.addCommand(aterDatumBackCommand);
    aterDatumForm.addCommand(aterDatumInfoCommand);
    aterDatumForm.setCommandListener(this);

    */

//------------- N Y A - � T E R - D A T U M - F O R M ----------------------


    dateForm = new Form("�ter Datum! ");

    textFieldClockDate = new TextField("hhmm: Ange Tid!", "", 4,
                                       TextField.NUMERIC);
    textFieldDate = new TextField("ddmm: Ange Datum!", "", 4, TextField.NUMERIC);


    datefieldDate = new DateField("Tid och Datum", DateField.DATE_TIME);
    datefieldDate.setDate(today);

    dateForm.append(textFieldDate);
    dateForm.append(textFieldClockDate);
    dateForm.append(datefieldDate);

    aterDatumSendCommand  = new Command("S�nd SMS", Command.OK, 1);
    aterDatumBackCommand  = new Command("Tillbaka", Command.BACK, 2);
    aterDatumInfoCommand   = new Command("Info",  Command.HELP, 3);

    dateForm.addCommand(aterDatumSendCommand);
    dateForm.addCommand(aterDatumBackCommand);
    dateForm.addCommand(aterDatumInfoCommand);
    dateForm.setCommandListener(this);



// --------------- �TER-KLOCKAN F O R M ----------------------------------------

    //this.tz = TimeZone.getTimeZone("GMT+1");

    clockForm = new Form("�ter KLockan! ");

    textFieldClock = new TextField("hhmm: Ange Tid!","",4,TextField.NUMERIC);

    //today = new Date();
    //today.getTime();

    System.out.println(today);
    //today.toString();
    datefieldClock = new DateField("Tid och Datum", DateField.DATE_TIME);
    datefieldClock.setDate(today);

    //clockForm.append(textFieldClock);
    //clockForm.append(datefield);

    aterKlockanSendCommand  = new Command("S�nd SMS", Command.OK, 1);
    aterKlockanBackCommand  = new Command("Tillbaka", Command.BACK, 2);
    aterKlockanInfoCommand = new Command("Info",  Command.HELP, 3);

    clockForm.addCommand(aterKlockanSendCommand);
    clockForm.addCommand(aterKlockanBackCommand);
    clockForm.addCommand(aterKlockanInfoCommand);
    clockForm.setCommandListener(this);

//---------------- LUNCH-LIST --------------------------------------------------

    lunchList = new List("Ange tid f�r din lunch!", Choice.MULTIPLE); // skapar en lista

    //lunchList.

    //lunchList.setTitle("Ange tid f�r din lunch!\n\n");

    lunchList.append("30 minuter", null); // skickar text-sms
    lunchList.append("45 minuter", null); // skickar text-sms
    lunchList.append("60 minuter", null); // skickar text-sms


    //lunchList.append("Vidarekoppling?", null); // skickar text-sms

    lunchListInfoCommand = new Command("Info", Command.HELP, 3);
    lunchListSendCommand = new Command("S�nd SMS", Command.OK, 1);
    lunchListBackCommand = new Command("Tillbaka",  Command.BACK, 2);


    // l�gger till knappar till displayen
    lunchList.addCommand(lunchListInfoCommand);
    lunchList.addCommand(lunchListSendCommand);
    lunchList.addCommand(lunchListBackCommand);
    lunchList.setCommandListener(this);


//---------------- EDITSETTINGFORM ---------------------------------------------

    editSettingForm = new Form("Edit Tv-Mobile");

    editUserName     = new TextField("Username: ","",32,TextField.ANY);
    editUserPassWord = new TextField("Password: ", "",32,TextField.ANY);
    editSmsNumber    = new TextField("SMS-Number: ","",32,TextField.ANY);

    editSettingBackCommand   = new Command("Tillbaka", Command.BACK, 1);
    editSettingCancelCommand = new Command("Logga Ut", Command.BACK, 1);
    editSettingSaveCommand = new Command("Spara",  Command.OK, 2);
    editSettingInfoCommand = new Command("Info",  Command.HELP, 3);

    editSettingForm.addCommand(editSettingInfoCommand);
    editSettingForm.addCommand(editSettingBackCommand);
    editSettingForm.addCommand(editSettingCancelCommand);
    editSettingForm.addCommand(editSettingSaveCommand);
    editSettingForm.setCommandListener(this);


//---------------- SETTING-FORM ---------------------------------------------

    settingsForm = new Form("Egenskaper Tv-Mobile ");


   settingsCancelCommand = new Command("Logga Ut", Command.BACK, 1);
   settingsChangeCommand = new Command("Redigera", Command.OK, 2);

   settingsForm.addCommand(settingsCancelCommand);
   settingsForm.addCommand(settingsChangeCommand);
   settingsForm.setCommandListener(this);

//---------------- LOGIN FORM --------------------------------------------------

    loginForm = new Form("Login Tv-Mobile");

    userName = new TextField("Name","",32,TextField.ANY);
    userPassWord = new TextField("Password", "",32,TextField.PASSWORD);

    loggInCommand    = new Command("Logga In", Command.OK, 2);
    logInbackCommand = new Command("Tillbaka", Command.BACK, 1);

    loginForm.addCommand(loggInCommand);
    loginForm.addCommand(logInbackCommand);
    loginForm.setCommandListener(this);

// --------------- EDIT-SETTINGS-INFO-FORM -------------------------------------


    editSettingsInfoForm = new Form("");

    editInfoSettingBackCommand = new Command("Tillbaka", Command.BACK, 1);

    editSettingsInfoForm.addCommand(editInfoSettingBackCommand);
    editSettingsInfoForm.setCommandListener(this);


// --------------- LUNCH-INFO-FORM ---------------------------------------------


    lunchInfoForm = new Form("");

    lunchInfoBackCommand = new Command("Tillbaka", Command.BACK, 1);

    lunchInfoForm.addCommand(lunchInfoBackCommand);
    lunchInfoForm.setCommandListener(this);



// --------------- �TER-DATUM-INFO-FORM ---------------------------------------------

    infoAterDatumForm = new Form("");

    infoAterDatumBackCommand = new Command("Tillbaka", Command.BACK, 1);

    infoAterDatumForm.addCommand(infoAterDatumBackCommand);
    infoAterDatumForm.setCommandListener(this);

//--------------- �TER-KLOCKAN-INFO-FORM --------------------------------------------

    infoAterKlockanForm = new Form("");

    infoAterKlockanBackCommand = new Command("Tillbaka", Command.BACK, 1);

    infoAterKlockanForm.addCommand(infoAterKlockanBackCommand);
    infoAterKlockanForm.setCommandListener(this);

//--------------- ABOUT-FORM ---------------------------------------------------

    aboutForm = new Form("");

    aboutFormBackCommand = new Command("Tillbaka", Command.BACK, 1);

    aboutForm.addCommand(aboutFormBackCommand);
    aboutForm.setCommandListener(this);

//--------------- HELP-FORM ----------------------------------------------------

    helpForm  = new Form("");

    helpFormBackCommand = new Command("Tillbaka", Command.BACK, 1);

    helpForm.addCommand(helpFormBackCommand);
    helpForm.setCommandListener(this);

//------------------------ ALERT-SCREEN'S -------------------------------------------

    alertSender = new Alert("S�nd SMS","\n\n\n.....S�nder...SMS...... " ,
                                             null,AlertType.CONFIRMATION);
    alertSender.setTimeout(8000);

    alertLoggOff = new Alert("Loggar Ut","\n\n\n...Du Loggas Ut... " ,
                                             null,AlertType.CONFIRMATION);
    alertLoggOff.setTimeout(2000);

    alertEditSettings = new Alert("Sparar �ndringar","\n\n\n...�ndringar sparas... " ,
                                             null,AlertType.CONFIRMATION);
    alertEditSettings.setTimeout(2000);

//--------------- HUVUD-VIEW-LISTAN --------------------------------------------

    listan = new List("Tv-Mobile", Choice.EXCLUSIVE); // skapar en lista

    // skapar nya kommandonknappar till listan

    exitListCommand = new Command("Avsluta", Command.EXIT, 1);
    sendListCommand = new Command("S�nd SMS", Command.OK, 3);
    goToListHelpFormCommand = new Command("Hj�lp", Command.HELP, 4);
    goToListAboutFormCommand = new Command("Om Tv-Mobile", Command.HELP, 5);
    goToListLoginLogInFormCommand = new Command("Egenskaper", Command.OK, 6);

    listan.append("Finns p� mobilen", null); // skickar text-sms
    listan.append("G�tt f�r dagen", null); // skickar text-sms
    listan.append("Inne", null); // skickar text-sms
    listan.append("Lunch", null); // Tid
    listan.append("Sjuk", null); // skickar text-sms
    listan.append("V�rd av barn", null); // skickar text-sms
    listan.append("�ter klockan", null); // Tid
    listan.append("�ter datum", null); // datum och tid

    // l�gger till knappar till displayen
    listan.addCommand(goToListLoginLogInFormCommand);
    listan.addCommand(exitListCommand);
    listan.addCommand(goToListHelpFormCommand);
    listan.addCommand(goToListAboutFormCommand);
    listan.addCommand(sendListCommand);
    listan.setCommandListener(this);

// -------- KODEN TILLH�R F�R ATT S�TTA TEX. IMAGE -----------------------------

    try {
      DateAndTimeItem();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

// ---------------- METODER ----------------------------------------------------

  public void tryAgainLunch() { // METODEN TILLH�R LUHCN-STATUS
  Alert error = new Alert("Felmarkerat! ", "F�rs�k igen...", null, AlertType.ERROR);
   error.setTimeout(Alert.FOREVER);
    userName.setString("");
    userPassWord.setString("");

    displayen.setCurrent(error, lunchList);
}

  public void tryAgain() { // METODEN TILLH�R INLOGGINGEN (egenskaper)
  Alert error = new Alert("Login Incorrect", "Please try again", null, AlertType.ERROR);
   error.setTimeout(Alert.FOREVER);
    userName.setString("");
    userPassWord.setString("");

    displayen.setCurrent(error, loginForm);
}
  public Form getEditSettingForm(){ // METODEN RETURNERAR FORMEN F�R EDITSETTINGS I EGENSKAPER

    editSettingForm.deleteAll();
    openRecStore();
    editUserName.setString(Uname);
    editSettingForm.append(editUserName);
    editUserPassWord.setString(passWord);
    editSettingForm.append(editUserPassWord);
    editSmsNumber.setString(smsNumber);
    editSettingForm.append(editSmsNumber);
    closeRecStore();


    return editSettingForm;
  }
  public Form getSettingsForm(){ // METODEN RETURNERAR FORMEN F�R SETTING-FORM

    userName.setString("");
    userPassWord.setString("");

    settingsForm.deleteAll();

    settingsForm.append("\nUsername: " + Uname + "\n");

    settingsForm.append("\nPassword: " + passWord + "\n");

    settingsForm.append("\nSMS-NUMBER: " + smsNumber + "\n");

    return settingsForm;
  }

  public Form getAterDatumForm(){ // METODEN RETURNERAR FORMEN F�R �TER-DATUM

   dateForm.deleteAll();
   textFieldClockDate.setString("");
   textFieldDate.setString("");

   dateForm.append(textFieldDate);
   dateForm.append(textFieldClockDate);
   dateForm.append(datefieldDate);


  return dateForm;

  /*
   dateForm = new Form("�ter Datum! ");

    textFieldClockDate = new TextField("hhmm: Ange Tid!", "", 4,
                                       TextField.NUMERIC);
    textFieldDate = new TextField("ddmm: Ange Datum!", "", 4, TextField.NUMERIC);


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

   */
}

  public Form getAterKlockanForm(){// METODEN RETURNERAR FORMEN F�R �TER-KLOCKAN

    clockForm.deleteAll();
    textFieldClock.setString("");
    clockForm.append(textFieldClock);
    clockForm.append(datefieldClock);

    return clockForm;
  }

  public Form getLogInForm(){ // METODEN RETURNERAR FORMEN F�R LOGIN-FORM

    loginForm.deleteAll();
    loginForm.append(userName);
    loginForm.append(userPassWord);

    return loginForm;
  }

  public Form getAterDatumInfoForm(){ // METODEN RETURNERAR FORMEN F�R DATUM-INFOT

    infoAterDatumForm.deleteAll();

    infoAterDatumForm.setTitle("Info �ter Datum");
    infoAterDatumForm.append("S� h�r fungerar funktionen "
                              + "�ter Datum! \n"
                              + "Ange datum med antal dagar fram till >> �ter datum! ");


    return infoAterDatumForm;
  }
  public Form getEditSettingInfoForm(){ // METODEN RETURNERAR FORMEN F�R EDIT-INFOT

    editSettingsInfoForm.deleteAll();

    editSettingsInfoForm.setTitle("Info Om Editsettings");
    editSettingsInfoForm.append("S� h�r fungerar funktionen "
                               + "att redigera i editsettings! \n"
                               + "Byt l�senord och anv�ndarnamn s�tt �ven det sms-nummer som du har f�r avsikt att anv�nda dig av! "
                               + "Allts� det nummer till den server som kommer arbeta gentemot Tv-Mobile! \n");

    return editSettingsInfoForm;
  }
  public Form getLunchInfoForm(){ // METODEN RETURNERAR FORMEN F�R LUNCH-INFOT

    lunchInfoForm.deleteAll();

    lunchInfoForm.setTitle("Info om Lunch");
    lunchInfoForm.append("S� h�r fungerar funktionen "
                               + "att skicka sms och st�lla status lunch! \n"
                               + "Man kan v�lja lunch i tre l�gen > 30 min, 45 min och 60 min! \n"
                               + "V�ljer man endast n�got av de tre alternativen s� �r man INTE vidarekopplad. "
                               + "Vill du �ven bli vidarekopplad s� v�lj det alternativet! ");

    return lunchInfoForm;
  }

  public Form getAterKlockanInfoForm(){ // METODEN RETURNERAR FORMEN F�R KLOCKAN-INFOT

   infoAterKlockanForm.deleteAll();

   infoAterKlockanForm.setTitle("Info �ter Klockan");
   infoAterKlockanForm.append("S� h�r fungerar funktionen "
                             + "�ter Klockan! \n"
                             + "Du kan st�lla �ter klockan genom att v�lja hur m�nga timmar det �r tills du �r tillbaka! ");


   return infoAterKlockanForm;
 }

  public Form getTextInfoAboutForm(){ // METODEN RETURNERAR FORMEN F�R ABOUT-INFOT

    aboutForm.deleteAll();
    aboutForm.setTitle("Om Tv-Mobile ");
    aboutForm.append("Det h�r �r version 1.3 av "
                     + "Tv-Mobile! \n"
                     + "Programmet tillh�r: \n"
                     + "� Stockholms Telefon AB 2006 \n"
                     + "All rights reserved \n");

    return aboutForm;
  }

  public Form getTextInfoHelpForm(){ // METODEN RETURNERAR FORMEN F�R HELP-INFOT

    helpForm.deleteAll();

    helpForm.setTitle("Tv-Mobile Hj�lp! ");
    helpForm.append("F�lj de anvisade instruktioner \n"
                  + "Info i menyerna! \n"
                  + "F�rsta g�ngen: \nstarta programmet \n"
                  + "logga in under egenskaper:\nmed > username och password. \n"
                  + "G� vidare till editl�get \n"
                  + "Ange det sms-nummer som �r f�r avsikt att anv�ndas till programmet! \n"
                  + "Spara logga ut!\nAvsluta programmet\nStarta programmet\nNu �r Tv-Mobile klart att anv�nda!");

    return helpForm;
  }

  public void commandAction(Command c, Displayable d) { // S�TTER COMMAND-ACTION STARTAR TR�DETS KOMMANDON (tr�dar)
    Thread th = new Thread(this);
    thCmd = c;
    th.start();
  } // commandAction()

  /*public String checkIMEI(){ // METODEN �r till f�r att IDENTIFIERA telefonens IMEI mot mjukvaran

    String serieNumber = System.getProperty("com.sonyericsson.imei");

    System.out.println("Skriver ut IMEI >> " + serieNumber);

    return serieNumber;

  }*/

  public void startApp() throws MIDletStateChangeException { // Applikationens main-metod
    try {
      //  Get the LCDI Display context             // startar displayen
      if (displayen == null) {
        displayen = Display.getDisplay(this);
        displayen.setCurrent(listan);


        // OM IMEI �r null s� st�ngs programmet ner eftersom det identifierar telefonen mot mjukvaran ???
        /*String identy = checkIMEI();
        if(identy == null){
        cleanUp();
        notifyDestroyed();

        }*/

      }
      if (server == null) {        // startar servern
      server = server.getInstance(displayen);

      if (server != null) {
        String sendNumber = getSMSNumber();
         server.createServerConnection(sendNumber);
         server.getSMSC();
         }
       }
    }
    catch (Exception e) {

    }
  }
  private void cleanUp() { // st�nger connection
    displayen = null;
    if (server != null) {
      server = null;
    }
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

  public void setTime()throws NumberFormatException, InvalidRecordIDException,
      RecordStoreNotOpenException, RecordStoreException {


    String mobileClock = today.toString(); // Tilldelar mobileClock 'todays' datumv�rde, skickar och g�r om till en string av java.lang.string-typ

    String setMobileClockCharacterPart1 = mobileClock.substring(11,13);// plockar ut tecken ur klockan
    String setMobileClockCharacterPart2 = mobileClock.substring(14,16);// plockar ut tecken ur klockan

    StringBuffer checkMobileNumber = new StringBuffer(setMobileClockCharacterPart1);// skapar en buggerstring och s�tter ihop v�rde f�r att kunna j�mf�ra
    checkMobileNumber.append(setMobileClockCharacterPart2);

    String ConvertcheckMobileNumber = checkMobileNumber.toString();// skickar buffestringen till toString() f�r att skapa ett string-objekt

    Integer convertMobilePartNumber1 = new Integer(0); // G�r om str�ngar till integer
    int mobileNumberPart1 = convertMobilePartNumber1.parseInt(setMobileClockCharacterPart1);

    Integer convertMobilePartNumber2 = new Integer(0);// G�r om str�ngar till integer
    int mobileNumberPart2 = convertMobilePartNumber2.parseInt(setMobileClockCharacterPart2);

    int summaMobilePartNumber1 = mobileNumberPart1 * 60; // utf�r matematisk tidr�kning f�r tidenheter
    int summaMobilePartNumber2 = mobileNumberPart2;
    int totalSummaMobilePartNumber = summaMobilePartNumber1 + summaMobilePartNumber2;

    //------------------------------------------------------------------------------------------------------------------------------------

    String inputMobileClock = textFieldClock.getString(); // tilldelar inputMobileclock den inmatade str�ngen

    String inputCheckMobileClock = inputMobileClock; // skapar en str�ng f�r att typ�ndra till int och j�mf�ra med checkMobileNumber

    Integer checkMobileNumberToInt = new Integer(0);// konverterar till typen > integer
    int MobileTimeClock = checkMobileNumberToInt.parseInt(ConvertcheckMobileNumber);

    Integer checkInputMobileNumberToInt = new Integer(0);// konverterar till typen > integer
    int MobileInputTimeClock = checkInputMobileNumberToInt.parseInt(inputCheckMobileClock);

    System.out.println("Skriver ut MobileInputTimeClock > "+ MobileInputTimeClock + "\n Skriver ut MobileTimeClock > " + MobileTimeClock);

    //----------------- LOGISKA UTTRYCK F�R INMATNINGEN ----------------------------------------------------------------------------------

    String checkNumberType = inputMobileClock;

    String TypeOfNumberCheck = checkNumberType.substring(0,1);// plockar ut tecken ut str�ngen
    String TypeOfNumberCheck2 = checkNumberType.substring(0,2);// plockar ut tecken ut str�ngen
    String TypeOfNumberCheck3 = checkNumberType.substring(2,3);// plockar ut tecken ut str�ngen

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
    else{ // eller om allting �r korrekt s� utf�r tidsber�kningen

    //-------------------------------------------------------------------------------------------------------------------------------------

    String setMobileInputClockCharacterPart1 = inputMobileClock.substring(0,2);// plockar ut tecken ut str�ngen
    String setMobileInputClockCharacterPart2 = inputMobileClock.substring(2,4);// plockar ut tecken ut str�ngen

    Integer convertInputMobilePartNumber1 = new Integer(0);
    int mobileInputNumberPart1 = convertInputMobilePartNumber1.parseInt(setMobileInputClockCharacterPart1);

    Integer convertInputMobilePartNumber2 = new Integer(0);
    int mobileInputNumberPart2 = convertInputMobilePartNumber2.parseInt(setMobileInputClockCharacterPart2);

    int summaInputMobilePartNumber1 = mobileInputNumberPart1 * 60;
    int summaInputMobilePartNumber2 = mobileInputNumberPart2;
    int totalInputSummaMobilePartNumber = summaInputMobilePartNumber1 + summaInputMobilePartNumber2;

    int totalMinuter = totalInputSummaMobilePartNumber - totalSummaMobilePartNumber - 3; // OBS. v�rdet (-3) m�ste anges f�r Tv-mobile-servern

    if(totalMinuter < 0){ // om totalMinuter �r negativt s� g�nga med minus ett f�r att g�ra totalMinuter positivt

     totalMinuter = totalMinuter * (-1);
    }

    System.out.println("visar den totala summan av typen int > " + totalMinuter);

    String tvConvertToString = Integer.toString(totalMinuter);
    String sendTimePlus = "3:";
    String sendTimePlus2 = "m";
    String vidarekoppling2 = ":e2060";

    String sendTimeClock = sendTimePlus + tvConvertToString + sendTimePlus2 + vidarekoppling2;
    sendTimeClock.trim();
    setSms(sendTimeClock); // skickar tiden till Tv-Mobile-SERVER

    System.out.println("visar den totala summan 'av typen String' som skickas till Tv-Mobile > " + sendTimeClock);

    }
  }

  public void setDataStore() throws RecordStoreNotOpenException,
      InvalidRecordIDException, RecordStoreNotOpenException,
      RecordStoreException {

    openRecStore();
        readRecords();
        readRecordsUpdate();

        if(recStore.getNumRecords() == 0){ // om inneh�llet i databasen �r '0' s� spara de tre f�rsta elementen i databasen

          writeRecord("STAB");
          writeRecord("2020");
          writeRecord("Sign SMSNumber !");
        }

        byte b[] = recStore.getRecord(1);
          Uname = new String(b,0,b.length);

        byte c[] = recStore.getRecord(2);
          passWord = new String(c,0,c.length);

        byte d[] = recStore.getRecord(3);
          smsNumber = new String(d,0,d.length);

      System.out.println("�r i validateUser-metoden skriver ut instansen Uname som �r >> " + Uname );
      System.out.println("�r i validateUser-metoden skriver ut instansen passWord som �r >> " + passWord );
      System.out.println("�r i validateUser-metoden skriver ut instansen smsNumber som �r >> " + smsNumber );

    closeRecStore();
  }
  public void validateUser() throws //  METOD F�R VALIDERING AV USERNAME OCH PASSWORD
      RecordStoreNotOpenException, InvalidRecordIDException,
      RecordStoreNotOpenException, RecordStoreException {

     if (userName.getString().equals(Uname) && userPassWord.getString().equals(passWord)) {
         displayen.setCurrent(getSettingsForm());

     } else {
       tryAgain();
     }
   }

  public void setSms(String setStatus) throws RecordStoreNotOpenException,
      InvalidRecordIDException, RecordStoreException { // METOD SOM SKICKAR STR�NGARARNA TILL ATT SKICKA WMA

    displayen.setCurrent(alertSender, listan);
    //setStatus = cU.testConvert(setStatus);
    String sendStatus = setStatus;
    String sendNumber = getSMSNumber();
    server.sendTextMessage(sendStatus, sendNumber);
    System.out.println("har just skickat en str�ng till setSms - metoden som �r > " + sendStatus);
  }


//------------ COMMAND-ACTION STARTAR H�R MED TR�DARNA -------------------------

  public void run() {
    try {
      if (thCmd == exitListCommand) { // om det �r exitcommandot s� avsluta � st�ng igen connection och applikationen
        cleanUp();
        notifyDestroyed();
      }
      else if (thCmd == sendListCommand) { // eller om det �r sendCommand s� k�r appliktationen
        int valet;
        valet = listan.getSelectedIndex();

        if (valet == 0) { //Finns p� mobilen
          String onTheMobile = "16:0m:e2060";
                                                 // OBS. ATT koden ser p� 'getSelectedIndex' f�r att se vilket ELEMENT I LISTAN SOM �R VALET
             setSms(onTheMobile);
         }
        else if (valet == 1) {            //G�TT F�R DAGEN
             String Finished = "2:0m:e2060";
             setSms(Finished);
        }
        else if (valet == 2) {            //INNE
          String InsideWork = "1";

              setSms(InsideWork);
        }

        else if (valet == 3) {          // LUNCH

             displayen.setCurrent(lunchList);
        }

        else if (valet == 4) {           //SJUK
          String Sick = "7:0m:e2060";
          setSms(Sick);

        }
        else if (valet == 5) {           //V�RD AV BARN
          String SickChild = "17:0m:e2060";
          setSms(SickChild);
        }
        else if (valet == 6) {              //�ter klockan UPPTAGEN INNE

          displayen.setCurrent(getAterKlockanForm());

        }

        else if (valet == 7) {             //�ter datum UPPTAGEN UTE

          displayen.setCurrent(getAterDatumForm());
        }
      }

//---------- S�TTER LUNCH-STATUS-COMMAND ---------------------------------------
      else if (thCmd == lunchListSendCommand) {

            boolean[] valen = new boolean[lunchList.size()];

                                             // NEDAN F�LJER LOGISKA KODRADER F�R FLERVALS-ALTERNATIV 'multiplte-lista'
            lunchList.getSelectedFlags(valen);

            if(true == valen[0] && valen[1] && valen[2] /*&& valen[3]*/){

              tryAgainLunch();

            }
            else if(true == valen[0] && valen[1] && valen[2]){

              tryAgainLunch();

            }
            else if(true == valen[0] && valen[1]){

              tryAgainLunch();

            }
            else if(true == valen[0] && valen[2]){

              tryAgainLunch();

            }

            else if(true == valen[1] && valen[1] && valen[2]){

              tryAgainLunch();

            }

                  /*else if(true == valen[0] && valen[3]){

                     String lunch30min = "4:30m:e";
                      setSms(lunch30min);
                      displayen.setCurrent(alertSender, listan);
                      System.out.println("val 30 minuter med prefix > " + lunch30min);

                  }*/
                  else if(true == valen[0]){

                        String lunch30minPrefix = "4:30m:e2060";
                        setSms(lunch30minPrefix);
                        displayen.setCurrent(alertSender, listan);
                          System.out.println("val 30 minuter utan prefix > " + lunch30minPrefix);
                  }
                  /*else if(true == valen[1] && valen[3] ){

                        String lunch45minPrefix = "4:45m:e";
                        setSms(lunch45minPrefix);
                        displayen.setCurrent(alertSender, listan);
                          System.out.println("val 45 minuter med prefix > " + lunch45minPrefix);
                  }*/
                  else if(true == valen[1]){

                        String lunch45min = "4:45m:e2060";
                        setSms(lunch45min);
                        displayen.setCurrent(alertSender, listan);
                          System.out.println("val 45 minuter utan prefix > " + lunch45min);
                  }
                  /*else if(true == valen[2] && valen[3] ){

                        String lunch60minPrefix = "4:60m:e";
                        setSms(lunch60minPrefix);
                        displayen.setCurrent(alertSender, listan);
                          System.out.println("val 60 minuter med prefix > " + lunch60minPrefix);
                  }*/
                  else if(true == valen[2]){

                        String lunch60min = "4:60m:e2060";
                        setSms(lunch60min);
                        displayen.setCurrent(alertSender, listan);
                          System.out.println("val 60 minuter utan prefix > " + lunch60min);
                  }
        }

      else if (thCmd == lunchListBackCommand) { // Om kommandot �r backkommandot s� visa listan igen

                displayen.setCurrent(listan);

      }

//---------------- �TER-KLOCKAN-COMMANDON --------------------------------------

      else if (thCmd == aterKlockanInfoCommand) {

                displayen.setCurrent(getAterKlockanInfoForm());

      }
      else if (thCmd == aterKlockanBackCommand) {

                displayen.setCurrent(listan);

      }
      else if (thCmd == infoAterKlockanBackCommand) {

                displayen.setCurrent(getAterKlockanForm());

      }
      else if (thCmd == aterKlockanSendCommand) {

        setTime();

           //displayen.setCurrent(alertSender, listan);
      }

//---------------- �TER-DATUM-COMMANDON --------------------------------------

      else if (thCmd == aterDatumInfoCommand) {

                displayen.setCurrent(getAterDatumInfoForm());

      }
      else if (thCmd == aterDatumBackCommand) {

                displayen.setCurrent(listan);

      }
      else if (thCmd == infoAterDatumBackCommand) {

                displayen.setCurrent(getAterDatumForm());

      }
      else if (thCmd == aterDatumSendCommand) {

        setDate();

      }


//----------- SE KOMMENTARER TILL H�GER OM KODEN F�R SE VILKET COMMANDO SOM H�R TILL VILKET

      else if (thCmd == goToListHelpFormCommand) { // Kommandot 'Hj�lp' h�r huvudf�nstret listan

        displayen.setCurrent(getTextInfoHelpForm());

      }
      else if (thCmd == lunchInfoBackCommand) { // Kommandot 'Tillbaka' h�r till lunch-info

        displayen.setCurrent(lunchList);
      }
      else if (thCmd == editInfoSettingBackCommand) { // Kommandot 'Info' h�r till edit-setting-formen

        displayen.setCurrent(getEditSettingForm());
      }

      else if (thCmd == lunchListInfoCommand) { // Kommandot 'Info' h�r till lunch-formen

        displayen.setCurrent(getLunchInfoForm());
      }
      else if (thCmd == editSettingInfoCommand) { // Kommandot 'Info' h�r till edit-setting-formen

        displayen.setCurrent(getEditSettingInfoForm());
      }

      else if (thCmd == helpFormBackCommand) { // Kommandot 'Tillbaka' h�r till hj�lp-formen

        displayen.setCurrent(listan);
      }

      else if (thCmd == goToListAboutFormCommand) { // Kommandot 'Om Tv-Moble' h�r till huvudf�nstret listan

        displayen.setCurrent(getTextInfoAboutForm());
      }
      else if (thCmd == aboutFormBackCommand) { // Kommandot 'Tillbaka' h�r till about-formen

        displayen.setCurrent(listan);
      }
      else if (thCmd == goToListLoginLogInFormCommand) { // Kommandot 'egenskaper' h�r till huvudf�nstret listan

        displayen.setCurrent(getLogInForm());
        setDataStore();
        upDateDataStore();

      }
      else if (thCmd == loggInCommand) { // Kommandot 'Logga In' h�r till Admin-form

        validateUser();
      }
      else if (thCmd == logInbackCommand) { // Kommandot 'Tillbaka' h�r till Admin-form

        displayen.setCurrent(listan);

      }
      else if (thCmd == settingsCancelCommand) { // Kommandot 'Logga Ut' h�r till setting-Form

       displayen.setCurrent(alertLoggOff, listan);
      }
      else if (thCmd == settingsChangeCommand) { // Kommandot 'Redigera' h�r till setting-Form

        displayen.setCurrent(getEditSettingForm());
      }
      else if (thCmd == editSettingCancelCommand) { // Kommandot 'Logga Ut' h�r till editSetting-Form

        displayen.setCurrent(alertLoggOff, listan);
      }
      else if (thCmd == editSettingSaveCommand) { // Kommandot 'Spara' h�r till editSetting-Form

        openRecStore();
        setName();
        setPassWord();
        setSMSNumber();
        closeRecStore();
        upDateDataStore();
        displayen.setCurrent(alertEditSettings, listan);

      }
      else if (thCmd == editSettingBackCommand) { // Kommandot 'Tillbaka' h�r till editSetting-Form

        displayen.setCurrent(getSettingsForm());

      }

    }
    catch (Exception e) {
      e.printStackTrace();
      System.out.println("WMAMIDlet.run Exception " + e);
    }
  }

//------------ D A T A - B A S - R M S -----------------------------------------

  public void upDateDataStore() throws InvalidRecordIDException,
      RecordStoreNotOpenException, RecordStoreException {

     openRecStore();
     String setBackUserNameRecord = "STAB";
     String setBackPasswordRecord = "2020";
     String setBackSMSNumberRecord = "Sign SMSNumber !";

     if(recStore.getRecord(1) == null && recStore.getRecord(2) == null && recStore.getRecord(3) == null){

       recStore.setRecord(1, setBackUserNameRecord.getBytes(), 0, setBackUserNameRecord.length());
       recStore.setRecord(2, setBackPasswordRecord.getBytes(), 0, setBackPasswordRecord.length());
       recStore.setRecord(3, setBackSMSNumberRecord.getBytes(), 0, setBackSMSNumberRecord.length());
    }
    else if(recStore.getRecord(1) == null && recStore.getRecord(2) == null){

       recStore.setRecord(1, setBackUserNameRecord.getBytes(), 0, setBackUserNameRecord.length());
       recStore.setRecord(2, setBackPasswordRecord.getBytes(), 0, setBackPasswordRecord.length());

    }
    else if(recStore.getRecord(2) == null && recStore.getRecord(3) == null){


       recStore.setRecord(2, setBackPasswordRecord.getBytes(), 0, setBackPasswordRecord.length());
       recStore.setRecord(3, setBackSMSNumberRecord.getBytes(), 0, setBackSMSNumberRecord.length());
    }
    else if(recStore.getRecord(1) == null && recStore.getRecord(3) == null){

       recStore.setRecord(1, setBackUserNameRecord.getBytes(), 0, setBackUserNameRecord.length());
       recStore.setRecord(3, setBackSMSNumberRecord.getBytes(), 0, setBackSMSNumberRecord.length());
    }
     else if(recStore.getRecord(1) == null){

        recStore.setRecord(1, setBackUserNameRecord.getBytes(), 0, setBackUserNameRecord.length());
     }
     else if(recStore.getRecord(2) == null){

        recStore.setRecord(2, setBackPasswordRecord.getBytes(), 0, setBackPasswordRecord.length());
     }
     else if(recStore.getRecord(3) == null){

        recStore.setRecord(3, setBackSMSNumberRecord.getBytes(), 0, setBackSMSNumberRecord.length());
     }

     closeRecStore();
   }

  public void setName(){

    try
    {
      recStore.setRecord(1, editUserName.getString().getBytes(), 0, editUserName.getString().length());
    }
    catch (Exception e)
    {
      // ALERT
    }
  }

  public void setPassWord(){


    try
    {
      recStore.setRecord(2, editUserPassWord.getString().getBytes(), 0, editUserPassWord.getString().length());
    }
    catch (Exception e)
    {
      // ALERT
    }
  }
  public String getSMSNumber() throws InvalidRecordIDException,
      RecordStoreNotOpenException, RecordStoreException {

    openRecStore();

    byte d[] = recStore.getRecord(3);
        smsNumber = new String(d,0,d.length);

    closeRecStore();

    return smsNumber;

  }

  public void setSMSNumber(){
    try
    {
      recStore.setRecord(3, editSmsNumber.getString().getBytes(), 0, editSmsNumber.getString().length());
    }
    catch (Exception e)
    {
      // ALERT
    }
  }

  public void readRecordsUpdate()
  {
    try
    {
      System.out.println("Number of records: " + recStore.getNumRecords());

      if (recStore.getNumRecords() > 0)
      {
        RecordEnumeration re = recStore.enumerateRecords(null, null, false);
        while (re.hasNextElement())
        {
          String str = new String(re.nextRecord());
          System.out.println("Record: " + str);
        }
      }
    }
    catch (Exception e)
    {
      System.err.println(e.toString());
    }
  }
  public void readRecords()
  {
    try
    {
      // Intentionally small to test code below
      byte[] recData = new byte[5];
      int len;

      for (int i = 1; i <= recStore.getNumRecords(); i++)
      {
        // Allocate more storage if necessary
        if (recStore.getRecordSize(i) > recData.length)
          recData = new byte[recStore.getRecordSize(i)];

        len = recStore.getRecord(i, recData, 0);
        if (Settings.debug) System.out.println("Record ID#" + i + ": " + new String(recData, 0, len));
      }
    }
    catch (Exception e)
    {
      System.err.println(e.toString());
    }
  }

  public void writeRecord(String str)
  {
    byte[] rec = str.getBytes();

    try
    {
      System.out.println("sparar ");
      recStore.addRecord(rec, 0, rec.length);
      System.out.println("Writing record: " + str);
    }
    catch (Exception e)
    {
      System.err.println(e.toString());
    }
  }

  public void openRecStore()
  {
    try
    {
      System.out.println("�ppnar databasen");
      // The second parameter indicates that the record store
       // should be created if it does not exist
      recStore = RecordStore.openRecordStore(REC_STORE, true );

    }
    catch (Exception e)
    {
      System.err.println(e.toString());
    }
  }
  public void closeRecStore()
  {
    try
    {
      recStore.closeRecordStore();
    }
    catch (Exception e)
    {
      System.err.println(e.toString());
    }
  }

  public void destroyApp(boolean unconditional) { } // avsluta programmet

  public void pauseApp() {} // pausa programmet

  private void DateAndTimeItem() throws Exception { // METOD F�R ATT BILDER, DATE-FORM TEX....
    imageItem1 = new ImageItem("", null, ImageItem.LAYOUT_CENTER, "");
    imageItem1.setLabel("");
    imageItem1.setAltText("");
  }
  public void setConvertInt(int totalMinutes) throws InvalidRecordIDException,
      RecordStoreNotOpenException, RecordStoreException {

    int convertINT = totalMinutes - 3;
    //System.out.println("Visar inneh�llet fr�n datemetoden " + convertINT);
    //System.out.println("Visar inneh�llet fr�n buffer " + checkSendNumber);

    String tvConvertSendString = Integer.toString(convertINT);

    String sendToSMS = tvConvertSendString.trim();
    String s = "3:";
    String p = sendToSMS;
    String m = "m";
    String vidareKoppling = ":e2060";

    sendToSMS = s + sendToSMS + m + vidareKoppling;
    System.out.println("Visar inneh�llet som skickas f�r SMS-s�ndning " + sendToSMS);

    setSms(sendToSMS);

  }

  public void setDate() throws NumberFormatException,
      RecordStoreNotOpenException, InvalidRecordIDException,
      RecordStoreException {

    String mobileClock = today.toString(); // Tilldelar mobileClock 'todays' datumv�rde, skickar och g�r om till en string av java.lang.string-typ

    String inputTimMin = textFieldClockDate.getString();

    String setMobileInputTim = inputTimMin.substring(0, 2); // plockar ut 'TIM' tecken ur klockan
    String setMobileInputMin = inputTimMin.substring(2, 4); // plockar ut 'TIM' tecken ur klockan

    String convertSetMobileIputTim = setMobileInputTim.trim();
    String convertSetMobileIputMin = setMobileInputMin.trim();

    String setMobileDateCharacterPart1 = mobileClock.substring(11, 13); // plockar ut 'TIM' tecken ur klockan
    String setMobileDateCharacterPart2 = mobileClock.substring(14, 16); // plockar ut 'MIN' tecken ur klockan

    Integer convertInputMobilePartNumber11 = new Integer(0); // G�r om str�ngar till integer
    int mobileInputTIM = convertInputMobilePartNumber11.parseInt(
        convertSetMobileIputTim);

    Integer convertInputMobilePartNumber12 = new Integer(0); // G�r om str�ngar till integer
    int mobileInputMIN = convertInputMobilePartNumber12.parseInt(
        convertSetMobileIputMin);

    Integer convertMobilePartNumber11 = new Integer(0); // G�r om str�ngar till integer
    int mobileTIM = convertMobilePartNumber11.parseInt(
        setMobileDateCharacterPart1);

    Integer convertMobilePartNumber12 = new Integer(0); // G�r om str�ngar till integer
    int mobileMIN = convertMobilePartNumber12.parseInt(
        setMobileDateCharacterPart2);

    int dygn = 1440;
    int minut = 60;

    int summaBerakning1 = (mobileTIM * minut) + mobileMIN;
    int summaInputclock = (mobileInputTIM * minut) + mobileInputMIN;

    System.out.println("visar summan av tiden input > " + summaInputclock);

    int xMinuter = dygn - summaBerakning1; // xMinuter inneh�ller f�rsta delen i ber�kningen.
    System.out.println("visar summa av xmin " + xMinuter);

    String setMobileDateMounth = mobileClock.substring(4, 7); // plockar ut 'M�NAD' tecken ur klockan

    String dateField = textFieldDate.getString();

    String setInputDateCharacterPart1 = dateField.substring(0, 2); // plockar ut 'DAY' tecken ur input date

    String setInputDateCharacterPart2 = dateField.substring(2, 4); // plockar ut 'M�nad' tecken ur input date

    String setInputyear = mobileClock.substring(24, 28); // plockar ut 'year' tecken ur klockan

    String setInputDay = mobileClock.substring(8, 11); // plockar ut 'year' tecken ur klockan

    this.year = setInputyear; // anv�nds f�r skott�r
    this.inputDate = setInputDateCharacterPart1; // g�rs om till en int f�r att r�kna med
    this.inputMounth = setInputDateCharacterPart2; // anv�nds f�r att j�mf�ra med
    this.mounth = setMobileDateMounth; // anv�nds f�r att j�mf�ra med
    this.mobileDay = setInputDay.trim();

    Integer convertInputDate = new Integer(0); // G�r om str�ngar till integer
    int checkInputDate = convertInputDate.parseInt(inputDate); // inmatat datum

    Integer convertMobileDay = new Integer(0); // G�r om str�ngar till integer
    int checkMobileDay = convertMobileDay.parseInt(mobileDay); // klockans dag to integer

// -------------------------- J A N U A R I ------------------------------------------------------------------------------------------

    if (mounth.trim().equals(Jan) && inputMounth.trim().equals(JanDa)) { // om Jan �r lika med Jan 'och' inputen �r lika med 01 (januari)

      int summaJanToJanDays = checkInputDate - checkMobileDay - 1; // jan = 31 dagar  - 1 - inskrivet v�rde

      if (summaJanToJanDays == 0 || summaJanToJanDays < 0) { // om v�rdet �r noll s� utf�r f�ljande kod

        int totalMinutes = xMinuter + summaInputclock; // plus delsumma 3
        setConvertInt(totalMinutes);

        System.out.println(
            "Totala summan i minuter fram till midnatt om dagar �r noll > " +
            totalMinutes);

      }
      else { // om v�rdet �r mer �n noll s� utf�r f�ljande kod....

        int summaJanToJanMinutes = summaJanToJanDays * 24 * 60;
        int SummaJanToJanTotalMinutes = summaJanToJanMinutes + xMinuter +
            summaInputclock; // plus delsumma 3
        setConvertInt(SummaJanToJanTotalMinutes);

        System.out.println("Summan av i minuter i jan - jan > " +
                           SummaJanToJanTotalMinutes);

      }
    }
    else if (mounth.trim().equals(Jan) && inputMounth.trim().equals(FebDa)) {

      int summaJanToFebDays = 31 - checkMobileDay - 1 + checkInputDate; //checkMobileDay �r datumet p� mobilen, checkInputDate �r det inmatade datumet

      if (summaJanToFebDays == 0 || summaJanToFebDays < 0) { // om v�rdet �r noll s� utf�r f�ljande kod

        int totalMinutes = xMinuter + summaInputclock; // plus delsumma 3
        setConvertInt(totalMinutes);
        System.out.println(
            "Totala summan i minuter fram tills midnat om summa �r noll jan - feb > " +
            totalMinutes);

      }
      else {

        int summaJanToFebMinutes = summaJanToFebDays * 24 * 60;
        int SummaJanToFebTotalMinutes = summaJanToFebMinutes + xMinuter +
            summaInputclock; // plus delsumma 3
        setConvertInt(SummaJanToFebTotalMinutes);

        System.out.println("Summan i minuter fr�n jan - feb > " +
                           SummaJanToFebTotalMinutes);
      }

    }
    else if (mounth.trim().equals(Jan) && inputMounth.trim().equals(MarDa)) {

      int summaJanToMarDays = 31 - checkMobileDay - 1 + 28 + checkInputDate;
      int summaJanToMarMinutes = summaJanToMarDays * 24 * 60;
      int SummaJanToMarTotalMinutes = summaJanToMarMinutes + xMinuter +
          summaInputclock; // plus delsumma 3
      setConvertInt(SummaJanToMarTotalMinutes);
      System.out.println("Den totala summan i minuter fr�n jan - mars > " +
                         SummaJanToMarTotalMinutes);

    }

// -------------------------- F E B R U A R I ------------------------------------------------------------------------------------------

    if (mounth.trim().equals(Feb) && inputMounth.trim().equals(FebDa)) { // om Jan �r lika med Jan 'och' inputen �r lika med 01 (januari)

      int summaFebToFebDays = checkInputDate - checkMobileDay - 1; // feb = 28 dagar  - 1 - inskrivet v�rde

      if (summaFebToFebDays == 0 || summaFebToFebDays < 0) { // om v�rdet �r noll s� utf�r f�ljande kod

        int totalMinutes = xMinuter + summaInputclock; // plus delsumma 3
        setConvertInt(totalMinutes);

        System.out.println(
            "Totala summan i minuter fram till midnatt om dagar �r noll 'februari' > " +
            totalMinutes);

      }
      else { // om v�rdet �r mer �n noll s� utf�r f�ljande kod....

        int summaFebToFebMinutes = summaFebToFebDays * 24 * 60;
        int SummaFebToFebTotalMinutes = summaFebToFebMinutes + xMinuter +
            summaInputclock; // plus delsumma 3
        setConvertInt(SummaFebToFebTotalMinutes);

        System.out.println("Summan av i minuter i feb - feb > " +
                           SummaFebToFebTotalMinutes);

      }
    }
    else if (mounth.trim().equals(Feb) && inputMounth.trim().equals(MarDa)) {

      int summaFebToMarDays = 28 - checkMobileDay - 1 + checkInputDate; //checkMobileDay �r datumet p� mobilen, checkInputDate �r det inmatade datumet

      if (summaFebToMarDays == 0 || summaFebToMarDays < 0) { // om v�rdet �r noll s� utf�r f�ljande kod

        int totalMinutes = xMinuter + summaInputclock; // plus delsumma 3
        setConvertInt(totalMinutes);
        System.out.println(
            "Totala summan i minuter fram tills midnat om summa �r noll feb - mars > " +
            totalMinutes);

      }
      else {

        int summaFebToMarMinutes = summaFebToMarDays * 24 * 60;
        int SummaFebToMarTotalMinutes = summaFebToMarMinutes + xMinuter +
            summaInputclock; // plus delsumma 3
        setConvertInt(SummaFebToMarTotalMinutes);

        System.out.println("Summan i minuter fr�n feb - mars > " +
                           SummaFebToMarTotalMinutes);
      }

    }
    else if (mounth.trim().equals(Feb) && inputMounth.trim().equals(AprDa)) {

      int summaFebToAprDays = 28 - checkMobileDay - 1 + 31 + checkInputDate;
      int summaFebToAprMinutes = summaFebToAprDays * 24 * 60;
      int SummaFebToAprTotalMinutes = summaFebToAprMinutes + xMinuter +
          summaInputclock; // plus delsumma 3
      setConvertInt(SummaFebToAprTotalMinutes);
      System.out.println("Den totala summan i minuter fr�n Feb - April > " +
                         SummaFebToAprTotalMinutes);

    }

// -------------------------- M A R S ------------------------------------------------------------------------------------------

    if (mounth.trim().equals(Mar) && inputMounth.trim().equals(MarDa)) { // om Jan �r lika med Jan 'och' inputen �r lika med 01 (januari)

      int summaMarToMarDays = checkInputDate - checkMobileDay - 1; // feb = 28 dagar  - 1 - inskrivet v�rde

      if (summaMarToMarDays == 0 || summaMarToMarDays < 0) { // om v�rdet �r noll s� utf�r f�ljande kod

        int totalMinutes = xMinuter + summaInputclock; // plus delsumma 3
        setConvertInt(totalMinutes);

        System.out.println(
            "Totala summan i minuter fram till midnatt om dagar �r noll 'februari' > " +
            totalMinutes);

      }
      else { // om v�rdet �r mer �n noll s� utf�r f�ljande kod....

        int summaMarToMarMinutes = summaMarToMarDays * 24 * 60;
        int SummaMarToMarTotalMinutes = summaMarToMarMinutes + xMinuter +
            summaInputclock; // plus delsumma 3
        setConvertInt(SummaMarToMarTotalMinutes);

        System.out.println("Summan av i minuter i feb - feb > " +
                           SummaMarToMarTotalMinutes);

      }
    }
    else if (mounth.trim().equals(Mar) && inputMounth.trim().equals(AprDa)) {

      int summaMarToAprDays = 31 - checkMobileDay - 1 + checkInputDate; //checkMobileDay �r datumet p� mobilen, checkInputDate �r det inmatade datumet

      if (summaMarToAprDays == 0 || summaMarToAprDays < 0) { // om v�rdet �r noll s� utf�r f�ljande kod

        int totalMinutes = xMinuter + summaInputclock; // plus delsumma 3
        setConvertInt(totalMinutes);
        System.out.println(
            "Totala summan i minuter fram tills midnat om summa �r noll feb - mars > " +
            totalMinutes);

      }
      else {

        int summaMarToAprMinutes = summaMarToAprDays * 24 * 60;
        int SummaMarToAprTotalMinutes = summaMarToAprMinutes + xMinuter +
            summaInputclock; // plus delsumma 3
        setConvertInt(SummaMarToAprTotalMinutes);

        System.out.println("Summan i minuter fr�n feb - mars > " +
                           SummaMarToAprTotalMinutes);
      }

    }
    else if (mounth.trim().equals(Mar) && inputMounth.trim().equals(MajDa)) {

      int summaMarToMajDays = 31 - checkMobileDay - 1 + 30 + checkInputDate;
      int summaMarToMajMinutes = summaMarToMajDays * 24 * 60;
      int SummaMarToMajTotalMinutes = summaMarToMajMinutes + xMinuter +
          summaInputclock; // plus delsumma 3
      setConvertInt(SummaMarToMajTotalMinutes);
      System.out.println("Den totala summan i minuter fr�n Feb - April > " +
                         SummaMarToMajTotalMinutes);

    }

    // -------------------------- A P R I L ------------------------------------------------------------------------------------------

    if (mounth.trim().equals(Apr) && inputMounth.trim().equals(AprDa)) { // om Jan �r lika med Jan 'och' inputen �r lika med 01 (januari)

      int summaAprToAprDays = checkInputDate - checkMobileDay - 1; // feb = 28 dagar  - 1 - inskrivet v�rde

      if (summaAprToAprDays == 0 || summaAprToAprDays < 0) { // om v�rdet �r noll s� utf�r f�ljande kod

        int totalMinutes = xMinuter + summaInputclock; // plus delsumma 3
        setConvertInt(totalMinutes);

        System.out.println(
            "Totala summan i minuter fram till midnatt om dagar �r noll 'februari' > " +
            totalMinutes);

      }
      else { // om v�rdet �r mer �n noll s� utf�r f�ljande kod....

        int summaAprToAprMinutes = summaAprToAprDays * 24 * 60;
        int SummaAprToAprTotalMinutes = summaAprToAprMinutes + xMinuter +
            summaInputclock; // plus delsumma 3
        setConvertInt(SummaAprToAprTotalMinutes);

        System.out.println("Summan av i minuter i feb - feb > " +
                           SummaAprToAprTotalMinutes);

      }
    }
    else if (mounth.trim().equals(Apr) && inputMounth.trim().equals(MajDa)) {

      int summaAprToMajDays = 30 - checkMobileDay - 1 + checkInputDate; //checkMobileDay �r datumet p� mobilen, checkInputDate �r det inmatade datumet

      if (summaAprToMajDays == 0 || summaAprToMajDays < 0) { // om v�rdet �r noll s� utf�r f�ljande kod

        int totalMinutes = xMinuter + summaInputclock; // plus delsumma 3
        setConvertInt(totalMinutes);
        System.out.println(
            "Totala summan i minuter fram tills midnat om summa �r noll feb - mars > " +
            totalMinutes);

      }
      else {

        int summaAprToMajMinutes = summaAprToMajDays * 24 * 60;
        int SummaAprToMajTotalMinutes = summaAprToMajMinutes + xMinuter +
            summaInputclock; // plus delsumma 3
        setConvertInt(SummaAprToMajTotalMinutes);

        System.out.println("Summan i minuter fr�n feb - mars > " +
                           SummaAprToMajTotalMinutes);
      }

    }
    else if (mounth.trim().equals(Apr) && inputMounth.trim().equals(JunDa)) {

      int summaAprToJunDays = 30 - checkMobileDay - 1 + 31 + checkInputDate;
      int summaAprToJunMinutes = summaAprToJunDays * 24 * 60;
      int SummaAprToJunTotalMinutes = summaAprToJunMinutes + xMinuter +
          summaInputclock; // plus delsumma 3
      setConvertInt(SummaAprToJunTotalMinutes);
      System.out.println("Den totala summan i minuter fr�n Feb - April > " +
                         SummaAprToJunTotalMinutes);

    }

    // -------------------------- M A J ------------------------------------------------------------------------------------------

    if (mounth.trim().equals(May) && inputMounth.trim().equals(MajDa)) { // om Jan �r lika med Jan 'och' inputen �r lika med 01 (januari)

      int summaMajToMajDays = checkInputDate - checkMobileDay - 1; // feb = 28 dagar  - 1 - inskrivet v�rde

      if (summaMajToMajDays == 0 || summaMajToMajDays < 0) { // om v�rdet �r noll s� utf�r f�ljande kod

        int totalMinutes = xMinuter + summaInputclock; // plus delsumma 3
        setConvertInt(totalMinutes);

        System.out.println(
            "Totala summan i minuter fram till midnatt om dagar �r noll 'februari' > " +
            totalMinutes);

      }
      else { // om v�rdet �r mer �n noll s� utf�r f�ljande kod....

        int summaMajToMajMinutes = summaMajToMajDays * 24 * 60;
        int SummaMajToMajTotalMinutes = summaMajToMajMinutes + xMinuter +
            summaInputclock; // plus delsumma 3
        setConvertInt(SummaMajToMajTotalMinutes);

        System.out.println("Summan av i minuter i feb - feb > " +
                           SummaMajToMajTotalMinutes);

      }
    }
    else if (mounth.trim().equals(May) && inputMounth.trim().equals(JunDa)) {

      int summaMajToJunDays = 31 - checkMobileDay - 1 + checkInputDate; //checkMobileDay �r datumet p� mobilen, checkInputDate �r det inmatade datumet

      if (summaMajToJunDays == 0 || summaMajToJunDays < 0) { // om v�rdet �r noll s� utf�r f�ljande kod

        int totalMinutes = xMinuter + summaInputclock; // plus delsumma 3
        setConvertInt(totalMinutes);
        System.out.println(
            "Totala summan i minuter fram tills midnat om summa �r noll feb - mars > " +
            totalMinutes);

      }
      else {

        int summaMajToJunMinutes = summaMajToJunDays * 24 * 60;
        int SummaMajToJunTotalMinutes = summaMajToJunMinutes + xMinuter +
            summaInputclock; // plus delsumma 3
        setConvertInt(SummaMajToJunTotalMinutes);

        System.out.println("Summan i minuter fr�n feb - mars > " +
                           SummaMajToJunTotalMinutes);
      }

    }
    else if (mounth.trim().equals(May) && inputMounth.trim().equals(JulDa)) {

      int summaMajToJulDays = 31 - checkMobileDay - 1 + 30 + checkInputDate;
      int summaMajToJulMinutes = summaMajToJulDays * 24 * 60;
      int SummaMajToJulTotalMinutes = summaMajToJulMinutes + xMinuter +
          summaInputclock; // plus delsumma 3
      setConvertInt(SummaMajToJulTotalMinutes);
      System.out.println("Den totala summan i minuter fr�n Feb - April > " +
                         SummaMajToJulTotalMinutes);

    }

    // -------------------------- J U N I ------------------------------------------------------------------------------------------

    if (mounth.trim().equals(Jun) && inputMounth.trim().equals(JunDa)) { // om Jan �r lika med Jan 'och' inputen �r lika med 01 (januari)

      int summaJunToJunDays = checkInputDate - checkMobileDay - 1; // feb = 28 dagar  - 1 - inskrivet v�rde

      if (summaJunToJunDays == 0 || summaJunToJunDays < 0) { // om v�rdet �r noll s� utf�r f�ljande kod

        int totalMinutes = xMinuter + summaInputclock; // plus delsumma 3
        setConvertInt(totalMinutes);

        System.out.println(
            "Totala summan i minuter fram till midnatt om dagar �r noll 'februari' > " +
            totalMinutes);

      }
      else { // om v�rdet �r mer �n noll s� utf�r f�ljande kod....

        int summaJunToJunMinutes = summaJunToJunDays * 24 * 60;
        int SummaJunToJUnTotalMinutes = summaJunToJunMinutes + xMinuter +
            summaInputclock; // plus delsumma 3
        setConvertInt(SummaJunToJUnTotalMinutes);

        System.out.println("Summan av i minuter i feb - feb > " +
                           SummaJunToJUnTotalMinutes);

      }
    }
    else if (mounth.trim().equals(Jun) && inputMounth.trim().equals(JulDa)) {

      int summaJunToJulDays = 30 - checkMobileDay - 1 + checkInputDate; //checkMobileDay �r datumet p� mobilen, checkInputDate �r det inmatade datumet

      if (summaJunToJulDays == 0 || summaJunToJulDays < 0) { // om v�rdet �r noll s� utf�r f�ljande kod

        int totalMinutes = xMinuter + summaInputclock; // plus delsumma 3
        setConvertInt(totalMinutes);
        System.out.println(
            "Totala summan i minuter fram tills midnat om summa �r noll feb - mars > " +
            totalMinutes);

      }
      else {

        int summaJunToJulMinutes = summaJunToJulDays * 24 * 60;
        int SummaJunToJulTotalMinutes = summaJunToJulMinutes + xMinuter +
            summaInputclock; // plus delsumma 3
        setConvertInt(SummaJunToJulTotalMinutes);

        System.out.println("Summan i minuter fr�n feb - mars > " +
                           SummaJunToJulTotalMinutes);
      }

    }
    else if (mounth.trim().equals(Jun) && inputMounth.trim().equals(AugDa)) {

      int summaMajToJulDays = 30 - checkMobileDay - 1 + 31 + checkInputDate;
      int summaMajToJulMinutes = summaMajToJulDays * 24 * 60;
      int SummaMajToJulTotalMinutes = summaMajToJulMinutes + xMinuter +
          summaInputclock; // plus delsumma 3
      setConvertInt(SummaMajToJulTotalMinutes);
      System.out.println("Den totala summan i minuter fr�n Feb - April > " +
                         SummaMajToJulTotalMinutes);

    }

    // -------------------------- J U L I ------------------------------------------------------------------------------------------

    if (mounth.trim().equals(Jul) && inputMounth.trim().equals(JulDa)) { // om Jan �r lika med Jan 'och' inputen �r lika med 01 (januari)

      int summaJulToJulDays = checkInputDate - checkMobileDay - 1; // feb = 28 dagar  - 1 - inskrivet v�rde

      if (summaJulToJulDays == 0 || summaJulToJulDays < 0) { // om v�rdet �r noll s� utf�r f�ljande kod

        int totalMinutes = xMinuter + summaInputclock; // plus delsumma 3
        setConvertInt(totalMinutes);

        System.out.println(
            "Totala summan i minuter fram till midnatt om dagar �r noll 'februari' > " +
            totalMinutes);

      }
      else { // om v�rdet �r mer �n noll s� utf�r f�ljande kod....

        int summaJulToJulMinutes = summaJulToJulDays * 24 * 60;
        int SummaJulToJulTotalMinutes = summaJulToJulMinutes + xMinuter +
            summaInputclock; // plus delsumma 3
        setConvertInt(SummaJulToJulTotalMinutes);

        System.out.println("Summan av i minuter i feb - feb > " +
                           SummaJulToJulTotalMinutes);

      }
    }
    else if (mounth.trim().equals(Jul) && inputMounth.trim().equals(AugDa)) {

      int summaJulToAugDays = 31 - checkMobileDay - 1 + checkInputDate; //checkMobileDay �r datumet p� mobilen, checkInputDate �r det inmatade datumet

      if (summaJulToAugDays == 0 || summaJulToAugDays < 0) { // om v�rdet �r noll s� utf�r f�ljande kod

        int totalMinutes = xMinuter + summaInputclock; // plus delsumma 3
        setConvertInt(totalMinutes);
        System.out.println(
            "Totala summan i minuter fram tills midnat om summa �r noll feb - mars > " +
            totalMinutes);

      }
      else {

        int summaJulToAugMinutes = summaJulToAugDays * 24 * 60;
        int SummaJulToAugTotalMinutes = summaJulToAugMinutes + xMinuter +
            summaInputclock; // plus delsumma 3
        setConvertInt(SummaJulToAugTotalMinutes);

        System.out.println("Summan i minuter fr�n feb - mars > " +
                           SummaJulToAugTotalMinutes);
      }

    }
    else if (mounth.trim().equals(Jul) && inputMounth.trim().equals(SepDa)) {

      int summaJulToSepDays = 31 - checkMobileDay - 1 + 31 + checkInputDate;
      int summaJulToSepMinutes = summaJulToSepDays * 24 * 60;
      int SummaJulToSepTotalMinutes = summaJulToSepMinutes + xMinuter +
          summaInputclock; // plus delsumma 3
      setConvertInt(SummaJulToSepTotalMinutes);
      System.out.println("Den totala summan i minuter fr�n Feb - April > " +
                         SummaJulToSepTotalMinutes);

    }

    // -------------------------- A U G U S T I ------------------------------------------------------------------------------------------

    if (mounth.trim().equals(Aug) && inputMounth.trim().equals(AugDa)) { // om Jan �r lika med Jan 'och' inputen �r lika med 01 (januari)

      int summaAugToAugDays = checkInputDate - checkMobileDay - 1; // feb = 28 dagar  - 1 - inskrivet v�rde

      if (summaAugToAugDays == 0 || summaAugToAugDays < 0) { // om v�rdet �r noll s� utf�r f�ljande kod

        int totalMinutes = xMinuter + summaInputclock; // plus delsumma 3
        setConvertInt(totalMinutes);

        System.out.println(
            "Totala summan i minuter fram till midnatt om dagar �r noll 'februari' > " +
            totalMinutes);

      }
      else { // om v�rdet �r mer �n noll s� utf�r f�ljande kod....

        int summaAugToAugMinutes = summaAugToAugDays * 24 * 60;
        int SummaAugToAugTotalMinutes = summaAugToAugMinutes + xMinuter +
            summaInputclock; // plus delsumma 3
        setConvertInt(SummaAugToAugTotalMinutes);

        System.out.println("Summan av i minuter i feb - feb > " +
                           SummaAugToAugTotalMinutes);

      }
    }
    else if (mounth.trim().equals(Aug) && inputMounth.trim().equals(SepDa)) {

      int summaAugToSepDays = 31 - checkMobileDay - 1 + checkInputDate; //checkMobileDay �r datumet p� mobilen, checkInputDate �r det inmatade datumet

      if (summaAugToSepDays == 0 || summaAugToSepDays < 0) { // om v�rdet �r noll s� utf�r f�ljande kod

        int totalMinutes = xMinuter + summaInputclock; // plus delsumma 3
        setConvertInt(totalMinutes);
        System.out.println(
            "Totala summan i minuter fram tills midnat om summa �r noll feb - mars > " +
            totalMinutes);

      }
      else {

        int summaAugToSepMinutes = summaAugToSepDays * 24 * 60;
        int SummaAugToSepTotalMinutes = summaAugToSepMinutes + xMinuter +
            summaInputclock; // plus delsumma 3
        setConvertInt(SummaAugToSepTotalMinutes);

        System.out.println("Summan i minuter fr�n feb - mars > " +
                           SummaAugToSepTotalMinutes);
      }

    }
    else if (mounth.trim().equals(Aug) && inputMounth.trim().equals(OktDa)) {

      int summaAugToOktDays = 31 - checkMobileDay - 1 + 30 + checkInputDate;
      int summaAugToOktMinutes = summaAugToOktDays * 24 * 60;
      int SummaAugToOktTotalMinutes = summaAugToOktMinutes + xMinuter +
          summaInputclock; // plus delsumma 3
      setConvertInt(SummaAugToOktTotalMinutes);
      System.out.println("Den totala summan i minuter fr�n Feb - April > " +
                         SummaAugToOktTotalMinutes);

    }

    // -------------------------- S E P T E M B E R ------------------------------------------------------------------------------------------

    if (mounth.trim().equals(Sep) && inputMounth.trim().equals(SepDa)) { // om Jan �r lika med Jan 'och' inputen �r lika med 01 (januari)

      int summaSepToSepDays = checkInputDate - checkMobileDay - 1; // feb = 28 dagar  - 1 - inskrivet v�rde

      if (summaSepToSepDays == 0 || summaSepToSepDays < 0) { // om v�rdet �r noll s� utf�r f�ljande kod

        int totalMinutes = xMinuter + summaInputclock; // plus delsumma 3
        setConvertInt(totalMinutes);

        System.out.println(
            "Totala summan i minuter fram till midnatt om dagar �r noll 'februari' > " +
            totalMinutes);

      }
      else { // om v�rdet �r mer �n noll s� utf�r f�ljande kod....

        int summaSepToSepMinutes = summaSepToSepDays * 24 * 60;
        int SummaSepToSepTotalMinutes = summaSepToSepMinutes + xMinuter +
            summaInputclock; // plus delsumma 3
        setConvertInt(SummaSepToSepTotalMinutes);

        System.out.println("Summan av i minuter i feb - feb > " +
                           SummaSepToSepTotalMinutes);

      }
    }
    else if (mounth.trim().equals(Sep) && inputMounth.trim().equals(OktDa)) {

      int summaSepToOktDays = 30 - checkMobileDay - 1 + checkInputDate; //checkMobileDay �r datumet p� mobilen, checkInputDate �r det inmatade datumet

      if (summaSepToOktDays == 0 || summaSepToOktDays < 0) { // om v�rdet �r noll s� utf�r f�ljande kod

        int totalMinutes = xMinuter + summaInputclock; // plus delsumma 3
        setConvertInt(totalMinutes);
        System.out.println(
            "Totala summan i minuter fram tills midnat om summa �r noll feb - mars > " +
            totalMinutes);

      }
      else {

        int summaSepToOktMinutes = summaSepToOktDays * 24 * 60;
        int SummaSepToOktTotalMinutes = summaSepToOktMinutes + xMinuter +
            summaInputclock; // plus delsumma 3
        setConvertInt(SummaSepToOktTotalMinutes);

        System.out.println("Summan i minuter fr�n feb - mars > " +
                           SummaSepToOktTotalMinutes);
      }

    }
    else if (mounth.trim().equals(Sep) && inputMounth.trim().equals(NovDa)) {

      int summaSepToNovDays = 30 - checkMobileDay - 1 + 31 + checkInputDate;
      int summaSepToNovMinutes = summaSepToNovDays * 24 * 60;
      int SummaSepToNovTotalMinutes = summaSepToNovMinutes + xMinuter +
          summaInputclock; // plus delsumma 3
      setConvertInt(SummaSepToNovTotalMinutes);
      System.out.println("Den totala summan i minuter fr�n Feb - April > " +
                         SummaSepToNovTotalMinutes);

    }

    // -------------------------- O K T O B E R ------------------------------------------------------------------------------------------

    if (mounth.trim().equals(Oct) && inputMounth.trim().equals(OktDa)) { // om Jan �r lika med Jan 'och' inputen �r lika med 01 (januari)

      int summaOctToOctDays = checkInputDate - checkMobileDay - 1; // feb = 28 dagar  - 1 - inskrivet v�rde

      if (summaOctToOctDays == 0 || summaOctToOctDays < 0) { // om v�rdet �r noll s� utf�r f�ljande kod

        int totalMinutes = xMinuter + summaInputclock; // plus delsumma 3
        setConvertInt(totalMinutes);

        System.out.println(
            "Totala summan i minuter fram till midnatt om dagar �r noll 'februari' > " +
            totalMinutes);

      }
      else { // om v�rdet �r mer �n noll s� utf�r f�ljande kod....

        int summaOctToOctMinutes = summaOctToOctDays * 24 * 60;
        int SummaOctToOctTotalMinutes = summaOctToOctMinutes + xMinuter +
            summaInputclock; // plus delsumma 3
        setConvertInt(SummaOctToOctTotalMinutes);

        System.out.println("Summan av i minuter i feb - feb > " +
                           SummaOctToOctTotalMinutes);

      }
    }
    else if (mounth.trim().equals(Oct) && inputMounth.trim().equals(NovDa)) {

      int summaOctToNovDays = 31 - checkMobileDay - 1 + checkInputDate; //checkMobileDay �r datumet p� mobilen, checkInputDate �r det inmatade datumet

      if (summaOctToNovDays == 0 || summaOctToNovDays < 0) { // om v�rdet �r noll s� utf�r f�ljande kod

        int totalMinutes = xMinuter + summaInputclock; // plus delsumma 3
        setConvertInt(totalMinutes);
        System.out.println(
            "Totala summan i minuter fram tills midnat om summa �r noll feb - mars > " +
            totalMinutes);

      }
      else {

        int summaOctToNovMinutes = summaOctToNovDays * 24 * 60;
        int SummaOctToNovTotalMinutes = summaOctToNovMinutes + xMinuter +
            summaInputclock; // plus delsumma 3
        setConvertInt(SummaOctToNovTotalMinutes);

        System.out.println("Summan i minuter fr�n feb - mars > " +
                           SummaOctToNovTotalMinutes);
      }

    }
    else if (mounth.trim().equals(Oct) && inputMounth.trim().equals(DecDa)) {

      int summaOctToDecDays = 31 - checkMobileDay - 1 + 30 + checkInputDate;
      int summaOctToDecMinutes = summaOctToDecDays * 24 * 60;
      int SummaOctToDecTotalMinutes = summaOctToDecMinutes + xMinuter +
          summaInputclock; // plus delsumma 3
      setConvertInt(SummaOctToDecTotalMinutes);
      System.out.println("Den totala summan i minuter fr�n Feb - April > " +
                         SummaOctToDecTotalMinutes);

    }

    // -------------------------- N O V E M B E R ------------------------------------------------------------------------------------------

    if (mounth.trim().equals(Nov) && inputMounth.trim().equals(NovDa)) { // om Jan �r lika med Jan 'och' inputen �r lika med 01 (januari)

      int summaNovToNovDays = checkInputDate - checkMobileDay - 1; // feb = 28 dagar  - 1 - inskrivet v�rde

      if (summaNovToNovDays == 0 || summaNovToNovDays < 0) { // om v�rdet �r noll s� utf�r f�ljande kod

        int totalMinutes = xMinuter + summaInputclock; // plus delsumma 3
        setConvertInt(totalMinutes);

        System.out.println(
            "Totala summan i minuter fram till midnatt om dagar �r noll 'februari' > " +
            totalMinutes);

      }
      else { // om v�rdet �r mer �n noll s� utf�r f�ljande kod....

        int summaNovToNovMinutes = summaNovToNovDays * 24 * 60;
        int SummaNovToNovTotalMinutes = summaNovToNovMinutes + xMinuter +
            summaInputclock; // plus delsumma 3
        setConvertInt(SummaNovToNovTotalMinutes);

        System.out.println("Summan av i minuter i feb - feb > " +
                           SummaNovToNovTotalMinutes);

      }
    }
    else if (mounth.trim().equals(Nov) && inputMounth.trim().equals(DecDa)) {

      int summaNovToDecDays = 30 - checkMobileDay - 1 + checkInputDate; //checkMobileDay �r datumet p� mobilen, checkInputDate �r det inmatade datumet

      if (summaNovToDecDays == 0 || summaNovToDecDays < 0) { // om v�rdet �r noll s� utf�r f�ljande kod

        int totalMinutes = xMinuter + summaInputclock; // plus delsumma 3
        setConvertInt(totalMinutes);
        System.out.println(
            "Totala summan i minuter fram tills midnat om summa �r noll feb - mars > " +
            totalMinutes);

      }
      else {

        int summaNovToDecMinutes = summaNovToDecDays * 24 * 60;
        int SummaNovToDecTotalMinutes = summaNovToDecMinutes + xMinuter +
            summaInputclock; // plus delsumma 3
        setConvertInt(SummaNovToDecTotalMinutes);

        System.out.println("Summan i minuter fr�n feb - mars > " +
                           SummaNovToDecTotalMinutes);
      }

    }
    else if (mounth.trim().equals(Nov) && inputMounth.trim().equals(JanDa)) {

      int summaNovToJanDays = 30 - checkMobileDay - 1 + 31 + checkInputDate;
      int summaNovToJanMinutes = summaNovToJanDays * 24 * 60;
      int SummaNovToJanTotalMinutes = summaNovToJanMinutes + xMinuter +
          summaInputclock; // plus delsumma 3
      setConvertInt(SummaNovToJanTotalMinutes);
      System.out.println("Den totala summan i minuter fr�n Feb - April > " +
                         SummaNovToJanTotalMinutes);

    }

    // -------------------------- D E C E M B E R ------------------------------------------------------------------------------------------

    if (mounth.trim().equals(Dec) && inputMounth.trim().equals(DecDa)) { // om Jan �r lika med Jan 'och' inputen �r lika med 01 (januari)

      int summaDecToDecDays = checkInputDate - checkMobileDay - 1; // feb = 28 dagar  - 1 - inskrivet v�rde

      if (summaDecToDecDays == 0 || summaDecToDecDays < 0) { // om v�rdet �r noll s� utf�r f�ljande kod

        int totalMinutes = xMinuter + summaInputclock; // plus delsumma 3
        setConvertInt(totalMinutes);

        System.out.println(
            "Totala summan i minuter fram till midnatt om dagar �r noll 'februari' > " +
            totalMinutes);

      }
      else { // om v�rdet �r mer �n noll s� utf�r f�ljande kod....

        int summaDecToDecMinutes = summaDecToDecDays * 24 * 60;
        int SummaDecToDecTotalMinutes = summaDecToDecMinutes + xMinuter +
            summaInputclock; // plus delsumma 3
        setConvertInt(SummaDecToDecTotalMinutes);

        System.out.println("Summan av i minuter i feb - feb > " +
                           SummaDecToDecTotalMinutes);

      }
    }
    else if (mounth.trim().equals(Dec) && inputMounth.trim().equals(JanDa)) {

      int summaDecToJanDays = 31 - checkMobileDay - 1 + checkInputDate; //checkMobileDay �r datumet p� mobilen, checkInputDate �r det inmatade datumet

      if (summaDecToJanDays == 0 || summaDecToJanDays < 0) { // om v�rdet �r noll s� utf�r f�ljande kod

        int totalMinutes = xMinuter + summaInputclock; // plus delsumma 3
        setConvertInt(totalMinutes);
        System.out.println(
            "Totala summan i minuter fram tills midnat om summa �r noll feb - mars > " +
            totalMinutes);

      }
      else {

        int summaDecToJanMinutes = summaDecToJanDays * 24 * 60;
        int SummaDecToJanTotalMinutes = summaDecToJanMinutes + xMinuter +
            summaInputclock; // plus delsumma 3
        setConvertInt(SummaDecToJanTotalMinutes);

        System.out.println("Summan i minuter fr�n feb - mars > " +
                           SummaDecToJanTotalMinutes);
      }

    }
    else if (mounth.trim().equals(Dec) && inputMounth.trim().equals(FebDa)) {

      int summaDecToFebDays = 31 - checkMobileDay - 1 + 31 + checkInputDate;
      int summaDecToFebMinutes = summaDecToFebDays * 24 * 60;
      int SummaDecToFebTotalMinutes = summaDecToFebMinutes + xMinuter +
          summaInputclock; // plus delsumma 3
      setConvertInt(SummaDecToFebTotalMinutes);
      System.out.println("Den totala summan i minuter fr�n Feb - April > " +
                         SummaDecToFebTotalMinutes);

    }

  }

}

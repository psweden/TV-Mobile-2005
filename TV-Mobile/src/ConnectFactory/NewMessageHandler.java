package ConnectFactory;

import javax.wireless.messaging.*;

public class NewMessageHandler implements Runnable  {

    private Thread th = new Thread(this);
    private MessageConnection mc;

    public NewMessageHandler() {

    }
    public NewMessageHandler(MessageConnection mc) {
        this.mc = mc;
    }

  public void start() {// startar med anrop fr�n Connectserver-classen
      try {
        th.start();
      }
      catch (Exception e) {
      }

    }
    public String getSMSC() {
        return System.getProperty("wireless.messaging.sms.smsc");
    }

  /**
   * synkroniserar och startar alla tr�d i hela applikationen
   */
  public void run() {
  }

}



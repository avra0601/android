package com.example.dell.alarm;

/**
 * Created by DELL on 18-01-2016.
 */

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.TextView;
import android.widget.Toast;
import android.telephony.PhoneNumberUtils;
public class SMSReceiver extends BroadcastReceiver

{
    @Override
    public void onReceive(Context context, Intent intent)
    {

//---get the SMS message passed in---
        Bundle bundle = intent.getExtras();
        SmsMessage[] msgs = null;
        String str = "";
        if (bundle != null)
        {
//---retrieve the SMS message received---
            Object[] pdus = (Object[]) bundle.get("pdus");
            msgs = new SmsMessage[pdus.length];
            for (int i=0; i<msgs.length; i++){
                msgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                str += msgs[i].getOriginatingAddress();
                str += " ";
                str += msgs[i].getMessageBody().toString();

            }
//---display the new SMS message---
         //   Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
            Intent mainActivityIntent = new Intent(context, MainActivity.class);
            mainActivityIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(mainActivityIntent);
//---send a broadcast intent to update the SMS received in the activity---
           String mess[]=str.split(" ");
            //Toast.makeText(context, mess[0] + " = " +MainActivity.g_phone, Toast.LENGTH_SHORT).show();
           if(PhoneNumberUtils.compare(mess[0], String.valueOf(MainActivity.g_phone)) && mess[1].equalsIgnoreCase("wru"))
            {
            Intent broadcastIntent = new Intent();
              //  Toast.makeText(context, "1", Toast.LENGTH_SHORT).show();
            broadcastIntent.setAction("SMS_RECEIVED_ACTION");
            broadcastIntent.putExtra("sms", str);
            context.sendBroadcast(broadcastIntent);

            }


            {
            //    Toast.makeText(context, "outside if", Toast.LENGTH_SHORT).show();
            }
        }
    }
}

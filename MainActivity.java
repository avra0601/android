package com.example.dell.alarm;
import java.io.IOException;
import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.view.*;
import android.graphics.Color;
import android.os.Bundle;
import android.app.Activity;
import java.util.Calendar;
import android.telephony.SmsManager;
import android.util.Log;

import java.util.ArrayList;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;
import android.os.BatteryManager;
import android.os.Handler;
import android.content.IntentFilter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.content.BroadcastReceiver;
import android.os.Vibrator;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;import android.net.Uri;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;

import android.provider.Settings;
import android.view.View.OnClickListener;
import android.widget.ProgressBar;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import java.text.DecimalFormat;

public class MainActivity extends Activity implements OnItemSelectedListener
        ,OnClickListener
{
    String last=null;
long next=0;
    int test=0;
   static boolean service=false;
    PendingIntent pendingIntent=null;
    Intent intent=null;
    AlarmManager alman=null;
    int sleep = 0;IntentFilter intentFilter;
    int stop = 1;
    int minutes;public  boolean fl = true;
    int oldmin; Spinner spinner=null;
    public long get_phone()
    {

        return g_phone;
    }
    static MainActivity obj;
    MediaPlayer mysound;
    public TextView status=null;
    int emerflag=0;
    int onlysms=0;
    public LocationManager locationMangaer = null;
    public  LocationListener locationListener = null;
    int mess = 0;
    String pos = null;
    private Button btnGetLocation = null;
    private EditText editLocation = null;
    private TextView t1 = null;
    private ProgressBar pb = null;
    double temp = 0;
    public static long  g_phone=0;
    double distance = 0;
    private static final String TAG = "Debug";
    private Boolean flag = false;
    private int smsfl=0;
    private Handler handler = new Handler();
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (Integer.parseInt(android.os.Build.VERSION.SDK) > 5
                && keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            Log.d("CDA", "onKeyDown Called");
            onBackPressed();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    public void onBackPressed() {
        Log.d("CDA", "onBackPressed Called");
        Intent setIntent = new Intent(Intent.ACTION_MAIN);
        setIntent.addCategory(Intent.CATEGORY_HOME);
        setIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(setIntent);    }
    private  BroadcastReceiver intentReceiver = new BroadcastReceiver() {
        IntentFilter intentFilter;
        @Override
        public void onReceive(Context context, Intent intent) {
//---display the SMS received in the TextView---
            // TextView SMSes = (TextView) findViewById(R.id.textView1);
         //String mess[]=intent.getExtras().getString("sms").split(" ");
            Toast.makeText(getApplicationContext(), "2", Toast.LENGTH_SHORT).show();

            if(stop==1) {
                Toast.makeText(getApplicationContext(), "3", Toast.LENGTH_SHORT).show();
        try
        {
            locationListener = new MyLocationListener();
        }
        catch(Exception e)
        {

        }
                try {
            locationMangaer = (LocationManager)
                    getSystemService(Context.LOCATION_SERVICE);
            locationMangaer.requestLocationUpdates(LocationManager
                    .GPS_PROVIDER, 5000, 10, locationListener);
            Toast.makeText(getApplicationContext(),"Inside the loca ",Toast.LENGTH_LONG).show();
        }
                catch(Exception e)
                {


                }
                mess=1;
                oldmin=minutes;
                smsfl=1;
            }
        }
    };
    public Button emer=null;
    String battery = null;
    private Button b1 = null;
    double x2 = 0.00;
    double y2 = 0.00;
    SmsManager sms = null;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    private double distance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        return (dist);
    }
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
//        String item = parent.getItemAtPosition(position).toString();

        if(parent.getSelectedItem().equals("5 minutes"))
        {
            minutes=5;
            onlysms=0;
        }
        else if (parent.getSelectedItem().equals("10 minutes"))
        {
            minutes=9;
            onlysms=0;
        }
        else if(parent.getSelectedItem().equals("15 minutes"))
        {
            minutes=14;
            onlysms=0;
        }
        else if(parent.getSelectedItem().equals("30 minutes"))
        {
            minutes=28;
            onlysms=0;
        }
        else if(parent.getSelectedItem().equals("45 minutes"))
        {
            minutes=43;
            onlysms=0;
        }
        else if(parent.getSelectedItem().equals("1 hour"))
        {
            minutes=60;
            onlysms=0;
        }
        else
        {
            onlysms=1;
            minutes=0;
        }
        // Showing selected spinner item
  //      Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
    }
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }
    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }
    public static MainActivity getInstance(){
        return   obj;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
obj=this;
        spinner = (Spinner) findViewById(R.id.spinner);
        status=(TextView)findViewById(R.id.status);
        editLocation=(EditText)findViewById(R.id.phone);
        btnGetLocation = (Button) findViewById(R.id.start);
        btnGetLocation.setOnClickListener(this);
    //  sendSMS("9597905228","message");
        Toast.makeText(getApplicationContext(),"before",Toast.LENGTH_SHORT).show();
        // sms = SmsManager.getDefault();
      //  sms.sendTextMessage("9597905228", null, "message", null, null);
        intentFilter = new IntentFilter();
        // Spinner element

        // Spinner click listener

        spinner.setOnItemSelectedListener(this);

        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
        categories.add("Please choose an option");
        categories.add("5 minutes");
        spinner.setEnabled(true);

        categories.add("10 minutes");
        categories.add("15 minutes");
        categories.add("30 minutes");
        categories.add("45 minutes");
        categories.add("1 hour");
        categories.add("Only using SMS command");
        // Creating adapter for spinner
emer=(Button)findViewById(R.id.emergency);
        emer.setEnabled(false);
        emer.setBackgroundColor(Color.parseColor("#200A0A"));
        emer.setOnClickListener(
                new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                      {
                          try {
                              locationListener=new MyLocationListener();
                          }
                          catch(Exception e)
                          {

                          }
                          try {


                              locationMangaer = (LocationManager)
                                      getSystemService(Context.LOCATION_SERVICE);
                              locationMangaer.requestLocationUpdates(LocationManager
                                      .GPS_PROVIDER, 5000, 10, locationListener);
                          }
                          catch(Exception e)
                          {

                          }
                              mess = 1;
                              stop = 1;
                              emerflag = 1;

                    }
                }}
        );

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);
        intentFilter.addAction("SMS_RECEIVED_ACTION");
        // SmsManager sms = SmsManager.getDefault();
        //    sms.sendTextMessage("9597905228", null, "message", null, null);
        //
        registerReceiver(intentReceiver, intentFilter);
//        Toast.makeText(getApplicationContext(),"after",Toast.LENGTH_SHORT).show();
        //if you want to lock screen for always Portrait mode
        setRequestedOrientation(ActivityInfo
                .SCREEN_ORIENTATION_PORTRAIT);
        t1 = (TextView) findViewById(R.id.t1);
      //  pb = (ProgressBar) findViewById(R.id.progressBar1);
        //pb.setVisibility(View.INVISIBLE);
        b1 = (Button) findViewById(R.id.stop);
        b1.setEnabled(false);
b1.setBackgroundColor(Color.parseColor("#200A0A"));
if(service==false) {
    locationMangaer = (LocationManager)
            getSystemService(Context.LOCATION_SERVICE);

    x2 = 0.0;
    onlysms=0;
    y2 = 0.0;

}

        if(service==true)
        {
            btnGetLocation.setEnabled(false);
            btnGetLocation.setBackgroundColor(Color.parseColor("#200A0A"));
            editLocation.setBackgroundColor(Color.parseColor("#200A0A"));
            spinner.setBackgroundColor(Color.parseColor("#200A0A"));
            editLocation.setEnabled(false);
            spinner.setEnabled(false);
            t1.setText(last);

        }
        b1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                btnGetLocation.setEnabled(true);
                editLocation.setBackgroundColor(Color.parseColor("#FFFFFF"));
                spinner.setBackgroundColor(Color.parseColor("#FFFFFF"));
                editLocation.setTextColor(Color.parseColor("#200A0A"));
                editLocation.setEnabled(true);
                stop = 0;
if(service==true)
{
    stopService(new Intent(getBaseContext(), MyService.class));
    service=false;
}
                b1.setEnabled(false);
                b1.setBackgroundColor(Color.parseColor("#200A0A"));
                emer.setEnabled(false);
             emer.setBackgroundColor(Color.parseColor("#200A0A"));
                spinner.setEnabled(true);
              try
                {   locationMangaer.removeUpdates(locationListener);
locationMangaer=null;

                }
catch(Exception e)
{

}
                finally {
                  mess=0;
              }
                try
                {
                    locationListener = null;
                }
                catch
                        (Exception e)
                {

                }
                spinner.setEnabled(true);
emerflag=0;
                mess=0;
                status.setText("Tracking is stopped");
                btnGetLocation.setBackgroundColor(Color.parseColor("#afafaf"));
            }
        });
                // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public void onClick(View v) {
        flag = displayGpsStatus();     String regexStr = "^[0-9]*$";
if(!flag){
    Toast.makeText(getApplicationContext(),"no gp",Toast.LENGTH_LONG).show();
}

       if(editLocation.getText().toString().trim().matches(regexStr))
        {



            MyLocationListener m1 = new MyLocationListener();
            stop = 1;
    /*    TelephonyManager tele=(TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        GsmCellLocation cellLocation=(GsmCellLocation) tele.getCellLocation();
        int cellid=cellLocation.getCid();
 Toast.makeText(getApplicationContext(),""+cellLocation.getClass().getName(),Toast.LENGTH_LONG).show();*/

            try {
                g_phone = Long.parseLong(editLocation.getText().toString().trim());
                if (displayGpsStatus()) {
                    startService(new Intent(getBaseContext(), MyService.class));

                }

            }
            catch(Exception e)
            { Toast.makeText(getApplicationContext(),"Please enter valid number ... ",Toast.LENGTH_LONG).show();


            }
            //write code here for success
        }
        else
         {
            // write code for failure
       Toast.makeText(getApplicationContext(),"Please enter the person who is going to track you ... ",Toast.LENGTH_LONG).show();
        }

    }

    /*----Method to Check GPS is enable or disable ----- */
    private Boolean displayGpsStatus() {
        ContentResolver contentResolver = getBaseContext()
                .getContentResolver();
        boolean gpsStatus = Settings.Secure
                .isLocationProviderEnabled(contentResolver,
                        LocationManager.GPS_PROVIDER);
        if (gpsStatus) {
            return true;

        } else {
            return false;
        }
    }

    /*----------Method to create an AlertBox ------------- */
    protected void alertbox(String title, String mymessage) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Your Device's GPS is Disable")
                .setCancelable(false)
                .setTitle("** Gps Status **")
                .setPositiveButton("Gps On",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // finish the current activity
                                // AlertBoxAdvance.this.finish();
                                Intent myIntent = new Intent(
                                        Settings.ACTION_SECURITY_SETTINGS);
                                startActivity(myIntent);
                                dialog.cancel();
                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // cancel the dialog box
                                dialog.cancel();
                            }
                        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    public void startbutton()
    {
    Log.v(TAG, "onClick");

    stop=1;   spinner.setEnabled(false);

//                pb.setVisibility(View.VISIBLE);

    btnGetLocation.setBackgroundColor(Color.parseColor("#200A0A"));
    locationListener = new MyLocationListener();
    Toast.makeText(getApplicationContext(),"Runth",Toast.LENGTH_LONG).show();
    status.setText("STATUS : Service Started Successfully...");
    if(onlysms==0) {


    try {
        locationMangaer = (LocationManager)
                getSystemService(Context.LOCATION_SERVICE);
        locationMangaer.requestLocationUpdates(LocationManager
                .GPS_PROVIDER, 5000, 10, locationListener);
        Toast.makeText(getApplicationContext(),"Inside the loca ",Toast.LENGTH_LONG).show();
    }
    catch(Exception e)
    {


    } }
    editLocation.setBackgroundColor(Color.parseColor("#200A0A"));
    spinner.setBackgroundColor(Color.parseColor("#200A0A"));
    editLocation.setTextColor(Color.parseColor("#FFFFFF"));


    btnGetLocation.setEnabled(false);


    editLocation.setEnabled(false);
    emer.setEnabled(true);
    emer.setBackgroundColor(Color.parseColor("#afafaf"));
    b1.setEnabled(true);
    b1.setBackgroundColor(Color.parseColor("#afafaf"));
}

    public float getBatteryLevel() {
        Intent batteryIntent = registerReceiver(null, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        int level = batteryIntent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        int scale = batteryIntent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);

        // Error checking that probably isn't needed but I added just in case.
        if (level == -1 || scale == -1) {
            return 50.0f;
        }

        return ((float) level / (float) scale) * 100.0f;
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.dell.alarm/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.dell.alarm/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
    private void sendSMS(String phoneNumber, String message) {
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phoneNumber, null, message, null, null);
    }
    /*----------Listener class to get coordinates ------------- */

        public void setListener()
        { status.setText("setlisterner alarm");
            if (fl) {
          //      fl=false;
                Toast.makeText(getApplicationContext(), "Inside run", Toast.LENGTH_LONG).show();

                try
                {
                    locationListener=new MyLocationListener();
                }catch(Exception e)
                {

                }
                if (onlysms == 1) {

                } else {
                    try
                    {
                        if(locationListener==null)
                        {
                            locationListener=new MyLocationListener();
                        }
                    }
                    catch(Exception e)
                    {

                    }
                    try {
                        locationMangaer = (LocationManager)
                                getSystemService(Context.LOCATION_SERVICE);
                        locationMangaer.requestLocationUpdates(LocationManager
                                .GPS_PROVIDER, 5000, 10, locationListener);
                    }
                    catch(Exception e)
                    {

                    }
                    mess = 1;
                    sleep=0;
                    //   minutes=oldmin;
                }
            }


    }
    private class MyLocationListener implements LocationListener {
        @Override
        public void onLocationChanged(Location loc) {
            try {
                locationMangaer.removeUpdates(locationListener);
                locationMangaer=null;
            }
            catch(Exception e)
            {


            }
try
{
    locationListener=null;
}
catch(Exception e)
{

}
Toast.makeText(getApplicationContext(), "av", Toast.LENGTH_LONG).show();
            if (stop == 1) {
                status.setText("You are being tracked by " + g_phone);
                //       mysound = MediaPlayer.create(MainActivity.this, R.raw.alarm1);
                //  mysound.start();
                //  editLocation.setText("");
                Toast.makeText(getApplicationContext(), "Avinash", Toast.LENGTH_LONG).show();
                //    pb.setVisibility(View.INVISIBLE);
                Toast.makeText(getBaseContext(), "Location changed : Lat: " +
                                loc.getLatitude() + " Lng: " + loc.getLongitude(),
                        Toast.LENGTH_SHORT).show();
                double x1 = loc.getLatitude();
                double y1 = loc.getLongitude();
                //   distance += distance(x1, y1, x2, y2);
                x2 = loc.getLatitude();
                y2 = loc.getLongitude();
                String longitude = "Longitude:" + loc.getLongitude();
                Log.v(TAG, longitude);
                String latitude = "Latitude:" + loc.getLatitude();
                Log.v(TAG, latitude);
                Vibrator vibrator = (Vibrator)
                        getSystemService(Context.VIBRATOR_SERVICE);
                //vibrator.vibrate(2000);


      /* Intent in=new Intent(Intent.ACTION_CALL);
            try {
                in.setData(Uri.parse("tel:9597905228"));
                startActivity(in);

                try {
                    Thread.sleep(5000);
                } catch (Exception e) {

                }
            }


            catch (android.content.ActivityNotFoundException ex){
                Toast.makeText(getApplicationContext(),"yourActivity is not founded",Toast.LENGTH_SHORT).show();
            }*/

    /*----------to get City-Name from coordinates ------------- */

                String cityName = null;
                String c = null;

                Geocoder gcd = new Geocoder(getBaseContext(),
                        Locale.getDefault());
                List<Address> addresses;
                try {
                    addresses = gcd.getFromLocation(loc.getLatitude(), loc
                            .getLongitude(), 1);
                    //  Toast.makeText(getApplicationContext(), "Avinash try", Toast.LENGTH_LONG).show();
                    if (addresses.size() > 0)
                        System.out.println(addresses.get(0).getLocality());

                    cityName = addresses.get(0).getAddressLine(0);


                    c = addresses.get(0).getLocality();
                    if (addresses.get(0).getPostalCode() != null) {
                        pos = addresses.get(0).getPostalCode();
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (NullPointerException ne) {

                }
                Toast.makeText(getApplicationContext(), "Avinashsms", Toast.LENGTH_LONG).show();
                // String s = longitude + "\n" + latitude + "\n\nMy Currrent City is: " + cityName + "\nAltitude : " + loc.getAltitude() + "\nDistance : " + distance + "\n" + c;
                // editLocation.setText(s);
                String message = "23.2";
                DecimalFormat precision = new DecimalFormat("0.00");
                String phoneNo = String.valueOf(g_phone);
                DecimalFormat bat=new DecimalFormat("0.0");
                if (emerflag == 0) {
                    message = "Battery:" + bat.format(getBatteryLevel()) + "%" + "\nLaT:" + precision.format(x2) + "\n" + "LonG:" + precision.format(y2) + "\n" + "Pincode:" + pos + "\nAddress:" + cityName + "\nCity:" + c ;
                } else {
                    emerflag = 0;
                    message = "Battery : " + bat.format(getBatteryLevel()) + "%"+"EMERGENCY!!!\n" + "LaT:" + precision.format(x2) + "\n" + "LonG:" + precision.format(y2) + "\n" + "Pincode:" + pos + "\nAddress:" + cityName + "\nCity:" + c;
                }
                if (mess == 1) {

                    try {
                        mess = 0;
                        // sms = SmsManager.getDefault();
                        char b[]=message.toCharArray();
                        String s="";
                        for(int i=0;i<155 && i<message.length();i++)
                        {
                            s+=b[i];
                        }
                       sendSMS(phoneNo, s);
                        Toast.makeText(getApplicationContext(), "Phone no : " + phoneNo + "Message :" + message, Toast.LENGTH_SHORT).show();
                        //     sendSMS("9597905228","hi");
                    sleep=0;
                    } catch (Exception e)
                    //
                    {
                        // Toast.makeText(getApplicationContext(), "SMS faild, please try again.", Toast.LENGTH_LONG).show();
                        // e.printStackTrace();
                        sleep = 0;
                    }



                    Calendar ci = Calendar.getInstance();
                    Toast.makeText(getApplicationContext(), "Avinashsms", Toast.LENGTH_LONG).show();
                    String CiDateTime = "" + ci.get(Calendar.YEAR) + "-" +
                            (ci.get(Calendar.MONTH) + 1) + "-" +
                            ci.get(Calendar.DAY_OF_MONTH) + " " +
                            ci.get(Calendar.HOUR) + ":" +
                            ci.get(Calendar.MINUTE) + ":" +
                            ci.get(Calendar.SECOND);
                    //  int level = intent.getIntExtra("level", 0);

                    t1.setText("Last Updated on " + CiDateTime);
last="Last Updated on " + CiDateTime;
                    //   Toast.makeText(getApplicationContext(), "SMS sent.", Toast.LENGTH_LONG).show();
                    sleep = 0;
                    //mess=0;

                }
                if(smsfl==0) {
                    Calendar calNow = Calendar.getInstance();
                    Calendar calSet = (Calendar) calNow.clone();
                    Date now = new Date();

                    SimpleDateFormat sdf = new SimpleDateFormat("mm");
                    String formattedTime = sdf.format(now).trim();
                    sdf = new SimpleDateFormat("HH");
                    int min = Integer.parseInt(formattedTime);
                    int hour = Integer.parseInt(sdf.format(now).trim().toString());
                    int tem = min + minutes;
                    if (tem >= 60) {
                        min = 60-tem;
                        hour=hour+1;
                        tem = hour + 1;
                        if (tem >= 24) {
                            hour = 0;
                        }
                    }


                    calSet.set(Calendar.HOUR_OF_DAY, hour);
                    calSet.set(Calendar.MINUTE, min + minutes);
                    calSet.set(Calendar.SECOND, 0);
                    calSet.set(Calendar.MILLISECOND, 0);
                    if (calSet.compareTo(calNow) <= 0) {
                        //Today Set time passed, count to tomorrow
                        calSet.add(Calendar.DATE, 1);
                    }

                    setAlarm(calSet);
                }

smsfl=0;
                if (sleep == 0) {
                    {
          /*      handler=new Handler();
                Runnable r=new Runnable() {
                    public void run() {
                        mess=1;

                        Toast.makeText(getApplicationContext(),"mess = 1",Toast.LENGTH_LONG).show();
                    }
                };
                handler.postDelayed(r, 1000 * 60 * 10);
                 Toast.makeText(getApplicationContext(),"mess = 1 after",Toast.LENGTH_LONG).show();
             //   Thread.sleep(1000 * 30);

                //mess=1;*/
                        //       //    locationMangaer=null;
                        try {

                        }catch(Exception e) {
                        }
                       try{


                        }
                        catch(Exception e)
                        {

                        }
                        try
                        {
                           // locationMangaer=null;
                        }catch(Exception e)
                        {


                        }
                       int temp=minutes;
                 /*           handler = new Handler();
                            Runnable r1 = new Runnable() {
                                public void run() {
                                    Toast.makeText(getApplicationContext(), "Inside res run", Toast.LENGTH_LONG).show();

                                }
                            };
                            handler.postDelayed(r1, 1000 * 60 * 5);

                            handler = new Handler();
                        Runnable r = new Runnable() {
                            public void run() {
                                if (fl == true) {
                                   // fl=false;
                                    Toast.makeText(getApplicationContext(), "Inside run", Toast.LENGTH_LONG).show();

                                   try
                                   {
                                       locationListener=new MyLocationListener();
                                   }catch(Exception e)
                                   {

                                   }
                                    if (onlysms == 1) {

                                    } else {
                                        try
                                        {
                                            if(locationListener==null)
                                            {
                                                locationListener=new MyLocationListener();
                                            }
                                        }
                                        catch(Exception e)
                                        {

                                        }
                                        try {
                                            locationMangaer = (LocationManager)
                                                    getSystemService(Context.LOCATION_SERVICE);
                                            locationMangaer.requestLocationUpdates(LocationManager
                                                    .GPS_PROVIDER, 5000, 10, locationListener);
                                        }
                                        catch(Exception e)
                                        {

                                        }
                                            mess = 1;
                                        //   minutes=oldmin;
                                    }
                                }
                            }
                        };handler.postDelayed(r,1000*60*minutes);*/
                                                //catch (InterruptedException e) {
                        //   e.printStackTrace();
                        //}
                    }
                }


            }
        }
        @Override
        public void onProviderDisabled(String provider) {
            // TODO Auto-generated method stub
        }

        @Override
        public void onProviderEnabled(String provider) {
            // TODO Auto-generated method stub
        }

        @Override
        public void onStatusChanged(String provider,
                                    int status, Bundle extras) {
            // TODO Auto-generated method stub
        }
        private void setAlarm(Calendar targetCal){



            Intent intent = new Intent(getBaseContext(), MyBroadcastReceiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(getBaseContext(), 1, intent, 0);
            AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
            alarmManager.set(AlarmManager.RTC_WAKEUP, targetCal.getTimeInMillis(), pendingIntent);
            intent=null;
            alarmManager=null;
            pendingIntent=null;
        }

    }
  }
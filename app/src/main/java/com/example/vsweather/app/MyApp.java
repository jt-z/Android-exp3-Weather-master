package com.example.vsweather.app;

import android.app.Application;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import com.example.vsweather.bean.City;
import com.example.vsweather.db.CityDB;


public class MyApp extends Application{
    private static final String TAG = "MyAPP";
    private static MyApp myApplication;
    private CityDB mCityDB;
    private List<City> mCityList;

    @Override
    public void onCreate(){
        super.onCreate();
        Log.d(TAG,"MyApplication->onCreate ... ");
        myApplication = this;
        mCityDB = openCityDB();

        initCityList();
    }


    private void initCityList() {
        mCityList = new ArrayList<City>();
        new Thread(new Runnable() {
            @Override
            public void run() {
                prepareCityList();
            }

        }).start();
    }

    private boolean prepareCityList() {
        mCityList = mCityDB.getAllCity();
        int i = 0;
        for(City city: mCityList){
            i++;
            String cityName = city.getCity();
            String cityCode = city.getNumber();
            Log.d(TAG,cityCode + "：" + cityName);
        }
        Log.d(TAG,"i=" + i);
        return true;
    }
    public List<City> getCityList(){
        return mCityList;
    }

    public static MyApp getInstance(){
        return myApplication;
    }

    private CityDB openCityDB(){
        String path = "/data"
                + Environment.getDataDirectory().getAbsolutePath()
                + File.separator + getPackageName()
                + File.separator + "databases1"
                + File.separator
                + CityDB.CITY_DB_NAME;
        File db = new File(path);
        Log.d(TAG,path);
        if (!db.exists()){
            String pathFolder = "/data"
                    + Environment.getDataDirectory().getAbsolutePath()
                    + File.separator + getPackageName()
                    + File.separator + "databases1"
                    + File.separator;
            File dirFirstFolder = new File(pathFolder);
            if (!dirFirstFolder.exists()){
                dirFirstFolder.mkdirs();
                Log.i("MyAPP","mkdirs");
            }
            Log.i("MyAPP","db is not exists");
            try {
                InputStream is = getAssets().open("city.db");
                FileOutputStream fos = new FileOutputStream(db);
                int len = -1;
                byte[] buffer = new byte[1024];
                while((len = is.read(buffer)) != -1){
                    fos.write(buffer,0,len);
                    fos.flush();
                }
                fos.close();
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return new CityDB(this,path);
    }
}
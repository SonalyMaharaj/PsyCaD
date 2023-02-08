package com.example.mom_mobile_as;


import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;


public class MySingleton {
    private static MySingleton instance;
    private RequestQueue requestQueue;
    private static Context ctx;
    private MySingleton(Context context){
        ctx=context;
        requestQueue=getRequestQueue();
    }

    public static synchronized  MySingleton getInstance(Context contex)
    {
        if(instance==null){
            instance=new MySingleton(contex);

        }
        return instance;
    }


    public RequestQueue getRequestQueue() {
        if(requestQueue==null){
            //getApplicationContext() is key, it keeps you from leaking the
            //Activity or BroadcastReceiver  if someone is one in.
            requestQueue= Volley.newRequestQueue(ctx.getApplicationContext());
        }

        return  requestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req){
        getRequestQueue().add(req);
    }

}

package com.arrowhc.harry.arrowhc;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class Staff_Activity extends AppCompatActivity {
    TextView tv;ListView lv;ArrayList<HashMap<String,String>> arrayList;String id,name;
    RequestQueue requestQueue;
    ImageButton addb,searchb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_);

        //initialize requestqueue && Arraylist
        requestQueue= Volley.newRequestQueue(getBaseContext());
        arrayList=new ArrayList<>();
        lv=(ListView)findViewById(R.id.list) ;
        tv=(TextView)findViewById(R.id.textView);
        tv.setText("hlo!");
         id=getIntent().getStringExtra("_id");
        name=getIntent().getStringExtra("name");
        if(id!=null)
        {
            tv.setText(name);
        }
        data();
    }

//dont forget to call this method in oncreate()

    public void data()
    {
        String  rurl="https://arrowhc.herokuapp.com/doctorpatients/"+id;

        //Toast.makeText(Staff_Activity.this,rurl, Toast.LENGTH_SHORT).show();
        JsonArrayRequest req = new JsonArrayRequest(rurl,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        String da="";

                      //  Toast.makeText(Staff_Activity.this, url, Toast.LENGTH_SHORT).show();
                        try {
                            for (int i = 0; i <response.length(); i++) {
                                JSONObject jresponse = response.getJSONObject(i);
                                String name = jresponse.getString("patient_name");
                                String did = jresponse.getString("doc_name");
                                String nurse= jresponse.getString("nurse_name");
                                String room = jresponse.getString("room_no");
                                String deptt=jresponse.getString("department");
                                //  da=da+name+","+did;
                                //  String dept = jresponse.getString("department");
                                HashMap<String,String> contact = new HashMap<>();

                                contact.put("namee",name);
                                contact.put("didd",did);
                                contact.put("nursee",nurse);
                                contact.put("roomm",room);
                                contact.put("dept",deptt);
                              //  da=contact.get("namee");
                                Toast.makeText(Staff_Activity.this,da, Toast.LENGTH_SHORT).show();

                                //Toast.makeText(Sign_In.this, name+","+usern+","+pswd, Toast.LENGTH_SHORT).show();

                                // Toast.makeText(Staff_Data.this, id, Toast.LENGTH_SHORT).show();
                                //  Toast.makeText(MainActivity.this, rurl, Toast.LENGTH_LONG).show();
                                // Log.d("nickname",nickname);
                                arrayList.add(contact);
                                ListAdapter adapter = new SimpleAdapter(
                                        Staff_Activity.this, arrayList,
                                        R.layout.list_item, new String[]{"namee","nursee","didd",
                                        "roomm","dept"}, new int[]{R.id.patient,
                                        R.id.doctor, R.id.nurse,R.id.room,R.id.dept});

                                lv.setAdapter(adapter);

                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley", "Error");
            }
        }
        );
        requestQueue.add(req);

    }

}

package com.smartdruglabel.smartdruglabel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class ShowDrugActivity extends Activity {
    @SuppressLint("NewApi")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.showdrug_screen);

        //Permission StrictMode
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        //Search Drug to Listview
        searchData();
    }

    public void getDrugID(String ID) {
        //btnSubmit
        final Button btnSubmit = (Button) findViewById(R.id.btnSubmit);

        final String drugID = ID;

        //Perform action on click
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Toast.makeText(ShowDrugActivity.this, "Checked " + drugID, Toast.LENGTH_SHORT).show();

                Intent write_screen = new Intent(getApplicationContext(), WriteTagActivity.class);
                write_screen.putExtra("drugID", drugID);
                startActivity(write_screen);
            }
        });
    }

    public void searchData() {
        //Listview1
        final ListView listView1 = (ListView) findViewById(R.id.lv_medname);
        listView1.setItemChecked(0, true);

        //Searchview
        final SearchView sv = (SearchView) findViewById(R.id.inputSearch);

        String url = "http://202.58.126.48:8081/smartdruglabel/getMedicine.php";
        /* String url = "http://www.kongtunmae-oncb.go.th/offer_hmf/smartdruglabel/getMedicine.php"; */
        Intent intent = getIntent();
        final String Username = intent.getStringExtra("Username");

        //Paste Parameters
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("updateUser", Username.toString()));

        try {
            JSONArray data = new JSONArray(getJSONUrl(url, params));

            final ArrayList<HashMap<String, String>> MyArrList = new ArrayList<HashMap<String, String>>();
            HashMap<String, String> map;

            for (int i = 0; i < data.length(); i++) {
                JSONObject c = data.getJSONObject(i);

                map = new HashMap<String, String>();
                map.put("drugName", c.getString("drugName"));
                map.put("drugExpired", c.getString("drugExpired"));
                map.put("drugID", c.getString("drugID"));
                MyArrList.add(map);
            }

            final SimpleAdapter sAdap;
            sAdap = new SimpleAdapter(ShowDrugActivity.this, MyArrList, android.R.layout.simple_list_item_single_choice,
                    new String[]{"drugName"}, new int[]{android.R.id.text1});

            listView1.setAdapter(sAdap);
            listView1.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

            final AlertDialog.Builder viewDetail = new AlertDialog.Builder(this);

            //Filter in Searchview
            sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String s) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String s) {
                    sAdap.getFilter().filter(s);
                    return false;
                }
            });

            // OnClick Item
            listView1.setOnItemClickListener(new OnItemClickListener() {
                public void onItemClick(AdapterView<?> myAdapter, View myView,
                                        int position, long mylng) {

                    String strMedId = MyArrList.get(position).get("drugID")
                            .toString();
                    String strMedName = MyArrList.get(position).get("drugName")
                            .toString();
                    String expiredDate = MyArrList.get(position).get("drugExpired")
                            .toString();

                    viewDetail.setIcon(android.R.drawable.btn_star_big_on);
                    viewDetail.setTitle("Drug Detail");
                    viewDetail.setMessage("Drug ID : " + strMedId + "\n"
                            + "Drug Name : " + strMedName + "\n"
                            + "Expired Date : " + expiredDate + "\n");
                    viewDetail.setPositiveButton("OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    // TODO Auto-generated method stub
                                    dialog.dismiss();
                                }
                            });
                    viewDetail.show();
                    getDrugID(strMedId);
                }
            });

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public String getJSONUrl(String url, List<NameValuePair> params) {
        StringBuilder str = new StringBuilder();
        HttpClient client = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url);

        try {
            httpPost.setEntity(new UrlEncodedFormEntity(params));
            HttpResponse response = client.execute(httpPost);
            StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();
            if (statusCode == 200) { // Download OK
                HttpEntity entity = response.getEntity();
                InputStream content = entity.getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(content));
                String line;
                while ((line = reader.readLine()) != null) {
                    str.append(line);
                }
            } else {
                Log.e("Log", "Failed to download file..");
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return str.toString();
    }
}

package volleyappsetup.com.volleyapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private TextView currency;
    private NetworkImageView networkImageView;
    private ImageView myImageview;



    private String imageUrl = "http://thetowne.net/wp-content/uploads/2016/01/Make-money-tumblr.jpg";
    private String stringUrl = "http://magadistudio.com/complete-android-developer-course-source-files/string.html";
    private String url = "http://jsonplaceholder.typicode.com/posts";
    private String currencyUrl ="https://openexchangerates.org/api/latest.json?app_id=e1e2950a33654caf83f39136321c5f53";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        currency = (TextView) findViewById(R.id.currencyText);
        networkImageView = (NetworkImageView) findViewById(R.id.imageview) ;
        myImageview = (ImageView) findViewById(R.id.myImageView);

        getJsonObject(currencyUrl);
        //getJsonArray(url);
        getStringObject(stringUrl);
        getImage(imageUrl);
        getImageOldWay(imageUrl);


    }
        private void getImageOldWay(String url) {

            ImageLoader imageLoader = AppController.getInstance().getImageLoader();

            imageLoader.get(url, new ImageLoader.ImageListener() {
                @Override
                public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {

                   myImageview.setImageBitmap(response.getBitmap());


                }
                @Override
                public void onErrorResponse(VolleyError error) {

                }





            });
        }

       private void getImage (String url){

           ImageLoader imageLoader = AppController.getInstance().getImageLoader();

           networkImageView.setImageUrl(url , imageLoader);


       }
       private void getStringObject(String url) {

           StringRequest stringRequest = new StringRequest(Request.Method.GET,
                   url, new Response.Listener<String>() {

               @Override
               public void onResponse(String response) {

                   Log.v("My response:", response.toString());

               }
           }, new Response.ErrorListener() {

               @Override
               public void onErrorResponse (VolleyError error) {

               }

           });

           AppController.getInstance().addToRequestQueue(stringRequest);
       }

        private void getJsonObject(String url ) {

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                    url, null, new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {



                    try {
                        JSONObject ratesObject = response.getJSONObject("rates");
                        String currencyText = ratesObject.getString("AED");

                        currency.setText("Ron:" + currencyText);

                        Log.v("CURRENCY" , currencyText);

                    }catch (JSONException e) {
                        e.printStackTrace();
                    }



                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                }

            });

            AppController.getInstance().addToRequestQueue(jsonObjectRequest);
        }
        private void getJsonArray(String url) {

        JsonArrayRequest request = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                for (int i = 0; i < response.length();  i++) {

                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        String title = jsonObject.getString("title");
                        String id  = jsonObject.getString("id");
                        String body = jsonObject.getString("body");

                        Log.v("Title is: ", title);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                //Log.v("Data from the web: " , response.toString());



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                VolleyLog.d("Mainactivity", error.getMessage());

            }
        });

        AppController.getInstance().addToRequestQueue(request);

    }

}
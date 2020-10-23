package mr.bel.projectforhttpre;
import androidx.appcompat.app.AppCompatActivity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import static mr.bel.projectforhttpre.utils.NetworkUtils.generalURL;
import static mr.bel.projectforhttpre.utils.NetworkUtils.getResponseFromURL;

public class MainActivity extends AppCompatActivity {

    private TextView viewVkInfo;
    private EditText InsertVkId;
    private Button Start;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewVkInfo = (TextView) findViewById(R.id.textView);
        InsertVkId = (EditText) findViewById(R.id.editTextTextPersonName);
        Start = (Button) findViewById(R.id.button);
        progressBar = (ProgressBar) findViewById(R.id.progbar);

        class VKQueryTask extends AsyncTask<URL,Void,String> {
            protected void onPreExecute(){
                progressBar.setVisibility(View.VISIBLE);

            }

            @Override
            protected String doInBackground(URL... urls) {
                String response = null;
                try {
                    response = getResponseFromURL(urls[0]);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return response;
            }
            protected void onPostExecute(String response){
                String FirstName = null;
                String LastName = null;

                try {
                    JSONObject jo = new JSONObject(response);
                    JSONArray ja = jo.getJSONArray("response");
                    JSONObject userInfo = ja.getJSONObject(0);
                    FirstName = userInfo.getString("first_name");
                    LastName = userInfo.getString("last_name");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                StringBuilder result = new StringBuilder("");
                result.append(FirstName).append(" ").append(LastName).append(" ");
                viewVkInfo.setText(result);
                progressBar.setVisibility(View.INVISIBLE);
            }
        }

        View.OnClickListener SuperStart = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                URL generateURL =generalURL(InsertVkId.getText().toString());
                new VKQueryTask().execute(generateURL);
            }
        };
        Start.setOnClickListener(SuperStart);
    }
}
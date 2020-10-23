package mr.bel.projectforhttpre;
import androidx.appcompat.app.AppCompatActivity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.io.IOException;
import java.net.URL;
import static mr.bel.projectforhttpre.utils.NetworkUtils.generalURL;
import static mr.bel.projectforhttpre.utils.NetworkUtils.getResponseFromURL;

public class MainActivity extends AppCompatActivity {

    private TextView viewVkInfo;
    private EditText InsertVkId;
    private Button Start;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewVkInfo = (TextView) findViewById(R.id.textView);
        InsertVkId = (EditText) findViewById(R.id.editTextTextPersonName);
        Start = (Button) findViewById(R.id.button);

        class VKQueryTask extends AsyncTask<URL,Void,String> {

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
                viewVkInfo.setText(response);
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
package android.singidunum.ac.recipesapp.API;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Api {
    public static void getJSON(String url, final ReadDataHandler rdh) {
        //kreiramo asinhroni task da ne bismo opterecivali aplikaciju cekanjem da se izvrsi ucitavanje sa api-ja i time je blokirali za bilo sta drugo
        AsyncTask<String, Void, String> task = new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... strings) {

                String answer = "";
                try {
                    URL url = new URL(strings[0]);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));

                    String line;
                    while((line = br.readLine()) != null) {
                        answer += line + "\n";
                    }
                    br.close();
                } catch (IOException e) {
                    answer = "[]";
                }

                return answer;
            }

            @Override
            protected void onPostExecute(String answer) {
                rdh.setJson(answer);
                rdh.sendEmptyMessage(0);
            }
        };

        task.execute(url);

    }
}

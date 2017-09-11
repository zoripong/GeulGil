package com.five.high.emirim.geulgil.Control;

import com.five.high.emirim.geulgil.Model.ApiItem;
import com.five.high.emirim.geulgil.Model.SameSounds;
import com.five.high.emirim.geulgil.Model.WordItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;


/**
 * Created by 유리 on 2017-08-17.
 */

public class ConnectApi {

    public String connectServer(String request){
        String queryUrl="http://52.78.168.169/request/";
        String json = null;
        try {
            URL url= new URL(queryUrl);
            HttpURLConnection httpURL = (HttpURLConnection)url.openConnection();
            httpURL.setRequestMethod("POST");
            httpURL.setDoInput(true);
            httpURL.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            OutputStream os = httpURL.getOutputStream();
            os.write(request.getBytes("utf-8") );
            os.flush();
            os.close();

            BufferedReader br = new BufferedReader( new InputStreamReader( httpURL.getInputStream(), "utf-8" ), httpURL.getContentLength() );
            json = br.toString();
            br.close();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }

        return json;
    }

    // TODO : 서버와 연결하여 json을 가지고 와서 파싱한 APIItem  리턴
    public ApiItem getRelativesResult(String request) {

        String json = connectServer(request);

        try {
            JSONObject obj = new JSONObject(json);
            String title = obj.getString("title");
            JSONArray relatives = obj.getJSONArray("relatives");

            HashSet<SameSounds> sameSoundsSet = new HashSet<SameSounds>();

            for (int i = 0; i < relatives.length(); i++) {

                String ids = relatives.getJSONObject(i).getString("id");
                JSONArray sameSounds = relatives.getJSONObject(i).getJSONArray("samesound");
                ArrayList<WordItem> wordItems = new ArrayList<WordItem>();

                for (int j = 0; j < sameSounds.length(); j++) {
                    int id = sameSounds.getJSONObject(j).getInt("id");
                    String word = sameSounds.getJSONObject(j).getString("word");
                    String mean = sameSounds.getJSONObject(j).getString("mean");
                    String part = sameSounds.getJSONObject(j).getString("part");
                    JSONArray meankeyword = sameSounds.getJSONObject(j).getJSONArray("meankeyword");
                    String m[] = new String[meankeyword.length()];
                    for (int k = 0; k < meankeyword.length(); k++)
                        m[k] = meankeyword.getString(k);
                    JSONArray similarkeyword = sameSounds.getJSONObject(i).getJSONArray("similarkeyword");
                    String s[] = new String[similarkeyword.length()];
                    for (int k = 0; k < similarkeyword.length(); k++)
                        s[k] = similarkeyword.getString(k);
                    int recommend = Integer.parseInt(sameSounds.getJSONObject(i).getString("recommend"));

                    wordItems.add(new WordItem(id, word, mean, part, m, s, recommend));
                }

                sameSoundsSet.add(new SameSounds(ids, wordItems));

            }

            return new ApiItem(title, sameSoundsSet);
        }catch(JSONException e){
            return null;
        }
    }
}

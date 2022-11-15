package Screenscapper;


import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ScapperTest {
    @Test
    public void CheckingUrl() throws JSONException {
//    amazon sometimes randomly redirects you to a different home page with a different url

    }
    @Test
    public void ConvertingToJsonAndPost() throws JSONException {
        String heading = "heading", description = "Description", url = "url", Imageurl = "image url", centInString = "4500";

        JSONObject post = new JSONObject();
        post.put("alertType", 6);
        post.put("heading", heading);
        post.put("description", description);
        post.put("url", url);
        post.put("imageurl", Imageurl);
        post.put("postedBy", "157831f5-179f-4640-9634-98d2801da987");
        post.put("priceInCents", centInString);

        String json = post.toString();

        String query_url = "https://api.marketalertum.com/Alert";


        try {
            URL Apiurl = new URL(query_url);
            HttpURLConnection conn = (HttpURLConnection) Apiurl.openConnection();
            conn.setConnectTimeout(5000);
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestMethod("POST");
            OutputStream os = conn.getOutputStream();
            os.write(json.getBytes("UTF-8"));
            os.close();
            // read the response
            InputStream in = new BufferedInputStream(conn.getInputStream());
            String result = IOUtils.toString(in, "UTF-8");
            System.out.println(result);
            Assertions.assertEquals(result.isEmpty(), false);

            System.out.println("result after Reading JSON Response");
            JSONObject myResponse = new JSONObject(result);
            in.close();
            conn.disconnect();
        } catch (Exception e) {
            System.out.println(e);
        }

    }

}

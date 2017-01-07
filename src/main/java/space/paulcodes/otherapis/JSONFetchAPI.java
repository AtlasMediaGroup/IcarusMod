package space.paulcodes.otherapis;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class JSONFetchAPI
{
    public static synchronized String getStringFromURL(String URL)
    {
        try
        {
            java.net.URL location = new URL(URL);
            URLConnection connection = location.openConnection();
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");
            connection.setDoInput(true);
            InputStream stream = connection.getInputStream();
            return new BufferedReader(new InputStreamReader(stream)).readLine();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            System.out.println("API: " + "Failed to get String from URL (" + URL + ")");
            return null;
        }
    }
}

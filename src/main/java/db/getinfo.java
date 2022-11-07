package db;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class getinfo {
	OkHttpClient client = new OkHttpClient();

    String run(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();
        return response.body().string();
    }
    public String getcount() {
        String url = "http://openapi.seoul.go.kr:8088/504d63755a61686f36306c746b5369/json/TbPublicWifiInfo/1/1/";
        String responseString;
        String tcount = "";
		try {
			responseString = new getinfo().run(url);
	        
	        JsonParser parser = new JsonParser();
	        JsonElement element = parser.parse(responseString);
	        JsonObject jsonObject = element.getAsJsonObject();
	        
	        String name = jsonObject.get("TbPublicWifiInfo").toString();
	        JsonElement element2 = parser.parse(name);
	        JsonObject jsonObject2 = element2.getAsJsonObject();
	        tcount = jsonObject2.get("list_total_count").toString();
	        
	        
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return tcount;
        
    }

}

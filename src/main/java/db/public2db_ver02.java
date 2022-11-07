//sqlite 의 wifi_info sqlite 파일의 infoVer4 테이블에 전체 와이파이 정보를 저장.. 
package db;

import java.sql.*;
import java.util.*;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import com.google.gson.*;

import java.io.IOException;



public class public2db_ver02 {
    OkHttpClient client = new OkHttpClient();

    String run(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();
        return response.body().string();
    }
    
    private static double deg2rad(double deg){
        return (deg * Math.PI/180.0);
    }

    private static double rad2deg(double rad){
        return (rad * 180 / Math.PI);
    }
    
    
private static double distance(double lat1, double lon1, double lat2, double lon2, String unit) {
		
		double theta = lon1 - lon2;
		double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
		
		dist = Math.acos(dist);
		dist = rad2deg(dist);
		dist = dist * 60 * 1.1515;
		
		if (unit.equals("kilometer")) {
			dist = dist * 1.609344;
		} else if(unit.equals("meter")){
			dist = dist * 1609.344;
		} 

		return ((double)Math.round(dist*10000)/10000);
	}


    public void dbInit(double lat1, double lon1) {
    	
    	String url = "http://openapi.seoul.go.kr:8088/504d63755a61686f36306c746b5369/json/TbPublicWifiInfo/11948/11948/";
        String responseString;
		try {
			responseString = new public2db_ver02().run(url);
			JsonParser parser = new JsonParser();
	        JsonElement element = parser.parse(responseString);
	        JsonObject jsonObject = element.getAsJsonObject();
	        
	        String name = jsonObject.get("TbPublicWifiInfo").toString();
	        JsonElement element2 = parser.parse(name);
	        JsonObject jsonObject2 = element2.getAsJsonObject();
	        String take1 = jsonObject2.get("list_total_count").toString();
	        
	        int totalCount = Integer.parseInt(take1);
	        
	        for (int i = 1; i <= totalCount; i+=500) {
	        	if (i + 500 >= totalCount) {
	        		String target = "http://openapi.seoul.go.kr:8088/504d63755a61686f36306c746b5369/json/TbPublicWifiInfo/"
	            			+ String.valueOf(i) + "/" + String.valueOf(totalCount) + "/";
	        		String response = new public2db_ver02().run(target);
	        		System.out.println(response);
	            	  JsonParser parser2 = new JsonParser();
	                JsonElement element3 = parser.parse(response);
	                JsonObject jsonObject3 = element3.getAsJsonObject();
	                String name2 = jsonObject3.get("TbPublicWifiInfo").toString();
	                JsonElement ele = parser.parse(name2);
	                JsonObject jsonObject4 = ele.getAsJsonObject();
	                String row = jsonObject4.get("row").toString();
	                System.out.println(row);
	                Gson gson = new Gson();
	                System.out.println(row);
	                WifiInfo[] arr = gson.fromJson(row, WifiInfo[].class);
	                List<WifiInfo> list = Arrays.asList(arr);
	                
	                Connection con = null;
	        		PreparedStatement preparedStatement = null;
	        		
	        		try {
	        			Class.forName("org.sqlite.JDBC");
	        			String dbFile = "C:\\Users\\ahojp\\Desktop\\eclipseSpace\\wifi_info.sqlite";
	        			con = DriverManager.getConnection("jdbc:sqlite:" + dbFile);
	        			
	        			con.setAutoCommit(false);
	        			for (int j = 0; j < list.size(); j++) {
	        				String sql = "insert into infoVer4 (ds, mn, gu, name, addr, detail, floor, tpe, city,"
	            					+ " servi, liners, yeara, insidea, enva, lata, lnta, datea) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?"
	            					+ ", ?, ?, ?, ?);";
	        				
	        				double distanceKiloMeter = 
	        						distance(lat1,lon1, list.get(j).getLAT(), list.get(j).getLNT(), "kilometer");
	        				
	        				
	            			preparedStatement = con.prepareStatement(sql);
	            			
	            			preparedStatement.setDouble(1,  distanceKiloMeter);
	            			preparedStatement.setString(2,  list.get(j).getX_SWIFI_MGR_NO());
	            			preparedStatement.setString(3,  list.get(j).getX_SWIFI_WRDOFC());
	            			preparedStatement.setString(4,  list.get(j).getX_SWIFI_MAIN_NM());
	            			preparedStatement.setString(5,  list.get(j).getX_SWIFI_ADRES1());
	            			preparedStatement.setString(6,  list.get(j).getX_SWIFI_ADRES2());
	            			preparedStatement.setString(7,  list.get(j).getX_SWIFI_INSTL_FLOOR());
	            			preparedStatement.setString(8,  list.get(j).getX_SWIFI_INSTL_TY());
	            			preparedStatement.setString(9,  list.get(j).getX_SWIFI_INSTL_MBY());
	            			preparedStatement.setString(10,  list.get(j).getX_SWIFI_SVC_SE());
	            			preparedStatement.setString(11,  list.get(j).getX_SWIFI_CMCWR());
	            			preparedStatement.setString(12,  list.get(j).getX_SWIFI_CNSTC_YEAR());
	            			preparedStatement.setString(13,  list.get(j).getX_SWIFI_INOUT_DOOR());
	            			preparedStatement.setString(14,  list.get(j).getX_SWIFI_REMARS3());
	            			preparedStatement.setDouble(15,  list.get(j).getLAT());
	            			preparedStatement.setDouble(16,  list.get(j).getLNT());
	            			preparedStatement.setString(17,  list.get(j).getWORK_DTTM());
	            			
	            			int affected = preparedStatement.executeUpdate();
	            			
	            			if (affected > 0) {
	            				System.out.println(" 저장 성공 ");
	            			} else {
	            				System.out.println(" 저장 실패 ");
	            			}
	        			}
	        			con.commit();
	        			
	        			
	        		}catch(Exception e) {
	        			e.printStackTrace();
	        		}finally {
	        			try {
	        				if (con != null && !con.isClosed()) {
	        					con.close();
	        				}
	        			} catch (SQLException e1) {
	        				// TODO Auto-generated catch block
	        				e1.printStackTrace();
	        			}
	        			
	        			
	        			try {
	        				if (preparedStatement != null && !preparedStatement.isClosed()) {
	        					preparedStatement.close();
	        				}
	        			} catch (SQLException e) {
	        				// TODO Auto-generated catch block
	        				e.printStackTrace();
	        			}
	        		}

	                
	                System.out.print(i);
	                break;
	        	}
	        	String target = "http://openapi.seoul.go.kr:8088/504d63755a61686f36306c746b5369/json/TbPublicWifiInfo/"
	        			+ String.valueOf(i) + "/" + String.valueOf(i + 499) + "/";
	        	String response = new public2db_ver02().run(target);
	        	JsonParser parser2 = new JsonParser();
	            JsonElement element3 = parser.parse(response);
	            JsonObject jsonObject3 = element3.getAsJsonObject();
	            String name2 = jsonObject3.get("TbPublicWifiInfo").toString();
	            JsonElement ele = parser.parse(name2);
	            JsonObject jsonObject4 = ele.getAsJsonObject();
	            String row = jsonObject4.get("row").toString();
	            Gson gson = new Gson();
	            WifiInfo[] arr = gson.fromJson(row, WifiInfo[].class);
	            List<WifiInfo> list = Arrays.asList(arr);//리스트에 와이파이 인포 클래스를 저장. for 문 돌며 sql 문 실행...
	            
	            
	            	//infoSqlite ct = new infoSqlite();
	            	//ct.dbInsert(list.get(j));
	            	Connection con = null;
	        		PreparedStatement preparedStatement = null;
	        		
	        		try {
	        			Class.forName("org.sqlite.JDBC");
	        			String dbFile = "C:\\Users\\ahojp\\Desktop\\eclipseSpace\\wifi_info.sqlite";
	        			con = DriverManager.getConnection("jdbc:sqlite:" + dbFile);
	        			con.setAutoCommit(false);
	        			for (int j = 0; j < list.size(); j++) {
	        				String sql = "insert into infoVer4 (ds, mn, gu, name, addr, detail, floor, tpe, city,"
	            					+ " servi, liners, yeara, insidea, enva, lata, lnta, datea) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?"
	            					+ ", ?, ?, ?, ?);";
	        				double distanceKiloMeter = 
	        						distance(lat1,lon1, list.get(j).getLAT(), list.get(j).getLNT(), "kilometer");
	        		        
	            			preparedStatement = con.prepareStatement(sql);
	            			preparedStatement.setDouble(1,  distanceKiloMeter);
	            			preparedStatement.setString(2,  list.get(j).getX_SWIFI_MGR_NO());
	            			preparedStatement.setString(3,  list.get(j).getX_SWIFI_WRDOFC());
	            			preparedStatement.setString(4,  list.get(j).getX_SWIFI_MAIN_NM());
	            			preparedStatement.setString(5,  list.get(j).getX_SWIFI_ADRES1());
	            			preparedStatement.setString(6,  list.get(j).getX_SWIFI_ADRES2());
	            			preparedStatement.setString(7,  list.get(j).getX_SWIFI_INSTL_FLOOR());
	            			preparedStatement.setString(8,  list.get(j).getX_SWIFI_INSTL_TY());
	            			preparedStatement.setString(9,  list.get(j).getX_SWIFI_INSTL_MBY());
	            			preparedStatement.setString(10,  list.get(j).getX_SWIFI_SVC_SE());
	            			preparedStatement.setString(11,  list.get(j).getX_SWIFI_CMCWR());
	            			preparedStatement.setString(12,  list.get(j).getX_SWIFI_CNSTC_YEAR());
	            			preparedStatement.setString(13,  list.get(j).getX_SWIFI_INOUT_DOOR());
	            			preparedStatement.setString(14,  list.get(j).getX_SWIFI_REMARS3());
	            			preparedStatement.setDouble(15,  list.get(j).getLAT());
	            			preparedStatement.setDouble(16,  list.get(j).getLNT());
	            			preparedStatement.setString(17,  list.get(j).getWORK_DTTM());
	            			
	            			int affected = preparedStatement.executeUpdate();
	            			
	            			if (affected > 0) {
	            				System.out.println(" 저장 성공 ");
	            			} else {
	            				System.out.println(" 저장 실패 ");
	            			}
	        			}
	        			con.commit();
	        			
	        			
	        		}catch(Exception e) {
	        			e.printStackTrace();
	        		}finally {
	        			try {
	        				if (con != null && !con.isClosed()) {
	        					con.close();
	        				}
	        			} catch (SQLException e1) {
	        				// TODO Auto-generated catch block
	        				e1.printStackTrace();
	        			}
	        			
	        			
	        			try {
	        				if (preparedStatement != null && !preparedStatement.isClosed()) {
	        					preparedStatement.close();
	        				}
	        			} catch (SQLException e) {
	        				// TODO Auto-generated catch block
	        				e.printStackTrace();
	        			}
	        		}
	            
	            
	            System.out.println(i);
	            
	        }
	        
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
        
    }




    public void dbFlush() {
		Connection con = null;
		PreparedStatement preparedStatement = null;

		try {
			Class.forName("org.sqlite.JDBC");
			String dbFile = "C:\\Users\\ahojp\\Desktop\\eclipseSpace\\wifi_info.sqlite";
			con = DriverManager.getConnection("jdbc:sqlite:" + dbFile);
			
			
			String sql = "delete from infoVer4;";
			
			preparedStatement = con.prepareStatement(sql);
			//preparedStatement.setString(1,  nameA);
			//preparedStatement.setLong(1,  num1);
			
			int affected = preparedStatement.executeUpdate();
			
			if (affected > 0) {
				System.out.println(" 삭제 성공 ");
			} else {
				System.out.println(" 삭제 실패 ");
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if (con != null && !con.isClosed()) {
					con.close();
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			
			try {
				if (preparedStatement != null && !preparedStatement.isClosed()) {
					preparedStatement.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
    }
    
    
    public void dbSelect() {
    	Connection con = null;
		PreparedStatement preparedStatement = null;

		ResultSet rs = null;
		
		//int num1 = 2;
		try {
			Class.forName("org.sqlite.JDBC");
			String dbFile = "C:\\Users\\ahojp\\Desktop\\eclipseSpace\\wifi_info.sqlite";
			con = DriverManager.getConnection("jdbc:sqlite:" + dbFile);
			
			
			String sql = "SELECT * FROM infoVer4 order by ds limit 20";
			
			preparedStatement = con.prepareStatement(sql);
			//preparedStatement.setLong(1,  num1);
			rs = preparedStatement.executeQuery();
			while(rs.next()) {
				Double dis = rs.getDouble("ds");
				String mn = rs.getString("mn");
				String gu = rs.getString("gu");
				String name = rs.getString("name");
				
				
				System.out.print(dis + " " + mn + " " + gu + " " + name);
				System.out.println();
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if (con != null && !con.isClosed()) {
					con.close();
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			try {
				if(rs != null && !rs.isClosed()) {
					rs.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try {
				if (preparedStatement != null && !preparedStatement.isClosed()) {
					preparedStatement.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
    }

}
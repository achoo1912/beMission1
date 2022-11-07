package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class public2web_ver01 {
	public List<String> list() {
		List<String> strings = new ArrayList<>();
		
    	Connection con = null;
		PreparedStatement preparedStatement = null;

		ResultSet rs = null;
		
		
		try {
			Class.forName("org.sqlite.JDBC");
			String dbFile = "C:\\Users\\ahojp\\Desktop\\eclipseSpace\\wifi_info.sqlite";
			con = DriverManager.getConnection("jdbc:sqlite:" + dbFile);
			
			
			String sql = "SELECT * FROM infoVer4 order by ds limit 20";
			
			preparedStatement = con.prepareStatement(sql);
			//preparedStatement.setLong(1,  num1);
			rs = preparedStatement.executeQuery();
			while(rs.next()) {
				String dis = String.valueOf(rs.getDouble("ds"));
				String mn = rs.getString("mn");
				String gu = rs.getString("gu");
				String name = rs.getString("name");
				String addr = rs.getString("addr");
				String detail = rs.getString("detail");
				String floor = rs.getString("floor");
				String tpe = rs.getString("tpe");
				String city = rs.getString("city");
				String servi = rs.getString("servi");
				String liners = rs.getString("liners");
				String yeara = rs.getString("yeara");
				String insidea = rs.getString("insidea");
				String enva = rs.getString("enva");
				String lata = String.valueOf(rs.getDouble("lata"));
				String lnta = String.valueOf(rs.getDouble("lnta"));
				String datea = rs.getString("datea");
				
				strings.add(dis);
				strings.add(mn);
				strings.add(gu);
				strings.add(name);
				strings.add(addr);
				strings.add(detail);
				strings.add(floor);
				strings.add(tpe);
				strings.add(city);
				strings.add(servi);
				strings.add(liners);
				strings.add(yeara);
				strings.add(insidea);
				strings.add(enva);
				strings.add(lata);
				strings.add(lnta);
				strings.add(datea);
				
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
		return strings;
    }
}

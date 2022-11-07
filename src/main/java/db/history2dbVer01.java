package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class history2dbVer01 {
	public List<String> dbSelect() {
    	Connection con = null;
		PreparedStatement preparedStatement = null;
		List<String> historyList = new ArrayList<>();
		ResultSet rs = null;
		
		//int num1 = 2;
		try {
			Class.forName("org.sqlite.JDBC");
			String dbFile = "C:\\Users\\ahojp\\Desktop\\eclipseSpace\\wifi_info.sqlite";
			con = DriverManager.getConnection("jdbc:sqlite:" + dbFile);
			
			
			String sql = "SELECT row_number() over (order by hi.wheen) as row_ind, "
					+ " hi.* FROM history as hi order by row_ind desc;";
			
			preparedStatement = con.prepareStatement(sql);
			//preparedStatement.setLong(1,  num1);
			rs = preparedStatement.executeQuery();
			while(rs.next()) {
				String row_ind = String.valueOf(rs.getInt("row_ind"));
				String laat = rs.getString("laat");
				String lnnt = rs.getString("lnnt");
				String wheen = rs.getString("wheen");
				historyList.add(row_ind);
				historyList.add(laat);
				historyList.add(lnnt);
				historyList.add(wheen);
				
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
		return historyList;
    }

	public void dbInsert(History hi) { 
		Connection con = null;
		PreparedStatement preparedStatement = null;
		
		
		try {
			Class.forName("org.sqlite.JDBC");
			String dbFile = "C:\\Users\\ahojp\\Desktop\\eclipseSpace\\wifi_info.sqlite";
			con = DriverManager.getConnection("jdbc:sqlite:" + dbFile);
			
			
			String sql = "insert into history (laat, lnnt, wheen) values (?, ?, ?);";
			
			preparedStatement = con.prepareStatement(sql);
			preparedStatement.setString(1,  hi.getLaat());
			preparedStatement.setString(2,  hi.getLnnt());
			preparedStatement.setString(3,  hi.getWheen());
			
			int affected = preparedStatement.executeUpdate();
			
			if (affected > 0) {
				System.out.println(" 저장 성공 ");
			} else {
				System.out.println(" 저장 실패 ");
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

	public void dbDel(String dd) {
		Connection con = null;
		PreparedStatement preparedStatement = null;
		
		try {
			Class.forName("org.sqlite.JDBC");
			String dbFile = "C:\\Users\\ahojp\\Desktop\\eclipseSpace\\wifi_info.sqlite";
			con = DriverManager.getConnection("jdbc:sqlite:" + dbFile);
			
			
			String sql = "delete from history where wheen = ?;";
			
			preparedStatement = con.prepareStatement(sql);
			preparedStatement.setString(1,  dd);
			
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
}

package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
	public class infoSqlite {
		public void dbSelect() {
			Connection con = null;
			PreparedStatement preparedStatement = null;

			ResultSet rs = null;
			
			int num1 = 2;
			try {
				Class.forName("org.sqlite.JDBC");
				String dbFile = "C:\\Users\\ahojp\\Desktop\\eclipseSpace\\test1.sqlite";
				con = DriverManager.getConnection("jdbc:sqlite:" + dbFile);
				
				
				String sql = "SELECT * FROM test1 where num = ? ";
				
				preparedStatement = con.prepareStatement(sql);
				preparedStatement.setLong(1,  num1);
				rs = preparedStatement.executeQuery();
				while(rs.next()) {
					String id = rs.getString("name");
					
					
					System.out.println(id);
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
		
		public void dbInsert(WifiInfo ct) {
			Connection con = null;
			PreparedStatement preparedStatement = null;
			//Statement stat = null;
			//ResultSet rs = null;
			
			
			try {
				Class.forName("org.sqlite.JDBC");
				String dbFile = "C:\\Users\\ahojp\\Desktop\\eclipseSpace\\wifi_info.sqlite";
				con = DriverManager.getConnection("jdbc:sqlite:" + dbFile);
				
				con.setAutoCommit(false);
				String sql = "insert into infoVer4 (mn, gu, name, addr, detail, floor, tpe, city,"
						+ " servi, liners, yeara, insidea, enva, lata, lnta, datea) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?"
						+ ", ?, ?, ?, ?);";
				
				preparedStatement = con.prepareStatement(sql);
				preparedStatement.setString(1,  ct.getX_SWIFI_MGR_NO());
				preparedStatement.setString(2,  ct.getX_SWIFI_WRDOFC());
				preparedStatement.setString(3,  ct.getX_SWIFI_MAIN_NM());
				preparedStatement.setString(4,  ct.getX_SWIFI_ADRES1());
				preparedStatement.setString(5,  ct.getX_SWIFI_ADRES2());
				preparedStatement.setString(6,  ct.getX_SWIFI_INSTL_FLOOR());
				preparedStatement.setString(7,  ct.getX_SWIFI_INSTL_TY());
				preparedStatement.setString(8,  ct.getX_SWIFI_INSTL_MBY());
				preparedStatement.setString(9,  ct.getX_SWIFI_SVC_SE());
				preparedStatement.setString(10,  ct.getX_SWIFI_CMCWR());
				preparedStatement.setString(11,  ct.getX_SWIFI_CNSTC_YEAR());
				preparedStatement.setString(12,  ct.getX_SWIFI_INOUT_DOOR());
				preparedStatement.setString(13,  ct.getX_SWIFI_REMARS3());
				preparedStatement.setDouble(14,  ct.getLAT());
				preparedStatement.setDouble(15,  ct.getLNT());
				preparedStatement.setString(16,  ct.getWORK_DTTM());
				
				
				int affected = preparedStatement.executeUpdate();
				
				if (affected > 0) {
					System.out.println(" 저장 성공 ");
				} else {
					System.out.println(" 저장 실패 ");
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
		}


		
		public void dbUpdate() {
			Connection con = null;
			PreparedStatement preparedStatement = null;
			//Statement stat = null;
			//ResultSet rs = null;
			
			int num1 = 3;
			String nameS = "brown";
			String nameA = "charlie";
			try {
				Class.forName("org.sqlite.JDBC");
				String dbFile = "C:\\Users\\ahojp\\Desktop\\eclipseSpace\\test1.sqlite";
				con = DriverManager.getConnection("jdbc:sqlite:" + dbFile);
				
				
				String sql = "update test1 set name = ? where num = ?;";
				
				preparedStatement = con.prepareStatement(sql);
				preparedStatement.setString(1,  nameA);
				preparedStatement.setLong(2,  num1);
				
				int affected = preparedStatement.executeUpdate();
				
				if (affected > 0) {
					System.out.println(" 수정 성공 ");
				} else {
					System.out.println(" 수정 실패 ");
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
		
		
		
		public void dbDelete() {
			Connection con = null;
			PreparedStatement preparedStatement = null;
			//Statement stat = null;
			//ResultSet rs = null;
			
			int num1 = 3;
//			String nameS = "brown";
//			String nameA = "charlie";
			try {
				Class.forName("org.sqlite.JDBC");
				String dbFile = "C:\\Users\\ahojp\\Desktop\\eclipseSpace\\test1.sqlite";
				con = DriverManager.getConnection("jdbc:sqlite:" + dbFile);
				
				
				String sql = "delete from test1 where num = ?;";
				
				preparedStatement = con.prepareStatement(sql);
				//preparedStatement.setString(1,  nameA);
				preparedStatement.setLong(1,  num1);
				
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

package com.order.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;


public class Store_OrderDAO implements Store_OrderDAO_interface{
	
	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/BA101G1");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	private static final String INSERT_STMT = 
			"INSERT INTO order (order_id, order_time, mem_id, store_id, order_state, totalprice, order_way, receive_address, qrcode, order_note, order_taketime) "
			         + "VALUES (to_char(sysdate,'YYYYmmdd')||'-'||LPAD(to_char(order_seq.NEXTVAL),6,'0'),?,?,?,?,?,?,?,?,?,?)";
	private static final String UPDATE = 
			"UPDATE order set order_id=?, order_time=?, mem_id=?, store_id=?, order_state=?, totalprice=?, order_way=?, receive_address=?, qrcode=?, order_note=?, order_taketime=?";
	private static final String DELETE = 
			"DELETE FROM order where order_id = ?";
	private static final String GET_ONE_STMT = 
			"SELECT order_id, order_time, mem_id, store_id, order_state, totalprice, order_way, receive_address, qrcode, order_note, order_taketime from order where order_id = ?";
	private static final String GET_ALL_STMT = 
			"SELECT order_id, order_time, mem_id, store_id, order_state, totalprice, order_way, receive_address, qrcode, order_note, order_taketime from store_order order order by order_time desc";
	private static final String GET_ALL_BY_MEMID = "select o.mem_id, o.order_id, o.store_id, o.totalprice, o.order_time, o.order_way, o.order_state ,s.store_name from store_order o join store s on o.store_id = s.store_id where mem_id = ? order by order_time desc";
	private static final String GET_ALL_BY_STATE_MEMEID = "select o.mem_id, o.order_id, o.store_id, o.totalprice, o.order_time, o.order_way, o.order_state ,s.store_name from store_order o join store s on o.store_id = s.store_id where mem_id = ? and order_state =? order by order_time desc";
	private static final String GET_ALL_BY_COMSTATE_MEMEID = "select o.mem_id, o.order_id, o.store_id, o.totalprice, o.order_time, o.order_way, o.order_state ,s.store_name from store_order o join store s on o.store_id = s.store_id where mem_id = ? and order_state =? order by order_time desc";
	@Override
	public void insert(Store_OrderVO orderVO) {
		// TODO Auto-generated method stub
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setTimestamp(1, orderVO.getOrder_time());
			pstmt.setString(2, orderVO.getMem_id());
			pstmt.setString(3, orderVO.getStore_id());
			pstmt.setInt(4, orderVO.getOrder_state());
			pstmt.setInt(5, orderVO.getTotalprice());
			pstmt.setInt(6, orderVO.getOrder_way());
			pstmt.setString(7, orderVO.getReceive_address());
			pstmt.setBytes(8, orderVO.getQrcode());
			pstmt.setString(9, orderVO.getOrder_note());
			pstmt.setTimestamp(10, orderVO.getOrder_taketime());
			
			pstmt.executeUpdate();

			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
	}

	@Override
	public void update(Store_OrderVO orderVO) {
		// TODO Auto-generated method stub
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);
			
			pstmt.setString(1, orderVO.getOrder_id());
			pstmt.setTimestamp(2, orderVO.getOrder_time());
			pstmt.setString(3, orderVO.getMem_id());
			pstmt.setString(4, orderVO.getStore_id());
			pstmt.setInt(5, orderVO.getOrder_state());
			pstmt.setInt(6, orderVO.getTotalprice());
			pstmt.setInt(7, orderVO.getOrder_way());
			pstmt.setString(8, orderVO.getReceive_address());
			pstmt.setBytes(9, orderVO.getQrcode());
			pstmt.setString(10, orderVO.getOrder_note());
			pstmt.setTimestamp(11, orderVO.getOrder_taketime());
			

			pstmt.executeUpdate();

			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
	}

	@Override
	public void delete(String order_id) {
		// TODO Auto-generated method stub
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, order_id);

			pstmt.executeUpdate();

			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
	}

	@Override
	public Store_OrderVO findByPrimaryKey(String order_id) {
		// TODO Auto-generated method stub
		Store_OrderVO orderVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, order_id);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVo �]�٬� Domain objects
				orderVO = new Store_OrderVO();
				orderVO.setOrder_id(rs.getString("order_id"));
				orderVO.setOrder_time(rs.getTimestamp("rec_mon"));
				orderVO.setMem_id(rs.getString("mem_id"));
				orderVO.setStore_id(rs.getString("store_id"));
				orderVO.setOrder_state(rs.getInt("order_state"));
				orderVO.setTotalprice(rs.getInt("totalprice"));
				orderVO.setOrder_way(rs.getInt("order_way"));
				orderVO.setReceive_address(rs.getString("receive_address"));
				orderVO.setQrcode(rs.getBytes("qrcode"));
				orderVO.setOrder_note(rs.getString("order_note"));
				orderVO.setOrder_taketime(rs.getTimestamp("order_taketime"));
			}

			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		
		return orderVO;
	}

//	@Override
//	public List<Store_OrderVO> getAll() {
//		// TODO Auto-generated method stub
//		List<Store_OrderVO> list = new ArrayList<Store_OrderVO>();
//		Store_OrderVO orderVO = null;
//
//		Connection con = null;
//		PreparedStatement pstmt = null;
//		ResultSet rs = null;
//		
//		try {
//
//			
//			con = ds.getConnection();
//			pstmt = con.prepareStatement(GET_ALL_STMT);
//			rs = pstmt.executeQuery();
//
//			while (rs.next()) {
//				// empVO �]�٬� Domain objects
//				orderVO = new Store_OrderVO();
//				orderVO.setOrder_id(rs.getString("order_id"));
//				orderVO.setOrder_time(rs.getTimestamp("order_time"));
//				orderVO.setMem_id(rs.getString("mem_id"));
//				orderVO.setStore_id(rs.getString("store_id"));
//				orderVO.setOrder_state(rs.getInt("order_state"));
//				orderVO.setTotalprice(rs.getInt("totalprice"));
//				orderVO.setOrder_way(rs.getInt("order_way"));
//				orderVO.setReceive_address(rs.getString("receive_address"));
//				orderVO.setQrcode(rs.getBytes("qrcode"));
//				orderVO.setOrder_note(rs.getString("order_note"));
//				orderVO.setOrder_taketime(rs.getTimestamp("order_taketime"));
//				list.add(orderVO); // Store the row in the list
//			}
//
//			// Handle any driver errors
//		}  catch (SQLException se) {
//			throw new RuntimeException("A database error occured. "
//					+ se.getMessage());
//			// Clean up JDBC resources
//		} finally {
//			if (rs != null) {
//				try {
//					rs.close();
//				} catch (SQLException se) {
//					se.printStackTrace(System.err);
//				}
//			}
//			if (pstmt != null) {
//				try {
//					pstmt.close();
//				} catch (SQLException se) {
//					se.printStackTrace(System.err);
//				}
//			}
//			if (con != null) {
//				try {
//					con.close();
//				} catch (Exception e) {
//					e.printStackTrace(System.err);
//				}
//			}
//		}
//		
//		return list;
//	}
	
	@Override
	public List<Store_OrderVO> getAll() {
		// TODO Auto-generated method stub
		List<Store_OrderVO> list = new ArrayList<Store_OrderVO>();
		Store_OrderVO orderVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {

			
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_BY_MEMID);
			pstmt.setString(1, "MEM-000001");
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVO �]�٬� Domain objects
				orderVO = new Store_OrderVO();
				orderVO.setOrder_id(rs.getString("order_id"));
				orderVO.setOrder_time(rs.getTimestamp("order_time"));
				orderVO.setMem_id(rs.getString("mem_id"));
				orderVO.setStore_id(rs.getString("store_id"));
				orderVO.setOrder_state(rs.getInt("order_state"));
				orderVO.setTotalprice(rs.getInt("totalprice"));
				orderVO.setOrder_way(rs.getInt("order_way"));
				orderVO.setStore_name(rs.getString("store_name"));
				list.add(orderVO); // Store the row in the list
			}

			// Handle any driver errors
		}  catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		
		return list;
	}
	
	public List<Store_OrderVO> getByState() {
		// TODO Auto-generated method stub
		List<Store_OrderVO> list = new ArrayList<Store_OrderVO>();
		Store_OrderVO orderVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {

			
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_BY_STATE_MEMEID);
			pstmt.setString(1, "MEM-000001");
			pstmt.setString(2, "3");
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVO �]�٬� Domain objects
				orderVO = new Store_OrderVO();
				orderVO.setOrder_id(rs.getString("order_id"));
				orderVO.setOrder_time(rs.getTimestamp("order_time"));
				orderVO.setMem_id(rs.getString("mem_id"));
				orderVO.setStore_id(rs.getString("store_id"));
				orderVO.setOrder_state(rs.getInt("order_state"));
				orderVO.setTotalprice(rs.getInt("totalprice"));
				orderVO.setOrder_way(rs.getInt("order_way"));
				orderVO.setStore_name(rs.getString("store_name"));
				list.add(orderVO); // Store the row in the list
			}

			// Handle any driver errors
		}  catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		
		return list;
	}
	
	public List<Store_OrderVO> getBycomState() {
		// TODO Auto-generated method stub
		List<Store_OrderVO> list = new ArrayList<Store_OrderVO>();
		Store_OrderVO orderVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {

			
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_BY_COMSTATE_MEMEID);
			pstmt.setString(1, "MEM-000001");
			pstmt.setString(2, "4");
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVO �]�٬� Domain objects
				orderVO = new Store_OrderVO();
				orderVO.setOrder_id(rs.getString("order_id"));
				orderVO.setOrder_time(rs.getTimestamp("order_time"));
				orderVO.setMem_id(rs.getString("mem_id"));
				orderVO.setStore_id(rs.getString("store_id"));
				orderVO.setOrder_state(rs.getInt("order_state"));
				orderVO.setTotalprice(rs.getInt("totalprice"));
				orderVO.setOrder_way(rs.getInt("order_way"));
				orderVO.setStore_name(rs.getString("store_name"));
				list.add(orderVO); // Store the row in the list
			}

			// Handle any driver errors
		}  catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		
		return list;
	}
}
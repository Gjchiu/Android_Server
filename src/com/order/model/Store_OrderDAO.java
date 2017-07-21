package com.order.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.orderlist.model.OrderlistDAO;
import com.orderlist.model.OrderlistVO;


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
			"INSERT INTO store_order (order_id, mem_id, store_id, totalprice, order_way, receive_address, order_note, order_taketime) "
			         + "VALUES (to_char(sysdate,'YYYYmmdd')||'-'||LPAD(to_char(order_seq.NEXTVAL),6,'0'),?,?,?,?,?,?,?)";
	private static final String UPDATE = 
			"UPDATE order set order_id=?, order_time=?, mem_id=?, store_id=?, order_state=?, totalprice=?, order_way=?, receive_address=?, qrcode=?, order_note=?, order_taketime=?";
	private static final String DELETE = 
			"DELETE FROM order where order_id = ?";
	private static final String GET_ONE_STMT = 
			"SELECT order_id, order_time, mem_id, store_id, order_state, totalprice, order_way, receive_address, qrcode, order_note, order_taketime from order where order_id = ?";
	private static final String GET_ALL_STMT = 
			"SELECT order_id, order_time, mem_id, store_id, order_state, totalprice, order_way, receive_address, qrcode, order_note, order_taketime from store_order order order by order_time desc";
	private static final String GET_ALL_BY_MEMID = "select o.mem_id, o.order_id, o.store_id, o.totalprice, o.order_time, o.order_way, o.order_state ,s.store_name from store_order o join store s on o.store_id = s.store_id where mem_id = ? order by order_time desc";
	private static final String GET_ALL_BY_VOTSTATE_MEMEID = "select o.mem_id, o.order_id, o.store_id, o.totalprice, o.order_time, o.order_way, o.order_state ,s.store_name from store_order o join store s on o.store_id = s.store_id where mem_id = ? and  not(order_state = ?)  order by order_time desc";
	private static final String GET_ALL_BY_STATE_MEMEID = "select o.mem_id, o.order_id, o.store_id, o.totalprice, o.order_time, o.order_way, o.order_state ,s.store_name from store_order o join store s on o.store_id = s.store_id where mem_id = ? and  order_state = ? order by order_time desc";
	private static final String UPDATE_STATE = 
			"UPDATE store_order set order_state= '已取餐' where order_id = ? and store_id = ?";
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
			pstmt.setString(4, orderVO.getOrder_state());
			pstmt.setInt(5, orderVO.getTotalprice());
			pstmt.setString(6, orderVO.getOrder_way());
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
			pstmt.setString(5, orderVO.getOrder_state());
			pstmt.setInt(6, orderVO.getTotalprice());
			pstmt.setString(7, orderVO.getOrder_way());
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
				orderVO.setOrder_state(rs.getString("order_state"));
				orderVO.setTotalprice(rs.getInt("totalprice"));
				orderVO.setOrder_way(rs.getString("order_way"));
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
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVO �]�٬� Domain objects
				orderVO = new Store_OrderVO();
				orderVO.setOrder_id(rs.getString("order_id"));
				orderVO.setOrder_time(rs.getTimestamp("order_time"));
				orderVO.setMem_id(rs.getString("mem_id"));
				orderVO.setStore_id(rs.getString("store_id"));
				orderVO.setOrder_state(rs.getString("order_state"));
				orderVO.setTotalprice(rs.getInt("totalprice"));
				orderVO.setOrder_way(rs.getString("order_way"));
				orderVO.setReceive_address(rs.getString("receive_address"));
				orderVO.setQrcode(rs.getBytes("qrcode"));
				orderVO.setOrder_note(rs.getString("order_note"));
				orderVO.setOrder_taketime(rs.getTimestamp("order_taketime"));
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
	
	@Override
	public List<Store_OrderVO> getAll(String memid) {
		// TODO Auto-generated method stub
		List<Store_OrderVO> list = new ArrayList<Store_OrderVO>();
		Store_OrderVO orderVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {

			
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_BY_MEMID);
			pstmt.setString(1, memid);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVO �]�٬� Domain objects
				orderVO = new Store_OrderVO();
				orderVO.setOrder_id(rs.getString("order_id"));
				orderVO.setOrder_time(rs.getTimestamp("order_time"));
				orderVO.setMem_id(rs.getString("mem_id"));
				orderVO.setStore_id(rs.getString("store_id"));
				orderVO.setOrder_state(rs.getString("order_state"));
				orderVO.setTotalprice(rs.getInt("totalprice"));
				orderVO.setOrder_way(rs.getString("order_way"));
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
	
	public List<Store_OrderVO> getByState(String memid) {
		// TODO Auto-generated method stub
		List<Store_OrderVO> list = new ArrayList<Store_OrderVO>();
		Store_OrderVO orderVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {

			
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_BY_VOTSTATE_MEMEID);
			pstmt.setString(1, memid);
			pstmt.setString(2, "已取餐");
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVO �]�٬� Domain objects
				orderVO = new Store_OrderVO();
				orderVO.setOrder_id(rs.getString("order_id"));
				orderVO.setOrder_time(rs.getTimestamp("order_time"));
				orderVO.setMem_id(rs.getString("mem_id"));
				orderVO.setStore_id(rs.getString("store_id"));
				orderVO.setOrder_state(rs.getString("order_state"));
				orderVO.setTotalprice(rs.getInt("totalprice"));
				orderVO.setOrder_way(rs.getString("order_way"));
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
	
	public List<Store_OrderVO> getBycomState(String memid) {
		// TODO Auto-generated method stub
		List<Store_OrderVO> list = new ArrayList<Store_OrderVO>();
		Store_OrderVO orderVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {

			
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_BY_STATE_MEMEID);
			pstmt.setString(1, memid);
			pstmt.setString(2, "已取餐");
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVO �]�٬� Domain objects
				orderVO = new Store_OrderVO();
				orderVO.setOrder_id(rs.getString("order_id"));
				orderVO.setOrder_time(rs.getTimestamp("order_time"));
				orderVO.setMem_id(rs.getString("mem_id"));
				orderVO.setStore_id(rs.getString("store_id"));
				orderVO.setOrder_state(rs.getString("order_state"));
				orderVO.setTotalprice(rs.getInt("totalprice"));
				orderVO.setOrder_way(rs.getString("order_way"));
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
	public List<Store_OrderVO> getBytake(String memid) {
		// TODO Auto-generated method stub
		List<Store_OrderVO> list = new ArrayList<Store_OrderVO>();
		Store_OrderVO orderVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {

			
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_BY_STATE_MEMEID);
			pstmt.setString(1, memid);
			pstmt.setString(2, "待取餐");
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVO �]�٬� Domain objects
				orderVO = new Store_OrderVO();
				orderVO.setOrder_id(rs.getString("order_id"));
				orderVO.setOrder_time(rs.getTimestamp("order_time"));
				orderVO.setMem_id(rs.getString("mem_id"));
				orderVO.setStore_id(rs.getString("store_id"));
				orderVO.setOrder_state(rs.getString("order_state"));
				orderVO.setTotalprice(rs.getInt("totalprice"));
				orderVO.setOrder_way(rs.getString("order_way"));
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

	@Override
	public void insertWithOrderlist(Store_OrderVO orderVO, List<OrderlistVO> list) {
		// TODO Auto-generated method stub

		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);
			
			// 1●設定於 pstm.executeUpdate()之前
    		con.setAutoCommit(false);
			
    		// 先新增部門
			String cols[] = {"ORDER_ID"};
			pstmt = con.prepareStatement(INSERT_STMT , cols);			
			pstmt.setString(1, orderVO.getMem_id());
			pstmt.setString(2, orderVO.getStore_id());
			pstmt.setInt(3, orderVO.getTotalprice());
			pstmt.setString(4, orderVO.getOrder_way());
			pstmt.setString(5, orderVO.getReceive_address());
			pstmt.setString(6, orderVO.getOrder_note());
			pstmt.setTimestamp(7, orderVO.getOrder_taketime());
			pstmt.executeUpdate();
			//掘取對應的自增主鍵值
			String next_order_id = null;
			ResultSet rs = pstmt.getGeneratedKeys();
			if (rs.next()) {
				next_order_id = rs.getString(1);
				System.out.println("自增主鍵值= " + next_order_id +"(剛新增成功的部門編號)");
			} else {
				System.out.println("未取得自增主鍵值");
			}
			rs.close();
			// 再同時新增員工
			OrderlistDAO dao = new OrderlistDAO();
			System.out.println("list.size()-A="+list.size());
			for (OrderlistVO orderlistVO : list) {
				orderlistVO.setOrder_id(new String(next_order_id)) ;
				dao.insert2(orderlistVO,con);
			}

			// 2●設定於 pstm.executeUpdate()之後
			con.commit();
			con.setAutoCommit(true);
			System.out.println("list.size()-B="+list.size());
			System.out.println("新增部門編號" + next_order_id + "時,共有員工" + list.size()
					+ "人同時被新增");
			
			// Handle any driver errors
		} catch (SQLException se) {
			if (con != null) {
				try {
					// 3●設定於當有exception發生時之catch區塊內
					System.err.print("Transaction is being ");
					System.err.println("rolled back-由-dept");
					con.rollback();
				} catch (SQLException excep) {
					throw new RuntimeException("rollback error occured. "
							+ excep.getMessage());
				}
			}
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
	
	public void updatestate(String orderid,String storeid) {
		// TODO Auto-generated method stub
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE_STATE);
			
			pstmt.setString(1, orderid);
			pstmt.setString(2, storeid);


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


}

package com.orderlist.model;

import java.util.List;


import com.order.model.Store_OrderVO;

public interface OrderlistDAO_interface {
	public void insert(OrderlistVO orderlistVO);
    public void update(OrderlistVO orderVO);
    public void delete(String order_id, String pro_id);
    public List<OrderlistVO> findByPrimaryKey(String order_id);
    public List<OrderlistVO> getAll();
	public List<OrderlistVO> getDetailOrder(String order_id, String pro_id);
	/*******************OrderDetailByOrderId�� from OrderListServlet.java********************************/
	public String getDetailProIdByOrderId(String order_id);
	
	//同時新增部門與員工 (實務上並不常用, 但,可用在訂單主檔與明細檔一次新增成功)
    public void insert2 (OrderlistVO orderlistVO , java.sql.Connection con);
	
}

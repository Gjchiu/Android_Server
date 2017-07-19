package com.order.model;

import java.util.*;


import com.orderlist.model.OrderlistVO;

public interface Store_OrderDAO_interface {
	public void insert(Store_OrderVO orderVO);
	public void update(Store_OrderVO orderVO);
	public void delete(String order_id);
	public Store_OrderVO findByPrimaryKey(String order_id);
	public List<Store_OrderVO> getAll();
	public List<Store_OrderVO> getAll(String memid);
	
    
    //同時新增部門與員工 (實務上並不常用, 但,可用在訂單主檔與明細檔一次新增成功)
    public void insertWithOrderlist(Store_OrderVO OrderVO , List<OrderlistVO> list);
}

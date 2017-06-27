package com.order.model;

import java.util.*;

public interface Store_OrderDAO_interface {
	public void insert(Store_OrderVO orderVO);
	public void update(Store_OrderVO orderVO);
	public void delete(String order_id);
	public Store_OrderVO findByPrimaryKey(String order_id);
	public List<Store_OrderVO> getAll();
}
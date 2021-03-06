package com.modesteam.urutau.service.persistence;

public class SearchOptions {
	private String attribute;
	private Object attributeValue;
	private String orderAtribute;
	private OrderEnum order;

	public SearchOptions(String attribute, Object value, String orderAtribute, OrderEnum order) {
		this.attribute = attribute;
		this.attributeValue = value;
		this.orderAtribute = orderAtribute;
		this.order = order;
	}

	public String getAttribute() {
		return attribute;
	}

	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}

	public Object getAttributeValue() {
		return attributeValue;
	}

	public void setAttributeValue(Object attributeValue) {
		this.attributeValue = attributeValue;
	}

	public String getOrderAtribute() {
		return orderAtribute;
	}

	public void setOrderAtribute(String orderAtribute) {
		this.orderAtribute = orderAtribute;
	}

	public OrderEnum getOrder() {
		return order;
	}

	public void setOrder(OrderEnum order) {
		this.order = order;
	}
}

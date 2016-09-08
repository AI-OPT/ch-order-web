package com.ai.ch.order.web.model.order;

import java.io.Serializable;

import com.ai.slp.order.api.warmorder.param.ProductImage;

public class OrdProdVo implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * 商品明细id
	 */
	private long prodDetalId;

	/**
	 * 商品单价
	 */
	private String prodSalePrice;
	
	/**
	 * 实付金额
	 */
	private String prodAdjustFee;
	
	/**
	 * 优惠扣减金额
	 */
	private String prodCouponFee;
	
	/**
	 * 积分扣减费用
	 */
	private long jfFee;
	/**
	 * 状态
	 */
	private String prodState;
	/**
	 * 总费用
	 */
	private String prodTotalFee;
	
	/**
	 * 商品名称
	 */
	private String prodName;
	
	/**
	 * 购买数量
	 */
	private long buySum;
	
	
	/**
	 * 图片信息
	 */
	private ProductImage productImage;
	
	/**
	 * 图片路径
	 */
	private String imageUrl;
	
	/**
	 * 商品售后是否标识
	 */
	private String cusServiceFlag;

	public String getProdSalePrice() {
		return prodSalePrice;
	}

	public void setProdSalePrice(String prodSalePrice) {
		this.prodSalePrice = prodSalePrice;
	}

	public String getProdAdjustFee() {
		return prodAdjustFee;
	}

	public void setProdAdjustFee(String prodAdjustFee) {
		this.prodAdjustFee = prodAdjustFee;
	}

	public String getProdCouponFee() {
		return prodCouponFee;
	}

	public void setProdCouponFee(String prodCouponFee) {
		this.prodCouponFee = prodCouponFee;
	}

	public String getProdState() {
		return prodState;
	}

	public void setProdState(String prodState) {
		this.prodState = prodState;
	}

	public String getProdName() {
		return prodName;
	}

	public void setProdName(String prodName) {
		this.prodName = prodName;
	}

	public long getBuySum() {
		return buySum;
	}

	public void setBuySum(long buySum) {
		this.buySum = buySum;
	}

	public long getJfFee() {
		return jfFee;
	}

	public void setJfFee(long jfFee) {
		this.jfFee = jfFee;
	}

	public ProductImage getProductImage() {
		return productImage;
	}

	public void setProductImage(ProductImage productImage) {
		this.productImage = productImage;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public long getProdDetalId() {
		return prodDetalId;
	}

	public void setProdDetalId(long prodDetalId) {
		this.prodDetalId = prodDetalId;
	}

	public String getCusServiceFlag() {
		return cusServiceFlag;
	}

	public void setCusServiceFlag(String cusServiceFlag) {
		this.cusServiceFlag = cusServiceFlag;
	}

	public String getProdTotalFee() {
		return prodTotalFee;
	}

	public void setProdTotalFee(String prodTotalFee) {
		this.prodTotalFee = prodTotalFee;
	}
	
}

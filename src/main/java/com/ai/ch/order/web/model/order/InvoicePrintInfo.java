package com.ai.ch.order.web.model.order;

public class InvoicePrintInfo {
	private String id;//授权ID
	//销售方的公司代码
    private String corporationCode;
    //发票类型： 0电子发票，1 纸质发票
    private String invoiceClass;
    //发票种类： 001增值税专用发票，002 增值税电子普通发票， 003增值税普通发票， 004废旧物资发票，005增值税电子专用发票
    private String invoiceKind;
    //购货方纳税人识别号
    private String buyerTaxpayerNumber ;
    private String buyerCode ;
    private String buyerName;
    private String buyerAddress;
    private String buyerTelephone  ;
    private String buyerMobiile  ;
    private String buyerEmail  ;	
    private String buyerCompanyClass ;
    private String buyerBankCode ;
    private String buyerBankName ;
    private String buyerBankAccount  ;
    private String salesOrderNo  ;
    private String orderCreateTime ;
    private String orderItem;
    private String materialCode   ;
    private String specification;
    private String materialName;//商品名称
    private String price   ;
    private String quantity   ;
    private String unit  ;
    private String discountAmount;
    private String rate;
    private String tax;
    private String amount;
    private String taxAmount;
    private String remark;
    
    public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCorporationCode() {
		return corporationCode;
	}
	public void setCorporationCode(String corporationCode) {
		this.corporationCode = corporationCode;
	}
	public String getInvoiceClass() {
		return invoiceClass;
	}
	public void setInvoiceClass(String invoiceClass) {
		this.invoiceClass = invoiceClass;
	}
	public String getInvoiceKind() {
		return invoiceKind;
	}
	public void setInvoiceKind(String invoiceKind) {
		this.invoiceKind = invoiceKind;
	}
	public String getBuyerTaxpayerNumber() {
		return buyerTaxpayerNumber;
	}
	public void setBuyerTaxpayerNumber(String buyerTaxpayerNumber) {
		this.buyerTaxpayerNumber = buyerTaxpayerNumber;
	}
	public String getBuyerCode() {
		return buyerCode;
	}
	public void setBuyerCode(String buyerCode) {
		this.buyerCode = buyerCode;
	}
	public String getBuyerName() {
		return buyerName;
	}
	public void setBuyerName(String buyerName) {
		this.buyerName = buyerName;
	}
	public String getBuyerAddress() {
		return buyerAddress;
	}
	public void setBuyerAddress(String buyerAddress) {
		this.buyerAddress = buyerAddress;
	}
	public String getBuyerTelephone() {
		return buyerTelephone;
	}
	public void setBuyerTelephone(String buyerTelephone) {
		this.buyerTelephone = buyerTelephone;
	}
	public String getBuyerMobiile() {
		return buyerMobiile;
	}
	public void setBuyerMobiile(String buyerMobiile) {
		this.buyerMobiile = buyerMobiile;
	}
	public String getBuyerEmail() {
		return buyerEmail;
	}
	public void setBuyerEmail(String buyerEmail) {
		this.buyerEmail = buyerEmail;
	}
	public String getBuyerCompanyClass() {
		return buyerCompanyClass;
	}
	public void setBuyerCompanyClass(String buyerCompanyClass) {
		this.buyerCompanyClass = buyerCompanyClass;
	}
	public String getBuyerBankCode() {
		return buyerBankCode;
	}
	public void setBuyerBankCode(String buyerBankCode) {
		this.buyerBankCode = buyerBankCode;
	}
	public String getBuyerBankAccount() {
		return buyerBankAccount;
	}
	public void setBuyerBankAccount(String buyerBankAccount) {
		this.buyerBankAccount = buyerBankAccount;
	}
	public String getSalesOrderNo() {
		return salesOrderNo;
	}
	public void setSalesOrderNo(String salesOrderNo) {
		this.salesOrderNo = salesOrderNo;
	}
	public String getOrderCreateTime() {
		return orderCreateTime;
	}
	public void setOrderCreateTime(String orderCreateTime) {
		this.orderCreateTime = orderCreateTime;
	}
	public String getOrderItem() {
		return orderItem;
	}
	public void setOrderItem(String orderItem) {
		this.orderItem = orderItem;
	}
	public String getMaterialCode() {
		return materialCode;
	}
	public void setMaterialCode(String materialCode) {
		this.materialCode = materialCode;
	}
	public String getSpecification() {
		return specification;
	}
	public void setSpecification(String specification) {
		this.specification = specification;
	}
	public String getMaterialName() {
		return materialName;
	}
	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getDiscountAmount() {
		return discountAmount;
	}
	public void setDiscountAmount(String discountAmount) {
		this.discountAmount = discountAmount;
	}
	public String getRate() {
		return rate;
	}
	public void setRate(String rate) {
		this.rate = rate;
	}
	public String getTax() {
		return tax;
	}
	public void setTax(String tax) {
		this.tax = tax;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getTaxAmount() {
		return taxAmount;
	}
	public void setTaxAmount(String taxAmount) {
		this.taxAmount = taxAmount;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getBuyerBankName() {
		return buyerBankName;
	}
	public void setBuyerBankName(String buyerBankName) {
		this.buyerBankName = buyerBankName;
	}
	
    

}

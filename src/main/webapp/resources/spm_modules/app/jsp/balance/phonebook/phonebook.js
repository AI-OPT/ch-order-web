//通讯录详情 批量删除弹出	
jQuery(document).ready(function($) {
	$('.eject-samll-title .img').click(function(){
	$('.eject-mask').fadeOut(100);
	$('.eject-samll').slideUp(150);
	})
	$('.eject-samll-confirm .close-btn').click(function(){
	$('.eject-mask').fadeOut(100);
	$('.eject-samll').slideUp(150);
	})
})
//通讯录详情 批量导入弹出
jQuery(document).ready(function($) {
	$('.batch').click(function(){
	$('.eject-mask').fadeIn(100);
	$('.eject-medium').slideDown(200);
	})
	$('.eject-medium-title .img').click(function(){
	$('.eject-mask').fadeOut(100);
	$('.eject-medium').slideUp(150);
	})
})	
//通讯录详情 添加弹出弹出
jQuery(document).ready(function($) {
	$('.add').click(function(){
	$('.eject-mask').fadeIn(100);
	$('.eject-large').slideDown(200);
	})
	$('.eject-large-title .img').click(function(){
	$('.eject-mask').fadeOut(100);
	$('.eject-large').slideUp(150);
	})
})	

//查询信息 结果弹出框
jQuery(document).ready(function($) {
	$('.eject-paging').click(function(){
	$('.eject-mask').fadeIn(100);
	$('.eject-large-paging').slideDown(200);
	})
	$('.eject-large-title .img').click(function(){
	$('.eject-mask').fadeOut(100);
	$('.eject-large-paging').slideUp(150);
	})
})	
//搜索 结果弹出框
jQuery(document).ready(function($) {
	$('.eject-query').click(function(){
	$('.eject-mask').fadeIn(100);
	$('.eject-large-query').slideDown(200);
	})
	$('.eject-large-title .img').click(function(){
	$('.eject-mask').fadeOut(100);
	$('.eject-large-query').slideUp(150);
	})
})
jQuery(document).ready(function($) {
	$('.multi-line').click(function(){
	$('.eject-mask1').fadeIn(100);
	$('#samll-block').slideDown(200);
	})
	$('.samll-block-title .img').click(function(){
	$('.eject-mask1').fadeOut(100);
	$('#samll-block').slideUp(150);
	})
	$('.eject-samll-confirm .close-btn1').click(function(){
	$('#samll-block').slideUp(150);
	})
})	
//通讯录管理 删除弹出	
jQuery(document).ready(function($) {
	$('.trash-close').click(function(){
	$('.eject-mask').fadeIn(100);
	$('.eject-samll').slideDown(200);
	})
	$('.eject-samll-title .img').click(function(){
	$('.eject-mask').fadeOut(100);
	$('.eject-samll').slideUp(150);
	})
	$('.eject-samll-confirm .close-btn').click(function(){
	$('.eject-mask').fadeOut(100);
	$('.eject-samll').slideUp(150);
	})
})
//弹出框带图标 删除弹出	
jQuery(document).ready(function($) {
	$('.eject-icon').click(function(){
	$('.eject-mask').fadeIn(100);
	$('.eject-samll-icon').slideDown(200);
	})
	$('.eject-samll-title .img').click(function(){
	$('.eject-mask').fadeOut(100);
	$('.eject-samll-icon').slideUp(150);
	})
	$('.eject-samll-confirm .close-btn').click(function(){
	$('.eject-mask').fadeOut(100);
	$('.eject-samll-icon').slideUp(150);
	})
})
//通讯录管理 充流量弹出
jQuery(document).ready(function($) {
	$('.charge-flow').click(function(){
	$('.eject-mask').fadeIn(100);
	$('.eject-large').slideDown(200);
	})
	$('.eject-large-title .img').click(function(){
	$('.eject-mask').fadeOut(100);
	$('.eject-large').slideUp(150);
	})
})	
//通讯录管理 充话费弹出
jQuery(document).ready(function($) {
	$('.charge-phone').click(function(){
	$('.eject-mask').fadeIn(100);
	$('#eject-iphone').slideDown(200);
	})
	$('.eject-large-title .img').click(function(){
	$('.eject-mask').fadeOut(100);
	$('#eject-iphone').slideUp(150);
	})
})

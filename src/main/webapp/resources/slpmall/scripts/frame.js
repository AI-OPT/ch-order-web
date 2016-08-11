//头部点击1 
 $(function () {
    var st = 100;
    $('.city').mouseenter(function () {
		$(this).children('a').addClass('b');
		$(this).children('.city-hover').show(1);  
    })
		$('.city').mouseleave(function () {
		$(this).children('a').removeClass('b');
		$(this).children('.city-hover').hide(1);  
  
    });	
  });
  

 
 //头部点击2 
 $(function () {
    var st = 100;
    $('.use').mouseenter(function () {
		$('.use a').addClass('b');
		$('.use .use-hover').show(1);
    })
		$(".use .use-hover").click(function () {
                $(this).hide(1);
            });
			
		$('.use').mouseleave(function () {
        $('.use .use-hover').hide(1);
		 $('.use a').removeClass('b');
    });	
  });
  
   //头部点击3 
 $(function () {
    var st = 100;
    $('.kefu').mouseenter(function () {
		$('.kefu a').addClass('b');
		$('.kefu .kefu-hover').show(1);
    })
		$(".kefu .kefu-hover").click(function () {
                $(this).hide(1);
            });
			
		$('.kefu').mouseleave(function () {
        $('.kefu .kefu-hover').hide(1);
		 $('.kefu a').removeClass('b');
    });	
  });
  
//左侧点击1 
 $(function () {
    var st = 100;
    $('.Mobile').mouseenter(function () {
		$('.Mobile a').addClass('b');
		$('.Mobile .Mobile-hover').show(1);
    })
		$(".Mobile .Mobile-hover").click(function () {
                $(this).hide(1);
            });
			
		$('.Mobile').mouseleave(function () {
        $('.Mobile .Mobile-hover').hide(1);
		 $('.Mobile a').removeClass('b');
    });	
  });
  
  //左侧点击2 
 $(function () {
    var st = 100;
    $('.Unicom').mouseenter(function () {
		$('.Unicom a').addClass('b');
		$('.Unicom .Unicom-hover').show(1);
    })
		$(".Unicom .Unicom-hover").click(function () {
                $(this).hide(1);
            });
			
		$('.Unicom').mouseleave(function () {
        $('.Unicom .Unicom-hover').hide(1);
		 $('.Unicom a').removeClass('b');
    });	
  });
  
    //左侧点击3 
 $(function () {
    var st = 100;
    $('.telecom').mouseenter(function () {
		$('.telecom a').addClass('b');
		$('.telecom .telecom-hover').show(1);
    })
		$(".telecom .telecom-hover").click(function () {
                $(this).hide(1);
            });
			
		$('.telecom').mouseleave(function () {
        $('.telecom .telecom-hover').hide(1);
		 $('.telecom a').removeClass('b');
    });	
  });
  
  
//table切换1
$(function(){
$(".charge-title ul li a").click(function () {
                $(".charge-title ul li a").each(function () {
                    $(this).removeClass("current");
                });
                $(this).addClass("current");
            });
$('.charge-title ul li a').click(function(){
  var index=$('.charge-title ul li a').index(this);
      if(index==0){
     $('#date1').show();
  $('#date2').hide();
   }
   if(index==1){
     $('#date2').show();
  $('#date1').hide();
   }
  }); 
});
//table切换结束

//图片table切换2
$(function(){
$(".plist-right-title ul li a").click(function () {
                $(".plist-right-title ul li a").each(function () {
                    $(this).removeClass("current");
                });
                $(this).addClass("current");
            });
$('.plist-right-title ul li a').click(function(){
  var index=$('.plist-right-title ul li a').index(this);
      if(index==0){
     $('#img-list1').show();
  $('#img-list2').hide();
   }
   if(index==1){
     $('#img-list2').show();
  $('#img-list1').hide();
   }
  }); 
});
//table切换结束

//图片table切换3
$(function(){
$(".plist-right-title-tow ul li a").click(function () {
                $(".plist-right-title-tow ul li a").each(function () {
                    $(this).removeClass("current");
                });
                $(this).addClass("current");
            });
$('.plist-right-title-tow ul li a').click(function(){
  var index=$('.plist-right-title-tow ul li a').index(this);
      if(index==0){
     $('#img-list3').show();
  $('#img-list4').hide();
   }
   if(index==1){
     $('#img-list4').show();
  $('#img-list3').hide();
   }
  }); 
});
//table切换结束  

//商品详情
$(function(){
$(".parameter-right-tilit ul li").click(function () {
                $(".parameter-right-tilit ul li").each(function () {
                    $(this).removeClass("current");
                });
                $(this).addClass("current");
            });
$('.parameter-right-tilit ul li').click(function(){
  var index=$('.parameter-right-tilit ul li').index(this);
      if(index==0){
     $('#date1').show();
     $('#date2').hide();
     $('#date3').hide();
   }
   if(index==1){
   $('#date2').show();
   $('#date1').hide();
   $('#date3').hide();
   }
   if(index==2){
   $('#date3').show();
   $('#date2').hide();
   $('#date1').shhideow();
   }
  }); 
});
//table切换结束  


//table切换1
$(function(){
$(".center-table ul li a").click(function () {
                $(".center-table ul li a").each(function () {
                    $(this).removeClass("current");
                });
                $(this).addClass("current");
            });
$('.center-table ul li a').click(function(){
  var index=$('.center-table ul li a').index(this);
      if(index==0){
     $('#regeiter-date1').show();
  $('#regeiter-date2').hide();
   }
   if(index==1){
     $('#regeiter-date2').show();
  $('#regeiter-date1').hide();
   }
  }); 
});

//点击展开
$(document).ready(function(){
  $(".balance-title p .checkbox").click(function () {
	  $(".balance-table").slideToggle(100);
	  $(".balance-title").toggleClass("reorder remove");
	  });
});

//搜索结果点击展开
$(document).ready(function(){
  $(".search-main ul li .more").click(function () {
	  $(".more-ctn").slideToggle(100);
	  $(".search-main").toggleClass("reorder remove");
	  });
});


$(document).ready(function(){
$(".results-left-title ul .rise").click(function(){
  $(".results-left-title ul .decline").show();
  $(this).hide();
});
$(".results-left-title ul .decline").click(function(){
  $(".results-left-title ul .rise").show();
  $(this).hide();
});

})
;

//搜索已选中关闭
$(function(){
$(".payment-title .close a").click(function () {
	$(this).parent('.close').hide();
	});
	});  
 
//table 点击展开
$(function () {
    $(".information-table table .click span").click(function () {
		$(this).children('i').toggleClass("icon-angle-down  icon-angle-up");
		$(this).parents().parent().children('.zhank').slideToggle(100);
    });
});	
//去掉table最后一个边框
$(function () {
$(".table-border-list tr:last").css("border-bottom","none");
$(".order-list-table  li a:last").css("border-right","none");
/**
$(".account-table tr:last").css("border-bottom","none");
$("#order-date1 tr:last").css("border-bottom","none");
$("#order-date tr:last").css("border-bottom","none");
$("#order-date2 tr:last").css("border-bottom","none");
**/
});

//忘记密码 radio 点击切换
$(function(){
$(".radioa").click(function () {
	$('.mail').hide();
	$('.phone').show();
	$('.password').hide();
});
});

$(function(){
$(".radiob").click(function () {
	$('.phone').hide();
	$('.mail').show();
	$('.password').hide();
});
});
$(function(){
$(".radioc").click(function () {
	$('.phone').hide();
	$('.mail').hide();
	$('.password').show();
});
});

//我的订单 table切换1
//$(function(){
//$(".order-list-table ul li a").click(function () {
//                $(".order-list-table ul li a").each(function () {
//                    $(this).removeClass("current");
//                });
//                $(this).addClass("current");
//            });
//$('.order-list-table ul li a').click(function(){
//  var index=$('.order-list-table ul li a').index(this);
//      if(index==0){
//     $('#order-date').show();
//  	  $('#order-date1').hide();
//	  $('#order-date2').hide();
//	  $('#order-date3').hide();
//	  $('#order-date4').hide();
//   }
//  if(index==1){
//     $('#order-date1').show();
//  	  $('#order-date').hide();
//	  $('#order-date2').hide();
//	  $('#order-date3').hide();
//	  $('#order-date4').hide();
//   }
//   if(index==2){
//     $('#order-date2').show();
//  	  $('#order-date').hide();
//	  $('#order-date1').hide();
//	  $('#order-date3').hide();
//	  $('#order-date4').hide();
//   }
//   if(index==3){
//     $('#order-date3').show();
//  	  $('#order-date').hide();
//	  $('#order-date1').hide();
//	  $('#order-date2').hide();
//	  $('#order-date4').hide();
//   }
//   if(index==4){
//     $('#order-date4').show();
//  	  $('#order-date').hide();
//	  $('#order-date1').hide();
//	  $('#order-date3').hide();
//	  $('#order-date2').hide();
//   }
//  }); 
//});
//table切换结束


////我的订单 点击展开
//$(function () {
//    $(".order-list-bj ul li .is").click(function () {
//		$(this).children('i').toggleClass("icon-angle-down  icon-angle-up");
//		$(this).parents().children('.open-gaoj').slideToggle(100);
//    });
//});	



//我的订单  账户设置 点击展开
 $(function () {
    var st = 100;
    $('.fsast-bav ul .shez a').mouseenter(function () {
		$(this).children('i').toggleClass("icon-angle-down  icon-angle-up");
		$('.setgs').show(1);
    })
		$(".fsast-bav .setgs").click(function () {
                $(this).hide(1);
            });
		$('.fsast-bav ul .shez').mouseleave(function () {
		$(this).children('i').toggleClass("icon-angle-up icon-angle-down");
        $('.fsast-bav .setgs').hide(1);
    });	
  });
 
//设置支付密码 关闭提示
$(function(){
$(".prompt-risk .img").click(function () {
	$(this).parent('.prompt-risk').hide();
	});
	}); 
	
//通讯录详情 table 点击变换div
$(document).ready(function(){
$(".account-table .click").click(function(){
  $(this).parent().parent().parent().children(".hover").show();
  $(this).parent().parent(".current").hide();
});

$(".account-table .mail-btn").click(function(){
  $(this).parent().parent().parent().children(".current").show();
  $(this).parent().parent(".hover").hide();
});

})
;	
//通讯录详情 批量删除弹出	
jQuery(document).ready(function($) {
//	$('.click-close').click(function(){
//	$('.eject-mask').fadeIn(100);
//	$('.eject-samll').slideDown(200);
//	})
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

 $(function () {
    var st = 100;
    $('.icon-envelope-alt').mouseenter(function () {
		$('.onclick-email').show(1);
    })
		$(".icon-envelope-alt").click(function () {
                $(this).hide(1);
           });
	
		$('.icon-envelope-alt').mouseleave(function () {
        $('.onclick-email').hide(1);
    });
    
    $('.icon-tablet').mouseenter(function () {
		$('.onclick').show(1);
    })
		$(".icon-tablet").click(function () {
                $(this).hide(1);
           });
	
		$('.icon-tablet').mouseleave(function () {
        $('.onclick').hide(1);
    });	
 });
 
/**限制字数个数**/


(function($) {	
	$.fn.fonts = function(option){
		option = $.extend({},$.fn.fonts.option,option);
		return this.each(function(){
		var objString = $(this).text(),
		    objLength = $(this).text().length,
			num = option.fontNum;
		if(objLength > num){
            objString = $(this).text(objString.substring(0,num) + "…");
		}
		 })
	}
	// default options
	$.fn.fonts.option = {
	fontNum:100 //font num
	};
})(jQuery);
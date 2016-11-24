package com.ai.ch.order.web.controller.ofc;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.ai.opt.sdk.dubbo.util.DubboConsumerFactory;
import com.ai.opt.sdk.util.DateUtil;
import com.ai.slp.order.api.ofc.interfaces.IOfcSV;

@Service
@Lazy(false)
@PropertySource(value="classpath:ofcConfig.properties")
public class OrderTaskJob {

	private static final Log LOG = LogFactory.getLog(OrderTaskJob.class);

	IOfcSV ofcSV;

	public BlockingQueue<String[]> ordOrderQueue;

	public static ExecutorService handlePool;

	@Scheduled(cron = "${ftp.schedule}")
	public void orderImportJob() {
		run();
	}

	public void run() {
		LOG.error("任务开始执行，当前时间戳：" + DateUtil.getSysDate());
		try {
			ordOrderQueue = new LinkedBlockingQueue<String[]>(1000);

			handlePool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 2);
			// 生产者
			Thread producerThred = new Thread(new OrderReadFileThread(ordOrderQueue));
			producerThred.start();
			// 消费者
			ofcSV = DubboConsumerFactory.getService(IOfcSV.class);
			LOG.error("开始插入订单信息，当前时间戳：" + DateUtil.getSysDate());
			for (int i = 0; i < Runtime.getRuntime().availableProcessors() * 2; i++) {
				handlePool.execute(new OrderThread(ordOrderQueue, ofcSV));
			}
			// 未消费完等待
			while (!ordOrderQueue.isEmpty()) {
				Thread.sleep(10 * 1000);
			}
 
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			handlePool.shutdown();
			LOG.error("任务结束，当前时间戳：" + DateUtil.getSysDate());
		}
	}

}

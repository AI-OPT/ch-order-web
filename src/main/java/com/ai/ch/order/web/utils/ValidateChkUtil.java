package com.ai.ch.order.web.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.SftpATTRS;

public class ValidateChkUtil {

	private static final Log LOG = LogFactory.getLog(ValidateChkUtil.class);

	private SimpleDateFormat longFormat = new SimpleDateFormat("yyyyMMddHHmmss");
	private SimpleDateFormat shortFormat = new SimpleDateFormat("yyyyMMdd");

	/**
	 * 校验校验文件
	 * 
	 * @param chkPath
	 * @param datPath
	 * @return
	 * @author zhangqiang7
	 * @throws Exception
	 * @UCUSER
	 */
	public String validateChk(String path, String localpath, String datName, String chkName, ChannelSftp sftp)
			throws Exception {
		SftpATTRS chkAttrs = sftp.lstat(path + "/" + chkName);
		SftpATTRS datAttrs = sftp.lstat(path + "/" + datName);
		LOG.info("+++++++开始校验文件:" + datName);
		StringBuilder errCode = new StringBuilder();
		// 获取校验文件数据
		/*
		 * FileInputStream fileInputStream = new FileInputStream(chkFile);
		 * InputStreamReader inputStreamReader = new
		 * InputStreamReader(fileInputStream, "gb2312"); if (!chkFile.exists()
		 * || chkFile.isDirectory() || chkFile.length() == 0) {
		 * errCode.append("99"); }
		 */
		if (chkAttrs.getSize() == 0) {
			errCode.append("99");
		}
		// BufferedReader br = new BufferedReader(inputStreamReader);
		InputStream ins = SftpUtil.download(path, chkName, localpath, sftp);
		BufferedReader br = new BufferedReader(new InputStreamReader(ins, "utf-8"));
		String temp = null;
		temp = br.readLine();
		String[] str = {};
		while (temp != null) {
			str = temp.split("\\t");
			temp = br.readLine();
		}
		// 获取数据文件数据
		InputStream datInputStream = SftpUtil.download(path, datName, localpath, sftp);
		InputStreamReader datInputStreamReader = new InputStreamReader(datInputStream, "utf-8");
		// 校验数据文件是否存在mark
		if (chkAttrs.getSize() == 0) {
			errCode.append("01");
		}
		BufferedReader datReader = new BufferedReader(datInputStreamReader);
		String datTemp = datReader.readLine();
		Long count = 0L;
		while (datTemp != null) {
			datTemp = datReader.readLine();
			count++;
		}

		// 校验数据文件大小
		if (!String.valueOf(datAttrs.getSize()).equals(str[1])) {
			errCode.append("02");
		}
		// 校验数据文件记录
		if (!count.toString().equals(str[2])) {
			errCode.append("03");
		}
		// 校验数据文件数据修改时间 if(!shortFormat.format(new
		/*
		 * if(shortFormat.format(datAttrs.getMTime()).equals(str[3])){
		 * errCode.append("04"); }
		 */
		// 校验数据文件创建时间
		/*
		 * Files.readAttributes(Paths.get(datPath),
		 * BasicFileAttributes.class).creationTime().toMillis();
		 * if(!longFormat.format(new Date(createTime)).equals(str[4])){
		 * errCode.append("05"); }
		 */
		return errCode.toString();
	}

}

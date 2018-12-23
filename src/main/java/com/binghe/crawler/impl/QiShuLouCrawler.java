package com.binghe.crawler.impl;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.binghe.crawler.Crawler;
import com.binghe.crawler.service.HttpService;

/**
 * 奇书楼下载小说
 * @author binghe
 *
 */
@Component
public class QiShuLouCrawler implements Crawler {
	@Autowired
	private HttpService httpService;
	
	@Override
	public void run() {
		// 三的修改
		
		// 二的修改
		
		// 小说主页
		String mainUrl = "https://www.qishulou.com/xuanyi/fangjian/";
		// 本地文本文件路径
		String filePath = "E:/text";
		try {
			Document mainDoc = parseHtml(mainUrl);
			
			// 解析html
			// 书名
			String bookName = mainDoc.select("#content-list .book-intro.clearfix .book-describe h1").first().text();
			
			// 获取章节节点
			Elements lis = mainDoc.select("#content-list .book-list.clearfix ul li");
			for (Element li : lis) {
				Element catalogNode = li.select("a").first();
				// 章节标题
				String title = catalogNode.attr("title");
				// 章节链接
				String catalogUrl = catalogNode.attr("href");
				
				Document catalogDoc = parseHtml(catalogUrl);
				Elements contentNodes = catalogDoc.select("#pagewrap .post.clearfix p");
				
				StringBuilder content = new StringBuilder(title + "\r\n");
				for (Element contentNode : contentNodes) {
					// 段落内容
					String section = contentNode.text();
					content.append("    " + section + "\r\n");
				}
				
				// 将本章内容存入本地文本
				appendToText(String.format("%s/%s.txt", filePath, bookName), content.toString());
			}
			
			System.out.println(">>>>>>>>>>>>>> 下载结束");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		String filePath = "E:/text";
		String bookName = "众";
		String string = String.format("%s/%s.txt", filePath, bookName);
		System.out.println(string);
	}
	
	/**
	 * 根据url获取网页并解析为html元素
	 * @param url
	 * @return
	 * @throws Exception
	 */
	private Document parseHtml(String url) throws Exception {
		String html = httpService.doGet(url);
		Document document = Jsoup.parse(html);
		return document;
	}
	
	/**
	 * 将文本添加到文件末尾
	 * @param filePath 文件路径
	 * @param content 文本内容
	 * @throws Exception 
	 */
	public void appendToText(String filePath, String content) throws Exception {
		BufferedWriter out = null;
		try {
			out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filePath, true)));
			out.write(content);
		} finally {
			out.close();
		}
	}
}

//package com.binghe.crawler.impl;
//
//import java.io.BufferedInputStream;
//import java.io.BufferedWriter;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.OutputStreamWriter;
//
//import javazoom.jl.decoder.JavaLayerException;
//import javazoom.jl.player.Player;
//
//import org.jsoup.Jsoup;
//import org.jsoup.nodes.Document;
//import org.jsoup.nodes.Element;
//import org.jsoup.select.Elements;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.binghe.crawler.Crawler;
//import com.binghe.crawler.service.HttpService;
//
//@Service
//public class StoryCatalogCrawler2 implements Crawler {
//	@Autowired
//	private HttpService httpService;
//	
//	@Override
//	public void run() {
//		// 要保存的目录名
//		String pathName = "E:/text";
//		// 目录页面url
//		String preUrl = "";
//		// 要保存的目录文本名
//		String catalog = "目录页";
//		// 关键词1
//		String keyWord1 = "";
//		// 关键词2
//		String keyWord2 = "";
//		// 关键词3
//		String keyWord3 = "";
//		// url起始页
//		int start = 64;
//		// url末页
//		int end = 72;
//		
//		for (int j = start; j <= end; j++) {
//			String catalogUrl = preUrl + j;
//			// 目录页的Document
//			Document catalogDoc = parseHtml(catalogUrl+".html");
//			
//			if (catalogDoc == null) {
//				return;
//			}else {
//				// 获取总页数
//				int pageCount = getPageCount(catalogDoc);
//				for (int i = 1; i <= pageCount; i++) {
//					StringBuffer content = new StringBuffer();
//					String pageUrl = "";
//					if (i == 1) {
//						pageUrl = catalogUrl + ".html";
//					}else {
//						pageUrl = catalogUrl + "-" + i + ".html";
//					}
//					Document pageDoc = parseHtml(pageUrl);
//					// 拿到各章节对应的a标签对象
//					Elements chapters = pageDoc.select(".box.list.channel ul li a");
//					
//					// 文章内容
//					for (Element chapter : chapters) {
//						// 标题名
//						String title = chapter.text();
//						if (title.contains(keyWord1)) {
//							content.append(title + "        " + i + "        " + j + "\r\n");
//						}
//					}
//					appendToTxt(pathName+"/"+catalog+".txt", content.toString());
//				}
//			}
//		}
//		try {
//			new Player(new BufferedInputStream(new FileInputStream(new File("E:/CloudMusic/the ARROWS - ツナ覚醒.mp3")))).play();
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (JavaLayerException e) {
//			e.printStackTrace();
//		}
//	}
//
//	private int getPageCount(Document catalogDoc) {
//		// 目录div
//		Element catalogDiv = catalogDoc.select(".box.list.channel").first();
//		String pageCountString = catalogDiv.select(".pagination").first().select("span").first().text();
//		return Integer.parseInt(pageCountString.split("/")[1].split("\\D")[0]);
//	}
//
//	/**
//	 * 获取对应链接的页面Document元素
//	 * @param url
//	 * @return
//	 */
//	private Document parseHtml(String url) {
//		String html = null;
//		try {
//			html = this.httpService.doGet(url);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		if (html != null) {
//			return Jsoup.parse(html);
//		}
//		return null;
//	}
//	
//	/**
//	 * 将文本添加到文件末尾
//	 * @param filePath 文件路径
//	 * @param content 文本内容
//	 */
//	public void appendToTxt(String filePath, String content) {
//		BufferedWriter out = null;
//		try {
//			out = new BufferedWriter(new OutputStreamWriter(
//					new FileOutputStream(filePath, true)));
//			out.write(content);
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			try {
//				out.close();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//	}
//}

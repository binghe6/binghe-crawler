//package com.binghe.crawler.impl;
//
//import java.io.BufferedWriter;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.OutputStreamWriter;
//import java.util.ArrayList;
//import java.util.List;
//
//import org.jsoup.Jsoup;
//import org.jsoup.nodes.Document;
//import org.jsoup.nodes.Element;
//import org.jsoup.nodes.TextNode;
//import org.jsoup.select.Elements;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.binghe.crawler.Crawler;
//import com.binghe.crawler.service.HttpService;
//
//@Service
//public class StoryDownCrawler2 implements Crawler {
//	@Autowired
//	private HttpService httpService;
//	
//	@Override
//	public void run() {
//		// 链接前缀
//		String preUrl = "";
//		// 要保存的目录名
//		String pathName = "E:/text";
//		// 目录页面url
//		String catalogUrl = "";
//		// 书名
//		String bookName = "";
//		// 1.解析目录页面，拿到各章节的url
//		// 目录页的Document
//		Document catalogDoc = parseHtml(catalogUrl+".jsp");
//		if (catalogDoc == null) {
//			return;
//		}else {
//			// 总页数
//			int pageCount = 117;
////			int pageCount = Integer.parseInt(catalogDoc.select(".listbox .page .end").first().text());
//			
//			
//			
//			for (int i = pageCount; i >= 1; i--) {
//				String pageUrl = "";
//				if (i == 1) {
//					pageUrl = catalogUrl + ".jsp";
//				}else {
//					pageUrl = catalogUrl + "_" + i + ".jsp";
//				}
//				Document pageDoc = parseHtml(pageUrl);
//				// 拿到各章节对应的a标签对象
//				Elements chapters = pageDoc.select(".listbox ul li a");
//				
//				// 存储当页符合的章节元素
//				List<Element> chapterList = new ArrayList<Element>();
//				
//				// 文章内容
//				for (Element chapter : chapters) {
//					// 标题名
//					String title = chapter.text();
//					if (title.contains(bookName)) {
//						chapterList.add(chapter);
//					}
//				}
//				
//				// 从最后一次出现的章节开始记录小说
//				for (int j = chapterList.size()-1; j >= 0; j--) {
//					Element chapter = chapterList.get(j);
//					// 各章节的url
//					String chapterUrl = preUrl + chapter.attr("href");
//					Document chapterDoc = parseHtml(chapterUrl);
//					if (catalogDoc == null) {
//						return;
//					}else {
//						StringBuffer content = new StringBuffer();
//						String title = chapterDoc.select(".viewbox .title").first().text();
//						// 拿到章节内容的节点
//						Element element = chapterDoc.select(".viewbox .content").first();
//						// 拿到内容文本节点集体
//						List<TextNode> textNodes = element.textNodes();
//						content.append(title + "\r\n");
//						for (TextNode textNode : textNodes) {
//							// 将各节点文本添加到章节内容里
//							content.append(textNode.text()+"\r\n");
//						}
//						appendToTxt(pathName+"/"+bookName+".txt", content.toString());
//					}
//				}
//			}
//			
//		}
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

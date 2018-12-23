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
//public class StoryDownloadCrawler implements Crawler {
//
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
//		
//		for (int z = 64; z <= 72; z++) {
//			
//			// 1.解析目录页面，拿到各章节的url
//			// 目录页的Document
//			Document catalogDoc = parseHtml(catalogUrl+z+".html");
//			if (catalogDoc == null) {
//				return;
//			}else {
//				// 目录div
//				Element catalogDiv = catalogDoc.select(".box.list.channel").first();
//				// 拿到总页数
//				String pageCountString = catalogDiv.select(".pagination").first().select("span").first().text();
//				int pageCount = Integer.parseInt(pageCountString.split("/")[1].split("\\D")[0]);
////				int pageCount = 3;
//				
//				for (int i = pageCount; i >= 1; i--) {
//					String pageUrl = "";
//					if (i == 1) {
//						pageUrl = catalogUrl+z + ".html";
//					}else {
//						pageUrl = catalogUrl+z + "-" + i + ".html";
//					}
//					Document pageDoc = parseHtml(pageUrl);
//					// 拿到各章节对应的a标签对象
//					Elements chapters = pageDoc.select(".box.list.channel ul li a");
//					
//					List<Element> list = new ArrayList<Element>();
//					// 文章内容
//					for (Element chapter : chapters) {
//						// 标题名
//						String title = chapter.text();
//						if (title.contains(bookName)) {
//							list.add(chapter);
//						}
//					}
//					
//					for (int j = list.size()-1; j >= 0; j--) {
//						// 各章节的url
//						String chapterUrl = preUrl + list.get(j).attr("href");
//						Document chapterDoc = parseHtml(chapterUrl);
//						if (catalogDoc == null) {
//							return;
//						}else {
//							String title = chapterDoc.select(".box.pic_text .page_title").first().text();
//							// 拿到章节内容的节点
//							Element element = chapterDoc.select(".box.pic_text .content .cmcss").first();
//							// 拿到内容文本节点集体
//							List<TextNode> textNodes = element.textNodes();
//							StringBuffer content = new StringBuffer();
//							content.append(title + "\r\n");
//							for (TextNode textNode : textNodes) {
//								// 将各节点文本添加到章节内容里
//								content.append(textNode.text()+"\r\n");
//							}
//							appendToTxt(pathName+"/"+bookName+".txt", content.toString());
//						}
//					}
//				}
//			}
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
//	
//	public static void main(String[] args) throws IOException {
//		String page = "共1847条数据 页次:1/124页";
//		String[] split = page.split("/");
//		String[] split2 = split[1].split("\\D");
//		String string = split2[0];
//		System.out.println(string);
//	}
//}

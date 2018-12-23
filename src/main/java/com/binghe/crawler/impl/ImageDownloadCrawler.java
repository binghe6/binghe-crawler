//package com.binghe.crawler.impl;
//
//import java.io.File;
//import java.util.ArrayList;
//import java.util.List;
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
//public class ImageDownloadCrawler implements Crawler {
//
//	@Autowired
//	private HttpService httpService;
//	
//	@Override
//	public void run() {
//		try {
//			// 图片集链接
////			String pageUrl = findImageCatalogUrl();
//			// 图片要保存的路径名
////			String pathname = "E:/picture/";
////			downloadImage(pageUrl, pathname);
//			List<String> list = listImageCatalogUrl2();
//			String pathname = "E:/picture/";
//			for (String pageUrl : list) {
//				// 图片要保存的路径名
//				downloadImage2(pageUrl, pathname);
//			}
//			
//			System.out.println("结束了===============");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//	/**
//	 * 下载图片
//	 * @param pageUrl
//	 * @param pathname
//	 * @throws Exception
//	 */
//	private void downloadImage(String pageUrl, String pathname)
//			throws Exception {
//		Document pageDoc = parseHtml(pageUrl);
//		Element picBox = pageDoc.select(".box.pic_text").first();
//		// 标题
//		String title = picBox.select(".page_title").first().text();
//		// 图片所在的img标签对象集
//		Elements imgs = picBox.select(".content .cmcss img");
//		for (int i = 0; i < imgs.size(); i++) {
//			// 图片url
//			String url = imgs.get(i).attr("src");
//			httpService.downloadFile(url, new File(pathname+title+"/"+i+".jpg"));
//		}
//	}
//	/**
//	 * 2. 下载图片
//	 * @param pageUrl
//	 * @param pathname
//	 * @throws Exception
//	 */
//	private void downloadImage2(String pageUrl, String pathname)
//			throws Exception {
//		Document pageDoc = parseHtml(pageUrl);
//		Element picBox = pageDoc.select(".w960.center.clear.mt1 .viewbox").first();
//		// 标题
//		String title = picBox.select(".title h2").first().text();
//		// 图片所在的img标签对象集
//		Elements imgs = picBox.select(".content img");
//		for (int i = 0; i < imgs.size(); i++) {
//			// 图片url
//			String url = imgs.get(i).attr("src");
//			try {
//				httpService.downloadFile(url, new File(pathname+title+"/"+i+".jpg"));
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
//	}
//
//	/**
//	 * 根据关键字找图片集所在的链接
//	 * @return
//	 */
//	private String findImageCatalogUrl() {
//		// 目录页面url
//		String preUrl = "";
//		// 关键词1
//		String keyWord1 = "";
//		// 关键词2
//		String keyWord2 = "";
//		// 前缀
//		String pre = "";
//		// url起始页
//		int start = 54;
//		// url末页
//		int end = 54;
//		
//		for (int j = start; j <= end; j++) {
//			String catalogUrl = preUrl + j;
//			// 目录页的Document
//			Document catalogDoc = parseHtml(catalogUrl+".html");
//			
//			if (catalogDoc == null) {
//				return null;
//			}else {
//				// 获取总页数
//				int pageCount = getPageCount(catalogDoc);
//				for (int i = 1; i <= pageCount; i++) {
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
//						if (title.contains(keyWord1) && title.contains(keyWord2)) {
//							return pre + chapter.attr("href");
//						}
//					}
//				}
//			}
//		}
//		return null;
//	}
//	/**
//	 * 根据关键字找图片集所在的链接
//	 * @return
//	 */
//	private List<String> listImageCatalogUrl() {
//		List<String> list = new ArrayList<String>();
//		// 目录页面url
//		String preUrl = "";
//		// 关键词1
//		String keyWord1 = "";
//		// 关键词2
//		String keyWord2 = "";
//		// 前缀
//		String pre = "";
//		// url起始页
//		int start = 57;
//		// url末页
//		int end = 57;
//		
//		for (int j = start; j <= end; j++) {
//			String catalogUrl = preUrl + j;
//			// 目录页的Document
//			Document catalogDoc = parseHtml(catalogUrl+".html");
//			
//			if (catalogDoc == null) {
//				return null;
//			}else {
//				// 获取总页数
//				int pageCount = getPageCount(catalogDoc);
//				for (int i = 1; i <= pageCount; i++) {
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
//						if (title.contains(keyWord1) && title.contains(keyWord2)) {
//							list.add(pre + chapter.attr("href"));
//						}
//					}
//				}
//			}
//		}
//		return list;
//	}
//	/**
//	 * 2. 根据关键字找图片集所在的链接
//	 * @return
//	 */
//	private List<String> listImageCatalogUrl2() {
//		List<String> list = new ArrayList<String>();
//		// 目录页面url
//		String preUrl = "";
//		// 关键词1
//		String keyWord1 = "";
//		// 关键词2
//		String keyWord2 = "";
//		// 前缀
//		String pre = "";
//		// url起始页
//		int start = 12;
//		// url末页
//		int end = 12;
//		
//		for (int j = start; j <= end; j++) {
//			String catalogUrl = preUrl + j;
//			// 目录页的Document
//			Document catalogDoc = parseHtml(catalogUrl+".jsp");
//			
//			if (catalogDoc == null) {
//				return null;
//			}else {
//				// 获取总页数
//				int pageCount = getPageCount2(catalogDoc);
//				for (int i = 1; i <= pageCount; i++) {
//					String pageUrl = "";
//					if (i == 1) {
//						pageUrl = catalogUrl + ".jsp";
//					}else {
//						pageUrl = catalogUrl + "_" + i + ".jsp";
//					}
//					Document pageDoc = parseHtml(pageUrl);
//					// 拿到各章节对应的a标签对象
//					Elements chapters = pageDoc.select(".listbox ul ul li a");
//					
//					// 文章内容
//					for (Element chapter : chapters) {
//						// 标题名
//						String title = chapter.text();
//						if (title.contains(keyWord1) && title.contains(keyWord2)) {
//							list.add(pre + chapter.attr("href"));
//						}
//					}
//				}
//			}
//		}
//		return list;
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
//	private int getPageCount(Document catalogDoc) {
//		// 目录div
//		Element catalogDiv = catalogDoc.select(".box.list.channel").first();
//		String pageCountString = catalogDiv.select(".pagination").first().select("span").first().text();
//		return Integer.parseInt(pageCountString.split("/")[1].split("\\D")[0]);
//	}
//	private int getPageCount2(Document catalogDoc) {
//		// 目录div
//		return Integer.parseInt(catalogDoc.select(".end").get(0).text());
//	}
//	
//	public static void main(String[] args) {
//	}
//}

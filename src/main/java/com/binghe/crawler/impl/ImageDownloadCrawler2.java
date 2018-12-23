package com.binghe.crawler.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.binghe.crawler.Crawler;
import com.binghe.crawler.service.HttpService;

@Service
public class ImageDownloadCrawler2 implements Crawler {

	@Autowired
	private HttpService httpService;
	
	@Override
	public void run() {
		// 漫画页面地址
		String pageUrl = "";
		// 关键字
		String keyWord = "";
		String keyWord2 = "";
		String keyWord3 = "";
		String keyWord4 = "";
		// 前缀url
		String preUrl = "";
		// 图片要保存的地址
		String pathname = "E:/picture/";
		
		Document pageDoc = parseHtml(pageUrl);
		// 获取总页数
		int pageCount = Integer.parseInt(pageDoc.select("#main section .end1").first().attr("href").split("/")[2]);
		// 循环每一页筛选符合条件的漫画地址
		for (int i = 0; i <= pageCount; i++) {
			if (i > 0) {
				pageDoc = parseHtml(pageUrl + i + "/");
			}
			Elements books = pageDoc.select("#main section ul li");
			for (Element book : books) {
				String title = book.select("a div img").last().attr("alt");
				if (title != null && (title.contains(keyWord) || title.contains(keyWord2) 
						|| title.contains(keyWord3) || title.contains(keyWord4))) {
					String baseUrl = preUrl + book.select("a").first().attr("href");
					// 获取图片页地址的标签页
					Document picDoc = parseHtml(baseUrl);
					Elements elements = picDoc.select("#download_form ul li label input");
					// 获取标题(截取40长度)
//					String imgTitle = picDoc.select("#main section div h1").first().text().substring(0, 40).trim();
					// 循环下载图片
					for (int j = 0; j < elements.size(); j++) {
						String imgUrl = elements.get(j).attr("data-url").replace("i3", "m1");
						try {
							if (imgUrl.startsWith("https")) {
								httpService.downloadFile(imgUrl, new File(pathname+title+"/"+j+".jpg"));
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
		
		System.out.println("=========结束===========");
	}

	/**
	 * 循环找出符合条件的漫画url
	 * @param pageUrl
	 * @param keyWord
	 * @param preUrl
	 * @return
	 */
	private List<String> listBaseUrl(String pageUrl, String keyWord, String preUrl) {
		List<String> list = new ArrayList<String>();
		Document pageDoc = parseHtml(pageUrl);
		// 获取总页数
		int pageCount = Integer.parseInt(pageDoc.select("#main section .end1").first().attr("href").split("/")[2]);
		// 循环每一页筛选符合条件的漫画地址
		for (int i = 0; i <= pageCount; i++) {
			if (i > 0) {
				pageDoc = parseHtml(pageUrl + i);
			}
			Elements books = pageDoc.select("#main section ul li a");
			for (Element book : books) {
				String title = book.attr("title");
				if (title.contains(keyWord)) {
					list.add(preUrl + book.attr("href"));
				}
			}
		}
		
		return list;
	}
	
	/**
	 * 获取对应链接的页面Document元素
	 * @param url
	 * @return
	 */
	private Document parseHtml(String url) {
		String html = null;
		try {
			html = this.httpService.doGet(url);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (html != null) {
			return Jsoup.parse(html);
		}
		return null;
	}
}

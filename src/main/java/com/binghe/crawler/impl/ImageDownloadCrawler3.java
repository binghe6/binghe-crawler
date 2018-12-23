package com.binghe.crawler.impl;

import java.io.File;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.binghe.crawler.Crawler;
import com.binghe.crawler.service.HttpService;

@Service
public class ImageDownloadCrawler3 implements Crawler {
	@Autowired
	private HttpService httpService;

	@Override
	public void run() {
		// 图片要保存的地址
		String pathname = "E:/picture/";
		// 图片标题
		String title = "";
		// 前缀
		String preUrl = "";
		// 图片展示页面
		String baseUrl = "";
		Document baseDoc = parseHtml(baseUrl);
		Element ul = baseDoc.select("#yw0 .pager ul").first();
		// 取首页url
		String firstUrl = preUrl + ul.select(".first").first().select("a").attr("href");
		// 获取页数
		String lastUrl = ul.select(".last").first().select("a").attr("href");
		int pageCount = Integer.parseInt(lastUrl.split("=")[1]);
	
		for (int i = 1; i <= pageCount; i++) {
			String picUrl = "";
			if (i==1) {
				picUrl = firstUrl;
			}else {
				picUrl = firstUrl + "?Pages_page=" + i;
			}
			Document picDoc = parseHtml(picUrl);
			// 图片地址
			String imgUrl = picDoc.select("#image").attr("src");
			try {
				httpService.downloadFile(imgUrl, new File(pathname+title+"/"+i+".jpg"));
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		System.out.println("=================结束=================");
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

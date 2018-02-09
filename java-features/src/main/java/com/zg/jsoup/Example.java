package com.zg.jsoup;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Example {

	public static void main(String[] args) throws IOException {
//		String url = "http://www.51job.com";
		String url = "https://www.baidu.com/s?wd=img";
//		Document doc = Jsoup.connect(url).get();
//		doc.getElementsByTag("a").forEach(e -> {
//			System.out.println(e.outerHtml() + "," + e.absUrl("href")); // e.html() includes e.text()
//		});
		findAndDownloadImg(url, Paths.get("D:\\pic"));
//		capturePage(url);
		// doc.getElementsByTag("script").forEach(e->{
		// System.out.println(e);
		// });
	}
	public static void accessAllLinks(String url,Path downloadToPath) throws IOException {
		Document doc = Jsoup.connect(url).get();
		doc.getElementsByTag("a").parallelStream().forEach(e -> {
			System.out.println(e.outerHtml());
			String subUrl = e.absUrl("href");
			if(subUrl != null && !subUrl.isEmpty()) {
				System.out.println(subUrl);
//				try {
//					findAndDownloadImg(url, downloadToPath);
//				} catch (IOException e1) {
//					e1.printStackTrace();
//				}
			}
		});
	}
	
	public static void findAndDownloadImg(String url, Path downloadToPath) throws IOException {
		Random r = new Random();
		Document doc = Jsoup.connect(url).get();
		doc.getElementsByTag("img").parallelStream().forEach(e -> {
			String srcUrl = e.absUrl("src");
			if(srcUrl != null && !srcUrl.isEmpty()) {
				System.out.println(e.outerHtml() + "," + srcUrl);
				String name = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS"))+"_"+r.nextInt(10000);
				if(e.hasAttr("alt") && !e.attr("alt").isEmpty()) {
					name = e.attr("alt")+"_"+name;
				}
				try {
					Response response = Jsoup.connect(srcUrl).ignoreContentType(true).execute();
					Files.copy(response.bodyStream(), Paths.get(downloadToPath.toString(), name + ".jpg"));
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			
		});
		
		doc.getElementsByTag("a").parallelStream().forEach(e -> {
			String subUrl = e.absUrl("href");
			if(subUrl != null && !subUrl.isEmpty()) {
				try {
					findAndDownloadImg(url, downloadToPath);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
	}

	public static void capturePage(String url) throws IOException {

		Document doc = Jsoup.connect(url).get();
		Elements links = doc.select("a[href]");
		Elements media = doc.select("[src]");
		Elements imports = doc.select("link[href]");

		print("\nMedia: (%d)", media.size());
		for (Element src : media) {
			if (src.tagName().equals("img"))
				print(" * %s: <%s> %sx%s (%s)", src.tagName(), src.attr("abs:src"), src.attr("width"),
						src.attr("height"), trim(src.attr("alt"), 20));
			else
				print(" * %s: <%s>", src.tagName(), src.attr("abs:src"));
		}

		print("\nImports: (%d)", imports.size());
		for (Element link : imports) {
			print(" * %s <%s> (%s)", link.tagName(), link.attr("abs:href"), link.attr("rel"));
		}

		print("\nLinks: (%d)", links.size());
		for (Element link : links) {
			print(" * a: <%s>  (%s)", link.attr("abs:href"), trim(link.text(), 35));
		}
	}

	private static void print(String msg, Object... args) {
		System.out.println(String.format(msg, args));
	}

	private static String trim(String s, int width) {
		if (s.length() > width)
			return s.substring(0, width - 1) + ".";
		else
			return s;
	}

}

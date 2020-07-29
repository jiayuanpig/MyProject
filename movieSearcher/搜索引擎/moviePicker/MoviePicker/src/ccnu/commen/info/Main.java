package ccnu.commen.info;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import ccnu.commen.Spider.EditData;
import ccnu.commen.Spider.RetrivePage;
import ccnu.commen.fileIO.SaveFile;

public class Main {

	public static void main(String[] args) {
//		爬取网页信息----------------------------------------------------------------------------------------------
		//1、设置爬取的网页链接
		RetrivePage retrivePage = new RetrivePage();
		//2、以document的形式保存RetrivePage抓取的文件
		Document doc = retrivePage.getDoc();
		
//		将HTML文件保存到本地---------------------------------------------------------------------------------------
		//文件名
		File file = new File("aimFile.html");
		SaveFile saveFile = new SaveFile(file);
		try {//保存
			saveFile.save(doc);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
//		数据库操作----------------------------------------------------------------------------------------------
		//主页面标题
		Elements title = doc.getElementsByTag("title");
		System.out.println(title.get(0).text());
		//新闻部分
		//在此选择 华大新闻:	新闻日期,链接,标题
		Element news = doc.select("[class='infolist']").get(0);
		Elements lis = news.getElementsByTag("li");
		for(Element li:lis) {
			EditData e = new EditData();
//			e.print(li);//打印出要保存的信息
//			e.save(li);//保存到数据库
		}
		
//		创建索引-------------------------------------------------------------------------------------
		
		
		
		
		
		
		
		
		
		
		
	}

}


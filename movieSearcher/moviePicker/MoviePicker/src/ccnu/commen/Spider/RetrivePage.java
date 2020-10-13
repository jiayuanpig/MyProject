package ccnu.commen.Spider;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * 抓取网页
 * @author ADMIN-JY
 *
 */
public class RetrivePage {
	//网页地址
	private String URL;
	private Document doc;
	
	public String getURL() {
		return this.URL;
	}
	public void setURL(String uRL) {
		this.URL = uRL;
	}
	
	public Document getDoc() {
		try {
			//设置10s的连接时间
			this.doc = Jsoup.connect(this.URL).timeout(10000).get();
		}catch (IOException e) {
			System.out.println("网络连接异常!");
			e.printStackTrace();
		}
		return this.doc;
	}

}

package ccnu.commen.Spider;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * ץȡ��ҳ
 * @author ADMIN-JY
 *
 */
public class RetrivePage {
	//��ҳ��ַ
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
			//����10s������ʱ��
			this.doc = Jsoup.connect(this.URL).timeout(10000).get();
		}catch (IOException e) {
			System.out.println("���������쳣!");
			e.printStackTrace();
		}
		return this.doc;
	}

}

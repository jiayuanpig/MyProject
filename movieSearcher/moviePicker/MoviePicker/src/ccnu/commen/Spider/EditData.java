package ccnu.commen.Spider;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import ccnu.entity.Movie;

/**
 * �༭����:
 * 	��ӡHTML��Ϣ
 * 	����Ϣ���浽���ݿ�
 * @author ADMIN-JY
 *
 */
public class EditData {
	
	String sql;
	int i = 1;
	/*
	 * ���浥��ҳ���ӰӰ����Ϣ
	 */
	public void save(String url) {
		ConnectDB connect = new ConnectDB();
		//��ȡ��ҳ
		RetrivePage retrivePage = new RetrivePage();
		retrivePage.setURL(url);
		Document doc = retrivePage.getDoc();
		//������ҳ
		Elements movieList = doc.select("[class='main review-item']");
		for(Element e:movieList) {
			Movie movie = new Movie();
			Element header = e.getElementsByTag("header").get(0);
			movie.setAuthor(header.getElementsByTag("a").get(1).text());
			movie.setDate(header.getElementsByTag("span").get(1).attr("content"));
			Element body = e.select("[class='main-bd']").get(0);
			movie.setTitle(body.getElementsByTag("h2").text());
			movie.setUrl(body.getElementsByTag("h2").get(0).getElementsByTag("a").get(0).attr("href"));
			String up,down;
			up = body.select("[class='action']").get(0).getElementsByTag("a").get(0).text();
			down = body.select("[class='action']").get(0).getElementsByTag("a").get(1).text();
			movie.setUp(Integer.parseInt(up));
			movie.setDown(Integer.parseInt(down));
			
			sql = "insert into movie values('"
					+movie.getId()+"','"
					+movie.getTitle()+"','"
					+movie.getAuthor()+"','"
					+movie.getDate()+"','"
					+movie.getUrl()+"','"
					+movie.getUp()+"','"
					+movie.getDown()+"')";
			
			connect.excuteUpdate(sql);
			i++;
			System.out.println(i);
		}
		connect.closeAll();
	}
	
	/*
	 * �����ҳ����Ϣ
	 */
	public void saveAll() {
		this.save("https://movie.douban.com/review/best/");
		this.save("https://movie.douban.com/review/best/"+"?start=20");
//		this.save("https://movie.douban.com/review/best/"+"?start=40");
	}
	
}	
	
	
	
	
	
	
	
	
	
	
	
	

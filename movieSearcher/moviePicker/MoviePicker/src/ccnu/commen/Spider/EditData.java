package ccnu.commen.Spider;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import ccnu.entity.Movie;

/**
 * 编辑数据:
 * 	打印HTML信息
 * 	将信息保存到数据库
 * @author ADMIN-JY
 *
 */
public class EditData {
	
	String sql;
	int i = 1;
	/*
	 * 保存单个页面电影影评信息
	 */
	public void save(String url) {
		ConnectDB connect = new ConnectDB();
		//爬取网页
		RetrivePage retrivePage = new RetrivePage();
		retrivePage.setURL(url);
		Document doc = retrivePage.getDoc();
		//分析网页
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
	 * 保存多页面信息
	 */
	public void saveAll() {
		this.save("https://movie.douban.com/review/best/");
		this.save("https://movie.douban.com/review/best/"+"?start=20");
//		this.save("https://movie.douban.com/review/best/"+"?start=40");
	}
	
}	
	
	
	
	
	
	
	
	
	
	
	
	


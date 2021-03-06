package ccnu.service;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.FuzzyQuery;
import org.apache.lucene.search.Hits;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.PrefixQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.RangeQuery;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.WildcardQuery;

import ccnu.commen.Spider.ConnectDB;
import ccnu.entity.Movie;

public class MovieService{
	
	
	/**
	 * 简单检索办法
	 * @param txt
	 * @return
	 * @throws CorruptIndexException
	 * @throws IOException
	 * @throws SQLException
	 */
	public List<Movie> SimpleSearch(String txt) throws CorruptIndexException, IOException, SQLException {
		
		List<Movie> movieList = new ArrayList<>();
		// 建立MultiFieldQueryParser		多短语搜索
		String[] fields = { "title", "author" };
		MultiFieldQueryParser mp = new MultiFieldQueryParser(fields, new StandardAnalyzer());
		Query query = null;
		try {		
			query = mp.parse(txt);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		IndexSearcher indexSearcher = new IndexSearcher("\\Users\\ADMIN-JY\\Desktop\\搜索引擎\\moviePicker\\MoviePicker\\index");
		Hits hits = indexSearcher.search(query);
		
		Document doc = null;
		for (int i = 0; i < hits.length(); i++) {
			doc = hits.doc(i);
			movieList.add(findByTitle(doc.get("title")));
		}
		
		return movieList;
	}
	
	/**
	 * 高级检索办法
	 * @param txt
	 * @param author
	 * @param authorType
	 * @param time1
	 * @param time2
	 * @return
	 * @throws CorruptIndexException
	 * @throws IOException
	 * @throws SQLException
	 * @throws ParseException
	 */
	public List<Movie> AdvanceSearch(String txt,String type,String timeDown,String timeUp,String author,String authorType) 
			throws CorruptIndexException, IOException, SQLException, ParseException {
	
		/*
		 * 检索方法:
		 * 1、初始化IndexSearcher searcher
		 * 2、Term t = new Term( 域 , 值)
		 * 3、Query q = new TermQuery(t)
		 * 4、Hits hits = searcher.search(q)
		 * 
		 * Lucene内建的Query对象:
		 * 1、TermQuery 		存在该词条的所有文档
		 * 2、BooleanQuery 	一个由多个子句和子句间的布尔逻辑所组成的查询
		 * 3、RangeQuery		查找一定范围内的文档
		 * 4、PrefixQuery	前缀搜索
		 * 5、PhraseQuery	输入一个以上的关键字，组成短语进行搜索
		 * 6、MultiPhraseQuery 多短语搜索
		 * 7、FuzzyQuery		模糊搜索
		 * 8、WildcardQuery 通配符搜索
		 * */
		
//		System.out.println(txt+type+timeDown+timeUp+author+authorType);
		
		IndexSearcher indexSearcher = new IndexSearcher("\\Users\\ADMIN-JY\\Desktop\\搜索引擎\\moviePicker\\MoviePicker\\index");
		BooleanQuery sumb = new BooleanQuery();// 总布尔查询条
		Query query = null;
		Term term = null;
		//	String txt,String type,String timeDown,String timeUp,String author,String authorType
		//type: 前缀检索1/通配符检索2
		if(!txt.isEmpty()) {
			term = new Term("title", txt);
			if(type.equals("1")) {//前缀
				query = new PrefixQuery(term);
			}else {//通配符
				query = new WildcardQuery(term);
			}
			sumb.add(query, BooleanClause.Occur.MUST);
		}
		
		query = null;
		term = null;
		//范围检索 	date
		if((!timeDown.isEmpty()) && (!timeUp.isEmpty())) {
			Term begintime = new Term("Date", timeDown);
			Term endtime = new Term("Date", timeUp);
			query = new RangeQuery(begintime, endtime, true);
			System.out.println(query.toString());
			sumb.add(query, BooleanClause.Occur.MUST);
		}
		
		query = null;
		term = null;
		//authorType: 精确1/模糊检索2
		if((!author.isEmpty())) {
			term = new Term("author", author);
			if(authorType.equals("1")) {//精确
				query = new TermQuery(term);
			}else {//模糊
				query = new FuzzyQuery(term);
			}
			sumb.add(query, BooleanClause.Occur.MUST);
		}
		
		//返回查询结果
		Hits hits = indexSearcher.search(sumb);
		Document doc = null;
		List<Movie> movieList = new ArrayList<>();
		for (int i = 0; i < hits.length(); i++) {
			doc = hits.doc(i);
			movieList.add(findByTitle(doc.get("title")));
		}
		return movieList;
	}
	
	
	
	public Movie findByTitle(String title) throws SQLException {
		String sql = "select * from movie where title = '"+title+"'";
		ResultSet rs;
		ConnectDB conn = new ConnectDB();
		rs = conn.excuteQuery(sql);
		Movie movie = new Movie();
		while(rs.next()) {
			movie.setId(rs.getString(1));
			movie.setTitle(rs.getString(2));
			movie.setAuthor(rs.getString(3));
			movie.setDate(rs.getString(4));
			movie.setUrl(rs.getString(5));
			movie.setUp(Integer.parseInt(rs.getString(6)));
			movie.setDown(Integer.parseInt(rs.getString(7)));
		}
		conn.closeAll();
		rs.close();
		return movie;
	}
	
	
}

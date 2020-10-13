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
	 * �򵥼����취
	 * @param txt
	 * @return
	 * @throws CorruptIndexException
	 * @throws IOException
	 * @throws SQLException
	 */
	public List<Movie> SimpleSearch(String txt) throws CorruptIndexException, IOException, SQLException {
		
		List<Movie> movieList = new ArrayList<>();
		// ����MultiFieldQueryParser		���������
		String[] fields = { "title", "author" };
		MultiFieldQueryParser mp = new MultiFieldQueryParser(fields, new StandardAnalyzer());
		Query query = null;
		try {		
			query = mp.parse(txt);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		IndexSearcher indexSearcher = new IndexSearcher("\\Users\\ADMIN-JY\\Desktop\\��������\\moviePicker\\MoviePicker\\index");
		Hits hits = indexSearcher.search(query);
		
		Document doc = null;
		for (int i = 0; i < hits.length(); i++) {
			doc = hits.doc(i);
			movieList.add(findByTitle(doc.get("title")));
		}
		
		return movieList;
	}
	
	/**
	 * �߼������취
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
		 * ��������:
		 * 1����ʼ��IndexSearcher searcher
		 * 2��Term t = new Term( �� , ֵ)
		 * 3��Query q = new TermQuery(t)
		 * 4��Hits hits = searcher.search(q)
		 * 
		 * Lucene�ڽ���Query����:
		 * 1��TermQuery 		���ڸô����������ĵ�
		 * 2��BooleanQuery 	һ���ɶ���Ӿ���Ӿ��Ĳ����߼�����ɵĲ�ѯ
		 * 3��RangeQuery		����һ����Χ�ڵ��ĵ�
		 * 4��PrefixQuery	ǰ׺����
		 * 5��PhraseQuery	����һ�����ϵĹؼ��֣���ɶ����������
		 * 6��MultiPhraseQuery ���������
		 * 7��FuzzyQuery		ģ������
		 * 8��WildcardQuery ͨ�������
		 * */
		
//		System.out.println(txt+type+timeDown+timeUp+author+authorType);
		
		IndexSearcher indexSearcher = new IndexSearcher("\\Users\\ADMIN-JY\\Desktop\\��������\\moviePicker\\MoviePicker\\index");
		BooleanQuery sumb = new BooleanQuery();// �ܲ�����ѯ��
		Query query = null;
		Term term = null;
		//	String txt,String type,String timeDown,String timeUp,String author,String authorType
		//type: ǰ׺����1/ͨ�������2
		if(!txt.isEmpty()) {
			term = new Term("title", txt);
			if(type.equals("1")) {//ǰ׺
				query = new PrefixQuery(term);
			}else {//ͨ���
				query = new WildcardQuery(term);
			}
			sumb.add(query, BooleanClause.Occur.MUST);
		}
		
		query = null;
		term = null;
		//��Χ���� 	date
		if((!timeDown.isEmpty()) && (!timeUp.isEmpty())) {
			Term begintime = new Term("Date", timeDown);
			Term endtime = new Term("Date", timeUp);
			query = new RangeQuery(begintime, endtime, true);
			System.out.println(query.toString());
			sumb.add(query, BooleanClause.Occur.MUST);
		}
		
		query = null;
		term = null;
		//authorType: ��ȷ1/ģ������2
		if((!author.isEmpty())) {
			term = new Term("author", author);
			if(authorType.equals("1")) {//��ȷ
				query = new TermQuery(term);
			}else {//ģ��
				query = new FuzzyQuery(term);
			}
			sumb.add(query, BooleanClause.Occur.MUST);
		}
		
		//���ز�ѯ���
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
package ccnu.commen.index;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;

import ccnu.commen.Spider.ConnectDB;

public class CreateIndex {

	//索引保存的地址
	String indexPath = "\\Users\\ADMIN-JY\\Desktop\\搜索引擎\\moviePicker\\MoviePicker\\index";
	
	
	public void createIndex() throws SQLException {
		ResultSet rs = null;
		ConnectDB connect = new ConnectDB();
		try {
			// 索引存放地址,分析器,IndexWriter 不管目录内是否已经有索引了,一律清空,重新建立
			IndexWriter writer = new IndexWriter(indexPath, new StandardAnalyzer(), true);
			//连接数据库,获取结果
			String sql = "select * from movie order by id";
			rs = connect.excuteQuery(sql);
			//便历结果
			while (rs.next()) {
//				System.out.println(rs.getRow());
				Document  doc = new Document();
				//属性：id title author date url up down
				String id = rs.getString("id");
				String title = rs.getString("title");
				String author = rs.getString("author");
				String date = rs.getString("date");
				String url = rs.getString("url");
				String up = Integer.toString(rs.getInt("up"));
				String down = Integer.toString(rs.getInt("down"));
				//若存在空值，保存为空
				if (title == null) {
					title = "null";
				}
				if (author == null) {
					author = "null";
				}
				if (date == null) {
					date = "null";
				}
				if (url == null) {
					url = "null";
				}
				if (up == null) {
					up = "null";
				}
				if (down == null) {
					down = "null";
				}
				//创建索引
				// Index.NO 不需要索引
				// Index.TOKENIZED先被分词再被索引
				// Index.UN_TOKENIZED被索引但不会被分词
				Field idFld = new Field("id", id, Field.Store.YES, Field.Index.NO);
				Field titleFld = new Field("title", title, Field.Store.YES, Field.Index.TOKENIZED);
				Field authorFld = new Field("author", author, Field.Store.YES, Field.Index.TOKENIZED);
				Field dateFld = new Field("date",date, Field.Store.YES, Field.Index.UN_TOKENIZED);
				Field urlFld = new Field("url", url, Field.Store.YES, Field.Index.UN_TOKENIZED);
				Field upFld = new Field("up", up, Field.Store.YES, Field.Index.UN_TOKENIZED);
				Field downFld = new Field("down", down, Field.Store.YES, Field.Index.UN_TOKENIZED);
				//添加到doc中
				doc.add(idFld);
				doc.add(titleFld);
				doc.add(authorFld);
				doc.add(dateFld);
				doc.add(urlFld);
				doc.add(upFld);
				doc.add(downFld);
				writer.addDocument(doc);
			}
			writer.optimize();// 对索引进行优化
			writer.close();// 关闭写入流对象并将索引写入目录
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(rs!=null) {
				rs.close();
			}
			
		}
		connect.closeAll();
	}
	
	public void DeleteIndex() {
		File file = new File(indexPath);  
		if(this.isFull()) {  
			System.out.println("索引为空!");
		} else {
			file.delete();
			System.out.println("索引删除成功!");
		}
	}
	
	public Boolean isFull() {
		Boolean bool = false;
		File file = new File(indexPath);
		File[] listFiles = file.listFiles();
		if(listFiles.length == 0) {  
			bool = true;
		}
		return bool;
	}
}

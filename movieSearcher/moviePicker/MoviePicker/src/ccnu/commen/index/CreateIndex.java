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

	//��������ĵ�ַ
	String indexPath = "\\Users\\ADMIN-JY\\Desktop\\��������\\moviePicker\\MoviePicker\\index";
	
	
	public void createIndex() throws SQLException {
		ResultSet rs = null;
		ConnectDB connect = new ConnectDB();
		try {
			// ������ŵ�ַ,������,IndexWriter ����Ŀ¼���Ƿ��Ѿ���������,һ�����,���½���
			IndexWriter writer = new IndexWriter(indexPath, new StandardAnalyzer(), true);
			//�������ݿ�,��ȡ���
			String sql = "select * from movie order by id";
			rs = connect.excuteQuery(sql);
			//�������
			while (rs.next()) {
//				System.out.println(rs.getRow());
				Document  doc = new Document();
				//���ԣ�id title author date url up down
				String id = rs.getString("id");
				String title = rs.getString("title");
				String author = rs.getString("author");
				String date = rs.getString("date");
				String url = rs.getString("url");
				String up = Integer.toString(rs.getInt("up"));
				String down = Integer.toString(rs.getInt("down"));
				//�����ڿ�ֵ������Ϊ��
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
				//��������
				// Index.NO ����Ҫ����
				// Index.TOKENIZED�ȱ��ִ��ٱ�����
				// Index.UN_TOKENIZED�����������ᱻ�ִ�
				Field idFld = new Field("id", id, Field.Store.YES, Field.Index.NO);
				Field titleFld = new Field("title", title, Field.Store.YES, Field.Index.TOKENIZED);
				Field authorFld = new Field("author", author, Field.Store.YES, Field.Index.TOKENIZED);
				Field dateFld = new Field("date",date, Field.Store.YES, Field.Index.UN_TOKENIZED);
				Field urlFld = new Field("url", url, Field.Store.YES, Field.Index.UN_TOKENIZED);
				Field upFld = new Field("up", up, Field.Store.YES, Field.Index.UN_TOKENIZED);
				Field downFld = new Field("down", down, Field.Store.YES, Field.Index.UN_TOKENIZED);
				//���ӵ�doc��
				doc.add(idFld);
				doc.add(titleFld);
				doc.add(authorFld);
				doc.add(dateFld);
				doc.add(urlFld);
				doc.add(upFld);
				doc.add(downFld);
				writer.addDocument(doc);
			}
			writer.optimize();// �����������Ż�
			writer.close();// �ر�д�������󲢽�����д��Ŀ¼
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
			System.out.println("����Ϊ��!");
		} else {
			file.delete();
			System.out.println("����ɾ���ɹ�!");
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
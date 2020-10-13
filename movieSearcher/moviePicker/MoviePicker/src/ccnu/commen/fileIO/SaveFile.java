package ccnu.commen.fileIO;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import org.jsoup.nodes.Document;

public class SaveFile {
	
	private File f;
	
	public SaveFile(File f) {
		this.f = f;
	}
	
	public File getFile() {
		return this.f;
	}
	
	/**
	 * 将网页保存至本地文件
	 * @throws FileNotFoundException 
	 * @throws IOException 
	 */
	public void save(Document doc) throws FileNotFoundException, IOException {
		FileOutputStream fos = new FileOutputStream(this.f, false);
		OutputStreamWriter osw = new OutputStreamWriter(fos, "utf-8");
		osw.write(doc.html());
		osw.close();
	}
	
		
		
	
}

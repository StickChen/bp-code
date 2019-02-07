package com.shallop.bpc.collection.project.gnote;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.shallop.bpc.collection.utils.DateUtil;

/**
 * 把笔记导入到Wiz里面整理
 * 
 * @author StickChen
 * @date 2016/1/5
 */
public class GNotes {

	@Test
	public void testImport() throws IOException {

		String path = GNotes.class.getClassLoader().getResource("").getPath().toString();
		String gnotesStr = FileUtils.readFileToString(new File(path + "gnotes.json"));
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").create();
		List<GNResult> gnResults = gson.fromJson(gnotesStr, new TypeToken<List<GNResult>>() {
		}.getType());
		StringBuilder sb = new StringBuilder();
		if (gnResults != null && gnResults.size() > 0) {
			for (GNResult gnResult : gnResults) {
				List<Note> notes = gnResult.getNotes();
				if (notes != null && notes.size() > 0) {
					for (Note note : notes) {
						sb.append("====================================================\r\n")
								.append("创建时间：").append(DateUtil.formatDate(note.getCreatedTime())).append(
								"\r\n")
								.append("更新时间：").append(DateUtil.formatDate(note.getModifiedTime())).append(
								"\r\n\r\n")
								.append(note.getContent()).append("\r\n\r\n");
					}
				}
			}
			System.out.println(sb.toString());
		}
	}

}

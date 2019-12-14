package com.shallop.bpc.app;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.regex.Pattern;

/**
*
 * @author StickChen
 * @date 2019/12/14
 */
public class PandocApp {

	private Pattern pattern = Pattern.compile("^#+ .*");

	/** 标题往下一级，方便多个文件合并后的目录结构，用于pandoc合并 */
	@Test
	public void testMarkdown() throws IOException {

//		String path = PandocApp.class.getClassLoader().getResource("D:\\WorkProject\\OpenSource\\CS-Notes\\notes").getPath();
		File folder = new File("D:\\WorkProject\\OpenSource\\CS-Notes\\notes");
		File[] tempList = folder.listFiles();
		for (File file : tempList) {
			if (file.isDirectory()) {
				continue;
			}
			List<String> lines = FileUtils.readLines(file);
			for (int i = 0; i < lines.size(); i++) {
				String line = lines.get(i).trim();
				if (pattern.matcher(line).matches()) {
					lines.set(i, "#" + line);
				}
			}
			lines.add(0, "# " + FilenameUtils.getBaseName(file.getName()));
			FileUtils.writeLines(file, lines);
		}

	}

}

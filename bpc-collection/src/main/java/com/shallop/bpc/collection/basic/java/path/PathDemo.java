package com.shallop.bpc.collection.basic.java.path;

import org.junit.Test;

import java.io.File;
import java.nio.file.Paths;

import static com.shallop.bpc.collection.utils.Printer.pt;

/**
*
 * @author chenxuanlong
 * @date 2017/9/20
 */
public class PathDemo {

	@Test
	public void testPath(){

		pt(PathDemo.class.getResource(""));
		pt(PathDemo.class.getResource("/"));
		pt(PathDemo.class.getClassLoader().getResource(""));
		pt(PathDemo.class.getClassLoader().getResource("/"));
		pt(Thread.currentThread().getContextClassLoader().getResource(""));
		pt(Thread.currentThread().getContextClassLoader().getResource("/"));
		pt(new File("").getAbsolutePath());
		pt(Paths.get("").toAbsolutePath());
		pt(System.getProperty("user.dir"));
	}

	@Test
	public void testPathDemo(){
		String str = null;
		pt(str);
	}
}

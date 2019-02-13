package com.shallop.bpc.collection.basic.regex;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.shallop.bpc.collection.utils.Printer.pt;

/**
 * @author StickChen
 * @date 2016/4/7
 */
public class RegexApp {

	private Pattern ad = Pattern.compile(".*点击订阅面试进阶专栏.*");
	private Pattern br = Pattern.compile("(\\r\\n|\\n){2,}");
	private Pattern toc = Pattern.compile("(?s)<!-- GFM-TOC -->.*<!-- GFM-TOC -->");
	private Pattern toc1 = Pattern.compile("(?s)<!-- MarkdownTOC -->.*<!-- /MarkdownTOC -->");
	private Pattern img = Pattern.compile(".*<img src=\"pics/(.*\\.(png|gif|jpg))\".*");
	private Pattern img1 = Pattern.compile("!\\[(.*)\\]\\((.*/(.*\\.(png|gif|jpg)))\\)");

	@Test
	public void testImg1(){
		String s = "由于这并不是一篇关于持续交付的文章，我们在这里只关注持续交付的几个关键特性。我们希望有尽可能多的信心确保我们的软件正常运行，因此我们进行了大量的**自动化测试**。想让软件达到“晋级”(Promotion)状态从而“推上”流水线，就意味着要在每一个新的环境中，对软件进行**自动化部署**。\n"
		+"许多使用微服务构建的产品或系统都是由具有丰富的持续交付和持续集成经验的团队构建的。以这种方式构建软件的团队广泛使用基础设施自动化技术。如下面显示的构建管道所示。\n" +
				"\n" +
				"![basic-pipeline](/images/basic-pipeline.png)\n" +
				"\n";
		Matcher m = img1.matcher(s);
		m.find();
		pt(m.group(1));
		pt(m.group(2));
		pt(m.group(3));
	}

	@Test
	public void testToc(){
		String content = "[\uD83C\uDF49 点击订阅面试进阶专栏 ](https://xiaozhuanlan.com/CyC2018)\n" +
				"<!-- GFM-TOC -->\n" +
				"\n" +
				"* [一、解决的问题](#一解决的问题)\n" +
				"* [二、与虚拟机的比较](#二与虚拟机的比较)\n" +
				"* [三、优势](#三优势)\n" +
				"* [四、使用场景](#四使用场景)\n" +
				"* [五、镜像与容器](#五镜像与容器)\n" +
				"* [参考资料](#参考资料)\n" +
				"<!-- GFM-TOC -->\n" +
				"\n" +
				"\n" +
				"# 一、解决的问题";
		content = ad.matcher(content).replaceAll("");
		content = toc.matcher(content).replaceAll("");
		content = br.matcher(content).replaceAll("\n");
		pt(content);
	}
	@Test
	public void testImg(){
		String content = "红黑树是 2-3 查找树，但它不需要分别定义 2- 节点和 3- 节点，而是在普通的二叉查找树之上，为节点添加颜色。指向一个节点的链接颜色如果为红色，那么这个节点和上层节点表示的是一个 3- 节点，而黑色则是普通链接。\n" +
				"\n" +
				"<div align=\"center\"> <img src=\"pics/4f48e806-f90b-4c09-a55f-ac0cd641c047.png\" width=\"250\"/> </div><br>\n" +
				"\n" +
				"红黑树具有以下性质：\n" +
				"\n" +
				"- 红链接都为左链接；\n" +
				"- 完美黑色平衡，即任意空链接到根节点的路径上的黑链接数量相同。\n" +
				"\n" +
				"画红黑树时可以将红链接画平。\n" +
				"\n" +
				"<div align=\"center\"> <img src=\"pics/3086c248-b552-499e-b101-9cffe5c2773e.png\" width=\"300\"/> </div><br>";
		Matcher matcher = img.matcher(content);
		StringBuffer sb = new StringBuffer();
		while (matcher.find()) {
			String group = matcher.group(1);
			matcher.appendReplacement(sb, "![](http://img.longxuanme.com/cs-note/" + group +")");
		}
		matcher.appendTail(sb);
		pt(sb);
	}

	@Test
	public void testMarkdownFilter() throws IOException {

//		filterMarkDown(new File("D:\\WorkDocs\\WorkProject\\OpenSource\\CS-Notes\\docs\\notes"));
//		filterMarkDown(new File("D:\\WorkDocs\\WorkProject\\OpenSource\\JavaGuide"));
		filterMarkDown(new File("D:\\WorkDocs\\WorkProject\\OpenSource\\advanced-java\\docs"));
	}

	private void filterMarkDown(File file) throws IOException {
		FileFilter fileFilter = pathname -> pathname.getName().endsWith(".md") || pathname.isDirectory();
		pt(file.getName());
		if (file.isDirectory()) {
			File[] files = file.listFiles(fileFilter);
			for (File f : files) {
				filterMarkDown(f);
			}
		}else {
			String content = FileUtils.readFileToString(file);
			content = ad.matcher(content).replaceAll("");
			content = toc.matcher(content).replaceAll("");
			content = toc1.matcher(content).replaceAll("");

			Matcher matcher = img.matcher(content);
			StringBuffer sb = new StringBuffer();
			while (matcher.find()) {
				String group = matcher.group(1);
				matcher.appendReplacement(sb, "- ![](http://img.longxuanme.com/cs-note/" + group +")");
			}
			matcher.appendTail(sb);
			content = sb.toString();
			sb = new StringBuffer();
			matcher = img1.matcher(content);
			while (matcher.find()) {
				matcher.appendReplacement(sb, "- !["+ matcher.group(1) + "](http://img.longxuanme.com/cs-note/" + matcher.group(3) +")");
			}
			matcher.appendTail(sb);
			content = sb.toString();
			FileUtils.write(file, content);
		}
	}

}

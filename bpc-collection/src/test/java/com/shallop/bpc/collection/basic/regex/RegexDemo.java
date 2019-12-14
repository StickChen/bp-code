package com.shallop.bpc.collection.basic.regex;

import com.alibaba.fastjson.JSON;
import org.junit.Test;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.shallop.bpc.collection.utils.Printer.pt;

/**
 * @author StickChen
 * @date 2016/4/7
 */
public class RegexDemo {

    @Test
    public void testEscape(){

        Pattern compile = Pattern.compile("^[0-9%!=<>,\"' +()-]+$");
        boolean matches = compile.matcher((String) ">='2019-01-18T03:27:24.419Z'").matches();
        pt(matches);

    }

    @Test
    public void testSpilt(){
        String s = "H|201710310231093440992|XXXX00720180709ClaimConfirm.txt";
        String[] split = s.split("\\|");
        pt(Arrays.asList(split));
    }

    @Test
    public void test1(){
        // 按指定模式在字符串查找
        String line = "This order was placed for QT3000! OK?";
        String pattern = "(.*)(\\d+)(.*)";

        // 创建 Pattern 对象
        Pattern r = Pattern.compile(pattern);

        // 现在创建 matcher 对象
        Matcher m = r.matcher(line);
        if (m.find()) {
            System.out.println("Found value: " + m.group(0) );
            System.out.println("Found value: " + m.group(1) );
            System.out.println("Found value: " + m.group(2) );
            System.out.println("Found value: " + m.group(3) );
        } else {
            System.out.println("NO MATCH");
        }
    }

    @Test
    public void test2(){
        Pattern p = Pattern.compile("\\bcat\\b");
        Matcher m = p.matcher("cat cat cat cattie cat"); // 获取 matcher 对象
        int count = 0;

        while (m.find()) {
            count++;
            System.out.println("Match number " + count);
            System.out.println("start(): " + m.start());
            System.out.println("end(): " + m.end());
        }
    }

    @Test
    public void test3(){
        String regex = "foo";
        Pattern pattern = Pattern.compile(regex);
        String input = "fooooooooooooooooo";
        Matcher matcher = pattern.matcher(input);

        System.out.println("Current REGEX is: "+regex);
        System.out.println("Current INPUT is: "+input);

        System.out.println("lookingAt(): "+matcher.lookingAt());
        System.out.println("matches(): "+matcher.matches());
    }

    @Test
    public void testReplace() {
        String regex = "a*b";
        Pattern p = Pattern.compile(regex);
        // 获取 matcher 对象
        String input = "aabfooaabfooabfoob";
        Matcher m = p.matcher(input);
        StringBuffer sb = new StringBuffer();
        while (m.find()) {
            m.appendReplacement(sb, "-\\\\-");
        }
        m.appendTail(sb);
        System.out.println(sb.toString());
    }

    @Test
    public void testOver() {
        // matches()判断字符串是否匹配某个表达式，"."表示任何一个字符
        p("abc".matches("..."));
        // 将字符串"a2389a"中的数字用*替换，\d 表示“0--9”数字
        p("a2389a".replaceAll("\\d", "*"));
        // 将任何是a--z的字符串长度为3的字符串进行编译，这样可以加快匹配速度
        Pattern p = Pattern.compile("[a-z]{3}");
        // 进行匹配，并将匹配结果放在Matcher对象中
        Matcher m = p.matcher("abc");
        p(m.matches());
        // 上面的三行代码可以用下面一行代码代替
        p("abc".matches("[a-z]{3}"));
    }

    public static void p(Object o){
        System.out.println(o);
    }

    @Test
    public void testGreedy(){
        p("# Docker".matches("^#+ .*"));
        // 初步认识. * + ?
        p("a".matches("."));// true
        p("aa".matches("aa"));// true
        p("aaaa".matches("a*"));// true
        p("aaaa".matches("a+"));// true
        p("".matches("a*"));// true
        p("aaaa".matches("a?"));// false
        p("".matches("a?"));// true
        p("a".matches("a?"));// true
        p("1232435463685899".matches("\\d{3,100}"));// true
        p("192.168.0.aaa".matches("\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}"));// false
        p("192".matches("[0-2][0-9][0-9]"));    //true

    }

    @Test
    public void testString() {
        // 范围
        p("a".matches("[abc]"));// true
        p("a".matches("[^abc]"));// false
        p("A".matches("[a-zA-Z]"));// true
        p("A".matches("[a-z]|[A-Z]"));// true
        p("A".matches("[a-z[A-Z]]"));// true
        p("R".matches("[A-Z&&[RFG]]"));// true
    }

    @Test
    public void testPreDefine(){
        //认识\s \w \d \
        p("\n\r\t".matches("\\s(4)"));//false
        p(" ".matches("\\S"));//false
        p("a_8 ".matches("\\w(3)"));//false
        p("abc888&^%".matches("[a-z]{1,3}\\d+[&^#%]+"));//true
        p("\\".matches("\\\\"));//true
    }

    @Test
    public void testBoundary(){
        //边界匹配
        p("hello sir".matches("^h.*"));//true
        p("hello sir".matches(".*ir$"));//true
        p("hello sir".matches("^h[a-z]{1,3}o\\b.*"));//true
        p("hellosir".matches("^h[a-z]{1,3}o\\b.*"));//false
        //空白行:一个或多个(空白并且非换行符)开头，并以换行符结尾
        p(" \n".matches("^[\\s&&[^\\n]]*\\n$"));//true
    }

    @Test
    public void testMatcher() {
        // email
        p("asdsfdfagf@adsdsfd.com".matches("[\\w[.-]]+@[\\w[.-]]+\\.[\\w]+"));// true

        // matches() find() lookingAt()
        Pattern p = Pattern.compile("\\d{3,5}");
        Matcher m = p.matcher("123-34345-234-00");

        // 将整个"123-34345-234-00"用正则表达式引擎查找匹配，当到第一个"-"不匹配了，就停止，
        // 但不会将不匹配的"-"吐出来
        p(m.matches());
        // 将不匹配的"-"吐出来
        m.reset();

        // 1:当前面有p(m.matches());查找子字符串从"...34345-234-00"开始
        // 将会是第1,2两个查到"34345"和"234" 后面2个查不到为false
        // 2:当前面有p(m.matches());和m.reset();查找子字符串从"123-34345-234-00"开始
        // 将为true,true,true,false
        p(m.find());
        p(m.start() + "---" + m.end());
        p(m.find());
        p(m.start() + "---" + m.end());
        p(m.find());
        p(m.start() + "---" + m.end());
        p(m.find());
        // 要是没找到就会报异常java.lang.IllegalStateException
        // p(m.start()+"---"+m.end());

        p(m.lookingAt());
    }

    @Test
    public void testReplace1(){
        //字符串替换
        //Pattern.CASE_INSENSITIVE大小写不敏感
        Pattern p = Pattern.compile("java",Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher("java Java jAva ILoveJavA youHateJAVA adsdsfd");
        //存放字符串
        StringBuffer  buf = new StringBuffer();
        //计数奇偶数
        int i  = 0;
        while(m.find()){
            i++;
            if(i%2 == 0){
                m.appendReplacement(buf, "java");
            }else{
                m.appendReplacement(buf, "JAVA");
            }
        }
        //不加这句话，字符串adsdsfd将会被遗弃
        m.appendTail(buf);
        p(buf); // JAVA java JAVA ILovejava youHateJAVA adsdsfd
    }

    @Test
    public void testGroup(){
        //group分组,用()分组
        Pattern p = Pattern.compile("(\\d{3,5})([a-z]{2})");
        String s = "123aa-34345bb-234cc-00";
        Matcher m = p.matcher(s);
        p(m.groupCount());//2组
        while(m.find()){
            p(m.group());//数字字母都有
//			p(m.group(1));//只有数字
//			p(m.group(2));//只有字母
        }
    }

    @Test
    public void testPattern(){
        Pattern p=Pattern.compile("\\d+");
        String[] str=p.split("我的QQ是:456456我的电话是:0532214我的邮箱是:aaa@aaa.com");
        p(JSON.toJSON(str));

        p(Pattern.matches("\\d+", "2223"));//返回true
        p(Pattern.matches("\\d+", "2223aa"));//返回false,需要匹配到所有字符串才能返回true,这里aa不能匹配到
        p(Pattern.matches("\\d+", "22bb23"));//返回false,需要匹配到所有字符串才能返回true,这里bb不能匹配到

        Pattern p1 = Pattern.compile("\\d+");
        Matcher m=p1.matcher("22bb23");
        m.pattern();//返回p 也就是返回该Matcher对象是由哪个Pattern对象的创建的

    }

    @Test
    public void testMatcher1() {
        Pattern p = Pattern.compile("\\d+");
        Matcher m = p.matcher("22bb23");
        p(m.matches());// 返回false,因为bb不能被\d+匹配,导致整个字符串匹配未成功.
        Matcher m2 = p.matcher("2223");
        p(m2.matches());// 返回true,因为\d+匹配到了整个字符串

        Pattern p1 = Pattern.compile("\\d+");
        Matcher m4 = p1.matcher("22bb23");
        p(m4.lookingAt());// 返回true,因为\d+匹配到了前面的22
        Matcher m3 = p1.matcher("aa2223");
        p(m3.lookingAt());// 返回false,因为\d+不能匹配前面的aa

    }

    @Test
    public void testFind(){
        Pattern p = Pattern.compile("\\d+");
        Matcher m = p.matcher("22bb23");
        p(m.find());// 返回true
        Matcher m2 = p.matcher("aa2223");
        p(m2.find());// 返回true
        Matcher m3 = p.matcher("aa2223bb");
        p(m3.find());// 返回true
        Matcher m4 = p.matcher("aabb");
        p(m4.find());// 返回false
    }

    @Test
    public void testStart() {
        Pattern p = Pattern.compile("\\d+");
        Matcher m = p.matcher("aaa2223bb");
        p(m.find());// true 匹配2223
        p(m.start());// 返回3
        p(m.end());// 返回7,返回的是2223后的索引号
        p(m.group());// 返回2223

        Matcher m2 = p.matcher("2223bb");
        p(m2.lookingAt());   // true 匹配2223
        p(m2.start());   // 返回0,由于lookingAt()只能匹配前面的字符串,所以当使用lookingAt()匹配时,start()方法总是返回0
        p(m2.end());   // 返回4
        p(m2.group());   // 返回2223

        Matcher m3 = p.matcher("2223"); // 如果Matcher m3=p.matcher("2223bb"); 那么下面的方法出错，因为不匹配返回false
        p(m3.matches());   // 匹配整个字符串
        p(m3.start());   // 返回0
        p(m3.end());   // 返回4,原因相信大家也清楚了,因为matches()需要匹配所有字符串
        p(m3.group());   // 返回2223
    }

    @Test
    public void testGroup1(){
        Pattern p = Pattern.compile("([a-z]+)(\\d+)");
        Matcher m = p.matcher("aaa2223bb");
        p(m.find());   // true 匹配aaa2223
        p(m.groupCount());   // 返回2,因为有2组
        p(m.start(1));   // 返回0 返回第一组匹配到的子字符串在字符串中的索引号
        p(m.start(2));   // 返回3
        p(m.end(1));   // 返回3 返回第一组匹配到的子字符串的最后一个字符在字符串中的索引位置.
        p(m.end(2));   // 返回7
        p(m.group(1));   // 返回aaa,返回第一组匹配到的子字符串
        p(m.group(2));   // 返回2223,返回第二组匹配到的子字符串
    }

    @Test
    public void testDigitalDemo() {
        Pattern p = Pattern.compile("\\d+");
        Matcher m = p.matcher("我的QQ是:456456 我的电话是:0532214 我的邮箱是:aaa123@aaa.com");
        while (m.find()) {
            System.out.println(m.group());
            System.out.print("start:"+m.start());
            System.out.println(" end:"+m.end());
        }
    }

    @Test
    public void testAppendReplacement() {
        String string = "1234567";
        Matcher matcher = Pattern.compile("3(4)5").matcher(string);
        if (matcher.find()) {
            System.out.println(matcher.group(1));
            StringBuffer sb = new StringBuffer();
//			matcher.appendReplacement(sb, matcher.group(1) + "0");  // 替换的是整个group()
            matcher.appendReplacement(sb, "$0" + "$1" + "_recycle/"); // 12+345+4+_recycle/

            System.out.println(sb);
            matcher.appendTail(sb); // +67
            System.out.println(sb.toString());
        }
    }

    @Test
    public void testRegex1(){
        String str = "String info = \"==> Parameters: null, 黄玉颜(String), 44200019800521042X(String), Q13610451402(String), jd_768270ef6b36d(String), 1(Byte), null, LO-510bd4bc-fb18-44fd-bde7-13698bfc2bab(String), 1(Byte), 1(Byte), YY-c7788eebb71cd88468a60208bbf14cd0(String), 51(String), 2170000001(String), null, 0(Byte), 1(String), 1(String), 113.73.18.92(String), 2016-10-21 17:01:02.0(Timestamp), 2016-10-21 17:01:12.457(Timestamp), c7788eebb71cd88468a60208bbf14cd0(String), null, 4(String), null, null, null, null, 1299730830719(Long), null, null, 0.0(BigDecimal), null, null, 1(Integer), null\";";


    }

    @Test
    public void testAppendReplacement1(){
        // 生成 Pattern 对象并且编译一个简单的正则表达式"Kelvin"
        Pattern p = Pattern.compile("Kelvin");
        // 用 Pattern 类的 matcher() 方法生成一个 Matcher 对象
        Matcher m = p.matcher("Kelvin Li and Kelvin Chan are both working in " + "Kelvin Chen's KelvinSoftShop company");
        StringBuffer sb = new StringBuffer();
        int i = 0;
        // 使用 find() 方法查找第一个匹配的对象
        boolean result = m.find();
        // 使用循环将句子里所有的 kelvin 找出并替换再将内容加到 sb 里
        while (result) {
            i++;
            m.appendReplacement(sb, "Kevin");
            System.out.println("第" + i + "次匹配后 sb 的内容是：" + sb);
            // 继续查找下一个匹配对象
            result = m.find();
        }
        // 最后调用 appendTail() 方法将最后一次匹配后的剩余字符串加到 sb 里；
        m.appendTail(sb);
        System.out.println("调用 m.appendTail(sb) 后 sb 的最终内容是 :" + sb.toString());
    }

    @Test
    public void testReplaceAll(){
        Pattern p = Pattern.compile("java");
        Matcher m = p.matcher("java java java");
        StringBuffer sb = new StringBuffer();
        if (m.find()) {
            m.appendReplacement(sb, "python");
        }
        System.out.println(sb);

        if (m.find()) {
            m.appendReplacement(sb, "python");
        }
        System.out.println(sb);

        if (m.find()) {
            m.appendReplacement(sb, "python");
        }
        System.out.println(sb);

        System.out.println(m.replaceAll("c++"));
    }

}

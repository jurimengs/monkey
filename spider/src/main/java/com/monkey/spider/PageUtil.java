package com.monkey.spider;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class PageUtil {
    private static final String PAGE_LIST_CLASS = "pagelist"; // 分页组件
    private static final String PAGE_BTN_CLASS = "btn"; // 分页组件
    private static final String PAGE_BTN_ATTR = "href"; // 分页组件
    private static final String IMG_CONTAINER_ID = "lightgallery"; // 图片所在的容器 ID
    private static final String IMG_TAG = "img"; // 图片标签名
    private static final String IMG_ATTR = "src"; // 图片属性
    private static final String TITLE_TAG = "title"; //
    private static final String TITLE_CLASS = "focusbox-title"; // 做文件夹的 CLASS

    private static final String UNDER_LINE = "_";
    private static final String POINT_ = "\\.";
    private static final String POINT = ".";

    public static List<String> parse(Document document, String host) {

        List<Document> docs = PageUtil.findAllDocument(document, host);
        List<String> collect = docs.stream().map(doc -> {
            List<String> parse = parse(doc, IMG_CONTAINER_ID, IMG_TAG, IMG_ATTR);
            return parse;
        }).flatMap(List::stream).collect(Collectors.toList());

        return collect;
    }

    public static List<Document> findAllDocument(Document tmp, String host) {
        // 理论上只有一个元素
        Elements elementsByClass = tmp.getElementsByClass(PAGE_LIST_CLASS);
        Element element = elementsByClass.get(0);
        Elements elementsOfPages = element.getElementsByClass(PAGE_BTN_CLASS);

        List<String> pageUrls = elementsOfPages.stream().map(ele -> {
            String attr = ele.attr(PAGE_BTN_ATTR);
            if(StringUtils.isNotBlank(attr)) {
                return host + attr;
            }
            return null;
        }).distinct().filter(url -> StringUtils.isNotBlank(url)).collect(Collectors.toList());

        String first = pageUrls.get(0);
        String strtmp = StringUtils.substring(first, first.lastIndexOf(UNDER_LINE), first.lastIndexOf(POINT));
        first = StringUtils.remove(first, strtmp);
        String last = pageUrls.get(pageUrls.size() -1);
        int pages = getTotalPage(last);

        List<Document> collect = new ArrayList<>();
        Document documentFirst = getDocument(first);
        collect.add(documentFirst);

        int indexOfPoint = first.lastIndexOf(POINT);
//        String[] split = first.split(POINT_);
        for (int i = 2; i <= pages; i++) {
            StringBuilder tmpstr = new StringBuilder(first);
            tmpstr.insert(indexOfPoint, UNDER_LINE + i);
            String tmpUrl = tmpstr.toString();
            Document tmpDoc = getDocument(tmpUrl);
            collect.add(tmpDoc);
        }

        return collect;
    }

    /**
     *
     * @param last /MM/3/48959_6.html
     * @return
     */
    public static int getTotalPage(String last) {
        String[] split = last.split(UNDER_LINE);
        String split2 = split[1]; // 6.html
        String[] split3 = split2.split(POINT_);
        return Integer.valueOf(split3[0]);
    }

    /**
     *
     * @param document html
     * @param aimId 从目标容器中
     * @param tagName 根据标签名检索
     * @param attrOfTag 返回检索结果的属性
     * @return
     */
    public static List<String> parse(Document document, String aimId, String tagName, String attrOfTag) {
        Element elementOfId = document.getElementById(aimId);
        Elements imgs = elementOfId.getElementsByTag(tagName);

        List<String> res = imgs.stream().map(ele -> {
            String attr = ele.attr(attrOfTag);
            return attr;
        }).collect(Collectors.toList());
        return res;
    }

    public static Document getDocument(String url) {
        try {
            // 使用Jsoup连接到目标网址并获取页面内容
            Document document = Jsoup.connect(url).get();

            // 输出页面的HTML内容
//            log.info(document.html());
            return document;

        } catch (Exception e) {
            log.error("url: {}", url, e);
            return null;
        }
    }

    public static String getPackageName(Document document) {
//        Elements elementsOFTitle = document.getElementsByTag(TITLE_TAG);
//        Element titleEle = elementsOFTitle.get(0);
        // 元素可能有很多， 取第一个就行了
        Elements elementsByClass = document.getElementsByClass(TITLE_CLASS);
        String text = elementsByClass.get(0).text();
        return text;
    }
}

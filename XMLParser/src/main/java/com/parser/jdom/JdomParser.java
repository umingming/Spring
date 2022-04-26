package com.parser.jdom;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jdom2.Comment;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.filter.ElementFilter;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import com.parser.XmlParserApplication;

/*
	XML Parser
	- XML 파일 객체화, 내용 출력, 수정
 */
public class JdomParser {
	private String path;
	private File file;
	private Document doc;
	private XMLOutputter xout;
	private Logger logger;
	
	public JdomParser() throws Exception {
		parse();
	}
	
	public JdomParser(String path) throws Exception {
		this.path = path;
		this.file = new File(path);
		this.logger = LogManager.getLogger(XmlParserApplication.class);
		this.xout = new XMLOutputter();
		parse();
	}
	
	/*
		doc를 xml 파싱 값으로 초기화
	 */
	public void parse() throws Exception {
		if(file.exists()) {
			SAXBuilder builder = new SAXBuilder();
			this.doc = builder.build(path);
			
			logger.info("Success to parse : " + file.getName());
		} else {
			logger.error("Failure to parse : " + file.getName());
		}
	}
	
	/*
		콘솔에 내용 출력
	 */
	public void print() throws Exception {
		Format format = xout.getFormat();
		
		format.setLineSeparator("\r\n");
		format.setIndent("\t");
		format.setTextMode(Format.TextMode.TRIM);
		xout.setFormat(format);
		
		if(doc != null) {
			xout.output(doc, System.out);
			logger.info("Success to print : " + file.getName());
		} else {
			logger.error("Failure to print : " + file.getName());
		}
	}

	/*
		루트 내에서 태그 탐색해 해당하는 요소를 반환
	 */
	public Element navigate(Tag tag) {
		Iterator<Element> iter = doc.getDescendants(new ElementFilter(tag.getTag()));
		
		while(iter.hasNext()) {
			Element descendant = (Element) iter.next();
			
			if(descendant.getAttributeValue("Name").equals(tag.getName())) {
				logger.info("Success to navigate : " + tag.getName());
				return descendant;
			} 
		}
		logger.error("Failure to navigate : " + tag.getName());
		return null;
	}
	
	/*
		요소 내에서 태그 탐색해 해당하는 요소를 반환함.
	 */
	public Element navigate(Element element, Tag tag) {
		if(element != null) {
			Iterator<Element> iter = element.getDescendants(new ElementFilter(tag.getTag()));
			
			while(iter.hasNext()) {
				Element descendant = (Element) iter.next();
				
				if(descendant.getAttributeValue("Name").equals(tag.getName())) {
					logger.info("Success to navigate : " + tag.getName());
					return descendant;
				}
			}
		}
		logger.error("Failure to navigate : " + tag.getName());
		return null;
	}
	
	/*
		루트 내에서 태그를 탐색하고, 반환된 요소에서 또 태그를 탐색해 반환함.
	 */
	public Element navigate(Tag parent, Tag child) {
		Element element = navigate(parent);
		
		if(element != null) {
			Iterator<Element> iter = element.getDescendants(new ElementFilter(child.getTag()));
			
			while(iter.hasNext()) {
				Element descendant = (Element) iter.next();
				
				if(descendant.getAttributeValue("Name").equals(child.getName())) {
					logger.info("Success to navigate : " + child.getName());
					return descendant;
				}
			}
		}
		logger.error("Failure to navigate : " + child.getName());
		return null;
	}

	/*
		태그 탐색 후 해당 요소를 카피함.
	 */
	public Element copy(Element element, Tag tag) {
		Element copy = navigate(element, tag).clone();

		if(copy != null) {
			copy.removeContent();
			
			String newVal = copy.getAttributeValue("Name") + "_APPEND";
			copy.setAttribute("Name", newVal);
			
			logger.info("Success to copy : " + tag.getName());
			return copy;
		}
		logger.error("Failure to copy : " + tag.getName());
		return null;
	}
	
	/*
		부모 태그로 요소 탐색 후 태그를 가진 자식을 생성함.
	 */
	public void createChild(Tag tagParent, Tag tagChild) { //if
		Element parent = navigate(tagParent);
		
		if(parent != null) {
			Element child = new Element(tagChild.getTag())
					.setAttribute("Name", tagChild.getName());
			parent.addContent(child);
			logger.info("Success to create : " + tagChild.getName());
		} else {
			logger.error("Failure to create : " + tagChild.getName());
		}
	}
	
	/*
		부모에 해당 태그 자식을 생성함.
	 */
	public void createChild(Element parent, Tag tagChild) { //if
		if(parent != null) {
			Element child = new Element(tagChild.getTag())
					.setAttribute("Name", tagChild.getName());
			parent.addContent(child);
			logger.info("Success to create : " + tagChild.getName());
		} else {
			logger.error("Failure to create : " + tagChild.getName());
		}
	}
	
	/*
		태그 탐색 후 해당 태그를 원하는 속성으로 수정함.
	 */
	public void modify(Tag tag, String attr, String value) { //if문으로 밸리데이션할 것.
		Element element = navigate(tag);
		
		if(element != null) {
			element.setAttribute(attr, value);
			logger.info("Success to modify : " + tag.getName());
		} else {
			logger.error("Filure to modify : " + tag.getName());
		}
	}
	
	/*
		해당 요소를 원하는 속성 값으로 수정함
	 */
	public void modify(Element element, String attr, String value) {
		if(element.getAttribute(attr) != null) {
			element.setAttribute(attr, value);
			logger.info("Success to modify : " + element.getName());
		} else {
			logger.error("Filure to modify : " + element.getName());
		}
	}

	/*
		부모 요소 내에서 태그 탐색해 원하는 속성으로 수정함.
	 */
	public void modify(Element parent, Tag tag, String attr, String value) {
		Element element = navigate(parent, tag);
		
		if(element != null) {
			element.setAttribute(attr, value);
			logger.info("Success to modify : " + tag.getName());
		} else {
			logger.error("Filure to modify : " + tag.getName());
		}
	}
	
	/*
		부모 요소 내에서 태그 탐색해 주석 처리함.
	 */
	public void toggleComment(Element parent, Tag tag) {
		Element child = navigate(parent, tag);
		
		if(child != null) {
			Comment comment = new Comment(xout.outputString(child));
			parent.setContent(parent.indexOf(child), comment);
			logger.info("Success to toggle : " + tag.getName());
		} else {
			logger.error("Filure to toggle : " + tag.getName());
		}
	}
	
	/*
		태그를 탐색해 주석 처리함.
	 */
	public void toggleComment(Tag tag) {
		Element child = navigate(tag);
		
		if(child != null) {
			Element parent = child.getParentElement();
			Comment comment = new Comment(xout.outputString(child));
			parent.setContent(parent.indexOf(child), comment);
			logger.info("Success to toggle : " + tag.getName());
		} else {
			logger.error("Filure to toggle : " + tag.getName());
		}
	}
	
	/*
		태그 탐색해 삭제함.
	 */
	public void remove(Tag tag) {
		Element element = navigate(tag);
		
		if(element != null) {
			element.getParent().removeContent(element);
			logger.info("Success to delete : " + tag.getName());
		} else {
			logger.error("Filure to delete : " + tag.getName());
		}
	}//if문
	
	/*
		새로운 경로에 저장
	 */
	public void rename(String name) throws Exception {
		if(doc != null) {
			String newFile = String.format("%s\\%s.xml"
											, file.getParent(), name);
			xout.output(doc, new FileOutputStream(newFile));
			logger.info("Success to update : " + file.getName());
		} else {
			logger.error("Failure to update : " + file.getName());
		}
	}
}

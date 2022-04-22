package com.parser.jdom;


import java.io.File;

import org.jdom2.Document;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.XMLOutputter;

public class Test {
	
	public static void main(String[] args) {
		/*
			고민만,,
			-> 잘못된 고민,,,
			가변적인 건 config 파일에서 불러오도록
			얘네가 수정이 되도록
			타협을 어떻게 할 것인가
		 */
		try {
			String xmlPath = "C:\\Aim\\Spring\\JavaTest\\OPC_TagMap_Mill_0.75.xml";
			
			// 파일 경로 확인
			if(new File(xmlPath).exists()) {
				// xml 파일 객체화 후 출력
				
				
				SAXBuilder builder = new SAXBuilder();
				Document doc = null;
				XMLOutputter xout = new XMLOutputter();
				
				xout.output(doc, System.out);
				
				System.out.println("success");
				
				
//				JdomParser parser = new JdomParser("D" + xmlPath);
				
//			    parser.print();
				
				
//				// Item 태그 찾기
//				Element block = parser.navigate("Block", "Name", "CoilRollingDataSendData_M1");
//				Element item = parser.navigate(block, "Item", "Name", "M1_MILL.EQ.CoilRollingDataSendData_M1_RefCurveIndex");
//				
//				// null이 아닌 경우 Item의 Point 속성 변경
//				if(item != null) {
//					item.setAttribute("Point", "10000");//TODO
//				}
//				
//				// Trx 태그 찾기
//				Element trx = parser.navigate("Trx", "Name", "S7_MILL.EQ.Event_TurnTableCoilIDRequest");
//				
//				if(trx != null) {
//					// MultiBock 복사
//					Element multiBlock = parser.copy(trx, "MultiBlock", "Name", "MB_EVENT_REPLY_TURNTABLECOILIDREQUEST");
//					block = parser.copy(trx, "Block", "Name", "S7_MILL.EQ.Event_Reply_TurnTableCoilIDRequest");
//					item = parser.copy(trx, "Item", "Name", "S7_MILL.EQ.Event_Reply_TurnTableCoilIDRequest");
//					
//					// Trx에 MultiBlock 추가
//					trx.addContent(multiBlock);
//					multiBlock.addContent(block);
//					block.addContent(item);
//				}
//				
//				parser.print();
//				
//				// 새로운 경로에 저장
//				String newPath = "C:\\Aim\\OPC_TagMap_MILL_0.78_modify2.xml";
//				parser.update(newPath);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

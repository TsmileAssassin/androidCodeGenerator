package com.tsmile.codeGenerator;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import com.tsmile.codeGenerator.model.ViewEntity;
import com.tsmile.codeGenerator.model.ViewProperty;

/**
 * 解析android xml 文件
 * 
 * @author Tsimle
 * 
 */
public class ViewXMLParser {
    public static ViewEntity parse(String xmlFile) throws Exception {
        BufferedReader in = null;
        XmlParseHandler handler = null;
        try {
            in = new BufferedReader(new InputStreamReader(new FileInputStream(xmlFile)));
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser parser = factory.newSAXParser();
            XMLReader reader = parser.getXMLReader();
            handler = new XmlParseHandler();
            reader.setContentHandler(handler);
            InputSource source = new InputSource(in);
            reader.parse(source);
            return handler.getOutput();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                throw e;
            }
        }
    }

    private static String genNameFromId(String id) {
        String name = null;
        if (null != id && !"".equals(id.trim())) {
            id = id.replace('/', '_').replace(':', '_').replace("?", "_");

            int beginFind = 0;
            while (beginFind >= 0) {
                int findIndex = id.indexOf("_", beginFind);

                if (findIndex >= 0) {
                    String start = id.substring(0, findIndex);
                    String midCap = null;
                    String end = null;

                    if (findIndex == id.length() - 1) {
                        midCap = "";
                        end = "";
                    } else {
                        midCap = String.valueOf(id.charAt(findIndex + 1)).toUpperCase();
                        if (findIndex + 2 > id.length() - 1) {
                            end = "";
                        } else {
                            end = id.substring(findIndex + 2);
                        }
                    }
                    id = new StringBuilder(start).append(midCap).append(end).toString();
                }

                beginFind = findIndex - 1;
                if (findIndex >= 0) {
                    if (beginFind < 0) {
                        beginFind = 0;
                    }
                }
            }

            name = "m" + String.valueOf(id.charAt(0)).toUpperCase() + id.substring(1);
        }
        return name;
    }

    private static class XmlParseHandler extends DefaultHandler {

        private ViewEntity viewEntity;

        private LinkedList<KeyValuePair> elementStacks = new LinkedList<KeyValuePair>();

        public ViewEntity getOutput() {
            return viewEntity;
        }

        @Override
        public void startDocument() throws SAXException {
            super.startDocument();
            viewEntity = new ViewEntity();
        }

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            super.startElement(uri, localName, qName, attributes);
            KeyValuePair kvp = new KeyValuePair();
            kvp.key = qName;
            if (attributes != null && attributes.getLength() > 0) {
                String id = attributes.getValue("android:id");
                if (id != null && !id.isEmpty()) {
                    int idIndex = id.indexOf("id/") + "id/".length();
                    id = id.substring(idIndex);
                    String type = qName;
                    if (type.equalsIgnoreCase("include")) {
                        type = "View";
                    }
                    String name = genNameFromId(id);

                    ViewProperty viewProperty = new ViewProperty(type, name, id);
                    // 有父元素
                    if (elementStacks.size() > 0) {
                        for (int i = elementStacks.size() - 1; i >= 0; i--) {
                            KeyValuePair parent = elementStacks.get(i);
                            if (parent.value != null && parent.value.length() > 0) {
                                viewProperty.setParentname(parent.value);
                            }
                        }
                    }
                    viewEntity.addViewProperty(viewProperty);
                    kvp.value = name;
                }
            }
            elementStacks.add(kvp);
        }

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            super.endElement(uri, localName, qName);
            if (elementStacks.size() > 0) {
                elementStacks.removeLast();
            }
        }

        @Override
        public void endDocument() throws SAXException {
            super.endDocument();
            viewEntity.genImportSet();
        }
    }

    private static class KeyValuePair {
        String key;
        String value;
    }
}

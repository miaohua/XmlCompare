package util;
import org.w3c.dom.DOMConfiguration;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.bootstrap.DOMImplementationRegistry;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSOutput;
import org.w3c.dom.ls.LSSerializer;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;


public class XmlUtils {

    /**
     * @param args
     */
    public static void main(String[] args) {

        byte[] data = outPut("<node><content>test</content><anotherNode/></node>");

        if (data != null) {
            try {
                System.out.println(new String(data, "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

    }

    /**
     * 格式化XML输出.
     * @param xmlStr xml文本内容.
     * @return
     */
    public static byte[] outPut(String xmlStr) {

        byte[] data = null;
        StringReader stringReader = null;

        try {

            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            stringReader =  new StringReader(xmlStr);
            InputSource is = new InputSource(stringReader);
            Document doc = db.parse(is);

            data = outPut(doc);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            if (stringReader != null) {
                stringReader.close();
            }

        }

        return data;
    }

    /**
     * 格式化输出.
     * @param node
     * @return
     */
    public static byte[] outPut(Node node) {

        ByteArrayOutputStream byteArrayOutputStream = null;
        byte[] data = null;

        try {
            DOMImplementationRegistry registry = DOMImplementationRegistry.newInstance();
            DOMImplementationLS impl = (DOMImplementationLS) registry.getDOMImplementation("XML 3.0");
            LSSerializer serializer = impl.createLSSerializer();

            DOMConfiguration domConfiguration = serializer.getDomConfig();
            boolean isSupport = domConfiguration.canSetParameter("format-pretty-print", true);
            if (isSupport) {
                domConfiguration.setParameter("format-pretty-print", true);
            }

            LSOutput output = impl.createLSOutput();
            output.setEncoding("UTF-8");
            byteArrayOutputStream = new ByteArrayOutputStream();
            output.setByteStream(byteArrayOutputStream);
            serializer.write(node, output);
            data = byteArrayOutputStream.toByteArray();

        } catch (Exception e) {

            e.printStackTrace();

        } finally {

            if (byteArrayOutputStream != null) {
                try {
                    byteArrayOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }

        return data;

    }

}
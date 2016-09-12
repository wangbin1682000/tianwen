import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ScanSql2
{
    
    public static void main(String[] args)
    {
        try
        {
            Set<String> cceTableSet = new HashSet<String>();
            File file = new File("D:/ccetable.txt");
            BufferedReader br = new BufferedReader(new FileReader(file));
            String str = null;
            while (null != (str = br.readLine()))
            {
                if (!"".equals(str.trim()))
                {
                    cceTableSet.add(str.trim().toLowerCase());
                }
            }
            br.close();
            String[] dirs = new String[] {"D:/newworkspace/EBagServer/conf/mapping/ds0/framework/model"};
            Map<String, List<String>> zuheMap = new HashMap<String, List<String>>();
            for (String dir : dirs)
            {
                file = new File(dir);
                if (file.exists() && file.isDirectory())
                {
                    for (File sql : file.listFiles())
                    {
                        if (sql.isFile())
                        {
                            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                            
                            try
                            {
                                Document document = dbf.newDocumentBuilder().parse(sql);
                                NodeList list = document.getElementsByTagName("select");
                                if (null == list)
                                {
                                    continue;
                                }
                                int len = list.getLength();
                                for (int i = 0; i < len; i++)
                                {
                                    Set<String> tableSet = new HashSet<String>();
                                    Node actionNode = list.item(i);
                                    NamedNodeMap map = actionNode.getAttributes();
                                    Node resultClassNode = map.getNamedItem("resultClass");
                                    Node idNode = map.getNamedItem("id");
                                    String id = idNode.getNodeValue().trim();
                                    String sqlStr = actionNode.getFirstChild().getNodeValue();
                                    if (null == sqlStr
                                        || (null != resultClassNode && (resultClassNode.getNodeValue()
                                            .toLowerCase()
                                            .contains("integer") || resultClassNode.getNodeValue()
                                            .toLowerCase()
                                            .contains("long"))))
                                    {
                                        continue;
                                    }
                                    sqlStr = sqlStr.replace("\n", " ")
                                        .replace("\r", " ")
                                        .replace("\t", " ")
                                        .replace("(", " ")
                                        .replace(")", " ")
                                        .replace(",", " ")
                                        .replace(".", " ")
                                        .trim()
                                        .toLowerCase();
                                    String[] array = sqlStr.split(" ");
                                    for (String s : array)
                                    {
                                        if ("".equals(s.trim()))
                                        {
                                            continue;
                                        }
                                        if (cceTableSet.contains(s))
                                        {
                                            tableSet.add(s);
                                        }
                                    }
                                    if (tableSet.isEmpty() || tableSet.size() > 3)
                                    {
                                        continue;
                                    }
                                    List<String> tableList = new ArrayList<String>(tableSet);
                                    Collections.sort(tableList);
                                    String zuheKey = tableList.toString();
                                    List<String> sqlList = zuheMap.get(zuheKey);
                                    if (null == sqlList)
                                    {
                                        sqlList = new ArrayList<String>();
                                        zuheMap.put(zuheKey, sqlList);
                                    }
                                    sqlList.add(sql.getName() + "." + id);
                                }
                            }
                            catch (Exception e)
                            {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
            
            List<String> keyList = new ArrayList<String>(zuheMap.keySet());
            Collections.sort(keyList);
            for (String key : keyList)
            {
                if (null == zuheMap.get(key) || zuheMap.get(key).size() < 2)
                {
                    continue;
                }
                System.out.println(key + "---" + zuheMap.get(key).toString());
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}

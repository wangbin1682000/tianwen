import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class ScanSql
{
    public static String getStringInFile(File file)
    {
        if (file.exists())
        {
            BufferedReader br = null;
            try
            {
                br = new BufferedReader(new FileReader(file));
                StringBuffer sb = new StringBuffer();
                boolean fag = false;
                String str = null;
                while (null != (str = br.readLine()))
                {
                    // 添加换行
                    if (fag)
                    {
                        sb.append("/n");
                    }
                    sb.append(str);
                    fag = true;
                }
                return sb.toString();
            }
            catch (Exception e)
            {
            }
            finally
            {
                try
                {
                    if (br != null)
                    {
                        br.close();
                    }
                }
                catch (IOException e)
                {
                }
            }
        }
        return null;
    }
    
    public static void main(String[] args)
    {
        try
        {
            Set<String> tableSet = new HashSet<String>();
            File file = new File("D:/ccetable.txt");
            BufferedReader br = new BufferedReader(new FileReader(file));
            String str = null;
            while (null != (str = br.readLine()))
            {
                if (!"".equals(str.trim()))
                {
                    tableSet.add(str.trim().toLowerCase());
                }
            }
            br.close();
            String[] dirs = new String[] {"D:/newworkspace/commonutil/src/main/resources/mapping/ds0/framework/model",
                "D:/newworkspace/CommonServer/resources/mapping/ds0/framework/model",
                "D:/newworkspace/EBagServer/conf/mapping/ds0/framework/model",
                "D:/newworkspace/ManagerServer/conf/mapping/ds0/framework/model"
                ,"D:/newworkspace/ResourceServer/conf/mapping/ds0/framework/model"};
            Set<String> noneSet = new HashSet<String>();
            for (String dir : dirs)
            {
                file = new File(dir);
                if (file.exists() && file.isDirectory())
                {
                    for (File sql : file.listFiles())
                    {
                        if (sql.isFile())
                        {
                            br = new BufferedReader(new FileReader(sql));
                            while (null != (str = br.readLine()))
                            {
                                if (str.contains("t_"))
                                {
                                    String[] array = str.replace("(", " ")
                                        .replace(")", " ")
                                        .replace(",", " ")
                                        .replace(".", " ")
                                        .split(" ");
                                    for (String s : array)
                                    {
                                        if ("".equals(s.trim()))
                                        {
                                            continue;
                                        }
                                        String t = s.toLowerCase();
                                        if (t.startsWith("t_") && !tableSet.contains(t) && !noneSet.contains(t))
                                        {
                                            System.out.println(t);
                                            noneSet.add(t);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}

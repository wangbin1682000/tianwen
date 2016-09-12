public class Test
{
    public static void main(String[] args)
        throws Exception
    {
        final String name = "haha";
        
        for (int i = 0; i < 10; i++)
        {
            new Thread(new Runnable()
            {
                
                @Override
                public void run()
                {
                    synchronized (name)
                    {
                        try
                        {
                            System.out.println("开始休眠...");
                            Thread.sleep(5000);
                            System.out.println("结束休眠...");
                        }
                        catch (InterruptedException e)
                        {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();
        }
        
        System.out.println(name);
        
    }
}

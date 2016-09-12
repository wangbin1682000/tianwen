public class TestCalc
{
    public static void main(String[] args)
    {
        //partA=INT(((OP*0.9+3)/20)^0.6*20)
        //partB=INT((OP/16)^0.6*16)
        //partC=INT((OP/24)^0.6*24)
        double OP = 3.10816062176165;
        // 19.516086551470956
        //15.392272681452157
        //22.779180985681755
//        18.396768664886974
//        14.125830946497691
//        21.513075138651402
        
//        16.00808752298772
//        11.329050135538825
//        18.817264360394777
        OP = 6.0;
        double result = Math.pow(((OP*0.9+3)/20),0.6) * 20;
        System.out.println(result);
        
        OP = 5.0;
        result = Math.pow(OP/16,0.6) * 16;
        System.out.println(result);
        
        OP = 6.0;
        result = Math.pow(OP/24,0.6) * 24;
        System.out.println(result);
        
    }
}

package Common;


import pojo.IGoogleApI;
import pojo.RetrofitClient;


public class Common {
    public static final String baseURL ="https://maps.googleapis.com";
    public  static IGoogleApI getGoogleApI()
    {
        return RetrofitClient.getClent(baseURL).create(IGoogleApI.class);

        }

}

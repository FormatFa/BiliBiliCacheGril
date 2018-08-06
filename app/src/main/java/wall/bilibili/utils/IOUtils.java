package wall.bilibili.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;

public class IOUtils {


    public static String filesize(long bytelen)
    {

        float resul = 0;
        String dan = null;
        if(bytelen<1024)
        {
            dan="b";
            resul=bytelen;
        }

        else if(bytelen>=1024&&bytelen<1048576)
        {dan="k";

            resul=(bytelen/1024.0f);
        }

        else if(bytelen>=1048576)
        {
            dan="m";
            resul= bytelen/1024.0f/1024.0f;
        }

        DecimalFormat dc=new DecimalFormat("#.00");
        return dc.format(resul)+dan;
    }
    public static String readString(String path)
    {
        try {
            InputStream is = new FileInputStream(path);
            byte[] buff = new byte[is.available()];
            is.read(buff);
            return new String(buff);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public static void writeString(String path,String title)
    {
        try {
            OutputStream os = new FileOutputStream(path);
            os.write(title.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}

package wall.bilibili.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.io.IOException;
import android.graphics.Bitmap;
import android.content.Context;
import android.provider.MediaStore;
import java.io.File;
public class IOUtils {


    public	static String download(String url)
    {

        String result = null;


        HttpURLConnection connection;

        URL Url = null;


        try
        {

            Url =new URL(url);
            connection=(HttpURLConnection)  Url.openConnection();

        }
        catch (Exception e)
        {
            return null;
        }

        try
        {
            InputStream is=	connection.getInputStream();

            byte[] buff = getBytesByInputStream(is);
            result=new String(buff);
            is.close();

        }
        catch (IOException e)
        {}




        return result;
    }


    //从InputStream中读取数据，转换成byte数组，最后关闭InputStream
    public static byte[] getBytesByInputStream(InputStream is) {
        byte[] bytes = null;
        BufferedInputStream bis = new BufferedInputStream(is);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        BufferedOutputStream bos = new BufferedOutputStream(baos);
        byte[] buffer = new byte[1024 * 8];
        int length = 0;
        try {
            while ((length = bis.read(buffer)) > 0) {
                bos.write(buffer, 0, length);
            }
            bos.flush();
            bytes = baos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                bis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return bytes;
    }

    public static void saveImage(Context context,Bitmap bmp, String path) throws Exception
    {

        if(bmp==null)return ;
        File file = new File(path);
        if(file.getParentFile().exists()==false)
            file.getParentFile().mkdirs();

        bmp.compress(Bitmap.CompressFormat.PNG,100, new FileOutputStream(path));
        MediaStore.Images.Media.insertImage(context.getContentResolver(),path,new File(path).getName(),null);


    }
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


    public static void streamCopy(InputStream is , OutputStream os)
    {
        byte[] buff = new byte[1024*4];
        int read = 0;
        try {
            while ((read = is.read(buff)) != -1) {

                os.write(buff, 0, read);


            }
        }   catch(IOException e)
        {
            e.printStackTrace();
        }


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

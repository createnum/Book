package tools;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.Thread.UncaughtExceptionHandler;
import java.util.Date;

import com.wangc.mybook.Constants;

import android.content.Context;
import android.os.Environment;
import android.util.Log;


/**
 * ȫ�ֲ��񵼳������浽���ش�����־����־
 * ·��λ��sdcard/������־Log/myErrorLog�¡�
 */
public class MyCrashHandler implements UncaughtExceptionHandler {


    private static MyCrashHandler instance;

    public static MyCrashHandler getInstance() {
        if (instance == null) {
            instance = new MyCrashHandler();
        }
        return instance;
    }

    public void init(Context ctx) {
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    /**
     * ���ķ�����������crash ��ص��˷����� Throwable�д���������־
     */
    @Override
    public void uncaughtException(Thread arg0, Throwable arg1) {

        String logPath;
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            logPath = Constants.ROOT_ABSOLUTE_PATH
                    + "Log";

            File file = new File(logPath);
            if (!file.exists()) {
                file.mkdirs();
            }
            try {
                FileWriter fw = new FileWriter(logPath + File.separator
                        + "myErrorlog.log", true);
                File f1=new File(logPath + File.separator
                        + "myErrorlog.log");
                if(f1==null){
                	f1.createNewFile();
                }
                fw.write(new Date() + "����ԭ��\n");
                // ������Ϣ
                // ���ﻹ���Լ��ϵ�ǰ��ϵͳ�汾�������ͺ� �ȵ���Ϣ
                StackTraceElement[] stackTrace = arg1.getStackTrace();
                fw.write(arg1.getMessage() + "\n");
                for (int i = 0; i < stackTrace.length; i++) {
                    fw.write("file:" + stackTrace[i].getFileName() + " class:"
                            + stackTrace[i].getClassName() + " method:"
                            + stackTrace[i].getMethodName() + " line:"
                            + stackTrace[i].getLineNumber() + "\n");
                }
                fw.write("\n");
                fw.close();
                // �ϴ�������Ϣ��������
                // uploadToServer();
            } catch (IOException e) {
                Log.e("crash handler", "load file failed...", e.getCause());
            }
        }
        arg1.printStackTrace();
        android.os.Process.killProcess(android.os.Process.myPid());
    }
}
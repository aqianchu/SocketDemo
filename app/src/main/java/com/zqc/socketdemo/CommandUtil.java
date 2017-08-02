package com.zqc.socketdemo;
import android.util.Log;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/***
 * 执行命令行工具类
 *
 */
public class CommandUtil {

    public static final String TAG = CommandUtil.class.getSimpleName();
    public static final String COMMAND_SH = "sh";
    public static final String COMMAND_LINE_END = "\n";
    public static final String COMMAND_EXIT = "exit\n";
    private static final boolean ISDEBUG = true;

    /**
     * 执行单条命令
     *
     * @param command
     * @return
     */
    public static List<String> execute(String command) {
        return execute(new String[] { command });
    }

    /**
     * 可执行多行命令（bat）
     *
     * @param commands
     * @return
     */
    public static List<String> execute(String[] commands) {
        List<String> results = new ArrayList<String>();
        int status = -1;
        if (commands == null || commands.length == 0) {
            return null;
        }
        debug("execute command start : " + commands);
        Process process = null;
        BufferedReader successReader = null;
        BufferedReader errorReader = null;
        StringBuilder errorMsg = null;

        DataOutputStream dos = null;
        try {
            // TODO
            process = Runtime.getRuntime().exec(COMMAND_SH);
            dos = new DataOutputStream(process.getOutputStream());
            for (String command : commands) {
                if (command == null) {
                    continue;
                }
                dos.write(command.getBytes());
                dos.writeBytes(COMMAND_LINE_END);
                dos.flush();
            }
            dos.writeBytes(COMMAND_EXIT);
            dos.flush();

            status = process.waitFor();

            errorMsg = new StringBuilder();
            successReader = new BufferedReader(new InputStreamReader(
                    process.getInputStream()));
            errorReader = new BufferedReader(new InputStreamReader(
                    process.getErrorStream()));
            String lineStr;
            while ((lineStr = successReader.readLine()) != null) {
                results.add(lineStr);
                debug(" command line item : " + lineStr);
            }
            while ((lineStr = errorReader.readLine()) != null) {
                errorMsg.append(lineStr);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (dos != null) {
                    dos.close();
                }
                if (successReader != null) {
                    successReader.close();
                }
                if (errorReader != null) {
                    errorReader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (process != null) {
                process.destroy();
            }
        }
        debug(String.format(Locale.CHINA,
                "execute command end,errorMsg:%s,and status %d: ", errorMsg,
                status));
        return results;
    }

    /**
     * DEBUG LOG
     *
     * @param message
     */
    private static void debug(String message) {
        if (ISDEBUG) {
            Log.d(TAG, message);
        }
    }

}
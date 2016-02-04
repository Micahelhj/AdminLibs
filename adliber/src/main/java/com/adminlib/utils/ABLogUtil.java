/*
 * Copyright (C) 2012 www.amsoft.cn
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.adminlib.utils;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * 类描述：ABLogUtil 日志工具类
 * 创建人：Michael
 * 创建时间：2014/12/15 11:52
 * 修改人：Michael
 * 修改时间：2015/12/15 9:40
 * 修改备注：
 */
public class ABLogUtil {


    /**
     * debug开关.
     */
    public static boolean D = true;

    /**
     * info开关.
     */
    public static boolean I = true;

    /**
     * error开关.
     */
    public static boolean E = true;

    /**
     * 起始执行时间.
     */
    public static long startLogTimeInMillis = 0;

    /**
     * debug开关.
     *
     * @param d 是否开启
     */
    public static void setD(boolean d) {
        D = d;
    }

    /**
     * info开关
     *
     * @param i 是否开启
     */
    public static void setI(boolean i) {
        I = i;
    }

    /**
     * error开关
     *
     * @param e 是否开启
     */
    public static void setE(boolean e) {
        E = e;
    }


    /**
     * 当前文件名 行号 函数名
     *
     * @return toStringBuffer TAG 当前文件名 方法行数 的字符串
     */
    public static String getFileLineMethod() {
        StackTraceElement traceElement = ((new Exception()).getStackTrace())[1];
        StringBuffer toStringBuffer = new StringBuffer("[").append(traceElement.getFileName()).append(" | ").append(traceElement.getLineNumber()).append(" | ").append(traceElement.getMethodName()).append("()").append("]");
        return toStringBuffer.toString();
    }

    /**
     * 当前文件名
     *
     * @return FileName 文件名
     */
    public static String _FILE_() {
        StackTraceElement traceElement = ((new Exception()).getStackTrace())[1];
        return traceElement.getFileName();
    }

    /**
     * 当前方法名
     *
     * @return MethodName 方法名
     */
    public static String _FUNC_() {
        StackTraceElement traceElement = ((new Exception()).getStackTrace())[1];
        return traceElement.getMethodName();
    }


    /**
     * 当前行
     *
     * @return LineNumber 行号
     */
    public static int _LINE_() {
        StackTraceElement traceElement = ((new Exception()).getStackTrace())[1];
        return traceElement.getLineNumber();
    }

    /**
     * 当前时间
     *
     * @return now time
     */
    public static String _TIME_() {
        Date now = new Date(0);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        return sdf.format(now);
    }

    /**
     * 获取标签
     *
     * @return TAG 标签
     */
    private static String getTag() {
        StackTraceElement traceElement = ((new Exception()).getStackTrace())[1];
        StringBuffer toStringBuffer = new StringBuffer("[").append(traceElement.getFileName()).append(" | ").append(traceElement.getLineNumber()).append(" | ").append(traceElement.getMethodName()).append("()").append("]");
        String TAG = toStringBuffer.toString();
        return TAG;
    }

    /**
     * debug日志
     *
     * @param message 打印的信息
     */
    public static void d(String message) {
        if (D) Log.d(getTag(), message);
    }


    /**
     * debug日志
     *
     * @param format the format
     * @param args   the args
     */
    public static void d(String format, Object... args) {
        d(buildMessage(format, args));
    }

    /**
     * info日志
     *
     * @param message 打印的信息
     */
    public static void i(String message) {
        Log.i(getTag(), message);
    }

    /**
     * info日志
     *
     * @param format the format
     * @param args   the args
     */
    public static void i(String format, Object... args) {
        i(buildMessage(format, args));
    }

    /**
     * error日志
     *
     * @param message 打印的信息
     */
    public static void e(String message) {
        Log.e(getTag(), message);
    }


    /**
     * error日志
     *
     * @param format the format
     * @param args   the args
     */
    public static void e(String format, Object... args) {
        e(buildMessage(format, args));
    }

    /**
     * 描述：记录当前时间毫秒.
     */
    public static void prepareLog() {
        Calendar current = Calendar.getInstance();
        startLogTimeInMillis = current.getTimeInMillis();
        Log.d(getTag(), "日志计时开始：" + startLogTimeInMillis);
    }

    /**
     * 描述：打印这次的执行时间毫秒，需要首先调用prepareLog().
     *
     * @param message     描述
     * @param isPrintTime 是否打印时间
     */
    public static void d(String message, boolean isPrintTime) {
        Calendar current = Calendar.getInstance();
        if (isPrintTime)
            Log.d(getTag(), message + ":" + _TIME_() + "ms");
        else
            Log.d(getTag(), message);
    }

    /**
     * debug日志的开关
     *
     * @param d 是否开启
     */
    public static void debug(boolean d) {
        D = d;
    }

    /**
     * info日志的开关
     *
     * @param i 是否开启
     */
    public static void info(boolean i) {
        I = i;
    }

    /**
     * error日志的开关
     *
     * @param e 是否开启
     */
    public static void error(boolean e) {
        E = e;
    }

    /**
     * 设置日志的开关
     *
     * @param d debug日志的开关
     * @param i info日志的开关
     * @param e error日志的开关
     */
    public static void setVerbose(boolean d, boolean i, boolean e) {
        D = d;
        I = i;
        E = e;
    }

    /**
     * 打开所有日志，默认全打开
     */
    public static void openAll() {
        D = true;
        I = true;
        E = true;
    }

    /**
     * 关闭所有日志
     */
    public static void closeAll() {
        D = false;
        I = false;
        E = false;
    }

    /**
     * format日志
     *
     * @param format the format
     * @param args   the args
     * @return
     */
    private static String buildMessage(String format, Object... args) {
        String msg = (args == null) ? format : String.format(Locale.US, format, args);
        StackTraceElement[] trace = new Throwable().fillInStackTrace().getStackTrace();
        String caller = "<unknown>";
        for (int i = 2; i < trace.length; i++) {
            Class<?> clazz = trace[i].getClass();
            if (!clazz.equals(ABLogUtil.class)) {
                String callingClass = trace[i].getClassName();
                callingClass = callingClass.substring(callingClass.lastIndexOf('.') + 1);
                callingClass = callingClass.substring(callingClass.lastIndexOf('$') + 1);
                caller = callingClass + "." + trace[i].getMethodName();
                break;
            }
        }
        return String.format(Locale.US, "[%d] %s: %s",
                Thread.currentThread().getId(), caller, msg);
    }
}

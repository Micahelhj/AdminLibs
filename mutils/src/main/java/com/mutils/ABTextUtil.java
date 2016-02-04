package com.mutils;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ImageSpan;
import android.util.DisplayMetrics;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;


public class ABTextUtil {

    /**
     * 获得字体的缩放密度
     *
     * @param context 上下文
     * @return the scaledDensity
     */
    public static float getScaledDensity(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm.scaledDensity;
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     *
     * @param context 上下文
     * @param dpValue zhe dp
     * @return zhe px
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     *
     * @param context 上下文
     * @param pxValue zhe px
     * @return zhe dip
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 将px值转换为sp值，保证文字大小不变
     *
     * @param context 上下文
     * @param pxValue zhe px
     * @return the sp
     */
    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param context 上下文
     * @param spValue the spValue
     * @return the px
     */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * 获得字体宽
     *
     * @param textSize the textSize
     * @param str      the str
     * @return FontWidth
     */
    public static int getFontWidth(int textSize, String str) {
        Paint mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setTextSize(textSize);
        return getFontWidth(mTextPaint, str);
    }

    /**
     * 获得字体宽
     *
     * @param paint zhe Paint
     * @param str   zhe str
     * @return the width
     */
    public static int getFontWidth(Paint paint, String str) {
        if (str == null || str.equals(""))
            return 0;
        Rect rect = new Rect();
        int length = str.length();
        paint.getTextBounds(str, 0, length, rect);
        return rect.width();
    }

    /**
     * 获得字体高度
     *
     * @param paint the Paint
     * @return the height
     */
    public static int getFontHeight(Paint paint) {
        Rect rect = new Rect();
        paint.getTextBounds("正", 0, 1, rect);
        return rect.height();
    }

    /**
     * 判断是否为空
     *
     * @param collection the Collection
     * @return boolean
     */
    public static boolean isEmpty(Collection collection) {
        return null == collection || collection.isEmpty();
    }

    /**
     * 判断是否为空
     *
     * @param map zhe map
     * @return boolean
     */
    public static boolean isEmpty(Map map) {
        return null == map || map.isEmpty();
    }

    /**
     * 判断是否为空
     *
     * @param objs the obj
     * @return boolean
     */
    public static boolean isEmpty(Object[] objs) {
        return null == objs || objs.length <= 0;
    }

    /**
     * 判断是否为空
     *
     * @param objs zhe objs
     * @return boolean
     */
    public static boolean isEmpty(int[] objs) {
        return null == objs || objs.length <= 0;
    }

    /**
     * 判断是否为空
     *
     * @param charSequence the charSequence
     * @return boolean
     */
    public static boolean isEmpty(CharSequence charSequence) {
        return null == charSequence || charSequence.length() <= 0;
    }

    /**
     * 判断是否为空白
     *
     * @param charSequence the charSequence
     * @return boolean
     */
    public static boolean isBlank(CharSequence charSequence) {
        return null == charSequence || charSequence.toString().trim().length() <= 0;
    }

    /**
     * 去除空格
     *
     * @param charSequence 字符
     * @return String
     */
    public static String trim(CharSequence charSequence) {
        return null == charSequence ? null : charSequence.toString().trim();
    }

    /**
     * 摘取里面第一个不为null的字符串
     *
     * @param options the options
     * @return result
     */
    public static String pickFirstNotNull(CharSequence... options) {
        if (isEmpty(options)) {
            return null;
        }
        String result = null;
        for (CharSequence cs : options) {
            if (null != cs) {
                result = cs.toString();
                break;
            }
        }
        return result;
    }

    /**
     * 摘取里面第一个不为null的字符串
     *
     * @param clazz   the clazz
     * @param options the options
     * @param <T>     the T
     * @return result
     */
    @SafeVarargs
    public static <T> T pickFirstNotNull(Class<T> clazz, T... options) {
        if (isEmpty(options)) {
            return null;
        }
        T result = null;
        for (T obj : options) {
            if (null != obj) {
                result = obj;
                break;
            }
        }
        return result;
    }

    /**
     * 替换文本为图片
     *
     * @param charSequence the CharSequence
     * @param regPattern   the String
     * @param drawable     the  Drawable
     * @return ss (SpannableString)
     */
    public static SpannableString replaceImageSpan(CharSequence charSequence, String regPattern, Drawable drawable) {
        SpannableString ss = charSequence instanceof SpannableString ? (SpannableString) charSequence : new SpannableString(charSequence);
        try {
            ImageSpan is = new ImageSpan(drawable);
            Pattern pattern = Pattern.compile(regPattern);
            Matcher matcher = pattern.matcher(ss);
            while (matcher.find()) {
                String key = matcher.group();
                ss.setSpan(is, matcher.start(), matcher.start() + key.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
            }
        } catch (Exception ex) {
            ABLogUtil.e("" + ex);
        }
        return ss;
    }

    /**
     * 压缩字符串到Zip
     *
     * @param str the str
     * @return 压缩后字符串
     */
    public static String compress(String str) {
        if (str == null || str.length() == 0) {
            return str;
        }
        ByteArrayOutputStream out = null;
        String outStr = "";
        try {
            out = new ByteArrayOutputStream();
            GZIPOutputStream gzip = null;

            gzip = new GZIPOutputStream(out);

            gzip.write(str.getBytes());
            gzip.close();
            outStr = out.toString("ISO-8859-1");
        } catch (IOException e) {
            new Throwable(e.getMessage());
            e.printStackTrace();
        }
        return outStr;
    }

    /**
     * 解压Zip字符串
     *
     * @param str zhe Str
     * @return 解压后字符串
     */
    public static String uncompress(String str)  {
        if (str == null || str.length() == 0) {
            return str;
        }
        ByteArrayInputStream in = null;
        try {
            in = new ByteArrayInputStream(str.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return uncompress(in);
    }

    /**
     * 解压Zip字符串
     *
     * @param inputStream the InputStream
     * @return 解压后字符串
     */
    public static String uncompress(InputStream inputStream) {
        if (inputStream == null) {
            return null;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        GZIPInputStream gunzip = null;
        try {
            gunzip = new GZIPInputStream(inputStream);

            byte[] buffer = new byte[256];
            int n;
            while ((n = gunzip.read(buffer)) >= 0) {
                out.write(buffer, 0, n);
            }
        } catch (IOException e) {
            e.printStackTrace();
            new Throwable(e.getMessage());
        }
        return out.toString();
    }

    /**
     * InputStream convert to string
     *
     * @param in 输入流
     * @return out（String）
     */
    public static String inputStream2String(InputStream in) {
        StringBuffer out = new StringBuffer();
        byte[] b = new byte[4096];
        try {
            for (int n; (n = in.read(b)) != -1; ) {
                out.append(new String(b, 0, n));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return out.toString();
    }

    /**
     * 解压Gzip获取
     *
     * @param is 输入流
     * @return resultSb
     */
    @SuppressWarnings("resource")
    public static String inputStream2StringFromGZIP(InputStream is) {
        StringBuilder resultSb = new StringBuilder();
        BufferedInputStream bis = null;
        InputStreamReader reader = null;
        try {
            bis = new BufferedInputStream(is);
            bis.mark(2);
            // 取前两个字节
            byte[] header = new byte[2];
            int result = bis.read(header);
            // reset输入流到开始位置
            bis.reset();
            // 判断是否是GZIP格式
            int headerData = getShort(header);
            // Gzip流的前两个字节是0x1f8b
            if (result != -1 && headerData == 0x1f8b) {
                is = new GZIPInputStream(bis);
            } else {
                is = bis;
            }
            reader = new InputStreamReader(is, "utf-8");
            char[] data = new char[100];
            int readSize;
            while ((readSize = reader.read(data)) > 0) {
                resultSb.append(data, 0, readSize);
            }
        } catch (Exception e) {
            ABLogUtil.e("" + e);
        } finally {
            ABIOUtil.closeIO(is, bis, reader);
        }
        return resultSb.toString();
    }

    private static int getShort(byte[] data) {
        return (int) ((data[0] << 8) | data[1] & 0xFF);
    }

    /**
     * 半角字符与全角字符混乱所致：这种情况一般就是汉字与数字、英文字母混用
     *
     * @param input 输入流
     * @return : String
     */
    public static String ToDBC(String input) {
        char[] c = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == 12288) {
                c[i] = (char) 32;
                continue;
            }
            if (c[i] > 65280 && c[i] < 65375)
                c[i] = (char) (c[i] - 65248);
        }
        return new String(c);
    }

    public static void setTextValue(TextView textView, String value, String success, String fail) {
        if (!TextUtils.isEmpty(value))
            textView.setText(success + "");
        else
            textView.setText(fail + "");
    }
}

/*package com.dazong.common.web.msg;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.dazong.common.util.BeanJsonUtil;
import com.dazong.common.web.msg.MsgContent.MsgType;
import com.dazong.common.web.php.PHPTokenUtil;
import com.dazong.common.web.util.CommonHttpClientUtil;

public class MessageSendUtilTest {

     //@Test
    public void testHttpRequest() {
        MessageSendUtil sendUtil = new MessageSendUtil();
        MsgContent msgContent = new MsgContent();
        msgContent.setType(MsgType.SMS);
        msgContent.setClient_id("15010062153");
        msgContent.setTemplate_id("8");
        msgContent.addData("sell_company_name", "飞马大宗");
        sendUtil.setMsgUrl("http://notice.dazong.com/notice/add");
        MsgSendResult result = sendUtil.sendMsgv1(msgContent);

        System.out.print(">>>>>>" + result.toString());
    }

    //@Test
    public void testURlEncode() throws Exception {
        String sell_company_name = "{\"order_no\":\"201000025965\",\"sell_company_name\":\"车八八公司\"}";
        System.out.println(string2Unicode(sell_company_name));
    }

    private String string2Unicode(String s) {
        StringBuffer unicode = new StringBuffer();
        for (int i = 0; i < s.length(); i++) {
            // 取出每一个字符
            char chr1 = s.charAt(i);
            if (chr1 >= 19968 && chr1 <= 171941) {
                unicode.append("\\u" + Integer.toHexString(chr1));
            } else {
                unicode.append(chr1);
            }
        }
        return unicode.toString();
    }

    // @Test
    public void testHttpRequest1() {
        MessageSendUtil sendUtil = new MessageSendUtil();
        MsgContent msgContent = new MsgContent();
        msgContent.setType(MsgType.SMS);
        msgContent.setClient_id("15010062153");
        msgContent.setTemplate_id("2");
        msgContent.addData("number", "332211");
        sendUtil.setMsgUrl("http://172.16.10.217:8012/notice/add");
        MsgSendResult result = sendUtil.sendMsg(msgContent);

        System.out.print(">>>>>>" + result.toString());
    }

    

    // @Test
    public void testGet() {
        try {
            MsgContent msgContent = new MsgContent();
            msgContent.setType(MsgType.SMS);
            msgContent.setClient_id("15010062153");
            msgContent.setTemplate_id("2");
            msgContent.addData("number", "332211");
            String postUrl = "https://www.baidu.com/";
            String result = CommonHttpClientUtil.getForString(postUrl,
                    new HashMap<String, Object>());
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
*/
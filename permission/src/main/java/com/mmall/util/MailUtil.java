package com.mmall.util;

import com.google.common.collect.Sets;
import com.mmall.beans.Mail;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

@Slf4j
public class MailUtil {

    public static boolean send(Mail mail) {

        // TODO
        String from = "lubingbin123@163.com";
        int port = 25;
        String host = "smtp.163.com";
        String pass = "lbb57892082";
        String nickname = "lubingbin";

        HtmlEmail email = new HtmlEmail();
        try {
            email.setHostName(host);
            email.setCharset("UTF-8");
            for (String str : mail.getReceivers()) {
                email.addTo(str);
            }
            email.setFrom(from, nickname);
            email.setSmtpPort(port);
            email.setAuthentication(from, pass);
            email.setSubject(mail.getSubject());
            email.setMsg(mail.getMessage());
            email.send();
            log.info("{} 发送邮件到 {}", from, StringUtils.join(mail.getReceivers(), ","));
            return true;
        } catch (EmailException e) {
            log.error(from + "发送邮件到" + StringUtils.join(mail.getReceivers(), ",") + "失败", e);
            return false;
        }
    }

    public static void main(String[] args) {
        Mail mail = new Mail();
        mail.setMessage("1");
        mail.setReceivers(Sets.newHashSet("lubingbin123@163.com"));
        mail.setSubject("1");
        MailUtil.send(mail);
    }
}
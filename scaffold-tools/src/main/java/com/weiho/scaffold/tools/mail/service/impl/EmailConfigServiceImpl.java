package com.weiho.scaffold.tools.mail.service.impl;

import cn.hutool.extra.mail.Mail;
import cn.hutool.extra.mail.MailAccount;
import com.weiho.scaffold.common.exception.BadRequestException;
import com.weiho.scaffold.common.util.des.DesUtils;
import com.weiho.scaffold.common.util.message.I18nMessagesUtils;
import com.weiho.scaffold.mp.service.impl.CommonServiceImpl;
import com.weiho.scaffold.tools.mail.entity.EmailConfig;
import com.weiho.scaffold.tools.mail.entity.convert.EmailConfigVOConvert;
import com.weiho.scaffold.tools.mail.entity.vo.EmailConfigVO;
import com.weiho.scaffold.tools.mail.entity.vo.EmailVO;
import com.weiho.scaffold.tools.mail.mapper.EmailConfigMapper;
import com.weiho.scaffold.tools.mail.service.EmailConfigService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Weiho
 * @since 2022-09-05
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
@RequiredArgsConstructor
public class EmailConfigServiceImpl extends CommonServiceImpl<EmailConfigMapper, EmailConfig> implements EmailConfigService {
    private final EmailConfigVOConvert emailConfigVOConvert;

    @Override
    @Cacheable(value = "Scaffold:Mail", key = "'config'")
    public EmailConfig getConfig() {
        try {
            EmailConfig config = this.list().get(0);
            config.setPass(DesUtils.desDecrypt(config.getPass()));
            return config;
        } catch (Exception e) {
            throw new BadRequestException(I18nMessagesUtils.get("mail.get.error"));
        }
    }

    @Override
    @CachePut(value = "Scaffold:Mail", key = "'config'")
    public void updateEmailConfig(EmailConfigVO newConfig) {
        EmailConfig oldConfig = getConfig();
        try {
            if (!newConfig.getPass().equals(oldConfig.getPass())) {
                newConfig.setPass(DesUtils.desEncrypt(newConfig.getPass()));
            }
        } catch (Exception e) {
            throw new BadRequestException(I18nMessagesUtils.get("mail.update.error"));
        }
        this.save(emailConfigVOConvert.toEntity(newConfig));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void send(EmailVO emailVO, EmailConfigVO config) {
        if (config == null) {
            throw new BadRequestException(I18nMessagesUtils.get("mail.error.tip"));
        }
        // 构建邮件体
        MailAccount account = new MailAccount();
        account.setHost(config.getHost());
        account.setPort(Integer.parseInt(config.getPort()));
        account.setAuth(true);
        account.setPass(config.getPass());
        account.setFrom(config.getUsername() + "<" + config.getFromUser() + ">");
        // ssl方式发送
        account.setSslEnable(true);
        // 使用STARTTLS安全连接
        account.setStarttlsEnable(true);
        String content = emailVO.getContent();
        // 发送
        try {
            int size = emailVO.getTos().size();
            Mail.create(account)
                    .setTos(emailVO.getTos().toArray(new String[size]))
                    .setTitle(emailVO.getSubject())
                    .setContent(content)
                    .setHtml(true)
                    // 关闭Session
                    .setUseGlobalSession(false)
                    .send();
        } catch (Exception e) {
            e.printStackTrace();
//            throw new BadRequestException(I18nMessagesUtils.get("mail.send.error.tip"));
        }
    }
}

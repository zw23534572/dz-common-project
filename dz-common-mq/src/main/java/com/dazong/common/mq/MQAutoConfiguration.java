package com.dazong.common.mq;

import com.dangdang.ddframe.job.lite.spring.job.util.AopTargetUtils;
import com.dazong.common.CommonStatus;
import com.dazong.common.exceptions.PlatformException;
import com.dazong.common.mq.annotation.Subscribe;
import com.dazong.common.mq.core.consumer.IMessageListener;
import com.dazong.common.mq.core.consumer.activemq.ActiveMQConsumer;
import com.dazong.common.mq.dao.mapper.MQMessageMapper;
import com.dazong.common.mq.domian.Consumer;
import com.dazong.common.mq.domian.TableInfo;
import com.dazong.common.mq.manager.DBManager;
import com.dazong.common.mq.manager.MQNotifyManager;
import org.apache.ibatis.io.Resources;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.FatalBeanException;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.ImportResource;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.jms.core.JmsTemplate;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

/**
 * @author huqichao
 * @date 2017-10-30 19:25
 **/
@ImportResource({"/META-INF/dz-common-mq.xml"})
public class MQAutoConfiguration implements ApplicationContextAware {

    private Logger logger = LoggerFactory.getLogger(MQAutoConfiguration.class);

    private static final int SQL_VERSION = 2;

    private static final String TABLE_NAME = "dz_mq_producer";

    private static final String SQL_FILE_NAME = "dz-common-mq.sql";

    @Autowired
    private DBManager dbManager;

    @Autowired
    private MQNotifyManager mqNotifyManager;

    private ApplicationContext context;

    @Autowired
    private MQMessageMapper messageMapper;

    @Autowired
    private JmsTemplate jmsTemplate;

    @Value("${db.name}")
    private String dbName;

    private volatile boolean inited;

    private void init() {
        try {
            TableInfo tableInfo = dbManager.selectTable(dbName, TABLE_NAME);
            String root = dbManager.sqlPath();
            String path;
            if (tableInfo == null) {
                tableInfo = new TableInfo();
                tableInfo.setDbName(dbName);
                tableInfo.setTableName(TABLE_NAME);
                tableInfo.setTableDesc("发送消息本地表-0");
                path = root + File.separator + SQL_FILE_NAME;
                logger.debug("执行数据库脚本: {}", path);
                dbManager.executeSqlFile(Resources.getResourceAsReader(path));
            }
            upgradeDBWithVersion(tableInfo, root);

            addListener();
            new ActiveMQConsumer(jmsTemplate, mqNotifyManager, messageMapper).init();
        } catch (Exception e){
            throw new PlatformException(CommonStatus.MQ_ERROR.joinSystemStatusCode(), e);
        }
    }

    private void upgradeDBWithVersion(TableInfo tableInfo, String root) throws SQLException, IOException {
        int version = tableInfo.getVersion();
        String path;
        if (version < SQL_VERSION){
            for (int i = version + 1; i<=SQL_VERSION; i++){
                path = String.format("%s/%s/dz-common-mq.sql", root, i);
                logger.debug("执行数据库脚本: {}", path);
                dbManager.executeSqlFile(Resources.getResourceAsReader(path), true, tableInfo, i);
            }
        }
    }

    private void addListener(){
        String[] names = context.getBeanNamesForAnnotation(Subscribe.class);
        for (String name : names){
            IMessageListener listener = context.getBean(name, IMessageListener.class);
            Subscribe subscribe = AnnotationUtils.findAnnotation(listener.getClass(), Subscribe.class);
            Object targetBean = AopTargetUtils.getTarget(listener);
            Consumer consumer = Consumer.create(subscribe, targetBean.getClass());
            mqNotifyManager.registerListener(consumer, listener);
        }
    }

    /**
     * Set the ApplicationContext that this object runs in.
     * Normally this call will be used to initialize the object.
     * <p>Invoked after population of normal bean properties but before an init callback such
     * as {@link InitializingBean#afterPropertiesSet()}
     * or a custom init-method. Invoked after {@link ResourceLoaderAware#setResourceLoader},
     * {@link ApplicationEventPublisherAware#setApplicationEventPublisher} and
     * {@link MessageSourceAware}, if applicable.
     *
     * @param applicationContext the ApplicationContext object to be used by this object
     * @throws ApplicationContextException in case of context initialization errors
     * @throws BeansException              if thrown by application context methods
     * @see BeanInitializationException
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (inited){
            logger.warn("mq init repeated");
            return;
        }
        inited = true;
        this.context = applicationContext;
        try {
            init();
        } catch (Exception e) {
            logger.error("mq init fail", e);
            throw new FatalBeanException(e.getMessage());
        }
    }
}

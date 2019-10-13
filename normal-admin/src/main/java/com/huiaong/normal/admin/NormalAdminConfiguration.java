package com.huiaong.normal.admin;

import com.huiaong.normal.admin.receivers.order.config.TradeQueueCreator;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan("com.huiaong.normal.admin")
@Import({
        TradeQueueCreator.class
})
public class NormalAdminConfiguration {
}

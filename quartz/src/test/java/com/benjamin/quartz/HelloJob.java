package com.benjamin.quartz;

import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
public class HelloJob  implements Job {
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss.SSS");
        log.info("{} -- {}, {} -- {}, >>>>>>>>>>>>>>>>>>>>>>> {}",
                context.getJobDetail().getKey().getName(),context.getJobDetail().getKey().getGroup(),
                context.getTrigger().getKey().getName(),context.getTrigger().getKey().getGroup(),
                simpleDateFormat.format(date));
    }
}

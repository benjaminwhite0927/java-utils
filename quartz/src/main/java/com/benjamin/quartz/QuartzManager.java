package com.benjamin.quartz;

import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import static org.quartz.CronScheduleBuilder.cronSchedule;

@Slf4j
public enum QuartzManager {
    /**
     * QuartzManager 单例
     */
    INSTANCE;

    private SchedulerFactory schedulerFactory = new StdSchedulerFactory();
    private static String JOB_GROUP_NAME = "JOB_GROUP";
    private static String TRIGGER_GROUP_NAME = "TRIGGER_GROUP";

    /**
     * 添加一个定时任务，使用默认的任务组名，触发器名，触发器组名
     *
     * @param name              任务名称 / 触发器名
     * @param job               定时任务实现job
     * @param cronExpression    任务时间策略
     */
    public boolean addJob(String name, Class<? extends Job> job, String cronExpression) {
        return addJob(name, JOB_GROUP_NAME,  name, TRIGGER_GROUP_NAME, job, cronExpression);
//        boolean result = false;
//        if (!CronExpression.isValidExpression(cronExpression)) return result;
//
//        try {
//            //设置任务
//            JobDetail jobDetail = JobBuilder.newJob(job).withIdentity(jobName, JOB_GROUP_NAME).build();
//            //设置触发器
//            Trigger trigger = TriggerBuilder.newTrigger()
//                    .forJob(jobDetail)
//                    .withIdentity(jobName, TRIGGER_GROUP_NAME)
//                    .withSchedule(cronSchedule(cronExpression)).build();
//
//            //获取Scheduler，并启动任务
//            Scheduler scheduler = schedulerFactory.getScheduler();
//            scheduler.scheduleJob(jobDetail, trigger);
//            //启动
//            if (!scheduler.isShutdown()) {
//                scheduler.start();
//            }
//            result = true;
//        } catch (Exception e) {
//            log.error(e.getMessage());
//            log.error("Failed to add job QuartzManager!");
//        }
//        return result;
    }

    /**
     * 添加一个定时任务，使用默认的任务组名，触发器名，触发器组名
     *
     * @param name              任务名称 / 触发器名
     * @param job               定时任务实现job
     * @param cronExpression    任务时间策略
     */
    public boolean addJob(String name, String job, String cronExpression) {
        try {
            return addJob(name, JOB_GROUP_NAME, name, TRIGGER_GROUP_NAME, (Class<Job>)Class.forName(job), cronExpression);
        } catch (ClassNotFoundException e) {
            log.error("Failed to found class - {}!", job);
            return false;
        }
    }

    /**
     * 添加一个定时任务
     *
     * @param jobName          任务名
     * @param jobGroupName     任务组名
     * @param triggerName      触发器名
     * @param triggerGroupName 触发器组名
     * @param job              任务
     * @param cronExpression   时间设置，参考quartz说明文档
     */
    public boolean addJob(String jobName, String jobGroupName,
                       String triggerName, String triggerGroupName,
                       Class<? extends Job> job, String cronExpression) {
        boolean result = false;
        if (!CronExpression.isValidExpression(cronExpression)) return result;

        try {
            //设置任务
            JobDetail jobDetail = JobBuilder.newJob(job).withIdentity(jobName, jobGroupName).build();
            //设置触发器
            Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity(triggerName, triggerGroupName)
                    .withSchedule(cronSchedule(cronExpression)).build();
            //获取Scheduler，并启动任务
            Scheduler scheduler = schedulerFactory.getScheduler();
            scheduler.scheduleJob(jobDetail, trigger);
            //启动
            if (!scheduler.isShutdown()) {
                scheduler.start();
            }
            result = true;
        } catch (Exception e) {
            log.error(e.getMessage());
            log.error("Failed to add job QuartzManager!");
        }
        return result;
    }

    /**
     * 添加一个定时任务
     *
     * @param jobName          任务名
     * @param jobGroupName     任务组名
     * @param triggerName      触发器名
     * @param triggerGroupName 触发器组名
     * @param job              任务
     * @param cronExpression   时间设置，参考quartz说明文档
     */
    public boolean addJob(String jobName, String jobGroupName,
                          String triggerName, String triggerGroupName,
                          String job, String cronExpression) {
        try {
            return addJob(jobName, jobGroupName, triggerName, triggerGroupName, (Class<Job>)Class.forName(job), cronExpression);
        } catch (ClassNotFoundException e) {
            log.error("Failed to found class - {}!", job);
            return false;
        }
    }

    /**
     * 修改一个任务的触发时间(使用默认的任务组名，触发器名，触发器组名)
     *
     * @param name              任务名 / 触发器名
     * @param cronExpression    时间参数
     */
    public boolean modifyJobTime(String name, String cronExpression) {
        return modifyJobTime(name, JOB_GROUP_NAME, name, TRIGGER_GROUP_NAME, cronExpression);
//        boolean result = false;
//        if (!CronExpression.isValidExpression(cronExpression)) return result;
//
//        try {
//            Scheduler scheduler = schedulerFactory.getScheduler();
//            TriggerKey triggerKey = TriggerKey.triggerKey(jobName, TRIGGER_GROUP_NAME);
//            Trigger trigger = scheduler.getTrigger(triggerKey);
//            if (null == trigger) {
//                return result;
//            }
//            CronTrigger cronTrigger = (CronTrigger) trigger;
//            String oldTime = cronTrigger.getCronExpression();
//            if (!oldTime.equals(cronExpression)) {
//                // 触发器
//                TriggerBuilder<Trigger> triggerBuilder = TriggerBuilder.newTrigger();
//                // 触发器名,触发器组
//                triggerBuilder.withIdentity(jobName, TRIGGER_GROUP_NAME);
//                // 触发器时间设定
//                triggerBuilder.withSchedule(CronScheduleBuilder.cronSchedule(cronExpression));
//                // 创建Trigger对象
//                trigger = triggerBuilder.build();
//                // 方式一 ：修改一个任务的触发时间
//                scheduler.rescheduleJob(triggerKey, trigger);
//            }
//        } catch (Exception e) {
//
//        }
//        return result;

    }

    /**
     * 修改一个任务的触发时间
     * @param jobName          任务名
     * @param jobGroupName     任务组名
     * @param triggerName      触发器名称
     * @param triggerGroupName 触发器组
     * @param cronExpression   时间参数
     */
    public boolean modifyJobTime(String jobName, String jobGroupName, String triggerName, String triggerGroupName,
                              String cronExpression) {
        boolean result = false;
        if (!CronExpression.isValidExpression(cronExpression)) return result;

        try {
            Scheduler scheduler = schedulerFactory.getScheduler();
            JobKey jobKey = new JobKey(jobName, jobGroupName);
            TriggerKey triggerKey = TriggerKey.triggerKey(triggerName, triggerGroupName);

            if (scheduler.checkExists(jobKey) && scheduler.checkExists(triggerKey)) {
                Trigger trigger = scheduler.getTrigger(triggerKey);
                CronTrigger cronTrigger = (CronTrigger) trigger;
                String oldTime = cronTrigger.getCronExpression();
                if (!oldTime.equals(cronExpression)) {
                    // 触发器
                    TriggerBuilder<Trigger> triggerBuilder = TriggerBuilder.newTrigger();
                    // 触发器名,触发器组
                    triggerBuilder.withIdentity(triggerName, triggerGroupName);
                    // 触发器时间设定
                    triggerBuilder.withSchedule(CronScheduleBuilder.cronSchedule(cronExpression));
                    // 创建Trigger对象
                    trigger = triggerBuilder.build();
                    // 方式一 ：修改一个任务的触发时间
                    scheduler.rescheduleJob(triggerKey, trigger);
                }
                result = true;
            } else {
                log.warn("Failed to update job name - {}, group name - {}, or trigger name - {}, group name - {}: not exists!",
                        jobName, jobGroupName, triggerName, triggerGroupName);
            }
        } catch (Exception e) {
            log.error("Failed to update job name - {}, group name - {}, or trigger name - {}, group name - {}!",
                    jobName, jobGroupName, triggerName, triggerGroupName);
        }
        return result;
    }

    /**
     * 移除一个任务(使用默认的任务组名，触发器名，触发器组名)
     *
     * @param name 任务名称 / 触发器名称
     */
    public boolean removeJob(String name)  {
        return removeJob(name, JOB_GROUP_NAME, name, TRIGGER_GROUP_NAME);
//        try {
//            Scheduler scheduler = schedulerFactory.getScheduler();
//            TriggerKey triggerKey = TriggerKey.triggerKey(jobName, TRIGGER_GROUP_NAME);
//            JobKey jobKey = JobKey.jobKey(jobName, JOB_GROUP_NAME);
//            //停止触发器
//            scheduler.pauseTrigger(triggerKey);
//            //移除触发器
//            scheduler.unscheduleJob(triggerKey);
//            //删除任务
//            scheduler.deleteJob(jobKey);
//        } catch (Exception e) {
//
//        }

    }

    /**
     * 移除一个任务
     *
     * @param jobName          任务名称
     * @param jobGroupName     任务组
     * @param triggerName      触发器名称
     * @param triggerGroupName 触发器组
     */
    public boolean removeJob(String jobName, String jobGroupName,
                          String triggerName, String triggerGroupName) {
        boolean result = false;
        try {
            Scheduler scheduler = schedulerFactory.getScheduler();
            TriggerKey triggerKey = TriggerKey.triggerKey(triggerName, triggerGroupName);
            JobKey jobKey = JobKey.jobKey(jobName, jobGroupName);

            if (scheduler.checkExists(jobKey) && scheduler.checkExists(triggerKey)) {
                //停止触发器
                scheduler.pauseTrigger(triggerKey);
                //移除触发器
                scheduler.unscheduleJob(triggerKey);
                //删除任务
                scheduler.deleteJob(jobKey);
                result = true;
            }
        } catch (Exception e) {
            log.warn("Failed to delete job name - {}, group name - {}, or trigger name - {}, group name - {}: not exists!",
                    jobName, jobGroupName, triggerName, triggerGroupName);
        }
        return result;
    }

    /**
     * 暂停一个任务
     * @param jobName          任务名称
     * @param jobGroupName     任务组
     */
    public void pauseJob(String jobName, String jobGroupName) {
        JobKey jobKey = JobKey.jobKey(jobName, jobGroupName);
        try {
            Scheduler scheduler = schedulerFactory.getScheduler();
            if (scheduler.checkExists(jobKey)) {
                scheduler.pauseJob(jobKey);
            } else {
                log.warn("Failed to pause Job name - {}, group name - {}!", jobName, jobGroupName);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
    /**
     * 暂停一个任务
     * @param jobName          任务名称
     */
    public void pauseJob(String jobName) {
        pauseJob(jobName, JOB_GROUP_NAME);
    }

    /**
     * 重启一个任务
     * @param jobName          任务名称
     * @param jobGroupName     任务组
     */
    public void resumeJob(String jobName, String jobGroupName) {
        JobKey jobKey = JobKey.jobKey(jobName, jobGroupName);
        try {
            Scheduler scheduler = schedulerFactory.getScheduler();
            if (scheduler.checkExists(jobKey)) {
                scheduler.resumeJob(jobKey);
            } else {
                log.warn("Failed to pause Job name - {}, group name - {}!", jobName, jobGroupName);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    /**
     * 重启一个任务
     * @param jobName          任务名称
     */
    public void resumeJob(String jobName) {
        resumeJob(jobName, JOB_GROUP_NAME);
    }


    public void stop() {
        try {
            Scheduler scheduler = schedulerFactory.getScheduler();
            if (!scheduler.isShutdown()) scheduler.shutdown();
        } catch (Exception e) {
            log.error("Failed to shutdown Job - {}", e.getMessage());
        }
    }

    /**
     * 获取定时任务运行状态
     *
     * @param jobName 任务名称
     */
    public Trigger.TriggerState getJobState(String jobName) throws SchedulerException {
        Scheduler scheduler = schedulerFactory.getScheduler();
        TriggerKey triggerKey = TriggerKey.triggerKey(jobName, TRIGGER_GROUP_NAME);
        return scheduler.getTriggerState(triggerKey);
    }

    public Trigger.TriggerState getJobState(String jobName, String triggerGroupName) throws SchedulerException {
        Scheduler scheduler = schedulerFactory.getScheduler();
        TriggerKey triggerKey = TriggerKey.triggerKey(jobName, triggerGroupName);
        return scheduler.getTriggerState(triggerKey);
    }
}

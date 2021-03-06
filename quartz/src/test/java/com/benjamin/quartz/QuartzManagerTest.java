package com.benjamin.quartz;

import org.junit.Test;
import org.quartz.Trigger;
public class QuartzManagerTest {

    @Test
    public void testQuartzManager() {
        try {
            System.out.println("【测试demo】开始");

            QuartzManager.INSTANCE.addJob("任务1", HelloJob.class, "0/1 * * * * ?");
            System.out.println("任务1添加成功，时间参数：0/1 * * * * ?");

            QuartzManager.INSTANCE.addJob("任务2", HelloJob.class, "0/2 * * * * ?");
            System.out.println("任务2添加成功，时间参数：0/2 * * * * ?");

            Trigger.TriggerState state1 = QuartzManager.INSTANCE.getJobState("任务1");
            System.out.println("任务1状态" + state1.toString());

            Trigger.TriggerState state2 = QuartzManager.INSTANCE.getJobState("任务2");
            System.out.println("任务2状态" + state2.toString());

            Thread.sleep(5000);

            QuartzManager.INSTANCE.modifyJobTime("任务2", "0/5 * * * * ?");
            System.out.println("任务2时间参数修改成功，时间参数：0/5 * * * * ?");

            QuartzManager.INSTANCE.removeJob("任务1");
            System.out.println("任务1成功停止");
            state1 = QuartzManager.INSTANCE.getJobState("任务1");
            System.out.println("任务1状态" + state1.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

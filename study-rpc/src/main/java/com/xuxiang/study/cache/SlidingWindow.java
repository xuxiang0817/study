package com.xuxiang.study.cache;

import com.xuxiang.study.thread.MyRunnable;
import com.xuxiang.study.thread.MyThreadExecutor;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * SlidingWindow
 *
 * @author xuxiang
 * @since 2018/8/6
 */
public class SlidingWindow {

    /* ѭ������ */
    private volatile AtomicInteger[] timeSlices;
    /* ���е��ܳ���  */
    private volatile int timeSliceSize;
    /* ÿ��ʱ��Ƭ��ʱ�� */
    private volatile int timeMillisPerSlice;
    /* ���ڳ��� */
    private volatile int windowSize;

    /* ��ǰ��ʹ�õ�ʱ��Ƭλ�� */
    private AtomicInteger cursor = new AtomicInteger(0);

    public SlidingWindow(int timeMillisPerSlice, int windowSize) {
        this.timeMillisPerSlice = timeMillisPerSlice;
        this.windowSize = windowSize;
        // ��֤�洢����������window
        this.timeSliceSize = windowSize * 2 + 1;
    }

    /**
     * ��ʼ�����У����ڴ˳�ʼ��������һЩ���ݿռ䣬Ϊ�˽�ʡ�ռ䣬�ӳٳ�ʼ��
     */
    private void initTimeSlices() {
        if (timeSlices != null) {
            return;
        }
        // �ڶ��̵߳�����£�����ֶ�γ�ʼ���������û��ϵ
        // ����ֻ��Ҫ��֤����ȡ����ֵһ����һ���ȶ��ģ���������ʹ���ȳ�ʼ�������ֵ�ķ���
        AtomicInteger[] localTimeSlices = new AtomicInteger[timeSliceSize];
        for (int i = 0; i < timeSliceSize; i++) {
            localTimeSlices[i] = new AtomicInteger(0);
        }
        timeSlices = localTimeSlices;
    }

    private int locationIndex() {
        long time = System.currentTimeMillis();
        return (int) ((time / timeMillisPerSlice) % timeSliceSize);
    }

    /**
     * <p>��ʱ��Ƭ����+1�������ش��������еļ����ܺ�
     * <p>�÷���ֻҪ���þ�һ�����ĳ��ʱ��Ƭ����+1
     *
     * @return
     */
    public int incrementAndSum() {
        initTimeSlices();
        int index = locationIndex();
        int sum = 0;
        // cursor����index������true
        // cursor������index������false�����Ὣcursor����Ϊindex
        int oldCursor = cursor.getAndSet(index);
        if (oldCursor == index) {
            // �ڵ�ǰʱ��Ƭ�����+1
            sum += timeSlices[index].incrementAndGet();
        } else {
            // ����������thread�Ѿ��ù�1�����ⲻ��
            timeSlices[index].set(1);
            // ���㣬����������ʱ����ʱ��Ƭ��Ծ�����
            clearBetween(oldCursor, index);
            // sum += 0;
        }
        for (int i = 1; i < windowSize; i++) {
            sum += timeSlices[(index - i + timeSliceSize) % timeSliceSize].get();
        }
        return sum;
    }

    /**
     * �ж��Ƿ�������з��ʣ�δ������ֵ�Ļ��Ż��ĳ��ʱ��Ƭ+1
     *
     * @param threshold
     * @return
     */
    public boolean allow(int threshold) {
        initTimeSlices();
        int index = locationIndex();
        int sum = 0;
        // cursor������index����cursor����Ϊindex
        int oldCursor = cursor.getAndSet(index);
        if (oldCursor != index) {
            // ����������thread�Ѿ��ù�1�����ⲻ��
            timeSlices[index].set(0);
            // ���㣬����������ʱ����ʱ��Ƭ��Ծ�����
            clearBetween(oldCursor, index);
        }
        for (int i = 1; i < windowSize; i++) {
            sum += timeSlices[(index - i + timeSliceSize) % timeSliceSize].get();
        }

        System.out.println(Thread.currentThread().getName() + "__" + sum + "__" + threshold);
        // ��ֵ�ж�
        if (sum <= threshold) {
            // δ������ֵ��+1
            sum += timeSlices[index].incrementAndGet();
            return true;
        }
        return false;
    }

    /**
     * <p>��fromIndex~toIndex֮���ʱ��Ƭ����������
     * <p>��������£���ѭ�������Ѿ����˳���1��timeSliceSize���ϣ���������㲢�����������Ľ���
     *
     * @param fromIndex ������
     * @param toIndex   ������
     */
    private void clearBetween(int fromIndex, int toIndex) {
        for (int index = (fromIndex + 1) % timeSliceSize; index != toIndex; index = (index + 1) % timeSliceSize) {
            timeSlices[index].set(0);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        SlidingWindow window = new SlidingWindow(100, 10);

        for(int i = 0; i < 10; i++){
            MyThreadExecutor.execute(new MyRunnable() {
                                         @Override
                                         public void runBiz() {
                                             for (int i = 0; i < 10; i++) {
//                                            if(!window.allow(1)){
//                                                System.out.println("yes");
//                                            }
                                                 try {
                                                     Thread.sleep(10);
                                                 } catch (InterruptedException e) {
                                                     e.printStackTrace();
                                                 }
//                                                 SlidingWindow window = cache.get(1);
//                                                 System.out.println(window.allow(1));
                                             }
                                         }
                                     }
            );
        }
    }
}

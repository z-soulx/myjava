package cache;

import com.google.common.cache.*;
import com.google.common.util.concurrent.ListenableFuture;
import org.junit.Test;

import java.util.Date;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * Guava demo.
 *
 * @author skywalker
 */
public class CacheDemo {

    @Test
    public void cacheLoader() throws ExecutionException {
        LoadingCache<String, String> cache = CacheBuilder.newBuilder().maximumSize(2)
                .build(new CacheLoader<String, String>() {
                    @Override
                    public String load(String s) throws Exception {
                        return "Hello: " + s;
                    }
                });
//        System.out.println(cache.get("China"));
        cache.put("US", "US");
        cache.put("US1", "US1");
        System.out.println(cache.get("US"));
        System.out.println(cache.get("US1"));
        System.out.println(cache.get("US"));
        cache.put("US", "US2");
        //放不进去
//        cache.put("UK", "UK");
    }


    @Test
    public void cacheUse(){
        LoadingCache<String, String> cache = CacheBuilder.newBuilder()
                // 当cache中元素个数超过100时，则使用LRU进行内存回收
                .maximumSize(100)
                // 设置cache基于比重大小来清理，这个和maximumSize不可同时使用！！！
                // .maximumWeight(1000)
                // 如果使用基于比重的清理策略，最好自己实现weigher，来指定某些k/v占的比重
//                .weigher(new Weigher<String, String>() {
//                    @Override
//                    public int weigh(String key, String value) {
//                        return 1;
//                    }
//                })
                // 缓存项在给定时间内没有被进行写操作，则回收这个数据项占用内存
                .expireAfterWrite(5, TimeUnit.SECONDS)
                // 缓存项在给定时间内没有被访问（读/写操作），则回收这个数据项占用内存
                .expireAfterAccess(10,TimeUnit.SECONDS)
        //缓存项在给的时间内没有进行写操作（创建/更新值），则刷新缓存， 调用reload()去重新加载数据
                .refreshAfterWrite(15, TimeUnit.SECONDS)
                // 删除监听器，当缓存被删除时，会触发onRemoval()方法
                // RemovalNotification是删除通知，其中包含移除原因[RemovalCause]、键和值。
                .removalListener(new RemovalListener<String, String>() {
                    @Override
                    public void onRemoval(RemovalNotification<String, String> notification) {
                        System.out.println("onRemoval execute: key="+notification.getKey()+",value="+notification.getValue()+" was deleted，cause="+notification.getCause().name());
                    }
                })

                /**
                 * recordStats用来开启Guava Cache的统计功能，用于统计缓存命中率、命中次数等值。
                 * 统计打开后，使用Cache.stats()方法会返回CacheStats对象以提供如下统计信息：
                 * hitRate()：缓存命中率；
                 * averageLoadPenalty()：加载新值的平均时间，单位为纳秒；
                 * evictionCount()：缓存项被回收的总数，不包括显式清除。
                 */
                .recordStats()
                .build(
                        // CacheLoader用于处理load, reload等逻辑
                        new CacheLoader<String, String>() {
                            public String load(String key)  {
                                System.out.println("load .......");
                                return key + new Date().toString();
                            }

                            //重载CacheLoader.reload(K, V)可以扩展刷新时（调用Cache#refresh()方法时）的行为，
                            // 这个方法允许在获取新值时返回旧的值。
                            @Override
                            public ListenableFuture<String> reload(String key, String oldValue) throws Exception {
                                System.out.println("reload cache:  key="+key+",oldValue="+oldValue);
                                return super.reload(key, oldValue);
                            }
                        });

    }
}

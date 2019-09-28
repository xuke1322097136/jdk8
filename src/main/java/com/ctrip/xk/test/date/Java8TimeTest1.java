package com.ctrip.xk.test.date;

import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.Set;
import java.util.TreeSet;

import static java.util.Collections.addAll;
import static java.util.Collections.sort;

/**
 * Created by xuke
 * Description:
 * Date: 2019-09-07
 * Time: 11:39
 *
 *    关于日期和时间：
 *    1. 格林威治标准时间：全球时间的基准时间，其他时区的时间可以根据格林威治标准时间不断地加时间（比格林威治标准时间更快）
 *                        或者减时间（比格林威治标准时间更慢）；
 *    2. UTC时间：不带时区的标准时间，形如2019-09-07T11:40:59.345Z（不带时区）或者2019-09-07T11:40:59.345+08:00（带时区），
 *                Mongodb实际上对Date存储使用的就是UTC时间；
 *     3. ISO-8601：LocalDate/LocalTime/LocalDateTime采用的就是无时区的ISO-8601日历系统。
 *格林威治时间、CET,UTC,GMT,CST几种常见时间的含义和关系？？等待调研
 *   标准UTC时间：2019-09-07T11:40:59.345Z（具体的格式为：年-月-日T:时:分:秒.毫秒Z，Z表示的是没有时区）
 *   如果有时区的话，我们可以在后面去掉大写的Z，然后加上 +08:00(表示的是东8区的时间)，即2019-09-07T11:40:59.345+08:00
 *
 *   Java8中所有的日期和时间类都是不可变的类，并且一定是可以确保是线程安全的。Java8以前的Date/Calendar/SimpleDateFormat都是
 *   可变的，因此都是非线程安全的。
 *
 *   Java8日期时间的API基本上就是借鉴的Joda Time(https://www.joda.org/joda-time/)，所以取的类名什么的基本上都是一样的。
 */
public class Java8TimeTest1 {
    public static void main(String[] args) {
        // LocalDate表示的是当前时区的时间
        LocalDate localDate1 = LocalDate.now();
        System.out.println(localDate1);

        // getMonthValue()返回的是月份的值（1-12，符合常人的思维，没有从0开始），而getMonth()返回的是一个枚举，即月份的大写
        System.out.println(localDate1.getYear() + " ," + localDate1.getMonthValue() + " ," + localDate1.getDayOfMonth());

        System.out.println(".................");

        // 以前使用Date类来构造年月日的时候，需要减去1900年，用这个差值来作为年份，但是现在不需要了，可以使用LocalDate.of()来构造
        LocalDate localDate2 = LocalDate.of(2019, 9, 10);
        System.out.println(localDate2);

        System.out.println(".................");

        LocalDate localDate3 = LocalDate.of(2010, 3, 3);
        // MonthDay是只包含月份和天数，没有年，即几月几号。可以运用于我们过生日的时候或者是执行重复任务的时候，不需要关注年的时候
        MonthDay monthDay1 = MonthDay.of(localDate3.getMonth(), localDate3.getDayOfMonth());
        MonthDay monthDay2 = MonthDay.from(LocalDate.of(2011, 3, 3));

        // 都是3月3号，所以是会输出“equals”，由此可见MonthDay重写了equals方法
        if (monthDay1.equals(monthDay2)) {
            System.out.println("equals");
        } else {
            System.out.println("not equals");
        }

        System.out.println(".................");

        LocalTime time1 = LocalTime.now();
        System.out.println("当前时间： " + time1);
        LocalTime time2 = time1.plusHours(3).plusMinutes(20);
        System.out.println("修改之后的时间： " + time2);

        System.out.println(".................");

        // plus是一个比较通用的方法，第一个参数是一个数值，第二个参数可以是周/月/年等等
        LocalDate localDate4 = localDate1.plus(2, ChronoUnit.WEEKS);
        System.out.println(localDate4);
        LocalDate localDate5 = localDate1.minus(1, ChronoUnit.MONTHS);
        System.out.println(localDate5);

        System.out.println(".................");

        // Clock表示的是当前时刻的意思
        Clock clock = Clock.systemDefaultZone();
        System.out.println(clock.millis());// 获得当前的毫秒

        System.out.println(".................");

        LocalDate localDate6 = LocalDate.now();
        LocalDate localDate7 = LocalDate.of(2019, 9, 7);
        System.out.println(localDate6.isBefore(localDate7));
        System.out.println(localDate6.isAfter(localDate7));
        System.out.println(localDate6.equals(localDate7));

        System.out.println(".................");

        // ZoneId表示的是时区
        Set<String> set = ZoneId.getAvailableZoneIds();
        // 利用匿名内部类的形式来构造treeSet，这样写起来比较简单，{}括起来表示的是代码块，在创建对象的时候，代码块就会被执行。
        TreeSet<String> treeSet = new TreeSet<String>() {
            {
            addAll(set);
            }
    };
        treeSet.stream().forEach(System.out::println);

        System.out.println(".................");

        //localDateTime1: 2019-09-07T12:36:48.800
        //zonedDateTime1: 2019-09-07T12:36:48.800+08:00[Asia/Shanghai]
        LocalDateTime localDateTime1 = LocalDateTime.now();
        System.out.println(localDateTime1);// 具体可以看它们的toString()方法是如何拼接的

        ZoneId zoneId = ZoneId.of("Asia/Shanghai");
        ZonedDateTime zonedDateTime1 = ZonedDateTime.of(localDateTime1, zoneId);
        System.out.println(zonedDateTime1);

        System.out.println(".................");

        // 只含有年和月
        YearMonth yearMonth1 = YearMonth.now();
        System.out.println(yearMonth1.getMonthValue());
        System.out.println(yearMonth1.isLeapYear());
        System.out.println(yearMonth1.lengthOfMonth());
        System.out.println(yearMonth1.lengthOfYear());

        System.out.println(".................");

         LocalDate localDate8 = LocalDate.now();
         LocalDate localDate9 = LocalDate.of(2020, 10, 10);

         // 可以求出两个date的时间间隔：在这（今天：2019-9-7）相差一年一个月零三天
         Period period = Period.between(localDate8, localDate9);
        System.out.println(period);
        System.out.println(period.getYears());
        System.out.println(period.getMonths());
        System.out.println(period.getDays());

        System.out.println(".................");
        // 当前时间是2019-09-07T12:52:44.700，输出结果是2019-09-07T04:52:44.700Z
        // 所以，Instant表示的是不带时区的标准的UTC时间的某一时刻，因为咱们位于东8区，所以需要减去8个小时的时间才是标准UTC时间
        System.out.println(Instant.now());

    }
}

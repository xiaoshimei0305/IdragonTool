package store.idragon.tool.example.bean;

import lombok.Data;

/**
 * @author xiaoshimei0305
 * date  2021/5/25 9:29 下午
 * description
 * @version 1.0
 */
@Data
public class Person {
    private String phone;
    private String name;
    private String time;
    private String content;

    public Person(String phone, String name, String time, String content) {
        this.phone = phone;
        this.name = name;
        this.time = time;
        this.content = content;
    }
}

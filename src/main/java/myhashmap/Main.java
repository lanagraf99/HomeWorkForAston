package myhashmap;

public class Main {
    public static void main(String[] args) {
        MyHashMap<String, Integer> map = new MyHashMap<>();
        for (int i = 0; i < 20; i++) {
            map.put("key" + i, i);
        }
        System.out.println(map.size());          // 20
        System.out.println(map.get("key15"));    // 15
        System.out.println(map.get("key0"));     // 0
        System.out.println(map.remove("key15")); //15
        System.out.println(map.get("key15"));    //null
        System.out.println(map.size());          // 19
    }
}
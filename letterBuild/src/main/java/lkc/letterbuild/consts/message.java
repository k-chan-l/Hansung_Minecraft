package lkc.letterbuild.consts;

import org.bukkit.ChatColor;

public class message {
    public static final String guide = "[use help command : /letter help]";
    public static final String help = ChatColor.GREEN + "usage : /letter <option> <instance>" +
            "\n<option> -> (list, help, build)" +
            ChatColor.YELLOW + "\nlist : 건설할 블록 종류를 사용자에게 보여줍니다." +
            ChatColor.WHITE + "\n<instance> : X" +
            "\n" +
            ChatColor.YELLOW + "\nhelp : 사용방법을 알려줍니다." +
            ChatColor.WHITE + "\n<instance> : x" +
            "\n" +
            ChatColor.YELLOW + "\nbuild : 문자를 입력하면 해당 문자에 해당하는 5*5 블럭을 건설합니다." +
            ChatColor.WHITE + "\n<instance> : <X> <Y> <Z> <letter> <MainBlockType> <BackgroundBlockType>" +
            "\n위의 instance들을 순서대로 입력"+
            "\n"+
            "\n<X> : 글자를 건설 할 왼쪽 아래의 블럭의 X좌표" +
            "\n<Y> : 글자를 건설 할 왼쪽 아래의 블럭의 Y좌표" +
            "\n<Z> : 글자를 건설 할 왼쪽 아래의 블럭의 Z좌표" +
            "\n<letter> : 건설할 글자의 종류 숫자,알파벳 대소문자 사용가능" +
            "\n<MainBlockType> : 글자의 블록 종류" +
            "\n<BackgroundBlockType> : 배경의 블록 종류";
}

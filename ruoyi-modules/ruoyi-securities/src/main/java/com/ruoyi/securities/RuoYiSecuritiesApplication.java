package com.ruoyi.securities;

import com.ruoyi.common.security.annotation.EnableCustomConfig;
import com.ruoyi.common.security.annotation.EnableRyFeignClients;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 财富任务
 * 
 * @author linhf
 */
@EnableCustomConfig
@EnableRyFeignClients   
@SpringBootApplication
public class RuoYiSecuritiesApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(RuoYiSecuritiesApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  财富服务模块启动成功   ლ(´ڡ`ლ)ﾞ  \n" +
                " .-------.       ____     __        \n" +
                " |  _ _   \\      \\   \\   /  /    \n" +
                " | ( ' )  |       \\  _. /  '       \n" +
                " |(_ o _) /        _( )_ .'         \n" +
                " | (_,_).' __  ___(_ o _)'          \n" +
                " |  |\\ \\  |  ||   |(_,_)'         \n" +
                " |  | \\ `'   /|   `-'  /           \n" +
                " |  |  \\    /  \\      /           \n" +
                " ''-'   `'-'    `-..-'              ");
    }
}
